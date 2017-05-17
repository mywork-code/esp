/**
 * GOODS-category
 */
$(function() {
	$("#editCategoryDetail").window('close');
	
	$('#westAttrDataGrid').datagrid({  
		url : ctx + '/categoryinfo/category/list',
		striped : true, 
		fitColumns : true,
		rownumbers : true,
		queryParams:{},
		pagination : true,
		sortOrder : 'asc',  
		columns : [[ 
		 {  
			width : '100',  
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
			 title : '类目 id',  
			 field : 'categoryId',  
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
                content += " onmouseover='$.optEdit(\"" + row.categoryId +"\",\""+row.level+"\",\""+row.categoryName+"\");'>操作</a>&nbsp;";
                return content;
            }
		 }  ] ],
		 onClickRow: function (index, row) {  //easyui封装好的事件（被单机行的索引，被单击行的值）
			 var id = row.categoryId;
			 $('#eastAttrDataGrid').datagrid({
				 url : ctx + '/categoryinfo/category/list',
				 rownumbers : false,
				 queryParams:{
					'parentId':'null',
				 },
			 })
			 $('#centerAttrDataGrid').datagrid({  
			    url : ctx + '/categoryinfo/category/list',
				striped : true, 
				fitColumns : true,
				rownumbers : true,
				queryParams:{
					'parentId':id,
				},
				sortOrder : 'asc',  
				columns : [[ 
				 {  
					width : '100',  
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
					 title : '类目 id',  
					 field : 'categoryId',  
					 align : 'center',
					 hidden: 'hidden',
					 sortable : true  
				 }, 
				 {  
					 width : '50',  
					 title : '父id',  
					 field : 'parentId',  
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
		                content += " onmouseover='$.optEdit(\"" + row.categoryId +"\",\""+row.level+"\",\""+row.categoryName+"\",\""+row.parentId+"\");'>操作</a>&nbsp;";
		                return content;
		            }
				 }  ] ],
				 onClickRow: function (index, row) {  //easyui封装好的事件（被单机行的索引，被单击行的值）
					 var id = row.categoryId;
					 $('#eastAttrDataGrid').datagrid({  
						 url : ctx + '/categoryinfo/category/list',
							striped : true, 
							fitColumns : true,
							rownumbers : true,
							queryParams:{
								'parentId':id,
							},
							sortOrder : 'asc',
							columns : [[ 
						 {  
							width : '100',  
							title : '类目名称',  
							field : 'categoryName',
							align : 'center',
							sortable : true  
						 }, 
						 {  
							 width : '100',  
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
							 title : '类目 id',  
							 field : 'categoryId',  
							 align : 'center',
							 hidden: 'hidden',
							 sortable : true  
						 }, 
						 {  
							 width : '50',  
							 title : '父id',  
							 field : 'parentId',  
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
				                content += " onmouseover='$.optEdit(\"" + row.categoryId +"\",\""+row.level+"\",\""+row.categoryName+"\",\""+row.parentId+"\");'>操作</a>&nbsp;";
				                return content;
				            }
						 }  ] ]
					 });
				 }
			 });
         }
     });
	var categoryId;
	var categoryName;
	var categoryLevel;
	var parentId;
	//操作菜单显示 
	$.optEdit=function(id,level,name,pId){
		debugger;
		if(level=='1'){
			$("#oneCategory").css("display","inline");
			$("#twoCategory").css("display","none");
			$("#threeCategory").css("display","none");
		}else if(level=='2'){
			$("#oneCategory").css("display","none");
			$("#twoCategory").css("display","inline");
			$("#threeCategory").css("display","none");
		}else if(level=='3'){
			$("#oneCategory").css("display","none");
			$("#twoCategory").css("display","none");
			$("#threeCategory").css("display","inline");
		}
		categoryId = id;
		categoryLevel = level;
		categoryName=name;
		parentId = pId;
		var  evt = (evt) ? evt : ((window.event) ? window.event : null);
		$('#optMenu').menu('show', {     
			left:evt.pageX,
			top:evt.pageY
		}); 
	}
	
	$('#optMenu').menu({    
	    onClick:function(item){    
	    	console.log(item);
	    	if(item.text == '编辑'){
	    		debugger;
	    		$("#editCategoryDetail").window('open');
	    		$("#categoryName").textbox('setValue',categoryName);
	    		
	    	}else if(item.text == '删除'){
	    		$.messager.confirm('删除确认', '确定要删除么?删除后不可恢复', function(r) {
	                if (!r) {
	                    return;
	                }
	                var address = ctx + '/categoryinfo/category/delete';
	                $.getJSON(address, {
	                    "id" : categoryId
	                }, function(resp) {
	                    $.validateResponse(resp, function() {
	                        $("#flashCategory").click();
	                    });
	                });
	            });
	    	}else if(item.text == '上移'){
	    		debugger;
	    		$.messager.alert("提示","上移",info);
	    		var row = $("#westAttrDataGrid").datagrid('getSelected');
	    	    var index = $("#westAttrDataGrid").datagrid('getRowIndex', row);
	    	    mysort(index, 'up', 'westAttrDataGrid');
	    	}else if(item.text == '下移'){
	    		
	    	}
	    }    
	}); 
	//确认编辑 
	$("#confirmGoodCategory").click(function(){
		debugger;
		var categoryName = $("#categoryName").textbox('getValue');
		var params = {
			'categoryId':categoryId,
			'categoryName':categoryName,
			'level':categoryLevel
		};
		$.ajax({
			type : "POST",
			url : ctx + '/categoryinfo/category/updateName',
			data : JSON.stringify(params),
			contentType : 'application/json',
			dateType:"json",
			success : function(data) {
				debugger;
				ifLogout(data);
				if(data.status=="1"){
            		$.messager.alert("提示",data.msg,'info');  
            		//关闭弹出窗
            		$('#editCategoryDetail').window('close');
            		//var params={'categoryId':categoryId};
            		if(categoryLevel == '1'){
            			$('#westAttrDataGrid').datagrid('load', {'categoryId':categoryId});
            		}else if(categoryLevel == '2'){
            			$('#centerAttrDataGrid').datagrid('load', {'parentId':parentId});
            		}else if(categoryLevel == '3'){
            			$('#eastAttrDataGrid').datagrid('load', {'parentId':parentId});
            		}
            	}else{
            		$.messager.alert("错误",data.msg,'error');  
            	}
			}
		});
	});
	//取消编辑 
	$("#disGoodCategory").click(function(){
		$("#editCategoryDetail").window('close');
	});
	
	
	//刷新分类
	$("#flashCategory").click(function() {
		$('#eastAttrDataGrid').datagrid({
			 url : ctx + '/categoryinfo/category/list',
			 rownumbers : false,
			 queryParams:{}
		 })
	});
	
});

//判断是否超时
function ifLogout(data){
	debugger;
	if(data.message=='timeout' && data.result==false){
		$.messager.alert("操作提示", "登录超时, 请重新登录", "info");
		window.top.location = ctx + "/logout";
		return false;
	}
}
//上移
function MoveUp() {
    var row = $("#Student_Table").datagrid('getSelected');
    var index = $("#Student_Table").datagrid('getRowIndex', row);
    mysort(index, 'up', 'Student_Table');
     
}
//下移
function MoveDown() {
    var row = $("#Student_Table").datagrid('getSelected');
    var index = $("#Student_Table").datagrid('getRowIndex', row);
    mysort(index, 'down', 'Student_Table');
     
}
function mysort(index, type, gridname) {
    if ("up" == type) {
        if (index != 0) {
            var toup = $('#' + gridname).datagrid('getData').rows[index];
            var todown = $('#' + gridname).datagrid('getData').rows[index - 1];
            $('#' + gridname).datagrid('getData').rows[index] = todown;
            $('#' + gridname).datagrid('getData').rows[index - 1] = toup;
            $('#' + gridname).datagrid('refreshRow', index);
            $('#' + gridname).datagrid('refreshRow', index - 1);
            $('#' + gridname).datagrid('selectRow', index - 1);
        }
    } else if ("down" == type) {
        var rows = $('#' + gridname).datagrid('getRows').length;
        if (index != rows - 1) {
            var todown = $('#' + gridname).datagrid('getData').rows[index];
            var toup = $('#' + gridname).datagrid('getData').rows[index + 1];
            $('#' + gridname).datagrid('getData').rows[index + 1] = todown;
            $('#' + gridname).datagrid('getData').rows[index] = toup;
            $('#' + gridname).datagrid('refreshRow', index);
            $('#' + gridname).datagrid('refreshRow', index + 1);
            $('#' + gridname).datagrid('selectRow', index + 1);
        }
    }
 
}


