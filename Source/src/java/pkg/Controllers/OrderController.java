package pkg.Controllers;

import pkg.Entities.Customers;
import pkg.Entities.CustomersFacadeLocal;
import pkg.Entities.Orders;
import pkg.Entities.OrdersDetails;
import pkg.Entities.OrdersDetailsFacadeLocal;
import pkg.Entities.OrdersFacadeLocal;
import pkg.Entities.ProductsFacadeLocal;
import pkg.Models.CartItem;
import pkg.Models.CartItemHelper;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named("orderController")
@SessionScoped
public class OrderController implements Serializable {

    @EJB
    private OrdersDetailsFacadeLocal ordersDetailsFacade;
    @EJB
    private ProductsFacadeLocal productsFacade;
    private Orders currentOrders = new Orders();
    @EJB
    private OrdersFacadeLocal ordersFacade;
    @EJB
    private CustomersFacadeLocal customersFacade;
    private String cardName = "Customer Name";
    private String cardNumber = "0000 0000 0000 0000";
    private String cardExpireMonth = "MM";
    private String cardExpireYear = "YY";

    public String continueShippingInfo() {
        Date selectedDate;
        Calendar cal = Calendar.getInstance();

        cal.setTime(new Date());
        cal.add(5, 2);
        Date defaultDate = cal.getTime();

        DateFormat df = DateFormat.getDateInstance(3, new Locale("vi", "VN"));

        try {
            selectedDate = df.parse(this.currentOrders.getShipDate());
        } catch (ParseException ex) {
            selectedDate = defaultDate;
        }

        if (selectedDate.before(defaultDate)) {
            cal.add(5, 1);
            this.currentOrders.setShipDate(df.format(cal.getTime()));
            FacesContext.getCurrentInstance().addMessage("shippingInfoForm", new FacesMessage("Shipping Date must be at least 3 days from today."));
            return "checkout";
        }
        return "checkoutPayment";
    }

    public String createOrder() {
        CartItemHelper cartHelper = new CartItemHelper();
        List<CartItem> cart = cartHelper.getCart();
        if (cart.isEmpty()) {
            return "404";
        }
        String email = "guest@mail.com";
        Customers currentCustomers = new Customers(email);
        if (getSession().getAttribute("user") != null) {
            currentCustomers = this.customersFacade.find(getSession().getAttribute("user").toString());
        }
        String orderID = this.ordersFacade.getNewID();
        this.currentOrders.setCustomerEmail(currentCustomers);
        this.currentOrders.setOrderID(orderID);
        this.currentOrders.setTotal(cartHelper.getTotal());
        this.ordersFacade.create(this.currentOrders);

        for (CartItem c : cart) {
            OrdersDetails od = new OrdersDetails(c.getPrice() * 0.9D, c.getQuantity(), this.currentOrders, this.productsFacade.find(c.getProductID()));
            this.ordersDetailsFacade.create(od);
        }

        cartHelper.removeAllCartItem();
        this.currentOrders = new Orders();
        return "orderSuccess";
    }

    public List<Orders> getOrders() {
        return this.ordersFacade.getOrdersByUser(getSession().getAttribute("user").toString());
    }

    public List<Orders> getUncompletedOrders() {
        return this.ordersFacade.getUncompletedOrdersByUser(getSession().getAttribute("user").toString());
    }

    private HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

    public Orders getCurrentOrders() {
        return this.currentOrders;
    }

    public void setCurrentOrders(Orders currentOrders) {
        this.currentOrders = currentOrders;
    }

    public String getCardName() {
        return this.cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpireMonth() {
        return this.cardExpireMonth;
    }

    public void setCardExpireMonth(String cardExpireMonth) {
        this.cardExpireMonth = cardExpireMonth;
    }

    public String getCardExpireYear() {
        return this.cardExpireYear;
    }

    public void setCardExpireYear(String cardExpireYear) {
        this.cardExpireYear = cardExpireYear;
    }
}
