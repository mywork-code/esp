/**
 * 系统参数管理
 */

// 主键id_o
var id_o = "";

$ (function ()
{
	
	$ ("#editSystemParamInfo").dialog ("close");
	// Grid 列表
	$ ('#systemparamlist').datagrid (
	{
	    title : '系统参数',
	    fit : true,
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
		                title : '商户结算折扣率(成本价/市场价)',
		                field : 'merchantSettleRate',
		                width : 200,
		                align : 'center'
		            },
		            
//		            {
//		                title : '商品价格折扣率(市场价*折扣率=当前售价)',
//		                field : 'goodsPriceRate',
//		                width : 250,
//		                align : 'center'
//		            },
		            {
		            	title : '保本率(售价/成本价*100%=保本率)',//保留两位小数，四舍五入
		            	field : 'priceCostRate',
		            	width : 250,
		            	align : 'center',
		            	formatter:function(value, row, index){
		            		if(value != null && value !=''){
		            			return value+"%";
		            		}
		            	}
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
			                content += " onclick='$.editSystemParamInfo(" + JSON.stringify (row) + ");'>编辑</a>";
			                
			                content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton'";
			                content += " onclick='$.querySystemParamInfo(" + JSON.stringify (row) + ");'>刷新</a>";
			                return content;
		                }
		            }
		    ]
	    ],
	    
	    loader : function (param, success, error)
	    {
		    $.ajax (
		    {
		        url : ctx + '/application/system/param/pagelist',
		        data : param,
		        type : "post",
		        dataType : "json",
		        success : function (data)
		        {
			        $.validateResponse (data, function ()
			        {
			        	debugger;
			        	console.log(data);
				        success (data);
			        });
		        }
		    })
	    }
	});
	$.editSystemParamInfo = function (data)
	{
		id_o = data.id;
		$ ("#editSystemParamInfo").dialog ("open");
		$ ("#merchantSettleRate").val (data.merchantSettleRate);
//		$ ("#goodsPriceRate").val (data.goodsPriceRate);
		$ ("#priceCostRate").val (data.priceCostRate);
	}

	$.querySystemParamInfo = function (data)
	{
		var params = {};
		$ ('#systemparamlist').datagrid ('load', params);
	}

});

// 确认修改系统参数
function confirmBtn ()
{
	$.messager.confirm ('订单信息', '确定要提交吗？', function (r)
	{
		if (r)
		{
			var merchantSettleRate = $ ("#merchantSettleRate").val ();
//			var goodsPriceRate = $ ("#goodsPriceRate").val ();
			var priceCostRate = $ ("#priceCostRate").val ();
			
			// 验证参数
			if (merchantSettleRate < 0 || merchantSettleRate > 1)
			{
				$.messager.alert ('消息', "商户结算折扣率字段不合法，必须在0到1之间");
				return false;
			}
			
//			if (goodsPriceRate < 0 || goodsPriceRate > 1)
//			{
//				$.messager.alert ('消息', "商品价格折扣率字段不合法，必须在0到1之间");
//				return false;
//			}
			if (priceCostRate < 0)
			{
				$.messager.alert ('消息', "保本率字段不合法，必须大于0");
				return false;
			}
			
			var id = id_o;
			$.ajax (
			{
			    url : ctx + '/application/system/param/update',
			    data :
			    {
			        "id" : id,
			        "merchantSettleRate" : merchantSettleRate,
//			        "goodsPriceRate" : goodsPriceRate,
			        "priceCostRate" : priceCostRate,
			    },
			    type : "post",
			    dataType : "json",
			    success : function (data)
			    {
				    $.messager.alert ('消息', data.msg);
				    closeBtn ();
				    var params = {};
				    $ ('#systemparamlist').datagrid ('load', params);
			    }
			})

		}
	});
	
}
// 关闭窗口
function closeBtn ()
{
	$ ("#editSystemParamInfo").dialog ("close");
}
