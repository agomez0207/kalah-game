package com.backbase.game.repository.dao;


import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity(name = "game")
public class GameDAO {

    @Id
    @GeneratedValue
    private int id;

    @ElementCollection
    @MapKeyColumn(name="pitId")
    @Column(name="pitValue")
    private Map<Integer, Integer> board;

    private String currentPlayer;

    private String gameStatus;

    private String uri;

    public GameDAO() {
    }

    public GameDAO(Map<Integer, Integer> initialBoard) {
        this.board = initialBoard;
    }

    public int getId() {
        return id;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Integer, Integer> getBoard() {
        return board;
    }

    public void setBoard(Map<Integer, Integer> board) {
        this.board = board;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}