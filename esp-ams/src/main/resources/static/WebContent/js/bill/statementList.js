$(function(){
	//Grid 列表
	$("#statementForList").datagrid({
        title : '帐单查询',
        fit : true,
        fitColumns : false,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        striped:true,
        toolbar : '#tb',
        columns : [[{
            title : '用户ID',
            field : 'userId',
            width : 150,
            align : 'center'
        }, {
            title : '账单日期',
            field : 'stmtDateDes',
            width : 150,
            align : 'center'
        }, {
            title : '账单月份',
            field : 'stmtMonth',
            width : 150,
            align : 'center'
        },{
            title : '姓名',
            field : 'name',
            width : 150,
            align : 'center',
        },{
            title : '最后还款日期',
            field : 'pmtDueDateDes',
            width : 150,
            align : 'center'
        },{
            title : '消费总金额',
            field : 'totalAmt',
            width : 120,
            align : 'center'
        },{
            title : '额度消费金额',
            field : 'creditAmt',
            width : 150,
            align : 'center'
        },{
            title : '额度取现金额',
            field : 'loanAmt',
            width : 150,
            align : 'center'
        },{
            title : '银行卡实付金额 ',
            field : 'cardPayAmt',
            width : 150,
            align : 'center'
        },{
            title : '当期账单金额',
            field : 'ctdStmtBal',
            width : 150,
            align : 'center'
        },{
            title : '当期取现金额',
            field : 'ctdCashAmt',
            width : 150,
            align : 'center'
        },{
            title : '当期消费金额',
            field : 'ctdRetailAmt',
            width : 150,
            align : 'center'
        },{
            title : '当期消费笔数',
            field : 'ctd_retail_cnt',
            width : 150,
            align : 'center'
        },{
        	title : '全部应还款额',
        	field : 'qual_grace_bal',
        	width : 150,
        	align : 'center'
        },{
        	title : '是否已全额还款',
        	field : 'grace_days_full_ind',
        	width : 150,
        	align : 'center',
        	formatter:function(val,rowData,rowIndex){
            	if(val== '1'){
            		return '是';
            	}else {
            		return '否';
            	}
            }
        },{
        	title : '电子邮箱 ',
        	field : 'email',
        	width : 150,
        	align : 'center'
        },{
        	title : '移动电话',
        	field : 'mobile_no',
        	width : 150,
        	align : 'center'
        },{
        	title : '账单状态',
        	field : 'stmt_status',
        	width : 150,
        	align : 'center',
        	formatter :function(value, row, index){
        		if(value == 's01'){
        			return '正常账单';
        		}else if(value == 's02'){
        			return '账单已分期';
        		}
        	}
        },{
            title : '操作',
            field : 'opt',
            width : 150,
            align : 'center',
            formatter : function(value, row, index) {
            	var content = "";
                    content += "<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.statementInfor('"
        				+ row.userId + "');\">账单详情</a>&nbsp;&nbsp;";
            	//encodeURI(JSON.stringify(row) )
                return content;
            }
        }]],
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + '/application/bill/statement/query',
                data : param,
                type : "post",
                dataType : "json",
                success : function(data) {
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
		$("#stmtDate").datebox('clear');
		var params = {};
		$('#statementForList').datagrid('load', params);
	});
	//查询
	$(".search-btn").click(function(){
		var params = {
			userId:	$("#userId").textbox('getValue'),
			stmtDate:$("#stmtDate").datebox('getValue'),
		};
		$('#statementForList').datagrid('load', params);
	});
	
});

