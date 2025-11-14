package ca.kpu.info2413.library.backend.security;

import ca.kpu.info2413.library.backend.model.Account;
import ca.kpu.info2413.library.backend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Loads account by email and returns AccountUserDetails.
 */
@Service
public class AccountUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Account> accounts = accountRepository.findByNotificationEmailIgnoreCase(username);
        if (accounts.isEmpty()) {
            throw new UsernameNotFoundException("No account with email: " + username);
        }
        Account account = accounts.get(0);
        return new AccountUserDetails(account);
    }
}