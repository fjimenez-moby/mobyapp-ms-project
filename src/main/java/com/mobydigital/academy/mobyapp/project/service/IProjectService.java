package com.mobydigital.academy.mobyapp.project.service;

import java.util.List;

import com.mobydigital.academy.mobyapp.project.exception.ProjectNotFoundException;
import com.mobydigital.academy.mobyapp.project.model.MobyAppProject;
import com.mobydigital.academy.mobyapp.project.model.ProjectDTO;

// Project service interface
public interface IProjectService {

    List<MobyAppProject> getAllProjects();
    ProjectDTO findProjectById(String id) throws ProjectNotFoundException;
    ProjectDTO findProjectByName(String name) throws ProjectNotFoundException;
    ProjectDTO createProject(ProjectDTO project) throws ProjectNotFoundException;
    ProjectDTO updateProject(String name, ProjectDTO updatedProject) throws ProjectNotFoundException;

}
