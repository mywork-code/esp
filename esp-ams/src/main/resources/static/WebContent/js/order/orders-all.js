/**
 * 订单信息查询
 */
var orderIdAll = "";

var refundIdAll = "";

$(function () {

    $('#orderDetailListWin,#logisticsDetail,#serviceDetail,#serviceDetailList,#refundDetails').window('close');

    // Grid 列表
    $('#tablelist').datagrid(
        {
            title: '订单信息查询',
            fit: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            striped: true,
            toolbar: '#tb',
            columns: [
                [
                    {
                        title: '编号',
                        hidden: true,
                        field: 'id',
                        width: 150,
                        align: 'center'
                    },
                    {
                        title: '订单号',
                        field: 'orderId',
                        width: 250,
                        align: 'center'
                    },

                    {
                        title: '下单时间',
                        field: 'createDate',
                        width: 150,
                        align: 'center'
                    },

                    {
                        title: '收货人姓名',
                        field: 'name',
                        width: 70,
                        align: 'center'
                    },

                    {
                        title: '收货人手机号',
                        field: 'telephone',
                        width: 100,
                        align: 'center'
                    },

                    {
                        title: '收货地址',
                        field: 'fullAddress',
                        width: 200,
                        align: 'center'
                    },
                    {
                        title: '商户编码',
                        hidden: true,
                        field: 'merchantCode',
                        width: 100,
                        align: 'center'
                    },
                    {
                        title: '商户名称',
                        field: 'merchantName',
                        width: 100,
                        align: 'center'
                    },
                    {
                        title: '付款方式',
                        field: 'payType',
                        width: 70,
                        align: 'center'
                    },
                    {
                        title: '付款时间',
                        field: 'payDate',
                        width: 150,
                        align: 'center',
                    },
                    {
                        title: '订单状态',
                        field: 'orderStatusDsc',
                        width: 70,
                        align: 'center',
                    },
                    {
                        title: '商品编号',
                        hidden: true,
                        field: 'goodsId',
                        width: 100,
                        align: 'center'
                    },
                    {
                        title: '商品名称',
                        hidden: true,
                        field: 'goodsName',
                        width: 100,
                        align: 'center'
                    },

                    {
                        title: '商品类型',
                        hidden: true,
                        field: 'goodsTypeDesc',
                        width: 100,
                        align: 'center'
                    },
                    {
                        title: '商品型号',
                        hidden: true,
                        field: 'goodsModel',
                        width: 100,
                        align: 'center'
                    },
                    {
                        title: '商品规格',
                        hidden: true,
                        field: 'goodsSkuAttr',
                        width: 100,
                        align: 'center'
                    },
                    {
                        title: '购买价格',
                        field: 'orderAmt',
                        width: 70,
                        align: 'center',
                        formatter: function (val, rowData, rowIndex) {
                            if (val != null)
                                return val.toFixed(2);
                        }
                    },
                    {
                        title: '购买量',
                        hidden: true,
                        field: 'goodsNum',
                        width: 100,
                        align: 'center'
                    },

                    {
                        title: '操作',
                        field: 'opt',
                        width: 150,
                        align: 'center',
                        formatter: function (value, row, index) {
                            // 授权标示
                            var grantedAuthority = $('#grantedAuthority').val();
                            // 订单状态
                            var orderStatus = row.orderStatus;
                            var content = "";
                            content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton'";
                            content += " onclick='$.queryOrderDetail(\"" + row.orderId + "\",\"" + row.userId
                                + "\",\"" + row.orderAmt + "\",\"" + row.orderStatusDsc + "\",\""
                                + row.goodName + "\",\"" + row.payType + "\",\"" + row.transNo + "\",\""
                                + row.createDate + "\");'>查看详情</a>";
                            if (grantedAuthority == 'permission') {
                                if (orderStatus == 'D05') {
                                    content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton'";
                                    content += " onclick='refundDeal(\"" + row.orderId + "\",\"" + row.refundId + "\",\"" + row.refundType + "\");'>售后受理</a>";
                                }
                                //退款处理中  交易关闭
                                if (orderStatus == 'D09' || orderStatus == 'D10') {
                                    content += "&nbsp;<a href='javascript:void(0);' class='easyui-linkedbutton'";
                                    content += " onclick='$.refundDetails(\"" + row.orderId + "\");'>退款详情</a>";
                                }
                            }
                            return content;
                        }
                    }
                ]
            ],

            loader: function (param, success, error) {
                $.ajax(
                    {
                        url: ctx + '/application/business/order/pagelistAll',
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

    // 查询列表
    $(".search-btn").click(function () {
        var params = {};
        params['orderId'] = $("#orderId").textbox('getValue');
        params['createDate'] = $("#createDate").textbox('getValue');
        params['name'] = $("#name").textbox('getValue');
        params['telephone'] = $("#telephone").textbox('getValue');
        params['orderStatus'] = $("#orderStatus").textbox('getValue');
        params['refundType'] = $("#refundType").textbox('getValue');
        $('#tablelist').datagrid('load', params);
    });

    // 重置
    $(".reset-btn").click(function () {
        $(".search #orderId").textbox('setValue', '');
        $(".search #createDate").textbox('setValue', '');
        $(".search #telephone").textbox('setValue', '');
        $(".search #orderStatus").combobox('setValue', '');
        $(".search #refundType").combobox('setValue', '');
        $(".search #name").textbox('setValue', '');
        var params = {};
        $('#tablelist').datagrid('load', params);
    });

    // 查询订单详情
    $.queryOrderDetail = function (orderId, userId, orderAmt, orderStatusDsc, goodName, payType, transNo, createDate) {
        debugger;
        // 设置窗口中的订单信息
//		$ ("#orderDetailListWin #userId").textbox ('setValue', userId);
        $("#orderDetailListWin #orderId").textbox('setValue', orderId);
        $("#orderDetailListWin #orderAmt").textbox('setValue', orderAmt);
        $("#orderDetailListWin #orderStatus").textbox('setValue', orderStatusDsc);
        $("#orderDetailListWin #createDate").textbox('setValue', createDate);
//		$ ("#orderDetailListWin #transNo").textbox ('setValue', transNo);

        if (payType != "null") {
            $("#orderDetailListWin #payType").textbox('setValue', payType);
        }
        if (transNo != "null") {
            $("#orderDetailListWin #transNo").textbox('setValue', transNo);
        }


        $('#orderDetailListWin').window('open');

        // 查询订单详情信息
        queryOrderDetail(orderId);
    }

    function queryOrderDetail(orderId) {
        $('#orderDetailList').datagrid(
            {
                url: ctx + '/application/business/order/queryOrderDetailInfo',
                method: 'POST',
                idField: '订单详细信息',
                fitColumns: false,
                pagination: true,
                rownumbers: true,
                striped: true,
                singleSelect: false,
                pagination: false,
                nowrap: false,
                showFooter: true,
                columns: [
                    [
                        {
                            title: 'ID',
                            hidden: true,
                            field: '编号',
                            width: 70,
                            align: 'center',
                            align: 'center'
                        },
                        {
                            title: '订单号',
                            field: 'orderId',
                            width: 150,
                            align: 'center'
                        },
                        {
                            title: '商品id',
                            field: 'goodsId',
                            width: 70,
                            align: 'center'
                        },
                        {
                            title: '商户编码',
                            field: 'merchantCode',
                            width: 70,
                            align: 'center'
                        },
                        {
                            title: '商品价格',
                            field: 'goodsPrice',
                            width: 70,
                            align: 'center',
                        },
                        {
                            title: '商品数量',
                            field: 'goodsNum',
                            width: 70,
                            align: 'center'
                        },
                        {
                            title: '商品名称',
                            field: 'goodsName',
                            width: 70,
                            align: 'center'
                        },
                        {
                            title: '商品类型',
                            field: 'goodsType',
                            width: 70,
                            align: 'center',
                            formatter: function (value, row, index) {
                                if (value == '1') {
                                    return '正常';
                                } else if (value == '2') {
                                    return '精选';
                                }
                            }
                        },
                        {
                            title: '商品型号',
                            field: 'goodsModel',
                            width: 70,
                            align: 'center'
                        },
                        {
                            title: '商品规格',
                            field: 'goodsSkuAttr',
                            width: 70,
                            align: 'center'
                        },
                        {
                            title: '商品上架时间',
                            field: 'listTime',
                            width: 150,
                            align: 'center',
                            formatter: function (value, row, index) {
                                return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                            }
                        },
                        {
                            title: '商品下架时间',
                            field: 'delistTime',
                            width: 150,
                            align: 'center',
                            formatter: function (value, row, index) {
                                return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                            }
                        },
                        {
                            title: '生产厂家',
                            field: 'supNo',
                            width: 70,
                            align: 'center'
                        },
                        {
                            title: '创建时间',
                            field: 'createDate',
                            width: 150,
                            align: 'center',
                            formatter: function (value, row, index) {
                                return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                            }
                        }
                    ]
                ],
                loader: function (param, success, error) {
                    debugger
                    $.ajax(
                        {
                            url: ctx + '/application/business/order/pagelistAll',
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
    }

    /**
     * 关闭窗口
     */
    $("#serviceDetailList .close-btn").click(function () {
        $("#serviceDetailList ").dialog("close");
    });

    /**
     * 确认退款
     */
    $("#serviceDetailList  .confirm-refund-btn").click(function () {
        if ($(this).linkbutton('options').disabled == true) {
            return false;
        }
        $.messager.confirm('确认退款', '确定要提交？', function (r) {
            if (r) {
                debugger;
                // 加载售后数据详情
                $.ajax(
                    {
                        url: ctx + '/application/business/refund/confirmRefundByOrderId',
                        data: {
                            "orderId": orderIdAll,
                            "refundId": refundIdAll
                        },
                        type: "post",
                        dataType: "json",
                        success: function (data) {
                            if (data.status == "1") {
                                $.messager.alert('消息', data.msg);
                            }
                            else {
                                $.messager.alert('警告', data.msg);
                                return;
                            }

                        }
                    });
            }
            else {
                return false;
            }
        });

        $("#serviceDetailList ").dialog("close");
    });

    // 导出订单
    $(".export-btn").click(function () {
        // var dataList;
        debugger;
        $.messager.confirm('全部订单信息', '确定要导出吗？', function (r) {
            if (r) {
                var params = {};
                params['orderId'] = $("#orderId").textbox('getValue');
                params['createDate'] = $("#createDate").textbox('getValue');
                params['name'] = $("#name").textbox('getValue');
                params['telephone'] = $("#telephone").textbox('getValue');
                var status = $("#orderStatus").textbox('getValue');
                if (status == '') {
                    params['orderStatus'] = "'D00','D01','D02','D03','D04','D05','D07','D08','D09','D10'";
                } else {
                    params['orderStatus'] = "'" + status + "'";
                }
                params['refundType'] = $("#refundType").combobox('getValue');
                params['isAll'] = 't';// t: 是 f: 否 是否导出全部订单信息
                params['busCode'] = 'E001';// 订单导出code

                exportFile("tablelist", "全部订单信息", params);
            }
        });
    });

    //图片放大，缩小
    $("#goodsUrlListDiv").on("click", "img", function () {
        if ($(this).attr("data-show") == 'true') {
            $(this).animate({
                width: "30px",
                height: "30px"
            }, 200, function () {
                $(this).attr("data-show", "false")
            });
        } else {
            $(this).animate({
                width: "100%",
                height: "100%"
            }, 200, function () {
                $(this).attr("data-show", "true")
            });
        }
    })

});

/**
 * 退款详情
 * @param orderId
 */

$.refundDetails = function (orderId) {

    $("#refundDetails").dialog("open");

    $('#refundDetails').datagrid(
        {
            title: '订单详细信息',
            fitColumns : false,
            pagination : true,
            rownumbers : true,
            striped : true,
            singleSelect : false,
            pagination : false,
            nowrap : false,
            showFooter : true,
            columns: [
                [
                    {
                        title: '退款申请时间',
                        field: 'createDate',
                        width: 80,
                        align: 'center',
                        formatter : function (value, row, index)
                        {
                            return new Date (value).Format ("yyyy-MM-dd hh:mm:ss");
                        }
                    },
                    {
                        title: '退款原因',
                        field: 'reason',
                        width: 80,
                        align: 'center'
                    },
                    {
                        title: '退款申请说明',
                        field: 'memo',
                        width: 80,
                        align: 'center'
                    },
                    {
                        title: '申请数量',
                        field: 'totalNum',
                        width: 80,
                        align: 'center'

                    },
                    {
                        title: '退款类型',
                        field: 'statu',
                        width: 80,
                        align: 'center',
                        formatter : function (value, row, index)
                        {
                            return "退款";
                        }
                    },
                    {
                        title: '退款处理进度',
                        field: 'status',
                        width: 80,
                        align: 'center',
                        formatter:function(value,row,index){

                            if(value=='1'){
                                return '退款提交';
                            }else if(value=='2'){
                                return '同意退款';
                            }else if(value='3'){
                                return "取消退款";
                            }else if(value=='4'){
                                return '退款成功';
                            }else{
                                return "退款失败";
                            }

                        }
                    }
                ]
            ],
            loader: function (param, success, error) {
                $.ajax(
                    {
                        url: ctx + '/application/business/cashRefund/getCashRefundByOrderId',
                        data: {"orderId":orderId},
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

}


/**
 * 关闭窗口
 */
$("#refundDetails .close-btn").click(function () {
    $("#refundDetails ").dialog("close");
});
/**
 * 退款受理
 */
function refundDeal(orderId, refundId, refundType) {
    $("#serviceDetailList .return").css("display", "none");
    $("#serviceDetailList .swap").css("display", "none");
    if (refundType == "0") {
        $("#serviceDetailList .return").css("display", "inline");
    } else if (refundType == "1") {
        $("#serviceDetailList .swap").css("display", "inline");
    }
    $("#serviceDetailList #goodsUrlListDiv").empty();
    orderIdAll = orderId;
    refundIdAll = refundId;
    $("#serviceDetailList").dialog("open");

    // 加载售后数据详情
    $.ajax(
        {
            url: ctx + '/application/business/serviceProcess/pagelist',
            data: {
                "orderId": orderId
            },
            type: "post",
            dataType: "json",
            success: function (data) {
                if (data.status == "1" && data.rows.length > 0) {
                    var dataObject = data.rows[0];
                    // 填写售后详细信息
                    var goodsRefundPrice = dataObject.goodsPrice * dataObject.goodsNum;
                    $("#serviceDetailList #refundReason").val(dataObject.refundReasonDes);
                    $("#serviceDetailList #goodsPrice").val(goodsRefundPrice);
                    $("#serviceDetailList #goodsNum").val(dataObject.goodsNum);
                    $("#serviceDetailList #status").val(dataObject.statusDesc);
                    $("#serviceDetailList #sLogisticsName").val(dataObject.sLogisticsName);
                    $("#serviceDetailList #sLogisticsNo").val(dataObject.sLogisticsNo);
                    $("#serviceDetailList #remark").val(dataObject.remark);
//			    $ ("#goodsUrl").attr ("src", ctx + "/fileView/query?picUrl=" + dataObject.goodsUrl);
                    //图片显示
                    if (dataObject.goodsUrl != null) {
                        var goodsUrlArr = dataObject.goodsUrl.split(",");//url是用,分隔的数组
                        $.each(goodsUrlArr, function (index, value) {
                            var picUrl = ctx + "/fileView/query?picUrl=" + value;
                            var goodsUrlListDivId = "goodsUrlListDiv" + index;
                            $("#goodsUrlListDiv").append("<img src='" + picUrl + "' border='1' width='30px' height='30px' id='" + goodsUrlListDivId + "'/> ");//"+ctx+"'\"/fileView/query?picUrl\"/
                        });
                    }
                    var param = {
                        dataNo: dataObject.sLogisticsName,
                        dataTypeNo: "100003"
                    };
                    $.ajax({
                        url: ctx + '/application/business/datadic/pagelist',
                        data: param,
                        type: "post",
                        dataType: "json",
                        success: function (data) {
                            if (data.rows.length == 1) {
                                var response = data.rows[0];
                                $("#serviceDetailList #sLogisticsName").val(response.dataName);
                            } else {
                                $("#serviceDetailList #sLogisticsName").val(dataObject.sLogisticsName);
                            }
                        }
                    });

                    // 设置确认付款状态按钮为只读（售后完成）
                    if (dataObject.status == "RS03" && dataObject.refundType == '0') {
                        $("#serviceDetailList  .confirm-refund-btn").linkbutton('enable');
                    }
                    else {
                        $("#serviceDetailList  .confirm-refund-btn").linkbutton('disable');
                    }
                }
            }
        });
}
