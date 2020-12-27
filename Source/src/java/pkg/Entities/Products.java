package pkg.Entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Cacheable(false)
@Entity
@Table(name = "Products", catalog = "MarcFashion", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Products.findAll", query = "SELECT p FROM Products p"),
    @NamedQuery(name = "Products.findByProductID", query = "SELECT p FROM Products p WHERE p.productID = :productID"),
    @NamedQuery(name = "Products.findByProductName", query = "SELECT p FROM Products p WHERE p.productName = :productName"),
    @NamedQuery(name = "Products.findByPrice", query = "SELECT p FROM Products p WHERE p.price = :price"),
    @NamedQuery(name = "Products.findByProductImages", query = "SELECT p FROM Products p WHERE p.productImages = :productImages"),
    @NamedQuery(name = "Products.findByDescriptions", query = "SELECT p FROM Products p WHERE p.descriptions = :descriptions"),
    @NamedQuery(name = "Products.findByFeature", query = "SELECT p FROM Products p WHERE p.feature = :feature"),
    @NamedQuery(name = "Products.findByProductState", query = "SELECT p FROM Products p WHERE p.productState = :productState")})
public class Products
        implements Serializable {

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "productID")
    private Collection<OrdersDetails> ordersDetailsCollection;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "productID")
    private Collection<Wishlist> wishlistCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ProductID", nullable = false, length = 10)
    private String productID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ProductName", nullable = false, length = 100)
    private String productName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Price", nullable = false)
    private long price;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "ProductImages", nullable = false, length = 2147483647)
    private String productImages;
    @Size(max = 2147483647)
    @Column(name = "Descriptions", length = 2147483647)
    private String descriptions;
    @Size(max = 100)
    @Column(name = "Feature", length = 100)
    private String feature;
    @Column(name = "ProductState")
    private Boolean productState;
    @JoinColumn(name = "BrandID", referencedColumnName = "BrandID", nullable = false)
    @ManyToOne(optional = false)
    private Brands brandID;
    @JoinColumn(name = "CategoryID", referencedColumnName = "CategoryID", nullable = false)
    @ManyToOne(optional = false)
    private Categories categoryID;
    @JoinColumn(name = "TypeID", referencedColumnName = "TypeID", nullable = false)
    @ManyToOne(optional = false)
    private Types typeID;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "productID")
    private Collection<Ratings> ratingsCollection;

    public Products() {
    }

    public Products(String productID) {
        this.productID = productID;
    }

    public Products(String productID, String productName, long price, String productImages) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.productImages = productImages;
    }

    public String getProductID() {
        return this.productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getPrice() {
        return this.price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getProductImages() {
        return this.productImages;
    }

    public void setProductImages(String productImages) {
        this.productImages = productImages;
    }

    public String getDescriptions() {
        return this.descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getFeature() {
        return this.feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Boolean getProductState() {
        return this.productState;
    }

    public void setProductState(Boolean productState) {
        this.productState = productState;
    }

    public Brands getBrandID() {
        return this.brandID;
    }

    public void setBrandID(Brands brandID) {
        this.brandID = brandID;
    }

    public Categories getCategoryID() {
        return this.categoryID;
    }

    public void setCategoryID(Categories categoryID) {
        this.categoryID = categoryID;
    }

    public Types getTypeID() {
        return this.typeID;
    }

    public void setTypeID(Types typeID) {
        this.typeID = typeID;
    }

    @XmlTransient
    public Collection<Ratings> getRatingsCollection() {
        return this.ratingsCollection;
    }

    public void setRatingsCollection(Collection<Ratings> ratingsCollection) {
        this.ratingsCollection = ratingsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productID != null ? productID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Products)) {
            return false;
        }
        Products other = (Products) object;
        if ((this.productID == null && other.productID != null) || (this.productID != null && !this.productID.equals(other.productID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pkg.Models.Products[ productID=" + productID + " ]";
    }

    @XmlTransient
    public Collection<OrdersDetails> getOrdersDetailsCollection() {
        return this.ordersDetailsCollection;
    }

    public void setOrdersDetailsCollection(Collection<OrdersDetails> ordersDetailsCollection) {
        this.ordersDetailsCollection = ordersDetailsCollection;
    }

    @XmlTransient
    public Collection<Wishlist> getWishlistCollection() {
        return this.wishlistCollection;
    }

    public void setWishlistCollection(Collection<Wishlist> wishlistCollection) {
        this.wishlistCollection = wishlistCollection;
    }
}
