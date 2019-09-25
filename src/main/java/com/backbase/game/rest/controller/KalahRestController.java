package com.backbase.game.rest.controller;

import com.backbase.game.rest.dtos.GameDTO;
import com.backbase.game.rest.dtos.NewGameDTO;
import com.backbase.game.rest.mapper.ControllerMapper;
import com.backbase.game.service.GameService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class KalahRestController {

    private GameService gameService;
    private ControllerMapper controllerMapper;

    public KalahRestController(GameService gameService, ControllerMapper controllerMapper){
        this.gameService = gameService;
        this.controllerMapper = controllerMapper;
    }

    /**
     * @api {post} /games/ Request to create a new game.
     * @apiVersion 0.1.0
     * @apiName CreateGame
     * @apiGroup Game
     *
     * @apiExample {curl} Example usage:
     *      curl -X POST http://localhost:8080/games -H 'Content-Type: application/json'
     *
     * @apiSuccess {int} id Identifier of the created game.
     * @apiSuccess {String} uri  URI of the created game.
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "id": 1,
     *       "uri": "http://localhost:8080/games/1"
     *     }
     */
    @PostMapping(path = "/games", consumes = "application/json")
    public NewGameDTO createGame(HttpServletRequest httpServletRequest) {
        String uri = httpServletRequest.getRequestURL().toString();
        return controllerMapper.getNewGameDTO(gameService.createGame(uri));
    }

    /**
     * @api {put} /games/:gameId/pits/:pitId Request to move the stones of a game starting from the pitId
     * @apiVersion 0.1.0
     * @apiName MoveStones
     * @apiGroup Game
     *
     * @apiParam {Number} gameId Game identifier.
     * @apiParam {Number} pitId Pit identifier to move the stones from.
     *
     * @apiExample {curl} Example usage:
     *      curl -X PUT http://localhost:8080/games/1/pits/2 -H 'Content-Type: application/json'
     *
     * @apiSuccess {int} id Identifier of the created game.
     * @apiSuccess {String} uri  URI of the created game.
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "id": 1,
     *       "uri": "http://localhost:8080/games/1",
     *       "board" : {
     *                 "1", "6",
     *                 "2", "0",
     *                 "3", "7",
     *                 "4", "7",
     *                 "5", "7",
     *                 "6", "7",
     *                 "7", "1",
     *                 "8", "7",
     *                 "9", "6",
     *                 "10", "6",
     *                 "11", "6",
     *                 "12", "6",
     *                 "13", "6",
     *                 "14", "0"
     *             }
     *     }
     *
     * @apiError GameNotFound The id of the Game was not found.
     *
     * @apiErrorExample GameNotFound-Response:
     *     HTTP/1.1 404 Not Found
     *     {
     *       "message": "Game with id 10 not found"
     *     }
     *
     *  @apiError GameFinishedException The requested has been already finished.
     *
     *  @apiErrorExample GameFinished-Response:
     *      HTTP/1.1 400 Bad Request
     *      {
     *        "message": "The game has been already finished with status of FIRST_PLAYER_WON"
     *      }
     *
     *  @apiError MoveNotValidException The requested move is not valid or is not the player turn.
     *
     *  @apiErrorExample MoveNotValid-Response:
     *      HTTP/1.1 400 Bad Request
     *      {
     *        "message": "The stones on the pit ID 8 cannot be moved or the pit is empty"
     *      }
     */
    @PutMapping(path = "/games/{gameId}/pits/{pitId}", consumes = "application/json")
    public GameDTO makeMove(@PathVariable(name="gameId") int gameId,
                            @PathVariable(value="pitId") int pitId) {
        return controllerMapper.getGameDTO(gameService.makeMove(gameId,pitId));
    }
}