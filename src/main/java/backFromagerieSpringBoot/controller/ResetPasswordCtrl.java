package backFromagerieSpringBoot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backFromagerieSpringBoot.DTO.ResetPasswordDTO;
import backFromagerieSpringBoot.service.AuthenticationService;

@RestController
@RequestMapping("reset-password")
public class ResetPasswordCtrl {

  private AuthenticationService authenticationService;

  public ResetPasswordCtrl(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping
  public ResponseEntity<Object> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
    return this.authenticationService.resetPassword(resetPasswordDTO);
  }

}
