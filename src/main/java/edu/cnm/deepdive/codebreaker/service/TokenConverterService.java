package edu.cnm.deepdive.codebreaker.service;

import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class TokenConverterService implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

  private final UserService userService;

  @Autowired
  public TokenConverterService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
    return new UsernamePasswordAuthenticationToken(
        userService.getOrCreate(jwt.getSubject(), jwt.getClaim("name")),
        jwt.getTokenValue(),
        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
    );
  }

}
