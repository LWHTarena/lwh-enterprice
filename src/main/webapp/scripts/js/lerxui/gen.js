

/*
 * 获得最外层窗口
 */
function parentW() {
	var w=window;
	var con=true;
	
	while (con){
		if (typeof(w.parent) == "undefined" || w.parent==w ){
			con=false;
		}else{
			w=w.parent;
		}
	}
	
	return w;
}

function artattasReload(path,aid,divID){
	var uri=path+'/action_atta/reloadByAid/'+aid;
    $.ajax({
			url: uri,
			type: 'post',
			async: false,
			success: function(data) {
				var endhtml='';
				var item;
				var title;
				var tmp;
				for(var i=0,l=data.length;i<l;i++){
					item=data[i];
					title=item.title;
					if (title == "null" || title == null){
						title=item.url;
					}
					if (data.length>1){
						tmp='<li><span class="glyphicon glyphicon-paperclip">附件'+(i+1)+'：</span>　<a title="'+title+'" href="'+path+'/'+item.url+'">'+title+'</a></li>';
						
					}else{
						tmp='<li><span class="glyphicon glyphicon-paperclip"></span>　<a title="'+title+'" href="'+path+'/'+item.url+'">'+title+'</a></li>';
						
					}
					//alert('tmp:'+tmp);
					endhtml += tmp;
				}
				
				$('#'+divID).html(endhtml);
			}
	});
} 

function getRootPath(){
	var strFullPath=window.document.location.href; 
	var strPath=window.document.location.pathname; 
	var pos=strFullPath.indexOf(strPath); 
	var prePath=strFullPath.substring(0,pos); 
	var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1); 
	return (prePath+postPath);
} 

/*
 * 个人名片
 */
function card(path,id) {
	if (id<=0){
		return;
	}
	/*var w=window;
	var con=true;
	
	while (con){
		if (typeof(w.parent) == "undefined" || w.parent==window ){
			con=false;
		}else{
			w=w.parent;
		}
	}*/
	
	var w=parentW();
	
	w.layer.open({
		type: 2,
		skin: 'layui-layer-rim', //加上边框
		title: '手机名片',
		area : ['780px' , '600px'],
		content: path+'/action_user/card/'+id,
		success: function(layero, index){
			var newt=$(layero).find("iframe")[0].contentWindow.document.title;
			w.layer.title(newt, index);
	    }

	});
				
}

/*
 * 投票
 */
function poll(path,pid,status){
	var w=parentW();
	$.ajax({
		url:path+'/action_poll/poll/'+pid+'/'+status,
		type: 'post',
		success:function(data){
			switch (data.status){
			case 0:
				$("#poll_"+pid+"_agrees").html(data.agrees);
				w.layer.msg('谢谢您的参与！');
				break;
			case -5:
				w.layer.msg('该项目投票被管理员禁止！');
				break;
			case -8:
				w.layer.msg('您已经投过票了！');
				break;
			default:

				w.layer.msg('参数错误！');
			}
		
		}
	 });
}

/*
 * 检测是否允许投票
 */
function commEnv(path,bid){
	var tmp = -1;
	var uri=path+'/action_comm/env/'+bid;
	$.ajax({
		url:path+'/action_comm/env/'+bid,
		type: 'post',
		async: false,
		success:function(data){
		tmp=data;
		}
	 });
	 return tmp;
}

/*
 * 删除评论
 */
function commDel(path,id){
	var w=parentW();
	w.layer.confirm('您确定删除该评论 吗？ 删除后将不可恢复！',{title:'警告'}, function(index){
		  
		  var uri=path+"/action_comm/del/"+id;
		
			$.ajax({
				url: uri,
				type: 'post',
				success: function(data) {
					if (data==0){
						w.layer.msg('删除成功！您需要刷新页面才能看见效果！');
					}else{
						w.layer.msg('删除失败！错误号：'+data);
					}
					//location.reload(); 
				}
			});
		
			w.layer.close(index);
		});
}

/*
 * 审核评论
 */
function commPass(path,id){
	var w=parentW();
	w.layer.confirm('您确定通过该评论吗？ ',{title:'提醒'}, function(index){
		  
		  var uri=path+"/action_comm/pass/"+id;
		
			$.ajax({
				url: uri,
				type: 'post',
				success: function(data) {
					if (data==0){
						w.layer.msg('审核成功！您需要刷新页面才能看见效果！');
					}else{
						w.layer.msg('删除失败！错误号：'+data);
					}
					//location.reload(); 
				}
			});
		
			w.layer.close(index);
		});
}

/*
 * 增加评论
 */
function comment(path,el,bid,pid){
	var w=parentW();
	var env=commEnv(path,bid);
	switch (env){
		case -3:
			w.layer.msg("请登录后再发表评论！");
			break;
		case -5:
			w.layer.msg("系统禁止发表评论！");
			break;
		case -11:
			w.layer.msg("参数错误！");
			break;
		case -1:
			w.layer.msg("出现异常，不能发表评论！");
			break;
	}
	if (env<0){
		return;
	}
	layer.open({
	    type: 2,
	    skin: 'layui-layer-rim', //加上边框
	title: '增加评论',
	    area : ['600px' , '400px'],
	    content: path+'/jsp/comm/add/?bid='+bid+'&pid='+pid,
	    success: function(layero, index){
	    },
	    end: function(){
	    	
	    	$(''+el).html('');
	    	commFlow(path,el,bid);

	    	//location.reload();
	        
	      }

	});
 
}

/*
 * 修改密码
 */
function pws(path,id){
	var w=parentW();
	w.layer.prompt({
		  formType: 0,
		  value: '',
		  title: '修改登录密码',
		  area: ['800px', '350px'] //自定义文本域宽高
		}, function(value, index, elem){
			uri =path+'/action_user/pws/'+id+"?password=" + value;
			  $.ajax({
					url : uri,
					type : 'post',
					success : function(data) {
						if (data== 0){
							
							w.layer.msg('修改成功！', {
							
								time: 1000 //2秒关闭（如果不配置，默认是3秒）
								}, function(){
									w.location.reload();
								});   

							
							//w.layer.msg('修改成功！');
							//w.layer.close(index);
							
						}else{
							w.layer.msg('操作失败，请检查您是否有权限或重新登录后操作！');
							w.layer.close(index);
						}
					}
				}); 
		  
		});
	
	} 

//忘记密码
function forget(path){
	var w=parentW();
	w.layer.prompt({
		  formType: 0,
		  value: '',
		  title: '请输入您的用户名、手机号或邮箱'
	}, function(value, index, elem){
		
		//发送验证码
		uri =path+'/action_user/forget?keywords=' + value;
		$.ajax({
			url : uri,
			type : 'post',
			success : function(data) {
				
				if (data.result<0){
					if ( data.result== -8){
						w.layer.alert("刚才已经为您申请过一次验证码服务，请等一会儿再来好吗？");
					}else{
						w.layer.alert("您输入有误，没有查到您的信息！");
					}
					
				}else{
					w.layer.close(index);
					
					w.layer.prompt({
						  formType: 0,
						  value: '',
						  title: '请输入'+data.valueS2+'收到的验证码'
						}, function(value2, index2, elem){
							
							//验证
							var uri2 =path+'/action_captcha/validate?vcode=' + value2;
							$.ajax({
								url: uri2,
								type: 'post',
								success: function(data2) {
									if (data2<0){
										w.layer.msg("验证失败了！请检查您输入的验证码是否正确定！",{time: 3000});
									}else{
										// --start打开密码重置窗口
										w.layer.close(index2);
										
										w.layer.prompt({
											  formType: 0,
											  value: '',
											  title: '请为用户'+data.valueS1+'输入新的密码'
											}, function(value3, index3, elem){
												//密码重置
												var uri3 =path+'/action_user/pwsReset?uid=' + data.valueL+'&password='+value3;
												$.ajax({
													url: uri3,
													type: 'post',
													success: function(data3) {
														if (data3>=0){
															w.layer.close(index3);
															w.layer.msg("用户"+data.valueS1+"密码修改成功！请用新的密码登录！");
														}else{
															w.layer.close(index3);
															w.layer.msg("操作失败！错误号："+data3);
														}
													}
												});
												
										});
										
										// --end
										//w.layer.alert("打开密码重置窗口！");
									}
								}
							});
							
					});
					
					
				}
			}
		});
	});
}

/*
 * 修改手机
 */
function chgmobile(path,id){
	var w=parentW();
	
	//-----------
	w.layer.prompt({
		  formType: 0,
		  value: '',
		  title: '请输入您新的手机号码'
	}, function(value, index2, elem){
			
		//发送验证码
		uri =path+'/action_captcha/send?mode=1&target=' + value;
		$.ajax({
			url : uri,
			type : 'post',
			success : function(data) {
				//----s
				w.layer.close(index2); 
				switch (data){
				case 0:
					w.layer.prompt({
						  formType: 0,
						  value: '',
						  title: '请输入您手机上收到的验证码'
						}, function(value2, index3, elem){
							
							//验证
							var uri2 =path+'/action_captcha/validate?vcode=' + value2;
							$.ajax({
								url: uri2,
								type: 'post',
								success: function(data) {
									
									if (data<0){
										w.layer.msg("验证失败了！请检查您输入的验证码是否正确定！",{time: 3000});
									}else{
										w.layer.close(index3); 
										//处理
										uri =path+'/action_user/chgmobile/'+id+"?mobile=" + value;
										  $.ajax({
												url : uri,
												type : 'post',
												success : function(data) {
													
													switch (data){
													case 0:
														w.layer.msg('修改成功！下次打开本页面时会自动更新！'); 
														break;
													case -7:
														w.layer.msg('对不起，您没有该权限！');
														break;
													case -10:
														w.layer.msg('对不起，该手机号已被占用！');
														break;
													default:
														w.layer.msg('操作失败，请检查您是否有权限或重新登录后操作！');
													}
													
													w.layer.close(index);
													
												}
											});
										//处理结束
										
									}
									
								}
							});
							
							//
							
						});
					break;
				case -1:
					w.layer.msg("服务器小弟说他大哥不理他的请求，您能联系一下管理员看看是不是配置错误或者欠费啦？",{time: 5000});
					break;
				case -5:
					w.layer.msg("服务器小弟说他只能向他熟悉的目标发送，您的请求他无法完成啊！",{time: 5000});
					break;
				case -8:
					w.layer.msg("验证码已经发送过了，请让服务器小弟歇会儿喝口浓茶，好吗？",{time: 5000});
					break;
				case -10:
					w.layer.msg("这个手机号已被占用啦！",{time: 5000});
					break;
				case -11:
					w.layer.msg("服务器小弟说您输入错误，您信吗？",{time: 3000});
					break;
				default:
					w.layer.msg("服务器小弟处理不了您这复杂的请求啦！错误号："+data,{time: 3000});	
				}
				
				
				//----e
			}
		});
	   //发送验证码--end
		
	});
	//--------
	
	} 

/*
 * 修改email
 */
function chgemail(path,id){
	var w=parentW();
	
	//-----------
	w.layer.prompt({
		  formType: 0,
		  value: '',
		  title: '请输入您新的邮箱'
	}, function(value, index2, elem){
			
		//发送验证码
		uri =path+'/action_captcha/send?mode=0&target=' + value;
		$.ajax({
			url : uri,
			type : 'post',
			success : function(data) {
				//----s
				w.layer.close(index2); 
				switch (data){
				case 0:
					w.layer.prompt({
						  formType: 0,
						  value: '',
						  title: '请输入您邮箱中收到的验证码'
						}, function(value2, index3, elem){
							
							//验证
							var uri2 =path+'/action_captcha/validate?vcode=' + value2;
							$.ajax({
								url: uri2,
								type: 'post',
								success: function(data) {
									
									if (data<0){
										w.layer.msg("验证失败了！请检查您输入的验证码是否正确定！",{time: 3000});
									}else{
										w.layer.close(index3); 
										//处理
										uri =path+'/action_user/chgemail/'+id+"?email=" + value;
										  $.ajax({
												url : uri,
												type : 'post',
												success : function(data) {
													
													switch (data){
													case 0:
														w.layer.msg('修改成功！下次打开本页面时会自动更新！'); 
														break;
													case -7:
														w.layer.msg('对不起，您没有该权限！');
														break;
													case -10:
														w.layer.msg('对不起，该email已被占用！');
														break;
													default:
														w.layer.msg('操作失败，请检查您是否有权限或重新登录后操作！');
													}
													
													w.layer.close(index);
													
												}
											});
										//处理结束
										
									}
									
								}
							});
							
							//
							
						});
					break;
				case -1:
					w.layer.msg("服务器小弟说他大哥不理他的请求，您能联系一下管理员看看是不是配置错误或者欠费啦？",{time: 5000});
					break;
				case -5:
					w.layer.msg("服务器小弟说他只能向他熟悉的目标发送，您的请求他无法完成啊！",{time: 5000});
					break;
				case -8:
					w.layer.msg("验证码已经发送过了，请让服务器小弟歇会儿喝口浓茶，好吗？",{time: 5000});
					break;
				case -10:
					w.layer.msg("这个Email已被占用啦！",{time: 5000});
					break;
				case -11:
					w.layer.msg("服务器小弟说您输入错误，您信吗？",{time: 3000});
					break;
				default:
					w.layer.msg("服务器小弟处理不了您这复杂的请求啦！错误号："+data,{time: 3000});	
				}
				
				
				//----e
			}
		});
	   //发送验证码--end
		
	});
	//--------
	
	} 

/*
 * 用户登录
 */
function login(path) {
	var w=parentW();
	  w.layer.open({
	  type: 2,
	  skin: 'layui-layer-rim', //加上边框
	  title: '请输入登录的用户名和密码',
	  area: ['420px', '320px'], //宽高
	  content: path+'/jsp/user/login',
	  end: function(){ 
	    userpanel(); 
	   //location.reload();  
	   } 
	});

}

/*
 * 用户注册协议
 */
function agreement(path) {
	var w=parentW();
	uri =path+'/action_portal/regtest';
	$.ajax({
		url : uri,
		type : 'post',
		success : function(data) {
			var smod=data;
			
			if (smod>=0){
				
				//准备协议
				$.get(path+'/agreement.txt', function(result){

					w.layer.open({
						formType: 2,
						title: '使用协议',
						btn: ['同意','不同意'],
						yes: function(index){
							w.layer.close(index); 
							var wtitle="";
							if (smod==0){
								wtitle="邮箱地址";
							}else{
								wtitle="手机号码";
							}
								
							//注册
							
								w.layer.prompt({
									  formType: 0,
									  value: '',
									  title: '请输入您的'+wtitle
								}, function(value, index2, elem){
									var indexP = w.layer.load(1, {
										  shade: [0.1,'#fff'] //0.1透明度的白色背景
										});	
									//发送验证码
									uri =path+'/action_captcha/send?target=' + value;
									$.ajax({
										url : uri,
										type : 'post',
										success : function(data) {
											//----s
											w.layer.close(indexP); 
											w.layer.close(index2); 
											switch (data){
											case 0:
												w.layer.prompt({
													  formType: 0,
													  value: '',
													  title: '请输入您'+wtitle+'中收到的验证码'
													}, function(value2, index3, elem){
														
														//验证
														var uri2 =path+'/action_captcha/validate?vcode=' + value2;
														$.ajax({
															url: uri2,
															type: 'post',
															success: function(data) {
																
																if (data<0){
																	w.layer.msg("验证失败了！请检查您输入的验证码是否正确定！",{time: 3000});
																}else{
																	w.layer.close(index3); 
//																	w.layer.msg("good！",{time: 2000});
																	//打开注册页面
																	w.layer.open({
																		  type: 2,
																		  title: '注册',
																		  shadeClose: true,
																		  shade: 0.8,
																		  area: ['480px', '400px'],
																		  content: path+'/jsp/user/reg' //iframe的url
																	});
																	//
																	
																}
																
															}
														});
														
														//
														
													});
												break;
											case -1:
												w.layer.msg("服务器小弟说他大哥不理他的请求，您能联系一下管理员看看是不是配置错误或者欠费啦？",{time: 5000});
												break;
											case -5:
												w.layer.msg("服务器小弟说他只能向他熟悉的目标发送，您的请求他无法完成啊！",{time: 5000});
												break;
											case -8:
												w.layer.msg("验证码已经发送过了，请让服务器小弟歇会儿喝口浓茶，好吗？",{time: 5000});
												break;
											case -10:
												w.layer.msg("您输入的"+wtitle+"已经被占用了！",{time: 3000});
												break;
											case -11:
												w.layer.msg("服务器小弟说您输入错误，您信吗？",{time: 3000});
												break;
											default:
												w.layer.msg("服务器小弟处理不了您这复杂的请求啦！错误号："+data,{time: 3000});	
											}
											
											
											//----e
										}
									});
								   //发送验证码--end
									
								});
							
							
							//reg();
						},
						btn2: function(index){
							w.layer.close(index);
						},
						area: ['800px', '350px'], //自定义文本域宽高
						content:'<textarea class="layui-layer-input" style="width: 740px; height: 200px;line-height: 20px;padding: 6px 10px;display: block;font-family: inherit;font-size: inherit;font-style: inherit;font-weight: inherit;outline: 0;border: 1px solid #e6e6e6;color: #333;">'+result+'</textarea>'
						});
							
					});
				
				//
				
				
			}else if (smod == -2){
				w.layer.msg("经IP检测您当前的位置不能进行注册！");
			}else{
				w.layer.msg("系统未开放注册功能！");
			}
		}
	});
	
	
}



/*
 * 文章页显示浏览信息并更新
*/

function artView(path,aid){
	$.ajax({
		url:path+'/action_show/artViewShow/'+aid+"?url="+encodeURIComponent(window.location.href)+"&referer="+encodeURIComponent(document.referrer),
		type: 'post',
		success:function(data){
			$("#viewsarea").html(data);
		
		}
	 });
}


/*
 * 栏目页显示浏览信息并更新
*/

function navView(path,gid){
	$.ajax({
		url:path+'/action_show/navViewShow/'+gid+"?url="+encodeURI(window.location.href)+"&referer="+encodeURI(document.referrer),
		type: 'post',
		success:function(data){
			//$("#viewsarea").html(data);
		
		}
	 });
}

/*
 * 首页更新浏览信息
*/

function indexView(path){
	$.ajax({
		url:path+'/action_show/indexViewShow'+"?url="+encodeURI(window.location.href)+"&referer="+encodeURI(document.referrer),
		type: 'post',
		success:function(data){
			//$("#viewsarea").html(data);
		
		}
	 });
}

function qrcode(href){
	//alert(href);
	var w=parentW();
	var div = document.createElement("div");
	div.id="qrDiv";
	div.style.display = "none";
	document.body.appendChild(div);
	$("#qrDiv").css("padding","5px");
	$('#qrDiv').qrcode(href);
	w.layer.open({
		  type: 1, 
		  title:'扫描二维码分享',
		  shadeClose: true,
		  content: $('#qrDiv'), //这里content是一个普通的String
		  end:function(){
			  $('#qrDiv').remove();
		  }
		});
}

function pathRoot(contextPath){
	var href=window.location.href;
	alert("href:"+href);
	alert("contextPath:"+contextPath);
	var pos=href.indexOf(contextPath);
	if (pos != -1){
		alert("href.substring(pos+contextPath.length):"+href.substring(pos+contextPath.length));
		return href.substring(pos+contextPath.length);
	}else{
		return contextPath;
	}
}

function artlist(contextPath,gid,status,title){
	
	var w=parentW();
	w.layer.open({
		  type: 2, 
		  title:title,
		  area: ['1000px', '650px'], //宽高
		  shadeClose: true,
		  content: contextPath+'/action_article/inventory?gid='+gid+'&status='+status //这里content是一个普通的String
		});
}

