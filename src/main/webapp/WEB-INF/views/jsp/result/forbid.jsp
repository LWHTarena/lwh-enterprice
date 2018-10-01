<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<jsp:useBean id="tu" class="com.lwhtarena.company.sys.util.TimeUtil" scope="page" ></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>禁止操作</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}" media="all">
<script src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js"></script>
</head>
<style type="text/css">
.site-code{margin: 0 auto;text-align:center;margin-top: 100px;}
.msg{font-size: 20px;}

.prompt1{margin-top: 40px;}
.prompt2{margin-top: 10px;}
.qrcode{margin-top: 30px;}
</style>
<script>
//一般直接写在一个js文件中
layui.use(['layer', 'form'], function(){
  var layer = layui.layer
  ,form = layui.form;
  
});
</script> 
<body>

	<div class="site-code">
	
	<div class="msg">非法操作或没有操作权限！</div>
	
	<div class="prompt1">扫描下面的二维码，赞助和支持Lerx项目开发！您也许会获得意想不到的收获！</div>
	<div class="qrcode"><img alt="" src="${pageContext.request.contextPath}/images/lerx/qrcode_lerx.png"></div>
	<div class="prompt2">Lerx获得您的赞助，将贴心了解和支持您的需求！</div>
	<div class="prompt2">赞助说明：金额任意。请备注您的UID：${requestScope.uid }，谢谢！</div>
	
		
	</div>
	

</body>
</html>