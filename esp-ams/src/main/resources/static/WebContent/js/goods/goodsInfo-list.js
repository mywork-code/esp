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
	var externalsource;
	var support7dRefundFinal;
	
	//添加商品--是否已经保存商品信息
	var ifSaveGoodsFlag = false;
	
	var catenum = 0;//记录添加属性次数
	var catename = new Array();//记录添加属性名称  (ID)
	var goodsCateChangeFalg=1;//保存修改商品时商品类目有没有修改    为1未修改 为0 已修改 
	
	var viewdisplaybysource = 'jd';//修改商品   判断该商品是否京东     判定页面显示      京东商品页面显示2个   非京东页面显示4个。    所以跳页需要修改 。。 
/**
 * GOODS - info
 */
$(function() {
	//初始化
//	$('#addGoodsInfo').window('close');
//	$('#editGoodsInfo').window('close');
	$('#goodsStockInfo').window('close');
	$('#addGoodsStockInfoWin').window('close');
	$('#editaddGoodsStockInfoWin').window('close');
	$('#editGoodsStockInfoWin').window('close');
	$('#addAmtGoodsStockInfoWin').window('close');
	$('#bannerPicUpWin').window('close');
	$('#previewBannerWin').window('close');
	$('#previewProductWin').window('close');
	$('#editJDStockWin').window('close');
	/*新增商品    -新增库存信息*/
	$(".add-btn1").click(function() {//新增商品属性
		if(catenum>2){
			$.messager.alert("提示", "商品属性最多选择3个！", "info");
			return;
		}
		var divid = "goodsAttrId"+catenum;
		var selectnum = "selectnum"+catenum;
		var buttonid = catenum+"+buttonId";
		var str ='<div id ='+divid+'>';
			str+='<div style="margin-left: 20px;margin-top: 10px;text-align: -webkit-left;">'
				str+='<input class="easyui-combobox" style="width:95px;"  id='+selectnum+' name="属性" value="请选择商品属性" editable="false">';
				str+="&nbsp;&nbsp;"
				str+='<input  type = "button" id = '+buttonid+' value = "删除" style="width:40px;height: 20px;color : blue" onclick ="buttonclick(this.id)"/>';
//				str+='<a href="javascript:void(0);" id = '+buttonid+' class="easyui-linkbutton buttonclick">删除</a>'
			str+='</div>';
			str+='<div style = "margin-left: 20px;margin-top: 10px;text-align: -webkit-left;width:550px;">';
			for(var i = 0;i<10;i++){
				id = catenum+"+"+i+"goodsAttrId";
				str+='<input style="width:100px" onblur="createTableByCate(this.value,this.id)" id='+id+' name='+id+'/>'
			}
			str+='</div>';
		str+='</div>';
		$('#inputDiv').append(str);
		dropdown(selectnum,"add");
		catenum++;
	});
	//新增商品  保存库存
	$(".save-btnAlladd").click(function() {
		var ids = [];
	    var rows = $('#tableattrlist').datagrid('getRows');
	    for(var i=0; i<rows.length; i++){
	    	var d = i+1;
	    	if(rows[i].editing==true){
	    		$.messager.alert("提示", "第"+d+"行表格未保存，不可提交！", "info");
				return;
	    	}
	    	if(rows[i].stockTotalAmt<rows[i].stockAmt){
	    		$.messager.alert("提示", "第"+d+"行表格保存错误，库存总量小于剩余库存，不可提交！", "info");
				return;
	    	}
	        ids.push(rows[i]);
	    }
	    var param = {};
	    param['categorynameArr1']= categorynameArrX.toString();
	    param['categorynameArr2']= categorynameArrY.toString();
	    param['categorynameArr3']= categorynameArrZ.toString();
	    param["goodsStock"]= JSON.stringify(ids);
	    param["goodsId"]= finalGoodId;
		var address = ctx + '/application/goods/management/saveGoodsCateAttrAndStock';
		$.ajax({
			url : address,
			type : "POST",
			data : param,
			success : function(data) {
				//刷新前置页面库存列表（无需刷新）
				//关闭本窗口 无需关闭
				$.messager.alert("提示", data.msg, "info");
				if(data.status==1){
					$('#addGoodsInfo').window('close');
				}
			}
		});
	});
	///*修改商品    -新增库存信息*/
    $(".add-btn2").click(function() {
    	if(viewdisplaybysource == 'jd'){
    		$.messager.alert("提示", "京东商品不可维护商品属性和商品库存信息！", "info");
			return;
    	}
    	if(goodsCateChangeFalg==1){
    		$.messager.alert("提示", "商品类目未修改，不可维护商品属性！", "info");
			return;
    	}
    	if(goodsCateChangeFalg==0){//库存已删  商品类目已修改，调用新增商品库存的方法！
    		function1()
    	}
    });
	//修改商品保存库存
	$(".save-btnAllEdit").click(function() {
		if(viewdisplaybysource == 'jd'){
    		$.messager.alert("提示", "京东商品不可维护商品属性和商品库存信息！", "info");
			return;
    	}
		if(goodsCateChangeFalg==0){//库存已删  商品类目已修改，调用新增商品库存的方法！
			function2();
			return;
		}
		var ids = [];
	    var rows = $('#tableattrEditlist').datagrid('getRows');
	    for(var i=0; i<rows.length; i++){
	    	var d = i+1;
	    	if(rows[i].editing==true){
	    		$.messager.alert("提示", "第"+d+"行表格未保存，不可提交！", "info");
				return;
	    	}
	    	if(rows[i].stockTotalAmt<rows[i].stockCurrAmt){
	    		$.messager.alert("提示", "第"+d+"行表格保存错误，库存总量小于剩余库存，不可提交！", "info");
				return;
	    	}
	        ids.push(rows[i]);
	    }
	    var param = {};
//	    param['categorynameArr1']= categorynameArrX.toString();
//	    param['categorynameArr2']= categorynameArrY.toString();
//	    param['categorynameArr3']= categorynameArrZ.toString();
	    param["goodsStock"]= JSON.stringify(ids);
	    param["goodsId"]= finalGoodId;
		var address = ctx + '/application/goods/management/editsaveGoodsCateAttrAndStock';
		$.ajax({
			url : address,
			type : "POST",
			data : param,
			success : function(data) {
				//刷新前置页面库存列表（无需刷新）
				//关闭本窗口 无需关闭
				$.messager.alert("提示", data.msg, "info");
				if(data.status==1){
					$('#editGoodsInfo').window('close');
				}
			}
		});
	});
	/*修改库存信息 见最下方flushGoodsStock*/	
    $('#tablelist').datagrid({
        title : '商品列表',
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        striped:true,
        toolbar : '#tb',
        columns : [[{
                    title : '商户名称',
                    field : 'merchantName',
                    width : 90,
                    align : 'center'
                },{
                    title : '商品名称',
                    field : 'goodsName',
                    width : 90,
                    align : 'center'
                },{
					title : '商品编号',
					field : 'goodsCode',
					width : 90,
					align : 'center'
				},{
					title : 'skuid',
					field : 'externalId',
					width : 90,
					align : 'center'
				},{  
       		 		title : '类目名称',  
       		 		field : 'categoryName3', 
       		 	    width : 90,  
       		 		align : 'center'
       		 	},{
                    title : '商品型号',
                    field : 'goodsModel',
                    width : 80,
                    align : 'center'
                },{
                    title : '商品小标题',
                    field : 'goodsTitle',
                    width : 90,
                    align : 'center'
                },{
                    title : '商品类型',
                    field : 'goodsTypeDesc',
                    width : 80,
                    align : 'center',
//                },{
//                    title : '规格类型',
//                    field : 'goodsSkuType',
//                    width : 80,
//                    align : 'center'
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
                    field : 'listTimeString',
                    width : 140,
                    align : 'center'
                },{
                    title : '商品下架时间',
                    field : 'delistTimeString',
                    width : 140,
                    align : 'center'
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
                	title : '商品来源标识',
                	field : 'source',
                	width : 140,
                	align : 'center',
                	hidden: 'hidden'
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
                	
            		 if(row.status =='G02'&& merchantStatus=="1"&&row.source != 'jd'){
            			 content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.queryGoodsStockInfo('"
            				 + index +"');\">库存</a>&nbsp;&nbsp;";
            		 }
                	 
                     if(grantedAuthority=='permission'){
	                     if(row.status !='G02'&& merchantStatus=="1"){//已上架不允许修改
                    		 content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.editGoods('"
                              + index + "');\">修改</a>&nbsp;&nbsp;"
                    	 }
                    	 
                         if((row.status =='G03'||row.status =='G00')&& merchantStatus=="1"){//待上架或下架才能上架
	                    	 content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.shelves('"
	                    		 +  row.id + "','" +  row.source + "','" +  row.listTime + "','" +  row.delistTime + "');\">上架</a>&nbsp;&nbsp;" 
                         }
                         if(row.status =='G02'&& merchantStatus=="1"){//上架商品才能下架
                        	 content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.shelf('"
                                 + row.id + "','" +  row.source + "');\">下架</a>&nbsp;&nbsp;"
                         }
                     }
                     content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.previewProduct('"
                         + row.id +"','"+ row.source+ "','"+ row.externalId+ "');\">预览</a>&nbsp;&nbsp;";
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
                	console.log(data.rows);
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
        var params = {};
        params['merchantName'] = $("#merchantName").textbox('getValue');
        params['merchantType'] = $("#merchantType").combobox('getValue');
        params['goodsName'] = $("#goodsNames").textbox('getValue');
        params['goodsType'] = $("#goodsTypes").combobox('getValue');
        params['goodsCode'] = $("#goodsCode").textbox('getValue');
        params['goodsStatus'] = $("#goodsStatus").combobox('getValue');
        var goodsCategoryCombo=$("#goodsCategoryCombo").combotree('getValue');
        if("请选择"==goodsCategoryCombo){
        	goodsCategoryCombo="";
        }
        params['goodsCategoryCombo']=goodsCategoryCombo;
        
        $('#tablelist').datagrid('load', params);
    });
    // 刷新列表
    $("#flush").click(function() {
    	$("#goodsNames").textbox('setValue','');
    	$("#goodsTypes").combobox('setValue','');
    	$("#merchantName").textbox('setValue','');
    	$("#merchantType").combobox('setValue','');
    	$("#goodsCategoryCombo").combotree('setValue','');
    	$("#goodsCategoryCombo").combotree('setValue', '请选择');
    	$("#goodsStatus").combobox('setValue','');
    	$("#goodsCode").textbox('setValue','');
    	var params = {};
    	$('#tablelist').datagrid('load', params);
    });

	goodsCategoryComboFun();
    
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
    	addGoodId = '';
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
 		  $("#addgoodsNameL").text('');
 	      $("#addgoodsTitleL").text('');
 		   var params = {};
 	       //$('#tablelist').datagrid('load', params);
 	   }
 	}); 
    /**监听关闭编辑商品窗口事件**/
    $('#editGoodsInfo').window({    
    	onBeforeClose:function(){ 
    		var params = {};
    		//$('#tablelist').datagrid('load', params);
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
    	
    	$("#addUnSupportProvince").combobox({
    		url:ctx + "/application/nation/v1/queryNations", //后台获取所有省  
            method:'get',  
            panelHeight:200,//设置为固定高度，combobox出现竖直滚动条  
            valueField:'code',  
            textField:'province',  
            multiple:true,  
            formatter: function (row) { //formatter方法就是实现了在每个下拉选项前面增加checkbox框的方法  
                var opts = $(this).combobox('options');  
                return '<input type="checkbox" class="combobox-checkbox">' + row[opts.textField]  ;
            },  
            onSelect: function (row) { //选中一个选项时调用  
                var opts = $(this).combobox('options');  
                //获取选中的值的values  
                var val = $(this).combobox('getValues');
                if(!val[0]){
                	val.shift();
                }
                $("#addUnSupportProvince").combobox('setValues',val);  
                
               //设置选中值所对应的复选框为选中状态  
                var el = opts.finder.getEl(this, row[opts.valueField]);  
                el.find('input.combobox-checkbox')._propAttr('checked', true);  
            },  
            onUnselect: function (row) {//不选中一个选项时调用  
                var opts = $(this).combobox('options');  
                //获取选中的值的values  
                //$("#addUnSupportProvince").val($(this).combobox('getValues'));  
                
                var el = opts.finder.getEl(this, row[opts.valueField]);  
                el.find('input.combobox-checkbox')._propAttr('checked', false);  
            }  
    	});
    	//填写商品信息初始化
    	if(!ifSaveGoodsFlag){//如果是第一次进入商品信息页面清空页面内容
    		initGoodsInfo();
    	}
    	/*新增商品   第一个页面跳页   需要第一个页面的一级类目信息*/
    	/*刷新第四页面库存信息     见721行大图下一步（第三页面下一步跳第四页）*/
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
		var goodWallPic = $("#addGoodsLogoImg").attr("src");
		if(goodWallPic==null || goodWallPic==''){
			$.messager.alert("提示", "商品墙图片为空，请上传！", "info");
			return;
		}
		var addGoodBanenrList = $("#addGoodsbannerList").datagrid("getRows"); 
		if(addGoodBanenrList==null || addGoodBanenrList==''){
			$.messager.alert("提示", "商品大图为空，请上传！", "info");
			return;
		}
		var addEditor = UE.getEditor('addEditor').getContent();
		if(addEditor==null || addEditor==''){
			$.messager.alert("提示", "商品详情不能为空,请添加！", "info");
			return;
		}
		
		$("#addPlanDecrible #one").css('font-weight','lighter');
		$("#addPlanDecrible #two").css('font-weight','lighter');
    	$("#addPlanDecrible #three").css('font-weight','lighter');
    	$("#addPlanDecrible #four").css('font-weight','bold');
		
		$("#addSelectCategoryList").css('display','none');
    	$("#addWriteGoodsInfo").css('display','none');
    	$("#addUpLoadGoodsPicture").css('display','none');
    	$("#addGoodsStock").css('display','block');
    	
    	loadStockGoods('addGoodsStockList',addGoodId,null);
    	$('#inputDiv').empty();//新增商品清掉输入框
    	//一下全局变量做清空默认值处理(以及表格清空)
    	catenum = 0;//记录添加属性次数
    	catename = [];//记录添加属性名称  (ID)
    	categorynameArr1 = [];//单纯保存input内容
    	categorynameArr2 = [];
    	categorynameArr3 = [];
    	categorynameArrX = [];//保存input内容-属性ID   该属性下属所有规格最多十个
    	categorynameArrY = [];
    	categorynameArrZ = [];
    	flushAttrList();
    	flushAttrListPrepare(categorynameArr1,categorynameArr2,categorynameArr3);
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
	//新增库存：取消保存  
	$(".save-btnAlladddis").click(function() {
		$("#addPlanDecrible #one").css('font-weight','lighter');
		$("#addPlanDecrible #two").css('font-weight','lighter');
		$("#addPlanDecrible #three").css('font-weight','bold');
		$("#addPlanDecrible #four").css('font-weight','lighter');
		
		$("#addSelectCategoryList").css('display','none');
		$("#addWriteGoodsInfo").css('display','none');
		$("#addUpLoadGoodsPicture").css('display','block');
		$("#addGoodsStock").css('display','none');;
	})
	//新增库存：保存见最上方  save-btnAlladd 
	//以下2个方法弃用
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
		$(".search-btn").click();
		
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
	//点击选择商品分类
	$("#editPlanDecrible #one").click(function(){
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
		
		$(".search-btn").click();
	});
	//点击新增库存
	$("#editPlanDecrible #four").click(function(){
		$("#editPlanDecrible #one").css('font-weight','lighter');
    	$("#editPlanDecrible #two").css('font-weight','lighter');
    	$("#editPlanDecrible #three").css('font-weight','lighter');
    	$("#editPlanDecrible #four").css('font-weight','bold');
    	
    	$("#editSelectCategoryList").css('display','none');
    	$("#editWriteGoodsInfo").css('display','none');
    	$("#editUpLoadGoodsPicture").css('display','none');
    	$("#editGoodsStock").css('display','block');
    	
    	loadStockGoods('editGoodsStockList',editGoodId,externalsource);
//    	loadStockGoods('tableattrEditlist',editGoodId,externalsource);
    	flushtableattrEditlist();
    	//类目未修改才刷新下方规格
    	if(goodsCateChangeFalg==1){
    		flushGoodsStock(finalGoodId);
    	}
	});
	
	//监听编辑商品输入商品名称事件
//	var $editgoodsName = $("#editgoodsName"),$edit_last = $("#editgoodsNameL");
//	$editgoodsName.on("keydown",function(){
//		alert("keydown");
//		var len = $editMerchantPostcode.textbox('getValue').length;
//		$edit_last.html(len);
//	})
	if(!(externalsource == 'jd')){
		$("input",$("#editgoodsName").next("span")).keyup(function(){
			var len = $("#editgoodsName").textbox('getText').length;
			var canLen;
			console.log(len);
			if(len>30){
				canLen = len - 30;
				$("#editgoodsNameL").text('已经超出'+canLen+'个字');
			}else{
				canLen = 30 - len;
				$("#editgoodsNameL").text('还可以输入'+canLen+'个字');
			}
		})
		
		$("input",$("#editgoodsTitle").next("span")).keyup(function(){ 
			var len = $("#editgoodsTitle").textbox('getText').length;
			var canLen;
			if(len>25){
				canLen = len - 25;
				$("#editgoodsTitleL").text('已经超出'+canLen+'个字');
			}else{
				canLen = 25 - len;
				$("#editgoodsTitleL").text('还可以输入'+canLen+'个字');
			}
		})
		
	}
	
	//添加--商品名称动态提醒
	$("input",$("#addgoodsName").next("span")).keyup(function(){
		var len = $("#addgoodsName").textbox('getText').length;
		var canLen;
		if(len>30){
			canLen = len - 30;
			$("#addgoodsNameL").text('已经超出'+canLen+'个字');
		}else{
			canLen = 30 - len;
			$("#addgoodsNameL").text('还可以输入'+canLen+'个字');
		}
	})
	
	//添加--商品小标题动态提醒
	$("input",$("#addgoodsTitle").next("span")).keyup(function(){ 
		var len = $("#addgoodsTitle").textbox('getText').length;
		var canLen;
		console.log(len);
		if(len>25){
			canLen = len - 25;
			$("#addgoodsTitleL").text('已经超出'+canLen+'个字');
		}else{
			canLen = 25 - len;
			$("#addgoodsTitleL").text('还可以输入'+canLen+'个字');
		}
	})
	//编辑
	$.editGoods = function(index) {//编缉初始化
		$('#editGoodsInfo').window('open');
		goodsCateChangeFalg=1;
		var rowData = $('#tablelist').datagrid('getData').rows[index];
		externalsource = rowData.source;
		editGoodId = rowData.id;
		finalGoodId = editGoodId;
		support7dRefundFinal = rowData.support7dRefund;
		if(support7dRefundFinal == null || support7dRefundFinal == ''){
			$.ajax({
				type : "POST",
				url : ctx + '/application/goods/management/getsupport7dRefund',
				data : {'skuId':rowData.externalId},
				success : function(data) {
					// debugger;
					support7dRefundFinal = data.msg;
				}
			});
		}

		$("#editGoodsId").val(rowData.id);
		//防止数据库中商品新建时间为空
		if(null==rowData.newCreatDate || ''==rowData.newCreatDate){
			$("#editNewCreatDate").val("1900-01-01 00:00:00");
		}else{
			$("#editNewCreatDate").val(rowData.newCreatDate);
		}
		editGoodLogoUrl = rowData.goodsLogoUrl;
		editCategoryId1 = rowData.categoryId1;
		editCategoryId2 = rowData.categoryId2;
		editCategoryId3 = rowData.categoryId3;
		
		initEditGoodsInfo(rowData);//编辑商品回显
		$("#editBannerGoodsId").val(rowData.id);
		if(rowData.source == 'jd'){
			viewdisplaybysource = 'jd';
			$("#editPlanDecrible #one").css({'display':'none'});
	    	$("#editPlanDecrible #two").css({'display':'inline','font-weight':'bold'});
	    	$("#editPlanDecrible #three").css('display','none');
	    	$("#editPlanDecrible #four").css({'display':'inline','font-weight':'lighter'});
	    	
	    	$("#editSelectCategoryList").css('display','none');
			$("#editWriteGoodsInfo").css('display','block');
			$("#editUpLoadGoodsPicture").css('display','none');
			$("#editGoodsStock").css('display','none');
			$("input[name='editSupport7dRefund'][value='Y']").attr("disabled",true);
			$("input[name='editSupport7dRefund'][value='N']").attr("disabled",true);
		}else{
			viewdisplaybysource = 'notjd';
			$("#editPlanDecrible #one").css({'display':'inline','font-weight':'bold'});
			$("#editPlanDecrible #two").css({'display':'inline','font-weight':'lighter'});
			$("#editPlanDecrible #three").css({'display':'inline','font-weight':'lighter'});
			$("#editPlanDecrible #four").css({'display':'inline','font-weight':'lighter'});
			
			$("#editSelectCategoryList").css('display','block');
			$("#editWriteGoodsInfo").css('display','none');
			$("#editUpLoadGoodsPicture").css('display','none');
			$("#editGoodsStock").css('display','none');

			// $("input[name='editSupport7dRefund'][value='Y']").removeAttr("disabled");
			// $("input[name='editSupport7dRefund'][value='N']").removeAttr("disabled");
		}
    	
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
	//取消保存类目 关闭页面
	$("#disSaveGoodsCategory").click(function(){
		$('#editGoodsInfo').window('close');
	})
	
	//确认保存类目  跳页  跳第二页
	$("#saveGoodsCategory").click(function(){
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
				if(data.status == '1'){
					$.messager.alert("提示", "修改商品分类信息成功！", "info");
					$(".search-btn").click();
					//跳填写商品信息tag页
					$("#editPlanDecrible #one").css('font-weight','lighter');
			    	$("#editPlanDecrible #two").css('font-weight','bold');
			    	$("#editPlanDecrible #three").css('font-weight','lighter');
			    	$("#editPlanDecrible #four").css('font-weight','lighter');
			    	
			    	$("#editSelectCategoryList").css('display','none');
			    	$("#editWriteGoodsInfo").css('display','block');
			    	$("#editUpLoadGoodsPicture").css('display','none');
			    	$("#editGoodsStock").css('display','none');
	    			goodsCateChangeFalg = data.msg;//一级类目修改标志    为1未修改 为0 已修改     若已修改需要1清空库存（后端操作）2前段刷新第四页面刷新
	    			if(goodsCateChangeFalg==0){
	    				$('#inputDivEdit').empty();
	    				//新增商品清掉输入框
	    		    	//一下全局变量做清空默认值处理(以及表格清空)
	    		    	catenum = 0;//记录添加属性次数
	    		    	catename = [];//记录添加属性名称  (ID)
	    		    	categorynameArr1 = [];//单纯保存input内容
	    		    	categorynameArr2 = [];
	    		    	categorynameArr3 = [];
	    		    	categorynameArrX = [];//保存input内容-属性ID   该属性下属所有规格最多十个
	    		    	categorynameArrY = [];
	    		    	categorynameArrZ = [];
	    		    	flushtableattrEditlist();
	    		    	flushtableattrEditlistEdit(editGoodId,categorynameArr1,categorynameArr2,categorynameArr3);
	    			}
				}else{
					$.messager.alert("提示", data.msg, "info");
				}
			}
		});
    	
	});
	
	//第二页  -- 上一步  跳页  跳第一页     ||||京东商品不可跳页|||||
	$("#disEditgoodsAddinfo").click(function() {
		if(viewdisplaybysource=='jd'){
			$.messager.alert("提示", '京东商品不可显示商品类目', "info");
			return;
		}
		$("#editPlanDecrible #one").css('font-weight','bold');
		$("#editPlanDecrible #two").css('font-weight','lighter');
		$("#editPlanDecrible #three").css('font-weight','lighter');
		$("#editPlanDecrible #four").css('font-weight','lighter');
		
		$("#editSelectCategoryList").css('display','block');
		$("#editWriteGoodsInfo").css('display','none');
		$("#editUpLoadGoodsPicture").css('display','none');
		$("#editGoodsStock").css('display','none');
	})
	 //编辑 -- 保存商品信息   跳页 跳第三页                 ||||京东商品跳第四页|||||
	$("#editgoodsAddinfo").click(function() {
		var id=$("#editid").val(),
		editNewCreatDate=$("#editNewCreatDate").val(),
		goodsModel=$("#editgoodsModel").textbox('getValue'),
		goodsName=$("#editgoodsName").textbox('getValue'), 
		goodsTitle=$("#editgoodsTitle").textbox('getValue'),
		listTime=$("#editlistTime").datetimebox('getValue'),
		proDate=$("#editproDate").datebox('getValue'),
		delistTime=$("#editdelistTime").datetimebox('getValue'),
		editSupport7dRefund	= $("input[name='editSupport7dRefund']:checked").val(),
		unSupportProvince = $("#editUnSupportProvince").combobox('getText');
		keepDate=$("#editkeepDate").textbox('getValue');
		sordNo=$("#editsordNo").numberbox('getValue');
		supNo=$("#editsupNo").textbox('getValue');
		//goodsSkuType=$("#editgoodsSkuType").combobox('getValue');

		if(!editSupport7dRefund){
			editSupport7dRefund = "";
		}
		//字段效验
		if (!(externalsource == 'jd') &&(null == goodsModel || ("") == goodsModel)) {
			$.messager.alert("提示", "商品型号不能为空！", "info");
			return;
		}
		if (null == goodsName || ("") == goodsName) {
			$.messager.alert("提示", "商品名称不能为空！", "info");
			return;
		}

		if (!(externalsource == 'jd') && goodsName.length>30) {
			$.messager.alert("提示", "商品名称最多30字！", "info");
			return;
		}
	
		if (null == goodsTitle || ("") == goodsTitle) {
			$.messager.alert("提示", "商品小标题不能为空！", "info");
			return;
		}
		if (!(externalsource == 'jd') && goodsTitle.length>25) {
			$.messager.alert("提示", "商品小标题最多25字！", "info");
			return;
		}
//		if ((!(externalsource == 'jd'))&&(null == goodsSkuType || ("") == goodsSkuType)) {
//			$.messager.alert("提示", "商品规格类型不能为空！", "info");
//			return;
//		}
		if (null == listTime || ("") == listTime) {
			$.messager.alert("提示", "商品上架时间不能为空！", "info");
			return;
		}	
//		if (null == delistTime || ("") == delistTime) {
//			$.messager.alert("提示", "商品下架时间不能为空！", "info");
//			return;
//		}
		if (null != delistTime && ("") != delistTime) {
			if (listTime > delistTime) {
				$.messager.alert("提示", "上架时间不能大于下架时间！", "info");
				return;
			}
		}
//		if (null == proDate || ("") == proDate) {
//			$.messager.alert("提示", "商品生产日期不能为空！", "info");
//			return;
//		}
		if (null != proDate && ("") != proDate) {
			if (proDate > listTime) {
				$.messager.alert("提示", "商品生产日期不能大于上架日期！", "info");
				return;
			}
		}
//		if (!(externalsource == 'jd')&&(null == sordNo || ("") == sordNo)) {
//			$.messager.alert("提示", "商品排序不能为空！", "info");
//			return;
//		}
		if (null == unSupportProvince || ("") == unSupportProvince) {
			unSupportProvince = '';
		}
//		if (null == keepDate || ("") == keepDate) {
//			$.messager.alert("提示", "商品保质期不能为空！", "info");
//			return;
//		}
//		if (null == supNo || ("") == supNo) {
//			$.messager.alert("提示", "生产厂家不能为空！", "info");
//			return;
//		}
		var newGoodsCreatDate="1900-01-01 00:00:00";
		var time = new Date().Format("yyyy-MM-dd hh:mm:ss");
		if(externalsource == 'jd' && newGoodsCreatDate==editNewCreatDate){
			editNewCreatDate=time;
		}
		
		//提交表单
		//from重组
		var formObj = $("<form></form>").attr("method","post");
		formObj.append("<input type='text' name='id' value='"+id+"'/>");
		formObj.append("<input type='text' name='goodsModel' value='"+goodsModel+"'/>");
	 	formObj.append('<input type="text" name="goodsName" value="'+goodsName+'"/>');//htmlspecialchars
	 	
	 	formObj.append("<input type='text' name='goodsTitle' value='"+goodsTitle+"'/>");
	 	formObj.append("<input type='text' name='listTime'  dataType='Require' value='"+listTime+"'/>");
	 	if(!(null == delistTime || ("") == delistTime)){
	 		formObj.append("<input type='text' name='delistTime' value='"+delistTime+"'/>");
	 	}else{
	 		formObj.append("<input type='text' name='delistTime' value=''/>");
	 	}
		if(!(null == proDate || ("") == proDate)){
			formObj.append("<input type='text' name='proDate' value='"+proDate+"  00:00:00'/>");
		}else{
			formObj.append("<input type='text' name='proDate' value=''/>");
		}
		formObj.append("<input type='text' name='newCreatDate' value='"+editNewCreatDate+"'/>");

		formObj.append("<input type='text' name='keepDate' value='"+keepDate+"'/>");
		formObj.append("<input type='text' name='supNo' value='"+supNo+"'/>");
		if(!(null == sordNo || ("") == sordNo)){
			formObj.append("<input type='text' name='sordNo' value='"+sordNo+"'/>");
		}else{
			formObj.append("<input type='text' name='sordNo' value=''/>");
		}
		//formObj.append("<input type='text' name='goodsSkuType' value='"+goodsSkuType+"'/>");
		formObj.append("<input type='text' name='unSupportProvince' value='"+unSupportProvince+"'/>");
		formObj.append("<input type='text' name='support7dRefund' value='"+editSupport7dRefund+"'/>");
		formObj.css('display','none').appendTo("body");
		formObj.form("submit",{ 
			url : ctx + '/application/goods/management/edit',
			success : function(response) {
				var data = JSON.parse(response);
				ifLogout(data);
				if(data.status == '1'){
					$(".search-btn").click();
					$.messager.alert("提示", "修改商品成功", "info");
					//京东跳第四页   
					if(viewdisplaybysource=='jd'){
						$("#editPlanDecrible #one").css('font-weight','lighter');
				    	$("#editPlanDecrible #two").css('font-weight','lighter');
				    	$("#editPlanDecrible #three").css('font-weight','lighter');
				    	$("#editPlanDecrible #four").css('font-weight','bold');
				    	
				    	$("#editSelectCategoryList").css('display','none');
				    	$("#editWriteGoodsInfo").css('display','none');
				    	$("#editUpLoadGoodsPicture").css('display','none');
				    	$("#editGoodsStock").css('display','block');
				    	
				    	loadStockGoods('editGoodsStockList',id,externalsource);
//				    	loadStockGoods('tableattrEditlist',editGoodId,externalsource);
				    	flushtableattrEditlist();
				    	//类目未修改才刷新规格
				    	if(goodsCateChangeFalg==1){
				    		flushGoodsStock(id);
				    	}
						return;
					}
					//非京东 跳页  跳第三页
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
					
					$(".search-btn").click();
				}else{
					$.messager.alert("提示", data.msg, "info");
				}
			}
		});
	});
	
	//保存上传的图片
	$("#saveEditUploadPic").click(function(){
		$.messager.alert("提示", "保存上传图片成功！", "info");
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
		$(".search-btn").click();
	});
	
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
					// debugger;
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
					// debugger;
					$.messager.alert("提示", '商品大图上传成功！');
					$("#editBannerPicOrder").numberbox('clear');
					$("#editBannerPicFile").val('')
					// debugger;
					loadBanner("editGoodsbannerList",editGoodId);
					$(".search-btn").click();
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
			params['goodsDetail'] = UE.getEditor('addEditor').getAllHtml();
			params['goodsContent'] = UE.getEditor('addEditor').getContent();
            var uehtml = UE.getEditor('editEditor').getAllHtml();
		    uehtml = uehtml.replace(/<p>/g,"<p style='margin: 0px'>");
            uehtml = uehtml.replace(/<img/g,"<img style='display: block'");
			if(UE.getEditor('addEditor').getContent() == null || UE.getEditor('addEditor').getContent()==''){
				$.messager.alert("提示", "请输入详情信息", "info");
				return;
			}
			$.ajax({
				type : "POST",
				url : ctx + '/application/goods/management/upeditor',
				data : params,
				success : function(data) {
					// debugger;
					if(data.message=='timeout'){
						$.messager.alert("操作提示", "登录超时, 请重新登录", "info");
	    				window.top.location = ctx + "/logout";
	    				return false;
					}
					if(data == 'SUCCESS'){
						$(".search-btn").click();
						$.messager.alert("提示", "保存商品详情成功", "info");
					}
				}
			});
	});
	//取消   确定 获取编辑器的html   并且跳页  第三页跳第二页
	$("#disEditGetAllHtml").click(function() {
		$("#editPlanDecrible #one").css('font-weight','lighter');
    	$("#editPlanDecrible #two").css('font-weight','bold');
    	$("#editPlanDecrible #three").css('font-weight','lighter');
    	$("#editPlanDecrible #four").css('font-weight','lighter');
    	
    	$("#editSelectCategoryList").css('display','none');
    	$("#editWriteGoodsInfo").css('display','block');
    	$("#editUpLoadGoodsPicture").css('display','none');
    	$("#editGoodsStock").css('display','none');
	})
	//确定 获取编辑器的html   并且跳页   第三页跳第四页
	$("#editGetAllHtml").click(function() {
		var params = {};
		params['id']=editGoodId;
		var rows = $('#editGoodsbannerList').datagrid('getRows');
		if(rows.length<2){
			$.messager.alert("提示", "请上传商品大图，最少2张", "info");
			return;
		}
//			var goodsDetails = UE.getEditor('editor').getAllHtml();
//			params['goodsDetail'] = goodsDetails.splice(12,0,'<meta charset="UTF-8">');
		params['goodsDetail'] = UE.getEditor('editEditor').getAllHtml();;
		params['goodsContent'] = UE.getEditor('editEditor').getContent();
		
		var uehtml = UE.getEditor('editEditor').getAllHtml();
		uehtml = uehtml.replace(/<p>/g,"<p style='margin: 0px'>");
        uehtml = uehtml.replace(/<img/g,"<img style='display: block'");
		params['goodsDetail'] =uehtml;
		if(UE.getEditor('editEditor').getContent() == null || UE.getEditor('editEditor').getContent()==''){
			$.messager.alert("提示", "请输入详情信息", "info");
			return;
		}
		$.ajax({
			type : "POST",
			url : ctx + '/application/goods/management/upeditor',
			data : params,
			success : function(data) {
				ifLogout(data)
//				if(data.message=='timeout'){
//					$.messager.alert("操作提示", "登录超时, 请重新登录", "info");
//					window.top.location = ctx + "/logout";
//					return false;
//				}
				if(data == 'SUCCESS'){
					$(".search-btn").click();
					$.messager.alert("提示", "保存商品详情成功", "info");
					//跳页
					$("#editPlanDecrible #one").css('font-weight','lighter');
			    	$("#editPlanDecrible #two").css('font-weight','lighter');
			    	$("#editPlanDecrible #three").css('font-weight','lighter');
			    	$("#editPlanDecrible #four").css('font-weight','bold');
			    	
			    	$("#editSelectCategoryList").css('display','none');
			    	$("#editWriteGoodsInfo").css('display','none');
			    	$("#editUpLoadGoodsPicture").css('display','none');
			    	$("#editGoodsStock").css('display','block');
			    	
			    	loadStockGoods('editGoodsStockList',editGoodId,externalsource);
//			    	loadStockGoods('tableattrEditlist',editGoodId,externalsource);
			    	flushtableattrEditlist();
			    	//类目未修改才刷新规格
			    	if(goodsCateChangeFalg==1){
			    		flushGoodsStock(finalGoodId);
			    	}
				}
			}
		});
	});
	//修改库存    第四页 取消  跳第三页    ||||||京东商品跳第二页   ||||||||
	$(".save-btnAllEditdis").click(function() {
		if(viewdisplaybysource=='jd'){
			$("#editPlanDecrible #one").css('font-weight','lighter');
	    	$("#editPlanDecrible #two").css('font-weight','bold');
	    	$("#editPlanDecrible #three").css('font-weight','lighter');
	    	$("#editPlanDecrible #four").css('font-weight','lighter');
	    	
	    	$("#editSelectCategoryList").css('display','none');
	    	$("#editWriteGoodsInfo").css('display','block');
	    	$("#editUpLoadGoodsPicture").css('display','none');
	    	$("#editGoodsStock").css('display','none');
	    	return;
		}
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
		
		$(".search-btn").click();
	});
	//修改库存    第四页 确定 保存库存    save-btnAllEdit   见上方
	//==================================-------编辑商品 末--------===============================================//
	//==================================-------库存商品 始--------===============================================//
	//库存弹框datagrid
	$.queryGoodsStockInfo = function(index) {
		var dataRow = $('#tablelist').datagrid('getData').rows[index];
		var goodsId=dataRow.id
		finalGoodId = goodsId;
		var status=dataRow.status;
		var source = dataRow.source;
		$('#goodsId').val(goodsId);
		$('#goodsStockInfo').window({modal: true});
		$('#goodsStockInfo').window('open');
		
		//loadStockGoods('goodsStockList',goodsId,source);
		loadStockGoodsAbout('goodsStockList',goodsId,source);
	}
	
	//商品规格长度校验
	$("input",$("#goodsSkuAttr").next("span")).blur(function(){  
		var goodsSkuAttr = $("#goodsSkuAttr").textbox('getValue');
		if(goodsSkuAttr.length>8){
			$.messager.alert("<span style='color: black;'>提示</span>","商品规格内容不能超过8个字",'info');
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
	//新增商品新增库存 取消
	$("#addGoodsStockInfoDisy").click(function() {
		$('#addGoodsStockInfoWin').window('close');
	});
	//新增商品新增库存 确定
	$("#addGoodsStockInfoSumbit").click(function() {
		$("#addstockInfogoodsId").val(finalGoodId);
		var goodsCostPrice=$("#goodsCostPrice").textbox('getValue');
		var goodsPrice=$("#goodsPrice").textbox('getValue');
//		var goodsSkuAttr=$("#goodsSkuAttr").textbox('getValue');
//		var marketPrice=$("#marketPrice").textbox('getValue');
		var stockTotalAmt=$("#stockTotalAmt").textbox('getValue');
//		var goodsCompareUrlone=$("#goodsCompareUrlone").textbox('getValue');
//		var goodsCompareUrltwo=$("#goodsCompareUrltwo").textbox('getValue');
		// debugger;
//		$('#goodsCompareUrl').val(goodsCompareUrlone+";"+goodsCompareUrlone);
//		console.log($('#goodsCompareUrl').val());
		var stockLogoFile=$("#stockLogoFile").val();
//		if (null == goodsCompareUrlone || ("") == goodsCompareUrlone) {
//			$.messager.alert("提示", "比价链接不允许为空！", "info");
//			return;
//		}
//		if (null == goodsCompareUrltwo || ("") == goodsCompareUrltwo) {
//			$.messager.alert("提示", "比价链接不允许为空！", "info");
//			return;
//		}
//		if (null == goodsSkuAttr || ("") == goodsSkuAttr) {
//			$.messager.alert("提示", "库存规格不能为空！", "info");
//			return;
//		}
//		if(goodsSkuAttr.length>8){
//			$.messager.alert("提示", "库存规格最多8字！", "info");  
//			return;
//		}
//		if (null == marketPrice || ("") == marketPrice) {
//			$.messager.alert("提示", "市场价不能为空！", "info");
//			return;
//		}
		if (null == goodsCostPrice || ("") == goodsCostPrice) {
			$.messager.alert("提示", "商品成本不能为空！", "info");
			return;
		}
		if (null == goodsPrice || ("") == goodsPrice) {
			$.messager.alert("提示", "商品售价不能为空！", "info");
			return;
		}
		if (null == stockTotalAmt || ("") == stockTotalAmt) {
			$.messager.alert("提示", "库存总量不能为空！", "info");
			return;
		}
		if (null == stockLogoFile || ("") == stockLogoFile) {
			$.messager.alert("提示", "未选择商品库存缩略图LOGO！", "info");
			return;
		}
		//提交from
		var theForm = $("#stockInfoFrom");
		theForm.form({
//			url : ctx + '/application/goods/stockinfo/add',
			url : ctx + '/application/goods/stockinfo/addForLogo',
			success : function(data) {
            	if (data.indexOf ('请输入账户') != -1){
            		$.messager.alert("提示", "超时，请重新登录", "info");
            		parent.location.reload();
			    }
				var response = JSON.parse(data);
				if(response.msg=="success"){
					$.messager.alert("提示","缩略图上传成功！", "info");
//			        loadStockGoods('addGoodsStockList',finalGoodId,null);
//			        loadStockGoods('editGoodsStockList',finalGoodId,null);
//			        loadStockGoods('goodsStockList',finalGoodId,null);
					$('#tableattrlist').datagrid('updateRow',
					{
						index: addindex,
						row:{
							"stockLogo":response.data,
							"goodsCostPrice":goodsCostPrice,
							"goodsPrice":goodsPrice,
							"stockTotalAmt":stockTotalAmt,
							"stockAmt":stockTotalAmt
							}
					});
					$('#addGoodsStockInfoWin').window('close');
					$(".search-btn").click();
				}else{
					$.messager.alert("提示", response.msg, "info");
				}
		    }
		});
		theForm.submit();
	});
	//新增商品新增库存 防止表单干净提交代码
	$("#stockInfoFrom").submit(function(){  
		$(":submit",this).attr("disabled","disabled");  
	});
	//修改商品新增库存 取消
	$("#editaddGoodsStockInfoDisy").click(function() {
		$('#editaddGoodsStockInfoWin').window('close');
	});
	//修改商品新增库存 确定
	$("#editaddGoodsStockInfoSumbit").click(function() {
		$("#editaddstockInfogoodsId").val(finalGoodId);
		var goodsCostPrice=$("#editaddgoodsCostPrice").textbox('getValue');
		var goodsPrice=$("#editaddgoodsPrice").textbox('getValue');
		var stockTotalAmt=$("#editaddstockTotalAmt").textbox('getValue');
		var stockLogoFile=$("#editaddstockLogoFile").val();
		if (null == goodsCostPrice || ("") == goodsCostPrice) {
			$.messager.alert("提示", "商品成本不能为空！", "info");
			return;
		}
		if (null == goodsPrice || ("") == goodsPrice) {
			$.messager.alert("提示", "商品售价不能为空！", "info");
			return;
		}
		if (null == stockTotalAmt || ("") == stockTotalAmt) {
			$.messager.alert("提示", "库存总量不能为空！", "info");
			return;
		}
		if (null == stockLogoFile || ("") == stockLogoFile) {
			$.messager.alert("提示", "未选择商品库存缩略图LOGO！", "info");
			return;
		}
		var theForm = $("#editaddstockInfoFrom");
		theForm.form({
			url : ctx + '/application/goods/stockinfo/addForLogo',
			success : function(data) {
            	if (data.indexOf ('请输入账户') != -1){
            		$.messager.alert("提示", "超时，请重新登录", "info");
            		parent.location.reload();
			    }
				var response = JSON.parse(data);
				if(response.msg=="success"){
					$.messager.alert("提示","缩略图上传成功！", "info");
					$('#tableattrEditlist').datagrid('updateRow',
					{
						index: editindex,
						row:{
							"stockLogo":response.data,
							"goodsCostPrice":goodsCostPrice,
							"goodsPrice":goodsPrice,
							"stockTotalAmt":stockTotalAmt,
							"stockCurrAmt":stockTotalAmt
							}
					});
					$('#editaddGoodsStockInfoWin').window('close');
					$(".search-btn").click();
				}else{
					$.messager.alert("提示", response.msg, "info");
				}
		    }
		});
		theForm.submit();
	});
	//修改商品新增库存 防止表单重复提交代码
	$("#editaddstockInfoFrom").submit(function(){  
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
//		var stockinfoAmtAll=parseInt(addstockinfoAmt)+parseInt(stockTotalAmtTemp);//库存商品总量
		var stockinfoAmtAll=parseInt(addstockinfoAmt)+parseInt(stockCurrAmtTemp);//当前库存量
		if (stockinfoAmtAll > 999){
			$.messager.alert ("提示", "当前库存数量不能超过999个！", "info");
			return;
		}
		var param = {};
		param['id']=stockinfoId;
		param['addAmt']=addstockinfoAmt;
		$.ajax({url : ctx + '/application/goods/stockinfo/editStockinfo',type : "POST",data : param,success : function(data) {
			 	var params = {};
		        params['goodsId'] = goodsId;
//		        $('#addGoodsStockList').datagrid('load', params);
//		        $('#editGoodsStockList').datagrid('load', params);
//		        loadStockGoods('addGoodsStockList',goodsId,null);
//		        loadStockGoods('editGoodsStockList',goodsId,null);
//		        loadStockGoods('goodsStockList',goodsId,null);
		        loadStockGoodsAbout('goodsStockList',goodsId,null);
		        $('#addAmtGoodsStockInfoWin').window('close');
		        $(".search-btn").click();
			}
		});
	});
	
	var sourceJd;//修改库存时商品来源标识
	//修改库存
	$.editStockinfo = function(index,datagridId,source,status) {
		var row = $('#'+datagridId).datagrid('getData').rows[index];
		sourceJd = source;
		if(source == "jd"){
			$("#editJDgoodsPrice").textbox('setValue',FormatAfterDotNumber(row.goodsPrice,2));
			$("#editJDmarketPrice").textbox('setValue',FormatAfterDotNumber(row.marketPrice,2));
			$("#editJDgoodsCostPrice").textbox('setValue',FormatAfterDotNumber(row.goodsCostPrice,2));
			$("#editJDstockinfoId").val(row.id);
			$("#editJDgoodsCompareUrlone").textbox('setValue',row.goodsCompareUrl);
			$("#editJDgoodsCompareUrltwo").textbox('setValue',row.goodsCompareUrl2);
			
			$('#editJDStockWin').window({modal: true});
			$('#editJDStockWin').window('open');
		}else{
			if(status == 'G02'){
				$("#editgoodsSkuAttr").textbox('textbox').attr("disabled","disabled");
				$("#editmarketPrice").next("span").children(".validatebox-text").attr("disabled","disabled");
				$("#editgoodsCostPrice").next("span").children(".validatebox-text").attr("disabled","disabled");
				$("#editgoodsPrice").next("span").children(".validatebox-text").attr("disabled","disabled");
			}else{
				$("#editgoodsSkuAttr").textbox('textbox').removeAttr("disabled");
				$("#editmarketPrice").next("span").children(".validatebox-text").removeAttr("disabled");
				$("#editgoodsCostPrice").next("span").children(".validatebox-text").removeAttr("disabled");
				$("#editgoodsPrice").next("span").children(".validatebox-text").removeAttr("disabled");
			}
			
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
		}
	};
	$("#editJDGoodsStockInfoDisy").click(function() {
		$('#editJDStockWin').window('close');
	});
	
	// 修改商品修改库存 取消
	$("#editGoodsStockInfoDisy").click(function() {
		$('#editGoodsStockInfoWin').window('close');
	});
	// 修改商品修改库存 确定
	$("#editGoodsStockInfoSumbit").click(function() {
		var goodsId=finalGoodId;
		var stockinfoId=$("#editstockinfoId").val();
		var goodsSkuAttr=$("#editgoodsSkuAttr").textbox('getValue');
		var goodsCostPrice=$("#editgoodsCostPrice").textbox('getValue');
		var goodsPrice=$("#editgoodsPrice").textbox('getValue');
		var stockTotalAmt=$("#editstockTotalAmt").textbox('getValue');
//		var marketPrice=$("#editmarketPrice").textbox('getValue');
//		var editgoodsCompareUrlone=$("#editgoodsCompareUrlone").textbox('getValue');
//		var editgoodsCompareUrltwo=$("#editgoodsCompareUrltwo").textbox('getValue');
		var editstockLogoFile=$("#editstockLogoFile").val();
		var editStockGoodsLogoFile=$('#editStockGoodsLogoFile').val();

//		if (null == editgoodsCompareUrlone || ("") == editgoodsCompareUrlone) {
//			$.messager.alert("提示", "比价链接不允许为空！", "info");
//			return;
//		}
//		if (null == editgoodsCompareUrltwo || ("") == editgoodsCompareUrltwo) {
//			$.messager.alert("提示", "比价链接不允许为空！", "info");
//			return;
//		}
//		if (null == goodsSkuAttr || ("") == goodsSkuAttr) {
//			$.messager.alert("提示", "属性不能为空！", "info");
//			return;
//		}
//		if(goodsSkuAttr.length>8){
//			$.messager.alert("提示", "库存规格最多8字！", "info");  
//			return;
//		}
//		if (null == marketPrice || ("") == marketPrice) {
//			$.messager.alert("提示", "市场价不能为空！", "info");
//			return;
//		}
		if (null == goodsCostPrice || ("") == goodsCostPrice) {
			$.messager.alert("提示", "商品成本不能为空！", "info");
			return;
		}
		if (null == goodsPrice || ("") == goodsPrice) {
			$.messager.alert("提示", "商品售价不能为空！", "info");
			return;
		}
		if (null == stockTotalAmt || ("") == stockTotalAmt) {
			$.messager.alert("提示", "库存总量不能为空！", "info");
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
		// debugger;
		var param = {};
		param['id']=stockinfoId;
//		param['goodsSkuAttr']=goodsSkuAttr;
		param['goodsCostPrice']=goodsCostPrice;
		param['goodsPrice']=goodsPrice;
//		param['addAmt']=stockTotalAmt;
//		param['marketPrice']=marketPrice;
//		param['goodsCompareUrl']=editgoodsCompareUrlone;
//		param['goodsCompareUrl2']=editgoodsCompareUrltwo;
		$.ajax({ url : ctx + '/application/goods/stockinfo/editStockinfo', type : "POST", data : param,
			success : function(data) {
//				var response = JSON.parse(data);
				if(data.msg=="success"){
					// debugger;
					$.messager.alert("提示", "修改库存信息成功！", "info");
					var params = {};
			        params['goodsId'] = goodsId;
//			        loadStockGoods('addGoodsStockList',goodsId,sourceJd);
//			        loadStockGoods('editGoodsStockList',goodsId,sourceJd);
//			        loadStockGoods('goodsStockList',goodsId,sourceJd);
			        $('#editGoodsStockInfoWin').window('close');
			        flushGoodsStock();
	            	flushtableattrEditlistEdit(goodsId,categorynameArr1,categorynameArr2,categorynameArr3);
			        $(".search-btn").click();
				}else{
					$.messager.alert("提示", data.msg, "info");
				}
			}
		});
	});
	//修改商品修改库存 上传库存logo(修改LOGO)
	$("#upStockLogoBtn").click(function() {
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
				 $.messager.alert("提示", response.msg, "info");
			}
		});
	});
	
	$("#editJDGoodsStockInfoSumbit").click(function() {
		var goodsId=finalGoodId;
		var stockId = $("#editJDstockinfoId").val()
		var goodsPrice=$("#editJDgoodsPrice").textbox('getValue');
		var marketPrice=$("#editJDmarketPrice").textbox('getValue');
		var goodsCostPrice=$("#editJDgoodsCostPrice").textbox('getValue');
		var editJDgoodsCompareUrlone=$("#editJDgoodsCompareUrlone").textbox('getValue');
		var editJDgoodsCompareUrltwo=$("#editJDgoodsCompareUrltwo").textbox('getValue');
		
		if (null == editJDgoodsCompareUrlone || ("") == editJDgoodsCompareUrlone) {
			editJDgoodsCompareUrlone = '';
		}
		if (null == editJDgoodsCompareUrltwo || ("") == editJDgoodsCompareUrltwo) {
			editJDgoodsCompareUrltwo = '';
		}
		if (null == goodsPrice || ("") == goodsPrice) {
			$.messager.alert("提示", "商品价格不能为空！", "info");
			return;
		}
		if (null == marketPrice || ("") == marketPrice) {
			$.messager.alert("提示", "市场价不能为空！", "info");
			return;
		}
		if (null == goodsCostPrice || ("") == goodsCostPrice) {
			$.messager.alert("提示", "成本价不能为空！", "info");
			return;
		}
		var param = {
			"id":stockId,
			"goodsPrice":goodsPrice,
			"marketPrice":marketPrice,
			"goodsCostPrice":goodsCostPrice,
			"goodsCompareUrl":editJDgoodsCompareUrlone,
			"goodsCompareUrl2":editJDgoodsCompareUrltwo
		};
		
		$.ajax({
			type : "POST",
			url : ctx + '/application/goods/stockinfo/editStockinfo',
			data : param,
			success : function(data) {
//				var response = JSON.parse(data);
				if(data.msg=="success"){
					$.messager.alert("提示", "修改库存信息成功！", "info");
					var params = {};
			        params['goodsId'] = goodsId;
			        
			        loadStockGoods('addGoodsStockList',goodsId,sourceJd);
			        loadStockGoods('editGoodsStockList',goodsId,sourceJd);
			        loadStockGoods('goodsStockList',goodsId,sourceJd);
			        $(".search-btn").click();
			        $('#editJDStockWin').window('close');
				}else{
					$.messager.alert("提示", data.msg, "info");
				}
			}
		});
		
	})
	
	
	
	//删除banner图
	$.delbanner = function(datagridId,id,goodsId) {
		$.messager.confirm('提示框', '你确定要删除吗?',function(r){
			// debugger;
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
	$.shelves = function(id,source,listTime,delistTime) {
		$.messager.confirm('提示框', '你确定要上架吗?',function(r){
			if(r){
				// debugger;
				var params = {};
				params['id']=id;
				params['source']=source;
				params['listTime']=listTime;
				params['delistTime']=delistTime;
//				var editorGoodsDetail  = UE.getEditor('editor').getContent();
//				params['editorGoodsDetail']=editorGoodsDetail;
				if (null == listTime || 'null' == listTime) {
					$.messager.alert("提示", "商品上架时间不能为空！", "info");
					return;
				}
//				if (null == delistTime || 'null' == delistTime) {
//					$.messager.alert("提示", "商品下架时间不能为空！", "info");
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
                            $(".search-btn").click();
						}
					}
				});
			} 
		})
	 
	};
	//下架
	$.shelf = function(id,source) {
		$.messager.confirm('提示框', '你确定要下架吗?',function(r){
			if(r){
				var params = {};
				params['id']=id;
				params['source']=source;
				$.ajax({
					type : "POST",
					url : ctx + '/application/goods/management/shelf',
					data : params,
					success : function(data) {
					  // debugger;
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
		// debugger;
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
	//编辑商品--上传logo
	$("#editUpLogoBtn").click(function() {
		var editGoodsLogoFile=$('#editGoodsLogoFile').val();
		if (null == editGoodsLogoFile || ("") == editGoodsLogoFile) {
			$.messager.alert("提示", "请选择文件！", "info");
			return;
		}
//		var pos = "." + editGoodsLogoFile.replace(/.+\./, "");
//		if(pos!=".png"){
//			$.messager.alert("提示", '请导入正确的文件格式', "info");
//			return;
//		}
		//提交from
		var thisform = $("#editLogoFilepic");
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
					loadLogo("editGoodsLogoImg",response.data);
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
		// debugger;
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
	$.previewProduct = function(id,source,eid) {
        var subtitle = "商品预览-" + id;
        var parentTabs = parent.$('#tabs');
        var destAddress="";
        if("jd"==source){
            subtitle = "商品预览-" + eid;
        	destAddress = ctx + "/application/goods/management/loadAllBannerPicJD?skuId=" + eid+"&view=list";
        }else{
        	destAddress = ctx + "/application/goods/management/loadAllBannerPic?id=" + id+"&view=list";
        }
//    	destAddress = ctx + "/application/goods/management/loadAllBannerPicJD?id=" + id+"&view=list";

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
	// 导出商品列表
	$ (".export-btn").click (function (){
		// debugger;
		$.messager.confirm ('商品信息', '确定要导出吗？', function (r)
		{
			if (r)
			{
				// debugger;
				var params = {};
				params['merchantName'] = $("#merchantName").textbox('getValue');
		        params['merchantType'] = $("#merchantType").combobox('getValue');
		        params['goodsName'] = $("#goodsNames").textbox('getValue');
		        params['goodsType'] = $("#goodsTypes").combobox('getValue');
		        var goodsCategoryCombo=$("#goodsCategoryCombo").combotree('getValue');
		        if("请选择"==goodsCategoryCombo){
		        	goodsCategoryCombo="";
		        }
		        var arry = goodsCategoryCombo.split("_");
		        var level = arry[0];
		        var id = arry[1];
		        var categoryId1 = '';
		        var categoryId2 = '';
		        var categoryId3 = '';
		        if(level == '1'){
		        	categoryId1 = id;
		        }else if(level == '2'){
		        	categoryId2 = id;
		        }else if(level == '3'){
		        	categoryId3 = id;
		        }
		        
		        params['categoryId1']=categoryId1;
		        params['categoryId2']=categoryId2;
		        params['categoryId3']=categoryId3;
				params['goodsCode'] = $("#goodsCode").textbox('getValue');
				params['status']= $("#goodsStatus").combobox('getValue');//params['goodsStatus']

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
	//20171027 sprint11删除属性录入  在库存页面录入
	var merchantCode=$("#addmerchantCode").textbox('getValue'), 
	goodsModel=$("#addgoodsModel").textbox('getValue'),
	goodsName=$("#addgoodsName").textbox('getValue'), 
	goodsTitle=$("#addgoodsTitle").textbox('getValue'), 
	listTime=$("#addlistTime").datetimebox('getValue'), 
	proDate=$("#addproDate").datebox('getValue'),
	delistTime=$("#adddelistTime").datetimebox('getValue'),
	keepDate=$("#addkeepDate").textbox('getValue'),
	supNo=$("#addsupNo").textbox('getValue'),
	//goodsSkuType=$("#addgoodsSkuType").combobox('getValue'),
	unSupportProvince=$("#addUnSupportProvince").combobox('getText'),
	addSupport7dRefund=$("input[name='addSupport7dRefund']:checked").val(),
	sordNo=$("#sordNo").numberbox('getValue');
	// debugger;
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
	
	if(goodsName.length>30){
		$.messager.alert("提示", "商品名称最多30字！", "info");  
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
//	if (null == goodsSkuType || ("") == goodsSkuType) {
//		$.messager.alert("提示", "商品规格类型不能为空！", "info");
//		return;
//	}
	if (null == listTime || ("") == listTime) {
		$.messager.alert("提示", "商品上架日期不能为空！", "info");
		return;
	}	
//	if (null == delistTime || ("") == delistTime) {
//		$.messager.alert("提示", "商品下架日期不能为空！", "info");
//		return;
//	}
	if (null != delistTime && ("") != delistTime) {
		if (listTime > delistTime) {
			$.messager.alert("提示", "上架时间不能大于下架时间！", "info");
			return;
		}
	}
//	if (null == proDate || ("") == proDate) {
//		$.messager.alert("提示", "商品生产日期不能为空！", "info");
//		return;
//	}
	if (null != proDate && ("") != proDate) {
		if (proDate > listTime) {
			$.messager.alert("提示", "商品生产日期不能大于上架日期！", "info");
			return;
		}
	}
//	if (null == keepDate || ("") == keepDate) {
//		$.messager.alert("提示", "商品保质期不能为空！", "info");
//		return;
//	}
	
//	if (null == supNo || ("") == supNo) {
//		$.messager.alert("提示", "生产厂家不能为空！", "info");
//		return;
//	}
	
	if (null == addSupport7dRefund || ("") == addSupport7dRefund) {
		$.messager.alert("提示", "是否支持7天退货还能为空！", "info");
		return;
	}
	// if (null == sordNo || ("") == sordNo) {
	// 	$.messager.alert("提示", "排序不能为空！", "info");
	// 	return;
	// }
	// debugger;
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
 	if (!(null == delistTime || ("") == delistTime)) {
 		formObj.append("<input type='text' name='delistTime' value='"+delistTime+"'/>");
 	}else{
 		formObj.append("<input type='text' name='delistTime' value=''/>");
 	}
 	formObj.append("<input type='text' name='unSupportProvince' value='"+unSupportProvince+"'/>");
	formObj.append("<input type='text' name='goodId' value='"+addGoodId+"'/>");
	if(proDate!=""){
		formObj.append("<input type='text' name='proDate' value='"+proDate+" 00:00:00'/>");
	}
	if(keepDate!=""){
		formObj.append("<input type='text' name='keepDate' value='"+keepDate+"'/>");
	}
	if(supNo!=""){
		formObj.append("<input type='text' name='supNo' value='"+supNo+"'/>");
	}
	//formObj.append("<input type='text' name='goodsSkuType' value='"+goodsSkuType+"'/>");
	formObj.append("<input type='text' name='support7dRefund' value='"+addSupport7dRefund+"'/>");
	formObj.append("<input type='text' name='sordNo' value='"+sordNo+"'/>");
	formObj.css('display','none').appendTo("body");
	formObj.form("submit",{ 
		url : ctx + '/application/goods/management/add',
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
		    	$(".search-btn").click();
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
function loadStockGoods(datagridId,goodsId,source){
	var statusV = null;
	if('goodsStockList' == datagridId){
		statusV = 'G02';
	}
	
		if(source=='jd'){
		$("#editGoodsStockAddButton").css('visibility','hidden');
	}else{
		$("#editGoodsStockAddButton").css('visibility','visible');
	}
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
                		if(value !=null){
                			var content = "";
                			content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.alertPic('"
                				+ row.stockLogo + "');\">"+row.stockLogo+"</a>&nbsp;&nbsp;";
                			return content;
                		}
                		
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
                    		if(source != 'jd'){
                    			content +="<a href='javascript:void(0);' class='easyui-linkedbutton' " +
                    			"onclick=\"$.updateStockinfo('"+row.id+"','"+row.stockTotalAmt+"','"+row.stockCurrAmt+"');\">加库存</a>&nbsp;&nbsp;";
                    		}
                    		content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.editStockinfo('"+index+"','"+datagridId+"','"+source+"','"+statusV+"');\">修改</a>&nbsp;&nbsp;";
                    	}else{
                     		content +="权限不足";
                    	}
                    	return content;
                    }
                }
            ]] 
    });
}
//点击商品库存   加载库存列表
function loadStockGoodsAbout(datagridId,goodsId,source){
	$('#'+datagridId).datagrid({
        width:"100%",
        url : ctx + '/application/goods/stockinfo/pagelist?goodsId='+goodsId,
        idField:'id',
        pagination : true,
		rownumbers : true,
        columns: [[{
        	field: 'stockLogo', 
        	title: 'logo', 
        	width: 210,
        	align : 'center',
        	formatter : function(value, row, index) {
        		if(value !=null){
        			var content = "";
        			content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.alertPic('"
        				+ row.stockLogo + "');\">"+row.stockLogo+"</a>&nbsp;&nbsp;";
        			return content;
        		}
        	}
        },{
        	field: 'id', title: '库存id', width: 70,align : 'center',hidden:'hidden'
		},{
			field: 'goodsSkuAttr', title: '库存规格', width: 80,align : 'center'
		},{
			field: 'goodsCostPrice',
			title: '成本价格', 
			width: 70,
			align : 'center',
            formatter:function(value,row,index){// 授权标示
             	var goodCostpriceIf=$('#goodCostpriceIf').val();
             	if(goodCostpriceIf=='permission'){
            		return value;
            	} 
        		return "未授权";
            }
		},{
			field: 'goodsPrice', title: '商品售价', width: 70,align : 'center'
		},{
			field: 'stockTotalAmt', title: '库存总量', width: 70,align : 'center'
		},{
			field: 'stockCurrAmt', title: '库存剩余', width: 70,align : 'center'
		},{
			field : 'opt',  
			title : '操作', 
			width : 80,
			align : 'center',
            formatter : function(value, row, index) {// 授权标示
             	var grantedAuthority=$('#grantedAuthority').val();
            	var content = "";
            	if(grantedAuthority=='permission'){
            		if(source != 'jd'){
            			content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.updateStockinfo('"+row.id+"','"+row.stockTotalAmt+"','"+row.stockCurrAmt+"');\">追加库存</a>&nbsp;&nbsp;";
            		}
            	}else{
             		content +="权限不足";
            	}
            	return content;
            }
        }]] 
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
	//$('#addgoodsSkuType').combobox('setValue','');
	$('#addUnSupportProvince').combobox('setValue','');
	$("#sordNo").numberbox('setValue','');
}

//编辑商品初始化商品信息
function initEditGoodsInfo(row){
	var unSupportPrivinces = row.unSupportProvince;//省份汉字
	var unSupportPrivincesCodes;//省份编码 
	$.ajax({
		url : ctx + '/application/nation/v1/queryCode',
		data : {unSupportPrivinces:unSupportPrivinces},
		type : "get",
		dataType : "text",
		success : function(resp) {
			unSupportPrivincesCodes = resp;
			$("#editUnSupportProvince").combobox({
				url:ctx + "/application/nation/v1/queryNations", //后台获取所有省  
				method:'get',  
				panelHeight:200,//设置为固定高度，combobox出现竖直滚动条  
				valueField:'code',  
				textField:'province',  
				multiple:true,  
				formatter: function (row) { //formatter方法就是实现了在每个下拉选项前面增加checkbox框的方法  
					var opts = $(this).combobox('options');  
					return '<input type="checkbox" class="combobox-checkbox">' + row[opts.textField]  ;
				},
				onLoadSuccess: function () {  //下拉框数据加载成功调用  
					var opts = $(this).combobox('options');  
					console.log(opts);
					var target = this;  
					var values = $(target).combobox('getValues');//获取选中的值的values
					if(unSupportPrivincesCodes != null && unSupportPrivincesCodes != ''){
						$.each(unSupportPrivincesCodes.split(","),function(index,value){
							var el = opts.finder.getEl(target, value);  
							el.find('input.combobox-checkbox')._propAttr('checked', true);   
						});
					}
				},  
				onSelect: function (row) { //选中一个选项时调用  
					var opts = $(this).combobox('options');  
					//获取选中的值的values  
					var val = $(this).combobox('getValues');
					if(!val[0]){
						val.shift();
					}
					$("#editUnSupportProvince").combobox('setValues',val);  
					
					//设置选中值所对应的复选框为选中状态  
					var el = opts.finder.getEl(this, row[opts.valueField]);  
					el.find('input.combobox-checkbox')._propAttr('checked', true);  
				}, 
				onUnselect: function (row) {//不选中一个选项时调用  
					var opts = $(this).combobox('options');  
					var el = opts.finder.getEl(this, row[opts.valueField]);  
					el.find('input.combobox-checkbox')._propAttr('checked', false);  
				}  
			});
			
			if(externalsource == 'jd'){
				$("#editmerchantCode").textbox('textbox').attr("disabled","disabled");
				$("#editgoodsModel").textbox('textbox').attr("disabled","disabled");
				$("#editgoodsName").textbox('textbox').attr("data-options","");
				//$("#editgoodsSkuType").combobox('disable');
				$("#editUnSupportProvince").combobox('disable');
				$("#editproDate").next("span").children(".validatebox-text").attr("disabled","disabled");
				$("#editproDate").next("span").children(".textbox-addon-right").children("a").addClass("textbox-icon-disabled");
				$("#editkeepDate").next("span").children(".validatebox-text").attr("disabled","disabled");
				$("#editkeepDate").next("span").children(".textbox-addon-right").children("a").addClass("textbox-icon-disabled");
				$("#editsupNo").textbox('textbox').attr("disabled","disabled");
				$("#editsordNo").next("span").children(".validatebox-text").attr("disabled","disabled");
				// $("input[name='editSupport7dRefund'][value='Y']").attr("checked", "checked");
			}else{
				$("#editgoodsModel").textbox('textbox').removeAttr("disabled");
				//$("#editgoodsSkuType").combobox('enable');
				$("#editUnSupportProvince").combobox('enable');
				$("#editproDate").next("span").children(".validatebox-text").removeAttr("disabled");
				$("#editproDate").next("span").children(".textbox-addon-right").children("a").removeClass("textbox-icon-disabled");
				$("#editkeepDate").next("span").children(".validatebox-text").removeAttr("disabled");
				$("#editkeepDate").next("span").children(".textbox-addon-right").children("a").removeClass("textbox-icon-disabled");
				$("#editsupNo").textbox('textbox').removeAttr("disabled");
				$("#editsordNo").next("span").children(".validatebox-text").removeAttr("disabled");

			}
			if(support7dRefundFinal != null && support7dRefundFinal != ""){
				$("input[type=radio][name=editSupport7dRefund]").each(function() {
					if ($(this).val() == support7dRefundFinal) {
						$(this).attr("checked", "checked");
					}
				});
			}

			$("#editid").val(row.id);
			$("#editLogogoodsId").val(row.id);
			
			$("#editmerchantCode").textbox('setValue',row.merchantCode);
			$("#editgoodsModel").textbox('setValue',row.goodsModel);
			$("#editgoodsName").textbox('setValue',row.goodsName);
			$("#editgoodsTitle").textbox('setValue',row.goodsTitle);
			//$("#editgoodsSkuType").combobox('setValue',row.goodsSkuType);
			if(unSupportPrivincesCodes != null && unSupportPrivincesCodes != ''){
				$("#editUnSupportProvince").combobox('setValues',unSupportPrivincesCodes.split(","));
			}
			if(null==row.listTime || ""==row.listTime){
				$("#editlistTime").datetimebox('setValue',"");
			}else{
				$("#editlistTime").datetimebox('setValue',new Date(row.listTime).Format("yyyy-MM-dd hh:mm:ss")); 
			}
			if(null==row.delistTime || ""==row.delistTime){
				$("#editdelistTime").datetimebox('setValue',"");
			}else{
				$("#editdelistTime").datetimebox('setValue',new Date(row.delistTime).Format("yyyy-MM-dd hh:mm:ss"));
			}
			if(null==row.proDate || ""==row.proDate){
				$("#editproDate").datebox('setValue',"");
			}else{
				$("#editproDate").datebox('setValue',new Date(row.proDate).Format("yyyy-MM-dd")); 
			}
			$("#editsupNo").textbox('setValue',row.supNo);
			$("#editkeepDate").textbox('setValue',row.keepDate);
			$("#editsordNo").numberbox('setValue',row.sordNo);
			// $("input[name='editSupport7dRefund'][value='Y']").attr("disabled",true);
			
		}
	});
	
}

/** 回显图片* */
function loadLogo (id,picUrl)
{
	// debugger;
	if (picUrl != null)
	{
		$ ("#"+id).attr ("src","");
		$ ("#"+id).attr ("src", ctx + "/fileView/query?picUrl=" + picUrl);
        $("#"+id).css('display','block');
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
		 ] ],
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
			// debugger;
 			var indexS;
		 	$.each(data.rows, function(i, n){
	 		  if(data.rows[i].categoryId == editCategoryId3){
	 			  indexS = i;
	 		  }
	 		});
		    $('#editEastAttrDataGrid').datagrid('selectRow',indexS);
		 }, 
		 onClickRow: function (index, row) {
			 // debugger;
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
function htmlspecialchars (string) {  
    string = string.replace(/&/g, '&amp;');//这个就是双引号  
    string = string.replace(/"/g, '"');  
    string = string.replace(/'/g, "'");  
    string = string.replace(/</g, '<');  
    string = string.replace(/>/g, '>');  
    return string;  
}
var goodsAttrId = null;
var attrid;
var categorynameArr = [];
var categorynameArr1 = [];//单纯保存input内容
var categorynameArr2 = [];
var categorynameArr3 = [];
var categorynameArrX = [];//保存input内容-属性ID   该属性下属所有规格最多十个
var categorynameArrY = [];
var categorynameArrZ = [];
function dropdown(selectId,type){
	if(type=="add"){
		var cate = categoryId1;
	}else if (type=="edit"){
		var cate = editCategoryId1;
	}
	//下拉框
	$('#'+selectId).combobox({//categoryId1 是新增商品时一级类目ID，     后面的不需要了 editCategoryId1是修改商品时一级目录ID
		method: "get",
        url: ctx + '/application/goods/management/goodAttrListByCategory?categoryId1='+cate,   
        panelHeight: '100',  
        valueField: 'id',
        textField: 'name',
        onChange: function (n, o) {
        	for(var a=0;a<catename.length;a++){//新值重复判定
        		if(catename[a]==n){
        			$.messager.alert("提示", "请勿重复选择商品属性！", "info");
        			$('#'+selectId).combobox('clear');
        			break;
        		}
        	}
        	var catenamenew = [];
        	for(var a=0;a<catename.length;a++){//旧值去除判定
        		if(catename[a]!=o&&catename[a]!=n){
        			catenamenew.push(catename[a]);
        		}
        	}
        	goodsAttrId = n;
    		catenamenew.push(goodsAttrId);
    		catename = [];
    		for(var a=0;a<catenamenew.length;a++){//空字符串去除判定
        		if(catenamenew[a]!=null&&catenamenew[a]!=""){
        			catename.push(catenamenew[a]);
        		}
        	}
        }
	});
}
function buttonclick(id){//删除本条属性下属十个规格，刷新表格
	if(catenum==1){
		$.messager.alert("提示", "库存列表唯一属性不可删除！", "info");
		return;
	}
	var attnum = id.split("+")[0];
	if(catenum>0){
		catenum--;
	}
	var child = document.getElementById(id);
	var childfir = child.parentNode.childNodes[0];
	var childVlaue = $('#'+childfir.id).combobox('getValue');
	child.parentNode.parentNode.remove();
	var catenameNew = new Array();
	for(var b = 0;b<catename.length;b++){
		if(catename[b]!=childVlaue){
			catenameNew.push(catename[b]);
		}
	}
	catename=[];
	catename = catenameNew;
	if(attnum==0){
		categorynameArr1=[];
		categorynameArrX=[];
	}
	if(attnum==1){
		categorynameArr2=[];
		categorynameArrY=[];
	}
	if(attnum==2){
		categorynameArr3=[];
		categorynameArrZ=[];
	}
	flushAttrListPrepare(categorynameArr1,categorynameArr2,categorynameArr3);
}
function createTableByCate(value,id){//根据每个属性  十条规格   失焦事件  刷新表格
	var attnum = id.split("+")[0];//第几个（属性和input）框  0 1 2
	var child = document.getElementById(id);//本次时间触发对象input
	var childarr = child.parentNode.parentNode.childNodes;//总共几个（属性和input）框 对象集合
	var	childmy = childarr[0];
	var childmyatt = childmy.childNodes[0];//本input归属属性下拉框
	var childattVlaue = $('#'+childmyatt.id).combobox('getValue');//本input归属属性下拉框ID
	var reg = /^[1-9]\d*$/;
	if(!reg.test(childattVlaue)||childattVlaue==0){
		$.messager.alert("提示", "请先选择商品属性！", "info");
		child.value="";
		return;
	}
	var childbroarr = child.parentNode.childNodes;//本节点兄弟input对象集合
	var valuearr=[];
	var valueidarr=[];
	for(var i=0;i<childbroarr.length;i++){
		var val = childbroarr[i].value;
		if(typeof(val)!="undefined"&&val!=null&&val!=''){
			valuearr.push(childbroarr[i].value);
			valueidarr.push(val+"-"+childattVlaue);
		}
	}
	if(attnum==0){
		categorynameArr1=[];
		categorynameArrX=[];
		categorynameArr1=valuearr;
		categorynameArrX=valueidarr;
	}
	if(attnum==1){
		categorynameArr2=[];
		categorynameArrY=[];
		categorynameArr2=valuearr;
		categorynameArrY=valueidarr;
	}
	if(attnum==2){
		categorynameArr3=[];
		categorynameArrZ=[];
		categorynameArr3=valuearr;
		categorynameArrZ=valueidarr;
	}
	flushAttrListPrepare(categorynameArr1,categorynameArr2,categorynameArr3);
}
function flushAttrListPrepare(value1,value2,value3){//刷新表格
	var params = {};
    params['categoryname1'] = value1.toString();
    params['categoryname2'] = value2.toString();
    params['categoryname3'] = value3.toString();
    $('#tableattrlist').datagrid('load', params);
}
function flushAttrList(){//表格   刷新       弹框 后才刷新 
	$('#tableattrlist').datagrid({
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        striped:true,
        columns:[[{
        	title : '属性规格',
            field : 'attrnameByAfter',
            width : 100,
            align : 'center'
        },{
            field : 'stockLogo',
            hidden: 'hidden'
        },{
        	title : '成本价',
            field : 'goodsCostPrice',
            width : 100,
            align : 'center',
        },{
        	title : '售价',
            field : 'goodsPrice',
            width : 100,
            align : 'center',
        },{
        	title : '库存总量',
            field : 'stockTotalAmt',
            width : 100,
            align : 'center',
        },{
        	title : '库存剩余',
            field : 'stockAmt',
            width : 100,
            align : 'center',//库存剩余删除可编辑
        },{
        	field:'action',
        	title:'操作',
        	width:100,
        	align:'center',
			formatter:function(value,row,index){
				return '<a href="#" onclick="editrowForAdd(this)">新增</a> ';
			}
        }]],
        loader : function(param, success, error) {//LOADING DATA
            $.ajax({
                url : ctx + '/application/goods/management/tableattrlist',
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
var addindex;
function updateActions(index){//可编辑动态表格
	addindex = index;
	$("#goodsCostPrice").textbox('clear');
	$("#goodsPrice").textbox('clear');
	$("#stockTotalAmt").textbox('clear');
	$("#stockLogoFile").val('');
	
	$('#addGoodsStockInfoWin').window({modal: true});
	$('#addGoodsStockInfoWin').window('open');
}
function editrowForAdd(target){//可编辑动态表格
	updateActions(getRowIndex(target));
}
function getRowIndex(target){//可编辑动态表格
	var tr = $(target).closest('tr.datagrid-row');
	return parseInt(tr.attr('datagrid-row-index'));
}
/*修改库存细信息相关*/
function flushGoodsStock(finalGoodId){
	var param = {};
	param['goodsId'] = finalGoodId;
	$.ajax({url : ctx + '/application/goods/management/findGoodsCateAttrAndStockForEdit',type : "post",data : param,
		success : function(data) {
			if(data.msg=='success'){
				var da = data.data;
				var attrVal1 = da.attrVal1;
				var attrVal2 = da.attrVal2;
				var attrVal3 = da.attrVal3;
				var attr1 = da.attr1;
				var attr2 = da.attr2;
				var attr3 = da.attr3;
				var str = "";
				if(typeof(attrVal1)!='undefined'&&attrVal1!=null&&attrVal1.length>0){
					str+='<div style = "margin-left: 20px;margin-top: 10px;text-align: -webkit-left;width: 550px">';
					str+='<input class="easyui-combobox" style="width:95px;"  value='+attr1.name+' disabled="true">';
					str+='</div>';
					str+='<div style = "margin-left: 20px;margin-top: 10px;text-align: -webkit-left;width: 550px">';
					var attrId1 = attrVal1[0].attrId;
					for(var i = 0;i<10;i++){
						var en = attrVal1[i];
						var id = 1+"+"+i+"goodsAttrIdEd"+attrId1;
						if(typeof(en)!='undefined'){
							var val = en.attrVal;
							id+="Ed";
							id+=en.id;
						}else{
							var val = "";
						}
						str+='<input style="width:100px" onblur="createTableByCateEdit(this.value,this.id,'+finalGoodId+')" id='+id+' name='+id+' value='+val+'>'
					}
					str+='</div>';
				}
				if(typeof(attrVal2)!='undefined'&&attrVal2!=null&&attrVal2.length>0){
					str+='<div style = "margin-left: 20px;margin-top: 10px;text-align: -webkit-left;width: 550px">';
					str+='<input class="easyui-combobox" style="width:95px;"  value='+attr2.name+' disabled="true">';
					str+='</div>';
					str+='<div style = "margin-left: 20px;margin-top: 10px;text-align: -webkit-left;width: 550px">';
					var attrId2 = attrVal2[0].attrId;
					for(var i = 0;i<10;i++){
						var en = attrVal2[i];
						var id = 2+"+"+i+"goodsAttrIdEd"+attrId2;
						if(typeof(en)!='undefined'){
							var val = en.attrVal;
							id+="Ed";
							id+=en.id;
						}else{
							var val = "";
						}
						str+='<input style="width:100px" onblur="createTableByCateEdit(this.value,this.id,'+finalGoodId+')" id='+id+' name='+id+' value='+val+'>'
					}
					str+='</div>';
				}
				if(typeof(attrVal3)!='undefined'&&attrVal3!=null&&attrVal3.length>0){
					str+='<div style = "margin-left: 20px;margin-top: 10px;text-align: -webkit-left;width: 550px">';
					str+='<input class="easyui-combobox" style="width:95px;"  value='+attr3.name+' disabled="true">';
					str+='</div>';
					str+='<div style = "margin-left: 20px;margin-top: 10px;text-align: -webkit-left;width: 550px">';
					var attrId3 = attrVal3[0].attrId;
					for(var i = 0;i<10;i++){
						var en = attrVal3[i];
						var id = 3+"+"+i+"goodsAttrIdEd"+attrId3;
						if(typeof(en)!='undefined'){
							var val = en.attrVal;
							id+="Ed";
							id+=en.id;
						}else{
							var val = "";
						}
						str+='<input style="width:100px" onblur="createTableByCateEdit(this.value,this.id,'+finalGoodId+')" id='+id+' name='+id+' value='+val+'>'
					}
					str+='</div>';
				}
				$('#inputDivEdit').empty();
				$('#inputDivEdit').append(str);
			}
		}
	});
	$('#tableattrEditlist').datagrid('load', param);
}
function createTableByCateEdit(value,id,goodsId){//input失焦直接save ATTRVAL
	var attrId = id.split("Ed")[1];
	var attrValId = id.split("Ed")[2];
	var param = {};
	param["attrValId"]=attrValId;
	param["attrId"]=attrId;
	param["attrVal"]=value;
	param["goodsId"]=goodsId;
	$.ajax({url : ctx + '/application/goods/management/createTableByCateEdit',data : param, type : "post",dataType : "json",
        success : function(data) {
        	if(data.status==1){
        		//此时刷新表格  tableattrEditlist
            	var attnum = id.split("+")[0];
            	var inp = document.getElementById(id);
        		var p = inp.parentNode.parentNode.children;
        		for(var i =0,pl= p.length;i<pl;i++) {
        			var q = p[i].children;
        			if(i/1%2==0){
        				continue;
        			}
        			if(i==1){
        				var categorynameArr1=[];
    					var categorynameArrX=[];
    					attrId = q[0].id.split("Ed")[1];
        				for(var j =0,ql= q.length;j<ql;j++) {
        					if(typeof(q[j].value)!="undefined"&&q[j].value!=null&&q[j].value!=''){
        						categorynameArr1.push(q[j].value);
            					categorynameArrX.push(q[j].value+"-"+attrId);
        					}
        				}
        				continue;
        			}
        			if(i==3){
        				var categorynameArr2=[];
    					var categorynameArrY=[];
    					attrId = q[0].id.split("Ed")[1];
        				for(var j =0,ql= q.length;j<ql;j++) {
        					if(typeof(q[j].value)!="undefined"&&q[j].value!=null&&q[j].value!=''){
        						categorynameArr2.push(q[j].value);
            					categorynameArrY.push(q[j].value+"-"+attrId);
        					}
        				}
        				continue;
        			}
        			if(i==5){
        				var categorynameArr3=[];
    					var categorynameArrZ=[];
    					attrId = q[0].id.split("Ed")[1];
        				for(var j =0,ql= q.length;j<ql;j++) {
        					if(typeof(q[j].value)!="undefined"&&q[j].value!=null&&q[j].value!=''){
        						categorynameArr3.push(q[j].value);
            					categorynameArrZ.push(q[j].value+"-"+attrId);
        					}
        				}
        				continue;
        			}
        		}
            	flushGoodsStock();
            	flushtableattrEditlistEdit(goodsId,categorynameArr1,categorynameArr2,categorynameArr3);
            	if(value==""){//该规格被删除动态修改该INPUT的ID
            		var s = id.split("Ed");
            		document.getElementById(id).id=s[0]+"Ed"+s[1];
            	}
            	if(data.data!=""){//该规格被保存入数据库动态修改该INPUT的ID
            		document.getElementById(id).id=id+"Ed"+data.data;
            	}
        	}else{
        		document.getElementById(id).value="";
        		$.messager.alert("提示", "商品属性规格名称验重失败，规格名称不可用！", "info");
        	}
        },
    })
}
function flushtableattrEditlistEdit(finalGoodId,value1,value2,value3){
	var param = {};
	param["goodsId"]=finalGoodId;
	if(value1!=null){
		param["categorynameArr1"]=value1.toString();
	}
	if(value2!=null){
		param["categorynameArr2"]=value2.toString();
	}
	if(value3!=null){
		param["categorynameArr3"]=value3.toString();
	}
	$('#tableattrEditlist').datagrid('load', param);
}
function flushtableattrEditlist(){
	$('#tableattrEditlist').datagrid({
		rownumbers : true,
        pagination : true,
        singleSelect : true,
        striped:true,
        fitColumns : true,
        columns:[[{
        	title : '属性规格',
            field : 'goodsSkuAttr',
            width : 100,
            align : 'center'
        },{
            field : 'stockLogo',
            hidden: 'hidden'
        },{
            field : 'id',
            hidden: 'hidden'
        },{
        	title : '成本价',
            field : 'goodsCostPrice',
            width : 100,
            align : 'center',
        },{
        	title : '售价',
            field : 'goodsPrice',
            width : 100,
            align : 'center',
        },{
        	title : '库存总量',
            field : 'stockTotalAmt',
            width : 100,
            align : 'center',
        },{
        	title : '库存剩余',
            field : 'stockCurrAmt',
            width : 100,
            align : 'center',
        },{
        	field:'action',
        	title:'操作',
        	width:100,
        	align:'center',
			formatter:function(value,row,index){
				if (row.id!=null){
					return '<a href="#" onclick="editrowForEdit1(this)">修改</a> ';
				} else {
					return '<a href="#" onclick="editrowForEdit2(this)">新增</a> ';
				}
			}
        }]],
        loader : function(param, success, error) {//LOADING DATA
            $.ajax({
                url : ctx + '/application/goods/management/flushtableattrEditlist',
                data : param,
                type : "post",
                dataType : "json",
                success : function(data) {
                    $.validateResponse(data, function() {
                        success(data);
                    });
                }
            })
        },
	});
}
var editindex;
function editrowForEdit1(target){
	editindex = getRowIndexE(target);
	var rows = $('#tableattrEditlist').datagrid('getRows');
	var r = rows[editindex];
	$("#editstockinfoId").val(r.id);
	$("#editStockinfoIdInForm").val(r.id);
	$("#editgoodsSkuAttr").textbox('setValue',r.goodsSkuAttr);
	$("#editgoodsCostPrice").textbox('setValue',FormatAfterDotNumber(r.goodsCostPrice,2));
	$("#editgoodsPrice").textbox('setValue',FormatAfterDotNumber(r.goodsPrice,2));
	$("#editstockTotalAmt").textbox('setValue',r.stockTotalAmt);
	$("#editStockGoodsLogoImg").attr("src",ctx + "/fileView/query?picUrl=" + r.stockLogo);
	$("#editStockLogoUrl").val(r.stockLogo);
	$('#editGoodsStockInfoWin').window({modal: true});
	$('#editGoodsStockInfoWin').window('open');
}
function editrowForEdit2(target){
	editindex = getRowIndexE(target);
	$("#editaddgoodsCostPrice").textbox('clear');
	$("#editaddgoodsPrice").textbox('clear');
	$("#editaddstockTotalAmt").textbox('clear');
	$("#editaddstockLogoFile").val('');
	
	$('#editaddGoodsStockInfoWin').window({modal: true});
	$('#editaddGoodsStockInfoWin').window('open');
}
function getRowIndexE(target){//可编辑动态表格
	var tr = $(target).closest('tr.datagrid-row');
	return parseInt(tr.attr('datagrid-row-index'));
}
/*=================================修改商品类目修改  此时逻辑与  新增商品库存的逻辑完全一致  只是页面DIV不同重写一遍。=====================================*/
function function1(){
	if(catenum>2){
		$.messager.alert("提示", "商品属性最多选择3个！", "info");
		return;
	}
	var divid = "goodsAttrId"+catenum;
	var selectnum = "selectnum"+catenum;
	var buttonid = catenum+"+buttonId";
	var str ='<div id ='+divid+'>';
		str+='<div style="margin-left: 20px;margin-top: 10px;text-align: -webkit-left;">'
			str+='<input class="easyui-combobox" style="width:95px"  id='+selectnum+' name="属性" value="请选择商品属性" editable="false">';
			str+="&nbsp;&nbsp;"
			str+='<input  type = "button" id = '+buttonid+' value = "删除" style="width:40px;height: 20px;color : blue" onclick ="function5(this.id)"/>';
//			str+='<a href="javascript:void(0);" id = '+buttonid+' class="easyui-linkbutton function5">删除</a>'
		str+='</div>';
		str+='<div style = "margin-left: 20px;margin-top: 10px;text-align: -webkit-left;width:550px;">';
		for(var i = 0;i<10;i++){
			id = catenum+"+"+i+"goodsAttrId";
			str+='<input style="width:100px" onblur="function3(this.value,this.id)" id='+id+' name='+id+'/>'
		}
		str+='</div>';
	str+='</div>';
	$('#inputDivEdit').append(str);
	dropdown(selectnum,"edit");
	catenum++;
}
function function2(){
	var ids = [];
    var rows = $('#tableattrEditlist').datagrid('getRows');
    for(var i=0; i<rows.length; i++){
    	var d = i+1;
    	if(rows[i].editing==true){
    		$.messager.alert("提示", "第"+d+"行表格未保存，不可提交！", "info");
			return;
    	}
    	if(rows[i].stockTotalAmt<rows[i].stockCurrAmt){
    		$.messager.alert("提示", "第"+d+"行表格保存错误，库存总量小于剩余库存，不可提交！", "info");
			return;
    	}
        ids.push(rows[i]);
    }
    var param = {};
    param['categorynameArr1']= categorynameArrX.toString();
    param['categorynameArr2']= categorynameArrY.toString();
    param['categorynameArr3']= categorynameArrZ.toString();
    param["goodsStock"]= JSON.stringify(ids);
    param["goodsId"]= finalGoodId;
    param['status'] = "editstatusaddmethod";
	var address = ctx + '/application/goods/management/saveGoodsCateAttrAndStock';
	$.ajax({
		url : address,
		type : "POST",
		data : param,
		success : function(data) {
			//刷新前置页面库存列表（无需刷新）
			//关闭本窗口 无需关闭
			$.messager.alert("提示", data.msg, "info");
			if(data.status==1){
				$('#editGoodsInfo').window('close');
			}
		}
	});
}
function function3(value,id){//根据每个属性  十条规格   失焦事件  刷新表格
	var attnum = id.split("+")[0];//第几个（属性和input）框  0 1 2
	var child = document.getElementById(id);//本次时间触发对象input
	var childarr = child.parentNode.parentNode.childNodes;//总共几个（属性和input）框 对象集合
	var	childmy = childarr[0];
	var childmyatt = childmy.childNodes[0];//本input归属属性下拉框
	var childattVlaue = $('#'+childmyatt.id).combobox('getValue');//本input归属属性下拉框ID
	var reg = /^[1-9]\d*$/;
	if(!reg.test(childattVlaue)||childattVlaue==0){
		$.messager.alert("提示", "请先选择商品属性！", "info");
		child.value="";
		return;
	}
	var childbroarr = child.parentNode.childNodes;//本节点兄弟input对象集合
	var valuearr=[];
	var valueidarr=[];
	for(var i=0;i<childbroarr.length;i++){
		var val = childbroarr[i].value;
		if(typeof(val)!="undefined"&&val!=null&&val!=''){
			valuearr.push(childbroarr[i].value);
			valueidarr.push(val+"-"+childattVlaue);
		}
	}
	if(attnum==0){
		categorynameArr1=[];
		categorynameArrX=[];
		categorynameArr1=valuearr;
		categorynameArrX=valueidarr;
	}
	if(attnum==1){
		categorynameArr2=[];
		categorynameArrY=[];
		categorynameArr2=valuearr;
		categorynameArrY=valueidarr;
	}
	if(attnum==2){
		categorynameArr3=[];
		categorynameArrZ=[];
		categorynameArr3=valuearr;
		categorynameArrZ=valueidarr;
	}
	function4(categorynameArr1,categorynameArr2,categorynameArr3);
}
function function4(value1,value2,value3){
	var params = {};
    params['categoryname1'] = value1.toString();
    params['categoryname2'] = value2.toString();
    params['categoryname3'] = value3.toString();
    params['status'] = "editstatusaddmethod";
    $('#tableattrEditlist').datagrid('load', params);
}
function function5(id){//删除本条属性下属十个规格，刷新表格
	if(catenum==1){
		$.messager.alert("提示", "库存列表唯一属性不可删除！", "info");
		return;
	}
	var attnum = id.split("+")[0];
	if(catenum>0){
		catenum--;
	}
	var child = document.getElementById(id);
	var childfir = child.parentNode.childNodes[0];
	var childVlaue = $('#'+childfir.id).combobox('getValue');
	child.parentNode.parentNode.remove();
	var catenameNew = new Array();
	for(var b = 0;b<catename.length;b++){
		if(catename[b]!=childVlaue){
			catenameNew.push(catename[b]);
		}
	}
	catename=[];
	catename = catenameNew;
	if(attnum==0){
		categorynameArr1=[];
		categorynameArrX=[];
	}
	if(attnum==1){
		categorynameArr2=[];
		categorynameArrY=[];
	}
	if(attnum==2){
		categorynameArr3=[];
		categorynameArrZ=[];
	}
	function4(categorynameArr1,categorynameArr2,categorynameArr3);
}