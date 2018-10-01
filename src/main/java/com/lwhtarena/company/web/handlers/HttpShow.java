package com.lwhtarena.company.web.handlers;

import com.lwhtarena.company.analyze.util.AnalyzeUtil;
import com.lwhtarena.company.analyze.vo.FindedDataAnalyzeResult;
import com.lwhtarena.company.hql.entities.Rs;
import com.lwhtarena.company.ip.util.IPUtil;
import com.lwhtarena.company.login.util.LoginUtil;
import com.lwhtarena.company.sys.obj.DatetimeSpanSuffixTxt;
import com.lwhtarena.company.sys.util.FileUtil;
import com.lwhtarena.company.sys.util.HttpUtil;
import com.lwhtarena.company.sys.util.StringUtil;
import com.lwhtarena.company.sys.util.TimeUtil;
import com.lwhtarena.company.web.common.*;
import com.lwhtarena.company.web.dao.*;
import com.lwhtarena.company.web.entities.*;
import com.lwhtarena.company.web.portal.obj.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Author：liwh
 * @Description:
 * @Date 12:31 2018/8/12
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */

@RequestMapping("/action_show")
@Controller
public class HttpShow {

    @Autowired
    private ITemplatePortalMainDao templetePortalMainDaoImpl;

    @Autowired
    private ResourceBundleMessageSource messageSource;

    @Autowired
    private IPortalDao portalDaoImpl;

    @Autowired
    private IArticleDao articleDaoImpl;

    @Autowired
    private IGroupDao groupDaoImpl;

    @Autowired
    private IUserDao userDaoImpl;

    @Autowired
    private IUserArtsCountDao userArtsCountDaoImp;

    @Autowired
    private IRoleDao roleDaoImpl;

    @Autowired
    private IVisitorIPRecordDao visitorIPRecordDaoImpl;

    @Autowired
    private IVisitorsBookDao visitorsBookDaoImpl;

    @Autowired
    private IVisitArchivesDao visitArchivesDaoImpl;

    @Autowired
    private IVisitorCountInPeriodDao visitorCountInPeriodDaoImpl;

    @Autowired
    private IHtmlFileStaticDao htmlFileStaticDaoImpl;

    @Autowired
    private ICommentBridgeDao commentBridgeDaoImpl;

    @Autowired
    private ICommentThreadDao commentThreadDaoImpl;

    private EnvirSet envirInit(long gid, long aid, HttpServletRequest request, HttpServletResponse response) {
        EnvirSet es = new EnvirSet();
        es.setStartTime(System.currentTimeMillis());
        if (request == null) {
            es.setRequest(HttpUtil.currRequest());
        } else {
            es.setRequest(request);
        }
        if (response == null) {
            es.setResponse(HttpUtil.currResponse());
        } else {
            es.setResponse(response);
        }
        es.setGid(gid);
        es.setAid(aid);
        es.setMessageSource(messageSource);
        es.setPortalDaoImpl(portalDaoImpl);
        es.setGroupDaoImpl(groupDaoImpl);
        es.setTempletPortalMainDaoImpl(templetePortalMainDaoImpl);
        es.setUserDaoImpl(userDaoImpl);
        es.setUserArtsCountDaoImp(userArtsCountDaoImp);
        es.setArticleDaoImpl(articleDaoImpl);
        es.setRoleDaoImpl(roleDaoImpl);
        es.setVisitorIPRecordDaoImpl(visitorIPRecordDaoImpl);
        es.setVisitorsBookDaoImpl(visitorsBookDaoImpl);
        es.setVisitArchivesDaoImpl(visitArchivesDaoImpl);
        es.setVisitorCountInPeriodDaoImpl(visitorCountInPeriodDaoImpl);
        es.setHtmlFileStaticDaoImpl(htmlFileStaticDaoImpl);

        return es;
    }

    // 初始化html
    private String htmlInit(TempletPortalMain template, TempletPortalSubElement elTemplate) {
        String html;
        TempletPortalSubElement el = TempletUtil.elInit(template.getElPublic(), elTemplate);
        html = el.getBorderCode();

        if (html == null || html.trim().equals("")) {
            html = FileUtil.readRes(messageSource, "template_main_outnormal");
        }
        // 当前子模块代码标签处理
        html = TempletUtil.elCodeReplace(html, el);
        // 公共模块代码标签处理
        html = TempletUtil.mainCodeReplace(html, template);

        return html;
    }

    private static String nullTemplate(ResourceBundleMessageSource ms) {
        String error = ms.getMessage("err.template.notspecified", null,
                "The portal template for the specified output is not found at present!", null);
        String html = FileUtil.readRes(ms, "template_main_outnormal");
        html = AnalyzeUtil.replace(html, "code", "main", error);
        html = AnalyzeUtil.replace(html, "tag", "title", error);
        return html;
    }

    /*
     * 取当前年份
     */
    @ResponseBody
    @RequestMapping("/date")
    public String date(@RequestParam(value = "fmt", required = false) String fmt) {
        return TimeUtil.coverLongToStr(System.currentTimeMillis(), fmt);
    }

    /*
     *
     */

    @ResponseBody
    @RequestMapping("/addChk")
    public String artAddChk(Long gid, HttpServletRequest request, HttpServletResponse response) {
        if (gid == null) {
            gid = 0L;
        }
        EnvirSet es = envirInit(gid, 0, request, response);
        return UserUtil.maskChkForArtAdd(es);
    }

    // 返回统计信息psi
    @ResponseBody
    @RequestMapping("/portalStat")
    public PortalStatInfo portalStat() {
        PortalStatInfo psi = new PortalStatInfo();
        psi = articleDaoImpl.stat(psi);
        psi = userDaoImpl.stat(psi);
        Portal portal = portalDaoImpl.query();
        psi.setViews(portal.getVbook().getViewsTotal());
        psi.setIps(portal.getVbook().getIpTotal());
        // System.out.println(psi.toString());
        return psi;
    }

    // 返回当天或某天的访问记录数
    @ResponseBody
    @RequestMapping("/visitQuery")
    public VisitArchives visitQuery(long vid, int dayKey) {
        return VisitUtil.visitQuery(vid, dayKey, visitArchivesDaoImpl);
    }

    // 返回用户发布排行
    @ResponseBody
    @RequestMapping(value = "/rank/{mode}/{pagesize}")
    public List<UserArtsRanking> rank(@PathVariable(value = "mode", required = false) Integer mode, @PathVariable(value = "pagesize", required = false) Integer pagesize) {
        if (mode==null) {
            mode=0;
        }
        if (pagesize==null) {
            pagesize=10;
        }
        User user;
        Rs rs;
        int step=0;
        List<UserArtsRanking> list = new ArrayList<UserArtsRanking>();
        java.text.SimpleDateFormat formatter;
        int timeKey;
        switch (mode) {
            case 1:		//本年度排行
                formatter = new java.text.SimpleDateFormat(
                        "yyyy");
                timeKey = Integer.valueOf(formatter.format(System.currentTimeMillis()));
                rs = userArtsCountDaoImp.findTopByUKAndGroup(0, timeKey, 0, 1, pagesize);
                break;
            case 2:		//本季度排行
                timeKey = TimeUtil.quarter(System.currentTimeMillis());
                rs = userArtsCountDaoImp.findTopByUKAndGroup(0, timeKey, 0, 1, pagesize);
                break;
            case 3:		//本月
                formatter = new java.text.SimpleDateFormat(
                        "yyyyMM");
                timeKey = Integer.valueOf(formatter.format(System.currentTimeMillis()));
                rs = userArtsCountDaoImp.findTopByUKAndGroup(0, timeKey, 0, 1, pagesize);
                break;
            case 4:		//本周排行
                Date weekStamp=TimeUtil.firstDayAtWeek(new Date(System.currentTimeMillis()));
                formatter = new java.text.SimpleDateFormat(
                        "yyyyMMdd");
                timeKey = Integer.valueOf(formatter.format(weekStamp.getTime()));
                rs = userArtsCountDaoImp.findTopByUKAndGroup(0, timeKey, 0, 1, pagesize);
                break;
            default:		//文章总通过数
                rs=userDaoImpl.artsPassedRank(1, pagesize);

        }
        UserArtsCount uac;
        for (Object u:rs.getList()) {
            step++;
            UserArtsRanking uar=new UserArtsRanking();
            if (mode==0) {
                user=(User) u;
                uar.setNum(user.getArtsPassed());
            }else {
                uac=(UserArtsCount) u;
                user=uac.getUser();
                uar.setNum(uac.getArtsPassed());
            }

            uar.setUid(user.getId());
            uar.setUsername(user.getUsername());
            uar.setTrueName(user.getTruename());

            uar.setSn(step);
            list.add(uar);
        }
        return list;
    }

    /*
     * 用户面板
     */
    @ResponseBody
    @RequestMapping("/panel")
    public String panel(Long gid, HttpServletRequest request, HttpServletResponse response,
                        @CookieValue(value = "username_lerx", required = false) String username_lerx,
                        @CookieValue(value = "password_lerx", required = false) String password_lerx,
                        @RequestParam(value = "currEl", required = false) String currEl) {
        if (gid == null) {
            gid = 0L;
        }
        EnvirSet es = envirInit(gid, 0, request, response);
        long uid = UserUtil.loginRefresh(es, username_lerx, password_lerx);
        TempletPortalMain template = templetePortalMainDaoImpl.findDef();
        TempletPortalSubElement el = TempletUtil.elInitByTagStr(template, currEl);
        String panelCode;
        if (uid > 0L) {
            panelCode = el.getUserPanelCodeLogin();
            User user = userDaoImpl.findByID(uid);
            if (user != null) {
                String imgHtmlTemplet = TempletUtil.sundriesTag(messageSource,template, "htmlTemplet_img");
                panelCode = UserUtil.fmt(panelCode, user, imgHtmlTemplet);
            }

        } else {
            panelCode = el.getUserPanelCodeLogout();
            String token = HttpUtil.buildToken();
            if (panelCode == null) {
                panelCode = "";
            }
            panelCode = AnalyzeUtil.replace(panelCode, "tag", "token", token);
        }
        panelCode = TempletUtil.tagToEnvir(panelCode, es);
        return panelCode;

    }

    /*
     * 调查投票区域ajax
     */
    @ResponseBody
    @RequestMapping(value = "/poll/{id}")
    public String poll(@PathVariable(value = "id", required = true) Long id, @RequestParam(value = "titleLen", required = false) Integer titleLen,HttpServletRequest request,
                       HttpServletResponse response) {
        if (titleLen==null) {
            titleLen=0;
        }
        Article art = articleDaoImpl.findByID(id);
        TempletPortalMain template = templetePortalMainDaoImpl.findDef();
        String html = TempletUtil.sundriesTag(messageSource,template, "htmlTemplet_poll");
        String imgHtmlTemplet = TempletUtil.sundriesTag(messageSource,template, "htmlTemplet_img");

        if (art!=null && art.getAgroup()!=null && art.getPoll()!=null && art.getPoll().isStatus() && GroupUtil.poolchk(art.getAgroup())) {
            html = ArticleUtil.fmt(html, art, imgHtmlTemplet, request,0);
        } else {
            html = "";
        }

        html = AnalyzeUtil.replace(html, "tag", "contextPath", request.getContextPath());

        return html;
    }

    /*
     * 评论表单加载
     */

    @ResponseBody
    @RequestMapping(value = "/commform/{aid}")
    public String commForm(@PathVariable(value = "aid", required = true) Long aid,
                           @CookieValue(value = "uid_lerx", required = false) String uid_lerx, HttpServletRequest request,
                           HttpSession session, HttpServletResponse response) {
        Portal portal = portalDaoImpl.query();
        if (!portal.isComm()) {
            return "";
        }

        if (aid == null) {
            return "";
        }
        Article art = articleDaoImpl.findByID(aid);
        ArticleGroup ag=art.getAgroup();
        if (!ag.isComm()) {
            return "";
        }


        CommentBridge cb = art.getCb();

        cb = commentBridgeDaoImpl.findByID(cb.getId());

        if (!cb.isStatus()) {
            return "";
        }

        TempletPortalMain template = templetePortalMainDaoImpl.findDef();
        TempletComment templatec=template.getElComment();
        String html=templatec.getFormCode();

        html = AnalyzeUtil.replace(html, "tag", "bid", ""+cb.getId());
        html = AnalyzeUtil.replace(html, "tag", "aid", ""+aid);
        EnvirSet es;
        es = envirInit(ag.getId(), aid, request, null);
        html = TempletUtil.tagToEnvir(html, es);
        return html;

    }

    /*
     * 评论加载
     */

    @ResponseBody
    @RequestMapping(value = "/commlist/{id}")
    public CommOut commlist(@PathVariable(value = "id", required = true) Long id,
                            @RequestParam(value = "aid", required = false) Long aid,
                            @RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "pageSize", required = false) Integer pageSize,
                            @CookieValue(value = "uid_lerx", required = false) String uid_lerx, HttpServletRequest request,
                            HttpSession session, HttpServletResponse response) {
        CommOut co = new CommOut();
        co.setCount(0);
        co.setPage(1);
        co.setPageCount(0);
        co.setHtml("");
        co.setPageSize(pageSize);
        Portal portal = portalDaoImpl.query();
        if (!portal.isComm()) {
            return co;
        }

        CommentBridge cb = null;
        if (id == null) {
            return co;
        }
        cb = commentBridgeDaoImpl.findByID(id);

        if (cb == null || !cb.isStatus()) {
            return co;
        }

        if (aid==null) {
            return co;
        }else {
            Article art=articleDaoImpl.findByID(aid);

            if (art==null) {
                return co;
            }else {
                ArticleGroup ag=art.getAgroup();
                if (ag==null || !ag.isComm()) {
                    return co;
                }
            }
        }

        if (page == null) {
            page = 0;
        }

        if (pageSize == null) {
            pageSize = 10;
        }
        boolean deletedShow = false;
        User user = null;
        if (uid_lerx != null && !uid_lerx.trim().equals("") && StringUtil.isNumber(uid_lerx)) {
            long uid = Long.valueOf(uid_lerx);
            user = userDaoImpl.findByID(uid);
        }
        boolean master = false;
        /*
         * if ((user!=null && cb.getUser()!=null && (user.getId() - cb.getUser().getId()
         * == 0)) || LoginUtil.admin0Chk(messageSource, session)) { master=true;
         *
         * }
         */

        if (user != null && cb.getUser() != null) {
            if ((user.getId() - cb.getUser().getId() == 0)) {
                master = true;
            }
            if (UserUtil.isadmin(user)) {
                master = true;
                deletedShow = true;
            }
        }
        int showMode = 1;
        if (master) {
            showMode = 0;
        }

        Rs rs = commentThreadDaoImpl.queryByBid(cb.getId(), page, pageSize, false, showMode, deletedShow);
        TempletPortalMain template = templetePortalMainDaoImpl.findDef();
        TempletComment templatec=template.getElComment();
        String lf = TempletUtil.sundriesTag(messageSource,template, "htmlTemplet_comm_loop");
        String tmp, tmpAll = "";
        String imgHtmlTemplet = TempletUtil.sundriesTag(messageSource,template, "htmlTemplet_img");
        String funHtmlDel = templatec.getActDelCode();
        String funHtmlPass = templatec.getActPassCode();
        String replyAdd = templatec.getActReplyCode();
		/*String funHtmlDel = TempletUtil.sundriesTag(messageSource,template, "htmlTemplet_comm_loop_act_del");
		String funHtmlPass = TempletUtil.sundriesTag(messageSource,template, "htmlTemplet_comm_loop_act_pass");
		String replyAdd = TempletUtil.sundriesTag(messageSource,template, "htmlTemplet_comm_loop_reply_add");*/
        String anonymity = TempletUtil.sundriesTag(messageSource,template, "anonymity");
        String headDef = TempletUtil.sundriesTag(messageSource,template, "headDef");
        DatetimeSpanSuffixTxt dsst = TempletUtil.sundriesTimesLostedTag(template);
        TempletCommentFmtRequisite tcfr = new TempletCommentFmtRequisite();

        tcfr.setFunHtmlPass(funHtmlPass);
        tcfr.setImgHtmlTemplet(imgHtmlTemplet);
        tcfr.setReplyAdd(replyAdd);
        tcfr.setLf(lf);
        tcfr.setMaster(master);
        tcfr.setDsst(dsst);
        tcfr.setAnonymity(anonymity);
        tcfr.setHeadDef(headDef);
        tcfr.setFunHtmlDel(funHtmlDel);
        tcfr.setAvatarNull(ConfigUtil.getAvatarNullFile(messageSource, request));
        for (Object o : rs.getList()) {
            CommentThread ct = (CommentThread) o;
            tcfr.setLf(lf);
            tmp = CommUtil.fmt(tcfr, ct);
            tmpAll += tmp;
        }

        tmpAll = AnalyzeUtil.replace(tmpAll, "tag", "contextPath", request.getContextPath());
        co.setHtml(tmpAll);
        co.setPageCount(rs.getPageCount());
        co.setCount(rs.getCount());
        return co;

    }

    /*
     * 会员发布排行
     *
     */
    @ResponseBody
    @RequestMapping(value = "/rank")
    public String rank(@RequestParam(value = "pageSize", required = false) Integer pageSize, HttpServletRequest request,
                       HttpServletResponse response) {
        return "";
    }

    /*
     * 栏目热点文章 byclicks 指按点击量
     */
    @ResponseBody
    @RequestMapping(value = "/hot/{gid}")
    public String hot(@PathVariable(value = "gid", required = true) Long gid,
                      @RequestParam(value = "byclicks", required = false) Integer byclicks,
                      @RequestParam(value = "pageSize", required = false) Integer pageSize,
                      @RequestParam(value = "titleLen", required = false) Integer titleLen,
                      HttpServletRequest request,
                      HttpServletResponse response) {
        if (titleLen==null) {
            titleLen=0;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        TempletPortalMain template = templetePortalMainDaoImpl.findDef();
        String lf = TempletUtil.sundriesTag(messageSource,template, "htmlTemplet_hot");
        Rs rs;
        if (byclicks == null) {
            byclicks = 0;
        }
        if (byclicks == 1) {
            rs = articleDaoImpl.clicksByGid(gid, 0, 1, pageSize);
        } else {
            rs = articleDaoImpl.hotByGid(gid, 0, 1, pageSize);
        }

        String html = "", tmp;
        @SuppressWarnings("unchecked")
        List<Article> artList = (List<Article>) rs.getList();
        for (Article art : artList) {
            tmp = lf;
            tmp = ArticleUtil.fmt(tmp, art, "", request,titleLen);
            html += tmp;
        }
        return html;

    }


    /*
     * 随机取文章
     */
    @ResponseBody
    @RequestMapping(value = "/rand/{gid}")
    public String rand(@PathVariable(value = "gid", required = true) Long gid,
                       @RequestParam(value = "pageSize", required = false) Integer pageSize,
                       @RequestParam(value = "titleLen", required = false) Integer titleLen,
                       HttpServletRequest request,
                       HttpServletResponse response) {
        if (titleLen==null) {
            titleLen=0;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        TempletPortalMain template = templetePortalMainDaoImpl.findDef();
        String lf = TempletUtil.sundriesTag(messageSource,template, "htmlTemplet_hot");
        Rs rs;
        rs = articleDaoImpl.randByGid(gid, 1, pageSize);

        String html = "", tmp;
        @SuppressWarnings("unchecked")
        List<Article> artList = (List<Article>) rs.getList();
        for (Article art : artList) {
            tmp = lf;
            tmp = ArticleUtil.fmt(tmp, art, "", request,titleLen);
            html += tmp;
        }
        return html;

    }

    /*
     * 个人热点文章 byclicks 指按点击量
     */
    @ResponseBody
    @RequestMapping(value = "/personalHot/{uid}/{gid}")
    public String personalHot(@PathVariable(value = "uid", required = true) Long uid,
                              @PathVariable(value = "gid", required = false) Long gid,
                              @RequestParam(value = "byclicks", required = false) Integer byclicks,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize,
                              @RequestParam(value = "titleLen", required = false) Integer titleLen,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        if (titleLen==null) {
            titleLen=0;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        if (gid == null) {
            gid = 0L;
        }
        TempletPortalMain template = templetePortalMainDaoImpl.findDef();
        String lf = TempletUtil.sundriesTag(messageSource,template, "htmlTemplet_hot");
        Rs rs;
        if (byclicks == null) {
            byclicks = 0;
        }
        if (byclicks == 1) {
            rs = articleDaoImpl.clicksByUid(uid, gid, 0, 1, pageSize);
        } else {
            rs = articleDaoImpl.hotByUid(uid, gid, 0, 1, pageSize);
        }

        String html = "", tmp;
        @SuppressWarnings("unchecked")
        List<Article> artList = (List<Article>) rs.getList();
        for (Article art : artList) {
            tmp = lf;
            tmp = ArticleUtil.fmt(tmp, art, "", request,titleLen);
            html += tmp;
        }
        return html;

    }

    /*
     * 首页被阅读信息
     */
    @ResponseBody
    @RequestMapping(value = "/indexViewShow")
    public String indexVisitfunUpdate(@RequestParam(value = "url", required = false) String url,
                                      @RequestParam(value = "referer", required = false) String referer, HttpServletRequest request,
                                      HttpServletResponse response) {
        String html = "";
        EnvirSet es = envirInit(0, 0, request, response);
        Portal portal = portalDaoImpl.query();
        VisitorsBook vbook = portal.getVbook();
        VisitUtil.visitorRefresh(es, vbook, "111", url, referer); // 更新浏览者信息
        return html;
    }

    /*
     * 栏目页被阅读信息
     */
    @ResponseBody
    @RequestMapping(value = "/navViewShow/{id}")
    public String navVisitfunUpdate(@PathVariable(value = "id", required = true) Long id,
                                    @RequestParam(value = "url", required = false) String url,
                                    @RequestParam(value = "referer", required = false) String referer, HttpServletRequest request,
                                    HttpServletResponse response) {
        String html = "";
        if (id == null || id == 0) {
            return html;
        }
        ArticleGroup agroup = groupDaoImpl.findByID(id);
        long gid = agroup.getId();
        EnvirSet es = envirInit(gid, 0, request, response);
        Portal portal = portalDaoImpl.query();
        VisitorsBook vbook = portal.getVbook();
        VisitUtil.visitorRefresh(es, vbook, "101", url, referer); // 更新浏览者信息
        vbook = agroup.getVbook();
        VisitUtil.visitorRefresh(es, vbook, "111", url, referer); // 更新浏览者信息
        ArticleGroup group = groupDaoImpl.findByID(gid);
        List<ArticleGroup> list = groupDaoImpl.queryParentBySubID(group.getId(), 1);
        for (ArticleGroup g : list) {
            vbook = g.getVbook();
            VisitUtil.visitorRefresh(es, vbook, "110", url, referer); // 更新浏览者信息
        }
        html = TempletUtil.sundriesTag(messageSource,templetePortalMainDaoImpl.findDef(), "htmlTemplet_view");
        /*
         * ,htmlTemplet_view:浏览数：{$tag\58views$} IP数：{$tag\58ips$}
         * 上次来访IP：{$tag\58lastip$}
         */
        // html = TempletUtil.escape(html);
        html = AnalyzeUtil.replace(html, "tag", "views", "" + agroup.getVbook().getViewsTotal());
        html = AnalyzeUtil.replace(html, "tag", "ips", "" + agroup.getVbook().getIpTotal());
        html = AnalyzeUtil.replace(html, "tag", "vbID", agroup.getVbook().getId());
        VisitorIPRecord vir = es.getVisitorIPRecordDaoImpl().findLast(agroup.getVbook().getId());
        // IPUtil.ipFilter(vir.getIp(),messageSource.getMessage("ip.filter.mask", null,
        // "1111",null) );
        html = AnalyzeUtil.replace(html, "tag", "lastip",
                IPUtil.ipFilter(vir.getIp(), messageSource.getMessage("ip.filter.mask", null, "1111", null)));
        return html;
    }

    /*
     * 文章被阅读信息
     */
    @ResponseBody
    @RequestMapping(value = "/artViewShow/{id}")
    public String artVisitfunUpdate(@PathVariable(value = "id", required = true) Long id,
                                    @RequestParam(value = "url", required = false) String url,
                                    @RequestParam(value = "referer", required = false) String referer, HttpServletRequest request,
                                    HttpServletResponse response) {
        String html = "";
        if (id == null || id == 0) {
            return html;
        }
        Article art = articleDaoImpl.findByID(id);
        if (art==null || art.getAgroup()==null) {
            return html;
        }
        long gid = art.getAgroup().getId();
        EnvirSet es = envirInit(gid, 0, request, response);
        Portal portal = portalDaoImpl.query();
        VisitorsBook vbook = portal.getVbook();
        VisitUtil.visitorRefresh(es, vbook, "101", url, referer); // 更新浏览者信息
        vbook = art.getVbook();
        VisitUtil.visitorRefresh(es, vbook, "101", url, referer); // 更新浏览者信息
        ArticleGroup group = groupDaoImpl.findByID(gid);
        vbook = group.getVbook();
        VisitUtil.visitorRefresh(es, vbook, "111", url, referer); // 更新浏览者信息
        List<ArticleGroup> list = groupDaoImpl.queryParentBySubID(group.getId(), 1);
        for (ArticleGroup g : list) {
            vbook = g.getVbook();
            VisitUtil.visitorRefresh(es, vbook, "110", url, referer); // 更新浏览者信息
        }
        html = TempletUtil.sundriesTag(messageSource,templetePortalMainDaoImpl.findDef(), "htmlTemplet_view");
        /*
         * ,htmlTemplet_view:浏览数：{$tag\58views$} IP数：{$tag\58ips$}
         * 上次来访IP：{$tag\58lastip$}
         */
        // html = TempletUtil.escape(html);
        html = AnalyzeUtil.replace(html, "tag", "views", "" + art.getVbook().getViewsTotal());
        html = AnalyzeUtil.replace(html, "tag", "ips", "" + art.getVbook().getIpTotal());
        html = AnalyzeUtil.replace(html, "tag", "vbID", art.getVbook().getId());
        VisitorIPRecord vir = es.getVisitorIPRecordDaoImpl().findLast(art.getVbook().getId());
        // IPUtil.ipFilter(vir.getIp(),messageSource.getMessage("ip.filter.mask", null,
        // "1111",null) );
        html = AnalyzeUtil.replace(html, "tag", "lastip",
                IPUtil.ipFilter(vir.getIp(), messageSource.getMessage("ip.filter.mask", null, "1111", null)));
        return html;
    }

    /*
     * 文章编辑功能区域
     */
    @ResponseBody
    @RequestMapping(value = "/artfun/{id}")
    public String artfun(@PathVariable(value = "id", required = true) Long id, HttpServletRequest request,
                         HttpServletResponse response,HttpSession session, @CookieValue(value = "username_lerx", required = false) String username_lerx,
                         @CookieValue(value = "password_lerx", required = false) String password_lerx) {

        String areaHtml = "";

        if (id == null || id == 0) {
            return areaHtml;
        }
        Article art = articleDaoImpl.findByID(id);
        if (art == null || art.getAgroup() == null) {
            return areaHtml;
        }

        long gid = art.getAgroup().getId();

        boolean backadmin=LoginUtil.adminChk(messageSource, session);


        EnvirSet es = envirInit(gid, 0, request, response);
        long uid = UserUtil.loginRefresh(es, username_lerx, password_lerx);
        User user = userDaoImpl.findByID(uid);
        boolean forePower=false;
        if (user!=null && user.getRole()!=null && user.getRole().getMask()!=null) {

            forePower=GroupUtil.auditMaskChk(art.getAgroup(), user.getRole().getMask());
        }

        if (!backadmin && (user == null || user.getRole() == null)) {
            return areaHtml;
        }

        if (backadmin || forePower) {

            String msg;
            // 审核
            String passHtmlTemplet = TempletUtil.sundriesTag(messageSource,templetePortalMainDaoImpl.findDef(), "htmlTemplet_artpass");
            if (art.isStatus()) {
                msg = TempletUtil.sundriesTag(messageSource,templetePortalMainDaoImpl.findDef(), "unpass");
            } else {
                msg = TempletUtil.sundriesTag(messageSource,templetePortalMainDaoImpl.findDef(), "pass");
            }

            // passHtmlTemplet = TempletUtil.escape(passHtmlTemplet);

            passHtmlTemplet = AnalyzeUtil.replace(passHtmlTemplet, "tag", "status", "" + !art.isStatus());
            passHtmlTemplet = AnalyzeUtil.replace(passHtmlTemplet, "tag", "title", msg);

            passHtmlTemplet = passHtmlTemplet.replaceAll("\r|\n", "");
            areaHtml += passHtmlTemplet;

        }


        if (UserUtil.isadmin(user)) {
            String msg;
            String topOneHtmlTemplet = TempletUtil.sundriesTag(messageSource,templetePortalMainDaoImpl.findDef(), "htmlTemplet_topOne");
            if (art.isTopOne()) {
                msg = TempletUtil.sundriesTag(messageSource,templetePortalMainDaoImpl.findDef(), "unTopOne");
            } else {
                msg = TempletUtil.sundriesTag(messageSource,templetePortalMainDaoImpl.findDef(), "topOne");
            }

            topOneHtmlTemplet = AnalyzeUtil.replace(topOneHtmlTemplet, "tag", "status", "" + !art.isTopOne());
            topOneHtmlTemplet = AnalyzeUtil.replace(topOneHtmlTemplet, "tag", "title", msg);

            topOneHtmlTemplet = topOneHtmlTemplet.replaceAll("\r|\n", "");
            areaHtml += topOneHtmlTemplet;

        }

        if (!forePower && (art.getUser() != null && art.getUser().getId() - uid == 0)) {
            forePower = true;
        }

        if (forePower) {
            // 编辑
            String editHtmlTemplet = TempletUtil.sundriesTag(messageSource,templetePortalMainDaoImpl.findDef(), "htmlTemplet_edit");
            editHtmlTemplet = TempletUtil.escape(editHtmlTemplet);
            String msg = TempletUtil.sundriesTag(messageSource,templetePortalMainDaoImpl.findDef(), "edit");
            editHtmlTemplet = AnalyzeUtil.replace(editHtmlTemplet, "tag", "title", msg);
            areaHtml += editHtmlTemplet;

        }

        areaHtml = AnalyzeUtil.replace(areaHtml, "tag", "aid", "" + art.getId());
        areaHtml = AnalyzeUtil.replace(areaHtml, "tag", "subject", art.getSubject());

        return areaHtml;
    }

    /*
     * private String artTagsAnalyze(String html,Article art,EnvirSet es) { String
     * uidStr=HttpUtil.getCookie(messageSource, es.getRequest(), "uid_lerx"); String
     * passHtmlTemplet;
     *
     * long uid; User user=null; if (uidStr!=null && StringUtil.isNumber(uidStr)){
     * uid = Long.valueOf(uidStr); }else{ uid = 0; } if (uid>0L) { user =
     * userDaoImpl.findByID(uid); } if (user!=null &&
     * GroupUtil.auditMaskChk(art.getAgroup(), user.getRole().getMask())) { String
     * msg; passHtmlTemplet=TempletUtil.sundriesTag(
     * templetPortalMainDaoImpl.findDef().getSundriesTag(), "htmlTemplet_artpass");
     * if (art.isStatus()) { msg=TempletUtil.sundriesTag(
     * templetPortalMainDaoImpl.findDef().getSundriesTag(), "unpass"); }else {
     * msg=TempletUtil.sundriesTag(
     * templetPortalMainDaoImpl.findDef().getSundriesTag(), "pass"); }
     *
     * passHtmlTemplet =AnalyzeUtil.replace(passHtmlTemplet, "tag", "aid",
     * ""+art.getId()); passHtmlTemplet =AnalyzeUtil.replace(passHtmlTemplet, "tag",
     * "msg", msg);
     *
     * }else { passHtmlTemplet=""; } html =AnalyzeUtil.replace(html, "tag",
     * "auditArea", passHtmlTemplet); return html; }
     */
    private GlobalTagsAnalyzeReturn globalTagsAnalyze(EnvirSet es, String station) {
        GlobalTagsAnalyzeReturn gtar = new GlobalTagsAnalyzeReturn();
        gtar.setEs(es);
        TempletPortalMain template = templetePortalMainDaoImpl.findDef();
        if (template == null) {
            gtar.setHtml(nullTemplate(messageSource));

            return gtar;
        }
        TempletPortalSubElement elTemplate = TempletUtil.elInitByTagStr(template, station);
        String html = htmlInit(template, elTemplate);
        html = StringUtil.clear65279(html);
        String imgHtmlTemplet = TempletUtil.sundriesTag(messageSource,template, "htmlTemplet_img");
        // 优先处理文章
        Article artCurr;
        if (es.getAid() > 0L) {
            artCurr = es.getArticleDaoImpl().findByID(es.getAid());
            html = ArticleUtil.fmt(html, artCurr, imgHtmlTemplet, es.getRequest(),0);
        }
        ArticleGroup agCurr = null;
        if (es.getGid() > 0L) {
            html = AnalyzeUtil.replace(html, "tag", "gid", "" + es.getGid());
            agCurr = es.getGroupDaoImpl().findByID(es.getGid());
            if (agCurr != null) {
                html = GroupUtil.fmt(html, agCurr);
            }
        } else {
            html = AnalyzeUtil.replace(html, "tag", "gid", "0");
        }
        String delimiter = TempletUtil.sundriesTag(messageSource,template, "delimiter");
        Set<FindedDataAnalyzeResult> fdarSet = AnalyzeUtil.find(html);
        if (fdarSet != null && fdarSet.size() > 0) {
            for (FindedDataAnalyzeResult fdar : fdarSet) {
                if (fdar != null) {
                    fdar = TempletUtil.fmt(fdar, template, elTemplate); // 此处的fdar中的dsp中的loopStr中必定有格式化字符串
					/*if (fdar != null && fdar.getDsp() != null
							&& fdar.getDsp().getDataSource().trim().equalsIgnoreCase("articles_from_nav_curr")) {

					}*/
					/*if (fdar.getDsp() != null) {
						if (es.getPage() > 1) {
							fdar.getDsp().setCurpage(es.getPage());
						}
						if (es.getPageSize() > 0) {
							fdar.getDsp().setPagesize(es.getPageSize());
						}
					}*/

                    if (fdar == null || fdar.getDsp() == null || fdar.getDsp().getLoopFormatStr() == null) {
                        continue;
                    }
                    html = TempletUtil.tagToData(html, es, fdar);

                    // 栏目处理
                    if (es.getGid() > 0L) {
                        if (fdar.getDsp().getGid() == 0) {
                            fdar.getDsp().setGid(es.getGid());
                        }

                        if (agCurr != null) {
                            if (agCurr.getHtmlOwn() != null && !agCurr.getHtmlOwn().trim().equals("")) {
                                html = AnalyzeUtil.replace(html, "code", "htmlOwn", agCurr.getHtmlOwn());
                            } else {
                                html = AnalyzeUtil.replace(html, "code", "htmlOwn", "");
                            }

                        }

                        html = TempletUtil.tagToNavs(html, es, fdar);
                    }

                }

            }
        }

        if (es.getGid() > 0L) {
            if (es.getAid() == 0) {
                html = AnalyzeUtil.replace(html, "tag", "station",
                        GroupUtil.locationStr(es,
                                "<a href=\"{$tag:href$}\" >{$tag:name$}</a>", delimiter, false));
            } else {
                html = AnalyzeUtil.replace(html, "tag", "station",
                        GroupUtil.locationStr(es,
                                "<a href=\"{$tag:href$}\" >{$tag:name$}</a>", delimiter, true));
            }

        }

        html = AnalyzeUtil.replace(html, "tag", "pageCurr", TempletUtil.sundriesTag(template, station));
        html = AnalyzeUtil.replace(html, "tag", "delimiter", delimiter);
        html = AnalyzeUtil.replace(html, "tag", "hrefTarget", StringUtil.nullFilter2(elTemplate.getTargetStr()));
        gtar.setHtml(html);
        gtar.setEs(es);
        return gtar;
    }

    /*
     * 首页
     */
    @ResponseBody
    @RequestMapping("/index")
    public String indexUpdate(HttpServletRequest request) {
        EnvirSet es = envirInit(0, 0, request, null);
        GlobalTagsAnalyzeReturn gtar = globalTagsAnalyze(es, "index");
        es=gtar.getEs();
        String html = gtar.getHtml();
        html = TempletUtil.tagToEnvir(html, es);
        if (es==null) {
            return html;
        }
        Portal portal = portalDaoImpl.query();
        VisitorsBook vbook = portal.getVbook();
        html = AnalyzeUtil.replace(html, "tag", "vbookID", ""+vbook.getId());
        return html;
    }

    /*
     * 分类页
     */
    @ResponseBody
    @RequestMapping(value = "/nav/{id}")
    public String navUpdate(@PathVariable(value = "id", required = false) Long id, HttpServletRequest request,
                            @RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (id == null) {
            id = 0L;
        }
        if (page == null) {
            page = 0;
        }


        if (pageSize == null) {
            pageSize = 0;
        }

        EnvirSet es = envirInit(id, 0, request, null);
        es.setPage(page);
        es.setPageSize(pageSize);
        GlobalTagsAnalyzeReturn gtar = globalTagsAnalyze(es, "nav");
        es=gtar.getEs();
        String html = gtar.getHtml();
        html = TempletUtil.tagToEnvir(html, es);
        if (es==null) {
            return html;
        }
        ArticleGroup ag=es.getGroupDaoImpl().findByID(id);
        if (ag!=null && ag.getVbook()!=null) {
            VisitorsBook vbook = ag.getVbook();
            html = AnalyzeUtil.replace(html, "tag", "vbookID", ""+vbook.getId());
        }else {
            html = AnalyzeUtil.replace(html, "tag", "vbookID", "0");
        }

        html = AnalyzeUtil.replace(html, "tag", "mappCurr",
                es.getRequest().getContextPath() + "/action_show/nav/" + id);
        return html;
    }

    /*
     * 文章编辑页
     */
    @ResponseBody
    @RequestMapping(value = "/edit/{id}")
    public String edit(@PathVariable(value = "id", required = false) Long id, HttpServletRequest request) {
        Article art = null;
        long gid=0L;
        if (id == null) {
            id = 0L;
        }else {
            art = articleDaoImpl.findByID(id);
            if (art!=null && art.getAgroup()!=null) {
                gid=art.getAgroup().getId();
            }

        }

        if (art == null) {
            art = new Article();
            art.setId(0);
        }

        String uidStr = HttpUtil.getCookie(messageSource, request, "uid_lerx");
        long uid;
        if (uidStr != null && !uidStr.trim().equals("") && StringUtil.isNumber(uidStr)) {
            uid = Long.valueOf(uidStr);
        } else {
            uid = 0L;
        }
        User user = userDaoImpl.findByID(uid);
        String mask="";

        if (user!=null && user.getRole()!=null && user.getRole().getMask()!=null) {
            mask=user.getRole().getMask();
        }
        String html;
        EnvirSet es;
        es = envirInit(gid, id, request, null);
        TempletPortalMain template = templetePortalMainDaoImpl.findDef();
        if (template == null) {
            return nullTemplate(messageSource);
        }
        GlobalTagsAnalyzeReturn gtar = globalTagsAnalyze(es, "edit");
        es = gtar.getEs();
        html = gtar.getHtml();
        if (id >0 && gid>0L) {
            if (!GroupUtil.auditMaskChk(art.getAgroup(), mask) && art.getUser().getId() - uid!=0) {
                html = TempletUtil.sundriesTag(messageSource,template, "resultHtml");
                if (html==null || html.trim().equals("")) {
                    html = FileUtil.readRes(messageSource, "template_result");
                }
                html = StringUtil.clear65279(html);
                html = AnalyzeUtil.replace(html, "tag", "referer", request.getHeader("Referer"));
                html = AnalyzeUtil.replace(html, "tag", "returnUrl", request.getHeader("Referer"));
                html = AnalyzeUtil.replace(html, "tag", "msg", messageSource.getMessage("fail.permission", null, "You have no permissions for the current operation!", null));
                return html;
            }
        }

//		html = globalTagsAnalyze(es, "edit");
        html = TempletUtil.tagToEnvir(html, es);
        return html;
    }


    /*
     * 文章页
     */
    @ResponseBody
    @RequestMapping(value = "/art/{id}")
    public String artUpdate(@PathVariable(value = "id", required = false) Long id, HttpServletRequest request) {
        if (id == null) {
            id = 0L;
        }
        Article art = articleDaoImpl.findByID(id);

        String html;
        EnvirSet es;
        String uidStr = HttpUtil.getCookie(messageSource, request, "uid_lerx");
        long uid;
        if (uidStr != null && !uidStr.trim().equals("") && StringUtil.isNumber(uidStr)) {
            uid = Long.valueOf(uidStr);
        } else {
            uid = 0L;
        }
        User user = userDaoImpl.findByID(uid);
        if (art != null && (UserUtil.isadmin(user)
                || (user != null && user.getRole() != null
                && GroupUtil.auditMaskChk(art.getAgroup(), user.getRole().getMask()))
                || (art.isStatus() && GroupUtil.statusChk(art.getAgroup())))) {
            long gid = 0;
            if (art != null && art.getAgroup() != null) {
                gid = art.getAgroup().getId();
            }
            es = envirInit(gid, id, request, null);
            GlobalTagsAnalyzeReturn gtar = globalTagsAnalyze(es, "art");
            es = gtar.getEs();
            html = gtar.getHtml();
            if (es==null) {
                System.out.println("es is null!");
            }
            if (es.getRequest()==null) {
                System.out.println("es.getRequest() is null!");
            }
            if (es.getRequest().getContextPath()==null) {
                System.out.println("es.getRequest().getContextPath() is null!");
            }
            html = AnalyzeUtil.replace(html, "tag", "mappCurr",
                    es.getRequest().getContextPath() + "/action_show/art/" + id);
            if (art!=null && art.getVbook()!=null) {
                VisitorsBook vbook = art.getVbook();
                html = AnalyzeUtil.replace(html, "tag", "vbookID", ""+vbook.getId());
            }else {
                html = AnalyzeUtil.replace(html, "tag", "vbookID", "0");
            }

        } else {
            es = envirInit(0, id, request, null);
            html = FileUtil.readRes(messageSource, "template_404");
        }
        if (art!=null && art.getMediaUrl()!=null && !art.getMediaUrl().trim().equals("") && art.getMediaUrl().trim().length()>3) {
            String videoHtmlTemplet=TempletUtil.sundriesTag(messageSource,templetePortalMainDaoImpl.findDef(), "template_video");

            videoHtmlTemplet = AnalyzeUtil.replace(videoHtmlTemplet, "tag", "media", art.getMediaUrl().trim());

            html = AnalyzeUtil.replace(html, "code", "mediaPlayer", videoHtmlTemplet);

        }else {
            html = AnalyzeUtil.replace(html, "code", "mediaPlayer", "");
        }


        html = TempletUtil.tagToEnvir(html, es);
        return html;
    }

    /*
     * 文集
     */
    @ResponseBody
    @RequestMapping(value = "/corpus/{uid}")
    public String corpus(@PathVariable(value = "uid", required = false) Long uid,
                         @RequestParam(value = "page", required = false) Integer page,
                         @RequestParam(value = "pageSize", required = false) Integer pageSize, HttpServletRequest request) {
        String html;
        EnvirSet es;
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 0;
        }
        es = envirInit(0, 0, request, null);
        es.setPage(page);
        es.setPageSize(pageSize);
        boolean own = false;
        long currUid;
        String uidStr = HttpUtil.getCookie(messageSource, request, "uid_lerx");
        if (uidStr != null && !uidStr.trim().equals("") && StringUtil.isNumber(uidStr)) {
            currUid = Long.valueOf(uidStr);
        } else {
            currUid = 0L;
        }
        if (uid == null || uid == 0L) {

            own = true;
            uid = currUid;
        } else {
            if (uid - currUid == 0) {
                own = true;
            }
        }
        es.setUid(uid);
        String ownname = "null";
        User user = userDaoImpl.findByID(uid);
        if (user != null) {
            ownname = user.getUsername();
        }
        GlobalTagsAnalyzeReturn gtar = globalTagsAnalyze(es, "corpus");
        es = gtar.getEs();
        html = gtar.getHtml();
        html = TempletUtil.tagToEnvir(html, es);

        if (own) {
            html = AnalyzeUtil.replace(html, "tag", "ownname",
                    messageSource.getMessage("title.own", null, "My ", null));
        } else {
            html = AnalyzeUtil.replace(html, "tag", "ownname", ownname);
        }

        html = AnalyzeUtil.replace(html, "tag", "station",
                messageSource.getMessage("title.corpus.personal", null, "Personal corpus", null));
        html = AnalyzeUtil.replace(html, "tag", "ownUid", "" + uid);
        html = AnalyzeUtil.replace(html, "tag", "mappCurr",
                es.getRequest().getContextPath() + "/action_show/corpus/" + uid);
        return html;
    }

}
