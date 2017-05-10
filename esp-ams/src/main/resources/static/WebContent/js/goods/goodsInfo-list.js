/**
 * GOODS - info
 */
$(function() {
	//初始化
	$('#addGoodsInfo').window('close');
	$('#editGoodsInfo').window('close');
	$('#editorReport').window('close');
	$('#goodsStockInfo').window('close');
	$('#addGoodsStockInfoWin').window('close');
	$('#editGoodsStockInfoWin').window('close');
	$('#addAmtGoodsStockInfoWin').window('close');
	$('#bannerPicUpWin').window('close');
	$('#previewBannerWin').window('close');
	$('#previewProductWin').window('close');
	
    $('#tablelist').datagrid({
        title : '商品列表',
        rownumbers : true,
        pagination : true,
        striped:true,
//        fit:true,
//        fitColumns:true,
        toolbar : '#tb',
//        rowStyler:function(rowIndex,rowData){
//        	if(rowData.colFalgt=='1'){
//        		return 'background-color:#6293BB;';
//        	}
//        },
        columns : [[
                {
                    title : '商户名称',
                    field : 'merchantName',
                    width : 90,
                    align : 'center'
                },
                {
                    title : '商品名称',
                    field : 'goodsName',
                    width : 90,
                    align : 'center'
                }, {
                    title : '商品型号',
                    field : 'goodsModel',
                    width : 80,
                    align : 'center'
                }, {
                    title : '商品小标题',
                    field : 'goodsTitle',
                    width : 90,
                    align : 'center'
                }, {
                    title : '商品类型',
                    field : 'goodsTypeDesc',
                    width : 80,
                    align : 'center',
                },{
                    title : '规格类型',
                    field : 'goodsSkuType',
                    width : 80,
                    align : 'center'
                },{
                    title : '商品生产日期',
                    field : 'proDate',
                    width : 120,
                    align : 'center',
                    formatter:function(value,row,index){
                    	if(null!=value && ""!=value){
                    		return new Date(value).Format("yyyy-MM-dd");
                    	}
                    }
                },{
                    title : '保质期',
                    field : 'keepDate',
                    width : 80,
                    align : 'center',
                    formatter:function(value,row,index){
                    	if(null!=value && ""!=value){
                    		return value+"个月";
                    	}
                    }
                },{
                    title : '生产厂家',
                    field : 'supNo',
                    width : 90,
                    align : 'center'
                },{
                    title : '排序',
                    field : 'sordNo',
                    width : 60,
                    align : 'center'
                },{
                    title : '状态',
                    field : 'statusDesc',
                    width : 80,
                    align : 'center',
                },{
                    title : '商品上架时间',
                    field : 'listTime',
                    width : 140,
                    align : 'center',
                    formatter:function(value,row,index){
                    	return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                    }
                }, {
                    title : '商品下架时间',
                    field : 'delistTime',
                    width : 140,
                    align : 'center',
                    formatter:function(value,row,index){
                    	return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                    }
                },{
                    title : '创建人',
                    field : 'createUser',
                    width : 100,
                    align : 'center'
                },{
                    title : '修改人',
                    field : 'updateUser',
                    width : 100,
                    align : 'center'
                },{
                    title : '创建时间',
                    field : 'createDate',
                    width : 140,
                    align : 'center',
                    formatter:function(value,row,index){
                    	return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                    }
                },{
                    title : '修改时间',
                    field : 'updateDate',
                    width : 140,
                    align : 'center',
                    formatter:function(value,row,index){
                    	return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                    }
                },{
                    title : '操作',
                    field : 'opt',
                    width : 150,
                    align : 'center',
                    formatter : function(value, row, index) {
                    	 var merchantStatus =$("#merchantStatus").val();//商户状态必须为正常
                    	// 授权标示
                     	var grantedAuthority=$('#grantedAuthority').val();
                    	 var content = "";
                    	 if(merchantStatus=="1"){
                    		 content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.queryGoodsStockInfo('"
                                 + index +"');\">库存</a>&nbsp;&nbsp;";
                    	 }
                    if(grantedAuthority=='permission'){
                    	 if(row.status !='G01'&&row.status !='G02'&& merchantStatus=="1"){//待审核状态不允许修改,已上架不允许
                    	 content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.editGoods('"
                             + index + "');\">修改</a>&nbsp;&nbsp;"
                    	 }
                    	 
                         if((row.status =='G03'||row.status =='G00')&& merchantStatus=="1"){//待上架或下架才能上架
	                    	 content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.shelves('"
	                             +  row.id + "');\">上架</a>&nbsp;&nbsp;" 
                         }
                         if(row.status =='G02'){//上架商品才能下架
                        	 content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.shelf('"
                                 + row.id + "');\">下架</a>&nbsp;&nbsp;"
                         }
                    }
                         content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.previewProduct('"
                             + row.id + "');\">预览</a>&nbsp;&nbsp;";
                    	 return content;
                    }
                }]],
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + '/application/goods/management/pagelist',
                data : param,
                type : "post",
                dataType : "json",
                success : function(data) {
                    $.validateResponse(data, function() {
                        success(data);
                    });
                    $("#tb").show();
                }
            })
        }
    });

    // 查询列表
    $(".search-btn").click(function() {
    	debugger;
        var params = {};
        params['merchantName'] = $("#merchantName").textbox('getValue');
        params['merchantType'] = $("#merchantType").combobox('getValue');
        params['goodsName'] = $("#goodsNames").textbox('getValue');
        params['goodsType'] = $("#goodsTypes").combobox('getValue');
        $('#tablelist').datagrid('load', params);
    });
    // 查询列表
    $("#flush").click(function() {
    	debugger;
    	$("#goodsNames").textbox('setValue','');
    	$("#goodsTypes").combobox('setValue','');
    	$("#merchantName").textbox('setValue','');
    	$("#merchantType").combobox('setValue','');
    	var params = {};
    	$('#tablelist').datagrid('load', params);
    });
    /** 
     * 新增商品
     */
    $(".add-btn").click(function() {
    	var merchantCode=$("#addmerchantCode").textbox('getValue');
    	if(merchantCode==''){
    		$.messager.alert("提示", "必须商户才能添加商品！", "info");
    		return;
    	}
    	debugger;
    	$('#addGoodsInfo').window('open');
    	$("#addgoodsModel").textbox('clear');
    	$("#addgoodsName").textbox('clear');
		$("#addgoodsTitle").textbox('clear');
		$("#addgoodsSellPt").textbox('clear');
		$("#addlistTime").datetimebox('setValue', '');
		$("#adddelistTime").datetimebox('setValue', '');
		$("#addproDate").datebox('setValue', '');
		$("#addkeepDate").numberbox('setValue','');  
		$("#addsupNo").textbox('clear');
		$('#addgoodsSkuType').combobox('setValue','');
		$('#addGoodsInfo').window({modal: true});
		$("#sordNo").numberbox('setValue','');  
    });
    
    //确定
	$("#goodsAddinfo").click(function() {
		debugger;
		var merchantCode=$("#addmerchantCode").textbox('getValue'), 
		goodsModel=$("#addgoodsModel").textbox('getValue'),
		goodsName=$("#addgoodsName").textbox('getValue'), 
		goodsTitle=$("#addgoodsTitle").textbox('getValue'), 
		listTime=$("#addlistTime").datetimebox('getValue'), 
		proDate=$("#addproDate").datebox('getValue'),
		delistTime=$("#adddelistTime").datetimebox('getValue'),
		keepDate=$("#addkeepDate").textbox('getValue'),
		supNo=$("#addsupNo").textbox('getValue'),
		goodsSkuType=$("#addgoodsSkuType").textbox('getValue');
		sordNo=$("#sordNo").textbox('getValue');
		//字段效验

		if (null == merchantCode || ("") == merchantCode) {
			$.messager.alert("提示", "商户编码不能为空！", "info");
			return;
		}
		if (null == goodsName || ("") == goodsName) {
			$.messager.alert("提示", "商品名称不能为空！", "info");
			return;
		}
		
		if(goodsName.length>18){
    		$.messager.alert("提示", "商品名称最多18字！", "info");  
    		return;
		}
		if (null == goodsModel || ("") == goodsModel) {
			$.messager.alert("提示", "商品型号不能为空！", "info");
			return;
		}
		
		if (null == goodsTitle || ("") == goodsTitle) {
			$.messager.alert("提示", "商品小标题不能为空！", "info");
			return;
		}
		if(goodsTitle.length>25){
    		$.messager.alert("提示", "商品小标题最多25字！", "info");  
    		return;
		}
		if (null == goodsSkuType || ("") == goodsSkuType) {
			$.messager.alert("提示", "商品规格类型不能为空！", "info");
			return;
		}
		if (null == listTime || ("") == listTime) {
			$.messager.alert("提示", "商品上架日期不能为空！", "info");
			return;
		}	
		if (null == delistTime || ("") == delistTime) {
			$.messager.alert("提示", "商品下架日期不能为空！", "info");
			return;
		}
		if (listTime > delistTime) {
			$.messager.alert("提示", "上架时间不能大于下架时间！", "info");
			return;
		}
//		if (null == proDate || ("") == proDate) {
//			$.messager.alert("提示", "商品生产日期不能为空！", "info");
//			return;
//		}
		if (proDate > listTime) {
			$.messager.alert("提示", "商品生产日期不能大于上架日期！", "info");
			return;
		}
//		if (null == keepDate || ("") == keepDate) {
//			$.messager.alert("提示", "商品保质期不能为空！", "info");
//			return;
//		}
		
//		if (null == supNo || ("") == supNo) {
//			$.messager.alert("提示", "生产厂家不能为空！", "info");
//			return;
//		}
		
		if (null == sordNo || ("") == sordNo) {
			$.messager.alert("提示", "排序不能为空！", "info");
			return;
		}
		
		//from重组
		var formObj = $("<form></form>").attr("method","post");
		formObj.append("<input type='text' name='merchantCode' value='"+merchantCode+"'/>");
	 	formObj.append("<input type='text' name='goodsModel' value='"+goodsModel+"'/>");
	 	formObj.append("<input type='text' name='goodsName' value='"+goodsName+"'/>");
	 	formObj.append("<input type='text' name='goodsTitle' value='"+goodsTitle+"'/>");
	 	formObj.append("<input type='text' name='listTime'  dataType='Require' value='"+listTime+"'/>");
		formObj.append("<input type='text' name='delistTime' value='"+delistTime+"'/>");
		if(proDate!=""){
			formObj.append("<input type='text' name='proDate' value='"+proDate+" 00:00:00'/>");
		}
		if(keepDate!=""){
			formObj.append("<input type='text' name='keepDate' value='"+keepDate+"'/>");
		}
		if(supNo!=""){
			formObj.append("<input type='text' name='supNo' value='"+supNo+"'/>");
		}
		formObj.append("<input type='text' name='goodsSkuType' value='"+goodsSkuType+"'/>");
		formObj.append("<input type='text' name='sordNo' value='"+sordNo+"'/>");
		formObj.css('display','none').appendTo("body");
		formObj.form("submit",{ 
			url : ctx + '/application/goods/management/add',
			success : function(data) {
				debugger;
				var flag1 = data.indexOf('登录系统');
				var flag2 = data.indexOf('</div');
				//console.log(data+"==========>"+flag);;
				if(flag1 != -1 && flag2 != -1){
					$.messager.alert("操作提示", "登录超时, 请重新登录", "info");
    				window.top.location = ctx + "/logout";
    				return false;
				}
				if(data=='SUCCESS'){
					$('#addGoodsInfo').window('close');
				    $(".search-btn").click();
				}else{
					$.messager.alert("提示", data, "info");
				}
			}
		});
	});

	// 取消
	$("#disGoods").click(function() {
		$('#addGoodsInfo').window('close');
	});
	/**
	 * 编辑
	 */
	$.editGoods = function(index) {
		debugger;
		$('#editGoodsInfo').window({modal: true});
		$('#editGoodsInfo').window('open');
		var row = $('#tablelist').datagrid('getData').rows[index];
		loadLogo(row.goodsLogoUrl);
		$("#editid").val(row.id);
		$("#editLogogoodsId").val(row.id);
		
		$("#editmerchantCode").textbox('setValue',row.merchantCode);
		$("#editgoodsModel").textbox('setValue',row.goodsModel);
		$("#editgoodsName").textbox('setValue',row.goodsName);
		$("#editgoodsTitle").textbox('setValue',row.goodsTitle);
		$("#editgoodsSkuType").combobox('setValue',row.goodsSkuType);
		$("#editlistTime").datetimebox('setValue',new Date(row.listTime).Format("yyyy-MM-dd hh:mm:ss")); 
		$("#editdelistTime").datetimebox('setValue',new Date(row.delistTime).Format("yyyy-MM-dd hh:mm:ss"));
		if(null==row.proDate || ""==row.proDate){
			$("#editproDate").datebox('setValue',"");
		}else{
			$("#editproDate").datebox('setValue',new Date(row.proDate).Format("yyyy-MM-dd")); 
		}
		$("#editsupNo").textbox('setValue',row.supNo);
		$("#editkeepDate").textbox('setValue',row.keepDate);
		$("#editsordNo").numberbox('setValue',row.sordNo);
		
		//上传表单内容初始化重置
		$("#logoFilepic")[0].reset();
		
		UE.getEditor('editor').setContent('');//重置编辑器
		
		/**加载编辑器数据**/
		var params = {};
		params['id']=row.id;
		$.ajax({
			type : "POST",
			url : ctx + '/application/goods/management/loalEditor',
			data : params,
			success : function(data) {
				UE.getEditor('editor').execCommand( 'inserthtml', data);//加载编辑器
			}
		});
	}
	  //确定
	$("#editgoodsAddinfo").click(function() {
		var id=$("#editid").val(),
		goodsModel=$("#editgoodsModel").textbox('getValue'),
		goodsName=$("#editgoodsName").textbox('getValue'), 
		goodsTitle=$("#editgoodsTitle").textbox('getValue'),
		listTime=$("#editlistTime").datetimebox('getValue'),
		proDate=$("#editproDate").datebox('getValue'),
		delistTime=$("#editdelistTime").datetimebox('getValue'),
		keepDate=$("#editkeepDate").textbox('getValue'),sordNo=$("#editsordNo").numberbox('getValue'),
		supNo=$("#editsupNo").textbox('getValue'),goodsSkuType=$("#editgoodsSkuType").textbox('getValue');
		//字段效验
		if (null == goodsModel || ("") == goodsModel) {
			$.messager.alert("提示", "商品型号不能为空！", "info");
			return;
		}
		if (null == goodsName || ("") == goodsName) {
			$.messager.alert("提示", "商品名称不能为空！", "info");
			return;
		}
		if (goodsName.length>18) {
			$.messager.alert("提示", "商品名称最多18字！", "info");
			return;
		}
	
		if (null == goodsTitle || ("") == goodsTitle) {
			$.messager.alert("提示", "商品大标题不能为空！", "info");
			return;
		}
		if (goodsTitle.length>25) {
			$.messager.alert("提示", "商品小标题最多25字！", "info");
			return;
		}
		if (null == goodsSkuType || ("") == goodsSkuType) {
			$.messager.alert("提示", "商品最小单元分类不能为空！", "info");
			return;
		}
		if (null == listTime || ("") == listTime) {
			$.messager.alert("提示", "商品上架时间不能为空！", "info");
			return;
		}	
		if (null == delistTime || ("") == delistTime) {
			$.messager.alert("提示", "商品下架时间不能为空！", "info");
			return;
		}
		if (listTime > delistTime) {
			$.messager.alert("提示", "上架时间不能大于下架时间！", "info");
			return;
		}
//		if (null == proDate || ("") == proDate) {
//			$.messager.alert("提示", "商品生产日期不能为空！", "info");
//			return;
//		}
		if (proDate > listTime) {
			$.messager.alert("提示", "商品生产日期不能大于上架日期！", "info");
			return;
		}
		if (null == sordNo || ("") == sordNo) {
			$.messager.alert("提示", "商品排序不能为空！", "info");
			return;
		}
//		if (null == keepDate || ("") == keepDate) {
//			$.messager.alert("提示", "商品保质期不能为空！", "info");
//			return;
//		}
//		if (null == supNo || ("") == supNo) {
//			$.messager.alert("提示", "生产厂家不能为空！", "info");
//			return;
//		}
		
		//提交表单
		//from重组
		var formObj = $("<form></form>").attr("method","post");
		formObj.append("<input type='text' name='id' value='"+id+"'/>");
		formObj.append("<input type='text' name='goodsModel' value='"+goodsModel+"'/>");
	 	formObj.append("<input type='text' name='goodsName' value='"+goodsName+"'/>");
	 	formObj.append("<input type='text' name='goodsTitle' value='"+goodsTitle+"'/>");
	 	formObj.append("<input type='text' name='listTime'  dataType='Require' value='"+listTime+"'/>");
		formObj.append("<input type='text' name='delistTime' value='"+delistTime+"'/>");
		if(!(null == proDate || ("") == proDate)){
			formObj.append("<input type='text' name='proDate' value='"+proDate+"  00:00:00'/>");
		}else{
			formObj.append("<input type='text' name='proDate' value=''/>");
		}
		
		formObj.append("<input type='text' name='keepDate' value='"+keepDate+"'/>");
		formObj.append("<input type='text' name='supNo' value='"+supNo+"'/>");
		formObj.append("<input type='text' name='sordNo' value='"+sordNo+"'/>");
		formObj.append("<input type='text' name='goodsSkuType' value='"+goodsSkuType+"'/>");
		formObj.css('display','none').appendTo("body");
		formObj.form("submit",{ 
			url : ctx + '/application/goods/management/edit',
			success : function(data) {
				var flag1 = data.indexOf('登录系统');
				var flag2 = data.indexOf('</div');
				//console.log(data+"==========>"+flag);;
				if(flag1 != -1 && flag2 != -1){
					$.messager.alert("操作提示", "登录超时, 请重新登录", "info");
    				window.top.location = ctx + "/logout";
    				return false;
				}
				
				if(data=='SUCCESS'){
					$.messager.alert("提示", "修改商品成功", "info");
					$('#editGoodsInfo').window('close');
					$(".search-btn").click();
				}else{
					$.messager.alert("提示", data, "info");
				}
			}
		});
	});
	// 取消
	$("#editdisGoods").click(function() {
		$('#editGoodsInfo').window('close');
	});
	// 打开editor编辑器
	$("#reportGoods").click(function() {
		$('#editorReport').window({modal: true});
		$('#editorReport').window('open');
	});
	
	  //确定 获取编辑器的html
	$("#getAllHtml").click(function() {
		 	var params = {};
			params['id']=$("#editid").val();
//			var goodsDetails = UE.getEditor('editor').getAllHtml();
//			params['goodsDetail'] = goodsDetails.splice(12,0,'<meta charset="UTF-8">');
			params['goodsDetail'] = UE.getEditor('editor').getAllHtml();;
			params['goodsContent'] = UE.getEditor('editor').getContent();
			$.ajax({
				type : "POST",
				url : ctx + '/application/goods/management/upeditor',
				data : params,
				success : function(data) {
					debugger;
					if(data.message=='timeout'){
						$.messager.alert("操作提示", "登录超时, 请重新登录", "info");
	    				window.top.location = ctx + "/logout";
	    				return false;
					}
					$('#editorReport').window('close');
				}
			});
	});

	// 取消
	$("#getAllHtmldisy").click(function() {
		$('#editorReport').window('close');
	});
	/**
	 * 库存datagrid
	 */
	$.queryGoodsStockInfo = function(index) {
		 var dataRow = $('#tablelist').datagrid('getData').rows[index];
		 var goodsId=dataRow.id
		 var status=dataRow.status;
		$('#goodsId').val(goodsId);
		$('#goodsStockInfo').window({modal: true});
		$('#goodsStockInfo').window('open');

		$('#goodsStockList').datagrid({
	        height:220,
	        width:"100%",
	        url : ctx + '/application/goods/stockinfo/pagelist?goodsId='+goodsId,
	        idField:'id',
	        pagination : true,
//	        fit:true,
			//fitColumns : true,
			rownumbers : true,
	        columns: [[
	                {field: 'stockLogo', title: 'logo', width: 350,align : 'center',
	                	formatter : function(value, row, index) {
	                		 var content = "";
	                		 content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.alertPic('"
	                	         + row.stockLogo + "');\">"+row.stockLogo+"</a>&nbsp;&nbsp;";
	                		 return content;
	                	}
	                },
	                {field: 'id', title: '库存id', width: 80,align : 'center',hidden:'hidden'},
	                {field: 'goodsSkuAttr', title: '库存规格', width: 80,align : 'center'},
	                {field: 'goodsCostPrice', title: '成本价格', width: 80,align : 'center',
                    formatter:function(value,row,index){
                    	// 授权标示
                     	var goodCostpriceIf=$('#goodCostpriceIf').val();
                     	if(goodCostpriceIf=='permission'){
                    		return value;
                    	} 
                    		return "未授权";
                    }
	                },
	                {field: 'marketPrice', title: '市场价格', width: 80,align : 'center'},
	                {field: 'goodsPrice', title: '商品现价', width: 80,align : 'center'},
	                {field: 'stockTotalAmt', title: '商品总量', width: 80,align : 'center'},
	                {field: 'stockCurrAmt', title: '当前库存量', width: 80,align : 'center'},
	                {field: 'goodsCompareUrl', title: '比价连接1', width: 280,align : 'center'},
	                {field: 'goodsCompareUrl2', title: '比价连接2', width: 280,align : 'center'},
	                {field : 'opt',  title : '操作', width : 100,align : 'center',
	                    formatter : function(value, row, index) {
	                    	// 授权标示
	                     	var grantedAuthority=$('#grantedAuthority').val();
	                    	var content = "";
	                    	if(grantedAuthority=='permission'){
	                    		content +="<a href='javascript:void(0);' class='easyui-linkedbutton' " +
	                    	 		"onclick=\"$.updateStockinfo('"+row.id+"','"+row.stockTotalAmt+"','"+row.stockCurrAmt+"');\">加库存</a>&nbsp;&nbsp;";
	                    		if(status!='G02'){//上架后不能修改
	                    			content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.editStockinfo('"+index+"');\">修改</a>&nbsp;&nbsp;";
	                    		}
	                    	}else{
	                     		content +="权限不足";
	                    	}
	                    	return content;
	                    }
	                }
	            ]] 
	    });
	}
	
	//商品规格长度校验
	$("input",$("#goodsSkuAttr").next("span")).blur(function(){  
		var goodsSkuAttr = $("#goodsSkuAttr").textbox('getValue');
		if(goodsSkuAttr.length>5){
			$.messager.alert("<span style='color: black;'>提示</span>","商品规格内容不能超过5个字",'info');
			$("#goodsSkuAttr").textbox('setValue','');
			return;
		}
	})  
	
	//新增库存
	$("#addGoodsStockInfo").click(function() {
		var goodsId=$('#goodsId').val();
		$("#goodsSkuAttr").textbox('clear');
		$("#goodsPrice").textbox('clear');
		$("#marketPrice").textbox('clear');
		$("#stockTotalAmt").textbox('clear');
		$("#goodsCostPrice").textbox('clear');
		$("#goodsCompareUrlone").textbox('clear');
		$("#goodsCompareUrltwo").textbox('clear');
		$("#goodsCompareUrl").val('');
		$("#stockLogoFile").val('');
		
		$('#addGoodsStockInfoWin').window({modal: true});
		$('#addGoodsStockInfoWin').window('open');
	});
	// 取消
	$("#addGoodsStockInfoDisy").click(function() {
		$('#addGoodsStockInfoWin').window('close');
	});
	//新增库存
	$("#addGoodsStockInfoSumbit").click(function() {
		debugger;
		var goodsId=$('#goodsId').val();
		$('#addstockInfogoodsId').val(goodsId);
		var goodsSkuAttr=$("#goodsSkuAttr").textbox('getValue');
		var goodsPrice=$("#goodsPrice").textbox('getValue');
		var marketPrice=$("#marketPrice").textbox('getValue');
		var stockTotalAmt=$("#stockTotalAmt").textbox('getValue');
		var goodsCostPrice=$("#goodsCostPrice").textbox('getValue');
		var goodsCompareUrlone=$("#goodsCompareUrlone").textbox('getValue');
		var goodsCompareUrltwo=$("#goodsCompareUrltwo").textbox('getValue');
		debugger;
//		$('#goodsCompareUrl').val(goodsCompareUrlone+";"+goodsCompareUrlone);
//		console.log($('#goodsCompareUrl').val());
		var stockLogoFile=$("#stockLogoFile").val();
		
		if (null == goodsCompareUrlone || ("") == goodsCompareUrlone) {
			$.messager.alert("提示", "比价链接不允许为空！", "info");
			return;
		}
		if (null == goodsCompareUrltwo || ("") == goodsCompareUrltwo) {
			$.messager.alert("提示", "比价链接不允许为空！", "info");
			return;
		}
		
		if (null == goodsSkuAttr || ("") == goodsSkuAttr) {
			$.messager.alert("提示", "库存规格不能为空！", "info");
			return;
		}
		if (null == goodsPrice || ("") == goodsPrice) {
			$.messager.alert("提示", "商品价格不能为空！", "info");
			return;
		}
		if (null == marketPrice || ("") == marketPrice) {
			$.messager.alert("提示", "市场价不能为空！", "info");
			return;
		}
		if (null == stockTotalAmt || ("") == stockTotalAmt) {
			$.messager.alert("提示", "库存量不能为空！", "info");
			return;
		}
		if (null == goodsCostPrice || ("") == goodsCostPrice) {
			$.messager.alert("提示", "成本价不能为空！", "info");
			return;
		}
		if (null == stockLogoFile || ("") == stockLogoFile) {
			$.messager.alert("提示", "库存logo不允许空！", "info");
			return;
		}
		if (null == stockLogoFile || ("") == stockLogoFile) {
			$.messager.alert("提示", "库存logo不允许空！", "info");
			return;
		}
//		var pos = "." + stockLogoFile.replace(/.+\./, "");
//		if(pos!=".png"){
//		$.messager.alert("提示", '请导入正确的文件格式');
//		return;
//		}
		//提交from
		var theForm = $("#stockInfoFrom");
		theForm.form({
			url : ctx + '/application/goods/stockinfo/add',
			success : function(data) {
            	if (data.indexOf ('请输入账户') != -1)
			    {
            		$.messager.alert("提示", "超时，请重新登录", "info");
            		parent.location.reload();
			    }
				var response = JSON.parse(data);
				if(response.msg=="success"){
					$.messager.alert("提示","缩略图上传成功！", "info");
					var params = {};
			        params['goodsId'] = goodsId;
			        $('#goodsStockList').datagrid('load', params);
					$('#addGoodsStockInfoWin').window('close');
				}else{
					$.messager.alert("提示", response.msg, "info");
				}
		    }
		});
		theForm.submit();
	});
	//防止表单干净提交代码
	$("#stockInfoFrom").submit(function(){  
		$(":submit",this).attr("disabled","disabled");  
	});
	//加库存弹出窗口
	$.updateStockinfo = function(stockinfoId,stockTotalAmt,stockCurrAmt) {
		debugger;
		$('#stockinfoId').val(stockinfoId);
		$('#stockTotalAmtTemp').val(stockTotalAmt);
		$('#stockCurrAmtTemp').val(stockCurrAmt);
		$("#addstockinfoAmt").textbox('clear');
		$('#addAmtGoodsStockInfoWin').window({modal: true});
		$('#addAmtGoodsStockInfoWin').window('open');
	};	
	// 取消
	$("#addAmtGoodsStockInfoWinDisy").click(function() {
		$('#addAmtGoodsStockInfoWin').window('close');
	});
	// 确定
	$("#addAmtGoodsStockInfoWinSumbit").click(function() {
		var goodsId=$('#goodsId').val();
		var stockinfoId=$('#stockinfoId').val();
		var stockTotalAmtTemp=$('#stockTotalAmtTemp').val();
		var stockCurrAmtTemp=$('#stockCurrAmtTemp').val();
		var addstockinfoAmt=$("#addstockinfoAmt").textbox('getValue');
		if (null == addstockinfoAmt || ("") == addstockinfoAmt) {
			$.messager.alert("提示", "请输入要加的库存数目！", "info");
			return;
		}
		//判断商品总量是否超过999
		debugger;
//		var stockinfoAmtAll=parseInt(addstockinfoAmt)+parseInt(stockTotalAmtTemp);//库存商品总量
		var stockinfoAmtAll=parseInt(addstockinfoAmt)+parseInt(stockCurrAmtTemp);//当前库存量
		if (stockinfoAmtAll > 999)
		{
			$.messager.alert ("提示", "当前库存数量不能超过999个！", "info");
			return;
		}
		var param = {};
		param['id']=stockinfoId;
		param['addAmt']=addstockinfoAmt;
		/**
		 * 验证未加
		 */
		$.ajax({
			type : "POST",
			url : ctx + '/application/goods/stockinfo/editStockinfo',
			data : param,
			success : function(data) {
			 	var params = {};
		        params['goodsId'] = goodsId;
		        $('#goodsStockList').datagrid('load', params);
		        $('#addAmtGoodsStockInfoWin').window('close');
			}
		});
	});
	
	//修改库存
	$.editStockinfo = function(index) {
		var row = $('#goodsStockList').datagrid('getData').rows[index];
		$("#editstockinfoId").val(row.id);
		$("#editStockinfoIdInForm").val(row.id);
		$("#editgoodsSkuAttr").textbox('setValue',row.goodsSkuAttr);
		$("#editgoodsPrice").textbox('setValue',FormatAfterDotNumber(row.goodsPrice,2));
		$("#editmarketPrice").textbox('setValue',FormatAfterDotNumber(row.marketPrice,2));
		$("#editstockTotalAmt").textbox('setValue',row.stockTotalAmt);
		$("#editgoodsCostPrice").textbox('setValue',FormatAfterDotNumber(row.goodsCostPrice,2));
		
		$("#editStockGoodsLogoImg").attr("src",ctx + "/fileView/query?picUrl=" + row.stockLogo);
		$("#editStockLogoUrl").val(row.stockLogo);
		
		$("#editgoodsCompareUrlone").textbox('setValue',row.goodsCompareUrl);
		$("#editgoodsCompareUrltwo").textbox('setValue',row.goodsCompareUrl2);
		
		$('#editGoodsStockInfoWin').window({modal: true});
		$('#editGoodsStockInfoWin').window('open');
	};
	
	// 取消
	$("#editGoodsStockInfoDisy").click(function() {
		$('#editGoodsStockInfoWin').window('close');
	});
	//确定
	$("#editGoodsStockInfoSumbit").click(function() {
		debugger;
		var goodsId=$('#goodsId').val();
		var stockinfoId=$("#editstockinfoId").val();
		var goodsSkuAttr=$("#editgoodsSkuAttr").textbox('getValue');
		var goodsPrice=$("#editgoodsPrice").textbox('getValue');
		var marketPrice=$("#editmarketPrice").textbox('getValue');
		var stockTotalAmt=$("#editstockTotalAmt").textbox('getValue');
		var goodsCostPrice=$("#editgoodsCostPrice").textbox('getValue');
		var editgoodsCompareUrlone=$("#editgoodsCompareUrlone").textbox('getValue');
		var editgoodsCompareUrltwo=$("#editgoodsCompareUrltwo").textbox('getValue');
		var editstockLogoFile=$("#editstockLogoFile").val();
		var editStockGoodsLogoFile=$('#editStockGoodsLogoFile').val();
		
		
		if (null == editgoodsCompareUrlone || ("") == editgoodsCompareUrlone) {
			$.messager.alert("提示", "比价链接不允许为空！", "info");
			return;
		}
		if (null == editgoodsCompareUrltwo || ("") == editgoodsCompareUrltwo) {
			$.messager.alert("提示", "比价链接不允许为空！", "info");
			return;
		}
		if (null == goodsSkuAttr || ("") == goodsSkuAttr) {
			$.messager.alert("提示", "属性不能为空！", "info");
			return;
		}
		if (null == goodsPrice || ("") == goodsPrice) {
			$.messager.alert("提示", "商品价格不能为空！", "info");
			return;
		}
		if (null == marketPrice || ("") == marketPrice) {
			$.messager.alert("提示", "市场价不能为空！", "info");
			return;
		}
		if (null == stockTotalAmt || ("") == stockTotalAmt) {
			$.messager.alert("提示", "库存量不能为空！", "info");
			return;
		}
		if (null == goodsCostPrice || ("") == goodsCostPrice) {
			$.messager.alert("提示", "成本价不能为空！", "info");
			return;
		}
//		if (null == editstockLogoFile || ("") == editstockLogoFile) {
//			$.messager.alert("提示", "库存logo不允许空！", "info");
//			return;
//		}
//		if (null == editstockLogoFile || ("") == editstockLogoFile) {
//			$.messager.alert("提示", "库存logo不允许空！", "info");
//			return;
//		}
		debugger;
		var param = {};
		param['goodsSkuAttr']=goodsSkuAttr;
		param['id']=stockinfoId;
		param['goodsCostPrice']=goodsCostPrice;
		param['marketPrice']=marketPrice;
		param['goodsPrice']=goodsPrice;
		param['goodsCompareUrl']=editgoodsCompareUrlone;
		param['goodsCompareUrl2']=editgoodsCompareUrltwo;
		
		
		$.ajax({
			type : "POST",
			url : ctx + '/application/goods/stockinfo/editStockinfo',
			data : param,
			success : function(data) {
				debugger;
//				var response = JSON.parse(data);
				if(data.msg=="success"){
					$.messager.alert("提示", "修改库存信息成功！", "info");
					var params = {};
			        params['goodsId'] = goodsId;
			        $('#goodsStockList').datagrid('load', params);
			        $('#editGoodsStockInfoWin').window('close');
				}else{
					$.messager.alert("提示", data.msg, "info");
				}
			}
		});
		
	});
	
	/**
	 * banner图 ：商品大图上传
	 */
	$("#bannerGoods").click(function() {
		$("#bannerPicName").textbox('clear');
		$("#bannerPicOrder").textbox('clear');
		$("#bannerPicFile").val('');
		$('#bannerPicUpWin').window({modal: true});
		$('#bannerPicUpWin').window('open');
		var goodsId=$("#editid").val();//商品Id
		$("#bannerGoodsId").val(goodsId);
		loadBanner(goodsId);
	});
	// 取消
	$("#addFiledisy").click(function() {
		$('#bannerPicUpWin').window('close');
	});
	// 上传
	$("#addFileSumbit").click(function() {
		var bannerPicOrder=$("#bannerPicOrder").textbox('getValue');
		var bannerPicFile=$("#bannerPicFile").val();
		var bannerGoodsId=$("#bannerGoodsId").val();
		/**
		 * 验证是否为空 或格式问题
		 */
		if (null == bannerPicOrder || ("") == bannerPicOrder) {
			$.messager.alert("提示", "请输入排序！", "info");
			return;
		}
		if (null == bannerPicFile || ("") == bannerPicFile) {
			$.messager.alert("提示", "请选择文件！", "info");
			return;
		}
//		var pos = "." + bannerPicFile.replace(/.+\./, "");
//		if(pos!=".png"){
//			$.messager.alert("提示", '请导入正确的文件格式');
//			return;
//		}
		//提交form
		var theForm = $("#bannerListpic");
		theForm.form("submit",{
			url : ctx + '/application/goods/management/addBanner',
			success : function(data) {
				var response = JSON.parse(data);
				console.log(response);
				if(response.msg=="success"){
					debugger;
					$.messager.alert("提示", '商品大图上传成功！');
					$("#bannerPicName").textbox('clear');
					$("#bannerPicOrder").textbox('clear');
					$("#bannerPicFile").val('')
//					var param = {};
//	            	param['goodsId'] = goodsId;
			        $('#goodsbannerList').datagrid('load', null);
				}else{
					$.messager.alert("提示", response.msg);
				}
				
			}
		});
	});
	//加载banner列表
	function loadBanner(goodsId){
		$('#goodsbannerList').datagrid({
	        height:250,
	        width:"100%",
	        idField:'id',
	        pagination : true,
			fitColumns : true,
			rownumbers : true,
	        columns: [[
//	                {field: 'bannerName', title: '名称', width: 100},
	                {field: 'bannerImgUrl', title: '地址', width: 355,
	                    formatter : function(value, row, index) {
	                    	 var content = "";
	                    	 content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.alertPic('"
	                             + row.bannerImgUrl + "');\">"+row.bannerImgUrl+"</a>&nbsp;&nbsp;";
	                    	 return content;
	                    }
	                },
	                {field: 'bannerOrder', title: '排序', width: 50},
	                {field : 'opt',  title : '操作', width : 50,
	                    formatter : function(value, row, index) {
	                    	 var content = "";
	                    	 content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.delbanner('"
	                             + row.id + "');\">删除</a>&nbsp;&nbsp;";
	                    	 return content;
	                    }
	                }
	            ]] ,
	            loader : function(param, success, error) {
	            	var param = {};
	            	param['goodsId'] = goodsId;
	                $.ajax({
	                    url : ctx + '/application/goods/management/goodsbannerList',
	                    data : param,
	                    type : "post",
	                    dataType : "json",
	                    success : function(data) {
	                        $.validateResponse(data, function() {
	                            success(data);
	                        });
	                    }
	                })
	            }
	    });
	}
	//删除banner图
	$.delbanner = function(id) {
		var goodsId=$('#goodsId').val();
		$.messager.confirm('提示框', '你确定要删除吗?',function(r){
			if(r){
				var params = {};
				params['id']=id;
				$.ajax({
					type : "POST",
					url : ctx + '/application/goods/management/delBanner',
					data : params,
					success : function(data) {
						var param = {};
		            	param['goodsId'] = goodsId;
				        $('#goodsbannerList').datagrid('load', param);
					}
				});
			} 
		})
	 
	};	
	//上架
	$.shelves = function(id) {
		$.messager.confirm('提示框', '你确定要上架吗?',function(r){
			if(r){
				debugger;
				var params = {};
				params['id']=id;
//				var editorGoodsDetail  = UE.getEditor('editor').getContent();
//				params['editorGoodsDetail']=editorGoodsDetail;
//				if (null == editorGoodsDetail || ("") == editorGoodsDetail) {
//					$.messager.alert("提示", "商品详情不能为空！", "info");
//					return;
//				}
				$.ajax({
					type : "POST",
					url : ctx + '/application/goods/management/shelves',
					data : params,
					success : function(data) {
						if(data=='SUCCESS'){
							$.messager.alert("提示", "商品上架成功", "info");
							$(".search-btn").click();
						}else{
							$.messager.alert("提示", data, "info");
						}
					}
				});
			} 
		})
	 
	};
	//下架
	$.shelf = function(id) {
		$.messager.confirm('提示框', '你确定要下架吗?',function(r){
			if(r){
				var params = {};
				params['id']=id;
				$.ajax({
					type : "POST",
					url : ctx + '/application/goods/management/shelf',
					data : params,
					success : function(data) {
					  debugger;
					  $.messager.alert("提示", "商品下架成功", "info");
					  $(".search-btn").click();
					}
				});
			} 
		})
	 
	};
	/** loadlogo* */
	function loadLogo (goodsLogoUrl)
	{
		debugger;
		if (goodsLogoUrl != null)
		{
			$ ("#editGoodsLogoImg").attr ("src","");
			$ ("#editGoodsLogoImg").attr ("src", ctx + "/fileView/query?picUrl=" + goodsLogoUrl);
		}else{
			$ ("#editGoodsLogoImg").attr ("src", '');
		}
	}
	
	/** loadlogo* */
	function loadStockLogo (goodsStockLogoUrl)
	{
		debugger;
		if (goodsStockLogoUrl != null)
		{
			var params = {};
			params['picUrl'] = goodsStockLogoUrl;
			$ ("#editStockGoodsLogoImg").attr ("src", ctx + "/fileView/query?picUrl=" + goodsStockLogoUrl);
		}else{
			$ ("#editStockGoodsLogoImg").attr ("src", '');
		}
	}
	
	//上传logo
	$("#upLogoBtn").click(function() {
		var editGoodsLogoFile=$('#editGoodsLogoFile').val();
		if (null == editGoodsLogoFile || ("") == editGoodsLogoFile) {
			$.messager.alert("提示", "请选择文件！", "info");
			return;
		}
//		var pos = "." + editGoodsLogoFile.replace(/.+\./, "");
//		if(pos!=".png"){
//			$.messager.alert("提示", '请导入正确的文件格式', "info");
//			return;
//		}
		//提交from
		var thisform = $("#logoFilepic");
		thisform.form("submit",{
			url : ctx + '/application/goods/management/uplogoFile',
			success : function(data) {
				var flag1 = data.indexOf('登录系统');
				var flag2 = data.indexOf('</div');
				//console.log(data+"==========>"+flag);;
				if(flag1 != -1 && flag2 != -1){
					$.messager.alert("操作提示", "登录超时, 请重新登录", "info");
    				window.top.location = ctx + "/logout";
    				return false;
				}
				
				 var response = JSON.parse(data);
				 $.validateResponse(response, function() {
		                $(".search-btn").click();
		         });
				 
				 if(response.status=="1"){
					 loadLogo(response.data);
				 }
			}
		});
	});
	
	//上传库存logo
	$("#upStockLogoBtn").click(function() {
		debugger;
		var editStockGoodsLogoFile=$('#editStockGoodsLogoFile').val();
//		$('#editStockInfogoodsId').val($('#goodsId').val());
//		var editStockInfogoodsId = $('#editStockInfogoodsId').val();
		var editStockinfoIdInForm = $('#editStockinfoIdInForm').val();
		if (null == editStockinfoIdInForm || ("") == editStockinfoIdInForm) {
			$.messager.alert("提示", "请选择文件！", "info");
			return;
		}
		if (null == editStockInfogoodsId || ("") == editStockInfogoodsId) {
			$.messager.alert("提示", "库存id不能为空！", "info");
			return;
		}
		//提交form
		var thisform = $("#stocklogoFilepic");
		thisform.form("submit",{
			url : ctx + '/application/goods/management/upstocklogoFile',
			success : function(data) {
				 var response = JSON.parse(data);
				 $.validateResponse(response, function() {
		                $(".search-btn").click();
		         });
				 
				 if(response.status=="1"){
					 loadStockLogo(response.data);
				 }
			}
		});
	});
	//预览单个banner图
	$.alertPic = function(url) {
		$("#previewBannerImg").attr("src","");
		$('#previewBannerWin').window({modal: true});
		$('#previewBannerWin').window('open');
//		var params = {};
//		params['picUrl']=url;
		debugger;
		$("#previewBannerImg").attr ("src", ctx + "/fileView/query?picUrl=" + url);
//		$.ajax({
//			type : "POST",
//			url : ctx + '/application/goods/management/loadPic',
//			data : params,
//			success : function(data) {
//				$("#previewBannerImg").attr("src","data:image/gif;base64,"+data);
//			}
//		});
	};
	//预览商品
	$.previewProduct = function(id,goodsId) {
		debugger;
        var subtitle = "商品预览-" + id;
        var parentTabs = parent.$('#tabs');
        var destAddress = ctx + "/application/goods/management/loadAllBannerPic?id=" + id;
        if (parentTabs.tabs('exists', subtitle)) {
            parentTabs.tabs('select', subtitle);
            return;
        }
        parentTabs.tabs('add', {
            title : subtitle,
            content : function() {
                var array = new Array();
                array.push('<iframe name="mainFrame" ');
                array.push('scrolling="auto" ');
                array.push('frameborder="0" ');
                array.push('src="' + destAddress + '" ');
                array.push(' style="width:100%;height:100%;" ');
                array.push(' ></iframe>');
                return array.join('');
            },
            closable : true
        });
	};
	$('#marketPrice').numberspinner({  
	    onChange:function(newValue,oldValue){ 
	    	var goodsPriceRate=$("#goodsPriceRate").val();
	    	if(goodsPriceRate==''){
	    		$("#goodsPrice").textbox('setValue',newValue);
	    	}else{
	    		$("#goodsPrice").textbox('setValue',FormatAfterDotNumber(newValue*goodsPriceRate,2));
	    	}
	    }  
	});
	$('#editmarketPrice').numberspinner({  
	    onChange:function(newValue,oldValue){ 
	    	var goodsPriceRate=$("#goodsPriceRate").val();
	    	if(goodsPriceRate==''){
	    		$("#editgoodsPrice").textbox('setValue',newValue);
	    	}else{
	    		$("#editgoodsPrice").textbox('setValue',FormatAfterDotNumber(newValue*goodsPriceRate,2));
	    	}
	    }  
	});
	
	// 导出订单
	$ (".export-btn").click (function (){
		$.messager.confirm ('商品信息', '确定要导出吗？', function (r)
		{
			if (r)
			{
				var params = {};
				params['goodsName'] = $ ("#goodsNames").textbox ('getValue');
				params['goodsType'] = $ ("#goodsTypes").combobox ('getValue');
				params['isAll'] = 'f';// t: 是 f: 否 是否导出全部订单信息
				params['busCode'] = 'E002';// 订单导出code
				exportFile ("tablelist", "商品信息", params);
			}
		});
		
	});
	
	
});

//往string中的某个位置插入内容
String.prototype.splice = function(idx, rem, str) {
    return this.slice(0, idx) + str + this.slice(idx + Math.abs(rem));
};



















