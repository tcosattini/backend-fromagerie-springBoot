package backFromagerieSpringBoot.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Table(name = "t_utilisateur")

public class ActiveUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer codeUtilisateur;
  private String username;
  private String password;
  private String email;
  // Login bruteforce fields
  private int failedLoginAttempts;
  private boolean loginDisabled;

  // ResetPassword bruteforce fields
  private int failedResetPasswordAttempts;
  private boolean resetPasswordDisabled;

  public ActiveUser(String username, String password, String email, java.util.List<String> roles) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.roles = roles;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @ElementCollection(fetch = FetchType.EAGER)
  private java.util.List<String> roles;

  public ActiveUser() {
  }
  
  public Integer getCodeUtilisateur() {
    return codeUtilisateur;
  }
  public void setCodeUtilisateur(Integer codeUtilisateur) {
    this.codeUtilisateur = codeUtilisateur;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRoles(java.util.List<String> roles) {
    this.roles = roles;
  }


  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public java.util.List<String> getRoles() {
    return roles;
  }

  public int getFailedLoginAttempts() {
    return failedLoginAttempts;
  }

  public void setFailedLoginAttempts(int failedLoginAttempts) {
    this.failedLoginAttempts = failedLoginAttempts;
  }

  public boolean isLoginDisabled() {
    return loginDisabled;
  }

  public void setLoginDisabled(boolean loginDisabled) {
    this.loginDisabled = loginDisabled;
  }

  public int getFailedResetPasswordAttempts() {
    return failedResetPasswordAttempts;
  }

  public void setFailedResetPasswordAttempts(int failedResetPasswordAttempts) {
    this.failedResetPasswordAttempts = failedResetPasswordAttempts;
  }

  public boolean isResetPasswordDisabled() {
    return resetPasswordDisabled;
  }

  public void setResetPasswordDisabled(boolean resetPasswordDisabled) {
    this.resetPasswordDisabled = resetPasswordDisabled;
  }

}