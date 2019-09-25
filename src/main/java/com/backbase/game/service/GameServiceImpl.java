package com.backbase.game.service;

import com.backbase.game.repository.GameRepository;
import com.backbase.game.repository.dao.GameDAO;
import com.backbase.game.service.bo.BoardConfig;
import com.backbase.game.service.bo.Game;
import com.backbase.game.service.bo.GameStatus;
import com.backbase.game.service.bo.Player;
import com.backbase.game.service.exceptions.GameFinishedException;
import com.backbase.game.service.exceptions.GameNotFoundException;
import com.backbase.game.service.mappers.GameMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @see GameService
 *
 * @author andres.gomez
 */
@Service
class GameServiceImpl implements GameService {

    private GameRepository gameRepository;
    private GameMapper gameMapper;
    private GameServiceFacade gameServiceFacade;

    GameServiceImpl(
            final GameRepository gameRepository,
            final GameMapper gameMapper,
            final GameServiceFacade gameServiceFacade) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
        this.gameServiceFacade = gameServiceFacade;
    }

    /**
     * @see GameService#createGame(String)
     */
    @Override
    public Game createGame(final String uri) {
        Map<Integer, Integer> initialBoard = BoardConfig.INITIAL_BOARD;

        Game newGame = new Game();

        newGame.setUri(uri);
        newGame.setBoard(initialBoard);
        newGame.setCurrentPlayer(Player.FIRST_PLAYER);
        newGame.setStatus(GameStatus.IN_PROGRESS);

        Game gameCreated = gameMapper.getGame(gameRepository.save(gameMapper.getGameDAO(newGame)));

        gameCreated.setUri(String.format("%s/%s", uri, gameCreated.getId()));

        return gameMapper.getGame(gameRepository.save(gameMapper.getGameDAO(gameCreated)));
    }

    /**
     * @see GameService#makeMove(int, int)
     */
    @Override
    public Game makeMove(final int gameId, final int pitId) {
        Optional<GameDAO> gameDAO = gameRepository.findById(gameId);

        if (gameDAO.isPresent()) {
            Game game = gameMapper.getGame(gameDAO.get());

            if (game.getStatus() != GameStatus.IN_PROGRESS) {
                throw new GameFinishedException(
                        String.format("The game has been already finished with status of %s", game.getStatus()));
            }

            Game gameUpdated = gameServiceFacade.moveStones(game, pitId);

            if (isGameFinished(gameUpdated)) {
                finishGame(gameUpdated);
            }

            gameRepository.save(gameMapper.getGameDAO(gameUpdated));

            return gameUpdated;
        }

        throw new GameNotFoundException(String.format("Game with id %d not found", gameId));
    }

    /**
     * Determines whether a game is finished (one of the sides ran out of stones), if it is,
     * the player who still has stones in his/her pits keeps them and puts them in his/hers Kalah.
     *
     * @param game Game to be validated.
     * @return True if the game have finished otherwise will returns false.
     */
    private boolean isGameFinished(Game game) {
        boolean gameFinished = false;
        Player currentPlayer = game.getCurrentPlayer();
        Player oppositePlayer = game.getCurrentPlayer().getOppositePlayer();
        Map<Integer, Integer> board = game.getBoard();
        List<Integer> currentPlayerPits = currentPlayer.getPlayerPits();
        List<Integer> oppositePlayerPits = oppositePlayer.getPlayerPits();

        int currentPlayerTotalStones =
                currentPlayerPits.stream().map(board::get).reduce(0, Integer::sum);

        int oppositePlayerTotalStones =
                oppositePlayerPits.stream().map(board::get).reduce(0, Integer::sum);

        if (currentPlayerTotalStones == 0 || oppositePlayerTotalStones == 0) {
            int currentPlayerKalahStones = board.get(currentPlayer.getKalahId());
            int oppositePlayerKalahStones = board.get(oppositePlayer.getKalahId());

            board.put(currentPlayer.getKalahId(), currentPlayerKalahStones + currentPlayerTotalStones);
            board.put(oppositePlayer.getKalahId(), oppositePlayerKalahStones + oppositePlayerTotalStones);

            gameFinished = true;
        }

        return gameFinished;
    }

    /**
     * Finish a game changing its status.
     *
     * @param game Game to be finished.
     */
    private void finishGame(Game game) {
        Map<Integer, Integer> board = game.getBoard();
        int currentPlayerKalahStones = board.get(BoardConfig.FIRST_PLAYER_KALAH);
        int oppositePlayerKalahStones = board.get(BoardConfig.SECOND_PLAYER_KALAH);

        if (currentPlayerKalahStones > oppositePlayerKalahStones) {
            game.setStatus(GameStatus.FIRST_PLAYER_WON);
        } else if (oppositePlayerKalahStones > currentPlayerKalahStones) {
            game.setStatus(GameStatus.SECOND_PLAYER_WON);
        } else {
            game.setStatus(GameStatus.DREW);
        }
    }
}
