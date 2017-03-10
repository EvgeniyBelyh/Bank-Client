
package ru.mti.bankclient.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import ru.mti.bankclient.entity.PartnerBank;

/**
 *
 * @author Белых Евгений
 */
@Stateless
public class PartnerBankFacade extends AbstractFacade<PartnerBank> {

    @PersistenceUnit(unitName = "BankClientPU")
    private EntityManagerFactory factory;
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        em = factory.createEntityManager();
        return em;
    }

    public PartnerBankFacade() {
        super(PartnerBank.class);
    }
    
}
