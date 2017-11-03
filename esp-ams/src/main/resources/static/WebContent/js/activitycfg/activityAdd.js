 
$(function(){
	//单击下一步所需参数
	var param = {};
	var arr = [];
	var obj1 = {};
	var obj2 = {};
	var obj3 = {};
	var obj4 = {};
	var obj5 = {};
	/**
	 * 保存活动添加信息
	 */
	$("#agreeAdd").click(function(){
    	if(checkParams()){
			param.procouponRelVoListList = arr;
			debugger;
			console.log(arr);
			console.log(param);
			$.ajax({
				url : ctx + '/activity/cfg/add/save',
				data : encodeURI(JSON.stringify(param)),
				type : "post",
				dataType : "json",
				success : function(data) {
					if(data.status){
						$.messager.alert("<span style='color: black;'>提示</span>",data.msg,"info");
						window.location.href = ctx + "/activity/cfg/edit?id="+respon.data;
					}else{
						$.messager.alert("<span style='color: black;'>警告</span>",data.msg,"warning");
					}
				}
			})
    	}
	});

	function checkParams(){
		var activityName = $("#activityName").textbox('getValue');
		param.activityName = activityName;
		if(activityName == '' || null == activityName){
			$.messager.alert("<span style='color: black;'>提示</span>","请填写活动名称！","info");
			return false;
		}
		if(activityName.length > 12){
			$.messager.alert("<span style='color: black;'>提示</span>","活动名称长度应在12个字符以内！","info");
			return false;
		}
		
		var startTime=$("#startTime").textbox('getValue');
		if(startTime=='' || null==startTime){
			$.messager.alert("<span style='color: black;'>提示</span>","请填写开始时间！","info");
			return false;
		}
		param.startTime = startTime;
		
		var endTime= $("#endTime").textbox('getValue');
		if(endTime=='' || null==endTime){
			$.messager.alert("<span style='color: black;'>提示</span>","请填写结束时间！","info");
			return false;
		}
		param.endTime = endTime;
		
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
		var activityType = $("#activityType").combobox('getValue');
		if(activityType == '' || null == activityType){
			$.messager.alert("<span style='color: black;'>提示</span>","请选择活动类型！",'info');
			return false;
		}
		param.activityType = activityType;

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
			param.offerSill1 = offerSill1;
			var offerSill2 = $("#offerSill2").numberbox('getValue');
			if (offerSill2 == '' || null == offerSill2) {
				$.messager.alert("<span style='color: black;'>提示</span>", "请填写第二个优惠门槛！", 'info');
				return false;
			}
			if (offerSill2.length > 15) {
				$.messager.alert("<span style='color: black;'>提示</span>", "第二个优惠门槛金额长度要小于15位！", 'info');
				return false;
			}
			param.offerSill2 = offerSill2;
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
			param.discount1 = discount1;
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
			param.discount2 = discount2;
		}

		var isCoupon = $("input[name='isCoupon']:checked").val()
		param.coupon = isCoupon;
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

	$("#activityType").combobox({
		onChange: function (n,o) {
			if(n == 'Y'){
				$("#xxxxxxID").show();
			}else{
				$("#offerSill1").numberbox('setValue','');
				$("#offerSill2").numberbox('setValue','');
				$("#discount1").numberbox('setValue','');
				$("#discount2").numberbox('setValue','');
				$("#xxxxxxID").css("display","none");
			}
		}
	});

	//radio监听事件
	$(".ifCou").change(
		function () {
			var value = $("input[name='isCoupon']:checked").val();
			if(value == 'Y'){
				$(".couponsDiv").show();
				$("#addOrDeleteButtonClass").show();
				//加载所有活动优惠券
				$(".issueCouponInput").combobox({
					method: "get",
					url: ctx + "/application/coupon/management/loadp",
					valueField: 'id',
					textField: 'name',
					queryParams: {
						"extendType" : "YHLQ",
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
			}else{
				$(".couponsDiv").css("display","none");
			}
		}
	);

	//手动发放优惠券---->添加优惠券种类
	$('#issueAddCoupon').bind('click', function(){
		var addOrdeleteCouponTrDisplay2 = $(".addOrdeleteCouponTr2").css("display");
		var addOrdeleteCouponTrDisplay3 = $(".addOrdeleteCouponTr3").css("display");
		var addOrdeleteCouponTrDisplay4 = $(".addOrdeleteCouponTr4").css("display");
		var addOrdeleteCouponTrDisplay5 = $(".addOrdeleteCouponTr5").css("display");

		if(addOrdeleteCouponTrDisplay2 == "none"){
			$(".addOrdeleteCouponTr2").show();
		}else if(addOrdeleteCouponTrDisplay3 == "none"){
			$(".addOrdeleteCouponTr3").show();
		}else if(addOrdeleteCouponTrDisplay4 == "none"){
			$(".addOrdeleteCouponTr4").show();
		}else{
			$(".addOrdeleteCouponTr5").show();
		}

		if(addOrdeleteCouponTrDisplay2 != "none"&& addOrdeleteCouponTrDisplay3 != "none"
			&& addOrdeleteCouponTrDisplay4 != "none"&& addOrdeleteCouponTrDisplay5 != "none"){
			$.messager.alert('<span style="color: black">提示</span>',"最多添加五种优惠券");
			return;
		}

	});

	//手动发放优惠券---->删除优惠券种类
	$('#issueDeleteCoupon').bind('click', function(){
		var addOrdeleteCouponTrDisplay2 = $(".addOrdeleteCouponTr2").css("display");
		var addOrdeleteCouponTrDisplay3 = $(".addOrdeleteCouponTr3").css("display");
		var addOrdeleteCouponTrDisplay4 = $(".addOrdeleteCouponTr4").css("display");
		var addOrdeleteCouponTrDisplay5 = $(".addOrdeleteCouponTr5").css("display");

		if(addOrdeleteCouponTrDisplay5 != "none"){
			$(".addOrdeleteCouponTr5").css("display","none");
		}else if(addOrdeleteCouponTrDisplay4 != "none"){
			$(".addOrdeleteCouponTr4").css("display","none");
		}else if(addOrdeleteCouponTrDisplay3 != "none"){
			$(".addOrdeleteCouponTr3").css("display","none");
		}else{
			$(".addOrdeleteCouponTr2").css("display","none");
		}

		if(addOrdeleteCouponTrDisplay5 == "none"&&addOrdeleteCouponTrDisplay4 == "none"
			&&addOrdeleteCouponTrDisplay3 == "none"&& addOrdeleteCouponTrDisplay2 == "none"){
			$.messager.alert('<span style="color: black">提示</span>',"最少添加一种优惠券");
			return;
		}

	});
	
	

})

function ifLogoutForm(data){
	var flag1 = data.indexOf('登录系统');
	var flag2 = data.indexOf('</div');
	if (flag1 != -1 && flag2 != -1) {
		$.messager.alert("操作提示", "登录超时, 请重新登录", "info");
		window.top.location = ctx + "/logout";
		return;
	}
}
