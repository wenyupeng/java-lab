package com.energyrating.repository;

import com.energyrating.model.Project;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    Project save(Project project);
    List<Project> findAll();
    Optional<Project> findById(long id);
    void deleteById(long id);
}
