package com.backbase.game.rest.controller;

import com.backbase.game.rest.dtos.GameDTO;
import com.backbase.game.rest.dtos.NewGameDTO;
import com.backbase.game.rest.mapper.ControllerMapper;
import com.backbase.game.service.GameService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class KalahRestController {

    private GameService gameService;
    private ControllerMapper controllerMapper;

    public KalahRestController(GameService gameService, ControllerMapper controllerMapper){
        this.gameService = gameService;
        this.controllerMapper = controllerMapper;
    }
    
    @PostMapping(path = "/games", consumes = "application/json")
    public NewGameDTO createGame(HttpServletRequest httpServletRequest) {
        String uri = httpServletRequest.getRequestURL().toString();
        return controllerMapper.getNewGameDTO(gameService.createGame(uri));
    }

    @PutMapping(path = "/games/{gameId}/pits/{pitId}", consumes = "application/json")
    public GameDTO makeMove(@PathVariable(name="gameId") int gameId,
                            @PathVariable(value="pitId") int pitId) {
        return controllerMapper.getGameDTO(gameService.makeMove(gameId,pitId));
    }
}