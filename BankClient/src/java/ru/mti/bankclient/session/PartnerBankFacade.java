
package ru.mti.bankclient.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import ru.mti.bankclient.shared.PartnerBank;

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
    
    public PartnerBank findByBik(String bik) {
        getEntityManager();
        PartnerBank pBank = null;
        EntityTransaction trans = em.getTransaction();
        Query query = em.createNamedQuery("PartnerBank.findByBik");
        query.setParameter("bik", bik);
        trans.begin();
        try {
            pBank = (PartnerBank) query.getSingleResult();
        } catch(NoResultException ex) {
            System.out.println("Объект банка-корреспондента не выбрался из базы по логину");
        } catch(Exception ex) {
            throw ex;
        }              
        trans.commit();
        return pBank;
    }   
    
}
