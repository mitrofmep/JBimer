package ru.mitrofmep.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mitrofmep.dao.EngineerDAO;
import ru.mitrofmep.models.Engineer;

@Component
public class EngineerValidator implements Validator {

    private final EngineerDAO engineerDAO;

    @Autowired
    public EngineerValidator(EngineerDAO engineerDAO) {
        this.engineerDAO = engineerDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Engineer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Engineer engineer = (Engineer) target;

        if (engineerDAO.show(engineer.getEmail()).isPresent()) {
            if (engineerDAO.show(engineer.getEmail()).get().getId() != engineer.getId()) {
                errors.rejectValue("email", "", "Пользователь с указанным email уже существует");
            }
        }
    }
}
