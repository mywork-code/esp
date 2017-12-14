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
        		return "24Hours";
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
                        $('#commonLayer').show(100,commonLayerShow());
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
    });
    //添加按钮事件
    $(".add-btn").click(function() {
    	status = ""
    	limitBuyActId = "";
    	$("#startDayAdd").datebox('setValue', '');
    	$("#startTimeAdd").combobox('setValue','');
    	$("#startDayAdd").datebox({'disabled': false }); 
		$("#startTimeAdd").combobox({'disabled': false }); 
    	var params = {};
    	$('#uploadGoodsList').datagrid('load', params);
		$('#editLayer').show(500,editLayerShow());
    });
    $.editDetails = function(id,startDate,startTime,status) {
    	status = status;
    	if(status=="2"){//进行中活动
    		$("#startDayAdd").datebox({'disabled': true }); 
    		$("#startTimeAdd").combobox({'disabled': true }); 
    	}
    	if(status=="1"){//未开始活动
    		$("#startDayAdd").datebox({'disabled': false }); 
    		$("#startTimeAdd").combobox({'disabled': false }); 
    	}
    	limitBuyActId = id;
    	$("#startDayAdd").datebox('setValue', new Date(startDate/1).Format("yyyy-MM-dd"));
    	$("#startTimeAdd").combobox('setValue',startTime);
    	var params = {};
    	params['limitBuyActId']=limitBuyActId;
    	$('#uploadGoodsList').datagrid('load', params);
		$('#editLayer').show(500,editLayerShow());
	};
    //返回
    $(".cancel-btn").click(function() {
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
	//导入商品  弹窗
	$(".upload-btn").click(function() {
		if(status!=""){
			$.messager.alert("提示", "编辑商品不能上传商品列表,请点击新增活动!", "info");
			return;
		}
		$("#upLoadGoodsFile").val('');
		$('#upLoadGoods').window({modal: true});
		$('#upLoadGoods').window('open');
	});
	//导入商品  弹窗 确定
	$("#upLoadGoodsFromSumbit").click(function() {
		var file=$("#upLoadGoodsFile").val();
		if (null == file || ("") == file) {
			$.messager.alert("提示", "请选择上传商品文件!", "info");
			return;
		}
		if(limitBuyActId!=""){
			$("#limitBuyActId").val(limitBuyActId);
		}
		var thisForm = $("#upLoadGoodsFrom");
		thisForm.form({
			url : ctx + '/activity/limitBuyActContro/upLoadLimitGoodsSku',
			success : function(data) {
				var response = JSON.parse(data);
				$.messager.alert("提示", response.msg, "info");
				if(response.status=="1"){
					//刷新商品列表
					$('#upLoadGoods').window('close');
					$('#uploadGoodsList').datagrid('loadData', response);
				}
		    }
		});
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
			$.messager.alert("提示", "请输入限购总量!", "info");
			return;
		}
		if (null == limitNumAdd || ("") == limitNumAdd) {
			$.messager.alert("提示", "请输入每人限购!", "info");
			return;
		}
		if (limitNumTotalAdd < limitNumAdd) {
			$.messager.alert("提示", "限购总量不可小于每人限购!", "info");
			return;
		}
		if (stockCurrAmt < limitNumTotalAdd) {
			$.messager.alert("提示", "限购总量不可大于库存剩余!", "info");
			return;
		}
		var filefalg = null == file || ("") == file;
		if(limitGoodsSkuId!=""){
			$("#limitGoodsSkuId").val(limitGoodsSkuId);
		}
		if(!filefalg){
			var thisForm = $("#editGoodsFrom");
			//此处上传缩略图
			//把URL返回在table里
			//把限购数量返回在table里
			$('#uploadGoodsList').datagrid('updateRow',{
				index: editindex,
				row:{
					"limitNumTotal":limitNumTotalAdd,
					"limitNum":limitNumAdd,
					"url":"123",
					}
			});
		}
		$('#uploadGoodsList').datagrid('updateRow',{
			index: editindex,
			row:{
				"limitNumTotal":limitNumTotalAdd,
				"limitNum":limitNumAdd,
				}
		});
		$('#editGoods').window('close');
	});
	//编辑商品  上传URL 弹窗   取消
	$("#editGoodsFromCancle").click(function() {
		$('#editGoods').window('close');
	});
	/**保存所有数据   限时购   和   限时购商品列表  （新增 修改）	 */
	$(".save-btn").click(function() {
		var startDay = $("#startDayAdd").datebox('getValue');
		var startTime = $("#startTimeAdd").combobox('getValue');
		var goodsrows = $('#uploadGoodsList').datagrid('getRows');
		if(startDay==""||startTime==""){
			$.messager.alert("提示", "请输入限时购活动开始日期和时间!", "info");
			return;
		}
		if(goodsrows==""||goodsrows.length==0){
			$.messager.alert("提示", "请上传限时购活动商品!", "info");
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
				if(data.status==1){
					var params = {};
			    	$('#limitBuyActPage').datagrid('load', params);
			    	$('#editLayer').hide(500,editLayerHide());
				}
				$.messager.alert("提示", data.msg, "info");
			}
		})
	});
});
function commonLayerShow(){
	$('#editLayer').hide();
}
function editLayerShow(){
	$('#commonLayer').hide();
}
function editLayerHide(){
	$('#commonLayer').show();
}
function getLimitGoodsList(){
	//商品列表
    $('#uploadGoodsList').datagrid({
//    	title : '限时购活动商品列表',
    	fit : false,
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
            title : '商品名称',
            field : 'goodsName',
            width : 90,
            align : 'center',
        },{
            title : '商品编码',
            field : 'goodsCode',
            width : 80,
            align : 'center',
        },{
            title : 'SKUID',
            field : 'skuId',
            width : 90,
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
             field : 'status',
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
             width : 80,
             align : 'center',
         },{
        	 title : '限购总量',
             field : 'limitNumTotal',
             width : 80,
             align : 'center',
         },{
        	 title : '每人限购',
             field : 'limitNum',
             width : 80,
             align : 'center',
         },{
            title : '操作',
            field : 'opt',
            width : 130,
            align : 'center',
            formatter : function(value, row, index) {
            	if(row.upLoadStatus==1){
            		var content = "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='editGoods(this);'>编辑</a>&nbsp;";
                    content += "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='upGoods(this);'>上移</a>";
                    content += "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='downGoods(this);'>下移</a>";
                    return content;
            	}else{
            		return "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='deleGoods(this);'>删除</a>";
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
function functionx(data){
	debugger
	var pager = $("#uploadGoodsList").datagrid("getPager"); 
	pager.pagination({ 
		total:data.total, 
		onSelectPage:function (pageNo, pageSize) { 
		    var start = (pageNo - 1) * pageSize; 
		    var end = start + pageSize; 
		    $("#uploadGoodsList").datagrid("loadData", data.slice(start, end)); 
		    pager.pagination('refresh', { 
		    	total:data.total, 
		    	pageNumber:pageNo 
		    });
		} 
	});
}
 
var limitGoodsSkuId ;//商品ID
var stockCurrAmt ;//商品库存剩余
var editindex;//商品表行号
//编辑商品弹框
function editGoods(target){
	var rowentity = getRowEntity(target);
	limitGoodsSkuId = rowentity.id;
	$("#editGoodsFile").val('');
	if(limitGoodsSkuId==""||limitGoodsSkuId==null){
		stockCurrAmt = rowentity.stockCurrAmt;
		$("#limitNumTotalAdd").textbox('clear');
		$("#limitNumAdd").textbox('clear');
		
		
		//有问题需要解决   有微调器失效BUG
		$("#limitNumTotalAdd").textbox({'disabled':false});
		$("#limitNumAdd").textbox({'disabled':false});
		$('#editGoods').window({modal: true});
		$('#editGoods').window('open');
	}else{
		$("#limitNumTotalAdd").textbox('setValue',rowentity.limitNumTotal);
		$("#limitNumAdd").textbox('setValue',rowentity.limitNum);
		$.ajax({url : ctx + "/activity/limitBuyActContro/getLimitBuyActStatus",data : {"id":limitGoodsSkuId},type : "post",dataType : "json",
            success : function(data) {
            	var sta = data.data;
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
function deleGoods(target){
	var rowentity = getRowEntity(target);
	if(rowentity.id!=null){
		//此处删除数据库中数据！！
	}
	$('#uploadGoodsList').datagrid('deleteRow', getRowIndex(target));
};
//上移商品
function upGoods(target){
	var rowIndex = getRowIndex(target);
	var selectrow = getRowEntity(target);
	if(rowIndex==0){  
        $.messager.alert('提示', '顶行无法上移!', 'warning');  
    }else{  
        $('#uploadGoodsList').datagrid('deleteRow', rowIndex);//删除一行  
        rowIndex--;  
        $('#uploadGoodsList').datagrid('insertRow', {  
            index:rowIndex,  
            row:selectrow  
        });  
        $('#uploadGoodsList').datagrid('selectRow', rowIndex);  
    }  
};
//下移商品
function downGoods(target){
	var rows=$('#uploadGoodsList').datagrid('getRows');  
	var rowlength=rows.length;
	var rowIndex = getRowIndex(target);
	var selectrow = getRowEntity(target);
	if(rowIndex==rowlength-1){  
        $.messager.alert('提示', '底行无法下移!', 'warning');  
    }else{  
        $('#uploadGoodsList').datagrid('deleteRow', rowIndex);//删除一行  
        rowIndex++;  
        $('#uploadGoodsList').datagrid('insertRow', {  
            index:rowIndex,  
            row:selectrow  
        });  
        $('#uploadGoodsList').datagrid('selectRow', rowIndex);  
    } 
};
function getRowIndex(target){
	var tr = $(target).closest('tr.datagrid-row');
	var editindex = parseInt(tr.attr('datagrid-row-index'));
	return editindex;
};
function getRowEntity(target){
	editindex = getRowIndex(target);
	var rows = $('#uploadGoodsList').datagrid('getRows');
	return rows[editindex];
};