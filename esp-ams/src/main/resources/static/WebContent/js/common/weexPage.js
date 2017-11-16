/**
 * 系统参数管理
 */

// 主键id_o
var id_o = "";

$ (function (){
	
	$ ("#editWeexJsInfo").dialog ("close");
	// Grid 列表
	$ ('#weexJslist').datagrid (
	{
	    title : 'weex管理',
	    fit : true,
		toolbar:"#tb",
	    rownumbers : true,
	    pagination : true,
	    singleSelect : true,
	    striped : true,
	    columns : [
		    [
		            {
		                title : 'id',
		                field : 'id',
		                width : 150,
		                align : 'center'
		            },
		            {
		                title : '环境',
		                field : 'weexEve',
		                width : 150,
		                align : 'center'
		            },
					{
		                title : 'weex类型',
		                field : 'weexType',
		                width : 150,
		                align : 'center'
		            },

		            {
		                title : 'ios版本号',
		                field : 'iosVer',
		                width : 100,
		                align : 'center'
		            },
					{
						title : 'android版本号',
						field : 'androidVer',
						width : 100,
						align : 'center'
					},
					{
						title : '归属项目',
						field : 'weexBlong',
						width : 100,
						align : 'center'
					},

		            {
		                title : '修改人',
		                field : 'updateUser',
		                width : 100,
		                align : 'center'
		            },
		            
		            {
		                title : '修改时间',
		                field : 'updateDateStr',
		                width : 150,
		                align : 'center'
		            },
		            {
		                title : '操作',
		                field : 'opt',
		                width : 100,
		                align : 'center',
		                formatter : function (value, row, index)
		                {
			                var content = "";
			                content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton'";
			                content += " onclick='$.editWeexjsInfo(" + JSON.stringify (row) + ");'>编辑</a>";

							return content;
		                }
		            }
		    ]
	    ],
	    
	    loader : function (param, success, error)
	    {
		    $.ajax (
		    {
		        url : ctx + '/application/system/param/listAndriodJs',
		        data : param,
		        type : "post",
		        dataType : "json",
		        success : function (data)
		        {
			        $.validateResponse (data, function ()
			        {
				        success (data);
			        });
		        }
		    })
	    }
	});
	$.editWeexjsInfo = function (data)
	{
		$ ("#editWeexJsInfo").dialog ("open");
		
		$ ("#iosVer").val (data.iosVer);
		$ ("#androidVer").val (data.androidVer);
		$ ("#weexType").combobox ('setValue',data.weexType);
		$ ("#weexFile").val (data.weexFile);
		$ ("#weexId").val (data.id);
		$ ("#weexEve").val (data.weexEve);
		$ ("#weexBlong").val (data.weexBlong);
	}

	$("#bsDiffUpload").click(function () {
		$("#bsdiffFile").value = "";
		$("#bsdiffVer").value = "";

		$("#bsdiffDiv").dialog({
			title : "增量更新上传zip包",
			modal : true,
			width : 400,
			resizable:true,
			buttons:[{
				text : "确定",
				handler : function() {
					var theForm = $("#bsdiffForm");
					var bsdiffFile = $("#bsdiffFile").val();
					if(bsdiffFile == null || bsdiffFile == ''){
						$.messager.alert ('消息', "请选择上传文件.");
					}

					var bsdiffVer = $("#bsdiffVer").val();
					if(bsdiffVer == null || bsdiffVer == ''){
						$.messager.alert ('消息', "请填写版本号(版本号只能是1,2,3......等正整数)");
					}

					theForm.form("submit",{
						url : ctx + '/application/system/param/bsdiffUpload',
						success : function(data) {
							var response = JSON.parse(data);
							if(response.status=="1"){
								$.messager.alert ('消息', response.msg);
								$('#bsdiffDiv').dialog('close');
							}else{
								$.messager.alert("提示", response.msg);
							}

						}
					});
				}
			},{
				text : "取消",
				handler : function() {
					$('#bsdiffDiv').dialog('close');
				}

			}],
		});
	});
	
	


});

// 确认更新weex
function confirmWeexBtn ()
{
	$.messager.confirm ('', '确定要提交吗？', function (r)
	{
		if (r)
		{
			debugger;
			var iosVer = $ ("#iosVer").val ();
			var androidVer = $ ("#androidVer").val ();
			var weexType = $ ("#weexType").combobox ('getValue');
			var weexFile = $ ("#weexFile").val ();
			var weexId = $ ("#weexId").val ();
			var weexEve = $ ("#weexEve").val ();
			var weexBlong = $("#weexBlong").val();

			
			// 验证参数
			if (iosVer == null || iosVer == "")
			{
				$.messager.alert ('消息', "ios版本号不能为空");
				return false;
			}
			if (androidVer == null || androidVer == "")
			{
				$.messager.alert ('消息', "android版本号不能为空");
				return false;
			}
			if (weexType == null || weexType == "")
			{
				$.messager.alert ('消息', "weex类型不能为空");
				return false;
			}
			if (weexFile == null || weexFile == "")
			{
				$.messager.alert ('消息', "weex上传文件不能为空");
				return false;
			}

			var theForm = $("#weexJsForm");
			theForm.form("submit",{
				url : ctx + '/application/system/param/updateWeex2',
				success : function(data) {
					var response = JSON.parse(data);
					console.log(response);
					if(response.status=="1"){
						$.messager.alert ('消息', response.msg);
						closeWeexBtn ();
						var params = {};
						$ ('#weexJslist').datagrid ('load', params);
					}else{
						$.messager.alert("提示", response.msg);
					}

				}
			});

		}
	});
	
}
// 关闭窗口
function closeWeexBtn ()
{
	$ ("#editWeexJsInfo").dialog ("close");
}
