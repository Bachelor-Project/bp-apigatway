/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bp.apigatway;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author Dato
 */
@ApplicationPath("api")
public class AppConfig extends ResourceConfig {
    
    public AppConfig(){
        super();
        
        register(APIService.class);
    }
}