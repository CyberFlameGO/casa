package org.gluu.casa.plugins.consentmanagementportal.client.impl;



import org.gluu.casa.plugins.consentmanagementportal.client.BaseExternalApiClient;
import org.gluu.casa.plugins.consentmanagementportal.client.ConsentClient;
import org.gluu.casa.plugins.consentmanagementportal.model.Consent;
import org.gluu.casa.plugins.consentmanagementportal.model.ConsentRequest;
import org.gluu.casa.plugins.consentmanagementportal.model.ConsentResponse;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * An app. scoped bean that encapsulates interactions with the consent-server.
 * @author jgomer
 */
@Named
@ApplicationScoped
public class ConsentClientImpl extends BaseExternalApiClient implements ConsentClient {


    @Inject
    private Logger logger;

    public List<Consent> getAllConsents(String url, String path){
        return  (List<Consent>) doGet(Consent.class,url, path);
    }
    public Consent getConsentById(String id, String url, String path){
        return (Consent) doGet(Consent.class, url, path);
    }
    public ConsentResponse createConsent(ConsentRequest consentRequest, String url, String path){
        return (ConsentResponse) doPost(consentRequest,ConsentResponse.class, url, path);
    }


}
