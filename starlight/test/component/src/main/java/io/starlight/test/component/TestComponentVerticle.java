package io.starlight.test.component;

import io.starlight.AutoWired;
import io.starlight.ComponentScan;
import io.starlight.StarlightVerticle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author denny
 */
@ComponentScan("io.starlight.test.component")
@ComponentScan("io.starlight.test.componentalt")
public class TestComponentVerticle extends StarlightVerticle {

    @AutoWired
    protected TestComponent comp;
    
    // test alt
    @AutoWired("Alt")
    protected TestComponent compAlt;
    
    @AutoWired
    protected TestService service;
    
    // test alt
    @AutoWired("SvcAlt")
    protected TestService serviceAlt;
    
    @Override
    public void start() throws Exception {
        
        System.err.println("====== TestComponentVerticle start ====");
        
        /*System.err.println("component : " + comp);
        System.err.println("component alt : " + compAlt);
                
        System.err.println("testInt ===> " + comp.testInt(5));
        System.err.println("testString Alt ===> " + compAlt.testString("xx", "yy"));
        
        service.svcInt(5).setHandler(ret -> {
            
                if (ret.succeeded()) {
                    
                    System.out.println("svcInt : " + ret.result());
                }
                else {
                    
                    System.out.println("failed svcInt : " + ret.cause());
                }
            });
        
        serviceAlt.svcString("xx", "yy")
                    .setHandler(ret -> {
                        
                       if (ret.succeeded()) {

                            System.out.println("svcString : " + ret.result());
                        }
                        else {

                            System.out.println("failed svcString : " + ret.cause());
                        }                        
                    });*/
        
        /*
        serviceAlt.svcDate(new Date())
                    .setHandler(ret -> {
                        
                       if (ret.succeeded()) {

                            System.out.println("svcDate : " + ret.result());
                        }
                        else {

                            System.out.println("failed svcDate : " + ret.cause());
                        }                        
                    });
        */
        
        List<TestPOJO> pojoList = new ArrayList<>();
        
        TestPOJO pojo = new TestPOJO(1, true, "satu");
        
            List<SubPOJO> subList = new ArrayList<>();
            
            subList.add(new SubPOJO(1, "aa"));
            subList.add(new SubPOJO(2, "bb"));
                
            pojo.setSubList(subList);
            
        pojoList.add(pojo);
        
        pojoList.add(new TestPOJO(2, false, "dua"));
        pojoList.add(new TestPOJO(3, true, "tiga"));
        
        service.svcSendList(pojoList)
                 .setHandler(ret -> {
                        
                       if (ret.succeeded()) {

                            System.out.println("svcSendList : " + ret.result());
                        }
                        else {

                            System.out.println("failed svcSendList : " + ret.cause());
                        }                        
                    });
        
        /*service.svcGetList(pojoList)
                 .setHandler(ret -> {
                        
                       if (ret.succeeded()) {

                           for (TestPOJO pj : ret.result()) {
                           
                               System.out.println("=====> " + pj.getDataInt());
                           }
                           
                            System.out.println("svcGetList : " + ret.result());
                        }
                        else {

                            System.out.println("failed svcGetList : " + ret.cause());
                        }                        
                    });*/
        
        /*List<String> stringList = new ArrayList<>();
        
        stringList.add("satu");
        stringList.add("dua");
        stringList.add("tiga");
        
        service.svcGetString(stringList)
                 .setHandler(ret -> {
                        
                       if (ret.succeeded()) {

                           for (String pj : ret.result()) {
                           
                               System.out.println("=====> " + pj);
                           }
                           
                            System.out.println("svcGetString : " + ret.result());
                        }
                        else {

                            System.out.println("failed svcGetString : " + ret.cause());
                        }                        
                    });*/
    }
}
