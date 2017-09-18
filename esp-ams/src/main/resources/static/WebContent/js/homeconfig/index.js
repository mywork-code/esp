$(function(){
	
	$("#addConfig").window('close');
	$("#editConfig").window('close');
    //Grid
    $('#list').datagrid({
        title : '首页窗口配置',
        fit : true,
        fitColumns : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        striped:true,
        nowrap:false,
        toolbar : '#tb',
        columns :[[
            {
                title : '窗口名称',
                field : 'homeName',
                width : 150,
                align : 'center'
            }, {
                title : '开始时间',
                field : 'startTime',
                width : 150,
                align : 'center'
            },{
                title : '结束时间',
                field : 'endTime',
                width : 120,
                align : 'center'
            },
            {
                title : '是否有效',
                field : 'homeStatus',
                width : 120,
                align : 'center'
            },{
				title : '操作',
				field : 'opt',
				width : 120,
				align : 'center',
				formatter : function(value, row, index) {
					var content = "";
					 if (row.homeStatus != '否') {
                         content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton'";
                         content += " onclick='$.setStatus(\"" + row.id + "\");'>设为无效</a>";
                         
                         content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton'";
                         content += " onclick='$.editConfig(\"" + row.id + "\",\"" + row.homeName + "\",\"" + row.startTime + "\",\"" + row.endTime + "\",\"" + row.activeLink + "\",\"" + row.logoUrl + "\");'>编辑</a>";
                     }  
				 return content;
			}}]],
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + '/homeconfig/list',
                data : param,
                type : "post",
                dataType : "json",
                success : function(data) {
                	console.log(data);
                    $.validateResponse(data, function() {
                        success(data);
                    });
                }
            })
        }
    });
    
    $("#add").click(function () {
    	$('#configForm').form('load', {
    		homeName : '',
    		startTime : '',
    		endTime : '',
    		activeLink : '',
    		addConfigFilePic : ''
		});
         //打开弹出框
         $('#addConfig').window({
        	 minimizable:false,
             maximizable:false,
             collapsible:false,
             modal:true,
             top:$(document).scrollTop() + ($(window).height()-250) * 0.5
         });
         $('#addConfig').window('open');
     });
    
    $.editConfig = function(id,homeName,startTime,endTime,activeLink,logoUrl){
    	$("#homeNameEdit").textbox('setValue',homeName);
		$("#startTimeEdit").datetimebox('setValue',startTime); 
		$("#startTimeEdit1").datetimebox('setValue',startTime);
		$("#endTimeEdit").datetimebox('setValue',endTime); 
		$("#activeLinkEdit").textbox('setValue',activeLink);
		//$("#logoUrl").val(logoUrl);
		$("#id").val(id);
		loadPic("addShowHomePicId", logoUrl);
        $('#editConfig').window('open');
    }
    
    //取消   添加 配置
    $("#cancelAdd").click(function(){
        $("#addConfig").window('close');
    });
    //取消 修改 配置
    $("#cancelEdit").click(function(){
        $("#editConfig").window('close');
    });
    
    $("#agreeAdd").click(function(){
		var homeName = $("#homeName").textbox('getValue');
		if(homeName=='' || null==homeName){
			$.messager.alert("<span style='color: black;'>提示</span>","请填写窗口名称！","info");
			return;
		}
		var startTime=$("#startTime").textbox('getValue');
		if(startTime=='' || null==startTime){
			$.messager.alert("<span style='color: black;'>提示</span>","请填写开始时间！","info");
			return;
		}
		
		var endTime= $("#endTime").textbox('getValue');
		if(endTime=='' || null==endTime){
			$.messager.alert("<span style='color: black;'>提示</span>","请填写结束时间！","info");
			return;
		}
		
		if(startTime!=null && startTime!=''&&endTime!=null && endTime!=''){
    		if(startTime > endTime){
    			$.messager.alert("<span style='color: black;'>提示</span>","开始时间填写错误，请重新填写！",'info');
    			return;
    		}
    		if(endTime < new Date().Format("yyyy-MM-dd hh:mm:ss")){
    			$.messager.alert("<span style='color: black;'>提示</span>","结束时间填写错误，请重新填写！",'info');
    			return;
    		}
    	}
		
		var activeLink= $("#activeLink").textbox('getValue');
		if(activeLink=='' || null==activeLink){
			$.messager.alert("<span style='color: black;'>提示</span>","请填写活动链接！","info");
			return;
		}
		
		var addConfigFilePic= $("#addConfigFilePic").val();
		if(addConfigFilePic=='' || null==addConfigFilePic){
			$.messager.alert("<span style='color: black;'>提示</span>","请上传图片！","info");
			return;
		}
		//提交from
		var theForm = $("#configForm");
		theForm.form("submit",{ 
			url : ctx + '/homeconfig/addconfig',
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
					$("#addConfig").dialog("close");
					var param = {};
		            $('#list').datagrid('load', param);
				}else{
					$.messager.alert("<span style='color: black;'>警告</span>",respon.msg,"warning");
				}
			}
		});
	});
    
    $("#agreeEdit").click(function(){
		var homeName = $("#homeNameEdit").textbox('getValue');
		if(homeName=='' || null==homeName){
			$.messager.alert("<span style='color: black;'>提示</span>","请填写窗口名称！","info");
			return;
		}
		var startTime=$("#startTimeEdit1").textbox('getValue');
		if(startTime=='' || null==startTime){
			$.messager.alert("<span style='color: black;'>提示</span>","请填写开始时间！","info");
			return;
		}
		
		var endTime= $("#endTimeEdit").textbox('getValue');
		if(endTime=='' || null==endTime){
			$.messager.alert("<span style='color: black;'>提示</span>","请填写结束时间！","info");
			return;
		}
		
		if(startTime!=null && startTime!=''&&endTime!=null && endTime!=''){
    		if(startTime > endTime){
    			$.messager.alert("<span style='color: black;'>提示</span>","开始时间应早于结束时间！",'info');
    			return;
    		}
    		if(endTime < new Date().Format("yyyy-MM-dd hh:mm:ss")){
    			$.messager.alert("<span style='color: black;'>提示</span>","活动时间：结束时间不能小于当前时间！",'info');
    			return;
    		}
    	}
		
		var activeLink= $("#activeLinkEdit").textbox('getValue');
		if(activeLink=='' || null==activeLink){
			$.messager.alert("<span style='color: black;'>提示</span>","请填写活动链接！","info");
			return;
		}
		
		//提交from
		var theForm = $("#configFormEdit");
		theForm.form("submit",{ 
			url : ctx + '/homeconfig/editconfig',
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
					$("#editConfig").dialog("close");
					var param = {};
		            $('#list').datagrid('load', param);
				}else{
					$.messager.alert("<span style='color: black;'>警告</span>",respon.msg,"warning");
				}
			}
		});
	});
    
    $.setStatus = function(id){
    	$.messager.confirm("<span style='color: black;'>操作提示</span>", "您确定将该条信息设为无效？", function (r) {
            if (r) {
            	$.ajax({
                    url: ctx + '/homeconfig/editstatus',
                    data: {"id":id},
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        if (data.status == "1") {
                            $.messager.alert("<span style='color: black;'>提示</span>", data.msg, 'info');
                            var params = {};
            	            $('#list').datagrid('load', params);
                        } else {
                            $.messager.alert("<span style='color: black;'>错误</span>", data.msg, 'error');
                        }
                    }
                });
            }
        });
    }
});
function loadPic(id, pictureUrl) {
	if (pictureUrl != null) {
		$("#" + id).attr("src", "");
		$("#" + id).attr("src", ctx + "/fileView/query?picUrl=" + pictureUrl);
	} else {
		$("#" + id).attr("src", '');
	}
}