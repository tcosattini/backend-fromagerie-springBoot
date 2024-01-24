package backFromagerieSpringBoot.unit.bruteforce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.jupiter.api.TestInstance;
import backFromagerieSpringBoot.entity.ActiveUser;
import backFromagerieSpringBoot.repository.ActiveUserRepository;
import backFromagerieSpringBoot.service.authentication.BruteForceProtectionService;
import backFromagerieSpringBoot.service.authentication.DefaultBruteForceProtectionService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class BruteforceTests {

  @Autowired
  BruteForceProtectionService bruteForceProtectionService;

  @MockBean
  private ActiveUser activeUser;

  @Autowired
  ActiveUserRepository userRepository;

  private String rawPassword;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @BeforeAll
  public void init() {
    setupUser();
  }

  private void setupUser() {

    this.rawPassword = "credentials";
    ActiveUser user = new ActiveUser();
    user.setPassword(passwordEncoder.encode(this.rawPassword));
    user.setUsername("test");
    userRepository.save(user);

    this.activeUser = user;
  }

  @BeforeEach
  void clearDatabase(@Autowired ActiveUserRepository activeUserRepository) {
    activeUserRepository.deleteAll();
  }

  @Test
  void registerLoginFailureGetFailedLoginAttemps() {
    userRepository.save(this.activeUser);
    bruteForceProtectionService.registerLoginFailure(this.activeUser.getUsername());
    Optional<ActiveUser> createdUser = userRepository.findByUsername("test");
    assertEquals(1, createdUser.get().getFailedLoginAttempts());
  }

  @Test
  void registerLoginFailureLoginDisabled() {

    this.activeUser.setFailedLoginAttempts(5);
    userRepository.save(this.activeUser);
    bruteForceProtectionService.registerLoginFailure(this.activeUser.getUsername());
    Optional<ActiveUser> createdUser = userRepository.findByUsername("test");

    assertEquals(5, createdUser.get().getFailedLoginAttempts());
    assertTrue(createdUser.get().isLoginDisabled());
  }

}
