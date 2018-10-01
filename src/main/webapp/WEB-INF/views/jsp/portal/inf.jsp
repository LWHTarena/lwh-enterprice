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

<jsp:useBean id="portal" class="com.lwhtarena.company.web.entities.Portal" scope="request" ></jsp:useBean>
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
.inf_main{width: 95%}
.layui-form-label {width: 20%;}
.layui-input, .layui-textarea{width: 80%;}
.toppos {margin-top: 20px;}
.addbtn {margin-top: 40px;text-align: center;}
.tips {margin-left: 20px;}
.layui-form-pane .layui-form-label{width:200px;text-align:right;}
</style>



</head>
<body>
<script type="text/javascript">
	var layer;
	layui.use(['layer', 'form'], function(){
		  layer = layui.layer
		  ,form = layui.form;
		  
			 var msg='${msg}';
			 if (msg!='0' && msg!=''){
				 layer.msg(msg); 
			 }
			
		});
</script>
<div style="padding: 20px; background-color: #F2F2F2;">
  <div class="layui-row layui-col-space15">
    <div class="layui-col-md12">
      <div class="layui-card">
      
      
<blockquote class="layui-elem-quote layui-quote-nm"> >> <i class="layui-icon">&#xe612;</i> 网站信息　　 　　　　<a href="javascript:vbook(${portal.vbook.id},'${portal.name}');"> 总访问量：${vbook.viewsTotal } 　　总IP量：${vbook.ipTotal } </a></blockquote>

<!-- ■★▲◆修改点3  表单的id -->
<div class="inf_main">
	<form:form commandName="portal" id="newcustomer" 
		action="${pageContext.request.contextPath }/action_portal/set"
		method="post" class="layui-form layui-form-pane" accept-charset="UTF-8">
		
		<input type="hidden" name="token" value="${hq.buildToken()}" />
		<div class="toppos"></div>

		<div class="runtest">
<!-- ■★▲◆修改点4 test="${person.id >0 } 中的 person 改为 相应的对象 
		
		另以下表单根据类的名称进行相应的修改
		
		 -->
			
			<div class="layui-form-item">
				<label class="layui-form-label">网站名称</label>
				<div class="layui-input-block">
					<form:input path="name" required="true"  lay-verify="required" placeholder="网站名称(简写)" autocomplete="off" class="layui-input" /> <form:errors path="name"></form:errors>

				</div>
			</div>
			
			
			<div class="layui-form-item">
				<label class="layui-form-label">网站名称(全称)</label>
				<div class="layui-input-block">
					<form:input path="fullName"   placeholder="网站名称(全称)" autocomplete="off" class="layui-input" /> <form:errors path="fullName"></form:errors>

				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">网址(URL)</label>
				<div class="layui-input-block">
					<form:input path="url"   placeholder="网址(URL)，请按 http://url 格式完整输入" autocomplete="off" class="layui-input" /> <form:errors path="url"></form:errors>

				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">主机名或域名</label>
				<div class="layui-input-block">
					<form:input path="host"   placeholder="如 www.yourdomain.com或192.168.1.8 不包括http及域名后的参数" autocomplete="off" class="layui-input" /> <form:errors path="host"></form:errors>

				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">限定可注册IP或范围</label>
				<div class="layui-input-block">
					<form:input path="ipRegScope"   placeholder="如：192.168.6.1,192.168.100.1-192.168.100.254，不同的ip或范围请用英文,隔开" autocomplete="off" class="layui-input" /> <form:errors path="ipRegScope"></form:errors>

				</div>
			</div>  
			
			<!-- 
			<div class="layui-form-item">
				<label class="layui-form-label">状态</label>
				<div class="layui-input-block">
				
				
				<form:radiobutton path="status" value="0" title="正常" /> 　
				<form:radiobutton path="status" value="1" title="维护" /> 　　
				<form:radiobutton path="status" value="-1" title="禁用" /> 
				</div>
			</div>
			-->
			<div class="layui-form-item">
				<label class="layui-form-label">验证码发送方式</label>
				<div class="layui-input-block">
				
				
				<form:radiobutton path="codeSendMode" value="0" title="邮件"   /> 　
				<form:radiobutton path="codeSendMode" value="1" title="短信"   /> 　　
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">默认参数</label>
				<div class="layui-input-block">
				<form:checkbox path="userRegAllow" value="true" lay-skin="primary" title="允许用户注册" />
				<form:checkbox path="poll" value="true" lay-skin="primary" title="允许投票" />
				<form:checkbox path="comm" value="true" lay-skin="primary" title="允许评论" />
				<form:checkbox path="freeComm" value="true" lay-skin="primary" title="匿名评论" />
				<form:checkbox path="commPassAuto" value="true" lay-skin="primary" title="评论自动通过" />
				<form:checkbox path="artPassAuto" value="true" lay-skin="primary" title="文章自动审核" />
				
				</div>
			</div>
			<!-- 
			<div class="layui-form-item">
				<label class="layui-form-label">欢迎词</label>
				<div class="layui-input-block">
					<form:input path="welcomeStr"   placeholder="输入一段欢迎辞，可以在网站中使用" autocomplete="off" class="layui-input" /> <form:errors path="welcomeStr"></form:errors>

				</div>
			</div>
			 
			<div class="layui-form-item">
				<label class="layui-form-label">关闭通知</label>
				<div class="layui-input-block">
					<form:input path="closeAnnounce"   placeholder="当网站关闭时，显示该文本" autocomplete="off" class="layui-input" /> <form:errors path="closeAnnounce"></form:errors>

				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">关键字</label>
				<div class="layui-input-block">
					<form:input path="keyWords"   placeholder="多个关键字请用英文,隔开" autocomplete="off" class="layui-input" /> <form:errors path="keyWords"></form:errors>

				</div>
			</div>
			
			<div class="layui-form-item layui-form-text" >
				<label class="layui-form-label">网站描述</label>
				<div class="layui-input-block">
				<form:textarea path="description" placeholder="网站简要文字描述" class="layui-textarea"></form:textarea>

				</div>
			</div>
			-->
			
			
			
			
			<c:if test="${portal.id >0 }">
			<form:hidden path="id" />
			
			</c:if>
			

			
			
			<div class="layui-form-item">
				<div class="layui-input-block addbtn">
				<button class="layui-btn" onclick="commit();" lay-submit lay-filter="formDemo">更新</button>
				</div>
			</div>


		</div>
	</form:form>
</div>
<script type="text/javascript">

function convertDateToUnix(str){
	
	str = str.replace(/-/g,"/");
	var date = new Date(str);
	var unixDate = new Date(Date.UTC(date.getFullYear(),date.getMonth(),date.getDate(),date.getHours(),date.getMinutes(), date.getSeconds()));   
	return unixDate.getTime();
}

function vbook(vid,name) {
	
	parent.layer.open({
	    type: 2,
	    skin: 'layui-layer-rim', //加上边框
	title: '网站　 “'+name+'”　的访问记录',
	    area : ['1200px' , '650px'],
	    content: '${pageContext.request.contextPath}/action_visitor/list?id='+vid,
	    success: function(layero, index){
	    }

	});
	
}

function commit(){
	
	/* $("#startDate").val(convertDateToUnix($("#times").val()));
	$("#expiryDate").val(convertDateToUnix($("#timee").val())); */
	
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
			</div>
		</div>
	</div>
</div>
</body>
</html>