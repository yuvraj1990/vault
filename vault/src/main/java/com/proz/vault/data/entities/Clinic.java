package com.proz.vault.data.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author yubraj.singh
 */
@Data
@Entity(name = "clinics")
public class Clinic {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @Column(nullable=false)

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    @Column(nullable=false, unique=true,name = "clinic_mail")
    @NotEmpty
    @Email(message="{errors.invalid_email}")
    private String clinicMail;

    @NotEmpty
    @Size(min = 10,max = 10)
    @Column(nullable = false,name = "clinic_number")
    private String clinicNumber;

}
