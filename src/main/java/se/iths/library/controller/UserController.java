package se.iths.library.controller;

import org.springframework.web.bind.annotation.*;
import se.iths.library.entity.User;
import se.iths.library.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/id/{id}")
    public Optional<User> getOneUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }
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

        return userService.getUserById(id)
                .map(member -> {
                    member.setFullName(newUser.getFullName());
                    member.setBirthDate(newUser.getBirthDate());
                    member.setAddress(newUser.getAddress());
                    return userService.createUser(member);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return userService.createUser(newUser);
                });
    }
    @DeleteMapping("/id/{id}")
    public void deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
    }
}
