$(function(){
	$("#addBannerInfor").window('close');
	$("#showBannerPoto").window('close');
	
	//Grid
	$('#bannerList').datagrid({
		title : 'banner信息',
		fit : true,
		fitColumns : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        striped:true,
        toolbar : '#tb',
        columns :[[
			//{
			//title : '名称',
			//field : 'bannerName',
			//width : 150,
			//align : 'center'
			//},	
			{
				title : '图片地址',
				field : 'bannerImgUrl',
				width : 250,
				align : 'center',
				formatter : function(value, row, index) {
						 var content = "";
						 content += "<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.show('"
						+ row.bannerImgUrl+ "');\">"+row.bannerImgUrl+"</a>";
				return content;
			    }
			}, {
				title : '类型',
				field : 'bannerType',
				width : 100,
				align : 'center',
				formatter:function(value,row,index){
					if(value=="index"){
						return "首页";
					}else if(value=="sift"){
						return "精选";
				}
			}
			},
			{
				title : '活动地址',
				field : 'activityUrl',
				width : 250,
				align : 'center',
				formatter : function(value, row, index) {
					 var content = "";
					 if(row.activityUrl!=null){
						 content += "<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.showActivity('"
								+ row.activityUrl+ "');\">"+row.activityUrl+"</a>";
						 
//						 content += "<a class='easyui-linkedbutton' href='"+row.activityUrl+"'>"+row.activityUrl+"</a>";
					 }
			         return content;
		        }
			},
			{
				title : '排序',
				field : 'bannerOrder',
				width : 120,
				align : 'center'
			},{
				title : '操作',
				field : 'opt',
				width : 150,
				align : 'center',
				formatter : function(value, row, index) {
					// 授权标示
	            	var grantedAuthority=$('#grantedAuthority').val();
					var content = "";
					if(grantedAuthority=='permission'){
					    content += "<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.deleteBanner("
							+ row.id+ ");\">删除</a>&nbsp;&nbsp;";
					}  
					     content += "<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.show('"
							+ row.bannerImgUrl+ "');\">查看</a>&nbsp;&nbsp;";//encodeURI(JSON.stringify(row.bannerImgUrl) )
				 return content;
			}
			}]],
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + '/application/banner/management/query',
                data : param,
                type : "post",
                dataType : "json",
                success : function(data) {
                  // console.log(data);
            	   $.validateResponse(data, function() {
            		  // debugger;
                       success(data);
                   });
                }
            })
        }
	});
	
	
	$("#bannerType").combobox({
		onSelect:function(){
			if($("#bannerType").combobox('getValue') == "index"){
				$("#fondSpan").empty();
				$("#fondSpan").append("<font color='red'>支持格式：.png或.jpg;宽：750px,高：420px;大小：≤500kb</font>");
			}else{
				$("#fondSpan").empty();	
				$("#fondSpan").append("<font color='blue'>支持格式：.png或.jpg;宽：750px,高：280px;大小：≤500kb</font>");
			}
		}
	});
	
	//添加  banner信息
	$("#add").click(function(){
		$('#addBannerInfor').window({
            minimizable:false,
            maximizable:false,
            collapsible:false,
            modal:true,
            top:$(document).scrollTop() + ($(window).height()-250) * 0.5
		});
		$("#addBannerInfor").window('open');
		
		$("#bannerName").textbox('setValue','');
		$("#bannerType").combobox('setValue','');
		$("#bannerOrder").numberbox('setValue','');
		$("#bannerFile").val('');
		$("#activityUrl").textbox('setValue','');
	});
	//确认   添加  banner信息
	$("#agreeAdd").click(function(){
//		var bannerName = $("#bannerName").textbox('getValue');
//		if(null == bannerName || bannerName==""){
//			alert("名称不能为空！");
//			return ;
//		}
		var bannerType=$("#bannerType").combobox('getValue');
		if(null == bannerType || bannerType==""){
			$.messager.alert("<span style='color: black;'>提示</span>","类型不能为空！","info");
			return;
		}
		var bannerOrder=$("#bannerOrder").numberbox('getValue');
		if(null == bannerOrder || bannerOrder==""){
			$.messager.alert("<span style='color: black;'>提示</span>","排序不能为空！","info");
			return;
		}
		var bannerFile= $("#bannerFile").val();
		if(bannerFile=='' || null==bannerFile){
			$.messager.alert("<span style='color: black;'>提示</span>","请选择上传图片！","info");
			return;
		}
		
//		 if(bannerFile!="" && null!= bannerFile){
//				var pos = "." + bannerFile.replace(/.+\./, "");
//				if(bannerFile!=null && pos!=".jpg" && pos!=".png"){
//					$.messager.alert("提示","请选择.png或.jpg类型文件！","info");
//					return;
//				}
//		 }
		 
		
		//提交from
		var theForm = $("#addBannerFile");
		theForm.form("submit",{ 
			url : ctx + '/application/banner/management/addBannerFile',
			success : function(data) {
				debugger;
				var flag1 = data.indexOf('登录系统');
				var flag2 = data.indexOf('</div');
				if (flag1 != -1 && flag2 != -1) {
					$.messager.alert("操作提示", "登录超时, 请重新登录", "info");
					window.top.location = ctx + "/logout";
					return;
	            }
				var respon=JSON.parse(data);
				if(respon.status=="1"){
					$.messager.alert("<span style='color: black;'>提示</span>",respon.msg,"info");
					 $('#addBannerInfor').window('close');
				}else{
					$.messager.alert("<span style='color: black;'>警告</span>",respon.msg,"warning");
				}
				var param = {};
            	param['bannerType'] = $('#bannerType2').combobox('getValue');
		        $('#bannerList').datagrid('load', param);
			}
		});
	});
	//取消   添加  banner信息
	$("#cancelAdd").click(function(){
		$("#addBannerInfor").window('close');
	});
	//删除
	$.deleteBanner=function(id){
		$.messager.confirm("<span style='color: black;'>操作提示</span>", "您确定删除该条banner图吗？", function (r){
			if(r){
				var params={};
				params['id']=id;
				$.ajax({
					url : ctx + '/application/banner/management/delete',
					data : params,
					type : "post",
					dataType : "json",
					success : function(data) {
						if (data.result == false && data.message == 'timeout')
					    {
//		            		$.messager.alert("提示", "超时，请重新登录", "info");
//		            		parent.location.reload();//刷新整个页面
		            		$.messager.alert("操作提示", "登录超时, 请重新登录", "info");
		    				window.top.location = ctx + "/logout";
		    				return false;
					    }
						if(data.status=="1"){
							$.messager.alert("<span style='color: black;'>提示</span>",data.msg,"info");
							var params={};
							params['bannerType']=null;
							$('#bannerList').datagrid('load',params);
						}else{
							$.messager.alert("<span style='color: black;'>提示</span>",data.msg,"info");
						}
					}
				});
			}
		})
	};
	
	//查看图片
	$.show = function(bannerImgUrl) {
		$("#showPicture").attr("src","");
		$("#showPicture").attr("src",ctx + "/fileView/query?picUrl=" + bannerImgUrl);
		$("#showBannerPoto").window('open');
	}
	//查看活动
	$.showActivity = function(activityUrl) {
		var index = activityUrl.indexOf("="); 
		var end=activityUrl.length;
		var acUrl=activityUrl.slice(index+1,end);
		if(isNaN(acUrl)){//排除是商品地址的情况
			window.location.href=acUrl;
		}
	}
	// 重置
	$("#reset").click(function(){
		$('#bannerType2').combobox('setValue','');
		var params={};
		$('#bannerList').datagrid('load',params);
	});
	//查询
	$(".search-btn").click(function(){
		var params={};
		params['bannerType']=$('#bannerType2').combobox('getValue');
		$('#bannerList').datagrid('load',params);
	});

});