package demo.spring.security.backFromagerieSpringBoot.onStartUp;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import demo.spring.security.backFromagerieSpringBoot.repository.ActiveUserRepository;

@Component
public class OnStartUp {

  private ActiveUserRepository activeUserRepository;
  private PasswordEncoder passwordEncoder;

  // public OnStartUp(ActiveUserRepository activeUserRepository, PasswordEncoder passwordEncoder) {
  //   this.activeUserRepository = activeUserRepository;
  //   this.passwordEncoder = passwordEncoder;
  // }

  // @EventListener(ContextRefreshedEvent.class)
  // public void init() {

  //   activeUserRepository
  //       .save(new ActiveUser("u1", passwordEncoder.encode("pass1"), "test1@mail.com", List.of("ROLE_ADMIN",
  //           "ROLE_USER")));
  //   activeUserRepository.save(new ActiveUser("u2", passwordEncoder.encode("pass2"), "test1@mail.com",
  //       List.of("ROLE_USER")));

  // }

}