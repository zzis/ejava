
package business;

import model.People;
import java.util.*;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class PeopleBean {

    @PersistenceContext
    private EntityManager em;

    public Optional<People> find(final String email) {
        TypedQuery<People> query = em.createNamedQuery(
                "People.findByEmail", People.class);

        query.setParameter("email", email);
        return (Optional.ofNullable(query.getSingleResult()));

    }

    public void add(People people) {
        em.persist(people);
    }

}
