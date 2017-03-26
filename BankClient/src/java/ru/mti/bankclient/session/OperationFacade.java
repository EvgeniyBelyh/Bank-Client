
package ru.mti.bankclient.session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import ru.mti.bankclient.shared.Operation;
import ru.mti.bankclient.shared.Status;

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
    
    
    public List<Operation> findByStatus(Status statusId) {
        getEntityManager();
        List<Operation> operationList = new ArrayList();
        
        EntityTransaction trans = em.getTransaction();
        Query query = em.createNamedQuery("Operation.findByStatus");
        query.setParameter("statusId", statusId);
        trans.begin();
        try {
            List tempList = query.getResultList();
            Iterator iterator = tempList.iterator();
            while(iterator.hasNext()) {
                Operation oper = (Operation) iterator.next();
                operationList.add(oper);
            }
        } catch(NoResultException ex) {
            System.out.println("Объект операции не выбрался из базы по статусу");
//        } catch(Exception ex) {
//            throw ex;
        }              
        trans.commit();
        return operationList;
    }   
    
}
