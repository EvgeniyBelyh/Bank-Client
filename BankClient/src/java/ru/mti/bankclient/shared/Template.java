/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.mti.bankclient.shared;

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
 * @author Жека
 */
@Entity
@Table(name = "template")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Template.findAll", query = "SELECT t FROM Template t")
    , @NamedQuery(name = "Template.findById", query = "SELECT t FROM Template t WHERE t.id = :id")
    , @NamedQuery(name = "Template.findByDescription", query = "SELECT t FROM Template t WHERE t.description = :description")
    , @NamedQuery(name = "Template.findByDestinationAccount", query = "SELECT t FROM Template t WHERE t.destinationAccount = :destinationAccount")
    , @NamedQuery(name = "Template.findByNumber", query = "SELECT t FROM Template t WHERE t.number = :number")
    , @NamedQuery(name = "Template.findByExecutionDate", query = "SELECT t FROM Template t WHERE t.executionDate = :executionDate")
    , @NamedQuery(name = "Template.findByComment", query = "SELECT t FROM Template t WHERE t.comment = :comment")})
public class Template implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
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

    public Template() {
    }

    public Template(Integer id) {
        this.id = id;
    }

    public Template(Integer id, String description, String destinationAccount) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Template)) {
            return false;
        }
        Template other = (Template) object;
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
