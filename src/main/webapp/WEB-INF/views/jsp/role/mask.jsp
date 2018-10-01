<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="group" class="com.lwhtarena.company.web.entities.ArticleGroup"
	scope="request"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限码设置</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}" media="all">
<script src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js"></script>

<style type="text/css">
.layui-form-item {margin-top: 10px;text-align: center; }
.layui-form-label {width: 120px;}
.layui-input {width: 80%}
.toppos {margin-top: 20px;}
.addbtn {margin-top: 40px;margin-left: 40px;}
.tips {margin-left: 20px;}
</style>
</head>
<body>
<form:form modelAttribute="group" id="newgroup" class="layui-form"
		action="${pageContext.request.contextPath }/action_role/maskchg"
		method="post" accept-charset="UTF-8">
		<input name="id" value="${requestScope.roleID }" hidden="hidden">
<div class="layui-form-item">
    <input type="checkbox" lay-filter="admin" name="admin" id="admin" title="前台管理员" <c:if test="${requestScope.admin }">checked</c:if> >
    <input type="checkbox" name="add0" id="add0" title="全部发表权" <c:if test="${requestScope.add0 }">checked</c:if> >
  </div>
<table class="layui-table" lay-filter="demo">
<thead>
    <tr>
		<th>栏目</th>
		<th>ID</th>
		<th>权限</th>
		
    </tr>
</thead>
<tbody>
    <c:forEach items="${requestScope.preListAll }" var="g" varStatus="st">
    <tr>
		<td>${g.title }</td>
		<td><c:if test="${g.id > 0 }">${g.id }</c:if></td>
		<td>
		<c:if test="${g.id > 0 }">
		<c:set var="iname" value="mvalue[${st.index}]"></c:set>
		<input type="radio" id="ag${st.index}" name="${iname}" value="" title="无" <c:if test="${g.tmp == 0 }">checked</c:if> >
      	<input type="radio" id="ag${st.index}" name="${iname}" value="a${g.id }" title="发布" <c:if test="${g.tmp == 1 }">checked</c:if> >
      	<input type="radio" id="ag${st.index}" name="${iname}" value="p${g.id }" title="管理" <c:if test="${g.tmp == 2 }">checked</c:if> >
		</c:if>
		
		</td>
		
    </tr>
    </c:forEach>
</tbody>
</table>
<div class="layui-form-item">
				<div class="layui-input-block addbtn">
				<input class="layui-btn" type="submit" value="提交" lay-submit lay-filter="formDemo">
					
				</div>
			</div>
</form:form>
<script type="text/javascript">
 function check() {
	//alert("ttt");
	 var aaa = $("#admin").prop("checked");
	if(aaa){
		$("#add0").attr('checked',false);
		  $("#add0").attr('disabled',"true");
		  $('input:radio').attr('disabled',"true");
	 }; 
	
} 


 
 
/* layui.use(['form','laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element'], function(){
	  var form = layui.form
	  ,laydate = layui.laydate //日期
	  ,laypage = layui.laypage //分页
	  layer = layui.layer //弹层
	  ,table = layui.table //表格
	  ,carousel = layui.carousel //轮播
	  ,upload = layui.upload //上传
	  ,element = layui.element; //元素操作 */
	  
	  layui.use(['form','layer','table','element'], function(){
		  var form = layui.form
		  ,layer = layui.layer //弹层
		  ,table = layui.table
		  ,element = layui.element; //表格  
		  
		  //各种基于事件的操作，下面会有进一步介绍
		
	  
	   form.on('checkbox(admin)', function(data){
		  console.log(data.elem); //得到checkbox原始DOM对象
		  console.log(data.elem.checked); //是否被选中，true或者false
		  console.log(data.value); //复选框value值，也可以通过data.elem.value得到
		  console.log(data.othis); //得到美化后的DOM对象
		  
		  if (data.elem.checked){
			  $("#add0").attr('checked',false);
			  $("#add0").attr('disabled',"true");
			  $('input:radio').attr('disabled',"true");
			  
		  }else{
			  $("#add0").removeAttr("disabled");;
			  $('input:radio').removeAttr("disabled");;
		  }
		  
		  form.render('checkbox');
		  form.render('radio');
		}); 
	   
	  //form.render('checkbox');
	  //form.render();
	  
	 /*  //监听Tab切换
	  element.on('tab(demo)', function(data){
	    layer.msg('切换了：'+ this.innerHTML);
	    console.log(data);
	  }); */
	
});	 

	  check();

</script>
</body>
</html>