package com.proz.vault.data.repository;

import com.proz.vault.data.entities.Document;
import com.proz.vault.data.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yubraj.singh
 */
@Repository
public interface DocumentRepository extends CrudRepository<Document,Integer> {

    @Modifying(clearAutomatically = true)
    @Query("update document a set a.accessRevoked = :accessRevoked WHERE a.appointmentId = :appointmentId")
    void revokeAcessForAppointment(@Param("appointmentId") int appointmentId,@Param("accessRevoked")boolean accessRevoked);

    @Query("SELECT a FROM document a WHERE a.appointmentId = :appointmentId")
    List<Document> getDocsByAppointment(@Param("appointmentId")  int appointmentId);
}
