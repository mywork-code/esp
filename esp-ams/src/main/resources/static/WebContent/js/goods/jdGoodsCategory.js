/**
 * GOODS-category
 */
$(function() {
	var categoryId1;
	var categoryId2;
	var categoryId3;
	
	
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
		 categoryId1 = rowFirst.categoryId;
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
		 categoryId2 = rowSecond.categoryId;
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
		categoryId3 = response.categoryId;
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
	                    title : '京东类目id',
	                    field : 'cateId',
	                    hidden : true,
	                    width : 10,
	                    align : 'center'
	                },{
	                    title : '级别',
	                    field : 'catClass',
	                    hidden : true,
	                    width : 10,
	                    align : 'center'
	                },{
	                    title : '安家趣花的三级类目',
	                    field : 'categoryId3',
	                    hidden : true,
	                    width : 10,
	                    align : 'center'
	                },{
	                    title : '是否关联',
	                    field : 'flag',
	                    hidden : true,
	                    width : 10,
	                    align : 'center'
	                },
	                {
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
	                			debugger;
	                			if(row.flag && row.categoryId3 == categoryId3){
	                				var checkboxflag = '<div class="border-circle"><span class="switch-circle"  style="left:54px" data-sku="'+encodeURI(JSON.stringify(row))+'"></span><span class="relation-text" style="left:14px;">已关联</span></div>';
		                		}else{
		                			var checkboxflag = '<div class="border-circle"><span class="switch-circle" style="left:0px"  data-sku="'+encodeURI(JSON.stringify(row))+'"></span><span class="relation-text" style="left:27PX;">未关联</span></div>';
		                		}
	                			return checkboxflag;
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
	                    
	                    
	                    stime = window.setInterval("start()", 5000);
	                    
	                	$('.datagrid-cell').on('click','.border-circle',function(){  
	                		if($(".border-circle").hasClass('disabled')){
	                			return;
	                		}
	                		$(".border-circle").addClass('disabled');
	                		var $that = $(this).find('.switch-circle');
	                		var rowData = JSON.parse(decodeURI($that.attr('data-sku')));
	                		var param = {
	                				"jdCategoryId":rowData.id,
	                				"cateId":rowData.cateId,
	                				"catClass":rowData.catClass,
	                				"categoryId1":categoryId1,
	                				"categoryId2":categoryId2,
	                				"categoryId3":categoryId3
	                		};
	                		
	                		if($that.css('left')=='0px') {
	                			$.ajax({
	        	        			url : ctx + '/application/jd/category/relevance',
	        	        			data : {'param':JSON.stringify(param)},
	        	        			type : "post",
	        	        			dateType:"json",
	        	        			success : function(data) {
	        	        				ifLogout(data);
	        	        				if(data.status=="1"){
	        	        					debugger;
	        	                    		$.messager.alert("提示",data.msg,'info');  
	        	                    		$that.animate({left:"54px"},50)
	        	                			$that.parent().find('.relation-text').css('left','0px');
	        	                    		$that.parent().find('.relation-text').html('已关联');
	        	                    	}else{
	        	                    		$.messager.alert("错误",data.msg,'error');  
	        	                    	}
	        	        			}
	        	        		});
	                			
	                		} else if($that.css('left')=='54px'){
	                			$.messager.confirm('确认','您确认想要取消关联吗？',function(r){    
	                			    if (r){  
	                			    	$.ajax({
	    	        	        			url : ctx + '/application/jd/category/disrelevance',
	    	        	        			data : {'param':JSON.stringify(param)},
	    	        	        			type : "post",
	    	        	        			dateType:"json",
	    	        	        			success : function(data) {
	    	        	        				ifLogout(data);
	    	        	        				if(data.status=="1"){
	    	        	        					debugger;
	    	        	                    		$.messager.alert("提示",data.msg,'info');  
	    	        	                    		$that.animate({left:"0px"},50)
	    	        	                			$that.parent().find('.relation-text').css('left','27px');
	    	        	                    		$that.parent().find('.relation-text').html('未关联');
	    	        	                    	}else{
	    	        	                    		$.messager.alert("错误",data.msg,'error');  
	    	        	                    	}
	    	        	        			}
	    	        	        		});
	                			    	
	                			    }    
	                			});  
	                		}
		            	})
	                }
	            })
	        }
	                
		})
	}
//	$('.border-circle').on('click','.switch-circle',function(){
//		alert('jjj');
//		$('.switch-circle').css('right','0');
//	});
//	console.log('123');
	$('.switch-circle').css('width','50px');
//	$('.border-circle').on('click','.switch-circle',function(){
//		alert('123');
//	})
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

var stime;
function start() {
   $(".border-circle").removeClass("disabled");
   window.clearInterval(stime);
}

