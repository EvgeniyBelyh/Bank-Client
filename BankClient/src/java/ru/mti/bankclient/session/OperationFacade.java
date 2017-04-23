
package ru.mti.bankclient.session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import ru.mti.bankclient.shared.Operation;
import ru.mti.bankclient.shared.Status;

/**
 *
 * @author Белых Евгений
 */
@Stateless
public class OperationFacade extends AbstractFacade<Operation> {

    @PersistenceUnit(unitName = "BankClientPU")
    private EntityManagerFactory factory;
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        em = factory.createEntityManager();
        return em;
    }

    public OperationFacade() {
        super(Operation.class);
    } 
    
}
