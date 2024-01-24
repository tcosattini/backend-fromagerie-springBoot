package fr.diginamic.fromagerie;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

public class DelegatingPasswordEncoderTest {

  PasswordEncoder passwordEncoder;

  @BeforeEach
  public void setUp() {
    // identifiant de l'encodage par défaut
    String encodingId = "bcrypt";

    // dictionnaire identifiant - encodeur
    java.util.Map<String, PasswordEncoder> encoders = new HashMap<>();
    encoders.put(encodingId, new BCryptPasswordEncoder());
    encoders.put("pbkdf2", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
    encoders.put("scrypt", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());

    // création de l'encodeur de mot de passe
    passwordEncoder = new DelegatingPasswordEncoder(encodingId, encoders);
  }

  @Test
  public void encode() {
    String password = "azerty";
    String encodedPassword = passwordEncoder.encode(password);

    assertTrue(encodedPassword.startsWith("{bcrypt}"));
  }

  @Test
  public void matches() {

    // "super pass" encodé avec Pbkdf2
    String encodedWithPbkdf2 = "{pbkdf2}6ba5c7e80e120daec6fee930e2ca06f415658af51094c83271e33bb93b6e4715b37def4d64b07b3d344bb9634516fabf";
    // "super pass" encodé avec Bcrypt
    String encodedWithBcrypt = "{bcrypt}$2a$10$8204hla/maNHppYaVAcviukYShIILTb7XojQwwfpVMVIPHhPqeZqq";

    String password = "super pass";
    assertTrue(passwordEncoder.matches(password, encodedWithPbkdf2));
    assertTrue(passwordEncoder.matches(password, encodedWithBcrypt));

  }

}
