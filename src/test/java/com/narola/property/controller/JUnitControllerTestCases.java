package com.narola.property.controller;

import com.narola.property.entity.Property;
import com.narola.property.entity.User;
import com.narola.property.model.AddUpdatePropertyRequest;
import com.narola.property.model.PropertySearchRequest;
import com.narola.property.model.PropertySearchResponse;
import com.narola.property.model.PropertySearchResponseWrapper;
import com.narola.property.security.UserUtils;

import com.narola.property.service.PropertyService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Objects;

//@SpringBootTest
@RunWith(PowerMockRunner.class)
@PrepareForTest(UserUtils.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class JUnitControllerTestCases {


    @Mock
    private PropertyService propertyService;

    @InjectMocks
    PropertyController propertyController;

    Property property = new Property();
    User user = new User();
    PropertySearchResponse propertySearchResponse = new PropertySearchResponse();


    @Test
    public void add(){
        user.setUserId(1);
        user.setEnabled(true);
        user.setPassword("demo");
        user.setUserName("demo");

        property.setPropertyId(1);
        property.setName("4bhk");
        property.setPrice(7000000);
        property.setType("bunglow");
        property.setVerified(true);
        property.setLocation("ahmedabad");
        property.setUser(user);

        AddUpdatePropertyRequest addUpdatePropertyRequest = new AddUpdatePropertyRequest(property.getPropertyId()
                , property.getName(), property.getType(), property.getPrice(), property.getLocation(), property.isVerified()
                , property.getUser().getUserId());
        Mockito.when(propertyService.addProperty(addUpdatePropertyRequest)).thenReturn(property);
        Property property1 = propertyController.addProperty(addUpdatePropertyRequest);
        Assertions.assertEquals("ahmedabad", property1.getLocation());
    }

    @Test
    public void update(){
        AddUpdatePropertyRequest propertyRequest = new AddUpdatePropertyRequest();
        propertyRequest.setUserId(10);
        propertyRequest.setVerified(true);
        propertyRequest.setPrice(5000000);
        propertyRequest.setName("4bhk");
        propertyRequest.setLocation("ahmedabad");
        propertyRequest.setType("bunglow");
        propertyRequest.setId(10);
        Mockito.when(propertyService.updateProperty(Mockito.any())).thenReturn(property);
        Property property1 = propertyController.updateProperty(propertyRequest);
        Assertions.assertEquals("ahmedabad", propertyRequest.getLocation());
    }

    @Test
    public void approve(){
        property.setPropertyId(1);
        property.setLocation("ahmedabad");
        property.setVerified(false);
        property.setType("bunglow");
        property.setPrice(7000000);
        property.setName("4bhk");

        PropertyController propertyController = Mockito.mock(PropertyController.class);
        Mockito.doNothing().when(propertyController).verifyProperty(property.getPropertyId());
        propertyController.verifyProperty(property.getPropertyId());
        Assertions.assertFalse(property.isVerified());
    }

    @Test
    public void search(){
        PropertySearchRequest request = new PropertySearchRequest();
        request.setKeyword("ahmedabad");
        request.setPageNo(1);
        request.setPageSize(5);
        request.setSortBy("type");
        request.setSortOrder("ASC");
        request.setPropertyId(1);

        Mockito.when(propertyService.getAllProperties(request)).thenReturn(Collections.singletonList(propertySearchResponse));
        ResponseEntity<PropertySearchResponseWrapper> list = propertyController.getAllProperties(request);
        Assertions.assertEquals(1, Objects.requireNonNull(list.getBody()).getProperties().size());
    }

    @Test
    public void getById(){
        property.setPropertyId(1);
        property.setLocation("ahmedabad");
        property.setVerified(true);
        property.setType("bunglow");
        property.setPrice(7000000);
        property.setName("4bhk");

        propertySearchResponse.setId(property.getPropertyId());
        propertySearchResponse.setPrice(property.getPrice());
        propertySearchResponse.setName(property.getName());
        propertySearchResponse.setType(property.getType());
        propertySearchResponse.setLocation(property.getLocation());
        propertySearchResponse.setVerified(property.isVerified());
        Mockito.when(propertyService.getPropertyById(1)).thenReturn(propertySearchResponse);
        PropertySearchResponse propertySearchResponse1 = propertyController.getById(1);
        Assertions.assertEquals("bunglow", propertySearchResponse1.getType());
    }
}