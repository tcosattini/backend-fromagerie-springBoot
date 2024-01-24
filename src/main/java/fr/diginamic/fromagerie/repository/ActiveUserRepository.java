package fr.diginamic.fromagerie.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.fromagerie.entity.ActiveUser;

public interface ActiveUserRepository extends JpaRepository<ActiveUser, Integer> {
  Optional<ActiveUser> findByUsername(String username);

  Optional<ActiveUser> findByEmail(String email);
}