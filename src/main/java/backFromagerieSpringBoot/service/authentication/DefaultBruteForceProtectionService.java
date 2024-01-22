package backFromagerieSpringBoot.service.authentication;

import backFromagerieSpringBoot.entity.ActiveUser;
import backFromagerieSpringBoot.repository.ActiveUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DefaultBruteForceProtectionService implements BruteForceProtectionService {

  private final int maxFailedLogins = 5;
  private final int maxFailedResetPassword = 5;

  @Autowired
  ActiveUserRepository userRepository;

  private int cacheMaxLimit;

  private final ConcurrentHashMap<String, FailedLogin> cache;

  public DefaultBruteForceProtectionService() {
    this.cache = new ConcurrentHashMap<>(cacheMaxLimit); // setting max limit for cache
  }

  /**
   * Add 1 login failure attemp
   * 
   * @param username
   */
  @Override
  public void registerLoginFailure(String username) {

    Optional<ActiveUser> userOptional = getUser(username);
    System.out.println(userOptional);
    if (userOptional.isPresent()) {
      ActiveUser user = userOptional.get();
      int failedCounter = user.getFailedLoginAttempts();
      if (maxFailedLogins < failedCounter + 1) {
        user.setLoginDisabled(true); // disabling login
      } else {
        // let's update the counter
        user.setFailedLoginAttempts(failedCounter + 1);
      }
      userRepository.save(user);
    }
  }

  /**
   * Add 1 reset-password failure attemp
   * 
   * @param username
   */
  @Override
  public void registerResetPasswordFailure(String username) {

    Optional<ActiveUser> userOptional = getUser(username);
    System.out.println(userOptional);
    if (userOptional.isPresent()) {
      ActiveUser user = userOptional.get();
      int failedCounter = user.getFailedResetPasswordAttempts();
      if (maxFailedResetPassword < failedCounter + 1) {
        user.setResetPasswordDisabled(true); // disabling reset password
      } else {
        // let's update the counter
        user.setFailedResetPasswordAttempts(failedCounter + 1);
      }
      userRepository.save(user);
    }
  }

  /**
   * Reset login counter
   * 
   * @param username
   */
  @Override
  public void resetLoginBruteForceCounter(String username) {
    Optional<ActiveUser> userOptional = getUser(username);
    if (userOptional.isPresent()) {
      ActiveUser user = userOptional.get();
      user.setFailedLoginAttempts(0);
      user.setLoginDisabled(false);
      userRepository.save(user);
    }
  }

  /**
   * Reset password-reset counter
   * 
   * @param username
   */
  @Override
  public void resetResetPasswordBruteForceCounter(String username) {
    Optional<ActiveUser> userOptional = getUser(username);
    if (userOptional.isPresent()) {
      ActiveUser user = userOptional.get();
      user.setFailedResetPasswordAttempts(0);
      user.setResetPasswordDisabled(false);
      userRepository.save(user);
    }
  }

  @Override
  public boolean isLoginBruteForceAttack(String username) {
    Optional<ActiveUser> userOptional = getUser(username);
    if (userOptional.isPresent()) {
      ActiveUser user = userOptional.get();
      return user.getFailedLoginAttempts() >= maxFailedLogins ? true : false;
    }
    return false;
  }

  @Override
  public boolean isResetPasswordBruteForceAttack(String username) {
    Optional<ActiveUser> userOptional = getUser(username);
    if (userOptional.isPresent()) {
      ActiveUser user = userOptional.get();
      return user.getFailedResetPasswordAttempts() >= maxFailedResetPassword ? true : false;
    }
    return false;
  }

  protected FailedLogin getFailedLogin(final String username) {
    FailedLogin failedLogin = cache.get(username.toLowerCase());

    if (failedLogin == null) {
      // setup the initial data
      failedLogin = new FailedLogin(0, LocalDateTime.now());
      cache.put(username.toLowerCase(), failedLogin);
      if (cache.size() > cacheMaxLimit) {

        // add the logic to remve the key based by timestamp
      }
    }
    return failedLogin;
  }

  private Optional<ActiveUser> getUser(final String username) {
    return userRepository.findByUsername(username);
  }

  public int getMaxFailedLogins() {
    return maxFailedLogins;
  }

  public void setMaxFailedLogins(int maxFailedLogins) {
    this.maxFailedLogins = maxFailedLogins;
  }

  public class FailedLogin {

    private int count;
    private LocalDateTime date;

    public FailedLogin() {
      this.count = 0;
      this.date = LocalDateTime.now();
    }

    public FailedLogin(int count, LocalDateTime date) {
      this.count = count;
      this.date = date;
    }

    public int getCount() {
      return count;
    }

    public void setCount(int count) {
      this.count = count;
    }

    public LocalDateTime getDate() {
      return date;
    }

    public void setDate(LocalDateTime date) {
      this.date = date;
    }
  }
}