
package ru.mti.bankclient.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import ru.mti.bankclient.entity.Client;

/**
 *
 * @author Белых Евгений
 */
@Stateless
public class ClientFacade extends AbstractFacade<Client> {

    @PersistenceUnit(unitName = "BankClientPU")
    private EntityManagerFactory factory;
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        em = factory.createEntityManager();
        return em;
    }

    public ClientFacade() {
        super(Client.class);
    }
    
    public Client findByLoginAndPassword(String login, String pass) {
        getEntityManager();
        Client client = null;
        EntityTransaction trans = em.getTransaction();
        Query query = em.createNamedQuery("Client.findByLoginAndPassword");
        query.setParameter("login", login);
        query.setParameter("password", pass);
        trans.begin();
        try {
            client = (Client) query.getSingleResult();
        } catch(NoResultException ex) {
            System.out.println("Объект клиента не выбрался из базы по логину и паролю");
        } catch(Exception ex) {
            throw ex;
        }              
        trans.commit();
        return client;
    }
    
    
     public Client findByLogin(String login) {
        getEntityManager();
        Client client = null;
        EntityTransaction trans = em.getTransaction();
        Query query = em.createNamedQuery("Client.findByLogin");
        query.setParameter("login", login);
        trans.begin();
        try {
            client = (Client) query.getSingleResult();
        } catch(NoResultException ex) {
            System.out.println("Объект клиента не выбрался из базы по логину");
        } catch(Exception ex) {
            throw ex;
        }              
        trans.commit();
        return client;
    }   
    
}