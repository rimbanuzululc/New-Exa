var FinanceService = function() {

    var service = this;
    
    service.search = function(param, filter, startDate, endDate ,page, cb) {

        ajaxGet("./konsumen/finance/search?param=" + param + "&filter=" + filter +"&startDate="+ startDate +"&endDate="+endDate+"&page=" + page,
                { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                cb);
    }

    service.listall = function(cb) {

        ajaxGet("./konsumen/finance/list/" , 
                { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                cb);
    }
    
    service.getfileexcel = function(data, cb) {
    	ajaxPostFile("./upload/finance/getFileExe",
    			{ 'Authorization' : 'Bearer ' + app.getAccessToken() },
    			data,
    			cb);
    }
    
    service.getbynoaggre = function(noAggrement, cb) {
        ajaxGet("./konsumen/finance/getbyna?noAggre=" + noAggrement ,
        {'Authorization' : 'Bearer ' + app.getAccessToken() }, 
        cb);
    }
    
    service.listAgent = function(cb) {
        
        ajaxGet("./agentpos/list", 
                { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                cb);
    }
    
    service.state = function(cb) {
        
        ajaxGet("./wilayah/state", 
                { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                cb);
    }
    
    service.city = function(stateId, cb) {
        
        ajaxGet("./wilayah/city?stateId="+stateId, 
                { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                cb);
    }
    
    service.district = function(cityId, cb) {
        
        ajaxGet("./wilayah/district?cityId="+cityId, 
                { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                cb);
    }
    
    service.kelurahan = function(districtId, cb) {
        
        ajaxGet("./wilayah/kelurahan?districtId="+districtId, 
                { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                cb);
    }
    
    service.addAssignt = function(data,cb) {
        
        ajaxPost("./agentpos/assign/finance", 
                { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                data,
                cb);
    }
    
    service.addAgent = function (data, cb) {
        
        ajaxPost("./agentpos/add", 
                { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                data,
                cb);
    }
    
    service.listMapping = function(cb) {
        
        ajaxGet("./mapping/area/list", 
                { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                cb);
    }
    
    
    service.listByCode = function(idMapping, cb) {
        
        ajaxGet("./mapping/area/list/code?codeArea="+idMapping, 
                { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                cb);
    }
    
    service.searchAgent = function(districtId, cb) {
        
        ajaxGet("./agentpos/search?districtid="+districtId, 
                { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                cb);
    }
    
    service.downloadSomasi1 = function (noaggrement) {
        return "./download/somasi1?no="+noaggrement;
    }
    
    service.downloadSomasi2 = function (noaggrement) {
        return "./download/somasi2?no="+noaggrement;
    }

};

window.financeService = new FinanceService();