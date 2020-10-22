package org.gluu.casa.rest.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.gluu.casa.core.ExtensionsManager;
import org.gluu.casa.timer.SyncSettingsTimer;
import org.gluu.casa.misc.Utils;
import org.pf4j.DefaultPluginDescriptor;
import org.pf4j.PluginDescriptor;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;

import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.ACCEPTED;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;

@ApplicationScoped
@Path(PluginsWS.PLUGINWS_ROOT_URL)
public class PluginsWS extends BaseWS {
    
	public static final String PLUGINWS_ROOT_URL = "/plugins";
	
    @Inject
    private Logger logger;

    @Inject
    private ExtensionsManager extManager;
    
    private ObjectMapper mapper;

    @PostConstruct
    private void init() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@ProtectedApi
    public Response list() {
    	
        Response.Status httpStatus;
        String json = null;
        logger.trace("PluginsWS list operation called");
    	
    	try {				
			List<PluginDescriptor> descriptors = extManager.getPlugins().stream()
				.map(PluginWrapper::getDescriptor).map(this::simplerDescriptor).collect(Collectors.toList());
				
			logger.trace("{} plugins found", descriptors.size());
			json = mapper.writeValueAsString(descriptors);
    		httpStatus = OK;
    		
    	} catch (Exception e) {
    		logger.error(e.getMessage(), e);
        	json = jsonString(e.getMessage());
        	httpStatus = INTERNAL_SERVER_ERROR;
    	}
    	
		return Response.status(httpStatus).entity(json).build();
    	
    }
        
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("authn-method-impl/{acr}")
    //@ProtectedApi
    public Response authnMethodImplementers(@PathParam("acr") String acr) {
    	
        Response.Status httpStatus;
        String json = null;
        logger.trace("PluginsWS authnMethodImplementers operation called");
    	
    	try {
			List<PluginDescriptor> descriptors = extManager.authnMethodPluginImplementers().stream()
					.map(this::simplerDescriptor)
					.filter(desc -> extManager.pluginImplementsAuthnMethod(acr, desc.getPluginId()))
					.collect(Collectors.toList());

			logger.trace("{} plugins found", descriptors.size());
			json = mapper.writeValueAsString(descriptors);
    		httpStatus = OK;
    		
    	} catch (Exception e) {
    		logger.error(e.getMessage(), e);
        	json = jsonString(e.getMessage());
        	httpStatus = INTERNAL_SERVER_ERROR;
    	}
    	
		return Response.status(httpStatus).entity(json).build();
    	
    }
            
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("schedule-removal/{id}")
    //@ProtectedApi
    public Response scheduleRemoval(@PathParam("id") String id) {
    	
        Response.Status httpStatus;
        String json = null;
        logger.trace("PluginsWS scheduleRemoval operation called");
    	
    	try {
    		java.nio.file.Path path = extManager.getPlugins().stream().filter(pw -> pw.getPluginId().equals(id))
    			.findFirst().map(PluginWrapper::getPluginPath).orElse(null);
    		
    		if (path == null) {
    			httpStatus = NOT_FOUND;
    		} else {
    			Files.delete(path);
    			String info = String.format("Removal of plugin '%s' has been scheduled. Operation can take " +
    				"up to %s seconds to take effect. You can issue a query to endpoint %s to determine " +
    				"its availability.", id, PLUGINWS_ROOT_URL, SyncSettingsTimer.SCAN_INTERVAL_SECONDS); 
    			logger.info(info);
    			json = Integer.toString(SyncSettingsTimer.SCAN_INTERVAL_SECONDS);
    			httpStatus = ACCEPTED;
    		}
    		
    	} catch (Exception e) {
    		logger.error(e.getMessage(), e);
        	json = jsonString(e.getMessage());
        	httpStatus = INTERNAL_SERVER_ERROR;
    	}
    	
		return Response.status(httpStatus).entity(json).build();
		
    }
    
    private PluginDescriptor simplerDescriptor(PluginDescriptor desc) {
    	return new DefaultPluginDescriptor(desc.getPluginId(), desc.getPluginDescription(), 
    		desc.getPluginClass(), desc.getVersion(), null, null, null);    	
    }
    
}
