/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.int303.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author INT303
 */
@Entity
@Table(name = "PRODUCTLINE", catalog = "", schema = "APP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productline.findAll", query = "SELECT p FROM Productline p")
    , @NamedQuery(name = "Productline.findByProductline", query = "SELECT p FROM Productline p WHERE p.productline = :productline")
    , @NamedQuery(name = "Productline.findByTextdescription", query = "SELECT p FROM Productline p WHERE p.textdescription = :textdescription")})
public class Productline implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PRODUCTLINE")
    private String productline;
    @Column(name = "TEXTDESCRIPTION")
    private String textdescription;
    @Lob
    @Column(name = "HTMLDESCRIPTION")
    private String htmldescription;
    @Lob
    @Column(name = "IMAGE")
    private Serializable image;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productline")
    private Collection<Product> productCollection;

    public Productline() {
    }

    public Productline(String productline) {
        this.productline = productline;
    }

    public String getProductline() {
        return productline;
    }

    public void setProductline(String productline) {
        this.productline = productline;
    }

    public String getTextdescription() {
        return textdescription;
    }

    public void setTextdescription(String textdescription) {
        this.textdescription = textdescription;
    }

    public String getHtmldescription() {
        return htmldescription;
    }

    public void setHtmldescription(String htmldescription) {
        this.htmldescription = htmldescription;
    }

    public Serializable getImage() {
        return image;
    }

    public void setImage(Serializable image) {
        this.image = image;
    }

    @XmlTransient
    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productline != null ? productline.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productline)) {
            return false;
        }
        Productline other = (Productline) object;
        if ((this.productline == null && other.productline != null) || (this.productline != null && !this.productline.equals(other.productline))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sit.int303.model.Productline[ productline=" + productline + " ]";
    }
    
}
