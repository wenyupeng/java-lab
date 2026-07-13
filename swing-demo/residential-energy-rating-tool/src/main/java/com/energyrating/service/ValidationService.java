package com.energyrating.service;

import com.energyrating.model.Project;
import java.util.ArrayList;
import java.util.List;

public class ValidationService {
    public List<String> validate(Project project) {
        List<String> errors = new ArrayList<>();
        if (project.getName() == null || project.getName().trim().isEmpty()) errors.add("Project name is required.");
        if (project.getAddress() == null || project.getAddress().trim().isEmpty()) errors.add("Address is required.");
        if (project.getClimateZone() == null || project.getClimateZone().trim().isEmpty()) errors.add("Climate zone is required.");
        if (project.getFloorArea() <= 0) errors.add("Floor area must be greater than zero.");
        if (project.getWallType() == null || project.getWallType().trim().isEmpty()) errors.add("Wall type is required.");
        if (project.getWindowType() == null || project.getWindowType().trim().isEmpty()) errors.add("Window type is required.");
        if (project.getRoofInsulation() == null || project.getRoofInsulation().trim().isEmpty()) errors.add("Roof insulation is required.");
        if (project.getOrientation() == null || project.getOrientation().trim().isEmpty()) errors.add("Orientation is required.");
        return errors;
    }

    public void validateOrThrow(Project project) {
        List<String> errors = validate(project);
        if (!errors.isEmpty()) throw new IllegalArgumentException(String.join("\n", errors));
    }
}
