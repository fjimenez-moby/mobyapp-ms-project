package com.mobydigital.academy.mobyapp.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mobydigital.academy.mobyapp.project.model.MobyAppProject;
import com.mobydigital.academy.mobyapp.project.model.ProjectAirtableResponse;
import com.mobydigital.academy.mobyapp.project.model.ProjectRecord;

@Service
public class AirtableService {

    private final RestTemplate restTemplate;
    private String airtableUrl;

    public AirtableService(RestTemplateBuilder builder, @Value("${javascript.microservice.url}") String airtableUrl) {
        this.restTemplate = builder.build();
        this.airtableUrl = airtableUrl;
    }

    /* This method returns a list of ProjectRecord 
     * [
     *      {
     *          fields: {
     *              name: MobyApp
     *              ...
     *          }
     *      },
     * ]
    */
    public List<ProjectRecord> getAllProjects() {
        String url = airtableUrl + "/api/mobyapp/projects";

        ProjectAirtableResponse response = restTemplate.getForObject(url, ProjectAirtableResponse.class);
        return response.getRecords();
    }

}
