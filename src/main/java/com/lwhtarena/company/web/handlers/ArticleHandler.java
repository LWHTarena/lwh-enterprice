package com.lwhtarena.company.web.handlers;

import com.lwhtarena.company.annotation.Token;
import com.lwhtarena.company.aop.args.CutTags;
import com.lwhtarena.company.hql.entities.Rs;
import com.lwhtarena.company.hql.util.RsUtil;
import com.lwhtarena.company.login.util.LoginUtil;
import com.lwhtarena.company.sys.util.FileUtil;
import com.lwhtarena.company.sys.util.HttpUtil;
import com.lwhtarena.company.sys.util.MavUtil;
import com.lwhtarena.company.sys.util.StringUtil;
import com.lwhtarena.company.web.common.ArticleUtil;
import com.lwhtarena.company.web.common.GroupUtil;
import com.lwhtarena.company.web.common.UserUtil;
import com.lwhtarena.company.web.dao.*;
import com.lwhtarena.company.web.entities.*;
import com.lwhtarena.company.web.portal.obj.ArticleGroupMapCreateArgs;
import com.lwhtarena.company.web.portal.obj.FileEl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

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
@RequestMapping("/action_article")
@Controller
public class ArticleHandler {
    private static final String ARTICLELIST = "jsp/article/list";
    private static final String ARTICLEEDIT = "jsp/article/add";
    private static final String LOGINPAGE = "jsp/user/login";
    private static final String ADMINFORBID = "_admin.forbid_";

    @Autowired
    private IPortalDao portalDaoImpl;

    @Autowired
    private IGroupDao groupDaoImpl;

    @Autowired
    private IArticleDao articleDaoImpl;

    @Autowired
    private IUserDao userDaoImpl;

    @Autowired
    private IUserArtsCountDao userArtsCountDaoImp;

    @Autowired
    private IHtmlFileStaticDao htmlFileStaticDaoImpl;

    @Autowired
    private ResourceBundleMessageSource messageSource;

    @ModelAttribute
    public void getArticle(@RequestParam(value = "id", required = false) Long id, Map<String, Object> map) {
        if (id != null && id > 0) {
            map.put("article", articleDaoImpl.findByID(id));
        }
    }

    @RequestMapping("/beforeAdd")
    public String beforeAdd(Map<String, Object> map, HttpServletRequest request, HttpSession session) {
        String uidStr=HttpUtil.getCookie(messageSource, request, "uid_lerx");
        long uid;
        if (uidStr!=null  && StringUtil.isNumber(uidStr)){
            uid = Long.valueOf(uidStr);
        }else{
            uid = 0;
        }

        boolean free=false;
        String mask="";
        Role role;
        if (LoginUtil.adminChk(messageSource, session)) {
            free=true;
        }
        if (uid>0L) {
            User user=userDaoImpl.findByID(uid);
            role=user.getRole();
            if (role!=null) {
                if (role.getMask()!=null && (role.getMask().trim().equals("0") || role.getMask().trim().equals("a0"))) {
                    free=true;
                }else {
                    mask = role.getMask();
                }
            }
        }

        Article article = new Article();
        article.setStatus(true);
        map.put("article", article);
        String rootTitle=messageSource.getMessage("root.select.title", null, "Root", null);
        ArticleGroupMapCreateArgs agmca = new ArticleGroupMapCreateArgs();
        agmca.setCurrRoleMask(mask);
        agmca.setCurrRoot(null);
        agmca.setFree(free);
        agmca.setGroupDaoImpl(groupDaoImpl);
        agmca.setMap(map);
        agmca.setRootTitle(rootTitle);
        agmca.setStatus(1);
        agmca.setMessageSource(messageSource);
        agmca.setRequest(request);

        GroupUtil.mapCreate(agmca);
        return ARTICLEEDIT;
    }

    @RequestMapping("/add")
    @Token(ajax=false,token=true,loginOrAdmin=true,log=true,mark="article--<add>",failedPage=LOGINPAGE)
    public ModelAndView add(Article article, Errors result, @CookieValue(value="uid_lerx",required=false) String uid_lerx, HttpServletRequest request, HttpSession session, Map<String, Object> map, CutTags tags) {
        Portal portal =portalDaoImpl.query();

        article=ArticleUtil.validate(messageSource, article);
        if (article.getId()==0) {

            CommentBridge cb=new CommentBridge();
            Poll poll=new Poll();
            poll.setStatus(portal.isPoll());
            poll.setObjTitle(article.getSubject());
            ArticleGroup agroup=article.getAgroup();
            if (agroup==null) {
                return MavUtil.mav("jsp/result/failed", messageSource.getMessage("fail.nav.null", null, "You do not have a choice of columns!", null)) ;
            }
            agroup=groupDaoImpl.findByID(article.getAgroup().getId());
            poll.setStatus(agroup.isPoll());
            cb.setStatus(agroup.isComm());

            if (portal.isArtPassAuto()) {
                article.setStatus(true);
            }else {
                article.setStatus(false);
            }

            article.setCreationTime(System.currentTimeMillis());
            article.setCb(cb);
            article.setPoll(poll);

            if (uid_lerx!=null && !uid_lerx.trim().equals("") && StringUtil.isNumber(uid_lerx)) {
                long uid=Long.valueOf(uid_lerx);
                User user=userDaoImpl.findByID(uid);
                article.setUser(user);
                cb.setUser(user);
                UserUtil.uacUpdate(user, article, userArtsCountDaoImp, 0, 1);
                user.setArtsTotal(user.getArtsTotal() + 1);
                if (article.isStatus()) {
                    user.setArtsPassed(user.getArtsPassed() + 1);
                    UserUtil.uacUpdate(user, article, userArtsCountDaoImp, 1, 1);
                }
                userDaoImpl.modify(user);
				/*UserUtil.uacModify(user, "yyyy", article.getCreationTime(),userArtsCountDaoImp, 0, 1,false);
				UserUtil.uacModify(user, "yyyyMM", article.getCreationTime(),userArtsCountDaoImp, 0, 1,false);
				Date weekStamp=TimeUtil.firstDayAtWeek(new Date(article.getCreationTime()));
				UserUtil.uacModify(user, "yyyyMMdd", weekStamp.getTime(),userArtsCountDaoImp, 0, 1,false);
				UserUtil.uacModify(user, String.valueOf(TimeUtil.quarter(article.getCreationTime())), article.getCreationTime(),userArtsCountDaoImp, 0, 1,true);*/


            }

            VisitorsBook vbook = new VisitorsBook();
            vbook.setObjType(2);
            vbook.setObjTitle(article.getSubject());
            article.setVbook(vbook);

            HtmlFileStatic hfs = new HtmlFileStatic();
            article.setHfs(hfs);
            article=articleDaoImpl.add(article);
            groupDaoImpl.changed(article.getAgroup());
            FileEl fe=ArticleUtil.feBuild(messageSource, request, article);
            hfs=article.getHfs();
            hfs.setRealPath(fe.getRealPath());
            hfs.setUrl(fe.getUrl());
            hfs.setFilename(FileUtil.getFileFromPath(fe.getRealPath()));
            htmlFileStaticDaoImpl.modify(hfs);

            if (portal.isArtPassAuto() && article.isStatus()) {
                ArticleUtil.htmlCreate(request, messageSource, article);
                article.getHfs().setStatus(true);
                htmlFileStaticDaoImpl.modify(article.getHfs());
            }

        }else {
            if (!portal.isArtPassAuto()) {
                article.setStatus(false);
            }
            articleDaoImpl.modify(article);
            if (article.isStatus()) {
                ArticleUtil.htmlCreate(request, messageSource, article);
                article.getHfs().setStatus(true);
                htmlFileStaticDaoImpl.modify(article.getHfs());
            }
        }

        //mapCreate(map,groupDaoImpl,group);
        return MavUtil.mav("jsp/result/success", messageSource.getMessage("success.article.add", null, "The article is published successfully!", null)) ;
    }



    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Map<String, Object> map,HttpServletRequest request,HttpSession session) {


        String uidStr=HttpUtil.getCookie(messageSource, request, "uid_lerx");
        long uid;
        if (uidStr!=null  && StringUtil.isNumber(uidStr)){
            uid = Long.valueOf(uidStr);
        }else{
            uid = 0;
        }
        boolean free=false;
        String mask="";
        Role role;
        if (LoginUtil.adminChk(messageSource, session)) {
            free=true;
        }
        if (uid>0L) {
            User user=userDaoImpl.findByID(uid);
            role=user.getRole();
            if (role!=null) {
                if (role.getMask()!=null && (role.getMask().trim().equals("0") || role.getMask().trim().equals("a0"))) {
                    free=true;
                }else {
                    mask = role.getMask();
                }
            }
        }

        Article article = articleDaoImpl.findByID(id);
        map.put("article", article);
        String rootTitle=messageSource.getMessage("root.title", null, "Root", null);

        ArticleGroupMapCreateArgs agmca = new ArticleGroupMapCreateArgs();
        agmca.setCurrRoleMask(mask);
        agmca.setCurrRoot(null);
        agmca.setFree(free);
        agmca.setGroupDaoImpl(groupDaoImpl);
        agmca.setMap(map);
        agmca.setRootTitle(rootTitle);
        agmca.setStatus(1);
        agmca.setMessageSource(messageSource);
        agmca.setRequest(request);

        GroupUtil.mapCreate(agmca);
        return ARTICLEEDIT;
    }


    @ResponseBody
    @RequestMapping(value = "/del/{id}")
    @Token(ajax=true,log=true,mark="article--<del>",admin=true,failedPage=LOGINPAGE)
    public int del(@PathVariable(value = "id", required = true) Long id,HttpSession session) {
        boolean result;

        Article article=articleDaoImpl.findByID(id);

        ArticleGroup g = article.getAgroup();
        User user= (User) article.getUser();
        if (user!=null) {

            UserUtil.uacUpdate(user, article, userArtsCountDaoImp, 0, -1);
            if (user.getArtsTotal()>0) {
                user.setArtsTotal(user.getArtsTotal() - 1);
            }
            if (article.isStatus()) {
                if (user.getArtsPassed()>0) {
                    user.setArtsPassed(user.getArtsPassed() - 1);
                }
                UserUtil.uacUpdate(user, article, userArtsCountDaoImp, 1, -1);
            }
            userDaoImpl.modify(user);
        }

        result=articleDaoImpl.delByID(id);
        if (result) {

            groupDaoImpl.changed(g);
            return 0;
        }else {
            return -1;
        }

    }

    @ResponseBody
    @RequestMapping(value = "/top/{id}/{status}")
    @Token(ajax=true,log=true,mark="article--<top>",role0=true,failedPage=LOGINPAGE)
    public int modifyTopOne(@PathVariable(value = "id", required = true) Long id,@PathVariable(value = "status", required = true) Integer status) {

        Article article=articleDaoImpl.findByID(id);
        boolean sta;
        if (status==0) {
            sta=false;
        }else {
            sta=true;
        }
        if (article!=null) {
            articleDaoImpl.topOne(id, sta);
        }else {
            return -13;
        }

        return 0;

    }



    @ResponseBody
    @RequestMapping(value = "/pass/{id}/{status}")
    @Token(ajax=true,log=true,mark="article--<pass>",loginOrAdmin=true,failedPage=LOGINPAGE)
    public int modifyPass(@PathVariable(value = "id", required = true) Long id,@PathVariable(value = "status", required = true) Integer status,HttpServletRequest request,HttpSession session) {
        boolean admin=LoginUtil.adminChk(messageSource, session);
        boolean con=false;
        if (admin) {
            con=true;
        }
        Article art = articleDaoImpl.findByID(id);
        String uidStr=HttpUtil.getCookie(messageSource, request, "uid_lerx");
        long uid=0L;
        if (uidStr!=null && !uidStr.trim().equals("") && StringUtil.isNumber(uidStr)) {
            uid = Long.valueOf(uidStr);
        }

        User user=userDaoImpl.findByID(uid);
        Role role=null;
        if (user!=null) {
            role=user.getRole();
        }

        String mask="";
        if (role!=null) {
            mask = role.getMask();
        }
        if (!admin && GroupUtil.auditMaskChk(art.getAgroup(), mask)) {
            con=true;
        }

        if (con) {
            if (status==1) {
                art.setStatus(true);
            }else {
                art.setStatus(false);
            }

            articleDaoImpl.modify(art);
            art = articleDaoImpl.findByID(art.getId());

            User po = (User) art.getUser();
            if (art.isStatus()) {

                ArticleUtil.htmlCreate(request, messageSource, art);
                art.getHfs().setStatus(true);
                if (StringUtil.isNotNull(po)) {
                    UserUtil.uacUpdate(po, art, userArtsCountDaoImp, 1, 1);
                    po.setArtsPassed(po.getArtsPassed()+1);
                    userDaoImpl.modify(po);
                }

            }else {
                if (StringUtil.isNotNull(po)) {
                    UserUtil.uacUpdate(po, art, userArtsCountDaoImp, 1, -1);
                    if (po.getArtsPassed()>0) {
                        po.setArtsPassed(po.getArtsPassed() - 1);
                        userDaoImpl.modify(po);
                    }
                }

                FileUtil.delete(art.getHfs().getRealPath());
                art.getHfs().setStatus(false);

            }
            //更新目录的状态
            groupDaoImpl.changed(art.getAgroup());
            htmlFileStaticDaoImpl.modify(art.getHfs());
            return 0;
        }

        return -2;

    }

    @RequestMapping("/inventory")
    @Token(ajax=false,login=true,failedPage=ADMINFORBID)
    public String inventory(@RequestParam(value = "gid", required = false) Long gid,@RequestParam(value = "status", required = false) Integer status,@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "pageSize", required = false) Integer pageSize,HttpServletRequest request,Map<String, Object> map) {
        Portal portal=portalDaoImpl.query();
        boolean asc=false;
        if (gid==null) {
            gid=0L;
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }

        String uidStr=HttpUtil.getCookie(messageSource, request, "uid_lerx");
        long uid=0L;
        if (uidStr!=null && !uidStr.trim().equals("") && StringUtil.isNumber(uidStr)) {
            uid = Long.valueOf(uidStr);
        }

        User user=userDaoImpl.findByID(uid);
        Role role=null;
        if (status==null) {
            status=1;
        }
        boolean admin=false;
        ArticleGroup agroup=groupDaoImpl.findByID(gid);
        if (user!=null) {
            role=user.getRole();
            if (role!=null && role.getMask()!=null) {
                if (GroupUtil.auditMaskChk(groupDaoImpl.findByID(gid), role.getMask())) {
                    admin=true;
                }
            }
        }
        Rs rs;
        if (!admin) {
            switch (status) {
                case 1:
                    rs=articleDaoImpl.queryByGid(gid,0, page, pageSize, asc,1,0);
                    break;
                case 0:
                    rs=articleDaoImpl.queryByGid(gid,0, page, pageSize, asc,1,0);
                    break;
                default:
                    rs=RsUtil.init(page, pageSize, 0);
                    rs.setList(null);
            }

        }else {
            rs=articleDaoImpl.queryByGid(gid,0, page, pageSize, asc,status,0);
        }
        map.put("rs", rs);
        map.put("pageUrl", "/action_article/inventory?gid="+gid+"&status="+status);
        map.put("portal", portal);
        map.put("agroup", agroup);
        map.put("status", status);

        return "jsp/article/inventory";
    }

    @RequestMapping("/list")
    @Token(ajax=false,admin=true,failedPage=ADMINFORBID)
    public String list(@RequestParam(value = "gid", required = false) Long gid, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "pageSize", required = false) Integer pageSize, Map<String, Object> map) {
        boolean asc=false;
        if (gid==null) {
            gid=0L;
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Rs rs=articleDaoImpl.queryByGid(gid,0, page, pageSize, asc,0,0);
        map.put("rs", rs);
        map.put("pageUrl", "/action_article/list");
        return ARTICLELIST;
    }

    @RequestMapping("/search")
    public String search(String keywords,@RequestParam(value = "page", required = false) Integer page,@RequestParam(value = "pageSize", required = false) Integer pageSize,Map<String, Object> map) {
        Portal portal =portalDaoImpl.query();
        map.put("portal", portal);
        if (keywords==null || keywords.trim().equals("")) {
            map.put("keywords", null);
            return "jsp/article/search";
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Rs rs  = articleDaoImpl.search(0, keywords, true, 0, page, pageSize);
        map.put("keywords", keywords);
        map.put("rs", rs);
        map.put("pageUrl", "/action_article/search?keywords="+keywords);

        return "jsp/article/search";
    }
}
