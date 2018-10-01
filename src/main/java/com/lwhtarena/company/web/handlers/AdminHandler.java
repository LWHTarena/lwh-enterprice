package com.lwhtarena.company.web.handlers;

import com.lwhtarena.company.analyze.util.AnalyzeUtil;
import com.lwhtarena.company.annotation.Token;
import com.lwhtarena.company.aop.args.CutTags;
import com.lwhtarena.company.app.SafeStrUtil;
import com.lwhtarena.company.hql.entities.Rs;
import com.lwhtarena.company.ip.util.IPUtil;
import com.lwhtarena.company.login.util.LoginByResourceFileUtil;
import com.lwhtarena.company.login.util.LoginSafeRecUtil;
import com.lwhtarena.company.login.util.LoginUtil;
import com.lwhtarena.company.login.val.LoginSignUnitValues;
import com.lwhtarena.company.sys.obj.LoginSessionTest;
import com.lwhtarena.company.sys.util.MavUtil;
import com.lwhtarena.company.sys.util.TimeUtil;
import com.lwhtarena.company.web.dao.IAdminDao;
import com.lwhtarena.company.web.dao.IPortalDao;
import com.lwhtarena.company.web.entities.Admin;
import com.lwhtarena.company.web.entities.Portal;
import com.lwhtarena.company.web.portal.obj.LoginTest;
import com.lwhtarena.company.web.portal.obj.UserCrucialInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

/**
 * @Author：liwh
 * @Description:
 * @Date 17:44 2018/8/4
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
@Controller
@RequestMapping("action_admin")
public class AdminHandler {

    private static final String SUCCESS = "jsp/result/success";
    private static final String FAILED = "jsp/result/failed";

    private static final String LOGINPAGE = "_admin.login_";
    private static final String MAINPAGE = "_admin.main_";
    private static final String ADMINADD = "_admin.add_";
    private static final String ADMINLIST = "_admin.list_";
    private static final String ADMINFORBID = "_admin.forbid_";

    @Autowired
    private IPortalDao portalDaoImpl;

    @Autowired
    private IAdminDao adminDaoImpl;

    @Autowired
    private ResourceBundleMessageSource messageSource;

    @ModelAttribute
    public void getAdmin(@RequestParam(value = "id",required = false) Long id, Map<String,Object> map){
        if(id !=null && id >0){
            map.put("admin",adminDaoImpl.findByID(id));
        }
    }

    @RequestMapping("/login")
    @Token(ajax = false, log = true, mark = "admin--<login>", failedPage = LOGINPAGE, msgKey = "")
    public ModelAndView login(Map<String, Object> map, HttpServletRequest request, HttpSession session, String username,
                              String password, String vcode, CutTags tags) {

		/*System.out.println("1username:"+username);
		System.out.println("1password:"+password);
		System.out.println("1vcode:"+vcode);*/
        if (messageSource.getMessage("admin.login.url.check", null, "false", null).trim().equalsIgnoreCase("true")) {
            String loginUrl = messageSource.getMessage("url.login.admin", null, "/admin/login", null);
            String referer=request.getHeader("Referer");
            if (referer!=null) {
                referer=referer.trim();
            }
            if (referer==null || referer.trim().equals("") || !referer.endsWith(loginUrl)) {
                return MavUtil.mav(MavUtil.adminUrlChk(messageSource, ADMINFORBID), "");
            }
        }

        LoginTest lt = new LoginTest();
        if (messageSource == null) {
            System.out.println("messageSource is null");
        }
        LoginSessionTest lst = LoginSafeRecUtil.interruptTest(
                messageSource.getMessage("login.upper.failed", null, "5", null),
                messageSource.getMessage("login.minutes.wait.afterFailed", null, "10", null), "admin");
        // 如果需要用验证码
        String vcodeRequest = (String) session.getAttribute("vcode_admin_lerx");
        String safeSessionStr = SafeStrUtil.getstr(messageSource, request, "admin");
        String vcodeSession = (String) session.getAttribute(safeSessionStr);

        if (lst != null && lst.isInterrup()) {
            String failMes = messageSource.getMessage("fail.login.time.upper", null,
                    "You have overloaded the misplaced username and password within the time limit. Please try again later!",
                    null);
            failMes = AnalyzeUtil.replace(failMes, "tag", "upper",
                    messageSource.getMessage("login.upper.failed", null, " 5 ", null));
            failMes = AnalyzeUtil.replace(failMes, "tag", "minutes",
                    messageSource.getMessage("login.minutes.wait.afterFailed", null, " 10 ", null));
            lt.setMsg(failMes);
            lt.setReturnPage(LOGINPAGE);
            lt.setResult(-5);
            session.setAttribute("vcode_admin_lerx", "true"); // 验证码需求

            return MavUtil.mav(MavUtil.adminUrlChk(messageSource, lt.getReturnPage()), lt.getMsg());
        }

        if (vcodeRequest != null && !vcodeRequest.trim().equals("") && vcodeRequest.trim().equals("true")) {

            if (((vcode == null) || (!(vcode.trim().equalsIgnoreCase(vcodeSession))))) {
                String msg = messageSource.getMessage("error.verify.code", null, "Verification code error!", null);
                lt.setMsg(msg);
                lt.setReturnPage(LOGINPAGE);
                lt.setResult(-5);
                session.setAttribute("vcode_admin_lerx", "true"); // 验证码需求
                session.setAttribute("lsr_admin", lst.getLsrStr()); // 登录次数Session
                return MavUtil.mav(MavUtil.adminUrlChk(messageSource, lt.getReturnPage()), lt.getMsg());
            }

        }

        String result;


        LoginSignUnitValues lsuv = LoginUtil.read(session);
        // AdminLogin al = null;
        if (lsuv != null && lsuv.getAdminID() != null) {

            result = "success";
        } else {

            result = LoginByResourceFileUtil.login(messageSource, request, session, username, password);
        }

        if (!result.trim().equals("success")) {

            if (vcodeSession == null || vcodeSession.trim().equals("")) {
                result = messageSource.getMessage("fail.nologin", null, "Need to log in!", null);
                lt.setMsg(result);
                lt.setReturnPage(LOGINPAGE);
                lt.setResult(-1);
                session.setAttribute("vcode_admin_lerx", "true"); // 验证码需求
                session.setAttribute("lsr_admin", lst.getLsrStr()); // 登录次数Session
                return MavUtil.mav(MavUtil.adminUrlChk(messageSource, lt.getReturnPage()), lt.getMsg());
            }

            Admin admin = new Admin();
            admin.setUsername(username);
            admin.setPassword(password);
            admin = adminDaoImpl.login(admin);
            if (admin != null) {
                String ip = IPUtil.getRealRemotIP(request);
                admin.setLastLoginIP(ip);
                admin.setLastLoginTime(System.currentTimeMillis());

                adminDaoImpl.modify(admin);
                lsuv = new LoginSignUnitValues();
                lsuv.setAdminID("" + admin.getId());
                lsuv.setAdminName(admin.getUsername());
                LoginUtil.save(session, lsuv);

                result = "success";

            }
        }

        if (result.trim().equals("success")) {
            session.removeAttribute("lsr_admin"); // 清除登录次数Session
            session.removeAttribute("vcode_admin_lerx"); // 清除验证码需求

            Portal portal = portalDaoImpl.query();
            map.put("portal", portal.getName());

            return MavUtil.mav(MavUtil.adminUrlChk(messageSource, MAINPAGE), "0");
        } else {
            // lsrStr = LoginSafeRecUtil.covLsrToStr(lsr);

            if (result == null || result.trim().equals("")) {
                result = messageSource.getMessage("fail.login", null,
                        "Login failed! Please check whether the username and password you entered is correct.", null);
            }
            lt.setMsg(result);
            lt.setReturnPage(LOGINPAGE);
            lt.setResult(-1);
            session.setAttribute("lsr_admin", lst.getLsrStr()); // 登录次数Session
            session.setAttribute("vcode_admin_lerx", "true"); // 验证码需求
            return MavUtil.mav(MavUtil.adminUrlChk(messageSource, lt.getReturnPage()), lt.getMsg());
        }

    }

    @RequestMapping("/logout")
    @Token(ajax = false, log = true,admin=true, mark = "admin--<logout>", failedPage = ADMINFORBID)
    public ModelAndView logout(HttpServletRequest request, HttpSession session, String username, String password,
                               String vcode) {
        if (LoginUtil.adminChk(messageSource, session)) {
            session.removeAttribute("lsr_admin"); // 清除登录次数Session
            session.removeAttribute("vcode_admin_lerx"); // 清除验证码需求
        }
        LoginUtil.clearAdmin(session);
        ModelAndView mav = new ModelAndView();
        String loginUrl = messageSource.getMessage("url.login.admin", null, "/admin/login", null);
        mav.setViewName("redirect:" + loginUrl);
        return mav;

    }

    @RequestMapping("/list")
    @Token(ajax = false, admin = true, failedPage = ADMINFORBID, msgKey = "fail.permission")
    public ModelAndView list(@RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize, HttpSession session,
                             Map<String, Object> map) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Rs rs = adminDaoImpl.find(page, pageSize);
        map.put("pageUrl", "/action_admin/list");
        map.put("rs", rs);
        // 如果是利用配置文件登录，允许修改管理员
        map.put("admin0", LoginUtil.admin0Chk(messageSource, session));

        return MavUtil.mav(MavUtil.adminUrlChk(messageSource, ADMINLIST), "");

    }

    // 增加
    @RequestMapping("/add")
    @Token(ajax = false, log = true, mark = "admin--<add>", admin0 = true, token = true, failedPage = ADMINFORBID, msgKey = "fail.permission")
    public String add(@Valid Admin admin, Errors result, Map<String, Object> map, HttpServletRequest request,
                      HttpSession session) {
        boolean existing = false;
        boolean r = false;
        String ip = null;
        if (admin.getId() == 0) {
            if (adminDaoImpl.findByUsername(admin.getUsername()) != null) {
                existing = true;
            }
            if (!existing) {
                if (LoginUtil.adminChk(messageSource, session)) {
                    ip = IPUtil.getRealRemotIP(request);
                    admin.setCreateTime(System.currentTimeMillis());
                    admin.setCreateIP(ip);

                    admin.setState(true);
                    admin = adminDaoImpl.add(admin);
                    if (admin == null) {
                        r = false;
                    } else {
                        r = true;
                    }

                }
            } else {
                r = false;
                map.put("error", messageSource.getMessage("fail.exists.username", null,
                        "The username has already existed!", null));
            }

        } else {

            if (LoginUtil.adminChk(messageSource, session)
                    || LoginUtil.userChk(messageSource, session) - admin.getId() == 0L) {
                adminDaoImpl.modifySafely(admin);
                r = true;
            }

        }
        if (r) {
            return SUCCESS;
        } else {
            return FAILED;
        }

    }

    // 进入修改页面
    @RequestMapping("/edit")
    @Token(ajax = false, admin = true, failedPage = ADMINFORBID, msgKey = "fail.nologin")
    public String edit(Map<String, Object> map, HttpSession session, Long id) {

        Admin admin = adminDaoImpl.findByID(id);
        admin.setPassword(null);
        map.put("admin", admin);

        return MavUtil.adminUrlChk(messageSource, ADMINADD);

    }

    @ResponseBody
    @RequestMapping("/pws")
    @Token(ajax = false, log = true, mark = "admin--<pwsmodify>", admin0 = true, failedPage = ADMINFORBID, msgKey = "fail.permission")
    public int modifyPws(long uid, String password) {
        Admin admin = adminDaoImpl.findByID(uid);
        adminDaoImpl.pwchange(admin, password);
        return 0;
    }

    @ResponseBody
    @RequestMapping("/ownpws")
    @Token(ajax = false, log = true, mark = "admin--<pwsmodifyown>", admin = true, failedPage = LOGINPAGE, msgKey = "fail.permission")
    public int modifyPwsOwn(String password, HttpSession session) {
        String uidStr = LoginUtil.getAdminID(messageSource, session);
        long uid = Long.valueOf(uidStr);
        Admin admin = adminDaoImpl.findByID(uid);
        adminDaoImpl.pwchange(admin, password);
        return 0;
    }

    @ResponseBody
    @RequestMapping("/del")
    @Token(ajax = false, log = true, mark = "admin--<delete>", admin0 = true, failedPage = ADMINFORBID, msgKey = "fail.permission")
    public int del(Long id, HttpSession session) {
        boolean result = false;
        result = adminDaoImpl.delByID(id);
        if (result) {
            return 0;
        } else {
            return -9;
        }

    }

    // 获取当前用户的关键信息
    @RequestMapping("/crucial")
    @ResponseBody
    @Token(ajax = true, admin = true, failedPage = ADMINFORBID, msgKey = "fail.permission")
    public UserCrucialInf crucial(HttpSession session) {
        UserCrucialInf uci = new UserCrucialInf();
        String uidStr = LoginUtil.getAdminID(messageSource, session);
        if (uidStr == null) {
            uci.setUid(-1);
        }
        long uid = Long.valueOf(uidStr);
        uci.setUid(uid);

        if (uid > 0L) {
            Admin admin = adminDaoImpl.findByID(uid);
            if (admin.getLastLoginTime() > 0L) {
                uci.setLastLoginDatetime(TimeUtil.coverLongToStr(admin.getLastLoginTime(),
                        messageSource.getMessage("datetime.fmt.default", null, "yyyy-MM-dd HH:mm:ss", null)));
            } else {
                uci.setLastLoginDatetime("");
            }
            uci.setLastLoginIP(admin.getLastLoginIP());
            if (admin.getCreateTime() > 0L) {
                uci.setRegDatetime(TimeUtil.coverLongToStr(admin.getCreateTime(),
                        messageSource.getMessage("datetime.fmt.default", null, "yyyy-MM-dd HH:mm:ss", null)));

            } else {
                uci.setRegDatetime("");
            }
            uci.setRegDatetime(TimeUtil.coverLongToStr(admin.getCreateTime(),
                    messageSource.getMessage("datetime.fmt.default", null, "yyyy-MM-dd HH:mm:ss", null)));
            uci.setRegIP(admin.getCreateIP());

        }

        return uci;
    }

}
