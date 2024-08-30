package com.api.keycloak.controller;

import com.api.keycloak.model.request.UserRequest;
import com.api.keycloak.service.KeycloakService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authentication/")
@AllArgsConstructor
public class AuthController {
    private final KeycloakService keycloakService;

    @PostMapping("register")
    public String addUser(@RequestBody UserRequest userRequest){
        keycloakService.addUser(userRequest);
        return "User Added Successfully.";
    }

    @GetMapping("/verification-link/{userId}")
    public String sendVerificationLink(@PathVariable("userId") String userId){
        keycloakService.sendVerificationLink(userId);
        return "Verification Link Send to Registered E-mail Id.";
    }

    @GetMapping("/reset-password/{userId}")
    public String sendResetPassword(@PathVariable("userId") String userId){
        keycloakService.sendResetPassword(userId);
        return "Reset Password Link Send Successfully to Registered E-mail Id.";
    }
}
