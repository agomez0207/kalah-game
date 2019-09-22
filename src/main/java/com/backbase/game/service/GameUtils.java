package com.backbase.game.service;

import com.backbase.game.service.bo.BoardConfig;
import com.backbase.game.service.bo.Game;
import com.backbase.game.service.bo.Player;
import com.backbase.game.service.exceptions.MoveNotValidException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Utility service class that move the stones and contains some of the rules of the game
 * such as if a player keeps or loose his/her turn.
 *
 * @author andres.gomez
 */
@Component
class GameUtils {

    /**
     * Moves stones starting from the required {@param pitId} only if it's a valid move.
     *
     * @param game Game instance to be used.
     * @param pitId Pit id to move the stones from.
     *
     * @throws MoveNotValidException if a player tries to move the stones from
     * a pit and it's not his/her turn or if tries to move the opponent stones.
     */
    void moveStones(Game game, final int pitId) {
        Player player = game.getCurrentPlayer();

        if (!isValidMove(player, pitId)) {
            throw new MoveNotValidException(
                String.format("The stones on the pit ID %d cannot be moved", pitId));
        }

        Map<Integer, Integer> board = game.getBoard();
        int stones = board.get(pitId);
        int lastFilledPit = 0;
        board.put(pitId, 0);

        for (int i = pitId + 1; i <= pitId + stones; i++) {
            lastFilledPit = convertOverflowPitId(i);

            // Stone cannot be put on opponent kalah
            if (player.getOppositePlayer().getKalahId() == lastFilledPit) {
                i++;
                stones++;
                lastFilledPit = convertOverflowPitId(i);
            }
            board.put(lastFilledPit, board.get(lastFilledPit) + 1 );
        }
        game.setCurrentPlayer(getNextPlayerLastPit(player, board, lastFilledPit));
    }

    /**
     * Helper method that determines if the player loose his/her turn and/or takes
     * opponent's pit stones.
     *
     * @param player Current player.
     * @param board Board of the game.
     * @param lastPit Pit ID where the last stone landed.
     * @return The next player to play.
     */
    private Player getNextPlayerLastPit(
            final Player player, Map<Integer, Integer> board, final int lastPit) {
        Player currentPlayer = player;

        if (board.get(lastPit) == 1 && isValidMove(player, lastPit)) {
            takesOpponentStones(player, board, lastPit);
            currentPlayer = player.getOppositePlayer();
        } else if (player.getKalahId() != lastPit) {
            currentPlayer = player.getOppositePlayer();
        }

        return currentPlayer;
    }

    /**
     * Checks if the move of stones is valid.
     *
     * @param currentPlayer Player that makes the move.
     * @param starterPit Starter pit ID of the move.
     * @return True if the move is valid otherwise will returns false.
     */
    private boolean isValidMove(Player currentPlayer, int starterPit) {
        boolean validMove = false;

        switch (currentPlayer){
            case FIRST_PLAYER:
                validMove = starterPit < BoardConfig.FIRST_PLAYER_KALAH;
                break;
            case SECOND_PLAYER:
                validMove = starterPit < BoardConfig.SECOND_PLAYER_KALAH && starterPit > BoardConfig.FIRST_PLAYER_KALAH;
                break;
            default:
                break;
        }

        return validMove;
    }

    /**
     * Converts a pit ID to its board equivalent if the pit ID is greater than the maximum of pits.
     *
     * @param pitId Pit ID to be converted.
     * @return The pit ID converted to its board equivalent.
     */
    private int convertOverflowPitId(int pitId) {
        return pitId > BoardConfig.SECOND_PLAYER_KALAH ? pitId - BoardConfig.SECOND_PLAYER_KALAH : pitId;
    }

    /**
     * Takes all stones of the opponent and updates the board.
     *
     * @param player Player that will take the stones.
     * @param board Board of the game.
     * @param pitId Pit ID of the player to take opponent's stones.
     */
    private void takesOpponentStones(Player player, Map<Integer, Integer> board, int pitId) {
        int currentPlayerKalahStones = board.get(player.getKalahId());
        int opponentPit = BoardConfig.SECOND_PLAYER_KALAH - pitId;
        int opponentStones = board.get(opponentPit);
        board.put(opponentPit, 0);
        board.put(pitId, 0);
        board.put(player.getKalahId(), opponentStones + currentPlayerKalahStones + 1);
    }
}
