package com.backbase.game.service;

import com.backbase.game.repository.dao.GameDAO;
import com.backbase.game.service.bo.BoardConfig;
import com.backbase.game.service.bo.Game;
import com.backbase.game.service.bo.GameStatus;
import com.backbase.game.service.bo.Player;

import java.util.HashMap;
import java.util.Map;

class GameServiceTestConstants {
    private static final Map<Integer, Integer> INITIAL_BOARD = BoardConfig.INITIAL_BOARD;
    private static final Player FIRST_PLAYER = Player.FIRST_PLAYER;
    static final int GAME_ID = 1;
    static final GameStatus DREW_STATUS = GameStatus.DREW;
    static final GameStatus SECOND_PLAYER_WON_STATUS = GameStatus.SECOND_PLAYER_WON;
    static final GameStatus FIRST_PLAYER_WON_STATUS = GameStatus.FIRST_PLAYER_WON;
    static final String URI = "https://testing-uri.com";
    static final String URI_ID = URI + "/" + GAME_ID;
    static final int STARTER_PIT_ID = 2;
    static final int FIRST_PIT_ID = 1;
    static final int SECOND_PLAYER_PIT_ID = 8;
    static final Map<Integer, Integer> BOARD_STONES_MOVED =
            new HashMap<Integer, Integer>() {{
                put(1, 6);
                put(2, 0);
                put(3, 7);
                put(4, 7);
                put(5, 7);
                put(6, 7);
                put(7, 1);
                put(8, 7);
                put(9, 6);
                put(10, 6);
                put(11, 6);
                put(12, 6);
                put(13, 6);
                put(14, 0);
            }};
    static final Map<Integer, Integer> BOARD_STONES_OVERFLOW =
            new HashMap<Integer, Integer>() {{
                put(1, 0);
                put(2, 24);
                put(3, 0);
                put(4, 0);
                put(5, 6);
                put(6, 6);
                put(7, 2);
                put(8, 8);
                put(9, 8);
                put(10, 8);
                put(11, 8);
                put(12, 8);
                put(13, 8);
                put(14, 0);
            }};
    static final Map<Integer, Integer> BOARD_STONES_OVERFLOW_AFTER_MOVE =
            new HashMap<Integer, Integer>() {{
                put(1, 1);
                put(2, 1);
                put(3, 2);
                put(4, 2);
                put(5, 8);
                put(6, 8);
                put(7, 4);
                put(8, 10);
                put(9, 10);
                put(10, 10);
                put(11, 10);
                put(12, 10);
                put(13, 10);
                put(14, 0);
            }};

    static final Map<Integer, Integer> BOARD_STONES_TAKES_OPPONENT_STONES =
            new HashMap<Integer, Integer>() {{
                put(1, 1);
                put(2, 1);
                put(3, 0);
                put(4, 2);
                put(5, 8);
                put(6, 10);
                put(7, 4);
                put(8, 10);
                put(9, 10);
                put(10, 10);
                put(11, 10);
                put(12, 10);
                put(13, 10);
                put(14, 0);
            }};

    static final Map<Integer, Integer> BOARD_STONES_TAKES_OPPONENT_STONES_AFTER_MOVE =
            new HashMap<Integer, Integer>() {{
                put(1, 1);
                put(2, 0);
                put(3, 0);
                put(4, 2);
                put(5, 8);
                put(6, 10);
                put(7, 15);
                put(8, 10);
                put(9, 10);
                put(10, 10);
                put(11, 0);
                put(12, 10);
                put(13, 10);
                put(14, 0);
            }};
    static Game getGame() {
        Game game = new Game();

        game.setId(GameServiceTestConstants.GAME_ID);
        game.setCurrentPlayer(GameServiceTestConstants.FIRST_PLAYER);
        game.setUri(GameServiceTestConstants.URI);
        game.setBoard(GameServiceTestConstants.INITIAL_BOARD);
        game.setStatus(GameStatus.IN_PROGRESS);

        return game;
    }

    static GameDAO getGameDAO() {
        GameDAO gameDAO = new GameDAO();

        gameDAO.setId(GameServiceTestConstants.GAME_ID);
        gameDAO.setCurrentPlayer(GameServiceTestConstants.FIRST_PLAYER.toString());
        gameDAO.setUri(GameServiceTestConstants.URI);
        gameDAO.setBoard(GameServiceTestConstants.INITIAL_BOARD);
        gameDAO.setStatus(GameStatus.IN_PROGRESS.toString());

        return gameDAO;
    }

    static Map<Integer, Integer> getNewFinishedBoard() {
        return new HashMap<Integer, Integer>() {{
            put(1, 0);
            put(2, 0);
            put(3, 0);
            put(4, 0);
            put(5, 0);
            put(6, 0);
            put(7, 0);
            put(8, 6);
            put(9, 6);
            put(10, 6);
            put(11, 6);
            put(12, 6);
            put(13, 6);
            put(14, 0);
        }};
    }
}
