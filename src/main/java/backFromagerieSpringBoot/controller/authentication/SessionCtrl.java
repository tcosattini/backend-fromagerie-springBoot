package backFromagerieSpringBoot.controller.authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backFromagerieSpringBoot.DTO.authentication.LoginDto;
import backFromagerieSpringBoot.service.authentication.AuthenticationService;

@RestController
@RequestMapping("login")
public class SessionCtrl {

  private AuthenticationService authenticationService;

  public SessionCtrl(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping
  public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) {
    return this.authenticationService.login(loginDto);
  }

}