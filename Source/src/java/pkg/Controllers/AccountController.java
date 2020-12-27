package pkg.Controllers;

import pkg.Entities.Customers;
import pkg.Entities.CustomersFacadeLocal;

import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@Named("accountController")
@SessionScoped
public class AccountController implements Serializable {

    @EJB
    private CustomersFacadeLocal customersFacade;
    private Customers newCustomer;
    private String newPasswordConfirm;
    private String username;
    private String password;

    public AccountController() {
        this.rememberMe = false;

        this.currentCustomer = null;

        isChecked();
        this.newCustomer = new Customers();
        this.newCustomer.setGender(Boolean.valueOf(true));
    }
    private boolean rememberMe;
    private Customers currentCustomer;
    private Part file;
    private String oldPass;
    private String newPass;
    private String confirmPass;

    //Check lan chay dau tien xem co cookie hay chua
    private void isChecked() {
        HttpServletRequest request = getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                String cName = cookie.getName();
                String cValue = cookie.getValue();
                System.out.println("cName: " + cName + "; cValue: " + cValue);
                switch (cName) {
                    case "cUsername":
                        this.username = cValue;
                        break;
                    case "cPassword":
                        this.password = cValue;
                        break;
                    case "cRememberMe":
                        System.out.println("isChecked: cRememeberMe = " + cValue);
                        this.rememberMe = cValue.toLowerCase().equals("true");
                        if (!this.rememberMe) {
                            this.username = null;
                            this.password = null;
                        }
                        break;
                }
            }
        }
    }

//    ============ Check Login =================
    public String checkLogin() {
        this.currentCustomer = this.customersFacade.checkLogin(this.username.trim(), this.password.trim());
//        Đối tượng session được sử dụng để theo dõi phiên của các request của client.
        HttpSession session = getSession();
        if (this.currentCustomer != null) {
            session.setAttribute("user", this.username.trim());
            //Chỉ định thời gian, tính bằng giây, giữa các yêu cầu của máy khách trước khi bộ chứa servlet sẽ vô hiệu hóa phiên này. 7200s = 120p
            session.setMaxInactiveInterval(7200);
            //su dung Cookie cRememberMe
            Cookie cRememberMe = new Cookie("cRememberMe", String.valueOf(this.rememberMe));
            HttpServletResponse response = getResponse();
            if (this.rememberMe) {
                Cookie cUserId = new Cookie("cUsername", this.username);
                Cookie cPassword = new Cookie("cPassword", this.password);
                //Đặt tuổi tối đa của cookie trong vài giây. <=> 43 200 phút
                int maxAge = 2592000;
                cUserId.setMaxAge(maxAge);
                cPassword.setMaxAge(maxAge);
                cRememberMe.setMaxAge(maxAge);

                response.addCookie(cUserId);
                response.addCookie(cPassword);
            }
            response.addCookie(cRememberMe);
            return "account";
        }
        FacesContext.getCurrentInstance().addMessage("signinForm", new FacesMessage("Wrong email or password."));

        return "signin";
    }

//    ====================== Logout ================
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        this.currentCustomer = null;
        this.username = "";
        this.password = "";
        this.rememberMe = false;
        return "index";
    }

    public String update() {
        this.customersFacade.edit(this.currentCustomer);
        return "profile";
    }

    public String changePassword() {
        if (this.currentCustomer.getPassword().equals(this.oldPass) && this.confirmPass.equals(this.newPass)) {
            this.currentCustomer.setPassword(this.newPass);

            this.customersFacade.edit(this.currentCustomer);

            this.oldPass = "";
            this.newPass = "";
            this.confirmPass = "";
            setErrorMessage("changePasswordFormSuccess", "Password changed successfully.");
            return "profile";
        }
        if (!this.currentCustomer.getPassword().equals(this.oldPass)) {
            setErrorMessage("changeOldPasswordWrong", "Old password is wrong.");
            return "profile";
        }
        setErrorMessage("changePasswordForm", "Confirm password does not match with New Password.");
        return "profile";
    }

    public String createCustomer() {
        for (Customers c : this.customersFacade.findAll()) {
            if (c.getEmail().equals(this.newCustomer.getEmail())) {
                setErrorMessage("profileForm", "Email [" + this.newCustomer.getEmail() + "] existed.");
                return "signup";
            }
        }
        if (!this.newPasswordConfirm.equals(this.newCustomer.getPassword())) {
            setErrorMessage("profileForm", "Confirm Password is not matched.");
            return "signup";
        }

        this.newCustomer.setCreatedDate(new Date());
        this.newCustomer.setCustomerState(Boolean.valueOf(true));

        this.customersFacade.create(this.newCustomer);

        this.newCustomer = new Customers();
        this.newPasswordConfirm = "";
        return "signin";
    }

    private void setErrorMessage(String uiID, String message) {
        if (uiID != null && message != null) {
            FacesContext.getCurrentInstance().addMessage(uiID, new FacesMessage(message));
        }
    }

    private HttpServletResponse getResponse() {
        return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    }

    private HttpSession getSession() {
        return getRequest().getSession();
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public String getOldPass() {
        return this.oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return this.newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getConfirmPass() {
        return this.confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

    public boolean isRememberMe() {
        return this.rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getUsername() {
        isChecked();
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Customers getCurrentCustomer() {
        return this.currentCustomer;
    }

    public Part getFile() {
        return this.file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Customers getNewCustomer() {
        return this.newCustomer;
    }

    public void setNewCustomer(Customers newCustomer) {
        this.newCustomer = newCustomer;
    }

    public String getNewPasswordConfirm() {
        return this.newPasswordConfirm;
    }

    public void setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
    }
}
