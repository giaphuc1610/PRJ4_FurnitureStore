package pkg.Controllers.Admin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import pkg.Entities.Types;
import pkg.Entities.TypesFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
@Named("adminTypesController")
public class adminTypesController implements Serializable {

    @EJB
    private TypesFacadeLocal typesFacade;
    private Types newType = new Types();
    private Types selectedType = null;

    String oldName;

    public String toCreate() {
        getSession().setAttribute("currentPage", "typesAdmin");
        getSession().setAttribute("currentPageChild", "typesCreate");
        return "typesCreate";
    }

    public List<Types> getTypes() {
        getSession().setAttribute("currentPage", "typesAdmin");
        getSession().setAttribute("currentPageChild", "typesView");

        String action = getRequest().getParameter("action");
        String id = getRequest().getParameter("id");
        String state = getRequest().getParameter("state");
        if (action != null && id != null && state != null && action.equals("disable")) {
            Types deletetype = this.typesFacade.find(id);
            if (deletetype != null && deletetype.getTypeState().toString().toLowerCase().equals(state.toLowerCase())) {
                boolean currentState = deletetype.getTypeState().booleanValue();
                deletetype.setTypeState(Boolean.valueOf(!currentState));
                this.typesFacade.edit(deletetype);
            }
        }

        return this.typesFacade.findAll();
    }

    public String create() {
        for (Types t : this.typesFacade.findAll()) {
            if (t.getTypeName().equalsIgnoreCase(this.newType.getTypeName())) {
                FacesContext.getCurrentInstance().addMessage("profileForm", new FacesMessage("Type Name existed."));
                return "typesCreate";
            }
        }
        String typeID = "";
        Integer numOfTypes = Integer.valueOf(this.typesFacade.count() + 1);
        switch (numOfTypes.toString().length()) {
            case 1:
                typeID = "TY00" + numOfTypes;
                break;
            case 2:
                typeID = "TY0" + numOfTypes;
                break;
            case 3:
                typeID = "TY" + numOfTypes;
                break;
        }

        this.newType.setTypeID(typeID);
        this.newType.setTypeState(Boolean.valueOf(true));

        this.typesFacade.create(this.newType);
        this.newType = new Types();
        return "typesView";
    }

    public String update() {
        if (!this.oldName.equalsIgnoreCase(this.selectedType.getTypeName())) {
            for (Types t : this.typesFacade.findAll()) {
                if (t.getTypeName().equalsIgnoreCase(this.selectedType.getTypeName())) {
                    FacesContext.getCurrentInstance().addMessage("profileForm", new FacesMessage("Type Name existed."));
                    return "typesUpdate";
                }
            }
        }
        this.typesFacade.edit(this.selectedType);
        this.selectedType = null;
        return "typesView";
    }

    public Types getSelectedType() {
        if (getRequest().getParameter("id") != null) {
            this.selectedType = this.typesFacade.find(getRequest().getParameter("id"));
            this.oldName = new String(this.selectedType.getTypeName());
        }

        return this.selectedType;
    }

    private HttpSession getSession() {
        return getRequest().getSession();
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public Types getNewType() {
        return this.newType;
    }

    public void setNewType(Types newType) {
        this.newType = newType;
    }
}
