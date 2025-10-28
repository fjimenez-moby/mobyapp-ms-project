package com.mobydigital.academy.mobyapp.project.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
//import com.mobydigital.academy.mobyapp.commons.dto.ProjectDTO;
import coms.dto.ProjectDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Entity from "Proyectos MobyApp"
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class MobyAppProject {

    @JsonProperty("Nombre")
    private String name;

    @JsonProperty("Cliente")
    private String client;

    @JsonProperty("Fecha inicio")
    private LocalDate creationDate;

    @JsonProperty("Fecha cierre")
    private LocalDate closingDate;
    
    @JsonProperty("Estado")
    private Boolean state; // The project is active or not?

    @JsonProperty("Usuarios MobyAp")
    private List<MobyAppUser> users; // TODO: Crear la clase MobyAppUser

    public ProjectDTO toFullDTO() {
        ProjectDTO projectDto = new ProjectDTO();

        projectDto.setName(this.name);
        projectDto.setClient(this.client);
        projectDto.setCreationDate(this.creationDate);
        projectDto.setClosingDate(this.closingDate);

        return projectDto;
    }

}
