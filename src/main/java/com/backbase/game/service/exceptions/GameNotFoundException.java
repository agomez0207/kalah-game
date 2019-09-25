package com.backbase.game.service.exceptions;

/**
 * Exception that is thrown when a stone is tried to be moved on a not existing game.
 *
 * @author andres.gomez
 */
public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}
