package com.api.keycloak.controller;

import com.api.keycloak.model.dto.UserDto;
import com.api.keycloak.model.request.UserRequest;
import com.api.keycloak.service.KeycloakService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user/")
@AllArgsConstructor
@SecurityRequirement(name = "oauth")
public class UserController {
    private final KeycloakService keycloakService;

    @GetMapping("currentUser")
    public UserDto currentUser(Principal principal) {
        return keycloakService.getCurrentUser(principal.getName());
    }

    @GetMapping(path = "{userName}")
    public UserDto getUser(@PathVariable("userName") String userName){
        return keycloakService.getUserByName(userName);
    }

    @PutMapping(path = "update/{userId}")
    public UserDto updateUser(@PathVariable("userId") String userId,   @RequestBody UserRequest userRequest){
        return keycloakService.updateUser(userId, userRequest);
    }

    @DeleteMapping(path = "{userId}")
    public String deleteUser(@PathVariable("userId") String userId){
        keycloakService.deleteUser(userId);
        return "User Deleted Successfully.";
    }
}
