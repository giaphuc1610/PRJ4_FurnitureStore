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
@Table(name = "Types", catalog = "MarcFashion", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Types.findAll", query = "SELECT t FROM Types t"),
    @NamedQuery(name = "Types.findByTypeID", query = "SELECT t FROM Types t WHERE t.typeID = :typeID"),
    @NamedQuery(name = "Types.findByTypeName", query = "SELECT t FROM Types t WHERE t.typeName = :typeName"),
    @NamedQuery(name = "Types.findByDescriptions", query = "SELECT t FROM Types t WHERE t.descriptions = :descriptions"),
    @NamedQuery(name = "Types.findByTypeState", query = "SELECT t FROM Types t WHERE t.typeState = :typeState")})
public class Types
        implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TypeID", nullable = false, length = 10)
    private String typeID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TypeName", nullable = false, length = 100)
    private String typeName;
    @Size(max = 2147483647)
    @Column(name = "Descriptions", length = 2147483647)
    private String descriptions;
    @Column(name = "TypeState")
    private Boolean typeState;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "typeID")
    private Collection<Products> productsCollection;

    public Types() {
    }

    public Types(String typeID) {
        this.typeID = typeID;
    }

    public Types(String typeID, String typeName) {
        this.typeID = typeID;
        this.typeName = typeName;
    }

    public String getTypeID() {
        return this.typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescriptions() {
        return this.descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public Boolean getTypeState() {
        return this.typeState;
    }

    public void setTypeState(Boolean typeState) {
        this.typeState = typeState;
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
        hash += (typeID != null ? typeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Types)) {
            return false;
        }
        Types other = (Types) object;
        if ((this.typeID == null && other.typeID != null) || (this.typeID != null && !this.typeID.equals(other.typeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pkg.Models.Types[ typeID=" + typeID + " ]";
    }

}
