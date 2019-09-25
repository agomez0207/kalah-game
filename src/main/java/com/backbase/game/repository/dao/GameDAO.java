package com.backbase.game.repository.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ElementCollection;
import javax.persistence.MapKeyColumn;
import javax.persistence.Column;
import java.util.Map;

/**
 * Data access object (DAO) representing a game.
 *
 * @author andres.gomez
 */
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

    private String status;

    private String uri;

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

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Map<Integer, Integer> getBoard() {
        return board;
    }

    public void setBoard(Map<Integer, Integer> board) {
        this.board = board;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}