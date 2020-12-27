package pkg.Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Cacheable(false)
@Entity
@Table(name = "Wishlist", catalog = "MarcFashion", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wishlist.findAll", query = "SELECT w FROM Wishlist w"),
    @NamedQuery(name = "Wishlist.findByWishlistID", query = "SELECT w FROM Wishlist w WHERE w.wishlistID = :wishlistID"),
    @NamedQuery(name = "Wishlist.findByQuantity", query = "SELECT w FROM Wishlist w WHERE w.quantity = :quantity")})
public class Wishlist
        implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "WishlistID", nullable = false)
    private Integer wishlistID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Quantity", nullable = false)
    private int quantity;
    @JoinColumn(name = "CustomerEmail", referencedColumnName = "Email", nullable = false)
    @ManyToOne(optional = false)
    private Customers customerEmail;
    @JoinColumn(name = "ProductID", referencedColumnName = "ProductID", nullable = false)
    @ManyToOne(optional = false)
    private Products productID;

    public Wishlist() {
    }

    public Wishlist(int quantity, Customers customerEmail, Products productID) {
        this.quantity = quantity;
        this.customerEmail = customerEmail;
        this.productID = productID;
    }

    public Integer getWishlistID() {
        return this.wishlistID;
    }

    public void setWishlistID(Integer wishlistID) {
        this.wishlistID = wishlistID;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Customers getCustomerEmail() {
        return this.customerEmail;
    }

    public void setCustomerEmail(Customers customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Products getProductID() {
        return this.productID;
    }

    public void setProductID(Products productID) {
        this.productID = productID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wishlistID != null ? wishlistID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Wishlist)) {
            return false;
        }
        Wishlist other = (Wishlist) object;
        if ((this.wishlistID == null && other.wishlistID != null) || (this.wishlistID != null && !this.wishlistID.equals(other.wishlistID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pkg.Models.Wishlist[ wishlistID=" + wishlistID + " ]";
    }

}
