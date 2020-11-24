package edu.cnm.deepdive.codebreaker.controller;

import static edu.cnm.deepdive.codebreaker.controller.BaseParameterPatterns.UUID_PATH_PARAMETER_PATTERN;
import static edu.cnm.deepdive.codebreaker.controller.UserController.RELATIVE_PATH;

import edu.cnm.deepdive.codebreaker.model.entity.User;
import edu.cnm.deepdive.codebreaker.service.UserService;
import edu.cnm.deepdive.codebreaker.service.UserService.UserNotFoundException;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RELATIVE_PATH)
@ExposesResourceFor(User.class)
public class UserController {

  static final String RELATIVE_PATH = "/users";

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  public User me(Authentication auth) {
    return (User) auth.getPrincipal();
  }

  @GetMapping(value = UUID_PATH_PARAMETER_PATTERN, produces = MediaType.APPLICATION_JSON_VALUE)
  public User get(@PathVariable UUID id, Authentication auth) {
    return userService.getUser(id)
        .orElseThrow(UserNotFoundException::new);
  }

}
