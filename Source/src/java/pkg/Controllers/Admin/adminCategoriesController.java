package pkg.Controllers.Admin;

import javax.faces.bean.ManagedBean;
import pkg.Entities.Categories;
import pkg.Entities.CategoriesFacadeLocal;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@Named("adminCategoriesController")
@SessionScoped
public class adminCategoriesController
        implements Serializable {

    private Categories newCat = new Categories();
    @EJB
    private CategoriesFacadeLocal categoriesFacade;
    private Categories selectedCat = null;

    private Part image;

    String oldName;

    public List<Categories> getCategories() {
        getSession().setAttribute("currentPage", "categoriesAdmin");
        getSession().setAttribute("currentPageChild", "categoriesView");

        String action = getRequest().getParameter("action");
        String id = getRequest().getParameter("id");
        String state = getRequest().getParameter("state");
        if (action != null && id != null && state != null && action.equals("disable")) {
            Categories deleteCategories = this.categoriesFacade.find(id);
            if (deleteCategories != null && deleteCategories.getCategoryState().toString().toLowerCase().equals(state.toLowerCase())) {
                boolean currentState = deleteCategories.getCategoryState().booleanValue();
                deleteCategories.setCategoryState(Boolean.valueOf(!currentState));
                this.categoriesFacade.edit(deleteCategories);
            }
        }

        return this.categoriesFacade.findAll();
    }

    public String toCreate() {
        getSession().setAttribute("currentPage", "categoriesAdmin");
        getSession().setAttribute("currentPageChild", "categoriesCreate");

        return "categoriesCreate";
    }

    public String create() {
        for (Categories c : this.categoriesFacade.findAll()) {
            if (c.getCategoryName().equalsIgnoreCase(this.newCat.getCategoryName())) {
                FacesContext.getCurrentInstance().addMessage("profileForm", new FacesMessage("Category Name existed."));
                return "categoriesCreate";
            }
        }
        String catID = "";
        Integer numOfCategories = Integer.valueOf(this.categoriesFacade.count() + 1);
        switch (numOfCategories.toString().length()) {
            case 1:
                catID = "CA00" + numOfCategories;
                break;
            case 2:
                catID = "CA0" + numOfCategories;
                break;
            case 3:
                catID = "CA" + numOfCategories;
                break;
        }

        String images = "images/logoMarc.jpg";
        if (this.image != null && this.image.getSize() > 0L) {
            images = "images/Categories/" + copyImage(catID, this.image);
        }
        this.newCat.setCategoryID(catID);
        this.newCat.setCategoryImage(images);
        this.newCat.setCategoryState(Boolean.valueOf(true));

        this.categoriesFacade.create(this.newCat);
        this.image = null;
        this.newCat = new Categories();
//        return "createSuccess";
        return "categoriesView?faces-redirect=true";
    }

    private String copyImage(String catID, Part file) {
        String dirPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/Categories");
        String fullFilename = file.getSubmittedFileName();
        int lastIndex = fullFilename.lastIndexOf('.');

        String extension = fullFilename.substring(lastIndex + 1, fullFilename.length());
        String filename = catID + "." + extension;
        try (InputStream input = file.getInputStream()) {
            Files.copy(input, (new File(dirPath + "/" + filename)).toPath(), new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
            file = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return filename;
    }

    
    
    
    public Categories getSelectedCategories() {
        if (getRequest().getParameter("id") != null) {
            this.selectedCat = this.categoriesFacade.find(getRequest().getParameter("id"));
            this.oldName = new String(this.selectedCat.getCategoryName());
        }
        return this.selectedCat;
    }

    
//   ============= UPDATE ==============
    public String update() {
        if (!this.oldName.equalsIgnoreCase(this.selectedCat.getCategoryName())) {
            for (Categories c : this.categoriesFacade.findAll()) {
                if (c.getCategoryName().equalsIgnoreCase(this.selectedCat.getCategoryName())) {
                    FacesContext.getCurrentInstance().addMessage("profileForm", new FacesMessage("Category Name existed."));
                    return "categoriesUpdate";
                }
            }
        }
        if (this.image != null && this.image.getSize() > 0L) {
            this.selectedCat.setCategoryImage("images/Categories/" + copyImage(this.selectedCat.getCategoryID(), this.image));
        }

        this.categoriesFacade.edit(this.selectedCat);
        this.selectedCat = null;
        this.image = null;
//        return "updateSuccess";
        return "categoriesView?faces-redirect=true";
    }

    private HttpSession getSession() {
        return getRequest().getSession();
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public Categories getNewCat() {
        return this.newCat;
    }

    public void setNewCat(Categories newCat) {
        this.newCat = newCat;
    }

    public Part getImage() {
        return this.image;
    }

    public void setImage(Part image) {
        this.image = image;
    }
}
