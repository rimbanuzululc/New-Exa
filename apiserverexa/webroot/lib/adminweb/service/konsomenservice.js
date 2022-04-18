var AddressService = function() {

    var service = this;
    
    service.search = function(param, filter, page, cb) {

        ajaxGet("./konsumen/search?param=" + param + "&filter=" + filter + "&page=" + page,
                { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                cb);
    }

    service.listall = function(cb) {

        ajaxGet("./konsumen/list/" , 
                { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                cb);
    }
    
    service.getfileexcel = function(data, cb) {
    	ajaxPostFile("./upload/getFileExe",
    			{ 'Authorization' : 'Bearer ' + app.getAccessToken() },
    			data,
    			cb);
    }
    
    service.listAgent = function(cb) {
        
        ajaxGet("./agentpos/list", 
                { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                cb);
    }
    
    service.addAssignt = function(data,cb) {
        
        ajaxPost("./agentpos/assign", 
                { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                data,
                cb);
    }

};

window.addressService = new AddressService();