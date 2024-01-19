package backFromagerieSpringBoot.event;

import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class ResetPasswordBadCredentialsEvent extends AbstractAuthenticationFailureEvent {

  public ResetPasswordBadCredentialsEvent(Authentication authentication, AuthenticationException exception) {
    super(authentication, exception);
  }

}
