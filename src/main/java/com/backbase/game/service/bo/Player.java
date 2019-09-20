package com.backbase.game.service.bo;


import java.util.List;

public enum Player {

    FIRST_PLAYER(BoardConfig.PLAYER_PITS, BoardConfig.FIRST_PLAYER_KALAH ),
    SECOND_PLAYER(BoardConfig.PLAYER_PITS, BoardConfig.SECOND_PLAYER_KALAH);

    private List<Integer> pits;
    private int kalahId;

    Player(List<Integer> pits, int kalahId) {
        this.pits = pits;
        this.kalahId = kalahId;
    }

    public List<Integer> getPits() {
        return pits;
    }

    public void setPits(List<Integer> pits) {
        this.pits = pits;
    }

    public int getKalahId() {
        return kalahId;
    }

    public void setKalahId(int kalahId) {
        this.kalahId = kalahId;
    }

    public String getPlayer(){
        return this.toString();
    }

    public Player getOppositePlayer() {
        return this == FIRST_PLAYER ? SECOND_PLAYER : FIRST_PLAYER;
    }
}
