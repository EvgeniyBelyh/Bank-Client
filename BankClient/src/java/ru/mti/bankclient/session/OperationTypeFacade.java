/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.mti.bankclient.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ru.mti.bankclient.entity.OperationType;

/**
 *
 * @author 1
 */
@Stateless
public class OperationTypeFacade extends AbstractFacade<OperationType> {

    @PersistenceContext(unitName = "BankClientPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OperationTypeFacade() {
        super(OperationType.class);
    }
    
}
