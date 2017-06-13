$(function(){
	
    //Grid
    $('#list').datagrid({
        title : '转介绍放款管理',
        fit : true,
        //fitColumns : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        striped:true,
        toolbar : '#tb',
        columns :[[
			{
			    title : '唯一标识',
			    width : 150,
			    field : 'awardDetailId',
			    hidden: true,
			    align : 'center'
			},
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
            	field : 'releaseDate',
            	width : 150,
            	align : 'center'
            },
            {
            	title : '放款状态',
            	field : 'statusDes',
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
        var params = getParam();
        $('#list').datagrid('load',params);
    });
    
    //导出
    $("#export").click(function(){
    	var params = getParam();
    	debugger;
    	params['busCode'] = 'E004';// 转介绍放款导出
    	exportFile("list","放款信息表",params);
    });
    
    //导入
    $("#import").click(function(){
    	var form = $("#ExcelFileForm");
    	var file = $("#Excelfile").val();
    	if(file == null || file == ''){
    		$.messager.alert("<font color='black'>提示</font>","请选择要上传的Excel文件","info");
    		return;
    	}
    	form.form("submit",{
    		url : ctx + '/application/business/importFile',
    		success : function(response) {
    			var data = JSON.parse(response);
    			if(data.status == '1'){
    				$.messager.alert("<font color='black'>提示</font>", data.msg, "info");
    				$('#Excelfile').val('');
    				var param = getParam();
 			        $('#list').datagrid('load', param);
    			}else{
    				$.messager.alert("<font color='black'>提示</font>", data.msg, "info");
    			}
    		}
    	});
    });
});
	
	function getParam(){
		debugger;
		var loanStatus=$("#loanStatus").combobox('getValue');
	    var realName=$("#realName").textbox('getValue');
	    var mobile=$("#mobile").textbox('getValue');
	    var releaseDate1=$("#releaseDate1").datetimebox('getValue');
	    var releaseDate2=$("#releaseDate2").datetimebox('getValue');
	    var applyDate1=$("#applyDate1").datetimebox('getValue');
	    var applyDate2=$("#applyDate2").datetimebox('getValue');
	    
	    if(releaseDate1!=null && releaseDate1!=''&&releaseDate2!=null && releaseDate2!=''){
			if(releaseDate1>releaseDate2){
				$.messager.alert("<span style='color: black;'>提示</span>","放款时间：开始时间应早于结束时间！",'info');
				$('#releaseDate1').datebox('setValue','');
				$('#releaseDate2').datebox('setValue','');
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
	    		loanStatus:loanStatus,
	    		realName:realName,
	    		mobile:mobile,
	    		releaseDate1:releaseDate1,
	    		releaseDate2:releaseDate2,
	    		applyDate1:applyDate1,
	    		applyDate2:applyDate2,
	    };
	    
	    return params;
	}