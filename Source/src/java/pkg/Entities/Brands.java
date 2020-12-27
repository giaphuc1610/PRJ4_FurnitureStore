package pkg.Entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Brands", catalog = "MarcFashion", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Brands.findAll", query = "SELECT b FROM Brands b"),
    @NamedQuery(name = "Brands.findByBrandID", query = "SELECT b FROM Brands b WHERE b.brandID = :brandID"),
    @NamedQuery(name = "Brands.findByBrandName", query = "SELECT b FROM Brands b WHERE b.brandName = :brandName"),
    @NamedQuery(name = "Brands.findByBrandImages", query = "SELECT b FROM Brands b WHERE b.brandImages = :brandImages"),
    @NamedQuery(name = "Brands.findByDescriptions", query = "SELECT b FROM Brands b WHERE b.descriptions = :descriptions"),
    @NamedQuery(name = "Brands.findByBrandState", query = "SELECT b FROM Brands b WHERE b.brandState = :brandState")})
public class Brands
        implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "BrandID", nullable = false, length = 10)
    private String brandID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "BrandName", nullable = false, length = 100)
    private String brandName;
    @Size(max = 2147483647)
    @Column(name = "BrandImages", length = 2147483647)
    private String brandImages;
    @Size(max = 2147483647)
    @Column(name = "Descriptions", length = 2147483647)
    private String descriptions;
    @Column(name = "BrandState")
    private Boolean brandState;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "brandID")
    private Collection<Products> productsCollection;

    public Brands() {
    }

    public Brands(String brandID) {
        this.brandID = brandID;
    }

    public Brands(String brandID, String brandName) {
        this.brandID = brandID;
        this.brandName = brandName;
    }

    public String getBrandID() {
        return this.brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandImages() {
        return this.brandImages;
    }

    public void setBrandImages(String brandImages) {
        this.brandImages = brandImages;
    }

    public String getDescriptions() {
        return this.descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public Boolean getBrandState() {
        return this.brandState;
    }

    public void setBrandState(Boolean brandState) {
        this.brandState = brandState;
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
        hash += (brandID != null ? brandID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Brands)) {
            return false;
        }
        Brands other = (Brands) object;
        if ((this.brandID == null && other.brandID != null) || (this.brandID != null && !this.brandID.equals(other.brandID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pkg.Models.Brands[ brandID=" + brandID + " ]";
    }

}
