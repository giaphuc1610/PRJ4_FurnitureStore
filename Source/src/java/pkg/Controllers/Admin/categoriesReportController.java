package pkg.Controllers.Admin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import pkg.Entities.Categories;
import pkg.Entities.CategoriesFacadeLocal;
import pkg.Entities.OrdersDetails;
import pkg.Entities.Products;
import pkg.Models.CategoryReport;
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
@Named("categoriesReportController")
public class categoriesReportController implements Serializable {

    @EJB
    private CategoriesFacadeLocal categoriesFacade;
    private double total;

    public double getTotal() {
        return this.total;
    }

    private HttpSession getSession() {
        return ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();
    }

    public String toCategories() {
        HttpSession session = getSession();
        session.setAttribute("currentPage", "reports");
        session.setAttribute("currentPageChild", "categories");
        return "reportByCategories";
    }

    public List<CategoryReport> getData() {
        List<CategoryReport> data = new ArrayList<CategoryReport>();

        this.total = 0.0D;
        for (Categories c : this.categoriesFacade.findAll()) {
            String category = c.getCategoryName();
            
            double income = 0.0D;
            for (Products p : c.getProductsCollection()) {
                
                for (OrdersDetails od : p.getOrdersDetailsCollection()) {
                    income += od.getSellingPrice() * od.getQuantity();
                }
                
            }
            data.add(new CategoryReport(category, income));
            this.total += income;
        }

        for (CategoryReport cr : data) {
            cr.setProportion(cr.getIncome() / this.total);
        }
        return data;
    }

    public int numOfCats() {
        return this.categoriesFacade.count();
    }
}
