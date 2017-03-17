
package ru.mti.bankclient.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ru.mti.bankclient.client.User;

/**
 *
 * @author Белых Евгений
 */
@RemoteServiceRelativePath("usercheck/usercheck")
public interface UserCheck extends RemoteService {

    public User checkUser(String login, String pass);
}
