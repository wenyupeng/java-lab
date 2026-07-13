package com.energyrating.service;

import com.energyrating.model.Project;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidationServiceTest {
    private final ValidationService validationService = new ValidationService();

    @Test
    void shouldRejectMissingProjectName() {
        Project project = validProject();
        project.setName(" ");

        List<String> errors = validationService.validate(project);

        assertTrue(errors.contains("Project name is required."));
    }

    @Test
    void shouldRejectInvalidFloorArea() {
        Project project = validProject();
        project.setFloorArea(0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateOrThrow(project)
        );

        assertTrue(exception.getMessage().contains("Floor area must be greater than zero."));
    }

    @Test
    void shouldPassValidProject() {
        Project project = validProject();

        List<String> errors = validationService.validate(project);

        assertTrue(errors.isEmpty());
    }

    private Project validProject() {
        return new Project(null, "Demo House", "Demo Address", "Melbourne Mild Temperate",
                120, "Brick Veneer", "Double Glazed", "High", "North", true, true);
    }
}
