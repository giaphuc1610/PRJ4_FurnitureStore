/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg.Controllers.Admin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import pkg.Entities.Brands;
import pkg.Entities.BrandsFacadeLocal;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@ManagedBean
@SessionScoped
@Named("adminBrandsController")
public class adminBrandsController implements Serializable {

    private Brands newBrand = new Brands();
    @EJB
    private BrandsFacadeLocal brandsFacade;
    private Brands selectedBrand = null;

    private Part image;

    String oldName;

    public String toCreate() {
        getSession().setAttribute("currentPage", "brandsAdmin");
        getSession().setAttribute("currentPageChild", "brandsCreate");
        return "brandsCreate?faces-redirect=true";
    }

    public List<Brands> getBrands() {
        getSession().setAttribute("currentPage", "brandsAdmin");
        getSession().setAttribute("currentPageChild", "brandsView");

        String action = getRequest().getParameter("action");
        String id = getRequest().getParameter("id");
        String state = getRequest().getParameter("state");
        if (action != null && id != null && state != null && action.equals("disable")) {
            Brands deleteBrands = this.brandsFacade.find(id);
            if (deleteBrands != null && deleteBrands.getBrandState().toString().toLowerCase().equals(state.toLowerCase())) {
                boolean currentState = deleteBrands.getBrandState().booleanValue();
                deleteBrands.setBrandState(Boolean.valueOf(!currentState));
                this.brandsFacade.edit(deleteBrands);
            }
        }
        List<Brands> brands = this.brandsFacade.findAll();
        return brands;
    }

    public String create() throws Exception {
        for (Brands b : this.brandsFacade.findAll()) {
            if (b.getBrandName().equalsIgnoreCase(this.newBrand.getBrandName())) {
                FacesContext.getCurrentInstance().addMessage("profileForm", new FacesMessage("Brand Name existed."));
                return "brandsCreate";
            }
        }
        String brandID = "";
        Integer numOfBrands = Integer.valueOf(this.brandsFacade.count() + 1);
        switch (numOfBrands.toString().length()) {
            case 1:
                brandID = "BR00" + numOfBrands;
                break;
            case 2:
                brandID = "BR0" + numOfBrands;
                break;
            case 3:
                brandID = "BR" + numOfBrands;
                break;
        }

        String images = "images/logoMarc.png";
        if (this.image != null && this.image.getSize() > 0L) {
            images = "images/Brands/" + copyImage(brandID, this.image);
        }
        this.newBrand.setBrandID(brandID);
        this.newBrand.setBrandImages(images);
        this.newBrand.setBrandState(Boolean.valueOf(true));

        this.brandsFacade.create(this.newBrand);
        this.newBrand = new Brands();
        this.image = null;
        return "brandsView?faces-redirect=true";
    }

    public String update() throws Exception {
        if (!this.oldName.equalsIgnoreCase(this.selectedBrand.getBrandName())) {
            for (Brands b : this.brandsFacade.findAll()) {
                if (b.getBrandName().equalsIgnoreCase(this.selectedBrand.getBrandName())) {
                    FacesContext.getCurrentInstance().addMessage("profileForm", new FacesMessage("Brand Name existed."));
                    return "brandsUpdate";
                }
            }
        }
        if (this.image != null && this.image.getSize() > 0L) {
            this.selectedBrand.setBrandImages("images/Brands/" + copyImage(this.selectedBrand.getBrandID(), this.image));
        }

        this.brandsFacade.edit(this.selectedBrand);
        this.selectedBrand = null;
        this.image = null;
//        return "updateSuccess";
        return "brandsView?faces-redirect=true";
    }

    private String copyImage(String brandID, Part file) throws Exception {
        String dirPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/Brands");
        String fullFilename = file.getSubmittedFileName();
        int lastIndex = fullFilename.lastIndexOf('.');

        String extension = fullFilename.substring(lastIndex + 1, fullFilename.length());
        String filename = brandID + "." + extension;
        
        try (InputStream input = file.getInputStream()) {
            Files.copy(input, (new File(dirPath + "/" + filename)).toPath(), new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
            input.close();
        } catch (Exception ex) {
            throw ex;
        }
        return filename;
    }

    private HttpSession getSession() {
        return getRequest().getSession();
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public Brands getNewBrand() {
        return this.newBrand;
    }

    public void setNewBrand(Brands newBrand) {
        this.newBrand = newBrand;
    }

    public Brands getSelectedBrand() {
        if (getRequest().getParameter("id") != null) {
            this.selectedBrand = this.brandsFacade.find(getRequest().getParameter("id"));
            this.oldName = new String(this.selectedBrand.getBrandName());
        }

        return this.selectedBrand;
    }

    public Part getImage() {
        return this.image;
    }

    public void setImage(Part image) {
        this.image = image;
    }
}
