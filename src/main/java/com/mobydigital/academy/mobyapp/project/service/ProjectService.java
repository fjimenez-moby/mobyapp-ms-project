package com.mobydigital.academy.mobyapp.project.service;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mobydigital.academy.mobyapp.project.exception.ProjectNotFoundException;
import com.mobydigital.academy.mobyapp.project.model.MobyAppProject;
import com.mobydigital.academy.mobyapp.project.model.ProjectDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;


@Service
public class ProjectService implements IProjectService {

    @Value("${PROJECT_AIRTABLE_URL}")
    private String urlProject;

    private final RestTemplate restTemplate;
    private String airtableUrl;

    public ProjectService(RestTemplateBuilder builder, @Value("${javascript.microservice.url}") String airtableUrl) {
        this.restTemplate = builder.build();
        this.airtableUrl = airtableUrl;
    }

    @Override
    public List<MobyAppProject> getAllProjects() { //TODO: IMPLEMENTAR METODO getAllProjects()
        boolean lock = true;
        List<MobyAppProject> v = null;
            if(lock){
                throw new NotImplementedException("Metodo no implementado");
            }
            else{
                return v;
            }
    }

    @Override
    public ProjectDTO findProjectById(String id) throws ProjectNotFoundException {
           String urlProjectoId = urlProject + "/" + id;
            ProjectDTO project = restTemplate.getForObject(urlProject, ProjectDTO.class);

            if(project != null){
                return project;
            }

            throw new ProjectNotFoundException("Project not created");
    }

    @Override
    public ProjectDTO findProjectByName(String name) throws ProjectNotFoundException {
            String urlProjectoId = urlProject + "/" + name;
            ProjectDTO project = restTemplate.getForObject(urlProject, ProjectDTO.class);

            if(project != null){
                return project;
            }

            throw new ProjectNotFoundException("Project not created");
    }
    
    @Override
    public ProjectDTO createProject(ProjectDTO newProject) throws ProjectNotFoundException{
            ProjectDTO project = restTemplate.postForObject(urlProject, newProject, ProjectDTO.class);

            if(project != null){
                return project;
            }

            throw new ProjectNotFoundException("Project not created");
    }

    @Override
    public ProjectDTO updateProject(String name, ProjectDTO updatedProject) throws ProjectNotFoundException {
        String urlName = urlProject +"/Name?="+ name; 
        
        HttpEntity<ProjectDTO> requestEntity = new HttpEntity<>(updatedProject);

            // Ejecuta el PUT y espera un UserDTO de vuelta
            ResponseEntity<ProjectDTO> response = restTemplate.exchange(
                    urlName,
                    HttpMethod.PUT,
                    requestEntity,
                    ProjectDTO.class
            );

            // Validar el código de respuesta
            if (response.getStatusCode().is2xxSuccessful()) {
                ProjectDTO updatedProjectR = response.getBody();
                if (updatedProjectR == null) {
                    throw new RuntimeException("Respuesta vacía al actualizar usuario");
                }
                return updatedProjectR;
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ProjectNotFoundException("Proyecto no encontrado: " + updatedProject.getName());
            } else {
                throw new RuntimeException("Error al actualizar usuario: " + response.getStatusCode());
            }

    }
}
