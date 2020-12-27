package pkg.Controllers.Admin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import pkg.Entities.AverageRatings;
import pkg.Entities.Customers;
import pkg.Entities.ItemsHelper;
import pkg.Entities.Ratings;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lib.*;
import pkg.Entities.AverageRatingsFacadeLocal;
import pkg.Entities.CustomersFacadeLocal;
import pkg.Entities.CustomersHelper;
import pkg.Entities.RatingsFacadeLocal;

@Named("recommenderController")
@ManagedBean
@SessionScoped
public class RecommenderController {

    private double lambda = 0.0D;
    private int num_features = 20;
    private int num_iters = 15;

    private List<AverageRatings> items;
    private List<Customers> customers;
    private AverageRatings item;
    private Customers customer;
    private int num_items = 0;

    private Result result;

    private List<AverageRatings> moviesForGuest;

    private List<AverageRatings> moviesForUser;

    private List<AverageRatings> relatedMovies;

    private PreparedData pd;

    @EJB
    private AverageRatingsFacadeLocal averageRatingsFacade;

    @EJB
    private RatingsFacadeLocal ratingsFacade;
    @EJB
    private CustomersFacadeLocal customersFacade;

    public double getLambda() {
        return this.lambda;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public int getNum_features() {
        return this.num_features;
    }

    public void setNum_features(int num_features) {
        this.num_features = num_features;
    }

    public int getNum_iters() {
        return this.num_iters;
    }

    public void setNum_iters(int num_iters) {
        this.num_iters = num_iters;
    }

    public Result getResult() {
        return this.result;
    }

    public int getNum_items() {
        return this.num_items;
    }

    public void setNum_items(int num_items) {
        this.num_items = num_items;
    }

    public List<Customers> getCustomers() {
        if (this.customers == null) {
            this.customers = this.customersFacade.findAll();
        }
        return this.customers;
    }

    public Customers getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public List<AverageRatings> getItems() {
        if (this.items == null) {
            this.items = this.averageRatingsFacade.findAll();
        }
        return this.items;
    }

    public AverageRatings getItem() {
        return this.item;
    }

    public List<AverageRatings> getMoviesForGuest() {
        if (this.num_items == 0) {
            return null;
        }
        try {
            Map<String, Double> topYmean = this.result.getMapOfItemsForGuest(this.num_items);
            List<String> itemsID = new ArrayList<String>(topYmean.keySet());

            this.moviesForGuest = ItemsHelper.findItems(getItems(), itemsID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.moviesForGuest;
    }

    public List<AverageRatings> getMoviesForUser() {
        try {
            if (this.num_items > 0 && this.customer != null) {
                Map<String, Double> predicts = this.result.getItemsForUser(this.num_items, this.customer.getEmail());
                this.moviesForUser = new ArrayList();

                for (String s : predicts.keySet()) {
                    AverageRatings ar = ItemsHelper.findItem(getItems(), s);
                    ar.setAverageRating(((Double) predicts.get(s)).doubleValue());
                    this.moviesForUser.add(ar);
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.moviesForUser;
    }

    public List<AverageRatings> getRelatedMovies() {
        try {
            if (this.num_items > 0 && this.item != null) {
                this.relatedMovies = ItemsHelper.findItems(getItems(), this.result.getRelatedItems(this.num_items, this.item.getProductID()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.relatedMovies;
    }

    private void prepareDataFromDatabase() {
        List<RatingModel> ratingModel = new ArrayList<RatingModel>();
        for (Ratings rating : this.ratingsFacade.findAll()) {
            String itemId = rating.getProductID().getProductID();
            String userId = rating.getCustomerEmail().getEmail();
            byte rate = (byte) rating.getRate();
            ratingModel.add(new RatingModel(itemId, userId, rate));
        }

        this.pd = new PreparedData(ratingModel, 0.7D, 0.2D);
    }

    private void prepareDataFromFiles() {
        this.pd = new PreparedData("d:/Aptech/Y.txt", "d:/Aptech/R.txt", "d:/Aptech/users.txt", "d:/Aptech/items.txt");
    }

    private List<Double> errorTraining = new ArrayList();
    private List<Double> errorCV = new ArrayList();

    public String recommenderSystem(boolean fromFile) {
        if (fromFile) {
            prepareDataFromFiles();
        } else {
            prepareDataFromDatabase();
        }
        RecommenderSystem rs = new RecommenderSystem(this.pd);
        for (int i = 1; i <= this.num_features; i++) {
            this.result = rs.train(this.lambda, this.num_iters, i);
            this.errorTraining.add(Double.valueOf(this.result.getTrainingError()));
            this.errorCV.add(Double.valueOf(this.result.getCVError()));
        }

        this.result.exportData("d:\\");
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.getSession().setAttribute("trainingResult", this.result);
        request.getSession().setAttribute("lambda", Double.valueOf(this.lambda));
        request.getSession().setAttribute("num_iters", Integer.valueOf(this.num_iters));
        request.getSession().setAttribute("num_features", Integer.valueOf(this.num_features));

        return "RecommenderSystem";
    }

    public void exportTrainingData() {
        if (this.pd == null) {
            prepareDataFromDatabase();
        }
        this.pd.exportData("d:/Aptech/");
    }

    public List<Double> getCostHistory() {
        if (this.result != null) {
            return this.result.getCostHistory();
        }
        return null;
    }

    public String onNumMoviesChanged(ValueChangeEvent event) {
        this.num_items = Integer.parseInt(event.getNewValue().toString());
        getMoviesForGuest();
        getMoviesForUser();
        getRelatedMovies();
        return "index";
    }

    public String onSelectCustomer(ValueChangeEvent event) {
        this.customer = CustomersHelper.findCustomer(getCustomers(), event.getNewValue().toString());
        getMoviesForUser();
        return "index";
    }

    public String onSelectMovie(ValueChangeEvent event) {
        this.item = ItemsHelper.findItem(getItems(), event.getNewValue().toString());
        getRelatedMovies();
        return "index";
    }

    private HttpSession getSession() {
        return ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();
    }

    public String toRS() {
        HttpSession session = getSession();
        session.setAttribute("currentPage", "RS");
        return "RecommenderSystem";
    }

    public List<Double> getErrorTraining() {
        return this.errorTraining;
    }

    public List<Double> getErrorCV() {
        return this.errorCV;
    }
}
