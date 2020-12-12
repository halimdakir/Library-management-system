package se.iths.library.bean;

import org.primefaces.event.FlowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.iths.library.controller.LoginController;
import se.iths.library.controller.MemberController;
import se.iths.library.entity.Login;
import se.iths.library.entity.Member;
import javax.annotation.PostConstruct;
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
        private String fullName;
        private String birthDate;
        private String address;
        private List<Member> memberList = new ArrayList<>();
        private ItemBean itemBean;

        @Autowired
        MemberController memberController;
        @Autowired
        LoginController loginController;



        @PostConstruct
        public void init(){
                getMembers();
        }

        public void show(){
                visible = true;
        }
        public void hide(){
                visible = false;
        }

        public void addMember(){
                memberController.createNewMember(new Member(getFullName(), getBirthDate(), getAddress()));
                loginController.createNewLogin(new Login(getEmail(), getPassword()));

        }
        public void updateMember(Long id){
                Optional<Member> member = memberController.getOneMemberById(id);
                setId(member.get().getId());
                setFullName(member.get().getFullName());
                setBirthDate(member.get().getBirthDate());
                setAddress(member.get().getAddress());
                show();
        }
        public void saveMember(String fullName, String birthDate, String address, Long id){
                Member member  = new Member(fullName, birthDate, address);
                memberController.updateMember(member, id);
                hide();
                getMembers();
        }
        public void deleteMember(Long id){
                memberController.deleteMemberById(id);
                getMembers();
        }
        public void getMembers(){
                Iterable<Member> iterable = memberController.getAllMembers();
                memberList = StreamSupport.stream(iterable.spliterator(), false)
                        .collect(Collectors.toList());
        }
        public String adminPage() {
                getMembers();
                return "admin";
        }
        public String onFlowProcess(FlowEvent event) {
                return event.getNewStep();
        }

        //<editor-fold desc="Getter & Setter">

        public ItemBean getItemBean() {
                return itemBean;
        }

        public void setItemBean(ItemBean itemBean) {
                this.itemBean = itemBean;
        }

        public List<Member> getMemberList() {
                return memberList;
        }

        public void setMemberList(List<Member> memberList) {
                this.memberList = memberList;
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
        //</editor-fold>
}
