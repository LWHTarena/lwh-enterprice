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
    .col_title_left{width: 250px;}
    .col_center_30px{text-align: center;width: 30px;}
    .col_center_50px{text-align: center;width: 50px;}
    .col_center_60px{text-align: center;width: 60px;}
    .col_center_80px{text-align: center;width: 80px;}
    .col_center_120px{text-align: center;width: 120px;}
    .col_center_160px{text-align: center;width: 160px;}
    .layui-table th{text-align: center;}
    /* table {  
		table-layout: fixed;  word-break:break-all;  
		word-wrap:break-word;  
	} */
    </style>
<body  class="layui-layout-body">
<div class="plant_mian">

    <!-- 内容主体区域 -->
    <div style="padding: 15px;" id="main">
    <blockquote class="layui-elem-quote layui-quote-nm"> >> <i class="layui-icon" style="font-size: 20px;">&#xe60a;</i> 文章列表 　　<a title="增加" href="javascript:add();"> <i class="layui-icon" style="font-size: 18px;">&#xe61f;</i> </a></blockquote>
 <c:if test="${empty requestScope.rs.list }">
 <div class="nullSoftware">没有任何文章。</div>
		
	</c:if>
<c:if test="${not empty requestScope.rs.list }">
<table class="layui-table">
<colgroup>
    <col width="150">
    <col width="200">
    <col>
  </colgroup>
  <thead>
  
  <tr>
		<th class="col_center_30px" align="center" >序号</th>
		<th class="col_title_left" align="center">标题</th>
		<th class="col_center_30px"  align="center">ID</th>
		<th  align="center">栏目 (栏目ID)</th>
		<th class="col_center_60px"  align="center">作者/会员</th>
		<th class="col_center"  align="center">发布时间</th>
		<th class="col_center"  align="center">访问量</th>
		<th class="col_center"  align="center">会员</th>
		<th class="col_center"  align="center">状态</th>
		<th class="col_center"  align="center">调查</th>
		<th class="col_center"  align="center">评论</th>
		<th colspan="2" class="col_center"  align="center">操作</th>
    </tr>
    </thead>
    <tbody>
<c:forEach items="${requestScope.rs.list }" var="art" varStatus="st">
<c:set var="subject" value="${su.escape(art.subject)}"></c:set>
<tr>
<c:if test="${art.hfs.status }">
<c:set var="href" value="${art.hfs.url }"></c:set>
</c:if>
<c:if test="${art.hfs.status == false }">
<c:set var="href" value="${pageContext.request.contextPath}/action_show/art/${art.id}"></c:set>
</c:if>
<td class="col_center_30px">${(rs.page-1)*rs.pageSize+st.index+1}</td>
<td class="col_title_left" ><a href="${href}" target="_blank">${art.subject} </a> </td> 
<td >${art.id} </td> 
<td >${art.agroup.name} (${art.agroup.id}) </td>
<td >${art.author} / ${art.user.username }</td>
<td ><date:date value ='${art.creationTime }' fmtstr='yyyy-MM-dd HH:mm:ss' /></td>
<td ><a href="javascript:vbook(${art.vbook.id },'${subject }');"> IP： ${art.vbook.ipTotal } 阅读： ${art.vbook.viewsTotal }</a></td>
<td > </td>
<td class="col_center_30px"><c:if test="${art.status }"><a href="javascript:pass(${art.id },'${subject }',false);">正常</a></c:if><c:if test="${not art.status }"><a href="javascript:pass(${art.id },'${subject }',true);"><span style="color: red;">禁用</span></a></c:if></td>
<td ><c:if test="${art.poll.status }">允许 <a href="javascript:pollview(${art.poll.id },'${subject }');">(赞成：${art.poll.agrees} | 酱油：${art.poll.passbys} | 反对：${art.poll.antis})</a></c:if><c:if test="${not art.poll.status }"><span style="color: red;">禁止</span></c:if></td>
<td class="col_center_30px"></td>

<td class="col_center_30px"><a title="删除" href="javascript:del(${art.id},'${subject}');"> <i class="layui-icon">&#xe640;</i> </a></td>　
<td class="col_center_30px"><a title="修改" href="javascript:details(${art.id},'${subject}');"> <i class="layui-icon">&#xe642;</i> </a></td>
 

</tr>
</c:forEach>
</tbody>
</table>

 <div class="pagestr" id="pagestr"></div>
 
</c:if>
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




function add() {
	parent.layer.open({
	    type: 2,
	    skin: 'layui-layer-rim', //加上边框
	title: '请输入文章信息',
	    area : ['800px' , '600px'],
	    content: '${pageContext.request.contextPath}/action_article/beforeAdd',
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
	title: '修改文章： “'+name+'” 的内容',
	    area : ['800px' , '600px'],
	    content: '${pageContext.request.contextPath}/action_article/edit/'+id,
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
	title: '文章　 “'+name+'”　的访问记录',
	    area : ['1200px' , '650px'],
	    content: '${pageContext.request.contextPath}/action_visitor/list?id='+vid,
	    success: function(layero, index){
	    }

	});
	
}

function pollview(pid,name) {
	
	parent.layer.open({
	    type: 2,
	    skin: 'layui-layer-rim', //加上边框
	title: '文章　 “'+name+'”　的投票记录',
	    area : ['1200px' , '650px'],
	    content: '${pageContext.request.contextPath}/action_poll/list/'+pid,
	    success: function(layero, index){
	    }

	});
	
}


function pass(id,name,status){
	var msgcmd;
	var staval;
	if (status){
		msgcmd=" <span style=\"color: green;\">审核通过</span> ";
		staval=1;
	}else{
		msgcmd=" <span style=\"color: red;\">禁止</span> ";
		staval=0;
	}
	parent.layer.confirm('您确定'+msgcmd+'文章  “'+name+'” 吗？',{title:'提醒'}, function(index){
		  
		  var uri="${pageContext.request.contextPath}/action_article/pass/"+id+"/"+staval;
		  
		  //alert("url:"+uri);
		  
			$.ajax({
				url: uri,
				type: 'post',
				success: function(data) {
					switch (data){
						case 0:
							parent.layer.msg('操作成功！');
							location.reload(); 
							break;
						case -2:
							parent.layer.msg('对不起，您没有权限！');
						break;
						default:
							parent.layer.msg('操作失败！错误号：'+data);
					}
					
					
				}
			});
		
		  layer.close(index);
		});
}

function del(id,name){
	
	parent.layer.confirm('您确定删除文章  “'+name+'” 吗？ 删除后将不可恢复！',{title:'警告'}, function(index){
		  
		  var uri="${pageContext.request.contextPath}/action_article/del/"+id;
		  
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

<script type="text/javascript">

layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element'], function(){
  var laydate = layui.laydate //日期
  ,laypage = layui.laypage //分页
  ,layer = layui.layer //弹层
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
</body>
</html>