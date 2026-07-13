package com.energyrating.ui;

import com.energyrating.model.Project;
import com.energyrating.service.ProjectService;

import javax.swing.*;
import java.awt.*;

public class ProjectListPanel extends JPanel {
    private final ProjectService projectService;
    private final ProjectTableModel tableModel = new ProjectTableModel();
    private final JTable table = new JTable(tableModel);
    private ProjectSelectionListener selectionListener;

    public ProjectListPanel(ProjectService projectService) {
        this.projectService = projectService;
        buildUi();
        refreshProjects();
    }

    private void buildUi() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshButton = new JButton("Refresh");
        JButton editButton = new JButton("Load Selected");
        JButton deleteButton = new JButton("Delete Selected");
        topBar.add(refreshButton);
        topBar.add(editButton);
        topBar.add(deleteButton);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);

        add(topBar, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshButton.addActionListener(e -> refreshProjects());
        editButton.addActionListener(e -> loadSelectedProject());
        deleteButton.addActionListener(e -> deleteSelectedProject());
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) loadSelectedProject();
        });
    }

    public void refreshProjects() {
        tableModel.setProjects(projectService.findAll());
    }

    public void setSelectionListener(ProjectSelectionListener selectionListener) {
        this.selectionListener = selectionListener;
    }

    private void loadSelectedProject() {
        int viewRow = table.getSelectedRow();
        if (viewRow < 0) return;
        int modelRow = table.convertRowIndexToModel(viewRow);
        Project project = tableModel.getProjectAt(modelRow);
        if (selectionListener != null) selectionListener.onProjectSelected(project);
    }

    private void deleteSelectedProject() {
        int viewRow = table.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a project first.");
            return;
        }
        int modelRow = table.convertRowIndexToModel(viewRow);
        Project project = tableModel.getProjectAt(modelRow);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete project '" + project.getName() + "'?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION && project.getId() != null) {
            projectService.delete(project.getId());
            refreshProjects();
        }
    }

    public interface ProjectSelectionListener {
        void onProjectSelected(Project project);
    }
}
