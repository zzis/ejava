/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nus.iss.business;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nus.iss.model.Delivery;

/**
 *
 * @author zz
 */
@Stateless
public class DeliveryBean {

    /**
     * Creates a new instance of DeliveryBean
     */
    public DeliveryBean() {
    }
    
    @PersistenceContext
    private EntityManager em;
	
    public void add(Delivery delivery){
        em.persist(delivery);
    }
        
    public List<Delivery> getAllDeliverys() {
        TypedQuery<Delivery> query = em.createNamedQuery("Delivery.findAll", Delivery.class);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return query.getResultList();
    }
    
//    public List<Delivery> getAllNotesByUserId(String userId) {
//        TypedQuery<Delivery> query = em.createNamedQuery("Note.getAllNotesByUserId", Delivery.class);
//        query.setParameter("userId", userId);
//        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
//	return query.getResultList();
//    }
//    
}
