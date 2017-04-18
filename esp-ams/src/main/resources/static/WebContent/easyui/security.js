(function($) {
	$.extend({
		// Validate Response
		validateResponse : function(respJson, callback) {
			var result = respJson.result;
			var message = respJson.message;

			if (result == false && message == "403") {
				$.messager.alert("操作提示", "调用接口无执行权限, 请联系管理员分配权限", "info");
				return false
			}

			if (result == false && message == "timeout") {
				$.messager.alert("操作提示", "登录超时, 请重新登录", "info");
				window.top.location = ctx + "/logout";
				return false;
			}
			if (respJson.status != '1') {
				$.messager.alert("<font color='black'>操作提示</font>", respJson.msg, "info");
				return false
			}
			callback();
		}
	});
})(jQuery);