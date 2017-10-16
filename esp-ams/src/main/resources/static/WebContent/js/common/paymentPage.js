/**
 * 京东商品售价管理
 */

// 主键id_o
var id_o = "";

$ (function ()
{
	
	$ ("#paymentParamInfo").dialog ("close");
	// Grid 列表
	$ ('#paymentParamlist').datagrid (
	{
	    title : '支付方式选项管理',
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
		                align : 'center',
						hidden: true
		            },
		            
		            {
		                title : '支付宝支付',
		                field : 'alipay',
		                width : 200,
		                align : 'center'
		            },
		            
		            {
		                title : '银联支付',
		                field : 'backUnion',
		                width : 200,
		                align : 'center'
		            }
		            ,
		            {
		                title : '操作',
		                field : 'opt',
		                width : 100,
		                align : 'center',
		                formatter : function (value, row, index)
		                {
			                var content = "";
			                content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton'";
			                content += " onclick='$.editPaymentParamInfo(" + JSON.stringify (row) + ");'>编辑</a>";
			                return content;
		                }
		            }
		    ]
	    ],
	    
	    loader : function (param, success, error)
	    {
		    $.ajax (
		    {
		        url : ctx + '/application/system/param/payment/query',
		        data : param,
		        type : "post",
		        dataType : "json",
		        success : function (data)
		        {
			        $.validateResponse (data, function ()
			        {
			        	console.log(data);
				        success (data);
			        });
		        }
		    })
	    }
	});

	$.editPaymentParamInfo = function (data)
	{
		debugger;
		id_o = data.id;
		$("#paymentParamInfo").dialog("open");
		$("#alipay").combobox('setValue',data.alipay == '显示'?'1':'0');
		$("#backUnion").combobox('setValue',data.backUnion == '显示'?'1':'0');
	}
});
// 确认修改京东价格系统参数
function confirmBtn ()
{
	$.messager.confirm ('支付方式参数', '确定要提交吗？', function (r)
	{
		if (r)
		{
			var alipay = $ ("#alipay").combobox('getValue');
			var backUnion =  $ ("#backUnion").combobox('getValue');
			var param =  {
					alipay : alipay,
					backUnion: backUnion
			}
			$.ajax (
			{
			    url : ctx + '/application/system/param/payment/update',
			    data :JSON.stringify(param),
			    type : "post",
			    dataType : "json",
				contentType: 'application/json',
			    success : function (data)
			    {
				    $.messager.alert ('消息', data.msg);
				    closeBtn ();
				    $ ('#paymentParamlist').datagrid ('load', {});
			    }
			})

		}
	});
}
function closeBtn ()
{
	$ ("#paymentParamInfo").dialog ("close");
}