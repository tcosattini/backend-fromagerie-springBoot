package fr.diginamic.fromagerie.authentication;

public class ResetPasswordDTO {

  private String username;
  private String lastPassword;
  private String newPassword;
  private String newPasswordRepeat;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getLastPassword() {
    return lastPassword;
  }

  public void setLastPassword(String lastPassword) {
    this.lastPassword = lastPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getNewPasswordRepeat() {
    return newPasswordRepeat;
  }

  public void setNewPasswordRepeat(String newPasswordRepeat) {
    this.newPasswordRepeat = newPasswordRepeat;
  }

}
