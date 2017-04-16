
package ru.mti.bankclient.shared;

import java.io.Serializable;

/**
 * DTO для сущности поставщика услуг
 * @author Евгений Белых
 */

public class ServiceProviderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String inn;
    private String accountNumber;
    private int partnerBankId;

    public ServiceProviderDTO() {
    }

    public ServiceProviderDTO(Integer id) {
        this.id = id;
    }

    public ServiceProviderDTO(Integer id, String name, String inn, String accountNumber, int partnerBankId) {
        this.id = id;
        this.name = name;
        this.inn = inn;
        this.accountNumber = accountNumber;
        this.partnerBankId = partnerBankId;
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

    public int getPartnerBankId() {
        return partnerBankId;
    }

    public void setPartnerBankId(int partnerBankId) {
        this.partnerBankId = partnerBankId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
/*
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServiceProviderDTO)) {
            return false;
        }
        ServiceProviderDTO other = (ServiceProviderDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
*/
    @Override
    public String toString() {
        return "ru.mti.bankclient.shared.ServiceProvider[ id=" + id + " ]";
    }
    
}
