package com.backbase.game.service.bo;


import java.util.Map;

public class Game {


    private int id;
    private String uri;
    private Player currentPlayer;
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

    public Player getCurrentPlayer() {
        return currentPlayer;
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

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}