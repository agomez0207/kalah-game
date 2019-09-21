package com.backbase.game.rest.controller.advice;

class ExceptionResponseMessage {
    private String exceptionMessage;

    ExceptionResponseMessage(String msg){
        this.exceptionMessage = msg;
    }

    public String getMessage() {
        return exceptionMessage;
    }

    public void setMessage(String message) {
        this.exceptionMessage = message;
    }
}
