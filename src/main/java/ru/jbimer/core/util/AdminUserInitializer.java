package ru.jbimer.core.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.jbimer.core.models.Engineer;
import ru.jbimer.core.services.EngineerDetailsService;
import ru.jbimer.core.services.EngineersService;

import java.util.Optional;

@Component
public class AdminUserInitializer implements ApplicationRunner {

    @Autowired
    private EngineersService engineersService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Engineer> existingAdmin = engineersService.findByUsername("admin");
        if (existingAdmin.isPresent()) return;
        Engineer admin = new Engineer();
        admin.setUsername("admin");
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setDiscipline("GIP");
        admin.setEmail("admin@gmail.com");
        admin.setTelegramUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRole("ROLE_ADMIN");
        engineersService.save(admin);
    }
}
