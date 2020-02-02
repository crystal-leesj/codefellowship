package com.example.codefellowship.controlloers;

import com.example.codefellowship.models.ApplicationUser;
import com.example.codefellowship.models.ApplicationUserRepository;
import com.example.codefellowship.models.Post;
import com.example.codefellowship.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class PostController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @PostMapping("/writePost")
    public RedirectView createAPost(Principal p, String body, long id){
        ApplicationUser writer = applicationUserRepository.findByUsername(p.getName());
        // save a post
        Post newPost = new Post(writer, body);
        postRepository.save(newPost);
        return new RedirectView("/myprofile");
//        return new RedirectView("/users/" + id);
    }

    @GetMapping("/feed")
    public String displayMyFeed(Principal p, Model m){
        ApplicationUser theUser = applicationUserRepository.findByUsername(p.getName());
        Set<ApplicationUser> usersIFollow = theUser.getUsersIFollow();
        m.addAttribute("usersIFollow", usersIFollow);
        m.addAttribute("principle", p.getName());

//        List<Post> allPosts = postRepository.findAll();
//        long loggedInUserId = theUser.id;
//        // Remove logged in users name from the users list
//        for (int i = 0; i < allPosts.size(); i++) {
//            long userId = allPosts.get(i).getApplicationUser().id;
//            System.out.println("i = " + i);
//            System.out.println("userID = " + allPosts.get(i).getApplicationUser().id);
//            if (userId == loggedInUserId) {
//                allPosts.remove(i);
//            }
//        }
//        m.addAttribute("allPosts", allPosts);


        m.addAttribute("user", theUser);
        m.addAttribute("usersIFollow", theUser.usersIFollow);
        m.addAttribute("posts", theUser.getPosts());
        return "feed";
    }
}
