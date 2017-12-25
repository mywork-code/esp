/**
 * 限时购
 */
$(function () {
	$('#upLoadGoods').window('close');
	$('#editGoods').window('close');
	var limitBuyActId ;//活动ID
	var status ;//活动状态
	// 活动列表
    $('#limitBuyActPage').datagrid({
    	title : '限时购活动管理',
    	fit : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        striped:true,
        toolbar : '#tb',
        columns : [[{
            title : '属性主键',
            field : 'id',
            hidden : true,
            width : 50,
            align : 'center'
        },{
            title : '活动开始时间',
            field : 'startDate',
            width : 200,
            align : 'center',
            formatter:function(value,row,index){
        		if(value!=null){
        			return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
        		}
            }
        },{
            title : '活动结束时间',
            field : 'endDate',
            width : 200,
            align : 'center',
            formatter:function(value,row,index){
        		if(value!=null){
        			return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
        		}
            }
        },{
            title : '有效时长',
            field : 'option1',
            width : 200,
            align : 'center',
            formatter:function(value,row,index){
        		return "24小时";
            }
        },{
            title : '活动状态',
            field : 'status',
            width : 200,
            align : 'center',
            formatter:function(value,row,index){
            	if(value==1){
            		return "未开始";
            	}
            	if(value==2){
            		return "进行中";
            	}
            	if(value==3){
            		return "已结束";
            	}
            }
        },{
            title : '操作',
            field : 'option2',
            width : 200,
            align : 'center',
            formatter : function(value, row, index) {
            	if(row.status!=3){
            		return "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='$.editDetails(\""+row.id+"\",\""+row.startDate+"\",\""+row.startTime+"\",\""+row.status+"\");'>编辑活动</a>&nbsp;";
            	}else{
            		return "";
            	}
            }
        }]],
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + "/activity/limitBuyActContro/getLimitBuyActPage",
                data : param,
                type : "post",
                dataType : "json",
                success : function(resp) {
                    $.validateResponse(resp, function() {
                    	getLimitGoodsList();
                        $('#commonLayer').show(500);
                        success(resp);
                    });
                }
            })
        }
    });
    //活动开始时间下拉框
	$('#startTime').combobox({
		method: "get",
        url: ctx + "/activity/limitBuyActContro/getStartTimeDorp",
        panelHeight: '100',  
        textField: 'key',
        valueField: 'value',
        onChange: function (n, o) {
        	//刷新下发表格    不刷新  
        }
	});
	//活动状态下拉框
	$('#status').combobox({
		method: "get",
        url: ctx + "/activity/limitBuyActContro/getStatusDorp",
        panelHeight: '100',  
        textField: 'key',
        valueField: 'value',
        onChange: function (n, o) {
        	//刷新下发表格    不刷新  
        }
	});
    // 查询列表
    $(".search-btn").click(function() {
        var params = {};
        params['startDay'] = $("#startDay").datebox('getValue');
        params['startTime'] = $("#startTime").combobox('getValue');
        params['status'] = $("#status").combobox('getValue');
        $('#limitBuyActPage').datagrid('load', params);
    });
    //重置
    $(".flush-btn").click(function() {
    	$("#startDay").datebox('setValue', '');
    	$("#startTime").combobox('setValue','');
    	$("#status").combobox('setValue','');
    	var params = {};
    	$('#limitBuyActPage').datagrid('load', params);
    });
    //添加活动
    $(".add-btn").click(function() {
    	status = ""
    	limitBuyActId = "";
    	$("#startDayAdd").datebox('setValue', '');
    	$("#startTimeAdd").combobox('setValue','');
    	$("#startDayAdd").datebox({'disabled': false }); 
		$("#startTimeAdd").combobox({'disabled': false }); 
//		$('#uploadGoodsListAdd').datagrid('loadData',[]);
		var rows = $('#uploadGoodsListAdd').datagrid('getRows');
        for(var i=rows.length-1;i>=0;i--){
            $('#uploadGoodsListAdd').datagrid('deleteRow',i);
        }
		$('#addLayer').show(500,addLayerShow());
    });
    //编辑活动
    $.editDetails = function(id,startDate,startTime,status) {
    	status = status;
    	if(status=="2"){//进行中活动
    		$("#startDayEdit").datebox({'disabled': true }); 
    		$("#startTimeEdit").combobox({'disabled': true }); 
    	}
    	if(status=="1"){//未开始活动
    		$("#startDayEdit").datebox({'disabled': false }); 
    		$("#startTimeEdit").combobox({'disabled': false }); 
    	}
    	limitBuyActId = id;
    	$("#startDayEdit").datebox('setValue', new Date(startDate/1).Format("yyyy-MM-dd"));
    	$("#startTimeEdit").combobox('setValue',startTime);
    	var params = {};
    	params['limitBuyActId']=limitBuyActId;
    	$('#uploadGoodsListEdit').datagrid('load', params);
//    	$("#uploadGoodsListEdit").datagrid("resize");
		$('#editLayer').show(500,editLayerShow());
	};
    //返回
    $(".cancel-btn-add").click(function() {
    	$('#addLayer').hide(500,addLayerHide());
    });
    $(".cancel-btn-edit").click(function() {
    	$('#editLayer').hide(500,editLayerHide());
    });
    //活动开始时间下拉框
	$('#startTimeAdd').combobox({
		method: "get",
        url: ctx + "/activity/limitBuyActContro/getStartTimeDorp",
        panelHeight: '100',  
        textField: 'key',
        valueField: 'value',
        onChange: function (n, o) {
        	//刷新下发表格    不刷新  
        }
	});
	//活动状态下拉框
	$('#statusAdd').combobox({
		method: "get",
        url: ctx + "/activity/limitBuyActContro/getStatusDorp",
        panelHeight: '100',  
        textField: 'key',
        valueField: 'value',
        onChange: function (n, o) {
        	//刷新下发表格    不刷新  
        }
	});
	//活动开始时间下拉框
	$('#startTimeEdit').combobox({
		method: "get",
        url: ctx + "/activity/limitBuyActContro/getStartTimeDorp",
        panelHeight: '100',  
        textField: 'key',
        valueField: 'value',
        onChange: function (n, o) {
        	//刷新下发表格    不刷新  
        }
	});
	//活动状态下拉框
	$('#statusEdit').combobox({
		method: "get",
        url: ctx + "/activity/limitBuyActContro/getStatusDorp",
        panelHeight: '100',  
        textField: 'key',
        valueField: 'value',
        onChange: function (n, o) {
        	//刷新下发表格    不刷新  
        }
	});
	//导入商品  弹窗
	$(".upload-btn-add").click(function() {
		$("#upLoadGoodsFile").val('');
		$('#upLoadGoods').window({modal: true});
		$('#upLoadGoods').window('open');
	});
	//导入商品  弹窗 确定
	$("#upLoadGoodsFromSumbit").click(function() {
		var file=$("#upLoadGoodsFile").val();
		if (null == file || ("") == file) {
			$.messager.alert("<font color='black'>提示</font>", "请选择上传商品文件!", "info");
			return;
		}
		if(limitBuyActId!=""){
			$("#limitBuyActId").val(limitBuyActId);
		}
		var thisForm = $("#upLoadGoodsFrom");
		thisForm.form({
			url : ctx + '/activity/limitBuyActContro/upLoadLimitGoodsSku',
			success : function(data) {
				if (data.indexOf ('请输入账户') != -1){
            		$.messager.alert("提示", "超时，请重新登录", "info");
            		parent.location.reload();
			    }
				var response = JSON.parse(data);
				$.messager.alert("<font color='black'>提示</font>", response.msg, "info");
				if(response.status=="1"){
					//刷新商品列表
					$('#uploadGoodsListAdd').datagrid('loadData', response);
					$("#uploadGoodsListAdd").datagrid("resize");
				}
		    }
		});
		$('#upLoadGoods').window('close');
		thisForm.submit();
	});
	//导入商品  弹窗 取消
	$("#upLoadGoodsFromCancle").click(function() {
		$('#upLoadGoods').window('close');
	});
	//编辑商品  上传URL 弹窗  见下方editGoods
	//编辑商品  上传URL 弹窗   确定
	$("#editGoodsFromSumbit").click(function() {
		var limitNumTotalAdd=$("#limitNumTotalAdd").textbox('getValue');
		var limitNumAdd=$("#limitNumAdd").textbox('getValue');
		var file=$("#editGoodsFile").val();
		if (null == limitNumTotalAdd || ("") == limitNumTotalAdd) {
			$.messager.alert("<font color='black'>提示</font>", "请输入限购总量!", "info");
			return;
		}
		var r = /^\+?[1-9][0-9]*$/;//正整数
	    var falg1 = r.test(limitNumTotalAdd);
	    if(!falg1){
	    	$.messager.alert("<font color='black'>提示</font>", "限购总量请输入正整数!", "info");
			return;
	    }
		if (null == limitNumAdd || ("") == limitNumAdd) {
			$.messager.alert("<font color='black'>提示</font>", "请输入单人限购!", "info");
			return;
		}
		var falg2 = r.test(limitNumAdd);
	    if(!falg2){
	    	$.messager.alert("<font color='black'>提示</font>", "单人限购请输入正整数!", "info");
			return;
	    }
		if (limitNumTotalAdd/1 < limitNumAdd/1) {
			$.messager.alert("<font color='black'>提示</font>", "限购总量不可小于单人限购!", "info");
			return;
		}
		if (stockCurrAmt/1 < limitNumTotalAdd/1&&source!='wz') {
			$.messager.alert("<font color='black'>提示</font>", "非微知来源商品，限购总量不可大于库存剩余!", "info");
			return;
		}
		var filefalg = null == file || ("") == file;
		$("#upskuId").val(skuId);
		if(!filefalg){
			//此处上传缩略图
			//把URL返回在table里
			//把限购数量返回在table里
			var thisForm = $("#editGoodsFrom");
			thisForm.form({
				url : ctx + '/activity/limitBuyActContro/upLoadSkuPic',
				success : function(data) {
					if (data.indexOf ('请输入账户') != -1){
	            		$.messager.alert("提示", "超时，请重新登录", "info");
	            		parent.location.reload();
				    }
					var response = JSON.parse(data);
					if(response.status==1){
						$.messager.alert("<font color='black'>提示</font>","该活动商品缩略图上传成功！", "info");
						if(addedit==1){
							$('#uploadGoodsListAdd').datagrid('updateRow',{
								index: editindex,
								row:{
									"limitNumTotal":limitNumTotalAdd,
									"limitNum":limitNumAdd,
									"url":response.data,
									}
							});
						}
						if(addedit==2){
							$('#uploadGoodsListEdit').datagrid('updateRow',{
								index: editindex,
								row:{
									"limitNumTotal":limitNumTotalAdd,
									"limitNum":limitNumAdd,
									"url":response.data,
									}
							});
						}
						$('#editGoods').window('close');
					}else{
						$.messager.alert("<font color='black'>提示</font>", response.msg, "info");
					}
			    }
			});
			thisForm.submit();
		}else{
			//新增弹窗，编辑时只有只有未开始活动弹窗价格变动弹窗，进行中活动价格已经不可修改无需弹窗
			var actStatus = $("#actStatus").val();
			if(actStatus=="1"||actStatus==""){
				$.messager.alert("<font color='black'>提示</font>","该商品了修改单人限购和限购总量！", "info");
			}
			if(addedit==1){
				$('#uploadGoodsListAdd').datagrid('updateRow',{
					index: editindex,
					row:{
						"limitNumTotal":limitNumTotalAdd,
						"limitNum":limitNumAdd,
						}
				});
			}
			if(addedit==2){
				$('#uploadGoodsListEdit').datagrid('updateRow',{
					index: editindex,
					row:{
						"limitNumTotal":limitNumTotalAdd,
						"limitNum":limitNumAdd,
						}
				});
			}
			$('#editGoods').window('close');
		}
	});
	//编辑商品  上传URL 弹窗   取消
	$("#editGoodsFromCancle").click(function() {
		$('#editGoods').window('close');
	});
	/**保存所有数据   限时购   和   限时购商品列表  （新增 修改）	 */
	$(".save-btn-add").click(function() {
		var startDay = $("#startDayAdd").datebox('getValue');
		var startTime = $("#startTimeAdd").combobox('getValue');
		var goodsrows = $('#uploadGoodsListAdd').datagrid('getRows');
		if(startDay==""||startTime==""){
			$.messager.alert("<font color='black'>提示</font>", "请选择限时购活动开始日期和时间!", "info");
			return;
		}
		if(goodsrows==""||goodsrows.length==0){
			$.messager.alert("<font color='black'>提示</font>", "请上传限时购活动商品!", "info");
			return;
		}
		var param = {
			id:limitBuyActId,
			startDay:startDay,
			startTime:startTime,
			list:goodsrows
		};
		//判断是新增还是修改
		var url = limitBuyActId==""? (ctx + '/activity/limitBuyActContro/addLimitBuyAct'):(ctx + '/activity/limitBuyActContro/editLimitBuyAct');
		$.ajax({url : url,type : "POST",data :JSON.stringify(param),dataType: "json",contentType: 'application/json',
			success : function(data) {
				ifLogout(data);
				if(data.status==1){
					var params = {};
			    	$('#limitBuyActPage').datagrid('load', params);
			    	$('#addLayer').hide(500,addLayerHide());
			    	window.location.reload();
				}
				$.messager.alert("<font color='black'>提示</font>", data.msg, "info");
			}
		})
	});
	/**保存所有数据   限时购   和   限时购商品列表  （新增 修改）	 */
	$(".save-btn-edit").click(function() {
		var startDay = $("#startDayEdit").datebox('getValue');
		var startTime = $("#startTimeEdit").combobox('getValue');
		var goodsrows = $('#uploadGoodsListEdit').datagrid('getRows');
		if(startDay==""||startTime==""){
			$.messager.alert("<font color='black'>提示</font>", "请选择限时购活动开始日期和时间!", "info");
			return;
		}
		if(goodsrows==""||goodsrows.length==0){
			$.messager.alert("<font color='black'>提示</font>", "请上传限时购活动商品!", "info");
			return;
		}
		var param = {
			id:limitBuyActId,
			startDay:startDay,
			startTime:startTime,
			list:goodsrows
		};
		//判断是新增还是修改
		var url = limitBuyActId==""? (ctx + '/activity/limitBuyActContro/addLimitBuyAct'):(ctx + '/activity/limitBuyActContro/editLimitBuyAct');
		$.ajax({url : url,type : "POST",data :JSON.stringify(param),dataType: "json",contentType: 'application/json',
			success : function(data) {
				ifLogout(data);
				if(data.status==1){
					var params = {};
			    	$('#limitBuyActPage').datagrid('load', params);
			    	$('#editLayer').hide(500,editLayerHide());
			    	window.location.reload();
				}
				$.messager.alert("<font color='black'>提示</font>", data.msg, "info");
			}
		})
	});
	$(".download-btn-add").click(function() {
		var temp = document.createElement("form");          
        temp.action = ctx + '/activity/limitBuyActContro/downloadTemplate';          
        temp.method = "post";          
        temp.style.display = "none";          
        document.body.appendChild(temp);          
        temp.submit();
        temp.remove();
	});
	$(".download-btn-edit").click(function() {
		var form = $ ('<form></form>');
		form.attr ('action', ctx + '/activity/limitBuyActContro/downloadTemplate');
		form.attr ('method', 'post');
		form.attr ('target', '_self');
		var my_input = $ ('<input type="submit"/>');
		form.append (my_input);
		$('body').append(form);
		my_input.click ();
		form.remove();
	});
});
function commonLayerShow(){
	$('#addLayer').hide();
	$('#editLayer').hide();
}
function addLayerShow(){
	$('#commonLayer').hide(500);
}
function addLayerHide(){
	$('#commonLayer').show(500);
}
function editLayerShow(){
	$('#commonLayer').hide(500);
}
function editLayerHide(){
	$('#commonLayer').show(500);
}
function getLimitGoodsList(){
	addFunction();
	editFunction();
};
function addFunction(){
    $('#uploadGoodsListAdd').datagrid({
    	fit : false,
    	fitColumns: true,
        rownumbers : true,
        singleSelect : true,
        striped:true,
        columns : [[{
            title : '主键',
            field : 'id',
            hidden : true,
            width : 10,
            align : 'center'
        },{
        	title : 'URL',
            field : 'url',
            hidden : true,
            width : 10,
            align : 'center'
        },{
        	title : '来源',
            field : 'source',
            width : 50,
            align : 'center',
            formatter:function(value,row,index){
          		if(value=='wz'){
          			return "微知";
          		}
          		if(value!='wz'){
          			return "供应商";
          		}
             }
        },{
            title : '商品名称',
            field : 'goodsName',
            width : 80,
            align : 'center',
        },{
            title : '商品编码',
            field : 'goodsCode',
            width : 80,
            align : 'center',
        },{
            title : 'SKUID',
            field : 'skuId',
            width : 80,
            align : 'center',
        },{
        	title : '活动价',
            field : 'activityPrice',
            width : 70,
            align : 'center',
        },{
            title : '商户名称',
            field : 'merchantName',
            width : 70,
            align : 'center',
        },{
        	 title : '类目名称',
             field : 'categoryId1Name',
             width : 70,
             align : 'center',
         },{
        	 title : '商品状态',
             field : 'statusDesc',
             width : 70,
             align : 'center',
         },{
        	 title : '上传标志',
             field : 'upLoadStatus',
             width : 50,
             align : 'center',
             formatter:function(value,row,index){
          		if(value==1){
          			return "成功";
          		}
          		if(value==0){
          			return "失败";
          		}
             }
         },{
        	 title : '商品上架时间',
             field : 'listTime',
             width : 80,
             align : 'center',
             formatter:function(value,row,index){
         		if(value!=null){
         			return new Date(value).Format("yyyy-MM-dd");
         		}
             }
         },{
        	 title : '商品下架时间',
             field : 'delistTime',
             width : 80,
             align : 'center',
             formatter:function(value,row,index){
         		if(value!=null){
         			return new Date(value).Format("yyyy-MM-dd");
         		}
             }
         },{
        	 title : '库存剩余',
             field : 'stockCurrAmt',
             width : 60,
             align : 'center',
         },{
        	 title : '限购总量',
             field : 'limitNumTotal',
             width : 60,
             align : 'center',
         },{
        	 title : '每人限购',
             field : 'limitNum',
             width : 60,
             align : 'center',
         },{
            title : '操作',
            field : 'opt',
            width : 130,
            align : 'center',
            formatter : function(value, row, index) {
            	if(row.upLoadStatus==1){
            		var content = "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='editGoods(this,1);'>编辑</a>&nbsp;";
                    content += "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='upGoods(this,1);'>上移</a>";
                    content += "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='downGoods(this,1);'>下移</a>";
                    return content;
            	}else{
            		return "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='deleGoods(this,1);'>删除</a>";
            	}
            }
        }]],
    });
}

function editFunction(){
    $('#uploadGoodsListEdit').datagrid({
    	fit : true,
		fitColumns: false,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        striped:true,
        columns : [[{
            title : '主键',
            field : 'id',
            hidden : true,
            width : 10,
            align : 'center'
        },{
        	title : 'URL',
            field : 'url',
            hidden : true,
            width : 10,
            align : 'center'
        },{
        	title : '来源',
            field : 'source',
            width : 50,
            align : 'center',
            formatter:function(value,row,index){
          		if(value=='wz'){
          			return "微知";
          		}
          		if(value!='wz'){
          			return "供应商";
          		}
             }
        },{
            title : '商品名称',
            field : 'goodsName',
            width : 80,
            align : 'center',
        },{
            title : '商品编码',
            field : 'goodsCode',
            width : 80,
            align : 'center',
        },{
            title : 'SKUID',
            field : 'skuId',
            width : 80,
            align : 'center',
        },{
        	title : '活动价',
            field : 'activityPrice',
            width : 70,
            align : 'center',
        },{
            title : '商户名称',
            field : 'merchantName',
            width : 70,
            align : 'center',
        },{
        	 title : '类目名称',
             field : 'categoryId1Name',
             width : 70,
             align : 'center',
         },{
        	 title : '商品状态',
             field : 'statusDesc',
             width : 70,
             align : 'center',
         },{
        	 title : '上传标志',
             field : 'upLoadStatus',
             width : 50,
             align : 'center',
             formatter:function(value,row,index){
          		if(value==1){
          			return "成功";
          		}
          		if(value==0){
          			return "失败";
          		}
             }
         },{
        	 title : '商品上架时间',
             field : 'listTime',
             width : 80,
             align : 'center',
             formatter:function(value,row,index){
         		if(value!=null){
         			return new Date(value).Format("yyyy-MM-dd");
         		}
             }
         },{
        	 title : '商品下架时间',
             field : 'delistTime',
             width : 80,
             align : 'center',
             formatter:function(value,row,index){
         		if(value!=null){
         			return new Date(value).Format("yyyy-MM-dd");
         		}
             }
         },{
        	 title : '库存剩余',
             field : 'stockCurrAmt',
             width : 60,
             align : 'center',
         },{
        	 title : '限购总量',
             field : 'limitNumTotal',
             width : 60,
             align : 'center',
         },{
        	 title : '每人限购',
             field : 'limitNum',
             width : 60,
             align : 'center',
         },{
            title : '操作',
            field : 'opt',
            width : 130,
            align : 'center',
            formatter : function(value, row, index) {
            	if(row.upLoadStatus==1){
            		var content = "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='editGoods(this,2);'>编辑</a>&nbsp;";
                    content += "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='upGoods(this,2);'>上移</a>";
                    content += "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='downGoods(this,2);'>下移</a>";
                    return content;
            	}
            }
        }]],
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + "/activity/limitBuyActContro/getLimitGoodsList",
                data : param,
                type : "post",
                dataType : "json",
                success : function(resp) {
                    $.validateResponse(resp, function() {
                        success(resp);
                    });
                }
            })
        }
    });
}
 
var limitGoodsSkuId ;//商品ID
var stockCurrAmt ;//商品库存剩余
var editindex;//商品表行号
var skuId ;//商品skuID
var addedit;//新增活动or修改活动  修改行号
var source;//新增活动or修改活动 商品来源
//编辑商品弹框
function editGoods(target,num){
	addedit=num;
	var rowentity = getRowEntity(target,num);
	limitGoodsSkuId = rowentity.id;
	skuId = rowentity.skuId;
	source = rowentity.source;
	$("#editGoodsFile").val('');
	stockCurrAmt = rowentity.stockCurrAmt;
	if(rowentity.url!=""&&rowentity.url!=null){
		$("#limitGoodsSkuUrl").attr("src",ctx + "/fileView/query?picUrl=" + rowentity.url);
	}else{
		$("#limitGoodsSkuUrl").attr("src",'');
	}
	if(limitGoodsSkuId==""||limitGoodsSkuId==null){//新增编辑
		$("#actStatus").val("");
		if(rowentity.limitNumTotal!=null&&rowentity.limitNum!=null){
			$("#limitNumTotalAdd").textbox('setValue',rowentity.limitNumTotal);
			$("#limitNumAdd").textbox('setValue',rowentity.limitNum);
		}else{
			$("#limitNumTotalAdd").textbox('clear');
			$("#limitNumAdd").textbox('clear');
		}
		//有问题需要解决   有微调器失效BUG
		$("#limitNumTotalAdd").textbox({'disabled':false});
		$("#limitNumAdd").textbox({'disabled':false});
		$('#editGoods').window({modal: true});
		$('#editGoods').window('open');
//		$win = $('#editGoods').window({
//             top:320,
//             left:420,
//             shadow: true,
//             modal:true,
//             closed:true,
//             minimizable:false,
//             maximizable:false,
//             collapsible:false
//         });
//         $win.window('open');
	}else{//修改编辑
		$("#limitNumTotalAdd").textbox('setValue',rowentity.limitNumTotal);
		$("#limitNumAdd").textbox('setValue',rowentity.limitNum);
		$.ajax({url : ctx + "/activity/limitBuyActContro/getLimitBuyActStatus",data : {"id":limitGoodsSkuId},type : "post",dataType : "json",
            success : function(data) {
				ifLogout(data);
            	var sta = data.data;
            	$("#actStatus").val(sta);
            	if(sta=="1"){//未开始 可编辑
            		//有问题需要解决   有微调器失效BUG
            		$("#limitNumTotalAdd").textbox({'disabled':false});
            		$("#limitNumAdd").textbox({'disabled':false});
            	}
            	if(sta=="2"){//进行中 不可编辑
            		$("#limitNumTotalAdd").textbox({'disabled':true});
            		$("#limitNumAdd").textbox({'disabled':true});
            	}
            	$('#editGoods').window({modal: true});
            	$('#editGoods').window('open');
            }
        })
	}
};
//删除商品弹框
function deleGoods(target,num){
	var rowentity = getRowEntity(target,num);
	if(rowentity.id!=null){
		//此处删除数据库中数据！！
	}
	$('#uploadGoodsListAdd').datagrid('deleteRow', getRowIndex(target));
};
//上移商品
function upGoods(target,num){
	var rowIndex = getRowIndex(target);
	var selectrow = getRowEntity(target,num);
	if(rowIndex==0){  
		$.messager.alert("<font color='black'>提示</font>", "顶行无法上移!", "warning");  
    }else{
    	if(num==1){
    		$('#uploadGoodsListAdd').datagrid('deleteRow', rowIndex);//删除一行  
            rowIndex--;  
            $('#uploadGoodsListAdd').datagrid('insertRow', {  
                index:rowIndex,  
                row:selectrow  
            });  
            $('#uploadGoodsListAdd').datagrid('selectRow', rowIndex);  
    	}
    	if(num==2){
    		$('#uploadGoodsListEdit').datagrid('deleteRow', rowIndex);//删除一行  
            rowIndex--;  
            $('#uploadGoodsListEdit').datagrid('insertRow', {  
                index:rowIndex,  
                row:selectrow  
            });  
            $('#uploadGoodsListEdit').datagrid('selectRow', rowIndex);  
    	}
    }  
};
//下移商品
function downGoods(target,num){
	var rows;
	if(num==1){
		rows=$('#uploadGoodsListAdd').datagrid('getRows');  
	}
	if(num==2){
		rows=$('#uploadGoodsListEdit').datagrid('getRows');  
	}
	var rowlength=rows.length;
	var rowIndex = getRowIndex(target);
	var selectrow = getRowEntity(target,num);
	if(rowIndex==rowlength-1){  
		$.messager.alert("<font color='black'>提示</font>", "底行无法下移!", "warning");  
    }else{  
    	if(num==1){
    		$('#uploadGoodsListAdd').datagrid('deleteRow', rowIndex);//删除一行  
            rowIndex++;  
            $('#uploadGoodsListAdd').datagrid('insertRow', {  
                index:rowIndex,  
                row:selectrow  
            });  
            $('#uploadGoodsListAdd').datagrid('selectRow', rowIndex);    
    	}
    	if(num==2){
    		$('#uploadGoodsListEdit').datagrid('deleteRow', rowIndex);//删除一行  
            rowIndex++;  
            $('#uploadGoodsListEdit').datagrid('insertRow', {  
                index:rowIndex,  
                row:selectrow   
            });  
            $('#uploadGoodsListEdit').datagrid('selectRow', rowIndex);    
    	}
    } 
};
function getRowIndex(target){
	var tr = $(target).closest('tr.datagrid-row');
	var editinde = parseInt(tr.attr('datagrid-row-index'));
	return editinde;
};
function getRowEntity(target,num){
	editindex = getRowIndex(target);
	var rows;
	if(num==1){
		rows = $('#uploadGoodsListAdd').datagrid('getRows');
	}
	if(num==2){
		rows = $('#uploadGoodsListEdit').datagrid('getRows');
	}
	return rows[editindex];
};
//判断是否超时
function ifLogout(data){
	if(data.message=='timeout' && data.result==false){
		$.messager.alert("操作提示", "登录超时, 请重新登录", "info");
		window.top.location = ctx + "/logout";
		return false;
	}
}