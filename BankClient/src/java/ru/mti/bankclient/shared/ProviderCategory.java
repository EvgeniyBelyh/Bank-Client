/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.mti.bankclient.shared;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Жека
 */
@Entity
@Table(name = "provider_category")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProviderCategory.findAll", query = "SELECT p FROM ProviderCategory p")
    , @NamedQuery(name = "ProviderCategory.findById", query = "SELECT p FROM ProviderCategory p WHERE p.id = :id")
    , @NamedQuery(name = "ProviderCategory.findByName", query = "SELECT p FROM ProviderCategory p WHERE p.name = :name")})
public class ProviderCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @JoinTable(name = "provider_category_has_service_provider", joinColumns = {
        @JoinColumn(name = "provider_category_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "service_provider_id", referencedColumnName = "id")})
    @ManyToMany
    private List<ServiceProviderDTO> serviceProviderList;

    public ProviderCategory() {
    }

    public ProviderCategory(Integer id) {
        this.id = id;
    }

    public ProviderCategory(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    @XmlTransient
    public List<ServiceProviderDTO> getServiceProviderList() {
        return serviceProviderList;
    }

    public void setServiceProviderList(List<ServiceProviderDTO> serviceProviderList) {
        this.serviceProviderList = serviceProviderList;
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
        if (!(object instanceof ProviderCategory)) {
            return false;
        }
        ProviderCategory other = (ProviderCategory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.mti.bankclient.shared.ProviderCategory[ id=" + id + " ]";
    }
    
}
