package demo.spring.security.backFromagerieSpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.spring.security.backFromagerieSpringBoot.DTO.UserDTO;
import demo.spring.security.backFromagerieSpringBoot.entity.ActiveUser;
import demo.spring.security.backFromagerieSpringBoot.service.AuthenticationService;
import demo.spring.security.backFromagerieSpringBoot.service.UserService;

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
