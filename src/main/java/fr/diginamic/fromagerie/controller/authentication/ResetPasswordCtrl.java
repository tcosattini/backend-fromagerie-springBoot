package fr.diginamic.fromagerie.controller.authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.fromagerie.authentication.ResetPasswordDTO;
import fr.diginamic.fromagerie.service.authentication.AuthenticationService;

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
