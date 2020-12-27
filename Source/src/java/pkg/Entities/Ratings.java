package pkg.Entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Cacheable(false)
@Entity
@Table(name = "Ratings", catalog = "MarcFashion", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ratings.findAll", query = "SELECT r FROM Ratings r"),
    @NamedQuery(name = "Ratings.findByRatingID", query = "SELECT r FROM Ratings r WHERE r.ratingID = :ratingID"),
    @NamedQuery(name = "Ratings.findByRate", query = "SELECT r FROM Ratings r WHERE r.rate = :rate"),
    @NamedQuery(name = "Ratings.findByRatingDate", query = "SELECT r FROM Ratings r WHERE r.ratingDate = :ratingDate")})
public class Ratings implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RatingID", nullable = false)
    private Integer ratingID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Rate", nullable = false)
    private int rate;
    @Column(name = "RatingDate")
    @Temporal(TemporalType.DATE)
    private Date ratingDate = new Date();

    @JoinColumn(name = "CustomerEmail", referencedColumnName = "Email", nullable = false)
    @ManyToOne(optional = false)
    private Customers customerEmail;

    @JoinColumn(name = "ProductID", referencedColumnName = "ProductID", nullable = false)
    @ManyToOne(optional = false)
    private Products productID;

    public Ratings() {
    }

    public Ratings(Integer ratingID) {
        this.ratingID = ratingID;
    }

    public Ratings(Integer ratingID, int rate) {
        this.ratingID = ratingID;
        this.rate = rate;
    }

    public Ratings(Customers customerEmail, Products productID, int rate) {
        this.rate = rate;
        this.customerEmail = customerEmail;
        this.productID = productID;
    }

    public Integer getRatingID() {
        return this.ratingID;
    }

    public void setRatingID(Integer ratingID) {
        this.ratingID = ratingID;
    }

    public int getRate() {
        return this.rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Date getRatingDate() {
        return this.ratingDate;
    }

    public void setRatingDate(Date ratingDate) {
        this.ratingDate = ratingDate;
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
        hash += (ratingID != null ? ratingID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ratings)) {
            return false;
        }
        Ratings other = (Ratings) object;
        if ((this.ratingID == null && other.ratingID != null) || (this.ratingID != null && !this.ratingID.equals(other.ratingID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pkg.Models.Ratings[ ratingID=" + ratingID + " ]";
    }

}
