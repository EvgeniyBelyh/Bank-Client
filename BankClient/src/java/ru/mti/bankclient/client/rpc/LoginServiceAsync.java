
package ru.mti.bankclient.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ru.mti.bankclient.shared.ClientDTO;

/**
 *
 * @author Белых Евгений
 */
public interface LoginServiceAsync {
    
    public void loginServer(String name, String password, AsyncCallback<ClientDTO> asyncCallback);
 
    public void loginFromSessionServer(AsyncCallback<ClientDTO> asyncCallback);
 
    void logout(AsyncCallback<Void> asyncCallback);
}
