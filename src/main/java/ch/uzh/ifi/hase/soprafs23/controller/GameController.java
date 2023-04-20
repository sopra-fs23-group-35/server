package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.constant.CityCategory;
import ch.uzh.ifi.hase.soprafs23.entity.*;
import ch.uzh.ifi.hase.soprafs23.rest.dto.*;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.GameService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * User Controller - Responsible for handling all REST request that are related to the user.
 * The controller will receive the request and delegate the execution
 * to the UserService and finally return the result.
 */
@RestController
public class GameController {
    private final UserService userService;
    private final GameService gameService;

    GameController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    /**
     * Start the game
     * @return DTO for game: gameId, current Round, Score board of player
     */
    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    public GameGetDTO createGame(@RequestBody GamePostDTO gamePostDTO) {
        Game gameInput = DTOMapper.INSTANCE.convertGamePostDTOtoEntity(gamePostDTO);
        Game newGame = gameService.createGame(gameInput);
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(newGame);
    }

    /**
     * Go to the next round of the game
     * @param gameId gameId of the game
     * @return QuestionDTO Return a DTO including - 4 options of String, the url of the picture
     */
    @PutMapping("/games/{gameId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public QuestionGetDTO goNextRound(@PathVariable Long gameId) {
        Question question = gameService.goNextRound(gameId);
        return DTOMapper.INSTANCE.convertEntityToQuestionGetDTO(question);
    }

    /**
     * Get game progress and score board
     * @return DTO for game: gameId, current Round, Score board of player
     */
    @GetMapping("/games/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public GameGetDTO getGame(@PathVariable Long gameId) {
        Game game = gameService.searchGameById(gameId);
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);
    }

    /**
     * Add players to the game
     * @param gameId gameId of the game
     * @param playerId userId of the player
     */
    @PostMapping("/games/{gameId}/players/{playerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPlayer(@PathVariable Long gameId, @PathVariable Long playerId) {
        User user = userService.searchUserById(playerId);
        gameService.addPlayer(gameId, user);
    }

    /**
     * Submit the answers of players
     * @param gameId gameId of the game
     * @param playerId userId of the player
     * @param answerPostDTO the answerDTO submitted by the player
     * will return the score, not implement yet
     */
    @PostMapping("/games/{gameId}/players/{playerId}/answers")
    @ResponseStatus(HttpStatus.OK)
    public void submitAnswer(@RequestBody AnswerPostDTO answerPostDTO,
                            @PathVariable Long gameId,
                            @PathVariable Long playerId) {
        Answer newAnswer = DTOMapper.INSTANCE.convertAnswerPostDTOtoEntity(answerPostDTO);
        int addedScore = gameService.submitAnswer(gameId, playerId, newAnswer);
    }
}
