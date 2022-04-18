package io.starlight.test.http;

import io.starlight.http.RequestBody;
import io.starlight.http.RequestMapping;
import io.starlight.http.ResponseType;
import io.starlight.http.RestController;
import io.vertx.core.Future;
import java.util.Date;
import io.starlight.http.PathParam;
import io.starlight.http.QueryParam;
import io.vertx.core.http.HttpMethod;

/**
 *
 * @author denny
 */
@RestController("/test")
public class TestController {
    
    @RequestMapping(value = "/default", produce = ResponseType.TEXT)
    public Future<String> test() {
        
        return Future.succeededFuture("test " + new Date());
    }
    
    @RequestMapping(value = "/path/:aa", produce = ResponseType.TEXT)
    public Future<String> testPath(@PathParam("aa") String data) {
        
        return Future.succeededFuture("test path " + new Date() + " : " + data);
    }
    
    @RequestMapping(value = "/pathquery/:aa", produce = ResponseType.TEXT)
    public Future<String> testPathQuery(@PathParam("aa") String data, @QueryParam("test") String test) {
        
        return Future.succeededFuture("test path " + new Date() + " : " + data + " - " + test);
    }
    
    @RequestMapping(value = "/body", method = HttpMethod.POST)
    public Future<TestDTO> testBody(@RequestBody TestDTO data) {
        
        data.setDataInt(data.getDataInt() + 10);
        data.setDataString(data.getDataString() + " Asik");
        data.setDataBool(!data.isDataBool());
        
        return Future.succeededFuture(data);
    }
}
