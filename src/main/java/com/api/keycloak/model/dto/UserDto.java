package com.api.keycloak.model.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String userName;
    private String emailId;
    private String firstname;
    private String lastName;
}
