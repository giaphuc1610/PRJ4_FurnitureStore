package pkg.Controllers.Admin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import pkg.Entities.Customers;
import pkg.Entities.CustomersFacadeLocal;
import pkg.Entities.Orders;
import pkg.Entities.OrdersDetails;
import pkg.Models.CustomerReport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
@Named("customersReportController")
public class customersReportController implements Serializable {

    @EJB
    private CustomersFacadeLocal customersFacade;
    private double total;

    public double getTotal() {
        return this.total;
    }

    private HttpSession getSession() {
        return ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();
    }

    public String toCustomers() {
        HttpSession session = getSession();
        session.setAttribute("currentPage", "reports");
        session.setAttribute("currentPageChild", "customers");
        return "reportByCustomers";
    }

    public List<CustomerReport> getData() {
        List<CustomerReport> data = new ArrayList<CustomerReport>();
        this.total = 0.0D;
        for (Customers c : this.customersFacade.findAll()) {
            String email = c.getEmail();
            String name = c.getFirstName();
            double income = 0.0D;
            for (Orders o : c.getOrdersCollection()) {
                for (OrdersDetails od : o.getOrdersDetailsCollection()) {
                    income += od.getSellingPrice() * od.getQuantity();
                }
            }
            if (income > 0.0D) {
                data.add(new CustomerReport(email, name, income));
            }
        }
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.size(); j++) {
                if (((CustomerReport) data.get(i)).getIncome() > ((CustomerReport) data.get(j)).getIncome()) {
                    CustomerReport temp = new CustomerReport((CustomerReport) data.get(i));
                    data.set(i, new CustomerReport((CustomerReport) data.get(j)));
                    data.set(j, new CustomerReport(temp));
                }
            }
        }
        int endIndex = (data.size() >= 15) ? 16 : data.size();
        for (CustomerReport cr : data.subList(1, endIndex)) {
            this.total += cr.getIncome();
        }
        return data.subList(1, endIndex);
    }

    public int numOfCusts() {
        return getData().size();
    }
}
