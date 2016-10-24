
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;


@Entity
@NamedQueries({
    @NamedQuery(name = "People.findByEmail", query = "SELECT p FROM People p WHERE p.email = :email")})
@Table(name = "people")
public class People implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "pid")
    private String peopleId;
    @Column(name = "name")
    @Basic(optional = false)
    private String name;
    @Column(name = "email")
    @Basic(optional = false)
    private String email;
    @OneToMany (mappedBy = "people")
    private ArrayList<Appointment> appointmentlist;

    public String getPeopleId() {
        return peopleId;
    }


    public void setPeopleId(String peopleId) {
        this.peopleId = peopleId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Appointment> getAppointmentlist() {
        return appointmentlist;
    }

    public void setAppointmentlist(ArrayList<Appointment> appointmentlist) {
        this.appointmentlist = appointmentlist;
    }

    @PrePersist
    public void onPrePersist(){
        
        String uuid=UUID.randomUUID().toString().substring(0,8);
        this.peopleId=uuid;
    }
}
