
package ru.mti.bankclient.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ru.mti.bankclient.client.rpc.LoginService;
import ru.mti.bankclient.session.ClientFacade;
import ru.mti.bankclient.session.OperationFacade;
import ru.mti.bankclient.shared.Account;
import ru.mti.bankclient.shared.Client;
import ru.mti.bankclient.shared.ClientDTO;

/**
 *
 * @author Белых Евгений
 */
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService
{
    private static final long serialVersionUID = 4456105400553118785L;
    
    @EJB
    private ClientFacade clientFacade;
    @EJB
    private OperationFacade operationFacade;
    
    private static byte tryCount = 5;
    
    @Override
    public ClientDTO loginServer(String login, String pass)
    {
        Client client;      
        ClientDTO user = null;
        
        //получаем объект клиента из базы данных
        client = clientFacade.findByLogin(login);
        
        if(client != null) {
            
            user = createClientDTO(client);
            
            //отправляем сведения о блокировке
            if(client.getBlocked()) {
                return user;
            }
            
            tryCount--; //уменьшаем количество попыток входа
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
        /*
        if(client.getAccountList() != null) {
            for(Account acc : client.getAccountList()) {
                 clientDTO.getAccountList().add(createAccountDTO(acc));
            }
        }
        */
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
 
    private void deleteUserFromSession()
    {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        session.removeAttribute("user");
    }
 
}
