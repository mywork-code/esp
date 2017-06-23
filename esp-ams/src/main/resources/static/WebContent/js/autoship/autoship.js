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
                title: '',
                field: 'status',
                width: 150,
                align: 'center'
            }, {
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
                align: 'center'
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
        debugger;
        var time1 = $('#time1').textbox('getValue');
        var time2 = $('#time2').textbox('getValue');
        var time3 = $('#time3').textbox('getValue');
        var time4 = $('#time4').textbox('getValue');
        if (time1 == '' || time1.length == 0 || time2 == '' || time2.length == 0 || time3 == '' || time3.length == 0 || time4 == '' || time4.length == 0) {
            $.messager.alert("提示", "请输入对应的值");
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

            }
        })
        $('#list').datagrid('load',{});
        $('#addIntroConfig').window('close');
    });

    //取消
    $("#cancel").click(function () {
        $('#addIntroConfig').window('close');
    });
});


function ww1(date) {
    debugger;
    var h = date.getHours()+"";
    if (h.length == 1) {
        h = '0' + h;
    }
    var m = date.getMinutes()+"";
    if (m.length == 1) {
        m = '0' + m;
    }
    var s = date.getSeconds()+"";
    if (s.length == 1) {
        s = '0' + s;
    }
    return h + ":" + m + ":" + s;
}
function ww2(date) {
    debugger;
    var h = date.getHours()+"";
    if (h.length == 1) {
        h = '0' + h;
    }
    var m = date.getMinutes()+"";
    if (m.length == 1) {
        m = '0' + m;
    }
    var s = date.getSeconds()+"";
    if (s.length == 1) {
        s = '0' + s;
    }
    return h + ":" + m + ":" + s;
}
function ww3(date) {
    debugger;
    var h = date.getHours()+"";
    if (h.length == 1) {
        h = '0' + h;
    }
    var m = date.getMinutes()+"";
    if (m.length == 1) {
        m = '0' + m;
    }
    var s = date.getSeconds()+"";
    if (s.length == 1) {
        s = '0' + s;
    }
    return h + ":" + m + ":" + s;
}