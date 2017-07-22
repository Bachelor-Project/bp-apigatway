/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bp.apigatway;

import dto.Analyze;
import dto.SignInRequestDTO;
import dto.SignUpRequestDTO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

/**
 *
 * @author Dato
 */
@Path("/")
//@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class APIService {
    
    public APIService(){
        
    }
    
    @GET
    public Response callGetMethod(@QueryParam("url") String serviceURL, @QueryParam("memType") String memType){
        System.out.println("call API GET method ...");
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(serviceURL);
        Response res = webTarget.request(memType).get();
        return res;
    }
    
    @POST
    @Path("signup")
    public Response signUp(@QueryParam("url") String url, SignUpRequestDTO requestObj){
        System.out.println(requestObj);
        System.out.println("რამე რამე");
        
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(url);
        Response res = webTarget.request().post(Entity.entity(requestObj, MediaType.APPLICATION_JSON));
        return res;
    }
    
    @POST
    @Path("signin")
    public Response signIn(@QueryParam("url") String url, SignInRequestDTO requestObj){
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(url);
        Response res = webTarget.request().post(Entity.entity(requestObj, MediaType.APPLICATION_JSON));
        return res;
    }
    
    @POST
    @Path("comment")
    public Response makeComment(Analyze analyze){
        System.out.println(analyze);
        
        String url = "http://localhost:8080/bp_analyzer/api/comment";
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(url);
        return webTarget.request().post(Entity.entity(analyze, MediaType.APPLICATION_JSON));
    }
    
    @GET
    @Path("comments/{task_id}")
    public Response getComments(@PathParam("task_id") int taskId){
        String url = "http://localhost:8080/bp_analyzer/api/comments/" + taskId;
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(url);
        return webTarget.request().get();
    }
    
    @POST
    @Path("/visUpload")
    @Produces(MediaType.TEXT_PLAIN)
    public Response visUpload(){
        System.out.println("call visUpload...");
        return Response.status(200).entity("mushaobs...").build();
    }
    
    @POST
    @Path("/uploadTopic")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response uploadTopic(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @FormDataParam("mainTopic") String mainTopic,
            @FormDataParam("priority") int priority) {

            String params = String.format("%s %d", mainTopic, priority);
            System.out.println(params);
        
            String uploadedFileLocation = "/home/dato/Desktop/" + fileDetail.getFileName();

            // save it
//            writeToFile(uploadedInputStream, uploadedFileLocation);

            String output = "File uploaded to : " + uploadedFileLocation;
            
            
            Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
            WebTarget t = client.target("http://localhost:8080/files_data/api/uploadTopic");
            
            File file = convertStreamToFile(uploadedInputStream);
            System.out.println("file: " + file.getAbsolutePath());
            
            FileDataBodyPart filePart = new FileDataBodyPart("file", file);
            
            MultiPart multipartEntity = new FormDataMultiPart()
                    .field("mainTopic", "1223", MediaType.TEXT_PLAIN_TYPE)
                    .field("priority", "10", MediaType.TEXT_PLAIN_TYPE)
                    .bodyPart(filePart);
            t.request().post(Entity.entity(multipartEntity, multipartEntity.getMediaType()));
            
            return Response.status(200).entity("Upload Success").build();

    }
    
    private File convertStreamToFile(InputStream in){
        File file = null;
        try {
            file = File.createTempFile("stream2file", ".pdf");
            file.deleteOnExit();
            FileOutputStream out = new FileOutputStream(file);
            IOUtils.copy(in, out);
        } catch (IOException ex) {
            Logger.getLogger(APIService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }

    // save uploaded file to new location
    private void writeToFile(InputStream uploadedInputStream,
            String uploadedFileLocation) {

            try {
                    OutputStream out = new FileOutputStream(new File(
                                    uploadedFileLocation));
                    int read = 0;
                    byte[] bytes = new byte[1024];

                    out = new FileOutputStream(new File(uploadedFileLocation));
                    while ((read = uploadedInputStream.read(bytes)) != -1) {
                            out.write(bytes, 0, read);
                    }
                    out.flush();
                    out.close();
            } catch (IOException e) {

                    e.printStackTrace();
            }

    }
    
    @POST
    @Path("/uploadTask")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response uploadTask(){
        return Response.status(200).entity("Upload Success").build();
    } 
}
