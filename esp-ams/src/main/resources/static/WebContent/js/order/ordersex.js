/**
 * 订单信息查询
 */
$ (function ()
{
	// 订单号 （全局）
	var orderIdAll = "";
	
	// 售后id （全局）
	var refundIdAll = "";
	
	//是否同意售后
	var refundTypeAll = "";
	
	$ ('#orderDetailListWin,#serviceDetailList,#logisticsDetail,#serviceDetail,#rejectDialog,#orderRefundDetailList,#sendGoodsAgain,#addLogisticsDetails').window ('close');
	
	// Grid 列表
	$ ('#tablelist').datagrid (
	        {
	            title : '异常订单信息查询',
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
		                        width : 250,
		                        align : 'center'
		                    },
		                    
		                    {
		                        title : '下单时间',
		                        field : 'createDate',
		                        width : 150,
		                        align : 'center'
		                    },
		                    
		                    {
		                        title : '收货人姓名',
		                        field : 'name',
		                        width : 70,
		                        align : 'center'
		                    },
		                    
		                    {
		                        title : '收货人手机号',
		                        field : 'telephone',
		                        width : 100,
		                        align : 'center'
		                    },
		                    
		                    {
		                        title : '收货地址',
		                        field : 'fullAddress',
		                        width : 200,
		                        align : 'center'
		                    },
		                    {
		                    	title : '商户编码',
		                    	hidden: true,
		                    	field : 'merchantCode',
		                    	width : 100,
		                    	align : 'center'
		                    },
		                    {
		                        title : '商户名称',
		                        field : 'merchantName',
		                        width : 100,
		                        align : 'center'
		                    },
		                    {
		                        title : '付款方式',
		                        field : 'payType',
		                        width : 70,
		                        align : 'center'
		                    },
		                    {
		                        title : '付款时间',
		                        field : 'payDate',
		                        width : 150,
		                        align : 'center'
		                    },
		                    {
		                        title : '订单状态',
		                        field : 'orderStatusDsc',
		                        width : 70,
		                        align : 'center'
		                    },
		                    {
		                    	title : '商品编号',
		                    	hidden: true,
		                    	field : 'goodsId',
		                    	width : 100,
		                    	align : 'center'
		                    },
		                    {
		                    	title : '商品名称',
		                    	hidden: true,
		                    	field : 'goodsName',
		                    	width : 100,
		                    	align : 'center'
		                    },
		                    {
		                    	title : '商品类型',
		                    	hidden: true,
		                    	field : 'goodsTypeDesc',
		                    	width : 100,
		                    	align : 'center'
		                    },
		                    {
		                    	title : '商品型号',
		                    	hidden: true,
		                    	field : 'goodsModel',
		                    	width : 100,
		                    	align : 'center'
		                    },
		                    {
		                    	title : '商品规格',
		                    	hidden: true,
		                    	field : 'goodsSkuAttr',
		                    	width : 100,
		                    	align : 'center'
		                    },
		                    {
		                        title : '购买价格',
		                        field : 'orderAmt',
		                        width : 70,
		                        align : 'center',
		                        formatter:function(val,rowData,rowIndex){
		                        	if(val!=null){
		                        		return val.toFixed(2);
		                        	}
		                        }
		                    },
		                    {
		                    	title : '购买量',
		                    	hidden: true,
		                    	field : 'goodsNum',
		                    	width : 100,
		                    	align : 'center'
		                    },
		                    {
		                    	title : '是否为预发货',
		                    	hidden: false,
		                    	field : 'preDelivery',
		                    	width : 100,
		                    	align : 'center'
		                    },
		                    {
		                        title : '操作',
		                        field : 'opt',
		                        width : 300,
		                        align : 'center',
		                        formatter : function (value, row, index)
		                        {
		                        	// 授权标示
		                        	var grantedAuthority=$('#grantedAuthority').val();
			                        // 订单状态
			                        var orderStatus = row.orderStatus;
			                        var content = "";
			                        if(grantedAuthority=='permission'){
			                        	 content += "<a href='javascript:void(0);' class='easyui-linkedbutton'";
					                     content += " onclick='$.serviceInfo(\"" + row.orderId + "\");'>售后详情</a>";
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
	$ (".search-btn").click (function (){
		var params = {};
		$ ('#tablelist').datagrid ('load', params);
	});
	
	$.serviceInfo = function (orderId)
	{
		$ ('#serviceDetail').window ('open');
		
		$ ('#serviceDetails').datagrid (
		        {
		            url : ctx + '/application/business/serviceProcess/pagelist',
		            method : 'POST',
		            idField : '售后详情',
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
			                        title : 'ID',
			                        hidden : true,
			                        field : 'id',
			                        width : 70,
			                        align : 'center'
			                    },
			                    {
			                        title : '售后申请时间',
			                        field : 'createDate',
			                        width : 100,
			                        align : 'center',
			                        formatter : function (value, row, index)
			                        {
				                        String
				                        temp = "";
				                        if (value != null)
				                        {
					                        temp = new Date (value).Format ("yyyy-MM-dd hh:mm:ss");
				                        }
				                        return temp;
			                        }
			                    },
			                    {
			                        title : '申请原因',
			                        field : 'refundReasonDes',
			                        width : 100,
			                        align : 'center'
			                    },
			                    {
			                        title : '申请数量',
			                        field : 'goodsNum',
			                        width : 100,
			                        align : 'center',
			                    },
			                    {
			                        title : '申请批复结果',
			                        field : 'approvalComments',
			                        width : 100,
			                        align : 'center'
			                    },
			                    {
			                        title : '申请批复人',
			                        field : 'approvalUser',
			                        width : 100,
			                        align : 'center'
			                    },
			                    {
			                        title : '售后类型',
			                        field : 'refundType',
			                        width : 100,
			                        align : 'center',
			                        formatter : function (value, row, index)
			                        {
				                        var content;
				                        // 售后类型 0 ：退货 1：换货
				                        var refundType = row.refundType;
				                        if (refundType == "0")
				                        {
					                        content = "退货";
				                        }
				                        else if (refundType == "1")
				                        {
					                        content = "换货";
				                        }
				                        return content;
			                        }
			                    },
			                    {
			                        title : '售后处理进度',
			                        field : 'statusDesc',
			                        width : 100,
			                        align : 'center',
			                    },
			                    {
			                        title : '操作',
			                        field : 'opt',
			                        width : 100,
			                        align : 'center',
			                        formatter : function (value, row, index)
			                        {
			                        	// 授权标示
			                        	var grantedAuthority=$('#grantedAuthority').val();
				                        var content = "";
				                        if(grantedAuthority=='permission'){
				                        content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton'";
				                        content += " onclick='$.queryServiceProcessDetail(" + JSON.stringify (row)
				                                + ");'>明细详情</a>";
				                        }else{
				                    		content +="权限不足";
				                    	} 
				                        return content;
			                        }
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
