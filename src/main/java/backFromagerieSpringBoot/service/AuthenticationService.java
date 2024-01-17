package backFromagerieSpringBoot.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import backFromagerieSpringBoot.DTO.LoginDto;
import backFromagerieSpringBoot.DTO.RegistrationDTO;
import backFromagerieSpringBoot.configuration.JWTConfig;
import backFromagerieSpringBoot.entity.ActiveUser;
import backFromagerieSpringBoot.repository.ActiveUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthenticationService {

  @Autowired
  private ActiveUserRepository activeUserRepository;
  private PasswordEncoder passwordEncoder;
  private JWTConfig jwtConfig;

  public AuthenticationService(PasswordEncoder passwordEncoder, ActiveUserRepository activeUserRepository,
      JWTConfig jwtConfig) {
    this.passwordEncoder = passwordEncoder;
    this.activeUserRepository = activeUserRepository;
    this.jwtConfig = jwtConfig;
  }

  public ResponseEntity<String> registration(@RequestBody RegistrationDTO registrationDTO) {

    if (activeUserRepository.findByEmail(registrationDTO.getEmail()).isPresent()) {
      return new ResponseEntity<>("Email already exist", HttpStatus.BAD_REQUEST);
    }
    if (activeUserRepository.findByUsername(registrationDTO.getUsername()).isPresent()) {
      return new ResponseEntity<>("Username already exist", HttpStatus.BAD_REQUEST);
    }

    activeUserRepository.save(new ActiveUser(registrationDTO.getUsername(),
        passwordEncoder.encode(registrationDTO.getPassword()), registrationDTO.getEmail(), List.of("ROLE_USER")));

    return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
  }

  public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) {
    return this.activeUserRepository.findByUsername(loginDto.getUsername())
        .filter(user -> this.passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
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
