package com.example.managersystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AssignUserResourcesDTO{
    private long userId;
    private List<String> endpoint;
}
