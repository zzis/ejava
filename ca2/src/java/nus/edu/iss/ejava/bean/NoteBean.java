/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nus.edu.iss.ejava.bean;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nus.edu.iss.ejava.model.Note;

/**
 *
 * @author Snow
 */
@Stateless
public class NoteBean {
@PersistenceContext
    private EntityManager em;
	
    private void add(Note note){
            em.persist(note);
    }
        
    public void createNote(Note note){
        this.add(note);
    }
        
    public List<Note> getAllNotes() {
        TypedQuery<Note> query = em.createNamedQuery("Note.getAllNotes", Note.class);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return query.getResultList();
    }
    
    public List<Note> getAllNotesByUserId(String userId) {
        TypedQuery<Note> query = em.createNamedQuery("Note.getAllNotesByUserId", Note.class);
        query.setParameter("userId", userId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
	return query.getResultList();
    }
    
}
