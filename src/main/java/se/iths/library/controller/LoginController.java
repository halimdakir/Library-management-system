package se.iths.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import se.iths.library.entity.Login;
import se.iths.library.exception.UnauthorizedException;
import se.iths.library.models.EmailJsonFormat;
import se.iths.library.service.LoginService;

import javax.annotation.security.RolesAllowed;

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


    @PostMapping("/create")
    public Login createNewLogin(@RequestBody Login login){
        return loginService.createLogin(login);
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
