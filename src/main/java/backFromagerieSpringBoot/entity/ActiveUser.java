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

  public ActiveUser(String username, String password,String email, java.util.List<String> roles) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.roles = roles;
  }

}