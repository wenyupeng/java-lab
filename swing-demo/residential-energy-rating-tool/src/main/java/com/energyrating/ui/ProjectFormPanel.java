package com.energyrating.ui;

import com.energyrating.model.Project;
import com.energyrating.model.RatingResult;
import com.energyrating.service.ProjectService;
import com.energyrating.service.RatingCalculator;

import javax.swing.*;
import java.awt.*;

public class ProjectFormPanel extends JPanel {
    private final JTextField nameField = new JTextField();
    private final JTextField addressField = new JTextField();
    private final JTextField floorAreaField = new JTextField("120");
    private final JComboBox<String> climateZoneBox = new JComboBox<>(new String[]{"Melbourne Mild Temperate", "Hot Dry", "Warm Humid", "Cool Temperate"});
    private final JComboBox<String> wallTypeBox = new JComboBox<>(new String[]{"Brick Veneer", "Lightweight", "Concrete", "High Performance"});
    private final JComboBox<String> windowTypeBox = new JComboBox<>(new String[]{"Single Glazed", "Double Glazed", "Low-E Glass"});
    private final JComboBox<String> roofInsulationBox = new JComboBox<>(new String[]{"Low", "Medium", "High"});
    private final JComboBox<String> orientationBox = new JComboBox<>(new String[]{"North", "East", "South", "West"});
    private final JCheckBox shadingBox = new JCheckBox("Effective shading");
    private final JCheckBox ventilationBox = new JCheckBox("Cross ventilation");
    private final JButton calculateButton = new JButton("Calculate Rating");
    private final JButton saveButton = new JButton("Save Project");
    private final JButton clearButton = new JButton("Clear Form");

    private final ProjectService projectService;
    private final RatingCalculator calculator;
    private final RatingResultPanel ratingResultPanel;
    private final Runnable refreshProjectListCallback;
    private Project currentProject;

    public ProjectFormPanel(ProjectService projectService, RatingCalculator calculator,
                            RatingResultPanel ratingResultPanel, Runnable refreshProjectListCallback) {
        this.projectService = projectService;
        this.calculator = calculator;
        this.ratingResultPanel = ratingResultPanel;
        this.refreshProjectListCallback = refreshProjectListCallback;
        buildUi();
        bindActions();
    }

    private void buildUi() {
        setLayout(new BorderLayout(12, 12));
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        addRow(form, gbc, row++, "Project Name:", nameField);
        addRow(form, gbc, row++, "Address:", addressField);
        addRow(form, gbc, row++, "Climate Zone:", climateZoneBox);
        addRow(form, gbc, row++, "Floor Area (sqm):", floorAreaField);
        addRow(form, gbc, row++, "Wall Type:", wallTypeBox);
        addRow(form, gbc, row++, "Window Type:", windowTypeBox);
        addRow(form, gbc, row++, "Roof Insulation:", roofInsulationBox);
        addRow(form, gbc, row++, "Orientation:", orientationBox);
        addRow(form, gbc, row++, "Shading:", shadingBox);
        addRow(form, gbc, row++, "Ventilation:", ventilationBox);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(clearButton);
        buttons.add(saveButton);
        buttons.add(calculateButton);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 1;
        panel.add(component, gbc);
    }

    private void bindActions() {
        saveButton.addActionListener(e -> saveProject());
        clearButton.addActionListener(e -> clearForm());
        calculateButton.addActionListener(e -> calculateRatingInBackground());
    }

    private void saveProject() {
        try {
            Project project = buildProjectFromForm();
            Project saved = projectService.save(project);
            currentProject = saved;
            refreshProjectListCallback.run();
            JOptionPane.showMessageDialog(this, "Project saved successfully.");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void calculateRatingInBackground() {
        try {
            Project project = buildProjectFromForm();
            calculateButton.setEnabled(false);
            calculateButton.setText("Calculating...");

            SwingWorker<RatingResult, Void> worker = new SwingWorker<>() {
                @Override
                protected RatingResult doInBackground() throws Exception {
                    Thread.sleep(600); // Simulates a longer assessment workflow without freezing the UI.
                    return calculator.calculate(project);
                }

                @Override
                protected void done() {
                    try {
                        RatingResult result = get();
                        ratingResultPanel.display(project, result);
                    } catch (Exception ex) {
                        showError("Failed to calculate rating: " + ex.getMessage());
                    } finally {
                        calculateButton.setEnabled(true);
                        calculateButton.setText("Calculate Rating");
                    }
                }
            };
            worker.execute();
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private Project buildProjectFromForm() {
        double floorArea;
        try {
            floorArea = Double.parseDouble(floorAreaField.getText().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Floor area must be a valid number.");
        }

        return new Project(
                currentProject == null ? null : currentProject.getId(),
                nameField.getText().trim(),
                addressField.getText().trim(),
                (String) climateZoneBox.getSelectedItem(),
                floorArea,
                (String) wallTypeBox.getSelectedItem(),
                (String) windowTypeBox.getSelectedItem(),
                (String) roofInsulationBox.getSelectedItem(),
                (String) orientationBox.getSelectedItem(),
                shadingBox.isSelected(),
                ventilationBox.isSelected()
        );
    }

    public void loadProject(Project project) {
        this.currentProject = project;
        nameField.setText(project.getName());
        addressField.setText(project.getAddress());
        climateZoneBox.setSelectedItem(project.getClimateZone());
        floorAreaField.setText(String.valueOf(project.getFloorArea()));
        wallTypeBox.setSelectedItem(project.getWallType());
        windowTypeBox.setSelectedItem(project.getWindowType());
        roofInsulationBox.setSelectedItem(project.getRoofInsulation());
        orientationBox.setSelectedItem(project.getOrientation());
        shadingBox.setSelected(project.isShading());
        ventilationBox.setSelected(project.isCrossVentilation());
    }

    private void clearForm() {
        currentProject = null;
        nameField.setText("");
        addressField.setText("");
        floorAreaField.setText("120");
        climateZoneBox.setSelectedIndex(0);
        wallTypeBox.setSelectedIndex(0);
        windowTypeBox.setSelectedIndex(0);
        roofInsulationBox.setSelectedIndex(0);
        orientationBox.setSelectedIndex(0);
        shadingBox.setSelected(false);
        ventilationBox.setSelected(false);
        ratingResultPanel.displayEmptyState();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
