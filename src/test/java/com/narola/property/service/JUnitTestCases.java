package com.narola.property.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.narola.property.dao.PropertyCriteria;
import com.narola.property.entity.Property;
import com.narola.property.entity.Role;
import com.narola.property.entity.User;
import com.narola.property.model.AddUpdatePropertyRequest;
import com.narola.property.model.PropertySearchRequest;
import com.narola.property.model.PropertySearchResponse;
import com.narola.property.repository.PropertyRepository;
import com.narola.property.repository.UserRepository;
import com.narola.property.security.UserUtils;
import com.narola.property.service.PropertyService;

import com.narola.property.serviceimpl.PropertyServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.DriverManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

//@SpringBootTest
@RunWith(PowerMockRunner.class)
@PrepareForTest(UserUtils.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class JUnitTestCases {

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PropertyCriteria propertyCriteria;

    @InjectMocks
    PropertyServiceImpl propertyService;

    Property property = new Property();
    User user = new User();


    @Test
    public void add(){
        user.setUserName("demo");
        user.setPassword("demo");
        user.setUserId(10);
        user.setEnabled(true);
        property.setUser(user);
        PowerMockito.mockStatic(UserUtils.class);
        BDDMockito.given(UserUtils.getLoggedInUserName()).willReturn("landlord");

        Mockito.when(UserUtils.getLoggedInUserName()).thenReturn("landlord");
//        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.getUserByUsername(UserUtils.getLoggedInUserName())).thenReturn(user);

        PowerMockito.verifyStatic(UserUtils.class);
        UserUtils.getLoggedInUserName();

        AddUpdatePropertyRequest addUpdatePropertyRequest = new AddUpdatePropertyRequest(property.getPropertyId()
                , property.getName(), property.getType(), property.getPrice(), property.getLocation(), property.isVerified()
                , property.getUser().getUserId());

        propertyService.addProperty(addUpdatePropertyRequest);
        Assertions.assertEquals("demo", property.getUser().getUserName());
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
        Mockito.when(propertyRepository.findById(Mockito.any())).thenReturn(Optional.of(property));
        Mockito.when(propertyRepository.save(Mockito.any())).thenReturn(property);
        propertyService.updateProperty(propertyRequest);
        Assertions.assertEquals("ahmedabad", propertyRequest.getLocation());
    }

    @Test
    public void approve(){
        Mockito.when(propertyRepository.findById(Mockito.any())).thenReturn(Optional.of(property));
        property.setVerified(true);
        Mockito.when(propertyRepository.save(Mockito.any())).thenReturn(property);
        propertyService.verifyProperty(6);
        Assertions.assertEquals(true, property.isVerified());
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

        Mockito.when(propertyCriteria.getPropertiesWithSearch(request.getSortBy(), request.getSortOrder(), request.getPageSize()
                        , request.getKeyword(), request.getPropertyId(), request.getPageNo()))
                .thenReturn(Collections.singletonList(property));
        List<PropertySearchResponse> list = propertyService.getAllProperties(request);
        Assertions.assertEquals(1, list.size());
    }

    @Test
    public void getById(){
        Property property = new Property();
        User user = new User();
        Role role = new Role();

        role.setName("demo");
        role.setRoleId(4);
        role.setUsers(Collections.singletonList(user));

        user.setUserName("demo");
        user.setPassword("demo");
        user.setUserId(10);
        user.setEnabled(true);
        user.setProperties(Collections.singletonList(property));
        user.setRoles(Collections.singletonList(role));

        property.setPrice(5000000);
        property.setVerified(true);
        property.setType("bunglow");
        property.setLocation("ahmedabad");
        property.setPropertyId(10);
        property.setUser(user);

        Mockito.when(propertyRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(property));
        propertyService.getPropertyById(10);
        Assertions.assertEquals(10, property.getPropertyId());
    }
}