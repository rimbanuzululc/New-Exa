/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsi.exa.foundation.service;

import io.vertx.core.Future;
import java.util.Map;

/**
 *
 * @author hp
 */
public interface TemplateService {
    
    Future<String> generateHTMLFromTempate(String templateId, Map<String, Object> variable);
    Future<String> generatePDFFromTempate(String templateId, Map<String, Object> variable);
    Future<String> generateStringFromTemplate(String templateId, Map<String, Object> obj);
    
    Future<String> generateHTMLFromString(String template, Map<String, Object> variable);
    Future<String> generatePDFFromString(String template, Map<String, Object> variable);
    Future<String> generateStringFromString(String templateStr, Map<String, Object> obj);
    
}
