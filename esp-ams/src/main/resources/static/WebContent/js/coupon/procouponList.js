$(function(){
	// 有无门槛标记
	var sillType = 'N';
	//优惠券类型标记
	var type = null;
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
				align : 'center',
				formatter :function(value, row, index) {
					if(value == '-1'){
						value = '';
					}
				 return value;
			    }
			},{
				title : '优惠券类型',
				field : 'type',
				width : 120,
				align : 'center'
			},{
				title : '优惠门槛',
				field : 'couponSill',
				width : 120,
				align : 'center',
				formatter :function(value, row, index) {
					if(value == '0'){
						value = '无门槛';
					}
				 return value;
			    }
			},{
				title : '优惠金额',
				field : 'discountAmonut',
				width : 120,
				align : 'center'
			},{
				title : '创建时间',
				field : 'createdTime',
				width : 140,
				align : 'center',
				formatter:function (value, row, index) {
					if(value != null){
						return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
					}
				}
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
						//参数验证
						if(!validateAddCoupon(categoryId1,categoryId2,categoryId3)){
							return;
						}
						var param = {
							"name":$("#addCouponName").textbox("getValue"),
							"extendType":$("#addExtendType").textbox("getValue"),
							"effectiveTime":$("#addEffectiveTime").textbox("getValue"),
							"type":type,
							"categoryId1":categoryId1,
							"categoryId2":categoryId2,
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
								if(data.status=="1"){
									$.messager.alert('<span style="color: black">提示</span>',data.msg);
									$('#addCouponDiv').dialog('close');
									$('#couponList').datagrid('reload',{});
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
		$(".issueCouponInput").combobox({
			method: "get",
			url: ctx + "/application/coupon/management/loadp",
			valueField: 'id',
			textField: 'name',
			queryParams: {
				"extendType" : "PTFF"
			},
			onLoadSuccess: function () {
				$(this).combobox('setValue','请选择');
				//加载完成后,设置选中第一项
				// var val = $(this).combobox('getData');
				// for (var item in val[0]) {
				// 	if (item == 'id') {
				// 		$(this).combobox('select', val[0][item]);
				// 	}
				// }
			},
		});
		clearIssueDiv();

		$("#issueCouponDiv").dialog({
			title:'<span style="color: black">添加优惠券</span>',
			resizable:true,
			width : 400,
			modal:true,
			buttons:[
				{
					text : "保存",
					handler : function() {
						debugger;
						var arr = [];
						var arrObj1 = {};
						var arrObj2 = {};
						var arrObj3 = {};
						arrObj1.id = $("#chooseCoupon").combobox('getValue');
						arrObj1.numer = $("#issueCouponNum").textbox('getValue');
						if(arrObj1.id=='请选择'){
							$.messager.alert('<span style="color: black">提示</span>','请选择发放优惠券');
							return;
						}
						if(arrObj1.numer == null || arrObj1.numer==''){
							$.messager.alert('<span style="color: black">提示</span>','请输入发放数量');
							return;
						}
						arr.push(arrObj1)

						var addOrdeleteCouponTrDisplay1 = $(".addOrdeleteCouponTr1").css("display");
						var addOrdeleteCouponTrDisplay2 = $(".addOrdeleteCouponTr2").css("display");
						if(addOrdeleteCouponTrDisplay1 != "none"){
							arrObj2.id = $("#chooseCoupon1").combobox('getValue');
							arrObj2.numer = $("#issueCouponNum1").textbox('getValue');
							if(arrObj2.id=='请选择'){
								$.messager.alert('<span style="color: black">提示</span>','请选择发放优惠券');
								return;
							}
							if(arrObj2.numer == null || arrObj2.numer==''){
								$.messager.alert('<span style="color: black">提示</span>','请输入发放数量');
								return;
							}
							arr.push(arrObj2);
						}
						if(addOrdeleteCouponTrDisplay2 != "none"){
							arrObj3.id = $("#chooseCoupon2").combobox('getValue');
							arrObj3.numer = $("#issueCouponNum2").textbox('getValue');
							if(arrObj3.id=='请选择'){
								$.messager.alert('<span style="color: black">提示</span>','请选择发放优惠券');
								return;
							}
							if(arrObj3.numer == null || arrObj3.numer==''){
								$.messager.alert('<span style="color: black">提示</span>','请输入发放数量');
								return;
							}
							arr.push(arrObj3);
						}

						// issueUserGroup
						if($("#issueUserGroup").textbox('getValue') == null || $("#issueUserGroup").textbox('getValue') == ""){
							$.messager.alert('<span style="color: black">提示</span>','请选择发放人群');
							return;
						}
						if($("#issueTelephone").textbox('getValue') == null || $("#issueTelephone").textbox('getValue') == ""){
							$.messager.alert('<span style="color: black">提示</span>','请输入发放手机号');
							return;
						}
						if($("#issueRemark").textbox('getValue') == ""){
							$.messager.alert('<span style="color: black">提示</span>','请输入备注');
							return;
						}
						var param = {
							"couponListIssue":arr,
							"telephone":$("#issueTelephone").textbox('getValue'),
							"remarks":$("#issueRemark").textbox('getValue')
						}

						console.log(JSON.stringify(param));
						$.ajax({
							url : ctx + '/application/coupon/management/issue',
							data : encodeURI(JSON.stringify(param)),
							type : "post",
							dataType : "json",
							success : function(data) {
								if(data.status=="1"){
									$.messager.alert('<span style="color: black">提示</span>',data.msg);
									$('#issueCouponDiv').dialog('close');
									$('#couponList').datagrid('reload',{});
								}else{
									$.messager.alert('<span style="color: black">提示</span>',data.msg);
								}
							}
						})
						


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

	//删除优惠券
	$.deleteCoupon = function (id,extendType) {
		if(extendType == "用户领取"){
			extendType = "YHLQ"
		}else if(extendType == "平台发放"){
			extendType = "PTFF"
		}else if(extendType == "新用户专享"){
			extendType = "XYH"
		}else{
			$.messager.alert('<span style="color: black">提示</span>',"推广方式数据有误？");
			return;
		}
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
						if(data.status=="1"){
							$.messager.alert('<span style="color: black">提示</span>',data.msg);
							$('#couponList').datagrid('reload',{});
						}else{
							$.messager.alert('<span style="color: black">提示</span>',data.msg);
						}
					}
				})
			}
		});

	}

	//手动发放优惠券---->添加优惠券种类
	$('#issueAddCoupon').bind('click', function(){
		var addOrdeleteCouponTrDisplay1 = $(".addOrdeleteCouponTr1").css("display");
		var addOrdeleteCouponTrDisplay2 = $(".addOrdeleteCouponTr2").css("display");

		if(addOrdeleteCouponTrDisplay1 == "none"){
			$(".addOrdeleteCouponTr1").show();
		}else{
			$(".addOrdeleteCouponTr2").show();
		}

		if(addOrdeleteCouponTrDisplay1 != "none" && addOrdeleteCouponTrDisplay2 != "none"){
			$.messager.alert('<span style="color: black">提示</span>',"最多添加三种优惠券");
			return;
		}

	});

	//手动发放优惠券---->删除优惠券种类
	$('#issueDeleteCoupon').bind('click', function(){
		var addOrdeleteCouponTrDisplay1 = $(".addOrdeleteCouponTr1").css("display");
		var addOrdeleteCouponTrDisplay2 = $(".addOrdeleteCouponTr2").css("display");

		if(addOrdeleteCouponTrDisplay2 != "none"){
			$("#chooseCoupon2").combobox('setValue','请选择');
			$("#issueCouponNum2").textbox('clear');

			$(".addOrdeleteCouponTr2").css("display","none");
		}else{
			$("#chooseCoupon1").combobox('setValue','请选择');
			$("#issueCouponNum1").textbox('clear');
			$(".addOrdeleteCouponTr1").css("display","none");
		}

		if(addOrdeleteCouponTrDisplay1 == "none" && addOrdeleteCouponTrDisplay2 == "none"){
			$.messager.alert('<span style="color: black">提示</span>',"最少添加一种优惠券");
			return;
		}

	});

	//发放人群监听事件
	$("#issueUserGroup").combobox({
		onChange: function(param){
			$("#issueTelephoneTr").show();
		}
	})

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

	//推广方式类型监听事件
	$('#addExtendType').combobox({
		onChange: function(param){
			if(param == 'YHLQ'){
				$("#effectiveTimeTr").css("display","none");
				$("#typeTd1").css("display","none");
				$("#typeTd2").show();

				$("#goodsCodeTr").css("display","none");
				$("#goosCategoryTr").css("display","none");

				$("#addType2").combobox('setValue','HDSP')
				type = $("#addType2").combobox('getValue');
				$("#addType2").combobox({ disabled: true });
			}else{
				$("#addType").combobox('setValue','');
				$("#typeTd1").show();
				$("#typeTd2").css("display","none");
				$("#effectiveTimeTr").show()
				type = $("#addType1").combobox('getValue');
			}
		}
	});

	//优惠券类型选中监听事件
	$('#addType1').combobox({
		onChange: function(param){
			type = $("#addType1").combobox('getValue');
			if(param == 'ZDPL'){
				$("#goosCategoryTr").show();
				$("#goodsCodeTr").css("display","none");
			}else if(param == 'ZDSP'){
				$("#goosCategoryTr").css("display","none");
				$("#goodsCodeTr").show();

			}else{
				$("#goosCategoryTr").css("display","none");
				$("#goodsCodeTr").css("display","none");
			}
			//加载类目树
			goodsCategoryComboFun2();
		}
	});

	//添加优惠券前台验证
	function validateAddCoupon(categoryId1,categoryId2,categoryId3) {
		if($("#addCouponName").textbox("getValue") == null || $("#addCouponName").textbox("getValue") == ""){
			$.messager.alert('<span style="color: black">提示</span>','请输入优惠券名称');
			return false;
		}
		if($("#addCouponName").textbox("getValue").length>20){
			$.messager.alert('<span style="color: black">提示</span>','优惠券名称不能大于20字符');
			return false;
		}
		if($("#addExtendType").textbox("getValue")==null || $("#addExtendType").textbox("getValue")==""){
			$.messager.alert('<span style="color: black">提示</span>','请选择推广方式');
			return false;
		}

		if($("#addExtendType").textbox("getValue") == "PTFF" || $("#addExtendType").textbox("getValue") == "XYH"){
			if($("#addEffectiveTime").textbox("getValue").length==null || $("#addEffectiveTime").textbox("getValue").length==""){
				$.messager.alert('<span style="color: black">提示</span>','请输入有效期');
				return false;
			}
		}
		if(type==null || type==""){
			$.messager.alert('<span style="color: black">提示</span>','请选择优惠券类型');
			return false;
		}

		if(type == "ZDPL"){
			if($("#addExtendType").textbox("getValue") == "PTFF" || $("#addExtendType").textbox("getValue") == "XYH"){
				if((categoryId1==null || categoryId1=="") && (categoryId2==null || categoryId2=="")){
					$.messager.alert('<span style="color: black">提示</span>','请选择商品类目');
					return false;
				}
			}
		}
		if(type == "ZDSP"){
			if($("#addExtendType").textbox("getValue") == "PTFF" || $("#addExtendType").textbox("getValue") == "XYH"){
				if($("#addGoodsCode").textbox("getValue")==null || $("#addGoodsCode").textbox("getValue")==""){
					$.messager.alert('<span style="color: black">提示</span>','请输入商品编码');
					return false;
				}
			}
		}
		
		if(sillType == "Y"){
			if($("#addCouponSill").textbox("getValue")==null || $("#addCouponSill").textbox("getValue") == ""){
				$.messager.alert('<span style="color: black">提示</span>','请输入优惠门槛');
				return false;
			}

			var couponSill = parseInt($("#addCouponSill").textbox("getValue"));
			if(couponSill<0){
				$.messager.alert('<span style="color: black">提示</span>','优惠门槛只能输入正整数');
				return false;
			}
		}


		if($("#addDiscountAmonut").textbox("getValue")==null || $("#addDiscountAmonut").textbox("getValue") == ""){
			$.messager.alert('<span style="color: black">提示</span>','请输入优惠金额');
			return false;
		}
		var dsicountAmount = parseInt($("#addDiscountAmonut").textbox("getValue"));
		if(dsicountAmount<0){
			$.messager.alert('<span style="color: black">提示</span>','优惠金额只能输入正整数');
			return false;
		}

		return true;
	}


});

//加载类目
function goodsCategoryComboFun2() {
	$("#goodsCategoryCombo").combotree({
//        required : true,
		loader : function(param, success, error) {
			$.ajax({
				url : ctx + "/application/goods/management/categoryList2",
				data : param,
				type : "get",
				dataType : "json",
				success : function(resp) {
					$.validateResponse(resp, function() {
						success(resp.data);
					});
				}
			})
		}
	});
	$("#goodsCategoryCombo").combotree('setValue', '请选择');
}
//添加优惠券窗口初始化
function clearFunction() {
	$("#addCouponName").textbox("clear");
	$("#addExtendType").textbox("clear");
	$("#addEffectiveTime").textbox("clear");
	$("#goodsCategoryCombo").combobox("clear");
	$("#addType1").combobox("clear");
	$("#addType2").combobox("clear");
	$("#addType2").combobox({ disabled: false });
	$("#addGoodsCode").textbox("clear");
	$("#addCouponSill").textbox("clear");
	$("#addCouponSill").textbox({disabled: false});
	$("#addDiscountAmonut").textbox("clear");
	$("#ifCouponSill").attr("checked",false)

}
//手动发放优惠券窗口初始化
function clearIssueDiv() {
	$("#chooseCoupon").combobox('clear');
	$("#issueCouponNum").textbox('clear');
	$("#chooseCoupon1").combobox('clear');
	$("#issueCouponNum1").textbox('clear');
	$("#chooseCoupon2").combobox('clear');
	$("#issueCouponNum2").textbox('clear');

	$("#issueUserGroup").combobox('clear');
	$("#issueTelephone").textbox('clear');
	$("#issueRemark").textbox('clear');
}

