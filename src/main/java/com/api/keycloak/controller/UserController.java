package com.api.keycloak.controller;

import com.api.keycloak.model.request.UserRequest;
import com.api.keycloak.service.KeycloakService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/")
@AllArgsConstructor
@SecurityRequirement(name = "oauth")
public class UserController {
    private final KeycloakService keycloakService;

    @GetMapping(path = "{userName}")
    public List<UserRepresentation> getUser(@PathVariable("userName") String userName){
        return keycloakService.getUser(userName);
    }

    @PutMapping(path = "/update/{userId}")
    public String updateUser(@PathVariable("userId") String userId,   @RequestBody UserRequest userRequest){
        keycloakService.updateUser(userId, userRequest);
        return "User Details Updated Successfully.";
    }

    @DeleteMapping(path = "/{userId}")
    public String deleteUser(@PathVariable("userId") String userId){
        keycloakService.deleteUser(userId);
        return "User Deleted Successfully.";
    }
}
