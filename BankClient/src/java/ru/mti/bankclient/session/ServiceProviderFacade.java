
package ru.mti.bankclient.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import ru.mti.bankclient.entity.ServiceProvider;

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
    
}
