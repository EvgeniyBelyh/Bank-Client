
package ru.mti.bankclient.client;

import java.io.Serializable;

/**
 *
 * @author Белых Евгений
 */
public class User implements Serializable {
    
    private Integer id;
    private String name;
    private String login;
    private String password;
    private boolean blocked;
    private boolean admin;
    
    public User() {
        
    }

    public User(Integer id, String name, String login, String password, boolean blocked, boolean admin) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.blocked = blocked;
        this.admin = admin;
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
    
    
}
