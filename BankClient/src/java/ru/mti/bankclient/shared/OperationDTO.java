
package ru.mti.bankclient.shared;

import java.io.Serializable;
import java.util.Date;

/**
 * Класс-обертка на стороне клиента для сущности Operation
 * @author Белых Евгений
 */

public class OperationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Date createDate; // дата создания операции
    private String description; // назначение платежа
    private String destinationAccount; // счет назначения перевода
    private Integer number; // номер операции
    private Date executionDate; // дата исполнения операции
    private double amount; // сумма операции
    private String comment; // комментарий
    private int accountId; // код счета клиента
    private int operationTypeId; // код типа операции
    private String operationTypeName; // наименование типа операции
    private PartnerBankDTO partnerBankId; // код банка-корреспондента
    private int statusId; // код статуса операции
    private String statusName; // наименование статуса

    public OperationDTO() {
    }

    public OperationDTO(Integer id) {
        this.id = id;
    }

    public OperationDTO(Integer id, Date createDate, String description, String destinationAccount, Integer number, Date executionDate, double amount, String comment, int accountId, int operationTypeId, String operationTypeName, PartnerBankDTO partnerBankId, int statusId, String statusName) {
        this.id = id;
        this.createDate = createDate;
        this.description = description;
        this.destinationAccount = destinationAccount;
        this.number = number;
        this.executionDate = executionDate;
        this.amount = amount;
        this.comment = comment;
        this.accountId = accountId;
        this.operationTypeId = operationTypeId;
        this.operationTypeName = operationTypeName;
        this.partnerBankId = partnerBankId;
        this.statusId = statusId;
        this.statusName = statusName;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getOperationTypeName() {
        return operationTypeName;
    }

    public void setOperationTypeName(String operationTypeName) {
        this.operationTypeName = operationTypeName;
    }
    
    public PartnerBankDTO getPartnerBankId() {
        return partnerBankId;
    }

    public void setPartnerBankId(PartnerBankDTO partnerBankId) {
        this.partnerBankId = partnerBankId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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
        if (!(object instanceof OperationDTO)) {
            return false;
        }
        OperationDTO other = (OperationDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.mti.bankclient.shared.Operation[ id=" + id + " ]";
    }
    
}
