package org.gluu.casa.rest.admin;

import org.apache.commons.lang3.StringEscapeUtils;

import org.gluu.casa.conf.MainSettings;
import org.gluu.casa.core.ConfigurationHandler;
import org.gluu.casa.core.PersistenceService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
class BaseWS {
	
    @Inject
    MainSettings mainSettings;
	
    @Inject
    ConfigurationHandler confHandler;
	
    @Inject
    PersistenceService persistenceService;
    
    //This will output the input as valid JSON string, see RFC 7159 section 7 
    String jsonString(String str) {
    	return "\"" + StringEscapeUtils.escapeJson(str) + "\"";
    }
    
}