package edu.cnm.deepdive.codebreaker.controller;

import static edu.cnm.deepdive.codebreaker.controller.BaseParameterPatterns.UUID_PATH_PARAMETER_PATTERN;
import static edu.cnm.deepdive.codebreaker.controller.GameController.RELATIVE_PATH;

import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.service.GameService;
import edu.cnm.deepdive.codebreaker.service.GameService.GameNotFoundException;
import edu.cnm.deepdive.codebreaker.service.MatchService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ExposesResourceFor(Game.class)
@RequestMapping(RELATIVE_PATH)
public class GameController {

  static final String RELATIVE_PATH = "/games";

  private final MatchService matchService;
  private final GameService gameService;

  @Autowired
  public GameController(MatchService matchService, GameService gameService) {
    this.matchService = matchService;
    this.gameService = gameService;
  }

  @GetMapping(value = UUID_PATH_PARAMETER_PATTERN, produces = MediaType.APPLICATION_JSON_VALUE)
  public Game get(@PathVariable UUID id) {
    return gameService.get(id)
        .orElseThrow(GameNotFoundException::new);
  }

}
