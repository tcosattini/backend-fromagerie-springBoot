package backFromagerieSpringBoot.service;

import backFromagerieSpringBoot.entity.ActiveUser;
import backFromagerieSpringBoot.repository.ActiveUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DefaultBruteForceProtectionService implements BruteForceProtectionService {

  private int maxFailedLogins = 5;

  @Autowired
  ActiveUserRepository userRepository;

  private int cacheMaxLimit;

  private final ConcurrentHashMap<String, FailedLogin> cache;

  public DefaultBruteForceProtectionService() {
    this.cache = new ConcurrentHashMap<>(cacheMaxLimit); // setting max limit for cache
  }

  @Override
  public void registerLoginFailure(String username) {

    Optional<ActiveUser> userOptional = getUser(username);
    System.out.println(userOptional);
    if (userOptional.isPresent()) {
      ActiveUser user = userOptional.get();
      int failedCounter = user.getFailedLoginAttempts();
      if (maxFailedLogins < failedCounter + 1) {
        user.setLoginDisabled(true); // disabling the account
      } else {
        // let's update the counter
        user.setFailedLoginAttempts(failedCounter + 1);
      }
      userRepository.save(user);
    }
  }

  @Override
  public void resetBruteForceCounter(String username) {
    Optional<ActiveUser> userOptional = getUser(username);
    if (userOptional.isPresent()) {
      ActiveUser user = userOptional.get();
      user.setFailedLoginAttempts(0);
      user.setLoginDisabled(false);
      userRepository.save(user);
    }
  }

  @Override
  public boolean isBruteForceAttack(String username) {
    Optional<ActiveUser> userOptional = getUser(username);
    if (userOptional.isPresent()) {
      ActiveUser user = userOptional.get();
      return user.getFailedLoginAttempts() >= maxFailedLogins ? true : false;
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