
package ru.mti.bankclient.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ru.mti.bankclient.entity.PartnerBank;

/**
 *
 * @author Белых Евгений
 */
@Stateless
public class PartnerBankFacade extends AbstractFacade<PartnerBank> {

    @PersistenceContext(unitName = "BankClientPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PartnerBankFacade() {
        super(PartnerBank.class);
    }
    
}
