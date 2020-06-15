package com.ensa.blockchainApp.Business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @AllArgsConstructor @NoArgsConstructor @ToString

//This class is made for creating a new user in keycloak server !
public class User {

    private String Id;
    private String userName;
    private String firstName;
    private String lastName;
    private String type;
    private String credentials;
    private String email;
    private boolean enabled;
    private String password;

}
