package com.backbase.game.rest.dtos;

/**
 * New game data transfer object (DTO)
 *
 * @author andres.gomez
 */
public class NewGameDTO {

    private int id;
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
}
