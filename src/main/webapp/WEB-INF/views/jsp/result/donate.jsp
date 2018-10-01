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
<title>赞助</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}" media="all">
<script src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js"></script>
</head>
<style type="text/css">
.site-code{margin: 0 auto;text-align:center;margin-top: 100px;}
.msg{font-size: 20px;}

.prompt1{margin-top: 40px;}
.prompt2{margin-top: 10px;}
.qrcode{margin-top: 30px;text-align: center;position:relative;}
.layui-tab{width: 400px;margin: auto;  }
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
	
	<div class="prompt1">扫描下面的二维码，赞助和支持Lerx项目开发！您也许会获得意想不到的收获！</div>
	<div class="qrcode">
	
		<div class="layui-tab" lay-filter="demo">
		  <ul class="layui-tab-title">
		    <li class="layui-this" lay-id="11">微信支付</li>
		    <li lay-id="22">支付宝支付</li>
		   
		  </ul>
		  <div class="layui-tab-content">
		    <div class="layui-tab-item layui-show"><img alt="" height="400" src="${pageContext.request.contextPath}/images/lerx/receipt/weixin.jpg"></div>
		    <div class="layui-tab-item"><img alt="" height="400" src="${pageContext.request.contextPath}/images/lerx/receipt/payee.jpg"></div>
		    
		  </div>
		</div>
	
	</div>
	<div class="prompt2">Lerx获得您的赞助，将贴心了解和支持您的需求！</div>
	<div class="prompt2">赞助说明：金额任意。请备注您的信息，谢谢！</div>
	
		
	</div>
	
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
layui.use('element', function(){
  var $ = layui.jquery
  ,element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
  
  //触发事件
  var active = {
    tabAdd: function(){
      //新增一个Tab项
      element.tabAdd('demo', {
        title: '新选项'+ (Math.random()*1000|0) //用于演示
        ,content: '内容'+ (Math.random()*1000|0)
        ,id: new Date().getTime() //实际使用一般是规定好的id，这里以时间戳模拟下
      })
    }
    ,tabDelete: function(othis){
      //删除指定Tab项
      element.tabDelete('demo', '44'); //删除：“商品管理”
      
      
      othis.addClass('layui-btn-disabled');
    }
    ,tabChange: function(){
      //切换到指定Tab项
      element.tabChange('demo', '22'); //切换到：用户管理
    }
  };
  
  $('.site-demo-active').on('click', function(){
    var othis = $(this), type = othis.data('type');
    active[type] ? active[type].call(this, othis) : '';
  });
  
  //Hash地址的定位
  var layid = location.hash.replace(/^#test=/, '');
  element.tabChange('test', layid);
  
  element.on('tab(test)', function(elem){
    location.hash = 'test='+ $(this).attr('lay-id');
  });
  
});
</script>
</body>
</html>