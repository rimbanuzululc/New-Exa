window.ajaxGet = function(url, headers, cb) {
	
	if (cb == null)  {

		cb = headers;
		headers = {};
	}

	$.ajax({
            url: url,
            type: "GET",
            headers: headers,
            success: function (data) {

                if (data.errorCode == 0)
                    cb(data.result);
                else
                    cb(null, data.errorCode, data.errorMsg);
            },
            error: function (error) {
                cb(null, -99, "Server error : " + error);
            }
        });
}

window.ajaxDelete = function(url, headers, cb) {
	
	if (cb == null)  {

		cb = headers;
		headers = {};
	}

	$.ajax({
            url: url,
            type: "DELETE",
            headers: headers,
            success: function (data) {

                if (data.errorCode == 0)
                    cb(data.result);
                else
                    cb(null, data.errorCode, data.errorMsg);
            },
            error: function (error) {
                cb(null, -99, "Server error : " + error);
            }
        });
}

window.ajaxPost = function(url, headers, data, cb) {

	if (cb == null) {

		cb = data;
		data = headers;
		headers = {};
	} 		

	$.ajax({
            url: url,
            type: "POST",
            headers: headers,
            dataType: "json",
            contentType: 'application/json',
            processData: false,
            data: JSON.stringify(data),
            success: function (data) {

                if (data != null) {

                    if (data.errorCode == 0)
                        cb(data.result);
                    else
                        cb(null, data.errorCode, data.errorMsg);
                }
                else
                    cb(null, -99, "Server returns invalid data");
            },
            error: function (error) {
                cb(null, -99, "Server error : " + error);
            }
        });
}

window.ajaxPostFile = function(url, headers, data, cb) {
        
	if (cb == null) {

		cb = data;
		data = headers;
		headers = {};
	} 		

	$.ajax({
            url: url,
            type: "POST",
            headers: headers,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            data: data,
            success: function (data) {

                if (data != null) {

                    if (data.errorCode == 0)
                        cb(data.result);
                    else
                        cb(null, data.errorCode, data.errorMsg);
                }
                else
                    cb(null, -99, "Server returns invalid data");
            },
            error: function (error) {
                cb(null, -99, "Server error : " + error);
            }
        });
}

window.ajaxPut = function(url, headers, data, cb) {

	if (cb == null) {

		cb = data;
		data = headers;
		headers = {};
	} 		

	$.ajax({
            url: url,
            type: "PUT",
            headers: headers,
            dataType: "json",
            contentType: 'application/json',
            processData: false,
            data: JSON.stringify(data),
            success: function (data) {

                if (data != null) {

                    if (data.errorCode == 0)
                        cb(data.result);
                    else
                        cb(null, data.errorCode, data.errorMsg);
                }
                else
                    cb(null, -99, "Server returns invalid data");
            },
            error: function (error) {
                cb(null, -99, "Server error : " + error);
            }
        });
}

window.dateToStr = function(dt) {

    return dt.getFullYear() + "-" +
            ((dt.getMonth() < 9) ? "0" + (dt.getMonth() + 1) : (dt.getMonth() + 1)) + "-" +
            ((dt.getDate() < 10) ? "0" + dt.getDate() : dt.getDate());
}

window.strToDate = function(str) {

    var parts = str.toString().split(/[\s-:]/);
    return new Date(parseInt(parts[0]), parseInt(parts[1]) - 1, parseInt(parts[2]), 0, 0, 0, 0);
}

window.dateTimeToStr = function(dt) {

    return dt.getFullYear() + "-" +
            ((dt.getMonth() < 9) ? "0" + (dt.getMonth() + 1) : (dt.getMonth() + 1)) + "-" +
            ((dt.getDate() < 10) ? "0" + dt.getDate() : dt.getDate()) + " " +
            ((dt.getHours() < 10) ? "0" + dt.getHours() : dt.getHours()) + ":" +
            ((dt.getMinutes() < 10) ? "0" + dt.getMinutes() : dt.getMinutes()) + ":" +
            ((dt.getSeconds() < 10) ? "0" + dt.getSeconds() : dt.getSeconds());
}

window.strToDateTime = function(str) {

    var parts = str.toString().split(/[\s-:]/);
    return new Date(parseInt(parts[0]), parseInt(parts[1]) - 1, parseInt(parts[2]), parseInt(parts[3]), parseInt(parts[4]), parseInt(parts[5]), 0);
}

window.formatNumber = function(number) {
	
	number = number.toFixed(0);
	var nstr = number.toString();
	var rgx = /(\d+)(\d{3})/;
	
	while (rgx.test(nstr))
		nstr = nstr.replace(rgx, '$1' + "." + '$2');
	
	return nstr;
}

window.setDdlValueWInterval = function(element, value, intrvl) {
	
	element.dropdown("set selected", "" + value);
	var count = 0;
	var setter = setInterval(function() {
		if (element.value == ("" + value) || count == 10){
			clearInterval(setter);
		}
		else {
			element.dropdown("set selected", "" + value);
			count++;
		}
	}, intrvl);
}