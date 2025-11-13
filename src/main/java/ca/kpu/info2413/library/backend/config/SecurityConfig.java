package ca.kpu.info2413.library.backend.config;

import ca.kpu.info2413.library.backend.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, DaoAuthenticationProvider authProvider) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/AccountLogin.html",
                                "/AccountLogin", // if needed
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/PasswordRecoveryPage.html",
                                "/RegisterationPage.html",
                                "/register"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authProvider) // register the DAO provider
                .formLogin(form -> form
                        .loginPage("/AccountLogin.html")    // your custom page
                        .loginProcessingUrl("/login")      // must match form action
                        .usernameParameter("username")     // form input name
                        .passwordParameter("password")
                        .defaultSuccessUrl("/HomePage.html", true)
                        .failureUrl("/AccountLogin.html?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/AccountLogin.html?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // keep disabled for testing; enable later for production

        return http.build();
    }

    // DaoAuthenticationProvider using our CustomUserDetailsService and provided PasswordEncoder
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    // Plain-text password encoder for now (development only).
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword == null ? false : rawPassword.toString().equals(encodedPassword);
            }
        };
    }
}