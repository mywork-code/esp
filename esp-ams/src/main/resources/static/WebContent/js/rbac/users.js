/**
 * RBAC - Users
 */
$(function() {
	$('#relevanceMerchantInfor').window('close');
	// Grid 列表
	$('#tablelist').datagrid({
		title : '系统用户列表',
		fit : true,
		fitColumns : true,
		rownumbers : true,
		pagination : true,
		singleSelect : true,
		striped : true,
		toolbar : '#tb',
		columns : [ [ {
			title : 'ID',
			hidden : true,
			field : '用户编号',
			width : 150,
			align : 'center',
			align : 'center'
		}, {
			title : '用户账号',
			field : 'userName',
			width : 50,
			align : 'center'
		}, {
			title : '真实姓名',
			field : 'realName',
			width : 50,
			align : 'center'
		}, {
			title : '手机',
			field : 'mobile',
			width : 50,
			align : 'center'
		}, {
			title : '邮箱',
			field : 'email',
			width : 50,
			align : 'center'
		}, {
			title : '状态',
			field : 'status',
			width : 50,
			align : 'center',
			formatter : function(value, row, index) {
				return value == '1' ? "有效" : "无效";
			}
		}, {
			title : '商户名称',
			field : 'merchantName',
			width : 50,
			align : 'center'
		}, {
			title : '操作',
			field : 'opt',
			width : 100,
			align : 'center',
			formatter : function(value, row, index) {
				var content = "";
				content += "<a href='javascript:void(0);' class='easyui-linkedbutton'";
				content += "onclick='$.deleteDetails(\"" + row.id + "\");'>删除用户</a>";

				content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton'";
				content += "onclick='$.resetPassword(\"" + row.id + "\", \"" + row.userName + "\");'>重置密码</a>";

				content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton'";
				content += "onclick='$.userRolesSettings(\"" + row.id + "\");'>用户角色设置</a>";

				content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton'";
				content += "onclick='$.relevanceMerchantSettings(\"" + row.id + "\",\"" + row.merchantCode + "\");'>关联商户</a>";
				return content;
			}
		} ] ],

		loader : function(param, success, error) {
			//debugger;
			$.ajax({
				url : ctx + '/application/rbac/user/pagelist',
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
		params['username'] = $("#username").textbox('getValue');
		params['realName'] = $("#realName").textbox('getValue');
		$('#tablelist').datagrid('load', params);
	});

	// 用户角色设置
	$.userRolesSettings = function(userId) {
		$("#roleSrcList").datagrid({
			title : '可分配角色列表',
			fit : true,
			fitColumns : true,
			rownumbers : true,
			pagination : false,
			checkbox : true,
			striped : true,
			queryParams : {
				'userId' : userId
			},
			tools : [ {
				iconCls : 'icon-add',
				handler : function() {
					$("#roleSrcList").datagrid("load");
					$("#roleDestList").datagrid("load");
				}
			} ],
			columns : [ [ {
				title : 'ID',
				hidden : true,
				field : 'id',
				align : 'left'
			}, {
				title : '角色编码',
				field : 'roleCode',
				align : 'left',
				width : 100
			}, {
				title : '角色名称',
				field : 'roleName',
				align : 'left',
				width : 200
			} ] ],

			loader : function(param, success, error) {
				$.ajax({
					url : ctx + '/application/rbac/user/load/available/roles',
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
		$("#roleDestList").datagrid({
			title : '已分配角色列表',
			fit : true,
			fitColumns : true,
			rownumbers : true,
			pagination : false,
			checkbox : true,
			striped : true,
			method : 'post',
			queryParams : {
				'userId' : userId
			},
			tools : [ {
				iconCls : 'icon-add',
				handler : function() {
					$("#roleSrcList").datagrid("load");
					$("#roleDestList").datagrid("load");
				}
			} ],
			columns : [ [ {
				title : 'ID',
				hidden : true,
				field : 'id',
				align : 'left'
			}, {
				title : '角色编码',
				field : 'roleCode',
				align : 'center',
				width : 100
			}, {
				title : '角色名称',
				field : 'roleName',
				align : 'left',
				width : 200
			} ] ],

			loader : function(param, success, error) {
				$.ajax({
					url : ctx + '/application/rbac/user/load/assigned/roles',
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
		// 保存分配資源
		var saveAssignedRoles = function() {
			var selectedRows = $("#roleDestList").datagrid('getRows');
			var srcArray = new Array();
			if (selectedRows && selectedRows.length > 0) {
				$(selectedRows).each(function(index, item) {
					srcArray.push(item.id);
				});
			}
			$.ajax({
				url : ctx + '/application/rbac/user/save/assigned/roles',
				method : 'post',
				dataType : 'json',
				data : {
					"userId" : userId,
					"roles" : srcArray.join(',')
				},
				success : function(resp) {
					$.validateResponse(resp, function() {
						$("#roleSrcList").datagrid("load");
						$("#roleDestList").datagrid("load");
					});
				},
				error : function() {
					$.messager.alert("操作提示", "保存资源设置失败", "info");
				}
			});
		};
		$("#userRoleDetails").dialog({
			modal : true,
			title : "用户角色设置",
			width : 900,
			height : 400,
			buttons : [ {
				text : "保存更新 ",
				handler : function() {
					saveAssignedRoles();
				}
			}, {
				text : "刷新",
				handler : function() {
					$("#roleSrcList").datagrid("load");
					$("#roleDestList").datagrid("load");
				}
			}, {
				text : "关闭",
				handler : function() {
					$("#userRoleDetails").dialog("close");
				}
			} ]
		});
	},

	// 左移选中记录
	$("#moveLeft").click(function() {
		var selectedRows = $("#roleDestList").datagrid('getSelected');
		if (!selectedRows || selectedRows.length <= 0) {
			$.messager.alert("操作提示", "已分配角色列表中至少选中一条", "info");
			return false;
		}
		$(selectedRows).each(function(index, item) {
			var rowIndex = $("#roleDestList").datagrid('getRowIndex', item);
			$("#roleDestList").datagrid('deleteRow', rowIndex);
			$("#roleSrcList").datagrid("appendRow", item);
		});
	});
	// 右移选中记录
	$("#moveRight").click(function() {
		var selectedRows = $("#roleSrcList").datagrid('getSelected');
		if (!selectedRows || selectedRows.length <= 0) {
			$.messager.alert("操作提示", "可分配角色列表中至少选中一条", "info");
			return false;
		}
		$(selectedRows).each(function(index, item) {
			var rowIndex = $("#roleSrcList").datagrid('getRowIndex', item);
			$("#roleSrcList").datagrid('deleteRow', rowIndex);
			$("#roleDestList").datagrid("appendRow", item);
		});
	});
	// 全部左移
	$("#moveAllLeft").click(function() {
		var selectedRows = $("#roleDestList").datagrid('getRows');
		if (!selectedRows || selectedRows.length <= 0) {
			$.messager.alert("操作提示", "已分配角色列表中已无记录", "info");
			return false;
		}
		$(selectedRows).each(function(index, item) {
			var rowIndex = $("#roleDestList").datagrid('getRowIndex', item);
			$("#roleDestList").datagrid('deleteRow', rowIndex);
			$("#roleSrcList").datagrid("appendRow", item);
		});
	});
	// 全部右移动
	$("#moveAllRight").click(function() {
		var selectedRows = $("#roleSrcList").datagrid('getRows');
		if (!selectedRows || selectedRows.length <= 0) {
			$.messager.alert("操作提示", "可分配角色列表中已经无记录", "info");
			return false;
		}
		$(selectedRows).each(function(index, item) {
			var rowIndex = $("#roleSrcList").datagrid('getRowIndex', item);
			$("#roleSrcList").datagrid('deleteRow', rowIndex);
			$("#roleDestList").datagrid("appendRow", item);
		});
	});

	// 添加按钮事件
	$(".add-btn").click(function() {
		$('#userForm').form('load', {
			username : '',
			password : '',
			repassword : '',
			email : '',
			mobile : '',
			realName : ''
		});
		$("#userDetails").dialog({
			modal : true,
			title : "添加用户",
			width : 500,
			height : 250,
			buttons : [ {
				text : "确定",
				handler : function() {
					$("#userForm").submit();
				}
			}, {
				text : "关闭",
				handler : function() {
					$("#userDetails").dialog("close");
				}
			} ]
		});
	});

	// 表单提交
	$("#userForm").form({
		url : ctx + "/application/rbac/user/save",
		method : 'POST',
		onSubmit : function() {
			var username = $("#userForm #username").val();
			if ($.trim(username) == "") {
				$.messager.alert("操作提示", "账号不能为空", "warning");
				return false;
			}
			var password = $("#userForm #password").val();
			if ($.trim(password) == "") {
				$.messager.alert("操作提示", "密码不能为空", "warning");
				return false;
			}
			var repassword = $("#userForm #repassword").val();
			if (password != repassword) {
				$.messager.alert("操作提示", "密码跟确认密码不一致", "warning");
				return false;
			}
			var mobile = $("#userForm #mobile").val();
			if ($.trim(mobile) == "") {
				$.messager.alert("操作提示", "手机号码不能为空", "warning");
				return false;
			}
			var email = $("#userForm #email").val();
			if ($.trim(email) == "") {
				$.messager.alert("操作提示", "邮箱不能为空", "warning");
				return false;
			}
		},
		success : function(data) {
			var respJson = $.parseJSON(data);
			$.validateResponse(respJson, function() {
				$("#userDetails").dialog("close");
				$(".search-btn").click();
			});
		}
	});

	// 删除
	$.deleteDetails = function(userId) {
		$.messager.confirm('删除确认', '确定要删除么?删除后不可恢复', function(r) {
			if (!r) {
				return;
			}
			var address = ctx + '/application/rbac/user/delete';
			$.getJSON(address, {
				"userId" : userId
			}, function(resp) {
				$.validateResponse(resp, function() {
					$(".search-btn").click();
				});
			});
		});
	};

	// 重置密码
	$.resetPassword = function(userId, username) {
		var forceResetPassword = function() {
			$.ajax({
				url : ctx + "/application/rbac/user/forceresetpwd",
				data : {
					"username" : username,
					"newpassword" : $("#resetPasswordDetails #newpassword").val(),
					"renewpassword" : $("#resetPasswordDetails #renewpassword").val()
				},
				dataType : 'json',
				method : 'post',
				success : function(respJson) {
					$.validateResponse(respJson, function() {
						$("#resetPasswordDetails").dialog("close");
						$(".search-btn").click();
						//返回登录页
						//      window.location.href=ctx+"/returnIndex";
					});
				}
			});
		}
		$("#resetPasswordDetails #username").val(username + " ");
		$("#resetPasswordDetails #newpassword").val('');
		$("#resetPasswordDetails #renewpassword").val('');
		$("#resetPasswordDetails").dialog({
			modal : true,
			title : "重置密码",
			width : 500,
			height : 250,
			buttons : [ {
				text : "确定提交 ",
				handler : function() {
					forceResetPassword();
				}
			}, {
				text : "关闭",
				handler : function() {
					$("#resetPasswordDetails").dialog("close");
				}
			} ]
		});
	}
	//用户id
	var id = null;
	//关联商户
	$.relevanceMerchantSettings = function(userId,merchantCode) {
		debugger;
		if (userId) {
			//debugger;
			id = userId;
		}
		//弹出页面背景变灰，不可操作
		$('#relevanceMerchantInfor').window({
			shadow : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			modal : true
		});
		//打开编辑弹出框
		$('#relevanceMerchantInfor').window('open');

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
						if(null == merchantCode ||'null' == merchantCode || ("") == merchantCode){
							$("#selectMerchant").combobox('setValue','');
						}else{
							$("#selectMerchant").combobox('setValue', merchantCode);
						}
					}
				})
			}
		});
	}
	//确认  关联商户
	$("#agreeEdit").click(function() {
		debugger;
		var merchantCode = $("#selectMerchant").combobox('getValue');
//		if (null == merchantCode || ("") == merchantCode) {
//			$.messager.alert("提示","商户编码不能为空！","info");
//			return;
//		}

		var params = {
			id : id,
			merchantCode : merchantCode
		};

		//关闭弹出框
		$('#relevanceMerchantInfor').window('close');
		$.ajax({
			url : ctx + '/application/rbac/user/relevanceMerchant',
			data : params,
			type : "post",
			dataType : "json",
			success : function(data) {
				if (data.status == "1") {
					// debugger;
					if(merchantCode!=null && merchantCode!=''){
						$.messager.alert("提示",data.msg,"info");
					}else{
						$.messager.alert("提示","解除商户关联成功","info");
					}
					var params = {};
					params['username'] = $("#username").textbox('getValue');
					params['realName'] = $("#realName").textbox('getValue');
					$('#tablelist').datagrid('load', params);
				} else {
					$.messager.alert("提示",data.msg,"info");
				}
			}
		});
	});
	//取消  编辑商户信息
	$("#cancelEdit").click(function() {
		$('#relevanceMerchantInfor').window('close');
	});

});