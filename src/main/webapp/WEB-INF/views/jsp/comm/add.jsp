<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="sutil" class="com.lwhtarena.company.sys.util.StringUtil"
	scope="page"></jsp:useBean>
<jsp:useBean id="hq" class="com.lwhtarena.company.sys.util.HttpUtil" scope="page"></jsp:useBean>
<jsp:useBean id="tu" class="com.lwhtarena.company.sys.util.TimeUtil" scope="page"></jsp:useBean>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>增加评论</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}"
	media="all">
<script
	src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js"></script>
<style type="text/css">
.runtest {
	margin-top: 5px;
}

.layui-form-label {
	width: 15%;
}

.layui-input {
	width: 85%;
}

.layui-textarea, .layui-layedit {
	width: 85%;
}

.toppos {
	margin-top: 20px;
}

.addbtn {
	margin-top: 40px;
	margin-left: 300px;
}

.tips {
	margin-left: 20px;
}
.vcode{width: 40%;}
</style>
</head>
<body>
	<div class="runtest">
		<form id="ctedit"
			action="${pageContext.request.contextPath}/action_comm/add"
			class="layui-form" method="post">

			<input type="hidden" name="token" value="${hq.buildToken()}" />
			<input type="hidden" name="bid" value="${param.bid}" />
			<input type="hidden" name="pid" value="${param.pid}" />


			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">评论内容：</label>
				<div class="layui-input-block">
					<textarea name="content" id="content" maxlength='<spring:message code="comment.length.upper"></spring:message>' placeholder="请输入内容，限<spring:message code="comment.length.upper"></spring:message>个字符！非法字符和脚本将被过滤。"
						class="layui-textarea"></textarea>
				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">验证码</label>
				<div class="layui-input-block">
					<input style="display:inline;" name="vcode" type="text" id="mobile" placeholder="请输入验证码" class="layui-input vcode" autocomplete="off" onfocus="showVcode()" />  <img class="vimg" src="" width="88" height="30" name="validCodeImg" id="validCodeImg"
		style="display:none; cursor: pointer;"
		onclick="javascript:changeVcode();" /> 

				</div>
			</div>



			<div class="layui-form-item">
				<div class="layui-input-block addbtn">
					<button class="layui-btn" lay-submit lay-filter="add">发表</button>
				</div>
			</div>

		</form>
</div>
<script type="text/javascript">
			layui.use([ 'form', 'element' ],function() {
				var form = layui.form
				, element = layui.element;
			form.render();
			});

</script>
<script type="text/javascript">
	function showVcode() {
		
		if (!$("#validCodeImg").is(':visible')){

			var timeNow = new Date().getTime();
			$("#validCodeImg").attr("src","${pageContext.request.contextPath}/action_captcha/randomNum?key=comm&time=" + timeNow);
			$("#validCodeImg").show();
		}
		
	}
	function changeVcode() {
		var timeNow = new Date().getTime();
		$("#validCodeImg").attr("src","${pageContext.request.contextPath}/action_captcha/randomNum?key=comm&time=" + timeNow);
		$("#vcode").focus();
	}
</script>
	
</body>
</html>