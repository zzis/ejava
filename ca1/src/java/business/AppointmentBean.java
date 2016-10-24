
package business;

import model.Appointment;
import java.util.*;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class AppointmentBean {

    @PersistenceContext
    private EntityManager em;

    public void add(Appointment appointment) {
        em.persist(appointment);
    }

    public List<Appointment> findAllAppointment(String email) {
        TypedQuery<Appointment> query = em.createNamedQuery(
                "Appointment.findAllAppointments", Appointment.class);
        query.setParameter("email", email);
        return (query.getResultList());
    }

}
