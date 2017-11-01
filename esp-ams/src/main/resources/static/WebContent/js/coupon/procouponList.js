$(function(){
	var sillType = null;
	//Grid
	$('#couponList').datagrid({
		title : '优惠券管理',
		fit : true,
		fitColumns : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        striped:true,
        toolbar : '#tb',
        columns :[[
			{
				title : '优惠券名称',
				field : 'name',
				width : 100,
				align : 'center'
			},{
				title : '推广方式',
				field : 'extendType',
				width : 120,
				align : 'center'
			},{
				title : '有效时间',
				field : 'effectiveTime',
				width : 120,
				align : 'center'
			},{
				title : '优惠券类型',
				field : 'type',
				width : 120,
				align : 'center'
			},{
				title : '优惠门槛',
				field : 'couponSill',
				width : 120,
				align : 'center'
			},{
				title : '优惠金额',
				field : 'discountAmonut',
				width : 120,
				align : 'center'
			},{
				title : '创建时间',
				field : 'createdTime',
				width : 120,
				align : 'center'
			},{
				title : '创建人',
				field : 'createUser',
				width : 120,
				align : 'center'
			},{
				title : '操作',
				field : 'opt',
				width : 150,
				align : 'center',
				formatter : function(value, row, index) {
					var content = "";
					content += "<a href='javascript:void(0);' class='easyui-linkedbutton' " +
						"onclick=\"$.deleteCoupon('"+ row.id+"','" +  row.extendType + "');\">删除</a>&nbsp;&nbsp;";
				 return content;
			}
			}]],
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + '/application/coupon/management/pageList',
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

	//单击添加优惠券按钮
	$("#addCouponButton").click(function () {
		clearFunction();

		$("#addCouponDiv").dialog({
			title:'<span style="color: black">添加优惠券</span>',
			resizable:true,
			width : 400,
			modal:true,
			buttons:[
				{
					text : "保存",
					handler : function() {
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
						
						var param = {
							"name":$("#addCouponName").textbox("getValue"),
							"extendType":$("#addExtendType").textbox("getValue"),
							"effectiveTime":$("#addEffectiveTime").textbox("getValue"),
							"type":$("#addType").combobox("getValue"),
							"categoryId1":categoryId1,
							"categoryId2":categoryId1,
							"goodsCode":$("#addGoodsCode").textbox("getValue"),
							"couponSill":$("#addCouponSill").textbox("getValue"),
							"discountAmonut":$("#addDiscountAmonut").textbox("getValue"),
							"sillType":sillType
						}

						$.ajax({
							url : ctx + '/application/coupon/management/add',
							data : param,
							type : "post",
							dataType : "json",
							success : function(data) {
								if(data.status){
									$.messager.alert('<span style="color: black">提示</span>',data.msg);
									$('#addCouponDiv').dialog('close');
									$('#couponList').datagrid('load',{});
								}else{
									$.messager.alert('<span style="color: black">提示</span>',data.msg);
								}
							}
						})
					},
				},{
					text : "取消",
					handler : function() {
						$('#addCouponDiv').dialog('close');
					}
				}
			]
		});
	});
	//单击手动发放优惠券按钮
	$("#issueCouponButton").click(function () {
		$("#issueCouponDiv").dialog({
			title:'<span style="color: black">添加优惠券</span>',
			resizable:true,
			width : 400,
			modal:true,
			buttons:[
				{
					text : "保存",
					handler : function() {
					}
				},{
					text : "取消",
					handler : function() {
						$('#issueCouponDiv').dialog('close');
					}
				}
			]
		});
		});

	$.deleteCoupon = function (id,extendType) {
		debugger;
		$.messager.confirm('<span style="color: black">提示</span>',"你确认要删除吗？",function (r) {
			if(r){
				var param = {
					"id":id,
					"extendType":extendType
				}
				$.ajax({
					url : ctx + '/application/coupon/management/delete',
					data : param,
					type : "post",
					dataType : "json",
					success : function(data) {
						if(data.status){
							$.messager.alert('<span style="color: black">提示</span>',data.msg);
							$('#couponList').datagrid('load',{});
						}else{
							$.messager.alert('<span style="color: black">提示</span>',data.msg);
						}
					}
				})
			}
		});

	}


	function clearFunction() {
		$("#addCouponName").textbox("clear");
		$("#addExtendType").textbox("clear");
		$("#addEffectiveTime").textbox("clear");
		$("#goodsCategoryCombo").combobox("clear");
		$("#addGoodsCode").textbox("clear");
		$("#addCouponSill").textbox("clear");
		$("#addDiscountAmonut").textbox("clear");
	}
	//有无门槛是否选中
	$("#ifCouponSill").click(function () {
		if($('#ifCouponSill').is(':checked')){
			$("#addCouponSill").textbox({disabled: true});
			$("#addCouponSill").textbox('clear');
			sillType = "N";
		}else{
			$("#addCouponSill").textbox({disabled: false});
			sillType = "Y";
		}
	});

	//推广方式 类型选中事件
	$('#addExtendType').combobox({
		onChange: function(param){
			if(param == 'YHLQ'){
				$("#effectiveTimeTr").css("display","none");
				$("#addType").combobox({ disabled: true });
				$("#addType").append("<option value='HDSP'>活动商品</option>");
				$("#addType").combobox({});
				$("#addType").combobox('setValue','HDSP');
			}else{
				$("#addType").combobox({ disabled: false });
				$("#addType").combobox('setValue','');
				$("#effectiveTimeTr").show()
			}
		}
	});

	//优惠券类型选中监听事件
	$('#addType').combobox({
		onChange: function(param){
			if(param == 'ZDPL'){
				$("#goosCategoryTr").show();
				$("#goodsCodeTr").css("display","none");
			}else if(param == 'ZDSP'){
				$("#goosCategoryTr").css("display","none");
				$("#goodsCodeTr").show();
				//加载类目树
				goodsCategoryComboFun();
			}else{
				$("#goosCategoryTr").css("display","none");
				$("#goodsCodeTr").css("display","none");
			}
		}
	});

});
