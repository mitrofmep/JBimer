package ru.mitrofmep.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mitrofmep.dao.EngineerDAO;
import ru.mitrofmep.models.Engineer;
import ru.mitrofmep.services.EngineerService;

@Component
public class EngineerValidator implements Validator {

    private final EngineerService engineerService;

    @Autowired
    public EngineerValidator(EngineerService engineerService) {
        this.engineerService = engineerService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Engineer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Engineer engineer = (Engineer) target;
        if (engineer.getFirstName().isEmpty()) errors.rejectValue("firstName", "", "Field is empty");
        if (engineer.getLastName().isEmpty()) errors.rejectValue("lastName", "", "Field is empty");
        if (engineer.getDiscipline().isEmpty()) errors.rejectValue("discipline", "", "Field is empty");
        if (engineer.getTelegramUsername().isEmpty()) errors.rejectValue("telegramUsername", "", "Field is empty");
        if (engineer.getEmail().isEmpty()) errors.rejectValue("email", "", "Field is empty");

        if (engineerService.getEngineerByFullName(engineer.getFirstName(), engineer.getLastName()).isPresent())
            errors.rejectValue("lastName", "", "Engineer with this name is already exist");
        if (engineerService.getEngineerByTelegramUsername(engineer.getTelegramUsername()).isPresent())
            errors.rejectValue("telegramUsername", "", "Engineer with this telegram username is already exist");
        if (engineerService.getEngineerByEmail(engineer.getEmail()).isPresent())
            errors.rejectValue("email", "", "Engineer with this email is already exist");
    }
}
