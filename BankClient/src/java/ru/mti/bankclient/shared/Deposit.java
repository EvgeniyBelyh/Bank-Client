/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.mti.bankclient.shared;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Жека
 */
@Entity
@Table(name = "deposit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deposit.findAll", query = "SELECT d FROM Deposit d")
    , @NamedQuery(name = "Deposit.findById", query = "SELECT d FROM Deposit d WHERE d.id = :id")
    , @NamedQuery(name = "Deposit.findByName", query = "SELECT d FROM Deposit d WHERE d.name = :name")
    , @NamedQuery(name = "Deposit.findByInterestRate", query = "SELECT d FROM Deposit d WHERE d.interestRate = :interestRate")
    , @NamedQuery(name = "Deposit.findByDuration", query = "SELECT d FROM Deposit d WHERE d.duration = :duration")
    , @NamedQuery(name = "Deposit.findByDiscription", query = "SELECT d FROM Deposit d WHERE d.discription = :discription")})
public class Deposit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "interest_rate")
    private float interestRate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "duration")
    private int duration;
    @Size(max = 250)
    @Column(name = "discription")
    private String discription;

    public Deposit() {
    }

    public Deposit(Integer id) {
        this.id = id;
    }

    public Deposit(Integer id, String name, float interestRate, int duration) {
        this.id = id;
        this.name = name;
        this.interestRate = interestRate;
        this.duration = duration;
    }

    public Deposit(DepositDTO depositDTO) {
        this.id = depositDTO.getId();
        this.name = depositDTO.getName();
        this.interestRate = depositDTO.getInterestRate();
        this.duration = depositDTO.getDuration();
        this.discription = depositDTO.getDiscription();
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

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
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
        if (!(object instanceof Deposit)) {
            return false;
        }
        Deposit other = (Deposit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.mti.bankclient.shared.Deposit[ id=" + id + " ]";
    }
    
}
