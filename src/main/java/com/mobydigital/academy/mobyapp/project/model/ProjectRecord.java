package com.mobydigital.academy.mobyapp.project.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true) // Used for ignoring the "id" field.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ProjectRecord {

    @JsonProperty("id")
    private String id;

    @JsonProperty("fields")
    @Autowired
    private MobyAppProject fields;

}
