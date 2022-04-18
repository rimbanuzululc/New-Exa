package io.starlight.test.component;

import io.starlight.Service;
import io.vertx.core.Future;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author denny
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public Future<Integer> svcInt(int a) {
        
        return Future.succeededFuture(a + a);
    }

    @Override
    public Future<Boolean> svcBoolean(boolean a) {
        
        return Future.succeededFuture(!a);
    }

    @Override
    public Future<String> svcString(String a, String b) {
        
        return Future.succeededFuture(a + "-" + b + " Service");
    }

    @Override
    public Future<String> svcSendList(List<TestPOJO> listPojo) {
        
        for (TestPOJO pojo : listPojo) {
            System.out.println("--->" + pojo.getDataInt() + " ==> " +pojo.getSubList());
        }
        return Future.succeededFuture("OK LIST " + listPojo.size());
    }

    @Override
    public Future<List<TestPOJO>> svcGetList(List<TestPOJO> listPojo) {
        
        for (TestPOJO pojo : listPojo) {            
            System.out.println("--->" + pojo.getDataInt() + " ==> " +pojo.getSubList());
            
            pojo.setDataInt(10 + pojo.getDataInt());
        }
        return Future.succeededFuture(listPojo);
    }

    @Override
    public Future<String> svcSendString(List<String> listPojo) {
        
        for (String pojo : listPojo) {
            System.out.println("--->" + pojo);
        }
        return Future.succeededFuture("OK LIST " + listPojo.size());
    }

    @Override
    public Future<List<String>> svcGetString(List<String> listPojo) {
        
        List<String> retList = new ArrayList<>();
        
        for (String pojo : listPojo) {            
            System.out.println("--->" + pojo);
            
            retList.add("10" + pojo + " ===> " + pojo);
            
        }
        
        return Future.succeededFuture(retList);
    }

    @Override
    public Future<String> svcDate(Date dt) {
        
        return Future.succeededFuture("Date-->" + dt);
    }
    
    
}
