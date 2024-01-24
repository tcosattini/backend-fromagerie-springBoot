package fr.diginamic.fromagerie.controller.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.fromagerie.authentication.UserDTO;
import fr.diginamic.fromagerie.entity.ActiveUser;
import fr.diginamic.fromagerie.service.authentication.AuthenticationService;
import fr.diginamic.fromagerie.service.authentication.UserService;

@RestController
@RequestMapping("user")
public class UserCtrl {

  @Autowired
  AuthenticationService authenticationService;

  @Autowired
  UserService userService;

  @PostMapping
  public UserDTO getUserInformations(@RequestBody ActiveUser activeUser) {
    return userService.findUser(activeUser);
  }
}
