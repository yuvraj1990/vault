package com.proz.vault.controllers;

import com.proz.vault.data.entities.Clinic;
import com.proz.vault.data.entities.User;
import com.proz.vault.services.ClinicService;
import com.proz.vault.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * @author yubraj.singh
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClinicService clinicService;


    @RequestMapping(value= {"vault/signup"}, method=RequestMethod.POST)
    public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();
        User userExists = userService.getUserByEmail(user.getEmail());
        List<Clinic> clinics = clinicService.getAllClinic();
        if(user.getRole().equals("Patient") && !user.getClinicId().equals(0)) {
            bindingResult.rejectValue("clinicId", "error.user", "Patient not allowed to select clinic");
        }
        else if(!user.getRole().equals("Patient") && user.getClinicId().equals(0)){
            bindingResult.rejectValue("clinicId", "error.user", "Please select your clinic");
        }
        if(userExists != null) {
           bindingResult.rejectValue("email", "error.user", "This email already exists!");
        }
        if(bindingResult.hasErrors()) {
            model.addObject("clinics", clinics);
            model.setViewName("user/signup");
       } else {
            User createdUser = userService.signUp(user);
            model.addObject("msg", "User has been registered successfully!");
            model.addObject("clinics", clinics);
            model.addObject("user", new User());
            model.setViewName("user/signup");
        }
        return model;
    }

    @RequestMapping(value= {"/vault/signup"}, method=RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView model = new ModelAndView();
        List<Clinic> clinics = clinicService.getAllClinic();
        User user = new User();
        model.addObject("user", user);
        model.addObject("clinics", clinics);
        model.setViewName("user/signup");
        return model;
    }

    @RequestMapping(value= {"/vault", "vault/login"}, method=RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView model = new ModelAndView();
        model.setViewName("user/login");
        return model;
    }

}
