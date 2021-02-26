package com.ensa.blockchainApp.Business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @ToString
public class ReportedProblem {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String userName;
    @NotNull
    private String object;
    @NotNull
    private String message;
    @NotNull
    @Temporal( TemporalType.DATE )
    private Date date;
}
