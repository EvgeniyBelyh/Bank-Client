
package ru.mti.bankclient.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;
import ru.mti.bankclient.entity.Account;

/**
 * Интерфейс удаленного сервиса получения списка счетов клиента
 * @author Белых Евгений
 */
@RemoteServiceRelativePath("clientaccounts")
public interface ClientAccounts extends RemoteService {

    public List<Account> getAccountList(int clientId);
}
