package se.iths.library.bean;

import org.primefaces.event.FlowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import se.iths.library.dto.UserInfoDTO;
import se.iths.library.entity.Login;
import se.iths.library.entity.User;
import se.iths.library.models.Roles;
import se.iths.library.service.LoginService;
import se.iths.library.service.UserService;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
@ViewScoped
public class RegisterBean implements Serializable {
        private boolean visible = false;
        private Long id;
        private Long loginId;
        private String email;
        private String password;
        private boolean active;
        private String role;
        private String usersRoleSelected;
        private String fullName;
        private String birthDate;
        private String address;
        private List<UserInfoDTO> userList = new ArrayList<>();
        private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



        @Autowired
        UserService userService;
        @Autowired
        LoginService loginService;




        public void show(){
                visible = true;
        }
        public void hide(){
                visible = false;
        }

        //Add User by user
        public void addUser(){

                boolean exist = loginService.checkEmailIfExist(getEmail());
                if (!exist){
                        String encodePassword = passwordEncoder.encode(getPassword());
                        var user = new User(getFullName(), getBirthDate(), getAddress(), new Login(getEmail(), encodePassword, false, Roles.ROLE_USER));
                        userService.createUser(user);
                        FacesMessage msg = new FacesMessage("Successful", "Welcome :" + user.getFullName());
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        emptyFields();
                }else {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email already exist", "");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                }

        }
        public void emptyInputs(){
                setEmail("");
                setPassword("");
                setRole(null);
                setFullName("");
                setBirthDate("");
                setAddress("");
        }
        //Add Admin or User by admin
        public void addUserByAdmin(){
                boolean exist = loginService.checkEmailIfExist(getEmail());
                if (!exist){
                        User  user;
                        String encodePassword = passwordEncoder.encode(getPassword());
                        if (role.equals("User")){

                                user = new User(getFullName(), getBirthDate(), getAddress(), new Login(getEmail(), encodePassword, true, Roles.ROLE_USER));

                        }else {
                                user = new User(getFullName(), getBirthDate(), getAddress(), new Login(getEmail(), encodePassword, true, Roles.ROLE_ADMIN));
                        }

                        userService.createUser(user);
                        FacesMessage msg = new FacesMessage("Successful", "Welcome :" + user.getFullName());
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        emptyInputs();
                }else {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email already exist", "");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                }

        }

        public void updateUser(Long userId, Long loginId){
                Optional<User> user = userService.getUserById(userId);
                Optional<Login> login = loginService.getLoginById(loginId);
                if (user.isPresent() && login.isPresent()){
                        setId(user.get().getId());
                        setLoginId(login.get().getId());
                        setActive(login.get().isActive());
                        setFullName(user.get().getFullName());
                        setEmail(login.get().getEmail());
                        //setPassword(login.get().getPassword());
                        if (login.get().getRoles() == Roles.ROLE_USER){
                                setRole("User");
                        }else {
                                setRole("Admin");

                        }
                        setBirthDate(user.get().getBirthDate());
                        setAddress(user.get().getAddress());
                        show();
                }
        }

        public void saveUser(Long userId, Long loginId){
                User user = new User(getFullName(), getBirthDate(), getAddress());
                Login login;
                String encodePassword = passwordEncoder.encode(getPassword());
                if (role.equals("User")){
                        login = new Login(getEmail(), encodePassword, isActive(), Roles.ROLE_USER);
                }else {
                        login = new Login(getEmail(), encodePassword, isActive(), Roles.ROLE_ADMIN);
                }

                userService.updateUser(user, userId);
                loginService.updateLogin(login, loginId);
                getUsers();
                hide();
        }
        public void saveUpdatedUser(Long userId, Long loginId){
                User user = new User(getFullName(), getBirthDate(), getAddress());
                Login login;

                if (role.equals("User")){
                        login = new Login(getEmail(), isActive(), Roles.ROLE_USER);
                }else {
                        login = new Login(getEmail(), isActive(), Roles.ROLE_ADMIN);
                }

                userService.updateUser(user, userId);
                loginService.activateLogin(login, loginId);
                getUsers();
                hide();
        }
        public void cancelUpdate(){
                hide();
        }


        public void deleteUser(Long userId, Long loginId){
                userService.deleteUserById(userId);
                loginService.deleteLoginById(loginId);
                getUsers();
        }

        public void getUsers(){
                switch (usersRoleSelected) {
                        case "Users" -> userList = userService.findUsersByLogin_Roles(Roles.ROLE_USER);
                        case "Admins" -> userList = userService.findUsersByLogin_Roles(Roles.ROLE_ADMIN);
                        case "All" -> userList = userService.getAllUserInfos();
                }
        }

        public String adminPage() {
                getUsers();
                return "admin";
        }
        public String loginPage() {
                return "login";
        }

        public String onFlowProcess(FlowEvent event) {
                return event.getNewStep();
        }
        private String emptyFields(){
                setFullName("");
                setBirthDate("");
                setAddress("");
                setEmail("");
                setPassword("");
                return "login";
        }


        //<editor-fold desc="Getter & Setter">


        public Long getLoginId() {
                return loginId;
        }

        public void setLoginId(Long loginId) {
                this.loginId = loginId;
        }

        public boolean isActive() {
                return active;
        }

        public void setActive(boolean active) {
                this.active = active;
        }

        public List<UserInfoDTO> getUserList() {
                return userList;
        }

        public void setUserList(List<UserInfoDTO> userList) {
                this.userList = userList;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
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

        public boolean isVisible() {
                return visible;
        }

        public void setVisible(boolean visible) {
                this.visible = visible;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }


        public String getRole() {
                return role;
        }

        public void setRole(String role) {
                this.role = role;
        }

        public String getUsersRoleSelected() {
                return usersRoleSelected;
        }

        public void setUsersRoleSelected(String usersRoleSelected) {
                this.usersRoleSelected = usersRoleSelected;
        }



        //</editor-fold>
}
