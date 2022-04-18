var UserService = function() {
	
	var service = this;

	service.login = function(userId, password, cb) {

		ajaxPost("./user/login", { userId : userId, password : password}, cb);
	}

	service.searchRole = function(filter, page, cb) {

    	ajaxGet("./role/search?filter=" + filter + "&page=" + page, 
    			{ 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
    			cb);
    }

    service.searchUser = function(filter, page, cb) {

    	ajaxGet("./user/search?filter=" + filter + "&page=" + page, 
    			{ 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
    			cb);
    }

     service.addUser = function(data, cb) {
        
        ajaxPut("./user",
                { 'Authorization' : 'Bearer ' + app.getAccessToken() },  
                data, 
                cb);
    }

     service.updateUser = function(data, cb) {
        
        ajaxPost("./user",
                { 'Authorization' : 'Bearer ' + app.getAccessToken() },  
                data, 
                cb);
    }

    service.deleteUser = function(id, cb) {
        ajaxDelete("./user/byid/" + id, 
                    { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                    cb);
        
    }

     service.searchMenu = function(filter,parentId,page, cb) {

    	ajaxGet("./menu/search?filter=" + filter +  "&parentId=" + parentId +  "&page=" + page, 
    			{ 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
    			cb);
    }

    service.getAllMenu = function(cb) {

        ajaxGet("./menu/all", 
                { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                cb);
    }

    service.getAll = function(cb) {

        ajaxGet("./role/listall", 
                { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                cb);
    }


    ///--- Menu --- ///
    
    service.addMenu = function(data, cb) {
        
    	ajaxPut("./menu",
    			{ 'Authorization' : 'Bearer ' + app.getAccessToken() },  
    			data, 
    			cb);
    }

    service.updateMenu = function(data, cb) {
        
        ajaxPost("./menu",
                { 'Authorization' : 'Bearer ' + app.getAccessToken() },  
                data, 
                cb);
    }

    service.deleteMenu = function(id, cb) {
        ajaxDelete("./menu/byid/" + id, 
                    { 'Authorization' : 'Bearer ' + app.getAccessToken() }, 
                    cb);
        
    }

    /// ---- Role ---- ///

    service.addRole = function(data, cb) {
        
    	ajaxPut("./role",
    			{ 'Authorization' : 'Bearer ' + app.getAccessToken() },  
    			data, 
    			cb);
    } 

    service.role = function(data,cb) {
        
        ajaxPost("./user/hakakses/" + data, 
                { 'Authorization' : 'Bearer ' + app.getAccessToken() },
                cb);
    }
    
    service.updateRole = function(data, cb) {
        
    	ajaxPost("./role",
    			{ 'Authorization' : 'Bearer ' + app.getAccessToken() },  
    			data, 
    			cb);
    } 

}

window.userService = new UserService();