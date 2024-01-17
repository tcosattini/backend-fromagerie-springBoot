package backFromagerieSpringBoot.configuration;

import java.util.Arrays;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import backFromagerieSpringBoot.controller.JWTAuthorizationFilter;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    String encodingId = "bcrypt";
    return new DelegatingPasswordEncoder(encodingId, Map.of(encodingId, new BCryptPasswordEncoder()));
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOrigin("http://localhost:5173"); // add front domain to allow CORS origin policy
    configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
    configuration.setAllowedHeaders(Arrays.asList("Content-Type"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, JWTAuthorizationFilter jwtFilter, JWTConfig jwtConfig)
      throws Exception {

    http.authorizeHttpRequests(
        auth -> auth
            .requestMatchers(new AntPathRequestMatcher("/login", "POST")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/register", "POST")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/user", "POST")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/exemples", "GET")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
            .anyRequest().authenticated())
        .cors(Customizer.withDefaults())

        // Spring Security va valoriser un jeton stocké dans un cookie XSRF-TOKEN
        // Le client souhaitant effectuer une requête de modification (POST par exemple)
        // devra valoriser une entête HTTP "X-XSRF-TOKEN" avec le jeton.
        .csrf(csrf -> csrf
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .ignoringRequestMatchers(new AntPathRequestMatcher("/login"))
            .ignoringRequestMatchers(new AntPathRequestMatcher("/register"))
            .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
            .csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler()::handle))

        .headers(
            headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))

        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .logout(logout -> logout
            // en cas de succès un OK est envoyé
            .logoutUrl("/logout")
            .logoutSuccessHandler((req, resp, auth) -> resp.setStatus(HttpStatus.OK.value()))
            // suppression du cookie d'authentification
            .deleteCookies(jwtConfig.getCookie()));

    return http.build();
  }

}
