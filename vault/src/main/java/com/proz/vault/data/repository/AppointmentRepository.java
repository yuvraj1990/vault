package com.proz.vault.data.repository;

import com.proz.vault.data.entities.Appointment;
import com.proz.vault.data.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author yubraj.singh
 */
@Repository
public interface AppointmentRepository extends CrudRepository<Appointment,Integer> {
    @Query("SELECT a FROM appointment a WHERE a.userId = :userId")
    List<Appointment> getAppointmentByUserId(@Param("userId") int userId);

    @Query("SELECT a FROM appointment a WHERE a.clinicId = :clinicId")
    List<Appointment> getAppointmentByClinicId(@Param("clinicId") int clinicId);
}
