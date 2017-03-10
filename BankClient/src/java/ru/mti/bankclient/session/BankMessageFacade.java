
package ru.mti.bankclient.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import ru.mti.bankclient.entity.BankMessage;

/**
 *
 * @author Белых Евгений
 */
@Stateless
public class BankMessageFacade extends AbstractFacade<BankMessage> {

    @PersistenceUnit(unitName = "BankClientPU")
    private EntityManagerFactory factory;
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        em = factory.createEntityManager();
        return em;
    }

    public BankMessageFacade() {
        super(BankMessage.class);
    }
    
}
