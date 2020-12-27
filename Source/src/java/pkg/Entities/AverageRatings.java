package pkg.Entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "AverageRatings", catalog = "MarcFashion", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AverageRatings.findAll", query = "SELECT a FROM AverageRatings a"),
    @NamedQuery(name = "AverageRatings.findById", query = "SELECT a FROM AverageRatings a WHERE a.id = :id"),
    @NamedQuery(name = "AverageRatings.findByProductID", query = "SELECT a FROM AverageRatings a WHERE a.productID = :productID"),
    @NamedQuery(name = "AverageRatings.findByProductName", query = "SELECT a FROM AverageRatings a WHERE a.productName = :productName"),
    @NamedQuery(name = "AverageRatings.findByAverageRating", query = "SELECT a FROM AverageRatings a WHERE a.averageRating = :averageRating")})
public class AverageRatings
        implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "id")
    @Id
    private BigInteger id;
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
    @Column(name = "averageRating", precision = 38, scale = 6)
    private double averageRating;

    public BigInteger getId() {
        return this.id;
    }

    public void setId(BigInteger id) {
        this.id = id;
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

    public double getAverageRating() {
        return this.averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}
