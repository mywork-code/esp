$(function(){
	//置空
    $("#reset").click(function(){
    	$("#monitorTime").textbox('setValue');
    	$("#monitorTimes").textbox('setValue');
    });
    
    //配置
    $("#add").click(function(){
    	debugger;
    	var time = $("#monitorTime").textbox('getValue');
    	var times = $("#monitorTimes").textbox('getValue');
    	
    	if(time == null || time == ''){
			$.messager.alert("提示", "请输入时间", "info");
			return;
		}
    	if(isNaN(time)){
    		$.messager.alert("提示", "时间必须为数字", "info");
    		return;
    	}
    	if(times == null || times == ''){
			$.messager.alert("提示", "请输入次数", "info");
			return;
		}
    	if(isNaN(times)){
    		$.messager.alert("提示", "次数必须为数字", "info");
    		return;
    	}
    	var params = {"monitorTime":time,"monitorTimes":times};
    	$.ajax({
            url : ctx + '/noauth/monitor/editMonitorSetUp',
            data : JSON.stringify(params),
            contentType : 'application/json',
            type : "POST",
            dataType : "json",
            success : function(data) {
            	$.messager.alert("提示",data.msg,'info');
            }
        });
    	
    });
	
	
    //Grid
    $('#list').datagrid({
        title : '接口监控统计',
        fit : true,
        fitColumns : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        striped:true,
        toolbar : '#tb',
        columns :[[
            {
                title : '主机',
                field : 'host',
                width : 150,
                align : 'center'
            }, {
                title : '应用',
                field : 'application',
                width : 150,
                align : 'center'
            },
            {
                title : '方法',
                field : 'methodName',
                width : 120,
                align : 'center',
                formatter: function (value,row, index) {
                	return  "<span title='"+value+"'>"+value+"</span>"
                }
            },
            {
                title : '方法描述',
                field : 'methodDesciption',
                width : 150,
                align : 'center'
            }
            ,
            {
                title : '调用总次数',
                field : 'totalInvokeNum',
                width : 150,
                align : 'center'
            }
            ,
            {
                title : '成功次数',
                field : 'successInvokeNum',
                width : 150,
                align : 'center'
            }
            ,
            {
                title : '失败次数',
                field : 'failInvokeNum',
                width : 150,
                align : 'center'
            }
            ,
            {
                title : '成功调用的平均时间(ms)',
                field : 'avgTime',
                width : 150,
                align : 'center'
            }
            ]],
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + '/noauth/monitor/list',
                data : param,
                type : "get",
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
});
