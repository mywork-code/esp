$(function(){

    //Grid
    $('#list').datagrid({
        title : '活动配置信息',
        fit : true,
        fitColumns : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        striped:true,
        toolbar : '#tb',
        columns :[[
            {
                title : '邀请人',
                field : 'mobile',
                width : 150,
                align : 'center'
            }, {
                title : '拉新人数',
                field : 'inviteNum',
                width : 150,
                align : 'center'
            },
            {
                title : '现金',
                field : 'bankAmt',
                width : 150,
                align : 'center'
            },
            {
                title : '额度',
                field : 'creditAmt',
                width : 120,
                align : 'center'
            },
            {
                title : '返现金额',
                field : 'rebateAmt',
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
                    	debugger;
                    	$("#manSum").html(data.total);
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
        var startCreateDate=$("#createDate1").textbox('getValue');
        var endCreateDate=$("#createDate2").textbox('getValue');
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

});
