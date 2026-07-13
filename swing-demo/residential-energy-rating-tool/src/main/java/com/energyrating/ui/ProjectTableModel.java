package com.energyrating.ui;

import com.energyrating.model.Project;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProjectTableModel extends AbstractTableModel {
    private final String[] columns = {"ID", "Project", "Climate Zone", "Floor Area", "Window", "Insulation"};
    private List<Project> projects = new ArrayList<>();

    public void setProjects(List<Project> projects) {
        this.projects = projects;
        fireTableDataChanged();
    }

    public Project getProjectAt(int rowIndex) {
        return projects.get(rowIndex);
    }

    @Override
    public int getRowCount() { return projects.size(); }

    @Override
    public int getColumnCount() { return columns.length; }

    @Override
    public String getColumnName(int column) { return columns[column]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Project p = projects.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> p.getId();
            case 1 -> p.getName();
            case 2 -> p.getClimateZone();
            case 3 -> p.getFloorArea();
            case 4 -> p.getWindowType();
            case 5 -> p.getRoofInsulation();
            default -> "";
        };
    }
}
