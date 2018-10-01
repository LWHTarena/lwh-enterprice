package com.lwhtarena.company.web.common;

import com.lwhtarena.company.analyze.util.AnalyzeUtil;
import com.lwhtarena.company.analyze.vo.DataShowParams;
import com.lwhtarena.company.analyze.vo.FindedDataAnalyzeResult;
import com.lwhtarena.company.hql.entities.Rs;
import com.lwhtarena.company.sys.obj.DatetimeSpanSuffixTxt;
import com.lwhtarena.company.sys.util.FileUtil;
import com.lwhtarena.company.sys.util.HttpUtil;
import com.lwhtarena.company.sys.util.StringUtil;
import com.lwhtarena.company.sys.util.TimeUtil;
import com.lwhtarena.company.web.entities.*;
import com.lwhtarena.company.web.portal.obj.ArticleGroupNearby;
import com.lwhtarena.company.web.portal.obj.EnvirSet;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.List;

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
public class TempletUtil {

    /*
     * 根据tag字符串得到当前模板
     */
    public static TempletPortalSubElement elInitByTagStr(TempletPortalMain templetMain, String tagStr) {
        TempletPortalSubElement el;
        if (tagStr==null || tagStr.trim().equals("") || tagStr.trim().equals("public")) {
            el = templetMain.getElPublic();
        }else if (tagStr.trim().equals("index")) {
            el = templetMain.getElIndex();
        }else if (tagStr.trim().equals("nav")) {
            el = templetMain.getElNav();
        }else if (tagStr.trim().equals("art") || tagStr.trim().equals("article") || tagStr.trim().equals("artshow")) {
            el = templetMain.getElArt();
        }else if (tagStr.trim().equals("corpus")) {
            el = templetMain.getElCorpus();
        }else if (tagStr.trim().equals("edit")) {
            el = templetMain.getElEdit();
        }else if (tagStr.trim().equals("extend")) {
            el = templetMain.getElExtend();
        }else {
            el = templetMain.getElPublic();
        }
        return el;
    }

    // 返回一个el，如果当前el中的某属性为空，则赋值为上一层public中对应的属性值
    public static TempletPortalSubElement elInit(TempletPortalSubElement subpub, TempletPortalSubElement subel) {
        TempletPortalSubElement el = new TempletPortalSubElement();
        if (subel.getBorderCode() != null && !subel.getBorderCode().trim().equals("")) {
            el.setBorderCode(subel.getBorderCode());
        } else {
            el.setBorderCode(subpub.getBorderCode());
        }
        if (subel.getMainCode() != null && !subel.getMainCode().trim().equals("")) {
            el.setMainCode(subel.getMainCode());
        } else {
            el.setMainCode(subpub.getMainCode());
        }
        if (subel.getCssCode() != null && !subel.getCssCode().trim().equals("")) {
            el.setCssCode(subel.getCssCode());
        } else {
            el.setCssCode(subpub.getCssCode());
        }
        if (subel.getTopCode() != null && !subel.getTopCode().trim().equals("")) {
            el.setTopCode(subel.getTopCode());
        } else {
            el.setTopCode(subpub.getTopCode());
        }
        if (subel.getFooterCode() != null && !subel.getFooterCode().trim().equals("")) {
            el.setFooterCode(subel.getFooterCode());
        } else {
            el.setFooterCode(subpub.getFooterCode());
        }
        if (subel.getTargetStr() != null && !subel.getTargetStr().trim().equals("")) {
            el.setTargetStr(subel.getTargetStr());
        } else {
            el.setTargetStr(subpub.getTargetStr());
        }
        if (subel.getTitleFormat() != null && !subel.getTitleFormat().trim().equals("")) {
            el.setTitleFormat(subel.getTitleFormat());
        } else {
            el.setTitleFormat(subpub.getTitleFormat());
        }
        if (subel.getFunctionAreaCode() != null && !subel.getFunctionAreaCode().trim().equals("")) {
            el.setFunctionAreaCode(subel.getFunctionAreaCode());
        } else {
            el.setFunctionAreaCode(subpub.getFunctionAreaCode());
        }

        if (subel.getSearchAreaCode() != null && !subel.getSearchAreaCode().trim().equals("")) {
            el.setSearchAreaCode(subel.getSearchAreaCode());
        } else {
            el.setSearchAreaCode(subpub.getSearchAreaCode());
        }
        if (subel.getMajorLoopCodeInLump() != null && !subel.getMajorLoopCodeInLump().trim().equals("")) {
            el.setMajorLoopCodeInLump(subel.getMajorLoopCodeInLump());
        } else {
            el.setMajorLoopCodeInLump(subpub.getMajorLoopCodeInLump());
        }
        if (subel.getMinorLoopCodeInLump() != null && !subel.getMinorLoopCodeInLump().trim().equals("")) {
            el.setMinorLoopCodeInLump(subel.getMinorLoopCodeInLump());
        } else {
            el.setMinorLoopCodeInLump(subpub.getMinorLoopCodeInLump());
        }
        if (subel.getExclusiveCode1() != null && !subel.getExclusiveCode1().trim().equals("")) {
            el.setExclusiveCode1(subel.getExclusiveCode1());
        } else {
            el.setExclusiveCode1(subpub.getExclusiveCode1());
        }
        if (subel.getExclusiveCode2() != null && !subel.getExclusiveCode2().trim().equals("")) {
            el.setExclusiveCode2(subel.getExclusiveCode2());
        } else {
            el.setExclusiveCode2(subpub.getExclusiveCode2());
        }
        if (subel.getExclusiveCode3() != null && !subel.getExclusiveCode3().trim().equals("")) {
            el.setExclusiveCode3(subel.getExclusiveCode3());
        } else {
            el.setExclusiveCode3(subpub.getExclusiveCode3());
        }
        if (subel.getExclusiveCode4() != null && !subel.getExclusiveCode4().trim().equals("")) {
            el.setExclusiveCode4(subel.getExclusiveCode4());
        } else {
            el.setExclusiveCode4(subpub.getExclusiveCode4());
        }
        return el;
    }

    // 通用模块代码替换
    public static String mainCodeReplace(String html, TempletPortalMain template) {
        html = AnalyzeUtil.replace(html, "code", "public1", template.getPublicCode1());
        html = AnalyzeUtil.replace(html, "code", "public2", template.getPublicCode2());
        html = AnalyzeUtil.replace(html, "code", "public3", template.getPublicCode3());
        html = AnalyzeUtil.replace(html, "code", "public4", template.getPublicCode4());
        html = AnalyzeUtil.replace(html, "code", "custom1", template.getCustomFormatCode1());
        html = AnalyzeUtil.replace(html, "code", "custom2", template.getCustomFormatCode2());
        html = AnalyzeUtil.replace(html, "code", "custom3", template.getCustomFormatCode3());
        html = AnalyzeUtil.replace(html, "code", "custom4", template.getCustomFormatCode4());
        html = AnalyzeUtil.replace(html, "code", "custom5", template.getCustomFormatCode5());
        html = AnalyzeUtil.replace(html, "code", "custom6", template.getCustomFormatCode6());
        html = AnalyzeUtil.replace(html, "code", "custom7", template.getCustomFormatCode7());
        html = AnalyzeUtil.replace(html, "code", "custom8", template.getCustomFormatCode8());
        return html;
    }

    // 当前子模块代码替换
    public static String elCodeReplace(String html, TempletPortalSubElement elTemplate) {
        html = AnalyzeUtil.replace(html, "code", "main", elTemplate.getMainCode());
        html = AnalyzeUtil.replace(html, "code", "top", elTemplate.getTopCode());
        html = AnalyzeUtil.replace(html, "code", "footer", elTemplate.getFooterCode());
        html = AnalyzeUtil.replace(html, "code", "css", elTemplate.getCssCode());
        html = AnalyzeUtil.replace(html, "code", "search", elTemplate.getSearchAreaCode());
        html = AnalyzeUtil.replace(html, "code", "function", elTemplate.getFunctionAreaCode());
        html = AnalyzeUtil.replace(html, "code", "exclusive1", elTemplate.getExclusiveCode1());
        html = AnalyzeUtil.replace(html, "code", "exclusive2", elTemplate.getExclusiveCode2());
        html = AnalyzeUtil.replace(html, "code", "exclusive3", elTemplate.getExclusiveCode3());
        html = AnalyzeUtil.replace(html, "code", "exclusive4", elTemplate.getExclusiveCode4());
        html = AnalyzeUtil.replace(html, "code", "loopMajor", elTemplate.getMajorLoopCodeInLump());
        html = AnalyzeUtil.replace(html, "code", "loopMinor", elTemplate.getMinorLoopCodeInLump());
        html = AnalyzeUtil.replace(html, "tag", "target", elTemplate.getTargetStr());
        html = AnalyzeUtil.replace(html, "tag", "htmlTitle", elTemplate.getTitleFormat());
        return html;
    }

    //得到逝去的时间说明
    public static String statTimesLosted(TempletPortalMain template,long datetime) {
        DatetimeSpanSuffixTxt dsst=sundriesTimesLostedTag(template);
        return TimeUtil.statTimesLosted(datetime,dsst);
    }

    /*
     * 获得逝去的时间标签库
     */
    public static DatetimeSpanSuffixTxt sundriesTimesLostedTag(TempletPortalMain template) {
        return TimeUtil.dsst(sundriesTag(template,"dsst"));
    }



    /*
     * 解析标签
     */
    public static String sundriesTag(TempletPortalMain template,
                                     String station) {
        String tagVal="";

        String tags=template.getSundriesTag();
        if (tags == null || tags.trim().equals("")) {
            return "";
        }
        if (tags.indexOf(station + ":") == -1) {
            return "";
        }
        String[] sArray = tags.split(",");
        String kv;
        String[] kvArray;
        for (int i = 0; i < sArray.length; i++) {
            kv = sArray[i];
            kvArray = kv.split(":");
            if (kvArray.length > 1 && kvArray[0].trim().equals(station)) {
                tagVal=escape(kvArray[1]);
            }

        }

        if (tagVal!=null && tagVal.trim().startsWith("code_with_")) {
            tagVal = tagVal.trim();
            String codewith=tagVal.substring(9, tagVal.length());
            if (codewith.trim().equalsIgnoreCase("_public_1")) {
                return template.getPublicCode1();
            }else if(codewith.trim().equalsIgnoreCase("_public_2")) {
                return template.getPublicCode2();
            }else if(codewith.trim().equalsIgnoreCase("_public_3")) {
                return template.getPublicCode3();
            }else if(codewith.trim().equalsIgnoreCase("_public_4")) {
                return template.getPublicCode4();
            }else if(codewith.trim().equalsIgnoreCase("_custom_1")) {
                return template.getCustomFormatCode1();
            }else if(codewith.trim().equalsIgnoreCase("_custom_2")) {
                return template.getCustomFormatCode2();
            }else if(codewith.trim().equalsIgnoreCase("_custom_3")) {
                return template.getCustomFormatCode3();
            }else if(codewith.trim().equalsIgnoreCase("_custom_4")) {
                return template.getCustomFormatCode4();
            }else if(codewith.trim().equalsIgnoreCase("_custom_5")) {
                return template.getCustomFormatCode5();
            }else if(codewith.trim().equalsIgnoreCase("_custom_6")) {
                return template.getCustomFormatCode6();
            }else if(codewith.trim().equalsIgnoreCase("_custom_7")) {
                return template.getCustomFormatCode7();
            }else if(codewith.trim().equalsIgnoreCase("_custom_8")) {
                return template.getCustomFormatCode8();
            }else if(codewith.trim().startsWith("_exclusive_")) {
                codewith=codewith.substring(10, codewith.length());
                TempletPortalSubElement el=null;
                int num=Integer.valueOf(codewith.substring(1, 2));
                String exc=codewith.substring(2, codewith.length());
                if (exc.trim().equalsIgnoreCase("_public")) {
                    el=template.getElPublic();
                }else if (exc.trim().equalsIgnoreCase("_index")) {
                    el=template.getElIndex();
                }else if (exc.trim().equalsIgnoreCase("_nav")) {
                    el=template.getElNav();
                }else if (exc.trim().equalsIgnoreCase("_art")) {
                    el=template.getElArt();
                }else if (exc.trim().equalsIgnoreCase("_corpus")) {
                    el=template.getElCorpus();
                }else if (exc.trim().equalsIgnoreCase("_edit")) {
                    el=template.getElEdit();
                }else if (exc.trim().equalsIgnoreCase("_extend")) {
                    el=template.getElExtend();
                }
                if (el!=null) {
                    switch (num) {
                        case 1:
                            return el.getExclusiveCode1();
                        case 2:
                            return el.getExclusiveCode2();
                        case 3:
                            return el.getExclusiveCode3();
                        case 4:
                            return el.getExclusiveCode4();
                    }
                }

            }
        }
        return tagVal;
    }


    /*
     * 解析标签，如果模板中没有，则从资源文件中获取
     */

    public static String sundriesTag(ResourceBundleMessageSource messageSource, TempletPortalMain template,
                                     String station) {
        String val=sundriesTag(template,station);
        if (val==null || val.trim().equals("")) {
            val = FileUtil.readRes(messageSource, station);
        }
        return val;
    }

    private static FindedDataAnalyzeResult lfSetup(FindedDataAnalyzeResult fdar, TempletPortalMain main,
                                                   TempletPortalSubElement el, boolean sub) {
        DataShowParams dsp = fdar.getDsp();
        String lf,fs;
        int fid;
        if (sub) {
            lf=dsp.getSubLoopFormatStr();
            fs = dsp.getSubFormatSource();
            fid=dsp.getSubFid();
        }else {
            fs = dsp.getFormatSource();
            lf=dsp.getLoopFormatStr();
            fid=dsp.getFid();
        }

        if (lf != null && lf.trim().equals("")) {
            lf=null;
        }

        if (fs != null && fs.trim().equals("")) { // 特例： 如果强制性规定格式化字符串和格式化来源均为空，返回null，应不处理子级
            fs = null;
        }

        //如果格式来源和格式字符串均为空
        if (!(fs==null && lf==null)) {
            /*
             * 详细处理
             */

            // 如果是模块
            if (fs.trim().equalsIgnoreCase("el")) {
                switch (fid) {
                    case 1:
                        lf=el.getMajorLoopCodeInLump();
                        break;
                    default:
                        lf=el.getMinorLoopCodeInLump();
                }

            }

            // 如果是模块
            if (fs.trim().equalsIgnoreCase("el_major")) {
                lf=el.getMajorLoopCodeInLump();
            }

            // 如果是模块
            if (fs.trim().equalsIgnoreCase("el_majinor")) {
                lf=el.getMinorLoopCodeInLump();
            }

            // 如果是exclusive
            if (fs.trim().equalsIgnoreCase("exclusive")) {
                switch (dsp.getFid()) {
                    case 2:
                        lf=el.getExclusiveCode2();
                        break;
                    case 3:
                        lf=el.getExclusiveCode3();
                        break;
                    case 4:
                        lf=el.getExclusiveCode4();
                        break;
                    default:
                        lf=el.getExclusiveCode1();
                }
            }

            // 如果是publicCode
            if (fs.trim().equalsIgnoreCase("public")) {
                switch (fid) {
                    case 2:
                        lf=main.getPublicCode2();
                        break;
                    case 3:
                        lf=main.getPublicCode3();
                        break;
                    case 4:
                        lf=main.getPublicCode4();
                        break;
                    default:
                        lf=main.getPublicCode1();
                }
            }

            // 如果是publicCode
            if (fs.trim().equalsIgnoreCase("custom")) {
                switch (fid) {
                    case 2:
                        lf=main.getCustomFormatCode2();
                        break;
                    case 3:
                        lf=main.getCustomFormatCode3();
                        break;
                    case 4:
                        lf=main.getCustomFormatCode4();
                        break;
                    case 5:
                        lf=main.getCustomFormatCode5();
                        break;
                    case 6:
                        lf=main.getCustomFormatCode6();
                        break;
                    case 7:
                        lf=main.getCustomFormatCode7();
                        break;
                    case 8:
                        lf=main.getCustomFormatCode8();
                        break;
                    default:
                        lf=main.getCustomFormatCode1();
                }

            }

            /*
             * 下面是提供的更加变态更加激进更加疯狂的几个标签 pubic_exclusive index_exclusive nav_exclusive
             * article_exclusive extend_exclusive search_exclusive artbody
             */

            // 如果是pubic_exclusive
            if (fs.trim().equalsIgnoreCase("pubic_exclusive")) {

                switch (fid) {
                    case 2:
                        lf=main.getElPublic().getExclusiveCode2();
                        break;
                    case 3:
                        lf=main.getElPublic().getExclusiveCode3();
                        break;
                    case 4:
                        lf=main.getElPublic().getExclusiveCode4();
                        break;
                    default:
                        lf=main.getElPublic().getExclusiveCode1();
                }
            }

            // 如果是index_exclusive
            if (fs.trim().equalsIgnoreCase("index_exclusive")) {

                switch (fid) {
                    case 2:
                        lf=main.getElIndex().getExclusiveCode2();
                        break;
                    case 3:
                        lf=main.getElIndex().getExclusiveCode3();
                        break;
                    case 4:
                        lf=main.getElIndex().getExclusiveCode4();
                        break;
                    default:
                        lf=main.getElIndex().getExclusiveCode1();
                }
            }

            // 如果是nav_exclusive
            if (fs.trim().equalsIgnoreCase("nav_exclusive")) {

                switch (fid) {
                    case 2:
                        lf=main.getElNav().getExclusiveCode2();
                        break;
                    case 3:
                        lf=main.getElNav().getExclusiveCode3();
                        break;
                    case 4:
                        lf=main.getElNav().getExclusiveCode4();
                        break;
                    default:
                        lf=main.getElNav().getExclusiveCode1();
                }
            }

            // 如果是article_exclusive
            if (fs.trim().equalsIgnoreCase("article_exclusive")) {

                switch (fid) {
                    case 2:
                        lf=main.getElArt().getExclusiveCode2();
                        break;
                    case 3:
                        lf=main.getElArt().getExclusiveCode3();
                        break;
                    case 4:
                        lf=main.getElArt().getExclusiveCode4();
                        break;
                    default:
                        lf=main.getElArt().getExclusiveCode1();
                }
            }

            // 如果是extend_exclusive
            if (fs.trim().equalsIgnoreCase("extend_exclusive")) {

                switch (fid) {
                    case 2:
                        lf=main.getElExtend().getExclusiveCode2();
                        break;
                    case 3:
                        lf=main.getElExtend().getExclusiveCode3();
                        break;
                    case 4:
                        lf=main.getElExtend().getExclusiveCode4();
                        break;
                    default:
                        lf=main.getElExtend().getExclusiveCode1();
                }
            }

            // 如果是corpus_exclusive
            if (fs.trim().equalsIgnoreCase("corpus_exclusive")) {

                switch (fid) {
                    case 2:
                        lf=main.getElCorpus().getExclusiveCode2();
                        break;
                    case 3:
                        lf=main.getElCorpus().getExclusiveCode3();
                        break;
                    case 4:
                        lf=main.getElCorpus().getExclusiveCode4();
                        break;
                    default:
                        lf=main.getElCorpus().getExclusiveCode1();
                }
            }

            if (fs.trim().equalsIgnoreCase("edit_exclusive")) {

                switch (fid) {
                    case 2:
                        lf=main.getElEdit().getExclusiveCode2();
                        break;
                    case 3:
                        lf=main.getElEdit().getExclusiveCode3();
                        break;
                    case 4:
                        lf=main.getElEdit().getExclusiveCode4();
                        break;
                    default:
                        lf=main.getElEdit().getExclusiveCode1();
                }
            }
        }

        // 如果未指定格式源或格式源不是上述几种，则失败

        if (sub) {
            dsp.setSubLoopFormatStr(lf);
        }else {
            dsp.setLoopFormatStr(lf);
        }

        fdar.setDsp(dsp);
        return fdar;

    }

    /*
     * 初始化标签返回对象，主要是给格式化字符串进行检查和赋值
     */
    public static FindedDataAnalyzeResult fmt(FindedDataAnalyzeResult fdar, TempletPortalMain main,
                                              TempletPortalSubElement el) {

        fdar = lfSetup(fdar, main, el, false);		//处理主格式化字符串
        if (fdar.getDsp()!=null && fdar.getDsp().getLoopFormatStr()==null) {
            return null;
        }
        fdar = lfSetup(fdar, main, el, true);		//处理子层

        return fdar;
    }


    public static String tagToNavs(String html, EnvirSet es, FindedDataAnalyzeResult fdar) {
        TempletPortalMain template=es.getTempletPortalMainDaoImpl().findDef();
        DataShowParams dsp = fdar.getDsp();
        boolean gather=false;

        String lf = dsp.getLoopFormatStr();
        String tmp, tmpAll = "";
        String imgHtmlTemplet=TempletUtil.sundriesTag(template, "imgHtmlTemplet");
        boolean asc;
        if (dsp.getOrder() == 0) {
            asc = false;
        } else {
            asc = true;
        }
        String indexFile=es.getMessageSource().getMessage("file.html.default", null, "index.html", null);
        String htmlRoot=es.getMessageSource().getMessage("group.file.static.root", null, "html", null);
        htmlRoot=es.getRequest().getContextPath()+"/"+htmlRoot;

        if (dsp.getDataSource().equalsIgnoreCase("articles_from_navs_sub")) {		//在本方法内同步处理指定顺序号的栏目数据
            List<ArticleGroup> aglist=es.getGroupDaoImpl().queryByParentID(es.getGid(), false,gather,1);
            String majLoopHtml,tmpSubHtmlAll="";

            if (aglist!=null && !aglist.isEmpty() && aglist.size()>0) {

                int n=0;
                for (ArticleGroup ag:aglist) {			//组循环
                    n++;
                    majLoopHtml=template.getElNav().getMajorLoopCodeInLump();
                    majLoopHtml=GroupUtil.folderUrl(majLoopHtml, ag,htmlRoot,indexFile);
                    if (majLoopHtml!=null) {
                        majLoopHtml=GroupUtil.fmt(majLoopHtml, ag);

                        //文章循环
                        if (lf==null || lf.trim().equals("")) {
                            lf=template.getElNav().getMinorLoopCodeInLump();
                        }

                        if (lf!=null) {

                            Rs rs = es.getArticleDaoImpl().queryByGid(ag.getId(),dsp.getFirstResult(), dsp.getCurpage(), dsp.getPagesize(), asc,1,dsp.getImg());
                            int step = 0;
                            int curStep;
                            tmpAll="";
                            for (Object obj : rs.getList()) {
                                step++;
                                Article article = (Article) obj;
                                tmp = lf;

                                tmp = ArticleUtil.fmt(tmp, article,imgHtmlTemplet,es.getRequest(),dsp.getTitleLen());
                                curStep = ((rs.getPage() - 1) * rs.getPageSize()) + step;
                                tmp = AnalyzeUtil.replace(tmp, "tag", "sn", "" + curStep);
                                tmp = AnalyzeUtil.replace(tmp, "tag", "ispassed", "");
                                tmpAll += tmp;
                            }

                            majLoopHtml=AnalyzeUtil.replace(majLoopHtml, "data", "arts", tmpAll);
                        }

                        if (html.indexOf("articles_from_navs_sub_"+n)!=-1) {	//处理指定栏目顺序号的数据
                            html = AnalyzeUtil.replace(html, "data", "articles_from_navs_sub_"+n, majLoopHtml);
                        }else {
                            /*
                             * 处理后，总数据表中将没有上面指定的数据
                             */
                            tmpSubHtmlAll += majLoopHtml;
                        }
                    }
                }

                html = StringUtil.strReplace(html, fdar.getWholeTag(), tmpSubHtmlAll);
            }else{
                html = AnalyzeUtil.replaceElong(html, "data", "articles_from_navs_sub_", "");
                html = StringUtil.strReplace(html, fdar.getWholeTag(), "");
            }

        }else if (dsp.getDataSource().equalsIgnoreCase("articles_from_nav_curr")) {

            String noPassedTag=TempletUtil.sundriesTag(template, "nopass");

            //检测是否本人登录或是否管理员
            boolean admin=false;
            String loginUidStr=HttpUtil.getCookie(es.getMessageSource(), es.getRequest(), "uid_lerx");
            long loginUid;
            if (loginUidStr!=null  && StringUtil.isNumber(loginUidStr)){
                loginUid = Long.valueOf(loginUidStr);
                User loginu=es.getUserDaoImpl().findByID(loginUid);
                //如果是管理员
                if (UserUtil.isadmin(loginu)) {
                    admin=true;
                }
                //如果所属用户组有管理权限
                if (!admin && GroupUtil.auditMaskChk(es.getGroupDaoImpl().findByID(dsp.getGid()), loginu.getRole().getMask())) {
                    admin=true;
                }
            }else{
                loginUid = 0;
            }

            int powerstatus;
            if (admin && dsp.getPermit()==1) {	//如果是管理员
                powerstatus=0;
            }else {
                powerstatus=1;
            }
            Rs rs = es.getArticleDaoImpl().queryByGid(dsp.getGid(),dsp.getFirstResult(), dsp.getCurpage(), dsp.getPagesize(), asc,powerstatus,dsp.getImg());
            int step = 0;
            int curStep;
            tmpAll="";
            for (Object obj : rs.getList()) {
                step++;
                Article article = (Article) obj;
                tmp = lf;
                tmp = ArticleUtil.fmt(tmp, article,imgHtmlTemplet,es.getRequest(),dsp.getTitleLen());
                curStep = ((rs.getPage() - 1) * rs.getPageSize()) + step;
                tmp = AnalyzeUtil.replace(tmp, "tag", "sn", "" + curStep);
                if (article.isStatus()) {
                    tmp = AnalyzeUtil.replace(tmp, "tag", "ispassed", "");
                }else {
                    tmp = AnalyzeUtil.replace(tmp, "tag", "ispassed", noPassedTag);
                }
                tmpAll += tmp;
            }

            html = AnalyzeUtil.replace(html, "tag", "dataCount", ""+rs.getCount());
            html = AnalyzeUtil.replace(html, "tag", "pageSize", ""+rs.getPageSize());
            html = AnalyzeUtil.replace(html, "tag", "pageCurr", ""+rs.getPage());
            html = AnalyzeUtil.replace(html, "tag", "pageCount", ""+rs.getPageCount());
            html = StringUtil.strReplace(html, fdar.getWholeTag(), tmpAll);
        }else if (dsp.getDataSource().equalsIgnoreCase("articlegroups_nearby")) {
            tmpAll="";
            String propTag;

            List<ArticleGroupNearby> listAgn=GroupUtil.subOrNearby(es.getGroupDaoImpl(), dsp.getGid(), dsp.getPagesize());
            if (listAgn!=null && listAgn.size()>0) {
                for (ArticleGroupNearby agn : listAgn) {
                    ArticleGroup ag = agn.getAgroup();
                    tmp = lf;
                    tmp=GroupUtil.fmt(tmp, ag);
                    tmp=GroupUtil.folderUrl(tmp, ag,htmlRoot,indexFile);
                    tmp = AnalyzeUtil.replace(tmp, "tag", "prop", "" + agn.getProp());
                    propTag=TempletUtil.sundriesTag(template, "propTag" + agn.getProp());
                    tmp = AnalyzeUtil.replace(tmp, "tag", "propTag", propTag);
                    tmpAll += tmp;
                }

            }
            html = StringUtil.strReplace(html, fdar.getWholeTag(), tmpAll);
        }

//		System.out.println("fdar.getWholeTag():"+fdar.getWholeTag());

        return html;

    }


    /*
     * 模板中数据标签解析
     */
    public static String tagToData(String html, EnvirSet es, FindedDataAnalyzeResult fdar) {

        TempletPortalMain template=es.getTempletPortalMainDaoImpl().findDef();
        String imgHtmlTemplet=TempletUtil.sundriesTag(template, "imgHtmlTemplet");
        DataShowParams dsp = fdar.getDsp();
        String lf = dsp.getLoopFormatStr();
        String tmp, tmpAll = "";
        String indexFile=es.getMessageSource().getMessage("file.html.default", null, "index.html", null);
        String htmlRoot=es.getMessageSource().getMessage("group.file.static.root", null, "html", null);
        htmlRoot=es.getRequest().getContextPath()+"/"+htmlRoot;
        boolean gather=false;

        boolean asc;
        if (dsp.getOrder() == 0) {
            asc = false;
        } else {
            asc = true;
        }
        if (fdar.getDsp().getCurpage()==0) {
            fdar.getDsp().setCurpage(es.getPage());
        }
        if (fdar.getDsp().getPagesize()==0) {
            fdar.getDsp().setPagesize(es.getPageSize());
        }
        //如果是文章
        if (dsp.getDataSource().equalsIgnoreCase("articles")) {
            if (dsp.getSingle() == 1) {
                if (dsp.getSingleID() > 0) {
                    Article article = es.getArticleDaoImpl().findByID(dsp.getSingleID());
                    lf = ArticleUtil.fmt(lf, article,imgHtmlTemplet,es.getRequest(),dsp.getTitleLen());
                } else {
                    Rs rs = es.getArticleDaoImpl().queryByGid(dsp.getGid(),dsp.getFirstResult(), 1, 1, false,1,dsp.getImg());
                    Article article = (Article) rs.getList().get(0);
                    lf = ArticleUtil.fmt(lf, article,imgHtmlTemplet,es.getRequest(),dsp.getTitleLen());
                    html = AnalyzeUtil.replace(html, "tag", "dataCount", ""+rs.getCount());
                    html = AnalyzeUtil.replace(html, "tag", "pageCurr", ""+rs.getPage());
                    html = AnalyzeUtil.replace(html, "tag", "pageSize", ""+rs.getPageSize());

                }
                html = StringUtil.strReplace(html, fdar.getWholeTag(), lf);

            } else {
                /*
                 * 下面检则spare1标记和子循环格式字符串
                 */
                int curStep,step = 0,step2;
                String tmp2;
                if (dsp.getSpare1()==1 && fdar.getDsp().getSubLoopFormatStr()!=null && !fdar.getDsp().getSubLoopFormatStr().trim().equals("")) {		//如果是自动首页全部栏目文章
                    List<ArticleGroup> agroupList=es.getGroupDaoImpl().queryGatherByParentID(dsp.getGid());
                    String tmpAll2;
                    for (ArticleGroup agroup:agroupList) {
                        step++;
                        tmp=fdar.getDsp().getLoopFormatStr();
                        tmp=GroupUtil.fmt(tmp, agroup);
                        tmp = GroupUtil.folderUrl(tmp, agroup, htmlRoot,indexFile);
                        tmp = AnalyzeUtil.replace(tmp, "tag", "nsn", "" + step);
                        Rs rs = es.getArticleDaoImpl().queryByGid(agroup.getId(),dsp.getFirstResult(), dsp.getCurpage(), dsp.getPagesize(), asc,1,dsp.getImg());

                        step2=0;
                        tmpAll2="";
                        for (Object obj : rs.getList()) {
                            step2++;

                            Article article = (Article) obj;
                            tmp2 = fdar.getDsp().getSubLoopFormatStr();
                            tmp2 = ArticleUtil.fmt(tmp2, article,imgHtmlTemplet,es.getRequest(),dsp.getTitleLen());
                            curStep = ((rs.getPage() - 1) * rs.getPageSize()) + step2;
                            tmp2 = AnalyzeUtil.replace(tmp2, "tag", "sn", "" + curStep);
                            tmpAll2 += tmp2;
                        }
                        tmp = AnalyzeUtil.replace(tmp, "tag", "articles", tmpAll2);
                        tmpAll+=tmp;

                    }

                    html = StringUtil.strReplace(html, fdar.getWholeTag(), tmpAll);
                }else {							//按gid或uid取文章
                    Rs rs;
                    //检测是否本人登录或是否管理员
                    boolean admin=false;
                    String loginUidStr=HttpUtil.getCookie(es.getMessageSource(), es.getRequest(), "uid_lerx");
                    long loginUid;
                    if (loginUidStr!=null  && StringUtil.isNumber(loginUidStr)){
                        loginUid = Long.valueOf(loginUidStr);
                        User loginu=es.getUserDaoImpl().findByID(loginUid);
                        //如果是管理员
                        if (UserUtil.isadmin(loginu)) {
                            admin=true;
                        }
                        //如果所属用户组有管理权限
                        if (!admin && GroupUtil.auditMaskChk(es.getGroupDaoImpl().findByID(dsp.getGid()), loginu.getRole().getMask())) {
                            admin=true;
                        }
                    }else{
                        loginUid = 0;
                    }

                    if (dsp.getPersonal()==1) {		//如果有personal标记，则按uid和gid取文章
                        int ownstatus;
                        if (loginUid - es.getUid() == 0 || admin) {		//如果是作者本人或者管理员
                            ownstatus=0;
                        }else {
                            ownstatus=1;
                        }

                        //下面只能用es.uid  指目标uid
                        rs = es.getArticleDaoImpl().queryByUid(es.getUid(),dsp.getGid(),dsp.getFirstResult(), dsp.getCurpage(), dsp.getPagesize(), asc,ownstatus);
                        html = AnalyzeUtil.replace(html, "tag", "dataCount", ""+rs.getCount());
                        html = AnalyzeUtil.replace(html, "tag", "pageCurr", ""+rs.getPage());
                        html = AnalyzeUtil.replace(html, "tag", "pageSize", ""+rs.getPageSize());
                    }else {						//如果无uid标记，则按gid取文章

                        int powerstatus;
                        if (admin && dsp.getPermit()==1) {	//如果是管理员
                            powerstatus=0;
                        }else {
                            powerstatus=1;
                        }
                        rs = es.getArticleDaoImpl().queryByGid(dsp.getGid(),dsp.getFirstResult(), dsp.getCurpage(), dsp.getPagesize(), asc,powerstatus,dsp.getImg());

                    }
                    String isPassedTag=TempletUtil.sundriesTag(template, "passed");
                    String noPassedTag=TempletUtil.sundriesTag(template, "nopass");
                    for (Object obj : rs.getList()) {
                        step++;
                        Article article = (Article) obj;
                        tmp = lf;
                        tmp = ArticleUtil.fmt(tmp, article,imgHtmlTemplet,es.getRequest(),dsp.getTitleLen());
                        if (article.isStatus()) {
                            tmp = AnalyzeUtil.replace(tmp, "tag", "ispassed", isPassedTag);
                        }else {
                            tmp = AnalyzeUtil.replace(tmp, "tag", "ispassed", noPassedTag);
                        }
                        curStep = ((rs.getPage() - 1) * rs.getPageSize()) + step;
                        tmp = AnalyzeUtil.replace(tmp, "tag", "sn", "" + curStep);

                        tmpAll += tmp;
                    }

                    html = AnalyzeUtil.replace(html, "tag", "recCount", "" + rs.getCount());
                    html = AnalyzeUtil.replace(html, "tag", "pageCount", "" + rs.getPageCount());
                    html = StringUtil.strReplace(html, fdar.getWholeTag(), tmpAll);
                }

            }

            return html;
        }else if (dsp.getDataSource().equalsIgnoreCase("navs")) {
            if (fdar.getDsp().getSubLoopFormatStr()!=null && ! fdar.getDsp().getSubLoopFormatStr().trim().equals("")) {
                gather=true;
            }
            List<ArticleGroup> list=es.getGroupDaoImpl().queryByParentID(dsp.getGid(), false,gather,1);
            int step = 0;
			/*String indexFile=es.getMessageSource().getMessage("file.html.default", null, "index.html", null);
			String htmlRoot=es.getMessageSource().getMessage("group.file.static.root", null, "html", null);
			htmlRoot=es.getRequest().getContextPath()+"/"+htmlRoot;*/
            for (Object obj : list) {
                step++;
                ArticleGroup agroup = (ArticleGroup) obj;
                tmp = lf;
                tmp = GroupUtil.fmt(tmp, agroup);
                tmp = GroupUtil.folderUrl(tmp, agroup, htmlRoot,indexFile);
                tmp = AnalyzeUtil.replace(tmp, "tag", "sn", "" + step);
                if (fdar.getDsp().getSubLoopFormatStr()!=null && ! fdar.getDsp().getSubLoopFormatStr().trim().equals("")) {
                    String tmp2,tmpAll2="";
                    int step2 = 0;
                    List<ArticleGroup> listsub=es.getGroupDaoImpl().queryByParentID(agroup.getId(), false,gather,1);
                    for (Object obj2 : listsub) {
                        step2++;
                        ArticleGroup agroup2 = (ArticleGroup) obj2;
                        tmp2 = fdar.getDsp().getSubLoopFormatStr();
                        tmp2 = GroupUtil.fmt(tmp2, agroup2);
                        tmp2 = GroupUtil.folderUrl(tmp2, agroup2, htmlRoot,indexFile);
                        tmp2 = AnalyzeUtil.replace(tmp2, "tag", "sn", "" + step2);
                        tmpAll2 += tmp2;
                    }
                    tmp = AnalyzeUtil.replace(tmp, "tag", "subNavs", "" + tmpAll2);
                }

                tmpAll += tmp;
            }
            html = StringUtil.strReplace(html, fdar.getWholeTag(), tmpAll);
        }

        /*
         * 以后再增加
         *
         */

        return html;

    }


    public static String tagToEnvir(String html, EnvirSet is) {

        if (html==null || html.trim().equals("") || is==null) {
            return html;
        }



        html = AnalyzeUtil.replace(html, "tag", "contextPath", is.getRequest().getContextPath());

        html = AnalyzeUtil.replace(html, "tag", "currTime", ""+System.currentTimeMillis());
        html = AnalyzeUtil.replace(html, "tag", "appName", is.getMessageSource().getMessage("app.name", null, "CMS", null));
        html = AnalyzeUtil.replace(html, "tag", "lerxVersionNumber", is.getMessageSource().getMessage("version.Number", null, "1.0", null));
        html = AnalyzeUtil.replace(html, "tag", "lerxVersionBuild", is.getMessageSource().getMessage("version.Build", null, "20180101", null));
        html = AnalyzeUtil.replace(html, "tag", "date", TimeUtil.coverLongToStr(System.currentTimeMillis(), "yyyy-MM-dd"));
        html = AnalyzeUtil.replace(html, "tag", "year", TimeUtil.coverLongToStr(System.currentTimeMillis(), "yyyy"));
        Portal portal = is.getPortalDaoImpl().query();

        TempletPortalMain template = is.getTempletPortalMainDaoImpl().findDef();
        if (template!=null && template.getResFolder()!=null && !template.getResFolder().trim().equals("")) {
            html = AnalyzeUtil.replace(html, "tag", "resFolder", template.getResFolder());
        }

        html = PortalUtil.fmt(html, portal);
        //页面执行时间
        if (is.getStartTime()>0L) {
            html = AnalyzeUtil.replace(html, "tag", "executeTimes", ""+(System.currentTimeMillis()-is.getStartTime()));

        }else {
            html = AnalyzeUtil.replace(html, "tag", "executeTimes", "");

        }

        return html;
    }


    public static TempletPortalMain copy(TempletPortalMain oldTemplate,TempletPortalMain newTemplate){
        newTemplate.setName(oldTemplate.getName());
        newTemplate.setAuthor(oldTemplate.getAuthor());
        newTemplate.setResFolder(oldTemplate.getResFolder());
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
        newTemplate.setSundriesTag(oldTemplate.getSundriesTag());
        newTemplate.setOrderNum(0);
        newTemplate.setPublicCode1(oldTemplate.getPublicCode1());
        newTemplate.setPublicCode2(oldTemplate.getPublicCode2());
        newTemplate.setPublicCode3(oldTemplate.getPublicCode3());
        newTemplate.setPublicCode4(oldTemplate.getPublicCode4());
        newTemplate.setState(true);
        newTemplate.setElArt(copy(oldTemplate.getElArt()));
        newTemplate.setElExtend(copy(oldTemplate.getElExtend()));
        newTemplate.setElIndex(copy(oldTemplate.getElIndex()));
        newTemplate.setElNav(copy(oldTemplate.getElNav()));
        newTemplate.setElPublic(copy(oldTemplate.getElPublic()));
        newTemplate.setElCorpus(copy(oldTemplate.getElCorpus()));
        newTemplate.setElEdit(copy(oldTemplate.getElEdit()));
        newTemplate.setElComment(copy(oldTemplate.getElComment()));
        return newTemplate;

    }

    public static TempletPortalMain copy(TempletPortalMain oldTemplate,String newTitle){
        TempletPortalMain newTemplate=new TempletPortalMain();
        newTemplate.setAuthor(oldTemplate.getAuthor());
        newTemplate.setResFolder(oldTemplate.getResFolder());
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
        newTemplate.setSundriesTag(oldTemplate.getSundriesTag());
        newTemplate.setOrderNum(0);
        newTemplate.setPublicCode1(oldTemplate.getPublicCode1());
        newTemplate.setPublicCode2(oldTemplate.getPublicCode2());
        newTemplate.setPublicCode3(oldTemplate.getPublicCode3());
        newTemplate.setPublicCode4(oldTemplate.getPublicCode4());
        newTemplate.setState(oldTemplate.isState());
        newTemplate.setName(newTitle);
        newTemplate.setElArt(copy(oldTemplate.getElArt()));
        newTemplate.setElExtend(copy(oldTemplate.getElExtend()));
        newTemplate.setElIndex(copy(oldTemplate.getElIndex()));
        newTemplate.setElNav(copy(oldTemplate.getElNav()));
        newTemplate.setElPublic(copy(oldTemplate.getElPublic()));
        newTemplate.setElCorpus(copy(oldTemplate.getElCorpus()));
        newTemplate.setElEdit(copy(oldTemplate.getElEdit()));
        newTemplate.setElComment(copy(oldTemplate.getElComment()));
        return newTemplate;

    }

    private static TempletComment copy(TempletComment oldEl){
        TempletComment el=new TempletComment();
        el.setActDelCode(oldEl.getActDelCode());
        el.setActPassCode(oldEl.getActPassCode());
        el.setActReplyCode(oldEl.getActReplyCode());
        el.setFormCode(oldEl.getFormCode());
        el.setItemLoopCode(oldEl.getItemLoopCode());
        return el;
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

    public static String escape(String str) {
        str=StringUtil.strReplace(str, "\\58", ":");
        str=StringUtil.strReplace(str, "\\44", ",");
        return str;
    }
}
