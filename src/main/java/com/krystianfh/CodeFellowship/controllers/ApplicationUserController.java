package com.krystianfh.codefellowship.controllers;


import com.krystianfh.codefellowship.models.user.ApplicationUser;
import com.krystianfh.codefellowship.models.user.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/newuser")
    public RedirectView makeNewUser(String username, String password){
        password = passwordEncoder.encode(password);

        ApplicationUser newUser = new ApplicationUser(username, password);

        return new RedirectView("/");

    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
