package com.proz.vault.data.repository;

import com.proz.vault.data.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


/**
 * @author yubraj.singh
 */
@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
     @Query("SELECT a FROM users a WHERE a.email = :email")
     User getUserByMail(@Param("email") @NotEmpty @Email(message = "{errors.invalid_email}") String email);
}
