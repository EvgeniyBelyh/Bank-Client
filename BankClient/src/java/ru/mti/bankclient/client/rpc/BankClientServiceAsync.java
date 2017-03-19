
package ru.mti.bankclient.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;
import ru.mti.bankclient.shared.AccountDTO;
import ru.mti.bankclient.shared.ClientDTO;

/**
 *
 * @author Белых Евгений
 */
public interface BankClientServiceAsync {

    public void checkUser(String login, String pass, AsyncCallback<ClientDTO> asyncCallback);

    public void getAccounts(int clientId, AsyncCallback<List<AccountDTO>> asyncCallback);
}
