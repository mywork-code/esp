$(function(){
    $("#addIntroConfig").window('close');

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
                title : '活动名称',
                field : 'activityName',
                width : 150,
                align : 'center'
            }, {
                title : '开始时间',
                field : 'aStartDate',
                width : 150,
                align : 'center'
            },
            {
                title : '结束时间',
                field : 'aEndDate',
                width : 150,
                align : 'center'
            },
            {
                title : '个人返点',
                field : 'rebate',
                width : 120,
                align : 'center'
            },
            {
                title : '操作',
                field : 'opt',
                width : 150,
                align : 'center',
                formatter : function(value, row, index) {
                    // 授权标示
                    var grantedAuthority=$('#grantedAuthority').val();
                    var content = "";
                        content += "<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.deleteActivity("
                            + row.id+ ");\">关闭活动</a>&nbsp;&nbsp;";
                    return content;
                }
            }
            ]],
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + '/activity/introduce/list',
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


    //添加  banner信息
    $("#add").click(function(){
        $('#addIntroConfig').window({
            minimizable:false,
            maximizable:false,
            collapsible:false,
            modal:true,
            top:$(document).scrollTop() + ($(window).height()-250) * 0.5
        });
        $("#addIntroConfig").window('open');

    });
    //确认   添加  banner信息
    $("#agreeAdd").click(function(){
        var name=$("#name").textbox('getValue');
        if(null == name || name==""){
            $.messager.alert("<span style='color: black;'>提示</span>","活动名称不能为空！","info");
            return;
        }
        var rebate=$("#rebate").textbox('getValue');
        if(null == rebate || rebate==""){
            $.messager.alert("<span style='color: black;'>提示</span>","返点配置不能为空！","info");
            return;
        }
        var startDate=$("#startDate").textbox('getValue');
        if(null == startDate || startDate==""){
            $.messager.alert("<span style='color: black;'>提示</span>","开始时间不能为空！","info");
            return;
        }
        var endDate=$("#endDate").textbox('getValue');
        if(null == endDate || endDate==""){
            $.messager.alert("<span style='color: black;'>提示</span>","结束时间不能为空！","info");
            return;
        }


        //提交from
        var theForm = $("#introConfigForm");
        theForm.form("submit",{
            url : ctx + '/activity/introduce/config',
            success : function(data) {
                debugger;
                var flag1 = data.indexOf('登录系统');
                var flag2 = data.indexOf('</div');
                if (flag1 != -1 && flag2 != -1) {
                    $.messager.alert("操作提示", "登录超时, 请重新登录", "info");
                    window.top.location = ctx + "/logout";
                    return;
                }
                var respon=JSON.parse(data);
                if(respon.status=="1"){
                    $.messager.alert("<span style='color: black;'>提示</span>",respon.msg,"info");
                    $('#addBannerInfor').window('close');
                }else{
                    $.messager.alert("<span style='color: black;'>警告</span>",respon.msg,"warning");
                    return;
                }
                var param = {};
                $('#list').datagrid('load', param);
            }
        });
    });
    //取消   添加  banner信息
    $("#cancelAdd").click(function(){
        $("#addIntroConfig").window('close');
    });

    //查询
    $(".search-btn").click(function(){
        var params={};
        $('#list').datagrid('load',params);
    });


    $.deleteActivity=function(id){
        $.messager.confirm("<span style='color: black;'>操作提示</span>", "您确定关闭该活动吗？", function (r){
            if(r){
                var params={};
                params['id']=id;
                $.ajax({
                    url : ctx + '/activity/introduce/delete',
                    data : params,
                    type : "post",
                    dataType : "json",
                    success : function(data) {
                        if (data.result == false && data.message == 'timeout')
                        {
//		            		$.messager.alert("提示", "超时，请重新登录", "info");
//		            		parent.location.reload();//刷新整个页面
                            $.messager.alert("操作提示", "登录超时, 请重新登录", "info");
                            window.top.location = ctx + "/logout";
                            return false;
                        }
                        if(data.status=="1"){
                            $.messager.alert("<span style='color: black;'>提示</span>",data.msg,"info");
                            var params={};
                            $('#bannerList').datagrid('load',params);
                        }else{
                            $.messager.alert("<span style='color: black;'>提示</span>",data.msg,"info");
                        }
                    }
                });
            }
        })
    };
});
