package com.backbase.game.service.exceptions;

/**
 * Exception that is thrown when a stone is tried to be moved on pit id that doesn't belongs to the current player
 * or if the pit id is not valid.
 *
 * @author andres.gomez
 */
public class MoveNotValidException extends RuntimeException {
    public MoveNotValidException(String errorMsg) {
        super(errorMsg);
    }
}
