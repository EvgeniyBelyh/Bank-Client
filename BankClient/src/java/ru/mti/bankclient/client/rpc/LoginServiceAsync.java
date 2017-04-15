
package ru.mti.bankclient.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;
import ru.mti.bankclient.shared.AccountDTO;
import ru.mti.bankclient.shared.ClientDTO;
import ru.mti.bankclient.shared.DepositDTO;
import ru.mti.bankclient.shared.OperationDTO;

/**
 *
 * @author Белых Евгений
 */
public interface LoginServiceAsync {
    
    public void loginServer(String name, String password, AsyncCallback<ClientDTO> asyncCallback);
 
    public void loginFromSessionServer(AsyncCallback<ClientDTO> asyncCallback);
 
    void logout(AsyncCallback<Void> asyncCallback);

    public void getAccounts(int clientId, AsyncCallback<List<AccountDTO>> asyncCallback);
    
    public void saveOperation(OperationDTO operationDTO, AsyncCallback<Void> asyncCallback);

    public void saveAccount(DepositDTO depositDTO, AsyncCallback<Void> asyncCallback);
    
    public void executeOperation(AsyncCallback asyncCallback);
    
    public void getDeposits(AsyncCallback<List<DepositDTO>> asyncCallback);

}
