package com.krystianfh.codefellowship.controllers;


import com.krystianfh.codefellowship.models.user.ApplicationUser;
import com.krystianfh.codefellowship.models.user.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/newuser")
    public RedirectView makeNewUser(@RequestParam String username, @RequestParam String password, @RequestParam String firstName, @RequestParam String lastName, @RequestParam int dateOfBirth, @RequestParam String bio){
        password = passwordEncoder.encode(password);

        ApplicationUser newUser = new ApplicationUser(username, password, firstName, lastName, dateOfBirth, bio);
        applicationUserRepository.save(newUser);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(newUser, null, newUser.getAuthorities()); // look up in Spring docs
        SecurityContextHolder.getContext().setAuthentication(authToken);

        return new RedirectView("/myaccount");

    }

    @GetMapping("/myaccount")
    public String getOneUser(Principal p, Model m){
        try{
            ApplicationUser loggedInUser = applicationUserRepository.findByUsername(p.getName());
            if(loggedInUser != null){
                m.addAttribute("singleUser", loggedInUser);
                return "singleUser";
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
