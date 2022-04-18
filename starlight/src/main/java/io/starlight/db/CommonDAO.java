package io.starlight.db;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.UpdateResult;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author denny
 */
public class CommonDAO {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    protected static Map<Class, List> fieldMap = new ConcurrentHashMap<>();
    protected static Map<Class, List> columnMap = new ConcurrentHashMap<>();
    
    protected Map<String, String> scriptMap = new ConcurrentHashMap<>();
    
    protected SQLClient sqlClient;
    protected JsonObject config;
    protected SQLDialect sqlDialect = SQLDialect.POSTGRESQL;
    
    protected static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    
    protected Vertx vertx() {
    
        return Vertx.currentContext().owner();
    }
    
    protected Context context() {
    
        return Vertx.currentContext();
    }
    
    protected Future<SQLConnection> getConn() {

        Future result = Future.future();
    
        if (sqlClient != null) {
            
            sqlClient.getConnection(connResult -> {
                
                    if (connResult.succeeded()) 
                        result.complete(connResult.result());
                    else 
                        result.fail(connResult.cause());
                });
        }
        else
            result.fail("SQLClient not defined");
            
        return result;
    }
    
    protected void setFieldValue(Object target, String fieldName, Object value) {
        
        String methodName = "set" + fieldName.toLowerCase();
        
        Method[] methodList = target.getClass().getMethods();
        boolean found = false;
        
        for (Method method : methodList) {
            
            if (method.getName().toLowerCase().equals(methodName)) {
                
                if (method.getParameterCount() == 1) {

                    found = true;
                
                    try {
                        if ((method.getParameterTypes()[0] == Date.class) && (value instanceof String)) {
                         
                            if (((String) value).length() == 8)
                                value = "0000-00-00T" + value;
                            else if (((String) value).length() == 10)
                                value += "T00:00:00";
                            else                                        
                                value = OffsetDateTime.parse(((String) value)).atZoneSameInstant(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                            
                            value = sdf.parse(((String )value).substring(0, 19));
                        }
                        else if ((method.getParameterTypes()[0] == Integer.class) && (value instanceof Long)) {
                         
                            value = ((Long) value).intValue();
                        }
                        else if ((method.getParameterTypes()[0] == Long.class) && (value instanceof Integer)) {
                         
                            value = ((Integer) value).longValue();
                        }
                        
                        method.invoke(target, value);
                    }
                    catch (Exception e) {

                        logger.error("Error setvalue " + fieldName + ", expecting " + method.getParameterTypes()[0].getName() + ", get " + value.getClass().getName() + " ==> " + value, e);
                    }

                    break;
                } 
            }
        }
        
        if (!found) {
            
            List<Field> fields = getAllFields(target.getClass());
            
            for (Field field : fields) {
                
                field.setAccessible(true);
                
                Column colAnot = field.getAnnotation(Column.class);
                fieldName = fieldName.toLowerCase();
                        
                if (colAnot != null) {
                    
                    if (colAnot.value().toLowerCase().equals(fieldName)) {
                        
                        methodName = "set" + field.getName().toLowerCase();
                        
                        for (Method method : methodList) {
            
                            if (method.getName().toLowerCase().equals(methodName)) {

                                try {                                        
                                    method.invoke(target, value);
                                }
                                catch (Exception e) {

                                    logger.error("Error setfieldvalue : ", e);
                                }

                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    
    protected void setFieldValues(Object target, List<String> names, List<Object> values) {

        int index = 0;
    
        if (target instanceof JsonObject) {

            while (index < names.size()) {
                
                ((JsonObject) target).put(names.get(index), values.get(index));
                index++;
            }
        }
        else {
         
            while (index < names.size()) {

                setFieldValue(target, names.get(index), values.get(index));
                index++;
            }
        }        
    }
    
    protected Object getFieldValue(Field field, Object target) {
        
        Object result = null;
        
        String methodName = "get" + 
                            field.getName().substring(0, 1).toUpperCase() +
                            field.getName().substring(1);
  
        Method method = null;
        
        try {
            
            method = target.getClass().getMethod(methodName);
        }
        catch (Exception e) {
        }
                
        if ((method == null) && ((field.getType() == boolean.class) || (field.getType() == Boolean.class))) {

            methodName = "is" + 
                        field.getName().substring(0, 1).toUpperCase() +
                        field.getName().substring(1);

            try {
                method = target.getClass().getMethod(methodName);
            }
            catch (Exception e) {
                                
            }
        }   

        if (method != null) {
         
            try {
                
                result = method.invoke(target);
            }
            catch (Exception e) {
                
                logger.error("Error getting field value : " + field.getName() + ": ", e);
            }
        }
        
        return result;
    }
    
    protected List<String> getColumn(Class<?> type, Connection conn) {
    
        List<String> result = columnMap.get(type);
        
        if (result == null) {
            
            result = new ArrayList<>();
            
            try {
                Table tableAnnot = type.getAnnotation(Table.class);
                
                if (tableAnnot != null) {
                    
                    DatabaseMetaData metaData = conn.getMetaData();
                    ResultSet rs = metaData.getColumns(null, null, tableAnnot.value(), null);

                    while (rs.next()) {
                        
                        if (result.indexOf(rs.getString("COLUMN_NAME")) < 0) 
                            result.add(rs.getString("COLUMN_NAME"));
                    }   
                }
            }
            catch (Exception e) {
                
                logger.error("get metadata failed : ", e);
            }
            
            columnMap.put(type, result);
        }
        
        return result;
    }
    
    protected static List<Field> getAllFields(Class<?> type) {
        
        List<Field> fields = fieldMap.get(type);
        
        if (fields == null) {
            
            fields = new ArrayList<>();
        
            for (Class<?> c = type; c != null; c = c.getSuperclass()) {
                
                if (c != Object.class)
                    fields.addAll(Arrays.asList(c.getDeclaredFields()));
            }
            
            fieldMap.put(type, fields);
        }
        
        return fields;
    }
    
    public void init(JsonObject config) {
    
        this.config = config;
        sqlClient = JDBCClient.createShared(vertx(), config);
        
        try {
            
            loadScript(this.getClass().getSimpleName() + ".sql");
        }
        catch (Exception e) {
            
        }
    }
    
    protected <T> Future<Boolean> insert(final List<T> listObj) {
    
        Future result = Future.future();
        
        List<Future> listFut = new ArrayList<>();
        
        for (T obj : listObj) {
        
            listFut.add(insert(obj));
        }
                
        CompositeFuture.join(listFut)
                    .setHandler(ret -> {
                       
                        if (ret.succeeded())
                            result.complete(true);
                        else
                            result.fail(ret.cause());
                    });
        
        return result;
    }
    
    protected <T> Future<T> insert(final T obj) {
        
        Future result = Future.future();
        
        try {

            final Table tableAnnot = obj.getClass().getAnnotation(Table.class);

            if (tableAnnot != null) {

                String fieldList = "";
                String valuesList = "";

                List<Field> fields = getAllFields(obj.getClass());
                List<Object> paramList = new ArrayList<>();

                for (Field field : fields) {

                    field.setAccessible(true);

                    Computed computedAnot = field.getAnnotation(Computed.class);

                    if (computedAnot == null) {

                        AutoKey autokeyAnot = field.getAnnotation(AutoKey.class);

                        if (autokeyAnot == null) {

                            Column columnAnot = field.getAnnotation(Column.class);

                            if (columnAnot != null)
                                fieldList += ", " + columnAnot.value();
                            else
                                fieldList += ", " + field.getName();

                            valuesList += ", ?";
                            
                            Object val = getFieldValue(field, obj);
                            
                            if (val instanceof Date)
                                paramList.add(new Timestamp(((Date) val).getTime()));
                            else
                                paramList.add(val);
                        }
                    }
                }

                if (fieldList.length() > 0) {

                    fieldList = fieldList.substring(2);
                    valuesList = valuesList.substring(2);
                }

                final String sql = "insert into " 
                        + tableAnnot.value()  
                        + "(" + fieldList +  ")"
                        + " values " 
                        + "(" + valuesList +  ")";
                
                if (logger.isDebugEnabled()) {
                	logger.debug(sql);
                }

                final JsonArray paramObj = new JsonArray(paramList);

                getConn()
                    .setHandler(connResult -> {

                        if (connResult.succeeded()) {

                            connResult.result().updateWithParams(sql, paramObj, upresult-> {

                                if (upresult.succeeded()) {

                                    if (upresult.result().getKeys().size() > 0) {

                                        Connection conn = connResult.result().unwrap();

                                        List<String> listColumn = getColumn(obj.getClass(), conn);

                                        if (logger.isDebugEnabled()) { 
                                        	logger.debug(listColumn.toString()); 
                                        	logger.debug(upresult.result().getKeys().toString()); 
                                        }
                                        
                                        int i = 0;
                                        for (Object val : upresult.result().getKeys()) {
                                            
                                            if (logger.isDebugEnabled()) { 
                                            	logger.debug("set " + i + " - " + listColumn.get(i) + " : " + val); 
                                            }
                                            
                                            if (i < listColumn.size())
                                                setFieldValue(obj, listColumn.get(i), val);
                                            
                                            i++;
                                        }

                                        result.complete(obj);
                                    }
                                    else
                                        result.complete(obj);
                                }                                    
                                else {

                                    logger.error("failed : ", upresult.cause());
                                    result.fail(upresult.cause());
                                }

                                close(connResult.result());
                            });
                        }
                        else {
                        	logger.error("failed conn : ", connResult.cause());                                
                            result.fail(connResult.cause());
                        }                            
                    });
            }
            else
                throw new Exception("Table annotation not found : " + obj.getClass().getCanonicalName());
        }
        catch (Exception e) {

            logger.error("Error insert : ", e);
            
            if (!result.isComplete())
                result.fail(e);
        }
        
        return result;
    }
    
    protected <T> Future<Boolean> update(final List<T> listObj) {
    
        Future result = Future.future();
        
        List<Future> listFut = new ArrayList<>();
        
        for (T obj : listObj) {
        
            listFut.add(update(obj));
        }
                
        CompositeFuture.join(listFut)
                    .setHandler(ret -> {
                       
                        if (ret.succeeded())
                            result.complete(true);
                        else
                            result.fail(ret.cause());
                    });
        
        return result;
    }
                
    protected <T> Future<T> update(T obj) {
        
        Future<T> result = Future.future();
        
        try {

            final Table tableAnnot = obj.getClass().getAnnotation(Table.class);

            if (tableAnnot != null) {

                String sql = "update " + tableAnnot.value() + " set ";

                String updateList = "";
                String filterList = "";

                List<Object> paramUpdateList = new ArrayList<>();
                List<Object> paramFilterList = new ArrayList<>();

                List<Field> fields = getAllFields(obj.getClass());

                for (Field field : fields) {

                    field.setAccessible(true);

                    Computed computedAnot = field.getAnnotation(Computed.class);

                    if (computedAnot == null) {

                        Object fieldValue = getFieldValue(field, obj);

                        if (fieldValue != null) {

                            AutoKey autoAnot = field.getAnnotation(AutoKey.class);
                            Key keyAnot = field.getAnnotation(Key.class);

                            Column columnAnot = field.getAnnotation(Column.class);

                            if ((autoAnot != null) || (keyAnot != null)) {

                                if (columnAnot != null)
                                    filterList += " and " + columnAnot.value() + " = ? ";
                                else
                                    filterList += " and " + field.getName() + " = ? ";

                                if (fieldValue instanceof Date)
                                    paramFilterList.add(new Timestamp(((Date) fieldValue).getTime()));
                                else
                                    paramFilterList.add(fieldValue);
                            }
                            else {

                                if (columnAnot != null)
                                    updateList += ", " + columnAnot.value() + " = ? ";
                                else
                                    updateList += ", " + field.getName() + " = ? ";

                                if (fieldValue instanceof Date)
                                    paramUpdateList.add(new Timestamp(((Date) fieldValue).getTime()));
                                else
                                    paramUpdateList.add(fieldValue);
                            }                                                                
                        }
                    }
                }

                if (paramUpdateList.size() > 0) {

                    sql += updateList.substring(1);

                    if (paramFilterList.size() > 0) {

                        sql += " where " + filterList.substring(4);
                        paramUpdateList.addAll(paramFilterList);
                    }

                    final JsonArray paramObj = new JsonArray(paramUpdateList);
                    final String querySQL = sql;

                    if (logger.isDebugEnabled()) {
                    	logger.debug(sql);
                    }

                    getConn()
                        .setHandler(connResult -> {
                            if (connResult.succeeded()) {
                                connResult.result().updateWithParams(querySQL, paramObj, queryResult-> {
                                	try {
	                                    if (queryResult.succeeded())
	                                        result.complete(obj);
	                                    else {
	
	                                        if (logger.isDebugEnabled()) {
	                                        	logger.debug("failed : " + queryResult.cause().getMessage());
	                                        }
	                                        result.fail(queryResult.cause());
	                                    }    
                                	} finally {
                                		close(connResult.result());
                                	}
                                });
                            }
                            else {
                            	logger.error("failed conn : ", connResult.cause());
                                result.fail(connResult.cause());
                            }                      
                            
                        });
                }
                else
                    throw new Exception("Update field not found : " + obj.getClass().getCanonicalName());
            }
            else
                throw new Exception("Table annotation not found : " + obj.getClass().getCanonicalName());
        }
        catch (Exception e) {

            result.fail(e);
        }            
        
        return result;
    }
    
    protected <T> Future<T> upsert(T obj) {
        
        Future<T> result = Future.future();
        
        try {

            final Table tableAnnot = obj.getClass().getAnnotation(Table.class);

            if (tableAnnot != null) {

                List<Field> fields = getAllFields(obj.getClass());
                                
                Constructor<T> ctor = (Constructor<T>) obj.getClass().getConstructor();
                T selectObj = ctor.newInstance();
        
                for (Field field : fields) {
                
                    AutoKey autoAnnot = field.getAnnotation(AutoKey.class);
                    Key keyAnnot = field.getAnnotation(Key.class);
                    
                    if ((autoAnnot != null) || (keyAnnot != null)) {
                        
                        field.setAccessible(true);
                        field.set(selectObj, field.get(obj));
                    }
                }
                
                selectOne(selectObj)
                        .setHandler(ret -> {
                            
                            if (ret.succeeded() && ret.result() != null) {
                                
                                update(obj)
                                    .setHandler(ret2 -> {

                                        if (ret2.succeeded())
                                            result.complete(obj);
                                        else
                                            result.fail(ret2.cause());
                                    });
                            }
                            else {
                                
                                insert(obj)
                                    .setHandler(ret3 -> {
                                        
                                        if (ret3.succeeded())
                                            result.complete(obj);
                                        else
                                            result.fail(ret3.cause());
                                    });
                            }
                        });               
            }
            else
                throw new Exception("Table annotation not found : " + obj.getClass().getCanonicalName());
        }
        catch (Exception e) {

            result.fail(e);
        }            
        
        return result;
    }

    protected <T> Future<Boolean> upsert(final List<T> listObj) {
    
        Future result = Future.future();
        
        List<Future> listFut = new ArrayList<>();
        
        for (T obj : listObj) {
        
            listFut.add(upsert(obj));
        }
                
        CompositeFuture.join(listFut)
                    .setHandler(ret -> {
                       
                        if (ret.succeeded())
                            result.complete(true);
                        else
                            result.fail(ret.cause());
                    });
        
        return result;
    }
    
    protected <T> Future<Boolean> delete(final List<T> listObj) {
    
        Future result = Future.future();
        
        List<Future> listFut = new ArrayList<>();
        
        for (T obj : listObj) {
        
            listFut.add(delete(obj));
        }
                
        CompositeFuture.join(listFut)
                    .setHandler(ret -> {
                       
                        if (ret.succeeded())
                            result.complete(true);
                        else
                            result.fail(ret.cause());
                    });
        
        return result;
    }
    
    protected <T> Future<T> delete(T obj) {
        
        Future<T> result = Future.future();
        
        try {

            final Table tableAnnot = obj.getClass().getAnnotation(Table.class);

            if (tableAnnot != null) {

                String sql = "delete from " + tableAnnot.value();

                String filterList = "";
                List<Object> paramList = new ArrayList<>();

                List<Field> fields = getAllFields(obj.getClass());

                for (Field field : fields) {

                    field.setAccessible(true);

                    Computed computedAnot = field.getAnnotation(Computed.class);

                    if (computedAnot == null) {

                        Object fieldValue = getFieldValue(field, obj);

                        if (fieldValue != null) {

                           Column columnAnot = field.getAnnotation(Column.class);

                            if (columnAnot != null)
                                filterList += " and " + columnAnot.value() + " = ? ";
                            else
                                filterList += " and " + field.getName() + " = ? ";

                            if (fieldValue instanceof Date)
                                paramList.add(new Timestamp(((Date) fieldValue).getTime()));
                            else
                                paramList.add(fieldValue);
                        }
                    }
                }

                if (paramList.size() > 0) {

                    filterList = filterList.substring(4);
                    sql += " where " + filterList;
                }

                final JsonArray paramObj = new JsonArray(paramList);
                final String querySQL = sql;

                if (logger.isDebugEnabled()) {
                	logger.debug(sql);
                }

                getConn()
                    .setHandler(connResult -> {

                        if (connResult.succeeded()) {

                            connResult.result().updateWithParams(querySQL, paramObj, queryResult-> {
                            	try {
	                                if (queryResult.succeeded()) 
	                                    result.complete();
	                                else {
	
                                    	logger.error("failed : ", queryResult.cause());
	                                    result.fail(queryResult.cause());
	                                }
                            	} finally {
                            		close(connResult.result());
                            	}
                            });
                        }
                        else {
                        	logger.error("failed conn : ", connResult.cause());                                
                            result.fail(connResult.cause());
                        }                            
                    });
            }
            else
                throw new Exception("Table annotation not found : " + obj.getClass().getCanonicalName());
        }
        catch (Exception e) {

            result.fail(e);
        }            
        
        return result;
    }

    protected <T> Future<List<T>> select(T obj) {
        
        Future<List<T>> result = Future.future();
        
        try {

            final Table tableAnnot = obj.getClass().getAnnotation(Table.class);

            if (tableAnnot != null) {

                String sql = "select * from " + tableAnnot.value();

                String filterList = "";
                List<Object> paramList = new ArrayList<>();

                List<Field> fields = getAllFields(obj.getClass());

                for (Field field : fields) {

                    field.setAccessible(true);

                    Computed computedAnot = field.getAnnotation(Computed.class);

                    if (computedAnot == null) {

                        Object fieldValue = getFieldValue(field, obj);

                        if (fieldValue != null) {

                           Column columnAnot = field.getAnnotation(Column.class);

                            if (columnAnot != null)
                                filterList += " and " + columnAnot.value() + " = ? ";
                            else
                                filterList += " and " + field.getName() + " = ? ";

                            if (fieldValue instanceof Date)
                                paramList.add(new Timestamp(((Date) fieldValue).getTime()));
                            else
                                paramList.add(fieldValue);
                        }
                    }
                }

                if (paramList.size() > 0) {

                    filterList = filterList.substring(4);
                    sql += " where " + filterList;
                }

                final JsonArray paramObj = new JsonArray(paramList);
                final String querySQL = sql;

                if (logger.isDebugEnabled()) {
                	logger.debug(sql);
                }

                getConn()
                    .setHandler(connResult -> {

                        if (connResult.succeeded()) {

                            connResult.result().queryWithParams(querySQL, paramObj, queryResult-> {
                            	try {
	                                if (queryResult.succeeded()) {
	
	                                    try {
	                                        @SuppressWarnings("unchecked")
											List<T> resultList = (List<T>) getItems(obj.getClass(), 
	                                                                                queryResult.result().getColumnNames(), 
	                                                                                queryResult.result().getResults());
	                                        result.complete(resultList);
	                                    }
	                                    catch (Exception e) {
	
	                                        e.printStackTrace(System.out);
	                                        if (!result.isComplete())
	                                            result.fail(e);
	                                    }
	                                }                    
	                                else {
	                                    logger.error("failed : ", queryResult.cause());
	                                    result.fail(queryResult.cause());
	                                }
                            	} finally {
                            		close(connResult.result());
                            	}
                            });
                        }
                        else {
                        	logger.error("failed conn : ", connResult.cause());                                
                            result.fail(connResult.cause());
                        }                            
                    });
            }
            else
                throw new Exception("Table annotation not found : " + obj.getClass().getCanonicalName());
        }
        catch (Exception e) {

            result.fail(e);
        }            
        
        return result;
    }
    
    protected <T> Future<List<T>> selectAll(Class<T> type) {
        
        return selectAll(type, null);
    }
    
    protected <T> Future<List<T>> selectAll(Class<T> type, String order) {
        
        Future<List<T>> result = Future.future();
        
        try {

            final Table tableAnnot = type.getAnnotation(Table.class);

            if (tableAnnot != null) {

                String sql = "select * from " + tableAnnot.value() + ((order == null) ? "" : " order by " + order);
                if (logger.isDebugEnabled()) {
                	logger.debug(sql);
                }

                getConn()
                    .setHandler(connResult -> {

                        if (connResult.succeeded()) {

                            connResult.result().query(sql, queryResult-> {
                            	try {
	                                if (queryResult.succeeded()) {
	
	                                    try {
	                                        List<T> resultList = (List<T>) getItems(type, 
	                                                                                queryResult.result().getColumnNames(), 
	                                                                                queryResult.result().getResults());
	                                        result.complete(resultList);
	                                    }
	                                    catch (Exception e) {
                                        	logger.error("query error", e);
	                                        if (!result.isComplete())
	                                            result.fail(e);
	                                    }
	                                }                    
	                                else {
                                    	logger.error("failed : ", queryResult.cause());
	                                    result.fail(queryResult.cause());
	                                }
                            	} finally {
                                    close(connResult.result());
                            	}
                            });
                        }
                        else {
                        	logger.error("failed conn : ", connResult.cause());                                
                            result.fail(connResult.cause());
                        }                            
                    });
            }
            else
                throw new Exception("Table annotation not found : " + type.getCanonicalName());
        }
        catch (Exception e) {

            result.fail(e);
        }            
        
        return result;
    }
    
    protected <T> Future<T> selectOne(T obj) {
        
        Future<T> result = Future.future();
        
        select(obj).setHandler(resultSelect -> {
           
            if (resultSelect.succeeded()) {
                
                if (resultSelect.result().size() > 0)
                    result.complete(resultSelect.result().get(0));
                else
                    result.complete(null);
                    //result.fail(new Exception("Select one failed, Not found : " + obj.getClass().getCanonicalName()));
            }
            else
                result.fail(resultSelect.cause());
        });
        
        return result;
    }
    
    protected <T> List<T> getItems(Class<T> type, List<String> columns, List<JsonArray> rows) throws Exception {
        
        List<T> result;

        if (type == JsonArray.class)
            result = (List<T>) rows;
        else {
            
            result = new ArrayList<>();
                    
            Constructor<T> ctor = type.getConstructor();

            for (JsonArray line : rows) {

                T item = ctor.newInstance();

                setFieldValues(item, columns, line.getList());

                result.add(item);
            }
        }                                                                                          
        
        return result;
    }
    
    protected <T> Future<List<T>> query(String sql, Class<T> type) {
        
        Future<List<T>> result = Future.future();
                    
        getConn()
            .setHandler(connResult -> {

                if (connResult.succeeded()) {
                    connResult.result().query(sql, queryResult-> {
                    	try {
	
	                        if (queryResult.succeeded()) {
	
	                            try {
	                                List<T> resultList = getItems(type, queryResult.result().getColumnNames(), queryResult.result().getResults());
	                                result.complete(resultList);
	                            }
	                            catch (Exception e) {
	                                result.fail(e);
	                            }
	                        }                    
	                        else {
                            	logger.error("query failed : ", queryResult.cause());
	                            result.fail(queryResult.cause());
	                        }
	
                    	} finally {
                            close(connResult.result());
                    	}
                    });
                }
                else {
                	logger.error("failed conn : " + connResult.cause().getMessage());                                
                    result.fail(connResult.cause());
                }                            
            });
        
        return result;
    }

    protected <T> Future<List<T>> queryWithParam(String sql, Class<T> type, Object... objParams) {
        
        Future<List<T>> result = Future.future();
        
        Map<String, Object> map = new HashMap<>();

        int i = 0;

        while (i < objParams.length - 1) {

            if (objParams[i] instanceof String) {

                if (objParams[i +1] instanceof Date)
                    map.put((String) objParams[i], new Timestamp(((Date) objParams[i +1]).getTime()));
                else
                    map.put((String) objParams[i], objParams[i +1]);
            }

            i += 2;
        }

        List<Object> params = new ArrayList<>();        

        String targetSQL = sql;

        // parameter replace
        int currPos = 0;
        int startPos = targetSQL.indexOf("{{", currPos);

        while (startPos > 0) {

            int endPos = targetSQL.indexOf("}}");

            if (endPos > 0) {

                String key = targetSQL.substring(startPos + 2, endPos);

                if (map.containsKey(key)) {

                    targetSQL = targetSQL.substring(0, startPos) +
                                "?" +
                                targetSQL.substring(endPos + 2);

                     params.add(map.get(key));
                }
            }

            currPos = startPos + 1;
            startPos = targetSQL.indexOf("{{", currPos);
        }

        // literal replace
        currPos = 0;
        startPos = targetSQL.indexOf("[[", currPos);

        while (startPos > 0) {

            int endPos = targetSQL.indexOf("]]");

            if (endPos > 0) {

                String key = targetSQL.substring(startPos + 2, endPos);

                if (map.containsKey(key)) {

                    targetSQL = targetSQL.substring(0, startPos) +
                                map.get(key) +
                                targetSQL.substring(endPos + 2);
                }
            }

            currPos = startPos + 2;
            startPos = targetSQL.indexOf("[[", currPos);
        }

        if (logger.isDebugEnabled()) {
        	logger.debug(targetSQL);
        }
        
        logger.info(targetSQL);

        final String execSQL = targetSQL;

        getConn()
            .setHandler(connResult -> {

                if (connResult.succeeded()) {
                    JsonArray jsonParams = new JsonArray(params);
	                    connResult.result().queryWithParams(execSQL, jsonParams, queryResult-> {
	                    	try {
		                        if (queryResult.succeeded()) {
		                            try {
		
		                                List<T> resultList = getItems(type, 
		                                                                queryResult.result().getColumnNames(),
		                                                                queryResult.result().getResults());
		                                result.complete(resultList);
		                            }
		                            catch (Exception e) {		
		                                logger.error("fail getitem : ", e);
		                                if (!result.isComplete())
		                                    result.fail(e);
		                            }                                
		                        }                                    
		                        else {
		
		                            logger.error("fail queryWithParams : ", queryResult.cause());
		                            result.fail(queryResult.cause());
		                        }
	                    	} finally {
	                    		close(connResult.result());
	                    	}
	                    });
                }
                else {

                    logger.error("failed conn : ", connResult.cause());
                    result.fail(connResult.cause());
                }                            
            });
        
        return result;
    }

    protected <T> Future<T> querySingle(String sql, Class<T> type) {
        
        Future<T> result = Future.future();
        
        query(sql, type).setHandler(ret -> {
           
            if (ret.succeeded()) {
                
                if (ret.result().size() > 0)
                    result.complete(ret.result().get(0));
                else
                    result.fail(new Exception("Not found"));
            }
            else
                result.fail(ret.cause());
        });
        
        return result;
    }

    protected <T> Future<T> querySingleWithParam(String sql, Class<T> type, Object... params)  {
        
        Future<T> result = Future.future();
        
        queryWithParam(sql, type, params)
                .setHandler(resultSelect -> {
           
            if (resultSelect.succeeded()) {
                
                if (resultSelect.result().size() > 0)
                    result.complete(resultSelect.result().get(0));
                else
                    result.complete(null);
            }
            else
                result.fail(resultSelect.cause());
        });
        
        return result;
    }

    @SuppressWarnings("unchecked")
	protected Future<UpdateResult> exec(String sql) {
        
        Future result = Future.future();
        
        getConn()
            .setHandler(connResult -> {

                if (connResult.succeeded()) {

                    connResult.result().update(sql, queryResult-> {
                    	try {
	                        if (queryResult.succeeded())
	                            result.complete(queryResult.result());
	                        else {
	
	                            logger.error("exec failed : ", queryResult.cause());
	                            result.fail(queryResult.cause());
	                        }
                    	} finally {
                    		close(connResult.result());
                    	}
                    });
                }
                else {

                    logger.error("failed conn : ", connResult.cause());
                    result.fail(connResult.cause());
                }                            
            });
        
        return result;
    }

    protected Future<UpdateResult> execWithParam(String sql, Object... objParams) {
        
        Future<UpdateResult> result = Future.future();
        
        Map<String, Object> map = new HashMap<>();

        int i = 0;

        while (i < objParams.length - 1) {

            if (objParams[i] instanceof String) {

                if (objParams[i +1] instanceof Date)
                    map.put((String) objParams[i], new Timestamp(((Date) objParams[i +1]).getTime()));
                else
                    map.put((String) objParams[i], objParams[i +1]);
            }

            i += 2;
        }

        List<Object> params = new ArrayList<>();

        String targetSQL = sql;

        // parameter replace
        int currPos = 0;
        int startPos = targetSQL.indexOf("{{", currPos);

        while (startPos > 0) {

            int endPos = targetSQL.indexOf("}}");

            if (endPos > 0) {

                String key = targetSQL.substring(startPos + 2, endPos);

                if (map.containsKey(key)) {

                    targetSQL = targetSQL.substring(0, startPos) +
                                "?" +
                                targetSQL.substring(endPos + 2);

                    params.add(map.get(key));
                }
            }

            currPos = startPos + 1;
            startPos = targetSQL.indexOf("{{", currPos);
        }

        // literal replace
        currPos = 0;
        startPos = targetSQL.indexOf("[[", currPos);

        while (startPos > 0) {

            int endPos = targetSQL.indexOf("]]");

            if (endPos > 0) {

                String key = targetSQL.substring(startPos + 2, endPos);

                if (map.containsKey(key)) {

                    targetSQL = targetSQL.substring(0, startPos) +
                                map.get(key) +
                                targetSQL.substring(endPos + 2);
                }
            }

            currPos = startPos + 1;
            startPos = targetSQL.indexOf("[[", currPos);
        }

        if (logger.isDebugEnabled()) {
        	logger.debug(targetSQL);
        }

        final String execSQL = targetSQL;

        getConn()
            .setHandler(connResult -> {

                if (connResult.succeeded()) {

                    JsonArray jsonParams = new JsonArray(params);
                    connResult.result().updateWithParams(execSQL, jsonParams, queryResult-> {
                    	try {
	                        if (queryResult.succeeded()) {
	                            result.complete(queryResult.result());
	                        }                                    
	                        else {
	
	                            logger.error("failed update with param : ", queryResult.cause());
	                            result.fail(queryResult.cause());
	                        }
                    	} finally {
                            close(connResult.result());
                    	}
                    });
                }
                else {

                    logger.error("failed conn : ", connResult.cause());
                    result.fail(connResult.cause());
                }                            
            });
        
        return result;
    }

    protected void loadScript(String scriptLocation) throws Exception {
                    
        InputStream is = this.getClass().getResourceAsStream(scriptLocation);

        StringBuilder sb = new StringBuilder();
        char[] tmp = new char[4096];

        try {
            
            InputStreamReader reader = new InputStreamReader(is);
           
            for (int cnt; (cnt = reader.read(tmp)) > 0;)
                sb.append( tmp, 0, cnt );
            
        } 
        finally {
            is.close();
        }
        
        String script = sb.toString();
        
        Scanner scanner = new Scanner(script);
        
        String scriptName = "";
        String scriptText = "";
        
        while (scanner.hasNextLine()) {
            
            String line = scanner.nextLine();
            
            if (line.startsWith("--")) {
                
                if (scriptName.length() > 0)                    
                    scriptMap.put(scriptName, scriptText.substring(2));
                
                scriptName = line.substring(2);
                scriptText = "";
            }
            else
                scriptText += "\r\n" + line;
        }
        
        if (scriptName.length() > 0)                    
            scriptMap.put(scriptName, scriptText.substring(2));        
    }
    
    protected <T> Future<List<T>> queryScript(String scriptName, Class<T> type) {
        
        if (scriptMap.containsKey(scriptName))
            return query(scriptMap.get(scriptName), type);
        else
            return Future.failedFuture(new Exception("Script not found : " + scriptName));
    }
    
    protected <T> Future<List<T>> queryScriptWihtParam(String scriptName, Class<T> type, Object... params) {
        
        if (scriptMap.containsKey(scriptName))            
            return queryWithParam(scriptMap.get(scriptName), type, params);
        else
            return Future.failedFuture(new Exception("Script not found : " + scriptName));
    }
    
    protected <T> Future<T> queryScriptSingle(String scriptName, Class<T> type) {
                
        if (scriptMap.containsKey(scriptName))            
            return querySingle(scriptMap.get(scriptName), type);
        else
            return Future.failedFuture(new Exception("Script not found : " + scriptName));
    }
    
    protected <T> Future<T> queryScriptSingleWithParam(String scriptName, Class<T> type, Object... params) {
        
        if (scriptMap.containsKey(scriptName))
            return querySingleWithParam(scriptMap.get(scriptName), type, params);
        else
            return Future.failedFuture(new Exception("Script not found : " + scriptName));            
    }
    
    protected Future<UpdateResult> execScript(String scriptName) {
        
        if (scriptMap.containsKey(scriptName))            
            return exec(scriptMap.get(scriptName));
        else
            return Future.failedFuture(new Exception("Script not found : " + scriptName));
    }
    
    protected Future<UpdateResult> execScriptWithParam(String scriptName, Object... params) {
        
        if (scriptMap.containsKey(scriptName))             
            return execWithParam(scriptMap.get(scriptName), params);
        else
            return Future.failedFuture(new Exception("Script not found : " + scriptName));
    }
    
    protected void close(SQLConnection c) {
    	if (c == null) {
    		return;
    	}
    	c.close();
    }
}
