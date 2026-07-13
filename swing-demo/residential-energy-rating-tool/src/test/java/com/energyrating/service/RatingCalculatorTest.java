package com.energyrating.service;

import com.energyrating.model.Project;
import com.energyrating.model.RatingResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RatingCalculatorTest {
    private final RatingCalculator calculator = new RatingCalculator();

    @Test
    void shouldReturnCompliantResultForEfficientDwelling() {
        Project project = new Project(null, "Efficient House", "8 Nicholson Street, East Melbourne",
                "Melbourne Mild Temperate", 120, "High Performance", "Double Glazed",
                "High", "North", true, true);

        RatingResult result = calculator.calculate(project);

        assertTrue(result.isCompliant());
        assertTrue(result.getScore() >= 6.0);
    }

    @Test
    void shouldReturnNonCompliantResultForPoorThermalFeatures() {
        Project project = new Project(null, "Poor Rating House", "Demo Address",
                "Melbourne Mild Temperate", 300, "Lightweight", "Single Glazed",
                "Low", "South", false, false);

        RatingResult result = calculator.calculate(project);

        assertFalse(result.isCompliant());
        assertTrue(result.getScore() < 6.0);
    }

    @Test
    void shouldKeepScoreWithinZeroToTenRange() {
        Project project = new Project(null, "Boundary Test House", "Demo Address",
                "Melbourne Mild Temperate", 100, "High Performance", "Low-E Glass",
                "High", "North", true, true);

        RatingResult result = calculator.calculate(project);

        assertTrue(result.getScore() >= 0.0);
        assertTrue(result.getScore() <= 10.0);
    }
}
