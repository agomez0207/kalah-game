package com.backbase.game.service;

import com.backbase.game.service.bo.Game;
import com.backbase.game.service.bo.Player;
import com.backbase.game.service.exceptions.MoveNotValidException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for {@link GameServiceFacade}
 *
 * @author andres.gomez
 */
public class GameServiceFacadeTest {

    private GameServiceFacade gameServiceFacade;
    private Game game = GameServiceTestConstants.getGame();

    @Before
    public void setUp() throws Exception {
        this.gameServiceFacade = new GameServiceFacade();
    }

    @Test
    public void moveStonesTest() {
        Game gameUpdated = gameServiceFacade.moveStones(game, GameServiceTestConstants.STARTER_PIT_ID);

        assertEquals(GameServiceTestConstants.BOARD_STONES_MOVED, gameUpdated.getBoard());
    }

    @Test(expected = MoveNotValidException.class)
    public void invalidMoveStonesTest() {
        gameServiceFacade.moveStones(game, GameServiceTestConstants.SECOND_PLAYER_PIT_ID);
    }

    @Test
    public void moveStonesOverflowBoardTest() {
        game.setBoard(GameServiceTestConstants.BOARD_STONES_OVERFLOW);
        Game gameUpdated = gameServiceFacade.moveStones(game, GameServiceTestConstants.STARTER_PIT_ID);

        assertEquals(GameServiceTestConstants.BOARD_STONES_OVERFLOW_AFTER_MOVE, gameUpdated.getBoard());
    }

    @Test
    public void moveStonesTakesOpponentStonesAndLooseTurnTest() {
        game.setBoard(GameServiceTestConstants.BOARD_STONES_TAKES_OPPONENT_STONES);
        Player currentPlayer = game.getCurrentPlayer();

        Game gameUpdated = gameServiceFacade.moveStones(game, GameServiceTestConstants.STARTER_PIT_ID);

        assertEquals(GameServiceTestConstants.BOARD_STONES_TAKES_OPPONENT_STONES_AFTER_MOVE, gameUpdated.getBoard());
        assertEquals(currentPlayer.getOppositePlayer(), gameUpdated.getCurrentPlayer());
    }

    @Test
    public void moveStonesLastStoneOnPlayerKalah() {
        Player currentPlayer = game.getCurrentPlayer();

        Game gameUpdated = gameServiceFacade.moveStones(game, GameServiceTestConstants.FIRST_PIT_ID);

        assertEquals(currentPlayer, gameUpdated.getCurrentPlayer());
    }

    @Test
    public void moveStonesSecondPlayer() {
        game.setCurrentPlayer(Player.SECOND_PLAYER);

        Game gameUpdated =
                gameServiceFacade.moveStones(game, GameServiceTestConstants.SECOND_PLAYER_PIT_ID);

        assertEquals(Player.FIRST_PLAYER, gameUpdated.getCurrentPlayer());
    }
}