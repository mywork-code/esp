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
                field : 'activityName',
                width : 150,
                align : 'center'
            }, {
                title : '拉新人数',
                field : 'aStartDate',
                width : 150,
                align : 'center'
            },
            {
                title : '现金',
                field : 'aEndDate',
                width : 150,
                align : 'center'
            },
            {
                title : '额度',
                field : 'rebate',
                width : 120,
                align : 'center'
            },
            {
                title : '返现金额',
                field : 'rebate',
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
                        success(data);
                    });
                }
            })
        }
    });



    //查询
    $(".search-btn").click(function(){
        var params={};
        $('#list').datagrid('load',params);
    });

    // 重置
    $("#reset").click(function(){
        $('#bannerType2').combobox('setValue','');
        var params={};
        $('#list').datagrid('load',params);
    });

});
