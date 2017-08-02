/**
 * Created by xianzhi.wang on 2017/6/19 .
 */
$(function () {
    $("#addIntroConfig").window('close');
//Grid
    $('#list').datagrid({
        title: '自动发货时间设置',
        fit: true,
        fitColumns: true,
        rownumbers: true,
        pagination: true,
        singleSelect: true,
        striped: true,
        toolbar: '#tb',
        columns: [[
            {
                title: '第一时间点',
                field: 'time1',
                width: 150,
                align: 'center',
                formatter: function (value, row, index) {
                    return "<span title='" + value + "'>" + value + "</span>"
                }
            },
            {
                title: '第二时间点',
                field: 'time2',
                width: 150,
                align: 'center',
                formatter: function (value, row, index) {
                    return "<span title='" + value + "'>" + value + "</span>"
                }
            },
            {
                title: '第三时间点',
                field: 'time3',
                width: 150,
                align: 'center',
                formatter: function (value, row, index) {
                    return "<span title='" + value + "'>" + value + "</span>"
                }
            }
            ,
            {
                title: '第四时间点',
                field: 'time4',
                width: 150,
                align: 'center',
                formatter: function (value, row, index) {
                    return "<span title='" + value + "'>" + "23:59:59" + "</span>"
                }
            }
            ,
            {
                title: '操作',
                field: 'opt',
                width: 150,
                align: 'center',
                formatter: function (value, row, index) {
                    var content = "";
                    content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton' onclick=\"$.edit('"
                        + index + "');\">修改</a>&nbsp;&nbsp;"
                    return content;
                }
            }
        ]],
        loader: function (param, success, error) {
            $.ajax({
                url: ctx + '/application/autoship/management/query',
                data: param,
                type: "get",
                dataType: "json",
                success: function (data) {
                    $.validateResponse(data, function () {
                        success(data);
                    });
                }
            })
        }
    });


    $.edit = function (index) {
        $('#addIntroConfig').window({
            minimizable: false,
            maximizable: false,
            collapsible: false,
            modal: true,
            top: $(document).scrollTop() + ($(window).height() - 250) * 0.5
        });
        var rowData = $('#list').datagrid('getData').rows[index];

        $('#time1').textbox('setValue', rowData.time1);
        $('#time2').textbox('setValue', rowData.time2);
        $('#time3').textbox('setValue', rowData.time3);
        $('#time4').textbox('setValue', rowData.time4);
        $("#addIntroConfig").window('open');
    }


    //
    $("#agree").click(function () {

        var time1 = $('#time1').textbox('getValue');
        var time2 = $('#time2').textbox('getValue');
        var time3 = $('#time3').textbox('getValue');
        var time4 = $('#time4').textbox('getValue');
        var flag= /^[0-2][0-9]:[0-5][0-9]:[0-5][0-9]$/.test(time1);
        var flag2= /^[0-2][0-9]:[0-5][0-9]:[0-5][0-9]$/.test(time2);
        var flag3= /^[0-2][0-9]:[0-5][0-9]:[0-5][0-9]$/.test(time3);

        if(!flag||time1.split(":")[0]>=24){
            $.messager.alert("<span style='color: black;'>提示</span>", "第一个时间节点输入错误，请重新输入");
            return;
        }
        if(!flag2||time2.split(":")[0]>=24){
            $.messager.alert("<span style='color: black;'>提示</span>", "第二个时间节点输入错误，请重新输入");
            return;
        }
        if(!flag3||time3.split(":")[0]>=24){
            $.messager.alert("<span style='color: black;'>提示</span>", "第三个时间节点输入错误，请重新输入");
            return;
        }
        if(time1==time2||time1==time3||time2==time3||time1=="23:59:59"||time2=="23:59:59"||time3=="23:59:59"){
            $.messager.alert("<span style='color: black;'>提示</span>", "时间节点不能重复，请重新输入");
            return;
        }
        var param = {
            time1: time1,
            time2: time2,
            time3: time3,
            time4: time4
        };
        $.ajax({
            url: ctx + "/application/autoship/management/update",
            data: JSON.stringify(param),
            type: "POST",
            contentType: 'application/json',
            dataType: "json",
            success: function (data) {
                $('#list').datagrid('load', {});
            }
        })
        $('#addIntroConfig').window('close');
    });

    //取消
    $("#cancel").click(function () {
        $('#addIntroConfig').window('close');
    });

    $("#time1").textbox('textbox').bind('keyup', function (event) {
        var e = event || window.event;
       // var k = e.keyCode || e.which;
       // var timeValue = $("#time1").textbox('getValue');
        var val = $(this).get(0).value;
        if(!/^[0-9]{0}([0-9]|[:])+$/.test(val)||$(this).get(0).value.length > 8){//含有数字和：以外的字符，则执行
            $("#time1").textbox('setValue', val.substr(0,val.length-1));
            event.preventDefault();
            return false
        }
    });

    $("#time2").textbox('textbox').bind('keyup', function (event) {
        var e = event || window.event;
        // var k = e.keyCode || e.which;
        // var timeValue = $("#time1").textbox('getValue');
        var val = $(this).get(0).value;
        if(!/^[0-9]{0}([0-9]|[:])+$/.test(val)||$(this).get(0).value.length > 8){//含有数字和：以外的字符，则执行
            $("#time2").textbox('setValue', val.substr(0,val.length-1));
            event.preventDefault();
            return false
        }
    });

    $("#time3").textbox('textbox').bind('keyup', function (event) {
        var e = event || window.event;
        // var k = e.keyCode || e.which;
        // var timeValue = $("#time1").textbox('getValue');
        var val = $(this).get(0).value;
        if(!/^[0-9]{0}([0-9]|[:])+$/.test(val)||$(this).get(0).value.length > 8){//含有数字和：以外的字符，则执行
            $("#time3").textbox('setValue', val.substr(0,val.length-1));
            event.preventDefault();
            return false
        }
    });
});


