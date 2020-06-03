package com.proz.vault.data.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author yubraj.singh
 */
@Data
@Entity(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @NotEmpty
    @Column(nullable = false)
    private String s3path;

    @NotEmpty
    @Column(nullable = false,name = "file_name")
    private String fileName;

    @Column(nullable=false,name = "user_id")
    @NotNull
    private Integer userId;

    @Column(nullable=false,name = "appointment_id")
    @NotNull
    private Integer appointmentId;

    @Column(nullable=false,name = "file_content")
    @Lob
    private byte[] fileContent;

    @Column(nullable=false,name ="created_at" )
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Column(nullable=false,name = "access_revoked")
    @NotNull
    private Boolean accessRevoked;

}
