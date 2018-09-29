
$.fn.getFormObj = function(){ 
	var serializedParams = this.serialize();

	function paramString2obj(serializedParams) {
		var obj = {}; 
		function evalThem(str) {
			var attributeName = str.split("=")[0];
			var attributeValue = str.split("=")[1];

			if (!attributeValue) {
				attributeValue ="";

			}
			var array = attributeName.split(".");
			for (var i = 1; i < array.length; i++) {
				var tmpArray = Array();
				tmpArray.push("obj");
				for (var j = 0; j < i; j++) {
					tmpArray.push(array[j]);
				};
				var evalString = tmpArray.join(".");
				// alert(evalString); 
				if (!eval(evalString)) {
					eval(evalString + "={};");
				}
			}; 
			eval("obj." + attributeName + "='" + attributeValue + "';");
		}
		var properties = serializedParams.split("&");
		for (var i = 0; i < properties.length; i++) {
			evalThem(properties[i]);
		};
		return obj;
	}
	return paramString2obj(serializedParams); 
}

/**********************************************
 * url:请求地址
 * page:页码，从1开始
 * rows:每页行数
 * con:参数对象
 * call:回调函数
 *********************************************/
function findDataByPage(url,page, rows,con,callback) {
    url=getRootPath()+"/"+url;//增加项目的根地址
    con.pageNumber=page-1;
    con.rowsCount=rows;
    $.ajax({
        type : 'post',
        url : url,
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data :JSON.stringify(con),
        success: function (obj) {
            callback(obj);//成功后回调操作
        },
        error : function(arg0,arg1,arg2) {
        	return arg0.status;
        }
    });
}

/**********************************************
 * url:请求地址
 * page:页码，从1开始
 * rows:每页行数
 * con:参数对象
 * call:回调函数
 *********************************************/
function findDataByPageAndSync(url,page, rows,con,callback) {
    url=getRootPath()+"/"+url;//增加项目的根地址
    con.pageNumber=page-1;
    con.rowsCount=rows;
    $.ajax({
        type : 'post',
        url : url,
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data :JSON.stringify(con),
        async:false,
        success: function (obj) {
            callback(obj);//成功后回调操作
        },
        error : function(arg0,arg1,arg2) {
            return arg0.status;
        }
    });
}
/**********************************************
 * datagrid的Ajax的数据分页查找
 * page:页码，从1开始
 * rows:每页行数
 * datagridId:datagrid的ID
 * params:参数对象
 * type:操作类别
 *********************************************/
function findData(url,page, rows,datagridId,params,callback) {
	$('#'+datagridId).datagrid("loading"); 
	params.pageNumber=page-1;
	params.rowsCount=rows; 
	$.ajax({
		type : 'post',
		url : url, 
		dataType: 'json',
		contentType: "application/json; charset=utf-8",
		data :JSON.stringify(params),
		success: function (obj) { 
			$('#'+datagridId).datagrid("options").pageNumber=page;
			$('#'+datagridId).datagrid("options").pageSize=rows;

			if (obj.obj==undefined && obj!=null)
                $('#'+datagridId).datagrid('loadData',obj);
			else if (obj!=null && obj.obj!=null)
			{
				if (obj.obj.rows==null)
					obj.obj.rows=[];
				$('#'+datagridId).datagrid('loadData',obj.obj);
			}
			$('#'+datagridId).datagrid("loaded");
			if (callback!=undefined)
			  callback(obj);//成功后回调操作
		},
		error : function(arg0,arg1,arg2) {
			$('#'+datagridId).datagrid("loaded"); 
			switch(arg0.status)
			{
			  case 200: 
			      alert("当前操作可能未授权操作，服务器未响应，请联系管理员进行处理，问题发生地址：" + url);
                  top.location.href =getRootPath()+"/login2.jsp";
                  break;
			  case 404:
				  alert("当前操作的资源不存在，请联系管理员！"); 
				  break;
			  case 500:
				  alert("程序内容处理错误：500,内部符号"+url); 
				  break;
			  default:
				  alert("数据处理错误,错误代码："+arg0.status);
			      break; 
			} 
		}
	}); 
}
function findByPage(url,id,con,callback)
{
	url=getRootPath()+"/"+url;//增加项目的根地址
	setPager(url,id,con,callback);
	findData(url, 1, 10, id, con,callback);
}
/**********************************************
 * datagrid的Ajax无分页数据查找
 * datagridId:datagrid的ID
 * params:参数对象
 * type:数据类型
 *********************************************/
function findDataNoPage(url,datagridId,params) {
    url=getRootPath()+"/"+url;//增加项目的根地址
	$('#'+datagridId).datagrid("loading");
	$.ajax({
		type : 'post',
		url : url,
		dataType: 'json',		
		data: params,
		success: function (obj) {
		    $('#' + datagridId).datagrid('loadData', obj.data);
			$('#'+datagridId).datagrid("loaded");
		},
		error : function(arg0,arg1,arg2) {
			$('#'+datagridId).datagrid("loaded");
			switch(arg0.status)
			{
			  case 200:
                  alert("当前操作可能未授权操作，服务器未响应，请联系管理员进行处理，问题发生地址：" + url);
                  top.location.href =getRootPath()+"/login2.jsp";
                  break;
			  case 404:
				  alert("当前操作的资源不存在，请联系管理员！"); 
				  break;
			  case 500:
				  alert("程序内容处理错误：500,内部符号"+url); 
				  break;
			  default:
				  alert("数据处理错误,错误代码："+arg0.status);
			      break; 
			} 
		}
	});
}

/**********************************************
 * datagrid的Ajax树形结构数据查找
 * datagridId:datagrid的ID
 * params:参数对象
 * type:数据处理类型
 *********************************************/
function findTreeGridData(url,datagridId,params) {
    url=getRootPath()+"/"+url;//增加项目的根地址
	$('#'+datagridId).treegrid("loading");
	$.ajax({
		type : 'post',
		url : url,
		dataType : 'json',
		data : params,
		success : function(obj) { 
			$('#'+datagridId).treegrid('loadData',obj);
			$('#'+datagridId).treegrid("loaded");
		},
		error : function(arg0,arg1,arg2) {
			$('#'+datagridId).datagrid("loaded");
			switch(arg0.status)
			{
			  case 200:

                  alert("当前操作可能未授权操作，服务器未响应，请联系管理员进行处理，问题发生地址：" + url);
                  top.location.href = "../../login2.jsp";

                  break;
			  case 404:
				  alert("当前操作未授权！");
				  break;
			  case 500:
				  alert("程序内容处理错误：500");
				  break;
			  default:
				  alert("数据处理错误,错误代码："+arg0.status);
			      break; 
			} 
		}
	});
}

function findTreeData(url,datagridId,params) {
    url=getRootPath()+"/"+url;//增加项目的根地址
	$.ajax({
		type : 'post',
		url : url,
		dataType : 'application/json',
		data : params,
		success : function(obj) { 
			$('#'+datagridId).tree('loadData', $.parseJSON(obj.data));
		},
		error : function(arg0,arg1,arg2) {
			$('#'+datagridId).datagrid("loaded");
			switch(arg0.status)
			{
			  case 200:
				  top.location.href = "../../login2.jsp";
				  alert("服务器返回字符格式可能存在问题，请联系管理员进行处理，问题发生地址："+url);
				  
				  break;
			  case 404:
				  alert("当前操作未授权！");
				  break;
			  case 500:
				  alert("程序内容处理错误：500");
				  break;
			  default:
				  alert("数据处理错误,错误代码："+arg0.status);
			      break; 
			} 
		}
	});
}
/**********************************************
 * ajax数据处理  
 * params:参数对象
 * type:数据处理类别
 * callback:回调函数
 *********************************************/
function doData(url,params,callback) {
	url=getRootPath()+"/"+url;//增加项目的根地址
	$.ajax({
		type : 'post',
		url : url,
		dataType: 'json',
        contentType: "application/json; charset=utf-8",
        cache:false,
        data: JSON.stringify(params),
		success: function (data) { 
		    if (callback) callback(data);
		},
		error:function(arg0,arg1,arg2) {
			 	switch(arg0.status)
				{
				  case 1:
					  alert("当前操作的资源不存在，请联系管理员！"); 
					  break;
				  default:
					  alert("数据处理错误,错误代码："+arg0.status);
				      break; 
				} 			  
		}
	});
}
/**********************************************
 * ajax数据处理
 * params:参数对象
 * type:数据处理类别
 * callback:回调函数
 *********************************************/
function doDataParam(url,params,callback) {
    url=getRootPath()+"/"+url;//增加项目的根地址
    $.ajax({
        type : 'post',
        url : url,
        cache:false,
        data: {bussineid:params.bussineid,status:params.status},
        success: function (data) {
            if (callback) callback(data);
        },
        error : function(arg0,arg1,arg2) {
            switch(arg0.code)
            {
                case -1:
                    alert("当前操作可能未授权操作，服务器未响应，请联系管理员进行处理，问题发生地址：" + url);
                    break;
                case 1:
                    alert("当前操作的资源不存在，请联系管理员！");
                    break;
                default:
                    alert("数据处理错误,错误代码："+arg0.code);
                    break;
            }
        }
    });
}

/**********************************************
 * 功能说明：ajax文件上传处理技术
 * url地址
 * formId:表单的id
 * beforeCallback:处理之前需要做的处理
 * afterCallback:上传返回回调函数
 *********************************************/
function doUploadFile(url,formId,beforeCallback,afterCallback)
{
    $.ajax({
        url:getRootPath()+"/"+url,
        type: 'POST',
        cache: false,
        data: new FormData($('#'+formId)[0]),
        processData: false,
        contentType: false,
        dataType:"json",
        beforeSend: function(){
            if (beforeCallback!=undefined && beforeCallback!=null)
            	beforeCallback();
        },
        success : function(data) {
             if (afterCallback!=undefined && afterCallback!=null)
             	afterCallback(data);
        }
    });
}
/*导出Excel*/
function loadoutExcel(formId,params,url) { 
	$('#'+formId).attr("action", url+"?" + $.param(params));
	$('#'+formId).submit(); 
} 

function setPager(url,id,con,callback)
{
	var pager = $('#'+id).datagrid().datagrid('getPager'); // get the pager of datagrid
	
	pager.pagination({
		pageSize : 10,//每页显示的记录条数，默认为10 
		pageList : [ 10, 20, 30,40,50 ],//可以设置每页记录条数的列表 
		onSelectPage : function(pageNumber, pageSize) {

			findData(url,pageNumber, pageSize,id,con,callback);
		},
		onBeforeRefresh : function(pageNumber, pageSize) {
			findData(url,pageNumber, pageSize,id,con,callback);
		}
	});
 }


function openModalWin(url,width,height)
{
	var value=window.showModalDialog(url, "", "dialogWidth=" + width + "px;dialogHeight=" + height + "px");
	return value;
}
function openWin(url,width,height)
{ 
	var win=window.open(url,null,"height="+height+",width="+width+",status=no,toolbar=no,resizable=no,left=400,top=300");
	return win;
}

function openWinNoUrl(url) {
	window.open(url, null, "location=no,status=no,toolbar=no");

}
/*
关闭当前页面
 */
function closeWin()
{
	var result=confirm("您确定退出系统吗？");
	if (result==true)
	{
		window.opener=null;
		window.parent.close();
	}
}

/*
获取地址栏中参数的值
 */
function getURLParamValue(pName)
{
	var url=window.location.search;
	if(url.indexOf("?")!=-1)   
	{   
		var start=url.indexOf("?");
		var str=url.substr(start+1);   
		var strs = str.split("&");   
		for(var i=0;i<strs.length;i++)   
		{   
			if([strs[i].split("=")[0]]==pName) 
				return unescape(strs[i].split("=")[1]);
		}   
	}
	else
		return ""; 
}

/*********************人民币数字转大写开始******************/
function convertCurrency(currencyDigits) {
	// Constants:
	var MAXIMUM_NUMBER = 99999999999.99;
	// Predefine the radix characters and currency symbols for output:
	var CN_ZERO = "零";
	var CN_ONE = "壹";
	var CN_TWO = "贰";
	var CN_THREE = "叁";
	var CN_FOUR = "肆";
	var CN_FIVE = "伍";
	var CN_SIX = "陆";
	var CN_SEVEN = "柒";
	var CN_EIGHT = "捌";
	var CN_NINE = "玖";
	var CN_TEN = "拾";
	var CN_HUNDRED = "佰";
	var CN_THOUSAND = "仟";
	var CN_TEN_THOUSAND = "万";
	var CN_HUNDRED_MILLION = "亿";
	var CN_SYMBOL = "人民币";
	var CN_DOLLAR = "元";
	var CN_TEN_CENT = "角";
	var CN_CENT = "分";
	var CN_INTEGER = "整";
	// Variables:
	var integral; // Represent integral part of digit number.
	var decimal; // Represent decimal part of digit number.
	var outputCharacters; // The output result.
	var parts;
	var digits, radices, bigRadices, decimals;
	var zeroCount;
	var i, p, d;
	var quotient, modulus;
	// Validate input string:
	currencyDigits = currencyDigits.toString();
	if (currencyDigits == "") {
		alert("没有数据!");
		return "";
	}

	var moneySingal = ""; //货币符号

	if (Number(currencyDigits) < 0) {
		currencyDigits = currencyDigits.substring(1);
		moneySingal = "负";
	}
	if (currencyDigits.match(/[^,.\d]/) != null) {
		alert("提供的数据无效!");
		return "";
	}
	if ((currencyDigits).match(/^((\d{1,3}(,\d{3})*(.((\d{3},)*\d{1,3}))?)|(\d+(.\d+)?))$/) == null) {
		alert("数据格式不合法!");
		return "";
	}
	// Normalize the format of input digits:
	currencyDigits = currencyDigits.replace(/,/g, ""); // Remove comma delimiters.
	currencyDigits = currencyDigits.replace(/^0+/, ""); // Trim zeros at the beginning.
	// Assert the number is not greater than the maximum number.
	if (Number(currencyDigits) > MAXIMUM_NUMBER) {
		alert("太大数据无法转换!");
		return "";
	}
	// Process the coversion from currency digits to characters:
	// Separate integral and decimal parts before processing coversion:
	parts = currencyDigits.split(".");
	if (parts.length > 1) {
		integral = parts[0];
		decimal = parts[1];
		// Cut down redundant decimal digits that are after the second.
		decimal = decimal.substr(0, 2);
	}
	else {
		integral = parts[0];
		decimal = "";
	}
	// Prepare the characters corresponding to the digits:
	digits = new Array(CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE, CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE);
	radices = new Array("", CN_TEN, CN_HUNDRED, CN_THOUSAND);
	bigRadices = new Array("", CN_TEN_THOUSAND, CN_HUNDRED_MILLION);
	decimals = new Array(CN_TEN_CENT, CN_CENT);
	// Start processing:
	outputCharacters = "";
	// Process integral part if it is larger than 0:
	if (Number(integral) > 0) {
		zeroCount = 0;
		for (i = 0; i < integral.length; i++) {
			p = integral.length - i - 1;
			d = integral.substr(i, 1);
			quotient = p / 4;
			modulus = p % 4;
			if (d == "0") {
				zeroCount++;
			}
			else {
				if (zeroCount > 0) {
					outputCharacters += digits[0];
				}
				zeroCount = 0;
				outputCharacters += digits[Number(d)] + radices[modulus];
			}
			if (modulus == 0 && zeroCount < 4) {
				outputCharacters += bigRadices[quotient];
			}
		}
		outputCharacters += CN_DOLLAR;
	}
	// Process decimal part if there is:
	if (decimal != "") {
		for (i = 0; i < decimal.length; i++) {
			d = decimal.substr(i, 1);
			if (d != "0") {
				outputCharacters += digits[Number(d)] + decimals[i];
			}
		}
	}
	// Confirm and return the final output string:
	if (outputCharacters == "") {
		outputCharacters = CN_ZERO + CN_DOLLAR;
	}
	if (decimal == "") {
		outputCharacters += CN_INTEGER;
	}
	outputCharacters = outputCharacters;
	return moneySingal+outputCharacters;
}
/*********************人民币转大写结束***********************/

/***********************判读字符串是否日期开始*********************************/
function IsDate(str) {
	try {
		if (str.indexOf('/') >= 0) {
			str = str.split(' ')[0];
			var strDate = str.split('/');
			if (strDate[2].length != 4 || strDate[0] * 1 > 12 || strDate[0] * 1 <= 0 || strDate[1] * 1 > 31 || strDate[1] * 1 <= 0) return false;
			var d = new Date(strDate[2], strDate[0] * 1 - 1, strDate[1]);

			return true;
		}
		if (str.indexOf('-') >= 0) {
			str = str.split(' ')[0];
			var strDate = str.split('-');
			if (strDate[0].length != 4 || strDate[1] * 1 > 12 || strDate[1] * 1 <= 0 || strDate[2] * 1 > 31 || strDate[2] * 1 <= 0) return false;

			var d = new Date(strDate[0], strDate[1] * 1 - 1, strDate[2]);
			return true;
		}
		return false;
	} catch (exception) {
		return false;
	}
}
/***********************判读字符串是否日期结束*********************************/

/***********************过滤json字符串开始*********************************/
function stringToJson(v) {
	if (v.indexOf("\"") != -1) {
	v = v.toString().replace(new RegExp('(["\"])', 'g'), "\\\"");
	}
	else if (v.indexOf("\\") != -1)
	v = v.toString().replace(new RegExp("([\\\\])", 'g'), "\\\\");
	return v;
} 
/***********************过滤json字符串结束*********************************/
function addTabsToWorkFrame(title,src)
{
	if (parent.$('#tWork').tabs('exists', title)) {
		parent.$('#tWork').tabs('select', title);
	} else {
		var content = '<iframe scrolling="auto" frameborder="0"  src="'
				+ src + '" style="width:100%;height:98%;"></iframe>';
		parent.$('#tWork').tabs('add', {
			title : title,
			content : content,
			closable : true,
			fit:true
		});
	}
}
/*将序列化转JSON对象*/
function formObject(formId) {
    var inputs = $("#" + formId).find("input");
    if (inputs == null || inputs.length == 0) return null;

    var obj = new Object();
    for (var i = 0; i < inputs.length; i++)
    {
        
        eval("obj." + $(inputs[i]).attr("name") + "=" + $(inputs[i]).val());
    }

    return obj;
}
/*将$(form).serializeArray()转对象*/
function serializeArrayToObject(formId) {
    var test = $("#"+formId).serializeArray();
    var temp = new Object();

    for (var i = 0; i < test.length; i++) {
        var arr=test[i].name.split(".");//对名称进行分解,为处理属性是对象打下基础
        var objName="temp";
        for(var j=0;j<arr.length-1;j++)
        {
            eval("temp." + arr[j] + "={}");//当前属性是对象，进行对象实例化
            objName=objName+"."+arr[i];
        }
        //temp[test[i].name]=stringToJson(test[i].value);

        eval("("+"temp." + test[i].name + "='" + stringToJson(test[i].value) + "'"+")");

    }

    return temp;
}

Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
        // millisecond
    }
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}
function formatDatebox(value) {
    if (value == null || value == '') {
        return '';
    }
    var dt;
    if (value instanceof Date) {
        dt = value;
    } else {
        dt = new Date(value);
    }

    return dt.format("yyyy-MM-dd"); //扩展的Date的format方法(上述插件实现)
}
/*
* uuix时间转换
* */
function formatUuix(value){
    if (value == null || value == '') {
        return '';
    }
    return new Date(parseInt(value) * 1000).toLocaleString();
}

/*布尔格式转换*/
function formateBool(value) {
	if (value)
		return "是";
	else
		return "否";
}
function parserDatebox(value){
    if (!value) return new Date();
    var ss = (value.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}

/*************************************************
 * 将数值型的日期转换成长日期格式，
 * 例如：1495977369000；YYYY-MM-DD  hh:mm:ss
 * 返回：字符串值
 *************************************************/
Date.prototype.getLongDate=function(value){
    var d=new Date(value);
    return d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getMinutes()+":"+d.getMilliseconds();
};
/*************************************************
 * 将数值型的日期转换成短日期格式，
 * 例如：1495977369000；YYYY-MM-DD
 * 返回：字符串值
 *************************************************/
Date.prototype.getShortDate=function(value){
    var d=new Date(value);
    return d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
};

/*项目操作常规代码*/
function returnCodeUI(data,callback) {
	switch (data.result)
	{
		case 0:
			alert("您没有提供满足操作的参数！");
			break;
		case 1:
        case 2:
        case 3:
        case 4:
			alert("操作成功！")
			break;
		case -1:
			alert("数据库操作异常，建议联系管理员解决！")
			break;
		case 10000:
			alert("当前操作的数据已经存在！");
			break;
		default:
			alert("操作失败，错误代码："+data.result+",请联系管理员解决！");
			break;
	}
	callback(data.result);//回调
}

/*获取网站的项目根地址*/
function getRootPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}

/*select数据绑定,每个绑定项的value的属性名为valueField,显示属性为nameField*/
function bindSelectData(domID,dataSource,valueField,nameField,callback)
{

	if (dataSource!=undefined && dataSource!=null && dataSource.length>0)
	{
        $("#"+domID+" option").remove();//清空原有选项
        for(var i=0;i<dataSource.length;i++)
        {
            $("#"+domID).append("<option value='"+eval("dataSource[i]."+valueField)+"'>"+eval("dataSource[i]."+nameField)+"</option>");
        }
        if (callback!=undefined && callback!=null)
		{
			callback(dataSource);
		}
	}
}

//自动填充form
function SetWebControls(data) {
    for (var key in data) {
        var id = $('#' + key);
        var value = $.trim(data[key]).replace(" ", "");
        //var value = data[key];
        var type = id.attr('type');
        switch (type) {
            case "checkbox":
                if (value == 1) {
                    id.attr("checked", 'checked');
                } else {
                    id.removeAttr("checked");
                }
                break;
            default:
                id.val(value);
                break;
        }
    }
}