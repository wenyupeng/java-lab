package com.energyrating.service;

import com.energyrating.model.Project;
import com.energyrating.model.RatingResult;

public class RatingCalculator {
    private static final double PASS_THRESHOLD = 6.0;

    public RatingResult calculate(Project project) {
        double score = 5.0;
        score += roofInsulationScore(project.getRoofInsulation());
        score += windowScore(project.getWindowType());
        score += orientationScore(project.getOrientation());

        if (project.isShading()) score += 0.5;
        if (project.isCrossVentilation()) score += 0.4;
        if (project.getFloorArea() > 250) score -= 0.4;
        if ("Lightweight".equals(project.getWallType())) score -= 0.2;
        if ("High Performance".equals(project.getWallType())) score += 0.5;

        score = Math.max(0.0, Math.min(10.0, score));
        score = Math.round(score * 10.0) / 10.0;

        boolean compliant = score >= PASS_THRESHOLD;
        String summary = compliant
                ? "The simulated dwelling meets the minimum energy rating threshold."
                : "The simulated dwelling does not meet the minimum energy rating threshold.";
        return new RatingResult(score, compliant, summary);
    }

    private double roofInsulationScore(String roofInsulation) {
        return switch (roofInsulation) {
            case "High" -> 1.0;
            case "Medium" -> 0.5;
            case "Low" -> -0.8;
            default -> 0.0;
        };
    }

    private double windowScore(String windowType) {
        return switch (windowType) {
            case "Double Glazed" -> 0.8;
            case "Low-E Glass" -> 1.0;
            case "Single Glazed" -> -0.6;
            default -> 0.0;
        };
    }

    private double orientationScore(String orientation) {
        return switch (orientation) {
            case "North" -> 0.5;
            case "East", "West" -> 0.1;
            case "South" -> -0.3;
            default -> 0.0;
        };
    }
}
