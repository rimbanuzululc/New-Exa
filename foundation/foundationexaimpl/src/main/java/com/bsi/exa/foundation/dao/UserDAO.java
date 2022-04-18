package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.HakAksesDTO;
import com.bsi.exa.foundation.dto.SubMenu;
import com.bsi.exa.foundation.dto.User;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 *
 * @author denny
 */
@DAO(config = "db")
public class UserDAO extends CommonDAO {
    
    public Future<User> add(User user) throws NoSuchAlgorithmException {
        Future<User> result = Future.future();
        
        insert(user).setHandler(ret -> {
            if(ret.succeeded()){
                
                getById(ret.result().getUserId())
                    .setHandler(ret2 -> {
                        
                        result.complete(ret2.result());
                        
                    });
            }else{
                result.fail(ret.cause());
            }
        });
                
         return result;
    }
    
    public Future<User> update(User user) {
    Future<User> result = Future.future();
        
//        String encoded = Base64.getEncoder().encodeToString(user.getPassword().getBytes());
//        user.setPassword(encoded);
        super.update(user).setHandler(ret -> {
            if(ret.succeeded()){
                 getById(ret.result().getUserId())
                    .setHandler(ret2 -> {
                        
                        result.complete(ret2.result());
                        
                    });
            }else{
                result.fail(ret.cause());
            }
        });
                
         return result;      
    }
    
    public Future<User> delete(User user) {
    
        Future<User> result = Future.future();
 
        super.delete(user).setHandler(ret -> {
            if(ret.succeeded()){
                result.complete(user);
            }else{
                result.fail(ret.cause());
            }
        });
                
         return result;  
    }
    
    public Future<User> getById(String userId) {
        
        Future<User> result = Future.future();
         System.out.println("userid : "+userId);
        queryScriptWihtParam("getById", User.class, "userId", userId)
            .setHandler(ret -> {
                
                if (ret.succeeded() && ret.result() != null) {
                    
                    result.complete(ret.result().get(0));
                
                } else {
                    result.fail(ret.cause());
                }
                
            });
        
        return result;
    }
    
    public Future<List<User>> search(String filter, int page) {
       
        int rowPerPage = 10;
        int start = (page - 1) * rowPerPage;
        
        return queryScriptWihtParam("search", User.class, "filter", filter, "start", start, "rowPerPage", rowPerPage);
    }
    
    
    public Future<List<HakAksesDTO>> hakAkses(String userId) {
        
        
        return queryScriptWihtParam("hakAkses", HakAksesDTO.class, "userId", userId)
                .compose(ret -> {
                    return loadHakAksesDetail(ret, userId);
                
                });
                
    }
    
    
    
    public Future<List<HakAksesDTO>> loadHakAksesDetail(List<HakAksesDTO> listAksesDTO, String userId) {
        Future<List<HakAksesDTO>> result = Future.future();

        List<Future> listFuture = new ArrayList<>();

        for (HakAksesDTO hakAksesDTO : listAksesDTO) {

            listFuture.add(loadHakAksesDetail(hakAksesDTO, userId));
        }
        CompositeFuture.join(listFuture)
                .setHandler(ret -> {

                    if (ret.succeeded()) {
                        result.complete(listAksesDTO);
                    } else {
                        result.fail(ret.cause());
                    }
                });

        return result;
    }
    
    
    protected Future<HakAksesDTO> loadHakAksesDetail(HakAksesDTO hakAksesDTO, String userId) {
        int parentId = hakAksesDTO.getId();
        return queryScriptWihtParam("subMenu", SubMenu.class,"parentId", parentId, "userId", userId)
                .compose(ret -> {

                    if (!ret.isEmpty()) {
                        hakAksesDTO.setSubMenu(ret);
                    }

                    return Future.succeededFuture(hakAksesDTO);
                });
    }
    
    public Future<List<User>> listAll () {
        
        return queryScript("listAll", User.class);
    }
    
}
