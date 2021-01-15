package se.iths.library.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import se.iths.library.securityJwt.config.JwtAuthenticationEntryPoint;
import se.iths.library.securityJwt.filter.JwtRequestFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private UserAuthenticationSuccessHandler successHandler;
    @Autowired
    private UserAuthenticationFailureHandler failureHandler;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO Spring security without JWT
        /*http.authorizeRequests()
                .antMatchers("/", "/home", "/home.xhtml", "/item", "/item.xhtml", "/register","/register.xhtml").permitAll()
                .antMatchers("/item/all", "/item/id/**").permitAll()
                .antMatchers("/user/all","/user/id/**").hasRole("ADMIN")
                .antMatchers("/user", "/user.xhtml").hasRole( "USER")
                .antMatchers("/admin", "/admin.xhtml", "/users", "/users.xhtml", "/registerAdmin.xhtml").hasRole("ADMIN")
                .and()
                .formLogin().successHandler(successHandler).permitAll().and().logout().permitAll();
        http.csrf().disable();*/

        //TODO Spring security with JSON WEB TOKEN


        http
                .csrf().disable()
                .authorizeRequests()
                //.antMatchers("/", "/home", "/register", "/login", "/item", "/auth").permitAll()
                .antMatchers("/admin", "/users", "/reserveAdmin", "/registerAdmin", "/stock", "/contact", "/message").hasRole("ADMIN")
                .antMatchers("/user", "/userInfo", "reserve", "/contactUser", "/messageUser").hasRole( "USER")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //TODO LEARN ABOUT EACH OPTION HERE
        //super.configure(auth);
        auth.authenticationProvider(authProvider());

    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authProvider;
    }
    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}
