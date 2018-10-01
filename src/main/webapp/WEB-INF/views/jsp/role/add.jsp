<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- ■★▲◆修改点1 id="person" class="com.krd.market.entities.Person" 中的 person 改为 相应的对象 -->

<jsp:useBean id="role" class="com.lwhtarena.company.web.entities.Role" scope="request" ></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />



<title>增加角色</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}" media="all">
<script src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js"></script>
	
<script src="${pageContext.request.contextPath}/scripts/js/miniColors/2.1/jquery.minicolors.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/miniColors/2.1/jquery.minicolors.css">
<style type="text/css">
.layui-form-label {width: 120px;}
.layui-input {width: 80%}
.layui-input2 {width: 60%}
.toppos {margin-top: 20px;}
.addbtn {margin-top: 40px;margin-left: 300px;}
.tips {margin-left: 20px;}
</style>



</head>
<body>

	<form:form commandName="role" id="newrole" class="layui-form"
		action="${pageContext.request.contextPath }/action_role/chg"
		method="post" enctype="multipart/form-data" accept-charset="UTF-8" >
		
		<div class="toppos"></div>

		<div class="runtest">

			
			<div class="layui-form-item">
				<label class="layui-form-label">角色名称</label>
				<div class="layui-input-block">
					<form:input path="name" required="true"  lay-verify="required" placeholder="角色名称" autocomplete="off" class="layui-input" /> <form:errors path="name"></form:errors>

				</div>
			</div>
			
			
			<c:if test="${role.id >0 }">
			<form:hidden path="id" />
			
			<div class="layui-form-item">
		    <label class="layui-form-label">默认角色</label>
		    <div class="layui-input-block">
		    <form:checkbox path="def"    lay-skin="switch" lay-text="是|否"   /> 
		    
		    </div>
		    </div>
  
			<div class="layui-form-item">
				<label class="layui-form-label">状态</label>
				<div class="layui-input-block">
				<form:radiobutton path="status" value="true" title="正常" check="true"  /> 　　　
				<form:radiobutton path="status" value="false" title="禁用" /> 
				</div>
			</div>
			</c:if>
			
			
			
			<div class="layui-form-item">
				<div class="layui-input-block addbtn">
				<button class="layui-btn"  lay-submit lay-filter="formDemo">提交</button>
				</div>
			</div>


		</div>
	</form:form>
<script type="text/javascript">

layui.use(['form','laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element'], function(){
	  var form = layui.form
	  ,laydate = layui.laydate //日期
	  ,laypage = layui.laypage //分页
	  layer = layui.layer //弹层
	  ,table = layui.table //表格
	  ,carousel = layui.carousel //轮播
	  ,upload = layui.upload //上传
	  ,element = layui.element; //元素操作
	  
	  //form.render();
	  
	  //监听Tab切换
	  element.on('tab(demo)', function(data){
	    layer.msg('切换了：'+ this.innerHTML);
	    console.log(data);
	  });
	
});	    
</script>

</body>
</html>