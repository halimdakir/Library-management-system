package se.iths.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import se.iths.library.entity.Login;
import se.iths.library.entity.User;
import se.iths.library.exception.DeleteDetails;
import se.iths.library.exception.NotFoundException;
import se.iths.library.exception.UnprocessableEntityException;
import se.iths.library.models.Roles;
import se.iths.library.models.UserDomain;
import se.iths.library.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
    public Iterable<User>getAllUsers(){
        return userService.getAllUsers();
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/new")
    public User createNewUser(@RequestBody UserDomain userDomain) {
        User user = new User();
        Login login = new Login();

        if (userDomain.getBirthDate() != null && userDomain.getFullName() != null && userDomain.getLoginDomain().getEmail() != null && userDomain.getLoginDomain().getPassword() != null){

            if (userDomain.getBirthDate().matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")){

                if (userDomain.getLoginDomain().getEmail().matches("^(.+)@(.+)$")){

                    if (userDomain.getLoginDomain().getRoles().equalsIgnoreCase("ADMIN")){
                        login.setRoles(Roles.ROLE_ADMIN);

                    }else if (userDomain.getLoginDomain().getRoles().equalsIgnoreCase("USER")){
                        login.setRoles(Roles.ROLE_USER);

                    }else {
                        throw new UnprocessableEntityException("Role are required! Please choose between ADMIN & USER");
                    }

                    login.setEmail(userDomain.getLoginDomain().getEmail());
                    login.setPassword(passwordEncoder.encode(userDomain.getLoginDomain().getPassword()));

                    user.setFullName(userDomain.getFullName());
                    user.setBirthDate(userDomain.getBirthDate());
                    user.setAddress(userDomain.getAddress());


                    User user1 = new User(user.getFullName(), user.getBirthDate(), user.getAddress(), new Login(login.getEmail(), login.getPassword(), login.getRoles()));

                    return userService.createUser(user1);

                }else {
                    throw new UnprocessableEntityException("Enter a valid email!");
                }
            }else {
                throw new UnprocessableEntityException("Correct date format is : yyyy-MM-dd");
            }
        }else {
            throw new UnprocessableEntityException("Full name, Email, Password & date of birth are required!");
        }

    }

    @PreAuthorize("isAuthenticated()")
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/email/{email}")
    public String getOneUserByEmail(@PathVariable String email){
        User user =  userService.findUserByLoginEmail(email);
        return user.getId()+" "+user.getFullName();
    }
}
