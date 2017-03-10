
package ru.mti.bankclient.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import ru.mti.bankclient.entity.Template;

/**
 *
 * @author Белых Евгений
 */
@Stateless
public class TemplateFacade extends AbstractFacade<Template> {

    @PersistenceUnit(unitName = "BankClientPU")
    private EntityManagerFactory factory;
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        em = factory.createEntityManager();
        return em;
    }

    public TemplateFacade() {
        super(Template.class);
    }
    
}
