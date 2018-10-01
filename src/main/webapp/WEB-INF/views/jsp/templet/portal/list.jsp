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
<jsp:useBean id="su" class="com.lwhtarena.company.sys.util.StringUtil" scope="page" ></jsp:useBean>

<jsp:setProperty name="dateValue" property="time" value="${timestampValue}"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/js/jquery/jquery.session.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}" media="all">
  <script src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js"></script>
 <style>
  
  	.clouds{display:none; }
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
    .layui-btn{font-style: italic; }
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
<script lang='javascript' type='text/javascript'>
 
 function descr(id) {
	 var str = $('#descr'+id).html();
	 var re=/\r\n/;
	 str=str.replace(/[\r\n]/g,"<br>");
	 $('#descr'+id).html(str);
 }
 </script>
<body class="layui-layout-body">

 <script lang='javascript' type='text/javascript'> 
 var ids = new Array();
 </script>
  <div class="plant_mian">
    <!-- 内容主体区域 -->
    <div style="padding: 15px;" id="main">

<!-- 开始 -->

<blockquote class="layui-elem-quote layui-quote-nm"> >> <i class="layui-icon layui-icon-template-1"></i> 门户模板列表　　<a title="增加" href="javascript:add();">　<i class="layui-icon layui-icon-add-circle" style="font-size: 18px;"></i>　</a>  <a id="test1" title="导入"  style="cursor:pointer;">　<img src="${pageContext.request.contextPath}/images/import.png" width="18">　</a>  <a title="模板市场" href="javascript:market();">　<i class="layui-icon layui-icon-app" style="font-size: 18px;"></i></a></blockquote>
 <c:if test="${empty requestScope.rs.list }">
 <div class="nullSoftware">没有任何门户模板。</div>
		
	</c:if>
<c:if test="${!empty requestScope.rs.list }">
<table class="layui-table" lay-filter="demo">
				<thead>
					<tr>
						<th>序号</th>
						<th>ID</th>
						<th>模板名称</th>
						<th>作者</th>
						<th>说明</th>
						<th>状态</th>
						<th>默认</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${requestScope.rs.list }" var="templet" varStatus="st">
				<script lang='javascript' type='text/javascript'> 
					ids[${st.index}] = "${templet.uuid }";
				</script>
				
					<tr>
						<td>${(rs.page-1)*rs.pageSize+st.index+1}</td>
						<td>${templet.id }</td>
						<td><a title="编辑" href="javascript:details('${templet.name }',${templet.id })">${templet.name }</a></td>
						<td>${templet.author }</td>
						<td width="400" id="descr${st.index }"  onload="descr(${st.index });">${su.strReplace(templet.description,"\\n","<br>") }</td>
						<td><a href="javascript:chgstatus('${templet.name }',${templet.id });"><c:if test="${templet.state }">正常</c:if><c:if test="${templet.state==false }"><span style="color: red;">禁用</span></c:if></a></td>
						<td><c:if test="${templet.def }"><i class="layui-icon">&#xe605;</i></c:if><c:if test="${templet.def==false }"><a class="delete" href="javascript:def('${templet.name }',${templet.id })">- 否 -</a></c:if></td>
						<td><date:date value ='${templet.designTime }' fmtstr='yyyy-MM-dd' /></td>
						<td><a title="编辑" href="javascript:details('${templet.name }',${templet.id })"> <i class="layui-icon">&#xe642;</i> </a>　<a title="删除" href="javascript:del('${templet.name }',${templet.id })"> <i class="layui-icon">&#xe640;</i> </a>　<a title="复制" href="javascript:copy(${templet.id })"> <i class="layui-icon">&#xe630;</i> </a>　<a  title="下载"  href="${pageContext.request.contextPath}/action_portal_templet/download/${templet.id }"> <i class="layui-icon">&#xe601;</i> </a>　<a id="${templet.uuid }_report" title="发布更新到模板市场" href="javascript:report('${templet.name }',${templet.id })"> <i class="layui-icon layui-icon-upload-drag"></i> </a> 　<span class="clouds" id="${templet.uuid }_clouds">|</span> 　<a class="clouds" id="${templet.uuid }_freechg" href="javascript:freechg('${templet.name }','${templet.uuid }')"> <i class="layui-icon"></i> </a>　<a class="clouds" title="分享" id="${templet.uuid }_share" href="javascript:share('${templet.name }','${templet.uuid }')"> <i class="layui-icon layui-icon-share"></i> </a></td>
					</tr>
					<script lang='javascript' type='text/javascript'>
					descr(${st.index });
					</script >
				</c:forEach>
				</tbody>
</table>
</c:if>
		
 <div class="pagestr" id="pagestr"></div>
 <script lang='javascript' type='text/javascript'>
 
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
			  title: '请输入一个模板名称',
			  area: ['800px', '350px'] //自定义文本域宽高
			}, function(value, index, elem){
				uri ='${pageContext.request.contextPath}/action_portal_templet/add?name='+ value;
					
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
		
		
	}
	
	function report(title,id) {
		var uri="${pageContext.request.contextPath}/action_portal_templet/integrityCheck/"+id;
		var msg;
		$.ajax({
			url: uri,
			type: 'post',
			async: false,
			success: function(data) {
				msg=data;
				
			}
		});
		
		if (!msg==""){
			msg="经检测，您的模板存在以下问题，但不影响您上传提交！<br />"+msg+"<br />";
		}  
		parent.layer.confirm(msg+'您确定将“ ' + title + ' ”的模板上传吗？',{title:'提醒'}, function(index){
		  
		  uri="${pageContext.request.contextPath}/action_portal_templet/report/"+id;
		
			$.ajax({
				url: uri,
				type: 'post',
				success: function(data) {
					if (data == -2){
						parent.layer.alert("对不起，经云端身份校验，您没有修改云端模板的权限！");
					}else{
						parent.layer.alert("发布成功!请刷新页面！");
						parent.layer.close(index);
					}
					
					
				}
			});
		
		});
}
	
	function def(title,id) {
		  
		parent.layer.confirm('您将“ ' + title + ' ”的模板设为默认吗？',{title:'提醒'}, function(index){
		  
		  var uri="${pageContext.request.contextPath}/action_portal_templet/def/"+id;
		
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

function del(title,id) {
		
	  parent.layer.confirm('您确定删除名称为“ ' + title + ' ”的模板吗？<br />删除后将不可恢复！',{title:'提醒'}, function(index){
		  
		  var uri="${pageContext.request.contextPath}/action_portal_templet/del/"+id;
		
			$.ajax({
				url: uri,
				type: 'post',
				success: function(data) {
					if (data==0){
						parent.layer.msg('删除成功！');
						location.reload();
					}else{
						parent.layer.msg('删除失败！可能该模板正在被使用！！错误号：'+data);
					}
					
				}
			});
		
			parent.layer.close(index);
		});
	  
	}
	
function chgstatus(title,id) {
	
	  parent.layer.confirm('您确定修改名称为“ ' + title + ' ”的可用状态吗？',{title:'提醒'}, function(index){
		  
		  var uri="${pageContext.request.contextPath}/action_portal_templet/chgstatus/"+id;
		
			$.ajax({
				url: uri,
				type: 'post',
				success: function(data) {
					if (data==0){
						parent.layer.msg('修改成功！');
						location.reload();
					}else{
						parent.layer.msg('修改失败！可能该模板正在被使用！！错误号：'+data);
					}
					
				}
			});
		
			parent.layer.close(index);
		});
	  
	}
	
function freechg(title,uuid) {
	var msg=$("#"+uuid+"_freechg").attr("title");
	if (msg=="收费模板"){
		msg="您确定将模板：“"+title+"” 更改为免费模板吗？";
	}else{
		msg="您确定取消模板：“"+title+"” 的免费性质吗？模板收费后将只能通过分享密钥获得！";
	}
	parent.layer.confirm(msg,{title:'提醒'}, function(index){
		  
		  var uri="${requestScope.marketUrl}/action_templet_market/freeChange/"+uuid+"/${requestScope.security}";
		
			$.ajax({
				url: uri,
				type: 'post',
				success: function(data) {
					switch (data){
					case 0:
						parent.layer.msg('修改成功！');
						location.reload();
						break;
					case -2:
						parent.layer.msg('对不起，您没有权限！');
						break;
					default:
						parent.layer.msg('修改失败！错误号：'+data);
					}
					
				}
			});
		
			parent.layer.close(index);
		});
	}
	
function upchk(uuid) {
	var uri="${requestScope.marketUrl}/action_templet_market/find?security=${requestScope.security}&uuid="+uuid;
		$.ajax({
			url: uri,
			type: 'post',
			success: function(data) {
				//alert(data);
				
				if (data>=0){
					
					$("#"+uuid+"_share").show();
					$("#"+uuid+"_freechg").show();
					$("#"+uuid+"_clouds").show();
					
					if (data>0){
						
						$("#"+uuid+"_freechg i").addClass("layui-icon-rmb");
						$("#"+uuid+"_freechg").attr("title","收费模板"); 
					}else{
						$("#"+uuid+"_freechg i").addClass("layui-icon-face-smile-b");
						$("#"+uuid+"_freechg").attr("title","免费模板");
					}
				}else if (data < -1){
					
					$("#"+uuid+"_report").hide();
				}
				
			}
		});
	}


function market() {
	
	parent.layer.open({
	    type: 2,
	    skin: 'layui-layer-rim', //加上边框
	    title: '云端模板市场-门户区',
	    area : ['1000px' , '700px'],
	    content: '${pageContext.request.contextPath}/action_portal_templet/marketlist/',
	    success: function(layero, index){
	    }

	});
	  
	}
	
	

function details(title,id) {
	
	
	parent.layer.open({
	    type: 2,
	    skin: 'layui-layer-rim', //加上边框
	    title: '模板 '+title+' 内容',
	    area : ['700px' , '500px'],
	    content: '${pageContext.request.contextPath}/action_portal_templet/fields/'+id+"/0",
	    success: function(layero, index){
	    },
	    end: function(){
	    	location.reload();
	        
	      }

	});
	  
	}
	
function copy(id) {
	parent.layer.prompt({
	    title: '模板复制：请输入新的标题（注意，不能与原标题相同）：',
	    formType: 0 //prompt风格，支持0-2
	}, function(newTitle,index){
		
		if (newTitle!=''){
			var uri="${pageContext.request.contextPath}/action_portal_templet/copy/"+id+'?newTitle='+newTitle;
			
			$.ajax({
				url: uri,
				type: 'post',
				success: function(result) {
					if (result==0){
						parent.layer.msg('成功地复制了一个新模板 “'+newTitle+'” ！');
						location.reload();
						parent.layer.close(index);
						
					}else{
						parent.layer.alert('增加失败！');
					}
					
				}
			});
		}else{
			parent.layer.alert('请输入名称');
		}
		
		
		
	});
}



function share(title,uuid){
	var random;
	var uri ='${pageContext.request.contextPath}/action_system/random';
	$.ajax({
		url : uri,
		type : 'post',
		async:false,
		success : function(data) {
			random=data;
		}
	}); 
	
	parent.layer.prompt({
		  formType: 0,
		  resize:true,
		  value: random,
		  title: '通过下面的密钥可以完成模板共享',
		  success: function(layero, index){
			  $('.layui-layer-input').width('280px');
			  $('.layui-layer-input').attr("disabled",true); 
			  }
		}, function(value, index, elem){
			
			uri ='${requestScope.marketUrl}/action_templet_market/share/'+uuid+"/" + value+"/${requestScope.security}";
			//parent.layer.alert(uri);	
			  $.ajax({
					url : uri,
					type : 'post',
					success : function(data) {
						parent.layer.close(index);
						switch (data){
							case 0:
								parent.layer.msg('服务器已接收！');
								break;
							case -2:
								parent.layer.msg('对不起，您没有云端该模板的权限！');
								break;
							default:
								parent.layer.msg('操作失败，请检查您是否有权限或重新登录后操作！');
						}
					}
				}); 
		  
		});
	
	} 
	
</script>

<script>
layui.use('upload', function(){
  var upload = layui.upload;
   
  //执行实例
  var uploadInst = upload.render({
    elem: '#test1' //绑定元素
    ,url: '${pageContext.request.contextPath}/action_portal_templet/import' //上传接口
    ,accept: 'file'
    ,exts: 'portal'
    ,done: function(res){
    	if (res.code==0){
    		parent.layer.msg("导入成功！");
    		location.reload();
    	}else{
    		parent.layer.alert("导入失败！");
    	}
      //上传完毕回调
    }
    ,error: function(){
      //请求异常回调
    }
  });
});
</script>

<!-- 结束 -->

</div>
  </div>
  
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
});


for (i=0;i<ids.length;i++)
{
	$("#"+ids[i]).hide();
	upchk(ids[i]);
}

var copytips=$.session.get('copytips');
//alert(copytips);
if (copytips!=2){
	$.session.set('copytips', 2);
	parent.layer.alert('模板也是有版权的！请在使用时注意尊重作者版权，以免引起法律诉讼问题！<br />所有用户上传的模板，将在云端作详细记录。为保证您的权益，请注意备份您网站目录下的WEB-INF/security身份证明文件！',{title:'友情提醒',btn: ['我知道了！']});
}

</script>


</body>
</html>