
package nus.iss.rest;

import java.io.InputStream;
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
import nus.iss.business.DeliveryBean;
import nus.iss.business.PodBean;
import nus.iss.model.Delivery;
import nus.iss.model.Pod;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;


@RequestScoped
@Path("/items")
@Named
public class ItemResource {
    
    @EJB private DeliveryBean deliveryBean;
    @EJB private PodBean podBean;
    private final ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();
    
    private List<Pod> podList;
    
    private String name;
    private String address;
    private String phone;
    
    
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public void getItems(
            @Suspended final AsyncResponse asyncResponse){
        
        executorService.submit(() -> {
            asyncResponse.resume(doGetItems());
        });
    }

    private Response doGetItems() {
        List<Pod> deliveries=podBean.getAllPods();
        JsonArrayBuilder appJsonArrayBuilder=Json.createArrayBuilder();
        deliveries.stream().forEach((p) -> {
            appJsonArrayBuilder.add(Json.createObjectBuilder()
                    .add("teamId","04802a45")
                    .add("podId", p.getPodId())
                    .add("address", p.getPkgId().getAddress())
                    .add("name", p.getPkgId().getName())
                    .add("phone", p.getPkgId().getPhone())
                    .build()
            );
          });
        return (Response.status(Response.Status.CREATED).entity(appJsonArrayBuilder.build()).build());
    }
    
    public List<JsonObject> getPodList(){
        List<Pod> list = podBean.getAllPods();
//        List<Note> list = noteBean.getAllNotesByUserId("123");
        List<JsonObject> podList = new ArrayList<JsonObject>();
        for (Pod pod: list) {
            podList.add(pod.toJSON());
        }
        return podList;
    }
    
    public List<JsonObject> getDeliveryList(){
        List<Delivery> list = deliveryBean.getAllDeliverys();
//        List<Note> list = noteBean.getAllNotesByUserId("123");
        List<JsonObject> deliveryList = new ArrayList<JsonObject>();
        for (Delivery delivery: list) {
            deliveryList.add(delivery.toJSON());
        }
        return deliveryList;
    }
    
    public void createDelivery(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Delivery delivery = new Delivery();
        delivery.setAddress(address);
        delivery.setName(name);
        delivery.setPhone(phone);
        delivery.setCreateDate(new Date());
        
        Pod pod = new Pod();
        pod.setPkgId(delivery);
        pod.setDeliveryDate(new Date());
        pod.setPodId(0);
        pod.setNote("");
        
        this.deliveryBean.add(delivery);
        this.podBean.add(pod);
        fc.addMessage(null, new FacesMessage("created!"));
    }
    
    
    
//    @POST
//    @Path("/upload")
//    @Consumes(value = MediaType.MULTIPART_FORM_DATA)
//    @Produces(value = MediaType.APPLICATION_JSON)
//    public void upload(@Suspended final AsyncResponse asyncResponse,
//            @FormDataParam("image") InputStream uploadedInputStream) {
//        executorService.submit(() -> {
//            asyncResponse.resume(doUpload(uploadedInputStream));
//        });
//    }
//
//    private Response doUpload(InputStream uploadedInputStream) {
//        Map<String, String> formData = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        
//        String email = formData.get("podId");
//        String timestamp = formData.get("note");
       // byte[] image = (byte[])formData.getFirst("image");
//
//        Calendar apptDate = Calendar.getInstance();
//        apptDate.setTimeInMillis(Long.valueOf(timestamp));
//        
//        Optional<People> people = podBean.find(email);
//        if (people.isPresent()) {
//            Appointment appointment = new Appointment();
//            appointment.setPeople(people.get());
//            appointment.setDescription(description);
//            appointment.setApptDate(apptDate.getTime());
//            
//            try{
//                deliveryBean.add(appointment);
//            }catch(Exception e){
//                System.out.println(e.getMessage());
//            }
//            JsonObject json = Json.createObjectBuilder()
//                    .add("email", email)
//                    .add("appt_date", timestamp)
//                    .add("description", description)
//                    .build();
//
//            return (Response.status(Response.Status.CREATED)
//                    .entity(json.toString())
//                    .build());
//        }
//        return (Response.status(Response.Status.NOT_FOUND)
//                .build());
//    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
