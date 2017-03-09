
package ru.mti.bankclient.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Белых Евгений
 */
@Entity
@Table(name = "operation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Operation.findAll", query = "SELECT o FROM Operation o"),
    @NamedQuery(name = "Operation.findById", query = "SELECT o FROM Operation o WHERE o.id = :id"),
    @NamedQuery(name = "Operation.findByCreateDate", query = "SELECT o FROM Operation o WHERE o.createDate = :createDate"),
    @NamedQuery(name = "Operation.findByDescription", query = "SELECT o FROM Operation o WHERE o.description = :description"),
    @NamedQuery(name = "Operation.findByDestinationAccount", query = "SELECT o FROM Operation o WHERE o.destinationAccount = :destinationAccount"),
    @NamedQuery(name = "Operation.findByNumber", query = "SELECT o FROM Operation o WHERE o.number = :number"),
    @NamedQuery(name = "Operation.findByExecutionDate", query = "SELECT o FROM Operation o WHERE o.executionDate = :executionDate"),
    @NamedQuery(name = "Operation.findByAmount", query = "SELECT o FROM Operation o WHERE o.amount = :amount"),
    @NamedQuery(name = "Operation.findByComment", query = "SELECT o FROM Operation o WHERE o.comment = :comment")})
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "destination_account")
    private String destinationAccount;
    @Column(name = "number")
    private Integer number;
    @Column(name = "execution_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date executionDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount")
    private double amount;
    @Size(max = 200)
    @Column(name = "comment")
    private String comment;
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account accountId;
    @JoinColumn(name = "operation_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OperationType operationTypeId;
    @JoinColumn(name = "partner_bank_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PartnerBank partnerBankId;
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Status statusId;

    public Operation() {
    }

    public Operation(Integer id) {
        this.id = id;
    }

    public Operation(Integer id, Date createDate, String description, String destinationAccount, double amount) {
        this.id = id;
        this.createDate = createDate;
        this.description = description;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
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

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    public OperationType getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(OperationType operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public PartnerBank getPartnerBankId() {
        return partnerBankId;
    }

    public void setPartnerBankId(PartnerBank partnerBankId) {
        this.partnerBankId = partnerBankId;
    }

    public Status getStatusId() {
        return statusId;
    }

    public void setStatusId(Status statusId) {
        this.statusId = statusId;
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
        if (!(object instanceof Operation)) {
            return false;
        }
        Operation other = (Operation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.mti.bankclient.entity.Operation[ id=" + id + " ]";
    }
    
}
