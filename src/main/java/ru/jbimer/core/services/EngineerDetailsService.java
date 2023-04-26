package ru.jbimer.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.jbimer.core.models.Engineer;
import ru.jbimer.core.repositories.EngineersRepository;
import ru.jbimer.core.security.EngineerDetails;

import java.util.Optional;

@Service
public class EngineerDetailsService implements UserDetailsService {

    private final EngineersRepository engineersRepository;

    @Autowired
    public EngineerDetailsService(EngineersRepository engineersRepository) {
        this.engineersRepository = engineersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Engineer> engineer = engineersRepository.findByUsername(username);

        if (engineer.isEmpty())
            throw new UsernameNotFoundException("User not found");
        return new EngineerDetails(engineer.get());
    }
}
