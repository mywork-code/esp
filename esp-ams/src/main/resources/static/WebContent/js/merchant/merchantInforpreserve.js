$(function(){
	//加载页面信息
	load();
	
	//确认  提交商户信息
	$("#submitMerchant").click(function(){
		$.messager.confirm("<span style='color: black;'>操作提示</span>", "您确定要提交吗？", function (r) {  
            if (r) { 
            	saveorsubmit('0');
            	//merchantReadonly()
            }	
        });  
	});
	//确认  保存商户信息
	$("#saveMerchant").click(function(){
		saveorsubmit(null);
	});
	
	//刷新当前页面
	$("#flush").click(function(){
		load();
	});
	
	//邮编校验
	$("input",$("#editMerchantPostcode").next("span")).blur(function(){  
	    debugger; 
		var postCode = $("#editMerchantPostcode").textbox('getValue');
		if(!regExp_post(postCode)){
			$.messager.alert("<span style='color: black;'>提示</span>","邮政编码只能是6位数字！",'info');
			$("#editMerchantPostcode").textbox('setValue','');
			return;
		}
	})  
	
});

//加载省市区
function loadDirect(provinceId, cityId, areaId) {
    $('#' + provinceId).combobox({
        method: "get",
        url: ctx + "/application/nation/v1/queryNations",
        valueField: 'code',
        textField: 'province',
        onSelect: function (data) {
            $("#" + cityId).textbox('setValue', '');
            $("#" + areaId).combobox('loadData', {});
            $("#" + areaId).textbox('setValue', '');
            $('#' + cityId).combobox({
                method: "get",
                url: ctx + "/application/nation/v1/queryNations?districtCode=" + data.code,
                valueField: 'code',
                textField: 'city',
                onSelect: function (data) {
                    $("#" + areaId).combobox('loadData', {});
                    $('#' + areaId).combobox({
                        method: "get",
                        url: ctx + "/application/nation/v1/queryNations?districtCode=" + data.code,
                        valueField: 'code',
                        textField: 'district',
                        onSelect: function (data) {
                           
                        }
                    });
                }
            });
        }
    });

}

//根据商户code查询商户信息
function load(){
	debugger;
	//监听事件,当页面省(直辖市)回显时加载对应二级区域
	$("#editMerchantProvince").combobox({
		onChange: function () {
			var code=$("#editMerchantProvince").combobox('getValue');
			$('#editMerchantCity').combobox({
				method:"get",
				url:ctx + "/application/nation/v1/queryNations?districtCode="+code,
			    valueField:'code',
			    textField:'city'
			});
		}
	});
	$("#editMerchantCity").combobox({
		onChange: function () {
			var code=$("#editMerchantCity").combobox('getValue');
			$('#editMerchantArea').combobox({
				method:"get",
				url:ctx + "/application/nation/v1/queryNations?districtCode=" + code,
				valueField:'code',
				textField:'district'
			});
		}
	});
	
	//加载省份 和城市
	loadDirect("editMerchantProvince", "editMerchantCity", "editMerchantArea");
	//回显页面信息
	$.get( ctx + "/application/merchantinfor/merchant/queryByMerchantcode",function(datas){
		$.validateResponse(datas, function() {
			$(".search-btn").click();
		});
		var data = datas.data;
		$("#merchantId").val(data.id);
        $("#channel").combobox('setValue',data.channel);
		$("#editMerchantCode").textbox('setValue',data.merchantCode);
		$("#editMerchantName").textbox('setValue',data.merchantName);
		$("#editMerchantProvince").combobox('setValue',data.merchantProvince);
		$("#editMerchantCity").combobox('setValue',data.merchantCity);
        $("#editMerchantArea").combobox('setValue',data.merchantArea);
		$("#editMerchantAddress").textbox('setValue',data.merchantAddress);
		$("#editMerchantReturnAddress").textbox('setValue',data.merchantReturnAddress);
		$("#editMerchantReturnName").textbox('setValue',data.merchantReturnName);
		$("#editMerchantReturnPhone").textbox('setValue',data.merchantReturnPhone);
		$("#editMerchantReturnPostCode").textbox('setValue',data.merchantReturnPostCode);
		$("#editMerchantPostcode").textbox('setValue',data.merchantPostcode);
		$("#editMerchantType").combobox('setValue',data.merchantType);
		$("#editMerchantNickname").textbox('setValue',data.merchantNickname);
		$("#editMerchantSettlementDate").combobox('setValue',data.merchantSettlementDate);
		$("#editSettlementBankName").textbox('setValue',data.settlementBankName);
		$("#editSettlementCardNo").textbox('setValue',data.settlementCardNo);
		$("#editManageType").textbox('setValue',data.manageType);
		$("#editOrgCode").textbox('setValue',data.orgCode);
		$("#message").textbox('setValue',data.remark);
		$("#editIsContainFreight").combobox('setValue',data.isContainFreight);
		$("#editMerchantStatu").combobox('setValue',data.status);
		
		if(data.status=="1"){
			$("#agreeSubmit").css('display','block'); 
			$("#saveIdSpan").css('display','block'); 
		}else{
			$("#agreeSubmit").css('display','none');
			$("#saveIdSpan").css('display','none');
		}
		
		$("#editMerchantStatu  ~ span input").css("color","red");
		
	});
}

function saveorsubmit(statu){
	var id=$("#merchantId").val();
	if(null==id || ("")==id){
		$.messager.alert("<span style='color: black;'>提示</span>","商户信息编辑失败,请重新编辑！",'info'); 
//		$("#editMerchantInfor").window('close');
		return;
	}
	var editMerchantReturnPostCode = $("#editMerchantReturnPostCode").textbox('getValue'); 
	if((0!= editMerchantReturnPostCode.length||"" != editMerchantReturnPostCode)){
		if(!/^\d{6}$/.test(editMerchantReturnPostCode)){
			$.messager.alert("<span style='color: black;'>提示</span>","收货邮编输入错误!",'info');  
			return;
		}
	}
    var channel = $("#channel").combobox('getValue');
//    if (null == channel || ("") == channel) {
//        $.messager.alert("<span style='color: black;'>提示</span>","商户渠道不能为空!",'info');
//        return;
//    }

	var merchantCode = $("#editMerchantCode").textbox('getValue');
	if (null == merchantCode || ("") == merchantCode) {
		$.messager.alert("<span style='color: black;'>提示</span>","商户编码不能为空!",'info');
		return;
	}
	var merchantName = $("#editMerchantName").textbox('getValue');
	if (null == merchantName || ("") == merchantName) {
		$.messager.alert("<span style='color: black;'>提示</span>","商户名称不能为空!",'info');
		return;
	}
	var isContainFreight = $("#editIsContainFreight").combobox('getValue');
	if (null == isContainFreight || ("") == isContainFreight) {
		$.messager.alert("<span style='color: black;'>提示</span>","是否含运费不能为空！",'info');
		return;
	}
	var merchantSettlementDate = $("#editMerchantSettlementDate").combobox('getValue');
	if (null == merchantSettlementDate || ("") == merchantSettlementDate) {
		$.messager.alert("<span style='color: black;'>提示</span>","结算日期不能为空！",'info');
		return;
	}
	var merchantProvince = $("#editMerchantProvince").combobox('getValue');
	if (null == merchantProvince || ("") == merchantProvince) {
		$.messager.alert("<span style='color: black;'>提示</span>" ,"所在省份不能为空！",'info'); 
		return;
	}
	var merchantCity = $("#editMerchantCity").combobox('getValue');
	if (null == merchantCity || ("") == merchantCity) {
		$.messager.alert("<span style='color: black;'>提示</span>","所在城市不能为空!",'info');
		return;
	}

    var merchantArea = $("#editMerchantArea").combobox('getValue');
    if (null == merchantArea || ("") == merchantArea) {
		alert("所在城市不能为空！");
        $.messager.alert("<span style='color: black;'>提示</span>","所在区域不能为空!",'info');
        return;
    }

	var merchantAddress = $("#editMerchantAddress").textbox('getValue');
	if (null == merchantAddress || ("") == merchantAddress) {
		$.messager.alert("<span style='color: black;'>提示</span>","详细地址不能为空！",'info'); 
		return;
	}
	var merchantPostcode = $("#editMerchantPostcode").textbox('getValue');
	if (null == merchantPostcode || ("") == merchantPostcode) {
		$.messager.alert("<span style='color: black;'>提示</span>","邮政编码不能为空！",'info'); 
		return;
	}
	var merchantType = $("#editMerchantType").combobox('getValue');
	if (null == merchantType || ("") == merchantType) {
		$.messager.alert("<span style='color: black;'>提示</span>","商户类型不能为空！",'info');
		return;
	}
	var merchantNickname = $("#editMerchantNickname").textbox('getValue');
	if (null == merchantNickname || ("") == merchantNickname) {
		$.messager.alert("<span style='color: black;'>提示</span>","商户昵称不能为空！",'info'); 
		return;
	}

	var settlementBankName = $("#editSettlementBankName").textbox('getValue');
	if (null == settlementBankName || ("") == settlementBankName) {
		$.messager.alert("<span style='color: black;'>提示</span>","结算银行名称不能为空！",'info');
		return;
	}
	var settlementCardNo = $("#editSettlementCardNo").textbox('getValue');
	if (null == settlementCardNo || ("") == settlementCardNo) {
		$.messager.alert("<span style='color: black;'>提示</span>","结算银行卡号不能为空！",'info');
		return;
	}
	if(settlementCardNo.length>18){   //长度可自定义
    	$.messager.alert("<span style='color: black;'>警告</span>","银行卡号长度不能超过18个字！",'warning');
    	return;
    }
	
	
	var manageType = $("#editManageType").textbox('getValue');
	if (null == manageType || ("") == manageType) {
		$.messager.alert("<span style='color: black;'>提示</span>","经营类型不能为空！",'info');
		return;   
	}
	debugger;
	var status = $("#editMerchantStatu").combobox('getValue');
	
	var orgCode = $("#editOrgCode").textbox('getValue');
	if (null == orgCode || ("") == orgCode) {
		$.messager.alert("<span style='color: black;'>提示</span>","企业机构代码不能为空！",'info');
		return;
	}
	var merchantReturnAddress = $("#editMerchantReturnAddress").textbox('getValue');
	if (null == merchantReturnAddress || ("") == merchantReturnAddress) {
		$.messager.alert("<span style='color: black;'>提示</span>","退货地址不能为空！",'info'); 
		return;
	}
	if(merchantReturnAddress.length>40){ 
    	$.messager.alert("<span style='color: black;'>警告</span>","退货地址长度不能超过40！",'warning');
    	return;
    }
	if(!is_forbid(merchantReturnAddress)){
    	$.messager.alert("<span style='color: black;'>警告</span>","商户退货地址含有非法字符！",'warning');
		return;
	}
	
	var merchantReturnName = $("#editMerchantReturnName").textbox('getValue');
	if (null == merchantReturnName || ("") == merchantReturnName) {
		$.messager.alert("<span style='color: black;'>提示</span>","收货人姓名不能为空！",'info'); 
		return;
	}
	if(merchantReturnName.length>6){ 
    	$.messager.alert("<span style='color: black;'>警告</span>","收货人姓名长度不能超过6！",'warning');
    	return;
    }
	var merchantReturnPhone = $("#editMerchantReturnPhone").textbox('getValue');
	if (null == merchantReturnPhone || ("") == merchantReturnPhone) {
		$.messager.alert("<span style='color: black;'>提示</span>","收货联系电话不能为空！",'info'); 
		return;
	}
   var r=/^[0-9-]*$/;//数字和横杆
   if(!r.test(merchantReturnPhone)){
   	  $.messager.alert("<span style='color: black;'>提示</span>", "收货人联系电话只能填写数字和-", 'info');
         return;
   }
//	if(merchantReturnPhone.length>11){ 
//    	$.messager.alert("<span style='color: black;'>警告</span>","收货人联系电话长度不能超过11！",'warning');
//    	return;
//    }
//	var merchantReturnPhoneFalge=/^[1][34578][0-9]{9}$/.test(merchantReturnPhone);
//	if(!merchantReturnPhoneFalge){
//		$.messager.alert("<span style='color: black;'>警告</span>","收货人联系电话填写错误！",'warning');
//    	return;
//	}
	var merchantReturnPostCode = $("#editMerchantReturnPostCode").textbox('getValue');

	debugger;
	var remark = $("#message").textbox('getValue');
    
	var params={};
	params['id']=id;
    params['channel']=channel;
	params['merchantCode']=merchantCode;
	params['merchantName']=merchantName;
	params['merchantProvince']=merchantProvince;
	params['merchantCity']=merchantCity;
    params['merchantArea']=merchantArea;
	params['merchantAddress']=merchantAddress;
	params['merchantReturnAddress']=merchantReturnAddress;
	params['merchantReturnName']=merchantReturnName;
	params['merchantReturnPhone']=merchantReturnPhone;
	params['merchantReturnPostCode']=merchantReturnPostCode;
	params['merchantPostcode']=merchantPostcode;
	params['merchantType']=merchantType;
	params['merchantNickname']=merchantNickname;
	params['merchantSettlementDate']=merchantSettlementDate;
	params['settlementBankName']=settlementBankName;
	params['settlementCardNo']=settlementCardNo;
	params['manageType']=manageType;
	params['isContainFreight']=isContainFreight;
	params['orgCode']=orgCode;
	params['status']=statu;
	params['remark']=remark;
	
	$.ajax({
        url : ctx + '/application/merchantinfor/merchant/editTemp',
        data : params,
        type : "post",
        dataType : "json",
        success : function(data) {
        	debugger;
        	if(data.status=="1"){
        		$.messager.alert("<span style='color: black;'>提示</span>",data.msg,'info'); 
        		load();
        	}else{
        		$.messager.alert("<span style='color: black;'>提示</span>",data.msg,'info');
        		return;
        	}
        }
	});
	
}

//控制邮编格式
function regExp_post(str){  
   //正则对象的标准声明方式，RegExp  
   var pattern=new RegExp(/^\d{6}$/);  //只能输入6位数字
   return pattern.test(str);  
} 

//设置各输入框为只读
function merchantReadonly(){
	debugger;
	$("#editIsContainFreight").combobox({disabled: true});
	$("#editMerchantSettlementDate").combobox({disabled: true});
	$("#editMerchantProvince").combobox({disabled: true});
	$("#editMerchantCity").combobox({disabled: true});
	$('#editMerchantAddress').textbox('textbox').attr('readonly',true);
	$('#editMerchantReturnAddress').textbox('textbox').attr('readonly',true);
	$('#editMerchantReturnName').textbox('textbox').attr('readonly',true);
	$('#editMerchantReturnPhone').textbox('textbox').attr('readonly',true);
	$('#editMerchantReturnPostCode').textbox('textbox').attr('readonly',true);
	$('#editMerchantPostcode').textbox('textbox').attr('readonly',true);
	$("#editMerchantType").combobox({disabled: true});
	$('#editMerchantNickname').textbox('textbox').attr('readonly',true);
	$('#editSettlementBankName').textbox('textbox').attr('readonly',true);
	$('#editSettlementCardNo').textbox('textbox').attr('readonly',true);
	$('#editManageType').textbox('textbox').attr('readonly',true);
	$('#editOrgCode').textbox('textbox').attr('readonly',true);
}

