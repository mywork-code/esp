/**
 * RBAC - Users
 */
$(function() {
    // 列表
    $('#tablelist').treegrid({
    	title : '菜单管理',
    	fit : true,
        idField : 'id',
        treeField : 'text',
        fitColumns : true,
        rownumbers : true,
        singleSelect : true,
        striped:true,
        toolbar : '#tb',
        columns : [[{
                    title : '编号',
                    field : 'id',
                    hidden : true,
                    width : 50,
                    align : 'center'
                }, {
                    title : '菜单名称',
                    field : 'text',
                    width : 150,
                    align : 'left'
                }, {
                    field : 'iconCls',
                    title : '菜单图标',
                    width : 80,
                    align : 'center'
                }, {
                    field : 'url',
                    title : '菜单链接',
                    width : 150,
                    align : 'center'
                }, {
                    field : 'display',
                    title : '显示顺序',
                    width : 80,
                    align : 'center'
                }, {
                    title : '操作',
                    field : 'opt',
                    width : 80,
                    align : 'center',
                    formatter : function(value, row, index) {
                        if (row.id == 'root') {
                            return "";
                        }
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
                url : ctx + "/application/rbac/menu/pagelist",
                data : param,
                type : "GET",
                dataType : "json",
                success : function(resp) {
                    $.validateResponse(resp, function() {
                        success(resp.data);
                    });
                }
            })
        }
    });

    // 查询菜单
    $(".search-btn").click(function() {
    	debugger;
    	//var menuName = encodeURI(encodeURI($("#menuName").textbox('getValue')));
    	var x = $("#menuName").textbox('getValue');
    	var menuName = encodeURI(x);
        $('#tablelist').treegrid('load', {
            'menuName' :  menuName
        });
    });
    $("#parentCombo").combotree({
        required : true,
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + "/application/rbac/menu/pagelist",
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

    // 添加按钮事件
    $(".add-btn").click(function(parentId) {
        $('#menuForm').form('load', {
            id : '',
            text : '',
            url : '',
            iconCls : '',
            display : 1
        });
        $('#parentCombo').combotree('reload');
        $('#parentCombo').combotree('setValue', '');
        $("#menuDetails").dialog({
        	modal:true,
            title : "添加菜单",
            width : 500,
            height : 250,
            buttons : [{
                        text : "确定",
                        handler : function() {
                            $("#menuForm").submit();
                        }
                    }, {
                        text : "关闭",
                        handler : function() {
                            $("#menuDetails").dialog("close");
                        }
                    }]
        });
    });

    // 表单提交
    $("#menuForm").form({
        url : ctx + "/application/rbac/menu/save",
        method : 'POST',
        iframe : false,
        ajax : true,
        onSubmit : function() {
        	var parentCombo = $("#parentCombo").combotree("getValue")
        	if ($.trim(parentCombo) == "") {
                $.messager.alert("操作提示", "上级菜单不能为空", "warning");
                return false;
            }
            var text = $("#menuForm #text").val();
            if ($.trim(text) == "") {
                $.messager.alert("操作提示", "菜单名称不能为空", "warning");
                return false;
            }
        },
        success : function(data) {
            var respJson = $.parseJSON(data);
            $.validateResponse(respJson, function() {
                $("#menuDetails").dialog("close");
                $(".search-btn").click();
            });
        }
    });

    // 编辑
    $.editDetails = function(menuId) {
        var address = ctx + '/application/rbac/menu/load';
        $.getJSON(address, {
            "menuId" : menuId
        }, function(resp) {
            $.validateResponse(resp, function() {
                $('#menuForm').form('load', resp.data);
                $('#parentCombo').combotree('reload');
                $('#parentCombo').combotree('setValue', resp.data.parentId);
                $("#menuDetails").dialog({
                	modal:true,
                    title : "编辑角色",
                    width : 500,
                    height : 250,
                    buttons : [{
                                text : "确定",
                                handler : function() {
                                    $("#menuForm").submit();
                                }
                            }, {
                                text : "关闭",
                                handler : function() {
                                    $("#menuDetails").dialog("close");
                                }
                            }]
                });
            });
        });
    }

    // 删除
    $.deleteDetails = function(menuId) {
        $.messager.confirm('删除确认', '下级菜单会一并删除,删除后不可恢复, 确定要删除么? ', function(r) {
            if (!r) {
                return;
            }
            var address = ctx + '/application/rbac/menu/delete';
            $.getJSON(address, {
                "menuId" : menuId
            }, function(resp) {
                $.validateResponse(resp, function() {
                    $(".search-btn").click();
                });
            });
        });
    }
});
