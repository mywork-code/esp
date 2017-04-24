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
                field : 'name',
                width : 150,
                align : 'center',
                formatter : function(value, row, index) {
                    var content = "";
                    content += "<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.show('"
                        + row.bannerImgUrl+ "');\">"+row.bannerImgUrl+"</a>";
                    return content;
                }
            }, {
                title : '开始时间',
                field : 'bannerType',
                width : 150,
                align : 'center',
                formatter:function(value,row,index){
                    if(value=="index"){
                        return "首页";
                    }else if(value=="sift"){
                        return "精选";
                    }
                }
            },
            {
                title : '结束时间',
                field : 'activityUrl',
                width : 150,
                align : 'center',
                formatter : function(value, row, index) {
                    var content = "";
                    if(row.activityUrl!=null){
                        content += "<a class='easyui-linkedbutton' href='"+row.activityUrl+"'>"+row.activityUrl+"</a>";
                    }
                    return content;
                }
            },
            {
                title : '个人返点',
                field : 'bannerOrder',
                width : 120,
                align : 'center'
            }]],
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + '/application/banner/management/query',
                data : param,
                type : "post",
                dataType : "json",
                success : function(data) {
                    // console.log(data);
                    $.validateResponse(data, function() {
                        // debugger;
                        success(data);
                    });
                }
            })
        }
    });


    $("#bannerType").combobox({
        onSelect:function(){
            if($("#bannerType").combobox('getValue') == "index"){
                $("#fondSpan").empty();
                $("#fondSpan").append("<font color='red'>支持格式：.png或.jpg;宽：750px,高：420px;大小：≤500kb</font>");
            }else{
                $("#fondSpan").empty();
                $("#fondSpan").append("<font color='blue'>支持格式：.png或.jpg;宽：750px,高：280px;大小：≤500kb</font>");
            }
        }
    });

    //添加  banner信息
    $("#add").click(function(){
        $('#addBannerInfor').window({
            minimizable:false,
            maximizable:false,
            collapsible:false,
            modal:true,
            top:$(document).scrollTop() + ($(window).height()-250) * 0.5
        });
        $("#addBannerInfor").window('open');

        $("#bannerName").textbox('setValue','');
        $("#bannerType").combobox('setValue','');
        $("#bannerOrder").numberbox('setValue','');
        $("#bannerFile").val('');
        $("#activityUrl").textbox('setValue','');
    });
    //确认   添加  banner信息
    $("#agreeAdd").click(function(){
//		var bannerName = $("#bannerName").textbox('getValue');
//		if(null == bannerName || bannerName==""){
//			alert("名称不能为空！");
//			return ;
//		}
        var bannerType=$("#bannerType").combobox('getValue');
        if(null == bannerType || bannerType==""){
            $.messager.alert("<span style='color: black;'>提示</span>","类型不能为空！","info");
            return;
        }
        var bannerOrder=$("#bannerOrder").numberbox('getValue');
        if(null == bannerOrder || bannerOrder==""){
            $.messager.alert("<span style='color: black;'>提示</span>","排序不能为空！","info");
            return;
        }
        var bannerFile= $("#bannerFile").val();
        if(bannerFile=='' || null==bannerFile){
            $.messager.alert("<span style='color: black;'>提示</span>","请选择上传图片！","info");
            return;
        }

//		 if(bannerFile!="" && null!= bannerFile){
//				var pos = "." + bannerFile.replace(/.+\./, "");
//				if(bannerFile!=null && pos!=".jpg" && pos!=".png"){
//					$.messager.alert("提示","请选择.png或.jpg类型文件！","info");
//					return;
//				}
//		 }


        //提交from
        var theForm = $("#addBannerFile");
        theForm.form("submit",{
            url : ctx + '/application/banner/management/addBannerFile',
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
                }
                var param = {};
                param['bannerType'] = $('#bannerType2').combobox('getValue');
                $('#bannerList').datagrid('load', param);
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
        params['bannerType']=$('#bannerType2').combobox('getValue');
        $('#bannerList').datagrid('load',params);
    });

});
