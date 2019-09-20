package com.backbase.game.service;

import com.backbase.game.service.bo.Game;

public interface GameService {

    Game createGame(String uri);

    Game makeMove(int gameId, int pitId);
}
