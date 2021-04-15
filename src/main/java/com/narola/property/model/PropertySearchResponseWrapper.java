package com.narola.property.model;

import java.util.List;

public class PropertySearchResponseWrapper {
    private List<PropertySearchResponse> properties;

    public List<PropertySearchResponse> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertySearchResponse> properties) {
        this.properties = properties;
    }

    public PropertySearchResponseWrapper(List<PropertySearchResponse> properties) {
        this.properties = properties;
    }
}
