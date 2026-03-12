package ca.kpu.info2413.library.backend.security;

import ca.kpu.info2413.library.backend.model.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Wrapper that exposes our Account inside Spring Security.
 */
public record AccountUserDetails(Account account) implements UserDetails
{

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        String role = account.getAccountType() != null ? account.getAccountType().toUpperCase() : "USER";
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getPassword()
    {
        return account.getPasswordHash();
    }

    @Override
    public String getUsername()
    {
        return account.getNotificationEmail();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}