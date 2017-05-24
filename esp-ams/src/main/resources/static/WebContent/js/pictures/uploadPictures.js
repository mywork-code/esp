$(function(){

	//确认   添加  banner信息
	$("#agreeAdd").click(function(){
		var pictureFile= $("#pictureFile").val();
		if(pictureFile=='' || null==pictureFile){
			$.messager.alert("<span style='color: black;'>提示</span>","请选择上传图片！","info");
			return;
		}
		//提交from
		var theForm = $("#addPictureFileForm");
		theForm.form("submit",{ 
			url : ctx + '/application/picture/addPicture',
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
			}
		});
	});


});