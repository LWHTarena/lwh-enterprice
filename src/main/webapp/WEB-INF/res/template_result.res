<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="{$tag:contextPath$}/scripts/bootstrap-3.3.7-dist/css/bootstrap.min.css" />
<meta http-equiv=refresh content=2;url={$tag:returnUrl$} >
<style type="text/css">
body{ background:#ccc; font-size:14px; font-family:Arial, Helvetica, sans-serif;}
.con{ border:5px solid #aaa; width:560px; height:80px; margin:50px auto; padding:20px 10px; background:#eee;}
a:link{text-decoration:none;color:#C30;} 
a:visited{text-decoration:none;color:#C30;} 
a:hover{text-decoration:none;color:#f00;} 
a:active{text-decoration:none;color:#C30;}
</style>
<title>找不到资源</title>
<script type="text/javascript"
	src="{$tag:contextPath$}/scripts/js/jquery/1.9.1/jquery.min.js"></script>
<link rel="stylesheet" href="{$tag:contextPath$}/scripts/js/layui/v2/css/layui.css?t={$tag:currTime$}" media="all">
<script src="{$tag:contextPath$}/scripts/js/layui/v2/layui.js"></script>
</head>
<body>
<div id="con" class="con">
<div id="msg">{$tag:msg$} </div>
<div id="cond">2 秒后将返回，如果没有响应，请这里进行<a href="{$tag:returnUrl$}">手动返回</a>。
</div>
</div>
</body>
</html>
