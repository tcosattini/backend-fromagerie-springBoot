package backFromagerieSpringBoot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import backFromagerieSpringBoot.DTO.RegistrationDTO;
import backFromagerieSpringBoot.entity.ActiveUser;
import backFromagerieSpringBoot.repository.ActiveUserRepository;

@Service
public class AuthenticationService {

  @Autowired
  private ActiveUserRepository activeUserRepository;

  private PasswordEncoder passwordEncoder;

  public AuthenticationService(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
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

}
