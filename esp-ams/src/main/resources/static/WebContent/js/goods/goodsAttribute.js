/**
 * goodsAttribute
 */
$(function () {
	$('#addAttr').window('close');
	$('#editAttr').window('close');
	$('#deleteAttr').window('close');
	// 列表
    $('#goodsAttrlist').datagrid({
    	title : '商品属性管理',
    	fit : true,
        //fitColumns : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        striped:true,
        toolbar : '#tb',
        columns : [[{
            title : '属性主键',
            field : 'id',
            hidden : true,
            width : 50,
            align : 'center'
        }, {
            title : '属性名称',
            field : 'name',
            width : 200,
            align : 'left'
        }, {
            field : 'createdTime',
            title : '创建时间',
            width : 200,
            align : 'center',
            formatter:function(value,row,index){
        		if(value!=null){
        			return new Date(value).Format("yyyy-MM-dd");
        		}
            }
        }, {
            field : 'createdUser',
            title : '创建人',
            width : 200,
            align : 'center'
        }, {
            field : 'updatedTime',
            title : '更新时间',
            width : 200,
            align : 'center',
            formatter:function(value,row,index){
        		if(value!=null){
        			return new Date(value).Format("yyyy-MM-dd");
        		}
            }
        }, {
            field : 'updatedUser',
            title : '更新人',
            width : 200,
            align : 'center'
        }, {
            title : '操作',
            width : 150,
            field : 'opt',
            align : 'center',
            formatter : function(value, row, index) {
                if (row.id == 'root') {
                    return "";
                }
                var content = "";
                content += "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='$.editDetails(\"" + row.id + "\",\"" + row.name + "\");'>编辑</a>&nbsp;";
                content += "<a href='javascript:void(0);' class='easyui-linkedbutton'; onclick='$.deleteDetails(\"" + row.id + "\");'>删除</a>";
                return content;
            }
        }]],
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + "/application/goods/goodsAttr/getGoodsAttrList",
                data : param,
                type : "post",
                dataType : "json",
                success : function(resp) {
                    $.validateResponse(resp, function() {
                        success(resp);
                    });
                }
            })
        }
    
    });
    // 查询列表
    $(".search-btn").click(function() {
        var params = {};
        params['attrName'] = $("#attrName").textbox('getValue');
        $('#goodsAttrlist').datagrid('load', params);
    });
    //添加按钮事件
    $(".add-btn").click(function() {
    	$("#name").textbox('clear');
    	$('#addAttr').window({
			shadow : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			modal : true
		});
		//打开编辑弹出框
		$('#addAttr').window('open');
    });
    //确定
	$("#addagree").click(function() {
		var name=$("#name").val();
		var length = name.length;
		var re = /^[\u4E00-\u9FA5]+$/;
		var falg = re.test(name);
		if(!falg){
			$.messager.alert("提示", "商品属性名称必须输入中文字符！", "info");
			return;
		}
		if(length>8){
			$.messager.alert("提示", "商品属性名称长度不可超过8个中文字符！", "info");
			return;
		}
		//提交
		 $.ajax({
             url : ctx + '/application/goods/goodsAttr/addGoodsAttr',
             data : {"name":name},
             type : "post",
             dataType : "json",
             success : function(data) {
                 $.validateResponse(data, function() {
                	 $(".search-btn").click();
 	                $('#addAttr').window('close');
                 });
             }
         })
	});
    //取消
	$("#addcancel").click(function() {
		$('#addAttr').window('close');
	});
	//全局变量id用于修改数据
	var idedit = null;
	//修改按钮事件
	$.editDetails = function(editid,name) {
		idedit = editid;
		$("#nameedit").textbox('setValue',name);
		$('#editAttr').window({
			shadow : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			modal : true
		});
		//打开编辑弹出框
		$('#editAttr').window('open');
	};
	//确定
	$("#editagree").click(function() {
		var id=idedit;
		var name=$("#nameedit").val();
		var length = name.length;
		var re = /^[\u4E00-\u9FA5]+$/;
		var falg = re.test(name);
		if(!falg){
			$.messager.alert("提示", "商品属性名称必须输入中文字符！", "info");
			return;
		}
		if(length>8){
			$.messager.alert("提示", "商品属性名称长度不可超过8个中文字符！", "info");
			return;
		}
		//提交
		var address = ctx + '/application/goods/goodsAttr/editGoodsAttr';
		$.ajax({
            url : address,
            data : {"id":id,"name":name},
            type : "post",
            dataType : "json",
            success : function(data) {
                $.validateResponse(data, function() {
                	$(".search-btn").click();
 	                $('#editAttr').window('close');
                });
            }
        })
	});
	//取消
	$("#editcancel").click(function() {
		$('#editAttr').window('close');
	});
	//删除商品属性
	$.deleteDetails = function(deleteid) {
		$('#deleteAttr').window({
			shadow : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			modal : true
		});
		idedit = deleteid;
		$('#deleteAttr').window('open');
	};
	//确定
	$("#deleteagree").click(function() {
		var id=idedit;
		var address = ctx + '/application/goods/goodsAttr/deleteGoodsAttr';
		$.getJSON(
			address,
			{"id":id}, 
			function(resp) {
				$.validateResponse(resp, function() {
	                $(".search-btn").click();
	            });
				$('#deleteAttr').window('close');
		    }
		);
	});
	//取消
	$("#deletecancel").click(function() {
		$('#deleteAttr').window('close');
	});
});