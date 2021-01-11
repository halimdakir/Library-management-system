package se.iths.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.library.entity.Login;
import se.iths.library.repository.LoginRepository;

import java.util.Optional;


@Service
public class LoginService {
    @Autowired
    LoginRepository loginRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }
    public Optional<Login> getLoginById(Long id){
        return loginRepository.findById(id);
    }
    public void deleteLoginById(Long id){
        Optional<Login> foundLogin = loginRepository.findById(id);
        loginRepository.deleteById(foundLogin.get().getId());
    }
    public Login createLogin(Login login){
        login.setPassword(passwordEncoder.encode(login.getPassword()));
        return loginRepository.save(login);
    }
    public boolean checkEmailIfExist(String email){
        Optional<Login> login = loginRepository.findByEmail(email);
        if (login.isPresent()){
            return true;
        }else {
            return false;
        }
    }
    public void updateLogin(Login newLogin, Long id){
        loginRepository.findById(id)
                .map(login -> {
                    login.setEmail(newLogin.getEmail());
                    login.setPassword(newLogin.getPassword());
                    login.setActive(newLogin.isActive());
                    login.setRoles(newLogin.getRoles());
                    return loginRepository.save(login);
                })
                .orElseGet(() -> {
                    newLogin.setId(id);
                    return loginRepository.save(newLogin);
                });
    }
    public void updateLoginByUser(String password, Long id){
        loginRepository.findById(id)
                .map(login -> {
                    login.setPassword(password);
                    return loginRepository.save(login);
                });
    }
    public Login getAuthenticatedUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName();
        return loginRepository.findLoginByEmail(authenticatedUsername);
    }

}
