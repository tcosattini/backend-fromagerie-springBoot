package backFromagerieSpringBoot.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.Authentication;
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
  private BruteForceProtectionService bruteForceProtectionService;
  private ApplicationEventPublisher applicationEventPublisher;

  public AuthenticationService(
      PasswordEncoder passwordEncoder,
      ActiveUserRepository activeUserRepository,
      JWTConfig jwtConfig,
      ApplicationEventPublisher applicationEventPublisher,
      BruteForceProtectionService bruteForceProtectionService) {

    this.passwordEncoder = passwordEncoder;
    this.activeUserRepository = activeUserRepository;
    this.jwtConfig = jwtConfig;
    this.applicationEventPublisher = applicationEventPublisher;
    this.bruteForceProtectionService = bruteForceProtectionService;
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
        .filter(user -> this.passwordEncoder.matches(loginDto.getPassword(), user.getPassword())
            && !this.bruteForceProtectionService.isBruteForceAttack(loginDto.getUsername()))
        .map(user -> ResponseEntity.ok().header(org.springframework.http.HttpHeaders.SET_COOKIE, buildJWTCookie(user))
            .build())
        .orElseGet(() -> {
          BadCredentialsException e = new BadCredentialsException("Bad credentials");
          AuthenticationFailureBadCredentialsEvent event = new AuthenticationFailureBadCredentialsEvent(
              createAuthentication(loginDto.getUsername(), null), e);
          applicationEventPublisher.publishEvent(event);
          if (this.bruteForceProtectionService.isBruteForceAttack(loginDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Your account is locked, please update your passeword");
          }
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        });
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

  private Authentication createAuthentication(String username, Object credentials) {
    // Logique pour créer l'objet Authentication (par exemple, utiliser
    // UsernamePasswordAuthenticationToken)
    // Retournez l'objet Authentication créé
    return new UsernamePasswordAuthenticationToken(username, credentials);
  }
}
