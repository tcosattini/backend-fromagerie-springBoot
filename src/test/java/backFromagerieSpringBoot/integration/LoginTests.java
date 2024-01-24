package backFromagerieSpringBoot.integration;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import backFromagerieSpringBoot.entity.ActiveUser;
import backFromagerieSpringBoot.repository.ActiveUserRepository;
import net.minidev.json.JSONObject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
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
public class LoginTests {

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
    user.setUsername("test");
    activeUserRepository.save(user);

    this.activeUser = user;
  }

  @BeforeAll
  public void init() {

    activeUserRepository.deleteAll();

    this.mockMvc = MockMvcBuilders
        .webAppContextSetup(applicationContext)
        .build();
    setupUser();
  }

  @Test
  public void testSuccessfullLoginShouldReturn200() throws Exception {

    JSONObject loginData = new JSONObject();
    loginData.put("username", this.activeUser.getUsername());
    loginData.put("password", this.rawPassword);

    mockMvc.perform(post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(loginData.toString()))
        .andExpect(status().isOk());
  }

  @Test
  public void testUnSuccessfullLoginShouldReturn401() throws Exception {

    JSONObject loginData = new JSONObject();
    loginData.put("username", this.activeUser.getUsername());
    loginData.put("password", "bad_password");

    mockMvc.perform(post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(loginData.toString()))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void testNotFoundLoginShouldReturn404() throws Exception {

    JSONObject loginData = new JSONObject();
    loginData.put("username", this.activeUser.getUsername());
    loginData.put("password", "bad_password");

    mockMvc.perform(post("/bad_endpoint")
        .contentType(MediaType.APPLICATION_JSON)
        .content(loginData.toString()))
        .andExpect(status().isNotFound());
  }

}
