<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<jsp:useBean id="tu" class="com.lwhtarena.company.sys.util.TimeUtil" scope="page" ></jsp:useBean>
<jsp:useBean id="hq" class="com.lwhtarena.company.sys.util.HttpUtil" scope="page" ></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.layui-btn{margin-top: 10px;}
.bottond{margin: 0 auto;text-align: center;}
</style>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}" media="all">
  <script src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js"></script>
</head>
<body>
<form class="layui-form" action="${pageContext.request.contextPath}/action_res/modify">
<input type="hidden" name="token" value="${hq.buildToken()}" />
<input type="hidden" name="filepath" value="${requestScope.file }" />
<div>
<textarea style="overflow-x:hidden" name="filecontent" class="layui-textarea" rows="24" cols=""  >${requestScope.txt }</textarea>
<div class="bottond">
<button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
</div>
</div>
</form>
</body>
</html>