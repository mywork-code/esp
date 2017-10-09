$(function(){
	
    //Grid
    $('#list').datagrid({
        title : '活动配置',
        fit : true,
        fitColumns : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        striped:true,
        nowrap:false,
        toolbar : '#tb',
        columns :[[
            {
                title : '活动名称',
                field : 'activityName',
                width : 150,
                align : 'center'
            }, {
                title : '开始时间',
                field : 'startTime',
                width : 150,
                align : 'center'
            },{
                title : '结束时间',
                field : 'endTime',
                width : 120,
                align : 'center'
            },
            {
                title : '活动类型',
                field : 'activityType',
                width : 120,
                align : 'center'
            },
            {
                title : '活动状态',
                field : 'status',
                width : 120,
                align : 'center'
            },{
				title : '操作',
				field : 'opt',
				width : 120,
				align : 'center',
				formatter : function(value, row, index) {
					var content = "";
                    content += "&nbsp;<a href='javascript:alert();' class='easyui-linkedbutton'>编辑</a>";
				 return content;
			}}]],
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + '/activity/cfg/list',
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
    
    
    $("#add").click(function(){
    	window.location.href= ctx + '/activity/cfg/add';
    })

    $(".search-btn").click(function() {
        debugger;
        var params = {'status':$('#status').combobox('getValue')};
        var stat = $('#status').combobox('getValue');

        $('#list').datagrid('load', params);
    });
});