package pkg.Models;

import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import pkg.Entities.Products;

public class CartItemHelper {
  private HttpSession getSession() { return getRequest().getSession(); }


  
  private HttpServletRequest getRequest() { return (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest(); }

  
  public List<CartItem> getCart() {
    HttpSession session = getSession();
    if (session.getAttribute("cart") == null) {
      return new ArrayList();
    }
    List<CartItem> cart = (List)session.getAttribute("cart");
    
    if (getRequest().getParameter("newQuantity") != null) {
      String productID = getRequest().getParameter("productID");
      int newQuantity = Integer.valueOf(getRequest().getParameter("newQuantity")).intValue();
      for (CartItem c : cart) {
        if (c.getProductID().equals(productID)) {
          c.setQuantity(newQuantity);
          
          break;
        } 
      } 
    } 
    getSession().setAttribute("cart", cart);
    
    return cart;
  }

  
  public void addToCart(Products currentProduct, int quantity) {
    List<CartItem> cart = getCart();
    boolean isNotFound = true;
    for (CartItem c : cart) {
      if (c.getProductID().equals(currentProduct.getProductID())) {
        c.setQuantity(c.getQuantity() + quantity);
        isNotFound = false;
        break;
      } 
    } 
    if (isNotFound) {
      cart.add(new CartItem(currentProduct, quantity));
    }
    
    getSession().setAttribute("cart", cart);
  }
  
  public double getTotal() {
    double sum = 0.0D;
    for (CartItem c : getCart()) {
      sum += c.getPrice() * 0.9D * c.getQuantity();
    }
    return sum;
  }
  
  public void removeCartItem(String productID) {
    List<CartItem> cart = getCart();
    for (CartItem c : cart) {
      if (c.getProductID().equals(productID)) {
        cart.remove(c);
        break;
      } 
    } 
    getSession().setAttribute("cart", cart);
  }

  
  public void removeAllCartItem() { getSession().removeAttribute("cart"); }
}
