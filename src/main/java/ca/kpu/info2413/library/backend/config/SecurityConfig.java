package ca.kpu.info2413.library.backend.config;

import ca.kpu.info2413.library.backend.security.AccountUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Security configuration that wires our AccountUserDetailsService into the DaoAuthenticationProvider.
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private AccountUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, DaoAuthenticationProvider authProvider) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/AccountLogin.html",
                                "/AccountLogin",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/PasswordRecoveryPage.html",
                                "/RegisterationPage.html",
                                "/register"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authProvider)
                .formLogin(form -> form
                        .loginPage("/AccountLogin.html")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
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
                .csrf(csrf -> csrf.disable()); // disable for development

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // use our service
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    // Plain-text encoder for development (replace with BCryptPasswordEncoder in prod)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword == null ? null : rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword != null && rawPassword.toString().equals(encodedPassword);
            }
        };
    }
}