package com.ensa.blockchainApp.Business;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data @ToString @NoArgsConstructor
public class Reclamation implements Serializable {
    //@NotNull
    //private String id;

    private Person complainer;

    private Respondent complainee;
    @NotNull
    private String object;
    @NotNull
    private String reclamation;

    @Temporal ( TemporalType.DATE )
    private Date date;

    public Reclamation(String rec) {
        this.reclamation = rec;
    } // TODO(): Should be removed later for testing.
    public Reclamation(Person complainer, Respondent complainee, String object, String reclamation, Date date) {
        this.complainer = complainer;
        this.complainee = complainee;
        this.object = object;
        this.reclamation = reclamation;
        this.date = new Date();
    }

}
