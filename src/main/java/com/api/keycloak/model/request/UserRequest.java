package com.api.keycloak.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {
    private String userName;
    private String emailId;
    private String password;
    private String firstname;
    private String lastName;
}
