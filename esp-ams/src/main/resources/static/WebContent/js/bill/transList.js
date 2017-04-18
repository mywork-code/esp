$(function(){
	//Grid 列表
	$("#transactionForList").datagrid({
        title : '交易查询',
        fit : true,
        fitColumns : false,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        striped:true,
        toolbar : '#tb',
        columns : [[{
            title : '交易流水号',
            field : 'txnId',
            width : 150,
            align : 'center'
        }, {
            title : '订单编号',
            field : 'orderId',
            width : 150,
            align : 'center'
        }, {
            title : '用户ID',
            field : 'userId',
            width : 150,
            align : 'center'
        },{
            title : '交易类型',
            field : 'txnType',
            width : 150,
            align : 'center'
        },{
            title : '交易时间',
            field : 'txnDateDes',
            width : 150,
            align : 'center'
        },{
            title : '交易金额',
            field : 'txnAmt',
            width : 120,
            align : 'center'
        },{
            title : '账单日期',
            field : 'stmtDateDes',
            width : 150,
            align : 'center'
        },{
            title : '开户银行',
            field : 'openBank',
            width : 150,
            align : 'center'
        },{
            title : '卡号 ',
            field : 'cardId',
            width : 150,
            align : 'center'
        },{
            title : '证件类型',
            field : 'certType',
            width : 150,
            align : 'center'
        },{
            title : '证件号',
            field : 'certId',
            width : 150,
            align : 'center'
        },{
            title : '持卡人姓名',
            field : 'usrName',
            width : 150,
            align : 'center'
        },{
            title : '付款标志',
            field : 'flag',
            width : 150,
            align : 'center',
            formatter:function(value,row,index){
            	if(value=='00'){
            		return '对私';
            	}else if(value=='01'){
            		return '对公';
            	}
            }
        },{
        	title : '交易状态',
        	field : 'status',
        	width : 150,
        	align : 'center'
        },{
        	title : '合同号',
        	field : 'contract_no',
        	width : 150,
        	align : 'center',
        },{
        	title : '扣款成功金额 ',
        	field : 'successAmt',
        	width : 150,
        	align : 'center'
        },{
        	title : '扣款失败金额',
        	field : 'failureAmt',
        	width : 150,
        	align : 'center'
        }]],
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + '/application/transaction/txninfo/query',
                data : param,
                type : "post",
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
	
	//刷新
	$("#flush").click(function(){
		$("#userId").textbox('clear');
		$("#txnDateBoot").datetimebox('clear');
		$("#txnDateTop").datetimebox('clear');
		var params = {};
		$('#transactionForList').datagrid('load', params);
	});
	//查询
	$(".search-btn").click(function(){
		var txnDateBoot = $("#txnDateBoot").datetimebox('getValue');
		var txnDateTop = $("#txnDateTop").datetimebox('getValue')
		var params = {
			userId:	$("#userId").textbox('getValue'),
			txnDateBoot:txnDateBoot,
			txnDateTop:txnDateTop
		};
		
		if(txnDateBoot!=null && txnDateBoot!=''&& txnDateTop!=null && txnDateTop!=''&& txnDateBoot > txnDateTop ){
			$.messager.alert("<span style='color: black;'>提示</span>","左侧时间不能大于右侧时间","info");
			return;
		}
		
		$('#transactionForList').datagrid('load', params);
	});
	
});

