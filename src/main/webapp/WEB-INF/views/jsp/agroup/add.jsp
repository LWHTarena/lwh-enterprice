<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="sutil" class="com.lwhtarena.company.sys.util.StringUtil"
	scope="page"></jsp:useBean>
<jsp:useBean id="hq" class="com.lwhtarena.company.sys.util.HttpUtil" scope="page"></jsp:useBean>
<jsp:useBean id="group" class="com.lwhtarena.company.web.entities.ArticleGroup"
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
<script
	src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js"></script>
<style type="text/css">
.layui-form-label {
	width: 160px;
}

.layui-input,.layui-textarea {
	width: 80%
}

.toppos {
	margin-top: 20px;
}

.addbtn {
	margin-top: 40px;
	margin-left: 300px;
}

.tips {
	margin-left: 20px;
}
.img-input{
	display:inline;
	width:15%;
}
</style>
</head>
<body>
	<div class="runtest">
		<form:form commandName="group" id="groupedit"
			action="${pageContext.request.contextPath}/action_agroup/add"
			class="layui-form" method="post">

			<input type="hidden" name="token" value="${hq.buildToken()}" />
			


			<div class="layui-form-item">
				<label class="layui-form-label">请选择父级分类 ：</label>
				<div class="layui-input-block">
					<form:select path="parent.id" items="${prelist }" itemLabel="title"
						itemValue="id">
						<option value="0">根</option>
					</form:select>

				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">栏目名称 ：</label>
				<div class="layui-input-block">
					<form:input path="name" required="true" lay-verify="required"
						placeholder="请输入栏目名称" autocomplete="off" class="layui-input" />
					<form:errors path="name"></form:errors>

				</div>
			</div>

			<c:if test="${group.id >0 }">
				<form:hidden path="id" />

			
			<div class="layui-form-item">
				<label class="layui-form-label">许可：</label>
				<div class="layui-input-block">
					<form:checkbox path="status" value="true" lay-skin="primary" title="可用" />
					<form:checkbox path="open" value="true" lay-skin="primary" title="公开" />
					<form:checkbox path="clogging" value="true" lay-skin="primary" title="发文" />
					<form:checkbox path="poll" value="true" lay-skin="primary" title="调查" />
					<form:checkbox path="comm" value="true" lay-skin="primary" title="评论" />	
					<form:checkbox path="staticPage" value="true" lay-skin="primary" title="静态" />
					<form:checkbox path="gather" value="true" lay-skin="primary" title="聚集" />
					
				</div>
			</div>
			</c:if>		
			<div class="layui-form-item">
				<label class="layui-form-label">跳转至URL ：</label>
				<div class="layui-input-block">
					<form:input path="jumpToUrl" 
						placeholder="请输入目的URL，如果有此项，本项下面可能无法加入文章" autocomplete="off" class="layui-input" />
					<form:errors path="jumpToUrl"></form:errors>

				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">缩略图智能宽高 ：</label>
				<div class="layui-input-block">
					宽：<form:input path="cw" 
						placeholder="请输入目的URL，如果有此项，本项下面可能无法加入文章" autocomplete="off" class="layui-input img-input" />　px
					　　高：<form:input path="ch" 
						placeholder="请输入目的URL，如果有此项，本项下面可能无法加入文章" autocomplete="off" class="layui-input img-input" />　px
					

				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">静态化使用文件夹：</label>
				<div class="layui-input-block">
					<form:input path="folder" 
						placeholder="请输入一个文件夹名称" autocomplete="off" class="layui-input" />
					<form:errors path="folder"></form:errors>

				</div>
			</div>
			
			<div class="layui-form-item">
				<label class="layui-form-label">可访问的IP或范围：</label>
				<div class="layui-input-block">
					<form:input path="ipVisitAllow" 
						placeholder="格式：192.168.6.1,192.168.1-192.168.6.254" autocomplete="off" class="layui-input" />
					<form:errors path="ipVisitAllow"></form:errors>

				</div>
			</div>
			
			
			<div class="layui-form-item">
				<label class="layui-form-label">特有HTML：</label>
				<div class="layui-input-block">
				<form:textarea path="htmlOwn" class="layui-textarea" htmlEscape="true" />
				</div>
			</div>

			<div class="layui-form-item">
				<div class="layui-input-block addbtn">
					<button class="layui-btn" onclick="commit();" lay-submit
						lay-filter="formDemo">提交</button>
				</div>
			</div>

		</form:form>
		<script type="text/javascript">
			layui.use('form', function() {
				var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功

				//……

				//但是，如果你的HTML是动态生成的，自动渲染就会失效
				//因此你需要在相应的地方，执行下述方法来手动渲染，跟这类似的还有 element.init();
				form.render();
			});

			function commit() {

				$("#groupedit").submit();

			}
		</script>
	</div>
</body>
</html>