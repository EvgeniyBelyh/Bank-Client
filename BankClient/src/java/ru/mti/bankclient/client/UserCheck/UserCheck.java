/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.mti.bankclient.client.UserCheck;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 *
 * @author 1
 */
@RemoteServiceRelativePath("usercheck/usercheck")
public interface UserCheck extends RemoteService {

    public String checkUser(String login, String pass);
}
