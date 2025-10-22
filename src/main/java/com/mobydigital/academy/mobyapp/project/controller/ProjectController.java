package com.mobydigital.academy.mobyapp.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mobydigital.academy.mobyapp.project.exception.ProjectNotFoundException;
import com.mobydigital.academy.mobyapp.project.model.MobyAppProject;
import com.mobydigital.academy.mobyapp.project.service.ProjectService;
import com.mobydigital.academy.mobyapp.project.model.ProjectDTO;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectService service;
    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<MobyAppProject>> getAllProjects() {
        List<MobyAppProject> projectList = service.getAllProjects();
        if (projectList != null) {
            return new ResponseEntity<>(projectList, HttpStatus.OK);
        }
        // There's a problem with the repository
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable String id) throws ProjectNotFoundException {
        ProjectDTO project = service.findProjectById(id);
        return new ResponseEntity<>(project, HttpStatus.OK);

    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProjectDTO> getProjectByName(@PathVariable String name) throws ProjectNotFoundException {
        ProjectDTO project = service.findProjectByName(name);
        return new ResponseEntity<>(project, HttpStatus.OK);

    }

    // TODO: Check this status responses with the exception handling
    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO newProject) throws ProjectNotFoundException {
            return new ResponseEntity<ProjectDTO>(service.createProject(newProject), HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable String id,@RequestBody ProjectDTO updatedProject) throws ProjectNotFoundException {
            projectService.updateProject(id, updatedProject);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        }


}
