package com.proz.vault.services;

import com.proz.vault.data.entities.Appointment;
import com.proz.vault.data.repository.AppointmentRepository;
import com.proz.vault.helpers.DateTimeHelper;
import com.proz.vault.modles.AppointmentRequest;
import com.proz.vault.modles.AppointmentResponse;
import com.proz.vault.modles.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yubraj.singh
 */
@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository repository;

    @Transactional
    public Appointment createAppointment(AppointmentRequest appointment,int userId,int clinicId) {
        Appointment appointmentData = new Appointment();
        appointmentData.setAppointmentDate(appointment.getDateTime());
        appointmentData.setClinicId(clinicId);
        appointmentData.setUserId(userId);
        appointmentData.setCreatedAt(DateTimeHelper.getCurrentTime());
        appointmentData.setUpdatedAt(DateTimeHelper.getCurrentTime());
        return repository.save(appointmentData);
        // return repository.save(appointment);
    }


    public List<AppointmentResponse> getAppointmentsByUser(int userId) {
        List<Appointment> appointments = repository.getAppointmentByUserId(userId);
        List<AppointmentResponse> appointmentDataList = new ArrayList<>();
        for (Appointment appointment:appointments) {
            AppointmentResponse data = new AppointmentResponse();
            data.setAddress(appointment.getClinic().getAddress());
            data.setEmail(appointment.getUser().getEmail());
            data.setClinicName(appointment.getClinic().getName());
            data.setUserName(appointment.getUser().getName());
            data.setTime(DateTimeHelper.formateDate(appointment.getAppointmentDate()));
            data.setFiles(appointment.getDocuments().stream().filter(document -> !document.getAccessRevoked())
                    .map(document -> new File(document.getFileName(),document.getS3path(),document.getId()))
                    .collect(Collectors.toList()));
            data.setUserId(appointment.getUserId());
            data.setAppointmentId(appointment.getAppointmentId());
            appointmentDataList.add(data);
        }
        return appointmentDataList;
    }

    @Transactional
    public List<AppointmentResponse> getAppointmentsByClinic(int clinicId) {
        List<Appointment> appointments = repository.getAppointmentByClinicId(clinicId);
        List<AppointmentResponse> appointmentDataList = new ArrayList<>();
        for (Appointment appointment:appointments) {
            AppointmentResponse data = new AppointmentResponse();
            if(appointment.getClinic()!=null) {
                data.setAddress(appointment.getClinic().getAddress());
                data.setEmail(appointment.getUser().getEmail());
                data.setClinicName(appointment.getClinic().getName());
            }
            if(appointment.getUser() !=null) {
                data.setUserName(appointment.getUser().getName());
            }
            data.setTime(DateTimeHelper.formateDate(appointment.getAppointmentDate()));
            if(appointment.getDocuments()!=null) {
                data.setFiles(appointment.getDocuments().stream().filter(document -> !document.getAccessRevoked())
                        .map(document -> new File(document.getFileName(), document.getS3path(), document.getId()))
                        .collect(Collectors.toList()));
            }
            data.setUserId(appointment.getUserId());
            data.setAppointmentId(appointment.getAppointmentId());
            appointmentDataList.add(data);
        }
        return appointmentDataList;
    }
}
