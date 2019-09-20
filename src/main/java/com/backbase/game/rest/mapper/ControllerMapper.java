package com.backbase.game.rest.mapper;

import com.backbase.game.rest.dtos.GameDTO;
import com.backbase.game.rest.dtos.NewGameDTO;
import com.backbase.game.service.bo.Game;
import org.springframework.stereotype.Component;

@Component
public class ControllerMapper {

    public NewGameDTO getNewGameDTO(Game game) {

        NewGameDTO newGameDTO = new NewGameDTO();

        newGameDTO.setId(game.getId());
        newGameDTO.setUri(game.getUri());

        return newGameDTO;
    }

    public GameDTO getGameDTO(Game game) {

        GameDTO gameDTO = new GameDTO();

        gameDTO.setId(game.getId());
        gameDTO.setUri(game.getUri());
        gameDTO.setBoard(game.getBoard());

        return gameDTO;
    }
}
