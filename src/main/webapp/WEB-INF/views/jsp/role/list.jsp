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

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>角色(用户组)列表</title>
  <script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}" media="all">
  <script src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js"></script>

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
    .layui-input{width: 80%;}
    /* table {  
		table-layout: fixed;  word-break:break-all;  
		word-wrap:break-word;  
	} */
    </style>   
</head>
<body>
<div class="layui-layout layui-layout-admin">

  
  
  

</div>

  <div class="plant_mian">
    <!-- 内容主体区域 -->
    <div style="padding: 15px;" id="main">

<!-- 开始 -->

<blockquote class="layui-elem-quote layui-quote-nm"> >> <i class="layui-icon">&#xe61b;</i> 角色（用户组）列表　　<a title="增加" href="javascript:add();"> <i class="layui-icon" style="font-size: 18px;">&#xe61f;</i> </a></blockquote>
 <c:if test="${empty requestScope.roles }">
 <div class="nullSoftware">没有任何角色。</div>
		
	</c:if>
<c:if test="${!empty requestScope.roles }">	
<table class="layui-table" lay-filter="demo">
  <thead>
    <tr>
		<th>序号</th>
		<th>ID</th>
		<th>组名</th>
		<th>权限码</th>
		<th>用户数量</th>
		<th>默认</th>
		<th>状态</th>
		<th>操作</th>
    </tr>
    </thead>
    <tbody>
    
    <c:forEach items="${requestScope.roles }" var="role" varStatus="st">
					<tr>
						<td>${st.index+1}</td>
						<td>${role.id }</td>
						<td>${role.name }</td>
						<td><span><input class="layui-input layui-input-inline" disabled="disabled" value="${role.mask }" /></span><span> <a title="设置" href="javascript:mask(${role.id },'${role.name }');"> <i class="layui-icon layui-icon-set"></i> </a></span></td>
						<td>${role.counts }</td>
						<td><c:if test="${role.def }"><strong>√</strong></c:if></td>
						<td><c:if test="${role.status }">正常</c:if><c:if test="${role.status==false }"><font color="red">禁用</font></c:if></td>
						<td><a title="编辑" href="javascript:details(${role.id },'${role.name }')"> <i class="layui-icon">&#xe642;</i> </a>　<a title="删除" class="delete" href="javascript:del(${role.id },'${role.name }')"> <i class="layui-icon">&#xe640;</i> </a></td>
					</tr>
	</c:forEach>
  </tbody>
</table>
 </c:if>
 
 <div class="pagestr" id="pagestr"></div>
 <script lang='javascript' type='text/javascript'> 
	
	/*
	
	■★▲◆  url 中的person url: '${pageContext.request.contextPath}/action_person/list'
	
	
	
			$(function() {
				
				$(".pagestr").pagefmt( {
					fmt:0,
					pageList:true,
					showall:true,
					showEnds:true,
					cur: ${requestScope.rs.page},
					pagesize: ${requestScope.rs.pageSize},
					pagecount: ${requestScope.rs.pageCount},
					url: '${pageContext.request.contextPath}${pageUrl}'
				} );
			});
	*/		
			function getTime(/** timestamp=0 **/) {  
			    var ts = arguments[0] || 0;  
			    var t,y,m,d,h,i,s;  
			    t = ts ? new Date(ts*1000) : new Date();  
			    y = t.getFullYear();  
			    m = t.getMonth()+1;  
			    d = t.getDate();  
			    h = t.getHours();  
			    i = t.getMinutes();  
			    s = t.getSeconds();  
			    // 可根据需要在这里定义时间格式  
			    return y+'-'+(m<10?'0'+m:m)+'-'+(d<10?'0'+d:d)+' '+(h<10?'0'+h:h)+':'+(i<10?'0'+i:i)+':'+(s<10?'0'+s:s);  
			}

			
	function add() {
		
		parent.layer.prompt({
			  formType: 0,
			  title: '请输入一个前台角色（用户组）名称',
			  area: ['800px', '350px'] //自定义文本域宽高
			}, function(value, index, elem){
				uri ='${pageContext.request.contextPath}/action_role/add?name='+ value;
					
				  $.ajax({
						url : uri,
						type : 'post',
						success : function(data) {
							parent.layer.close(index);
							switch (data){
							case 0:
								parent.layer.msg('增加成功！');
								location.reload();
								break;
							case -10:
								parent.layer.alert('该名称已存在！'); 
								break;
							case -12:
								parent.layer.alert('名称不能为空！'); 
								break;
							default:
								parent.layer.alert('增加失败，请联系技术人员解决！');
							}
						}
					}); 
			  
			});
		
		/* parent.layer.open({
		    type: 2,
		    skin: 'layui-layer-rim', //加上边框
		title: '新增：请输入角色信息',
		    area : ['700px' , '500px'],
		    content: '${pageContext.request.contextPath}/jsp/role/add',
		    success: function(layero, index){
		    },
		    end: function(){
		    	location.reload();
		        
		      }

		}); */
		
	}

	function details(id,title) {
		parent.layer.open({
		    type: 2,
		    skin: 'layui-layer-rim', //加上边框
		title: '修改角色 '+title+' 的信息',
		    area : ['700px' , '500px'],
		    content: '${pageContext.request.contextPath}/action_role/edit?id='+id,
		    success: function(layero, index){
		    },
		    end: function(){
		    	location.reload();
		        
		      }

		});
		
	}
	
	function mask(id,title) {
		parent.layer.open({
		    type: 2,
		    skin: 'layui-layer-rim', //加上边框
		title: '修改角色 “'+title+'” 的权限码',
		    area : ['700px' , '500px'],
		    content: '${pageContext.request.contextPath}/action_role/mask?id='+id,
		    success: function(layero, index){
		    },
		    end: function(){
		    	location.reload();
		        
		      }

		});
		
	}

	function del(id,title){
		parent.layer.confirm('您确定删除角色组 “ ' + title + ' ” 吗？ 删除后将不可恢复！',{title:'警告'}, function(index){
			  
			  var uri="${pageContext.request.contextPath}/action_role/del?id="+id;
			
				$.ajax({
					url: uri,
					type: 'post',
					success: function(data) {
						location.reload();
					}
				});
			
				parent.layer.close(index);
			});
	} 
</script>

<script type="text/javascript">

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

<!-- 结束 -->

</div>
  </div>
  
  
</body>
</html>