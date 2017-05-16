/**
 * RBAC - Users
 */
$(function() {
	$('#westAttrDataGrid').datagrid({  
		url : ctx + '/categoryinfo/category/list',
		striped : true, 
		fitColumns : true,
		rownumbers : true,
		queryParams:{},
		sortOrder : 'asc',  
		frozenColumns : [[ 
		 {  
			width : '50',  
			title : '类目名称',  
			field : 'categoryName',
			align : 'center',
			sortable : true  
		 }, 
		 {  
			 width : '50',  
			 title : '级别',  
			 field : 'level',
			 align : 'center',
			 hidden: 'hidden',
			 sortable : true  
		 }, 
		 {  
			 width : '50',  
			 title : '排序序号',  
			 field : 'sortOrder',  
			 align : 'center',
			 hidden: 'hidden',
			 sortable : true  
		 }, 
		 {  
			 width : '50',  
			 title : 'id',  
			 field : 'id',  
			 align : 'center',
			 hidden: 'hidden',
			 sortable : true  
		 }, 
		 {  
			width : '200',  
			title : '操作',  
			field : 'opt', 
			align : 'center',
			sortable : true ,
			formatter : function(value, row, index) {
                var content = "";
                // 编辑
                content += "<a href='javascript:void(0);' class='easyui-linkedbutton'";
                content += " onclick='$.editDetails(\"" + row.id + "\");'>编辑</a>&nbsp;";
                return content;
            }
		 }  ] ],
		
	});
});
