package fr.diginamic.fromagerie;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = BCryptPasswordEncoder.class)
public class BCryptPasswordEncoderTest {

  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Test
  public void bcrypt_encode() {
    String rawPassword = "motdepasse123";
    String encodedPassword = passwordEncoder.encode(rawPassword);
    assertNotNull(encodedPassword);
  }

  @Test
  public void bcrypt_decode_admin_1() {
    String rawPassword = "admin";
    String encodedPassword = passwordEncoder.encode(rawPassword);
    assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
  }

  @Test
  public void bcrypt_decode_admin_2() {
    String rawPassword = "admin";
    String encodedPassword = passwordEncoder.encode(rawPassword);

    assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
  }

  @Test
  public void bcrypt_decode_azerty_1() {
    String rawPassword = "azerty";
    String encodedPassword = passwordEncoder.encode(rawPassword);

    assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
  }

  @Test
  public void bcrypt_decode_azerty_2() {
    String rawPassword = "azerty";
    String encodedPassword = passwordEncoder.encode(rawPassword);
    assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
  }

}