/*
 * casa is available under the MIT License (2008). See http://opensource.org/licenses/MIT for full text.
 *
 * Copyright (c) 2018, Gluu
 */
package org.gluu.casa.plugins.authnmethod.rs.status.sg;

import org.gluu.casa.core.pojo.SuperGluuDevice;
import org.gluu.casa.misc.Utils;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author jgomer
 */
public enum EnrollmentStatusCode {
    PENDING,
    DUPLICATED,
    FAILED,
    SUCCESS;

    private String getEntity(SuperGluuDevice device) {

        Map<String, Object> map = new LinkedHashMap<>();    //Ensure data can be received in the same order as here
        map.put("code", toString());
        if (device != null) {
            map.put("data", device);
        }
        return Utils.jsonFromObject(map);

    }

    public Response getResponse(SuperGluuDevice device) {

        String json;
        Response.Status httpStatus;

        if (equals(SUCCESS)) {
            httpStatus = Status.CREATED;
            json = Utils.jsonFromObject(device);
        } else {
            httpStatus = equals(PENDING) ? Status.ACCEPTED : Status.INTERNAL_SERVER_ERROR;
            json = Utils.jsonFromObject(Collections.singletonMap("code", toString()));
        }
        return Response.status(httpStatus).entity(json).build();

    }

    public Response getResponse() {
        return getResponse(null);
    }

}