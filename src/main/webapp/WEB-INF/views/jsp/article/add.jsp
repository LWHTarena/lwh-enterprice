<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="sutil" class="com.lwhtarena.company.sys.util.StringUtil"
	scope="page"></jsp:useBean>
<jsp:useBean id="hq" class="com.lwhtarena.company.sys.util.HttpUtil" scope="page"></jsp:useBean>
<jsp:useBean id="tu" class="com.lwhtarena.company.sys.util.TimeUtil" scope="page" ></jsp:useBean>
<jsp:useBean id="article" class="com.lwhtarena.company.web.entities.Article"
	scope="request"></jsp:useBean>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>编辑</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}"
	media="all">
<script src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js"></script>
<script src="${pageContext.request.contextPath}/scripts/js/lerxui/gen.js"></script>

<style type="text/css">
.layui-form-select .layui-edge{right:110px;}
.runtest {
margin-top: 5px;}

.layui-form-label {
	width: 15%;
}

.layui-input {
	width: 95%;
}

.layui-textarea,.layui-layedit {
	width:95%;
}

.toppos {
	margin-top: 20px;
}

.addbtn {
	position:absolute;
	left:150px;
	bottom:-5px;
	margin-top:0;
	margin-left:0;
}

.tips {
	margin-left: 20px;
}
.layui-input-block{
	margin-left:150px;
}
#test1 .layui-icon,#test2 .layui-icon{
	display:inline-block;
	margin-top:8px ;
	font-size:24px;
}

.uploadFile{ width:70% }
</style>
</head>
<body  class="layui-layout-body">
	<div class="runtest">
		<form:form commandName="article" id="articleedit"
			action="${pageContext.request.contextPath}/action_article/add"
			class="layui-form" method="post">
			<input type="hidden" name="token" value="${hq.buildToken()}" />
			
<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
  <ul class="layui-tab-title">
    <li class="layui-this">基本内容</li>
    <li>图片与视频</li>
    <li>扩展</li>
    <li>作者</li>
    <c:if test="${article.id >0 }">
    <li>附件</li>
    </c:if>
  </ul>
  <div class="layui-tab-content" style="height: 100px;">
    <div class="layui-tab-item layui-show">
    <!-- 第1个标签 -->
			<div class="layui-form-item">
				<label class="layui-form-label">栏目 ：</label>
				<div class="layui-input-block">
				
				<form:select path="agroup.id" id="agroup" lay-filter="agroup">
				
				<c:forEach items="${prelist }" var="ag" varStatus="st">
				<form:option label="${ag.title }" disabled="${!ag.clogging }" value="${ag.id }"   >
				
				</form:option>
				</c:forEach>  
					
              		 
           		</form:select> 
           

				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">完整标题 ：</label>
				<div class="layui-input-block">
					<form:input path="subject" required="true" lay-verify="required"
						placeholder="请输入文章标题" autocomplete="off" class="layui-input" />
					<form:errors path="subject"></form:errors>

				</div>
			</div>
			
			
			<div class="layui-form-item">
				<label class="layui-form-label">正文：</label>
				<div class="layui-input-block">
				<form:textarea path="content" id="content" style="display: none;" class="layui-textarea" placeholder="请输入文章内容" />

				</div>
			</div>
			

    </div>
    <div class="layui-tab-item">
    <!-- 第2个标签 -->
    		<div class="layui-form-item">
				<label class="layui-form-label">缩略图 ：</label>
				<div class="layui-input-block">
					<form:input path="thumbnail" id="thumbnail" placeholder="缩略图URL（可选）" autocomplete="off" class="layui-input layui-input-inline uploadFile" />
					<form:errors path="thumbnail"></form:errors><a id="test1" title="上传"  style="cursor:pointer;">　<i class="layui-icon">&#xe681;</i> 上传　</a>

				</div>
			</div>
			
			<div class="layui-form-item">
							<label class="layui-form-label">标题图 ：</label>
							<div class="layui-input-block">
								<form:input path="titleImg" id="titleImg" placeholder="标题图URL（可选）" autocomplete="off" class="layui-input layui-input-inline" />
								<form:errors path="titleImg"></form:errors><a id="test2" title="上传"  style="cursor:pointer;">　<i class="layui-icon">&#xe681;</i> 上传　</a>

							</div>
			</div>
			
			<div class="layui-form-item">
							<label class="layui-form-label">视频文件 ：</label>
							<div class="layui-input-block">
								<form:input path="mediaUrl" id="mediaUrl" placeholder="媒体文件URL（可选），请使用mp4文件" autocomplete="off" class="layui-input" />
								<form:errors path="mediaUrl"></form:errors>

							</div>
			</div>
			


	</div>
    <div class="layui-tab-item">
	<!-- 第3个标签 -->
			<div class="layui-form-item">
							<label class="layui-form-label">精简标题 ：</label>
							<div class="layui-input-block">
								<form:input path="subjectShort"  placeholder="请输入文章精简标题（可选，请精简后填写，不精简无须填写）" autocomplete="off" class="layui-input" />
								<form:errors path="subjectShort"></form:errors>

							</div>
			</div>
			
			<div class="layui-form-item">
							<label class="layui-form-label">附加标题 ：</label>
							<div class="layui-input-block">
								<form:input path="extra" placeholder="请输入一个附加标题（可选）" autocomplete="off" class="layui-input" />
								<form:errors path="extra"></form:errors>

							</div>
			</div>
			<div class="layui-form-item">
							<label class="layui-form-label">专题名称 ：</label>
							<div class="layui-input-block">
								<form:input path="toppic" placeholder="请输入一个专题名称（可选）" autocomplete="off" class="layui-input" />
								<form:errors path="toppic"></form:errors>

							</div>
			</div>
			
			<c:if test="${article.id >0 }">
				<form:hidden path="id" />
			<div class="layui-form-item">
				<label class="layui-form-label">状态：</label>
				<div class="layui-input-block">
					
					<form:checkbox path="poll.status" value="true" lay-skin="primary" title="允许调查" />
					<form:checkbox path="cb.status" value="true" lay-skin="primary" title="允许评论" />
					
					
					
				</div>
			</div>
			</c:if>
						

	</div>
    <div class="layui-tab-item">
	<!-- 第4个标签 -->
		<div class="layui-form-item">
				<label class="layui-form-label">作者 ：</label>
				<div class="layui-input-block">
					<form:input path="author" 
						placeholder="请输入文章作者" autocomplete="off" class="layui-input" />
					<form:errors path="author"></form:errors>

				</div>
		</div>
		<div class="layui-form-item">
				<label class="layui-form-label">单位 ：</label>
				<div class="layui-input-block">
					<form:input path="authorDept" 
						placeholder="请输入文章作者单位" autocomplete="off" class="layui-input" />
					<form:errors path="authorDept"></form:errors>

				</div>
		</div>
		<div class="layui-form-item">
				<label class="layui-form-label">Email ：</label>
				<div class="layui-input-block">
					<form:input path="authorEmail" 
						placeholder="请输入文章作者的Email" autocomplete="off" class="layui-input" />
					<form:errors path="authorEmail"></form:errors>

				</div>
		</div>
		<div class="layui-form-item">
				<label class="layui-form-label">URL ：</label>
				<div class="layui-input-block">
					<form:input path="authorUrl" 
						placeholder="请输入文章作者的站点" autocomplete="off" class="layui-input" />
					<form:errors path="authorUrl"></form:errors>

				</div>
		</div>
	</div>
	<c:if test="${article.id >0 }">
    <div class="layui-tab-item">
    <!-- 第4个标签 -->
    <a id="test3" title="上传"  style="cursor:pointer;">　<i class="layui-icon">&#xe681;</i> 上传　</a>
    <div id="attas"></div>
    <div id="upbtn_lerx" class="layui-hide">隐藏的上传</div>
    <input id="aid" class="layui-hide" value="${article.id}" />
	</div>
    </c:if>
  </div>
</div> 

<div class="layui-form-item addbtn">
				<div class="layui-input-block">
					<button  class="layui-btn" lay-submit 
						lay-filter="add" >提交</button>
						
						
				</div>
</div>
			

		</form:form>
		
		
				
		<script type="text/javascript">
		
			layui.use(['form','element','upload'], function() {
				var form = layui.form,
				element = layui.element,
				upload = layui.upload;

				form.on('submit(add)', function(data){
					if ($("#agroup").val() == 0){
						parent.layer.alert("您必须选择文章所属的栏目后才能发布");
						return false;
					}
				});
				
				
				//执行实例
				  var uploadInst = upload.render({
				    elem: '#test1' //绑定元素
				    ,url: '${pageContext.request.contextPath}/action_file/upload?cw=0&ch=0' //上传接口
				    /* ,accept: 'file'
				    ,exts: 'jpg' */
				    ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
				    	//上传之前修改宽和高
				    	var w=0,h=0;
				    	var uri='${pageContext.request.contextPath}/action_agroup/smartWH/'+$('#agroup').val();
				         $.ajax({
								url: uri,
								type: 'post',
								async: false,
								success: function(data) {
									w=data.width;
									h=data.height;
								}
						});
				         
				    	this.url='${pageContext.request.contextPath}/action_file/upload?cw='+w+'&ch='+h;
				        
				      }
				    ,done: function(res){
				    	if (res.code==0){
				    		$("#thumbnail").val(res.data.src);
				    		//layer.msg("导入成功！");
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
				
				
				  var uploadInst2 = upload.render({
					    elem: '#test2' //绑定元素
					    ,url: '${pageContext.request.contextPath}/action_file/upload?intact=true' //上传接口
					    /* ,accept: 'file'
					    ,exts: 'jpg' */
					    ,done: function(res){
					    	if (res.code==0){
					    		$("#titleImg").val(res.data.src);
					    		//layer.msg("导入成功！");
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
				  
				  
				//……
				var uuri;
				var uploadInst3 = upload.render({
					    elem: '#test3' //绑定元素
					    ,url: '${pageContext.request.contextPath}/action_file/uploadAtta?aid='+$('#aid').val()+'&intact=true&cw=0&ch=0' //上传接口
					    ,accept: 'file'
					    ,bindAction: '#upbtn_lerx'
					    ,auto: false //选择文件后不自动上传layui-layer-btn0
					    /*,exts: 'jpg' */
					    ,choose: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
					    	var uri;
					    	var uploadRander=this;
					    	layer.prompt({
								  formType: 0,
								  value: '无说明',
								  title: '请输入附件说明',
								  async: false,
								  area: ['800px', '350px'] //自定义文本域宽高
								}, function(value, index, elem){
									//value=encodeURI($.trim(value));
									if (value=="无说明"){
										value="";
									}
									uri='${pageContext.request.contextPath}/action_file/uploadAtta?aid='+$('#aid').val()+'&intact=true&cw=0&ch=0&title='+value;
									uuri=uri;
									
									$("#upbtn_lerx").click();
									layer.close(index);
									
							});
					      }
				 		,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
				         
				    		this.url=uuri;
				 			//$("#urlshow").html(this.url);
				        
				      	}
					    ,done: function(res){
					    	if (res>0){
					    		artattasReload('${pageContext.request.contextPath}',$('#aid').val(),'attas');
					    		layer.msg("上传成功！");
					    		//location.reload();
					    	}else{
					    		layer.alert("上传失败！"+res);
					    	}
					      //上传完毕回调
					    }
					    ,error: function(){
					      //请求异常回调
					    }
					  });

				//但是，如果你的HTML是动态生成的，自动渲染就会失效
				//因此你需要在相应的地方，执行下述方法来手动渲染，跟这类似的还有 element.init();
				form.render();
			});
			
			
			layui.use('layedit', function(){
				  var layedit = layui.layedit;
				  
				  layedit.set({
					  uploadImage: {
					    url: '${pageContext.request.contextPath}/action_file/upload' //接口url
					    ,type: 'post' //默认post
					    					    
					  }
				  
					});
				  
				  
				  layedit.build('content'); //建立编辑器
				});
			
			
			
			artattasReload('${pageContext.request.contextPath}',$('#aid').val(),'attas');
			
		</script>
	</div>
</body>
</html>