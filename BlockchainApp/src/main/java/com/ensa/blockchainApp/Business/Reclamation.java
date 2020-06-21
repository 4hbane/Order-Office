package com.ensa.blockchainApp.Business;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Data @ToString @NoArgsConstructor
public class Reclamation implements Serializable {
    @NotNull
    @Column(unique = true)
    private String orderNumber;
    @NotNull
    private Person complainer;
    @NotNull
    private Respondent complainee;
    @NotNull
    private String object;

    @Lob
    private byte[] reclamation;

    @Temporal ( TemporalType.DATE )
    private Date date;

    // TODO(): Need to change rec to take bytes.
    public Reclamation(byte[] rec) {
        this.reclamation = rec;
    }
    public Reclamation(Person complainer, Respondent complainee, String object, byte[] reclamation, Date date) {
        this.complainer = complainer;
        this.complainee = complainee;
        this.object = object;
        this.reclamation = reclamation;
        this.date = new Date();
    }

}
