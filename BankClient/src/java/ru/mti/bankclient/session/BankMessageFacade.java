
package ru.mti.bankclient.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ru.mti.bankclient.entity.BankMessage;

/**
 *
 * @author Белых Евгений
 */
@Stateless
public class BankMessageFacade extends AbstractFacade<BankMessage> {

    @PersistenceContext(unitName = "BankClientPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BankMessageFacade() {
        super(BankMessage.class);
    }
    
}
