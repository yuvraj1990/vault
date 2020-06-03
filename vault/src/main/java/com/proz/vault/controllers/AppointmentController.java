package com.proz.vault.controllers;

import com.proz.vault.data.entities.User;
import com.proz.vault.modles.AppointmentRequest;
import com.proz.vault.services.AppointmentService;
import com.proz.vault.services.ClinicService;
import com.proz.vault.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author yubraj.singh
 */
@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClinicService clinicService;

    @GetMapping("/appointment")
    public ModelAndView getAppiontment(Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        if(model.containsAttribute("appointment")){
           modelAndView.addObject(model.getAttribute("appointment"));
        }
        else {
            AppointmentRequest appointment = new AppointmentRequest();
            modelAndView.addObject("appointment", appointment);
        }
        modelAndView.setViewName("user/appointment");
        return  modelAndView;
    }

    @RequestMapping(value= {"/appointment"}, method=RequestMethod.POST)
    public String createAppointment(@Valid AppointmentRequest appointment, BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        try {
            User user = userService.getUserByEmail(appointment.getEmail());
            if(user == null) {
                bindingResult.rejectValue("email", "error.user", "This email does not exists!");
            }
            if(bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("appointment",appointment);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.appointment",bindingResult);
                return "redirect:/appointment";
            }
            User loggedInUser = userService.getUserByEmail((SecurityContextHolder.getContext()
                    .getAuthentication().getName()));
            int clinicId = clinicService.getClinicByWorkerId(loggedInUser.getId());
            appointmentService.createAppointment(appointment,user.getId(),clinicId);
        }
        catch (Throwable ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("msg", "Something went wrong please try again");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/appointment";
        }
        redirectAttributes.addFlashAttribute("message", "Appointment successfully created");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/assistant/home";
    }
}
