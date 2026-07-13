package com.energyrating.ui;

import com.energyrating.repository.ProjectRepository;
import com.energyrating.repository.SqliteProjectRepository;
import com.energyrating.service.ProjectService;
import com.energyrating.service.RatingCalculator;
import com.energyrating.service.ValidationService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Residential Energy Rating Tool");
        setSize(1050, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ProjectRepository repository = new SqliteProjectRepository();
        ValidationService validationService = new ValidationService();
        ProjectService projectService = new ProjectService(repository, validationService);
        RatingCalculator ratingCalculator = new RatingCalculator();

        RatingResultPanel resultPanel = new RatingResultPanel();
        ProjectListPanel listPanel = new ProjectListPanel(projectService);
        ProjectFormPanel formPanel = new ProjectFormPanel(projectService, ratingCalculator, resultPanel, listPanel::refreshProjects);
        listPanel.setSelectionListener(formPanel::loadProject);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Project List", listPanel);
        tabs.addTab("Project Details", formPanel);
        tabs.addTab("Rating Result", resultPanel);

        add(buildHeader(), BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
    }

    private JComponent buildHeader() {
        JLabel label = new JLabel("Residential Energy Rating Desktop Tool - Java Swing Practice Project");
        label.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        label.setFont(label.getFont().deriveFont(Font.BOLD, 16f));
        return label;
    }
}
