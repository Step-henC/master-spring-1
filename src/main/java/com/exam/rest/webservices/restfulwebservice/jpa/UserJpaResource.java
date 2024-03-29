package com.exam.rest.webservices.restfulwebservice.jpa;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import com.exam.rest.webservices.restfulwebservice.user.Post;
import com.exam.rest.webservices.restfulwebservice.user.PostNotFoundException;
import com.exam.rest.webservices.restfulwebservice.user.User;
import com.exam.rest.webservices.restfulwebservice.user.UserNotFoundException;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {
    
    @Autowired
    private UserRepository userRepository;

    private PostRepository postRepository;

    public UserJpaResource(UserRepository userRepository, PostRepository postRepository){
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }
//     <dependency>
//     <groupId>com.fasterxml.jackson.dataformat</groupId>
//     <artifactId>jackson-dataformat-xml</artifactId>
// </dependency> allows for xml in client/consumer request
    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    //add link using spring hateoas
    //make use of hateoas with EntityModel that wraps pojo and adds links to it
    //add webmvclinkbuilder to create links
    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id){


       Optional<User> user = userRepository.findById(id);

       if (user.isEmpty()){
        throw new UserNotFoundException("id: "+ id);
       }

        EntityModel<User> entityModel = EntityModel.of(user.get());

        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));
       return entityModel;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        //we want to return user and 201 response code to show "creted"
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder 
                        .fromCurrentRequest().path("/{id}") //grabs current url and appends ID
                        .buildAndExpand(savedUser.getId()) //replace variable in path with id from saved user
                        .toUri(); //convert to URI and return

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id){

       userRepository.deleteById(id);

    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> getPostsForUser(@PathVariable int id){

        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new UserNotFoundException("id: "+ id);
        }
        
        return user.get().getPosts();

    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Post> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post){

        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new UserNotFoundException("id: "+ id);
        }
        
        post.setUser(user.get());

       Post savedPost =  postRepository.save(post);

        URI location = ServletUriComponentsBuilder 
        .fromCurrentRequest().path("/{id}") //grabs current url and appends ID
        .buildAndExpand(savedPost.getId()) //replace variable in path with id from saved user
        .toUri(); //convert to URI and return

        return ResponseEntity.created(location).build();

    }

    @GetMapping("/jpa/users/{id}/posts/{postId}")
    public EntityModel<Post> retrievePost(@PathVariable int id, @PathVariable int postId){


       Optional<User> user = userRepository.findById(id);

       if (user.isEmpty()){
        throw new UserNotFoundException("id: "+ id);
       }

        Optional<Post> post = Optional.ofNullable(postRepository.findPostByUserId(id, postId));
        if (post.isEmpty()){
            throw new PostNotFoundException("Post with id: "+ postId + " and user id " + id+ " not found");
        }   

        EntityModel<Post> entityModel = EntityModel.of(post.get());

        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getPostsForUser(user.get().getId()));
        entityModel.add(link.withRel("all-users-post"));
       return entityModel;
    }
}
