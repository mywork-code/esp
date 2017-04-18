/**
 * 数据字典工具类 id ：html元素id param ：请求后台参数 value:html元素默认值 data:后台返回的json数据
 */

function getDataDic(id, param, value) {
	debugger;
	var dataObject = new Object();
	$(id).combobox({
		valueField : 'dataNo',
		textField : 'dataName',
		queryParams : param,
		loader : function(param, success, error) {
			$.ajax({
				url : ctx + '/application/business/datadic/pagelist',
				data : param,
				async : false,
				type : "post",
				dataType : "json",
				success : function(data) {
					$.validateResponse(data, function() {
						success(data.rows);
					});
					dataObject = data.rows;
					// 回调赋值
					$(id).combobox('setValue', value);
				}
			})
		}
	});
	return dataObject;
}