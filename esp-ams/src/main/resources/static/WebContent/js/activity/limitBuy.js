/**
 * goodsAttribute
 */
$(function () {
	$('#upLoadGoods').window('close');
	var limitBuyActId ;
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
		$('#upLoadGoods').window('open');
	});
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
				if(response.status=="1"){
					//刷新商品列表
					$('#upLoadGoods').window('close');
					debugger
					var da = response.data;
					$('#uploadGoodsList').datagrid('loadData', { total: da.length, rows: [da]});
				}
				$.messager.alert("提示", response.msg, "info");
		    }
		});
		thisForm.submit();
	});
	$("#upLoadGoodsFromCancle").click(function() {
		$('#upLoadGoods').window('close');
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
            title : '属性主键',
            field : 'id',
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
         			return new Date(value).Format("yyyy-MM-dd HH:mm:ss");
         		}
             }
         },{
        	 title : '商品下架时间',
             field : 'delistTime',
             width : 90,
             align : 'center',
             formatter:function(value,row,index){
         		if(value!=null){
         			return new Date(value).Format("yyyy-MM-dd HH:mm:ss");
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
                var content = "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='$.editGoods(\"" + row.id + "\");'>编辑</a>&nbsp;";
                content += "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='$.deleGoods(\"" + row.id + "\");'>删除</a>";
                content += "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='$.upGoods(\"" + row.id + "\");'>上移</a>";
                content += "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='$.downGoods(\"" + row.id + "\");'>下移</a>";
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