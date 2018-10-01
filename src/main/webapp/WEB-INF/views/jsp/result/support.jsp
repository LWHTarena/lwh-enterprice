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

	<div style="padding: 20px; background-color: #F2F2F2;">
  <div class="layui-row layui-col-space15">
    <div class="layui-col-md6">
      <div class="layui-card">
        <div class="layui-card-header">说明</div>
        <div class="layui-card-body" id="readme">
        </div>
      </div>
    </div>
    <div class="layui-col-md6">
      <div class="layui-card">
        <div class="layui-card-header">模板支持</div>
        <div class="layui-card-body" id="templet">
        </div>
      </div>
    </div>
    
    <div class="layui-col-md12">
      <div class="layui-card">
        <div class="layui-card-header">收费服务与支持</div>
        <div class="layui-card-body"  id="vip">
          
        </div>
      </div>
    </div>
    
  </div>
</div> 
<script type="text/javascript">

String.prototype.startWith=function(str){     
	  var reg=new RegExp("^"+str);     
	  return reg.test(this);        
	}  

String.prototype.endWith=function(str){     
	  var reg=new RegExp(str+"$");     
	  return reg.test(this);        
	}
	
var uri='<spring:message code="support.srv.url"></spring:message>';
if (uri.endWith('/')){
		uri +='action_support';
}else{
	uri += '/action_support';
}

function remoteInf(div,field) {
	
	$.ajax({
		
		url: uri+'/'+field+'?newlines='+escape('</p><p>'),
		type: 'post',
		success: function(data) {
			var txt='<p>'+data+'</p>';
			$("#"+div).html(txt);
		}
	});
}

remoteInf('readme','readme');
remoteInf('templet','templet');
remoteInf('vip','vip');
</script>

</body>
</html>