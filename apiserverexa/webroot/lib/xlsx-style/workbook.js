function Sheet(sheetName) {

	var sheet = this;
	sheet.sheetName = sheetName;
	sheet.range = {s: {c:10000000, r:10000000}, e:{c:0, r:0}};
	sheet.data = [];

	sheet.updateRange = function(row, col) {
		if (sheet.range.s.r > row) { sheet.range.s.r = row;}
		if (sheet.range.s.c > col) { sheet.range.s.c = col; }
		if (sheet.range.e.r < row) { sheet.range.e.r = row; }
		if (sheet.range.e.c < col) { sheet.range.e.c = col; }
	}

	sheet.datenum = function(v, date1904) {
		if (date1904) v += 1462;
		var epoch = Date.parse(v);
		return (epoch - new Date(Date.UTC(1899, 11, 30))) / (24 * 60 * 60 * 1000);
	}

	sheet.addCell = function(value, row, col, styles) {

		if (value instanceof Array) {

			sheet.updateRange(row, col);

			var currRow = row;
			var currCol = col;

			for (var i in value) {

				currCol = col;

				if (value[i] instanceof Array) {

					for (var j in value[i]) {

						var type = 's';

						if (typeof(value[i][j]) == "number")
							type = 'n'
						else if (value[i][j] instanceof Date) {
							value = sheet.datenum(value[i][j]);
							type = 'd';
						}

						var cell = {t: type, v: value[i][j], s:styles};

						if (cell.t === 'd') {
							cell.t = 'n';
							cell.z = XLSX.SSF._table[14];
						}

						var cell_ref = XLSX.utils.encode_cell({c: currCol, r: currRow});
						sheet.data[cell_ref] = cell;

						currCol++;
					}
				}
				else {

					var type = 's';

					if (typeof(value[i]) == "number")
						type = 'n'
					else if (value[i] instanceof Date) {
						value = sheet.datenum(value[i]);
						type = 'd';
					}

					var cell = {t: type, v: value[i], s:styles};

					if (cell.t === 'd') {
						cell.t = 'n';
						cell.z = XLSX.SSF._table[14];
					}

					var cell_ref = XLSX.utils.encode_cell({c: currCol, r: currRow});
					sheet.data[cell_ref] = cell;
				}

				currRow++;
			}

			sheet.updateRange(currRow, currCol);
		}
		else {

			var type = 's';

			if (typeof(value) == "number")
				type = 'n'
			else if (value instanceof Date) {
				value = sheet.datenum(value);
				type = 'd';
			}

			sheet.updateRange(row, col);

			var cell = {t: type, v: value, s:styles};

			if (cell.t === 'd') {
				cell.t = 'n';
				cell.z = XLSX.SSF._table[14];
			}

			var cell_ref = XLSX.utils.encode_cell({c: col, r:row});
			sheet.data[cell_ref] = cell;
		}		
	}

	sheet.getCell = function(row, col) {

		var result = null;

		var cell_ref = XLSX.utils.encode_cell({c: col, r:row});

		return sheet.data[cell_ref];
	}

	sheet.getCellValue = function(row, col) {

		var cell = sheet.getCell(row, col);
		
		if (cell != null) 
			return cell.v;
		else
			return null;
	}

	sheet.getMinRow = function() {

		return sheet.range.s.r;
	}

	sheet.getMaxRow = function() {

		return sheet.range.e.r;
	}

	sheet.getMinCol = function() {

		return sheet.range.s.c;
	}

	sheet.getMaxCol = function() {

		return sheet.range.e.c;
	}

	sheet.allBorder = {
						"border": {
							"left": {
								"style": "medium",
								"color": {
								"auto": 1
								}
							},
							"right": {
								"style": "medium",
								"color": {
								"auto": 1
								}
							},
							"top": {
								"style": "medium",
								"color": {
								"auto": 1
								}
							},
							"bottom": {
								"style": "medium",
								"color": {
								"auto": 1
								}
							},
						}
					};
}

function Workbook() {

	var wb = this;

	wb.sheetList = [];

	wb.addSheet = function(sheetName) {

		var newSheet = new Sheet(sheetName);
		wb.sheetList.push(newSheet);

		return newSheet;		
	}

	wb.s2ab = function(s) {
		var buf = new ArrayBuffer(s.length);
		var view = new Uint8Array(buf);
		for (var i=0; i!=s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
		return buf;
	}

	wb.save = function(fileName) {

		outWb = {
					SheetNames : [],
					Sheets : {}	
				};

		for (var i in wb.sheetList) {

			wb.sheetList[i].data['!ref'] = XLSX.utils.encode_range(wb.sheetList[i].range);
			wb.sheetList[i].data['!pageSetup'] = {scale: '140'};

			outWb.SheetNames.push(wb.sheetList[i].sheetName);
			outWb.Sheets[wb.sheetList[i].sheetName] = wb.sheetList[i].data;
		}

		var defaultCellStyle =  { font: { name: "Verdana", sz: 11, color: "FF00FF88"}, fill: {fgColor: {rgb: "FFFFAA00"}}};
		var wopts = { bookType:'xlsx', bookSST:false, type:'binary', defaultCellStyle: defaultCellStyle, showGridLines: false};

		var wbout = XLSX.write(outWb, {bookType:'xlsx', bookSST:true, type: 'binary'});
		saveAs(new Blob([wb.s2ab(wbout)],{type:"application/octet-stream"}), fileName);
	}

	wb.load = function(file, cb) {

		if (file) {
			
			var reader  = new FileReader();

			reader.addEventListener("load", 
				function () {
					
					var data = new Uint8Array(reader.result);
					var wbin = XLSX.read(data, {type:"array"});
				
					for (var i in wbin.Sheets) {

						var sheet = wb.addSheet(i);
						sheet.data = wbin.Sheets[i];

						var ranges = wbin.Sheets[i]["!ref"];

						if (ranges != null) {

							var range = ranges.split(":");
							var cell = XLSX.utils.decode_cell(range[0]);

							sheet.updateRange(cell.r, cell.c);

							range = ranges.split(":");
							cell = XLSX.utils.decode_cell(range[1]);

							sheet.updateRange(cell.r, cell.c);
						}
					}

					console.log(wb);
					if (cb != null)
						cb();
				}, false);

			reader.readAsArrayBuffer(file);
		}
	}
}