package io.starlight.test.db;

import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.Future;
import java.util.List;

/**
 *
 * @author denny
 */
@DAO(driver = "org.postgresql.Driver", user = "postgres", pass = "admin", url = "jdbc:postgresql://localhost:5432/ecomm", config = "db")
public class TestDAO extends CommonDAO {
    
    public Future<TestDomain> add(TestDomain data) {
        
        return super.insert(data);
    }
    
    public Future<TestDomain> update(TestDomain data) {
        
        return super.update(data);
    }
    
    public Future<TestDomain> delete(TestDomain data) {
        
        return super.delete(data);
    }
    
    public Future<List<TestDomain>> selectAll() {
        
        return super.queryScript("selectAll", TestDomain.class);
    }
    
    public Future<List<TestDomain>> selectFilter(int id) {
        
        return super.queryScriptWihtParam("selectFilter", TestDomain.class, "id", id);
    }
}