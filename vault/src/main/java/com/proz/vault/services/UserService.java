package com.proz.vault.services;

import com.proz.vault.data.entities.Appointment;
import com.proz.vault.data.entities.ClinicVsWorker;
import com.proz.vault.data.entities.Role;
import com.proz.vault.data.entities.User;
import com.proz.vault.data.repository.AppointmentRepository;
import com.proz.vault.data.repository.RoleRespository;
import com.proz.vault.data.repository.UserRepository;
import com.proz.vault.data.repository.WorkerRepository;
import com.proz.vault.helpers.DateTimeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

/**
 * @author yubraj.singh
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRespository roleRespository;

    @Autowired
    private WorkerRepository workerRepository;

    @Transactional
    public User signUp(User user) {
        user.setUpdatedAt(DateTimeHelper.getCurrentTime());
        user.setCreatedAt(DateTimeHelper.getCurrentTime());
        user.setActive(1);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRespository.findByRole(user.getRole());
        user.setRoles(new HashSet <>(Arrays.asList(userRole)));
        User createdUser=  userRepository.save(user);
        if(!userRole.getRole().equals("Patient")) {
            ClinicVsWorker clinicVsWorker = new ClinicVsWorker();
            clinicVsWorker.setWorkerId(createdUser.getId());
            clinicVsWorker.setClinicId(user.getClinicId());
            workerRepository.save(clinicVsWorker);
        }
        return user;
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByMail(email);
    }

}
