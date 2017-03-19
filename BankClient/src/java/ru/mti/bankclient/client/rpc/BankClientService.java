
package ru.mti.bankclient.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;
import ru.mti.bankclient.shared.AccountDTO;
import ru.mti.bankclient.shared.ClientDTO;

/**
 *
 * @author Белых Евгений
 */
@RemoteServiceRelativePath("usercheck")
public interface BankClientService extends RemoteService {

    public ClientDTO checkUser(String login, String pass);
    
    public List<AccountDTO> getAccounts(int clientId);
}
