package com.narola.property.serviceimpl;

import com.narola.property.dao.PropertyCriteria;
import com.narola.property.entity.Property;
import com.narola.property.model.AddUpdatePropertyRequest;
import com.narola.property.model.PropertySearchRequest;
import com.narola.property.model.PropertySearchResponse;
import com.narola.property.model.PropertySearchResponseWrapper;
import com.narola.property.repository.PropertyRepository;
import com.narola.property.repository.UserRepository;
import com.narola.property.security.UserUtils;
import com.narola.property.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyCriteria propertyCriteria;

    @Override
    public List<PropertySearchResponse> getAllVerifiedProperties() {
        return propertyRepository.findAllVerifiedProperties();
    }

//    @Override
//    public List<Property> getAllProperties() {
//        return propertyRepository.findAll();
//    }


    @Override
    public List<PropertySearchResponse> getAllProperties(PropertySearchRequest request){
        List<Property> propertyList=propertyCriteria.getPropertiesWithSearch(request.getSortBy()
                ,request.getSortOrder()
                ,request.getPageSize()
                ,request.getKeyword()
                ,request.getPropertyId()
                ,request.getPageNo());
        List<PropertySearchResponse> responseList=new ArrayList<>();
        for (Property property : propertyList) {
            PropertySearchResponse obj=new PropertySearchResponse(property.getPropertyId()
                    ,property.getName()
                    ,property.getType()
                    ,Math.toIntExact(property.getPrice())
                    ,property.getLocation()
                    ,property.isVerified());
            responseList.add(obj);
        }
        return responseList;
    }

    @Override
    public PropertySearchResponse getPropertyById(int propertyId) {
        return propertyRepository.findPropertyById(propertyId);
    }

    @Override
    public Property addProperty(AddUpdatePropertyRequest request) {
        Property property = new Property();
        property.setName(request.getName());
        property.setType(request.getType());
        property.setPrice(request.getPrice());
        property.setLocation(request.getLocation());
        property.setVerified(request.isVerified());
        property.setUser(userRepository.getUserByUsername(UserUtils.getLoggedInUserName()));
        return propertyRepository.save(property);
    }

    @Override
    public Property updateProperty(AddUpdatePropertyRequest request) {
        Property property = propertyRepository.findById(request.getId()).get();
        property.setName(request.getName());
        property.setType(request.getType());
        property.setPrice(request.getPrice());
        property.setLocation(request.getLocation());
        property.setVerified(request.isVerified());
//        property.setUser(userRepository.getUserByUsername(UserUtils.getLoggedInUserName()));
        return propertyRepository.save(property);
    }

    @Override
    public void verifyProperty(int propertyId) {
        propertyRepository.verifyProperty(propertyId);
    }

    /*private List<PropertySearchResponse> entityToModel(List<Property> productEntities) {
        return productEntities.stream().map(w -> {
            PropertySearchResponse response = new PropertySearchResponse();
            response.setId(w.getPropertyId());
            response.setName(w.getName());
            response.setType(w.getType());
            response.setPrice(Math.toIntExact(w.getPrice()));
            response.setLocation(w.getLocation());
            response.setVerified(w.isVerified());
            response.setUserId(w.getUser().getUserId());
            return response;
        }).collect(Collectors.toList());
    }*/
}
