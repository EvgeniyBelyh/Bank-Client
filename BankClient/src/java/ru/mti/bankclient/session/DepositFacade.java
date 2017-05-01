
package ru.mti.bankclient.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import ru.mti.bankclient.shared.Deposit;

/**
 *
 * @author Евгений Белых
 */
@Stateless
public class DepositFacade extends AbstractFacade<Deposit> {

    @PersistenceUnit(unitName = "BankClientPU")
    private EntityManagerFactory factory;
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        em = factory.createEntityManager();
        return em;
    }

    public DepositFacade() {
        super(Deposit.class);
    }
    
}
