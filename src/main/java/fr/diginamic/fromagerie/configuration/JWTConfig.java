package fr.diginamic.fromagerie.configuration;

import java.security.Key;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;

@Configuration
public class JWTConfig {

  @Value("${jwt.expires_in}")
  private long expireIn;

  @Value("${jwt.cookie}")
  private String cookie;

  public long getExpireIn() {
    return expireIn;
  }

  public void setExpireIn(long expireIn) {
    this.expireIn = expireIn;
  }

  public String getCookie() {
    return cookie;
  }

  public void setCookie(String cookie) {
    this.cookie = cookie;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public Key getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(Key secretKey) {
    this.secretKey = secretKey;
  }

  @Value("${jwt.secret}")
  private String secret;

  private Key secretKey;

  @PostConstruct
  public void buildKey() {
    secretKey = new SecretKeySpec(Base64.getDecoder().decode(getSecret()),
        SignatureAlgorithm.HS256.getJcaName());
  }

}
