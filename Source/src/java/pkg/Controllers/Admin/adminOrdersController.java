/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.Controllers.Admin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import pkg.Entities.Orders;
import pkg.Entities.OrdersDetails;
import pkg.Entities.OrdersDetailsFacadeLocal;
import pkg.Entities.OrdersFacadeLocal;
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
@Named("adminOrdersController")
public class adminOrdersController implements Serializable {

    @EJB
    private OrdersDetailsFacadeLocal ordersDetailsFacade;
    private Orders editedOrder = null;
    @EJB
    private OrdersFacadeLocal ordersFacade;
    private Orders selectedOrder = null;

    public Orders getEditedOrder() {
        if (getRequest().getParameter("id") != null) {
            this.editedOrder = this.ordersFacade.find(getRequest().getParameter("id"));
        }
        return this.editedOrder;
    }

    public Orders getSelectedOrder() {
        if (getRequest().getParameter("id") != null) {
            this.selectedOrder = this.ordersFacade.find(getRequest().getParameter("id"));
        }
        return this.selectedOrder;
    }

    public List<OrdersDetails> getOD() {
        if (getSelectedOrder().getOrdersDetailsCollection() != null) {
            String action = getRequest().getParameter("action");
            String odId = getRequest().getParameter("odID");
            String state = getRequest().getParameter("state");
            if (action != null && odId != null && state != null && action.equals("disable")) {
                OrdersDetails disabledOD = this.ordersDetailsFacade.find(Integer.valueOf(Integer.parseInt(odId)));
                if (disabledOD != null && disabledOD.getOdState().toString().toLowerCase().equals(state.toLowerCase())) {
                    boolean currentState = disabledOD.getOdState().booleanValue();
                    disabledOD.setOdState(Boolean.valueOf(!currentState));
                    this.ordersDetailsFacade.edit(disabledOD);

                    Orders editedOrder = this.ordersFacade.find(disabledOD.getOrderID().getOrderID());
                    double newTotal = 0.0D;
                    for (OrdersDetails od : editedOrder.getOrdersDetailsCollection()) {
                        if (od.getOdState().booleanValue()) {
                            newTotal += od.getQuantity() * od.getSellingPrice();
                        }
                    }
                    editedOrder.setTotal(newTotal);
                    this.ordersFacade.edit(editedOrder);
                }
            }
            return new ArrayList(getSelectedOrder().getOrdersDetailsCollection());
        }
        return null;
    }

    public List<Orders> getOrders() {
        getSession().setAttribute("currentPage", "ordersAdmin");

        String action = getRequest().getParameter("action");
        String id = getRequest().getParameter("id");
        String state = getRequest().getParameter("state");
        if (action != null && id != null && state != null && action.equals("disable")) {
            Orders disabledOrders = this.ordersFacade.find(id);
            if (disabledOrders != null && !disabledOrders.getProcessStatus().equals("Completed") && disabledOrders.getOrderState().toString().toLowerCase().equals(state.toLowerCase())) {
                boolean currentState = disabledOrders.getOrderState().booleanValue();
                disabledOrders.setOrderState(Boolean.valueOf(!currentState));
                this.ordersFacade.edit(disabledOrders);
            }
        }

        return this.ordersFacade.findAll();
    }

    private HttpSession getSession() {
        return getRequest().getSession();
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public String update() {
        this.ordersFacade.edit(this.editedOrder);
        return "ordersView";
    }

    public List<String> getNotifications() {
        List<String> nots = new ArrayList<String>();
        int numOfProcessing = 0;
        int numOfShipping = 0;
        for (Orders o : this.ordersFacade.findAll()) {
            if (o.getProcessStatus().equals("Processing")) {
                numOfProcessing++;
            }
            if (o.getProcessStatus().equals("Shipping")) {
                numOfShipping++;
            }
        }
        if (numOfProcessing > 0) {
            nots.add("You have " + numOfProcessing + " Processing orders");
        }
        if (numOfShipping > 0) {
            nots.add("You have " + numOfShipping + " Shipping orders");
        }
        return nots;
    }
}
