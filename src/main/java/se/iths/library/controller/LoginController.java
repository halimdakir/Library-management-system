package se.iths.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import se.iths.library.entity.Login;
import se.iths.library.exception.UnauthorizedException;
import se.iths.library.exception.UnprocessableEntityException;
import se.iths.library.models.EmailJsonFormat;
import se.iths.library.models.LoginDomain;
import se.iths.library.models.Roles;
import se.iths.library.service.LoginService;

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


    @PostMapping("/new")
    public Login createNewLogin(@RequestBody LoginDomain loginDomain){
        Login login = new Login();

        if (loginDomain.getEmail() != null && loginDomain.getPassword() != null) {
            if (loginDomain.getEmail().matches("^(.+)@(.+)$")){

                login.setEmail(loginDomain.getEmail());
                login.setPassword(loginDomain.getPassword());

                if (loginDomain.getRoles().equalsIgnoreCase("ADMIN")){
                    login.setRoles(Roles.ROLE_ADMIN);
                    return loginService.createLogin(login);

                }else if (loginDomain.getRoles().equalsIgnoreCase("USER")){
                    login.setRoles(Roles.ROLE_USER);
                    return loginService.createLogin(login);
                }
                else {
                    throw new UnprocessableEntityException("Role are required! Please choose between ADMIN & USER");
                }
            }else {
                throw new UnprocessableEntityException("Enter a valid email!");
            }

        }else {
            throw new UnprocessableEntityException("Email & Password are required!");
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
