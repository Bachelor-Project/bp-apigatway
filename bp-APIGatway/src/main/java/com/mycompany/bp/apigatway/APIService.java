/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bp.apigatway;

import dto.Analyze;
import dto.SignInRequestDTO;
import dto.SignUpRequestDTO;
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

/**
 *
 * @author Dato
 */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class APIService {
    
    public APIService(){
        
    }
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response callGetMethod(@QueryParam("url") String serviceURL, @QueryParam("memType") String memType){
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
    
}
