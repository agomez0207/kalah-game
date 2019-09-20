package com.backbase.game.rest.dtos;


import java.util.Map;

public class GameDTO {

    private int id;
    private String uri;
    private Map<Integer, Integer> board;

    public int getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public Map<Integer, Integer> getBoard() {
        return board;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setBoard(Map<Integer, Integer> board) {
        this.board = board;
    }
}