package fr.diginamic.fromagerie.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import fr.diginamic.fromagerie.event.ResetPasswordBadCredentialsEvent;
import fr.diginamic.fromagerie.service.authentication.BruteForceProtectionService;

@Component
public class ResetPasswordListener implements ApplicationListener<ResetPasswordBadCredentialsEvent> {

  @Autowired
  private BruteForceProtectionService bruteForceProtectionService;

  private static Logger LOG = LoggerFactory.getLogger(AuthenticationListener.class);

  public ResetPasswordListener(BruteForceProtectionService bruteForceProtectionService) {
    this.bruteForceProtectionService = bruteForceProtectionService;
  }

  /**
   * Dispatch an ResetPasswordBadCredentailsEvent
   * during reset-password process
   * 
   * @param AuthenticationFailureBadCredentialsEvent
   * @return void
   */
  @Override
  public void onApplicationEvent(ResetPasswordBadCredentialsEvent event) {
    String username = event.getAuthentication().getName();
    LOG.info("********* reset password failed for user {} ************ ", username);
    bruteForceProtectionService.registerResetPasswordFailure(username);
  }

}
