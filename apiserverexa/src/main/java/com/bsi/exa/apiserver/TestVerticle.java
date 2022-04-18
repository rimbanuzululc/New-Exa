package com.bsi.exa.apiserver;

import com.bsi.exa.apiserver.service.SecurityHandler;
import io.starlight.Logger;
import io.starlight.StarlightVerticle;
import io.starlight.http.EnableWebServer;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;

/**
 *
 * @author denny
 */
@EnableWebServer(port = "${ecommerce.restapi.port}", preHandler = {SecurityHandler.class})
public class TestVerticle extends StarlightVerticle {

    protected Logger logger = Logger.getLogger(this.getClass());
    
    protected Future<String> getA(String data) {
        
        return Future.succeededFuture(data + "A");
    }
    
    protected Future<String> getB(String data) {
        
        return Future.succeededFuture(data + "B");
    }
    
    protected Future<String> getC(String data) {
        
        return Future.succeededFuture(data + "C");
    }
    
    @Override
    public void start() throws Exception {
        
        logger.debug("TEST REST API Server started");
        
        getA("denny").compose(ret -> {
            
            return getB("Kamu " + ret);
        }).compose(ret2 -> {
            
            return getC("Siapa " + ret2);
        }).setHandler(ret3 -> {
           
            System.out.println("==================================> " + ret3.result());
        });
        
        /*WebClient client = WebClient.create(vertx);
        
        JsonObject jo = new JsonObject();
        jo.put("userId", "test");
        jo.put("password", "test");
        
        client
                .post(8090, "localhost", "/user/testy")
                .sendJsonObject(jo, ret -> {
                    
                    if (ret.succeeded()) {
                     
                        JsonObject retObj = ret.result().bodyAsJsonObject();
                        System.out.println(retObj.toString());
                    }
                    else {
                        
                        System.out.println("testy gagal");
                    }
                });
        
        client
                .post(8090, "localhost", "/user/login")
                .sendJsonObject(jo, ret -> {
                    
                    if (ret.succeeded()) {
                     
                        JsonObject retObj = ret.result().bodyAsJsonObject();
                        System.out.println(retObj.toString());
                        
                        System.out.println("=====>" + retObj.getJsonObject("result").getString("accessToken"));
                        client
                            .get(8090, "localhost", "/user/testy")
                            //.putHeader("Authorization", "Bearer " + retObj.getJsonObject("result").getString("accessToken"))
                            .send(hndlr -> {
                               
                                if (hndlr.succeeded()) {
                     
                                    JsonObject retObj2 = hndlr.result().bodyAsJsonObject();
                                    System.out.println(retObj2.toString());
                                }
                                else {

                                    System.out.println("testy gagal");
                                }
                            });
                            
                    }
                    else {
                        
                        System.out.println("gagal");
                    }
                });*/
    }
}
