package pkg.Entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Cacheable(false)
@Entity
@Table(name = "Orders", catalog = "MarcFashion", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findByOrderID", query = "SELECT o FROM Orders o WHERE o.orderID = :orderID"),
    @NamedQuery(name = "Orders.findByTotal", query = "SELECT o FROM Orders o WHERE o.total = :total"),
    @NamedQuery(name = "Orders.findByShipName", query = "SELECT o FROM Orders o WHERE o.shipName = :shipName"),
    @NamedQuery(name = "Orders.findByShipPhone", query = "SELECT o FROM Orders o WHERE o.shipPhone = :shipPhone"),
    @NamedQuery(name = "Orders.findByShipAddress", query = "SELECT o FROM Orders o WHERE o.shipAddress = :shipAddress"),
    @NamedQuery(name = "Orders.findByShipDate", query = "SELECT o FROM Orders o WHERE o.shipDate = :shipDate"),
    @NamedQuery(name = "Orders.findByShipNote", query = "SELECT o FROM Orders o WHERE o.shipNote = :shipNote"),
    @NamedQuery(name = "Orders.findByOrderDate", query = "SELECT o FROM Orders o WHERE o.orderDate = :orderDate"),
    @NamedQuery(name = "Orders.findByPaymentMethod", query = "SELECT o FROM Orders o WHERE o.paymentMethod = :paymentMethod"),
    @NamedQuery(name = "Orders.findByProcessStatus", query = "SELECT o FROM Orders o WHERE o.processStatus = :processStatus"),
    @NamedQuery(name = "Orders.findByOrderState", query = "SELECT o FROM Orders o WHERE o.orderState = :orderState")})
public class Orders
        implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "OrderID", nullable = false, length = 10)
    private String orderID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Total", nullable = false)
    private double total;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ShipName", nullable = false, length = 100)
    private String shipName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ShipPhone", nullable = false, length = 20)
    private String shipPhone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "ShipAddress", nullable = false, length = 200)
    private String shipAddress;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ShipDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date shipDate;

    public Orders() {
        this.orderState = Boolean.valueOf(true);
        this.orderDate = new Date();
        this.paymentMethod = "Cash";
        this.processStatus = "Processing";

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(5, 3);
        this.shipDate = cal.getTime();
    }
    @Size(max = 2147483647)
    @Column(name = "ShipNote", length = 2147483647)
    private String shipNote;
    @Column(name = "OrderDate")
    @Temporal(TemporalType.DATE)
    private Date orderDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PaymentMethod", nullable = false, length = 100)
    private String paymentMethod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ProcessStatus", nullable = false, length = 50)
    private String processStatus;
    @Column(name = "OrderState")
    private Boolean orderState;
    @JoinColumn(name = "CustomerEmail", referencedColumnName = "Email", nullable = false)
    @ManyToOne(optional = false)
    private Customers customerEmail;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "orderID")
    private Collection<OrdersDetails> ordersDetailsCollection;

    public Orders(String orderID) {
        this.orderID = orderID;
    }

    public Orders(String orderID, double total, String shipName, String shipPhone, String shipAddress, Date shipDate, String paymentMethod, String processStatus) {
        this.orderID = orderID;
        this.total = total;
        this.shipName = shipName;
        this.shipPhone = shipPhone;
        this.shipAddress = shipAddress;
        this.shipDate = shipDate;
        this.paymentMethod = paymentMethod;
        this.processStatus = processStatus;
    }

    public String getOrderID() {
        return this.orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getShipName() {
        return this.shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipPhone() {
        return this.shipPhone;
    }

    public void setShipPhone(String shipPhone) {
        this.shipPhone = shipPhone;
    }

    public String getShipAddress() {
        return this.shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getShipDate() {
        DateFormat dateFormatter = DateFormat.getDateInstance(3, new Locale("vi", "VN"));
        return dateFormatter.format(this.shipDate);
    }

    public void setShipDate(String shipDate) {
        DateFormat dateFormatter = DateFormat.getDateInstance(3, new Locale("vi", "VN"));
        try {
            this.shipDate = dateFormatter.parse(shipDate);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    public String getShipNote() {
        return this.shipNote;
    }

    public void setShipNote(String shipNote) {
        this.shipNote = shipNote;
    }

    public String getOrderDate() {
        DateFormat dateFormatter = DateFormat.getDateInstance(3, new Locale("vi", "VN"));
        return dateFormatter.format(this.orderDate);
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getProcessStatus() {
        return this.processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public Boolean getOrderState() {
        return this.orderState;
    }

    public void setOrderState(Boolean orderState) {
        this.orderState = orderState;
    }

    public Customers getCustomerEmail() {
        return this.customerEmail;
    }

    public void setCustomerEmail(Customers customerEmail) {
        this.customerEmail = customerEmail;
    }

    @XmlTransient
    public Collection<OrdersDetails> getOrdersDetailsCollection() {
        return this.ordersDetailsCollection;
    }

    public void setOrdersDetailsCollection(Collection<OrdersDetails> ordersDetailsCollection) {
        this.ordersDetailsCollection = ordersDetailsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderID != null ? orderID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.orderID == null && other.orderID != null) || (this.orderID != null && !this.orderID.equals(other.orderID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pkg.Models.Orders[ orderID=" + orderID + " ]";
    }

}
