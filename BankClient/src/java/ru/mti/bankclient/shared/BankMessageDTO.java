
package ru.mti.bankclient.shared;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO для сущности сообщения от банка
 * @author Евгений Белых
 */

public class BankMessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String text;
    private int clientId;
    private Date messageDate;

    public BankMessageDTO() {
    }

    public BankMessageDTO(Integer id) {
        this.id = id;
    }

    public BankMessageDTO(Integer id, String text) {
        this.id = id;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
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
        if (!(object instanceof BankMessageDTO)) {
            return false;
        }
        BankMessageDTO other = (BankMessageDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.mti.bankclient.shared.BankMessage[ id=" + id + " ]";
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }
    
}
