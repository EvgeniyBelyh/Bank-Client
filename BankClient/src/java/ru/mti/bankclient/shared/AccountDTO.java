
package ru.mti.bankclient.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DTO для сущности Account (счет)
 * @author Белых Евгений
 */

public class AccountDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String number;
    private double balance;
    private double overlimit;
    private float interestRate;
    private float loanRate;
    private String cardNumber;
    private boolean blocked;
    private Date expirationDate;
    private String cvv;
    private String accountName;
    //private List<Template> templateList;
    private List<OperationDTO> operationList = new ArrayList();
    private int accountTypeId;
    private String accountTypeName;
    private int clientId;
    private int currencyId;
    private String currencyName;
    private String currencyCode;

    public AccountDTO() {
    }

    public AccountDTO(Integer id) {
        this.id = id;
    }

    public AccountDTO(Integer id, String number, double balance, double overlimit, 
            float interestRate, float loanRate, String cardNumber, 
            boolean blocked, Date expirationDate, String cvv, int accountTypeId, 
            String accountTypeName, int clientId, int currencyId, 
            String currencyName, String currencyCode) {
        this.id = id;
        this.number = number;
        this.balance = balance;
        this.overlimit = overlimit;
        this.interestRate = interestRate;
        this.loanRate = loanRate;
        this.cardNumber = cardNumber;
        this.blocked = blocked;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.accountTypeId = accountTypeId;
        this.accountTypeName = accountTypeName;
        this.clientId = clientId;
        this.currencyId = currencyId;
        this.currencyName = currencyName;
        this.currencyCode = currencyCode;
    }

    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getOverlimit() {
        return overlimit;
    }

    public void setOverlimit(double overlimit) {
        this.overlimit = overlimit;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public float getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(float loanRate) {
        this.loanRate = loanRate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
/*
    public List<Template> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<Template> templateList) {
        this.templateList = templateList;
    }

    public List<Operation> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<Operation> operationList) {
        this.operationList = operationList;
    }
*/
    public int getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(int accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AccountDTO)) {
            return false;
        }
        AccountDTO other = (AccountDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.mti.bankclient.shared.Account[ id=" + id + " ]";
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public void setAccountTypeName(String accountTypeName) {
        this.accountTypeName = accountTypeName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public List<OperationDTO> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<OperationDTO> operationList) {
        this.operationList = operationList;
    }
    
}
