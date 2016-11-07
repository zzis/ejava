/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nus.iss.rest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javafx.concurrent.Task;
import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import nus.iss.business.PodBean;
import nus.iss.model.Pod;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import sun.net.www.http.HttpClient;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

/**
 *
 * @author zz
 */
@WebServlet("/upload")
@MultipartConfig 
public class Upload extends HttpServlet {
    
    @EJB private PodBean podBean;
    
    
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        Part part = req.getPart("image");
        String note = req.getParameter("note");
        String podId = req.getParameter("podId");
        String time = req.getParameter("time");
        Pod pod = podBean.getPodById(Integer.parseInt(podId));
        byte[] b = new byte[(int)part.getSize()];
        part.getInputStream().read(b);
        Date date = new Date();
        date.setTime(Long.parseLong(time));
        pod.setDeliveryDate(date);
        pod.setNote(note);
        pod.setImage(b);
        podBean.save(pod);
        
        writeArray(podId, b);
        callHQ(podId, note);
//        if(lock.writeLock().tryLock()){
//            list.add(pod);
//        }
//        if(!isRunning){
//            loader(podBean);
//            isRunning = true;
//        }
        
    }
    
    
    public static void callHQ(String podId, String note){
        Client client = ClientBuilder.newBuilder()
				.register(MultiPartFeature.class)
				.build();

        MultiPart part = new MultiPart();

        FileDataBodyPart imgPart = new FileDataBodyPart("image", 
                        new File(podId),
                        MediaType.APPLICATION_OCTET_STREAM_TYPE);
        imgPart.setContentDisposition(
                        FormDataContentDisposition.name("image")
                        .fileName(podId).build());

        MultiPart formData = new FormDataMultiPart()
                        .field("callback", "http://10.10.25.9:8080/week05_ca3/api/callback", MediaType.TEXT_PLAIN_TYPE)
                        .field("teamId", "04802a45", MediaType.TEXT_PLAIN_TYPE)
                        .field("note", note, MediaType.TEXT_PLAIN_TYPE)
                        .field("podId",podId, MediaType.TEXT_PLAIN_TYPE)
                        .bodyPart(imgPart);
        formData.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);

        WebTarget target = client.target("http://10.10.0.50:8080/epod/upload");
        Invocation.Builder inv = target.request();

        System.out.println(">>> part: " + formData);

        Response callResp = inv.post(Entity.entity(formData, formData.getMediaType()));

        System.out.println(">> call resp:" + callResp.getStatus());
    }
    
    
    public static void writeArray (String filename, byte[] x) throws IOException{
        try(FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(x);
        }
    }
    
   private static final ExecutorService executor = Executors.newSingleThreadExecutor(); // An application wide thread pool executor is better.
   private static final List<Pod> list = new ArrayList<>();
   private static final ReadWriteLock lock = new ReentrantReadWriteLock();
   private static  boolean isRunning = false;
   
   public static void loader(PodBean podBean){
        executor.submit(new Task() {
            @Override
            protected Object call() throws Exception {
                while(true){
                    int size = 0;
                    if(lock.readLock().tryLock()){
                       size = list.size();
                    }
                    for(int i=size-1; i>0;i--){
                        Pod p = list.get(i);
                        podBean.refresh(p);
                        if(lock.readLock().tryLock()){
                            if(p.getAckId() == null){
                                callHQ(p.getPodId().toString(), p.getNote());
                            }else{
                                list.remove(i);
                            }

                        }
                    }
                    this.wait(1000);
                }
            }
        });
   }
    
}
