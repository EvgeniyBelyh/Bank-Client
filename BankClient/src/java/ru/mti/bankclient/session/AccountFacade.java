package ru.mti.bankclient.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import ru.mti.bankclient.shared.Account;

/**
 *
 * @author Белых Евгений
 */
@Stateless
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceUnit(unitName = "BankClientPU")
    private EntityManagerFactory factory;
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        em = factory.createEntityManager();
        return em;
    }

    public AccountFacade() {
        super(Account.class);
    }

    
    public Account findByNumber(String number) {
        getEntityManager();
        Account account = null;
        EntityTransaction trans = em.getTransaction();
        Query query = em.createNamedQuery("Account.findByNumber");
        query.setParameter("number", number);
        trans.begin();
        try {
            account = (Account) query.getSingleResult();
        } catch(NoResultException ex) {
            System.out.println("Объект счета не выбрался из базы по логину и паролю");
        } catch(Exception ex) {
            throw ex;
        }              
        trans.commit();
        em.clear();
        em.close();
        return account;
    }
    
}
