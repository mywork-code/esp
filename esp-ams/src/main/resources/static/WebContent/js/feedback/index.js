$(function(){

    //Grid
    $('#list').datagrid({
        title : '意见反馈信息',
        fit : true,
        fitColumns : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        striped:true,
        nowrap:false,
        toolbar : '#tb',
        columns :[[
            {
                title : '用户名',
                field : 'mobile',
                width : 150,
                align : 'center'
            }, {
                title : '提交时间',
                field : 'createDate',
                width : 150,
                align : 'center'
            },
            {
                title : '反馈问题类型',
                field : 'feedbackType',
                width : 120,
                align : 'center'
            },
            {
                title : '反馈内容',
                field : 'comments',
                width : 150,
                align : 'center'
            }
            ]],
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + '/feedbackinfo/feedback/list',
                data : param,
                type : "get",
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
});