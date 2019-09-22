package com.backbase.game.service.bo;

import java.util.List;

public enum Player {

    FIRST_PLAYER(BoardConfig.FIRST_PLAYER_KALAH, BoardConfig.FIRST_PLAYER_PITS),
    SECOND_PLAYER(BoardConfig.SECOND_PLAYER_KALAH, BoardConfig.SECOND_PLAYER_PITS);

    private int kalahId;
    private List<Integer> playerPits;

    Player(int kalahId, List<Integer> playerPits) {
        this.kalahId = kalahId;
        this.playerPits = playerPits;
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

    public List<Integer> getPlayerPits() {
        return this.playerPits;
    }

    public void setPlayerPits(List<Integer> playerPits) {
        this.playerPits = playerPits;
    }

}
