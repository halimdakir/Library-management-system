package se.iths.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import se.iths.library.entity.Login;
import se.iths.library.entity.User;
import se.iths.library.exception.DeleteDetails;
import se.iths.library.exception.NotFoundException;
import se.iths.library.exception.UnauthorizedException;
import se.iths.library.exception.UnprocessableEntityException;
import se.iths.library.models.EmailJsonFormat;
import se.iths.library.models.LoginDomain;
import se.iths.library.models.Roles;
import se.iths.library.service.LoginService;

import java.util.Optional;

@RestController
@RequestMapping("/login")
@PreAuthorize("permitAll()")
public class LoginController {

    @Autowired
    LoginService loginService;



    @GetMapping("")
    public String login(){
        return "login";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/id/{id}")
    public Optional<Login> getOneLoginById(@PathVariable Long id){
        Optional<Login> Login = loginService.getLoginById(id);
        if (Login.isPresent()){
            return Login;
        }else {
            throw new NotFoundException("Login not found with id :" + id);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/id/{id}")
    public Login updateLogin(@RequestBody Login newLogin, @PathVariable Long id) {
        return loginService.updateLogin(newLogin, id);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/activate/id/{id}")
    public Login activateUser(@PathVariable Long id) {
        return loginService.activateUser(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        var login = loginService.getLoginById(id);
        if (login.isPresent()){
            loginService.deleteLoginById(id);
            return new ResponseEntity<>(new DeleteDetails("Delete request", "Login with id :"+id+" is successfully deleted!"), HttpStatus.OK);
        }else {
            throw new NotFoundException("Login not found with id :" + id);
        }
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("authenticated")
    public EmailJsonFormat getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken){
            throw new UnauthorizedException("Unauthorized! You have to login.");

        }else {
            Login login =  loginService.getAuthenticatedUserEmail();
            return new EmailJsonFormat(login.getEmail());
        }

    }

}
