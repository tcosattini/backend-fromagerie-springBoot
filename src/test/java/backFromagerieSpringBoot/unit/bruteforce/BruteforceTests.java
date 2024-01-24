package backFromagerieSpringBoot.unit.bruteforce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import backFromagerieSpringBoot.entity.ActiveUser;
import backFromagerieSpringBoot.repository.ActiveUserRepository;
import backFromagerieSpringBoot.service.authentication.BruteForceProtectionService;
import backFromagerieSpringBoot.service.authentication.DefaultBruteForceProtectionService;

public class BruteforceTests {

  @Test
  void registerLoginFailure() {
    ActiveUserRepository userRepository = mock(ActiveUserRepository.class);
    BruteForceProtectionService bruteForceProtectionService = mock(
        BruteForceProtectionService.class);

    String username = "test";
    ActiveUser activeUser = new ActiveUser();

    userRepository.save(activeUser);

    bruteForceProtectionService.registerLoginFailure(username);

    assertEquals(1, activeUser.getFailedLoginAttempts());
  }

}
