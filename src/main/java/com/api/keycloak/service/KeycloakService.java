package com.api.keycloak.service;

import com.api.keycloak.model.dto.UserDto;
import com.api.keycloak.model.request.PasswordRequest;
import com.api.keycloak.model.request.UserRequest;
import com.api.keycloak.security.Credentials;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakService {
    private final Keycloak keycloak;
    private final ModelMapper modelMapper;

    @Value("${keycloak.realm}")
    private String realm;

    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }

    private CredentialRepresentation getCredentialRepresentation(String password){
        return Credentials.createPasswordCredentials(password);
    }
    private UserRepresentation credential(UserRequest userRequest) {
        UserRepresentation user = new UserRepresentation();

        user.setUsername(userRequest.getUserName());
        user.setFirstName(userRequest.getFirstname());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());

        user.setCredentials(Collections.singletonList(getCredentialRepresentation(userRequest.getPassword())));

        return user;
    }

    public UserDto addUser(UserRequest userRequest) {
        UserRepresentation user = credential(userRequest);
        user.setEnabled(true);

        Response response = getUsersResource().create(user);
        UserRepresentation userRepresentation = getUsersResource()
                .get(CreatedResponseUtil.getCreatedId(response))
                .toRepresentation();

        return modelMapper.map(userRepresentation, UserDto.class);
    }

    public UserDto getCurrentUser(String userId) {
        UserRepresentation userRepresentation = getUsersResource().get(userId).toRepresentation();

        return modelMapper.map(userRepresentation, UserDto.class);
    }

    public UserDto getUserByName(String userName) {
        UserRepresentation user = getUsersResource().searchByUsername(userName, true).getFirst();

        return modelMapper.map(user, UserDto.class);
    }

    public UserDto updateUser(String userId, UserRequest userRequest) {
        UserRepresentation user = credential(userRequest);
        getUsersResource().get(userId).update(user);

        return modelMapper.map(getUsersResource().get(userId).toRepresentation(), UserDto.class);
    }

    public void deleteUser(String userId) {
        getUsersResource().get(userId).remove();
    }

    public void sendEmailVerification(String userId) {
       getUsersResource().get(userId).sendVerifyEmail();
    }

    public void sendResetPassword(String userId) {
        getUsersResource().get(userId)
                .executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }

    public void resetPassword(String userId, PasswordRequest passwordRequest) {
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(passwordRequest.getPassword());

        getUsersResource().get(userId)
                .resetPassword(credential);
    }
}
