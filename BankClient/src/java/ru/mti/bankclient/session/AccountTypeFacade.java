
package ru.mti.bankclient.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import ru.mti.bankclient.entity.AccountType;

/**
 *
 * @author Белых Евгений
 */
@Stateless
public class AccountTypeFacade extends AbstractFacade<AccountType> {

    @PersistenceUnit(unitName = "BankClientPU")
    private EntityManagerFactory factory;
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        em = factory.createEntityManager();
        return em;
    }

    public AccountTypeFacade() {
        super(AccountType.class);
    }
    
}
