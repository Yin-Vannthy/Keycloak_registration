package com.api.keycloak.controller;

import com.api.keycloak.model.dto.UserDto;
import com.api.keycloak.model.request.PasswordRequest;
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
    public UserDto addUser(@RequestBody UserRequest userRequest){
        return keycloakService.addUser(userRequest);
    }

    @PutMapping("send-email-verification/{userId}")
    public String sendEmailVerification(@PathVariable("userId") String userId){
        keycloakService.sendEmailVerification(userId);
        return "verification sent Successfully.";
    }

    @PutMapping("send-reset-password-by-link/{userId}")
    public String sendResetPassword(@PathVariable("userId") String userId){
        keycloakService.sendResetPassword(userId);
        return "Reset Password Link Send Successfully to Registered E-mail Id.";
    }

    @PutMapping("reset-password/{userId}")
    public String resetPassword(@PathVariable("userId") String userId, @RequestBody PasswordRequest passwordRequest){
        keycloakService.resetPassword(userId, passwordRequest);
        return "Reset Password Successfully.";
    }
}
