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
<!-- ■★▲◆修改点1 id="person" class="com.krd.market.entities.Person" 中的 person 改为 相应的对象 -->

<jsp:useBean id="admin" class="com.lwhtarena.company.web.entities.Admin" scope="request" ></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>增加用户</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}" media="all">
<script src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js"></script>
	
<script src="${pageContext.request.contextPath}/scripts/js/miniColors/2.1/jquery.minicolors.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/miniColors/2.1/jquery.minicolors.css">
<style type="text/css">
.layui-form-label {width: 120px;}
.layui-input {width: 80%}
.toppos {margin-top: 20px;}
.addbtn {margin-top: 40px;margin-left: 300px;}
.tips {margin-left: 20px;}


</style>



</head>
<body>

<p style="text-align: right;"></p>

<!-- ■★▲◆修改点3  表单的id -->
	<form:form id="newcustomer"  modelAttribute="admin"
		action="${pageContext.request.contextPath }/action_admin/add"
		method="post" class="layui-form" accept-charset="UTF-8">
		
		<input type="hidden" name="token" value="${hq.buildToken()}" />
		<div class="toppos"></div>

		<div class="runtest">
<!-- ■★▲◆修改点4 test="${person.id >0 } 中的 person 改为 相应的对象 
		
		另以下表单根据类的名称进行相应的修改
		
		 -->
			<c:if test="${admin.id ==0 }">
			<div class="layui-form-item">
				<label class="layui-form-label">用户名</label>
				<div class="layui-input-block">
					<form:input path="username" required="true"  lay-verify="required" placeholder="用户名，建议使用英文字母和数字" autocomplete="off" class="layui-input" /> <form:errors path="username"></form:errors>

				</div>
			</div>
			
			
			<div class="layui-form-item">
				<label class="layui-form-label">密码</label>
				<div class="layui-input-block">
					<form:input path="password"   placeholder="请输入密码" autocomplete="off" class="layui-input" /> <form:errors path="password"></form:errors>

				</div>
			</div>
			</c:if>
			<c:if test="${admin.id >0 }">
			<form:hidden path="id" />
			
			</c:if>
			
			<div class="layui-form-item">
				<label class="layui-form-label">姓名</label>
				<div class="layui-input-block">
					<form:input path="truename" placeholder="真实姓名" autocomplete="off" class="layui-input" /> <form:errors path="truename"></form:errors>

				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">Email</label>
				<div class="layui-input-block">
					<form:input path="email" placeholder="Email" autocomplete="off" class="layui-input" /> <form:errors path="email"></form:errors>

				</div>
			</div>
			
			<c:if test="${admin.id >0 }">
			<div class="layui-form-item">
				<label class="layui-form-label">状态</label>
				<div class="layui-input-block">
				<form:radiobutton path="state" value="true" title="正常" check="true"  /> 　　　
				<form:radiobutton path="state" value="false" title="禁用" /> 
				</div>
			</div>
			</c:if>
			
			<div class="layui-form-item">
				<div class="layui-input-block addbtn">
				<button class="layui-btn" onclick="commit();" lay-submit lay-filter="formDemo">提交</button>
				</div>
			</div>


		</div>
	</form:form>
<script type="text/javascript">

function convertDateToUnix(str){
	
	str = str.replace(/-/g,"/");
	var date = new Date(str);
	var unixDate = new Date(Date.UTC(date.getFullYear(),date.getMonth(),date.getDate(),date.getHours(),date.getMinutes(), date.getSeconds()));   
	return unixDate.getTime();
}

function commit(){
	
	$("#startDate").val(convertDateToUnix($("#times").val()));
	$("#expiryDate").val(convertDateToUnix($("#timee").val()));
	
	$("#newcustomer").submit();
	
}


</script>
<script type="text/javascript">


layui.use('form', function(){
	  var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
	  
	  //……
	  
	  //但是，如果你的HTML是动态生成的，自动渲染就会失效
	  //因此你需要在相应的地方，执行下述方法来手动渲染，跟这类似的还有 element.init();
	  form.render();
	}); 
	
</script>

<script>
layui.use('laydate', function(){
  var laydate = layui.laydate;
  
  //执行一个laydate实例
  laydate.render({
    elem: '#times' //指定元素
  });
  
  laydate.render({
	    elem: '#timee' //指定元素
	  });
});

	
</script>

</body>
</html>