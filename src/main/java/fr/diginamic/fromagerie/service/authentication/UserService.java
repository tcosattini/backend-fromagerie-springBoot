package fr.diginamic.fromagerie.service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diginamic.fromagerie.authentication.UserDTO;
import fr.diginamic.fromagerie.entity.ActiveUser;
import fr.diginamic.fromagerie.repository.ActiveUserRepository;

@Service
public class UserService {

  @Autowired
  private ActiveUserRepository activeUserRepository;

  public UserDTO findUser(ActiveUser activeUser) {
    ActiveUser foundUser = activeUserRepository.findByUsername(activeUser.getUsername()).orElseGet(null);
    return new UserDTO(foundUser);
  }
}
