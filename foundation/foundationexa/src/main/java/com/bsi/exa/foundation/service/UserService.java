package com.bsi.exa.foundation.service;

import com.bsi.exa.foundation.dto.User;
import com.bsi.exa.foundation.dto.HakAksesDTO;
import com.bsi.exa.foundation.dto.UpdatePassword;
import java.util.List;
import io.vertx.core.Future;

/**
 *
 * @author denny
 */
public interface UserService {
    
    Future<User> add(User user);
    Future<User> update(User user);
    Future<User> delete(String id);
    Future<User> get(String userId);
    Future<UpdatePassword> updatePass (String userId, String password);
    
    Future<User> login(String userId, String password);
    Future<List<HakAksesDTO>> hakAkses(String userId);
    
    Future<List<User>> search(String filter, int page);
    Future<List<User>> listAll();
}
