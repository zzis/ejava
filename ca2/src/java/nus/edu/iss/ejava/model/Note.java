/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nus.edu.iss.ejava.model;

import java.io.Serializable;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Snow
 */
@Entity
@Table (name = "notes")
@NamedQueries({
    @NamedQuery(name = "Note.getAllNotesByUserId", query = "SELECT n FROM Note n WHERE n.user.userId = :userId ORDER BY n.createDate DESC"),
    @NamedQuery(name = "Note.getAllNotes", query = "SELECT n FROM Note n ORDER BY n.createDate DESC")
})
public class Note implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "notesid")
	private String noteId;
	@Column(name = "title")
	private String title;
	@Column (name = "posteddate")
	@Temporal(javax.persistence.TemporalType.DATE)
   	private Date createDate;
	@Column (name = "category")
	private String category;
	@Column (name = "content")
	private String content;
	@ManyToOne 
	@JoinColumn(name = "userid",referencedColumnName = "userid" )	
	private User user;

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
        
        public JsonObject toJSON() {
            return (Json.createObjectBuilder()
                    .add("id", noteId)
                    .add("title", title)
                    .add("content", content)
                    .add("create_date", createDate.toString())
                    .add("category", category)
                    .add("user_name", user.getUserId())
                    .build());
    }
	
}
