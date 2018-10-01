<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags" prefix="date" %>
<jsp:useBean id="tu" class="com.lwhtarena.company.sys.util.TimeUtil" scope="page" ></jsp:useBean>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		  <script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/bootstrap-3.3.7-dist/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}" media="all">
		<script src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js"></script>
		<script type="text/javascript" src='${pageContext.request.contextPath}/scripts/js/jquery/jquery.qrcode.min.js'></script>
		<script src="${pageContext.request.contextPath}/scripts/js/lerxui/gen.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/css/iconfont/iconfont.css">
		<style>
			.user-card .logo{text-align: center; margin-top: 20px;}
			.card-header{ background: url(../images/002.jpg) center; height:200px}
			.logo img{width:100px; height:100px;border-radius:50%; border:2px #fff solid; margin-top:30px;}
			.user-card .user-image{ text-align: center; margin-top: 10px;}
			.user-card .user-name{ margin-top:5px; font-size: 18px; color:#333; margin:10px 0 15px 0;}
			.user-info-hl{ background: #ddd; padding: 8px; margin:10px 0;}
			.user-card .row{ height: 30px; border-bottom: 1px #ccc dotted; padding:5px 15px;}
			.user-card .col-xs-4{ font-weight: bold;}
			.user-card .col-xs-5{ font-size: 12px;}
			.change-user-info{  margin:10px 0;}
			.iconfont{font-size:14px;}
			.icon {
   				width: 1em; height: 1em;
   				vertical-align: -0.15em;
   				fill: currentColor;
  				overflow: hidden;
				}
		</style>
		<title>${user.username } 的手机名片</title>
	</head>
	<body>
		<div class="user-card">
			<div class="container">
            <div class="card-header">
            
				<div class="logo"><img id="avatar"  alt=""/></br >
				<div class="user-name">${user.username }</div>
				<div class="change-user-info hidden-xs">
					<span class="user-mobile" ><a title="修改手机号码" href="javascript:chgmobile('${pageContext.request.contextPath}',${user.id });"><i class="iconfont icon-shouji">修改手机</i> <span id="user-mobile"></span></a></span>
					<span class="user-email"><a title="修改邮箱" href="javascript:chgemail('${pageContext.request.contextPath}',${user.id });"><i class="iconfont icon-email">修改邮箱</i> ${user.email }</a></span>
					<span class="pws" id="pws"><a title="修改密码" href="javascript:pws('${pageContext.request.contextPath}',${user.id });"><i class="iconfont icon-tubiao202">修改密码</i></a></span></span>
				</div>
				
            </div>
				<h4 class="user-info-hl hidden-xs">二维码</h4>
				<div class="user-image hidden-xs" id="qrcode"></div>
				<script type="text/javascript">
				var uri=window.location.href;
					$('#qrcode').qrcode({width: 100,height: 100,text: uri}); 
				</script>
				<h4 class="user-info-hl">用户信息</h4>
				<div class="row">
					<div class="col-xs-4">UID</div>
					<div class="col-xs-8">${user.id } <span id="detailsbtn"></span></div>
				</div>
				
				<div id="details" class="details" >
					<div class="row">
						<div class="col-xs-4">注册IP</div>
						<div class="col-xs-8">${user.createIP }</div>
					</div>
					<div class="row">
						<div class="col-xs-4">注册时间</div>
						<div class="col-xs-8"><date:date value ='${user.createTime }' fmtstr='yyyy-MM-dd HH:mm:ss' /></div>
					</div>
					<div class="row">
						<div class="col-xs-4">上次IP</div>
						<div class="col-xs-8">${user.lastLoginIP }</div>
					</div>
					<div class="row">
						<div class="col-xs-4">上次时间</div>
						<div class="col-xs-8"><date:date value ='${user.lastLoginTime }' fmtstr='yyyy-MM-dd HH:mm:ss' /></div>
					</div>
					<div class="row">
						<div class="col-xs-4">真实姓名</div>
						<div class="col-xs-8">${user.truename }</div>
					</div>
					
					<div class="row">
						<div class="col-xs-4">审核</div>
						<div class="col-xs-8"></div>
					</div>
				</div>
				
			</div>
		</div>
		<script src="${pageContext.request.contextPath}/scripts/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
		<script type="text/javascript">
		var con=false;
		$.ajax({
			url:'${pageContext.request.contextPath}/action_user/match/${user.id}',
			type: 'post',
			async: false,
			success:function(data){
				if (data==0){
					$('#pws').show();
					con=true;
				}else{
					$('#pws').hide();
				}
			}
		 });
		layui.use(['element','upload'], function() {
			var element = layui.element,
			upload = layui.upload;

			if (con) {
				//执行实例
				  var uploadInst = upload.render({
				    elem: '#avatar' //绑定元素
				    ,url: '${pageContext.request.contextPath}/action_file/upload' //上传接口
				    /* ,accept: 'file'
				    ,exts: 'jpg' */
				    ,done: function(res){
				    	
				    	if (res.code==0){
				    		var avatarUrl=res.data.src;
				    		 
				    		$.ajax({
				    			url:'${pageContext.request.contextPath}/action_user/avatar?uid=${user.id }&avatar='+res.data.src,
				    			type: 'post',
				    			async: false,
				    			success:function(data){
					    			if (data==0){
					    				$("#avatar").attr('src',avatarUrl);
					    				layer.msg("头像更新成功！页面刷新以后才能正确显示。");
					    			}else{
					    				layer.msg("头像更新失败！");
					    			}
				    			}
				    		 });
				    		
				    		
				    		
				    		//location.reload();
				    	}else{
				    		layer.alert("上传失败！");
				    	}
				      //上传完毕回调
				    }
				    ,error: function(){
				      //请求异常回调
				    }
				  });
				
			}
			
			
			
			form.render();
		});
		
		
		
		var mobile="${user.mobile }";
		mobile=mobile.replace(/(\d{4})\d{4}(\d{3})/, '$1****$2');
		$("#user-mobile").html(mobile);
		var avatarUrl="${user.avatarUrl }" ;
		
		if (avatarUrl.length<5){
			avatarUrl='${requestScope.avatarNull }';
		}
		
		$("#avatar").attr('src',avatarUrl); 
		
		</script>
		
	</body>
</html>
