package pkg.Controllers;

import pkg.Entities.Categories;
import pkg.Entities.CategoriesFacadeLocal;
import pkg.Entities.Customers;
import pkg.Entities.CustomersFacadeLocal;
import pkg.Entities.Products;
import pkg.Entities.ProductsFacadeLocal;
import pkg.Entities.RatingsFacadeLocal;
import pkg.Entities.Wishlist;
import pkg.Entities.WishlistFacadeLocal;
import pkg.Models.CartItem;
import pkg.Models.CartItemHelper;
import pkg.Models.ComparedItem;
import pkg.Models.ComparedItemHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lib.Result;

@Named("productController")
@SessionScoped
public class ProductController implements Serializable {

    @EJB
    private WishlistFacadeLocal wishlistFacade;
    @EJB
    private CustomersFacadeLocal customersFacade;
    @EJB
    private RatingsFacadeLocal ratingsFacade;

    public List<Products> getProducts() {
        List<Products> list = new ArrayList<Products>();
        for (Products p : this.productsFacade.findAll()) {
            if (p.getProductState().booleanValue()) {
                list.add(p);
            }
        }
        return list;
    }
    @EJB
    private CategoriesFacadeLocal categoriesFacade;
    @EJB
    private ProductsFacadeLocal productsFacade;
    private Products currentProduct;

    private Result getResult() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return (Result) request.getSession().getAttribute("trainingResult");
    }

    public List<Products> getRecommendedProducts() {
        if (getResult() == null) {
            return null;
        }

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request.getSession().getAttribute("user") == null) {
            return this.productsFacade.getProductsForGuest(getResult());
        }

        String mostRatedCategory = "";
        double maxRating = 0.0D;
        String email = request.getSession().getAttribute("user").toString();
        List<Categories> categories = this.categoriesFacade.findAll();

        for (Categories c : categories) {
            int numberOfRatings = this.ratingsFacade.getNumberOfRatings(email, c.getCategoryID());
            double avgRating = this.ratingsFacade.getAverageRatings(email, c.getCategoryID());
            if (numberOfRatings >= 3 && avgRating > 2.5D && maxRating < avgRating) {
                maxRating = avgRating;
                mostRatedCategory = c.getCategoryID();
            }
        }

        if (mostRatedCategory.isEmpty()) {
            maxRating = 0.0D;
            for (Categories c : categories) {
                double avgRating = this.ratingsFacade.getAverageRatings(email, c.getCategoryID());
                if (avgRating > 2.5D && maxRating < avgRating) {
                    maxRating = avgRating;
                    mostRatedCategory = c.getCategoryID();
                }
            }
        }

        if (mostRatedCategory.isEmpty()) {
            return this.productsFacade.getProductsForGuest(getResult());
        }
        return this.productsFacade.getProductsForUser(getResult(), email, mostRatedCategory);
    }

    public List<Products> getRelatedProducts() {
        if (getResult() == null || getCurrentProduct() == null) {
            return null;
        }
        return this.productsFacade.getRelatedProducts(getResult(), this.currentProduct.getProductID());
    }

    public List<Products> getFeaturedProducts(String feature) {
        return this.productsFacade.getFeaturedProducts(feature);
    }

    public Products getCurrentProduct() {
        HttpServletRequest request = getRequest();
        if (request.getParameter("productID") == null) {
            if (this.currentProduct != null) {
                return this.currentProduct;
            }
            return null;
        }
        if (this.currentProduct == null || !this.currentProduct.getProductID().equals(request.getParameter("productID"))) {
            this.currentProduct = this.productsFacade.find(request.getParameter("productID"));
            if (!this.currentProduct.getProductState().booleanValue()) {
                this.currentProduct = null;
            }
        }
        return this.currentProduct;
    }

    private int quantity = 1;

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private HttpSession getSession() {
        return getRequest().getSession();
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    private HttpServletResponse getResponse() {
        return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    }

    private CartItemHelper cartItemHelper = new CartItemHelper();

    public List<CartItem> getCart() {
        return this.cartItemHelper.getCart();
    }

    public String addToCart() {
        String user = (String) getSession().getAttribute("user");
        
        if(user == null){
            return "signin";
        }
        
        if (this.currentProduct == null) {
            return "404";
        }

        this.cartItemHelper.addToCart(this.currentProduct, this.quantity);
        this.quantity = 1;
        this.currentProduct = null;
        return "cart";
    }

    public double getTotal() {
        return this.cartItemHelper.getTotal();
    }

    public String removeCartItem(String productID) {
        this.cartItemHelper.removeCartItem(productID);
        return "cart";
    }

    private ComparedItemHelper comparedItemHelper = new ComparedItemHelper();
    private boolean checkCompare;

    public boolean isCheckCompare() {
        this.checkCompare = true;
        List<ComparedItem> list = getCompareList();
        if (list.size() > 0) {
            String cat = ((ComparedItem) list.get(0)).getCategory();
            String type = ((ComparedItem) list.get(0)).getType();
            if (!cat.equals(this.currentProduct.getCategoryID().getCategoryName()) || !type.equals(this.currentProduct.getTypeID().getTypeName())) {
                this.checkCompare = false;
            }
        }
        return this.checkCompare;
    }

    public List<ComparedItem> getCompareList() {
        return this.comparedItemHelper.getCompareList();
    }

    public String addToCompare() {
        if (this.currentProduct == null) {
            return "404";
        }

        String result = this.comparedItemHelper.addToCompare(this.currentProduct);
        this.currentProduct = null;
        return result;
    }

    public String removeComparedItem(String productID) {
        this.comparedItemHelper.removeComparedItem(productID);
        return "compare";
    }

    public String addToWishlist() {
        Customers customerEmail = this.customersFacade.find(getSession().getAttribute("user").toString());
        if (customerEmail == null || this.currentProduct == null) {
            return "404";
        }

        Wishlist item = this.wishlistFacade.find(customerEmail.getEmail(), this.currentProduct.getProductID());

        if (item == null) {
            item = new Wishlist(this.quantity, customerEmail, this.currentProduct);
            this.wishlistFacade.create(item);
        } else {
            item.setQuantity(this.quantity);
            this.wishlistFacade.edit(item);
        }
        this.currentProduct = null;
        this.quantity = 1;
        return "wishlist";
    }

    private List<String> addToRecentlyViewed() {
        if (this.currentProduct == null) {
            return null;
        }

        int maxAge = 2592000;

        List<String> recentlyViewed = null;

        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("recentlyViewed")) {
                    recentlyViewed = new ArrayList<String>(Arrays.asList(cookie.getValue().split(":")));
                }
            }
            if (recentlyViewed != null) {
                for (String s : recentlyViewed) {
                    if (this.currentProduct.getProductID().equals(s)) {
                        return recentlyViewed;
                    }
                }
                if (recentlyViewed.size() == 6) {
                    recentlyViewed.remove(5);
                }
                recentlyViewed.add(0, this.currentProduct.getProductID());
                StringBuilder newRecently = new StringBuilder("");
                for (String s : recentlyViewed) {
                    newRecently.append(":" + s);
                }
                Cookie cRecentlyViewed = new Cookie("recentlyViewed", newRecently.substring(1));
                cRecentlyViewed.setMaxAge(maxAge);
                getResponse().addCookie(cRecentlyViewed);
                return recentlyViewed;
            }
        }
        Cookie cRecentlyViewed = new Cookie("recentlyViewed", this.currentProduct.getProductID());
        cRecentlyViewed.setMaxAge(maxAge);
        getResponse().addCookie(cRecentlyViewed);
        recentlyViewed = new ArrayList<String>();
        recentlyViewed.add(this.currentProduct.getProductID());
        return recentlyViewed;
    }

    public List<Products> getRecentlyViewed() {
        List<Products> result = new ArrayList<Products>();

        List<String> productIDs = addToRecentlyViewed();
        if (productIDs == null) {
            return null;
        }

        for (String s : productIDs) {
            result.add(this.productsFacade.find(s));
        }

        return result;
    }
}
