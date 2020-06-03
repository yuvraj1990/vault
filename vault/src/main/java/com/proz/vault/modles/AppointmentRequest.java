package com.proz.vault.modles;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author yubraj.singh
 */
@Data
public class AppointmentRequest {
    @NotEmpty
    private String email;
    @NotEmpty
    @Pattern(regexp="(^$|[0-9]{10})",message = "Not a valid mobile number")
    private String mobileNumber;
    @NotNull(message = "Date Time required !!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date dateTime;
}
