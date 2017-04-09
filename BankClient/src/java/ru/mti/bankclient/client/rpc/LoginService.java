
package ru.mti.bankclient.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;
import ru.mti.bankclient.shared.AccountDTO;
import ru.mti.bankclient.shared.ClientDTO;
import ru.mti.bankclient.shared.OperationDTO;

/**
 *
 * @author Белых Евгений
 */
@RemoteServiceRelativePath("loginservice")
public interface LoginService extends RemoteService
{
    /**
     * Utility class for simplifying access to the instance of async service.
     */
    public static class Util
    {
        private static LoginServiceAsync instance;
 
        public static LoginServiceAsync getInstance()
        {
            if (instance == null)
            {
                instance = GWT.create(LoginService.class);
            }
            return instance;
        }
    }
 
    ClientDTO loginServer(String name, String password);
 
    ClientDTO loginFromSessionServer();
     
    //boolean changePassword(String name, String newPassword);
 
    void logout();
    
    public ClientDTO checkUser(String login, String pass);
    
    public List<AccountDTO> getAccounts(int clientId);
    
    public void saveOperation(OperationDTO operationDTO);
    
    public void executeOperation();
}