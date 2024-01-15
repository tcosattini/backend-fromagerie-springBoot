package demo.spring.security.backFromagerieSpringBoot.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.spring.security.backFromagerieSpringBoot.DTO.LoginDto;
import demo.spring.security.backFromagerieSpringBoot.configuration.JWTConfig;
import demo.spring.security.backFromagerieSpringBoot.entity.ActiveUser;
import demo.spring.security.backFromagerieSpringBoot.repository.ActiveUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@RestController
@RequestMapping("login")

public class SessionCtrl {

  private ActiveUserRepository activeUserRepository;
  private PasswordEncoder passwordEncoder;
  private JWTConfig jwtConfig;

  public SessionCtrl(ActiveUserRepository activeUserRepository, PasswordEncoder passwordEncoder, JWTConfig jwtConfig) {
    this.activeUserRepository = activeUserRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtConfig = jwtConfig;
  }

  @PostMapping

  public ResponseEntity<?> create(@RequestBody LoginDto loginDto) {

    return this.activeUserRepository.findByUsername(loginDto.getUsername())
        .filter(user -> passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
        .map(user -> ResponseEntity.ok().header(org.springframework.http.HttpHeaders.SET_COOKIE, buildJWTCookie(user))
            .build())
        .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());

  }

  private String buildJWTCookie(ActiveUser user) {

    Keys.secretKeyFor(SignatureAlgorithm.HS512);

    String jetonJWT = Jwts.builder()
        .setSubject(user.getUsername())
        .addClaims(Map.of("roles", user.getRoles()))
        .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpireIn() * 1000))
        .signWith(
            jwtConfig.getSecretKey())
        .compact();

    ResponseCookie tokenCookie = ResponseCookie.from(jwtConfig.getCookie(), jetonJWT)
        .httpOnly(true)
        .maxAge(jwtConfig.getExpireIn() * 1000)
        .path("/")
        .build();

    return tokenCookie.toString();
  }
}