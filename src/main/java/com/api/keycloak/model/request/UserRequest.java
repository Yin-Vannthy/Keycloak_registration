package com.api.keycloak.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {
    private String userName;
    private String email;
    private String password;
    private String firstname;
    private String lastName;
}
