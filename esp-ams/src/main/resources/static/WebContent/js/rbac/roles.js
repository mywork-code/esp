/**
 * RBAC - Users
 */
$(function() {
    // 列表
    $('#tablelist').datagrid({
        title : '角色列表',
        fit : true,
        fitColumns : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        striped:true,
        toolbar : '#tb',
        columns : [[{
                    title : 'ID',
                    hidden : true,
                    field : 'id',
                    width : 150,
                    align : 'center',
                    align : 'center'
                }, {
                    title : '角色编码',
                    field : 'roleCode',
                    width : 50,
                    align : 'left'
                }, {
                    title : '角色名称',
                    field : 'roleName',
                    width : 50,
                    align : 'left'
                }, {
                    title : '角色描述',
                    field : 'description',
                    width : 150,
                    align : 'left'
                }, {
                    title : '操作',
                    field : 'opt',
                    width : 100,
                    align : 'center',
                    formatter : function(value, row, index) {
                        var content = "";
                        // 编辑
                        content += "<a href='javascript:void(0);' class='easyui-linkedbutton'";
                        content += " onclick='$.editDetails(\"" + row.id + "\");'>编辑</a>&nbsp;";
                        // 删除
                        content += "<a href='javascript:void(0);' class='easyui-linkedbutton'";
                        content += " onclick='$.deleteDetails(\"" + row.id + "\");'>删除</a>&nbsp;";
                        // 角色资源设置
                        content += "<a href='javascript:void(0);' class='easyui-linkedbutton'";
                        content += " onclick='$.rolePermissionSettings(\"" + row.id + "\");'>角色资源设置</a>&nbsp;";
                        // 角色菜单设置
                        content += "<a href='javascript:void(0);' class='easyui-linkedbutton'";
                        content += " onclick='$.roleMenuSettings(\"" + row.id + "\");'>角色菜单设置</a>";
                        return content;
                    }
                }]],

        loader : function(param, success, error) {
            $.ajax({
                url : ctx + '/application/rbac/role/pagelist',
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

    // 查询
    $(".search-btn").click(function() {
        $('#tablelist').datagrid('load', {
            'roleName' : $("#roleName").textbox('getValue'),
            'roleCode' : $("#roleCode").textbox('getValue')
        });
    });

    // 添加按钮事件
    $(".add-btn").click(function() {
        $('#roleForm').form('load', {
            id : '',
            roleCode : '',
            roleName : '',
            description : ''
        });
        $("#roleDetails").dialog({
        	modal:true,
            title : "添加角色",
            width : 500,
            height : 250,
            buttons : [{
                        text : "确定",
                        handler : function() {
                            $("#roleForm").submit();
                        }
                    }, {
                        text : "关闭",
                        handler : function() {
                            $("#roleDetails").dialog("close");
                        }
                    }]
        });
    });

    // 表单提交
    $("#roleForm").form({
        url : ctx + "/application/rbac/role/save",
        method : 'POST',
        onSubmit : function() {
            var roleCode = $("#roleForm #roleCode").val();
            if ($.trim(roleCode) == "") {
                $.messager.alert("操作提示", "角色编码不能为空", "warning");
                return false;
            }
            var roleName = $("#roleForm #roleName").val();
            if ($.trim(roleName) == "") {
                $.messager.alert("操作提示", "角色名称不能为空", "warning");
                return false;
            }
        },
        success : function(data) {
            var respJson = $.parseJSON(data);
            $.validateResponse(respJson, function() {
                $("#roleDetails").dialog("close");
                $(".search-btn").click();
            });
        }
    });

    // 编辑
    $.editDetails = function(roleId) {
        var address = ctx + '/application/rbac/role/load';
        $.getJSON(address, {
            "roleId" : roleId
        }, function(resp) {
            $.validateResponse(resp, function() {
                $('#roleForm').form('load', resp.data);
                $("#roleDetails").dialog({
                	modal:true,
                    title : "编辑角色",
                    width : 500,
                    height : 250,
                    buttons : [{
                                text : "确定",
                                handler : function() {
                                    $("#roleForm").submit();
                                }
                            }, {
                                text : "关闭",
                                handler : function() {
                                    $("#roleDetails").dialog("close");
                                }
                            }]
                });
            });
        });
    }

    // 删除
    $.deleteDetails = function(roleId) {
        $.messager.confirm('删除确认', '确定要删除么?删除后不可恢复', function(r) {
            if (!r) {
                return;
            }
            var address = ctx + '/application/rbac/role/delete';
            $.getJSON(address, {
                "roleId" : roleId
            }, function(resp) {
                $.validateResponse(resp, function() {
                    $(".search-btn").click();
                });
            });
        });
    }

    // 角色菜单设置
    $.roleMenuSettings = function(roleId) {
        $('#menutablelist').tree({
            idField : 'id',
            treeField : 'text',
            checkbox : true,
            queryParams : {
                "roleId" : roleId
            },
            loader : function(param, success, error) {
                $.ajax({
                    url : ctx + "/application/rbac/role/load/rolemenu/settings",
                    data : param,
                    type : "post",
                    dataType : "json",
                    success : function(resp) {
                        $.validateResponse(resp, function() {
                            success(resp.data);
                        });
                    }
                })
            }
        });
        var saveSettings = function() {
            var nodes = $('#menutablelist').tree('getChecked', ['checked', 'indeterminate']);
            var array = new Array();
            $(nodes).each(function(index, item) {
                array.push(item.id);
            });
            $.ajax({
                url : ctx + '/application/rbac/role/save/rolemenu/settings',
                method : 'POST',
                dataType : 'json',
                cache : false,
                data : {
                    "menus" : array.join(","),
                    "roleId" : roleId
                },
                success : function(resp) {
                    $.validateResponse(resp, function() {
                        $('#menutablelist').tree("reload");
                    });
                },
                error : function() {
                    $.messager.alert("操作提示", "保存失败", "error");
                }
            });
        }
        $("#roleMenuDetails").dialog({
        	modal:true,
            title : "角色菜单设置",
            width : 300,
            height : 500,
            buttons : [{
                        text : "保存更新 ",
                        handler : function() {
                            saveSettings();
                        }
                    }, {
                        text : "刷新",
                        handler : function() {
                            $('#menutablelist').tree("reload");
                        }
                    }, {
                        text : "关闭",
                        handler : function() {
                            $("#roleMenuDetails").dialog("close");
                        }
                    }]
        });
    }

    // 角色资源分配
    $.rolePermissionSettings = function(roleId) {
        $("#permissionSrcList").datagrid({
            title : '可分配资源列表',
            fit : true,
            fitColumns : true,
            rownumbers : true,
            pagination : false,
            checkbox : true,
            striped:true,
            queryParams : {
                'roleId' : roleId
            },
            tools : [{
                        iconCls : 'icon-add',
                        handler : function() {
                            $("#permissionSrcList").datagrid("load");
                            $("#permissionDestList").datagrid("load");
                        }
                    }],
            columns : [[{
                        title : 'ID',
                        hidden : true,
                        field : 'id',
                        align : 'center',
                        align : 'center'
                    }, {
                        title : '资源编码',
                        field : 'permissionCode',
                        align : 'center',
                        width : 100
                    }, {
                        title : '资源名称',
                        field : 'permissionName',
                        align : 'center',
                        width : 200
                    }]],

            loader : function(param, success, error) {
                $.ajax({
                    url : ctx + '/application/rbac/role/load/available/permissions',
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
        $("#permissionDestList").datagrid({
            title : '已分配资源列表',
            fit : true,
            fitColumns : true,
            rownumbers : true,
            pagination : false,
            checkbox : true,
            striped:true,
            method : 'post',
            queryParams : {
                'roleId' : roleId
            },
            tools : [{
                        iconCls : 'icon-add',
                        handler : function() {
                            $("#permissionSrcList").datagrid("load");
                            $("#permissionDestList").datagrid("load");
                        }
                    }],
            columns : [[{
                        title : 'ID',
                        hidden : true,
                        field : 'id',
                        align : 'center',
                        align : 'center'
                    }, {
                        title : '资源编码',
                        field : 'permissionCode',
                        align : 'center',
                        width : 100
                    }, {
                        title : '资源名称',
                        field : 'permissionName',
                        align : 'center',
                        width : 200
                    }]],

            loader : function(param, success, error) {
                $.ajax({
                    url : ctx + '/application/rbac/role/load/assigned/permissions',
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
        var saveAssignedPermissions = function() {
            var selectedRows = $("#permissionDestList").datagrid('getRows');
            console.log(selectedRows);
            var permissionArray = new Array();
            if (selectedRows && selectedRows.length > 0) {
                $(selectedRows).each(function(index, item) {
                    permissionArray.push(item.id);
                });
            }
            $.ajax({
                url : ctx + '/application/rbac/role/save/assigned/permissions',
                method : 'post',
                dataType : 'json',
                data : {
                    "roleId" : roleId,
                    "permissions" : permissionArray.join(',')
                },
                success : function(resp) {
                    $.validateResponse(resp, function() {
                        $("#permissionSrcList").datagrid("load");
                        $("#permissionDestList").datagrid("load");
                    });
                },
                error : function() {
                    $.messager.alert("操作提示", "保存资源设置失败", "info");
                }
            });
        };

        $("#rolePermissionDetails").dialog({
        	modal:true,
            title : "角色资源设置",
            width : 900,
            height : 400,
            buttons : [{
                        text : "保存更新 ",
                        handler : function() {
                            saveAssignedPermissions();
                        }
                    }, {
                        text : "刷新",
                        handler : function() {
                            $("#permissionSrcList").datagrid("load");
                            $("#permissionDestList").datagrid("load");
                        }
                    }, {
                        text : "关闭",
                        handler : function() {
                            $("#rolePermissionDetails").dialog("close");
                        }
                    }]
        });
    }

    // 左移选中记录
    $("#moveLeft").click(function() {
        var selectedRows = $("#permissionDestList").datagrid('getSelected');
        if (!selectedRows || selectedRows.length <= 0) {
            $.messager.alert("操作提示", "已分配资源列表中至少选中一条", "info");
            return false;
        }
        $(selectedRows).each(function(index, item) {
            var rowIndex = $("#permissionDestList").datagrid('getRowIndex', item);
            $("#permissionDestList").datagrid('deleteRow', rowIndex);
            $("#permissionSrcList").datagrid("appendRow", item);
        });
    });
    // 右移选中记录
    $("#moveRight").click(function() {
        var selectedRows = $("#permissionSrcList").datagrid('getSelected');
        if (!selectedRows || selectedRows.length <= 0) {
            $.messager.alert("操作提示", "可分配资源列表中至少选中一条", "info");
            return false;
        }
        $(selectedRows).each(function(index, item) {
            var rowIndex = $("#permissionSrcList").datagrid('getRowIndex', item);
            $("#permissionSrcList").datagrid('deleteRow', rowIndex);
            $("#permissionDestList").datagrid("appendRow", item);
        });
    });
    // 全部左移
    $("#moveAllLeft").click(function() {
        var selectedRows = $("#permissionDestList").datagrid('getRows');
        if (!selectedRows || selectedRows.length <= 0) {
            $.messager.alert("操作提示", "已分配资源列表中已无记录", "info");
            return false;
        }
        $(selectedRows).each(function(index, item) {
            var rowIndex = $("#permissionDestList").datagrid('getRowIndex', item);
            $("#permissionDestList").datagrid('deleteRow', rowIndex);
            $("#permissionSrcList").datagrid("appendRow", item);
        });
    });
    // 全部右移动
    $("#moveAllRight").click(function() {
        var selectedRows = $("#permissionSrcList").datagrid('getRows');
        if (!selectedRows || selectedRows.length <= 0) {
            $.messager.alert("操作提示", "可分配资源列表中已经无记录", "info");
            return false;
        }
        $(selectedRows).each(function(index, item) {
            var rowIndex = $("#permissionSrcList").datagrid('getRowIndex', item);
            $("#permissionSrcList").datagrid('deleteRow', rowIndex);
            $("#permissionDestList").datagrid("appendRow", item);
        });
    });
});