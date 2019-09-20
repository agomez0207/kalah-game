package com.backbase.game.service.mappers;

import com.backbase.game.repository.dao.GameDAO;
import com.backbase.game.service.bo.Game;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public GameDAO getGameDAO(Game game){
        GameDAO gameDAO = new GameDAO();

        gameDAO.setId(game.getId());
        gameDAO.setUri(game.getUri());
        gameDAO.setBoard(game.getBoard());
        gameDAO.setCurrentPlayer(game.getCurrentPlayer());

        return gameDAO;
    }

    public Game getGame(GameDAO gameDAO){

        Game game = new Game();

        game.setId(gameDAO.getId());
        game.setUri(gameDAO.getUri());
        game.setBoard(gameDAO.getBoard());
        game.setCurrentPlayer(gameDAO.getCurrentPlayer());

        return game;
    }
}
