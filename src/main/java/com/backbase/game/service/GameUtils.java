package com.backbase.game.service;

import com.backbase.game.service.bo.BoardConfig;
import com.backbase.game.service.bo.Game;
import com.backbase.game.service.bo.Player;
import com.backbase.game.service.exceptions.MoveNotValidException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
class GameUtils {

    void moveStones(Game game, int pitId) {

        if (!isValidMove(game, pitId)) {
            throw new MoveNotValidException(
                    String.format("The stones on the pit ID %d cannot be moved", pitId));
        }

        Map<Integer, Integer> board = game.getBoard();
        Player player = game.getCurrentPlayer();
        int stones = board.get(pitId);
        int lastFilledPit = 0;
        board.put(pitId, 0);

        for (int i = pitId + 1; i <= pitId + stones; i++) {

            lastFilledPit = i;

            // Stone cannot be put on opponent kalah
            if (player.getOppositePlayer().getKalahId() == lastFilledPit) {
                i++;
                stones++;
            }

            if (i > BoardConfig.SECOND_PLAYER_KALAH) {
                lastFilledPit = i - BoardConfig.SECOND_PLAYER_KALAH;
            }

            board.put(lastFilledPit, board.get(lastFilledPit) + 1 );
        }
        checkLastPit(game, lastFilledPit);
    }

    private void checkLastPit(Game game, int lastPit){
        Player player = game.getCurrentPlayer();
        Map<Integer, Integer> board = game.getBoard();

        if (player.getKalahId() != lastPit) {
            game.setCurrentPlayer(player.getOppositePlayer());
        }

        // If last pit stone is empty takes opponent stones and loose turn
        else if (board.get(lastPit) == 0) {
            int opponentPit = BoardConfig.SECOND_PLAYER_KALAH - lastPit;
            int opponentStones = board.get(opponentPit);
            board.put(opponentPit, 0);
            board.put(player.getKalahId(), opponentStones + 1);
            game.setCurrentPlayer(player.getOppositePlayer());
        }
    }

    private boolean isValidMove(Game game, int starterPit) {
        boolean validMove = false;
        Player currentPlayer = game.getCurrentPlayer();

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
}
