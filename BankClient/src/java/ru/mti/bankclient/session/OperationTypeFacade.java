
package ru.mti.bankclient.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import ru.mti.bankclient.shared.OperationType;

/**
 *
 * @author Белых Евгений
 */
@Stateless
public class OperationTypeFacade extends AbstractFacade<OperationType> {

    @PersistenceUnit(unitName = "BankClientPU")
    private EntityManagerFactory factory;
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        em = factory.createEntityManager();
        return em;
    }

    public OperationTypeFacade() {
        super(OperationType.class);
    }
    
}
