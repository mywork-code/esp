	//定义全局变量三个类目id
	var categoryId1;
	var categoryId2;
	var categoryId3;
	var addGoodId;
	
	//编辑显示三级类目所需变量
	var editGoodLogoUrl;//商品墙图片url
	var editCategoryId1;
	var editCategoryId2;
	var editCategoryId3;
	var editGoodId;
	
	// 新增库存共用 id
	var finalGoodId;
	
	//添加商品--是否已经保存商品信息
	var ifSaveGoodsFlag = false;
/**
 * GOODS - info
 */
$(function() {
	//初始化
//	$('#addGoodsInfo').window('close');
//	$('#editGoodsInfo').window('close');
	$('#goodsStockInfo').window('close');
	$('#addGoodsStockInfoWin').window('close');
	$('#editGoodsStockInfoWin').window('close');
	$('#addAmtGoodsStockInfoWin').window('close');
	$('#bannerPicUpWin').window('close');
	$('#previewBannerWin').window('close');
	$('#previewProductWin').window('close');
	
    $('#tablelist').datagrid({
        title : '商品列表',
        rownumbers : true,
        pagination : true,
        striped:true,
//        fit:true,
//        fitColumns:true,
        toolbar : '#tb',
//        rowStyler:function(rowIndex,rowData){
//        	if(rowData.colFalgt=='1'){
//        		return 'background-color:#6293BB;';
//        	}
//        },
        columns : [[
                {
                    title : '商户名称',
                    field : 'merchantName',
                    width : 90,
                    align : 'center'
                },
                {  
       			 width : '50',  
       			 title : '类目 id',  
       			 field : 'categoryId1',  
       			 align : 'center',
       			 hidden: 'hidden'
       		 	}, 
       		 	{  
       		 		width : '50',  
       		 		title : '类目 id',  
       		 		field : 'categoryId2',  
       		 		align : 'center',
       		 		hidden: 'hidden'
       		 	}, 
       		 	{  
       		 		width : '50',  
       		 		title : '类目 id',  
       		 		field : 'categoryId3',  
       		 		align : 'center',
       		 		hidden: 'hidden'
       		 	}, 
                {
                	title : '商户名称',
                	field : 'merchantName',
                	width : 90,
                	align : 'center'
                },
                {
                    title : '商品名称',
                    field : 'goodsName',
                    width : 90,
                    align : 'center'
                }, {
                    title : '商品型号',
                    field : 'goodsModel',
                    width : 80,
                    align : 'center'
                }, {
                    title : '商品小标题',
                    field : 'goodsTitle',
                    width : 90,
                    align : 'center'
                }, {
                    title : '商品类型',
                    field : 'goodsTypeDesc',
                    width : 80,
                    align : 'center',
                },{
                    title : '规格类型',
                    field : 'goodsSkuType',
                    width : 80,
                    align : 'center'
                },{
                    title : '商品生产日期',
                    field : 'proDate',
                    width : 120,
                    align : 'center',
                    formatter:function(value,row,index){
                    	if(null!=value && ""!=value){
                    		return new Date(value).Format("yyyy-MM-dd");
                    	}
                    }
                },{
                    title : '保质期',
                    field : 'keepDate',
                    width : 80,
                    align : 'center',
                    formatter:function(value,row,index){
                    	if(null!=value && ""!=value){
                    		return value+"个月";
                    	}
                    }
                },{
                    title : '生产厂家',
                    field : 'supNo',
                    width : 90,
                    align : 'center'
                },{
                    title : '排序',
                    field : 'sordNo',
                    width : 60,
                    align : 'center'
                },{
                    title : '状态',
                    field : 'statusDesc',
                    width : 80,
                    align : 'center',
                },{
                    title : '商品上架时间',
                    field : 'listTime',
                    width : 140,
                    align : 'center',
                    formatter:function(value,row,index){
                    	return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                    }
                }, {
                    title : '商品下架时间',
                    field : 'delistTime',
                    width : 140,
                    align : 'center',
                    formatter:function(value,row,index){
                    	return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                    }
                },{
                    title : '创建人',
                    field : 'createUser',
                    width : 100,
                    align : 'center'
                },{
                    title : '修改人',
                    field : 'updateUser',
                    width : 100,
                    align : 'center'
                },{
                    title : '创建时间',
                    field : 'createDate',
                    width : 140,
                    align : 'center',
                    formatter:function(value,row,index){
                    	return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                    }
                },{
                    title : '修改时间',
                    field : 'updateDate',
                    width : 140,
                    align : 'center',
                    formatter:function(value,row,index){
                    	return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                    }
                },{
                    title : '操作',
                    field : 'opt',
                    width : 150,
                    align : 'center',
                    formatter : function(value, row, index) {
                	 var merchantStatus =$("#merchantStatus").val();//商户状态必须为正常
                	 // 授权标示,
                 	 var grantedAuthority=$('#grantedAuthority').val();
                	 var content = "";
                	 if((row.status =='G01'|| row.status =='G02')&& merchantStatus=="1"){
                		 content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.queryGoodsStockInfo('"
                             + index +"');\">库存</a>&nbsp;&nbsp;";
                	 }
                    if(grantedAuthority=='permission'){
                    	 if(row.status !='G01'&&row.status !='G02'&& merchantStatus=="1"){//待审核状态不允许修改,已上架不允许
                    	 content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.editGoods('"
                             + index + "');\">修改</a>&nbsp;&nbsp;"
                    	 }
                    	 
                         if((row.status =='G03'||row.status =='G00')&& merchantStatus=="1"){//待上架或下架才能上架
	                    	 content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.shelves('"
	                             +  row.id + "');\">上架</a>&nbsp;&nbsp;" 
                         }
                         if(row.status =='G02'){//上架商品才能下架
                        	 content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.shelf('"
                                 + row.id + "');\">下架</a>&nbsp;&nbsp;"
                         }
                    }
                         content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.previewProduct('"
                             + row.id + "');\">预览</a>&nbsp;&nbsp;";
                    	 return content;
                    }
                }]],
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + '/application/goods/management/pagelist',
                data : param,
                type : "post",
                dataType : "json",
                success : function(data) {
                    $.validateResponse(data, function() {
                        success(data);
                    });
                    $("#tb").show();
                }
            })
        }
    });

    // 查询列表
    $(".search-btn").click(function() {
    	debugger;
        var params = {};
        params['merchantName'] = $("#merchantName").textbox('getValue');
        params['merchantType'] = $("#merchantType").combobox('getValue');
        params['goodsName'] = $("#goodsNames").textbox('getValue');
        params['goodsType'] = $("#goodsTypes").combobox('getValue');
        $('#tablelist').datagrid('load', params);
    });
    // 查询列表
    $("#flush").click(function() {
    	debugger;
    	$("#goodsNames").textbox('setValue','');
    	$("#goodsTypes").combobox('setValue','');
    	$("#merchantName").textbox('setValue','');
    	$("#merchantType").combobox('setValue','');
    	var params = {};
    	$('#tablelist').datagrid('load', params);
    });
//==============================================-----新增商品 始----====================================================//-----------------------
    /** 
     * 新增商品
     */
    $(".add-btn").click(function() {
    	ifSaveGoodsFlag = false;//初次弹出添加商品窗口
    	$('#addGoodsInfo').window('open');
    	//关闭添加商品窗口--监听方法
//	   ('#addGoodsInfo').window({
//		   onBeforeClose:function(){ 
//		     alert(111);
//		   }
//		});
	    
    	$("#addPlanDecrible #one").css('font-weight','bold');
    	$("#addPlanDecrible #two").css('font-weight','lighter');
    	$("#addPlanDecrible #three").css('font-weight','lighter');
    	$("#addPlanDecrible #four").css('font-weight','lighter');
    	
    	$("#addSelectCategoryList").css('display','block');
    	$("#addWriteGoodsInfo").css('display','none');
    	$("#addUpLoadGoodsPicture").css('display','none');
    	$("#addGoodsStock").css('display','none');
    	
    	$ ("#addGoodsLogoImg").attr ("src", '');
    	$("#addGoodsLogoFile").val('');
    	
    	$('#addWestAttrDataGrid').datagrid({  
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
    	
    });
    /**监听关闭添加商品窗口事件**/
    $('#addGoodsInfo').window({    
 	   onBeforeClose:function(){ 
 		   var params = {};
 	       $('#tablelist').datagrid('load', params);
 	   }
 	}); 
    /**监听关闭编辑商品窗口事件**/
    $('#editGoodsInfo').window({    
    	onBeforeClose:function(){ 
    		var params = {};
    		$('#tablelist').datagrid('load', params);
    	}
    }); 
    $('#addGoodsInfo').window('close'); 
    $('#editGoodsInfo').window('close'); 
    /**单击一级类目执行方法**/
	function firstClickRowFunction(indexFirst,rowFirst){
		 var id = rowFirst.categoryId;
//		 $('#eastAttrDataGrid').datagrid({
//			 url : ctx + '/categoryinfo/category/list',
//			 rownumbers : false,
//			 queryParams:{
//				'parentId':'null',
//			 }
//		 })
		 $('#addEastAttrDataGrid').datagrid({
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
		 $('#addCenterAttrDataGrid').datagrid({  
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
		 $('#addEastAttrDataGrid').datagrid({  
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
    //商品分类 ：下一步
    $("#confirmGoodsCategory").click(function(){
    	var firstCategory = $('#addWestAttrDataGrid').datagrid('getSelected');
    	if(firstCategory=='' || firstCategory==null){
    		$.messager.alert("提示", "未选择一级类目：请选择一级类目！", "info");
    		return;
    	}
    	var secondCategory = $('#addCenterAttrDataGrid').datagrid('getSelected');
    	if(secondCategory=='' || secondCategory==null){
    		$.messager.alert("提示", "未选择二级类目：请选择二级类目！", "info");
    		return;
    	}
    	var thirdCategory = $('#addEastAttrDataGrid').datagrid('getSelected');
    	if(thirdCategory=='' || thirdCategory==null){
    		$.messager.alert("提示", "未选择三级类目：请选择三级类目！", "info");
    		return;
    	}
    	//传递类目
    	categoryId1 = firstCategory.categoryId;
    	categoryId2 = secondCategory.categoryId;
    	categoryId3 = thirdCategory.categoryId;
    	$("#addPlanDecrible #one").css('font-weight','lighter');
    	$("#addPlanDecrible #two").css('font-weight','bold');
    	$("#addPlanDecrible #three").css('font-weight','lighter');
    	$("#addPlanDecrible #four").css('font-weight','lighter');
    	
    	$("#addSelectCategoryList").css('display','none');
    	$("#addWriteGoodsInfo").css('display','block');
    	$("#addUpLoadGoodsPicture").css('display','none');
    	$("#addGoodsStock").css('display','none');
    	
    	//填写商品信息初始化
    	
    	if(!ifSaveGoodsFlag){
    		initGoodsInfo();
    	}
    	
    });
    //商品分类： 取消
	$("#disGoodsCategory").click(function() {
		$('#addGoodsInfo').window('close');
	});
	
	//添加商品：下一步
	$("#nextStepAddGood").click(function() {
		saveGoodsInfo(categoryId1,categoryId2,categoryId3);
	});
	
	//添加商品：上一步
	$("#previousStepAddGood").click(function() {
		$("#addPlanDecrible #one").css('font-weight','bold');
		$("#addPlanDecrible #two").css('font-weight','lighter');
    	$("#addPlanDecrible #three").css('font-weight','lighter');
    	$("#addPlanDecrible #four").css('font-weight','lighter');
		
		$("#addSelectCategoryList").css('display','block');
    	$("#addWriteGoodsInfo").css('display','none');
    	$("#addUpLoadGoodsPicture").css('display','none');
    	$("#addGoodsStock").css('display','none');
	});
	
	//大图：下一步
	$("#nextStepUpLoadPic").click(function() {
		$("#addPlanDecrible #one").css('font-weight','lighter');
		$("#addPlanDecrible #two").css('font-weight','lighter');
    	$("#addPlanDecrible #three").css('font-weight','lighter');
    	$("#addPlanDecrible #four").css('font-weight','bold');
		
		$("#addSelectCategoryList").css('display','none');
    	$("#addWriteGoodsInfo").css('display','none');
    	$("#addUpLoadGoodsPicture").css('display','none');
    	$("#addGoodsStock").css('display','block');
    	
    	loadStockGoods('addGoodsStockList',addGoodId);
	});
	
	//大图：上一步
	$("#previousStepUpLoadPic").click(function() {
		$("#addPlanDecrible #one").css('font-weight','lighter');
		$("#addPlanDecrible #two").css('font-weight','bold');
		$("#addPlanDecrible #three").css('font-weight','lighter');
    	$("#addPlanDecrible #four").css('font-weight','lighter');
    	
		$("#addSelectCategoryList").css('display','none');
    	$("#addWriteGoodsInfo").css('display','block');
    	$("#addUpLoadGoodsPicture").css('display','none');
    	$("#addGoodsStock").css('display','none');
	});
	
	//库存：保存
	$("#saveGoodsStock").click(function() {
		$("#addPlanDecrible #one").css('font-weight','lighter');
		$("#addPlanDecrible #two").css('font-weight','lighter');
		$("#addPlanDecrible #three").css('font-weight','lighter');
		$("#addPlanDecrible #four").css('font-weight','bold');
		
		$("#addSelectCategoryList").css('display','none');
		$("#addWriteGoodsInfo").css('display','none');
		$("#addUpLoadGoodsPicture").css('display','none');
		$("#addGoodsStock").css('display','none');
		$('#tablelist').datagrid('load', {});
		
		//关闭窗口
		$('#addGoodsInfo').window('close');
	});
	
	//库存：上一步
	$("#previousStepGoodsStock").click(function() {
		$("#addPlanDecrible #one").css('font-weight','lighter');
		$("#addPlanDecrible #two").css('font-weight','lighter');
		$("#addPlanDecrible #three").css('font-weight','bold');
		$("#addPlanDecrible #four").css('font-weight','lighter');
		
		$("#addSelectCategoryList").css('display','none');
		$("#addWriteGoodsInfo").css('display','none');
		$("#addUpLoadGoodsPicture").css('display','block');
		$("#addGoodsStock").css('display','none');;
	});
	
//========================================-----新增商品 末----====================================================//===================
	
	
//=======================================-------编辑商品 始--------===================================================================//````````````````````
	//监听编辑商品输入商品名称事件
//	var $editgoodsName = $("#editgoodsName"),$edit_last = $("#editgoodsNameL");
//	$editgoodsName.on("keydown",function(){
//		alert("keydown");
//		var len = $editMerchantPostcode.textbox('getValue').length;
//		$edit_last.html(len);
//	})
	$("input",$("#editgoodsName").next("span")).keyup(function(){ 
		var len = $("#editgoodsName").textbox('getText').length;
		var canLen;
		console.log(len);
		if(len>15){
			canLen = len - 15;
			$("#editgoodsNameL").text('已经超出'+canLen+'个字');
		}else{
			canLen = 15 - len;
			$("#editgoodsNameL").text('还可以输入'+canLen+'个字');
		}
	})
	
	$("input",$("#editgoodsTitle").next("span")).keyup(function(){ 
		var len = $("#editgoodsTitle").textbox('getText').length;
		var canLen;
		console.log(len);
		if(len>15){
			canLen = len - 15;
			$("#editgoodsTitleL").text('已经超出'+canLen+'个字');
		}else{
			canLen = 15 - len;
			$("#editgoodsTitleL").text('还可以输入'+canLen+'个字');
		}
	})
	/**
	 * 编辑
	 */
	$.editGoods = function(index) {//编缉初始化
		$('#editGoodsInfo').window('open');
		var rowData = $('#tablelist').datagrid('getData').rows[index];
		editGoodId = rowData.id;
		finalGoodId = editGoodId;
		$("#editGoodsId").val(rowData.id);
		editGoodLogoUrl = rowData.goodsLogoUrl;
		editCategoryId1 = rowData.categoryId1;
		editCategoryId2 = rowData.categoryId2;
		editCategoryId3 = rowData.categoryId3;
		
		initEditGoodsInfo(rowData);//编辑商品回显
		$("#editBannerGoodsId").val(rowData.id);
		
    	$("#editPlanDecrible #one").css('font-weight','bold');
    	$("#editPlanDecrible #two").css('font-weight','lighter');
    	$("#editPlanDecrible #three").css('font-weight','lighter');
    	$("#editPlanDecrible #four").css('font-weight','lighter');
    	
    	$("#editSelectCategoryList").css('display','block');
    	$("#editWriteGoodsInfo").css('display','none');
    	$("#editUpLoadGoodsPicture").css('display','none');
    	$("#editGoodsStock").css('display','none');
    	
    	UE.getEditor('editEditor').setContent('');//重置编辑器
    	
    	/**加载富文本编辑器数据**/
		var params = {};
		params['id']=rowData.id;
		$.ajax({
			type : "POST",
			url : ctx + '/application/goods/management/loalEditor',
			data : params,
			success : function(data) {
				UE.getEditor('editEditor').execCommand('inserthtml', data);//加载编辑器
			}
		});
		//加载类目
		loadEditDatagrid();
	}
	
	//确认保存类目
	$("#saveGoodsCategory").click(function(){
		debugger;
		var firstCategory = $('#editWestAttrDataGrid').datagrid('getSelected');
    	if(firstCategory=='' || firstCategory==null){
    		$.messager.alert("提示", "未选择一级类目：请选择一级类目！", "info");
    		return;
    	}
    	var secondCategory = $('#editCenterAttrDataGrid').datagrid('getSelected');
    	if(secondCategory=='' || secondCategory==null){
    		$.messager.alert("提示", "未选择二级类目：请选择二级类目！", "info");
    		return;
    	}
    	var thirdCategory = $('#editEastAttrDataGrid').datagrid('getSelected');
    	if(thirdCategory=='' || thirdCategory==null){
    		$.messager.alert("提示", "未选择三级类目：请选择三级类目！", "info");
    		return;
    	}
		
		var params = {
			id:editGoodId,
			categoryId1:editCategoryId1,	
			categoryId2:editCategoryId2,	
			categoryId3:editCategoryId3,	
		};
		
		$.ajax({
			type : "POST",
			url : ctx + '/application/goods/management/editCategory',
			data : params,
			success : function(data) {
//				var response = JSON.parse(data);
				if(data.status == '1'){
					$.messager.alert("提示", "修改商品分类信息成功！", "info");
				}else{
					$.messager.alert("提示", data.msg, "info");
				}
			}
		});
    	
	});
	
	//点击选择商品分类
	$("#editPlanDecrible #one").click(function(){
		debugger;
		$("#editPlanDecrible #one").css('font-weight','bold');
		$("#editPlanDecrible #two").css('font-weight','lighter');
		$("#editPlanDecrible #three").css('font-weight','lighter');
		$("#editPlanDecrible #four").css('font-weight','lighter');
		
		$("#editSelectCategoryList").css('display','block');
		$("#editWriteGoodsInfo").css('display','none');
		$("#editUpLoadGoodsPicture").css('display','none');
		$("#editGoodsStock").css('display','none');
		
		loadEditDatagrid();
	});
	
	//点击填写商品信息
	$("#editPlanDecrible #two").click(function(){
		$("#editPlanDecrible #one").css('font-weight','lighter');
    	$("#editPlanDecrible #two").css('font-weight','bold');
    	$("#editPlanDecrible #three").css('font-weight','lighter');
    	$("#editPlanDecrible #four").css('font-weight','lighter');
    	
    	$("#editSelectCategoryList").css('display','none');
    	$("#editWriteGoodsInfo").css('display','block');
    	$("#editUpLoadGoodsPicture").css('display','none');
    	$("#editGoodsStock").css('display','none');
	});
	
	 //编辑 -- 保存商品信息
	$("#editgoodsAddinfo").click(function() {
		var id=$("#editid").val(),
		goodsModel=$("#editgoodsModel").textbox('getValue'),
		goodsName=$("#editgoodsName").textbox('getValue'), 
		goodsTitle=$("#editgoodsTitle").textbox('getValue'),
		listTime=$("#editlistTime").datetimebox('getValue'),
		proDate=$("#editproDate").datebox('getValue'),
		delistTime=$("#editdelistTime").datetimebox('getValue'),
		keepDate=$("#editkeepDate").textbox('getValue'),sordNo=$("#editsordNo").numberbox('getValue'),
		supNo=$("#editsupNo").textbox('getValue'),goodsSkuType=$("#editgoodsSkuType").textbox('getValue');
		//字段效验
		if (null == goodsModel || ("") == goodsModel) {
			$.messager.alert("提示", "商品型号不能为空！", "info");
			return;
		}
		if (null == goodsName || ("") == goodsName) {
			$.messager.alert("提示", "商品名称不能为空！", "info");
			return;
		}
		if (goodsName.length>15) {
			$.messager.alert("提示", "商品名称最多15字！", "info");
			return;
		}
	
		if (null == goodsTitle || ("") == goodsTitle) {
			$.messager.alert("提示", "商品大标题不能为空！", "info");
			return;
		}
		if (goodsTitle.length>22) {
			$.messager.alert("提示", "商品小标题最多22字！", "info");
			return;
		}
		if (null == goodsSkuType || ("") == goodsSkuType) {
			$.messager.alert("提示", "商品最小单元分类不能为空！", "info");
			return;
		}
		if (null == listTime || ("") == listTime) {
			$.messager.alert("提示", "商品上架时间不能为空！", "info");
			return;
		}	
		if (null == delistTime || ("") == delistTime) {
			$.messager.alert("提示", "商品下架时间不能为空！", "info");
			return;
		}
		if (listTime > delistTime) {
			$.messager.alert("提示", "上架时间不能大于下架时间！", "info");
			return;
		}
//		if (null == proDate || ("") == proDate) {
//			$.messager.alert("提示", "商品生产日期不能为空！", "info");
//			return;
//		}
		if (proDate > listTime) {
			$.messager.alert("提示", "商品生产日期不能大于上架日期！", "info");
			return;
		}
		if (null == sordNo || ("") == sordNo) {
			$.messager.alert("提示", "商品排序不能为空！", "info");
			return;
		}
//		if (null == keepDate || ("") == keepDate) {
//			$.messager.alert("提示", "商品保质期不能为空！", "info");
//			return;
//		}
//		if (null == supNo || ("") == supNo) {
//			$.messager.alert("提示", "生产厂家不能为空！", "info");
//			return;
//		}
		
		//提交表单
		//from重组
		var formObj = $("<form></form>").attr("method","post");
		formObj.append("<input type='text' name='id' value='"+id+"'/>");
		formObj.append("<input type='text' name='goodsModel' value='"+goodsModel+"'/>");
	 	formObj.append("<input type='text' name='goodsName' value='"+goodsName+"'/>");
	 	formObj.append("<input type='text' name='goodsTitle' value='"+goodsTitle+"'/>");
	 	formObj.append("<input type='text' name='listTime'  dataType='Require' value='"+listTime+"'/>");
		formObj.append("<input type='text' name='delistTime' value='"+delistTime+"'/>");
		if(!(null == proDate || ("") == proDate)){
			formObj.append("<input type='text' name='proDate' value='"+proDate+"  00:00:00'/>");
		}else{
			formObj.append("<input type='text' name='proDate' value=''/>");
		}
		
		formObj.append("<input type='text' name='keepDate' value='"+keepDate+"'/>");
		formObj.append("<input type='text' name='supNo' value='"+supNo+"'/>");
		formObj.append("<input type='text' name='sordNo' value='"+sordNo+"'/>");
		formObj.append("<input type='text' name='goodsSkuType' value='"+goodsSkuType+"'/>");
		formObj.css('display','none').appendTo("body");
		formObj.form("submit",{ 
			url : ctx + '/application/goods/management/edit',
			success : function(response) {
				debugger;
				var data = JSON.parse(response);
				ifLogout(data);
				debugger;
				if(data.status == '1'){
					$.messager.alert("提示", "修改商品成功", "info");
				}else{
					$.messager.alert("提示", data.msg, "info");
				}
			}
		});
	});
	
	//点击上传商品图片
	$("#editPlanDecrible #three").click(function(){
		$("#editPlanDecrible #one").css('font-weight','lighter');
    	$("#editPlanDecrible #two").css('font-weight','lighter');
    	$("#editPlanDecrible #three").css('font-weight','bold');
    	$("#editPlanDecrible #four").css('font-weight','lighter');
    	
    	$("#editSelectCategoryList").css('display','none');
    	$("#editWriteGoodsInfo").css('display','none');
    	$("#editUpLoadGoodsPicture").css('display','block');
    	$("#editGoodsStock").css('display','none');
    	

    	//回显商品墙图片
    	loadLogo("editGoodsLogoImg",editGoodLogoUrl);
    	//商品大图回显
    	loadBanner("editGoodsbannerList",editGoodId);
    	
	});
	
	//保存上传的图片
	$("#saveEditUploadPic").click(function(){
		$.messager.alert("提示", "保存上传图片成功！", "info");
	});
	
	//点击上传商品图片
	$("#editPlanDecrible #four").click(function(){
		$("#editPlanDecrible #one").css('font-weight','lighter');
    	$("#editPlanDecrible #two").css('font-weight','lighter');
    	$("#editPlanDecrible #three").css('font-weight','lighter');
    	$("#editPlanDecrible #four").css('font-weight','bold');
    	
    	$("#editSelectCategoryList").css('display','none');
    	$("#editWriteGoodsInfo").css('display','none');
    	$("#editUpLoadGoodsPicture").css('display','none');
    	$("#editGoodsStock").css('display','block');
    	
    	loadStockGoods('editGoodsStockList',editGoodId);
	});
	
	//库存：保存
	$("#saveEditGoodsStock").click(function() {
		$("#addPlanDecrible #four").css('font-weight','bold');
		
		$("#editSelectCategoryList").css('display','block');
    	$("#editWriteGoodsInfo").css('display','none');
    	$("#editUpLoadGoodsPicture").css('display','none');
    	$("#editGoodsStock").css('display','none');
		
		//关闭窗口
		$('#editGoodsInfo').window('close');
	});
	
//==================================-------编辑商品 末--------===================================================================//````````````````````

	// 取消
	$("#editdisGoods").click(function() {
		$('#editGoodsInfo').window('close');
	});
	
	// 添加--上传大图
	$("#addFileSumbit").click(function() {
		var addBannerPicOrder=$("#addBannerPicOrder").numberbox('getValue');
		var addBannerPicFile=$("#addBannerPicFile").val();
		var addBannerGoodsId=$("#addBannerGoodsId").val();
		/**
		 * 验证是否为空 或格式问题
		 */
		if (null == addBannerPicOrder || ("") == addBannerPicOrder) {
			$.messager.alert("提示", "请输入排序！", "info");
			return;
		}
		if (null == addBannerPicFile || ("") == addBannerPicFile) {
			$.messager.alert("提示", "请选择文件！", "info");
			return;
		}
//		var pos = "." + addBannerPicFile.replace(/.+\./, "");
//		if(pos!=".png"){
//			$.messager.alert("提示", '请导入正确的文件格式');
//			return;
//		}
		//提交form
		var theForm = $("#addBannerListpic");
		theForm.form("submit",{
			url : ctx + '/application/goods/management/addBanner',
			success : function(data) {
				var response = JSON.parse(data);
				console.log(response);
				if(response.msg=="success"){
					debugger;
					$.messager.alert("提示", '商品大图上传成功！');
					$("#addBannerPicOrder").numberbox('clear');
					$("#addBannerPicFile").val('')
//					var param = {};
//	            	param['goodsId'] = goodsId;
//			        $('#goodsbannerList').datagrid('load', null);
					loadBanner("addGoodsbannerList",addGoodId);
				}else{
					$.messager.alert("提示", response.msg);
				}
				
			}
		});
	});
	
	// 编辑--上传大图
	$("#editFileSumbit").click(function() {
		var editBannerPicOrder=$("#editBannerPicOrder").numberbox('getValue');
		var editBannerPicFile=$("#editBannerPicFile").val();
		var editBannerGoodsId=$("#editBannerGoodsId").val();
		/**
		 * 验证是否为空 或格式问题
		 */
		if (null == editBannerPicOrder || ("") == editBannerPicOrder) {
			$.messager.alert("提示", "请输入排序！", "info");
			return;
		}
		if (null == editBannerPicFile || ("") == editBannerPicFile) {
			$.messager.alert("提示", "请选择文件！", "info");
			return;
		}
		//提交form
		var editForm = $("#editBannerListpic");
		editForm.form("submit",{
			url : ctx + '/application/goods/management/addBanner',
			success : function(data) {
				var response = JSON.parse(data);
				console.log(response);
				if(response.msg=="success"){
					debugger;
					$.messager.alert("提示", '商品大图上传成功！');
					$("#editBannerPicOrder").numberbox('clear');
					$("#editBannerPicFile").val('')
					debugger;
					loadBanner("editGoodsbannerList",editGoodId);
				}else{
					$.messager.alert("提示", response.msg);
				}
			}
		});
	});
	
	//确定 获取编辑器的html
	$("#addGetAllHtml").click(function() {
		 	var params = {};
			params['id']=addGoodId;
//			var goodsDetails = UE.getEditor('editor').getAllHtml();
//			params['goodsDetail'] = goodsDetails.splice(12,0,'<meta charset="UTF-8">');
			params['goodsDetail'] = UE.getEditor('addEditor').getAllHtml();;
			params['goodsContent'] = UE.getEditor('addEditor').getContent();
			if(UE.getEditor('addEditor').getContent() == null || UE.getEditor('addEditor').getContent()==''){
				$.messager.alert("提示", "请输入详情信息", "info");
				return;
			}
			$.ajax({
				type : "POST",
				url : ctx + '/application/goods/management/upeditor',
				data : params,
				success : function(data) {
					debugger;
					if(data.message=='timeout'){
						$.messager.alert("操作提示", "登录超时, 请重新登录", "info");
	    				window.top.location = ctx + "/logout";
	    				return false;
					}
					if(data == 'SUCCESS'){
						$.messager.alert("提示", "保存商品详情成功", "info");
					}
				}
			});
	});
	
	//确定 获取编辑器的html
	$("#editGetAllHtml").click(function() {
		debugger;
		var params = {};
		params['id']=editGoodId;
//			var goodsDetails = UE.getEditor('editor').getAllHtml();
//			params['goodsDetail'] = goodsDetails.splice(12,0,'<meta charset="UTF-8">');
		params['goodsDetail'] = UE.getEditor('editEditor').getAllHtml();;
		params['goodsContent'] = UE.getEditor('editEditor').getContent();
		if(UE.getEditor('editEditor').getContent() == null || UE.getEditor('editEditor').getContent()==''){
			$.messager.alert("提示", "请输入详情信息", "info");
			return;
		}
		$.ajax({
			type : "POST",
			url : ctx + '/application/goods/management/upeditor',
			data : params,
			success : function(data) {
				if(data.message=='timeout'){
					$.messager.alert("操作提示", "登录超时, 请重新登录", "info");
					window.top.location = ctx + "/logout";
					return false;
				}
				if(data == 'SUCCESS'){
					$.messager.alert("提示", "保存商品详情成功", "info");
				}
			}
		});
	});

	/**
	 * 库存datagrid
	 */
	$.queryGoodsStockInfo = function(index) {
		debugger;
		var dataRow = $('#tablelist').datagrid('getData').rows[index];
		var goodsId=dataRow.id
		finalGoodId = goodsId;
		var status=dataRow.status;
		$('#goodsId').val(goodsId);
		$('#goodsStockInfo').window({modal: true});
		$('#goodsStockInfo').window('open');
		
		loadStockGoods('goodsStockList',goodsId);
	}
	
	//商品规格长度校验
	$("input",$("#goodsSkuAttr").next("span")).blur(function(){  
		var goodsSkuAttr = $("#goodsSkuAttr").textbox('getValue');
		if(goodsSkuAttr.length>5){
			$.messager.alert("<span style='color: black;'>提示</span>","商品规格内容不能超过5个字",'info');
			$("#goodsSkuAttr").textbox('setValue','');
			return;
		}
	})  
	
	//新增库存
	$(".addGoodsStockInfo").click(function() {
		$("#goodsSkuAttr").textbox('clear');
		$("#goodsPrice").textbox('clear');
		$("#marketPrice").textbox('clear');
		$("#stockTotalAmt").textbox('clear');
		$("#goodsCostPrice").textbox('clear');
		$("#goodsCompareUrlone").textbox('clear');
		$("#goodsCompareUrltwo").textbox('clear');
		$("#goodsCompareUrl").val('');
		$("#stockLogoFile").val('');
		
		$('#addGoodsStockInfoWin').window({modal: true});
		$('#addGoodsStockInfoWin').window('open');
	});
	// 取消
	$("#addGoodsStockInfoDisy").click(function() {
		$('#addGoodsStockInfoWin').window('close');
	});
	//新增库存
	$("#addGoodsStockInfoSumbit").click(function() {
		$("#addstockInfogoodsId").val(finalGoodId);
		var goodsSkuAttr=$("#goodsSkuAttr").textbox('getValue');
		var goodsPrice=$("#goodsPrice").textbox('getValue');
		var marketPrice=$("#marketPrice").textbox('getValue');
		var stockTotalAmt=$("#stockTotalAmt").textbox('getValue');
		var goodsCostPrice=$("#goodsCostPrice").textbox('getValue');
		var goodsCompareUrlone=$("#goodsCompareUrlone").textbox('getValue');
		var goodsCompareUrltwo=$("#goodsCompareUrltwo").textbox('getValue');
		debugger;
//		$('#goodsCompareUrl').val(goodsCompareUrlone+";"+goodsCompareUrlone);
//		console.log($('#goodsCompareUrl').val());
		var stockLogoFile=$("#stockLogoFile").val();
		
		if (null == goodsCompareUrlone || ("") == goodsCompareUrlone) {
			$.messager.alert("提示", "比价链接不允许为空！", "info");
			return;
		}
		if (null == goodsCompareUrltwo || ("") == goodsCompareUrltwo) {
			$.messager.alert("提示", "比价链接不允许为空！", "info");
			return;
		}
		
		if (null == goodsSkuAttr || ("") == goodsSkuAttr) {
			$.messager.alert("提示", "库存规格不能为空！", "info");
			return;
		}
		if (null == goodsPrice || ("") == goodsPrice) {
			$.messager.alert("提示", "商品价格不能为空！", "info");
			return;
		}
		if (null == marketPrice || ("") == marketPrice) {
			$.messager.alert("提示", "市场价不能为空！", "info");
			return;
		}
		if (null == stockTotalAmt || ("") == stockTotalAmt) {
			$.messager.alert("提示", "库存量不能为空！", "info");
			return;
		}
		if (null == goodsCostPrice || ("") == goodsCostPrice) {
			$.messager.alert("提示", "成本价不能为空！", "info");
			return;
		}
		if (null == stockLogoFile || ("") == stockLogoFile) {
			$.messager.alert("提示", "库存logo不允许空！", "info");
			return;
		}
		if (null == stockLogoFile || ("") == stockLogoFile) {
			$.messager.alert("提示", "库存logo不允许空！", "info");
			return;
		}
//		var pos = "." + stockLogoFile.replace(/.+\./, "");
//		if(pos!=".png"){
//		$.messager.alert("提示", '请导入正确的文件格式');
//		return;
//		}
		//提交from
		var theForm = $("#stockInfoFrom");
		theForm.form({
			url : ctx + '/application/goods/stockinfo/add',
			success : function(data) {
            	if (data.indexOf ('请输入账户') != -1)
			    {
            		$.messager.alert("提示", "超时，请重新登录", "info");
            		parent.location.reload();
			    }
				var response = JSON.parse(data);
				if(response.msg=="success"){
					$.messager.alert("提示","缩略图上传成功！", "info");
			        loadStockGoods('addGoodsStockList',finalGoodId);
			        loadStockGoods('editGoodsStockList',finalGoodId);
			        loadStockGoods('goodsStockList',finalGoodId);
			        //loadStockGoods('editGoodsStockList',goodsId);
			        
//			        var params = {};
//			        params['goodsId'] = goodsId;
//			        $('#addGoodsStockList').datagrid('load', params);
					$('#addGoodsStockInfoWin').window('close');
				}else{
					$.messager.alert("提示", response.msg, "info");
				}
		    }
		});
		theForm.submit();
	});
	
	//防止表单干净提交代码
	$("#stockInfoFrom").submit(function(){  
		$(":submit",this).attr("disabled","disabled");  
	});
	
	//加库存弹出窗口
	$.updateStockinfo = function(stockinfoId,stockTotalAmt,stockCurrAmt) {
		$('#stockinfoId').val(stockinfoId);
		$('#stockTotalAmtTemp').val(stockTotalAmt);
		$('#stockCurrAmtTemp').val(stockCurrAmt);
		$("#addstockinfoAmt").textbox('clear');
		$('#addAmtGoodsStockInfoWin').window({modal: true});
		$('#addAmtGoodsStockInfoWin').window('open');
	};	
	// 取消
	$("#addAmtGoodsStockInfoWinDisy").click(function() {
		$('#addAmtGoodsStockInfoWin').window('close');
	});
	// 确定
	$("#addAmtGoodsStockInfoWinSumbit").click(function() {
		debugger;
		var goodsId=finalGoodId;
		var stockinfoId=$('#stockinfoId').val();
		var stockTotalAmtTemp=$('#stockTotalAmtTemp').val();
		var stockCurrAmtTemp=$('#stockCurrAmtTemp').val();
		var addstockinfoAmt=$("#addstockinfoAmt").textbox('getValue');
		if (null == addstockinfoAmt || ("") == addstockinfoAmt) {
			$.messager.alert("提示", "请输入要加的库存数目！", "info");
			return;
		}
		//判断商品总量是否超过999
		debugger;
//		var stockinfoAmtAll=parseInt(addstockinfoAmt)+parseInt(stockTotalAmtTemp);//库存商品总量
		var stockinfoAmtAll=parseInt(addstockinfoAmt)+parseInt(stockCurrAmtTemp);//当前库存量
		if (stockinfoAmtAll > 999)
		{
			$.messager.alert ("提示", "当前库存数量不能超过999个！", "info");
			return;
		}
		var param = {};
		param['id']=stockinfoId;
		param['addAmt']=addstockinfoAmt;
		/**
		 * 验证未加
		 */
		$.ajax({
			type : "POST",
			url : ctx + '/application/goods/stockinfo/editStockinfo',
			data : param,
			success : function(data) {
			 	var params = {};
		        params['goodsId'] = goodsId;
//		        $('#addGoodsStockList').datagrid('load', params);
//		        $('#editGoodsStockList').datagrid('load', params);
		        loadStockGoods('addGoodsStockList',goodsId);
		        loadStockGoods('editGoodsStockList',goodsId);
		        loadStockGoods('goodsStockList',goodsId);
		        $('#addAmtGoodsStockInfoWin').window('close');
			}
		});
	});
	
	//修改库存
	$.editStockinfo = function(index,datagridId) {
		var row = $('#'+datagridId).datagrid('getData').rows[index];
		$("#editstockinfoId").val(row.id);
		$("#editStockinfoIdInForm").val(row.id);
		$("#editgoodsSkuAttr").textbox('setValue',row.goodsSkuAttr);
		$("#editgoodsPrice").textbox('setValue',FormatAfterDotNumber(row.goodsPrice,2));
		$("#editmarketPrice").textbox('setValue',FormatAfterDotNumber(row.marketPrice,2));
		$("#editstockTotalAmt").textbox('setValue',row.stockTotalAmt);
		$("#editgoodsCostPrice").textbox('setValue',FormatAfterDotNumber(row.goodsCostPrice,2));
		
		$("#editStockGoodsLogoImg").attr("src",ctx + "/fileView/query?picUrl=" + row.stockLogo);
		$("#editStockLogoUrl").val(row.stockLogo);
		
		$("#editgoodsCompareUrlone").textbox('setValue',row.goodsCompareUrl);
		$("#editgoodsCompareUrltwo").textbox('setValue',row.goodsCompareUrl2);
		
		$('#editGoodsStockInfoWin').window({modal: true});
		$('#editGoodsStockInfoWin').window('open');
	};
	
	// 取消
	$("#editGoodsStockInfoDisy").click(function() {
		$('#editGoodsStockInfoWin').window('close');
	});
	//确定
	$("#editGoodsStockInfoSumbit").click(function() {
		var goodsId=finalGoodId;
		var stockinfoId=$("#editstockinfoId").val();
		var goodsSkuAttr=$("#editgoodsSkuAttr").textbox('getValue');
		var goodsPrice=$("#editgoodsPrice").textbox('getValue');
		var marketPrice=$("#editmarketPrice").textbox('getValue');
		var stockTotalAmt=$("#editstockTotalAmt").textbox('getValue');
		var goodsCostPrice=$("#editgoodsCostPrice").textbox('getValue');
		var editgoodsCompareUrlone=$("#editgoodsCompareUrlone").textbox('getValue');
		var editgoodsCompareUrltwo=$("#editgoodsCompareUrltwo").textbox('getValue');
		var editstockLogoFile=$("#editstockLogoFile").val();
		var editStockGoodsLogoFile=$('#editStockGoodsLogoFile').val();
		
		
		if (null == editgoodsCompareUrlone || ("") == editgoodsCompareUrlone) {
			$.messager.alert("提示", "比价链接不允许为空！", "info");
			return;
		}
		if (null == editgoodsCompareUrltwo || ("") == editgoodsCompareUrltwo) {
			$.messager.alert("提示", "比价链接不允许为空！", "info");
			return;
		}
		if (null == goodsSkuAttr || ("") == goodsSkuAttr) {
			$.messager.alert("提示", "属性不能为空！", "info");
			return;
		}
		if (null == goodsPrice || ("") == goodsPrice) {
			$.messager.alert("提示", "商品价格不能为空！", "info");
			return;
		}
		if (null == marketPrice || ("") == marketPrice) {
			$.messager.alert("提示", "市场价不能为空！", "info");
			return;
		}
		if (null == stockTotalAmt || ("") == stockTotalAmt) {
			$.messager.alert("提示", "库存量不能为空！", "info");
			return;
		}
		if (null == goodsCostPrice || ("") == goodsCostPrice) {
			$.messager.alert("提示", "成本价不能为空！", "info");
			return;
		}
//		if (null == editstockLogoFile || ("") == editstockLogoFile) {
//			$.messager.alert("提示", "库存logo不允许空！", "info");
//			return;
//		}
//		if (null == editstockLogoFile || ("") == editstockLogoFile) {
//			$.messager.alert("提示", "库存logo不允许空！", "info");
//			return;
//		}
		debugger;
		var param = {};
		param['goodsSkuAttr']=goodsSkuAttr;
		param['id']=stockinfoId;
		param['goodsCostPrice']=goodsCostPrice;
		param['marketPrice']=marketPrice;
		param['goodsPrice']=goodsPrice;
		param['goodsCompareUrl']=editgoodsCompareUrlone;
		param['goodsCompareUrl2']=editgoodsCompareUrltwo;
		
		$.ajax({
			type : "POST",
			url : ctx + '/application/goods/stockinfo/editStockinfo',
			data : param,
			success : function(data) {
				debugger;
//				var response = JSON.parse(data);
				if(data.msg=="success"){
					debugger;
					$.messager.alert("提示", "修改库存信息成功！", "info");
					var params = {};
			        params['goodsId'] = goodsId;
			        
			        loadStockGoods('addGoodsStockList',goodsId);
			        loadStockGoods('editGoodsStockList',goodsId);
			        loadStockGoods('goodsStockList',goodsId);
			        $('#editGoodsStockInfoWin').window('close');
				}else{
					$.messager.alert("提示", data.msg, "info");
				}
			}
		});
		
	});
	
	
	//删除banner图
	$.delbanner = function(datagridId,id,goodsId) {
		$.messager.confirm('提示框', '你确定要删除吗?',function(r){
			debugger;
			if(r){
				var params = {};
				params['id']=id;
				$.ajax({
					type : "POST",
					url : ctx + '/application/goods/management/delBanner',
					data : params,
					success : function(data) {
						var param = {};
		            	param['goodsId'] = goodsId;
				        loadBanner(datagridId,goodsId);
					}
				});
			} 
		})
	 
	};	
	//上架
	$.shelves = function(id) {
		$.messager.confirm('提示框', '你确定要上架吗?',function(r){
			if(r){
				debugger;
				var params = {};
				params['id']=id;
//				var editorGoodsDetail  = UE.getEditor('editor').getContent();
//				params['editorGoodsDetail']=editorGoodsDetail;
//				if (null == editorGoodsDetail || ("") == editorGoodsDetail) {
//					$.messager.alert("提示", "商品详情不能为空！", "info");
//					return;
//				}
				$.ajax({
					type : "POST",
					url : ctx + '/application/goods/management/shelves',
					data : params,
					success : function(data) {
						if(data=='SUCCESS'){
							$.messager.alert("提示", "商品上架成功", "info");
							$(".search-btn").click();
						}else{
							$.messager.alert("提示", data, "info");
						}
					}
				});
			} 
		})
	 
	};
	//下架
	$.shelf = function(id) {
		$.messager.confirm('提示框', '你确定要下架吗?',function(r){
			if(r){
				var params = {};
				params['id']=id;
				$.ajax({
					type : "POST",
					url : ctx + '/application/goods/management/shelf',
					data : params,
					success : function(data) {
					  debugger;
					  $.messager.alert("提示", "商品下架成功", "info");
					  $(".search-btn").click();
					}
				});
			} 
		})
	 
	};
	
	/** loadlogo* */
	function loadStockLogo (goodsStockLogoUrl)
	{
		debugger;
		if (goodsStockLogoUrl != null)
		{
			var params = {};
			params['picUrl'] = goodsStockLogoUrl;
			$ ("#editStockGoodsLogoImg").attr ("src", ctx + "/fileView/query?picUrl=" + goodsStockLogoUrl);
		}else{
			$ ("#editStockGoodsLogoImg").attr ("src", '');
		}
	}
	
	//添加商品--上传logo
	$("#addUpLogoBtn").click(function() {
		var addGoodsLogoFile=$('#addGoodsLogoFile').val();
		if (null == addGoodsLogoFile || ("") == addGoodsLogoFile) {
			$.messager.alert("提示", "请选择文件！", "info");
			return;
		}
//		var pos = "." + editGoodsLogoFile.replace(/.+\./, "");
//		if(pos!=".png"){
//			$.messager.alert("提示", '请导入正确的文件格式', "info");
//			return;
//		}
		//提交from
		var thisform = $("#addLogoFilepic");
		thisform.form("submit",{
			url : ctx + '/application/goods/management/uplogoFile',
			success : function(data) {
				var flag1 = data.indexOf('登录系统');
				var flag2 = data.indexOf('</div');
				//console.log(data+"==========>"+flag);;
				if(flag1 != -1 && flag2 != -1){
					$.messager.alert("操作提示", "登录超时, 请重新登录", "info");
    				window.top.location = ctx + "/logout";
    				return false;
				}
				
				 var response = JSON.parse(data);
				 $.validateResponse(response, function() {
		                $(".search-btn").click();
		         });
				 
				 if(response.status=="1"){
					 loadLogo("addGoodsLogoImg",response.data);
				 }
			}
		});
	});
	
	//上传库存logo
	$("#upStockLogoBtn").click(function() {
		debugger;
		var editStockGoodsLogoFile=$('#editStockGoodsLogoFile').val();
		var editStockinfoIdInForm = $('#editStockinfoIdInForm').val();
		if (null == editStockGoodsLogoFile || ("") == editStockGoodsLogoFile) {
			$.messager.alert("提示", "请选择文件！", "info");
			return;
		}
		if (null == editStockinfoIdInForm || ("") == editStockinfoIdInForm) {
			$.messager.alert("提示", "库存id不能为空！", "info");
			return;
		}
		//提交form
		var thisform = $("#stocklogoFilepic");
		thisform.form("submit",{
			url : ctx + '/application/goods/management/upstocklogoFile',
			success : function(data) {
				 var response = JSON.parse(data);
				 $.validateResponse(response, function() {
		                $(".search-btn").click();
		         });
				 
				 if(response.status=="1"){
					 loadStockLogo(response.data);
				 }
			}
		});
	});
	//预览单个banner图
	$.alertPic = function(url) {
		$("#previewBannerImg").attr("src","");
		$('#previewBannerWin').window({modal: true});
		$('#previewBannerWin').window('open');
//		var params = {};
//		params['picUrl']=url;
		debugger;
		$("#previewBannerImg").attr ("src", ctx + "/fileView/query?picUrl=" + url);
//		$.ajax({
//			type : "POST",
//			url : ctx + '/application/goods/management/loadPic',
//			data : params,
//			success : function(data) {
//				$("#previewBannerImg").attr("src","data:image/gif;base64,"+data);
//			}
//		});
	};
	//预览商品
	$.previewProduct = function(id,goodsId) {
		debugger;
        var subtitle = "商品预览-" + id;
        var parentTabs = parent.$('#tabs');
        var destAddress = ctx + "/application/goods/management/loadAllBannerPic?id=" + id;
        if (parentTabs.tabs('exists', subtitle)) {
            parentTabs.tabs('select', subtitle);
            return;
        }
        parentTabs.tabs('add', {
            title : subtitle,
            content : function() {
                var array = new Array();
                array.push('<iframe name="mainFrame" ');
                array.push('scrolling="auto" ');
                array.push('frameborder="0" ');
                array.push('src="' + destAddress + '" ');
                array.push(' style="width:100%;height:100%;" ');
                array.push(' ></iframe>');
                return array.join('');
            },
            closable : true
        });
	};
	$('#marketPrice').numberspinner({  
	    onChange:function(newValue,oldValue){ 
	    	var goodsPriceRate=$("#goodsPriceRate").val();
	    	if(goodsPriceRate==''){
	    		$("#goodsPrice").textbox('setValue',newValue);
	    	}else{
	    		$("#goodsPrice").textbox('setValue',FormatAfterDotNumber(newValue*goodsPriceRate,2));
	    	}
	    }  
	});
	$('#editmarketPrice').numberspinner({  
	    onChange:function(newValue,oldValue){ 
	    	var goodsPriceRate=$("#goodsPriceRate").val();
	    	if(goodsPriceRate==''){
	    		$("#editgoodsPrice").textbox('setValue',newValue);
	    	}else{
	    		$("#editgoodsPrice").textbox('setValue',FormatAfterDotNumber(newValue*goodsPriceRate,2));
	    	}
	    }  
	});
	
	// 导出订单
	$ (".export-btn").click (function (){
		$.messager.confirm ('商品信息', '确定要导出吗？', function (r)
		{
			if (r)
			{
				var params = {};
				params['goodsName'] = $ ("#goodsNames").textbox ('getValue');
				params['goodsType'] = $ ("#goodsTypes").combobox ('getValue');
				params['isAll'] = 'f';// t: 是 f: 否 是否导出全部订单信息
				params['busCode'] = 'E002';// 订单导出code
				exportFile ("tablelist", "商品信息", params);
			}
		});
		
	});
	

});

//往string中的某个位置插入内容
String.prototype.splice = function(idx, rem, str) {
    return this.slice(0, idx) + str + this.slice(idx + Math.abs(rem));
};

//保存添加的商品信息
function saveGoodsInfo(categoryId1,categoryId2,categoryId3){
	var merchantCode=$("#addmerchantCode").textbox('getValue'), 
	goodsModel=$("#addgoodsModel").textbox('getValue'),
	goodsName=$("#addgoodsName").textbox('getValue'), 
	goodsTitle=$("#addgoodsTitle").textbox('getValue'), 
	listTime=$("#addlistTime").datetimebox('getValue'), 
	proDate=$("#addproDate").datebox('getValue'),
	delistTime=$("#adddelistTime").datetimebox('getValue'),
	keepDate=$("#addkeepDate").textbox('getValue'),
	supNo=$("#addsupNo").textbox('getValue'),
	goodsSkuType=$("#addgoodsSkuType").textbox('getValue');
	sordNo=$("#sordNo").textbox('getValue');
	//字段效验
	if (null == categoryId1 || ("") == categoryId1) {
		$.messager.alert("提示", "商品一级类目不能为空！", "info");
		return;
	}
	if (null == categoryId2 || ("") == categoryId2) {
		$.messager.alert("提示", "商品二级类目不能为空！", "info");
		return;
	}
	if (null == categoryId3 || ("") == categoryId3) {
		$.messager.alert("提示", "商品三级类目不能为空！", "info");
		return;
	}
	if (null == merchantCode || ("") == merchantCode) {
		$.messager.alert("提示", "商户编码不能为空！", "info");
		return;
	}
	if (null == goodsName || ("") == goodsName) {
		$.messager.alert("提示", "商品名称不能为空！", "info");
		return;
	}
	
	if(goodsName.length>18){
		$.messager.alert("提示", "商品名称最多18字！", "info");  
		return;
	}
	if (null == goodsModel || ("") == goodsModel) {
		$.messager.alert("提示", "商品型号不能为空！", "info");
		return;
	}
	
	if (null == goodsTitle || ("") == goodsTitle) {
		$.messager.alert("提示", "商品小标题不能为空！", "info");
		return;
	}
	if(goodsTitle.length>25){
		$.messager.alert("提示", "商品小标题最多25字！", "info");  
		return;
	}
	if (null == goodsSkuType || ("") == goodsSkuType) {
		$.messager.alert("提示", "商品规格类型不能为空！", "info");
		return;
	}
	if (null == listTime || ("") == listTime) {
		$.messager.alert("提示", "商品上架日期不能为空！", "info");
		return;
	}	
	if (null == delistTime || ("") == delistTime) {
		$.messager.alert("提示", "商品下架日期不能为空！", "info");
		return;
	}
	if (listTime > delistTime) {
		$.messager.alert("提示", "上架时间不能大于下架时间！", "info");
		return;
	}
//	if (null == proDate || ("") == proDate) {
//		$.messager.alert("提示", "商品生产日期不能为空！", "info");
//		return;
//	}
	if (proDate > listTime) {
		$.messager.alert("提示", "商品生产日期不能大于上架日期！", "info");
		return;
	}
//	if (null == keepDate || ("") == keepDate) {
//		$.messager.alert("提示", "商品保质期不能为空！", "info");
//		return;
//	}
	
//	if (null == supNo || ("") == supNo) {
//		$.messager.alert("提示", "生产厂家不能为空！", "info");
//		return;
//	}
	
	if (null == sordNo || ("") == sordNo) {
		$.messager.alert("提示", "排序不能为空！", "info");
		return;
	}
	
	//from重组
	var formObj = $("<form></form>").attr("method","post");
	formObj.append("<input type='text' name='categoryId1' value='"+categoryId1+"'/>");
	formObj.append("<input type='text' name='categoryId2' value='"+categoryId2+"'/>");
	formObj.append("<input type='text' name='categoryId3' value='"+categoryId3+"'/>");
	formObj.append("<input type='text' name='merchantCode' value='"+merchantCode+"'/>");
 	formObj.append("<input type='text' name='goodsModel' value='"+goodsModel+"'/>");
 	formObj.append("<input type='text' name='goodsName' value='"+goodsName+"'/>");
 	formObj.append("<input type='text' name='goodsTitle' value='"+goodsTitle+"'/>");
 	formObj.append("<input type='text' name='listTime'  dataType='Require' value='"+listTime+"'/>");
	formObj.append("<input type='text' name='delistTime' value='"+delistTime+"'/>");
	if(proDate!=""){
		formObj.append("<input type='text' name='proDate' value='"+proDate+" 00:00:00'/>");
	}
	if(keepDate!=""){
		formObj.append("<input type='text' name='keepDate' value='"+keepDate+"'/>");
	}
	if(supNo!=""){
		formObj.append("<input type='text' name='supNo' value='"+supNo+"'/>");
	}
	formObj.append("<input type='text' name='goodsSkuType' value='"+goodsSkuType+"'/>");
	formObj.append("<input type='text' name='sordNo' value='"+sordNo+"'/>");
	formObj.css('display','none').appendTo("body");
	formObj.form("submit",{ 
		url : ctx + '/application/goods/management/add',
		success : function(data) {
			debugger;
			var flag1 = data.indexOf('登录系统');
			var flag2 = data.indexOf('</div');
			//console.log(data+"==========>"+flag);;
			if(flag1 != -1 && flag2 != -1){
				$.messager.alert("操作提示", "登录超时, 请重新登录", "info");
				window.top.location = ctx + "/logout";
				return false;
			}
			var response = JSON.parse(data);
			if(response.status=='1'){
				addGoodId = response.data.goodId;
				finalGoodId = addGoodId;//新增库存时所需商品id
				ifSaveGoodsFlag = true;
				$("#addLogogoodsId").val(addGoodId);
				$("#addBannerGoodsId").val(addGoodId);
				$("#addPlanDecrible #one").css('font-weight','lighter');
		    	$("#addPlanDecrible #two").css('font-weight','lighter');
		    	$("#addPlanDecrible #three").css('font-weight','bold');
		    	$("#addPlanDecrible #four").css('font-weight','lighter');
				
				$("#addSelectCategoryList").css('display','none');
		    	$("#addWriteGoodsInfo").css('display','none');
		    	$("#addUpLoadGoodsPicture").css('display','block');
		    	$("#addGoodsStock").css('display','none');
		    	loadBanner("addGoodsbannerList",addGoodId);
			}else{
				$.messager.alert("提示", data, "info");
			}
		}
	});
}

//判断是否超时
function ifLogout(data){
	if(data.message=='timeout' && data.result==false){
		$.messager.alert("操作提示", "登录超时, 请重新登录", "info");
		window.top.location = ctx + "/logout";
		return false;
	}
}
//加载商品大图列表
function loadBanner(datagridId,goodsId){
	$('#'+datagridId).datagrid({
        height:250,
        width:"100%",
        idField:'id',
        pagination : true,
		fitColumns : true,
		rownumbers : true,
        columns: [[
//                {field: 'bannerName', title: '名称', width: 100},
                {field: 'bannerImgUrl', title: '地址', width: 100,
                    formatter : function(value, row, index) {
                    	 var content = "";
                    	 content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.alertPic('"
                             + row.bannerImgUrl + "');\">"+row.bannerImgUrl+"</a>&nbsp;&nbsp;";
                    	 return content;
                    }
                },
                {field: 'bannerOrder', title: '排序', width: 50},
                {field : 'opt',  title : '操作', width : 50,
                    formatter : function(value, row, index) {
                    	 var content = "";
                    	 content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton'";
	                     content += " onclick='$.delbanner(\"" + datagridId + "\",\""
	                                + row.id + "\",\""+goodsId+"\");'>删除</a>";
	                      
                    	 return content;
                    }
                }
            ]] ,
            loader : function(param, success, error) {
            	var param = {};
            	param['goodsId'] = goodsId;
                $.ajax({
                    url : ctx + '/application/goods/management/goodsbannerList',
                    data : param,
                    type : "post",
                    dataType : "json",
                    success : function(data) {
                        $.validateResponse(data, function() {
                            success(data);
                        });
                    }
                })
            }
    });
}
//加载库存列表
function loadStockGoods(datagridId,goodsId){
	$('#'+datagridId).datagrid({
        height:220,
        width:"100%",
        url : ctx + '/application/goods/stockinfo/pagelist?goodsId='+goodsId,
        idField:'id',
        pagination : true,
//        fit:true,
		//fitColumns : true,
		rownumbers : true,
        columns: [[
                {field: 'stockLogo', title: 'logo', width: 350,align : 'center',
                	formatter : function(value, row, index) {
                		 var content = "";
                		 content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.alertPic('"
                	         + row.stockLogo + "');\">"+row.stockLogo+"</a>&nbsp;&nbsp;";
                		 return content;
                	}
                },
                {field: 'id', title: '库存id', width: 80,align : 'center',hidden:'hidden'},
                {field: 'goodsSkuAttr', title: '库存规格', width: 80,align : 'center'},
                {field: 'goodsCostPrice', title: '成本价格', width: 80,align : 'center',
                formatter:function(value,row,index){
                	// 授权标示
                 	var goodCostpriceIf=$('#goodCostpriceIf').val();
                 	if(goodCostpriceIf=='permission'){
                		return value;
                	} 
                		return "未授权";
                }
                },
                {field: 'marketPrice', title: '市场价格', width: 80,align : 'center'},
                {field: 'goodsPrice', title: '商品现价', width: 80,align : 'center'},
                {field: 'stockTotalAmt', title: '商品总量', width: 80,align : 'center'},
                {field: 'stockCurrAmt', title: '当前库存量', width: 80,align : 'center'},
                {field: 'goodsCompareUrl', title: '比价连接1', width: 280,align : 'center'},
                {field: 'goodsCompareUrl2', title: '比价连接2', width: 280,align : 'center'},
                {field : 'opt',  title : '操作', width : 100,align : 'center',
                    formatter : function(value, row, index) {
                    	// 授权标示
                     	var grantedAuthority=$('#grantedAuthority').val();
                    	var content = "";
                    	if(grantedAuthority=='permission'){
                    		content +="<a href='javascript:void(0);' class='easyui-linkedbutton' " +
                    	 		"onclick=\"$.updateStockinfo('"+row.id+"','"+row.stockTotalAmt+"','"+row.stockCurrAmt+"');\">加库存</a>&nbsp;&nbsp;";
                    		if(status!='G02'){//上架后不能修改
                    			content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.editStockinfo('"+index+"','"+datagridId+"');\">修改</a>&nbsp;&nbsp;";
                    		}
                    	}else{
                     		content +="权限不足";
                    	}
                    	return content;
                    }
                }
            ]] 
    });
}
//添加商品初始化商品信息
function initGoodsInfo(){
	var merchantCode=$("#addmerchantCode").textbox('getValue');
	if(merchantCode==''){
		$.messager.alert("提示", "必须商户才能添加商品！", "info");
		return;
	}	
	$("#addgoodsModel").textbox('clear');
	$("#addgoodsName").textbox('clear');
	$("#addgoodsTitle").textbox('clear');		
	$("#addlistTime").datetimebox('setValue', '');
	$("#adddelistTime").datetimebox('setValue', '');
	$("#addproDate").datebox('setValue', '');
	$("#addkeepDate").numberbox('setValue','');  
	$("#addsupNo").textbox('clear');
	$('#addgoodsSkuType').combobox('setValue','');
	$("#sordNo").numberbox('setValue','');  
}

//编辑商品初始化商品信息
function initEditGoodsInfo(row){
	$("#editid").val(row.id);
	$("#editLogogoodsId").val(row.id);
	
	$("#editmerchantCode").textbox('setValue',row.merchantCode);
	$("#editgoodsModel").textbox('setValue',row.goodsModel);
	$("#editgoodsName").textbox('setValue',row.goodsName);
	$("#editgoodsTitle").textbox('setValue',row.goodsTitle);
	$("#editgoodsSkuType").combobox('setValue',row.goodsSkuType);
	$("#editlistTime").datetimebox('setValue',new Date(row.listTime).Format("yyyy-MM-dd hh:mm:ss")); 
	$("#editdelistTime").datetimebox('setValue',new Date(row.delistTime).Format("yyyy-MM-dd hh:mm:ss"));
	if(null==row.proDate || ""==row.proDate){
		$("#editproDate").datebox('setValue',"");
	}else{
		$("#editproDate").datebox('setValue',new Date(row.proDate).Format("yyyy-MM-dd")); 
	}
	$("#editsupNo").textbox('setValue',row.supNo);
	$("#editkeepDate").textbox('setValue',row.keepDate);
	$("#editsordNo").numberbox('setValue',row.sordNo);
}

/** 回显图片* */
function loadLogo (id,picUrl)
{
	debugger;
	if (picUrl != null)
	{
		$ ("#"+id).attr ("src","");
		$ ("#"+id).attr ("src", ctx + "/fileView/query?picUrl=" + picUrl);
	}else{
		$ ("#"+id).attr ("src", '');
	}
}

/**编辑商品 加载datagrid**/
function loadEditDatagrid(){
	//加载商品类目信息
	$('#editWestAttrDataGrid').datagrid({  
		url : ctx + '/categoryinfo/category/list',
		striped : true, 
		fitColumns : true,
		rownumbers : true,
		singleSelect : true,
		queryParams:{},
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
			width : '200',  
			title : '操作',  
			field : 'opt', 
			align : 'center',
		 }  ] ],
		 onLoadSuccess : function(data){
			var indexS;
		 	$.each(data.rows, function(i, n){
	 		  if(data.rows[i].categoryId == editCategoryId1){
	 			  indexS = i;
	 		  }
	 		});
		    $('#editWestAttrDataGrid').datagrid('selectRow',indexS);
		    firtClickCategoryEdit(indexS,data.rows[indexS]);
		 },
		 onClickRow: function (index, row) {
			 firtClickCategoryEdit(index, row);
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

function firtClickCategoryEdit(indexFirstEd,rowFirstEd){
	 var id = rowFirstEd.categoryId;
	 editCategoryId1 = rowFirstEd.categoryId;
	 $('#editEastAttrDataGrid').datagrid({
	   rownumbers : false,
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
	 $('#editCenterAttrDataGrid').datagrid({  
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
		 onLoadSuccess : function(data){
 			var indexS;
		 	$.each(data.rows, function(i, n){
	 		  if(data.rows[i].categoryId == editCategoryId2){
	 			  indexS = i;
	 		  }
	 		});
		    $('#editCenterAttrDataGrid').datagrid('selectRow',indexS);
		    secondClickCategoryEdit(indexS,data.rows[indexS]);
	     },
		 onClickRow: function (index, row) {  //easyui封装好的事件（被单机行的索引，被单击行的值）
			 secondClickCategoryEdit(index,row);
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
//点击二级类目调用方法
function secondClickCategoryEdit(indexSecondEd,rowSecondEd){
	 var id = rowSecondEd.categoryId;
	 editCategoryId2 = rowSecondEd.categoryId;
	 $('#editEastAttrDataGrid').datagrid({  
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
		 }  ] ],
		 onLoadSuccess : function(data){
			debugger;
 			var indexS;
		 	$.each(data.rows, function(i, n){
	 		  if(data.rows[i].categoryId == editCategoryId3){
	 			  indexS = i;
	 		  }
	 		});
		    $('#editEastAttrDataGrid').datagrid('selectRow',indexS);
		 }, 
		 onClickRow: function (index, row) {
			 debugger;
    		 editCategoryId3 = row.categoryId;
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















