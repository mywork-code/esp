/**
 *  - info
 */
$(function() {
	
	$('#checkActivityWin').window('close');
	
	//初始化
    $('#tablelist').datagrid({
        title : '活动列表',
        fit : true,
        //fitColumns : true,
        rownumbers : true,
        pagination : true,
        singleSelect: false, //允许选择多行  
        selectOnCheck: true,   
        checkOnSelect: false,
        striped:true,
        toolbar : '#tb',
        columns : [[ { field: 'ck', checkbox: true, width: '30' },  //复选框 
                    {
                    title : 'ID',
                    hidden:true,
                    field : 'id',
                    width : 50,
                    align : 'center'
                }, {
                    title : '商户',
                    field : 'merchantCode',
                    width : 80,
                    align : 'center'
                }, {
                    title : '商品名称',
                    field : 'goodsName',
                    width : 90,
                    align : 'center'
                },{  
       		 		title : '三级类目 id',  
       		 		field : 'categoryId3', 
       		 	    width : 90,  
       		 		align : 'center'
       		 	}, {
                    title : '商品型号',
                    field : 'goodsModel',
                    width : 80,
                    align : 'center'
                }, {
                    title : '商品规格',
                    field : 'goodsSkuType',
                    width : 80,
                    align : 'center'
                }, {
                	field: 'statusDesc', 
                	title: '活动状态', 
                	width: 80,
                	align : 'center',
//                    styler: function(value,row,index){
//       			     return 'color:red;';
//       			    }
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
                	field: 'aStartDate', 
                	title: '活动开始时间', 
                	width: 140,
                	align : 'center',
                    formatter:function(value,row,index){
                    	return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                    }
                },{
                	field: 'aEndDate', 
                	title: '活动结束时间', 
                	width: 140,
                	align : 'center',
                    formatter:function(value,row,index){
                    	return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                    }
                }, {
                	field: 'pDiscountRate', 
                	title: '价格折扣率', 
                	width: 80,
                	align : 'center',
                	 formatter:function(value,row,index){
                		 if(value!=null){
                			 return (value*100).toFixed(2) + "%";
                		 }
                     }
                }, {
                    title : '操作',
                    field : 'opt',
                    width : 80,
                    align : 'center',
                    formatter : function(value, row, index) {
                    	 var content = "";
                    	 if(row.status=='0'){
                    		 content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.checkOneActivity("
                                 + row.id + ");\">审核</a>&nbsp;&nbsp;";  
                    	 }
                    	 return content;
                    }
                }]],

	        loader : function(param, success, error) {
	            $.ajax({
	                url : ctx + '/application/activity/management/activityAndGoodsPageList',
	                data : param,
	                type : "post",
	                dataType : "json",
	                success : function(data) {
	                	debugger;
	                	console.log(data);
	                    $.validateResponse(data, function() {
	                        success(data);
	                    });
	                }
	            })
	        }
    });
    // 查询列表
    $(".search-btn").click(function() {
        var params = {};
        params['goodsName'] = $("#goodsNames").textbox('getValue');
        params['status'] = $("#status").textbox('getValue');
        params['merchantCode'] = $("#selectMerchant").textbox('getValue');
        $('#tablelist').datagrid('load', params);
    });
	//重置
	$("#reset").click(function(){
		$('#status').combobox('setValue','');
		$('#selectMerchant').combobox('setValue','');
		$("#goodsNames").textbox('clear');
		var params={};
		$("#tablelist").datagrid('load',params);
	});
	
	//单个活动审核
	$.checkOneActivity = function(id) {
		var ids=[];
		ids.push(id);
		batchDeal(ids);
	};
	
	// 批量复核
    $(".checkAll").click(function() {
      debugger;
	  var selRow = $('#tablelist').datagrid('getChecked')  
		if(selRow.length==0){  
			$.extend($.messager.defaults,{ok:"确定",cancel:"取消"});
			$.messager.alert("提示", "至少勾选一条数据！", "info");  
			return ;  
		}else{
			var ids=[];  
			for (var i = 0; i < selRow.length; i++) {
				if(selRow[i].status=='0'){
					var id=selRow[i].id;     
					ids.push(id);
				}
		      }  
			if(ids.length == 0){
			$.messager.alert("提示", "至少勾选一条待复核数据！", "info");  
			}else{
				batchDeal(ids);
			}
		}
    });
    
    //批量审核活动
    function batchDeal(ids){
    	debugger;
    	$.extend($.messager.defaults,{ok:"通过",cancel:"拒绝"});
//    	$.messager.defaults = {ok: "是", cancel: "否"};  
		$.messager.confirm('提示框', '你确定要批量复核吗?',function(r){
	     var params = {};
		if(r){
			params['status'] = "1";
		}else{
			params['status'] = "-1";
		}
	     params['ids'] = JSON.stringify(ids);
    	  $.ajax({
				type : "POST",
				url : ctx + '/application/activity/management/editAllActivity',
				data : params,
				success : function(data) {
					 $(".search-btn").click();
				}
			});
		});
    }

	$("#selectMerchant").combobox({
		valueField : 'merchantCode',
		textField : 'merchantName',
		onBeforeLoad : function(param) {
		},
		loader : function(param, success, error) {
			$.ajax({
				url : ctx + '/application/rbac/user/merchantList',
				data : param,
				async : false,
				type : "get",
				dataType : "json",
				success : function(data) {
					console.log(data);
					$.validateResponse(data, function() {
						success(data.data);
					});
					// 回调赋值
					//$("#selectMerchant").combobox('setValue', merchantCode);
				}
			})
		}
	});
	
	// 导出活动推荐
	$ (".export-btn").click (function (){
		$.extend($.messager.defaults,{ok:"确定",cancel:"取消"});
		$.messager.confirm ('活动推荐', '确定要导出吗？', function (r)
		{
			if (r)
			{
				debugger;
				var params = {};
				params['merchantCode'] = $("#selectMerchant").combobox('getValue');
				params['goodsNames'] = $ ("#goodsNames").textbox ('getValue');
				params['status'] = $ ("#status").combobox ('getValue');
				params['isAll'] = 't';// t: 是 f: 否 是否导出全部订单信息
				params['busCode'] = 'E003';// 订单导出code
				exportFile ("tablelist", "活动推荐", params);
			}
		});
		
	});
});

