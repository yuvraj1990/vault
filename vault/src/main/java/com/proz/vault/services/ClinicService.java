package com.proz.vault.services;

import com.proz.vault.data.entities.Clinic;
import com.proz.vault.data.entities.ClinicVsWorker;
import com.proz.vault.data.repository.ClinicRepository;
import com.proz.vault.data.repository.WorkerRepository;
import com.proz.vault.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author yubraj.singh
 */
@Service
public class ClinicService {
    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private WorkerRepository workerRepository;

    public Clinic createClinic(Clinic clinic) {
        return clinicRepository.save(clinic);
    }

    public List<Clinic> getAllClinic() {
         List<Clinic> allClinics = new ArrayList<>();
         clinicRepository.findAll().forEach(clinic -> {
             allClinics.add(clinic);
         });
         return allClinics;
    }
    public int getClinicByWorkerId(int workerId) {
            Optional<ClinicVsWorker> worker = workerRepository.findById(workerId);
            if(!worker.isPresent()) {
                throw new RecordNotFoundException("worker doesnt work anywhere yet");
            }
            return worker.get().getClinicId(); }

}
