package demo.spring.security.backFromagerieSpringBoot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.spring.security.backFromagerieSpringBoot.entity.ActiveUser;

public interface ActiveUserRepository extends JpaRepository<ActiveUser, Integer> {
  Optional<ActiveUser> findByUsername(String username);

  Optional<ActiveUser> findByEmail(String email);
}