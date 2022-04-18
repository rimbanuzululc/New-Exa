/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.dao;

import com.bsi.exa.foundation.dto.Assign;
import com.bsi.exa.foundation.dto.Konsumen;
import com.bsi.exa.foundation.dto.UploadFailureDTO;
import com.bsi.exa.foundation.dto.UploadResultDTO;
import io.starlight.AutoWired;
import io.starlight.db.CommonDAO;
import io.starlight.db.DAO;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author hp
 */
@DAO(config = "db")
public class UploadKonsumenDAO extends CommonDAO {
    
    @AutoWired
    protected AgentPosDAO agentPosDao;
    
    @AutoWired
    protected AssignDAO assignDao;
    
    public Future<UploadResultDTO> getFileExcel (String file) {
        
        UploadResultDTO resultDTO = new UploadResultDTO();
        System.out.println("file : " + file);
        Future<String> result = Future.future();

        byte[] bytes = vertx().fileSystem().readFileBlocking(file).getBytes();
        System.out.println("Bytes : " + Arrays.toString(bytes));
        InputStream iss = new ByteArrayInputStream(bytes);
        
        try {

            return readExcelAndInsert(iss)
                    .setHandler(ret -> {
                        if (ret.succeeded()) {
                            result.complete();
                        } else {
                            result.fail(ret.cause());
                        }
                    });

        } catch (InvalidFormatException | IOException ex) {
            result.fail(ex.getCause());
        }
        return Future.succeededFuture(resultDTO);
    }
    
    private Future<UploadResultDTO> readExcelAndInsert(InputStream iss) throws IOException, InvalidFormatException {
        UploadResultDTO resultDTO = new UploadResultDTO();
        List<UploadFailureDTO> failures = new ArrayList<>();
        resultDTO.setFailures(failures);
        List<Future> futures = new ArrayList<>();
        
        Workbook workbook = WorkbookFactory.create(iss);

        Sheet sheet = workbook.getSheetAt(0);


        Iterator<Row> rowIterator = sheet.rowIterator();

        if (rowIterator.hasNext()) {
            rowIterator.next();
        }
        while (rowIterator.hasNext()) {
            resultDTO.addTotalRow();
            
            Row row = rowIterator.next();
            
            futures.add(processRowKon(sheet, resultDTO, row));
        }

        return CompositeFuture.all(futures)
                .compose(ret2 -> {

                    return Future.succeededFuture(resultDTO);
                });

       
    }
    
    public Future<UploadResultDTO> processRowKon (Sheet sheet, UploadResultDTO resultDTO, Row row) {
        
        Future<UploadResultDTO> future = Future.future();
        Future<String> result = Future.future();
        
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"));
        Date currentDate = calendar.getTime();
        
        Map<String, Integer> data = new HashMap<>();
        Map<String, String> data2 = new HashMap<>();
        DataFormatter formatter = new DataFormatter();
        
        UploadFailureDTO f = new UploadFailureDTO();
        
        String ref_no               = formatter.formatCellValue(row.getCell(0));
        String cardNo               = formatter.formatCellValue(row.getCell(1));
        String name                 = formatter.formatCellValue(row.getCell(2));
        String outstanding          = formatter.formatCellValue(row.getCell(3));
        String main_address_1       = formatter.formatCellValue(row.getCell(4));
        String address_1_city       = formatter.formatCellValue(row.getCell(5));
        String address_1_zipcode    = formatter.formatCellValue(row.getCell(6));
        String address_2            = formatter.formatCellValue(row.getCell(7));
        String address_2_zipcode    = formatter.formatCellValue(row.getCell(8));
        String address_2_city       = formatter.formatCellValue(row.getCell(9));
        String address_3_address_   = formatter.formatCellValue(row.getCell(10));
        String address_3_zipcode    = formatter.formatCellValue(row.getCell(11));
        String address_3_city       = formatter.formatCellValue(row.getCell(12));
        String hp_no                = formatter.formatCellValue(row.getCell(13));
        String hp_no2               = formatter.formatCellValue(row.getCell(14));
        String home_phone_1         = formatter.formatCellValue(row.getCell(15));
        String office_phone_1       = formatter.formatCellValue(row.getCell(16));
        String no_ktp               = formatter.formatCellValue(row.getCell(17));
        String expired_date         = formatter.formatCellValue(row.getCell(18));
        
        System.out.println("----" + formatter.formatCellValue(row.getCell(0)) );

        Double dpd                  = row.getCell(19) == null ? null : row.getCell(19).getNumericCellValue();
        
        Integer int_dpd = null;
        if (dpd != null) {
            int_dpd = dpd.intValue();
        }
        
        
        Konsumen konsumen = new Konsumen();
        
        konsumen.setAddress_1_city(address_1_city);
        konsumen.setAddress_1_zipcode(address_1_zipcode);
        konsumen.setAddress_2(address_2);
        konsumen.setAddress_2_city(address_2_city);
        konsumen.setAddress_2_zipcode(address_2_zipcode);
        konsumen.setAddress_3(address_3_address_);
        konsumen.setAddress_3_city(address_3_city);
        konsumen.setAddress_3_zipcode(address_3_zipcode);
        konsumen.setCardNo(cardNo);
        konsumen.setDpd(int_dpd);
        konsumen.setExpired_date(expired_date);
        konsumen.setHome_phone_1(home_phone_1);
        konsumen.setHp_no(hp_no);
        konsumen.setHp_no2(hp_no2);
        konsumen.setMain_address_1(main_address_1);
        konsumen.setName(name);
        konsumen.setNo_ktp(no_ktp);
        konsumen.setOffice_phone_1(office_phone_1);
        konsumen.setOutstanding(outstanding);
        konsumen.setRef_No(ref_no);
        konsumen.setUpload_date(currentDate);
        
        agentPosDao.getByZipcode(konsumen.getAddress_1_zipcode())
                .setHandler(ret1 -> {
                    if (ret1.succeeded() && ret1.result() != null){
                        Assign assign = new Assign();
                        assign.setIdAgentpos(ret1.result().getIdAgentpos());
                        assign.setIdKonsumen(konsumen.getIdKonsumen());
                        assign.setRef_No(ref_no);
                        assign.setCardNo(cardNo);
                        assign.setAssignDate(new Date());
                        assignDao.add(assign);
                    }
                });
        
        super.insert(konsumen)
                .compose(ret -> {
                
                    return Future.succeededFuture(resultDTO);
                })
                .setHandler(retVail -> {
                
                    if(retVail.failed()){
                                f.setObject(konsumen);
                                f.setRow(row.getRowNum());
                                f.setErrorMessage("error : " + retVail.cause().toString());  
                            resultDTO.getFailures().add(f);
                            
                            resultDTO.addFailed();
                            future.complete(resultDTO);
                        }else{
                                f.setObject(konsumen);
                                f.setRow(row.getRowNum());
                                f.setErrorMessage("Success");  
                            resultDTO.getFailures().add(f);
                            resultDTO.addSuccess();
                            future.complete(resultDTO);

                        }
                });
        
        return future;
    }
    
}
