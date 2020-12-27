package pkg.Entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Categories", catalog = "MarcFashion", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categories.findAll", query = "SELECT c FROM Categories c"),
    @NamedQuery(name = "Categories.findByCategoryID", query = "SELECT c FROM Categories c WHERE c.categoryID = :categoryID"),
    @NamedQuery(name = "Categories.findByCategoryName", query = "SELECT c FROM Categories c WHERE c.categoryName = :categoryName"),
    @NamedQuery(name = "Categories.findByCategoryImage", query = "SELECT c FROM Categories c WHERE c.categoryImage = :categoryImage"),
    @NamedQuery(name = "Categories.findByCategoryState", query = "SELECT c FROM Categories c WHERE c.categoryState = :categoryState")})
public class Categories
        implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CategoryID", nullable = false, length = 10)
    private String categoryID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "CategoryName", nullable = false, length = 100)
    private String categoryName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "CategoryImage", nullable = false, length = 2147483647)
    private String categoryImage;
    @Column(name = "CategoryState")
    private Boolean categoryState;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "categoryID")
    private Collection<Products> productsCollection;

    public Categories() {
    }

    public Categories(String categoryID) {
        this.categoryID = categoryID;
    }

    public Categories(String categoryID, String categoryName, String categoryImage) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
    }

    public String getCategoryID() {
        return this.categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return this.categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public Boolean getCategoryState() {
        return this.categoryState;
    }

    public void setCategoryState(Boolean categoryState) {
        this.categoryState = categoryState;
    }

    @XmlTransient
    public Collection<Products> getProductsCollection() {
        return this.productsCollection;
    }

    public void setProductsCollection(Collection<Products> productsCollection) {
        this.productsCollection = productsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoryID != null ? categoryID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categories)) {
            return false;
        }
        Categories other = (Categories) object;
        if ((this.categoryID == null && other.categoryID != null) || (this.categoryID != null && !this.categoryID.equals(other.categoryID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pkg.Models.Categories[ categoryID=" + categoryID + " ]";
    }

}
