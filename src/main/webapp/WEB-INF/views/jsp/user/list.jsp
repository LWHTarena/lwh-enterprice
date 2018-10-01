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
  <title>前台用户列表</title>
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
    /* table {  
		table-layout: fixed;  word-break:break-all;  
		word-wrap:break-word;  
	} */
    </style>   
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">

  
  
  

</div>

  <div class="plant_mian">
    <!-- 内容主体区域 -->
    <div style="padding: 15px;" id="main">

<!-- 开始 -->

<blockquote class="layui-elem-quote layui-quote-nm"> >> <i class="layui-icon">&#xe612;</i> 前台用户列表　　<a title="增加" href="javascript:add();"> <i class="layui-icon" style="font-size: 18px;">&#xe61f;</i> </a></blockquote>
 <c:if test="${empty requestScope.rs.list }">
 <div class="nullSoftware">没有任用户。</div>
		
	</c:if>
<c:if test="${!empty requestScope.rs.list }">	
<table class="layui-table" lay-filter="demo">
  <thead>
    <tr>
		<th>序号</th>
		<th>ID</th>
		<th>用户名</th>
		<th>姓名</th>
		<th>用户组</th>
		<th>手机号</th>
		<th>邮箱</th>
		<th>创建时间 / IP</th>
		<th>上次登录时间 / IP</th>
		<th>注册时验证地址</th>
		<th>状态</th>
		<th>操作</th>
    </tr>
   </thead> 
   <tbody>
    <c:forEach items="${requestScope.rs.list }" var="user" varStatus="st">
    

					<tr>
						<td>${(rs.page-1)*rs.pageSize+st.index+1}</td>
						<td>${user.id }</td>
						<td><c:if test="${user.pwdAtCreate.equals(user.password) }"><span style="color: red;font-weight: bolder;"> ! </span></c:if>${user.username }</td>
						<td>${user.truename }</td>
						<td><c:if test="${user.role!=null }">${user.role.name }</c:if></td>
						<td>${user.mobile }</td>
						<td>${user.email }</td>
						<td><date:date value ='${user.createTime }' fmtstr='yyyy-MM-dd HH:mm:ss' /> / ${user.createIP } </td>
						<td><date:date value ='${user.lastLoginTime }' fmtstr='yyyy-MM-dd HH:mm:ss' /> / ${user.lastLoginIP } </td>
						<td>${user.regCodeSendTarget }</td>
						<td><c:if test="${user.state }">正常</c:if><c:if test="${user.state==false }"><font color="red">禁用</font></c:if></td>
						<td><a title="编辑" href="javascript:details(${user.id },'${user.username }')"> <i class="layui-icon">&#xe642;</i> </a>　<a title="修改密码" href="javascript:pws(${user.id },'${user.username }')"> <i class="layui-icon">&#xe620;</i> </a>　<a title="删除" class="delete" href="javascript:del(${user.id },'${user.username }')"> <i class="layui-icon">&#xe640;</i> </a></td>
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
			  title: '请输入一个用户名',
			  area: ['800px', '350px'] //自定义文本域宽高
			}, function(value, index, elem){
				uri ='${pageContext.request.contextPath}/action_user/addByName?name='+ value;
					
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

			
			function add2() {
				parent.layer.open({
				    type: 2,
				    skin: 'layui-layer-rim', //加上边框
				title: '新增：请输入用户信息',
				    area : ['700px' , '500px'],
				    content: '${pageContext.request.contextPath}/jsp/user/add',
				    success: function(layero, index){
				    },
				    end: function(){
				    	location.reload();
				        
				      }

				});
				
			}

			function details(id,name) {
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
			
			
			function pws(id,username){
				parent.layer.prompt({
					  formType: 0,
					  value: '',
					  title: '修改 '+username+' 的登录密码',
					  area: ['800px', '350px'] //自定义文本域宽高
					}, function(value, index, elem){
						uri ='${pageContext.request.contextPath}/action_user/pws?uid='+id+"&password=" + value;
							
						  $.ajax({
								url : uri,
								type : 'post',
								success : function(data) {
									if (data== 0){
										
										parent.layer.msg('<i class="layui-icon">&#xe618;</i> 修改成功！');
										parent.layer.close(index);
										location.reload();
										//
										//alert("修改成功！");
									}else{
										parent.layer.msg('<i class="layui-icon">&#x1007;</i> 操作失败，请检查您是否有权限或重新登录后操作！');
										parent.layer.close(index);
										//alert("修改时发生错误！");
									}
								}
							}); 
					  
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