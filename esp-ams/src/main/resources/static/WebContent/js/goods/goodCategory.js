/**
 * RBAC - Users
 */
$(function() {
	$('#westAttrDataGrid').datagrid({  
		url : ctx + '/application/categoryinfo/category/list',
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
			sortable : true  
		 }, 
		 {  
			 width : '50',  
			 title : '级别',  
			 field : 'level',
			 hidden: 'hidden',
			 sortable : true  
		 }, 
		 {  
			 width : '50',  
			 title : '排序序号',  
			 field : 'sortOrder',  
			 hidden: 'hidden',
			 sortable : true  
		 }, 
		 {  
			 width : '50',  
			 title : 'id',  
			 field : 'id',  
			 hidden: 'hidden',
			 sortable : true  
		 }, 
		 {  
			width : '200',  
			title : '操作',  
			field : 'opt',  
			sortable : true  
		 }  ] ],
		
	});
});
