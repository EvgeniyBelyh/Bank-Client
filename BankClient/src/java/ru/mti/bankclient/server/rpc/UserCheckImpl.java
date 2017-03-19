
package ru.mti.bankclient.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import javax.ejb.EJB;
import ru.mti.bankclient.client.User;
import ru.mti.bankclient.client.rpc.UserCheck;
import ru.mti.bankclient.shared.Client;
import ru.mti.bankclient.session.ClientFacade;


/**
 * Удаленный сервис проверки пользователя
 * @author Белых Евгений
 */
public class UserCheckImpl extends RemoteServiceServlet implements UserCheck {
    
    @EJB
    private ClientFacade clientFacade;
    private static byte tryCount = 5;
    
    /**
     * Метод получает объект клиента из базы данных по логину и паролю
     * @param login - логин
     * @param pass - пароль
     * @return - имя пользователя или пустую строку
     */
    public User checkUser(String login, String pass) {
        
        Client client;      
        User user = null;
        
        //получаем объект клиента из базы данных
        client = clientFacade.findByLogin(login);
        
        if(client != null) {
            
            user = new User(client.getId(), client.getName(), client.getLogin(), 
                    client.getPassword(), client.getBlocked(), client.getAdmin());
            
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
        
        return user;
    }
}
