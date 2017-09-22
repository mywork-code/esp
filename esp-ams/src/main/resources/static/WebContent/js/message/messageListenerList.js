$(function () {
    $("#messageListenerList").datagrid({
        title: '消息推送',
        fit: true,
        fitColumns: true,
        rownumbers: true,
        pagination: true,
        singleSelect: true,
        striped: true,
        toolbar: '#tb',
        columns: [[{
            title: '推送类型',
            field: 'type',
            width: 150,
            align: 'center',
            formatter: function (value, row, index) {
                if (value == "1") {
                    return "拆单消息";
                } else if (value == "2") {
                    return "商品价格变更消息";
                }else if (value == "4") {
                    return "商品上下架消息";
                }else if (value == "5") {
                    return "订单妥投消息";
                }else if (value == "6") {
                    return "商品添加删除消息";
                }else if (value == "100") {
                    return "京东售后状态消息";
                }
            }
        }, {
            title: '京东商品编号',
            field: 'skuid',
            width: 150,
            align: 'center'
        }, {
            title: '京东订单编号',
            field: 'orderid',
            width: 150,
            align: 'center'
        }, {
            title: '接口状态',
            field: 'status',
            width: 150,
            align: 'center',
            formatter: function (value, row, index) {
                if (value == "1") {
                    return "成功";
                } else if (value == "0") {
                    return "失败";
                }
            }
        }, {
            title: '错误原因',
            field: 'errorMassage',
            width: 150,
            align: 'center'
        }, {
            title: '返回结果',
            field: 'result',
            width: 120,
            align: 'center'
        }, {
            title: '时间',
            field: 'createDateString',
            width: 150,
            align: 'center'
        }]],
        loader: function (param, success, error) {
            $.ajax({
                url: ctx + '/application/message/listener/pagelist',
                data: param,
                type: "post",
                dataType: "json",
                success: function (data) {
                    $.validateResponse(data, function () {
                        success(data);
                    });
                }
            })
        }
    });
    //刷新
    $("#flush").click(function () {
        $("#type").combobox('setValue', '');
        $("#skuid").textbox('setValue', '');
        $("#orderid").textbox('setValue', '');
        $("#status").combobox('setValue', '');
        var params = {};
        $('#messageListenerList').datagrid('load', params);
    });
    //查询
    $(".search-btn").click(function () {
        var params = {};
        params['type'] = $("#type").combobox('getValue');
        params['skuid'] = $("#skuid").textbox('getValue');
        params['orderid'] = $("#orderid").textbox('getValue');
        params['status'] = $("#status").combobox('getValue');
        $('#messageListenerList').datagrid('load', params);
    });

});

