package se.iths.library.bean;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;
import se.iths.library.dto.BorrowedItemsDTO;
import se.iths.library.entity.Login;
import se.iths.library.entity.User;
import se.iths.library.securityJwt.controller.AuthenticationController;
import se.iths.library.securityJwt.models.AuthenticationRequest;
import se.iths.library.securityJwt.models.AuthenticationResponse;
import se.iths.library.service.ItemLendingService;
import se.iths.library.service.LoginService;
import se.iths.library.service.UserService;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
public class UserBean implements Serializable {
    private String loginUsername;
    private String loginPassword;
    private boolean Logged = false;
    private boolean LoggedOut = false;
    private String authenticatedUserFullName;
    private Long loggedId;
    private String email;
    private Long id;
    private List<BorrowedItemsDTO> borrowedItemList = new ArrayList<>();
    private List<BorrowedItemsDTO> borrowedItemListByUser = new ArrayList<>();
    //TODO PERSONAL INFORMATION
    private Long userId;
    private Long loginId;
    private String mail;
    private String password;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
    private String fullName;
    private String birthDate;
    private String address;
    private String token;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();




    private ItemLendingService itemLendingService;
    private LoginService loginService;
    private UserService userService;

    public UserBean(ItemLendingService itemLendingService, LoginService loginService, UserService userService) {
        this.itemLendingService = itemLendingService;
        this.loginService = loginService;
        this.userService = userService;
    }

    public void getBorrowedItems(String email){
        borrowedItemList = itemLendingService.findBorrowedItemsAndCreationDueDateByUserEmail(email);
    }
    public void getBorrowedItemsByUserId(){
        Login authenticatedUser = loginService.getAuthenticatedUserEmail();
        if (authenticatedUser!=null){
            User userInfo = userService.findUserByLoginEmail(authenticatedUser.getEmail());
            borrowedItemListByUser = itemLendingService.findBorrowedItemsAndCreationDueDateByUserId(userInfo.getId());
        }

    }
    public void getAuthenticatedUserInfo(){
        Long idLogin = null;
        Long idUser = null;
        Login authenticatedUser = loginService.getAuthenticatedUserEmail();
        if (authenticatedUser!=null){
            idLogin = authenticatedUser.getId();
            User userInfo = userService.findUserByLoginEmail(authenticatedUser.getEmail());
            idUser = userInfo.getId();
        }

        var user = userService.getUserById(idUser);
        var login = loginService.getLoginById(idLogin);

        if (user.isPresent() && login.isPresent()){

            setUserId(user.get().getId());
            setLoginId(login.get().getId());
            setFullName(user.get().getFullName());
            setMail(login.get().getEmail());
            setPassword(login.get().getPassword());
            setOldPassword("");
            setNewPassword("");
            setConfirmPassword("");
            setBirthDate(user.get().getBirthDate());
            setAddress(user.get().getAddress());
        }
    }
    public void saveUser(Long userId, Long loginId){
        if (getNewPassword().equals(getConfirmPassword())){
            var login = loginService.getLoginById(loginId);
            if (login.isPresent()) {

                if (passwordEncoder.matches(getOldPassword(), login.get().getPassword())) {
                    String encodePassword = passwordEncoder.encode(getNewPassword());
                    User user = new User(getFullName(), getBirthDate(), getAddress());
                    userService.updateUser(user, userId);
                    loginService.updateLoginByUser(encodePassword, loginId);

                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Old password incorrect", "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "New password & confirmation are not identical", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationController authenticationController;

    public void loginAction() throws Exception {
        /*try {
            Authentication request = new UsernamePasswordAuthenticationToken(this.getLoginUsername(), this.getLoginPassword());
            Authentication result = authenticationManager.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect("/home");

        } catch (AuthenticationException e) {
            e.printStackTrace();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Bad Credentials!", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }*/


        try {
            token = authenticationController.createAuthenticationToken(new AuthenticationRequest(this.getLoginUsername(), this.getLoginPassword()));
            System.out.println(" TOKEN :" +token);
            setLoggedOut(false);
            setLogged(true);
            User user = userService.findUserByLoginEmail(this.getLoginUsername());
            setAuthenticatedUserFullName(user.getFullName());
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect("/home");

        } catch (Exception e){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }
    public void logout() throws IOException {
        setAuthenticatedUserFullName("");
        setLogged(false);
        setLoggedOut(true);
        authenticationController.logout();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().redirect("/login");
    }

    //<editor-fold desc="Getter & Setter">


    public String getAuthenticatedUserFullName() {
        return authenticatedUserFullName;
    }

    public void setAuthenticatedUserFullName(String authenticatedUserFullName) {
        this.authenticatedUserFullName = authenticatedUserFullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BorrowedItemsDTO> getBorrowedItemList() {
        return borrowedItemList;
    }

    public void setBorrowedItemList(List<BorrowedItemsDTO> borrowedItemList) {
        this.borrowedItemList = borrowedItemList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<BorrowedItemsDTO> getBorrowedItemListByUser() {
        return borrowedItemListByUser;
    }

    public void setBorrowedItemListByUser(List<BorrowedItemsDTO> borrowedItemListByUser) {
        this.borrowedItemListByUser = borrowedItemListByUser;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public boolean isLogged() {
        return Logged;
    }

    public void setLogged(boolean logged) {
        Logged = logged;
    }

    public boolean isLoggedOut() {
        return LoggedOut;
    }

    public void setLoggedOut(boolean loggedOut) {
        LoggedOut = loggedOut;
    }

    public Long getLoggedId() {
        return loggedId;
    }

    public void setLoggedId(Long loggedId) {
        this.loggedId = loggedId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    //</editor-fold>
}
