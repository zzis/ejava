
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;

@NamedQuery(name="Appointment.findAllAppointments",
       query="Select a from Appointment a join a.people as p where p.email=:email")
@Entity
@Table(name="appointment")
public class Appointment implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "appt_id")
    private Integer appointmentId;
    @Column(name = "description")
    private String description;
    @Column(name = "appt_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date apptDate;
    
    @ManyToOne
    @JoinColumn( name = "pid", referencedColumnName = "pid")
    private People people;

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public Date getApptDate() {
        return apptDate;
    }

    public void setApptDate(Date apptDate) {
        this.apptDate = apptDate;
    }


    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }
    
    
}
