package com.bsi.exa.apiserver.controller;

import com.bsi.exa.apiserver.dto.APIResult;
import com.bsi.exa.foundation.Errors;
import com.bsi.exa.foundation.dto.Menu;
import com.bsi.exa.foundation.service.MenuService;
import io.starlight.AutoWired;
import io.starlight.http.PathParam;
import io.starlight.http.QueryParam;
import io.starlight.http.RequestBody;
import io.starlight.http.RequestMapping;
import io.starlight.http.RestController;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;

/**
 *
 * @author anisa.pebriani
 */
@RestController("/menu")
public class MenuController {
    @AutoWired
    protected MenuService service;
    
    @RequestMapping(value = "", method = HttpMethod.PUT)
    public Future<APIResult> add(@RequestBody Menu menu) {
        
        Future<APIResult> result = Future.future();
        
        service.add(menu)
                .setHandler(ret -> {
                   
                    APIResult apiResult = new APIResult();
                    
                    if (ret.succeeded())
                        apiResult.setResult(ret.result());
                    else
                        apiResult.error(Errors.COMMON, "" + ret.cause());
                    
                    result.complete(apiResult);
                });
        
        return result;
    }
    
    @RequestMapping(value = "", method = HttpMethod.POST)
    public Future<APIResult> update(@RequestBody Menu menu) {
        
        Future<APIResult> result = Future.future();
        
        service.update(menu)
            .setHandler(ret -> {

                APIResult apiResult = new APIResult();

                if (ret.succeeded())
                    apiResult.setResult(ret.result());
                else
                    apiResult.error(Errors.COMMON, "" + ret.cause());

                result.complete(apiResult);
            });
        
        return result;
    }
    
    @RequestMapping(value = "/byid/:id", method = HttpMethod.DELETE)
    public Future<APIResult> delete(@PathParam("id") int id) {
        
        Future<APIResult> result = Future.future();
        
        service.delete(id)
                .setHandler(ret -> {
                    
                    APIResult apiResult = new APIResult();
                    
                    if (ret.succeeded())
                        apiResult.setResult(ret.result());
                    else
                        apiResult.error(Errors.COMMON, "" + ret.cause());
                    
                    result.complete(apiResult);
                });
        
        return result;
    }
    
    @RequestMapping("/search")
    public Future<APIResult> search(@QueryParam("filter") String filter, @QueryParam("parentId") int parentId, @QueryParam("page") int page) {
        
        Future<APIResult> result = Future.future();
        
        service.search(filter, parentId, page)
            .setHandler(ret -> {

                APIResult apiResult = new APIResult();

                if (ret.succeeded())
                    apiResult.setResult(ret.result());
                else
                    apiResult.error(Errors.COMMON, "" + ret.cause());

                result.complete(apiResult);
            });
        
        return result;
    }
    
    @RequestMapping("/all")
    public Future<APIResult> list() {
        
        Future<APIResult> result = Future.future();
        
        service.listAll()
            .setHandler(ret -> {

                APIResult apiResult = new APIResult();

                if (ret.succeeded())
                    apiResult.setResult(ret.result());
                else
                    apiResult.error(Errors.COMMON, "" + ret.cause());

                result.complete(apiResult);
            });
        
        return result;
    }
    
    @RequestMapping("/byid/:id")
    public Future<APIResult> getById (@PathParam ("id") int menuId) {
        
        Future<APIResult> result = Future.future();
        
        service.getId(menuId)
            .setHandler(ret -> {

                APIResult apiResult = new APIResult();

                if (ret.succeeded())
                    apiResult.setResult(ret.result());
                else
                    apiResult.error(Errors.COMMON, "" + ret.cause());

                result.complete(apiResult);
            });
        
        return result;
    }
  
}
