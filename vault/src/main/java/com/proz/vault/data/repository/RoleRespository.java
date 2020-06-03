package com.proz.vault.data.repository;

import com.proz.vault.data.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * @author yubraj.singh
 */
public interface RoleRespository  extends CrudRepository<Role, Integer> {
    Role findByRole(String role);
}
