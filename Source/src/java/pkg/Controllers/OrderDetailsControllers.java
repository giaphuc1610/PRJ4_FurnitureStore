package pkg.Controllers;


import pkg.Entities.Orders;
import pkg.Entities.OrdersDetails;
import pkg.Entities.OrdersDetailsFacadeLocal;
import pkg.Entities.OrdersFacadeLocal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named("orderDetailsControllers")
@SessionScoped
public class OrderDetailsControllers
        implements Serializable {

    @EJB
    private OrdersDetailsFacadeLocal ordersDetailsFacade;
    @EJB
    private OrdersFacadeLocal ordersFacade;
    private Orders currentOrder;

    public Orders getCurrentOrder() {
        if (getRequest().getParameter("orderID") != null) {
            Orders tempOrder = this.ordersFacade.find(getRequest().getParameter("orderID"));
            if (tempOrder != null && tempOrder.getOrderState().booleanValue()) {
                this.currentOrder = tempOrder;
            }
        }
        return this.currentOrder;
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public List<OrdersDetails> getOrdersDetails() {
        List<OrdersDetails> list = new ArrayList<OrdersDetails>();
        for (OrdersDetails od : this.ordersDetailsFacade.getOrdersDetails(getCurrentOrder().getOrderID())) {
            if (od.getOdState().booleanValue()) {
                list.add(od);
            }
        }
        return list;
    }
}
