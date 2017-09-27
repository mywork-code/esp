$(function(){
	
    $('#importFileList').datagrid({
        title : '商品池',
        fit : true,
        rownumbers : true,
        pagination : true,
        singleSelect: false, //允许选择多行  
        selectOnCheck: true,   
        checkOnSelect: false,
        striped:true,
        toolbar : '#tb',
        rowStyler:function(rowIndex,rowData){
        	if(rowData.colFalgt=='1'){
        		return 'background-color:#6293BB;';
        	}
        },
        columns : [[ { field: 'ck', checkbox: true, width: '30' },  //复选框 
            {
                title : '商品编号',
                field : 'goodsCode',
                width : 150,
                align : 'center'
            }, {
                title : 'skuid',
                field : 'skuId',
                width : 150,
                align : 'center'
            },{
                title : '商品名称',
                field : 'goodsName',
                width : 120,
                align : 'center'
            },
            {
                title : '商品状态',
                field : 'goodsStatus',
                width : 120,
                align : 'center'
            },{
                title : '商品类目（三级）',
                field : 'goodsCategory',
                width : 120,
                align : 'center'
            },{
                title : '成本价',
                field : 'goodsCostPrice',
                width : 120,
                align : 'center'
            },{
                title : '售价',
                field : 'goodsPrice',
                width : 120,
                align : 'center'
            },{
                title : '市场价',
                field : 'marketPrice',
                width : 120,
                align : 'center'
            },{
                title : '活动价',
                field : 'activityPrice',
                width : 120,
                align : 'center'
            },{
                title : '分组',
                field : 'groupName',
                width : 120,
                align : 'center'
            },{
                title : '备注',
                field : 'detailDesc',
                width : 120,
                align : 'center',
                formatter : function(value, row, index) {
                	if(value=='1'){
                		return "导入成功";
                	}else{
                		return "导入失败";
                	}
                }
            },{
				title : '操作',
				field : 'opt',
				width : 120,
				align : 'center',
				formatter : function(value, row, index) {
					var content = "";
                    content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton'";
                    content += " onclick='$.editConfig(\"" + row.id + "\",\"" + row.homeName + "\",\"" + row.startTime + "\",\"" + row.endTime + "\",\"" + row.activeLink + "\",\"" + row.logoUrl + "\");'>添加至</a>";
				 return content;
			}}]],
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + '/application/activity/list',
                data : param,
                type : "post",
                dataType : "json",
                success : function(data) {
                	console.log(data);
                    $.validateResponse(data, function() {
                        success(data);
                    });
                }
            })
        }
    });
    //加载商品类目信息
    goodsCategoryComboFun();
    // 查询列表
    $("#searchGoods").click(function() {
        var params = {};
        params['goodsCode'] = $("#goodsCode").textbox('getValue');
        params['skuId'] = $("#skuId").textbox('getValue');
        var goodsCategoryCombo=$("#goodsCategoryCombo").combotree('getValue');
        if("请选择"==goodsCategoryCombo){
        	goodsCategoryCombo="";
        }
        params['goodsCategory']=goodsCategoryCombo;
        $('#importFileList').datagrid('load', params);
    });
    // 刷新列表
    $("#resetGoods").click(function() {
    	$("#goodsCode").textbox('setValue','');
    	$("#skuId").textbox('setValue','');
    	$("#goodsCategoryCombo").combotree('setValue','');
    	$("#goodsCategoryCombo").combotree('setValue', '请选择');
    	var params = {};
    	$('#importFileList').datagrid('load', params);
    });

    //导入
    $("#import").click(function(){
    	$("#activityId").val("4");
    	var form = $("#ExcelFileForm");
    	var file = $("#Excelfile").val();
    	if(file == null || file == ''){
    		$.messager.alert("<font color='black'>提示</font>","请选择要上传的Excel文件","info");
    		return;
    	}
    	form.form("submit",{
    		url : ctx + '/application/activity/importFile',
    		success : function(response) {
    			var data = JSON.parse(response);
    			if(data.status == '1'){
    				$.messager.alert("<font color='black'>提示</font>", data.msg, "info");
    				$('#Excelfile').val('');
    				var param = getParam();
 			        $('#list').datagrid('load', param);
    			}else{
    				$.messager.alert("<font color='black'>提示</font>", data.msg, "info");
    			}
    		}
    	});
    });
    
    
});