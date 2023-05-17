package ru.jbimer.core.exception;

import ru.jbimer.core.models.Project;

public class InvalidFileNameException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private Project project;

    public InvalidFileNameException(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
