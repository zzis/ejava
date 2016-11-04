/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nus.edu.iss.ejava.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.websocket.Session;

/**
 *
 * @author zz
 */
public class UserSessionHandler {
    
    private Set<Session> userSessions;
    
    private static UserSessionHandler instance;
    
    private UserSessionHandler(){
        this.userSessions = new HashSet<Session>();
    }
    
    public static UserSessionHandler getInstance(){
        if(instance == null){
            instance = new UserSessionHandler();
        }
        return instance;
    }
    
    public void addSession(Session s){
        this.userSessions.add(s);
    }
    
    public void setSession(Set<Session> s){
        this.userSessions = s;
    }
    
    public Set<Session> getSessions(){
        return this.userSessions;
    }
}
