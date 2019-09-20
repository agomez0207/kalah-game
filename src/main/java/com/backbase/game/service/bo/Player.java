package com.backbase.game.service.bo;

public enum Player {

    FIRST_PLAYER(BoardConfig.FIRST_PLAYER_KALAH ),
    SECOND_PLAYER(BoardConfig.SECOND_PLAYER_KALAH);

    private int kalahId;

    Player(int kalahId) {
        this.kalahId = kalahId;
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
