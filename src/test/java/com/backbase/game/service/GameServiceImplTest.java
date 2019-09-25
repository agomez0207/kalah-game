package com.backbase.game.service;

import com.backbase.game.repository.GameRepository;
import com.backbase.game.repository.dao.GameDAO;
import com.backbase.game.service.bo.Game;
import com.backbase.game.service.bo.GameStatus;
import com.backbase.game.service.bo.Player;
import com.backbase.game.service.exceptions.GameFinishedException;
import com.backbase.game.service.exceptions.GameNotFoundException;
import com.backbase.game.service.mappers.GameMapper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link GameServiceImpl}
 */
public class GameServiceImplTest {

    private GameServiceImpl gameService;
    private Map<Integer, Integer> FINISHED_BOARD;
    private Game game;
    private Game gameToUpdate;
    private GameDAO gameDAO;
    private GameDAO gameDAOToUpdate;

    @Mock
    private GameRepository gameRepository;
    @Mock
    private GameMapper gameMapper;
    @Mock
    private GameServiceFacade gameServiceFacade;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        this.FINISHED_BOARD = GameServiceTestConstants.getNewFinishedBoard();
        this.game = GameServiceTestConstants.getGame();
        this.gameToUpdate = GameServiceTestConstants.getGame();
        this.gameDAO = GameServiceTestConstants.getGameDAO();
        this.gameDAOToUpdate = GameServiceTestConstants.getGameDAO();

        this.gameService = new GameServiceImpl(gameRepository, gameMapper, gameServiceFacade);
    }

    @Test
    public void createGameTest() {
        when(gameMapper.getGameDAO(any(Game.class))).thenReturn(gameDAO);
        when(gameRepository.save(gameDAO)).thenReturn(gameDAO);
        when(gameMapper.getGame(gameDAO)).thenReturn(game);

        Game gameCreated = gameService.createGame(GameServiceTestConstants.URI);

        verify(gameMapper).getGameDAO(game);
        verify(gameRepository, times(2)).save(gameDAO);
        verify(gameMapper, times(2)).getGame(gameDAO);

        assertEquals(GameServiceTestConstants.URI_ID, gameCreated.getUri());
    }

    @Test
    public void makeValidMoveTest() {
        gameToUpdate.setBoard(GameServiceTestConstants.BOARD_STONES_MOVED);
        gameDAOToUpdate.setBoard(GameServiceTestConstants.BOARD_STONES_MOVED);

        when(gameRepository.findById(GameServiceTestConstants.GAME_ID))
                .thenReturn(Optional.of(gameDAO));
        when(gameMapper.getGame(gameDAO)).thenReturn(game);
        when(gameServiceFacade.moveStones(game, GameServiceTestConstants.STARTER_PIT_ID))
                .thenReturn(gameToUpdate);
        when(gameMapper.getGameDAO(gameToUpdate)).thenReturn(gameDAOToUpdate);
        when(gameRepository.save(gameDAOToUpdate)).thenReturn(gameDAOToUpdate);

        Game movedGame = gameService.makeMove(
                GameServiceTestConstants.GAME_ID, GameServiceTestConstants.STARTER_PIT_ID);

        assertEquals(GameServiceTestConstants.BOARD_STONES_MOVED, movedGame.getBoard());

        runMakeMoveVerifies();
    }

    @Test
    public void makeFinishDrewValidMoveTest() {
        gameToUpdate.setBoard(FINISHED_BOARD);
        gameToUpdate.setCurrentPlayer(Player.SECOND_PLAYER);
        gameDAOToUpdate.setBoard(FINISHED_BOARD);
        gameDAOToUpdate.setCurrentPlayer(Player.SECOND_PLAYER.toString());

        FINISHED_BOARD.put(7, 36);

        when(gameRepository.findById(GameServiceTestConstants.GAME_ID))
                .thenReturn(Optional.of(gameDAO));
        when(gameMapper.getGame(gameDAO)).thenReturn(game);
        when(gameServiceFacade.moveStones(game, GameServiceTestConstants.STARTER_PIT_ID))
                .thenReturn(gameToUpdate);
        when(gameMapper.getGameDAO(gameToUpdate)).thenReturn(gameDAOToUpdate);
        when(gameRepository.save(gameDAOToUpdate)).thenReturn(gameDAOToUpdate);

        Game movedGame = gameService.makeMove(
                GameServiceTestConstants.GAME_ID, GameServiceTestConstants.STARTER_PIT_ID);

        assertEquals(FINISHED_BOARD, movedGame.getBoard());
        assertEquals(GameServiceTestConstants.DREW_STATUS, movedGame.getStatus());

        runMakeMoveVerifies();
    }

    @Test
    public void makeFinishSecondPlayerWonValidMoveTest() {
        gameToUpdate.setBoard(FINISHED_BOARD);
        gameDAOToUpdate.setBoard(FINISHED_BOARD);

        FINISHED_BOARD.put(7, 12);
        FINISHED_BOARD.put(14, 24);

        when(gameRepository.findById(GameServiceTestConstants.GAME_ID))
                .thenReturn(Optional.of(gameDAO));
        when(gameMapper.getGame(gameDAO)).thenReturn(game);
        when(gameServiceFacade.moveStones(game, GameServiceTestConstants.STARTER_PIT_ID))
                .thenReturn(gameToUpdate);
        when(gameMapper.getGameDAO(gameToUpdate)).thenReturn(gameDAOToUpdate);
        when(gameRepository.save(gameDAOToUpdate)).thenReturn(gameDAOToUpdate);

        Game movedGame = gameService.makeMove(
                GameServiceTestConstants.GAME_ID, GameServiceTestConstants.STARTER_PIT_ID);

        assertEquals(FINISHED_BOARD, movedGame.getBoard());
        assertEquals(GameServiceTestConstants.SECOND_PLAYER_WON_STATUS, movedGame.getStatus());

        runMakeMoveVerifies();
    }

    @Test
    public void makeFinishFirstPlayerWonValidMoveTest() {
        gameToUpdate.setBoard(FINISHED_BOARD);
        gameDAOToUpdate.setBoard(FINISHED_BOARD);

        FINISHED_BOARD.put(7, 48);
        FINISHED_BOARD.put(12, 0);
        FINISHED_BOARD.put(13, 0);
        FINISHED_BOARD.put(14, 12);

        when(gameRepository.findById(GameServiceTestConstants.GAME_ID)).thenReturn(Optional.of(gameDAO));
        when(gameMapper.getGame(gameDAO)).thenReturn(game);
        when(gameServiceFacade.moveStones(game, GameServiceTestConstants.STARTER_PIT_ID))
                .thenReturn(gameToUpdate);
        when(gameMapper.getGameDAO(gameToUpdate)).thenReturn(gameDAOToUpdate);
        when(gameRepository.save(gameDAOToUpdate)).thenReturn(gameDAOToUpdate);

        Game movedGame = gameService.makeMove(
                GameServiceTestConstants.GAME_ID, GameServiceTestConstants.STARTER_PIT_ID);

        assertEquals(FINISHED_BOARD, movedGame.getBoard());
        assertEquals(GameServiceTestConstants.FIRST_PLAYER_WON_STATUS, movedGame.getStatus());

        runMakeMoveVerifies();
    }

    @Test(expected = GameNotFoundException.class)
    public void makeMoveOnNotExistingGame(){
        when(gameRepository.findById(GameServiceTestConstants.GAME_ID))
                .thenReturn(Optional.empty());

        gameService.makeMove(
                GameServiceTestConstants.GAME_ID, GameServiceTestConstants.STARTER_PIT_ID);
    }

    @Test(expected = GameFinishedException.class)
    public void makeMoveOnGameFinishedTest() {
        game.setStatus(GameStatus.FIRST_PLAYER_WON);
        gameDAOToUpdate.setStatus(GameStatus.FIRST_PLAYER_WON.toString());

        when(gameRepository.findById(GameServiceTestConstants.GAME_ID))
                .thenReturn(Optional.of(gameDAOToUpdate));
        when(gameMapper.getGame(gameDAOToUpdate)).thenReturn(game);

        gameService.makeMove(GameServiceTestConstants.GAME_ID, GameServiceTestConstants.STARTER_PIT_ID);
    }

    private void runMakeMoveVerifies(){
        verify(gameRepository).findById(GameServiceTestConstants.GAME_ID);
        verify(gameMapper).getGame(gameDAO);
        verify(gameServiceFacade).moveStones(game, GameServiceTestConstants.STARTER_PIT_ID);
        verify(gameMapper).getGameDAO(gameToUpdate);
        verify(gameRepository).save(gameDAOToUpdate);
    }
}