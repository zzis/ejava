/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nus.edu.iss.ejava.view;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nus.edu.iss.ejava.bean.UserBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import nus.edu.iss.ejava.model.User;

/**
 *
 * @author Snow
 */
@Named
@RequestScoped
public class Register {
	
	@EJB private UserBean userBean;

	private String userId;
	private String password;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void register() throws NoSuchAlgorithmException {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            if (userBean.findUserById(userId) == null) {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(password.getBytes("UTF-8"));
                byte[] digest = md.digest();
                BigInteger bigInt = new BigInteger(1, digest);
                password = bigInt.toString(16);
                
                User user = new User();
                user.setUserId(userId);
                user.setPassword(password);
                userBean.createUser(user);
                ExternalContext ec = fc.getExternalContext();
                ec.redirect(ec.getRequestContextPath() + "/faces/index.xhtml");
            } else {
                fc.addMessage(null, new FacesMessage("User already existed!"));
            }
        }
         catch (IOException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }	
	
	public void logout() {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ((HttpServletRequest) ec.getRequest()).logout();
            ec.invalidateSession();
            ec.redirect(ec.getRequestContextPath() + "/faces/index.xhtml");
        } catch (IOException | ServletException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
}
