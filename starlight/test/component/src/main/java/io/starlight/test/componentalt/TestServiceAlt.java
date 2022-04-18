package io.starlight.test.componentalt;

import io.starlight.Service;
import io.starlight.test.component.TestPOJO;
import io.starlight.test.component.TestService;
import io.vertx.core.Future;
import java.util.Date;
import java.util.List;

/**
 *
 * @author denny
 */
@Service("SvcAlt")
public class TestServiceAlt implements TestService {

    @Override
    public Future<Integer> svcInt(int a) {
        
        return Future.succeededFuture(a * a);
    }

    @Override
    public Future<Boolean> svcBoolean(boolean a) {
        
        return Future.succeededFuture(a);
    }

    @Override
    public Future<String> svcString(String a, String b) {
        
        return Future.succeededFuture(a + "-" + b + " -- Service alt");
    }

    @Override
    public Future<String> svcSendList(List<TestPOJO> listPojo) {
        
        return Future.succeededFuture("OK -- Service alt");
    }

    @Override
    public Future<List<TestPOJO>> svcGetList(List<TestPOJO> listPojo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Future<String> svcSendString(List<String> listPojo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Future<List<String>> svcGetString(List<String> listPojo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Future<String> svcDate(Date dt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
