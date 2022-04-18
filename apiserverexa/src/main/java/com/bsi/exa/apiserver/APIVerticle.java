package com.bsi.exa.apiserver;

import com.bsi.exa.apiserver.service.SecurityHandler;
import io.starlight.Logger;
import io.starlight.StarlightVerticle;
import io.starlight.http.EnableWebServer;

/**
 *
 * @author denny
 */
@EnableWebServer(port = "${exa.restapi.port}", preHandler = {SecurityHandler.class})
public class APIVerticle extends StarlightVerticle {

    protected Logger logger = Logger.getLogger(this.getClass());
    
    @Override
    public void start() throws Exception {
        
        logger.debug("REST API Server started");
    }
}
