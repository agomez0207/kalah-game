package com.backbase.game.service;

import com.backbase.game.repository.GameRepository;
import com.backbase.game.repository.dao.GameDAO;
import com.backbase.game.service.bo.BoardConfig;
import com.backbase.game.service.bo.Game;
import com.backbase.game.service.bo.Player;
import com.backbase.game.service.exceptions.GameNotFoundException;
import com.backbase.game.service.mappers.GameMapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
class GameServiceImpl implements GameService {

    private GameRepository gameRepository;
    private GameMapper gameMapper;
    private GameUtils gameUtils;

    GameServiceImpl(
            GameRepository gameRepository, GameMapper gameMapper, GameUtils gameUtils) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
        this.gameUtils = gameUtils;
    }

    @Override
    public Game createGame(String uri) {

        Map<Integer, Integer> initialBoard = BoardConfig.INITIAL_BOARD;

        Game newGame = new Game();

        newGame.setUri(uri);
        newGame.setBoard(initialBoard);
        newGame.setCurrentPlayer(Player.FIRST_PLAYER);

        Game gameCreated = gameMapper.getGame(gameRepository.save(gameMapper.getGameDAO(newGame)));

        gameCreated.setUri(String.format("%s/%s", uri, gameCreated.getId()));

        return gameMapper.getGame(gameRepository.save(gameMapper.getGameDAO(gameCreated)));
    }

    @Override
    public Game makeMove(int gameId, int pitId) {

        Optional<GameDAO> gameDAO = gameRepository.findById(gameId);

        if (gameDAO.isPresent()) {
            Game game = gameMapper.getGame(gameDAO.get());

            gameUtils.moveStones(game, pitId);
            gameRepository.save(gameMapper.getGameDAO(game));

            //validate winner

            return game;
        }

        throw new GameNotFoundException(String.format("Game with id %d not found", gameId));
    }
}
