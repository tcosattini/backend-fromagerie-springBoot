package backFromagerieSpringBoot.controller.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backFromagerieSpringBoot.DTO.authentication.RegistrationDTO;
import backFromagerieSpringBoot.service.authentication.AuthenticationService;

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
