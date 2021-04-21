package com.narola.property.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.narola.property.entity.Property;
import com.narola.property.entity.User;
import com.narola.property.model.AddUpdatePropertyRequest;
import com.narola.property.model.PropertySearchRequest;
import com.narola.property.model.PropertySearchResponse;
import com.narola.property.model.PropertySearchResponseWrapper;
import com.narola.property.repository.UserRepository;
import com.narola.property.security.JwtUtil;
import com.narola.property.security.MyEntryPoint;
import com.narola.property.security.MyUserDetailsServiceImpl;
import com.narola.property.service.PropertyService;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest(UserUtils.class)
//@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
@WebMvcTest
@AutoConfigureMockMvc
//@RunWith(SpringRunner.class)
//@MockBean({PropertyService.class, MyUserDetailsServiceImpl.class, JwtUtil.class, MyEntryPoint.class, UserRepository.class})
public class JUnitControllerTestCases {
    @MockBean
    private PropertyService propertyService;
    @MockBean
    private MyUserDetailsServiceImpl myUserDetailsService;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private MyEntryPoint entryPoint;
    @MockBean
    private UserRepository userRepository;

//    @InjectMocks
//    PropertyController propertyController;

    @Autowired
    private MockMvc mockMvc;


    Property property = null;
    User user = null;
    PropertySearchResponse propertySearchResponse = null;
    AddUpdatePropertyRequest propertyRequest = null;
    PropertySearchRequest request = null;
    ObjectMapper objectMapper = null;
    String jsonString;

    @org.junit.jupiter.api.Test
    public void add() throws Exception {
        user = new User();
        property = new Property();

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

        objectMapper = new ObjectMapper();
        jsonString = objectMapper.writeValueAsString(property);

        propertyRequest = new AddUpdatePropertyRequest(property.getPropertyId()
                , property.getName(), property.getType(), property.getPrice(), property.getLocation(), property.isVerified()
                , property.getUser().getUserId());
        Mockito.when(propertyService.addProperty(propertyRequest)).thenReturn(property);
//        Property property1 = propertyController.addProperty(propertyRequest);
//        Assertions.assertEquals("ahmedabad", property1.getLocation());
        mockMvc.perform(MockMvcRequestBuilders.post("/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(property.getName())))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void update() throws Exception {
        propertyRequest = new AddUpdatePropertyRequest();
        propertyRequest.setUserId(10);
        propertyRequest.setVerified(true);
        propertyRequest.setPrice(5000000);
        propertyRequest.setName("4bhk");
        propertyRequest.setLocation("ahmedabad");
        propertyRequest.setType("bunglow");
        propertyRequest.setId(10);

        objectMapper = new ObjectMapper();
        jsonString = objectMapper.writeValueAsString(propertyRequest);

        Mockito.when(propertyService.updateProperty(Mockito.any())).thenReturn(property);
        mockMvc.perform(MockMvcRequestBuilders.put("/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(propertyRequest.getName())))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void approve() throws Exception {
        property = new Property();
        property.setPropertyId(1);
        property.setLocation("ahmedabad");
        property.setVerified(false);
        property.setType("bunglow");
        property.setPrice(7000000);
        property.setName("4bhk");

        objectMapper = new ObjectMapper();
        jsonString = objectMapper.writeValueAsString(propertyRequest);

        PropertyController propertyController = Mockito.mock(PropertyController.class);
        Mockito.doNothing().when(propertyController).verifyProperty(property.getPropertyId());
        mockMvc.perform(MockMvcRequestBuilders.put("/properties/{id}", property.getPropertyId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(property.getName())))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void search() throws Exception {
        property = new Property();
        property.setPropertyId(1);
        property.setLocation("ahmedabad");
        property.setVerified(false);
        property.setType("bunglow");
        property.setPrice(7000000);
        property.setName("4bhk");

        request = new PropertySearchRequest();
        request.setKeyword("ahmedabad");
        request.setPageNo(1);
        request.setPageSize(5);
        request.setSortBy("type");
        request.setSortOrder("ASC");
        request.setPropertyId(1);

        propertySearchResponse = new PropertySearchResponse();
        propertySearchResponse.setId(property.getPropertyId());
        propertySearchResponse.setLocation(property.getLocation());
        propertySearchResponse.setVerified(property.isVerified());
        propertySearchResponse.setType(property.getType());
        propertySearchResponse.setName(property.getName());
        propertySearchResponse.setPrice(property.getPrice());

        List<PropertySearchResponse> propertySearchResponseList = new ArrayList<>();
        objectMapper = new ObjectMapper();
        jsonString = objectMapper.writeValueAsString(request);

        Mockito.when(propertyService.getAllProperties(request)).thenReturn(propertySearchResponseList);
        mockMvc.perform(MockMvcRequestBuilders.post("/properties/_list")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(request.getKeyword())))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void getById() throws Exception {
        property = new Property();
        propertySearchResponse = new PropertySearchResponse();

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

        objectMapper = new ObjectMapper();
        jsonString = objectMapper.writeValueAsString(propertyRequest);

        Mockito.when(propertyService.getPropertyById(property.getPropertyId())).thenReturn(propertySearchResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/properties/{id}", propertySearchResponse.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(propertySearchResponse.getName())))
                .andReturn().getResponse().getContentAsString();
    }
}