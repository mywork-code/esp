
$(function(){
	//单击下一步所需参数
	var param = {};
	var arr = [];
	var obj1 = {};
	var obj2 = {};
	var obj3 = {};
	var obj4 = {};
	var obj5 = {};

	//是否选择优惠券成员变量
	var coupon = null;

	//获取选择发放优惠券下拉框内容
	$(".issueCouponInput").combobox({
		method: "get",
		url: ctx + "/application/coupon/management/loadp",
		valueField: 'id',
		textField: 'name',
		queryParams: {
			"extendType" : "YHLQ",
		},
		onLoadSuccess:function () {

		}
	});
	//活动id
	var paramMapActivityId = $("#paramMapActivityId").val();
	$.ajax({
		url : ctx + '/activity/cfg/edit/find',
		data : {"id":paramMapActivityId},
		type : "post",
		dataType : "json",
		success : function(data) {
			var resp = data.data;
			if(data.status){
				console.log(data);
				$("#activityName").textbox('setValue',resp.activityName);
				$("#startTime").datetimebox('setValue',new Date(resp.startTime).Format("yyyy-MM-dd hh:mm:ss"));
				$("#endTime").datetimebox('setValue',new Date(resp.endTime).Format("yyyy-MM-dd hh:mm:ss"));
				var activityType = resp.activityType;
				$("#activityType").combobox('setValue',activityType);

				coupon = resp.coupon;
				$("[name='isCoupon']").each(function(){
					if($(this).val() == coupon){
						$(this).attr("checked",'true');
					}
				});
				if(activityType == "Y"){
					$("#xxxxxxID").show();
					$("#offerSill1").numberbox('setValue',resp.offerSill1)
					$("#discount1").numberbox('setValue',resp.discountAmonut1)
					$("#offerSill2").numberbox('setValue',resp.offerSill2)
					$("#discount2").numberbox('setValue',resp.discountAmount2)
				}else {
					$("#offerSill1").numberbox('setValue','');
					$("#offerSill2").numberbox('setValue','');
					$("#discount1").numberbox('setValue','');
					$("#discount2").numberbox('setValue','');
					$("#xxxxxxID").css("display","none");
				}

				if(coupon == "Y"){
					$(".couponsDiv").show();

					var proCopuonRels = resp.proCouponRels;
					$("#chooseCoupon1").combobox("setValue",proCopuonRels[0].couponId)
					$("#issueCouponNum1").textbox("setValue",proCopuonRels[0].totalNum)
					$("#issueLimitNum1").textbox("setValue",proCopuonRels[0].limitNum)
					$("#proCouponRelId1").val(proCopuonRels[0].id)

					for (var i=2; i<proCopuonRels.length+1; i++) {
						$(".addOrdeleteCouponTr"+i).show();
						$("#chooseCoupon"+i).combobox("setValue",proCopuonRels[i-1].couponId)
						$("#issueCouponNum"+i).textbox("setValue",proCopuonRels[i-1].totalNum)
						$("#issueLimitNum"+i).textbox("setValue",proCopuonRels[i-1].limitNum)
						$("#proCouponRelId"+i).val(proCopuonRels[i-1].id)
					}

				}else{
					$(".couponsDiv").css("display","none");
				}
			}else{
				$.messager.alert("<span style='color: black;'>警告</span>",data.msg,"warning");
			}
		}
	});

	//单击添加优惠券按钮
	$("#addTotalCouponNumId1").click(function () {
		addTotalNum("issueCouponNum1");
	});
	$("#addTotalCouponNumId2").click(function () {
		addTotalNum("issueCouponNum2");
	});
	$("#addTotalCouponNumId3").click(function () {
		addTotalNum("issueCouponNum3");
	});
	$("#addTotalCouponNumId4").click(function () {
		addTotalNum("issueCouponNum4");
	});
	$("#addTotalCouponNumId5").click(function () {
		addTotalNum("issueCouponNum5");
	});

	/**
	 * 保存活动添加信息
	 */
	$("#agreeAdd").click(function(){
		if(coupon == "Y"){
			if(checkParams()){
				param.relList = arr;
				console.log(arr);
				console.log(param);
				$.ajax({
					url : ctx + '/activity/cfg/editpro/update',
					data : encodeURI(JSON.stringify(param)),
					type : "post",
					dataType : "json",
					success : function(data) {
						if(data.status){
							$.messager.alert("<span style='color: black;'>提示</span>",data.msg,"info");
							window.location.href = ctx + "/activity/cfg/edit?id="+paramMapActivityId;
						}else{
							$.messager.alert("<span style='color: black;'>警告</span>",data.msg,"warning");
						}
					}
				})
			}
		}else{
			window.location.href = ctx + "/activity/cfg/edit?id="+paramMapActivityId;
		}
	});
	//校验参数
	function checkParams(){
		var activityName = $("#activityName").textbox('getValue');
		if(activityName == '' || null == activityName){
			$.messager.alert("<span style='color: black;'>提示</span>","请填写活动名称！","info");
			return false;
		}
		if(activityName.length > 12){
			$.messager.alert("<span style='color: black;'>提示</span>","活动名称长度应在12个字符以内！","info");
			return false;
		}

		var startTime = $("#startTime").textbox('getValue');
		if(startTime=='' || null==startTime){
			$.messager.alert("<span style='color: black;'>提示</span>","请填写开始时间！","info");
			return false;
		}

		var endTime= $("#endTime").textbox('getValue');
		if(endTime=='' || null==endTime){
			$.messager.alert("<span style='color: black;'>提示</span>","请填写结束时间！","info");
			return false;
		}

		if(startTime!=null && startTime!=''&&endTime!=null && endTime!=''){
			if(startTime >= endTime){
				$.messager.alert("<span style='color: black;'>提示</span>","开始时间应小于结束时间，请重新填写！",'info');
				return false;
			}
			if(endTime < new Date().Format("yyyy-MM-dd hh:mm:ss")){
				$.messager.alert("<span style='color: black;'>提示</span>","结束时间填写错误，请重新填写！",'info');
				return false;
			}
		}
		if(activityType == '' || null == activityType){
			$.messager.alert("<span style='color: black;'>提示</span>","请选择活动类型！",'info');
			return false;
		}

		if(activityType == 'Y') {
			var offerSill1 = $("#offerSill1").numberbox('getValue');
			if (offerSill1 == '' || null == offerSill1) {
				$.messager.alert("<span style='color: black;'>提示</span>", "请填写第一个优惠门槛！", 'info');
				return false;
			}
			if (offerSill1.length > 15) {
				$.messager.alert("<span style='color: black;'>提示</span>", "第一个优惠门槛金额长度要小于15位！", 'info');
				return false;
			}
			var offerSill2 = $("#offerSill2").numberbox('getValue');
			if (offerSill2 == '' || null == offerSill2) {
				$.messager.alert("<span style='color: black;'>提示</span>", "请填写第二个优惠门槛！", 'info');
				return false;
			}
			if (offerSill2.length > 15) {
				$.messager.alert("<span style='color: black;'>提示</span>", "第二个优惠门槛金额长度要小于15位！", 'info');
				return false;
			}
			if (offerSill1 == offerSill2) {
				$.messager.alert("<span style='color: black;'>提示</span>", "优惠门槛不能相同！", 'info');
				return false;
			}
			var discount1 = $("#discount1").numberbox('getValue');
			if (discount1 == '' || null == discount1) {
				$.messager.alert("<span style='color: black;'>提示</span>", "请填写第一个优惠金额！", 'info');
				return false;
			}
			if (discount1.length > 15) {
				$.messager.alert("<span style='color: black;'>提示</span>", "第一个优惠金额长度要小于15位！", 'info');
				return false;
			}
			if (parseInt(discount1) >= parseInt(offerSill1)) {
				$.messager.alert("<span style='color: black;'>提示</span>", "第一个优惠金额要小于第一个优惠门槛金额！", 'info');
				return false;
			}
			var discount2 = $("#discount2").numberbox('getValue');
			if (discount2 == '' || null == discount2) {
				$.messager.alert("<span style='color: black;'>提示</span>", "请填写第二个优惠金额！", 'info');
				return false;
			}
			if (discount2.length > 15) {
				$.messager.alert("<span style='color: black;'>提示</span>", "第二个优惠金额长度要小于15位！", 'info');
				return false;
			}
			if (parseInt(discount2) >= parseInt(offerSill2)) {
				$.messager.alert("<span style='color: black;'>提示</span>", "第二个优惠金额要小于第二个优惠门槛金额！", 'info');
				return false;
			}
		}

		var isCoupon = $("input[name='isCoupon']:checked").val()
		if(isCoupon == "Y") {
			var chooseCoupon1 = $('#chooseCoupon1').textbox('getValue');
			if (chooseCoupon1 == '' || null == chooseCoupon1) {
				$.messager.alert("<span style='color: black;'>提示</span>", "请填写第一个选择发放优惠券！", 'info');
				return false;
			}
			var issueCouponNum1 = $('#issueCouponNum1').textbox('getValue');
			if (issueCouponNum1 == '' || null == issueCouponNum1) {
				$.messager.alert("<span style='color: black;'>提示</span>", "请填写第一个发放总量！", 'info');
				return false;
			}
			var issueLimitNum1 = $('#issueLimitNum1').textbox('getValue');
			if (issueLimitNum1 == '' || null == issueLimitNum1) {
				$.messager.alert("<span style='color: black;'>提示</span>", "请填写第一个每人限领数量！", 'info');
				return false;
			}
			obj1.couponId = chooseCoupon1;
			obj1.totalNum = issueCouponNum1;
			obj1.limitNum = issueLimitNum1;
			obj1.id = $("#proCouponRelId1").val();
			arr.push(obj1)


			var addOrdeleteCouponTrDisplay2 = $(".addOrdeleteCouponTr2").css("display");
			var addOrdeleteCouponTrDisplay3 = $(".addOrdeleteCouponTr3").css("display");
			var addOrdeleteCouponTrDisplay4 = $(".addOrdeleteCouponTr4").css("display");
			var addOrdeleteCouponTrDisplay5 = $(".addOrdeleteCouponTr5").css("display");
			if (addOrdeleteCouponTrDisplay2 != "none") {
				var chooseCoupon2 = $('#chooseCoupon2').textbox('getValue');
				if (chooseCoupon2 == '' || null == chooseCoupon2) {
					$.messager.alert("<span style='color: black;'>提示</span>", "请填写第二个选择发放优惠券！", 'info');
					return false;
				}
				var issueCouponNum2 = $('#issueCouponNum2').textbox('getValue');
				if (issueCouponNum2 == '' || null == issueCouponNum2) {
					$.messager.alert("<span style='color: black;'>提示</span>", "请填写第二个发放总量！", 'info');
					return false;
				}
				var issueLimitNum2 = $('#issueLimitNum2').textbox('getValue');
				if (issueLimitNum2 == '' || null == issueLimitNum2) {
					$.messager.alert("<span style='color: black;'>提示</span>", "请填写第二个每人限领数量！", 'info');
					return false;
				}

				obj2.couponId = chooseCoupon2;
				obj2.totalNum = issueCouponNum2;
				obj2.limitNum = issueLimitNum2;
				obj2.id = $("#proCouponRelId2").val();
				arr.push(obj2)
			}

			if (addOrdeleteCouponTrDisplay3 != "none") {
				var chooseCoupon3 = $('#chooseCoupon3').textbox('getValue');
				if (chooseCoupon3 == '' || null == chooseCoupon3) {
					$.messager.alert("<span style='color: black;'>提示</span>", "请填写第三个选择发放优惠券！", 'info');
					return false;
				}
				var issueCouponNum3 = $('#issueCouponNum3').textbox('getValue');
				if (issueCouponNum3 == '' || null == issueCouponNum3) {
					$.messager.alert("<span style='color: black;'>提示</span>", "请填写第三个发放总量！", 'info');
					return false;
				}
				var issueLimitNum3 = $('#issueLimitNum3').textbox('getValue');
				if (issueLimitNum3 == '' || null == issueLimitNum3) {
					$.messager.alert("<span style='color: black;'>提示</span>", "请填写第三个每人限领数量！", 'info');
					return false;
				}
				obj3.couponId = chooseCoupon3;
				obj3.totalNum = issueCouponNum3;
				obj3.limitNum = issueLimitNum3;
				obj3.id = $("#proCouponRelId3").val();
				arr.push(obj3)
			}
			if (addOrdeleteCouponTrDisplay4 != "none") {
				var chooseCoupon4 = $('#chooseCoupon4').textbox('getValue');
				if (chooseCoupon4 == '' || null == chooseCoupon4) {
					$.messager.alert("<span style='color: black;'>提示</span>", "请填写第四个选择发放优惠券！", 'info');
					return false;
				}
				var issueCouponNum4 = $('#issueCouponNum4').textbox('getValue');
				if (issueCouponNum4 == '' || null == issueCouponNum4) {
					$.messager.alert("<span style='color: black;'>提示</span>", "请填写第四个发放总量！", 'info');
					return false;
				}
				var issueLimitNum4 = $('#issueLimitNum4').textbox('getValue');
				if (issueLimitNum4 == '' || null == issueLimitNum4) {
					$.messager.alert("<span style='color: black;'>提示</span>", "请填写第四个每人限领数量！", 'info');
					return false;
				}
				obj4.couponId = chooseCoupon4;
				obj4.totalNum = issueCouponNum4;
				obj4.limitNum = issueLimitNum4;
				obj4.id = $("#proCouponRelId4").val();
				arr.push(obj4)
			}
			if (addOrdeleteCouponTrDisplay5 != "none") {
				var chooseCoupon5 = $('#chooseCoupon5').textbox('getValue');
				if (chooseCoupon5 == '' || null == chooseCoupon5) {
					$.messager.alert("<span style='color: black;'>提示</span>", "请填写第五个选择发放优惠券！", 'info');
					return false;
				}
				var issueCouponNum5 = $('#issueCouponNum5').textbox('getValue');
				if (issueCouponNum5 == '' || null == issueCouponNum5) {
					$.messager.alert("<span style='color: black;'>提示</span>", "请填写第五个发放总量！", 'info');
					return false;
				}
				var issueLimitNum5 = $('#issueLimitNum5').textbox('getValue');
				if (issueLimitNum5 == '' || null == issueLimitNum5) {
					$.messager.alert("<span style='color: black;'>提示</span>", "请填写第五个每人限领数量！", 'info');
					return false;
				}
				obj5.couponId = chooseCoupon5;
				obj5.totalNum = issueCouponNum5;
				obj5.limitNum = issueLimitNum5;
				arr.push(obj5)
			}
		}

		return true;
	}
})

function addTotalNum(id) {
	$("#addTotalCouponNumDiv").dialog({
		title:'<span style="color: black">增加发放总量</span>',
		resizable:true,
		width : 400,
		modal:true,
		buttons:[
			{
				text : "保存",
				handler : function() {
					var num = $("#addTotalCouponNum").textbox('getValue');
					var totalCount = parseInt($("#"+id).textbox('getValue'))+parseInt(num);
					if(parseInt(num)<=0){
						$.messager.alert("<span style='color: black;'>提示</span>","添加发放总量必须大于0","info");
						return;
					}
					$("#"+id).textbox('setValue',totalCount);
					$('#addTotalCouponNumDiv').dialog('close');
				},
			},{
				text : "取消",
				handler : function() {
					$('#addTotalCouponNumDiv').dialog('close');
				}
			}
		]
	});
}
