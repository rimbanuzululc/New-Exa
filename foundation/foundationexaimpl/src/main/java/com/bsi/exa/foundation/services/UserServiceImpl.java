package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.CodedException;
import com.bsi.exa.foundation.Errors;
import com.bsi.exa.foundation.dao.UserDAO;
import com.bsi.exa.foundation.dto.HakAksesDTO;
import com.bsi.exa.foundation.dto.SubMenu;
import com.bsi.exa.foundation.dto.UpdatePassword;
import com.bsi.exa.foundation.dto.User;
import com.bsi.exa.foundation.service.UserService;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author denny
 */
@Service
public class UserServiceImpl implements UserService {

    @AutoWired
    protected UserDAO dao;
    
    @Override
    public Future<User> add(User user) {
        
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 90);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String pass = sdf.format(date);
        
        
        try {
            String password = user.getPassword();
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes(),0,password.length());
            
            String encoded = new BigInteger(1,digest.digest()).toString(16);
            user.setPassword(encoded);
            user.setCreated(date);
            user.setExpired(calendar.getTime());

            return dao.add(user);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Future<User> update(User user) {
        
        Future<User> result = Future.future();
        
        String password = user.getPassword();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes(),0,password.length());
            
            String encoded = new BigInteger(1,digest.digest()).toString(16);
            user.setPassword(encoded);
            
            dao.getById(user.getUserId())
            .setHandler(ret -> {
                
                if (ret.succeeded() && (ret.result() != null)) { 
                        Date date = new Date();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        calendar.add(Calendar.DATE, 90);

                        user.setModify(date);
                        user.setExpired(calendar.getTime());

                        dao.update(user)
                            .setHandler(ret2 -> {
                                
                                if (ret2.succeeded() && ret2.result() != null) {
                                    result.complete(ret2.result());
                                } else {
                                    result.complete(null);
                                }
                                
                            
                            });
                        
                        
                    } else
                        result.fail(new CodedException(Errors.LOGIN_USER_NOT_FOUND));
                });
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
        
    }
    @Override
    public Future<User> get(String userId) {
        
        return dao.getById(userId);
    }
    
    @Override
    public Future<User> login(String userId, String password) {
        
        Future<User> result = Future.future();
        User user = new User();
         try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes(),0,password.length());
            
            String encoded = new BigInteger(1,digest.digest()).toString(16);
            user.setPassword(encoded);
        
        dao.getById(userId)
            .setHandler(ret -> {
                
                if (ret.succeeded() && (ret.result() != null)) { 
                    // to do : password encryptr
                    
                    System.out.println("Password : "+password);
                    System.out.println("Password DB : "+ret.result().getPassword());
                    if (ret.result().getPassword().equals(user.getPassword()) && ret.result().getIsActive() == true) {
                        
                        result.complete(ret.result());
                    } 
                    else
                        result.fail(new CodedException(Errors.LOGIN_USER_NOT_FOUND));
                }
                else
                    System.out.println("error : " +ret.result());
                    result.fail(new CodedException(Errors.LOGIN_USER_NOT_FOUND));
            });
        
        } catch(Exception e){
            System.out.println("Error login exception : " + e.getMessage());
            e.printStackTrace(System.out);
            result.fail(new CodedException(Errors.COMMON));
        }
        
        return result;
    }

    @Override
    public Future<List<User>> search(String filter, int page) {
        
        return dao.search(filter, page);
    }

    @Override
    public Future<User> delete(String id) {
        
         User user = new User();
         user.setUserId(id);
        
        
        return dao.delete(user);
    }
    
    @Override
    public Future<List<HakAksesDTO>> hakAkses(String userId) {
        
        return dao.hakAkses(userId);
    }
    
    
    @Override
    public Future<UpdatePassword> updatePass(String userId, String password) {
        
        UpdatePassword updatePassword = new UpdatePassword();
        Future<UpdatePassword> result = Future.future();
        
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes(),0,password.length());
            
            String encoded = new BigInteger(1,digest.digest()).toString(16);
            System.out.println("New Password : "+encoded);
        dao.getById(userId)
                .setHandler(ret -> {
                    
                    if (ret.succeeded() && ret.result() != null) {
                        
                        System.out.println("Old Password : "+ret.result().getPassword());
                        
                        if (ret.result().getPassword().equals(encoded)) {
                            
                            updatePassword.setAccepted(false);
                            updatePassword.setNewPassword(true);
                            
                        } else {
                            
                            ret.result().setPassword(encoded);
                            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"));
                            cld.setTime(new Date());
                            cld.add(Calendar.MONTH, 3);
                            ret.result().setExpired(cld.getTime());
                            ret.result().setModify(new Date());
                            dao.update(ret.result())
                                    .setHandler(update -> {
                                        
                                        if (update.result() != null) {
                                            
                                            System.out.println("Hasil Update Password : "+update.result().getPassword());
                                        }
                                        
                                    });
                            
                                            updatePassword.setAccepted(true);
                                            updatePassword.setNewPassword(false);
                        }
                        
                    } else {
                        result.fail(new CodedException(Errors.LOGIN_USER_NOT_FOUND));
                    }
                    
                    result.complete(updatePassword);
                });
        
        } catch(Exception e){
            System.out.println("Error login exception : " + e.getMessage());
            e.printStackTrace(System.out);
            result.fail(new CodedException(Errors.COMMON));
        }
        
        return result;
    }
    
    @Override
    public Future<List<User>> listAll() {
        
        return dao.listAll();
    }
    
//  percobaan update setelah commmit  
    
   
}
