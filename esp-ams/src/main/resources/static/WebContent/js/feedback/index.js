$(function(){
	$('#lookPictureWindow').window('close');

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
            },{
                title : '关联模块',
                field : 'module',
                width : 120,
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
            },{
				title : '操作',
				field : 'opt',
				width : 120,
				align : 'center',
				formatter : function(value, row, index) {
					var content = "";
					 if (null !=row.picture  && row.picture!='') {
                         content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton'";
                         content += " onclick='$.lookPicture(\"" + row.picture + "\");'>查看上传图片</a>";
                     }  
				 return content;
			}}]],
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + '/feedbackinfo/feedback/list',
                data : param,
                type : "post",
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
         params['createDateBegin'] = $("#submitDate1").datetimebox('getValue');
         params['createDateEnd'] = $("#submitDate2").datetimebox('getValue');
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
		var $lookPictureImgWrap = $("#lookPictureImgWrap");
		var $lookPictureShowImg = $("#lookPictureShowImg");
		var html = '';
		var pictureList = picture.split(";");
		pictureList.forEach(function(e,i){
			html += '<img  src="'+e+'" />';
		});
		console.log(pictureList)
		$lookPictureShowImg.attr("src",pictureList[0]);
		$lookPictureImgWrap.html(html);
		$lookPictureImgWrap.on("click","img",function(){
			$lookPictureShowImg.attr("src",$(this).attr("src"))
		})
		
		$('#lookPictureWindow').window('open');
	}
});