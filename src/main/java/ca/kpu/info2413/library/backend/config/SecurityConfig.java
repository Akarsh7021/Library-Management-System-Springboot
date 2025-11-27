package ca.kpu.info2413.library.backend.config;

import ca.kpu.info2413.library.backend.security.AccountUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Security configuration that wires our AccountUserDetailsService into the DaoAuthenticationProvider.
 */
@Configuration
public class SecurityConfig
{

    @Autowired
    private AccountUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, DaoAuthenticationProvider authProvider) throws Exception
    {
        http
                // enable CORS (configured by corsConfigurationSource bean)


                // authorisation rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/AccountLogin.html",
                                "/AccountLogin",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/PasswordRecoveryPage.html",
                                "/RegisterationPage.html",
                                "/register",
                                // Allow registration endpoints (backend mappings vary in your project)
                                "/register",
                                "/account/register",
                                "/api/register"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                // auth provider
                .authenticationProvider(authProvider)
                // form login configuration (keeps your current behaviour)
                .formLogin(form -> form
                        .loginPage("/AccountLogin.html")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/HomePage.html", true)
                        .failureUrl("/AccountLogin.html?error=true")
                        .permitAll()
                )
                // logout configuration
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/AccountLogin.html?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                // DEV only: disable CSRF for now so JS POSTs work without CSRF token.
                // In production, enable CSRF and have your frontend send the token.
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    // DaoAuthenticationProvider using our AccountUserDetailsService and provided PasswordEncoder
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder)
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // use our service
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    // Plain-text encoder for development (replace with BCryptPasswordEncoder in prod)
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new PasswordEncoder()
        {
            @Override
            public String encode(CharSequence rawPassword)
            {
                return rawPassword == null ? null : rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword)
            {
                return rawPassword != null && rawPassword.toString().equals(encodedPassword);
            }
        };
    }

    /**
     * Permissive CORS settings for local development.
     * If your frontend is served from the same origin, this is harmless.
     * If you prefer to lock origins down, replace "*" with explicit origin(s).
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration configuration = new CorsConfiguration();
        // allow any origin (development). Use List.of("http://localhost:5500") to restrict.
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // apply to all endpoints
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}