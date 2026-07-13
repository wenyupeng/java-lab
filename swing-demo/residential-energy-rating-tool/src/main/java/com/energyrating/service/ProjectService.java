package com.energyrating.service;

import com.energyrating.model.Project;
import com.energyrating.repository.ProjectRepository;

import java.util.List;

public class ProjectService {
    private final ProjectRepository repository;
    private final ValidationService validationService;

    public ProjectService(ProjectRepository repository, ValidationService validationService) {
        this.repository = repository;
        this.validationService = validationService;
    }

    public Project save(Project project) {
        validationService.validateOrThrow(project);
        return repository.save(project);
    }

    public List<Project> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
