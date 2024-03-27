package com.exam.rest.webservices.restfulwebservice.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

//component allows Spring to manage this
@Component 
public class UserDaoService {
    
    //temp static list
    private static List<User> users = new ArrayList<>();

    private static int usersCount = 0;

    //initialize empty list
    static {
        users.add(new User(++usersCount, "Adam", LocalDate.now().minusYears(30)));
        users.add(new User(++usersCount, "Eve", LocalDate.now().minusYears(25)));
        users.add(new User(++usersCount, "Jim", LocalDate.now().minusYears(22)));

    }
    public List<User> findAll(){
        return users;
    }

    public User findOne(int id){
        Predicate<? super User> predicate = user -> 
            user.getId().equals(id);
            return users.stream().filter(predicate).findFirst().orElse(null);
    }

    public User save(User user){
        user.setId(++usersCount);
        users.add(user);

        return user;
    }

    public void deleteById(int id){
        Predicate<? super User> predicate = user -> 
            user.getId().equals(id);

            users.removeIf(predicate);
    }
}
