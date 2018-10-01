var __page_show_js__=document.scripts;
__page_show_js__=__page_show_js__[__page_show_js__.length-1].src.substring(0,__page_show_js__[__page_show_js__.length-1].src.lastIndexOf("/")+1);

jQuery.fn.extend({
	pagefmt: function(a) {
		
		var js=document.scripts;
		js=js[js.length-1].src.substring(0,js[js.length-1].src.lastIndexOf("/")+1);
		
		var cur = a.cur;
		var pagesize=a.pagesize;
		var pagecount = a.pagecount;
		var rest=a.rest;
		var url = a.url;
		var fmt=a.fmt;
		var showall=a.showall;
		var joinstr,tmp,tmpAll='';
		var s, e;
		var pageList=a.pageList;
		var showVoid=a.showVoid;
		var showEnds=a.showEnds;
		if (cur > pagecount) {
			cur = pagecount;
		}
		if (cur < 1) {
			cur = 1;
		}
		if (pagesize<=0){
			pagesize=10;
		}
		
		var forward,backward,first,end,total;
		var pre,post;
		var spanF,spanE;
		
		switch(fmt){
			case 1:
				backward='&nbsp;<&nbsp;';
				forward='&nbsp;>&nbsp;';
				first='&nbsp;<img src="'+__page_show_js__+'/images/first.png">&nbsp;';
				end='&nbsp;<img src="'+__page_show_js__+'/images/last.png">&nbsp;';
				total='&nbsp;total:' + pagecount;
				pre='';
				post='';
				spanF='&nbsp;';
				spanE='';
				spanDisableF='';
				
			break;
			case 2:
				backward='&nbsp;<&nbsp;';
				forward='&nbsp;>&nbsp;';
				first='<span aria-hidden="true">&laquo;</span>';
				end='<span aria-hidden="true">&raquo;</span>';
				total='';
				pre='';
				post='';
				spanF='<li>';
				spanE='</li>';
				spanDisableF='';
				break;
			case 3:
				backward='&nbsp;向前&nbsp;';
				forward='&nbsp;向后&nbsp;';
				first='';
				end='';
				total='';
				pre='';
				post='';
				spanF='<li>';
				spanE='</li>';
				spanDisableF='';
				break;
			case 4:
				backward='<';
				forward='>';
				first='';
				end='';
				total='';
				pre='';
				post='';
				spanF='<div class="filp__ward_">';
				spanDisableF='<div class="filp__ward__disable">';
				spanE='</div>';
				break;
			default:
				backward='上一页';
				forward='下一页';
				first='首页';
				end='尾页';
				total='共&nbsp;' + pagecount + '&nbsp;页';
				pre='&nbsp;第';
				post='页&nbsp;';
				spanF='&nbsp;';
				spanE='';
				spanDisableF='';
				
				
		}
		
		
		if (!showall){
			total='';
		}
		if (rest){
			joinstr = "/";
		}else{
			if (url.indexOf("?") == -1) {
				joinstr = "?";
			} else {
				joinstr = "&";
			}
			
		}
		
		
		url+=joinstr;
		
		s = cur - 5;
		e = cur + 4;
		if (s < 1) {
			s = 1;
			if (pagecount >= 10) {
				e = 10;
			} else {
				e = pagecount;
			}
		}
		if (e > pagecount) {
			e = pagecount;
			s = pagecount - 10;
			if (s < 1) {
				s = 1;
			}
		}
		
		if (pageList){
			for (var i=s;i<=e;i++){
				if (i!=cur){
					if (rest){
						tmp=spanF+'<a href='+url+'page'+i+'/pagesize'+pagesize+'>'+i+'</a>'+spanE;
					}else{
						tmp=spanF+'<a href='+url+'page='+i+'&pagesize='+pagesize+'>'+i+'</a>'+spanE;
					}
					
				}else{
					tmp=spanF+'<a href="javascript:;"><b>'+i+'</b></a>'+spanE;
					
				}
				tmpAll+=tmp;
			}
			
			if (tmpAll==""){
				tmpAll="1";
			}
			
			tmpAll=pre+''+tmpAll+'&nbsp;'+post;
		}
		var endsStr;
		if (pagecount>1){
			var spanFTmp,spanDisableFTmp;
			spanFTmp=spanF;
			spanFTmp=spanFTmp.replace('_ward_', 'backward');
			spanDisableFTmp=spanDisableF;
			spanDisableFTmp=spanDisableFTmp.replace('_ward_', 'backward');
			if (cur>1){
				if (rest){
				
					if (showEnds){
						endsStr=spanFTmp+'<a href='+url+'page1/pagesize'+pagesize+'>'+first+'</a>'+spanE;
					}else{
						endsStr='';
					}
					
					tmpAll=endsStr+spanFTmp+'<a href='+url+'page'+(cur-1)+'/pagesize'+pagesize+'>'+backward+'</a>'+spanE+tmpAll;
				}else{
					if (showEnds){
						endsStr=spanFTmp+'<a href='+url+'page=1&pagesize='+pagesize+'>'+first+'</a>'+spanE;
					}else{
						endsStr='';
					}
					tmpAll=endsStr+spanFTmp+'<a href='+url+'page='+(cur-1)+'&pagesize='+pagesize+'>'+backward+'</a>'+spanE+tmpAll;
				}
				
			}else{
				if (showVoid){
					tmpAll=spanDisableFTmp+'<a href=javascript:;>'+backward+'</a>'+spanE+tmpAll;
				}
				
			}
			spanFTmp=spanF;
			spanFTmp=spanFTmp.replace('_ward_', 'forward');
			spanDisableFTmp=spanDisableF;
			spanDisableFTmp=spanDisableFTmp.replace('_ward_', 'forward');
			if (cur<pagecount){
				if (rest){
					if (showEnds){
						endsStr=spanFTmp+'<a href='+url+'page'+pagecount+'/pagesize'+pagesize+'>'+end+'</a>'+spanE;
					}else{
						endsStr='';
					}
					tmpAll+=spanFTmp+'<a href='+url+'page'+(cur+1)+'/pagesize'+pagesize+'>'+forward+'</a>'+spanE+endsStr;
				}else{
					if (showEnds){
						endsStr=spanFTmp+'<a href='+url+'page='+pagecount+'&pagesize='+pagesize+'>'+end+'</a>'+spanE
					}else{
						endsStr='';
					}
					tmpAll+=spanFTmp+'<a href='+url+'page='+(cur+1)+'&pagesize='+pagesize+'>'+forward+'</a>'+spanE+endsStr;
				}
				
			}else{
				if (showVoid){
					tmpAll+=spanDisableFTmp+'<a href=javascript:;>'+forward+'</a>'+spanE;
				}
			}
			tmpAll+='&nbsp;'+total;
			
			//tmpAll+='　跳转到第<input type="text" name="page" size="2" maxlength="5" style="text-align: center" value="' + cur + '" onKeyPress="if (event.keyCode==13) window.location=' + url + 'pageSize="+rs.getPageSize()+"&page='" + "+this.value;\">"+fs.getSuffix();
			
		}
		
		$(this).html(tmpAll);
		
	}
});