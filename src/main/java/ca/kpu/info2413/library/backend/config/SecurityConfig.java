package ca.kpu.info2413.library.backend.config;

import ca.kpu.info2413.library.backend.security.AccountUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder; // for plain-text password
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final AccountUserDetailsService userDetailsService;

    public SecurityConfig(AccountUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF for simplicity in development
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/account/api/login", "/AccountLogin.html", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/account/api/login")
                        .usernameParameter("notificationEmail")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/HomePage.html", true)
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .httpBasic(httpBasic -> {}); // enable HTTP Basic if needed

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder()); // for plain-text
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // NoOp for plain-text passwords (only for development!)
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
