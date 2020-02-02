package com.example.codefellowship.controlloers;

import com.example.codefellowship.models.ApplicationUser;
import com.example.codefellowship.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @GetMapping("/")
    public String getHome(Principal p, Model m){

        if(p != null){
            m.addAttribute("username", p.getName());
        }

        ApplicationUser loggedInUser = applicationUserRepository.findByUsername(p.getName());
        long loggedInUserId = loggedInUser.id;

        List<ApplicationUser> users = applicationUserRepository.findAll();
        // Remove logged in users name from the users list
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).id == loggedInUserId) {
                users.remove(i);
            }
        }
        m.addAttribute("users", users);

        return "home";
    }
}
