
package rest;

import business.PeopleBean;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import model.People;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/people")
public class PeopleResource {
    
    @EJB private PeopleBean peopleBean;
    private final ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();
    
    
    

    @POST
    @Consumes(value = MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(value = MediaType.APPLICATION_JSON)
    public void createPeople(@Suspended final AsyncResponse asyncResponse, final MultivaluedMap<String,String> formData) {
        executorService.submit(() -> {
            asyncResponse.resume(doCreatePeople(formData));
        });
    }

    private Response doCreatePeople(MultivaluedMap<String,String> formData) {
        String name= formData.getFirst("name");
        String email= formData.getFirst("email");
        
        People people=new People();
        people.setEmail(email);
        people.setName(name);
        
        peopleBean.add(people);
        
        
        JsonObject json = Json.createObjectBuilder()
                .add("name", people.getEmail())
                .add("email", people.getName())
                .build();

        System.out.println(String.format(">> name: %s, email: %s", name,email));

        return (Response.status(Response.Status.CREATED)
                .entity(json)
                .build());
    }

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public void findPeople(@Suspended final AsyncResponse asyncResponse, @QueryParam(value = "email") final String email) {
        executorService.submit(() -> {
            asyncResponse.resume(doFindPeople(email));
        });
    }

    private Response doFindPeople(@QueryParam("email") String email) {
        Optional<People> optPeople = peopleBean.find(email);

        if (!optPeople.isPresent()) {
            return (Response.status(Response.Status.NOT_FOUND)
                    .build());
        }

        People people = optPeople.get();

        JsonObject returnValue = Json.createObjectBuilder()
                .add("pid", people.getPeopleId())
                .add("name", people.getName())
                .add("email", people.getEmail())
                .build();

        return (Response.ok(returnValue).build());
    }
}
