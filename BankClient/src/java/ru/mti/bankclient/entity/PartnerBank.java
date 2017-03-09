
package ru.mti.bankclient.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "partner_bank")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PartnerBank.findAll", query = "SELECT p FROM PartnerBank p"),
    @NamedQuery(name = "PartnerBank.findById", query = "SELECT p FROM PartnerBank p WHERE p.id = :id"),
    @NamedQuery(name = "PartnerBank.findByName", query = "SELECT p FROM PartnerBank p WHERE p.name = :name"),
    @NamedQuery(name = "PartnerBank.findByInn", query = "SELECT p FROM PartnerBank p WHERE p.inn = :inn"),
    @NamedQuery(name = "PartnerBank.findByKpp", query = "SELECT p FROM PartnerBank p WHERE p.kpp = :kpp"),
    @NamedQuery(name = "PartnerBank.findByBik", query = "SELECT p FROM PartnerBank p WHERE p.bik = :bik"),
    @NamedQuery(name = "PartnerBank.findByCorrAccount", query = "SELECT p FROM PartnerBank p WHERE p.corrAccount = :corrAccount")})
public class PartnerBank implements Serializable {

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
    @Size(min = 1, max = 10)
    @Column(name = "KPP")
    private String kpp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "BIK")
    private String bik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "corr_account")
    private String corrAccount;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partnerBankId")
    private List<Template> templateList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partnerBankId")
    private List<ServiceProvider> serviceProviderList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partnerBankId")
    private List<Operation> operationList;

    public PartnerBank() {
    }

    public PartnerBank(Integer id) {
        this.id = id;
    }

    public PartnerBank(Integer id, String name, String inn, String kpp, String bik, String corrAccount) {
        this.id = id;
        this.name = name;
        this.inn = inn;
        this.kpp = kpp;
        this.bik = bik;
        this.corrAccount = corrAccount;
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

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getBik() {
        return bik;
    }

    public void setBik(String bik) {
        this.bik = bik;
    }

    public String getCorrAccount() {
        return corrAccount;
    }

    public void setCorrAccount(String corrAccount) {
        this.corrAccount = corrAccount;
    }

    @XmlTransient
    public List<Template> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<Template> templateList) {
        this.templateList = templateList;
    }

    @XmlTransient
    public List<ServiceProvider> getServiceProviderList() {
        return serviceProviderList;
    }

    public void setServiceProviderList(List<ServiceProvider> serviceProviderList) {
        this.serviceProviderList = serviceProviderList;
    }

    @XmlTransient
    public List<Operation> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<Operation> operationList) {
        this.operationList = operationList;
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
        if (!(object instanceof PartnerBank)) {
            return false;
        }
        PartnerBank other = (PartnerBank) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.mti.bankclient.entity.PartnerBank[ id=" + id + " ]";
    }
    
}
