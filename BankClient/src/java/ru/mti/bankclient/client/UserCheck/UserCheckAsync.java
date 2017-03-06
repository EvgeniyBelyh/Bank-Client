/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.mti.bankclient.client.UserCheck;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author 1
 */
public interface UserCheckAsync {

    public void checkUser(String login, String pass, AsyncCallback<String> callback);
}
