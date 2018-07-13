$(function(){

    $("#activityType").combobox({
        onChange: function (n,o) {
            if(n == 'Y'){
                $("#xxxxxxID").show();
            }else{
                $("#offerSill1").numberbox('setValue','');
                $("#offerSill2").numberbox('setValue','');
                $("#discount1").numberbox('setValue','');
                $("#discount2").numberbox('setValue','');
                $("#xxxxxxID").css("display","none");
            }
        }
    });

    //加载activityCate
    $("#id_activityCate").combobox({
        onChange: function (n, o) {
            if (n == '1') {
                $("#1_xxxxId").show();
                $("#2_xxxxId").hide();
                $(".couponsDiv").css("display","none");

            } else {
                $("#2_xxxxId").show();

                $("#1_xxxxId").hide();
            }
        }
    });

    $(".ifCouCate").change(
        function () {
            var value = $("input[name='cateCoupon']:checked").val();
            if(value == 'Y'){
                $("#id_xxx_copon").show();
            }else{
                $("#id_xxx_copon").css("display","none");
            }
        }
    );

    //radio监听事件
    $(".ifCou").change(
        function () {
            var value = $("input[name='isCoupon']:checked").val();
            if(value == 'Y'){
                $(".couponsDiv").show();
                $("#addOrDeleteButtonClass").show();
            }else{
                clearTextBox('1');
                $(".couponsDiv").css("display","none");
            }
        }
    );



    //手动发放优惠券---->添加优惠券种类
    $('#issueAddCoupon').bind('click', function(){
        var addOrdeleteCouponTrDisplay2 = $(".addOrdeleteCouponTr2").css("display");
        var addOrdeleteCouponTrDisplay3 = $(".addOrdeleteCouponTr3").css("display");
        var addOrdeleteCouponTrDisplay4 = $(".addOrdeleteCouponTr4").css("display");
        var addOrdeleteCouponTrDisplay5 = $(".addOrdeleteCouponTr5").css("display");

        if(addOrdeleteCouponTrDisplay2 == "none"){
            $(".addOrdeleteCouponTr2").show();
        }else if(addOrdeleteCouponTrDisplay3 == "none"){
            $(".addOrdeleteCouponTr3").show();
        }else if(addOrdeleteCouponTrDisplay4 == "none"){
            $(".addOrdeleteCouponTr4").show();
        }else{
            $(".addOrdeleteCouponTr5").show();
        }

        if(addOrdeleteCouponTrDisplay2 != "none"&& addOrdeleteCouponTrDisplay3 != "none"
            && addOrdeleteCouponTrDisplay4 != "none"&& addOrdeleteCouponTrDisplay5 != "none"){
            $.messager.alert('<span style="color: black">提示</span>',"最多添加五种优惠券");
            return;
        }

    });

    //手动发放优惠券---->删除优惠券种类
    $('#issueDeleteCoupon').bind('click', function(){
        var addOrdeleteCouponTrDisplay2 = $(".addOrdeleteCouponTr2").css("display");
        var addOrdeleteCouponTrDisplay3 = $(".addOrdeleteCouponTr3").css("display");
        var addOrdeleteCouponTrDisplay4 = $(".addOrdeleteCouponTr4").css("display");
        var addOrdeleteCouponTrDisplay5 = $(".addOrdeleteCouponTr5").css("display");

        debugger;
        if(addOrdeleteCouponTrDisplay5 != "none"){
            clearTextBox('5');
            arr.splice(arr.length-1)//删除数组中的参数
            $(".addOrdeleteCouponTr5").css("display","none");
        }else if(addOrdeleteCouponTrDisplay4 != "none"){
            clearTextBox('4');
            arr.splice(arr.length-1)
            $(".addOrdeleteCouponTr4").css("display","none");
        }else if(addOrdeleteCouponTrDisplay3 != "none"){
            clearTextBox('3');
            arr.splice(arr.length-1)
            $(".addOrdeleteCouponTr3").css("display","none");
        }else{
            clearTextBox('2');
            arr.splice(arr.length-1)
            $(".addOrdeleteCouponTr2").css("display","none");
        }

        if(addOrdeleteCouponTrDisplay5 == "none"&&addOrdeleteCouponTrDisplay4 == "none"
            &&addOrdeleteCouponTrDisplay3 == "none"&& addOrdeleteCouponTrDisplay2 == "none"){
            $.messager.alert('<span style="color: black">提示</span>',"最少添加一种优惠券");
            return;
        }

    });



})


function clearTextBox(No){
    $("#chooseCoupon"+No).combobox('clear');
    $("#issueCouponNum"+No).textbox('setValue','');
    $("#issueLimitNum"+No).textbox('setValue','');
}
