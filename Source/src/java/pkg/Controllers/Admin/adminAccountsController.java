/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.Controllers.Admin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import pkg.Entities.Admins;
import pkg.Entities.AdminsFacadeLocal;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@ManagedBean
@SessionScoped
@Named("adminAccountsController")
public class adminAccountsController implements Serializable {

    private Admins currentAdmin = null;
    @EJB
    private AdminsFacadeLocal adminsFacade;
    private Admins editedAdmin = null;
    private Admins newAdmin = new Admins();
    private String email;
    private String password;
    private String oldPass;
    private String newPass;
    private String confirmPass;

    public List<Admins> getAdminAccounts() {
       getSession().setAttribute("currentPage", "accountsAdmin");
        getSession().setAttribute("currentPageChild", "");

        String action = getRequest().getParameter("action");
        String id = getRequest().getParameter("id");
        String state = getRequest().getParameter("state");
        if (action != null && id != null && state != null && action.equals("disabledAdmins")) {
            Admins disabledAdmins = this.adminsFacade.find(id);
            if (disabledAdmins != null && disabledAdmins.getAdminState().toString().toLowerCase().equals(state.toLowerCase())) {
                boolean currentState = disabledAdmins.getAdminState().booleanValue();
                disabledAdmins.setAdminState(Boolean.valueOf(!currentState));
                this.adminsFacade.remove(disabledAdmins);
            }
        }

        return this.adminsFacade.findAll();
    }

    public String toCreate() {
        getSession().setAttribute("currentPage", "accountsAdmin");
        getSession().setAttribute("currentPageChild", "accountsCreate");
        return "accountsCreate";
    }

    public String toProfile() {
        getSession().setAttribute("currentPage", "profile");
        getSession().removeAttribute("currentPageChild");
        return "adminProfile";
    }

    public String checkLogin() {
        this.currentAdmin = this.adminsFacade.checkLogin(this.email.trim(), this.password);
        if (this.currentAdmin != null && this.currentAdmin.getAdminState().booleanValue()) {
            HttpSession session = getSession();
            session.setAttribute("admin", this.email.trim());
            session.setMaxInactiveInterval(7200);
            session.setAttribute("currentPage", "reports");
            session.setAttribute("currentPageChild", "categories");
            return "Admin/reportByCategories";
        }
        FacesContext.getCurrentInstance().addMessage("loginForm", new FacesMessage("Wrong email or password."));
        this.email = "";
        this.password = "";
        return "login";
    }

    public String logout() {
        getSession().removeAttribute("admin");
        this.currentAdmin = null;
        this.email = "";
        this.password = "";
        return "/login";
    }

    public String create() {
        String email = this.newAdmin.getFullName().replaceAll(" ", "") + "@mail.com";
        for (Admins a : this.adminsFacade.findAll()) {
            if (a.getEmail().equals(email)) {
                email = a.getEmail().replaceAll("@", this.adminsFacade.count() + "@");

                break;
            }
        }
        this.newAdmin.setEmail(email);
        this.newAdmin.setPassword("admin");
        this.newAdmin.setCreatedDate(new Date());
        this.newAdmin.setAdminState(Boolean.valueOf(true));
        this.adminsFacade.create(this.newAdmin);

        this.newAdmin = new Admins();
        return "accountsView";
    }

    public String update() {
        this.adminsFacade.edit(this.editedAdmin);
        this.editedAdmin = null;
        return "accountsView";
    }

    public String updateProfile() {        
        this.adminsFacade.edit(this.currentAdmin);
        return "adminProfile";
    }

    public String changePassword() {
        if (this.currentAdmin.getPassword().equals(this.oldPass) && this.confirmPass.equals(this.newPass)) {
            this.currentAdmin.setPassword(this.newPass);

            this.adminsFacade.edit(this.currentAdmin);

            this.oldPass = "";
            this.newPass = "";
            this.confirmPass = "";
            setErrorMessage("changePasswordForm", "Password changed successfully.");
            return "adminProfile";
        }
        if (!this.currentAdmin.getPassword().equals(this.oldPass)) {
            setErrorMessage("changePasswordForm", "Old password is wrong.");
            return "adminProfile";
        }
        setErrorMessage("changePasswordForm", "Confirm Password does not match with New Password.");
        return "adminProfile";
    }

    private void setErrorMessage(String uiID, String message) {
        if (uiID != null && message != null) {
            FacesContext.getCurrentInstance().addMessage(uiID, new FacesMessage(message));
        }
    }
    
    private HttpSession getSession() {
        return getRequest().getSession();
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public Admins getEditedAdmins() {
        if (getRequest().getParameter("id") != null) {
            this.editedAdmin = this.adminsFacade.find(getRequest().getParameter("id"));
        }
        return this.editedAdmin;
    }

    public Admins getCurrentAdmin() {
        return this.currentAdmin;
    }

    public Admins getNewAdmin() {
        return this.newAdmin;
    }

    public void setNewAdmin(Admins admin) {
        this.newAdmin = admin;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
