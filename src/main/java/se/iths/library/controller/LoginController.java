package se.iths.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.iths.library.entity.Login;
import se.iths.library.service.LoginService;

import java.util.Optional;

@RestController
@RequestMapping("/login")
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

    @PostMapping("authenticated")
    public Login getAuthenticatedUser(){
        return loginService.getAuthenticatedUserEmail();
    }

}
