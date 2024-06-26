package com.exam.rest.webservices.restfulwebservice.user;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity(name = "user_details") //must rename because User is a reserved word in H2
public class User {
 
    protected User(){} //need this for jpa
    @Id
    @GeneratedValue
    private Integer id;

    @Size(min=2, message = "Name must be at least 2 characters") //from validation package/dependency matching valid annotation in controller
    @JsonProperty("user_name") //customize jackson serialization with name
    private String name;

    @Past(message = "Birth Date should be in the past")
    @JsonProperty("birth_date") //customize jackson serialization with name
    private LocalDate birthDate;

    @OneToMany(mappedBy = "user") //field in posts that maps this relationship one user has many posts
    @JsonIgnore  //we do not want to return posts info on User request
    private List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public User(Integer id, String name, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
   
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }


    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + "]";
    }

}
