/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.services;

import com.bsi.exa.foundation.dao.ImageKonsumenFinanceDAO;
import com.bsi.exa.foundation.dto.ImageKonsumenFinance;
import com.bsi.exa.foundation.service.ImageKonsumenFinanceService;
import com.bsi.exa.foundation.util.ImageCompressor;
import io.starlight.AutoWired;
import io.starlight.Service;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import static io.vertx.core.Vertx.vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author hp
 */
@Service
public class ImageKonsumenFinanceServiceImpl implements ImageKonsumenFinanceService{

    public final static Logger logger = LoggerFactory.getLogger(ImageCompressor.class);
    
    @AutoWired
    ImageKonsumenFinanceDAO financeDAO;
    
    @Override
    public Future<ImageKonsumenFinance> add(ImageKonsumenFinance finance) {
        
        FileSystem fs = Vertx.currentContext().owner().fileSystem();
        Boolean exist = false;
        String targetFolderString = "D:/SLI/e-WL/"+finance.getKonsumenid()+"/"+finance.getImageName();
        String folderKonsumen = "D:/SLI/e-WL/"+finance.getKonsumenid();
        String tempTargetFolder = "D:/SLI/e-WL/temp/";
        File targetFolder = new File(folderKonsumen);
        
        float quality = 1.0f;
        
        if (!fs.existsBlocking(tempTargetFolder)) {
            fs.mkdirBlocking(tempTargetFolder);
        }
        
        tempTargetFolder = "D:/SLI/e-WL/temp/"+new Date().getTime()+""+((int)(Math.random()*100))+".jpg";
        
        Buffer data = Buffer.buffer(Base64.getDecoder().decode(finance.getImageFile()));
        
        fs.writeFileBlocking(tempTargetFolder, data);
        
        final String input = tempTargetFolder;
        final String output = targetFolderString;
        
        finance.setImagePath(targetFolderString);
        finance.setImageFile("");
        
        if (fs.existsBlocking(folderKonsumen)) {
            if (fs.readDirBlocking(folderKonsumen).size() > 0) {
                String targetName = "";
                for(File file : targetFolder.listFiles()){
                    
                    String fileName = file.getName();
                    if(fileName.equalsIgnoreCase(finance.getImageName())){
                        exist = true;
                        targetName = file.getName();
                        File fldr = new File(targetFolder+"/"+targetName);
                        fldr.delete(); 
                        
                        ImageCompressor.compress(vertx(), input, output, quality
                                , 50, 1200, true)
                                .compose(ret -> {
                                    
                                    logger.info("File temp : "+input);
                                    fs.deleteRecursiveBlocking(input, true);
                                    financeDAO.update(finance);
                                    return Future.succeededFuture();
                                });
                    }
                
                }
                if (!exist){
                    ImageCompressor.compress(vertx(), input, output, quality
                                , 50, 1200, true)
                                .compose(ret -> {
                                    
                                    logger.info("File temp : "+input);
                                    fs.deleteRecursiveBlocking(input, true);
                                    financeDAO.add(finance);
                                    return Future.succeededFuture();
                                });
                }  
            }
            
        } else {
            fs.mkdirBlocking(folderKonsumen);      
            
            ImageCompressor.compress(vertx(), input, output, quality
                                , 50, 1200, true)
                                .compose(ret -> {
                                    
                                    logger.info("File temp : "+input);
                                    fs.deleteRecursiveBlocking(input, true);
                                    financeDAO.add(finance);
                                    return Future.succeededFuture();
                                });
        }
        
        
        return Future.succeededFuture(finance);
    }

    @Override
    public Future<List<ImageKonsumenFinance>> list() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Future<List<ImageKonsumenFinance>> history(Integer id) {
       return financeDAO.listbykonsumen(id);
    }

    @Override
    public Future<ImageKonsumenFinance> getImage(String filename, Integer id) {
        
        Future<ImageKonsumenFinance> result = Future.future();
        
        String targetFolderString = "/home/somasi/konsumenImage/"+id;
        File targetFolder = new File(targetFolderString);
        ImageKonsumenFinance image = new ImageKonsumenFinance();
        
        if (targetFolder.exists()) {
            
            if (targetFolder.isDirectory()) {
                
                String targetName = "";
                for(File file : targetFolder.listFiles()){
                    
                    String fileNama = file.getName();
                    
                    if(fileNama.equalsIgnoreCase(filename)) {
                        targetName = file.getName();
                        break;
                        
                    }
                }
                    
                    if (targetName != null) {
                        
                        File f = new File(targetFolderString + "/" + targetName);
                        String encode = "";
                        
                        try {
                            
                            FileInputStream fileInputStreamReader = new FileInputStream(f);
                            byte[] bytes = new byte[(int) f.length()];
                            fileInputStreamReader.read(bytes);
                            
                            encode = new String(Base64.getEncoder().encode(bytes), "UTF-8");
                            
                            image.setKonsumenid(id);
                            image.setImageFile(encode);
                            image.setImageName(targetName);
                            
                            result.complete(image);
                            
                            
                        } catch (Exception e) {
                            result.fail(new Exception("Error encode " + e));
                        }
                    } else {
                        result.fail(new Exception("File not found"));
                    }
                } else {
                    result.fail(new Exception("File not found"));
                }  
            } else {
                result.fail(new Exception("File not found"));
            }
         return result;
    }
    
}
