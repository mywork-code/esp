$(function(){
	//Grid 列表
	$("#statementListList").datagrid({
        title : '报表信息',
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
				    width : 260,
				    align : 'center'
				},
				{
				    title : '下单时间',
				    field : 'orderCreateDate',
				    width : 160,
				    align : 'center'
				},
                {
                    title : '商品供应商',
                    field : 'merchantName',
                    width : 90,
                    align : 'center'
                },
                {
                	title : '发货时间',
                	field : 'sendOutgoodsdate',
                	width : 150,
                	align : 'center',
                },
                {
                    title : '客户姓名',
                    field : 'customerName',
                    width : 70,
                    align : 'center',
                },
                {
                	title : '客户电话',
                	field : 'customerTelephone',
                	width : 90,
                	align : 'center',
                },
                {
                	title : '快递单号',
                	field : 'logisticsNo',
                	width : 160,
                	align : 'center',
                },
                {
                	title : '领取地址',
                	field : 'address',
                	width : 180,
                	align : 'center',
                },
                {
                	title : '付款方式',
                	field : 'payType',
                	width : 110,
                	align : 'center',
                },
                {
                	title : '付款时间',
                	field : 'payTime',
                	width : 150,
                	align : 'center'
                },
                {
                    title : '签收时间',//首次
                    field : 'signTime',
                    width : 160,
                    align : 'center'
                },
                {
                	title : '结算时间',
                	field : 'settlementTime',
                	width : 160,
                	align : 'center'
                },
                {
                	title : '退货/换货',
                	field : 'refundType',
                	width : 70,
                	align : 'center'
                },
                {
                	title : '退换货发起时间',
                	field : 'refundCreateDate',
                	width : 160,
                	align : 'center',
                },
                {
                	title : '批复结果',
                	field : 'isAgree',
                	width : 70,
                	align : 'center'                	
                },
                {
                	title : '批复人',
                	field : 'approvalUser',
                	width : 90,
                	align : 'center',
                },
                {
                	title : '客户退换货快递单号',
                	field : 'sLogisticsNo',
                	width : 160,
                	align : 'center',
                },
                {
                	title : '供应商换货快递单号',
                	field : 'rLogisticsNo',
                	width : 160,
                	align : 'center',
                },
                {
                	title : '售后完成时间',
                	field : 'completionTime',
                	width : 160,
                	align : 'center'
                },
                {
                	title : '备注',
                	field : 'remark',
                	width : 200,
                	align : 'center',
                },
                {
                    title : '商品名称',
                    field : 'goodsName',
                    hidden: true,
                    width : 120,
                    align : 'center'
                },
                {
                    title : '商品型号',
                    field : 'goodsModel',
                    hidden: true,
                    width : 80,
                    align : 'center'
                },
                {
                    title : '商品市场价格',
                    field : 'marketPrice',
                    hidden: true,
                    width : 100,
                    align : 'center'
                },
                {
                    title : '商品成本价',
                    field : 'goodsCostPrice',
                    hidden: true,
                    width : 70,
                    align : 'center'
                },
                
                {
                    title : '商品购买数量',
                    field : 'goodsNum',
                    hidden: true,
                    width : 90,
                    align : 'center'
                },
                {
                    title : '商品当前库存数量',
                    field : 'stockCurrAmt',
                    hidden: true,
                    width : 90,
                    align : 'center',
                }
            ]
        ],
        
        loader : function (param, success, error)
        {
            $.ajax (
            {
                url : ctx + '/application/merchant/statement/pagelist',
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
		var params = getParams();
		$('#statementListList').datagrid('load', params);  
	});
	debugger;
	var merchantCode = $("#userMerchantCode").val();
	//动态加载商户信息
	$("#selectMerchant").combobox({
		valueField : 'merchantCode',
		textField : 'merchantName',
		onBeforeLoad : function(param) {
		},
		loader : function(param, success, error) {
			$.ajax({
				url : ctx + '/application/rbac/user/merchantList',
				data : param,
				async : false,
				type : "get",
				dataType : "json",
				success : function(data) {
					debugger;
					console.log(data);
					$.validateResponse(data, function() {
						success(data.data);
					});
					// 回调赋值
//					if(null == merchantCode ||'null' == merchantCode || ("") == merchantCode){
//						$("#selectMerchant").combobox('setValue','');
//					}else{
//						$("#selectMerchant").combobox('setValue', merchantCode);
//					}
				}
			})
		}
	});
	//刷新
	$("#flush").click(function(){
		debugger;
		$('#createDate1').datetimebox('setValue','');
		$('#createDate2').datetimebox('setValue','');
		$("#customerName").textbox ('setValue','');
		$("#goodsNames").textbox ('setValue','');
		$("#orderId").textbox ('setValue','');
		$('#date1').datebox('setValue','');
		$('#date2').datebox('setValue','');
		$("#payType").combobox ('setValue','');
		$("#refundType").combobox ('setValue','');
		$("#selectMerchant").combobox ('setValue','');
		var params = {};
		$('#statementListList').datagrid('load', params);
	});
	
	//导出
	$(".export-btn").click(function(){
		$.messager.confirm ('报表信息', '确定要导出吗？', function (r){
			if(r){
				var params = getParams();
				exportStatement("statementListList","报表详情",params);
			}
		});
	});
	
});

function getParams(){
	var params = {};
	//下单时间区间
	var createDate1 = $('#createDate1').datetimebox('getValue');
	var createDate2 = $('#createDate2').datetimebox('getValue');
	params['createDate1'] = createDate1;
	params['createDate2'] = createDate2;
	//客户姓名
	params['customerName'] = $ ("#customerName").textbox ('getValue');
	//商品名称
//	params['goodsNames'] = $ ("#goodsNames").textbox ('getValue');
	//订单id
	params['orderId'] = $ ("#orderId").textbox ('getValue');
	
	//自然日区间（包括下单时间和修改时间）
	var date1 = $('#date1').datetimebox('getValue');
	var date2 = $('#date2').datetimebox('getValue');
	params['date1'] = date1;
	params['date2'] = date2;
	//付款方式
	params['payType'] = $ ("#payType").combobox ('getValue');
	//退货/换货
	params['refundType'] = $ ("#refundType").combobox ('getValue');
	//商户名称
	params['merchantCode'] = $ ("#selectMerchant").combobox ('getValue');
	
	if(createDate1!=null && createDate1!=''&&createDate2!=null && createDate2!=''){
		if($('#createDate1').datebox('getValue')>$('#createDate2').datebox('getValue')){
			$.messager.alert("<span style='color: black;'>提示</span>","下单时间：开始时间应早于结束时间！",'info');
			$('#createDate1').datebox('setValue','');
			$('#createDate2').datebox('setValue','');
			return;
		}
		
	}
	if(date1!=null && date1!=''&&date2!=null && date2!=''){
		if($('#date1').datebox('getValue')>$('#date2').datebox('getValue')){
			$.messager.alert("<span style='color: black;'>提示</span>","日期：开始时间应早于结束时间！",'info');
			$('#date1').datebox('setValue','');
			$('#date2').datebox('setValue','');
			return;
		}
		
	}
	return params;
}

/**
 * 导出报表.csv
 */

// id：dataGrid对应id , fileName: 文件名称 , params:要查询的参数
function exportStatement (id, fileName, params)
{
	debugger;
	params = JSON.stringify (params);
	
	// 字段标题集合 {"orderId":"订单id"，"orderAmt":"订单金额"}
	var attrs = {};
	
	// 字段集合
	var fields = $ ("#" + id).datagrid ('getColumnFields');
	
	// datagrid列属性
	var columns = $ ("#" + id).datagrid ('options').columns[0];
	
	// 填充字段标题集合
	$.each (fields, function (i, n)
	{
		// 去除不需要的字段
		if (n != "opt"&&n != "id")
		{
			attrs[n] = columns[i].title;
		}
	});
	
	// 格式化
	attrs = JSON.stringify (attrs);
	
	// 创建Form
	var form = $ ('<form></form>');
	// 设置属性
	form.attr ('action', ctx + '/application/statement/exportStatement');
	form.attr ('method', 'post');
	form.attr ('target', '_self');
	
	// 文件名
	var my_input1 = $ ('<input type="text" name="fileName"/>');
	my_input1.attr ('value', fileName);
	
	// 标题头
	var my_input2 = $ ('<input type="text" name="attrs"/>');
	my_input2.attr ('value', attrs);
	
	// 附加到Form
	form.append (my_input1);
	form.append (my_input2);
	
	// 数据
	var my_input3 = $ ('<input type="text" name="params"/>');
	my_input3.attr ('value', params);
	form.append (my_input3);
	
	var my_input4 = $ ('<input type="submit"/>');
	form.append (my_input4);
	$('body').append(form);
	
	// 提交表单
	my_input4.click ();
	form.remove();
}

