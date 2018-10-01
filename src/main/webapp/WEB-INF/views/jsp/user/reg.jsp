<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="tu" class="com.lwhtarena.company.sys.util.TimeUtil" scope="page" ></jsp:useBean>
<!-- ■★▲◆修改点1 id="person" class="com.krd.market.entities.Person" 中的 person 改为 相应的对象 -->

<jsp:useBean id="user" class="com.lwhtarena.company.entities.User" scope="request" ></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />

<title>注册</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}" media="all">
<script src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js"></script>

<style type="text/css">
.layui-form-label {width: 120px;}
.layui-input {width: 80%}
.toppos {margin-top: 20px;}
.addbtn {margin-top: 40px;margin-left: 300px;}
.tips {margin-left: 20px;}
</style>



</head>
<body>
	<form:form modelAttribute="user" id="userreg" class="layui-form"
		action="${pageContext.request.contextPath }/action_user/reg"
		method="post" accept-charset="UTF-8">
		
		<div class="toppos"></div>

		<div class="runtest">
		 
		 <input type="hidden" name="sendTarget" value="${sessionScope.sendTarget}" />
		 <input type="hidden" name="targetMode" value="${sessionScope.targetMode}" />
			
			<div class="layui-form-item">
				<label class="layui-form-label">请输入用户名</label>
				<div class="layui-input-block">
					<form:input path="username"  id="username" lay-verify="username"
						placeholder="登录名(会员号)" autocomplete="off" class="layui-input" /> <form:errors path="username"></form:errors>

				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">请输入密码</label>
				<div class="layui-input-block">
					<form:input path="password"  required="true" lay-verify="required"
						placeholder="密码" autocomplete="off" class="layui-input" /> <form:errors path="password"></form:errors>

				</div>
			</div>	
			<c:if test="${sessionScope.targetMode==1}">
			<div class="layui-form-item">
				<label class="layui-form-label">请输入您的邮箱</label>
				<div class="layui-input-block">
					<form:input path="email"  required="true" lay-verify="required|email|lemail"
						placeholder="邮箱" autocomplete="off" class="layui-input" /> <form:errors path="email"></form:errors>

				</div>
			</div>
			</c:if>
			<c:if test="${sessionScope.targetMode==0}">
			<div class="layui-form-item">
				<label class="layui-form-label">请输入手机号码</label>
				<div class="layui-input-block">
					<form:input path="mobile"  required="true" lay-verify="required|phone|lmobile"
						placeholder="邮箱" autocomplete="off" class="layui-input" /> <form:errors path="mobile"></form:errors>

				</div>
			</div>
			</c:if>
			
			<div class="layui-form-item">
				<div class="layui-input-block addbtn">
				<button class="layui-btn" lay-submit="" lay-filter="formDemo">立即提交</button>
				</div>
			</div>


		</div>
	</form:form>
<script type="text/javascript">

layui.use(['form', 'layer',  'element'], function(){
	  var form = layui.form 
	  ,layer = layui.layer 
	  ,element = layui.element;
	  
	//自定义验证规则  
	  form.verify({  
		  
		
		  username: function(value){ 
			  
	          if(value.length < 3){  
	            return '标题至少得3个字符啊';  
	          }else{
	        	  var uri="${pageContext.request.contextPath}/action_user/findname?name="+value;
	        	  var r;
		          $.ajax({
						url: uri,
						type: 'post',
						async: false,
						success: function(data) {
							
							if (data>0){
								r= '该用户名已被占用！请重新输入！';
							}
						}
					});
		          return r;
	          }
	          
	          
		  }
	  
	  	,lemail:function(value){ 
			  
	  		var uri="${pageContext.request.contextPath}/action_user/findemail?email="+value;
      	  	var r;
	          $.ajax({
					url: uri,
					type: 'post',
					async: false,
					success: function(data) {
						
						if (data>0){
							r= '该邮箱已被占用！请重新输入！';
						}
					}
				});
	          return r;
	          
	          
		  }
	  	,lmobile:function(value){ 
			  
	  		var uri="${pageContext.request.contextPath}/action_user/findmobile?mobile="+value;
      	  	var r;
	          $.ajax({
					url: uri,
					type: 'post',
					async: false,
					success: function(data) {
						
						if (data>0){
							r= '该手机号已被占用！请重新输入！';
						}
					}
				});
	          return r;
	          
	          
		 }
	  
	  });
	  
	  
	//监听提交  
	  form.on('submit(formDemo)', function(data){
				  
	    /* layer.alert(JSON.stringify(data.field), {  
	      title: '最终的提交信息'  
	    })  
	    return false;   */
	  }); 
	
	  form.render(); // 更新全部
	
});

</script>	
</body>
</html>