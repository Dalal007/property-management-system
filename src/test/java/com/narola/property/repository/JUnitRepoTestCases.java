package com.narola.property.repository;

import com.narola.property.entity.Property;
import com.narola.property.entity.User;
import com.narola.property.model.PropertySearchResponse;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class JUnitRepoTestCases {
    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private UserRepository userRepository;

    User user = new User();
    Property property = new Property();
    PropertySearchResponse propertySearchResponse = new PropertySearchResponse();
    @Test
    public void getUserByUserName(){
        user.setUserName("demo");
        Mockito.when(userRepository.getUserByUsername(user.getUserName())).thenReturn(user);
        User user1 = userRepository.getUserByUsername(user.getUserName());
        Assertions.assertEquals("demo", user1.getUserName());
    }

    @Test
    public void verifyProperty(){
        property.setPropertyId(1);
        property.setVerified(false);
        PropertyRepository propertyRepository1 = Mockito.mock(PropertyRepository.class);
        Mockito.doNothing().when(propertyRepository1).verifyProperty(property.getPropertyId());
        propertyRepository.verifyProperty(property.getPropertyId());
        Assertions.assertFalse(property.isVerified());
    }

    @Test
    public void findPropertyById(){
        property.setPropertyId(1);
        propertySearchResponse.setLocation("demo");
        Mockito.when(propertyRepository.findPropertyById(property.getPropertyId())).thenReturn(propertySearchResponse);
        PropertySearchResponse propertySearchResponse1 = propertyRepository.findPropertyById(property.getPropertyId());
        Assertions.assertEquals("demo", propertySearchResponse1.getLocation());
    }

    @Test
    public void findAllVerifiedProperties(){
        property.setPropertyId(1);
        property.setVerified(true);
        property.setLocation("ahmedabad");
        property.setType("flat");
        property.setPrice(5000000);
        property.setName("2bhk");
        Mockito.when(propertyRepository.findAllVerifiedProperties()).thenReturn(Collections.singletonList(propertySearchResponse));
        List<PropertySearchResponse> responses = propertyRepository.findAllVerifiedProperties();
        Assertions.assertEquals(1, responses.size());
    }
}