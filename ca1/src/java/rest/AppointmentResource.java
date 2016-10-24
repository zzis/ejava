
package rest;

import business.AppointmentBean;
import business.PeopleBean;
import java.util.Calendar;
import model.Appointment;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import model.People;

@RequestScoped
@Path("/appointment")
public class AppointmentResource {
    
    @EJB private AppointmentBean appointmentBean;
    @EJB private PeopleBean peopleBean;
    private final ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();
    
    @GET
    @Path("/{email}")
    public void getAppointment(
            @Suspended final AsyncResponse asyncResponse, 
            @PathParam(value = "email") final String email){
        
        executorService.submit(() -> {
            asyncResponse.resume(doGetAppointment(email));
        });
    }

    private Response doGetAppointment(@PathParam("email") String email) {
        List<Appointment> appointments=appointmentBean.findAllAppointment(email);
        JsonArrayBuilder appJsonArrayBuilder=Json.createArrayBuilder();
        appointments.stream().forEach((app) -> {
            appJsonArrayBuilder.add(Json.createObjectBuilder()
                    .add("appointmentId",app.getAppointmentId())
                    .add("dateTime", app.getApptDate().getTime())
                    .add("description", app.getDescription())
                    .add("personId", app.getPeople().getPeopleId())
                    .build()
            );
          });
        return (Response.status(Response.Status.CREATED).entity(appJsonArrayBuilder.build()).build());
    }
    
    
    
    @POST
    @Consumes(value = MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(value = MediaType.APPLICATION_JSON)
    public void createAppointment(@Suspended final AsyncResponse asyncResponse, final MultivaluedMap<String, String> formData) {
        executorService.submit(() -> {
            asyncResponse.resume(doCreateAppointment(formData));
        });
    }

    private Response doCreateAppointment(MultivaluedMap<String, String> formData) {
        String email = formData.getFirst("email");
        String timestamp = formData.getFirst("date");
        String description = formData.getFirst("description");

        Calendar apptDate = Calendar.getInstance();
        apptDate.setTimeInMillis(Long.valueOf(timestamp));
        
        Optional<People> people = peopleBean.find(email);
        if (people.isPresent()) {
            Appointment appointment = new Appointment();
            appointment.setPeople(people.get());
            appointment.setDescription(description);
            appointment.setApptDate(apptDate.getTime());
            
            try{
                appointmentBean.add(appointment);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            JsonObject json = Json.createObjectBuilder()
                    .add("email", email)
                    .add("appt_date", timestamp)
                    .add("description", description)
                    .build();

            return (Response.status(Response.Status.CREATED)
                    .entity(json.toString())
                    .build());
        }
        return (Response.status(Response.Status.NOT_FOUND)
                .build());
    }
}
