<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="sutil" class="com.lwhtarena.company.sys.util.StringUtil" scope="page" ></jsp:useBean>
<jsp:useBean id="hq" class="com.lwhtarena.company.sys.util.HttpUtil" scope="page" ></jsp:useBean>
<jsp:useBean id="tu" class="com.lwhtarena.company.sys.util.TimeUtil" scope="page" ></jsp:useBean>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}" media="all">
  <script src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js"></script>
</head>
 <style>
  
    .demo-carousel{height: 200px; line-height: 200px; text-align: center;}
    .addarea{
    position: fixed; /*or前面的是absolute就可以用*/ 
    /* top: 550px;  */
    margin-top: 20px;
    }
    .nullSoftware{height: 100px;text-align: center;margin-top: 30px;}
    .pagestr{text-align: center;margin-top: 10px;}
    .addarea,.addbuttom{
    	a:link{text-decoration:none;color:#2c374b;} 
		a:visited{text-decoration:none;color:#2c374b;} 
		a:hover{text-decoration:underline;color:#c00;} 
		a:active{text-decoration:none;color:#2c374b;}
    }
    .layui-btn{font-style: italic;    }
    .logout{float: right;
    padding-right: 10px;
    position: fixed; /*or前面的是absolute就可以用*/ 
    bottom: 5px;
    left: 10px;
    }
    
    .ver{float: right;
    padding-right: 10px;
    position: fixed; /*or前面的是absolute就可以用*/  
       bottom: 5px;
       right: 10px;}
    .layui-icon {font-size: 20px;}
    .col_center{text-align: center;}
    .col_center_30px{text-align: center;width: 30px;}
    .col_center_80px{text-align: center;width: 80px;}
    .col_center_120px{text-align: center;width: 120px;}
    .col_center_160px{text-align: center;width: 160px;}
    .layui-table th{text-align: center;}
    /* table {  
		table-layout: fixed;  word-break:break-all;  
		word-wrap:break-word;  
	} */
    </style>
<body>
<div class="plant_mian">

    <!-- 内容主体区域 -->
    <div style="padding: 15px;" id="main">
    <blockquote class="layui-elem-quote layui-quote-nm"> >> <i class="layui-icon" style="font-size: 18px;">&#xe637;</i> 栏目列表 　　<a title="增加" href="javascript:add();"> <i class="layui-icon" style="font-size: 18px;">&#xe61f;</i> </a></blockquote>
 <c:if test="${empty requestScope.preListAll }">
 <div class="nullSoftware">没有任何栏目。</div>
		
	</c:if>
<table class="layui-table">
<colgroup>
    <col width="150">
    <col width="200">
    <col>
  </colgroup>
  <thead>
  
  <tr>
		<th class="col_center_80px" align="center" >序号</th>
		<th  align="center">名称</th>
		<th class="col_center_30px"  align="center">GID</th>
		<th  colspan="2" class="col_center_160px"  align="center">脚值参考(左、右脚) [前后ID]</th>
		<th class="col_center_120px"  align="center">静态文件夹</th>
		<th class="col_center"  align="center">跳转</th>
		<th class="col_center"  align="center">IP限制</th>
		<th class="col_center"  align="center">状态</th>
		<th class="col_center"  align="center">发文</th>
		
		<th class="col_center"  align="center">调查</th>
		<th class="col_center"  align="center">评论</th>
		<th class="col_center"  align="center">访问量</th>
		<th colspan="4" class="col_center"  align="center">操作</th>
    </tr>
    </thead>
    <tbody>
<c:forEach items="${requestScope.preListAll }" var="g" varStatus="st">

<tr>
<%-- <li>${sutil.countStr((g.orderStr.length()-1)/3,'　')  }├ ${g.name} (${g.id},${g.rup.LastID}) --<a href="${pageContext.request.contextPath}/action_article/del/${g.id }">删除</a>--　　<a href="${pageContext.request.contextPath}/action_article/edit/${g.id }">修改</a></li> --%>
<td class="col_center_80px"><c:if test="${g.id >0 }">${st.index}</c:if></td>
<c:if test="${g.id == 0 }">
<td colspan="16">${g.title} </td> 
</c:if>
<c:if test="${g.id > 0 }">
<td ><a href="${pageContext.request.contextPath}/action_show/nav/${g.id}" target="_blank">${g.title} </a></td>
<td class="col_center_30px"><c:if test="${g.id >0 }">${g.id}</c:if></td>
<td class="col_center_80px"><c:if test="${g.id >0 }">( ${g.footLeft} , ${g.footRight} )</c:if></td>
<td class="col_center_80px"><c:if test="${g.id >0 }">[${g.rup.lastID},${g.rup.nextID}]</c:if></td>
<td class="col_center_120px">${g.folder }</td>
<td class="col_center_30px"><a href="javascript:jumpurl(${g.id },'${g.name }','${g.jumpToUrl }');"><c:if test="${empty g.jumpToUrl }">未设置</c:if><c:if test="${not empty g.jumpToUrl }"><span style="color: red;">查　看</span></c:if></a></td>
<td class="col_center_80px"><a href="javascript:ipview(${g.id },'${g.name }','${g.ipVisitAllow }');"><c:if test="${empty g.ipVisitAllow }">未设置</c:if><c:if test="${not empty g.ipVisitAllow }"><span style="color: red;">查　看</span></c:if></a></td>
<td class="col_center_30px"><c:if test="${g.status }">正常</c:if><c:if test="${not g.status }"><span style="color: red;">禁用</span></c:if></td>
<td class="col_center_30px"><c:if test="${g.clogging }">正常</c:if><c:if test="${not g.clogging }"><span style="color: red;">禁止</span></c:if></td>
<td class="col_center_30px"><c:if test="${g.poll }">允许</c:if><c:if test="${not g.poll }"><span style="color: red;">禁止</span></c:if></td>
<td class="col_center_30px"><c:if test="${g.comm }">允许</c:if><c:if test="${not g.comm }"><span style="color: red;">禁止</span></c:if></td>
<td ><a href="javascript:vbook(${g.vbook.id },'${g.name }');"> IP： ${g.vbook.ipTotal } 阅读： ${g.vbook.viewsTotal }</a></td>

<td class="col_center_30px"><c:if test="${g.rup.lastID >0 }"><a title="上移" href="javascript:move(${g.id },-1);"> <i class="layui-icon">&#xe619;</i> </a></c:if></td>
<td class="col_center_30px"><c:if test="${g.rup.nextID >0 }"><a title="下移" href="javascript:move(${g.id },1);"> <i class="layui-icon">&#xe61a;</i> </a></c:if></td>
<td class="col_center_30px"><c:if test="${g.id >0 }"><a title="删除" href="javascript:del(${g.id},'${g.name}');"> <i class="layui-icon">&#xe640;</i> </a></c:if></td>　
<td class="col_center_30px"><c:if test="${g.id >0 }"><a title="修改" href="javascript:details(${g.id},'${g.name}');"> <i class="layui-icon">&#xe642;</i> </a></c:if></td>
 
</c:if>

</tr>
</c:forEach>
</tbody>
</table>
</div>
</div>
<!--  
<form action="${pageContext.request.contextPath}/action_article/add" method="post">
<input type="hidden" name="token" value="${hq.buildToken()}" />
请选择父级分类：
<select name="parent.id">

<c:forEach items="${requestScope.preListAll }" var="g" varStatus="st">
<%-- <option value="${g.id }">${sutil.countStr((g.orderStr.length()-1)/3,'　')  }├ ${g.name}</option> --%>
<option value="${g.id }">${g.title}</option>
</c:forEach>
</select>
<br />
<input type="text" name="name"><br />
<input type="submit" title="提交">
</form>
-->
<script type="text/javascript">
function move(id,offset) {
	var uri="${pageContext.request.contextPath}/action_agroup/move/"+id+"/"+offset;
	//alert(uri);
	$.ajax({
		url: uri,
		type: 'post',
		success: function(data) {
			location.reload(); 
		}
	});
	//var timeNow = new Date().getTime();
	//$("#validCodeImg").attr("src","/action_captcha/randomNum?key=admin&time=" + timeNow);
	//$("#vcode").focus();
}

function ipview(id,name,val){
	//alert("aaaa");
	 parent.layer.prompt({
		  formType: 0,
		  value: val,
		  btn: ['提交','关闭'], //按钮
		  closeBtn: 0, //不显示关闭按钮
		  title: '栏目 '+name+' 允许访问的IP地址及IP范围'
		}, function(value, index, elem){
			var uri="${pageContext.request.contextPath}/action_agroup/ipscope?id="+id+"&val="+value;
			//alert("uri:"+uri);
			$.ajax({
				url: uri,
				type: 'post',
				success: function(data) {
					//alert("data:"+data");
					if (data==0){
						parent.layer.msg('保存成功！');
						parent.layer.close(index);
					}else{
						parent.layer.msg('保存失败！错误号：'+data);
					}
					location.reload(); 
				}
			});
			//parent.layer.close(index);				  
		}); 
}


function jumpurl(id,name,val){
	//alert("aaaa");
	 parent.layer.prompt({
		  formType: 0,
		  value: val,
		  btn: ['提交','关闭'], //按钮
		  closeBtn: 0, //不显示关闭按钮
		  title: '设定栏目 '+name+' 的跳转目标'
		}, function(value, index, elem){
			var uri="${pageContext.request.contextPath}/action_agroup/jumpurl?id="+id+"&val="+value;
			//alert("uri:"+uri);
			$.ajax({
				url: uri,
				type: 'post',
				success: function(data) {
					//alert("data:"+data");
					if (data==0){
						parent.layer.msg('保存成功！');
						parent.layer.close(index);
					}else{
						parent.layer.msg('保存失败！错误号：'+data);
					}
					location.reload(); 
				}
			});
			//parent.layer.close(index);				  
		}); 
}

function add() {
	parent.layer.open({
	    type: 2,
	    skin: 'layui-layer-rim', //加上边框
	title: '新增栏目',
	    area : ['760px' , '560px'],
	    content: '${pageContext.request.contextPath}/action_agroup/beforeAdd',
	    success: function(layero, index){
	    },
	    end: function(){
	    	location.reload();
	        
	      }

	});
	
}

function vbook(vid,name) {
	
	parent.layer.open({
	    type: 2,
	    skin: 'layui-layer-rim', //加上边框
	title: '栏目　 “'+name+'”　的访问记录',
	    area : ['1200px' , '650px'],
	    content: '${pageContext.request.contextPath}/action_visitor/list?id='+vid,
	    success: function(layero, index){
	    }

	});
	
}

function details(id,name) {
	
	parent.layer.open({
	    type: 2,
	    skin: 'layui-layer-rim', //加上边框
	title: '修改栏目： “'+name+'” 的信息',
	    area : ['760px' , '620px'],
	    content: '${pageContext.request.contextPath}/action_agroup/edit/'+id,
	    success: function(layero, index){
	    },
	    end: function(){
	    	location.reload();
	        
	      }

	});
	
}

function del(id,name){
	
	parent.layer.confirm('您确定删除栏目  “'+name+'” 吗？ 删除后将不可恢复！',{title:'警告'}, function(index){
		  
		  var uri="${pageContext.request.contextPath}/action_agroup/del/"+id;
		  
		  //alert("url:"+uri);
		  
			$.ajax({
				url: uri,
				type: 'post',
				success: function(data) {
					//alert("data:"+data");
					if (data==0){
						parent.layer.msg('删除成功！');
					}else{
						parent.layer.msg('删除失败！错误号：'+data);
					}
					location.reload(); 
				}
			});
		
		  layer.close(index);
		});
}

</script>

</body>
</html>