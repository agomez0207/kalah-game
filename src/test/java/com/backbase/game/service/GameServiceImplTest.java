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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GameServiceImplTest {

    private static final int GAME_ID = 1;
    private static final Player FIRST_PLAYER = Player.FIRST_PLAYER;
    private static final GameStatus DREW_STATUS = GameStatus.DREW;
    private static final GameStatus SECOND_PLAYER_WON_STATUS = GameStatus.SECOND_PLAYER_WON;
    private static final GameStatus FIRST_PLAYER_WON_STATUS = GameStatus.FIRST_PLAYER_WON;
    private static final Map<Integer, Integer> INITIAL_BOARD = BoardConfig.INITIAL_BOARD;
    private static final String URI = "https://testing-uri.com";
    private static final String URI_ID = URI + "/" + GAME_ID;
    private static final int STARTER_PIT_ID = 2;
    private static final Map<Integer, Integer> BOARD_STONES_MOVED =
            new HashMap<Integer, Integer>() {{
                put(1, 6);
                put(2, 0);
                put(3, 7);
                put(4, 7);
                put(5, 7);
                put(6, 7);
                put(7, 1);
                put(8, 7);
                put(9, 6);
                put(10, 6);
                put(11, 6);
                put(12, 6);
                put(13, 6);
                put(14, 0);
    }};

    Map<Integer, Integer> FINISHED_BOARD;

    private GameServiceImpl gameService;

    @Mock
    private GameRepository gameRepository;
    @Mock
    private GameMapper gameMapper;
    @Mock
    private GameUtils gameUtils;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        FINISHED_BOARD = new HashMap<Integer, Integer>() {{
            put(1, 0);
            put(2, 0);
            put(3, 0);
            put(4, 0);
            put(5, 0);
            put(6, 0);
            put(7, 0);
            put(8, 6);
            put(9, 6);
            put(10, 6);
            put(11, 6);
            put(12, 6);
            put(13, 6);
            put(14, 0);
        }};

        this.gameService = new GameServiceImpl(gameRepository, gameMapper, gameUtils);
    }

    @Test
    public void createGameTest() {

        Game game = getGame();
        GameDAO gameDAO = getGameDAO();

        when(gameMapper.getGameDAO(any(Game.class))).thenReturn(gameDAO);
        when(gameRepository.save(gameDAO)).thenReturn(gameDAO);
        when(gameMapper.getGame(gameDAO)).thenReturn(game);

        Game gameCreated = gameService.createGame(URI);

        verify(gameMapper).getGameDAO(game);
        verify(gameRepository, times(2)).save(gameDAO);
        verify(gameMapper, times(2)).getGame(gameDAO);

        assertEquals(gameCreated.getUri(), URI_ID);
    }

    @Test
    public void makeValidMoveTest() {

        Game game = getGame();
        Game gameUpdated = getGame();
        gameUpdated.setBoard(BOARD_STONES_MOVED);

        GameDAO gameDAO = getGameDAO();
        GameDAO gameDAOUpdated = getGameDAO();
        gameDAOUpdated.setBoard(BOARD_STONES_MOVED);

        when(gameRepository.findById(GAME_ID)).thenReturn(Optional.of(gameDAO));
        when(gameMapper.getGame(gameDAO)).thenReturn(game);
        when(gameUtils.moveStones(game, STARTER_PIT_ID)).thenReturn(gameUpdated);
        when(gameMapper.getGameDAO(gameUpdated)).thenReturn(gameDAOUpdated);
        when(gameRepository.save(gameDAOUpdated)).thenReturn(gameDAOUpdated);

        Game movedGame = gameService.makeMove(GAME_ID, STARTER_PIT_ID);

        assertEquals(movedGame.getBoard(), BOARD_STONES_MOVED);

        verify(gameRepository).findById(GAME_ID);
        verify(gameMapper).getGame(gameDAO);
        verify(gameUtils).moveStones(game, STARTER_PIT_ID);
        verify(gameMapper).getGameDAO(gameUpdated);
        verify(gameRepository).save(gameDAOUpdated);
    }

    @Test
    public void makeFinishDrewValidMoveTest() {

        Game game = getGame();
        Game gameUpdated = getGame();
        gameUpdated.setBoard(FINISHED_BOARD);
        gameUpdated.setCurrentPlayer(Player.SECOND_PLAYER);

        GameDAO gameDAO = getGameDAO();
        GameDAO gameDAOUpdated = getGameDAO();
        gameDAOUpdated.setBoard(FINISHED_BOARD);
        gameDAOUpdated.setCurrentPlayer(Player.SECOND_PLAYER.toString());

        FINISHED_BOARD.put(7, 36);

        when(gameRepository.findById(GAME_ID)).thenReturn(Optional.of(gameDAO));
        when(gameMapper.getGame(gameDAO)).thenReturn(game);
        when(gameUtils.moveStones(game, STARTER_PIT_ID)).thenReturn(gameUpdated);
        when(gameMapper.getGameDAO(gameUpdated)).thenReturn(gameDAOUpdated);
        when(gameRepository.save(gameDAOUpdated)).thenReturn(gameDAOUpdated);

        Game movedGame = gameService.makeMove(GAME_ID, STARTER_PIT_ID);

        assertEquals(movedGame.getBoard(), FINISHED_BOARD);
        assertEquals(movedGame.getStatus(), DREW_STATUS);

        verify(gameRepository).findById(GAME_ID);
        verify(gameMapper).getGame(gameDAO);
        verify(gameUtils).moveStones(game, STARTER_PIT_ID);
        verify(gameMapper).getGameDAO(gameUpdated);
        verify(gameRepository).save(gameDAOUpdated);
    }

    @Test
    public void makeFinishSecondPlayerWonValidMoveTest() {

        Game game = getGame();
        Game gameUpdated = getGame();
        gameUpdated.setBoard(FINISHED_BOARD);

        GameDAO gameDAO = getGameDAO();
        GameDAO gameDAOUpdated = getGameDAO();
        gameDAOUpdated.setBoard(FINISHED_BOARD);

        FINISHED_BOARD.put(7, 12);
        FINISHED_BOARD.put(14, 24);

        when(gameRepository.findById(GAME_ID)).thenReturn(Optional.of(gameDAO));
        when(gameMapper.getGame(gameDAO)).thenReturn(game);
        when(gameUtils.moveStones(game, STARTER_PIT_ID)).thenReturn(gameUpdated);
        when(gameMapper.getGameDAO(gameUpdated)).thenReturn(gameDAOUpdated);
        when(gameRepository.save(gameDAOUpdated)).thenReturn(gameDAOUpdated);

        Game movedGame = gameService.makeMove(GAME_ID, STARTER_PIT_ID);

        assertEquals(movedGame.getBoard(), FINISHED_BOARD);
        assertEquals(movedGame.getStatus(), SECOND_PLAYER_WON_STATUS);

        verify(gameRepository).findById(GAME_ID);
        verify(gameMapper).getGame(gameDAO);
        verify(gameUtils).moveStones(game, STARTER_PIT_ID);
        verify(gameMapper).getGameDAO(gameUpdated);
        verify(gameRepository).save(gameDAOUpdated);
    }

    @Test
    public void makeFinishFirstPlayerWonValidMoveTest() {

        Game game = getGame();
        Game gameUpdated = getGame();
        gameUpdated.setBoard(FINISHED_BOARD);

        GameDAO gameDAO = getGameDAO();
        GameDAO gameDAOUpdated = getGameDAO();
        gameDAOUpdated.setBoard(FINISHED_BOARD);

        FINISHED_BOARD.put(7, 48);
        FINISHED_BOARD.put(12, 0);
        FINISHED_BOARD.put(13, 0);
        FINISHED_BOARD.put(14, 12);

        when(gameRepository.findById(GAME_ID)).thenReturn(Optional.of(gameDAO));
        when(gameMapper.getGame(gameDAO)).thenReturn(game);
        when(gameUtils.moveStones(game, STARTER_PIT_ID)).thenReturn(gameUpdated);
        when(gameMapper.getGameDAO(gameUpdated)).thenReturn(gameDAOUpdated);
        when(gameRepository.save(gameDAOUpdated)).thenReturn(gameDAOUpdated);

        Game movedGame = gameService.makeMove(GAME_ID, STARTER_PIT_ID);

        assertEquals(movedGame.getBoard(), FINISHED_BOARD);
        assertEquals(movedGame.getStatus(), FIRST_PLAYER_WON_STATUS);

        verify(gameRepository).findById(GAME_ID);
        verify(gameMapper).getGame(gameDAO);
        verify(gameUtils).moveStones(game, STARTER_PIT_ID);
        verify(gameMapper).getGameDAO(gameUpdated);
        verify(gameRepository).save(gameDAOUpdated);
    }

    @Test(expected = GameNotFoundException.class)
    public void makeMoveOnNotExistingGame(){
        when(gameRepository.findById(GAME_ID)).thenReturn(Optional.empty());

        gameService.makeMove(GAME_ID, STARTER_PIT_ID);

    }

    @Test(expected = GameFinishedException.class)
    public void makeMoveOnGameFinishedTest() {
        Game gameFinished = getGame();
        gameFinished.setStatus(GameStatus.FIRST_PLAYER_WON);

        GameDAO gameDAOFinished = getGameDAO();
        gameDAOFinished.setStatus(GameStatus.FIRST_PLAYER_WON.toString());


        when(gameRepository.findById(GAME_ID)).thenReturn(Optional.of(gameDAOFinished));
        when(gameMapper.getGame(gameDAOFinished)).thenReturn(gameFinished);

        gameService.makeMove(GAME_ID, STARTER_PIT_ID);
    }

    private Game getGame() {
        Game game = new Game();

        game.setId(GAME_ID);
        game.setCurrentPlayer(FIRST_PLAYER);
        game.setUri(URI);
        game.setBoard(INITIAL_BOARD);
        game.setStatus(GameStatus.IN_PROGRESS);

        return game;
    }

    private GameDAO getGameDAO() {
        GameDAO gameDAO = new GameDAO();

        gameDAO.setId(GAME_ID);
        gameDAO.setCurrentPlayer(FIRST_PLAYER.toString());
        gameDAO.setUri(URI);
        gameDAO.setBoard(INITIAL_BOARD);
        gameDAO.setStatus(GameStatus.IN_PROGRESS.toString());

        return gameDAO;
    }
}