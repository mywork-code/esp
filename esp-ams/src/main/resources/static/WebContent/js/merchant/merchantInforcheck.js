$(function(){
	$("#editMerchantInfor").window('close');
	//Grid 列表
	$("#merchantInforList").datagrid({
        title : '商户信息',
        fit : true,
        fitColumns : true,
        rownumbers : true,
        pagination : true,
        singleSelect: false, //允许选择多行 
        selectOnCheck: true,
        checkOnSelect: false,
        striped:true,
        toolbar : '#tb',
        columns : [[
        { field: 'ck', checkbox: true, width: '30'},  //复选框 
        {
            title : '商户编码',
            field : 'merchantCode',
            width : 150,
            align : 'center'
        }, {
            title : '商户名称',
            field : 'merchantName',
            width : 150,
            align : 'center'
        }, {
            title : '商户昵称',
            field : 'merchantNickname',
            width : 150,
            align : 'center'
        },{
            title : '商户类型',
            field : 'merchantType',
            width : 150,
            align : 'center',
            formatter:function(value,row,index){
            	if(value=="1"){
            		return "个人";
            	}else if(value=="2"){
            		return "企业";
            	}
            }
        },{
            title : '结算日期',
            field : 'merchantSettlementDate',
            width : 150,
            align : 'center'
        },{
            title : '结算银行名称',
            field : 'settlementBankName',
            width : 120,
            align : 'center'
        },{
            title : '商户状态',
            field : 'status',
            width : 150,
            align : 'center',
            formatter:function(value,row,index){
            	if(value=="1"){
            		return "正常";
            	}else if(value=="-1"){
            		return "无效";
            	}else if(value=="0"){
            		return "待审核";
            	}
            }
        },{
            title : '操作',
            field : 'opt',
            width : 150,
            align : 'center',
            formatter : function(value, row, index) {
            	// 授权标示
            	var grantedAuthority=$('#grantedAuthority').val();
            	var content = "";
            	if(row.status=="0"){
            		if(grantedAuthority=='permission'){
            	    content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.checkOneMerchant('"
						+ row.merchantCode+"');\">审核</a>&nbsp;&nbsp;";
            		}
            	    content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.showMerchant('"
            	    	+ encodeURI(JSON.stringify(row) )+"');\">预览</a>&nbsp;&nbsp;";
            	}
                return content;
            },
        }]],
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + '/application/merchantinfor/merchant/queryCheck',
                data : param,
                type : "post",
                dataType : "json",
                success : function(data) {
                	debugger;
                	console.log(data);
                    $.validateResponse(data, function() {
                    	  success(data);
                    });
                }
            })
        }
	});
	//多个审核
	$("#checkAll").click(function(){
		debugger;
	    var selRow = $('#merchantInforList').datagrid('getChecked');  
		if(selRow.length==0){  
			$.messager.alert("<span style='color: black;'>提示</span>", "至少勾选一条数据！", "info");  
			return ;  
		}else{
			var merchantCodes=[];  
			for (var i = 0; i < selRow.length; i++) {
				var merchantCode=selRow[i].merchantCode;     
				merchantCodes.push(merchantCode); 
		      }  
			checkMerchants(merchantCodes,"<font color='black'>商品批量审核</font>");
		}
	});
	
	//单个审核
	$.checkOneMerchant = function(merchantCode) {
		debugger;
		var arr = new Array();
		arr[0]=merchantCode;
		checkMerchants(arr,"<font color='black'>商户单个审核</font>");
    }
	
	$("#checkOpinion").switchbutton({
        onText : '审核通过', //true
        offText : '审核驳回' //false
	});
	
	//审核
	function checkMerchants(merchantCodes,msgss){
		  var handlesubmit = function() {
		        var pass = $("#checkOpinion").switchbutton("options").checked;//false:off true:on
		        
		        $.ajax({
					type : "POST",
					url : ctx + '/application/merchantinfor/merchant/checkview',					
					data : {
			                "merchantCodes" : JSON.stringify(merchantCodes),
			                "flag" : pass ? "pass" : "reject",
			                "message" : $("#message").val()
			            },
					success : function(data) {
						debugger;
						//merchantReadonlyFalse();
						console.log(data);
		            	$("#checkMerchant").dialog("close");
		            	$.messager.alert("<span style='color: black;'>提示</span>", data.msg, "info");  
	                    $(".search-btn").click();
					}
				});
		    }
		  
		  //清缓存
		  $("#message").textbox('clear');
	      $("#checkOpinion").switchbutton("uncheck");
	      
		   $("#checkMerchant").dialog({
            modal : true,
            title : msgss,
            width : 500,
            height : 250,
            buttons : [{
                        text : "确定",
                        handler : function() {
                        	var pass = $("#checkOpinion").switchbutton("options").checked;
                        	var flags = pass ? "pass" : "reject";
                        	if(flags=='reject'){
                        		if($("#message").val()=='' || $("#message").val()==null){
                            		$.messager.alert("<span style='color: black;'>提示</span>", "请填写驳回理由！", "info");  
                            		return;
                            	} 	
                        	}
                        	if($("#message").textbox('getValue').length>100){
                        		$.messager.alert("<span style='color: black;'>提示</span>", "审核理由不允许超过100字！", "info");  
                        		return;
                    		}
                        	handlesubmit();
                         }
                    	}, {
                        text : "关闭",
                        handler : function() {
                            $("#checkMerchant").dialog("close");
                        }
                    }]
	        });
	}
	//监听事件,当页面省(直辖市)回显时加载对应二级区域
	$("#editMerchantProvince").combobox({
		onChange: function () {
			debugger;
			var code=$("#editMerchantProvince").combobox('getValue');
			$('#editMerchantCity').combobox({
				method:"get",
				url:ctx + "/application/nation/queryNations?districtCode="+code,
			    valueField:'code',
			    textField:'name'
			});
		}
	});
	
	
	//预览
	$.showMerchant = function(row) {
		debugger;
		//加载省份 和城市
		loadDirect("editMerchantProvince","editMerchantCity");
		//string转json对象
		var row3=JSON.parse(decodeURI(row));
		console.log(row3);
		//回显编辑弹出框中的数据
		$("#merchantId").val(row3.id);
		$("#editMerchantCode").textbox('setValue',row3.merchantCode);
		$("#editMerchantName").textbox('setValue',row3.merchantName);
		$("#editIsContainFreight").combobox('setValue',row3.isContainFreight);
		if(null ==row3.merchantSettlementDate || ''==row3.merchantSettlementDate){
			$("#editMerchantSettlementDate").combobox('setValue','');
		}else{
			$("#editMerchantSettlementDate").combobox('setValue',row3.merchantSettlementDate);
		}
		$("#editMerchantProvince").combobox('setValue',row3.merchantProvince);
		$("#editMerchantCity").combobox('setValue',row3.merchantCity);
		$("#editMerchantAddress").textbox('setValue',row3.merchantAddress);
		$("#editMerchantReturnAddress").textbox('setValue',row3.merchantReturnAddress);
		$("#editMerchantReturnName").textbox('setValue',row3.merchantReturnName);
		$("#editMerchantReturnPhone").textbox('setValue',row3.merchantReturnPhone);
		$("#editMerchantReturnPostCode").textbox('setValue',row3.merchantReturnPostCode);
		$("#editMerchantPostcode").textbox('setValue',row3.merchantPostcode);
		//alert(row3.merchantType);
		$("#editMerchantType").combobox('setValue',row3.merchantType);
		$("#editMerchantNickname").textbox('setValue',row3.merchantNickname);
		$("#editSettlementBankName").textbox('setValue',row3.settlementBankName);
		$("#editSettlementCardNo").textbox('setValue',row3.settlementCardNo);
		$("#editManageType").textbox('setValue',row3.manageType);
		$("#editOrgCode").textbox('setValue',row3.orgCode);
		debugger;
		console.log(row3);
		$("#editMerchantStatu").combobox('setValue',row3.status)
		$("#message1").textbox('setValue',row3.remark);
		
		
		//打开编辑弹出框
		$('#editMerchantInfor').window({
            shadow: true,
            minimizable:false,
            maximizable:false,
            collapsible:false,
            modal:true 
		});
		
		$('#editMerchantInfor').window('open');
	}
	
	
	
	
	//刷新
	$("#flush").click(function(){
		$("#merchantName").textbox('setValue','');
		$("#merchantType").combobox('setValue','');
		var params = {};
		$('#merchantInforList').datagrid('load', params);
	});
	//查询
	$(".search-btn").click(function(){
		var params = {};
		params['merchantName'] = $("#merchantName").textbox('getValue');
		params['merchantType'] = $("#merchantType").combobox('getValue');
		
		$('#merchantInforList').datagrid('load', params);
	});
	
});

function loadDirect(provinceId,cityId){
	$('#'+provinceId).combobox({
		method:"get",
	    url:ctx + "/application/nation/queryNations",
	    valueField:'code',
	    textField:'name',
   	    onSelect:function(data){
	    	console.log(data);
			$('#'+cityId).combobox({
			    //url:ctx + "/application/merchantinfor/merchant/queryCity?provinceId="+data.code,
				method:"get",
				url:ctx + "/application/nation/queryNations?districtCode="+data.code,
			    valueField:'code',
			    textField:'name'
			});
	    }
		
	});
}

//设置只读为false
function merchantReadonlyFalse(){
	debugger;
	$("#editIsContainFreight").combobox({disabled: false});
	$("#editMerchantSettlementDate").combobox({disabled: false});
	$("#editMerchantProvince").combobox({disabled: false});
	$("#editMerchantCity").combobox({disabled: false});
	$('#editMerchantAddress').textbox('textbox').attr('readonly',false);
	$('#editMerchantReturnAddress').textbox('textbox').attr('readonly',false);
	$('#editMerchantReturnName').textbox('textbox').attr('readonly',false);
	$('#editMerchantReturnPhone').textbox('textbox').attr('readonly',false);
	$('#editMerchantReturnPostCode').textbox('textbox').attr('readonly',false);
	$('#editMerchantPostcode').textbox('textbox').attr('readonly',false);
	$("#editMerchantType").combobox({disabled: false});
	$('#editMerchantNickname').textbox('textbox').attr('readonly',false);
	$('#editSettlementBankName').textbox('textbox').attr('readonly',false);
	$('#editSettlementCardNo').textbox('textbox').attr('readonly',false);
	$('#editManageType').textbox('textbox').attr('readonly',false);
	$('#editOrgCode').textbox('textbox').attr('readonly',false);
}

