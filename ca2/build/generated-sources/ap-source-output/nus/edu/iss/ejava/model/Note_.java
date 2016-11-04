package nus.edu.iss.ejava.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nus.edu.iss.ejava.model.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-04T13:26:28")
@StaticMetamodel(Note.class)
public class Note_ { 

    public static volatile SingularAttribute<Note, String> noteId;
    public static volatile SingularAttribute<Note, String> title;
    public static volatile SingularAttribute<Note, String> category;
    public static volatile SingularAttribute<Note, User> user;
    public static volatile SingularAttribute<Note, String> content;
    public static volatile SingularAttribute<Note, Date> createDate;

}