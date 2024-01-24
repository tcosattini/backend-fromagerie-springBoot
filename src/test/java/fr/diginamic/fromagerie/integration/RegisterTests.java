package fr.diginamic.fromagerie.integration;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import fr.diginamic.fromagerie.entity.ActiveUser;
import fr.diginamic.fromagerie.repository.ActiveUserRepository;
import net.minidev.json.JSONObject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class RegisterTests {

  @MockBean
  private ActiveUser activeUser;

  private String rawPassword;

  private MockMvc mockMvc;
  @Autowired
  private WebApplicationContext applicationContext;

  @Autowired
  private ActiveUserRepository activeUserRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private void setupUser() {

    this.rawPassword = "credentials";
    ActiveUser user = new ActiveUser();
    user.setPassword(passwordEncoder.encode(this.rawPassword));
    user.setUsername("test_register");
    user.setEmail("test@test.com");

    this.activeUser = user;
  }

  @BeforeAll
  public void init() {
    this.mockMvc = MockMvcBuilders
        .webAppContextSetup(applicationContext)
        .build();
    setupUser();
  }

  @BeforeEach
  void clearDatabase(@Autowired ActiveUserRepository activeUserRepository) {
    activeUserRepository.deleteAll();
  }

  @Test
  public void testSuccessfullRegisterShouldReturn200() throws Exception {

    JSONObject registerData = new JSONObject();
    registerData.put("username", this.activeUser.getUsername());
    registerData.put("password", this.rawPassword);
    registerData.put("email", this.activeUser.getEmail());

    System.out.println(registerData);

    mockMvc.perform(post("/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(registerData.toString()))
        .andExpect(status().isCreated());

  }

  @Test
  public void testAlreadyUsedUsernameShouldReturn400() throws Exception {

    this.activeUserRepository.save(this.activeUser);

    JSONObject registerData = new JSONObject();
    registerData.put("username", this.activeUser.getUsername());
    registerData.put("password", this.rawPassword);
    registerData.put("email", this.activeUser.getEmail());

    System.out.println(registerData);

    mockMvc.perform(post("/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(registerData.toString()))
        .andExpect(status().isBadRequest());

  }

  @Test
  public void testMissingRegisterFieldShouldReturn400() throws Exception {

    this.activeUserRepository.save(this.activeUser);

    JSONObject registerData = new JSONObject();
    registerData.put("username", this.activeUser.getUsername());
    registerData.put("password", this.rawPassword);

    System.out.println(registerData);

    mockMvc.perform(post("/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(registerData.toString()))
        .andExpect(status().isBadRequest());

    this.activeUserRepository.delete(activeUser);
  }
}
