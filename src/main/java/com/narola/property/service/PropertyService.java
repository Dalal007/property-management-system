package com.narola.property.service;

import com.narola.property.entity.Property;
import com.narola.property.model.AddUpdatePropertyRequest;
import com.narola.property.model.PropertySearchRequest;
import com.narola.property.model.PropertySearchResponse;

import java.util.List;

public interface PropertyService {
    List<PropertySearchResponse> getAllVerifiedProperties();
    List<PropertySearchResponse> getAllProperties(PropertySearchRequest request);
//    List<Property> getAllProperties();
    PropertySearchResponse getPropertyById(int propertyId);
    Property addProperty(AddUpdatePropertyRequest request);
    Property updateProperty(AddUpdatePropertyRequest request);
    void verifyProperty(int propertyId);

}
