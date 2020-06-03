package com.proz.vault.data.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;


/**
 * @author yubraj.singh
 */
@Data
@Entity(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "appointment_id")
    private Integer appointmentId;

    @Column(nullable=false,name = "user_id")
    @NotNull
    private Integer userId;

    @Column(nullable=false,name = "clinic_id")
    @NotNull
    private Integer clinicId;


    @Column(nullable=false,name ="appontmnet_date" )
    @Temporal(TemporalType.DATE)
    private Date appointmentDate;

    @Column(nullable=false,name ="created_at" )
    @Temporal(TemporalType.DATE)
    private Date createdAt;


    @Column(nullable=false,name ="updated_at" )
    @Temporal(TemporalType.DATE)
    private Date updatedAt;

    @OneToOne
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "clinic_id",insertable = false,updatable = false)
    private Clinic clinic;

    @OneToMany
    @JoinColumn(name = "appointment_id",insertable = false,updatable = false)
    private Set<Document> documents;
}
