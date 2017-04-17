
package ru.mti.bankclient.shared;

import java.io.Serializable;

/**
 * DTO для сущности шаблона операции
 * @author Евгений Белых
 */

public class TemplateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String description;
    private String destinationAccount;
    private String name;
    private int accountId;
    private int operationTypeId;
    private int partnerBankId;
    private double amount;

    public TemplateDTO() {
    }

    public TemplateDTO(Integer id) {
        this.id = id;
    }

    public TemplateDTO(Integer id, String description, String destinationAccount) {
        this.id = id;
        this.description = description;
        this.destinationAccount = destinationAccount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(int operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public int getPartnerBankId() {
        return partnerBankId;
    }

    public void setPartnerBankId(int partnerBankId) {
        this.partnerBankId = partnerBankId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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
        if (!(object instanceof TemplateDTO)) {
            return false;
        }
        TemplateDTO other = (TemplateDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.mti.bankclient.shared.Template[ id=" + id + " ]";
    }
    
}
