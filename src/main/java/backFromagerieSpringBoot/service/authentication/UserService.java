package backFromagerieSpringBoot.service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backFromagerieSpringBoot.DTO.authentication.UserDTO;
import backFromagerieSpringBoot.entity.ActiveUser;
import backFromagerieSpringBoot.repository.ActiveUserRepository;

@Service
public class UserService {

  @Autowired
  private ActiveUserRepository activeUserRepository;

  public UserDTO findUser(ActiveUser activeUser) {
    ActiveUser foundUser = activeUserRepository.findByUsername(activeUser.getUsername()).orElseGet(null);
    return new UserDTO(foundUser);
  }
}
