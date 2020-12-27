package pkg.Controllers.Admin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import pkg.Entities.Brands;
import pkg.Entities.BrandsFacadeLocal;
import pkg.Entities.OrdersDetails;
import pkg.Entities.Products;
import pkg.Models.BrandReport;
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
@Named("brandsReportController")
public class brandsReportController implements Serializable {

    @EJB
    private BrandsFacadeLocal brandsFacade;
    private double total;

    public double getTotal() {
        return this.total;
    }

    private HttpSession getSession() {
        return ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();
    }

    public String toBrands() {
        HttpSession session = getSession();
        session.setAttribute("currentPage", "reports");
        session.setAttribute("currentPageChild", "brands");
        return "reportByBrands";
    }

    public List<BrandReport> getData() {
        List<BrandReport> data = new ArrayList<BrandReport>();
        this.total = 0.0D;
        for (Brands b : this.brandsFacade.findAll()) {
            String brand = b.getBrandName();
            double income = 0.0D;
            for (Products p : b.getProductsCollection()) {
                for (OrdersDetails od : p.getOrdersDetailsCollection()) {
                    income += od.getSellingPrice() * od.getQuantity();
                }
            }
            data.add(new BrandReport(brand, income));
            this.total += income;
        }
        return data;
    }

    public int numOfBrands() {
        return this.brandsFacade.count();
    }
}
