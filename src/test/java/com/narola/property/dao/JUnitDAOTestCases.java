package com.narola.property.dao;

import com.narola.property.entity.Property;
import com.narola.property.model.PropertySearchRequest;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {PropertyDAOImpl.class, EntityManager.class, EntityManagerFactory.class})
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class JUnitDAOTestCases {

    @Mock
    private PropertyDAOImpl propertyDAOImpl = new PropertyDAOImpl();

//    @MockBean
//    private EntityManager entityManager;
//
//    @MockBean
//    private EntityManagerFactory entityManagerFactory;

    PropertySearchRequest request = new PropertySearchRequest();
    Property property = new Property();
    @Test
    public void getPropertiesWithSearch(){
        request.setSortBy("type");
        request.setPropertyId(1);
        request.setKeyword("ahme");
        List<Property> properties = Arrays.asList(property);

        Mockito.when(propertyDAOImpl.getPropertiesWithSearch(request.getSortBy()
                , request.getSortOrder(), request.getPageSize(), request.getKeyword()
                , request.getPropertyId(), request.getPageNo())).thenReturn(properties);
        List<Property> propertyList = propertyDAOImpl.getPropertiesWithSearch(request.getSortBy()
                , request.getSortOrder(), request.getPageSize(), request.getKeyword()
                , request.getPropertyId(), request.getPageNo());
        Assertions.assertEquals(properties.size(), propertyList.size());
    }
}