package demo.spring.security.backFromagerieSpringBoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.spring.security.backFromagerieSpringBoot.DTO.UserDTO;
import demo.spring.security.backFromagerieSpringBoot.entity.ActiveUser;
import demo.spring.security.backFromagerieSpringBoot.repository.ActiveUserRepository;

@Service
public class UserService {

  @Autowired
  private ActiveUserRepository activeUserRepository;

  public UserDTO findUser(ActiveUser activeUser) {
    ActiveUser foundUser = activeUserRepository.findByUsername(activeUser.getUsername()).orElseGet(null);
    return new UserDTO(foundUser);
  }
}
