
package ru.mti.bankclient.shared;

import java.io.Serializable;

/**
 * DTO для сущности Депозит
 * @author Евгений Белых
 */

public class DepositDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private float interestRate;
    private int duration;
    private String discription;

    public DepositDTO() {
    }

    public DepositDTO(Integer id) {
        this.id = id;
    }

    public DepositDTO(Integer id, String name, float interestRate, int duration, String discription) {
        this.id = id;
        this.name = name;
        this.interestRate = interestRate;
        this.duration = duration;
        this.discription = discription;
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
        if (!(object instanceof DepositDTO)) {
            return false;
        }
        DepositDTO other = (DepositDTO) object;
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
