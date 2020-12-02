package se.iths.library.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.iths.library.entity.Login;
import se.iths.library.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {
    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/create")
    public Login createNewLogin(@RequestBody Login login){
        return loginService.createLogin(login);
    }
}
