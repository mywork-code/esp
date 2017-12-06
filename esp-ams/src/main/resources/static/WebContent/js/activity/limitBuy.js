/**
 * goodsAttribute
 */
$(function () {
	$('#upLoadGoods').window('close');
	$('#editGoods').window('close');
	var limitBuyActId ;//活动ID
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
        			return new Date(value).Format("yyyy-MM-dd HH:mm:ss");
        		}
            }
        },{
            title : '活动结束时间',
            field : 'endDate',
            width : 200,
            align : 'center',
            formatter:function(value,row,index){
        		if(value!=null){
        			return new Date(value).Format("yyyy-MM-dd HH:mm:ss");
        		}
            }
        },{
            title : '有效时长',
            field : 'opt',
            width : 200,
            align : 'center',
            formatter : function(value, row, index) {
            	return "24Hours";
            }
        },{
            title : '活动状态',
            field : 'status',
            width : 200,
            align : 'center',
            formatter:function(value,row,index){
    			return "jinxingzhong";
            }
        },{
            title : '操作',
            field : 'opt',
            width : 200,
            align : 'center',
            formatter : function(value, row, index) {
                var content = "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='$.editDetails(\"" + row.id + "\");'>编辑活动</a>&nbsp;";
                content += "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='$.deleteDetails(\"" + row.id + "\");'>删除活动</a>";
                return content;
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
                        success(resp);
                        $('#commonLayer').show(100,commonLayerShow());
                        getLimitGoodsList();
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
    	limitBuyActId = "";
    	$("#startDayAdd").datebox('setValue', '');
    	$("#startTimeAdd").combobox('setValue','');
    	var params = {};
    	$('#uploadGoodsList').datagrid('load', params);
		$('#editLayer').show(500,editLayerShow());
    });
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
		if (null == file || ("") == file) {
			$.messager.alert("提示", "请选择上传缩略图文件!", "info");
			return;
		}
		if(limitGoodsSkuId!=""){
			$("#limitGoodsSkuId").val(limitGoodsSkuId);
		}
		var thisForm = $("#editGoodsFrom");
		//此处上传缩略图
		//把URL返回在table里
		//吧限购数量返回在table里
		$('#uploadGoodsList').datagrid('updateRow',{
			index: editindex,
			row:{
				"limitNumTotal":limitNumTotalAdd,
				"limitNum":limitNumAdd,
				"url":"123",
				}
		});
		$('#editGoods').window('close');
	});
	//编辑商品  上传URL 弹窗   取消
	$("#editGoodsFromCancle").click(function() {save-btn
		$('#editGoods').window('close');
	});
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
			startDay:startDay,
			startTime:startTime,
			list:goodsrows
		};
		$.ajax({url : ctx + '/activity/limitBuyActContro/addLimitBuyAct',
			data :JSON.stringify(param),
			type : "POST",
			contentType: 'application/json',
            dataType: "json",
			success : function(data) {
				
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
    	fit : true,
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
            width : 90,
            align : 'center',
        },{
            title : 'SKUID',
            field : 'skuId',
            width : 90,
            align : 'center',
        },{
            title : '商户名称',
            field : 'merchantName',
            width : 90,
            align : 'center',
        },{
        	 title : '类目名称',
             field : 'categoryId1Name',
             width : 90,
             align : 'center',
         },{
        	 title : '商品状态',
             field : 'status',
             width : 90,
             align : 'center',
         },{
        	 title : '商品上架时间',
             field : 'listTime',
             width : 90,
             align : 'center',
             formatter:function(value,row,index){
         		if(value!=null){
         			return new Date(value).Format("yyyy-MM-dd");
         		}
             }
         },{
        	 title : '商品下架时间',
             field : 'delistTime',
             width : 90,
             align : 'center',
             formatter:function(value,row,index){
         		if(value!=null){
         			return new Date(value).Format("yyyy-MM-dd");
         		}
             }
         },{
        	 title : '库存剩余',
             field : 'stockCurrAmt',
             width : 90,
             align : 'center',
         },{
        	 title : '限购总量',
             field : 'limitNumTotal',
             width : 90,
             align : 'center',
         },{
        	 title : '每人限购',
             field : 'limitNum',
             width : 90,
             align : 'center',
         },{
            title : '操作',
            field : 'opt',
            width : 130,
            align : 'center',
            formatter : function(value, row, index) {
                var content = "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='editGoods(this);'>编辑</a>&nbsp;";
                content += "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='deleGoods(this);'>删除</a>";
                content += "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='upGoods(this);'>上移</a>";
                content += "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='downGoods(this);'>下移</a>";
                return content;
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
//编辑商品弹框
function editGoods(target){
	var rowentity = getRowEntity(target);
	limitGoodsSkuId = rowentity.id;
	stockCurrAmt = rowentity.stockCurrAmt;
	$("#editGoodsFile").val('');
	$("#limitNumTotalAdd").textbox('clear');
	$("#limitNumAdd").textbox('clear');
	$('#editGoods').window({modal: true});
	$('#editGoods').window('open');
};
//删除商品弹框
function deleGoods(target){
	var rowentity = getRowEntity(target);
	if(rowentity.id==null){
		//此处删除数据库中数据！！
	}
	$('#uploadGoodsList').datagrid('deleteRow', getRowIndex(target));
};
//上移商品
function upGoods(target){
	var rowentity = getRowEntity(target);
};
//下移商品
function downGoods(target){
	var rowentity = getRowEntity(target);
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