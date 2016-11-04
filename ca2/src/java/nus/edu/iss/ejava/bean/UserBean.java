/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nus.edu.iss.ejava.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nus.edu.iss.ejava.model.Group;
import nus.edu.iss.ejava.model.User;

/**
 *
 * @author Snow
 */
@Stateless
public class UserBean {
	@PersistenceContext
	private EntityManager em;

	public void createUser(final User user) {
        em.persist(user);
        Group group = new Group();
        group.setGroupId("manage");
        group.setUserId(user.getUserId());
        em.persist(group);
    }
    
    public User findUserById(String id) {
        return em.find(User.class, id);
    }
}

