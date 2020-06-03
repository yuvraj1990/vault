package com.proz.vault.data.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

/**
 * @author yubraj.singh
 */
@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @Column(nullable=false)

    @NotEmpty
    private String name;

    @Column(nullable=false, unique=true)
    @NotEmpty
    @Email(message="Not a valid email")
    private String email;

    @Column(nullable=false)
    @NotEmpty
    @Size(min=8,message = "Password must contain at least 8 character")
    private String password;

    @Column(nullable = false)
    @NotEmpty
    @Pattern(regexp="(^$|[0-9]{10})",message = "Not a valid mobile number")
    private String mobileNumber;

    @Column(nullable=false,name ="created_at" )
    @Temporal(TemporalType.DATE)
    private Date createdAt;


    @Column(nullable=false,name ="updated_at" )
    @Temporal(TemporalType.DATE)
    private Date updatedAt;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="user_role", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles;

    @Column(nullable = true,name = "active")
    private Integer active;

    @Transient
    private String role;

    @Transient
    private Integer clinicId;
}
