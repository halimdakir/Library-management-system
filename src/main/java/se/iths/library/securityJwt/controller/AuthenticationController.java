package se.iths.library.securityJwt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.iths.library.security.MyUserDetailsService;
import se.iths.library.securityJwt.config.JwtUtil;
import se.iths.library.securityJwt.models.AuthenticationRequest;


@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwTokenUtil;

    @PostMapping("/auth")
    public /*ResponseEntity<?>*/ String createAuthenticationToken(@RequestBody AuthenticationRequest authRequest) throws Exception{

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        }catch (BadCredentialsException e){
            throw new Exception("Invalid username and password!", e);
        } catch (DisabledException e) {
            throw new Exception("Account is not active!", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwTokenUtil.generateToken(userDetails);
        //return ResponseEntity.ok(new AuthenticationResponse(jwt));
        return jwt;
    }
    @PostMapping("/logout")
    public String logout(){
        return "logout";
    }
}
