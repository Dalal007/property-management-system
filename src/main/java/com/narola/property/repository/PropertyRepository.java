package com.narola.property.repository;

import com.narola.property.entity.Property;
import com.narola.property.model.PropertySearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Integer> {
    @Query("select new com.narola.property.model.PropertySearchResponse(p.propertyId, p.name, p.type, p.price, p.location, p.verified) from Property p where p.verified=true")
    List<PropertySearchResponse> findAllVerifiedProperties();

    @Modifying
    @Query("update Property p set p.verified=true where p.propertyId=:propertyId")
    void verifyProperty(int propertyId);

    @Query("select new com.narola.property.model.PropertySearchResponse(p.propertyId, p.name, p.type, p.price, p.location, p.verified) from Property p where p.propertyId=:propertyId")
    PropertySearchResponse findPropertyById(int propertyId);
}
