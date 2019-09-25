package com.backbase.game.rest.dtos;

import java.util.Map;

/**
 * Game data transfer object (DTO)
 *
 * @author andres.gomez
 */
public class GameDTO {

    private int id;
    private String uri;
    private Map<Integer, Integer> board;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<Integer, Integer> getBoard() {
        return board;
    }

    public void setBoard(Map<Integer, Integer> board) {
        this.board = board;
    }
}