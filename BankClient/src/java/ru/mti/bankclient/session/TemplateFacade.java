
package ru.mti.bankclient.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ru.mti.bankclient.entity.Template;

/**
 *
 * @author Белых Евгений
 */
@Stateless
public class TemplateFacade extends AbstractFacade<Template> {

    @PersistenceContext(unitName = "BankClientPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TemplateFacade() {
        super(Template.class);
    }
    
}
