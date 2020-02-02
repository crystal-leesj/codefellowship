package com.example.codefellowship.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class ApplicationUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name="following", // name of the table in sql
            joinColumns = { @JoinColumn(name="follower")},
            inverseJoinColumns = {@JoinColumn(name="influencer")}
    )
    public Set<ApplicationUser> usersIFollow;

    @ManyToMany(mappedBy = "usersIFollow")
    public Set<ApplicationUser> usersFollowMe;

    public Set<ApplicationUser> getUsersIFollow() {
        return this.usersIFollow;
    }

    public void follow(ApplicationUser influencer){
        this.usersIFollow.add(influencer);
    }

    // matches the property on the other class
    @OneToMany(mappedBy = "applicationUser")
    List<Post> posts;

    public List<Post> getPosts(){
        return this.posts;
    }

    String username;
    String password;
    String firstName;
    String lastName;
    String dateOfBirth;
    String bio;

    public ApplicationUser() {};

    public ApplicationUser(String username, String password, String firstName, String lastName, String dateOfBirth, String bio){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }


    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getBio() {
        return this.bio;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
