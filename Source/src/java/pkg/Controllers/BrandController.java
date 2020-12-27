package pkg.Controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import pkg.Entities.Brands;
import pkg.Entities.BrandsFacadeLocal;

@Named("brandController")
@SessionScoped
public class BrandController
        implements Serializable {

    @EJB
    private BrandsFacadeLocal brandsFacade;

    public List<Brands> getBrands() {
        List<Brands> list = new ArrayList<Brands>();
        for (Brands b : this.brandsFacade.findAll()) {
            if (b.getBrandState().booleanValue()) {
                list.add(b);
            }
        }
        return list;
    }
}
