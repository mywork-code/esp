/**
 * Data - extence
 */

//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { //author: meizz 
	 var o = {
	     "M+": this.getMonth() + 1, //月份 
	     "d+": this.getDate(), //日 
	     "h+": this.getHours(), //小时 
	     "m+": this.getMinutes(), //分 
	     "s+": this.getSeconds(), //秒 
	     "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	     "S": this.getMilliseconds() //毫秒 
	 };
	 if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	 for (var k in o)
	 if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	 return fmt;
}

/**
 * 检查是否含有非法字符
 * @param temp_str
 * @returns {Boolean}
 */
function is_forbid(temp_str){
    temp_str = temp_str.replace(/(^\s*)|(\s*$)/g, "");
//	temp_str = temp_str.replace('--',"@");
	temp_str = temp_str.replace('/',"@");
	temp_str = temp_str.replace('+',"@");
	temp_str = temp_str.replace('\'',"@");
	temp_str = temp_str.replace('\\',"@");
	temp_str = temp_str.replace('$',"@");
	temp_str = temp_str.replace('^',"@");
//	temp_str = temp_str.replace('.',"@");
	temp_str = temp_str.replace(';',"@");
	temp_str = temp_str.replace('<',"@");
	temp_str = temp_str.replace('>',"@");
	temp_str = temp_str.replace('"',"@");
	temp_str = temp_str.replace('=',"@");
	temp_str = temp_str.replace('{',"@");
	temp_str = temp_str.replace('}',"@");
	temp_str = temp_str.replace('!',"@");
	var forbid_str = new String('@,%,~,&');
	var forbid_array = new Array();
	forbid_array = forbid_str.split(',');
	for(i=0;i<forbid_array.length;i++){
		if(temp_str.search(new RegExp(forbid_array[i])) != -1)
		return false;
	}
	return true;
}

//控制保留几位有效小数的js函数
//ValueString：’数字字符串’  nAfterDotNum：保留小数位数
function FormatAfterDotNumber( ValueString, nAfterDotNum ){
  var resultStr,nTen;
  ValueString = ""+ValueString+"";
  strLen = ValueString.length;
  dotPos = ValueString.indexOf(".",0);
  if (dotPos == -1) {
		resultStr = ValueString+".";
		for (i=0;i<nAfterDotNum ;i++){
		resultStr = resultStr+"0";
      }
	   return resultStr;
	}else {
		if ((strLen - dotPos - 1) >= nAfterDotNum ){
	 		nAfter = dotPos + nAfterDotNum  + 1;
	  		nTen =1;
		    for(j=0;j<nAfterDotNum ;j++){
		    	nTen = nTen*10;
		    }
		    resultStr = Math.round(parseFloat(ValueString)*nTen)/nTen;
		    return resultStr;
		 } else{
		     resultStr = ValueString;
		     for (i=0;i<(nAfterDotNum  - strLen + dotPos + 1);i++){
		    	 resultStr = resultStr+"0";
			     }
			     return resultStr;
			 }
	}
} 

