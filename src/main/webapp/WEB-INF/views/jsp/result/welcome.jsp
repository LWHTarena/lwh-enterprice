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
<title>欢迎</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}" media="all">
<script src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js"></script>
</head>
<style type="text/css">
.site-code{margin: 0 auto;text-align:center;margin-top: 100px;}
.msg{font-size: 20px;}

.prompt1{margin-top: 60px;font-weight: bold;font-size: 24px;}
.prompt2{margin-top: 40px;}
.qrcode{margin-top: 30px;}
.verChk{ text-align: left; margin: 30px 0 0 50px;}
.ver_found{font-weight: bolder;font: bolder;font-size: large;color: red;margin-bottom: 20px;}
.ver_downpage{font-weight: bolder;}
.ver_history{margin-top: 10px;font-weight: bolder;}
.ver_history_body{margin-top: 10px;}
.ver_history_loop{margin-left: 10px;}
.ver_history_details{margin-left: 50px;margin-top: 5px;}
.ver_history_details li{list-style:decimal;  }
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
	
	<div class="prompt1">欢迎您使用 <spring:message code="app.name"></spring:message>！</div>
	
	<div class="prompt2">为了避免您在操作过程出现错误或其它不良体验，请不要使用微软IE及IE内核的浏览器！</div>
	
	<div class="verChk" id="curr">正在检测...</div>

<script type="text/javascript">

function verChk(){
    $("#curr").html("正在检测...");
    $.ajax({
        url:'http://58.222.239.220:8000/action_notice/compareToCurr?id=6&clientSubVer=<spring:message code="version.Build"></spring:message>',
        type: 'post',
        success:function(data){
        	//$("#curr").html("yyy");
            $("#curr").html(data.html);
        }
    });
}

verChk();
</script>
		
	</div>
	

</body>
</html>