/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.Errors;
import com.bsi.exa.foundation.dao.AgentPosDAO;
import com.bsi.exa.foundation.dto.AgentPos;
import com.bsi.exa.foundation.dto.Assign;
import com.bsi.exa.foundation.dto.Konsumen;
import com.bsi.exa.foundation.service.AgentPosService;
import com.bsi.exa.foundation.CodedException;
import com.bsi.exa.foundation.dto.OTP;
import com.bsi.exa.foundation.dto.Setting;
import com.bsi.exa.foundation.dto.UpdatePassword;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
/**
 *
 * @author hp
 */
@Service
public class AgentPosServiceImpl implements AgentPosService{
    
    @AutoWired
    AgentPosDAO dao;

    @Override
    public Future<AgentPos> add(AgentPos agentPos) {
        
        Future<AgentPos> result = Future.future();
//        String password = agentPos.getPassword();
        Date date = new Date();
        agentPos.setIsActive(true);
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 90);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String pass = sdf.format(date);

        System.out.println("PASS == " + pass);
        String password = ("WL-"+agentPos.getNama()+"-"+pass);
        
        
        
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes(),0,password.length());
            
            String encoded = new BigInteger(1,digest.digest()).toString(16);
            
            agentPos.setPassword(encoded);
            agentPos.setCreated(date);
            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"));
            cld.setTime(new Date());
            cld.add(Calendar.MONTH, 3);
            agentPos.setExpiredPassword(cld.getTime());
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AgentPosServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        dao.add(agentPos)
                .setHandler(ret2 -> {
                    if(ret2.result() == null || ret2.result().getIdAgentpos() == null){
                        
                        result.fail(new CodedException(Errors.ALREADY_TAKEN_ZIPCODE));
                    } else {
                       
                        dao.checkName(agentPos.getIdAgentpos())
                            .setHandler(ret -> {
                                
                                if (ret.result() != null && ret.succeeded()) {
                                    
                                        agentPos.setCityName(ret.result().getCityName());
                                        agentPos.setCountryName(ret.result().getCountryName());
                                        agentPos.setStateName(ret.result().getStateName());
                                        agentPos.setDistrictName(ret.result().getDistrictName());
                                        agentPos.setSubDistrictName(ret.result().getSubDistrictName());
                                        
                                        result.complete(agentPos);
                                }
                                    
                            });

                    }
                });
        
        return result;
    }

    @Override
    public Future<List<AgentPos>> list() {
        return dao.list();
    }

    @Override
    public Future<Boolean> assign(List<Integer> listIdKonsumen, int idAgent) {
        
        List<Future> listFuture = new ArrayList<>();
        Future<List<Assign>> result = Future.future();
        
        for (int i = 0; i < listIdKonsumen.size(); i++) {
            
            listFuture.add(dao.assignKonsumen(listIdKonsumen.get(i), idAgent));
            
        }
        
        return CompositeFuture.all(listFuture)
        .compose(ret -> {
            
            return Future.succeededFuture(true);
        });
    }

    @Override
    public Future<Assign> listAssign() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Future<Boolean> assignFinance(List<Integer> listKonsumenId, int idAgent) {
        
        List<Future> listFuture = new ArrayList<>();
        Future<List<Assign>> result = Future.future();
        
        for (int i = 0; i < listKonsumenId.size(); i++) {
            
            listFuture.add(dao.assignKonsumenFinance(listKonsumenId.get(i), idAgent));
            
        }
        
        return CompositeFuture.all(listFuture)
        .compose(ret -> {
            
            return Future.succeededFuture(true);
        });
    }

    @Override
    public Future<AgentPos> login(String username, String password) {
        
        Future<AgentPos> result = Future.future();
        AgentPos agentPos = new AgentPos();
        
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes(),0,password.length());
            
            String encoded = new BigInteger(1,digest.digest()).toString(16);
            agentPos.setPassword(encoded);
        
        dao.getByUsername(username)
                .setHandler(ret -> {
                    
                    if (ret.succeeded() && ret.result() != null) {
                        
                        if (ret.result().getPassword().equals(agentPos.getPassword()) && 
                                ret.result().getIsActive() == true) {
                            
//                            ret.result().setPassword("");
//                            if (password.equals("somasi123")) {
//                                 ret.result().setChangePassword(true);
//                            }
//                            
                            result.complete(ret.result());
                        } else {
                            result.fail(new CodedException(Errors.LOGIN_WRONG_PASSWORD));
                        }
                        
                    } else {
                        result.fail(new CodedException(Errors.LOGIN_USER_NOT_FOUND));
                    }
                    
                });
        } catch(Exception e){
            System.out.println("Error login exception : " + e.getMessage());
            e.printStackTrace(System.out);
            result.fail(new CodedException(Errors.COMMON));
        }
        
        return result;
    }

    @Override
    public Future<UpdatePassword> updatePass(String username, String password) {
        
        UpdatePassword updatePassword = new UpdatePassword();
        Future<UpdatePassword> result = Future.future();
        
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes(),0,password.length());
            
            String encoded = new BigInteger(1,digest.digest()).toString(16);
            System.out.println("New Password : "+encoded);
        dao.getByUsername(username)
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
                            ret.result().setExpiredPassword(cld.getTime());
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
    public Future<String> generatePDFFromTempate(String templateId, Map<String, Object> variable) {
        Future<String> result = Future.future();
         
        return
            generateHTMLFromTempate(templateId, variable)
            .compose(ret -> {
                
                return generatePDFFromString(ret, variable);
            });
            
    }
    
     public Future<String> generatePDFFromString(String template, Map<String, Object> variable) {

        Future<String> result = Future.future();
        
        Vertx.currentContext().owner().executeBlocking(ret -> {

            try {
                File tmpFile = File.createTempFile("out", ".html");

                System.out.println("out to ==> " + tmpFile.getAbsolutePath());

                String retHtml = template;
                Files.write(Paths.get(tmpFile.getAbsolutePath()), retHtml.getBytes());

                String spr = ":";

                boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
                if (isWindows)
                    spr = ";";

                String outputName = tmpFile.getName();
                outputName = outputName.substring(0, outputName.length() - 5) + ".pdf";
                System.out.println("output name : " + outputName);

                String currPath = Paths.get(".").toAbsolutePath().normalize().toString();
                String[] script = { "google-chrome",
                                    "--no-sandbox",
                                    "--user-data-dir",
                                    "--headless",
                                    "--disable-gpu",
                                    "-print-to-pdf=" + outputName,
                                    tmpFile.getAbsolutePath()};

                ProcessBuilder builder = new ProcessBuilder();
                builder.command(script);
                builder.inheritIO();

                try {
                    builder.directory(tmpFile.getParentFile());
                    Process process = builder.start();
                    process.waitFor();

                    ret.complete(tmpFile.getParentFile().getAbsolutePath() + "/" + outputName);
                }
                catch (Exception e) {

                    e.printStackTrace();

                    ret.fail(e);
                }
            } 
            catch (Exception e) {

                e.printStackTrace(System.out);

                ret.fail(e);
            }                        
        }, 
        false,
        ret2 -> {

            if (ret2.succeeded())
                result.complete(""  + ret2.result());
            else
                result.fail(ret2.cause());
        });
        
        return result;
    }
    
   public Future<String> generateHTMLFromTempate(String templateId, Map<String, Object> variable) {
        
        Future<String> result = Future.future();
        
       dao.getByCode(templateId)
            .setHandler(retStg -> {

                if (retStg.succeeded()) {
                    
                    String template = retStg.result().getValue();
                
                    generateHTMLFromString(template, variable)
                        .setHandler(ret -> {

                            if (ret.succeeded())
                                result.complete(ret.result());
                            else
                                result.fail(ret.cause());
                        });
                }
                else
                    result.fail(retStg.cause());
            });
        
        return result;
    }
   
    
    public Future<String> generateHTMLFromString(String template, Map<String, Object> variable) {
        
        Future<String> result = Future.future();
        
        Vertx.currentContext().executeBlocking(future -> {

            try {
                System.out.println("Generate string ");

                TemplateEngine engine = new TemplateEngine();

                Context context = new Context();

                for (String name : variable.keySet())
                    context.setVariable(name, variable.get(name));

                String html = engine.process(template, context);
                future.complete(html);
            } 
            catch (Exception e) {

                e.printStackTrace(System.out);
                future.fail(e);
            }
        }, 
        false,
        ret -> {
            
            if (ret.succeeded())
                result.complete("" + ret.result());
            else
                result.fail(ret.cause());
        });
        
        return result;
    }

    @Override
    public Future<List<AgentPos>> search(int id) {
        return dao.search(id);
    }

    @Override
    public Future<AgentPos> edit(AgentPos agentPos) {
        
        String password = agentPos.getPassword();
        
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes(),0,password.length());

            String encoded = new BigInteger(1,digest.digest()).toString(16);
            
            agentPos.setPassword(encoded);
        } catch (Exception e) {
        }
        
        
        return dao.edit(agentPos);
    }
    
    @Override
    public Future<AgentPos> getById(int idAgentPos) {
        return dao.getById(idAgentPos);
    }

    @Override
    public Future<OTP> sendOTP (OTP otp) {
        
        String otp_num = ("" + (ThreadLocalRandom.current().nextInt(100000, 999999))).substring(0, 6);
        otp.setOtp(otp_num);
        
        return dao.sendOtp(otp);
    }

    @Override
    public Future<Boolean> validateOTP(OTP otp) {
        
        Future<Boolean> result = Future.future();
                
        dao.validateOTP(otp)
                .setHandler(ret -> {
                
                    if (ret.result().getOtp().equals(otp.getOtp())) {
                        result.complete(Boolean.TRUE);
                    } else {
                        result.complete(Boolean.FALSE);
                    }
                
                });
        return result;
    }
    
}
