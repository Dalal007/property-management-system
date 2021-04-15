package com.narola.property.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PropertySearchResponse {
    private int id;
    private String name;
    private String type;
    private long price;
    private String location;
    private boolean verified;
}
