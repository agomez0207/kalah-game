package com.backbase.game.service.mappers;

import com.backbase.game.repository.dao.GameDAO;
import com.backbase.game.service.GameServiceTestConstants;
import com.backbase.game.service.bo.Game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for {@link GameMapper}
 *
 * @author andres.gomez
 */
public class GameMapperTest {

    private GameMapper gameMapper;
    private Game game;
    private GameDAO gameDAO;

    @Before
    public void setUp() throws Exception {
        this.gameMapper = new GameMapper();
        this.game = GameServiceTestConstants.getGame();
        this.gameDAO = GameServiceTestConstants.getGameDAO();
    }

    @Test
    public void getGameDAOTest() {
        GameDAO gameDAOMapped = gameMapper.getGameDAO(game);

        assertEquals(gameDAOMapped.getId(), gameDAO.getId());
        assertEquals(gameDAOMapped.getUri(), gameDAO.getUri());
        assertEquals(gameDAOMapped.getStatus(), gameDAO.getStatus());
        assertEquals(gameDAOMapped.getCurrentPlayer(), gameDAO.getCurrentPlayer());
        assertEquals(gameDAOMapped.getBoard(), gameDAO.getBoard());
    }

    @Test
    public void getGameTest() {
        Game gameMapped = gameMapper.getGame(gameDAO);

        assertEquals(gameMapped.getId(), gameDAO.getId());
        assertEquals(gameMapped.getUri(), gameDAO.getUri());
        assertEquals(gameMapped.getStatus().toString(), gameDAO.getStatus());
        assertEquals(gameMapped.getCurrentPlayer().toString(), gameDAO.getCurrentPlayer());
        assertEquals(gameMapped.getBoard(), gameDAO.getBoard());
    }
}