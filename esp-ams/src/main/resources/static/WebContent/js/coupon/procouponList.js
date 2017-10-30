$(function(){
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
				field : 'bannerType',
				width : 100,
				align : 'center'
			},{
				title : '推广方式',
				field : 'bannerOrder',
				width : 120,
				align : 'center'
			},{
				title : '有效时间',
				field : 'bannerOrder',
				width : 120,
				align : 'center'
			},{
				title : '优惠券类型',
				field : 'bannerOrder',
				width : 120,
				align : 'center'
			},{
				title : '优惠门槛',
				field : 'bannerOrder',
				width : 120,
				align : 'center'
			},{
				title : '优惠金额',
				field : 'bannerOrder',
				width : 120,
				align : 'center'
			},{
				title : '创建时间',
				field : 'bannerOrder',
				width : 120,
				align : 'center'
			},{
				title : '创建人',
				field : 'bannerOrder',
				width : 120,
				align : 'center'
			},{
				title : '操作',
				field : 'opt',
				width : 150,
				align : 'center',
				formatter : function(value, row, index) {
					var content = "";
					content += "<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.deleteCoupon("
						+ row.id+ ");\">删除</a>&nbsp;&nbsp;";
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

});
