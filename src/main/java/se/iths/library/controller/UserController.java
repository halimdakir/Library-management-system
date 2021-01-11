package se.iths.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.iths.library.entity.User;
import se.iths.library.exception.DeleteDetails;
import se.iths.library.exception.NotFoundException;
import se.iths.library.service.UserService;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/id/{id}")
    public Optional<User> getOneUserById(@PathVariable Long id){
       Optional<User> user = userService.getUserById(id);
        if (user.isPresent()){
            return user;
        }else {
            throw new NotFoundException("User not found with id :" + id);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public Iterable<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/new")
    public User createNewUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/id/{id}")
    public User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return userService.updateUser(newUser, id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        var user = userService.getUserById(id);
        if (user.isPresent()){
            userService.deleteUserById(id);
            return new ResponseEntity<>(new DeleteDetails("Delete request", "User with id :"+id+" is successfully deleted!"), HttpStatus.OK);
        }else {
            throw new NotFoundException("User not found with id :" + id);
        }
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/email/{email}")
    public String getOneUserByEmail(@PathVariable String email){
        User user =  userService.findUserByLoginEmail(email);
        return user.getId()+" "+user.getFullName();
    }
}
