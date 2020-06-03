package com.proz.vault.data.repository;

import com.proz.vault.data.entities.Clinic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yubraj.singh
 */

@Repository
public interface ClinicRepository extends CrudRepository<Clinic,Integer> {
}
