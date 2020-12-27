package se.iths.library.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import se.iths.library.dto.BorrowedItemsDTO;
import se.iths.library.dto.UserInfoDTO;
import se.iths.library.entity.Login;
import se.iths.library.entity.User;
import se.iths.library.models.Roles;
import se.iths.library.service.ItemLendingService;
import se.iths.library.service.LoginService;
import se.iths.library.service.UserService;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@ViewScoped
public class UserBean {
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
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    @Autowired
    private ItemLendingService itemLendingService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;


    public void getBorrowedItems(String email){
        borrowedItemList = itemLendingService.findBorrowedItemsAndCreationDueDateByUserEmail(email);
    }
    public void getBorrowedItemsByUserId(){
        Optional<Login> authenticatedUser = loginService.getAuthenticatedUserEmail();
        if (authenticatedUser.isPresent()){
            List<User> userInfo = userService.findUserByLoginEmail(authenticatedUser.get().getEmail());
            borrowedItemListByUser = itemLendingService.findBorrowedItemsAndCreationDueDateByUserId(userInfo.get(0).getId());
        }

    }
    public void getAuthenticatedUserInfo(){
        Long idLogin = null;
        Long idUser = null;
        Optional<Login> authenticatedUser = loginService.getAuthenticatedUserEmail();
        if (authenticatedUser.isPresent()){
            idLogin = authenticatedUser.get().getId();
            List<User> userInfo = userService.findUserByLoginEmail(authenticatedUser.get().getEmail());
            idUser = userInfo.get(0).getId();
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
    public void redirectToHome() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().redirect("/home");
    }

    //<editor-fold desc="Getter & Setter">

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
    //</editor-fold>
}
