
package ru.mti.bankclient.session;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Абстрактный класс для управляющих классов, взаимодействующих
 * с базой данных
 * @author Белых Евгений
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        EntityManager manager = getEntityManager();
        EntityTransaction trans = manager.getTransaction();
        trans.begin();
        manager.persist(entity);
        manager.flush();
        trans.commit();
        
    }

    public void edit(T entity) {
        EntityManager manager = getEntityManager();
        EntityTransaction trans = manager.getTransaction();
        trans.begin();
        manager.merge(entity);
        manager.flush();
        trans.commit();
    }

    public void remove(T entity) {
        EntityManager manager = getEntityManager();
        EntityTransaction trans = manager.getTransaction();
        trans.begin();
        manager.remove(manager.merge(entity));
        manager.flush();
        trans.commit();
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
