package com.ensa.blockchainApp.Business;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Traceability {
    @Id
    private Long id;
    @NotNull
    private String userName;
    @NotNull
    @Temporal ( TemporalType.DATE )
    private Date date;
}
