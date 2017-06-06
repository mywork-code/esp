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
                url : ctx + '/activity/introduce/statistic/list',
                data : param,
                type : "get",
                dataType : "json",
                success : function(data) {
                    $.validateResponse(data, function() {
                    	$("#manSum").html(data.allCount);
                    	$("#cashSum").html(data.bankAmtSum);
                    	$("#amountSum").html(data.creditAmtSum);
                    	$("#returnCashSum").html(data.rebateAmtSum);
                        success(data);
                    });
                }
            })
        }
    });

    //查询
    $(".search-btn").click(function(){
        var startCreateDate=$("#createDate1").datebox('getValue');
        var endCreateDate=$("#createDate2").datebox('getValue');
        if(startCreateDate!=null && startCreateDate!=''&&endCreateDate!=null && endCreateDate!=''){
    		if(startCreateDate>endCreateDate){
    			$.messager.alert("<span style='color: black;'>提示</span>","活动时间：开始时间应早于结束时间！",'info');
    			$('#createDate1').datebox('setValue','');
    			$('#createDate2').datebox('setValue','');
    			return;
    		}
    	}
        var params={};
        params['startCreateDate'] = startCreateDate;
        params['endCreateDate'] = endCreateDate;
        $('#list').datagrid('load',params);
    });

    // 重置
    $("#reset").click(function(){
        $("#createDate1").textbox('setValue','');
        $("#createDate2").textbox('setValue','');
        var params={};
        $('#list').datagrid('load',params);
    });
    
    //查询已放款和预计放款
    $("#awarddetail").combobox({ 
    	onBeforeLoad:function(){
    		$('#awarddetail').combobox('setValue',-1);
    		awarddetai(-1);
    	},
    	onChange:function(){
    		var days = $("#awarddetail").combobox('getValue');
    		awarddetai(days);
    	}
    });
    
    function awarddetai(days){
    	$.ajax({
            url : ctx + '/activity/introduce/sumAmountGroupByType',
            data : {"days":days},
            type : "POST",
            dataType : "json",
            success : function(data) {
        		 $("#loadAmount").html(data.loadAmount);
             	 $("#expectLoadAmount").html(data.expectLoadAmount);
            }
        });
    }
    //查询拉取人数
    $("#awardbindrel").combobox({ 
    	onBeforeLoad:function(){
    		$('#awardbindrel').combobox('setValue',-1);
    		awardbindrel(-1);
    	},
    	onChange:function(){
    		var days = $("#awardbindrel").combobox('getValue');
    		awardbindrel(days);
    	}
    });
    
    function awardbindrel(days){
    	$.ajax({
            url : ctx + '/activity/introduce/inviterUserCountByTime',
            data : {"days":days},
            type : "POST",
            dataType : "json",
            success : function(data) {
            	$("#personSum").html(data);
            }
        });
    }
});
