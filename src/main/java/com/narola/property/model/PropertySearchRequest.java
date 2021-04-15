package com.narola.property.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PropertySearchRequest {
    private String keyword;
    private String sortBy;
    private String sortOrder="ASC";
    private int pageSize=5;
    private int pageNo=1;
    private int propertyId=0;
}
