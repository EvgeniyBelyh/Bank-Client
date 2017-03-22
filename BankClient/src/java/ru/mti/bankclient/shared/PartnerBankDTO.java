
package ru.mti.bankclient.shared;

import java.io.Serializable;


/**
 * Класс-обертка на стороне клиента для сущности PartnerBank
 * @author Белых Евгений
 */

public class PartnerBankDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id; // код банка-корреспондента
    private String name; // краткое наименование банка-корреспондента
    private String inn; // ИНН банка-корреспондента
    private String kpp; // КПП банка-корреспондента
    private String bik; // БИК банка-корреспондента
    private String corrAccount; // корреспондентский счет банка-корреспондента


    public PartnerBankDTO() {
    }

    public PartnerBankDTO(Integer id) {
        this.id = id;
    }

    public PartnerBankDTO(Integer id, String name, String inn, String kpp, String bik, String corrAccount) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PartnerBankDTO)) {
            return false;
        }
        PartnerBankDTO other = (PartnerBankDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.mti.bankclient.shared.PartnerBank[ id=" + id + " ]";
    }
    
}
