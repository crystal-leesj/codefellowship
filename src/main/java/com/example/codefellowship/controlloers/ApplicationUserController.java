package com.example.codefellowship.controlloers;

import com.example.codefellowship.models.ApplicationUser;
import com.example.codefellowship.models.ApplicationUserRepository;
import com.example.codefellowship.models.Post;
import com.example.codefellowship.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

@Controller
public class ApplicationUserController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public RedirectView createNewApplicationUser(String username, String password, String firstName, String lastName, String dateOfBirth, String bio){
        // make the user AND salt and hash the password
        // this does the salting and hashing for you
        ApplicationUser newUser = new ApplicationUser(username, passwordEncoder.encode(password), firstName, lastName, dateOfBirth, bio);

        // save the user to db
        applicationUserRepository.save(newUser);

        // send them back home
        return new RedirectView("/");
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }


    @GetMapping("/myprofile")
    public String getMyprofile(Principal p, Model m){
        ApplicationUser theUser = applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("user", theUser);
        m.addAttribute("principle", p.getName());
        return "myprofile";
    }

    @GetMapping("/users/{id}")
    public String showUserDetails(@PathVariable long id, Principal p, Model m){
        if (p == null) {
            return "login";
        } else {
            ApplicationUser theUser = applicationUserRepository.findById(id).get();
            // set the attribute on Model
            m.addAttribute("usernameWeAreVisiting", theUser.getUsername());
            m.addAttribute("userIdWeAreVisiting", theUser.id);
            m.addAttribute("userWeAreVisiting", theUser);
            m.addAttribute("principle", p.getName());

            m.addAttribute("user_firstName", theUser.getFirstName());
            m.addAttribute("user_lastName", theUser.getLastName());
            m.addAttribute("user_dateOfBirth", theUser.getDateOfBirth());
            m.addAttribute("user_bio", theUser.getBio());

            return "userDetails";
        }
    }

    @PostMapping("/follow")
    public RedirectView follow(long id, Principal p) {
        ApplicationUser userToFollow = applicationUserRepository.findById(id).get();
        ApplicationUser loggedInUser = applicationUserRepository.findByUsername(p.getName());
        if (loggedInUser == userToFollow) {
            System.out.println("You cannot follow yourself!");
        } else {
            loggedInUser.follow(userToFollow);
            applicationUserRepository.save(loggedInUser);
            System.out.println("-----------SAVE!");
        }
        return new RedirectView("/users/" + id);
    }
}
