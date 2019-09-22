package com.backbase.game.service.exceptions;

public class GameFinishedException extends RuntimeException {
    public GameFinishedException(final String msg) {
        super(msg);
    }
}
