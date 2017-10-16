 
$(function(){
	/**
	 * 保存活动添加信息
	 */
	$("#agreeAdd").click(function(){
    	if(checkParams()){
    		var theForm = $("#configForm");
			var activityName = $("#activityName").textbox("getValue");
			var startTime = $("#startTime").datetimebox("getValue");
			var endTime = $("#endTime").datetimebox("getValue");
			var activityType = $("#activityType").combobox("getValue");
			console.log(activityName+"::"+startTime+"::"+activityType);
    		theForm.form("submit",{ 
    			url : ctx + '/activity/cfg/add/save',
    			success : function(data) {
    				ifLogoutForm(data);
    				var respon=JSON.parse(data);
    				if(respon.status=="1"){
    					$.messager.alert("<span style='color: black;'>提示</span>",respon.msg,"info");
    					//window.location.href = ctx + "/activity/cfg/importInit?id="+respon.data+"&activityName="+activityName+"&startTime="+startTime+"&endTime="+endTime+"&activityType="+activityType;
    					window.location.href = ctx + "/activity/cfg/edit?id="+respon.data;
    				}else{
    					$.messager.alert("<span style='color: black;'>警告</span>",respon.msg,"warning");
    				}
    			}
    		});
    	}
	});
	function checkParams(){
		var activityName = $("#activityName").textbox('getValue');
		if(activityName == '' || null == activityName){
			$.messager.alert("<span style='color: black;'>提示</span>","请填写活动名称！","info");
			return false;
		}
		if(activityName.length > 12){
			$.messager.alert("<span style='color: black;'>提示</span>","活动名称长度应在12个字符以内！","info");
			return false;
		}
		
		var startTime=$("#startTime").textbox('getValue');
		if(startTime=='' || null==startTime){
			$.messager.alert("<span style='color: black;'>提示</span>","请填写开始时间！","info");
			return false;
		}
		
		var endTime= $("#endTime").textbox('getValue');
		if(endTime=='' || null==endTime){
			$.messager.alert("<span style='color: black;'>提示</span>","请填写结束时间！","info");
			return false;
		}
		
		if(startTime!=null && startTime!=''&&endTime!=null && endTime!=''){
			if(startTime >= endTime){
				$.messager.alert("<span style='color: black;'>提示</span>","开始时间应小于结束时间，请重新填写！",'info');
				return false;
			}
		}
		var activityType = $("#activityType").combobox('getValue');
		if(activityType == '' || null == activityType){
			$.messager.alert("<span style='color: black;'>提示</span>","请选择活动类型！",'info');
			return false;
		}
		if(activityType == 'N'){
			return true;
		}
		var offerSill1 = $("#offerSill1").numberbox('getValue');
		if(offerSill1 == '' || null == offerSill1){
			$.messager.alert("<span style='color: black;'>提示</span>","请填写第一个优惠门槛！",'info');
			return false;
		}
		if(offerSill1.length > 15){
			$.messager.alert("<span style='color: black;'>提示</span>","第一个优惠门槛金额长度要小于15位！",'info');
			return false;
		}
		var offerSill2 = $("#offerSill2").numberbox('getValue');
		if(offerSill2 == '' || null == offerSill2){
			$.messager.alert("<span style='color: black;'>提示</span>","请填写第二个优惠门槛！",'info');
			return false;
		}
		if(offerSill2.length > 15){
			$.messager.alert("<span style='color: black;'>提示</span>","第二个优惠门槛金额长度要小于15位！",'info');
			return false;
		}
		if(offerSill1 == offerSill2){
			$.messager.alert("<span style='color: black;'>提示</span>","优惠门槛不能相同！",'info');
			return false;
		}
		var discount1 = $("#discount1").numberbox('getValue');
		if(discount1 == '' || null == discount1){
			$.messager.alert("<span style='color: black;'>提示</span>","请填写第一个优惠金额！",'info');
			return false;
		}
		if(discount1.length > 15){
			$.messager.alert("<span style='color: black;'>提示</span>","第一个优惠金额长度要小于15位！",'info');
			return false;
		}
		var discount2 = $("#discount2").numberbox('getValue');
		if(discount2 == '' || null == discount2){
			$.messager.alert("<span style='color: black;'>提示</span>","请填写第二个优惠金额！",'info');
			return false;
		}
		if(discount2.length > 15){
			$.messager.alert("<span style='color: black;'>提示</span>","第二个优惠金额长度要小于15位！",'info');
			return false;
		}
		return true;
	}
	
	$("#activityType").combobox({
		onChange: function (n,o) {
			if(n == 'Y'){
				$("#xxxxxxID").show();
			}else{
				$("#offerSill1").numberbox('setValue','');
				$("#offerSill2").numberbox('setValue','');
				$("#discount1").numberbox('setValue','');
				$("#discount2").numberbox('setValue','');
				$("#xxxxxxID").css("display","none");	
			}
		}
	});
})

function ifLogoutForm(data){
	var flag1 = data.indexOf('登录系统');
	var flag2 = data.indexOf('</div');
	if (flag1 != -1 && flag2 != -1) {
		$.messager.alert("操作提示", "登录超时, 请重新登录", "info");
		window.top.location = ctx + "/logout";
		return;
	}
}
