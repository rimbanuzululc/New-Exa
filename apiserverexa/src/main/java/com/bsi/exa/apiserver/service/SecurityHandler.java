package com.bsi.exa.apiserver.service;

import com.bsi.exa.apiserver.dto.APIResult;
import com.bsi.exa.foundation.Errors;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.starlight.AutoWired;
import io.starlight.Config;
import io.starlight.Logger;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.RoutingContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author denny
 */
public class SecurityHandler  implements Handler<RoutingContext> {

    protected Logger logger = Logger.getLogger(this.getClass());
    
    @Config("exa.restapi.allowedURL.ALL")
    protected JsonArray allowedALLList;
    
    @Config("exa.restapi.allowedURL.GET")
    protected JsonArray allowedGETList;
    
    @Config("exa.restapi.allowedURL.POST")
    protected JsonArray allowedPOSTList;
    
    @Config("exa.restapi.allowedURL.PUT")
    protected JsonArray allowedPUTList;
    
    @Config("exa.restapi.allowedURL.DELETE")
    protected JsonArray allowedDELETEList;
    
    @AutoWired
    protected TokenService tokenService;
    
    @Override
    public void handle(RoutingContext e) {

        boolean tokenValid = false;
        String tokenError = null;
        boolean cont = false;
        
        APIResult apiResult = new APIResult();
        
        String token = e.request().headers().get("Authorization");
            
        if ((token != null) && token.startsWith("Bearer")) {

            token = token.substring(7);

            try {

                Jws<Claims> claim = tokenService.validate(token);

                e.put("userId", claim.getBody().getSubject());
                e.put("sessionId", claim.getBody().getId());

                tokenValid = true;
            }
            catch (Exception evalidate) {

                tokenError = evalidate.getMessage();
            }
        }
        else {
            
            tokenError = "Invalid authorization header";
        }

        if (!tokenValid) {
        
            if (allowedALLList != null) {
        
                for (int i = 0; i < allowedALLList.size(); i++) {

                    String patt = allowedALLList.getString(i);

                    Pattern p = Pattern.compile(patt);
                    Matcher m = p.matcher(e.request().path());

                    if (m.find()) {    
                        cont = true;
                        break;
                    }
                }            
            }

            if (!cont) {

                JsonArray list = null;

                switch (e.request().method()) {

                    case GET : list = allowedGETList; break;
                    case POST : list = allowedPOSTList; break;
                    case PUT : list = allowedPUTList; break;
                    case DELETE : list = allowedDELETEList; break;
                }

                if (list != null) {

                    for (int i = 0; i < list.size(); i++) {

                        String patt = list.getString(i);

                        Pattern p = Pattern.compile(patt);
                        Matcher m = p.matcher(e.request().path());

                        if (m.find()) {    
                            cont = true;
                            break;
                        }
                    }
                }
            }
        }
        else
            cont = true;
                
        if (cont) 
            e.next();
        else {
         
            apiResult.error(Errors.INVALID_TOKEN, tokenError);
            
            e.response().headers().set("Content-type", "application/json");
            e.response()
                .setStatusCode(403)
                .end(Json.encode(apiResult));
        }
    }
}
