
package ru.mti.bankclient.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ru.mti.bankclient.entity.ProviderCategory;

/**
 *
 * @author Белых Евгений
 */
@Stateless
public class ProviderCategoryFacade extends AbstractFacade<ProviderCategory> {

    @PersistenceContext(unitName = "BankClientPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProviderCategoryFacade() {
        super(ProviderCategory.class);
    }
    
}
