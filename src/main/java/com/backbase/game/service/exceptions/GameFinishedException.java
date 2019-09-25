package com.backbase.game.service.exceptions;

/**
 * Exception that is thrown when a stone is tried to be moved on a finished game.
 *
 * @author andres.gomez
 */
public class GameFinishedException extends RuntimeException {
    public GameFinishedException(final String msg) {
        super(msg);
    }
}
