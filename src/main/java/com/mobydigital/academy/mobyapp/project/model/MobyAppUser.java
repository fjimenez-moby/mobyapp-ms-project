package com.mobydigital.academy.mobyapp.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
//import com.mobydigital.academy.mobyapp.commons.dto.*;
import coms.dto.*;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

//import com.mobydigital.academy.mobyapp.commons.dto.ProjectDTO;
import coms.dto.ProjectDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class MobyAppUser {
    
    @JsonProperty("Correo Moby")
    private String email;

    private String names;
    private String familyNames;

    @JsonProperty("Fecha de Nacimiento")
    private LocalDate dateOfBirth; 

    @JsonProperty("Provincia")
    private ProvinceDTO province;

    @JsonProperty("Localidad")
    private LocalityDTO locality;

    @JsonProperty("Tecnología Actual")
    private String technologies; //si se almacena de otra forma, hay que cambiar el tipo de dato

    @JsonProperty("Proyectos")
    private List<MobyAppProject> projects;

    @JsonProperty("Referente")
    private MobyAppUser referent;      // refete? :( chi refete :)

    @JsonProperty("Es Referente?")
    private Boolean isReferent; 

    @JsonProperty("Talent Partner")
    private MobyAppUser talentPartner;

    @JsonProperty("Es Talent Partner?")
    private Boolean isTalentPartner;

    @JsonProperty("Fecha de Alta")
    private LocalDate startDate;

    @JsonProperty("FirmaURL")
    private String urlSignature;
}