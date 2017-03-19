
package ru.mti.bankclient.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;
import ru.mti.bankclient.shared.Account;

/**
 *
 * @author Белых Евгений
 */
public interface ClientAccountsAsync {

    public void getAccountList(int clientId, AsyncCallback<List<Account>> asyncCallback);
}
