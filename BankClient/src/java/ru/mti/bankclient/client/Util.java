
package ru.mti.bankclient.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import ru.mti.bankclient.client.rpc.LoginService;
import ru.mti.bankclient.shared.ClientDTO;

/**
 * Утилитарный класс
 * @author Евгений Белых
 */
public class Util {
    
    private static ClientDTO user = null;
    
    /**
     * Возвращает объект клиента из сессии
     * @return объект DTO клиента
     */
    public static ClientDTO getClientDTO() {
               
        LoginService.Util.getInstance().loginFromSessionServer(new AsyncCallback<ClientDTO>() {
            @Override
            public void onSuccess(ClientDTO result) {
                user = result;
            }

            // в случае возникновения ошибки
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Ошибка связи с сервером. Повторите попытку позднее");
            }
        });
        
        return user;
    }
}
