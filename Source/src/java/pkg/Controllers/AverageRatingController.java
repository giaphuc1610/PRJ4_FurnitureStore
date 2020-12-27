package pkg.Controllers;

import pkg.Entities.AverageRatings;
import pkg.Entities.AverageRatingsFacadeLocal;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("averageRatingController")
@SessionScoped
public class AverageRatingController
        implements Serializable {

    @EJB
    private AverageRatingsFacadeLocal averageRatingsFacade;
    private List<AverageRatings> all;

    public List<AverageRatings> getAll() {
        if (this.all == null) {
            this.all = this.averageRatingsFacade.findAll();
        }
        return this.all;
    }

    public double getAverageRating(String productID) {
        return getNotRoundedAverageRating(productID) + 0.5D;
    }

    public double getNotRoundedAverageRating(String productID) {
        return this.averageRatingsFacade.getAverageRating(productID);
    }
}
