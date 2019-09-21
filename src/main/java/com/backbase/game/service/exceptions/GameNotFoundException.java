package com.backbase.game.service.exceptions;

public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}
