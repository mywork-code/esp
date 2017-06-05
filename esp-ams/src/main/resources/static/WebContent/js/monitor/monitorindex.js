$(function(){
    
   // 添加按钮事件
	$(".add-btn").click(function() {
		$('#monitorForm').form('load', {
			host : '',
			application : '',
			methodName : '',
			methodDesciption : ''
		});
		$("#monitorDetails").dialog({
			modal : true,
			title : "<font color='black'>添加配置</font>",
			width : 500,
			height : 250,
			buttons : [ {
				text : "确定",
				handler : function() {
					$("#monitorForm").submit();
				}
			}, {
				text : "关闭",
				handler : function() {
					$("#monitorDetails").dialog("close");
				}
			} ]
		});
	});

	// 表单提交
	$("#monitorForm").form({
		url : ctx + "/noauth/monitor/addMonitor",
		method : 'POST',
		onSubmit : function() {
			var host = $("#monitorForm #host").val();
			if ($.trim(host) == "") {
				$.messager.alert("<font color='black'>操作提示</font>", "主机不能为空", "warning");
				return false;
			}
			var application = $("#monitorForm #application").val();
			if ($.trim(application) == "") {
				$.messager.alert("<font color='black'>操作提示</font>", "应用名称不能为空", "warning");
				return false;
			}
			var methodName = $("#monitorForm #methodName").val();
			if ($.trim(methodName) == "") {
				$.messager.alert("<font color='black'>操作提示</font>", "方法名称不能为空", "warning");
				return false;
			}
			var methodDesciption = $("#monitorForm #methodDesciption").val();
			if ($.trim(methodDesciption) == "") {
				$.messager.alert("<font color='black'>操作提示</font>", "方法描述不能为空", "warning");
				return false;
			}
		},
		success : function(data) {
			var json =  $.parseJSON(data);
			$.validateResponse(json, function() {
				$("#monitorDetails").dialog("close");
				$(".search-btn").click();
			});
		}
	});
	
  //查询
    $(".search-btn").click(function () {
        var params = {};
        //params['days'] = $("#days").combobox('getvalue');
        $('#list').datagrid('load', params);
    });
    
    //Grid
    $('#list').datagrid({
        title : '接口监控',
        fit : true,
        fitColumns : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        striped:true,
        toolbar : '#tb',
        columns :[[
             {
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
            ]],
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + '/noauth/monitor/monitorlist',
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
