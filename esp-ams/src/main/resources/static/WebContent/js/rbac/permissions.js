/**
 * RBAC - Users
 */
$(function() {
    // 列表
    $('#tablelist').datagrid({
        title : '资源列表',
        // url : ctx + '/application/rbac/permission/pagelist',
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
                    align : 'left'
                }, {
                    title : '资源编码',
                    field : 'permissionCode',
                    width : 100,
                    align : 'left'
                }, {
                    title : '资源名称',
                    field : 'permissionName',
                    width : 100,
                    align : 'left'
                }, {
                    title : '资源描述',
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
                        content += "<a href='javascript:void(0);' class='easyui-linkedbutton'";
                        content += " onclick='$.editDetails(\"" + row.id + "\");'>编辑</a>&nbsp;";
                        content += "<a href='javascript:void(0);' class='easyui-linkedbutton'";
                        content += " onclick='$.deleteDetails(\"" + row.id + "\");'>删除</a>";
                        return content;
                    }
                }]],

        loader : function(param, success, error) {
            $.ajax({
                url : ctx + '/application/rbac/permission/pagelist',
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
            'permissionName' : $("#permissionName").textbox('getValue'),
            'permissionCode' : $("#permissionCode").textbox('getValue')
        });
    });

    // 添加按钮事件
    $(".add-btn").click(function() {
        $('#permissionForm').form('load', {
            id : '',
            permissionCode : '',
            permissionName : '',
            description : ''
        });
        $("#permissionDetails").dialog({
        	modal:true,
            title : "添加资源",
            width : 500,
            height : 250,
            buttons : [{
                        text : "确定",
                        handler : function() {
                            $("#permissionForm").submit();
                        }
                    }, {
                        text : "关闭",
                        handler : function() {
                            $("#permissionDetails").dialog("close");
                        }
                    }]
        });
    });

    // 表单提交
    $("#permissionForm").form({
        url : ctx + "/application/rbac/permission/save",
        method : 'POST',
        iframe : false,
        ajax : true,
        onSubmit : function() {
            var permissionCode = $("#permissionForm #permissionCode").val();
            if ($.trim(permissionCode) == "") {
                $.messager.alert("警告", "资源编码不能为空", "warning");
                return false;
            }
            var permissionName = $("#permissionForm #permissionName").val();
            if ($.trim(permissionName) == "") {
                $.messager.alert("警告", "资源名称不能为空", "warning");
                return false;
            }
        },
        success : function(data) {
            var respJson = $.parseJSON(data);
            $.validateResponse(respJson, function() {
                $("#permissionDetails").dialog("close");
                $(".search-btn").click();
            });
        }
    });

    // 编辑
    $.editDetails = function(permissionId) {
        var address = ctx + '/application/rbac/permission/load';
        $.getJSON(address, {
            "permissionId" : permissionId
        }, function(resp) {
            $.validateResponse(resp, function() {
                $('#permissionForm').form('load', resp.data);
                $("#permissionDetails").dialog({
                	modal:true,
                    title : "编辑资源",
                    width : 500,
                    height : 250,
                    buttons : [{
                                text : "确定",
                                handler : function() {
                                    $("#permissionForm").submit();
                                }
                            }, {
                                text : "关闭",
                                handler : function() {
                                    $("#permissionDetails").dialog("close");
                                }
                            }]
                });
            });
        });
    }

    // 删除
    $.deleteDetails = function(permissionId) {
        $.messager.confirm('删除确认', '确定要删除么?删除后不可恢复', function(r) {
            if (!r) {
                return;
            }
            var address = ctx + '/application/rbac/permission/delete';
            $.getJSON(address, {
                "permissionId" : permissionId
            }, function(resp) {
                $.validateResponse(resp, function() {
                    $(".search-btn").click();
                });
            });
        });
    }
});