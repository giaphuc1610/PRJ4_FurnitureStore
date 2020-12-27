package pkg.Entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "OrdersDetails", catalog = "MarcFashion", schema = "dbo")
@Cacheable(false)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdersDetails.findAll", query = "SELECT o FROM OrdersDetails o"),
    @NamedQuery(name = "OrdersDetails.findByOdID", query = "SELECT o FROM OrdersDetails o WHERE o.odID = :odID"),
    @NamedQuery(name = "OrdersDetails.findBySellingPrice", query = "SELECT o FROM OrdersDetails o WHERE o.sellingPrice = :sellingPrice"),
    @NamedQuery(name = "OrdersDetails.findByQuantity", query = "SELECT o FROM OrdersDetails o WHERE o.quantity = :quantity"),
    @NamedQuery(name = "OrdersDetails.findByOdState", query = "SELECT o FROM OrdersDetails o WHERE o.odState = :odState")})
public class OrdersDetails
        implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OdID", nullable = false)
    private Integer odID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SellingPrice", nullable = false, scale = 2)
    private double sellingPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Quantity", nullable = false)
    private int quantity;
    @Column(name = "OdState")
    private Boolean odState;
    @JoinColumn(name = "OrderID", referencedColumnName = "OrderID", nullable = false)
    @ManyToOne(optional = false)
    private Orders orderID;
    @JoinColumn(name = "ProductID", referencedColumnName = "ProductID", nullable = false)
    @ManyToOne(optional = false)
    private Products productID;

    public OrdersDetails() {
    }

    public OrdersDetails(Integer odID) {
        this.odID = odID;
    }

    public OrdersDetails(Integer odID, double sellingPrice, int quantity) {
        this.odID = odID;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
    }

    public OrdersDetails(double sellingPrice, int quantity, Orders orderID, Products productID) {
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.orderID = orderID;
        this.productID = productID;
        this.odState = Boolean.valueOf(true);
    }

    public Integer getOdID() {
        return this.odID;
    }

    public void setOdID(Integer odID) {
        this.odID = odID;
    }

    public double getSellingPrice() {
        return this.sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Boolean getOdState() {
        return this.odState;
    }

    public void setOdState(Boolean odState) {
        this.odState = odState;
    }

    public Orders getOrderID() {
        return this.orderID;
    }

    public void setOrderID(Orders orderID) {
        this.orderID = orderID;
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
        hash += (odID != null ? odID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdersDetails)) {
            return false;
        }
        OrdersDetails other = (OrdersDetails) object;
        if ((this.odID == null && other.odID != null) || (this.odID != null && !this.odID.equals(other.odID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pkg.Models.OrdersDetails[ odID=" + odID + " ]";
    }

}
