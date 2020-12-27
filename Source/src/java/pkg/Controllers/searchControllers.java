package pkg.Controllers;

import pkg.Entities.Products;
import pkg.Entities.ProductsFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named("searchControllers")
@SessionScoped
public class searchControllers
        implements Serializable {

    @EJB
    private ProductsFacadeLocal productsFacade;
    private int pageSize = 6;
    private int page = 1;

    private List<Products> allProducts;
    private String search = "";

    private boolean clearSearch = false;

    public String getSearch() {
        if (this.clearSearch) {
            this.search = "";
        }
        return this.search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getPageSize() {
        if (getRequest().getParameter("pageSize") != null) {
            this.pageSize = Integer.parseInt(getRequest().getParameter("pageSize"));
        }
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public int getPage() {
        if (getRequest().getParameter("page") != null) {
            this.page = Integer.parseInt(getRequest().getParameter("page"));
        }
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        if (getPageSize() == -1) {
            return 1;
        }

        int numOfProducts = this.productsFacade.searchByName(this.search).size();
        if (numOfProducts == 0) {
            numOfProducts++;
        }

        if (numOfProducts % this.pageSize != 0) {
            return numOfProducts / this.pageSize + 1;
        }

        return numOfProducts / this.pageSize;
    }

    public List<Products> getSearchResult() {
        int pageFrom = (getPage() - 1) * getPageSize();
        int pageTo = pageFrom + this.pageSize;

        List<Products> searchResult = this.productsFacade.searchByName(this.search);

        if (this.pageSize == -1) {
            return searchResult;
        }

        if (pageTo > searchResult.size()) {
            pageTo = searchResult.size();
        }
        this.clearSearch = true;
        return searchResult.subList(pageFrom, pageTo);
    }

    public List<Products> searchProducts() {
        if (this.search.isEmpty() || this.search.trim().isEmpty()) {
            return null;
        }

        this.search = this.search.trim();

        this.clearSearch = false;
        if (this.allProducts == null) {
            this.allProducts = getProducts();
        }

        String openTabs = "<strong style='color: green;'><em>";
        String endTabs = "</em></strong>";
        List<Products> result = new ArrayList<Products>();
        for (Products p : this.allProducts) {
            if (p.getProductName().toLowerCase().contains(this.search.toLowerCase())) {
                Products temp = new Products();
                temp.setProductID(new String(p.getProductID()));
                temp.setProductImages(new String(p.getProductImages()));
                StringBuilder newName = new StringBuilder(p.getProductName());
                int index = 0;
                int k = p.getProductName().toLowerCase().split(this.search.toLowerCase()).length - 1;
                for (int i = 0; i < k; i++) {
                    index = newName.toString().toLowerCase().indexOf(this.search.toLowerCase(), index);
                    if (index == -1) {
                        break;
                    }
                    newName = newName.insert(index, openTabs);

                    index = newName.toString().toLowerCase().indexOf(this.search.toLowerCase(), index + openTabs.length()) + this.search.length();
                    newName = newName.insert(index, endTabs);
                    index += endTabs.length();
                }
                temp.setProductName(newName.toString());
                result.add(temp);
            }
        }
        return result;
    }

    public List<Products> getProducts() {
        List<Products> list = new ArrayList<Products>();
        for (Products p : this.productsFacade.findAll()) {
            if (p.getProductState().booleanValue()) {
                list.add(p);
            }
        }
        return list;
    }
}
