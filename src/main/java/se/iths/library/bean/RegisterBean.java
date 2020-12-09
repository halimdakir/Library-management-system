package se.iths.library.bean;

import org.primefaces.event.FlowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.iths.library.controller.LoginController;
import se.iths.library.controller.MemberController;
import se.iths.library.entity.Login;
import se.iths.library.entity.Member;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;


@Component
@ViewScoped
public class RegisterBean implements Serializable {
        private String email;
        private String password;
        private String fullName;
        private String birthDate;
        private String address;

        @Autowired
        MemberController memberController;
        @Autowired
        LoginController loginController;

        /*public RegisterBean(MemberController memberController, LoginController loginController) {
                this.memberController = memberController;
                this.loginController = loginController;
        }*/

        public void addMember(){
                memberController.createNewMember(new Member(getFullName(), getBirthDate(), getAddress()));
                loginController.createNewLogin(new Login(getEmail(), getPassword()));

        }
        public List<Member> getMembers(){
          return memberController.getAllMembers();
        }
        public String onFlowProcess(FlowEvent event) {
                return event.getNewStep();
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
}
