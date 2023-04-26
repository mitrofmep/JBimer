package ru.jbimer.core.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.jbimer.core.models.Engineer;

import java.util.Collection;

public class EngineerDetails implements UserDetails {

    private final Engineer engineer;

    public EngineerDetails(Engineer engineer) {
        this.engineer = engineer;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.engineer.getPassword();
    }

    @Override
    public String getUsername() {
        return this.engineer.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true ;
    }

    // to get data of authenticated user
    public Engineer getEngineer() {
        return this.engineer;
    }
}
