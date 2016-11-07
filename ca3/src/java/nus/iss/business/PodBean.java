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
import nus.iss.model.Pod;

/**
 *
 * @author zz
 */
@Stateless
public class PodBean {

    /**
     * Creates a new instance of PodBean
     */
    public PodBean() {
    }
       @PersistenceContext
    private EntityManager em;
	
    public void add(Pod pod){
        em.persist(pod);
    }
    
     public void save(Pod pod){
        em.merge(pod);
    }
    
    public List<Pod> getAllPods() {
        TypedQuery<Pod> query = em.createNamedQuery("Pod.findAll", Pod.class);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return query.getResultList();
    }
    
    public Pod getPodById(int podId) {
        TypedQuery<Pod> query = em.createNamedQuery("Pod.findByPodId", Pod.class);
        query.setParameter("podId", podId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
	return query.getSingleResult();
    }

    public void refresh(Pod p) {
        em.refresh(p);
    }
    
}
