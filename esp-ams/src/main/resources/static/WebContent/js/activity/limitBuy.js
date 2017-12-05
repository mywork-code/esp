/**
 * goodsAttribute
 */
$(function () {
	// 列表
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
    //添加按钮事件
    $(".add-btn").click(function() {
    	$("#name").textbox('clear');
    	$('#addAttr').window({
			shadow : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			modal : true
		});
		//打开编辑弹出框
		$('#addAttr').window('open');
    });
});