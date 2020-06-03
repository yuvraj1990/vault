package com.proz.vault.data.repository;

import com.proz.vault.data.entities.ClinicVsWorker;
import com.proz.vault.data.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author yubraj.singh
 */
public interface WorkerRepository extends CrudRepository<ClinicVsWorker,Integer> {
}
