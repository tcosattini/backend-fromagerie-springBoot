package backFromagerieSpringBoot.DTO;

import backFromagerieSpringBoot.entity.ActiveUser;

public class UserDTO {
  private String username;

  private String email;

  private java.util.List<String> roles;

  public UserDTO(ActiveUser activeUser) {

    this.username = activeUser.getUsername();
    this.email = activeUser.getEmail();
    this.roles = activeUser.getRoles();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public java.util.List<String> getRoles() {
    return roles;
  }

  public void setRoles(java.util.List<String> roles) {
    this.roles = roles;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
