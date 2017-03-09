
package ru.mti.bankclient.server.UserCheck;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import javax.ejb.EJB;

import ru.mti.bankclient.client.UserCheck.UserCheck;
import ru.mti.bankclient.entity.Client;
import ru.mti.bankclient.session.ClientFacade;


/**
 *
 * @author Белых Евгений
 */
public class UserCheckImpl extends RemoteServiceServlet implements UserCheck {
    
    @EJB
    private ClientFacade clientFacade;
    
    public String checkUser(String login, String pass) {
        
        Client client = clientFacade.find(1);
        return "Server says: User " + client.getName() + " is now loging in";
    }
}
