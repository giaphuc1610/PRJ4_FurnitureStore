package pkg.Controllers;

import pkg.Entities.Brands;
import pkg.Entities.BrandsFacadeLocal;
import pkg.Entities.Categories;
import pkg.Entities.CategoriesFacadeLocal;
import pkg.Entities.Products;
import pkg.Entities.ProductsFacadeLocal;
import pkg.Entities.Types;
import pkg.Entities.TypesFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named("filterController")
@SessionScoped
public class filterController
        implements Serializable {

    @EJB
    private ProductsFacadeLocal productsFacade;
    @EJB
    private BrandsFacadeLocal brandsFacade;
    @EJB
    private TypesFacadeLocal typesFacade;
    @EJB
    private CategoriesFacadeLocal categoriesFacade;
    private Categories currentCat;
    private Types currentType;
    private Brands currentBrand;
    private int from = 0;
    private int to = 0;
    private int pageSize = 6;
    private int page = 1;
    private int pages = 0;

    public long getPages() {
        long totalPages = 0L;
        if (getCurrentCat() != null) {
            totalPages = this.productsFacade.byCatType(getCurrentCat().getCategoryID(), getCurrentType().getTypeID());
        } else if (getCurrentBrand() != null) {
            totalPages = this.currentBrand.getProductsCollection().size();
        } else {
            totalPages = this.productsFacade.searchByPrice(this.from, this.to).size();
        }

        if (getPageSize() == -1) {
            return 1L;
        }
        if (totalPages % getPageSize() != 0L) {
            return totalPages / getPageSize() + 1L;
        }
        return totalPages / getPageSize();
    }

    public int getPageSize() {
        this.pageSize = Integer.parseInt(getRequest().getParameter("pageSize"));
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        this.page = Integer.parseInt(getRequest().getParameter("page"));
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getMinPrice() {
        return this.productsFacade.getMinPrice().longValue();
    }

    public long getMaxPrice() {
        return this.productsFacade.getMaxPrice().longValue();
    }

    public int getFrom() {
        String from = getRequest().getParameter("from");
        if (from != null) {
            this.from = Integer.valueOf(from).intValue();
        } else {
            this.from = (int) getMinPrice();
        }
        return this.from;
    }

    public int getTo() {
        String to = getRequest().getParameter("to");
        if (to != null) {
            this.to = Integer.valueOf(to).intValue();
        } else {
            this.to = (int) getMaxPrice();
        }
        return this.to;
    }

    public Categories getCurrentCat() {
        String catID = getRequest().getParameter("categoryID");
        if (catID != null) {
            this.currentCat = this.categoriesFacade.find(catID);
            if (this.currentCat == null) {
                this.currentCat = (Categories) this.categoriesFacade.findAll().get(0);
            }
        } else {
            this.currentCat = null;
        }
        return this.currentCat;
    }

    public Types getCurrentType() {
        String typeID = getRequest().getParameter("typeID");
        if (typeID != null) {
            if (typeID.equals("all")) {
                this.currentType = new Types("all", "All");
            } else {
                this.currentType = this.typesFacade.find(typeID);
                String catID = getCurrentCat().getCategoryID();
                if (this.currentType == null || !this.typesFacade.containCat(this.currentType, catID)) {
                    this.currentType = this.typesFacade.find(this.categoriesFacade.getFirstType(catID));
                }
            }
        } else {
            this.currentType = null;
        }
        return this.currentType;
    }

    public Brands getCurrentBrand() {
        String brandID = getRequest().getParameter("brandID");
        if (brandID != null) {
            this.currentBrand = this.brandsFacade.find(brandID);
            if (this.currentBrand == null) {
                this.currentBrand = (Brands) this.brandsFacade.findAll().get(0);
            }
        } else {
            this.currentBrand = null;
        }
        return this.currentBrand;
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public boolean isCurrentCat(Categories category) {
        if (this.currentCat == null) {
            return false;
        }
        return this.currentCat.getCategoryID().equals(category.getCategoryID());
    }

    public boolean isCurrentType(Types type) {
        if (this.currentType == null) {
            return false;
        }
        return this.currentType.getTypeID().equals(type.getTypeID());
    }

    public boolean isAllType(Categories cat) {
        if (getRequest().getParameter("typeID") == null) {
            return false;
        }
        return (isCurrentCat(cat) && getRequest().getParameter("typeID").equals("all"));
    }

    public boolean isActive(Types type, Categories cat) {
        return (isCurrentType(type) && isCurrentCat(cat));
    }

    public boolean isCurrentBrand(Brands brand) {
        if (this.currentBrand == null) {
            return false;
        }
        return this.currentBrand.getBrandID().equals(brand.getBrandID());
    }

    public List<Products> getFilteredProducts() {
        List<Products> filteredProducts;
        int pageFrom = (getPage() - 1) * getPageSize();
        int pageTo = pageFrom + this.pageSize;

        if (getCurrentCat() != null && getCurrentType() != null) {
            if (this.currentType.getTypeID().equals("all")) {
                filteredProducts = (List) this.currentCat.getProductsCollection();
            } else {
                filteredProducts = this.productsFacade.searchByCatType(this.currentCat, this.currentType);
            }
        } else if (getCurrentBrand() != null) {
            filteredProducts = (List) this.currentBrand.getProductsCollection();
        } else {
            filteredProducts = this.productsFacade.searchByPrice(this.from, this.to);
        }

        if (this.pageSize == -1) {
            return filteredProducts;
        }

        if (pageTo > filteredProducts.size() || this.page == this.pages) {
            pageTo = filteredProducts.size();
        }
        return filteredProducts.subList(pageFrom, pageTo);
    }

    public String getRedirectUrl() {
        String url = "";
        if (getCurrentCat() != null && getCurrentType() != null) {
            url = "products.xhtml?categoryID=" + this.currentCat.getCategoryID() + "&typeID=" + this.currentType.getTypeID() + "&";
        } else if (getCurrentBrand() != null) {
            url = "products.xhtml?brandID=" + this.currentBrand.getBrandID() + "&";
        } else {
            url = "products.xhtml?from=" + this.from + "&to=" + this.to + "&";
        }
        return url;
    }
}
