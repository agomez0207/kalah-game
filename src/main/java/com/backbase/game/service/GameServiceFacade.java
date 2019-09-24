package com.backbase.game.service;

import com.backbase.game.service.bo.BoardConfig;
import com.backbase.game.service.bo.Game;
import com.backbase.game.service.bo.Player;
import com.backbase.game.service.exceptions.MoveNotValidException;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Service facade that move the stones and contains some of the rules of the game
 * such as if a player keeps or loose his/her turn.
 *
 * @author andres.gomez
 */
@Component
class GameServiceFacade {

    /**
     * Moves stones starting from the required {@param pitId} only if it's a valid move.
     *
     * @param game Game instance to be used.
     * @param pitId Pit id to move the stones from.
     *
     * @throws MoveNotValidException if a player tries to move the stones from
     * a pit and it's not his/her turn or if tries to move the opponent stones.
     */
    Game moveStones(Game game, final int pitId) {
        Player player = game.getCurrentPlayer();

        if (!isValidMove(player, pitId) || game.getBoard().get(pitId) == 0) {
            throw new MoveNotValidException(
                String.format("The stones on the pit ID %d cannot be moved or the pit is empty", pitId));
        }

        Map<Integer, Integer> board = game.getBoard();
        int secondPlayerKalah = BoardConfig.SECOND_PLAYER_KALAH;

        int stones = board.get(pitId);
        board.put(pitId, 0);
        int lastFilledPit = 0;
        int stonesMoved = 0;
        int pitToFill = pitId + 1;

        while (stonesMoved != stones) {
            lastFilledPit = pitToFill > secondPlayerKalah ? pitToFill - secondPlayerKalah : pitToFill;

            // Stone cannot be put on opponent kalah
            if (player.getOppositePlayer().getKalahId() != lastFilledPit) {
                board.put(lastFilledPit, board.get(lastFilledPit) + 1 );
                stonesMoved++;
            }
            pitToFill++;
        }

        game.setCurrentPlayer(getNextPlayerLastPit(player, board, lastFilledPit));

        return game;
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
