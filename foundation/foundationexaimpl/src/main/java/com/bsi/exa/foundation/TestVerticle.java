package com.bsi.exa.foundation;

//import com.jatis.commerce.foundation.dao.SkuDAO;
//import com.jatis.commerce.foundation.dto.Sku;
//import com.jatis.commerce.foundation.dto.User;
//import com.jatis.commerce.foundation.services.MessageService;
//import com.jatis.commerce.foundation.services.UserService;
import io.starlight.AutoWired;
import io.starlight.StarlightVerticle;

/**
 *
 * @author denny
 */
public class TestVerticle extends StarlightVerticle {

//    @AutoWired
//    protected UserService service;
////    
////    @AutoWired
////    protected SkuDAO skuDAO;
//
//    @AutoWired
//    protected MessageService msgService;
//    
    @Override
    public void start() throws Exception {
        
        System.out.println("Foundation Impl Started");
        
        /*
        Sku sku = new Sku();
        sku.setDealerPrice(10000);
        
        skuDAO.add(sku)
                .setHandler(ret -> {
                   
                    System.out.println("===> selesai");
                });
        */
        
        /*User usr = new User();
        
        usr.setUserId("test");
        usr.setPassword("password");
        usr.setName("Test User");
        
        service.add(usr)
                .setHandler(ret -> {
                   
                    if (ret.succeeded()) 
                        System.out.println("sukses add user");
                    else
                        System.out.println("Gagal add user " + ret.cause().getMessage());
                    
                    
                    service.login("test", "password")
                            .setHandler(ret2 -> {

                                if (ret2.succeeded())
                                    System.out.println("login ok");
                                else
                                    System.out.println("login failed " + ret2.cause().getMessage());
                            });
                });   
        */
    }
}
