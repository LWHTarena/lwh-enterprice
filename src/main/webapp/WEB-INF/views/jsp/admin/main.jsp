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
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}" media="all">
<script src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js?t=${tu.getCurrTime()}"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/js/jquery/jquery.qrcode.min.js"></script>
<script src="${pageContext.request.contextPath}/scripts/js/lerxui/gen.js"></script>
<title>${requestScope.portal}后台管理</title>

<style type="text/css">
body, div, dl, dt, dd, ul, li, h1, h2, h3, h4, h5, h6, pre, form, fieldset, select, input, textarea, button, p, blockquote, th, td, img ,iframe { margin: 0; padding: 0; }
table{ border-collapse: collapse; border-spacing: 0; }
input,button,textarea,option { font: 12px "\5b8b\4f53", Arial, Helvetica, sans-serif; }
ul, li, div{ list-style: none; border: 0px; }
.layui-layout .layui-header .layui-logo {width:auto;}
img{ border: 0px; }
.clear{ font: 0px/0px serif; display: block; clear: both; }
html{ -webkit-text-size-adjust: none; }
input{ outline: none; }
textarea{ resize: none; }
a{ text-decoration: none; }
.layui-icon-friends{font-size: 24px;}
.layui-footer{text-align: right;}
.layui-logo{padding-left: 20px;}
</style>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo appname">${requestScope.portal}管理后台</div>
    <!-- 头部区域（可配合layui已有的水平导航） -->
    
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item">
        <a href="javascript:;">
          <i class="layui-icon layui-icon-friends"></i> 
          ${ requestScope.adminName } 
          
          
        </a>
        <dl class="layui-nav-child">
          <dd><a href="javascript:crucial();">基本资料</a></dd>
          <dd><a href="javascript:pws();">修改密码</a></dd>
        </dl>
      </li>
      <li class="layui-nav-item"><a href="${pageContext.request.contextPath}/action_admin/logout">安全退出</a></li>
    </ul>
  </div>
  
  <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
      <ul class="layui-nav layui-nav-tree" lay-shrink="all"  lay-filter="layadmin-system-side-menu">
        <li data-name="System" data-jump  class="layui-nav-item layui-nav-itemed">
          <a class="" href="javascript:;" >站点管理</a>
          <dl class="layui-nav-child">
            <dd><a target="myframe" href="${pageContext.request.contextPath}/action_portal/query">　<i class="layui-icon">&#xe623;</i> 网站信息</a></dd>
            <dd><a target="myframe" href="${pageContext.request.contextPath}/action_admin/list">　<i class="layui-icon">&#xe623;</i> 后台管理员</a></dd>
            <dd><a target="myframe" href="${pageContext.request.contextPath}/action_res/list">　<i class="layui-icon">&#xe623;</i> 资源文件</a></dd>
            
          </dl>
        </li>
        <li class="layui-nav-item" data-jump data-name="user">
          <a href="javascript:;" >前台用户管理</a>
          <dl class="layui-nav-child">
            <dd><a target="myframe" href="${pageContext.request.contextPath}/action_role/list">　<i class="layui-icon">&#xe623;</i> 角色（用户组管理）</a></dd>
            <dd><a target="myframe" href="${pageContext.request.contextPath}/action_user/list">　<i class="layui-icon">&#xe623;</i> 用户管理</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item" data-jump data-name="user">
          <a href="javascript:;" >文章管理</a>
          <dl class="layui-nav-child">
            <dd><a target="myframe" href="${pageContext.request.contextPath}/action_agroup/list">　<i class="layui-icon">&#xe623;</i> 栏目管理</a></dd>
            <dd><a target="myframe" href="${pageContext.request.contextPath}/action_article/list">　<i class="layui-icon">&#xe623;</i> 文章管理</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item" data-jump data-name="user">
          <a href="javascript:;" >模板管理</a>
          <dl class="layui-nav-child">
            <dd><a target="myframe" href="${pageContext.request.contextPath}/action_portal_templet/list">　<i class="layui-icon">&#xe623;</i> 门户模板列表</a></dd>
          </dl>
        </li>
        <li class="layui-nav-item" data-jump data-name="user">
          <a href="javascript:;" >系统信息</a>
          <dl class="layui-nav-child">
            <dd><a target="myframe" href="${pageContext.request.contextPath}/action_portal/env">　<i class="layui-icon">&#xe623;</i> 运行状态</a></dd>
            <dd><a target="myframe" href="${pageContext.request.contextPath}/action_log/list">　<i class="layui-icon">&#xe623;</i> 操作日志</a></dd>
            <dd><a target="myframe" href="${pageContext.request.contextPath}/jsp/result/welcome">　<i class="layui-icon">&#xe623;</i> 后台首页</a></dd>
            <dd><a target="myframe" href="${pageContext.request.contextPath}/jsp/result/support">　<i class="layui-icon">&#xe623;</i> 服务与支持</a></dd>
            <dd><a target="myframe" class="donate" href="${pageContext.request.contextPath}/jsp/result/donate"><i class="layui-icon">&#xe6af;</i>  　赞助开发</a></dd>
          </dl>
        </li>
      </ul>
    </div>
  </div>
  
  <div class="layui-body">
  
    <!-- 内容主体区域 -->
    <iframe id="myframe" name="myframe" scrolling="AUTO" NORESIZE frameborder="0" width="100%"  height="99%"  <c:if test="${requestScope.donate }"> src="${pageContext.request.contextPath}/jsp/result/donate" </c:if> <c:if test="${!requestScope.donate }"> src="${pageContext.request.contextPath}/jsp/result/welcome" </c:if> ></iframe>
  </div>
  
  <div class="layui-footer">
    <!-- 底部固定区域 -->
   技术支持：<a href='<spring:message code="company.portal.url"></spring:message>' target="_blank"><spring:message code="company"></spring:message></a>　　　　　 版本号：<spring:message code="version.Number"></spring:message> 　　build: <spring:message code="version.Build"></spring:message>
  </div>
</div>

<script>
//JavaScript代码区域

 
layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element'], function(){
  var laydate = layui.laydate //日期
  ,laypage = layui.laypage //分页
  ,layer = layui.layer //弹层
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
  
  //分页
  laypage.render({
    elem: 'pageDemo' //分页容器的id
    ,count: 100 //总页数
    ,skin: '#1E9FFF' //自定义选中色值
    //,skip: true //开启跳页
    ,jump: function(obj, first){
      if(!first){
        layer.msg('第'+ obj.curr +'页');
      }
    }
  });
  
  //上传
  upload.render({
    elem: '#uploadDemo'
    ,url: '' //上传接口
    ,done: function(res){
      console.log(res)
    }
  });
});
</script>

<script>
function crucial(){
	var uri ='${pageContext.request.contextPath}/action_admin/crucial';
	$.ajax({
		url : uri,
		type : 'post',
		success : function(data) {
			
			if (data.uid>0){
				var result="你的ID:"+data.uid;
				
				result += "<br>注册时间：" + data.regDatetime;
				result += "<br>注册IP："+data.regIP;
				result += "<br>上次登录的时间："+data.lastLoginDatetime;
				result += "<br>上次登录的IP："+data.lastLoginIP;
				layer.alert(result,{title: "个人信息"});
			}else if (data.uid<0){
				/* layer.alert("对不起，查不到您的信息。您可能登录超时，请重新登录！"); */
				layer.alert('对不起，查不到您的信息。您可能登录超时，请重新登录', function(index){
					$(window).attr('location','${pageContext.request.contextPath}/admin/login');
				});
			}else{
				layer.alert('当前为配置文件登录！查询登录情况请查看日志文件！');
			}
			
		}
	});
}

function pws(){
	var adminID=${ sessionScope.session_admin_uid };
	if (adminID==0){
		layer.alert('当前为配置文件登录！修改密码请联系管理员！');
	}else{
		layer.prompt({
			  formType: 0,
			  value: '',
			  title: '请输入新的密码',
			  area: ['800px', '350px'] //自定义文本域宽高
			}, function(value, index, elem){
				var uri ='${pageContext.request.contextPath}/action_admin/ownpws?password=' + value;
					
				  $.ajax({
						url : uri,
						type : 'post',
						success : function(data) {
							layer.close(index);
							if (data== 0){
								layer.msg('修改成功！');
								//alert("修改成功！");
							}else{
								layer.alert('对不起，请检查您是否有权限或重新登录后操作！', function(index2){
									$(window).attr('location','${pageContext.request.contextPath}/admin/login');
								});
							}
						}
					}); 
			  
			});
	}
	
} 

function qr(href){
	//alert(href);
	 var div = document.createElement("div");
	div.id="qrDiv";
	div.style.display = "none";
	document.body.appendChild(div);
	$("#qrDiv").css("padding","5px");
	$('#qrDiv').qrcode(href);
	layer.open({
		  type: 1, 
		  title:'扫描二维码打开地址',
		  shadeClose: true,
		  content: $('#qrDiv'), //这里content是一个普通的String
		  end:function(){
			  $('#qrDiv').remove();
		  }
		}); 
}

function srcshow(src) {
	//alert($("#demo")[0].src);
	
	
	var j={};
	j.title = "demo";
    j.id = 0;
    j.start = 0;
    var data={};
    data.alt="";
    data.pid=666;
    data.src=src;
    data.thumb=src;
    
    var arr = new Array(); 
    arr[0]=data;
    
    j.data=arr;
	
    var a = JSON.stringify(j);
	layer.photos({
		  photos: j
		  ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
		}); 
}
</script>
</body>
</html>