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
	    title : 'WEEX版本管理',
	    fit : true,
		toolbar:"#tb",
	    rownumbers : true,
	    pagination : true,
	    singleSelect : true,
	    striped : true,
	    columns : [
		    [
		            {
		                title : 'NO',
		                field : 'id',
		                width : 150,
		                align : 'center'
		            },
					{
						title : 'ID',
						field : 'lineId',
						width : 100,
						align : 'center'
					},
		            {
		                title : 'VER',
		                field : 'bsdiffVer',
		                width : 100,
		                align : 'center'
		            },
					{
						title : 'isForce',
						field : 'ifCompelUpdate',
						width : 100,
						align : 'center'
					}
		    ]
	    ],
	    
	    loader : function (param, success, error)
	    {
		    $.ajax (
		    {
		        url : ctx + '/application/system/param/list',
		        data : param,
		        type : "post",
		        dataType : "json",
		        success : function (data)
		        {
					debugger;
			        $.validateResponse (data, function (){
				        success (data);
			        });
		        }
		    })
	    }
	});

	$("#bsDiffUpload").click(function () {
		$("#bsdiffFile").val("");
		$("#lineId").val("");
		$("#bsdiffVer").val("");
		$("#ifCompelUpdate").combobox("clear");

		$("#bsdiffDiv").dialog({
			title : "增量更新上传zip包",
			modal : true,
			width : 400,
			resizable:true,
			buttons:[{
				text : "确定",
				handler : function() {
					//$('.ui-dialog-buttonpane').find('button:contains("确定")').attr("disabled", "disabled");
					var theForm = $("#bsdiffForm");
					var bsdiffFile = $("#bsdiffFile").val();
					if(bsdiffFile == null || bsdiffFile == ''){
						$.messager.alert ('消息', "请选择上传文件.");
						return;
					}

					var bsdiffVer = $("#bsdiffVer").val();
					if(bsdiffVer == null || bsdiffVer == ''){
						$.messager.alert ('消息', "请填写版本号(版本号只能是1,2,3......等正整数)");
						return;
					}

					theForm.form("submit",{
						url : ctx + '/noauth/application/system/param/bsdiffUpload',
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
