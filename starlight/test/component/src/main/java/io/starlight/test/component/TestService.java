package io.starlight.test.component;

import io.vertx.core.Future;
import java.util.Date;
import java.util.List;

/**
 *
 * @author denny
 */
public interface TestService {

    Future<Integer> svcInt(int a);
    Future<Boolean> svcBoolean(boolean a);
    Future<String> svcString(String a, String b);    
    Future<String> svcDate(Date dt);    
    
    Future<String> svcSendList(List<TestPOJO> listPojo);
    Future<List<TestPOJO>> svcGetList(List<TestPOJO> listPojo);
    
    Future<String> svcSendString(List<String> listPojo);
    Future<List<String>> svcGetString(List<String> listPojo);
}
