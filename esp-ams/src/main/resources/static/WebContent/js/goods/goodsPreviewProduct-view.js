$(function(){
$("#SIZE_Chcolor").on("click","span",function(){
	    $("#goodsSkuTempColor").removeAttr("color");
		$(this).addClass("active").siblings().removeClass();
		var SIZE_price = $(this).attr("data-price");
		var SIZE_costPrice = $(this).attr("data-costPrice");
		var supply = $(this).attr("data-amount");
		var SIZE_color = $(this).text();
		var stockLogoUrl=$(this).attr("data-logourl");
		var compareurl=$(this).attr("data-compareurl");
		var compareurl2 =$(this).attr("data-compareurl2");
		console.log(compareurl+"::"+compareurl2);
		$("#Product_details .SIZE_price").html('¥'+ SIZE_price);
		$("#Product_details .SIZE_costPrice").html('¥'+ SIZE_costPrice);
		$("#Product_details .SIZE_color").html('已选择：'+ SIZE_color);
		$("#Product_details .supply").html('商品库存：'+ supply+'件');
		$("#Product_details .pro_img").attr('src','data:image/gif;base64,'+stockLogoUrl);
		$("#compareurl1").html("<a href='" + compareurl + "' target='_blank'>比价链接1</a>");
		$("#compareurl2").html("<a href='" + compareurl2 + "' target='_blank'>比价链接2</a>");
		
	})
	
	
	
});