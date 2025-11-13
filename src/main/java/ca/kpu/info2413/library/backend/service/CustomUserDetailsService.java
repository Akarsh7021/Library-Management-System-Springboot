package ca.kpu.info2413.library.backend.service;

import ca.kpu.info2413.library.backend.model.Account;
import ca.kpu.info2413.library.backend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username is the value sent from the login form (your <input name="username">)
        var accounts = accountRepository.findByNotificationEmailIgnoreCase(username.trim());
        if (accounts.isEmpty()) {
            // helpful debug in the logs
            System.out.println("[Auth] loadUserByUsername: no account found for '" + username + "'");
            throw new UsernameNotFoundException("User not found: " + username);
        }

        Account account = accounts.get(0);
        String email = account.getNotificationEmail();
        String dbPass = account.getPasswordHash() != null ? account.getPasswordHash().trim() : "";

        System.out.println("[Auth] loadUserByUsername: found account: " + email + " | dbPass='" + dbPass + "'");

        // Build a simple UserDetails. No roles/authorities for now.
        return User.withUsername(email)
                .password(dbPass)
                .authorities(Collections.emptyList())
                .build();
    }
}
