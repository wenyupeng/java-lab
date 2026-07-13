package com.energyrating.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Project {
    private Long id;
    private String name;
    private String address;
    private String climateZone;
    private double floorArea;
    private String wallType;
    private String windowType;
    private String roofInsulation;
    private String orientation;
    private boolean shading;
    private boolean crossVentilation;
}
