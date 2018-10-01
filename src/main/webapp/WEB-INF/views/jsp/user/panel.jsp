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

<jsp:useBean id="user" class="com.lwhtarena.company.web.entities.User" scope="request" ></jsp:useBean>
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
  
  //监听Tab切换
  element.on('tab(demo)', function(data){
    layer.msg('切换了：'+ this.innerHTML);
    console.log(data);
  });
  
  //监听工具条
  table.on('tool(demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
    var data = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
    if(layEvent === 'detail'){
      layer.msg('查看操作');
    } else if(layEvent === 'del'){
      layer.confirm('真的删除行么', function(index){
        obj.del(); //删除对应行（tr）的DOM结构
        layer.close(index);
        //向服务端发送删除指令
      });
    } else if(layEvent === 'edit'){
      layer.msg('编辑操作');
    }
  });
  
  //执行一个轮播实例
  carousel.render({
    elem: '#test1'
    ,width: '100%' //设置容器宽度
    ,height: 200
    ,arrow: 'none' //不显示箭头
    ,anim: 'fade' //切换动画方式
  });
  
  //将日期直接嵌套在指定容器中
  var dateIns = laydate.render({
    elem: '#laydateDemo'
    ,position: 'static'
    ,calendar: true //是否开启公历重要节日
    ,mark: { //标记重要日子
      '0-10-14': '生日'
      ,'2017-10-26': ''
      ,'2017-10-27': ''
    } 
    ,done: function(value, date, endDate){
      if(date.year == 2017 && date.month == 10 && date.date == 27){
        dateIns.hint('明天不上班');
      }
    }
    ,change: function(value, date, endDate){
      layer.msg(value)
    }
  });
  
  
});


</script>
<script lang='javascript' type='text/javascript'> 
var uri="${pageContext.request.contextPath }/action_user/loginTest";
					
						$.ajax({
							url: uri,
							type: 'post',
							async: false,
							success: function(data) {
								//alert("return:"+data);
								
							}
						});
</script>
<p style="text-align: right;"></p>
<c:set var="l" value="${pageContext.request.contextPath }/action_user/loginTest"  scope="page"/>
<c:set var="ll" value="${loginstatus }"  scope="page"/>
<c:set var="lll" value="${loginstatus }"  scope="page"/>
<c:out value="${loginstatus}"/>
<p>
值1：${l }
</p>
<p>
值2：${ll }
</p>
<p>
值3：${requestScope.loginstatus}
</p>

<c:if test="${(cookie['username_lerx'].value !=null &&cookie['username_lerx'].value eq '')}">

</c:if>
<c:if test="${not login}">
<form:form modelAttribute="user" id="newuser"
		action="${pageContext.request.contextPath }/action_user/login"
		method="post" class="layui-form" accept-charset="UTF-8">
		
		<input type="hidden" name="token" value="${hq.buildToken()}" />
		<div class="toppos"></div>

		<div class="runtest">
			<div class="layui-form-item">
				<label class="layui-form-label">用户名</label>
				<div class="layui-input-block">
					<form:input path="username" required="true"  lay-verify="required" placeholder="用户名，建议使用英文字母和数字" autocomplete="off" class="layui-input" /> <form:errors path="username"></form:errors>

				</div>
			</div>
			<spring:message code='defalut.password' var="defpws"></spring:message>
			<div class="layui-form-item">
				<label class="layui-form-label">密码</label>
				<div class="layui-input-block">
					<form:input path="password"   placeholder="请输入密码，此处空白则使用默认密码  ${defpws}" autocomplete="off" class="layui-input" /> <form:errors path="password"></form:errors>

				</div>
			</div>
			
			<div class="layui-form-item">
				<div class="layui-input-block addbtn">
				<button  class="layui-btn" lay-submit 
						lay-filter="add" >提交</button>

				</div>
			</div>


		</div>
	</form:form>
</c:if>


	
<script type="text/javascript">

function convertDateToUnix(str){
	
	str = str.replace(/-/g,"/");
	var date = new Date(str);
	var unixDate = new Date(Date.UTC(date.getFullYear(),date.getMonth(),date.getDate(),date.getHours(),date.getMinutes(), date.getSeconds()));   
	return unixDate.getTime();
}



</script>
<script type="text/javascript">


layui.use('form', function() {
	var form = layui.form;

	form.on('submit(add)', function(data){
		/* if ($("#agroup").val() == 0){
			parent.layer.alert("您必须选择文章所属的栏目后才能发布");
			return false;
		} */
	});
	
	
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