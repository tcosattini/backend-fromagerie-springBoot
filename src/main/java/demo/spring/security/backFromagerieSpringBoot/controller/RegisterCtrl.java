package demo.spring.security.backFromagerieSpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.spring.security.backFromagerieSpringBoot.DTO.RegistrationDTO;
import demo.spring.security.backFromagerieSpringBoot.service.AuthenticationService;

@RestController
@RequestMapping("register")
public class RegisterCtrl {

  @Autowired
  AuthenticationService authenticationService;

  public RegisterCtrl(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping
  public ResponseEntity<String> register(@RequestBody RegistrationDTO registrationDTO) {
    return this.authenticationService.registration(registrationDTO);
  }
}
