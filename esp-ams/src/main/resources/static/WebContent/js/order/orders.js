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
	            title : '订单信息查询',
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
			                        content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton'";
			                        content += " onclick='$.queryOrderDetail(" + JSON.stringify (row) + ");'>查看详情</a>";
			                        if(grantedAuthority=='permission'){
			                        if (orderStatus == 'D02')
			                        {
				                        content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton'";
				                        content += " onclick='$.sendGoods(\"" + row.orderId + "\",\""
				                                + row.logisticsName + "\",\"" + row.logisticsNo + "\");'>我要发货</a>";
			                        }
			                        }
			                        if (orderStatus == 'D03')
			                        {
				                        content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton'";
				                        content += " onclick='$.logisticsInfo(\"" + row.orderId + "\",\""
				                                + row.logisticsName + "\",\"" + row.logisticsNo + "\");'>物流信息</a>";
			                        }
			                        
			                        if (orderStatus == 'D05')
			                        {
				                        content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton'";
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
		                url : ctx + '/application/business/order/pagelist',
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
		params['orderId'] = $ ("#orderId").textbox ('getValue');
		params['createDate'] = $ ("#createDate").textbox ('getValue');
		params['name'] = $ ("#name").textbox ('getValue');
		params['telephone'] = $ ("#telephone").textbox ('getValue');
		params['orderStatus'] = $ ("#orderStatus").textbox ('getValue');
		params['refundType'] = $ ("#refundType").textbox ('getValue');
		$ ('#tablelist').datagrid ('load', params);
	});
	
	// 重置
	$ (".reset-btn").click (function ()
	{
		$ (".search #orderId").textbox ('setValue', '');
		$ (".search #createDate").textbox ('setValue', '');
		$ (".search #telephone").textbox ('setValue', '');
		$ (".search #orderStatus").combobox('setValue','');
		$ (".search #refundType").combobox('setValue','');
		$ (".search #name").textbox ('setValue', '');
		var params = {};
		$ ('#tablelist').datagrid ('load', params);
	});
	
	// 导出订单
	$ (".export-btn").click (function ()
	{
		// var dataList;
		$.messager.confirm ('订单信息', '确定要导出吗？', function (r)
		{
			if (r)
			{
				var params = {};
				params['orderId'] = $ ("#orderId").textbox ('getValue');
				params['createDate'] = $ ("#createDate").textbox ('getValue');
				params['name'] = $ ("#name").textbox ('getValue');
				params['telephone'] = $ ("#telephone").textbox ('getValue');
				params['orderStatus'] = $ ("#orderStatus").textbox ('getValue');
				params['refundType'] = $ ("#refundType").combobox ('getValue');
				params['isAll'] = 'f';// t: 是 f: 否 是否导出全部订单信息
				params['busCode'] = 'E001';// 订单导出code
				
				// $.ajax (
				// {
				// url : ctx + '/application/business/order/pagelist',
				// data : params,
				// type : "post",
				// dataType : "json",
				// success : function (data)
				// {
				// dataList = data.rows;
				// exportFile ("tablelist", "订单信息", params);
				// }
				// })
				
				exportFile ("tablelist", "订单信息", params);
			}
		});
		
	});
	
	// 查询订单详情
	$.queryOrderDetail = function (data)
	{
		// 设置窗口中的订单信息
		$ ("#orderDetailListWin #orderId").textbox ('setValue', data.orderId);
		$ ("#orderDetailListWin #orderAmt").textbox ('setValue', data.orderAmt);
		$ ("#orderDetailListWin #orderStatus").textbox ('setValue', data.orderStatusDsc);
		$ ("#orderDetailListWin #payType").textbox ('setValue', data.payType);
//		$ ("#orderDetailListWin #transNo").textbox ('setValue', '');
		$ ("#orderDetailListWin #createDate").textbox ('setValue', data.createDate);
		
		$ ('#orderDetailListWin').window ('open');
		
		// 查询订单详情信息
		queryOrderDetail (data.orderId);
	}

	// 物流详情
	$.logisticsInfo = function (orderId, logisticsName, logisticsNo)
	{
		$ ('#logisticsDetails').datagrid (
		{
//		    url : ctx + '/application/business/order/pagelistLogistics',
		    method : 'POST',
		    idField : '物流详细信息',
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
			            /*
						 * { title : '数量', field : 'num', width : 70, align :
						 * 'center' },
						 */
			            {
			                title : '物流单号',
			                field : 'logisticsNo',
			                width : 100,
			                align : 'center'
			            },
			            {
			                title : '物流公司',
			                field : 'logisticsNameDes',
			                width : 100,
			                align : 'center'
			            },
			            {
			                title : '物流公司联系电话',
			                field : 'logisticsTel',
			                width : 100,
			                align : 'center',
			            },
			            {
			                title : '物流状态',
			                field : 'logisticsStatus',
			                width : 100,
			                align : 'center'
			            }
			    ]
		    ],
		    onBeforeLoad : function (param)
		    {
			    param.orderId = orderId;
			    param.logisticsName = logisticsName;
			    param.logisticsNo = logisticsNo;
		    },
		    
		    loader : function (param, success, error)
            {
	            $.ajax (
	            {
	                url : ctx + '/application/business/order/pagelistLogistics',
	                data : param,
	                type : "post",
	                dataType : "json",
	                success : function (data){
	                	debugger;
	                	if (data.result == false && data.message == 'timeout') {
	                        window.top.location = ctx + "/logout";
	                        return;
	                    }
	                	if(data.status == "1"){
	                		//$.messager.alert("提示",data.msg);
	                		$ ('#logisticsDetail').window ('open');
	                		success(data);
	                	}else{
	                		$.messager.alert("提示",data.msg);
	                	}
	                }
	            })
            }

//		    onLoadSuccess : function (data)
//		    {
//		    	debugger;
//			    var respJson = $.parseJSON (data);
//			    console.log(data);
//		    },
		});
	}

	// 我要发货
	$.sendGoods = function (orderId, logisticsName, logisticsNo)
	{
		debugger;
		if (logisticsName == 'null')
		{
			logisticsName = '';
		}
		if (logisticsNo == 'null')
		{
			logisticsNo = '';
		}
		
		$ ("#addLogisticsDetails").dialog (
		{
		    modal : true,
		    title : "物流信息",
		    width : 500,
		    height : 180,
		    buttons : [
		            {
		                text : "确定",
		                handler : function ()
		                {
			                $.messager.confirm ('物流信息', '确定要提交？', function (r)
			                {
				                if (r)
				                {
					                $("#logisticForm").submit ();
				                }
				                else
				                {
					                return;
				                }
				                
			                });
			                
		                }
		            },
		            {
		                text : "关闭",
		                handler : function ()
		                {
			                $ ("#addLogisticsDetails").dialog ("close");
		                }
		            }
		    ]
		});
		
		debugger;
		var param = new Object ();
		param.dataTypeNo = '100003';
		
		$("#addLogisticsDetails #orderId").val(orderId);
		getDataDic('#addLogisticsDetails #logisticsName', param, "");
		$("#addLogisticsDetails #logisticsNo").val("");
		
	}
	
	
	// 更新物流信息、订单状态
	$ ("#logisticForm").form (
	{
	    url : ctx + "/application/business/order/updateLogisticsInfoAndOrderInfoByOrderId",
	    method : 'POST',
	    onSubmit : function ()
	    {
		    var logisticsName = $ ("#addLogisticsDetails #logisticsName").combobox ('getValue', logisticsName);
		    if ($.trim (logisticsName) == "")
		    {
			    $.messager.alert ("操作提示", "物流公司不能为空", "warning");
			    return false;
		    }
		    var logisticsNo = $ ("#addLogisticsDetails #logisticsNo").val ();
		    if ($.trim (logisticsNo) == "")
		    {
			    $.messager.alert ("操作提示", "物流单号不能为空", "warning");
			    return false;
		    }
	    },
	    success : function (data)
	    {
	    	debugger;
		    var respJson = $.parseJSON (data);
		    $.validateResponse (respJson, function ()
		    {
			    if (respJson.status = "1")
			    {
				    $.messager.alert ("操作提示", respJson.msg);
				    $ ("#addLogisticsDetails").dialog ("close");
				    $ (".search-btn").click ();
			    }
			    else
			    {
				    $.messager.alert ("操作提示", respJson.msg);
				    return false;
			    }
		    });
	    }
	});
	
	function queryOrderDetail (orderId)
	{
		$ ('#orderDetailList').datagrid (
		{
		    url : ctx + '/application/business/order/queryOrderDetailInfo',
		    method : 'POST',
		    idField : '订单详细信息',
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
			                field : '编号',
			                width : 70,
			                align : 'center',
			                align : 'center'
			            },
			            {
			                title : '订单号',
			                field : 'orderId',
			                width : 300,
			                align : 'center'
			            },
			            {
			                title : '商品id',
			                field : 'goodsId',
			                width : 70,
			                align : 'center'
			            },
			            {
			                title : '商户编码',
			                field : 'merchantCode',
			                width : 70,
			                align : 'center'
			            },
			            {
			                title : '商品价格',
			                field : 'goodsPrice',
			                width : 70,
			                align : 'center',
			            },
			            {
			                title : '商品数量',
			                field : 'goodsNum',
			                width : 70,
			                align : 'center'
			            },
			            {
			                title : '商品名称',
			                field : 'goodsName',
			                width : 70,
			                align : 'center'
			            },
			            {
			            	title : '商品型号',
			            	field : 'goodsModel',
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
			                title : '商品类型',
			                field : 'goodsType',
			                width : 70,
			                align : 'center',
			                formatter:function(value,row,index){
			                	if(value=='1'){
			                		return '正常';
			                	}else if(value=='2'){
			                		return '精选';
			                	}
			                }	
			            },
			            {
			                title : '商品上架时间',
			                field : 'listTime',
			                width : 150,
			                align : 'center',
			                formatter : function (value, row, index)
			                {
				                return new Date (value).Format ("yyyy-MM-dd hh:mm:ss");
			                }
			            },
			            {
			                title : '商品下架时间',
			                field : 'delistTime',
			                width : 150,
			                align : 'center',
			                formatter : function (value, row, index)
			                {
				                return new Date (value).Format ("yyyy-MM-dd hh:mm:ss");
			                }
			            },
			            {
			                title : '生产厂家',
			                field : 'supNo',
			                width : 70,
			                align : 'center'
			            },
			            {
			                title : '创建时间',
			                field : 'createDate',
			                width : 150,
			                align : 'center',
			                formatter : function (value, row, index)
			                {
				                return new Date (value).Format ("yyyy-MM-dd hh:mm:ss");
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
	
	// // 填写退货信息
	// $.returnGoods = function (orderId)
	// {
	//		
	// $ ("#orderRefundDetailList #orderId").val (orderId);
	// $ ("#orderRefundDetailList").dialog (
	// {
	// modal : true,
	// title : "退货信息",
	// width : 500,
	// height : 300,
	// buttons : [
	// {
	// text : "确认退货",
	// handler : function ()
	// {
	// $.messager.confirm ('确认退货', '确定要退货吗？', function (r)
	// {
	// if (!r)
	// {
	// return;
	// }
	// $ ("#refundDetailForm").submit ();
	// });
	// }
	// },
	// {
	// text : "关闭",
	// handler : function ()
	// {
	// $ ("#orderRefundDetailList").dialog ("close");
	// }
	// }
	// ]
	// });
	//		
	// // 查询退货详细信息
	// var param = {};
	// param.orderId = orderId;
	// $.ajax (
	// {
	// url : ctx + '/application/business/refund/pagelist',
	// data : param,
	// async : false,
	// type : "post",
	// dataType : "json",
	// success : function (data)
	// {
	// var list = data.rows;
	// var dataObject = list[0];
	// if (dataObject && dataObject != null)
	// {
	// // 设置退款详情
	// $ ("#orderRefundDetailList #orderId").val (dataObject.orderId);
	// $ ("#orderRefundDetailList #orderAmt").val (dataObject.orderAmt);
	// $ ("#orderRefundDetailList #refundAmt").val (dataObject.refundAmt);
	// $ ("#orderRefundDetailList #logisticsNo").val (dataObject.logisticsNo);
	// $ ("#orderRefundDetailList #refundReason").val (dataObject.refundReason);
	// $ ("#orderRefundDetailList #createDate").val (dataObject.createDate);
	// }
	// else
	// {
	// $ ("#orderRefundDetailList #orderAmt").val ('');
	// $ ("#orderRefundDetailList #refundAmt").val ('');
	// $ ("#orderRefundDetailList #logisticsNo").val ('');
	// $ ("#orderRefundDetailList #refundReason").val ('');
	// $ ("#orderRefundDetailList #createDate").val ('');
	// }
	// }
	// });
	// };
	//	
	// 2.更新订单状态
	// $ ("#refundDetailForm").form (
	// {
	// url : ctx + '/application/business/order/updateOrderStatus',
	// method : 'POST',
	// onSubmit : function (param)
	// {
	// },
	// success : function (data)
	// {
	// var respJson = $.parseJSON (data);
	// $.validateResponse (respJson, function ()
	// {
	// if (respJson.status = "1")
	// {
	// $.messager.alert ('消息', respJson.msg);
	// $ ("#orderRefundDetailList").dialog ("close");
	// $ (".search-btn").click ();
	// }
	// else
	// {
	// $.messager.alert ('警告', respJson.msg);
	// return false;
	// }
	// });
	// }
	// });
	//	
	// 售后详情
	$.serviceInfo = function (orderId)
	{
		$ ('#serviceDetail').window ('open');
		
		$ ('#serviceDetails').datagrid (
		        {
		            url : ctx + '/application/business/serviceProcess/pagelist',
		            method : 'POST',
		            idField : '售后详细信息',
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
		            	debugger;
		            },
		        });
	}

	$.queryServiceProcessDetail = function (data)
	{
		$("#serviceDetailList .return").css("display","none");
		$("#serviceDetailList .swap").css("display","none");
		debugger;
		if(data.refundType == '0'){
			$("#serviceDetailList .return").css("display","inline");
		}else if(data.refundType == '1'){
			$("#serviceDetailList .swap").css("display","inline");
		}
		
		$("#serviceDetailList #goodsUrlListDiv").empty();
		orderIdAll = data.orderId;
		refundIdAll = data.refundId;
		refundTypeAll = data.refundType;
		isAgree = data.isAgree,
		$("#serviceDetailList").dialog ('open');
		var goodsRefundPrice = data.goodsPrice*data.goodsNum;
		$ ("#serviceDetailList #refundReason").val (data.refundReasonDes);
		$ ("#serviceDetailList #goodsPrice").val (goodsRefundPrice);
		$ ("#serviceDetailList #goodsNum").val (data.goodsNum);
		$ ("#serviceDetailList #status").val (data.statusDesc);
		$ ("#serviceDetailList #sLogisticsNo").val (data.sLogisticsNo);
//		$("#serviceDetailList goodsUrl").attr("src",ctx + "/fileView/query?picUrl=" + data.goodsUrl);
		//图片显示 
		debugger;
		if(data.goodsUrl != null){
			var goodsUrlArr = data.goodsUrl.split(",");//url是用,分隔的数组
			$.each(goodsUrlArr,function(index,value){
			    var picUrl = ctx + "/fileView/query?picUrl=" + value;
			    var goodsUrlListDivId = "goodsUrlListDiv"+index;
				$("#serviceDetailList #goodsUrlListDiv").append("<img src='"+picUrl+"' border='1'  id='"+goodsUrlListDivId+"'/> ");//"+ctx+"'\"/fileView/query?picUrl\"/
			});
		}
		
		$ ("#serviceDetailList #remark").val (data.remark);
		var sLogisticsName = data.sLogisticsName;
		$ ("#serviceDetailList #sLogisticsName").val (sLogisticsName);
		if(sLogisticsName!=null&&sLogisticsName!=""){
			var param = {
					dataNo:sLogisticsName,
					dataTypeNo:"100003"
			};
			$.ajax ({
			    url : ctx + '/application/business/datadic/pagelist',
			    data : param,
			    type : "post",
			    dataType : "json",
			    success : function (data){
			    	debugger;
			    	var response = data.rows[0];
				    if (data.status == "1"){
				    	if(null != response && "" != response){
				    		$ ("#serviceDetailList #sLogisticsName").val(response.dataName);
				    	}
				    }  else{
				    	$.messager.alert ('警告', data.msg);
				    }
			    }
			});
		}
		
		debugger;
		
		$ ("#goodsUrl").attr ("src", ctx + "/fileView/query?picUrl=" + data.goodsUrl);
		var status = data.status;
		var refundType = data.refundType;
		// 先设置所有按钮为只读
		$("#serviceDetailList .agree-btn,#serviceDetailList .reject-btn,#serviceDetailList .confirm-receipt-btn,#serviceDetailList .send-goods-again-btn")
		        .css("display", "none");
		
		// 设置对象按钮的显示
		if (status == "RS01")
		{			
			$("#sLogisticsNameAndSLogisticsNo").attr("hidden","hidden");
			if(isAgree!='1'){
				$ ("#serviceDetailList .agree-btn,#serviceDetailList .reject-btn").css ("display", "");
			}
		}else{
			$ ("#sLogisticsNameAndSLogisticsNo").removeAttr("hidden");
		}
		if (status == "RS02")
		{
			$ ("#serviceDetailList .confirm-receipt-btn").css ("display", "");
		}
		if (status == "RS03" && refundType == "1")
		{
			$ ("#serviceDetailList .send-goods-again-btn").css ("display", "");
		}
		
		
	}
	$("#goodsUrlListDiv").on("click","img",function(){
		if($(this).attr("data-show") == 'true'){
			$(this).animate({ 
				width: "30px",
				height: "30px"
			}, 200,function(){
				$(this).attr("data-show","false")
			});
		}else {
			$(this).animate({ 
				width: "100%",
				height: "100%"
			}, 200,function(){
				$(this).attr("data-show","true")
			});
		}
		
		
	})

	$ ("#serviceDetailList .close-btn").click (function ()
	{
		$ ("#serviceDetailList").dialog ('close');
	});
	
	/**
	 * 同意
	 */
	$ (".agree-btn").click (function ()
	{
		$.messager.confirm ('售后处理', '确定同意吗？', function (r)
		{
			if (!r)
			{
				return;
			}
			debugger;
			var param = {};
			param.orderId = orderIdAll;
			param.refundId = refundIdAll;
			$.ajax (
			{
			    url : ctx + '/application/business/refund/agreeRefundApplyByOrderId',
			    data : param,
			    type : "post",
			    dataType : "json",
			    success : function (data)
			    {
				    if (data.status == "1")
				    {
					    $.messager.alert ('消息', data.msg);
					    $ ("#serviceDetail").dialog ("close");
					    $ ("#serviceDetailList").dialog ('close');
				    }
				    else
				    {
					    $.messager.alert ('警告', data.msg);
				    }
			    }
			});
		});
	});
	
	/**
	 * 确认关闭
	 */
	$ (".reject-btn").click (function ()
	{
		debugger;
		$("#rejectReason").val('');
		$ ("#rejectDialog").dialog ('open');
	});
	
	/**
	 * 驳回关闭
	 */
	$ ("#rejectDialog .close-btn").click (function ()
	{
		$ ("#rejectDialog").dialog ('close');
	});
	
	/**
	 * 确认驳回
	 */
	$ ("#rejectDialog .confirm-btn").click (function ()
	{
		// 驳回原因
		var rejectReason = $ ("#rejectDialog #rejectReason").val ();
		$.messager.confirm ('售后驳回', '确定要驳回吗？', function (r)
		{
			if (!r)
			{
				return;
			}
			var param = {};
			param.orderId = orderIdAll;
			param.refundId = refundIdAll;
			param.rejectReason = rejectReason;
			
			if (rejectReason == null)
			{
				$.messager.alert ('警告', "请填写驳回原因！");
				return false;
			}
			$.ajax (
			{
			    url : ctx + '/application/business/refund/rejectRequestByOrderId',
			    data : param,
			    type : "post",
			    dataType : "json",
			    success : function (data)
			    {
				    if (data.status == "1")
				    {
					    $.messager.alert ('消息', data.msg);
					    $ ("#rejectDialog").dialog ('close');
					    $ ("#serviceDetail").dialog ("close");
					    $ ("#serviceDetailList").dialog ('close');
				    }
				    else
				    {
					    $.messager.alert ('警告', data.msg);
				    }
			    }
			});
		});
	});
	
	/**
	 * 确认收货
	 */
	$ ("#serviceDetailList .confirm-receipt-btn").click (function ()
	{
		// 确认收货
		$.messager.confirm ('确认收货', '确定要收货吗？', function (r)
		{
			if (!r)
			{
				return;
			}
			var param = {};
			param.orderId = orderIdAll;
			param.refundId = refundIdAll;
			param.refundType = refundTypeAll;
			$.ajax (
			{
			    url : ctx + '/application/business/refund/confirmReceiptByOrderId',
			    data : param,
			    type : "post",
			    dataType : "json",
			    success : function (data)
			    {
				    if (data.status == "1")
				    {
					    $.messager.alert ('消息', data.msg);
					    $ ("#serviceDetail").dialog ("close");
					    $ ("#serviceDetailList").dialog ('close');
				    }
				    else
				    {
					    $.messager.alert ('警告', data.msg);
				    }
			    }
			});
		});
	});
	
	/**
	 * 重新发货
	 */
	$ ("#serviceDetailList .send-goods-again-btn").click (function ()
	{
		$ ("#sendGoodsAgain").dialog (
		{
		    modal : true,
		    title : "物流信息",
		    width : 500,
		    height : 150,
		    buttons : [
		            {
		                text : "确定",
		                handler : function ()
		                {
			                $.messager.confirm ('物流信息', '确定要提交？', function (r)
			                {
				                if (r)
				                {
					                $ ("#sendGoodsAgainForm").submit ();
				                }
				                else
				                {
					                return;
				                }
				                
			                });
			                
		                }
		            },
		            {
		                text : "关闭",
		                handler : function ()
		                {
			                $ ("#sendGoodsAgain").dialog ("close");
		                }
		            }
		    ]
		});
		
		var param = new Object ();
		param.dataTypeNo = '100003';
		getDataDic ('#sendGoodsAgain #rlogisticsName', param, null);
		
		$ ("#sendGoodsAgain #orderId").val (orderIdAll);
	});
	
	// 商家重新发货
	$ ("#sendGoodsAgainForm").form (
	{
	    url : ctx + '/application/business/refund/sendGoodsAgain',
	    method : 'POST',
	    onSubmit : function (param)
	    {
	    	var logisticsName = $("#sendGoodsAgain #rlogisticsName").combobox ('getValue');
 		    if ($.trim (logisticsName) == "")
 		    {
 			    $.messager.alert ("操作提示", "物流公司不能为空", "warning");
 			    return false;
 		    }
 		    var logisticsNo = $ ("#sendGoodsAgain #rlogisticsNo").val ();
 		    if ($.trim (logisticsNo) == "")
 		    {
 			    $.messager.alert ("操作提示", "物流单号不能为空", "warning");
 			    return false;
 		    }
		    param.refundId = refundIdAll;
	    },
	    success : function (data)
	    {
		    var respJson = $.parseJSON (data);
		    $.validateResponse (respJson, function ()
		    {
			    if (respJson.status = "1")
			    {
				    debugger;
				    $.messager.alert ('消息', respJson.msg);
				    $ ("#serviceDetail").dialog ("close");
				    $ ("#serviceDetailList").dialog ("close");
				    $ ("#sendGoodsAgain").dialog ("close");
			    }
			    else
			    {
				    $.messager.alert ('警告', respJson.msg);
				    return false;
			    }
		    });
	    }
	});
});
