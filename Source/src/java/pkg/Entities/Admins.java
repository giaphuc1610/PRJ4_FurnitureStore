package pkg.Entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Cacheable(false)
@Entity
@Table(name = "Admins", catalog = "MarcFashion", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Admins.findAll", query = "SELECT a FROM Admins a"),
    @NamedQuery(name = "Admins.findByEmail", query = "SELECT a FROM Admins a WHERE a.email = :email"),
    @NamedQuery(name = "Admins.findByPassword", query = "SELECT a FROM Admins a WHERE a.password = :password"),
    @NamedQuery(name = "Admins.findByFullName", query = "SELECT a FROM Admins a WHERE a.fullName = :fullName"),
    @NamedQuery(name = "Admins.findByCreatedDate", query = "SELECT a FROM Admins a WHERE a.createdDate = :createdDate"),
    @NamedQuery(name = "Admins.findByAdminState", query = "SELECT a FROM Admins a WHERE a.adminState = :adminState")})
public class Admins implements Serializable {

    private static final long serialVersionUID = 1L;
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Email")
    private String email;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Password")
    private String password;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "FullName")
    private String fullName;

//    @Size(max = 10)
//    @Column(name = "CreatedDate")
//    private String createdDate;
    @Column(name = "CreatedDate")
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Column(name = "AdminState")
    private Boolean adminState;

    public Admins() {
    }

    public Admins(String email) {
        this.email = email;
    }

    public Admins(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;

        this.createdDate = new Date();
        this.adminState = Boolean.valueOf(true);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCreatedDate() {
        //return createdDate;
        DateFormat dateFormatter = DateFormat.getDateInstance(3, new Locale("vi", "VN"));
        return dateFormatter.format(this.createdDate);
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getAdminState() {
        return adminState;
    }

    public void setAdminState(Boolean adminState) {
        this.adminState = adminState;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Admins)) {
            return false;
        }
        Admins other = (Admins) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pkg.Models.Admins[ email=" + email + " ]";
    }

}
