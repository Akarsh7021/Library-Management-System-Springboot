package ca.kpu.info2413.library.backend.security;

import ca.kpu.info2413.library.backend.model.Account;
import ca.kpu.info2413.library.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // make sure this is the bean Spring wires into DaoAuthenticationProvider
public class AccountUserDetailsService implements UserDetailsService
{

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        var matches = accountService.findByNotificationEmailIgnoreCase(username.trim());
        if (matches == null || matches.isEmpty())
        {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        Account account = matches.getFirst();

        // build list of authorities from accountType (case-insensitive)
        List<GrantedAuthority> authorities = new ArrayList<>();
        String type = account.getAccountType();
        if (type != null && type.equalsIgnoreCase("admin"))
        {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        else
        {
            // default to member role
            authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
        }

        String email = account.getNotificationEmail();
        String password = account.getPasswordHash() == null ? "" : account.getPasswordHash().trim();

        return User.withUsername(email)
                .password(password)
                .authorities(authorities)
                .build();
    }
}