
package ru.mti.bankclient.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Белых Евгений
 */
@Entity
@Table(name = "service_provider")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ServiceProvider.findAll", query = "SELECT s FROM ServiceProvider s"),
    @NamedQuery(name = "ServiceProvider.findById", query = "SELECT s FROM ServiceProvider s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceProvider.findByName", query = "SELECT s FROM ServiceProvider s WHERE s.name = :name"),
    @NamedQuery(name = "ServiceProvider.findByInn", query = "SELECT s FROM ServiceProvider s WHERE s.inn = :inn"),
    @NamedQuery(name = "ServiceProvider.findByAccountNumber", query = "SELECT s FROM ServiceProvider s WHERE s.accountNumber = :accountNumber")})
public class ServiceProvider implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "INN")
    private String inn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "account_number")
    private String accountNumber;
    @ManyToMany(mappedBy = "serviceProviderList")
    private List<ProviderCategory> providerCategoryList;
    @JoinColumn(name = "partner_bank_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PartnerBank partnerBankId;

    public ServiceProvider() {
    }

    public ServiceProvider(Integer id) {
        this.id = id;
    }

    public ServiceProvider(Integer id, String name, String inn, String accountNumber) {
        this.id = id;
        this.name = name;
        this.inn = inn;
        this.accountNumber = accountNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @XmlTransient
    public List<ProviderCategory> getProviderCategoryList() {
        return providerCategoryList;
    }

    public void setProviderCategoryList(List<ProviderCategory> providerCategoryList) {
        this.providerCategoryList = providerCategoryList;
    }

    public PartnerBank getPartnerBankId() {
        return partnerBankId;
    }

    public void setPartnerBankId(PartnerBank partnerBankId) {
        this.partnerBankId = partnerBankId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServiceProvider)) {
            return false;
        }
        ServiceProvider other = (ServiceProvider) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.mti.bankclient.entity.ServiceProvider[ id=" + id + " ]";
    }
    
}
