
package ru.mti.bankclient.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;
import ru.mti.bankclient.shared.AccountDTO;
import ru.mti.bankclient.shared.ClientDTO;
import ru.mti.bankclient.shared.DepositDTO;
import ru.mti.bankclient.shared.OperationDTO;
import ru.mti.bankclient.shared.PartnerBankDTO;
import ru.mti.bankclient.shared.ProviderCategories;
import ru.mti.bankclient.shared.ServiceProviderDTO;
import ru.mti.bankclient.shared.TemplateDTO;

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

    public void openDeposit(DepositDTO depositDTO, AsyncCallback<String> asyncCallback);
    
    public void executeOperation(OperationDTO operationDTO, AsyncCallback<Void> asyncCallback);
    
    public void getDeposits(AsyncCallback<List<DepositDTO>> asyncCallback);

    public void getServiceProviderByCategory(int categorieId, AsyncCallback<List<ServiceProviderDTO>> asyncCallback);
    
    public void getServiceProviderByInn(String inn, AsyncCallback<ServiceProviderDTO> asyncCallback);
    
    public void getPartnerBankByBik(String bik, AsyncCallback<PartnerBankDTO> asyncCallback);
    
    public void saveTemplate(TemplateDTO templateDTO, AsyncCallback<Void> asyncCallback);
    
    public void closeDeposit(AccountDTO accountDTO, AsyncCallback<Void> asyncCallback);
    
    public void deleteTemplate(TemplateDTO templateDTO, AsyncCallback<Void> asyncCallback);
}
