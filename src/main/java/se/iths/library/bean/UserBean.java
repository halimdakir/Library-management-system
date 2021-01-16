package se.iths.library.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import se.iths.library.dto.BorrowedItemsDTO;
import se.iths.library.dto.ReservedItemDTO;
import se.iths.library.entity.ItemLending;
import se.iths.library.entity.Login;
import se.iths.library.entity.User;
import se.iths.library.jms.component.MessageConsumer;
import se.iths.library.jms.model.SystemMessage;
import se.iths.library.jms.service.JmsPublishServiceImpl;
import se.iths.library.models.Roles;
import se.iths.library.repository.ItemLendingRepository;
import se.iths.library.securityJwt.controller.AuthenticationController;
import se.iths.library.securityJwt.models.AuthenticationRequest;
import se.iths.library.service.*;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.validation.constraints.Email;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@ViewScoped
public class UserBean implements Serializable {
    private String loginUsername;
    private String loginPassword;
    private boolean Logged = false;
    private boolean LoggedOut = false;
    private String authenticatedUserFullName;
    private boolean authenticatedUserRole;
    private boolean authenticateAdminRole;
    private boolean isAccept;
    private boolean isReturn;
    private boolean toAccept;
    private boolean toReturn;
    private Long loggedId;
    private String email;
    private Long id;
    private List<BorrowedItemsDTO> borrowedItemList = new ArrayList<>();
    private List<BorrowedItemsDTO> borrowedItemListByUser = new ArrayList<>();
    private List<BorrowedItemsDTO> reservedItemListByUser = new ArrayList<>();
    private List<ReservedItemDTO> reservedAllItemListByAdmin = new ArrayList<>();
    //User info
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
    private ItemLending selectedReservedItem;
    private ReservedItemDTO selectedReservedItemDTO;
    private BorrowedItemsDTO selectedBorrowedItemsDTO;
    //Contact
    @Email(regexp = "[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]" )
    private String contactToEmail;
    private String contactFromEmail;
    private String contactSubject;
    private String contactMessage;
    //Message
    private List<SystemMessage> systemMessageList = new ArrayList<>();


    @Autowired
    private ItemLendingService itemLendingService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemLendingRepository itemLendingRepository;
    @Autowired
    private StockService stockService;
    @Autowired
    private JmsPublishServiceImpl jmsPublishService;



    public void sendMessage(){
        var systemMessage = new SystemMessage(getContactFromEmail(), getContactToEmail(), getContactSubject(), getContactMessage());
        try {
            String message = jmsPublishService.publishMessage(systemMessage);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            setContactToEmail(null);
            setContactSubject(null);
            setContactMessage(null);
        }catch (Exception e){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    public void sendMessageByUser(){
        try {
            setContactToEmail("salim@gmail.com");
            String message = jmsPublishService.publishMessage(new SystemMessage(getContactFromEmail(), getContactToEmail(), getContactSubject(), getContactMessage()));
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            setContactToEmail(null);
            setContactSubject(null);
            setContactMessage(null);
        }catch (Exception e){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    public void getJmsMessages(){
       systemMessageList = MessageConsumer.list.stream()
                .filter(e -> e.getEmailTo().equals(this.getLoginUsername()))
               .collect(Collectors.toList());

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
    private AuthenticationController authenticationController;
    public void loginAction(){
        try {
            token = authenticationController.createAuthenticationToken(new AuthenticationRequest(this.getLoginUsername(), this.getLoginPassword()));
            System.out.println(" TOKEN : " +token);
            setLoggedOut(false);
            setLogged(true);
            User user = userService.findUserByLoginEmail(this.getLoginUsername());
            setAuthenticatedUserFullName(user.getFullName());
            setContactFromEmail(this.getLoginUsername());

            var login = loginService.getLoginByEmail(this.getLoginUsername());
            if (login.isPresent()){
                if (login.get().getRoles() == Roles.ROLE_ADMIN){
                    authenticateAdminRole = true;
                    authenticatedUserRole = false;
                }else if (login.get().getRoles() == Roles.ROLE_USER){
                    authenticateAdminRole = false;
                    authenticatedUserRole = true;
                }
            }

            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect("/connect");

        } catch (Exception e){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }
    public void reserveItems(String itemBarCode){
        var item = itemService.findItemByBarCode(itemBarCode);
        var user = new User();
        Login authenticatedUser = loginService.getAuthenticatedUserEmail();
        if (authenticatedUser!=null){
            user = userService.findUserByLoginEmail(authenticatedUser.getEmail());
        }
        itemLendingRepository.save(new ItemLending(java.time.LocalDate.now().toString(), java.time.LocalDate.now().plusDays(15).toString(), false, false, user, item));
        getReservedItemList();
    }

    private void getReservedItemList(){
        Login authenticatedUser = loginService.getAuthenticatedUserEmail();
        if (authenticatedUser!=null){
            User userInfo = userService.findUserByLoginEmail(authenticatedUser.getEmail());
            reservedItemListByUser = itemLendingService.findReservedItemsAndCreationDueDateByUserId(userInfo.getId());
        }
    }

    public void getAllReservedItemList(){
        reservedAllItemListByAdmin = itemLendingService.getAllReservedItems();
    }

    public void deleteReservedItems(Long reservedItemId){
        itemLendingService.deleteReservedItem(reservedItemId);
        getReservedItemList();
    }

    public String redirectToUserDashBoard(){
        getBorrowedItemsByUserId();
        getAuthenticatedUserInfo();
        getReservedItemList();
        getAllReservedItemList();
        getJmsMessages();
        return "user";
    }

    public String redirectToAdminDashBoard(){
        getAllReservedItemList();
        getJmsMessages();
        return "admin";
    }

    public void preAcceptReservedItemD(Long id){
        setToAccept(true);
        selectedReservedItem = itemLendingService.getReservedItemById(id);
        List<ReservedItemDTO> filterList = reservedAllItemListByAdmin.stream()
                .filter(e -> e.getId().equals(id))
                .collect(Collectors.toList());
        if (filterList.size() !=0){
            selectedReservedItemDTO = reservedAllItemListByAdmin.get(0);
        }
    }

    public void acceptReservedItem(Long selectedReservedItemId){
        selectedReservedItem = itemLendingService.getReservedItemById(selectedReservedItemId);
        if (selectedReservedItem != null){
            selectedReservedItem.setConfirmed(isAccept());
            //Update stock quantity
            var stock = stockService.getStockByItemId(selectedReservedItem.getItem().getId());
            if (stock != null) {
                int newQuantity = stock.getQuantity() - 1;
                stockService.updateStockQuantity(newQuantity, stock.getId());
            }
            itemLendingService.updateBorrowedItem(selectedReservedItem, selectedReservedItemId);
        }
        setToAccept(false);
        setAccept(false);
        getAllReservedItemList();
    }

    public void cancelAcceptReservedItem(){
        setToAccept(false);
    }
    public void cancelReturnReservedItem(){
        setToReturn(false);
    }

    public void preReturnReservedItemD(Long id){
        setToReturn(true);
        selectedReservedItem = itemLendingService.getReservedItemById(id);
        List<BorrowedItemsDTO> filterList = borrowedItemList.stream()
                .filter(e -> e.getId().equals(id))
                .collect(Collectors.toList());
        if (filterList.size() !=0){
            selectedBorrowedItemsDTO = borrowedItemList.get(0);
        }
    }
    public void returnReservedItem(Long selectedReservedItemId){
        selectedReservedItem = itemLendingService.getReservedItemById(selectedReservedItemId);
        if (selectedReservedItem != null){
            selectedReservedItem.setReturned(isReturn());
            //Update stock quantity
            var stock = stockService.getStockByItemId(selectedReservedItem.getItem().getId());
            if (stock != null){
                int newQuantity = stock.getQuantity() + 1;
                stockService.updateStockQuantity(newQuantity, stock.getId());

            }
            itemLendingService.updateBorrowedItem(selectedReservedItem, selectedReservedItemId);
        }
        setToReturn(false);
        setReturn(false);
        getBorrowedItems(email);
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

    public boolean isAuthenticatedUserRole() {
        return authenticatedUserRole;
    }

    public void setAuthenticatedUserRole(boolean authenticatedUserRole) {
        this.authenticatedUserRole = authenticatedUserRole;
    }

    public boolean isAuthenticateAdminRole() {
        return authenticateAdminRole;
    }

    public void setAuthenticateAdminRole(boolean authenticateAdminRole) {
        this.authenticateAdminRole = authenticateAdminRole;
    }

    public List<BorrowedItemsDTO> getReservedItemListByUser() {
        return reservedItemListByUser;
    }

    public void setReservedItemListByUser(List<BorrowedItemsDTO> reservedItemListByUser) {
        this.reservedItemListByUser = reservedItemListByUser;
    }

    public List<ReservedItemDTO> getReservedAllItemListByAdmin() {
        return reservedAllItemListByAdmin;
    }

    public void setReservedAllItemListByAdmin(List<ReservedItemDTO> reservedAllItemListByAdmin) {
        this.reservedAllItemListByAdmin = reservedAllItemListByAdmin;
    }

    public ItemLending getSelectedReservedItem() {
        return selectedReservedItem;
    }

    public void setSelectedReservedItem(ItemLending selectedReservedItem) {
        this.selectedReservedItem = selectedReservedItem;
    }

    public ReservedItemDTO getSelectedReservedItemDTO() {
        return selectedReservedItemDTO;
    }

    public void setSelectedReservedItemDTO(ReservedItemDTO selectedReservedItemDTO) {
        this.selectedReservedItemDTO = selectedReservedItemDTO;
    }

    public boolean isToAccept() {
        return toAccept;
    }

    public void setToAccept(boolean toAccept) {
        this.toAccept = toAccept;
    }

    public boolean isToReturn() {
        return toReturn;
    }

    public void setToReturn(boolean toReturn) {
        this.toReturn = toReturn;
    }
    public BorrowedItemsDTO getSelectedBorrowedItemsDTO() {
        return selectedBorrowedItemsDTO;
    }
    public void setSelectedBorrowedItemsDTO(BorrowedItemsDTO selectedBorrowedItemsDTO) {
        this.selectedBorrowedItemsDTO = selectedBorrowedItemsDTO;
    }
    public boolean isAccept() {
        return isAccept;
    }
    public void setAccept(boolean accept) {
        isAccept = accept;
    }
    public boolean isReturn() {
        return isReturn;
    }
    public void setReturn(boolean aReturn) {
        isReturn = aReturn;
    }

    public String getContactToEmail() {
        return contactToEmail;
    }
    public void setContactToEmail(String contactToEmail) {
        this.contactToEmail = contactToEmail;
    }
    public String getContactSubject() {
        return contactSubject;
    }
    public void setContactSubject(String contactSubject) {
        this.contactSubject = contactSubject;
    }
    public String getContactMessage() {
        return contactMessage;
    }
    public void setContactMessage(String contactMessage) {
        this.contactMessage = contactMessage;
    }

    public String getContactFromEmail() {
        return contactFromEmail;
    }

    public void setContactFromEmail(String contactFromEmail) {
        this.contactFromEmail = contactFromEmail;
    }

    public List<SystemMessage> getSystemMessageList() {
        return systemMessageList;
    }

    public void setSystemMessageList(List<SystemMessage> systemMessageList) {
        this.systemMessageList = systemMessageList;
    }
    //</editor-fold>
}