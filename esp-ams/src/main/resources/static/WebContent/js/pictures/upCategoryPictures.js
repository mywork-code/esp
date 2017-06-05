$(function(){
	$("#agreeAdd").click(function(){
		var categoryType = $("#categoryType").combobox('getValue');
		if(categoryType=='' || null==categoryType){
			$.messager.alert("<span style='color: black;'>提示</span>","请选择类目名称！","info");
			return;
		}
		var categoryTitle=$("#categoryTitle").textbox('getValue');
		if(categoryType=='' || null==categoryType){
			$.messager.alert("<span style='color: black;'>提示</span>","类目小标题不能为空！","info");
			return;
		}
		
		var categoryPictureFile= $("#categoryPictureFile").val();
		if(categoryPictureFile=='' || null==categoryPictureFile){
			$.messager.alert("<span style='color: black;'>提示</span>","请选择上传类目图片！","info");
			return;
		}
		var categoryBannerPictureFile= $("#categoryBannerPictureFile").val();
		if(categoryBannerPictureFile=='' || null==categoryBannerPictureFile){
			$.messager.alert("<span style='color: black;'>提示</span>","请选择上传类目Banner图片！","info");
			return;
		}
		//提交from
		var theForm = $("#addPicturFile");
		theForm.form("submit",{ 
			url : ctx + '/application/category/picture/addCategoryPicture',
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
					$.messager.alert("<span style='color: black;'>提示</span>","上传成功！","info");

				}else{
					$.messager.alert("<span style='color: black;'>警告</span>",respon.msg,"warning");
				}
			}
		});
	});


});