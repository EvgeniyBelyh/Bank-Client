
package ru.mti.bankclient.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс-обертка на стороне клиента для сущности Client
 * @author Белых Евгений
 */
public class ClientDTO implements Serializable {
    
    private Integer id;
    private String name;
    private String login;
    private String password;
    private boolean blocked;
    private boolean admin;
    private List<AccountDTO> accountList = new ArrayList();

    
    public ClientDTO() {
        
    }

    public ClientDTO(Integer id, String name, String login, String password, boolean blocked, boolean admin, List<AccountDTO> accountList) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.blocked = blocked;
        this.admin = admin;
        this.accountList = accountList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    
    public List<AccountDTO> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<AccountDTO> accountList) {
        this.accountList = accountList;
    }
}
