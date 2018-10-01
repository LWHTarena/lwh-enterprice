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

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}" media="all">
<script src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js"></script>
<title>${username}的文集</title>
</head>
<body>

<c:if test="${empty requestScope.rs.list }">
 <div class="nullSoftware">没有文章。</div>
		
</c:if>
	
<c:if test="${!empty requestScope.rs.list }">	
<table class="layui-table" lay-filter="demo">
  <thead>
    <tr>
		<th>序号</th>
		<th>ID</th>
		<th>标题</th>
		<th>所属栏目</th>
		<th>作者</th>
		<th>发布时间</th>
		<th>上次修改时间</th>
		<th>阅读量 / IP量</th>
		<th>状态</th>
		<th>审核人</th>
		<th>操作</th>
    </tr>
   </thead> 
   <tbody>
    <c:forEach items="${requestScope.rs.list }" var="art" varStatus="st">
    

					<tr>
						<td>${(rs.page-1)*rs.pageSize+st.index+1}</td>
						<td>${art.id }</td>
						<td>${art.subject }</td>
						<td>${art.agroup.name }</td>
						<td>${art.author }</td>
						<td><date:date value ='${art.creationTime }' fmtstr='yyyy-MM-dd HH:mm:ss' /> </td>
						<td><date:date value ='${art.lastModifyTime }' fmtstr='yyyy-MM-dd HH:mm:ss' /> </td>
						<td>${art.vbook.viewsTotal } / ${art.vbook.ipTotal }</td>
						<td><c:if test="${art.status }">通过</c:if><c:if test="${user.status==false }"><font color="red">禁用</font></c:if></td>
						<td><a title="编辑" href="javascript:edit(${art.id },'${art.subject }')"> <i class="layui-icon">&#xe642;</i> </a>　 <a title="删除" class="delete" href="javascript:del(${art.id },'${art.subject }')"> <i class="layui-icon">&#xe640;</i> </a></td>
					</tr>
	</c:forEach>
  </tbody>
</table>
 </c:if>
 <div class="pagestr" id="pagestr"></div>
 <script lang='javascript' type='text/javascript'> 
 
 function del(id,name){
		parent.layer.confirm('您确定删除用户  “'+name+'” 吗？ 删除后将不可恢复！',{title:'警告'}, function(index){
			  
			  var uri="${pageContext.request.contextPath}/action_user/del?id="+id;
			
				$.ajax({
					url: uri,
					type: 'post',
					success: function(data) {
						if (data==0){
							parent.layer.msg('删除成功！');
						}else{
							parent.layer.msg('删除失败！错误号：'+data);
						}
						location.reload(); 
					}
				});
			
				parent.layer.close(index);
			});
	}
 
 function edit(id,name) {
		parent.layer.open({
		    type: 2,
		    skin: 'layui-layer-rim', //加上边框
		title: '修改：请输入用户 “'+name+'” 的信息',
		    area : ['700px' , '600px'],
		    content: '${pageContext.request.contextPath}/action_user/edit?id='+id,
		    success: function(layero, index){
		    },
		    end: function(){
		    	location.reload();
		        
		      }

		});
		
	}
 
 layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element'], function(){
	  var laydate = layui.laydate //日期
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
	    elem: 'pagestr' //分页容器的id
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
	  
	});

 </script>
</body>
</html>