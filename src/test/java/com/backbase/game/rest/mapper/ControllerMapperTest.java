package com.backbase.game.rest.mapper;

import com.backbase.game.rest.dtos.GameDTO;
import com.backbase.game.rest.dtos.NewGameDTO;
import com.backbase.game.service.GameServiceTestConstants;
import com.backbase.game.service.bo.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for {@link ControllerMapper}
 */
public class ControllerMapperTest {

    private ControllerMapper controllerMapper;
    private GameDTO gameDTO;
    private NewGameDTO newGameDTO;
    private Game game;

    @Before
    public void setUp() throws Exception {
        gameDTO = GameServiceTestConstants.getGameDTO();
        newGameDTO = GameServiceTestConstants.getNewGameDTO();
        game = GameServiceTestConstants.getGame();

        this.controllerMapper = new ControllerMapper();
    }

    @Test
    public void getNewGameDTOTest() {

        NewGameDTO newGame = controllerMapper.getNewGameDTO(game);

        assertEquals(newGame.getId(), newGameDTO.getId());
        assertEquals(newGame.getUri(), newGameDTO.getUri());
    }

    @Test
    public void getGameDTOTest() {
        GameDTO gameDTOMapped = controllerMapper.getGameDTO(game);

        assertEquals(gameDTOMapped.getId(), gameDTO.getId());
        assertEquals(gameDTOMapped.getUri(), gameDTO.getUri());
        assertEquals(gameDTOMapped.getBoard(), gameDTO.getBoard());
    }
}