
package ru.mti.bankclient.session;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import ru.mti.bankclient.shared.Operation;

/**
 *
 * @author Белых Евгений
 */
@Stateless
public class OperationFacade extends AbstractFacade<Operation> {

    @PersistenceUnit(unitName = "BankClientPU")
    private EntityManagerFactory factory;
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        em = factory.createEntityManager();
        return em;
    }

    public OperationFacade() {
        super(Operation.class);
    }
    
    
    public List<Operation> findByStatus(int statusId) {
        getEntityManager();
        List<Operation> operationList = new ArrayList();
        EntityTransaction trans = em.getTransaction();
        Query query = em.createNativeQuery("SELECT * FROM Operation WHERE status_id = 1", Operation.class);
        query.setParameter("status_id", statusId);
        trans.begin();
        try {
            operationList = query.getResultList();
        } catch(NoResultException ex) {
            System.out.println("Объект операции не выбрался из базы по статусу");
        } catch(Exception ex) {
            throw ex;
        }              
        trans.commit();
        return operationList;
    }   
    
}
