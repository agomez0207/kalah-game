package com.backbase.game.service.bo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class that contains all the configuration of the game board.
 *
 * @author andres.gomez
 */
public final class BoardConfig {

    private static final int PITS_PER_PLAYER = 6;
    public static final int FIRST_PLAYER_KALAH = 7;
    public static final int SECOND_PLAYER_KALAH = 14;
    static final List<Integer> FIRST_PLAYER_PITS = Arrays.asList(1,2,3,4,5,6);
    static final List<Integer> SECOND_PLAYER_PITS = Arrays.asList(8,9,10,11,12,13);

    public static final Map<Integer, Integer> INITIAL_BOARD =
            IntStream.rangeClosed(1, SECOND_PLAYER_KALAH).boxed().collect(Collectors.toMap(
                    pit -> pit,
                    pitId -> (pitId != FIRST_PLAYER_KALAH && pitId != SECOND_PLAYER_KALAH) ? PITS_PER_PLAYER : 0));

    /**
     * Private constructor to hide the default constructor and avoid instantiation.
     */
    private BoardConfig() {
    }
}

