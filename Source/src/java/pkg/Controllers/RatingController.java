package pkg.Controllers;

import pkg.Entities.Customers;
import pkg.Entities.CustomersFacadeLocal;
import pkg.Entities.Products;
import pkg.Entities.ProductsFacadeLocal;
import pkg.Entities.Ratings;
import pkg.Entities.RatingsFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lib.PreparedData;
import lib.RatingModel;
import lib.RecommenderSystem;
import lib.Result;


@Named("ratingController")
@SessionScoped
public class RatingController
        implements Serializable {

    @EJB
    private CustomersFacadeLocal customersFacade;
    @EJB
    private ProductsFacadeLocal productsFacade;
    @EJB
    private RatingsFacadeLocal ratingsFacade;
    private Ratings currentRating;
    private int rating;
    private String productID;

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getRatingString(String productID) {
        this.productID = productID;
        this.currentRating = this.ratingsFacade.getRating(getCustomerEmail(), productID);
        this.rating = (this.currentRating == null) ? 0 : this.currentRating.getRate();
        return (this.rating == 0) ? "Not rating yet" : (this.rating + " stars");
    }

    public String getCustomerEmail() {
        return getSession().getAttribute("user").toString();
    }

    private HttpSession getSession() {
        return getRequest().getSession();
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public String submitRating() {
        Customers customer = this.customersFacade.find(getCustomerEmail());
        Products product = this.productsFacade.find(this.productID);
        if (this.currentRating == null) {
            this.ratingsFacade.create(new Ratings(customer, product, this.rating));
        } else {
            this.currentRating.setRate(this.rating);
            this.ratingsFacade.edit(this.currentRating);
        }

        List<RatingModel> ratingModel = new ArrayList<RatingModel>();
        for (Ratings rating : this.ratingsFacade.findAll()) {
            String itemId = rating.getProductID().getProductID();
            String userId = rating.getCustomerEmail().getEmail();
            byte rate = (byte) rating.getRate();
            ratingModel.add(new RatingModel(itemId, userId, rate));
        }

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        double lambda = (request.getSession().getAttribute("lambda") != null) ? ((Double) request.getSession().getAttribute("lambda")).doubleValue() : 0.0D;
        int num_iters = (request.getSession().getAttribute("num_iters") != null) ? ((Integer) request.getSession().getAttribute("num_iters")).intValue() : 30;
        int num_features = (request.getSession().getAttribute("num_features") != null) ? ((Integer) request.getSession().getAttribute("num_features")).intValue() : 12;
        Result result = (new RecommenderSystem(new PreparedData(ratingModel, 0.7D, 0.2D))).train(lambda, num_iters, num_features);
        request.getSession().setAttribute("trainingResult", result);

        return "singleProduct";
    }

    public int getNumberOfRating(String productID) {
        return this.ratingsFacade.getNumberOfRatings(productID);
    }
}
