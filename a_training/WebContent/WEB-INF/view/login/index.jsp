<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../inc/initTaglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>鱼管家后台管理系统</title>
<script src="/resources/js/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		 $(document).ready(function() {
			 $('#btn').click(function() {
				 	var mess = check();
					if ( mess == null) {
						$("#Tip").text("Loading...");
						$.ajax({
							type:'POST',
							url:'/login/in',
							data:$('#loginForm').serializeArray(),
							dataType:"json",
							cache: false,
							success: function(responseText) {
								if (responseText == "1") {
									location.href = "/login/home.do";
								} else {
									$('#Tip').html("登录失败，请与管理员联系");
								}
							}
						});
						return true;
					}else{
						$('#Tip').html(mess);
						return false;
					}
				});
		 });

       	function check(){
           	var u = $("#username").val();
           	var p = $("#password").val();
           	var msg = null;
            if(u.replace(/(^\s*)|(\s*$)/g,"").length == 0){
                if(p.replace(/(^\s*)|(\s*$)/g,"").length == 0)
                	msg='请输入帐号和密码';
                else 
                	msg='请输入帐号';
            } else if(p.replace(/(^\s*)|(\s*$)/g,"").length == 0){
                	msg='请输入密码';
            }
            return msg;
        }
    </script>
    <style type="text/css">
    
html,body {
	width: 100%;
	height: 100%;
	padding: 0;
	margin: 0;
}

body {
	display: block;
	font-size: 15px;
	background: url(/resources/login/indexBg.jpg) center  no-repeat;
	background-size: cover;
	filter: none;
}

.login_bg {
	width: 1031px;
	height: 361px;
	background-repeat: no-repeat;
	background-position: center center;
	text-align: center;
}

.logo {
	width: 1031px;
	height: 361px;
}

.login {
	width: 600px;
	height: 361px;
	padding: 160px 0px 0px;
	padding-left: 40%
}

#loginForm {
	margin: 0px;
	padding: 0px;
}

.login_top {
	background: url(/resources/login/bg.png);
	border: 1px solid #EFEFEF;
	padding: 5px;
	position:absolute;top:50%;left:50%;margin-left:-314px;margin-top:-70px;
}

.l-input {
	width: 216px;
	line-height: 24px;
	border: 1px solid #CCC;
	background: #FFF;
	color: #333;
	line-height: 24px;
}

.select {
	width: 218px;
	line-height: 24px;
	border: 1px solid #CCC;
	background: #FFF;
	color: #333;
	line-height: 24px;
}

.l-table-edit-td {
	width: 20%;
	padding: 1px 4px 1px 4px;
	line-height: 16px;
	font-size: 12px;
	font-family: "微软雅黑"
}

.l-button-submit,.l-button-test {
	font-size: 12px;
	margin-left: 6px;
	cursor: pointer
}

.font1 {
	font-family: "新宋体";
	font-size: 12px;
	color: #666;
	line-height: 12px;
	padding-left: 20px;
}

.mouse {
	cursor: pointer
}

td {
	padding-bottom: 4px;
	padding-top: 4px
}

input[type="text"],input[type="password"] {
	box-shadow: none;
	font-family: "맑은 고딕", "Malgun Gothic";
	color: #000;
	line-height: 43px;
	font-weight: normal;
	font-size: 14px;
	width: 358px;
	height: 43px;
	overflow: hidden;
	text-indent: 30px;
	padding-top: 0;
	padding-bottom: 0;
	background: rgba(255, 255, 255, .3);
}

</style>
  </head>
  
  <body style="zoom: 1;"  >
  	<div class="login_bg" >
  		<div class="login"  >
  		<div  style="position:absolute;top:50%;left:50%;margin-left:-140px;margin-top:-150px;" ></div>
  			<form id="loginForm" onkeydown="if(event.keyCode==13) $('#btn').click();">
  			<div class="login_top" >
	    	<table border="0" cellspacing="0" cellpadding="0" id="infoTB" width="40%">
	    		<tr>
	    			 <td class="l-table-edit-td"    style="font:#9a9a9a" >
	    				<font color="#9a9a9a"> USER ID</font>
	    			</td>
	    			<td align="left">
                        <input type="text" id="username" name="username" value=""
	    					class="l-input" onkeyup="check()">
	    			</td>	
	    			<td style="margin-top:50px;" rowspan="3" align="left" valign="top">
	    				<img src="/resources/login/btn.png"  
	    						title="login" 
								id="btn" class="l-button l-button-submit"/>
	    			</td>	    		
		    	</tr>	
		    	<tr>
		    		<td class="l-table-edit-td"    style="font:#9a9a9a" >
	    				<font color="#9a9a9a"> PASSWORD</font>
	    			</td>
	    			<td align="left">
	    				<input type="password"  id="password" name="password" value=""
	    				class="l-input" onkeyup="check()">
	    			</td>
		    	</tr>
				<tr>
			    	<td colspan="3" align="center">		    		
						<font size=2 color=red><span id="Tip">${msg}</span></font>
			    	</td>
		    	</tr>
	    	</table>
	    </div>   	
	    </form>
  		</div>
  	</div>
  </body>
</html>
