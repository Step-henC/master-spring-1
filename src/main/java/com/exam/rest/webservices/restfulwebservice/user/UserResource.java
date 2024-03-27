package com.exam.rest.webservices.restfulwebservice.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResource {
    
    @Autowired
    private UserDaoService userDaoService;

    public UserResource(UserDaoService userDaoService){
        this.userDaoService = userDaoService;
    }
//     <dependency>
//     <groupId>com.fasterxml.jackson.dataformat</groupId>
//     <artifactId>jackson-dataformat-xml</artifactId>
// </dependency> allows for xml in client/consumer request
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userDaoService.findAll();
    }

    //add link using spring hateoas
    //make use of hateoas with EntityModel that wraps pojo and adds links to it
    //add webmvclinkbuilder to create links
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id){


       User user = userDaoService.findOne(id);

       if (user == null){
        throw new UserNotFoundException("id: "+ id);
       }

        EntityModel<User> entityModel = EntityModel.of(user);

        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));
       return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        //we want to return user and 201 response code to show "creted"
        User savedUser = userDaoService.save(user);
        URI location = ServletUriComponentsBuilder 
                        .fromCurrentRequest().path("/{id}") //grabs current url and appends ID
                        .buildAndExpand(savedUser.getId()) //replace variable in path with id from saved user
                        .toUri(); //convert to URI and return

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){

       userDaoService.deleteById(id);

    }
}
