<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="tu" class="com.lwhtarena.company.sys.util.TimeUtil" scope="page" ></jsp:useBean>
<jsp:useBean id="hq" class="com.lwhtarena.company.sys.util.HttpUtil" scope="page" ></jsp:useBean>
<jsp:useBean id="vt" class="com.lwhtarena.company.login.util.LoginSafeRecUtil" scope="page" ></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}" media="all">
<script src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js"></script>
<script src="${pageContext.request.contextPath}/scripts/js/lerxui/gen.js"></script>	

<style type="text/css">
.layui-form-label {width: 30px;}
.layui-input {width: 80%}
.toppos {margin-top: 20px;}
.addbtn {margin-top: 40px;margin-left: 170px;}
.tips {margin-left: 20px;}
.vcode{width: 40%; display:inline;}
.layui-icon{font-size: 18px;}

</style>
</head>
<body>

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
			 
<form id="newuser" class="layui-form" accept-charset="UTF-8" action="${pageContext.request.contextPath}/action_user/login" method="post">
		
		<input type="hidden" name="token" value="${hq.buildToken()}" />
		<div class="toppos"></div>

		<div class="runtest">
			<div class="layui-form-item">
				<label class="layui-form-label"><i class="layui-icon layui-icon-user"></i></label>
				<div class="layui-input-block">
					<input id="username" name="username" lay-verify="required" placeholder="请输入用户名、手机号或邮箱" class="layui-input" required="true" type="text" value="" autocomplete="off"/> 

				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label"><i class="layui-icon layui-icon-password"></i></label>
				<div class="layui-input-block">
					<input id="password" name="password" placeholder="请输入密码" class="layui-input" type="password" value="" autocomplete="off"/> 

				</div>
			</div>
			<c:if test="${sessionScope.vcode_user_lerx eq 'true'}">
			<div class="layui-form-item">
				<label class="layui-form-label"><i class="layui-icon layui-icon-vercode"></i></label>
				<div class="layui-input-inline">
					<input name="vcode" type="text" id="mobile" placeholder="请输入验证码" class="layui-input vcode " autocomplete="off" onfocus="showVcode()" />  <img class="vimg" src="" width="88" height="30" name="validCodeImg" id="validCodeImg"
		style="display:none; cursor: pointer;"
		onclick="javascript:changeVcode();" /> 

				</div>
			</div>
			</c:if>
			
			
			<div class="layui-form-item">
				<div class="layui-input-block addbtn">
				<button  class="layui-btn" lay-submit 
						lay-filter="add" >提交</button>　　 <a href="javascript:forget('${pageContext.request.contextPath}');">忘记密码</a>

				</div>
			</div>


		</div>
	</form>
<script type="text/javascript">
	function showVcode() {
		
		if (!$("#validCodeImg").is(':visible')){

			var timeNow = new Date().getTime();
			$("#validCodeImg").attr("src","${pageContext.request.contextPath}/action_captcha/randomNum?key=user&time=" + timeNow);
			$("#validCodeImg").show();
		}
		
	}
	function changeVcode() {
		var timeNow = new Date().getTime();
		$("#validCodeImg").attr("src","${pageContext.request.contextPath}/action_captcha/randomNum?key=user&time=" + timeNow);
		$("#vcode").focus();
	}
</script>
</body>
</html>