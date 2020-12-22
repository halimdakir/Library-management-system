package se.iths.library.bean;

import org.primefaces.event.FlowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.iths.library.controller.LoginController;
import se.iths.library.controller.UserController;
import se.iths.library.domain.Roles;
import se.iths.library.entity.Login;
import se.iths.library.entity.User;
import se.iths.library.service.UserService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Component
@ViewScoped
public class RegisterBean implements Serializable {
        private boolean visible = false;
        private Long id;
        private String email;
        private String password;
        private boolean active;
        private String fullName;
        private String birthDate;
        private String address;
        private List<User> userList = new ArrayList<>();


        @Autowired
        UserController userController;
        @Autowired
        UserService userService;
        @Autowired
        LoginController loginController;

        @PostConstruct
        public void init(){
                getUsers();
        }



        public void show(){
                visible = true;
        }
        public void hide(){
                visible = false;
        }

        public void addUser(){
                var user = new User(getFullName(), getBirthDate(), getAddress());
                var login = new Login(getEmail(), getPassword(), Roles.ROLE_USER);
                user.setLogin(login);
                login.setUser(user);
                userService.createUser(user);
                FacesMessage msg = new FacesMessage("Successful", "Welcome :" + user.getFullName());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                emptyFields();
        }

        public void updateUser(Long id){
                Optional<User> user = userService.getUserById(id);
                if (user.isPresent()){
                        setId(user.get().getId());
                        setFullName(user.get().getFullName());
                        setBirthDate(user.get().getBirthDate());
                        setAddress(user.get().getAddress());
                        show();
                }
        }

        public void saveUser(String fullName, String birthDate, String address, Long id){
                User user = new User(fullName, birthDate, address);
                userService.updateUser(user, id);
                getUsers();
                hide();
        }

        public void deleteUser(Long id){
                userService.deleteUserById(id);
                getUsers();
        }

        public void getUsers(){
                Iterable<User> iterable = userService.findUsersByLogin_Roles(Roles.ROLE_USER);
                userList = StreamSupport.stream(iterable.spliterator(), false)
                        .collect(Collectors.toList());
        }

        public String adminPage() {
                getUsers();
                return "admin";
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


        public List<User> getMemberList() {
                return userList;
        }

        public void setMemberList(List<User> userList) {
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

        public List<User> getUserList() {
                return userList;
        }

        public void setUserList(List<User> userList) {
                this.userList = userList;
        }
        //</editor-fold>
}
