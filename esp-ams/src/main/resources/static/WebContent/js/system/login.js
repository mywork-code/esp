$(function() {
	 if($("#alertMsg").val() != ""){
	     alert($("#alertMsg").val());
	     $("#alertMsg").val("");
		}
    // Login
    $("#submitLogin").click(function() {
    	if($("#username").val() == ""){
    		$("#alertError").html("请输入用户名");
    		return false;
    	}
    	if($("#password").val() == ""){
    		$("#alertError").html("请输入密码");
    		return false;
    	}
    	if($("#random").val() == ""){
    		$("#alertError").html("请输入验证码");
    		return false;
    	}
        $("#loginForm").submit();
    });
    $("#loginForm input").focus(function(){
    	$("#alertError").html("");
    });
    $(document).keyup(function(event){
    	  if(event.keyCode ==13){
    	    $("#submitLogin").trigger("click");
    	  }
    });
});
