package com.ensa.blockchainApp.KeyCloak;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @AllArgsConstructor @NoArgsConstructor @ToString

//This class is made for getting the data we need from a user who is already in keycloak server !
public class keyCloakUser {
    private String Id;
    private String userName;
    private String firstName;
    private String lastName;
    private String type;
    private String email;
}
