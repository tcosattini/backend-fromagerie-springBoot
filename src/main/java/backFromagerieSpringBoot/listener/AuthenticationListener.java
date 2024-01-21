package backFromagerieSpringBoot.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import backFromagerieSpringBoot.service.authentication.BruteForceProtectionService;
import backFromagerieSpringBoot.service.authentication.DefaultBruteForceProtectionService;



@Component
public class AuthenticationListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

  @Autowired
  private BruteForceProtectionService bruteForceProtectionService;

  private static Logger LOG = LoggerFactory.getLogger(AuthenticationListener.class);

  public AuthenticationListener(BruteForceProtectionService bruteForceProtectionService) {
    this.bruteForceProtectionService = bruteForceProtectionService;
  }

  /**
   * Dispatch an AuthenticationFailureBadCredentialsEvent
   * during login process
   * 
   * @param AuthenticationFailureBadCredentialsEvent
   * @return void
   */
  @Override
  public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
    String username = event.getAuthentication().getName();
    LOG.info("********* login failed for user {} ************ ", username);
    bruteForceProtectionService.registerLoginFailure(username);
  }

}