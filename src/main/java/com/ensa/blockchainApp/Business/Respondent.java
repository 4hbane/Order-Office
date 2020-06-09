package com.ensa.blockchainApp.Business;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data @NoArgsConstructor @AllArgsConstructor @ToString

public class Respondent implements Serializable {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String adress;

 }
