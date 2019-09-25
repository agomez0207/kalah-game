package com.backbase.game.service.mappers;

import com.backbase.game.repository.dao.GameDAO;
import com.backbase.game.service.bo.Game;
import com.backbase.game.service.bo.GameStatus;
import com.backbase.game.service.bo.Player;

import org.springframework.stereotype.Component;

/**
 * Service mapper for convert a {@link Game} business object into a {@link GameDAO} data access object and vice versa.
 *
 * @author andres.gomez
 */
@Component
public class GameMapper {

    /**
     * Converts a {@link Game} business object into a {@link GameDAO} object.
     *
     * @param game {@link Game} to be converted.
     * @return The {@link GameDAO} converted
     */
    public GameDAO getGameDAO(final Game game) {
        GameDAO gameDAO = new GameDAO();

        gameDAO.setId(game.getId());
        gameDAO.setUri(game.getUri());
        gameDAO.setStatus(game.getStatus().toString());
        gameDAO.setBoard(game.getBoard());
        gameDAO.setCurrentPlayer(game.getCurrentPlayer().toString());

        return gameDAO;
    }

    /**
     * Converts a {@link GameDAO} object into a {@link Game}
     *
     * @param gameDAO {@link GameDAO} to be converted.
     * @return The {@link Game} converted
     */
    public Game getGame(final GameDAO gameDAO) {
        Game game = new Game();

        game.setId(gameDAO.getId());
        game.setUri(gameDAO.getUri());
        game.setStatus(GameStatus.valueOf(gameDAO.getStatus()));
        game.setBoard(gameDAO.getBoard());
        game.setCurrentPlayer(Player.valueOf(gameDAO.getCurrentPlayer()));

        return game;
    }
}
