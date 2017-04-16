package ru.mti.bankclient.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import ru.mti.bankclient.shared.ServiceProvider;

/**
 *
 * @author Белых Евгений
 */
@Stateless
public class ServiceProviderFacade extends AbstractFacade<ServiceProvider> {

    @PersistenceUnit(unitName = "BankClientPU")
    private EntityManagerFactory factory;
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        em = factory.createEntityManager();
        return em;
    }

    public ServiceProviderFacade() {
        super(ServiceProvider.class);
    }

    public ServiceProvider findByInn(String inn) {
        getEntityManager();
        ServiceProvider serviceProvider = null;
        EntityTransaction trans = em.getTransaction();
        Query query = em.createNamedQuery("ServiceProvider.findByInn");
        query.setParameter("inn", inn);
        trans.begin();
        try {
            serviceProvider = (ServiceProvider) query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("Объект поставщика услуг не выбрался из базы по ИНН");
        } catch (Exception ex) {
            throw ex;
        }
        trans.commit();
        return serviceProvider;
    }

}
