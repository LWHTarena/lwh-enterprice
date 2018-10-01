<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="sutil" class="com.lwhtarena.company.sys.util.StringUtil" scope="page" ></jsp:useBean>
<%@ taglib uri="/tags" prefix="date" %>
<jsp:useBean id="dateValue" class="java.util.Date"/>
<jsp:setProperty name="dateValue" property="time" value="${timestampValue}"/>
<jsp:useBean id="hq" class="com.lwhtarena.company.sys.util.HttpUtil" scope="page" ></jsp:useBean>
<jsp:useBean id="tu" class="com.lwhtarena.company.sys.util.TimeUtil" scope="page" ></jsp:useBean>
<jsp:useBean id="su" class="com.lwhtarena.company.sys.util.StringUtil" scope="page" ></jsp:useBean>
<!DOCTYPE html>
<html lang="zh-CN">

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
		<title>搜索- ${requestScope.portal.name }</title>

		<link rel="stylesheet" href="${pageContext.request.contextPath}/templates/portal/licai/css/bootstrap.min.css" />

		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}" media="all">
		<script src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js"></script>

		<link href="${pageContext.request.contextPath}/scripts/css/search.css" rel="stylesheet">

		<!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->
		<!-- 警告：通过 file:// 协议（就是直接将 html 页面拖拽到浏览器中）访问页面时 Respond.js 不起作用 -->
		<!--[if lt IE 9]>
      <script src="js/html5shiv.min.js"></script>
      <script src="js/respond.min.js"></script>
    <![endif]-->
	</head>

	<body>
		<div class="search_top container">
		<form id="searchform"
			action="${pageContext.request.contextPath}/action_article/search"
			class="layui-form" method="post">
			<div class="row">
				<div class="col-sm-5"><span class="search_logo"><img src="${pageContext.request.contextPath}/templates/portal/licai/images/search_logo.png" /></span><span class="website_name">${portal.name }搜索结果</span><div class="clearfix"></div></div>
				<div class="col-sm-4 search_button">
					<div class="input-group">
						<input type="text" class="form-control" name="keywords" id="keywords" value="${keywords }" placeholder="站内搜索">
						<span class="input-group-btn">
						<button  class="layui-btn btn btn-primary" lay-submit lay-filter="search" ><span class="glyphicon glyphicon-search"></span></button>
						</span>
					</div>
				</div>
				
			</div>
		</form>
		</div>

		<c:if test="${empty requestScope.rs.list && !empty keywords }">
		<div class="nullSoftware">没有任何文章。</div>
	
		</c:if>

		<c:forEach items="${requestScope.rs.list }" var="art" varStatus="st">


		<div class="search_result_con">



			<div class="result_title"><a target="_blank" href="${art.hfs.url }">${art.subject}</a><c:if test="${art.user!=null }"><span class="author">发布：${art.user.username }</span></c:if><c:if test="${art.author!=null && art.author!='' }"><span class="author_other">作者：${art.author}</span></c:if></div>
			<div class="result_content">[<date:date value ='${art.creationTime }' fmtstr='yyyy-MM-dd' />] &nbsp;&nbsp;${art.content} ...</div>
			<div class="result_url"><a target="_blank" href="${art.hfs.url }">${art.hfs.url }</a></div>
		</div>


		</c:forEach>
		
		<c:if test="${!empty requestScope.rs.list  }">
		<div class="pagestr hidden-xs hidden-sm" id="pagestr1"></div>
		<div class="pagestr hidden-md hidden-lg" id="pagestr2"></div>
 		</c:if>
 		
<script type="text/javascript">

layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element'], function(){
  var laydate = layui.laydate //日期
  ,laypage = layui.laypage //分页
  layer = layui.layer //弹层
  ,table = layui.table //表格
  ,carousel = layui.carousel //轮播
  ,upload = layui.upload //上传
  ,element = layui.element; //元素操作
  
 
  
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
    elem: 'pagestr1' //分页容器的id
    ,count: ${rs.count} //总页数
    ,limit:${rs.pageSize}
    ,curr:${rs.page}
    ,skin: '#1E9FFF' //自定义选中色值
    //,skip: true //开启跳页
    ,jump: function(obj, first){
      if(!first){
        
        var jurl='${pageContext.request.contextPath}${pageUrl}';
        if (jurl.indexOf('?') != -1){
        	jurl+='&page='+obj.curr+'&pageSize=${rs.pageSize}';
        }else{
        	jurl+='?page='+obj.curr+'&pageSize=${rs.pageSize}';
        }
        //layer.msg('第'+ obj.curr +'页 ' + jurl);
        window.location.href=jurl;
        
      }
    }
  });
  
//分页
  laypage.render({
    elem: 'pagestr2' //分页容器的id
    ,count: ${rs.count} //总页数
    ,limit:${rs.pageSize}
    ,curr:${rs.page}
    ,skin: '#1E9FFF' //自定义选中色值
    ,layout: ['prev', 'next']
    //,skip: true //开启跳页
    ,jump: function(obj, first){
      if(!first){
        
        var jurl='${pageContext.request.contextPath}${pageUrl}';
        if (jurl.indexOf('?') != -1){
        	jurl+='&page='+obj.curr+'&pageSize=${rs.pageSize}';
        }else{
        	jurl+='?page='+obj.curr+'&pageSize=${rs.pageSize}';
        }
        //layer.msg('第'+ obj.curr +'页 ' + jurl);
        window.location.href=jurl;
        
      }
    }
  });
  
  
});


</script>

	
		<script src="${pageContext.request.contextPath}/templates/portal/licai/js/bootstrap.min.js"></script>
	</body>

</html>