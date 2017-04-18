$(function() {
    // 解决超时重新登录Iframe嵌套的问题
    if (window != top) {
        top.location.href = location.href;
    }
    $("#tabs .tabs-inner:first").on("contextmenu",function(e){
    	 e.preventDefault();
    })
    // 显示系统时间
    clockon();
    // 初始化左侧菜单
    InitLeftMenu();
    tabClose();
    tabCloseEven();

    // 个人中心菜单
    $("#personcenter").click(function(e) {
        $('#personcentermenu').menu('show', {
            left : e.pageX,
            top : e.pageY + 10
        });
    });

    // 个人中心-修改密码
    $("#personCenterResetPwd").click(function() {
        var resetPassword = function() {
            $.ajax({
                url : ctx + "/application/rbac/user/resetpwd",
                data : {
                    "oldpassword" : $("#personCenterResetPwdDetails #oldpassword").val(),
                    "newpassword" : $("#personCenterResetPwdDetails #newpassword").val(),
                    "renewpassword" : $("#personCenterResetPwdDetails #renewpassword").val()
                },
                dataType : 'json',
                method : 'post',
                success : function(respJson) {
                    if (respJson.result == false && respJson.message == 'timeout') {
                        window.top.location = ctx + "/logout";
                        return;
                    }
                    if (respJson.status == 1) {
                    	$("#personCenterResetPwdDetails").dialog("close");
                        $.messager.alert("操作提示", '更改成功,请使用新密码重新登录', "info",function(){
                        	window.location.href=ctx+"/returnIndex";
                        });
                    } else {
                        $.messager.alert("操作提示", respJson.msg, "error");
                    }
                }
            });
        }
        $("#personCenterResetPwdDetails #oldpassword").val('');
        $("#personCenterResetPwdDetails #newpassword").val('');
        $("#personCenterResetPwdDetails #renewpassword").val('');
        $("#personCenterResetPwdDetails").dialog({
            title : "重置密码",
            width : 500,
            height : 250,
            buttons : [{
                        text : "确定提交 ",
                        handler : function() {
                            resetPassword();
                        }
                    }, {
                        text : "关闭",
                        handler : function() {
                            $("#personCenterResetPwdDetails").dialog("close");
                        }
                    }]
        });
    });

    // 个人中心-基本信息
    $("#personCenterResetBasic").click(function() {
        $.ajax({
            url : ctx + "/application/rbac/user/loadbasic",
            dataType : 'json',
            success : function(respJson) {
                if (respJson.result == false && respJson.message == 'timeout') {
                    window.top.location = ctx + "/logout";
                    return;
                }
                var modifyBasicInfo = function() {
                    $.ajax({
                        url : ctx + "/application/rbac/user/savebasic",
                        data : {
                            "realname" : $("#personCenterResetBasicDetails #realname").val(),
                            "mobile" : $("#personCenterResetBasicDetails #mobile").val(),
                            "email" : $("#personCenterResetBasicDetails #email").val()
                        },
                        dataType : 'json',
                        method : 'post',
                        success : function(respJson) {
                            if (respJson.result == false && respJson.message == 'timeout') {
                                window.top.location = ctx + "/logout";
                                return;
                            }
                            if (respJson.status == 1) {
                                $.messager.alert("操作提示", '更改成功', "info");
                            } else {
                                $.messager.alert("操作提示", respJson.msg, "error");
                            }
                        }
                    });
                }
                $("#personCenterResetBasicDetails #realname").val(respJson.data.realName);
                $("#personCenterResetBasicDetails #mobile").val(respJson.data.mobile);
                $("#personCenterResetBasicDetails #email").val(respJson.data.email);
                $("#personCenterResetBasicDetails").dialog({
                    title : "修改基本信息",
                    width : 500,
                    height : 250,
                    buttons : [{
                                text : "确定提交 ",
                                handler : function() {
                                    modifyBasicInfo();
                                }
                            }, {
                                text : "关闭",
                                handler : function() {
                                    $("#personCenterResetBasicDetails").dialog("close");
                                }
                            }]
                });
            }
        });

    });
});

// 初始化左侧
function InitLeftMenu() {
    $("#leftMenu").tree({
        animate : true,
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + "/application/security/loginmenus",
                data : param,
                type : "get",
                dataType : "json",
                success : function(resp) {
                    if (resp.result == false && resp.message == 'timeout') {
                        window.top.location = ctx + "/logout";
                        return;
                    }
                    success(resp.data);
                }
            })
        },
        onClick : function(node) {
            if (node.url) {
                addTab(node.text, ctx + node.url);
            }
        }
    });
}

function addTab(subtitle, url) {
    if (!$('#tabs').tabs('exists', subtitle)) {
        $('#tabs').tabs('add', {
            title : subtitle,
            content : createFrame(url),
            closable : true,
            width : $('#mainPanle').width() - 10,
            height : $('#mainPanle').height() - 26
        });
    } else {
        $('#tabs').tabs('select', subtitle);
    }
    tabClose();
}

function createFrame(url) {
    var s = '<iframe name="mainFrame" scrolling="auto" frameborder="0"  src="' + url
            + '" style="width:100%;height:100%;"></iframe>';
    return s;
}

function tabClose() {
    /* 双击关闭TAB选项卡 */
	$("#tabs").on('dblclick','.tabs-inner:not(:first)',function() {
        var subtitle = $(this).children("span").text();
        $('#tabs').tabs('close', subtitle);
    });
    $("#tabs").on('contextmenu','.tabs-inner:not(:first)', function(e) {
        $('#mm').menu('show', {
            left : e.pageX,
            top : e.pageY
        });

        var subtitle = $(this).children("span").text();
        $('#mm').data("currtab", subtitle);

        return false;
    });
}

// 绑定右键菜单事件
function tabCloseEven() {
    // 关闭当前
    $('#mm-tabclose').click(function() {
        var currtab_title = $('#mm').data("currtab");
        $('#tabs').tabs('close', currtab_title);
    });
    // 全部关闭
    $('#mm-tabcloseall').click(function() {
        $('.tabs-inner:not(:first) span').each(function(i, n) {
            var t = $(n).text();
            $('#tabs').tabs('close', t);
        });
    });
    // 关闭除当前之外的TAB
    $('#mm-tabcloseother').click(function() {
        var currtab_title = $('#mm').data("currtab");
        $('.tabs-inner:not(:first) span').each(function(i, n) {
            var t = $(n).text();
            if (t != currtab_title)
                $('#tabs').tabs('close', t);
        });
        $('#tabs').tabs('select',currtab_title); 
    });
    // 关闭当前右侧的TAB
    $('#mm-tabcloseright').click(function() {
        var nextall = $('.tabs-selected').nextAll();
        if (nextall.length == 0) {
            return false;
        }
        nextall.each(function(i, n) {
            var t = $('a:eq(0) span', $(n)).text();
            $('#tabs').tabs('close', t);
        });
        return false;
    });
    // 关闭当前左侧的TAB
    $('#mm-tabcloseleft').click(function() {
        var prevall = $('.tabs-selected').prevUntil(".tabs-first");
        if (prevall.length == 0) {
            return false;
        }
        prevall.each(function(i, n) {
            var t = $('a:eq(0) span', $(n)).text();
            $('#tabs').tabs('close', t);
        });
        return false;
    });

    // 退出
    $("#mm-exit").click(function() {
        $('#mm').menu('hide');
    });
}
// 弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
    $.messager.alert(title, msgString, msgType);
}
function clockon() {
    var now = new Date();
    var year = now.getFullYear(); // getFullYear getYear
    var month = now.getMonth();
    var date = now.getDate();
    var day = now.getDay();
    var hour = now.getHours();
    var minu = now.getMinutes();
    var sec = now.getSeconds();
    var week;
    month = month + 1;
    if (month < 10)
        month = "0" + month;
    if (date < 10)
        date = "0" + date;
    if (hour < 10)
        hour = "0" + hour;
    if (minu < 10)
        minu = "0" + minu;
    if (sec < 10)
        sec = "0" + sec;
    var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
    week = arr_week[day];
    var time = "";
    time = year + "年" + month + "月" + date + "日" + " " + hour + ":" + minu + ":" + sec + " " + week;
    $("#bgclock").html(time);
    setTimeout("clockon()", 200);
}
