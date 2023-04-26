package ru.jbimer.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.jbimer.core.services.EngineerDetailsService;

import java.util.Collections;


@Component
public class AuthProviderImpl implements AuthenticationProvider {

    private final EngineerDetailsService engineerDetailsService;

    @Autowired
    public AuthProviderImpl(EngineerDetailsService engineerDetailsService) {
        this.engineerDetailsService = engineerDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();

        UserDetails engineerDetails = engineerDetailsService.loadUserByUsername(username);

        String password = authentication.getCredentials().toString();

        // engineerDetails is good wrapper pattern, so we use here getPassword(),
        // regardless of the name of the field of the object
        if (!password.equals(engineerDetails.getPassword()))
            throw new BadCredentialsException("Incorrect Password");

        // return also authentification obj (impl Authentification) but with more credentials
        // (principal - person, password, list of credentials)
        return new UsernamePasswordAuthenticationToken(engineerDetails,
                password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
