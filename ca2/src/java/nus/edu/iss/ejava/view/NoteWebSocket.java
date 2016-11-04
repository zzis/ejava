/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nus.edu.iss.ejava.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import nus.edu.iss.ejava.bean.NoteBean;
import nus.edu.iss.ejava.model.Note;

/**
 *
 * @author zz
 */
@RequestScoped
@ServerEndpoint("/notews/all")
public class NoteWebSocket {
    
    @EJB private NoteBean noteBean;

    @OnOpen
    public void onOpen(Session userSession) {
        UserSessionHandler us = UserSessionHandler.getInstance();
        us.setSession(userSession.getOpenSessions());
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        
    }

    @OnMessage
    public void onMessage(Session s, String message) {
        if(message.equals("getAllNotes")){
            List<Note> listNote = noteBean.getAllNotes();
            JsonArrayBuilder ja = Json.createArrayBuilder();
            for(Note note : listNote){
                JsonObjectBuilder jsonNote = Json.createObjectBuilder()
                    .add("title", note.getTitle())
                    .add("content", note.getContent())
                    .add("create_date", note.getCreateDate().toString())
                    .add("category", note.getCategory()); 
                ja.add(jsonNote);
            }

            try {
                s.getBasicRemote().sendText(ja.build().toString());
            } catch (IOException ex) {
                Logger.getLogger(NoteWebSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
