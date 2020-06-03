package com.proz.vault.controllers;
import com.proz.vault.data.entities.Clinic;
import com.proz.vault.modles.Response;
import com.proz.vault.services.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author yubraj.singh
 */
@RestController
public class ClinicController {
    @Autowired
    private ClinicService clinicService;

    @PostMapping("/clinic")
    public ResponseEntity<Response> createClinic(@Valid @RequestBody Clinic clinic)
            throws Exception {
        Clinic createdClinic = clinicService.createClinic(clinic);
        return new ResponseEntity<Response>
                (new Response("added clinic successfully"
                        ,createdClinic),HttpStatus.OK);
    }
}
