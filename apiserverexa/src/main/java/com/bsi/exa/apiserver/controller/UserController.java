package com.bsi.exa.apiserver.controller;

import com.bsi.exa.apiserver.dto.APIResult;
import com.bsi.exa.apiserver.dto.LoginRequest;
import com.bsi.exa.apiserver.dto.LoginResult;
import com.bsi.exa.apiserver.service.TokenService;
import com.bsi.exa.foundation.CodedException;
import com.bsi.exa.foundation.Errors;
import com.bsi.exa.foundation.dto.HakAksesDTO;
import com.bsi.exa.foundation.dto.Menu;
import com.bsi.exa.foundation.dto.SubMenu;
import com.bsi.exa.foundation.dto.User;
import com.bsi.exa.foundation.service.UserService;
import io.starlight.AutoWired;
import io.starlight.http.PathParam;
import io.starlight.http.QueryParam;
import io.starlight.http.RequestBody;
import io.starlight.http.RequestMapping;
import io.starlight.http.RestController;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import java.util.ArrayList;
import java.util.List;


@RestController("/user")
public class UserController {
    
    @AutoWired
    protected TokenService tokenService;
    
    @AutoWired
    protected UserService userService;
    
    @RequestMapping(value = "/login", method = HttpMethod.POST)
    public Future<APIResult> login(@RequestBody LoginRequest request) {
        
        Future<APIResult> future = Future.future();
        APIResult result = new APIResult();
        
        userService.login(request.getUserId(), request.getPassword())
            .setHandler(ret -> {
                    
                if (ret.succeeded()) {
                    
                    LoginResult loginResult = new LoginResult();
                    loginResult.setAccessToken(tokenService.generateToken(request.getUserId()));
                    loginResult.setUser(ret.result());
                    result.setResult(loginResult);
                    future.complete(result);
                }
                else {
                    result.error(ret.cause());
                    future.complete(result);
                }
                     
                    
            });
        
        return future;
    }
    
    @RequestMapping("/byuserid/:userId")
    public Future<APIResult> get(@PathParam("userId") String userId) {
        
        Future<APIResult> result = Future.future();
        
        userService.get(userId)
                    .setHandler(ret -> {
                        APIResult apiResult = new APIResult();
                       
                        if (ret.succeeded()) {
                            
                            apiResult.setResult(ret.result());
                            result.complete(apiResult);
                        }
                        else
                            result.fail(ret.cause());
                    });
        
        return result;
    }
    
    @RequestMapping(value = "", method = HttpMethod.POST)
    public Future<APIResult> add(@RequestBody User user) {
        
        Future<APIResult> result = Future.future();
        
        userService.add(user)
                .setHandler(ret -> {
                   
                    APIResult apiResult = new APIResult();
                    
                    if (ret.succeeded())
                        apiResult.setResult(ret.result());
                    else
                        apiResult.error(ret.cause());
                    
                    result.complete(apiResult);
                });
        
        return result;
    }
    
    @RequestMapping(value = "", method = HttpMethod.PUT)
    public Future<APIResult> update(@RequestBody User user) {
        
        Future<APIResult> result = Future.future();
        
        userService.update(user)
                .setHandler(ret -> {
                   
                    APIResult apiResult = new APIResult();
                    
                    if (ret.succeeded())
                        apiResult.setResult(ret.result());
                    else
                        apiResult.error(ret.cause());
                    
                    result.complete(apiResult);
                });
        
        return result;
    }
    
    @RequestMapping("/search")
    public Future<APIResult> search(@QueryParam("filter") String filter, @QueryParam("page") int page) {
        
        Future<APIResult> result = Future.future();
        
        userService.search(filter, page)
                .setHandler(ret -> {
                   
                    APIResult apiResult = new APIResult();
                    
                    if (ret.succeeded())
                        apiResult.setResult(ret.result());
                    else
                        apiResult.error(ret.cause());
                    
                    result.complete(apiResult);
                });
        
        return result;
    }
    
    @RequestMapping(value = "/byid/:id", method = HttpMethod.DELETE)
    public Future<APIResult> delete(@PathParam("id") String id) {
        
        Future<APIResult> result = Future.future();
        
        userService.delete(id)
                .setHandler(ret -> {
                    
                    APIResult apiResult = new APIResult();
                    
                    if (ret.succeeded())
                        apiResult.setResult(ret.result());
                    else
                        apiResult.error(ret.cause());
                    
                    result.complete(apiResult);
                });
        
        return result;
    }
    
    @RequestMapping(value="/hakakses/:id", method = HttpMethod.GET)
    public Future<APIResult> hakAkses(@PathParam("id") String userId) {
        
        Future<APIResult> result = Future.future();
        System.out.println("------------"+userId);
        userService.hakAkses(userId)
                .setHandler(ret -> {
                   
                    APIResult apiResult = new APIResult();
                    
                    if (ret.succeeded()){
                        apiResult.setResult(ret.result());
                    }else{
                        apiResult.error(ret.cause());
                    }
                    result.complete(apiResult);
                });
        
        return result;
    }
    
    @RequestMapping(value = "/update/password", method = HttpMethod.PUT)
    public Future<APIResult> updatePass (@QueryParam("username") String userId, @QueryParam("password") String password) {
        
        Future<APIResult> result = Future.future();
        
        userService.updatePass(userId, password)
                .setHandler(ret -> {
                    
                    APIResult apiResult = new APIResult();
                    
                    if (ret.succeeded()) {
                        apiResult.setResult(ret.result());
                    } else {
                        apiResult.error(ret.cause());
                        result.complete(apiResult);
                    }
                    
                    result.complete(apiResult);
                });
        return result;
    }
    
    @RequestMapping("/list")
    public Future<APIResult> listAll() {
        
        Future<APIResult> result = Future.future();
        
        userService.listAll()
                .setHandler(ret -> {
                   
                    APIResult apiResult = new APIResult();
                    
                    if (ret.succeeded())
                        apiResult.setResult(ret.result());
                    else
                        apiResult.error(ret.cause());
                    
                    result.complete(apiResult);
                });
        
        return result;
    }
}
