<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="/tags" prefix="date" %>
<jsp:useBean id="dateValue" class="java.util.Date"/>
<jsp:setProperty name="dateValue" property="time" value="${timestampValue}"/>
<jsp:useBean id="tu" class="com.lwhtarena.company.sys.util.TimeUtil" scope="page" ></jsp:useBean>
<jsp:useBean id="cu" class="com.lwhtarena.company.sys.util.ContextUtil" scope="page" ></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}" media="all">
  <script src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js"></script>
  <script src="${pageContext.request.contextPath}/scripts/js/lerxui/gen.js"></script>
</head>
<style type="text/css">
.name {font-weight: bolder;}
</style>
<body>

<div style="padding: 20px; background-color: #F2F2F2;">
  <div class="layui-row layui-col-space15">
    <div class="layui-col-md12">
      <div class="layui-card">
        <div class="layui-card-header">
        	<div class="layui-row">
    		<div class="layui-col-sm10">
				模板 <span class="name" id="name"></span> 详情 
    		</div>
  			<div class="layui-col-sm2" style="padding-top: 5px;">
				<a class="layui-btn layui-btn-sm layui-btn-radius" href="javascript:downbef();"><i class="layui-icon layui-icon-add-1"></i> 获取</a>
    		</div>
			</div>
        </div>
        <div class="layui-card-body">
        	<table class="layui-table">
  <colgroup>
    <col width="10">
    <col width="200">
  </colgroup>
  <tbody>
    <tr>
      <td>作者：</td>
      <td><span id="author"></span></td>

    </tr>
    <tr>
      <td>说明：</td>
      <td><span id="description"></span></td>
    </tr>
    <tr>
      <td>创建时间：</td>
      <td><span id="designTime"></span></td>

    </tr>
    <tr>
      <td>修改时间：</td>
      <td><span id="modifyTime"></span></td>
    </tr>
    <tr>
      <td>预览：</td>
      <td>
      	<div class="demo" id="layer-photos-demo">
          <a href="javascript:demoshow();"><img id="demo" alt="" src=""></a>
          <div>点击图片查看大图</div>
        </div>
      </td>
    </tr>
  </tbody>
</table>
        </div>

        <div class="layui-card-header"><a class="layui-btn layui-btn-fluid" href="javascript:window.history.go(-1);">返回</a></div>

      </div>
    </div>

  </div>
</div> 

<script type="text/javascript">
function descr(str) {
	 
	 var re=/\r\n/;
	 str=str.replace(/[\r\n]/g,"<br>");
	 var reg = new RegExp( '<br><br>' , "g" )
	 str=str.replace(reg,"<br>");
	 return str;
}

var uri="${requestScope.marketUrl}/action_templet_market/demo/${requestScope.remotID}";
var free;
var id;
		
			$.ajax({
				url: uri,
				type: 'post',
				success: function(data) {
					id=data.id;
					$("#name").html(data.name);
					$("#author").html(data.author);
					$("#description").html(descr(data.description));
					
					$("#designTime").html(data.designTime);
					$("#modifyTime").html(data.modifyTime);
					var demourl="${requestScope.marketUrl}/"+data.demo;
					$("#demo").attr("src",demourl);
					
					free=data.free;
					
				}
			});
			
			
			function downbef() {
				var uri="${requestScope.marketUrl}/action_templet_market/download/"+id;
				//alert(uri);
				if (free==0){
					
					parent.layer.prompt({
						  formType: 0,
						  title: '请输入您获得的密钥'
						}, function(value, index, elem){
							
							uri+="/"+value;
							down(uri);
							//layer.close(index);
					});
					
				}else{
					
					parent.layer.confirm('点击下载后，模板将自动下载并导入您当前的服务器，资源文件将自动解压到相当的目录！<br />您需要刷新模板列表（右键或重点左侧链接），如果模板已经存在(uuid为模板的唯一身份)，将进行覆盖。', function(index){
						
						uri+="/null";
						down(uri);
					}); 
					
					
				}
				
				
			}
			
function down(uri) {
	
				if (uri=="" || uri == "null"){
					parent.layer.alert("请求失败！仔细检查您的密钥是否有错！");
				}else{
					var fileUri;
					$.ajax({
						url: uri,
						type: 'post',
						async:false,
						success: function(data) {
							
							fileUri=data;
							
							//layer.alert("3:"+fileUri);
							
						}
					});
					
					
					if (fileUri=="" || fileUri == "null"){
						parent.layer.alert("请求失败！仔细检查您的密钥是否有错！");
					}else{
						var downuri="${pageContext.request.contextPath}/action_portal_templet/downRemote?url="+fileUri;
						$.ajax({
							url: downuri,
							type: 'post',
							success: function(data) {
								
								parent.layer.alert('成功!请刷新模板窗口！', function(index){
									
									parent.layer.closeAll();
									
								}); 
								
							}
						});
					}
					
				}
				
				
				  
	}
	
function demoshow() {
	//alert($("#demo")[0].src);
	var w=parentW();
	w.srcshow($("#demo")[0].src);
	
}



</script>	

<script type="text/javascript">

layui.use(['form','laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element'], function(){
	  var form = layui.form
	  ,laydate = layui.laydate //日期
	  ,laypage = layui.laypage //分页
	  ,layer = layui.layer //弹层
	  ,table = layui.table //表格
	  ,carousel = layui.carousel //轮播
	  ,upload = layui.upload //上传
	  ,element = layui.element; //元素操作
	  
	 
	});
  

</script>		
</body>
</html>