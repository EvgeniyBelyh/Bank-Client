
package ru.mti.bankclient.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import ru.mti.bankclient.client.rpc.BankClientService;

import ru.mti.bankclient.client.rpc.LoginService;
import ru.mti.bankclient.session.ClientFacade;
import ru.mti.bankclient.session.OperationFacade;
import ru.mti.bankclient.shared.Account;
import ru.mti.bankclient.shared.AccountDTO;
import ru.mti.bankclient.shared.Client;
import ru.mti.bankclient.shared.ClientDTO;
import ru.mti.bankclient.shared.Operation;
import ru.mti.bankclient.shared.OperationDTO;
import ru.mti.bankclient.shared.PartnerBank;
import ru.mti.bankclient.shared.PartnerBankDTO;

/**
 *
 * @author Белых Евгений
 */
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService, BankClientService
{
    private static final long serialVersionUID = 4456105400553118785L;
    
    @EJB
    private ClientFacade clientFacade;
    @EJB
    private OperationFacade operationFacade;
    
    private byte TRY_COUNT = 5;
    
    @Override
    public ClientDTO loginServer(String login, String pass)
    {
        Client client;      
        ClientDTO user = null;
        int tryCount;
        
        if(getTryCountInSesstion() != null) {
            tryCount = getTryCountInSesstion();
        } else {
            tryCount = TRY_COUNT;
            setTryCountInSesstion(tryCount);            
        }
        
        //получаем объект клиента из базы данных
        client = clientFacade.findByLogin(login);
        
        if(client != null) {
            
            user = createClientDTO(client);
            
            //отправляем сведения о блокировке
            if(client.getBlocked()) {
                return user;
            }
            
            //уменьшаем количество попыток входа
            tryCount--; 
            setTryCountInSesstion(tryCount); 
            
            //блокируем пользователя
            if(tryCount < 1) {
                client.setBlocked(true);
                clientFacade.edit(client);
                user.setBlocked(true);
                return user;
            }
            //если неверный пароль, то возвращаем количество попыток
            if(!client.getPassword().equals(pass)) {
                user.setPassword(String.valueOf(tryCount));
            }
        }
                 
        //store the user/session id
        storeUserInSession(user);
 
        return user;
    }

     /**
     * Создает DTO для сущности Client
     * @param client - объект клиента
     * @return DTO
     */
    private ClientDTO createClientDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        clientDTO.setLogin(client.getLogin());
        clientDTO.setPassword(client.getPassword());
        clientDTO.setBlocked(client.getBlocked());
        clientDTO.setAdmin(client.getAdmin());
        
        if(client.getAccountList() != null) {
            for(Account acc : client.getAccountList()) {
                 clientDTO.getAccountList().add(createAccountDTO(acc));
            }
        }
        
        return clientDTO;
    }
    
    
    @Override
    public ClientDTO loginFromSessionServer()
    {
        return getUserAlreadyFromSession();
    }
 
    @Override
    public void logout()
    {
        deleteUserFromSession();
    }
 /*
    @Override
    public boolean changePassword(String name, String newPassword)
    {
        // change password logic
    }
*/ 
    private ClientDTO getUserAlreadyFromSession()
    {
        ClientDTO user = null;
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        Object userObj = session.getAttribute("user");
        if (userObj != null && userObj instanceof ClientDTO)
        {
            user = (ClientDTO) userObj;
        }
        return user;
    }
 
    private void storeUserInSession(ClientDTO user)
    {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("user", user);
    }
    
    private void setTryCountInSesstion(int count) {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("tryCount", count);        
    }

    private Integer getTryCountInSesstion() {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession(true);
        Integer count = (Integer) session.getAttribute("tryCount");
        return count;
    }
    
    private void deleteUserFromSession()
    {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        session.removeAttribute("user");
        session.removeAttribute("tryCount");
        session.invalidate();
    }

    
     /**
     * Создает DTO для сущности Account
     * @param account - объект счета клиента
     * @return DTO
     */
    private AccountDTO createAccountDTO(Account account) {
        
        AccountDTO accountDTO = new AccountDTO();
        
        accountDTO.setId(account.getId());
        accountDTO.setNumber(account.getNumber());
        accountDTO.setBalance(account.getBalance());
        accountDTO.setOverlimit(account.getOverlimit());
        accountDTO.setInterestRate(account.getInterestRate());
        accountDTO.setLoanRate(account.getLoanRate());
        accountDTO.setBlocked(account.getBlocked());
        accountDTO.setClientId(account.getClientId().getId());
        accountDTO.setAccountTypeId(account.getAccountTypeId().getId());
        accountDTO.setAccountTypeName(account.getAccountTypeId().getName());
        accountDTO.setCurrencyId(account.getCurrencyId().getId());
        accountDTO.setCurrencyName(account.getCurrencyId().getName());
        accountDTO.setCurrencyCode(account.getCurrencyId().getCode());
        accountDTO.setCardNumber(account.getCardNumber());
        accountDTO.setExpirationDate(account.getExpirationDate());
        accountDTO.setCvv(account.getCvv());
        
        if(account.getOperationList() != null) {
            for(Operation oper : account.getOperationList()) {
                accountDTO.getOperationList().add(createOperationDTO(oper));
            }
        }
        
        return accountDTO;
    }
    
    /**
     * Выбирает счета клиента
     * @param clientId - код клиента
     * @return - список счетов клиента
     */
    @Override
    public List<AccountDTO> getAccounts(int clientId) {
        
        Client client = clientFacade.find(clientId);
        ClientDTO clientDTO = createClientDTO(client);
        List list = new ArrayList(clientDTO.getAccountList());               
        return list;
        
    }

    @Override
    public ClientDTO checkUser(String login, String pass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Создает DTO для сущности PartnerDTO
     * @param partnerBank - объект банка-корреспондента
     * @return DTO
     */
    private PartnerBankDTO createPartnerBankDTO(PartnerBank partnerBank) {
        
        PartnerBankDTO partnerBankDTO = new PartnerBankDTO(partnerBank.getId(), 
                partnerBank.getName(), partnerBank.getInn(), partnerBank.getKpp(), 
                partnerBank.getBik(), partnerBank.getCorrAccount());
        
        return partnerBankDTO;
    }
    
     /**
     * Создает DTO для сущности PartnerDTO
     * @param operation - объект операции
     * @return DTO
     */
    private OperationDTO createOperationDTO(Operation operation) {
       
        OperationDTO operationDTO = new OperationDTO(operation.getId(), 
                operation.getCreateDate(), operation.getDescription(), 
                operation.getDestinationAccount(), operation.getNumber(), 
                operation.getExecutionDate(), operation.getAmount(), 
                operation.getComment(), operation.getAccountId().getId(), 
                operation.getOperationTypeId().getId(), operation.getOperationTypeId().getName(), 
                createPartnerBankDTO(operation.getPartnerBankId()), 
                operation.getStatusId().getId(), operation.getStatusId().getName());
     
        return operationDTO;
    } 
    
    /**
     * Сохраняет операцию в базу данных
     * @param operationDTO - DTO операции
     */
    @Override
    public void saveOperation(OperationDTO operationDTO) {
        operationFacade.create(new Operation(operationDTO));
    }

    @Override
    public void executeOperation() {
        
        BankSystem bankSystem = new BankSystem();
        bankSystem.executeOperations();
    }
    
}
