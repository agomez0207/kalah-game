package com.backbase.game.service;

import com.backbase.game.service.bo.Game;
import com.backbase.game.service.exceptions.GameFinishedException;
import com.backbase.game.service.exceptions.GameNotFoundException;

/**
 * Service interface containing all the services related with a game.
 *
 * @author andres.gomez
 */
public interface GameService {

    /**
     * Creates a new game and returns the game created.
     *
     * @param uri String containing the URI of the game to be created.
     * @return The game created.
     */
    Game createGame(final String uri);

    /**
     * Moves the stones of a game starting from the {@param pitId}
     *
     * @param gameId Identifier of the game to move its stones.
     * @param pitId Identifier of the pit to move the stones.
     * @return The game with the board updated.
     *
     * @throws GameNotFoundException when a move is tried on a not existing game.
     * @throws GameFinishedException when a move is tried on a finished game.
     */
    Game makeMove(final int gameId, final int pitId);
}
