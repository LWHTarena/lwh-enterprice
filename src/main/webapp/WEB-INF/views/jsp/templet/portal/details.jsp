<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="tu" class="com.lwhtarena.company.sys.util.TimeUtil" scope="page" ></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>模板内容</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}" media="all">
  <script src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js"></script>
</head>
<script type="text/javascript">
	layui.use('element', function() {
		var element = layui.element;

		//一些事件监听
		element.on('tab(demo)', function(data) {
			console.log(data);
		});
	});
	
</script>

<body>

<div class="layui-form">
	<div class="layui-form-item">
    <label class="layui-form-label">标签位置：</label>
    <div class="layui-input-block">
      <select lay-filter="test" name="city" class="area" id="area" lay-verify="required">
      
        <option value="0" <c:if test="${area==0 }"> selected </c:if> >(全局标签)</option>
        <c:forEach items="${requestScope.els }" var="vars" varStatus="st">
        <option value="${st.index+1}"  <c:if test="${area==(st.index+1) }"> selected </c:if> >${vars}</option>
        </c:forEach>
      </select>
    </div>
  </div>
 </div>
 
<script>
layui.use('form', function(){
  var form = layui.form;
  form.on('select(test)', function(data){
		 var url="${pageContext.request.contextPath}/action_portal_templet/fields/${requestScope.templetID }/"+$("#area").val();
		 location.href =url;
	 });
  //各种基于事件的操作，下面会有进一步介绍
});
form.render(); //更新全部
</script>
	<table class="layui-table">
				<thead>
					<tr>
						<th>序号</th>
						<th>标签名</th>
						<th>操作</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${requestScope.fields }" var="vars" varStatus="st">
					<tr>
						<td>${st.index+1}</td>
						<td>${vars}</td>
						<td><a href="${pageContext.request.contextPath}/action_portal_templet/viewcode/${requestScope.templetID }/${requestScope.area }/${vars}">查看和编辑</a></td>
					</tr>
					</c:forEach>
				</tbody>
				
				
				</table>

</body>
</html>