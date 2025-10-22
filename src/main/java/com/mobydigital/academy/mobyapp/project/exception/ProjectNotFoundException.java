package com.mobydigital.academy.mobyapp.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// This exception sets the HTTP status to 404 when thrown.
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProjectNotFoundException extends Exception {

    // Default exception message
    public ProjectNotFoundException() {
        super("The project doesn't exist.");
    }

    public ProjectNotFoundException(String message) {
        super(message);
    }

}
