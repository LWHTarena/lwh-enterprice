package com.lwhtarena.company.web.common;

import com.lwhtarena.company.analyze.util.AnalyzeUtil;
import com.lwhtarena.company.analyze.vo.DataShowParams;
import com.lwhtarena.company.analyze.vo.FindedDataAnalyzeResult;
import com.lwhtarena.company.sys.util.StringUtil;
import com.lwhtarena.company.web.entities.TempletPortalMain;
import com.lwhtarena.company.web.entities.TempletPortalSubElement;
import com.lwhtarena.company.web.portal.obj.EnvirSet;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * <h2>简述：</h2>
 * <ol></ol>
 * <h2>功能描述：</h2>
 * <ol></ol>
 * </p>
 *
 * @Author: liwh
 * @Date :
 * @Version: 版本
 */
public class TempletUtil2 {
    public static String tagToValue(String html, EnvirSet is, FindedDataAnalyzeResult fdar){
		/*DataShowParams dsp=fdar.getDsp();
		String lf=dsp.getLoopFormatStr();
		lf=replaceMid(lf,mid);
		String tmp,tmpAll="";
		if (dsp.getDataSource().equals("booth")){
			Rs rs=is.getBoothDaoImpl().queryByMarketID(mid, dsp.getCurpage(), dsp.getPagesize());
			for (Object obj:rs.getList()){
				Booth booth=(Booth) obj;
				tmp=lf;
				tmp=BoothUtil.fmt(tmp, booth);
				tmpAll+=tmp;
			}
			html=StringUtil.strReplace(html, "{$$recCount$$}", ""+rs.getCount());
			html=StringUtil.strReplace(html, "{$$pageCount$$}", ""+rs.getPageCount());
			html=StringUtil.strReplace(html, fdar.getWholeTag(), tmpAll);
			return html;
		}

		if (dsp.getDataSource().equals("article")){
			if (dsp.getSingle()==1){
				if (dsp.getSingleID()>0){
					Article article=is.getArticleDaoImpl().findByID(dsp.getSingleID());
					lf=ArticleUtil.fmt(lf, article);
				}else{
					Rs rs=is.getArticleDaoImpl().queryByGroupID(dsp.getGid(), 1, 1,1);
					Article article=(Article) rs.getList().get(0);
					lf=ArticleUtil.fmt(lf, article);
				}
				html=StringUtil.strReplace(html, fdar.getWholeTag(), lf);
			}else{
				Rs rs=is.getArticleDaoImpl().queryByGroupID(dsp.getGid(), dsp.getCurpage(), dsp.getPagesize(),1);
				int step=0;
				int curStep;
				for (Object obj:rs.getList()){
					step++;
					Article article=(Article) obj;
					tmp=lf;
					tmp=ArticleUtil.fmt(tmp, article);
					curStep=((rs.getPage()-1)*rs.getPageSize()) + step;
					tmp=StringUtil.strReplace(tmp, "{$$sn$$}", ""+curStep);
					tmpAll+=tmp;
				}
				html=StringUtil.strReplace(html, "{$$recCount$$}", ""+rs.getCount());
				html=StringUtil.strReplace(html, "{$$pageCount$$}", ""+rs.getPageCount());
				html=StringUtil.strReplace(html, fdar.getWholeTag(), tmpAll);
			}

			return html;
		}*/

        /*
         * 以后再增加
         *
         */

        return html;

    }

    /*
     * 初始化标签返回对象，主要是给格式化字符串进行检查和赋值
     */
    public static FindedDataAnalyzeResult fmtjj(FindedDataAnalyzeResult fdar, TempletPortalMain main, TempletPortalSubElement el){
        DataShowParams dsp=fdar.getDsp();
        if (dsp.getLoopFormatStr()!=null && dsp.getLoopFormatStr().trim().equals("")){
            dsp.setLoopFormatStr(null);
        }
        if (dsp.getLoopFormatStr()==null){ 		//只有在强制性规定格式化字符串的情况下才进行处理
            String fs=dsp.getFormatSource();
            if (fs==null || fs.trim().equals("")){				   		//如果强制性规定格式化字符串和格式化来源均为空，返回null
                return null;
            }else{

                /*
                 * 详细处理
                 */

                //如果是模块
                if (fs.trim().equalsIgnoreCase("el")){
                    switch (dsp.getFid()){
                        case 1:
                            dsp.setLoopFormatStr(el.getMinorLoopCodeInLump());
                            break;
                        default:
                            dsp.setLoopFormatStr(el.getMajorLoopCodeInLump());
                    }
                    fdar.setDsp(dsp);
                    return fdar;
                }

                //如果是exclusive
                if (fs.trim().equalsIgnoreCase("exclusive")){
                    switch (dsp.getFid()){
                        case 2:
                            dsp.setLoopFormatStr(el.getExclusiveCode2());
                            break;
                        case 3:
                            dsp.setLoopFormatStr(el.getExclusiveCode3());
                            break;
                        case 4:
                            dsp.setLoopFormatStr(el.getExclusiveCode4());
                            break;
                        default:
                            dsp.setLoopFormatStr(el.getExclusiveCode1());
                    }
                    fdar.setDsp(dsp);
                    return fdar;
                }

                //如果是publicCode
                if (fs.trim().equalsIgnoreCase("public")){
                    switch (dsp.getFid()){
                        case 2:
                            dsp.setLoopFormatStr(main.getPublicCode2());
                            break;
                        case 3:
                            dsp.setLoopFormatStr(main.getPublicCode3());
                            break;
                        case 4:
                            dsp.setLoopFormatStr(main.getPublicCode4());
                            break;
                        default:
                            dsp.setLoopFormatStr(main.getPublicCode1());
                    }
                    fdar.setDsp(dsp);
                    return fdar;
                }

                //如果是publicCode
                if (fs.trim().equalsIgnoreCase("custom")){
                    switch (dsp.getFid()){
                        case 2:
                            dsp.setLoopFormatStr(main.getCustomFormatCode2());
                            break;
                        case 3:
                            dsp.setLoopFormatStr(main.getCustomFormatCode3());
                            break;
                        case 4:
                            dsp.setLoopFormatStr(main.getCustomFormatCode4());
                            break;
                        case 5:
                            dsp.setLoopFormatStr(main.getCustomFormatCode5());
                            break;
                        case 6:
                            dsp.setLoopFormatStr(main.getCustomFormatCode6());
                            break;
                        case 7:
                            dsp.setLoopFormatStr(main.getCustomFormatCode7());
                            break;
                        case 8:
                            dsp.setLoopFormatStr(main.getCustomFormatCode8());
                            break;
                        default:
                            dsp.setLoopFormatStr(main.getCustomFormatCode1());
                    }


                    fdar.setDsp(dsp);
                    return fdar;
                }

                /*
                 * 下面是提供的更加变态更加激进更加疯狂的几个标签
                 * pubic_exclusive
                 * index_exclusive
                 * nav_exclusive
                 * article_exclusive
                 * extend_exclusive
                 * search_exclusive
                 * artbody
                 */

                //如果是pubic_exclusive
                if (fs.trim().equalsIgnoreCase("pubic_exclusive")){
                    String varStr;

                    switch (dsp.getFid()){
                        case 2:
                            varStr=main.getElPublic().getExclusiveCode2();
                            break;
                        case 3:
                            varStr=main.getElPublic().getExclusiveCode3();
                            break;
                        case 4:
                            varStr=main.getElPublic().getExclusiveCode4();
                            break;
                        default:
                            varStr=main.getElPublic().getExclusiveCode1();
                    }
                    dsp.setLoopFormatStr(varStr);
                    fdar.setDsp(dsp);
                    return fdar;
                }

                //如果是index_exclusive
                if (fs.trim().equalsIgnoreCase("index_exclusive")){
                    String varStr;

                    switch (dsp.getFid()){
                        case 2:
                            varStr=main.getElIndex().getExclusiveCode2();
                            break;
                        case 3:
                            varStr=main.getElIndex().getExclusiveCode3();
                            break;
                        case 4:
                            varStr=main.getElIndex().getExclusiveCode4();
                            break;
                        default:
                            varStr=main.getElIndex().getExclusiveCode1();
                    }
                    dsp.setLoopFormatStr(varStr);
                    fdar.setDsp(dsp);
                    return fdar;
                }

                //如果是nav_exclusive
                if (fs.trim().equalsIgnoreCase("nav_exclusive")){
                    String varStr;

                    switch (dsp.getFid()){
                        case 2:
                            varStr=main.getElNav().getExclusiveCode2();
                            break;
                        case 3:
                            varStr=main.getElNav().getExclusiveCode3();
                            break;
                        case 4:
                            varStr=main.getElNav().getExclusiveCode4();
                            break;
                        default:
                            varStr=main.getElNav().getExclusiveCode1();
                    }
                    dsp.setLoopFormatStr(varStr);
                    fdar.setDsp(dsp);
                    return fdar;
                }

                //如果是article_exclusive
                if (fs.trim().equalsIgnoreCase("article_exclusive")){
                    String varStr;

                    switch (dsp.getFid()){
                        case 2:
                            varStr=main.getElArt().getExclusiveCode2();
                            break;
                        case 3:
                            varStr=main.getElArt().getExclusiveCode3();
                            break;
                        case 4:
                            varStr=main.getElArt().getExclusiveCode4();
                            break;
                        default:
                            varStr=main.getElArt().getExclusiveCode1();
                    }
                    dsp.setLoopFormatStr(varStr);
                    fdar.setDsp(dsp);
                    return fdar;
                }


                //如果是extend_exclusive
                if (fs.trim().equalsIgnoreCase("extend_exclusive")){
                    String varStr;

                    switch (dsp.getFid()){
                        case 2:
                            varStr=main.getElExtend().getExclusiveCode2();
                            break;
                        case 3:
                            varStr=main.getElExtend().getExclusiveCode3();
                            break;
                        case 4:
                            varStr=main.getElExtend().getExclusiveCode4();
                            break;
                        default:
                            varStr=main.getElExtend().getExclusiveCode1();
                    }
                    dsp.setLoopFormatStr(varStr);
                    fdar.setDsp(dsp);
                    return fdar;
                }


                //如果是search_exclusive
                if (fs.trim().equalsIgnoreCase("corpus_exclusive")){
                    String varStr;

                    switch (dsp.getFid()){
                        case 2:
                            varStr=main.getElCorpus().getExclusiveCode2();
                            break;
                        case 3:
                            varStr=main.getElCorpus().getExclusiveCode3();
                            break;
                        case 4:
                            varStr=main.getElCorpus().getExclusiveCode4();
                            break;
                        default:
                            varStr=main.getElCorpus().getExclusiveCode1();
                    }
                    dsp.setLoopFormatStr(varStr);
                    fdar.setDsp(dsp);
                    return fdar;
                }


                if (fs.trim().equalsIgnoreCase("artbody")){
                    String varStr=main.getElArt().getMainCode();
                    varStr=elInsideii(varStr,main.getElArt());
                    dsp.setLoopFormatStr(varStr);
                    fdar.setDsp(dsp);
                    return fdar;
                }



                //如果未指定格式源或格式源不是上述几种，则失败
                return null;


            }

        }



        return fdar;
    }





    public static TempletPortalMain copyuu(TempletPortalMain oldTemplate,String newTitle){
        TempletPortalMain newTemplate=new TempletPortalMain();
        newTemplate.setAuthor(oldTemplate.getAuthor());
        newTemplate.setCustomFormatCode1(oldTemplate.getCustomFormatCode1());
        newTemplate.setCustomFormatCode2(oldTemplate.getCustomFormatCode2());
        newTemplate.setCustomFormatCode3(oldTemplate.getCustomFormatCode3());
        newTemplate.setCustomFormatCode4(oldTemplate.getCustomFormatCode4());
        newTemplate.setCustomFormatCode5(oldTemplate.getCustomFormatCode5());
        newTemplate.setCustomFormatCode6(oldTemplate.getCustomFormatCode6());
        newTemplate.setCustomFormatCode7(oldTemplate.getCustomFormatCode7());
        newTemplate.setCustomFormatCode8(oldTemplate.getCustomFormatCode8());
        newTemplate.setDef(false);
        newTemplate.setDescription(oldTemplate.getDescription());
        newTemplate.setDesignTime(System.currentTimeMillis());
        newTemplate.setOrderNum(0);
        newTemplate.setPublicCode1(oldTemplate.getPublicCode1());
        newTemplate.setPublicCode2(oldTemplate.getPublicCode2());
        newTemplate.setPublicCode3(oldTemplate.getPublicCode3());
        newTemplate.setPublicCode4(oldTemplate.getPublicCode4());
        newTemplate.setState(true);
        newTemplate.setName(newTitle);
        newTemplate.setElArt(copy(oldTemplate.getElArt()));
        newTemplate.setElExtend(copy(oldTemplate.getElExtend()));
        newTemplate.setElIndex(copy(oldTemplate.getElIndex()));
        newTemplate.setElNav(copy(oldTemplate.getElNav()));
        newTemplate.setElPublic(copy(oldTemplate.getElPublic()));
        newTemplate.setElCorpus(copy(oldTemplate.getElCorpus()));
        return newTemplate;

    }

    private static TempletPortalSubElement copy(TempletPortalSubElement oldEl){
        TempletPortalSubElement el=new TempletPortalSubElement();
        el.setBorderCode(oldEl.getBorderCode());
        el.setCssCode(oldEl.getCssCode());
        el.setExclusiveCode1(oldEl.getExclusiveCode1());
        el.setExclusiveCode2(oldEl.getExclusiveCode2());
        el.setExclusiveCode3(oldEl.getExclusiveCode3());
        el.setExclusiveCode4(oldEl.getExclusiveCode4());
        el.setFooterCode(oldEl.getFooterCode());
        el.setFunctionAreaCode(oldEl.getFunctionAreaCode());
        el.setMainCode(oldEl.getMainCode());
        el.setMajorLoopCodeInLump(oldEl.getMajorLoopCodeInLump());
        el.setMinorLoopCodeInLump(oldEl.getMinorLoopCodeInLump());
        el.setSearchAreaCode(oldEl.getSearchAreaCode());
        el.setTargetStr(oldEl.getTargetStr());
        el.setTopCode(oldEl.getTopCode());
        el.setFooterCode(oldEl.getFooterCode());
        el.setTitleFormat(oldEl.getTitleFormat());
        el.setUserPanelCodeLogin(oldEl.getUserPanelCodeLogin());
        el.setUserPanelCodeLogout(oldEl.getUserPanelCodeLogout());
        return el;
    }

    public static String baseTagsjj(String html,TempletPortalMain  templet,HttpServletRequest request){

        html=StringUtil.strReplace(html, "{$$publicCode1$$}", templet.getPublicCode1());
        html=StringUtil.strReplace(html, "{$$publicCode2$$}", templet.getPublicCode2());
        html=StringUtil.strReplace(html, "{$$publicCode3$$}", templet.getPublicCode3());
        html=StringUtil.strReplace(html, "{$$publicCode4$$}", templet.getPublicCode4());
        html=StringUtil.strReplace(html, "{$$customFormatCode1$$}", templet.getCustomFormatCode1());
        html=StringUtil.strReplace(html, "{$$customFormatCode2$$}", templet.getCustomFormatCode2());
        html=StringUtil.strReplace(html, "{$$customFormatCode3$$}", templet.getCustomFormatCode3());
        html=StringUtil.strReplace(html, "{$$customFormatCode4$$}", templet.getCustomFormatCode4());
        html=StringUtil.strReplace(html, "{$$customFormatCode5$$}", templet.getCustomFormatCode5());
        html=StringUtil.strReplace(html, "{$$customFormatCode6$$}", templet.getCustomFormatCode6());
        html=StringUtil.strReplace(html, "{$$customFormatCode7$$}", templet.getCustomFormatCode7());
        html=StringUtil.strReplace(html, "{$$customFormatCode8$$}", templet.getCustomFormatCode8());
        html=StringUtil.strReplace(html, "{$$contextPath$$}", request.getContextPath());
        return html;
    }

    public static String init77(String html,TempletPortalSubElement el){

        html=elInsideii(html,el);
        return html;
    }

    private static String elInsideii(String html,TempletPortalSubElement el){
        /*
         * 先处理code标签，支持非关键位置空格或换行如：
         * {$
         * code :top
         * $}
         */
        html=AnalyzeUtil.replace(html, "code", "main", el.getMainCode());
        html=AnalyzeUtil.replace(html, "code", "top", el.getTopCode());
        html=AnalyzeUtil.replace(html, "code", "footer", el.getFooterCode());
        html=AnalyzeUtil.replace(html, "code", "exclusive1", el.getExclusiveCode1());
        html=AnalyzeUtil.replace(html, "code", "exclusive2", el.getExclusiveCode2());
        html=AnalyzeUtil.replace(html, "code", "exclusive3", el.getExclusiveCode3());
        html=AnalyzeUtil.replace(html, "code", "exclusive4", el.getExclusiveCode4());

        html=StringUtil.strReplace(html, "{$$exclusiveCode1$$}", el.getExclusiveCode1());
        html=StringUtil.strReplace(html, "{$$exclusiveCode2$$}", el.getExclusiveCode2());
        html=StringUtil.strReplace(html, "{$$exclusiveCode3$$}", el.getExclusiveCode3());
        html=StringUtil.strReplace(html, "{$$exclusiveCode4$$}", el.getExclusiveCode4());
        return html;
    }
}
