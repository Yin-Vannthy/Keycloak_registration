package com.api.keycloak.service;

import com.api.keycloak.model.request.UserRequest;
import com.api.keycloak.security.Credentials;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakService {
    private final Keycloak keycloak;
    
    @Value("${keycloak.realm}")
    private String realm;
    
    private UserRepresentation credential(UserRequest userDTO) {
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(userDTO.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstname());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmailId());
        user.setCredentials(Collections.singletonList(credential));
        return user;
    }

    public void addUser(UserRequest userRequest) {
        UserRepresentation user = credential(userRequest);
        user.setEnabled(true);

        UsersResource usersResource = keycloak.realm(realm).users();
        usersResource.create(user);
    }

    public List<UserRepresentation> getUser(String userName) {
        UsersResource usersResource = keycloak.realm(realm).users();
        return usersResource.search(userName, true);
    }

    public void updateUser(String userId, UserRequest userDTO) {
        UserRepresentation user = credential(userDTO);

        UsersResource usersResource = keycloak.realm(realm).users();
        usersResource.get(userId).update(user);
    }

    public void deleteUser(String userId) {
        UsersResource usersResource = keycloak.realm(realm).users();
        usersResource.get(userId).remove();
    }

    public void sendVerificationLink(String userId) {
        UsersResource usersResource = keycloak.realm(realm).users();
        usersResource.get(userId).sendVerifyEmail();
    }

    public void sendResetPassword(String userId) {
        UsersResource usersResource = keycloak.realm(realm).users();
        usersResource.get(userId)
                .executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }
}
