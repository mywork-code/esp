/**
 * Created by xianzhi.wang on 2017/6/19 .
 */
$(function(){
//Grid
    $('#list').datagrid({
        title : '自动发货时间设置',
        fit : true,
        fitColumns : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        striped:true,
        toolbar : '#tb',
        columns :[[
            {
                title : '',
                field : 'status',
                width : 150,
                align : 'center'
            }, {
                title : '第一时间点',
                field : 'time1',
                width : 150,
                align : 'center',
                formatter: function (value,row, index) {
                    return  "<span title='"+value+"'>"+value+"</span>"
                }
            },
            {
                title : '第二时间点',
                field : 'time2',
                width : 120,
                align : 'center',
                formatter: function (value,row, index) {
                    return  "<span title='"+value+"'>"+value+"</span>"
                }
            },
            {
                title : '第三时间点',
                field : 'time3',
                width : 150,
                align : 'center',
                formatter: function (value,row, index) {
                    return  "<span title='"+value+"'>"+value+"</span>"
                }
            }
            ,
            {
                title : '第四时间点',
                field : 'time4',
                width : 150,
                align : 'center'
            }
            ,
            {
                title : '操作',
                field : 'opt',
                width : 150,
                align : 'center',
                formatter: function (value,row, index) {
                    return  "<span title='"+value+"'>"+value+"</span>"
                }
            }
        ]],
        loader : function(param, success, error) {
            $.ajax({
                url : ctx + '/application/autoship/management/query',
                data : param,
                type : "get",
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
});