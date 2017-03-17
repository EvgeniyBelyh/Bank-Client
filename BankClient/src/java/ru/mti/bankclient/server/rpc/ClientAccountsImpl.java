
package ru.mti.bankclient.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

import ru.mti.bankclient.client.rpc.ClientAccounts;
import ru.mti.bankclient.entity.Account;
import ru.mti.bankclient.entity.Client;
import ru.mti.bankclient.session.ClientFacade;

/**
 * Выбирает список счетов клиента
 * @author Белых Евгений
 */
public class ClientAccountsImpl extends RemoteServiceServlet implements ClientAccounts {

    @EJB
    private ClientFacade clientFacade;
    
    public List<Account> getAccountList(int clientId) {
        
        Client client = clientFacade.find(clientId);
        return client.getAccountList();
        
    }
}
