package com.backbase.game.rest.controller;

import com.backbase.game.rest.dtos.GameDTO;
import com.backbase.game.rest.dtos.NewGameDTO;
import com.backbase.game.rest.mapper.ControllerMapper;
import com.backbase.game.service.GameService;
import com.backbase.game.service.GameServiceTestConstants;
import com.backbase.game.service.bo.Game;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link KalahRestController}
 *
 * @author andres.gomez
 */
public class KalahRestControllerTest {

    private KalahRestController kalahRestController;
    private Game game;
    private GameDTO gameDTO;
    private NewGameDTO newGameDTO;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private GameService gameService;

    @Mock
    private ControllerMapper controllerMapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        this.game = GameServiceTestConstants.getGame();
        this.gameDTO = GameServiceTestConstants.getGameDTO();
        this.newGameDTO = GameServiceTestConstants.getNewGameDTO();

        this.kalahRestController = new KalahRestController(gameService, controllerMapper);
    }

    @Test
    public void createGameTest() {
        StringBuffer stringBuffer = new StringBuffer(GameServiceTestConstants.URI);

        when(httpServletRequest.getRequestURL()).thenReturn(stringBuffer);
        when(gameService.createGame(GameServiceTestConstants.URI)).thenReturn(game);
        when(controllerMapper.getNewGameDTO(game))
                .thenReturn(newGameDTO);

        NewGameDTO newGame = kalahRestController.createGame(httpServletRequest);

        assertEquals(newGame, newGameDTO);

        verify(httpServletRequest).getRequestURL();
        verify(gameService).createGame(GameServiceTestConstants.URI);
        verify(controllerMapper).getNewGameDTO(game);
    }

    @Test
    public void makeMoveTest() {
        int gameId = GameServiceTestConstants.GAME_ID;
        int pitId = GameServiceTestConstants.STARTER_PIT_ID;

        when(gameService.makeMove(gameId, pitId))
                .thenReturn(game);
        when(controllerMapper.getGameDTO(game)).thenReturn(gameDTO);

        GameDTO gameUpdated = kalahRestController.makeMove(gameId, pitId);

        assertEquals(gameUpdated, gameDTO);

        verify(gameService).makeMove(gameId, pitId);
        verify(controllerMapper).getGameDTO(game);
    }
}