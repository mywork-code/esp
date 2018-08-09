/**
 * 京东商品售价管理
 */

// 主键id_o
var id_o = "";

$ (function ()
{
	
	$ ("#editJDSystemParamInfo").dialog ("close");
	// Grid 列表
	$ ('#jdSystemparamlist').datagrid (
	{
	    title : '京东商品售价管理',
	    fit : true,
	    rownumbers : true,
	    pagination : true,
	    singleSelect : true,
	    striped : true,
	    columns : [[
		            {
		                title : 'id',
		                field : 'id',
		                width : 150,
		                align : 'center',
						hidden: true
		            },
					{
						title : '名称',
						field : 'flag',
						width : 150,
						align : 'center',
                        formatter:function(value,row,index){
							if(value == "1"){
                                return "商品定价系数";
							}else{
                                return "商品下架系数";
							}
                        }
					},

		            {
		                title : '京东价(99-500)比例系数',
		                field : 'protocolPrice1',
		                width : 200,
		                align : 'center'
		            },
		            
		            {
		            	title : '京东价(500-2000)比例系数',//保留两位小数，四舍五入
		            	field : 'protocolPrice2',
		            	width : 250,
		            	align : 'center'
		            },
		            
		            {
		                title : '京东价(2000及以上)比例系数',
		                field : 'protocolPrice3',
		                width : 200,
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
			                content += " onclick='$.editJDSystemParamInfo(" + JSON.stringify (row) + ");'>编辑</a>";
			                return content;
		                }
		            }
				   ]],
	    
	    loader : function (param, success, error)
	    {
		    $.ajax (
		    {
		        url : ctx + '/application/system/param/jd/query',
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


	$.editJDSystemParamInfo = function (data)
	{
		debugger;
		id_o = data.id;
		$ ("#editJDSystemParamInfo").dialog ("open");
		$ ("#protocolPrice1").val (data.protocolPrice1);
		$ ("#protocolPrice2").val (data.protocolPrice2);
		$ ("#protocolPrice3").val (data.protocolPrice3);
		$('#id_flag').val(data.flag);
	}

	$.querySystemParamInfo = function (data)
	{
		var params = {};
		$ ('#systemparamlist').datagrid ('load', params);
	}

	$(document).on("keyup","#protocolPrice1", function (event) {
		KEYUP(event,$(this));
	})
	$(document).on("keyup","#protocolPrice2", function (event) {
		KEYUP(event,$(this));
	})
	$(document).on("keyup","#protocolPrice3", function (event) {
		KEYUP(event,$(this));
	})

	function KEYUP(event,that){
		var e = event || window.event;
		var val = that.get(0).value;

		if(!/^[0-9]{0}([0-9]|[\.])+$/.test(val) || val.split(".")[1].length>2){//含有数字和.以外的字符，则执行
			that.val(val.substr(0,val.length-1));
			event.preventDefault();
			return false
		}
	}



});
// 确认修改京东价格系统参数
function confirmBtn ()
{
	$.messager.confirm ('订单信息', '确定要提交吗？', function (r)
	{
		if (r)
		{
			debugger;
			var protocolPrice1 = $ ("#protocolPrice1").val ();
			var protocolPrice2 = $ ("#protocolPrice2").val ();
			var protocolPrice3 = $ ("#protocolPrice3").val ();
			var flag = $('#id_flag').val();
			var param =  {
				protocolPrice1 : protocolPrice1,
				protocolPrice2 : protocolPrice2,
				protocolPrice3 : protocolPrice3,
				flag:flag
			}
			// 验证参数
			if (protocolPrice1 <= 0 || protocolPrice2<= 0 ||protocolPrice3 <= 0)
			{
				$.messager.alert ("提示","比例系数填写错误，请重新填写");
				return false;
			}

			$.ajax (
			{
			    url : ctx + '/application/system/param/jd/update',
			    data :JSON.stringify(param),
			    type : "post",
			    dataType : "json",
				contentType: 'application/json',
			    success : function (data)
			    {
				    $.messager.alert ('消息', data.msg);
				    closeBtn ();
				    var params = {};
				    $ ('#jdSystemparamlist').datagrid ('load', params);
			    }
			})

		}
	});


}
// 关闭窗口
function closeBtn ()
{
	$ ("#editJDSystemParamInfo").dialog ("close");
}
