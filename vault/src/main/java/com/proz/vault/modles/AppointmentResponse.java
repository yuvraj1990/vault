package com.proz.vault.modles;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author yubraj.singh
 */
@Data
public class AppointmentResponse {
    private String clinicName;
    private String userName;
    private String email;
    private String address ;
    private String time;
    private int appointmentId;
    private int userId;
    private List<File>files;
}
