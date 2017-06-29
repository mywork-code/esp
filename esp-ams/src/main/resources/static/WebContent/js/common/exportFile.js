/**
 * 导出文件 .csv
 */

// id：dataGrid对应id , fileName: 文件名称 , params:要查询的参数
function exportFile (id, fileName, params)
{
	debugger;
	params = JSON.stringify (params);
	
	// 字段标题集合 {"orderId":"订单id"，"orderAmt":"订单金额"}
	var attrs = {};
	
	// 字段集合
	var fields = $ ("#" + id).datagrid ('getColumnFields');
	
	// datagrid列属性
	var columns = $ ("#" + id).datagrid ('options').columns[0];
	
	// 填充字段标题集合
	$.each (fields, function (i, n)
	{
		// 去除不需要的字段
		if (n != "opt"&&n != "id")
		{
			attrs[n] = columns[i].title;
		}
	});
	
	// 格式化
	attrs = JSON.stringify (attrs);
	
	// 创建Form
	var form = $ ('<form></form>');
	// 设置属性
	form.attr ('action', ctx + '/application/business/exportFile');
	form.attr ('method', 'post');
	form.attr ('target', '_self');
	
	// 文件名
	var my_input1 = $ ('<input type="text" name="fileName"/>');
	my_input1.attr ('value', fileName);
	
	// 标题头
	var my_input2 = $ ('<input type="text" name="attrs"/>');
	my_input2.attr ('value', attrs);
	
	// 附加到Form
	form.append (my_input1);
	form.append (my_input2);
	
	// 数据
	var my_input3 = $ ('<input type="text" name="params"/>');
	my_input3.attr ('value', params);
	form.append (my_input3);
	
	var my_input4 = $ ('<input type="submit"/>');
	form.append (my_input4);
	$('body').append(form);
	
	// 提交表单
	my_input4.click ();
	form.remove();
}

