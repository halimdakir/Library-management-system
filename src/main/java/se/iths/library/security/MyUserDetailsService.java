package se.iths.library.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.iths.library.entity.Login;
import se.iths.library.repository.LoginRepository;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Login> userLogin = loginRepository.findByEmail(email);
        userLogin.orElseThrow( () -> new UsernameNotFoundException("Not found: "+email));
        return userLogin.map(MyUserDetails::new).get();
    }
}
