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
<jsp:useBean id="hq" class="com.lwhtarena.company.sys.util.HttpUtil" scope="page" ></jsp:useBean>

<jsp:useBean id="portal" class="com.lwhtarena.company.entities.Portal" scope="request" ></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>站点信息</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/js/layui/v2/css/layui.css?t=${tu.getCurrTime()}" media="all">
<script src="${pageContext.request.contextPath}/scripts/js/layui/v2/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/js/jquery/jquery.qrcode.min.js"></script>
<script src="${pageContext.request.contextPath}/scripts/js/lerxui/gen.js"></script>
<script src="${pageContext.request.contextPath}/scripts/js/RGraph/libraries/RGraph.common.core.js" ></script>
<script src="${pageContext.request.contextPath}/scripts/js/RGraph/libraries/RGraph.line.js" ></script>
	
<style type="text/css">
A       { COLOR: #000000; TEXT-DECORATION: none}
A:hover { COLOR: #f58200}
body,td,span { font-size: 9pt}
.input  { BACKGROUND-COLOR: #ffffff;BORDER:#f58200 1px solid;FONT-SIZE: 9pt}
.backc  { BACKGROUND-COLOR: #f58200;BORDER:#f58200 1px solid;FONT-SIZE: 9pt;color:white}
.PicBar { background-color: #f58200; border: 1px solid #000000; height: 12px;}
.tableBorder {BORDER-RIGHT: #183789 1px solid; BORDER-TOP: #183789 1px solid; BORDER-LEFT: #183789 1px solid; BORDER-BOTTOM: #183789 1px solid; BACKGROUND-COLOR: #ffffff; WIDTH: 760;}
.tableNoBorder {border:0px;border-style:hidden;width:100%;}
.divcenter {
	position:absolute;
	height:30px;
	z-index:1000;
	left: 101px;
	top: 993px;
}
.layui-collapse{}
.layui-table img{max-width: 100%;}
</style>



</head>
<body>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>服务器相关参数</legend>
</fieldset>


      <c:set var="level" value='${requestScope.es.queryHashtable("sun.os.patch.level")}'></c:set>
     
      <table class="layui-table">
      <tr>
      <td style="text-align: right;">主机：</td><td>${requestScope.request.getServerName()} (${requestScope.request.getRemoteAddr()}) 　<a title="手机端监测" href="javascript:qrsec();"><img src="${pageContext.request.contextPath}/images/monitor.png" width="24" ></a> 　<a title="重启服务" href="javascript:reload();"><img src="${pageContext.request.contextPath}/images/restart.png" width="24" ></a></td>
      <td style="text-align: right;">CPU核心数：</td><td>${requestScope.processors}</td>
      <td style="text-align: right;">操作系统：</td><td>${requestScope.es.queryHashtable("os.name")}</td>
      <td style="text-align: right;">版本：</td><td>${requestScope.es.queryHashtable("os.version")} <c:if test="${level!='unknown'}">${level}</c:if></td>
      </tr>
      <tr>
      <td style="text-align: right;">系统类型和模式：</td><td>${requestScope.es.queryHashtable("os.arch")} | ${requestScope.es.queryHashtable("sun.arch.data.model")}位</td>
      <td style="text-align: right;">地区和语言：</td><td>${requestScope.es.queryHashtable("user.country")} ${requestScope.es.queryHashtable("user.language")}</td>
      <td style="text-align: right;">时区：</td><td>${requestScope.zone}</td>
      <td style="text-align: right;">当前时间：</td><td><date:date value ='${tu.getCurrTime() }' fmtstr='yyyy-MM-dd HH:mm:ss' /></td>
      </tr>
      <tr>
      <td style="text-align: right;">引擎和端口：</td><td>${requestScope.request.getServletContext().getServerInfo()} | ${requestScope.request.getServerPort()}</td>
      <td style="text-align: right;">启动用户及目录：</td><td>${requestScope.es.queryHashtable("user.name")} | ${requestScope.es.queryHashtable("user.dir")}</td>
      <td style="text-align: right;">启动日期：</td><td><date:date value ='${requestScope.runtime }' fmtstr='yyyy-MM-dd HH:mm:ss' /></td>
      <td style="text-align: right;">运行时长：</td><td>${requestScope.de.day} 天  ${requestScope.de.hour} 小时   ${requestScope.de.minute} 分钟 ${requestScope.de.second} 秒 </td>
      </tr>
      <tr>
      <td style="text-align: right;">应用目录：</td><td colspan="3">${requestScope.currPath}</td>
      <td style="text-align: right;">磁盘剩余：</td><td colspan="3">
      <table class="tableNoBorder">
      <tr>
      <td width="70%"><div class="layui-progress layui-progress-big" lay-showPercent="yes">
	  					<div class="layui-progress-bar layui-bg-green" lay-percent="${requestScope.spacePercent}%"></div>
		  </div>
	  </td>
	  <td  width="30%">${requestScope.spaceFree} ${requestScope.capacityUnit}/${requestScope.spaceTotal} ${requestScope.capacityUnit}</td>
      </tr>
      </table>
      
      </td>
      </tr>
      </table>

  
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>JAVA相关参数</legend>
</fieldset>


      <table class="layui-table">
      <thead>
          <tr > 
            <th width="20%">&nbsp;名称</th>
            <th width="50%">&nbsp;英文名称</th>
            <th width="30%">&nbsp;版本</th>
          </tr>
      </thead>
      <tbody>
          <tr> 
            <td>运行环境</td>
            <td>${requestScope.es.queryHashtable("java.runtime.name")} </td>
            <td>${requestScope.es.queryHashtable("java.runtime.version")}</td>
          </tr>
          <tr> 
            <td>平台API技术规范</td>
            <td>${requestScope.es.queryHashtable("java.specification.name")}</td>
            <td>${requestScope.es.queryHashtable("java.specification.version")}</td>
          </tr>
          <tr> 
            <td>虚拟机</td>
            <td>${requestScope.es.queryHashtable("java.vm.name")}</td>
            <td>${requestScope.es.queryHashtable("java.vm.version")}</td>
          </tr>
          <tr> 
            <td>虚拟机（JVM）规范</td>
            <td>${requestScope.es.queryHashtable("java.vm.specification.name")}</td>
            <td>${requestScope.es.queryHashtable("java.vm.specification.version")}</td>
          </tr>
		  <tr> 
            <td>虚拟机内存剩余</td>
            <td colspan="2">
            <table class="tableNoBorder">
      			<tr>
      				<td width="85%"><div class="layui-progress layui-progress-big" lay-showPercent="yes">
	  					<div class="layui-progress-bar layui-bg-green" lay-percent="${requestScope.percentMemory}%"></div>
		  				</div>
	  				</td>
	  				<td  width="15%">${requestScope.freeMemory} MB/${requestScope.totalMemory} MB</td>
      			</tr>
      		</table>
            
            </td>
            
          </tr>
		  	
        </tbody>
        </table>
		<table  class="layui-table">
		<thead>
          <tr > 
            <th width="20%">&nbsp;参数名称</th>
            <th width="80%">&nbsp;参数路径</th>
          </tr>
         </thead>
         <tbody>
          <tr> 
            <td>java.class.path </td>
            <td>${requestScope.es.queryHashtable("java.class.path").replaceAll(requestScope.es.queryHashtable("path.separator"),";<br>")}		
			</td>
          </tr>
          <tr> 
            <td>java.home</td>
            <td>${requestScope.es.queryHashtable("java.home")}</td>
          </tr>
          <tr> 
            <td>java.endorsed.dirs</td>
            <td>${requestScope.es.queryHashtable("java.endorsed.dirs")}</td>
          </tr>
          <tr> 
            <td>java.library.path</td>
            <td>${requestScope.es.queryHashtable("java.library.path").replaceAll(requestScope.es.queryHashtable("path.separator"),";<br>")}
			</td>
          </tr>
		  <tr> 
            <td>java.io.tmpdir</td>
            <td>${requestScope.es.queryHashtable("java.io.tmpdir")}</td>
          </tr>
          </tbody>
        </table>



<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>时刻流量表</legend>
</fieldset>
 
<div class="layui-tab layui-tab-card cvs-card">
  <ul class="layui-tab-title">
    <li class="layui-this"  id="tab11" >今日</li>
    <li id="tab22">昨日</li>
    <li id="tab33">平均</li>
   
  </ul>
  <div class="layui-tab-content">
    <div class="layui-tab-item layui-show">

		<canvas id="cvsToday" width="800px" class="cvs" >[No canvas support]</canvas>
	</div>
    <div class="layui-tab-item">
    	<canvas id="cvsYesterday" width="800px" class="cvs" >[No canvas support]</canvas>
    </div>
    <div class="layui-tab-item">
		<canvas id="cvsAvg" width="800px" class="cvs" >[No canvas support]</canvas>
	</div>
    

   
  </div>
</div>

<script>
        window.onload = function ()
        {
        	            
            var lineToday = new RGraph.Line({
                id: 'cvsToday',
                data: [[${ip24HourToday}],[${vs24HourToday}]],
                options: {
                    textAccessible: true,
                    hmargin: 5,
                    tickmarks: 'endcircle',
                    colors: [['#099'],['#F00']],
                    labels: ['','1','','3','','5','','7','','9','','11','','13','','15','','17','','19','','21','','23']
                }
            }).draw();
           
            
           
           $("#tab22").click();
            var lineYesterday = new RGraph.Line({
                id: 'cvsYesterday',
                data: [[${ip24HourYesterday}],[${vs24HourYesterday}]],
                options: {
                    textAccessible: true,
                    hmargin: 5,
                    tickmarks: 'endcircle',
                    colors: [['#099'],['#F00']],
                    labels: ['','1','','3','','5','','7','','9','','11','','13','','15','','17','','19','','21','','23']
                }
            }).draw();
            
            $("#tab33").click();
            
            var lineAvg = new RGraph.Line({
                id: 'cvsAvg',
                data: [[${ip24HourAvg}],[${vs24HourAvg}]],
                options: {
                    textAccessible: true,
                    hmargin: 5,
                    tickmarks: 'endcircle',
                    colors: [['#099'],['#F00']],
                    labels: ['','1','','3','','5','','7','','9','','11','','13','','15','','17','','19','','21','','23']
                }
            }).draw();
           
            $("#tab11").click();
            
        };
    </script>
    
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>数据统计</legend>
</fieldset>

<table  class="layui-table">
		<thead>
         </thead>
         <tbody>
          <tr> 
            <td>访问总数 (PV/IP)： </td>
            <td>${requestScope.psi.views} / ${requestScope.psi.ips}	</td>
          </tr>
          <tr> 
            <td>今日访问总数 (PV/IP)： </td>
            <td>${requestScope.vaToday.views} / ${requestScope.vaToday.ips}	</td>
          </tr>
          <tr> 
            <td>昨日访问总数 (PV/IP)： </td>
            <td>${requestScope.vaYesterday.views} / ${requestScope.vaYesterday.ips}	</td>
          </tr>
         
          
      </tbody>
</table>

<script>
layui.use(['element', 'layer'], function(){
  var element = layui.element;
  var layer = layui.layer;
  
  //监听折叠
  element.on('collapse(test)', function(data){
    layer.msg('展开状态：'+ data.show);
  });
});
</script>

<script>
function qrsec() {
	var mes='请不要将本二维码及产生的地址泄露给无关人员！';
	<c:if test="${adminLogin}">
	mes='本二维码及产生的地址如果泄露给无关人员，可能对您的网站造成灾难性结果！请注意保密！';
	</c:if>
	parent.layer.confirm(mes,{title:'警告'}, function(index){
		parent.layer.close(index);
		window.parent.qr('${requestScope.host}/action_portal/runn/${requestScope.md5target}/${requestScope.salt}');
	});
}

function reload(){
	parent.layer.confirm('您确定重新加载服务器 吗？该操作会引起访问中断！',{title:'警告'}, function(index){
		  
		  var uri="${pageContext.request.contextPath}/action_portal/reload";
		
			$.ajax({
				url: uri,
				type: 'post',
				success: function(data) {
					if (data==0){
						parent.layer.msg('指定发送成功！服务即将重新刷新。');
					}else{
						parent.layer.msg('操作失败！错误号：'+data);
					}
					//location.reload(); 
				}
			});
		
			parent.layer.close(index);
		});
}

</script>

</body>
</html>