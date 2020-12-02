package se.iths.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.library.entity.Login;
import se.iths.library.repository.LoginRepository;


@Service
public class LoginService {
    @Autowired
    LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public Login createLogin(Login login){
        return loginRepository.save(login);
    }

}
