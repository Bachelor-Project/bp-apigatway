/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bp.apigatway;

import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author Dato
 */
@ApplicationPath("/api")
public class AppConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(MultiPartFeature.class);
        addRestResourceClasses(resources);
        return resources;
    }
    
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.mycompany.bp.apigatway.APIService.class);
        resources.add(org.glassfish.jersey.server.wadl.internal.WadlResource.class);
    }
    
//    public AppConfig(){
//        super();
//        
//        register(APIService.class);
//    }
}





//public class AppConfig extends ResourceConfig {
//    
//    public AppConfig(){
//        super();
//        
//        register(APIService.class);
//    }
//}
