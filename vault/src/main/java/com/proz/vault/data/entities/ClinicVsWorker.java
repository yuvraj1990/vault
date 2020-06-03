package com.proz.vault.data.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author yubraj.singh
 */
@Data
@Entity(name = "clinic_Vs_worker")
public class ClinicVsWorker {

    @Id
    @NotNull
    @Column(name = "worker_id")
    private Integer workerId;

    @NotNull
    @Column(name = "clinic_id")
    private Integer clinicId;

}
