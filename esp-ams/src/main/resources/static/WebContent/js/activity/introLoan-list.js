$(function(){
	
    //Grid
    $('#list').datagrid({
        title : '转介绍放款管理',
        fit : true,
        fitColumns : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        striped:true,
        toolbar : '#tb',
        columns :[[
            {
                title : '邀请人手机号',
                field : 'mobile',
                width : 150,
                align : 'center'
            }, {
                title : '可提现金额 ',
                field : 'canWithdrawAmount',
                width : 150,
                align : 'center'
            },
            {
                title : '申请提现提交时间',
                field : 'applyDate',
                width : 150,
                align : 'center'
            },
            {
                title : '申请提现金额',
                field : 'amount',
                width : 120,
                align : 'center'
            },
            {
                title : '推荐人姓名',
                field : 'realName',
                width : 120,
                align : 'center'
            },
            {
            	title : '推荐人银行卡号',
            	field : 'cardNO',
            	width : 120,
            	align : 'center'
            },
            {
            	title : '所属银行',
            	field : 'cardBank',
            	width : 120,
            	align : 'center'
            },
            {
            	title : '放款时间',
            	field : 'arrivedDate',
            	width : 120,
            	align : 'center'
            },
            {
            	title : '放款状态',
            	field : 'status',
            	width : 120,
            	align : 'center'
            }
        ]],
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + '/activity/introduce/loans/list',
                data : param,
                type : "get",
                dataType : "json",
                success : function(data) {
                    $.validateResponse(data, function() {
                        success(data);
                    });
                }
            })
        }
    });

    //查询
    $(".search-btn").click(function(){
        var loanStatus=$("#loanStatus").combobox('getValue');
        var realName=$("#realName").textbox('getValue');
        var mobile=$("#mobile").textbox('getValue');
        var arrivedDate1=$("#arrivedDate1").datetimebox('getValue');
        var arrivedDate2=$("#arrivedDate2").datetimebox('getValue');
        var applyDate1=$("#applyDate1").datetimebox('getValue');
        var applyDate2=$("#applyDate2").datetimebox('getValue');
        
        if(arrivedDate1!=null && arrivedDate1!=''&&arrivedDate2!=null && arrivedDate2!=''){
    		if(arrivedDate1>arrivedDate2){
    			$.messager.alert("<span style='color: black;'>提示</span>","放款时间：开始时间应早于结束时间！",'info');
    			$('#arrivedDate1').datebox('setValue','');
    			$('#arrivedDate2').datebox('setValue','');
    			return;
    		}
    	}
        if(applyDate1!=null && applyDate1!=''&&applyDate2!=null && applyDate2!=''){
        	if(arrivedDate1>arrivedDate2){
        		$.messager.alert("<span style='color: black;'>提示</span>","申请提现时间：开始时间应早于结束时间！",'info');
        		$('#applyDate1').datebox('setValue','');
        		$('#applyDate2').datebox('setValue','');
        		return;
        	}
        }
        var params={
        		loanStatus:"loanStatus",
        		realName:"realName",
        		mobile:"mobile",
        		arrivedDate1:"arrivedDate1",
        		arrivedDate2:"arrivedDate2",
        		applyDate1:"applyDate1",
        		applyDate2:"applyDate2",
        };
        $('#list').datagrid('load',params);
    });
});
