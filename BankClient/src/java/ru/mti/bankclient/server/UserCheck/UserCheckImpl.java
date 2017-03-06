/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.mti.bankclient.server.UserCheck;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import ru.mti.bankclient.client.UserCheck.UserCheck;

/**
 *
 * @author 1
 */
public class UserCheckImpl extends RemoteServiceServlet implements UserCheck {

    public String checkUser(String login, String pass) {
        
        return "Server says: User " + login + " with password '" + pass + "' is now loging in";
    }
}
