
package ru.mti.bankclient.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ru.mti.bankclient.client.User;

/**
 *
 * @author Белых Евгений
 */
public interface UserCheckAsync {

    public void checkUser(String login, String pass, AsyncCallback<User> asyncCallback);
}
