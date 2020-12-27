package pkg.Models;

import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import pkg.Entities.Products;

public class ComparedItemHelper {
  private HttpSession getSession() { return getRequest().getSession(); }


  
  private HttpServletRequest getRequest() { return (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest(); }

  
  public List<ComparedItem> getCompareList() {
    HttpSession session = getSession();
    if (session.getAttribute("compareList") == null) {
      return new ArrayList();
    }
    return (List)session.getAttribute("compareList");
  }

  
  public String addToCompare(Products currentProduct) {
    List<ComparedItem> compareList = getCompareList();
    for (ComparedItem c : compareList) {
      if (c.getProductID().equals(currentProduct.getProductID())) {
        return "compare";
      }
    } 
    compareList.add(new ComparedItem(currentProduct));
    
    getSession().setAttribute("compareList", compareList);
    return "compare";
  }
  
  public void removeComparedItem(String productID) {
    List<ComparedItem> compareList = getCompareList();
    for (ComparedItem c : compareList) {
      if (c.getProductID().equals(productID)) {
        compareList.remove(c);
        break;
      } 
    } 
    getSession().setAttribute("compareList", compareList);
  }
}
