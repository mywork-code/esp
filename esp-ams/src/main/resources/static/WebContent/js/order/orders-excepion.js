/**
 * 订单信息查询
 */
$ (function ()
{
	
	$ ('#orderDetailListWin,#serviceDetailList,#logisticsDetail,#serviceDetail,#rejectDialog,#orderRefundDetailList,#sendGoodsAgain,#addLogisticsDetails').window ('close');
	
	// Grid 列表
	$ ('#tablelist').datagrid (
	        {
	            title : '异常订单管理',
	            fit : true,
	            fitColumns : false,
	            rownumbers : true,
	            pagination : true,
	            singleSelect : true,
	            striped : true,
	            toolbar : '#tb',
	            columns : [
		            [
		                    {
		                        title : '订单号',
		                        field : 'orderId',
		                        width : 150,
		                        align : 'center'
		                    },
		                    
		                    {
		                        title : '下单时间',
		                        field : 'createDate',
		                        width : 150,
		                        align : 'center'
		                    },
		                    {
		                        title : '付款方式',
		                        field : 'payType',
		                        width : 100,
		                        align : 'center'
		                    },
		                    {
		                        title : '订单状态',
		                        field : 'orderStatusDsc',
		                        width : 100,
		                        align : 'center'
		                    },
		                    {
		                        title : '订单金额',
		                        field : 'orderAmt',
		                        width : 100,
		                        align : 'center',
	                            formatter: function (val, rowData, rowIndex) {
	                                if (val != null)
	                                    return val.toFixed(2);
	                            }
		                    },
		                    {
		                        title : '退款金额',
		                        field : 'txnAmt',
		                        width : 100,
		                        align : 'center',
		                        formatter: function (val, rowData, rowIndex) {
		                            if (val != null)
		                                return val.toFixed(2);
		                        }
		                    },
		                    {
		                        title : '审核人',
		                        field : 'auditorName',
		                        width : 100,
		                        align : 'center'
		                    },
		                    {
		                        title : '审核时间',
		                        field : 'auditorDate',
		                        width : 100,
		                        align : 'center',
	                            formatter: function (value, row, index) {
	                                return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
	                            }
		                    },
		                    {
		                        title : '操作',
		                        field : 'opt',
		                        width : 250,
		                        align : 'center',
		                        formatter : function (value, row, index)
		                        {
		                        	// 授权标示
		                        	var grantedAuthority=$('#grantedAuthority').val();
			                        
			                        // 订单状态
			                        var refundStatus = row.refundStatus;
			                        var content = "";
			                        content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton'";
			                        content += " onclick='$.queryOrderDetail(" + JSON.stringify (row) + ");'>查看详情</a>";
			                        
			                        if (refundStatus == '2' || refundStatus == '')
			                        {
				                        content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton'";
				                        content += " onclick='$.logisticsInfo(\"" + row.orderId + "\",\""
				                                + row.logisticsName + "\",\"" + row.logisticsNo + "\");'>退款</a>";
			                        }
			                        return content;
		                        }
		                    }
		            ]
	            ],
	            
	            loader : function (param, success, error)
	            {
		            $.ajax (
		            {
		                url : ctx + '/application/business/order/exception/pagelist',
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
	
	// 查询列表
	$ (".search-btn").click (function ()
	{
		var params = {};
		params['refundType'] = $ ("#refundType").textbox ('getValue');
		$ ('#tablelist').datagrid ('load', params);
	});
	
	// 导出订单
	$ (".export-btn").click (function ()
	{
		// var dataList;
		$.messager.confirm ('异常订单信息', '确定要导出吗？', function (r)
		{
			if (r)
			{
				var params = {};
				
				params['refundType'] = $ ("#refundType").combobox ('getValue');
				params['busCode'] = 'E005';// 订单导出code
				
				exportFile ("tablelist", "异常订单信息", params);
			}
		});
		
	});
	
	// 查询订单详情
	$.queryOrderDetail = function (data)
	{
		$ ('#orderDetailListWin').window ('open');
		// 查询订单详情信息
		queryOrderDetail (data.orderId);
	}

	function queryOrderDetail (orderId)
	{
		$ ('#orderDetailList').datagrid (
		{
		    url : ctx + '/application/business/order/exception/queryOrderDetailInfo',
		    method : 'POST',
		    idField : '详情',
		    fitColumns : false,
		    pagination : true,
		    rownumbers : true,
		    striped : true,
		    singleSelect : false,
		    pagination : false,
		    nowrap : false,
		    showFooter : true,
		    columns : [
			    [

						{
			                title : '商品名称',
			                field : 'goodsName',
			                width : 70,
			                align : 'center'
			            },
			            {
			                title : '商户名称',
			                field : 'merchantName',
			                width : 70,
			                align : 'center'
			            },
			            {
			                title : '商品数量',
			                field : 'goodsNum',
			                width : 70,
			                align : 'center'
			            },
			            {
			            	title : '商品规格',
			            	field : 'goodsSkuAttr',
			            	width : 70,
			            	align : 'center'
			            },
			            {
			            	title : '商品金额',
			            	field : 'goodsAmt',
			            	width : 70,
			            	align : 'center',
	                        formatter: function (val, rowData, rowIndex) {
	                            if (val != null)
	                                return val.toFixed(2);
	                        }
			            },
			            {
			            	title : '退款金额',
			            	field : 'refundAmt',
			            	width : 70,
			            	align : 'center',
	                        formatter: function (val, rowData, rowIndex) {
	                            if (val != null)
	                                return val.toFixed(2);
	                        }
			            },
			            {
			            	title : '退款商品数量',
			            	field : 'goodsNum',
			            	width : 70,
			            	align : 'center'
			            }
			    ]
		    ],
		    onBeforeLoad : function (param)
		    {
			    param.orderId = orderId;
		    },
		    onLoadSuccess : function (data)
		    {
		    },
		});
	}
});
