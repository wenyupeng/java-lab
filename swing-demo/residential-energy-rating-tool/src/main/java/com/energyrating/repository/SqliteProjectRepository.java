package com.energyrating.repository;

import com.energyrating.model.Project;
import com.energyrating.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteProjectRepository implements ProjectRepository {
    @Override
    public Project save(Project project) {
        if (project.getId() == null) return insert(project);
        update(project);
        return project;
    }

    private Project insert(Project project) {
        String sql = """
                INSERT INTO projects(name, address, climate_zone, floor_area, wall_type, window_type,
                roof_insulation, orientation, shading, cross_ventilation)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bindProject(statement, project);
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) project.setId(keys.getLong(1));
            }
            return project;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert project", e);
        }
    }

    private void update(Project project) {
        String sql = """
                UPDATE projects SET name=?, address=?, climate_zone=?, floor_area=?, wall_type=?, window_type=?,
                roof_insulation=?, orientation=?, shading=?, cross_ventilation=? WHERE id=?
                """;
        try (Connection connection = DatabaseManager.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            bindProject(statement, project);
            statement.setLong(11, project.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update project", e);
        }
    }

    @Override
    public List<Project> findAll() {
        String sql = "SELECT * FROM projects ORDER BY id DESC";
        List<Project> projects = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) projects.add(mapProject(resultSet));
            return projects;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load projects", e);
        }
    }

    @Override
    public Optional<Project> findById(long id) {
        String sql = "SELECT * FROM projects WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) return Optional.of(mapProject(resultSet));
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find project", e);
        }
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM projects WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete project", e);
        }
    }

    private void bindProject(PreparedStatement statement, Project project) throws SQLException {
        statement.setString(1, project.getName());
        statement.setString(2, project.getAddress());
        statement.setString(3, project.getClimateZone());
        statement.setDouble(4, project.getFloorArea());
        statement.setString(5, project.getWallType());
        statement.setString(6, project.getWindowType());
        statement.setString(7, project.getRoofInsulation());
        statement.setString(8, project.getOrientation());
        statement.setInt(9, project.isShading() ? 1 : 0);
        statement.setInt(10, project.isCrossVentilation() ? 1 : 0);
    }

    private Project mapProject(ResultSet rs) throws SQLException {
        return new Project(
                rs.getLong("id"), rs.getString("name"), rs.getString("address"), rs.getString("climate_zone"),
                rs.getDouble("floor_area"), rs.getString("wall_type"), rs.getString("window_type"),
                rs.getString("roof_insulation"), rs.getString("orientation"), rs.getInt("shading") == 1,
                rs.getInt("cross_ventilation") == 1
        );
    }
}
