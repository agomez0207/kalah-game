package com.backbase.game.rest.controller.advice;

import com.backbase.game.service.exceptions.GameNotFoundException;
import com.backbase.game.service.exceptions.MoveNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class KalahRestControllerAdvice {

    @ExceptionHandler(value= {GameNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponseMessage gameNotFound(GameNotFoundException exception){
        return new ExceptionResponseMessage(exception.getMessage());
    }

    @ExceptionHandler(value= {MoveNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponseMessage moveNotValid(MoveNotValidException exception){
        return new ExceptionResponseMessage(exception.getMessage());
    }
}
