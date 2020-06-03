package com.proz.vault.controllers;
import com.proz.vault.data.entities.User;
import com.proz.vault.modles.AppointmentResponse;
import com.proz.vault.services.AppointmentService;
import com.proz.vault.services.ClinicService;
import com.proz.vault.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

/**
 * @author yubraj.singh
 */
@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ClinicService clinicService;

    @RequestMapping(value= {"/doctor/home"}, method=RequestMethod.GET)
    public ModelAndView doctorHomePage() {
        ModelAndView model = new ModelAndView();
        User user = userService.getUserByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName());
        int clinicId  = clinicService.getClinicByWorkerId(user.getId());
        List<AppointmentResponse> appointmentDataList = appointmentService.getAppointmentsByClinic(clinicId);
        model.addObject("appointments",appointmentDataList);
        model.setViewName("home/home_doctor");
        return model;
    }

    @RequestMapping(value= {"/patient/home"}, method=RequestMethod.GET)
    public ModelAndView patient() {
        ModelAndView model = new ModelAndView();
        User user = userService.getUserByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName());
        List<AppointmentResponse> appointmentDataList = appointmentService.getAppointmentsByUser(user.getId());
        model.addObject("appointments",appointmentDataList);
        model.setViewName("home/home_patient");
        return model;
    }

    @RequestMapping(value= {"/assistant/home"}, method=RequestMethod.GET)
     public ModelAndView assistant() {
        ModelAndView model = new ModelAndView();
        User user = userService.getUserByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName());
        int clinicId  = clinicService.getClinicByWorkerId(user.getId());
        List<AppointmentResponse> appointmentDataList = appointmentService.getAppointmentsByClinic(clinicId);
        model.addObject("appointments",appointmentDataList);
        model.setViewName("home/home_assistant");
        return model;
    }

}
