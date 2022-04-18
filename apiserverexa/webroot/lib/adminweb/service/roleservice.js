var roleService = function() {

    var service = this;
	
    service.search = function(filter, page, cb) {

    	ajaxGet("./role/search?filter=" + filter + "&page=" + page, 
    			{ 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
    			cb);
    }

    service.getAll = function(cb) {

        ajaxGet("./role/listAll", 
                { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                cb);
    }

    service.add = function(data, cb) {
        
    	ajaxPut("./role",
    			{ 'Authorization' : 'Bearer ' + app.getAccessToken() },  
    			data, 
    			cb);
    }

    service.addMenu = function(data, cb) {
        
        ajaxPost("./role/menu",
                { 'Authorization' : 'Bearer ' + app.getAccessToken() },  
                data, 
                cb);
    }    

    service.update = function(data, cb) {

    	ajaxPost("./role", 
    			{ 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
    			data, 
    			cb);
    }

    service.delete = function(id, cb) {
        ajaxDelete("./role/byid/" + id, 
    				{ 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
    				cb);
        
    }

    service.getAddress = function(username, cb) {

        ajaxGet("./role/byid/" + username + "/address", 
                { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                cb);
    }

    service.getListById = function(id, cb) {

        ajaxGet("./role/menu/list/" + id, 
                { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                cb);
    }
};

window.roleService = new roleService();