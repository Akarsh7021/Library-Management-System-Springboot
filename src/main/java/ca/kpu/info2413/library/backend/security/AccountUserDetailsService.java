package ca.kpu.info2413.library.backend.security;

import ca.kpu.info2413.library.backend.model.Account;
import ca.kpu.info2413.library.backend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username is notificationEmail
        List<Account> accounts = accountRepository.findByNotificationEmailIgnoreCase(username);
        if (accounts.isEmpty()) {
            throw new UsernameNotFoundException("No account with email: " + username);
        }
        // pick first account (or decide how to handle duplicates)
        Account account = accounts.get(0);
        return new AccountUserDetails(account);
    }
}
