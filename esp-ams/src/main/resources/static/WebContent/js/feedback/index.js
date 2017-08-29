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
    /**
     * 查询
     */
    $(".search-btn").click(function(){
    	 var params = {};
         params['mobile'] = $("#mobile").textbox('getValue');
         params['module'] = $("#module").combobox('getValue');
         params['feedbackType'] = $("#feedbackType").combobox('getValue');
         params['submitDate1'] = $("#submitDate1").datetimebox('getValue');
         params['submitDate2'] = $("#submitDate2").datetimebox('getValue');
         $('#list').datagrid('load', params);
    });
	    /**
		 * 重置
		 */
	$(".reset-btn").click(function() {
		$("#mobile").textbox('setValue', '');
		$("#module").combobox('setValue', '');
		$("#feedbackType").combobox('setValue', '');
		$("#submitDate1").datetimebox('setValue', '');
		$("#submitDate2").datetimebox('setValue', '');
		var params = {};
		$('#list').datagrid('load', params);
	});
	    /**
		 * 查看上传图片
		 */
	$.lookPicture = function(picture) {
		$("#lookPictureImg1").attr("src", "");
		var pictureList = picture.split(";");
		if (pictureList.length == 1) {
			$("#lookPictureImg1").attr("src", pictureList[0]);
		} else if (pictureList.length== 2) {
			$("#lookPictureImg1").attr("src", pictureList[0]);
			$("#lookPictureImg2").attr("src", pictureList[1]);
		} else if (pictureList.length == 3) {
			$("#lookPictureImg1").attr("src", pictureList[0]);
			$("#lookPictureImg2").attr("src", pictureList[1]);
			$("#lookPictureImg3").attr("src", pictureList[2]);
		}
		$('#lookPictureWindow').window('open');
	}
});