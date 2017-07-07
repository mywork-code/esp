/**
 * GOODS-category
 */
$(function() {
	$('#jdCategoryTreeWindow').window('close');
	
	$('#westAttrDataGrid').datagrid({  
		striped : true, 
		fitColumns : true,
		rownumbers : true,
		singleSelect : true,
		pagination : false,
		columns : [[ 
		 {  
			width : '100',  
			title : '类目名称',  
			field : 'categoryName',
			align : 'center',
		 }, 
		 {  
			 width : '50',  
			 title : '级别',  
			 field : 'level',
			 align : 'center',
			 hidden: 'hidden',
		 }, 
		 {  
			 width : '50',  
			 title : '排序序号',  
			 field : 'sortOrder',  
			 align : 'center',
			 hidden: 'hidden',
		 }, 
		 {  
			 width : '50',  
			 title : '类目 id',  
			 field : 'categoryId',  
			 align : 'center',
			 hidden: 'hidden',
		 }] ],
		 onClickRow: function (index, row) {
			 firstClickRowFunction(index,row)
		 },
         loader : function(param, success, error) {
            $.ajax({
                url : ctx + '/categoryinfo/category/list',
                data : param,
                type : "get",
                dataType : "json",
                success : function(data) {
                    $.validateResponse(data, function() {
                        success(data.rows);
                    });
                }
            })
        }
     });
	
	/**单击一级类目执行方法**/
	function firstClickRowFunction(indexFirst,rowFirst){
		 var id = rowFirst.categoryId;
		 clickPid = rowFirst.categoryId;
		 ifClickAndLevel = rowFirst.level;
		 $('#eastAttrDataGrid').datagrid({
		   rownumbers : false,
		   striped : false,
		   loader : function(param, success, error) {
	            $.ajax({
	                url : ctx + '/categoryinfo/category/list',
	                data : {'parentId':-2,},
	                type : "get",
	                dataType : "json",
	                success : function(data) {
	                    $.validateResponse(data, function() {
	                        success(data.rows);
	                    });
	                }
	            })
		       }
		  });
		 $('#centerAttrDataGrid').datagrid({  
			striped : true, 
			fitColumns : true,
			rownumbers : true,
			singleSelect : true,
			queryParams:{
				'parentId':id,
			},
			columns : [[ 
			 {  
				width : '100',  
				title : '类目名称',  
				field : 'categoryName',
				align : 'center',
			 }, 
			 {  
				 width : '50',  
				 title : '级别',  
				 field : 'level',
				 align : 'center',
				 hidden: 'hidden',
			 }, 
			 {  
				 width : '50',  
				 title : '排序序号',  
				 field : 'sortOrder',  
				 align : 'center',
				 hidden: 'hidden',
			 }, 
			 {  
				 width : '50',  
				 title : '类目 id',  
				 field : 'categoryId',  
				 align : 'center',
				 hidden: 'hidden',
			 }, 
			 {  
				 width : '50',  
				 title : '父id',  
				 field : 'parentId',  
				 align : 'center',
				 hidden: 'hidden',
			 }] ],
			 onClickRow: function (index, row) {  //easyui封装好的事件（被单机行的索引，被单击行的值）
				secondClickRowFunction(index,row);
			 },
			 loader : function(param, success, error) {
	            $.ajax({
	                url : ctx + '/categoryinfo/category/list',
	                data : param,
	                type : "get",
	                dataType : "json",
	                success : function(data) {
	                    $.validateResponse(data, function() {
	                        success(data.rows);
	                    });
	                }
	            })
	        }
		 });
	}
	/**单击二级类目执行方法**/
	function secondClickRowFunction(indexSecond,rowSecond){
		 var id = rowSecond.categoryId;
		 clickPid = rowSecond.categoryId;
		 ifClickAndLevel = rowSecond.level;
		 $('#eastAttrDataGrid').datagrid({  
				striped : true, 
				fitColumns : true,
				rownumbers : true,
				singleSelect : true,
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
			 }, 
			 {  
				 width : '100',  
				 title : '级别',  
				 field : 'level',
				 align : 'center',
				 hidden: 'hidden',
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
			 }, 
			 {  
				 width : '50',  
				 title : '父id',  
				 field : 'parentId',  
				 align : 'center',
				 hidden: 'hidden',
			 }, 
			 {  
				 width : '50',  
				 title : '图片url',  
				 field : 'pictureUrl',  
				 align : 'center',
				 hidden: 'hidden',
			 }, 
			 {  
				width : '200',  
				title : '操作',  
				field : 'opt', 
				align : 'center',
				formatter : function(value, row, index) {
	                var content = "";
	                // 编辑
	                content += "<a href='javascript:void(0);' class='easyui-linkedbutton'";
	                content += " onclick='$.optEdit(\"" + encodeURI(JSON.stringify(row)) +"\",\""+index+"\");'>编辑</a>&nbsp;";
	                return content;
	            }
			 }  ] ],
			 loader : function(param, success, error) {
	            $.ajax({
	                url : ctx + '/categoryinfo/category/list',
	                data : param,
	                type : "get",
	                dataType : "json",
	                success : function(data) {
	                    $.validateResponse(data, function() {
	                        success(data.rows);
	                    });
	                }
	            })
	        }
		 });
	}
	
	$.optEdit = function(row,index){
		$('#jdCategoryTreeWindow').window('open');
		var response = JSON.parse(decodeURI(row));
		
		$('#jdCategoryTree').treegrid({
			title : '关联京东类目',
	    	fit : true,
	        idField : 'id',
	        treeField : 'text',
	        fitColumns : true,
	        rownumbers : true,
	        singleSelect : true,
	        striped:true,
	        columns : [[{
	                    title : '编号',
	                    field : 'id',
	                    hidden : true,
	                    width : 10,
	                    align : 'center'
	                }, {
	                    title : '菜单名称',
	                    field : 'text',
	                    width : 100,
	                    align : 'left'
	                }, {
	                	title : '操作',
	                	field : 'opt',
	                	witch : 150,
	                	align : 'center',
	                	formatter : function(value, row, index) {
	                		if(row.catClass == '2'){
	                			return "<input class='easyui-switchbutton' 'checked'>" 
	                		}
	                	}
	                } ]],
	        loader : function(param, success, error) {
	            $.ajax({
	                url : ctx + "/application/jd/category/allCategorys",
	                data : param,
	                type : "GET",
	                dataType : "json",
	                success : function(resp) {
	                    $.validateResponse(resp, function() {
	                        success(resp.data);
	                    });
	                }
	            })
	        }
	                
		})
	}
	
});

//判断是否超时
function ifLogout(data){
	if(data.message=='timeout' && data.result==false){
		$.messager.alert("操作提示", "登录超时, 请重新登录", "info");
		window.top.location = ctx + "/logout";
		return false;
	}
}

/** 回显图标* */
function loadPic (id,pictureUrl)
{
	debugger;
	if (pictureUrl != null)
	{
		$ ("#"+id).attr ("src","");
		$ ("#"+id).attr ("src", ctx + "/fileView/query?picUrl=" + pictureUrl);
	}else{
		$ ("#"+id).attr ("src", '');
	}
}
/**正则校验**/
function regExp_pattern(str, pattern) {
    return pattern.test(str);
}

