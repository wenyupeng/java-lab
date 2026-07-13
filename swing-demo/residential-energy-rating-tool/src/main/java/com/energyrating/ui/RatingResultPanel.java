package com.energyrating.ui;

import com.energyrating.model.Project;
import com.energyrating.model.RatingResult;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class RatingResultPanel extends JPanel {
    private final JTextArea resultArea = new JTextArea();

    public RatingResultPanel() {
        setLayout(new BorderLayout(8, 8));
        resultArea.setEditable(false);
        resultArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
        displayEmptyState();
    }

    public void displayEmptyState() {
        resultArea.setText("No rating result yet. Fill in project details and click Calculate Rating.");
    }

    public void display(Project project, RatingResult result) {
        String certificate = """
                Residential Energy Rating Certificate Preview
                =============================================
                
                Project: %s
                Address: %s
                Climate Zone: %s
                Floor Area: %.2f sqm
                Wall Type: %s
                Window Type: %s
                Roof Insulation: %s
                Orientation: %s
                Effective Shading: %s
                Cross Ventilation: %s
                
                Rating Score: %.1f / 10
                Compliance Status: %s
                
                Summary:
                %s
                
                Assessment Date: %s
                
                Disclaimer:
                This is a simplified software engineering practice project.
                It is not an official NatHERS or FirstRate5 assessment tool.
                """.formatted(
                project.getName(), project.getAddress(), project.getClimateZone(), project.getFloorArea(),
                project.getWallType(), project.getWindowType(), project.getRoofInsulation(), project.getOrientation(),
                project.isShading() ? "Yes" : "No", project.isCrossVentilation() ? "Yes" : "No",
                result.getScore(), result.isCompliant() ? "PASS" : "FAIL", result.getSummary(), LocalDate.now()
        );
        resultArea.setText(certificate);
    }
}
