<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags" prefix="date" %>
<jsp:useBean id="dateValue" class="java.util.Date"/>
<jsp:setProperty name="dateValue" property="time" value="${timestampValue}"/>
<jsp:useBean id="tu" class="com.lwhtarena.company.sys.util.TimeUtil" scope="page" ></jsp:useBean>
<jsp:useBean id="hq" class="com.lwhtarena.company.sys.util.HttpUtil" scope="page" ></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}" media="all">
<script src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js?t=${tu.getCurrTime()}"></script>
<title>后台登录--Lerx CMS</title>
<style type="text/css">
body, div, dl, dt, dd, ul, li, h1, h2, h3, h4, h5, h6, pre, form, fieldset, select, input, textarea, button, p, blockquote, th, td, img ,iframe { margin: 0; padding: 0; }
table{ border-collapse: collapse; border-spacing: 0; }
input,button,textarea,option { font: 12px "\5b8b\4f53", Arial, Helvetica, sans-serif; }
ul, li, div{ list-style: none; border: 0px; }
img{ border: 0px; }
.clear{ font: 0px/0px serif; display: block; clear: both; }
html{ -webkit-text-size-adjust: none; }
input{ outline: none; }
textarea{ resize: none; }
a{ text-decoration: none; }
body:nth-of-type(1) input:focus,textarea:focus{ outline: none; }
body:before {
  content: ' ';
  position: fixed;
  z-index: -1;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  background: url(${pageContext.request.contextPath }/images/login/admin/background.jpg) center 0 no-repeat;
  background-size: cover;
}
input[type="button"], input[type="submit"], input[type="reset"]{
	-webkit-appearance:none;
	outline:none
}
.login_box{width:400px;height:432px;position:absolute;margin-left:-200px;margin-top:-180px;left:50%;top:40%;}
.login_box_main{/* height:280px; */width:348px;background:#fff;border-radius:5px;padding: 20px 26px;}
.login_box_top{height:152px;}
.login_box_top h3{font:26px "microsoft yahei";color:#fff;margin:20px 0 0;text-align:center;}
.login_box_top_ico{width:214px;height:59px;background:url(/img/logo.png) no-repeat;display:block;margin:0 auto;}
.login_box_main input{width:346px;height:38px;background:#f2f2f2;border:1px solid #f5f7fa;border-radius:3px;text-indent:10px;font:14px/38px "microsoft yahei";color:#333;}
.login_box_main input.ouser{margin:27px 0 20px 0;}
.login_box_main .forget_password{margin-top:20px;}
.login_box_main .forget_password a{font:14px "microsoft yahei";color:#333;margin-left: 177px;}
.login_box_main .forget_password a:hover{text-decoration:underline;}
.login_box_main .loginBtn{width:348px;height:50px;background:#00aaee;color:#fff;font:16px/50px "microsoft yahei";text-align:center;display: block;margin-top:20px;border-radius:5px;}
.login_box_main .loginBtn:hover{background:#0397d2;}
.login_box_main input.erro{border-color:#ff8080;background:#ffe5e5 url(/public/images/v3/new_login_erro.png) no-repeat  323px 10px;}
.login_box_main .login_box_main_mobile,.login_box_main .login_box_main_password{position:relative;}
.login_box_main .login_box_main_mobile span{color:#ff3030;position:absolute;font:14px "microsoft yahei";top:36px;right:30px;}
.login_box_main .login_box_main_password span{color:#ff3030;position:absolute;font:14px "microsoft yahei";top:10px;right:30px;}
.login_box_main input.rember{width:12px;height:12px;position:absolute;top: 5px;left:0px;}
.login_box_main .forget_password label{font:14px "microsoft yahei";color:#333;position:absolute;left:14px;top:0px;cursor:pointer;}
.login_box_main .forget_password label:hover{text-decoration:underline;}
.login_box_main .forget_password span{position:relative;width:100px;height:16px;display:inline-block;}

.login_box_main input.vcode{width:200px;margin:20px 0 20px 0;}
/*.vimg{margin:20px 0 20px 0;}*/
.to_register{font:12px "microsoft yahei";color:#333;text-align:right;margin-top:10px;}
.to_register a{color:#00aaee;}
.to_register a:hover{text-decoration:underline;}
.loginErro{font:14px "microsoft yahei";color:red;margin-top:5px;display:none;}


</style>
</head>
<script>
	if (top.location != self.location) {
		top.location = self.location;
	}
</script>
<script type="text/javascript">
$(document).ready(function(){
  
    $("#username").focus();
 
});
</script>
<script type="text/javascript">
	var layer;
	layui.use(['layer', 'form'], function(){
		  layer = layui.layer
		  ,form = layui.form;
		  
		  
			 var error='${msg}';
			 if (error!='0' && error!=''){
				 var face='<i class="layui-icon" style="font-size: 20px; color: #FF3030;">&#xe69c;</i>  　';
				 //alert(error);
				 layer.msg(face + error); 
			 }
			
		});
	</script>
<body>

<form method="post" action="${pageContext.request.contextPath }/action_admin/login" id="form1" enctype="multipart/form-data" accept-charset="UTF-8">
<input type="hidden" name="token" value="${hq.buildToken()}" />

    <div class="login_box">
	   	  <div class="login_box_top">
	   	     	<i class="login_box_top_ico"></i>
	   	        <h3>登录 <span id="portalName"></span> 管理后台</h3>
	   	     	
	   	  </div>
	   	  <div class="login_box_main">
	   	     <p class="login_box_main_mobile">
                    <input name="username" type="text" id="username" placeholder="管理用户名"  class="ouser" autocomplete="off" />                    
	   	     </p>
	   	     <p class="login_box_main_password"><input name="password" type="password" id="pwd" placeholder="登录密码" class="opassword" /></p>
	   	    <c:if test="${sessionScope.vcode_admin_lerx eq 'true'}">
	   	     <p class="login_box_main_mobile">
                    <input name="vcode" type="text" id="vcode" placeholder="验证码" class="vcode" autocomplete="off" onfocus="showVcode()" />  <img class="vimg" src="" width="88" height="30" name="validCodeImg" id="validCodeImg"
		style="display:none; cursor: pointer;"
		onclick="javascript:changeVcode();" />                  
	   	     </p>
	   	     </c:if>
	   	     <p class="loginErro" style="display: none;"></p>
	   	     
                 <input type="submit" name="btn" value="立即登录" id="btn" class="loginBtn" />
	   	     <p class="forget_password"><!--<span><input class="rember" value="0" id="rember" checked="checked" type="checkbox"><label for="rember">记住密码</label></span><a href="/findpwd.aspx">忘记密码？</a>--></p>
	   	     <p class="to_register"> <!-- 没有账号,<a href="register.aspx">立即注册</a> --></p>
	   	  </div>
	   	     	         	       
   	      	
	   </div>
    </form>
<script type="text/javascript">
	function showVcode() {
		
		if (!$("#validCodeImg").is(':visible')){

			var timeNow = new Date().getTime();
			$("#validCodeImg").attr("src","${pageContext.request.contextPath}/action_captcha/randomNum?key=admin&time=" + timeNow);
			$("#validCodeImg").show();
		}
		
	}
	function changeVcode() {
		var timeNow = new Date().getTime();
		$("#validCodeImg").attr("src","${pageContext.request.contextPath}/action_captcha/randomNum?key=admin&time=" + timeNow);
		$("#vcode").focus();
	}
	
	function pn(){
		
		
		$.ajax({
			url:'${pageContext.request.contextPath}/action_portal/queryName',
			type: 'post',
			success:function(data){
				if (data!='Lerx CMS'){
					data+='网站';
				}
	 			$("#portalName").html(data);
			}
 		});

	}
	
	pn();
</script>	


</body>
</html>