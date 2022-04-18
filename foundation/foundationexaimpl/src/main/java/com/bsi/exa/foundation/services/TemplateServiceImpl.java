/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.service.TemplateService;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 *
 * @author hp
 */
public class TemplateServiceImpl implements TemplateService{

    @Override
    public Future<String> generateHTMLFromTempate(String templateId, Map<String, Object> variable) {
        
        String template = templateId;
        Future<String> result = Future.future();
                
                    generateHTMLFromString(template, variable)
                        .setHandler(ret -> {

                            if (ret.succeeded())
                                result.complete(ret.result());
                            else
                                result.fail(ret.cause());
                        });
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

    @Override
    public Future<String> generateStringFromTemplate(String templateId, Map<String, Object> obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Future<String> generateHTMLFromString(String template, Map<String, Object> variable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
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

                String currPath = Paths.get(".").toAbsolutePath().normalize().toString();
                String[] script = { "google-chrome",  
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

    @Override
    public Future<String> generateStringFromString(String templateStr, Map<String, Object> obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
