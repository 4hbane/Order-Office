package com.ensa.blockchainApp.Business;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data @AllArgsConstructor @NoArgsConstructor @ToString

public class Person implements Serializable {
    @NotNull
    private String civility; // Either Mr or Mrs.
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String cin;
    @NotNull
    private String email;
    @NotNull
    private String address;
    @NotNull
    private String phone;

    //@NotNull
    //private boolean anonymous = false;

}
