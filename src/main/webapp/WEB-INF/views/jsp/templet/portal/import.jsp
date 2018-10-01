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



<title>上传</title>
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
<form name="upload" id="upload" action="${pageContext.request.contextPath}/action_portal_templet/import" method="post" enctype="multipart/form-data">
	<span class="sub_file"><input type="file" id="file" name="file" class="layui-btn test" lay-data="{accept: 'file',exts: 'portal'}" ></span>
	<span class="sub_but"><input type="button" onclick="javascript:importTemp();" value="导入" /></span>
	<p>
	<button class="layui-btn test" lay-data="{url: '${pageContext.request.contextPath}/action_portal_templet/import', accept: 'file',exts: 'portal'}">上传文件</button>
	
	</p>
</form>
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

upload.render({
	  elem: '.test'
	  ,done: function(res, index, upload){
	    //获取当前触发上传的元素，一般用于 elem 绑定 class 的情况，注意：此乃 layui 2.1.0 新增
	    var item = this.item;
	  }
	})

function importTemp() {
	var form = new FormData(document.getElementById("upload"));
	if ($("#file").val()==''){
		alert("请选择一个本地文件！");
	}else{
		$.ajax({
	        url:"${pageContext.request.contextPath}/action_portal_templet/import",
	        type:"post",
	        data:form,
	        processData:false,
	        contentType:false,
	        success:function(data){
	           
	        	alert("上传成功");
	        	location.reload();
	        },
	        error:function(e){
	            alert("上传失败！！");
	         
	        }
	    });
	}
	
	        
	
}
</script>

</body>
</html>