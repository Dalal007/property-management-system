package com.narola.property.controller;

import com.narola.property.entity.Property;
import com.narola.property.model.*;
import com.narola.property.security.JwtUtil;
import com.narola.property.security.MyUserDetailsServiceImpl;
import com.narola.property.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/properties")
public class PropertyController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private MyUserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<?> login(@RequestBody TokenGenerationRequest tokenGenerationRequest) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(tokenGenerationRequest.getUserName()
                        , tokenGenerationRequest.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(tokenGenerationRequest.getUserName());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new TokenGenerationResponse(jwt));
    }

    @GetMapping
    public List<PropertySearchResponse> listOFVerifiedProperties(){
        return propertyService.getAllVerifiedProperties();
    }

    @GetMapping("/{id}")
    public PropertySearchResponse getById(@PathVariable("id") int propertyId){
        PropertySearchResponse property = propertyService.getPropertyById(propertyId);
        if (!property.isVerified()){
            return null;
        }
        return propertyService.getPropertyById(propertyId);
    }

//    @GetMapping("/list")
//    public List<Property> getAllProperties(){
//        return userService.getAllProperties();
//    }

    @PostMapping("/_list")
    public ResponseEntity<PropertySearchResponseWrapper> getAllProperties(@RequestBody(required = false) PropertySearchRequest request){
        List<PropertySearchResponse> responses;
        if (request==null){
            responses = propertyService.getAllProperties(new PropertySearchRequest());
        } else {
            responses = propertyService.getAllProperties(request);
        }
        return ResponseEntity.ok(new PropertySearchResponseWrapper(responses));
    }

    @PutMapping("/{id}")
    public void verifyProperty(@PathVariable("id") int propertyId){
        propertyService.verifyProperty(propertyId);
    }

    @PostMapping
    public Property addProperty(@RequestBody AddUpdatePropertyRequest request){
        return propertyService.addProperty(request);
    }

    @PutMapping
    public Property updateProperty(@RequestBody AddUpdatePropertyRequest request){
        return propertyService.updateProperty(request);
    }
}
