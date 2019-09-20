package com.backbase.game.service.bo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class BoardConfig {

    private static final int PITS_PER_PLAYER = 6;
    static final int FIRST_PLAYER_KALAH = 7;
    public static final int SECOND_PLAYER_KALAH = 14;
    static final List<Integer> PLAYER_PITS =
            IntStream.rangeClosed(1, PITS_PER_PLAYER).boxed().collect(Collectors.toList());

    public static final Map<Integer, Integer> INITIAL_BOARD = IntStream.rangeClosed(1, SECOND_PLAYER_KALAH)
            .boxed().collect(Collectors.toMap(
                    pit -> pit,
                    pitId -> (pitId != FIRST_PLAYER_KALAH && pitId != SECOND_PLAYER_KALAH) ? PITS_PER_PLAYER : 0));

    // Added private constructor hide the default constructor and avoid instantiation.
    private BoardConfig() {
    }
}

