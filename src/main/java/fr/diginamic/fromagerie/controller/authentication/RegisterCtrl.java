package fr.diginamic.fromagerie.controller.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.fromagerie.authentication.RegistrationDTO;
import fr.diginamic.fromagerie.service.authentication.AuthenticationService;

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
