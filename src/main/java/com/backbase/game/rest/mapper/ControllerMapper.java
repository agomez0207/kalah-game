package com.backbase.game.rest.mapper;

import com.backbase.game.rest.dtos.GameDTO;
import com.backbase.game.rest.dtos.NewGameDTO;
import com.backbase.game.service.bo.Game;

import org.springframework.stereotype.Component;

/**
 * Controller mapper for convert a {@link Game} business object
 * into a {@link GameDTO} or a {@link NewGameDTO} object and vice versa.
 *
 * @author andres.gomez
 */
@Component
public class ControllerMapper {

    /**
     * Converts a {@link Game} business object into a {@link NewGameDTO} object.
     *
     * @param game {@link Game} to be converted.
     * @return The {@link NewGameDTO} converted
     */
    public NewGameDTO getNewGameDTO(final Game game) {

        NewGameDTO newGameDTO = new NewGameDTO();

        newGameDTO.setId(game.getId());
        newGameDTO.setUri(game.getUri());

        return newGameDTO;
    }

    /**
     * Converts a {@link Game} object into a {@link GameDTO}
     *
     * @param game {@link Game} to be converted.
     * @return The {@link GameDTO} converted
     */
    public GameDTO getGameDTO(final Game game) {
        GameDTO gameDTO = new GameDTO();

        gameDTO.setId(game.getId());
        gameDTO.setUri(game.getUri());
        gameDTO.setBoard(game.getBoard());

        return gameDTO;
    }
}
