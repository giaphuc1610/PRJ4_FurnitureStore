/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.Controllers.Admin;

import javax.faces.bean.ManagedBean;
import pkg.Entities.Brands;
import pkg.Entities.BrandsFacadeLocal;
import pkg.Entities.Categories;
import pkg.Entities.CategoriesFacadeLocal;
import pkg.Entities.Products;
import pkg.Entities.ProductsFacadeLocal;
import pkg.Entities.Types;
import pkg.Entities.TypesFacadeLocal;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@Named("adminProductsController")
@SessionScoped
public class adminProductsController implements Serializable {

    @EJB
    private TypesFacadeLocal typesFacade;
    @EJB
    private BrandsFacadeLocal brandsFacade;
    private Products newPro = new Products();
    @EJB
    private CategoriesFacadeLocal categoriesFacade;
    @EJB
    private ProductsFacadeLocal productsFacade;
    private Products selectedPro = null;
    private String categoryID;
    private String typeID;
    private String brandID;
    private Part[] image = new Part[4];

    String oldName;

    public String toCreate() {
        getSession().setAttribute("currentPage", "productsAdmin");
        getSession().setAttribute("currentPageChild", "productsCreate");

        resetProducts();
        return "productsCreate";
    }

    public List<Products> getProducts() {
        getSession().setAttribute("currentPage", "productsAdmin");
        getSession().setAttribute("currentPageChild", "productsView");

        String action = getRequest().getParameter("action");
        String id = getRequest().getParameter("id");
        String state = getRequest().getParameter("state");
        if (action != null && id != null && state != null && action.equals("disable")) {
            Products DeletaProduct = this.productsFacade.find(id);
            if (DeletaProduct != null && DeletaProduct.getProductState().toString().toLowerCase().equals(state.toLowerCase())) {
                boolean currentState = DeletaProduct.getProductState().booleanValue();
                DeletaProduct.setProductState(Boolean.valueOf(!currentState));
                this.productsFacade.edit(DeletaProduct);
            }
        }

        return this.productsFacade.findAll();
    }

    public String create() {
        for (Products p : this.productsFacade.findAll()) {
            if (p.getProductName().equalsIgnoreCase(this.newPro.getProductName())) {
                FacesContext.getCurrentInstance().addMessage("profileForm", new FacesMessage("Name existed."));
                return "productsCreate";
            }
        }
        String productID = "";
        Integer numOfProducts = Integer.valueOf(this.productsFacade.count() + 1);
        switch (numOfProducts.toString().length()) {
            case 1:
                productID = "PR00" + numOfProducts;
                break;
            case 2:
                productID = "PR0" + numOfProducts;
                break;
            case 3:
                productID = "PR" + numOfProducts;
                break;
        }

        Categories cat = this.categoriesFacade.find(this.categoryID);
        Types type = this.typesFacade.find(this.typeID);
        Brands brand = this.brandsFacade.find(this.brandID);

        StringBuilder images = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String filename = "defaultProduct.jpg" + ((i == 3) ? "" : ";");
            if (this.image[i] != null && this.image[i].getSize() > 0L) {
                filename = copyImage(productID.toLowerCase() + "-" + (i + 1), this.image[i]) + ((i == 3) ? "" : ";");
            }
            images.append(filename);
        }

        this.newPro.setProductID(productID);
        this.newPro.setCategoryID(cat);
        this.newPro.setTypeID(type);
        this.newPro.setBrandID(brand);
        this.newPro.setProductImages(new String(images));
        this.newPro.setProductState(Boolean.valueOf(true));

        this.productsFacade.create(this.newPro);
        resetProducts();
//        return "createSuccess";
        return "productsView?faces-redirect=true";
    }

    public Products getSelectedProduct() {
        if (getRequest().getParameter("id") != null) {
            this.selectedPro = this.productsFacade.find(getRequest().getParameter("id"));
            if (this.selectedPro != null) {
                this.oldName = new String(this.selectedPro.getProductName());
                this.categoryID = this.selectedPro.getCategoryID().getCategoryID();
                this.typeID = this.selectedPro.getTypeID().getTypeID();
                this.brandID = this.selectedPro.getBrandID().getBrandID();
            }
        }
        return this.selectedPro;
    }

    public String update() {
        if (!this.oldName.equalsIgnoreCase(this.selectedPro.getProductName())) {
            for (Products p : this.productsFacade.findAll()) {
                if (p.getProductName().equalsIgnoreCase(this.selectedPro.getProductName())) {
                    FacesContext.getCurrentInstance().addMessage("profileForm", new FacesMessage("Name existed."));
                    return "productsUpdate";
                }
            }
        }

        Categories cat = this.categoriesFacade.find(this.categoryID);
        Types type = this.typesFacade.find(this.typeID);
        Brands brand = this.brandsFacade.find(this.brandID);

        String[] images = this.selectedPro.getProductImages().split(";");
        List<String> uploadedImages = new ArrayList<>();
        
        for (int i = 0; i < images.length; i++) {
            if (this.image[i] != null && this.image[i].getSize() > 0L) {
                String filename = copyImage(this.selectedPro.getProductID().toLowerCase() + "-" + (i + 1), this.image[i]) + ((i == 3) ? "" : ";");
                uploadedImages.add(filename);
            }
        }

        this.selectedPro.setCategoryID(cat);
        this.selectedPro.setTypeID(type);
        this.selectedPro.setBrandID(brand);
        if (uploadedImages.size() > 0) {
            this.selectedPro.setProductImages(String.join(";", uploadedImages));
        }
        this.productsFacade.edit(this.selectedPro);
        resetProducts();
//        return "updateSuccess";
        return "productsView?faces-redirect=true";
    }

    private String copyImage(String productID, Part file) {
        String dirPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/Products");
        String fullFilename = file.getSubmittedFileName();
        int lastIndex = fullFilename.lastIndexOf('.');

        String extension = fullFilename.substring(lastIndex + 1, fullFilename.length());
        String filename = productID + "." + extension;
        try (InputStream input = file.getInputStream()) {
            Files.copy(input, (new File(dirPath + "/" + filename)).toPath(), new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
            file = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return filename;
    }

    private void resetProducts() {
        this.newPro = new Products();
        this.selectedPro = new Products();
        this.categoryID = "";
        this.typeID = "";
        this.brandID = "";
        this.image = new Part[4];
    }

    private HttpSession getSession() {
        return getRequest().getSession();
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public List<Categories> getCategories() {
        return this.categoriesFacade.findAll();
    }

    public List<Types> getTypes() {
        return this.typesFacade.findAll();
    }

    public List<Brands> getBrands() {
        return this.brandsFacade.findAll();
    }

    public Products getNewPro() {
        return this.newPro;
    }

    public void setNewPro(Products newPro) {
        this.newPro = newPro;
    }

    public String getCategoryID() {
        return this.categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getTypeID() {
        return this.typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getBrandID() {
        return this.brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public Part[] getImage() {
        return this.image;
    }

    public void setImage(Part[] image) {
        this.image = image;
    }
}
