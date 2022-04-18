/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.service;

import io.vertx.core.Future;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;



/**
 *
 * @author hp
 */
public class TemplateUtil {
    
    public String generateText(String template, Map<String, Object> parameters, 
                                        String rowStart, String rowEnd,
                                        String tblStart, String tblEnd,
                                        String tblSeparator) throws Exception { 
        
        if (template == null)
            template = "";
                    
        Future<String> resultx = Future.future();
        
        String result = template;
        
        
        try {        
            String source = template;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
            DecimalFormat df = new DecimalFormat("###,###,###");
            
            int start = source.indexOf("{{");

            while (start > 0) {

                int end = source.indexOf("}}", start);

                if (end > 0) {
                    String part = source.substring(start, end + 2);

                    part = part.replaceAll("<.*?>", "");

                    source = source.substring(0, start) +
                             part +
                            source.substring(end + 2);

                    start = source.indexOf("{{", start + 2);
                }
                else
                    start = -1;
            } 

            for (String key : parameters.keySet()) {

                if (parameters.get(key) == null) {
                    source = source.replaceAll("\\{\\{" + key + "\\}\\}", "");
                }
                else if (parameters.get(key) instanceof Map) {

                    Map<String, Object> subparam = (Map<String, Object>) parameters.get(key);

                    for (String subkey : subparam.keySet()) {
                        
                        source = source.replaceAll("\\{\\{" + key + "." + subkey + "\\}\\}", subparam.get(subkey).toString());
                    }
                }
                else if (parameters.get(key) instanceof List) {

                    List<Map<String, Object>> list = (List<Map<String, Object>>) parameters.get(key);

                    int pos = source.indexOf("{{" + key);

                    if (pos > 0) {

                        int lastPos = source.lastIndexOf("{{" + key);
                        
                        int startRow = source.lastIndexOf(rowStart, pos);
                        int endRow = source.indexOf(rowEnd, pos);
                        
                        int endRowLastPos = source.indexOf(rowEnd, lastPos);
                        boolean useTable = false;
                        
                        if (endRow != endRowLastPos) {
                            
                            startRow = source.lastIndexOf(tblStart, pos);
                            endRow = source.indexOf(tblEnd, lastPos);
                            useTable = true;
                        }

                        if ((startRow > 0) && (endRow > 0)) {

                            String part;
                            
                            if (useTable)
                                part = source.substring(startRow, endRow + tblEnd.length());
                            else
                                part = source.substring(startRow, endRow + rowEnd.length());

                            String row = "";

                            for (Object obj : list) {

                                if (obj instanceof Map) {

                                    Map<String, Object> map = (Map<String, Object>) obj;

                                    String rowPart = part;

                                    for (String keyMap : map.keySet())  {

                                        String str = map.get(keyMap) == null ? "" : map.get(keyMap).toString();
                                        rowPart = rowPart.replaceAll("\\{\\{" + key + "." + keyMap + "\\}\\}", str);
                                    }

                                    row += rowPart;
                                }
                                else {

                                    Map<String, Object> beanMap = PropertyUtils.describe(obj);

                                    String rowPart = part;

                                    for (String keyMap : beanMap.keySet()) {
                                        
                                        try {
                                            if (beanMap.get(keyMap) == null) {

                                                rowPart = rowPart.replaceAll("\\{\\{" + key + "." + keyMap + "\\}\\}", "");
                                            }
                                            else if (beanMap.get(keyMap) instanceof Date) {

                                                rowPart = rowPart.replaceAll("\\{\\{" + key + "." + keyMap + "\\}\\}", sdf.format(beanMap.get(keyMap)));
                                            }
                                            else if ((beanMap.get(keyMap) instanceof Integer) 
                                                    ||(beanMap.get(keyMap) instanceof Double) 
                                                    ||(beanMap.get(keyMap) instanceof Long) 
                                                    ) {

                                                rowPart = rowPart.replaceAll("\\{\\{" + key + "." + keyMap + "\\}\\}", df.format(beanMap.get(keyMap)));
                                            }
                                            else if ((beanMap.get(keyMap) instanceof String) 
                                                    || (beanMap.get(keyMap) instanceof Float) 
                                                    || (beanMap.get(keyMap) instanceof Boolean) 
                                                    )
                                                rowPart = rowPart.replaceAll("\\{\\{" + key + "." + keyMap + "\\}\\}", beanMap.get(keyMap).toString());
                                            else {

                                                rowPart = rowPart.replaceAll("\\{\\{" + key + "." + keyMap + "\\}\\}", beanMap.get(keyMap).toString());                                                
                                            }                                
                                        }
                                        catch (Exception esub) {

                                            System.out.println("error replace " + key + "." + keyMap);
                                        }                                          
                                    }      
                                    
                                    row += rowPart;
                                }
                                
                                if (useTable)
                                    row += tblSeparator;
                            }

                            source = source.substring(0, startRow) + 
                                        row +
                                    source.substring(endRow + rowEnd.length()); 
                        }                            
                    }
                }
                else if (parameters.get(key) instanceof Date) {

                    source = source.replaceAll("\\{\\{" + key + "\\}\\}", sdf.format(parameters.get(key)));
                }
                else if ((parameters.get(key) instanceof Integer) 
                        ||(parameters.get(key) instanceof Double) 
                        ||(parameters.get(key) instanceof Long) 
                        ) {
                    
                    source = source.replaceAll("\\{\\{" + key + "\\}\\}", df.format(parameters.get(key)));
                }
                else if ((parameters.get(key) instanceof String) 
                        || (parameters.get(key) instanceof Float) 
                        || (parameters.get(key) instanceof Boolean) 
                        )
                    source = source.replaceAll("\\{\\{" + key + "\\}\\}", parameters.get(key).toString());
                else if (parameters.get(key) != null) {

                    Object obj = parameters.get(key);

                    Map<String, Object> beanMap = PropertyUtils.describe(obj);
                    
                    for (String keyMap : beanMap.keySet()) {

                        try {
                            if (beanMap.get(keyMap) instanceof Date) {

                                source = source.replaceAll("\\{\\{" + key + "." + keyMap + "\\}\\}", sdf.format(beanMap.get(keyMap)));
                            }
                            else if ((beanMap.get(keyMap) instanceof Integer) 
                                    ||(beanMap.get(keyMap) instanceof Double) 
                                    ||(beanMap.get(keyMap) instanceof Long) 
                                    ) {

                                source = source.replaceAll("\\{\\{" + key + "." + keyMap + "\\}\\}", df.format(beanMap.get(keyMap)));
                            }
                            else if ((beanMap.get(keyMap) instanceof String) 
                                    || (beanMap.get(keyMap) instanceof Float) 
                                    || (beanMap.get(keyMap) instanceof Boolean) 
                                    )
                                source = source.replaceAll("\\{\\{" + key + "." + keyMap +  "\\}\\}", beanMap.get(keyMap).toString());
                            else {

                                String str = beanMap.get(keyMap) == null ? "" : beanMap.get(keyMap).toString();
                                source = source.replaceAll("\\{\\{" + key + "." + keyMap + "\\}\\}", str);
                            }                                
                        }
                        catch (Exception esub) {

                            System.out.println("error replace " + key + "." + keyMap);
                        }                            
                    }
                }
            }

            // delete yang tidak ada mapping
//            result.complete(source);//.replaceAll("\\{\\{.+?\\}\\}", "");
            result = source;
            
            resultx.complete(result);
        }
        catch (Exception e) {
            throw e;
        }
        
        return result;
    }
    
    public Future<String> generateDocx(String template, Map<String, Object> parameters, String output) throws Exception { 
        
        Future<String> result = Future.future();
        String source = null;
        
        try {
        
            File templateFile = new File(template);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
            
            if (templateFile.exists()) {
                
                File outputFile = new File(output);
                File tempDir = new File(outputFile.getAbsolutePath() + "_temp");
                
                tempDir.mkdirs();
                
                
                source = FileUtils.readFileToString(new File(tempDir.getAbsoluteFile() + "/word/document.xml"), "UTF-8");
                
                source = generateText(source, parameters, 
                "<w:tr>", "</w:tr>",
                "<w:tbl>", "</w:tbl>",
                "<w:p><w:pPr><w:pStyle w:val=\"Normal\"/><w:rPr><w:lang w:val=\"id-ID\"/></w:rPr></w:pPr><w:r><w:rPr><w:lang w:val=\"id-ID\"/></w:rPr></w:r></w:p>");

                FileUtils.writeStringToFile(new File(tempDir.getAbsoluteFile() + "/word/document.xml"), source, "UTF-8");
                
                if (output.toLowerCase().endsWith(".pdf")) {
                
                    File docFile = new File(outputFile.getAbsolutePath() + ".docx");
                    docxToPdf(docFile.getAbsolutePath(), output)
                            .setHandler(ret -> {
                                
                                result.complete(output);
                            });
                }
                else{
                FileUtils.deleteDirectory(tempDir);
                result.complete("");
                }
            }            
        }
        catch (Exception e) {
            
            throw e;
        }
        
        return result;
    }
    
    public Future<String> docxToPdf(String source, String dest) {
        
        Future<String> result = Future.future();
        
        try {
            
            FileInputStream in = new FileInputStream(source);
            XWPFDocument document = new XWPFDocument(in);
            File outFile = new File(dest);
            OutputStream out = new FileOutputStream(outFile);
            PdfOptions options = null;
            PdfConverter.getInstance().convert(document, out, options);
        } 
        catch (Exception e) {
            e.printStackTrace(System.out);
        }
        
        return result;
    }
    
}
