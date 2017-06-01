/**
 *  - info
 */
$(function() {
	//初始化
    $('#tablelist').datagrid({
        title : '商品列表',
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
                    field : '商品编号',
                    width : 50,
                    align : 'center'
                }, {
                    title : '商品型号',
                    field : 'goodsModel',
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
                    title : '商品大标题',
                    field : 'goodsTitle',
                    width : 90,
                    align : 'center'
                }, {
                    title : '商品类型',
                    field : 'goodsType',
                    width : 80,
                    align : 'center',
                    formatter : function(value, row, index) {
                        return value == '1' ? "正常" : "精选";
                    }
                }, {
                    title : '状态',
                    field : 'status',
                    width : 80,
                    align : 'center',
                    formatter : function(value, row, index) {
                    	if(value == 'G00'){
                    		return  '待上架';
                    	}
                    	if(value == 'G01'){
                    		return  '待审核';
                    	}
                    	if(value == 'G02'){
                    		return  '已上架';
                    	}
                    	if(value == 'G03'){
                    		return  '已下架';
                    	}
                    }
                },{
                    title : '商品上架时间',
                    field : 'listTime',
                    width : 140,
                    align : 'center',
                    formatter:function(value,row,index){
                    	if(value != null){
                    		return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                    	}
                    }
                }, {
                    title : '商品下架时间',
                    field : 'delistTime',
                    width : 140,
                    align : 'center',
                    formatter:function(value,row,index){
                    	if(value != null){
                    		return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                    	}
                    }
                },{
                    title : '商品生产日期',
                    field : 'proDate',
                    width : 140,
                    align : 'center',
                    formatter:function(value,row,index){
                    	if(value != null){
                    		return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                    	}
                    }
                },{
                    title : '商品保质期',
                    field : 'keepDate',
                    width : 80,
                    align : 'center',
                    formatter:function(value,row,index){
                    	if(value!=null){
                    		return value+'个月';
                    	}
                    }
                },{
                    title : '生产厂家',
                    field : 'supNo',
                    width : 100,
                    align : 'center'
                },{
                    title : '创建人',
                    field : 'createUser',
                    width : 80,
                    align : 'center'
                },{
                    title : '修改人',
                    field : 'updateUser',
                    width : 80,
                    align : 'center'
                },{
                    title : '创建时间',
                    field : 'createDate',
                    width : 140,
                    align : 'center',
                    formatter:function(value,row,index){
                    	if(value != null){
                    		return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                    	}
                    }
                },{
                    title : '修改时间',
                    field : 'updateDate',
                    width : 140,
                    align : 'center',
                    formatter:function(value,row,index){
                    	if(value != null){
                    		return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                    	}
                    }
                },{
                    title : '操作',
                    field : 'opt',
                    width : 80,
                    align : 'center',
                    formatter : function(value, row, index) {
                    	 var content = "";
                    	 content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.checkOne("
                             + row.id + ");\">添加</a>&nbsp;&nbsp;"; 
                    	 return content;
                    }
                }]],

        loader : function(param, success, error) {
        	debugger;
        	param['status']='G02';
            $.ajax({
                url : ctx + '/application/goods/management/pagelist',
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
    // 查询列表
    $(".search-btn").click(function() {
        var params = {};
        params['goodsName'] = $("#goodsNames").textbox('getValue');
        params['goodsType'] = $("#goodsTypes").textbox('getValue');
        params['status']='G02';//默认
        $('#tablelist').datagrid('load', params);
    });
    // 添加活动
    $(".checkAll").click(function() {
      debugger;
	  var selRow = $('#tablelist').datagrid('getChecked')  
		if(selRow.length==0){  
			 $.messager.alert("提示", "至少勾选一条数据！", "info");  
			return ;  
		}else{
			var ids=[];  
			for (var i = 0; i < selRow.length; i++) {
				var id=selRow[i].id;     
				ids.push(id); 
		      }  
			activityInfoinsert(ids,"商品批量设置活动");
		}
    });
    
	$.checkOne = function(id) {
		var arr = new Array();
		arr[0]=id;
		activityInfoinsert(arr,"商品单个设置活动");
    }
	
	function activityInfoinsert(ids,msgss){
		debugger;
		document.getElementById("activityFromlist").style.display="none";//隐藏  
		if(msgss=='商品单个设置活动'){
		document.getElementById("activityFromlist").style.display="";//显示 
			loandDatagrid(ids[0]);
		}
		  var handlesubmit = function(pDiscountRate,aStartDate,aEndDate,remark) {
		        $.ajax({
					type : "POST",
					url : ctx + "/application/activity/management/insert",
					data : {
			                "ids" : JSON.stringify(ids),
			                "pDiscountRate" : pDiscountRate,
			                "aStartDate" : aStartDate,
			                "aEndDate" : aEndDate,
			                "remark" : remark
			            },
					success : function(data) {
		            	$("#activityInfos").dialog("close");
	                    $(".search-btn").click();
					}
				});
		    }
		  
		  //清缓存
		  $("#activityInfoForm #pDiscountRate").textbox('clear');
		  $("#activityInfoForm #aStartDate").textbox('clear');
		  $("#activityInfoForm #aEndDate").textbox('clear');
		  $("#activityInfoForm #remark").textbox('clear');
	      
		   $("#activityInfos").dialog({
            modal : true,
            title : msgss,
            top:80,
            width : 520,
            buttons : [{
                        text : "确定",
                        handler : function() {
                        	debugger;
                        	var pDiscountRate = $("#pDiscountRate").textbox("getValue");
                        	var aStartDate = $("#aStartDate").textbox("getValue");
                        	var aEndDate = $("#aEndDate").textbox("getValue");
                        	var remark = $("#remark").textbox("getValue");
                         
                        	if($("#activityInfos #remark").val().length>100){
                        		$.messager.alert("提示", "备注不允许超过100字！", "info");  
                        		return;
                    		}
                        	if (null == pDiscountRate || ("") == pDiscountRate) {
                    			$.messager.alert("提示", "价格折扣率不能为空！", "info");
                    			return;
                    		}
                        	if (null == aStartDate || ("") == aStartDate) {
                    			$.messager.alert("提示", "开始时间不能为空！", "info");
                    			return;
                    		}
                        	if (null == aEndDate || ("") == aEndDate) {
                    			$.messager.alert("提示", "结束时间不能为空！", "info");
                    			return;
                    		}
                        	if(aStartDate>aEndDate){
                        		$.messager.alert("提示", "开始时间不能大于结束时间！", "info");
                    			return;
                        	}
                        	
                        	if(msgss=='商品单个设置活动'){
                        		//验证时间是否交叉
                        		 $.ajax({
                        			 type : "POST",
                        			 url : ctx + "/application/activity/management/idCheck",
                        			 data : {
             			                "goodId" : ids[0],
            			                "aStartDate" : aStartDate,
            			                "aEndDate" : aEndDate
            			            },
                 					success : function(data) {
                 		            	if(data=="SUCCESS"){
                 		            		handlesubmit(pDiscountRate,aStartDate,aEndDate,remark);
                 		            	}else{
                 		            		$.messager.alert("提示", "时间有交叉请勿添加！", "info");
                 		            	}
                 					}
                 				});
                        	}else{
                        		handlesubmit(pDiscountRate,aStartDate,aEndDate,remark);
                        	}
                        }
                    }, {
                        text : "关闭",
                        handler : function() {
                            $("#activityInfos").dialog("close");
                        }
                    }]
	        });
	}
	
	
	//加载
	function loandDatagrid(goodsId){
		$('#activityTablelist').datagrid({
	        height:200,
	        url : ctx + '/application/activity/management/pagelist?goodsId='+goodsId,
	        pagination : true,
			rownumbers : true,
	        columns: [[
	                {field: 'pDiscountRate', title: '价格折扣率', width: 80,align:'center',
	                	 formatter:function(value,row,index){
	                		 if(value!=null){
	                			 return (value*100).toFixed(2) + "%";
	                		 }
	                     }
	                },
	                {field: 'aStartDate', title: '活动开始时间', width: 140,align : 'center',
	                    formatter:function(value,row,index){
	                    	return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
	                    }
	                },
	                {field: 'aEndDate', title: '活动结束时间', width: 140,align : 'center',
	                    formatter:function(value,row,index){
	                    	return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
	                    }
	                },
	                {field: 'status', title: '活动状态', width: 80,align : 'center',
	                    formatter : function(value, row, index) {
	                    	if(value == '1'){
	                    		return  '有效';
	                    	}
	                    	if(value == '-1'){
	                    		return  '无效';
	                    	}
	                    	if(value == '0'){
	                    		return  '待审核';
	                    	}
	                    },
	                    styler: function(value,row,index){
	        			     return 'color:red;';
	        			}

	                },{field : 'opt',  title : '操作', width : 50,align : 'center',
	                    formatter : function(value, row, index) {
	                    	var content = "";
                            if(row.status=="-1"){
                           	 content +="<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.del("
	                             + row.id + ","+row.goodsId+");\">删除</a>&nbsp;&nbsp;";
	                    	}
                            return content;
	                    }
	                }
	            ]]
	    });
		
	}
	//删除活动
	$.del = function(id,goodsId) {
		$.messager.confirm('提示框', '你确定要删除吗?',function(r){
			if(r){
				var params = {};
				$.ajax({
					type : "get",
					url : ctx + '/application/activity/management/del?id='+id,
					success : function(data) {
						loandDatagrid(goodsId);
					}
				});
			} 
		}) 
    }
	
	
});