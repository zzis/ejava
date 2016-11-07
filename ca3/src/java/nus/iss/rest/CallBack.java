/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nus.iss.rest;

import java.util.List;
import java.util.concurrent.ExecutorService;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nus.iss.business.DeliveryBean;
import nus.iss.business.PodBean;
import nus.iss.model.Pod;

/**
 *
 * @author zz
 */

@RequestScoped
@Path("/callback")
public class CallBack {
    
     
    @EJB private DeliveryBean deliveryBean;
    @EJB private PodBean podBean;
    private final ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();
    
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public void callback(
        @Suspended final AsyncResponse asyncResponse,
            @QueryParam("podId") String podId,
            @QueryParam("ackId") String ackId){
        
        executorService.submit(() -> {
            asyncResponse.resume(doCallcack(podId, ackId));
        });
    }

    private Response doCallcack(String podId, String ackId) {
        Pod pod = podBean.getPodById(Integer.parseInt(podId));
        pod.setAckId(ackId);
        podBean.save(pod);
        System.out.print("Ack: " + ackId);
        return (Response.status(Response.Status.OK).build());
    }
}
