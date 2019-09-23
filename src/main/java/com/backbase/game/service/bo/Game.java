package com.backbase.game.service.bo;

import java.util.Map;

/**
 * Game business object.
 *
 * @author andres.gomez
 */
public class Game {

    private int id;
    private String uri;
    private GameStatus status;
    private Player currentPlayer;
    private Map<Integer, Integer> board;

    /**
     * @return Identifier of the game.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id Game identifier to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Identifier of the game.
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri Game URI to set.
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return Status of the game.
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * @param status Game status to set.
     */
    public void setStatus(GameStatus status) {
        this.status = status;
    }

    /**
     * @return The current player of the game.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @param currentPlayer Current player of the game to set.
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * @return The board of the game.
     */
    public Map<Integer, Integer> getBoard() {
        return board;
    }

    /**
     * @param board Board to set.
     */
    public void setBoard(Map<Integer, Integer> board) {
        this.board = board;
    }
}