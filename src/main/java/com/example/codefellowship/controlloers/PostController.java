package com.example.codefellowship.controlloers;

import com.example.codefellowship.models.ApplicationUser;
import com.example.codefellowship.models.ApplicationUserRepository;
import com.example.codefellowship.models.Post;
import com.example.codefellowship.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class PostController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @PostMapping("/writePost")
    public RedirectView createAPost(Principal p, String body, long id){
        System.out.println("----------------writePOST");
        ApplicationUser writer = applicationUserRepository.findByUsername(p.getName());
        // save a post
        Post newPost = new Post(writer, body);
        postRepository.save(newPost);
        return new RedirectView("/users/" + id);
    };
}
