package com.backbase.game.service.exceptions;

public class MoveNotValidException extends RuntimeException {
    public MoveNotValidException(String errorMsg) {
        super(errorMsg);
    }
}
