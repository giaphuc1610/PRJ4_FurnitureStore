package pkg.Controllers.Admin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import pkg.Entities.Customers;
import pkg.Entities.CustomersFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
@Named("adminCustomerController")
public class adminCustomerController implements Serializable {

    @EJB
    private CustomersFacadeLocal customersFacade;

    public List<Customers> getCustomers() {
        getSession().setAttribute("currentPage", "customersAdmin");
        getSession().setAttribute("currentPageChild", "accountsView");

          String action = getRequest().getParameter("action");
        String id = getRequest().getParameter("id");
        String state = getRequest().getParameter("state");
        if (action != null && id != null && state != null && action.equals("disable")) {
            Customers disabledCustomers = this.customersFacade.find(id);
            if (disabledCustomers != null && disabledCustomers.getCustomerState().toString().toLowerCase().equals(state.toLowerCase())) {
                boolean currentState = disabledCustomers.getCustomerState().booleanValue();
                disabledCustomers.setCustomerState(Boolean.valueOf(!currentState));
                this.customersFacade.edit(disabledCustomers);
            }
        }


        return this.customersFacade.findAll();
    }

    private HttpSession getSession() {
        return getRequest().getSession();
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }
}
