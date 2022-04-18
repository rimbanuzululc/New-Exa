package io.starlight.test.db;

import io.starlight.AutoWired;
import io.starlight.StarlightVerticle;

/**
 *
 * @author denny
 */
public class TestDBVerticle extends StarlightVerticle {

    @AutoWired
    protected TestDAO dao;
    
    @Override
    public void start() throws Exception {
        
        System.out.println("STARTED");
        
        TestDomain domain = new TestDomain();
        domain.setDataString("bbb");
        domain.setDataBool(false);
        
        dao.add(domain)
                .setHandler( ret -> {
                   
                    if (ret.succeeded()) {
                        
                        System.out.println("sukses add " + ret.result().toString());
                        
                        domain.setDataString(domain.getDataString() + " --> updated");
                        domain.setDataBool(true);
                        
                        dao.update(domain)
                                .setHandler(ret2 -> {
                                    
                                    if (ret2.succeeded()) {
                                        
                                        System.out.println("sukses update " + ret2.result().toString());
                                    }
                                    else {
                                        
                                        System.out.println("gagal update " + ret.cause().getMessage());
                                    }
                                });                        
                    }
                    else {
                        
                        System.out.println("gagal add " + ret.cause().getMessage());
                    }
                });
        
        TestDomain domain2 = new TestDomain();
        domain2.setDataInt(41);
        
        dao.delete(domain2)
                .setHandler(ret -> {
                   
                    if (ret.succeeded())
                        System.out.println("sukses delete");
                    else
                        System.out.println("gagal delete " + ret.cause().getMessage());
                });
        
        
        dao.selectAll()
                .setHandler(ret -> {
                   
                    if (ret.succeeded()) {
                        
                        for (TestDomain obj : ret.result()) {
                            
                            System.out.println("--> " + obj.toString());
                        }
                    }
                    else
                        System.out.println("gagal select all " + ret.cause().getMessage());
                });
        
        dao.selectFilter(43)
                .setHandler(ret -> {
                   
                    if (ret.succeeded()) {
                        
                        for (TestDomain obj : ret.result()) {
                            
                            System.out.println("--> " + obj.toString());
                        }
                    }
                    else
                        System.out.println("gagal select filter " + ret.cause().getMessage());
                });
    }
    
    
}
