package com.lwhtarena.company.web.handlers;

import com.lwhtarena.company.analyze.util.AnalyzeUtil;
import com.lwhtarena.company.annotation.Token;
import com.lwhtarena.company.app.SafeStrUtil;
import com.lwhtarena.company.hql.entities.Rs;
import com.lwhtarena.company.ip.util.IPUtil;
import com.lwhtarena.company.login.util.LoginSafeRecUtil;
import com.lwhtarena.company.login.util.LoginUtil;
import com.lwhtarena.company.sys.obj.LoginSessionTest;
import com.lwhtarena.company.sys.util.HttpUtil;
import com.lwhtarena.company.sys.util.MavUtil;
import com.lwhtarena.company.sys.util.StringUtil;
import com.lwhtarena.company.web.common.CaptchaUtil;
import com.lwhtarena.company.web.common.ConfigUtil;
import com.lwhtarena.company.web.common.UserUtil;
import com.lwhtarena.company.web.dao.*;
import com.lwhtarena.company.web.entities.Portal;
import com.lwhtarena.company.web.entities.Role;
import com.lwhtarena.company.web.entities.User;
import com.lwhtarena.company.web.portal.obj.LoginTest;
import com.lwhtarena.company.web.portal.obj.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author：liwh
 * @Description:
 * @Date 16:12 2018/8/4
 * @Modified By:
 * <h1>
 * <ol></ol>
 * </h1>
 */
@RequestMapping("/action_user")
@Controller
public class UserHandler {

    private static final String SUCCESS = "jsp/result/success";
    private static final String FAILED = "jsp/result/failed";

    private static final String LOGINPAGE = "jsp/user/login";
    private static final String USERLIST = "jsp/user/list";
    private static final String USERADD = "jsp/user/add";
    private static final String ADMINFORBID = "_admin.forbid_";


    @Autowired
    private ResourceBundleMessageSource messageSource;

    @Autowired
    private IPortalDao portalDaoImpl;

    @Autowired
    private IUserDao userDaoImpl;

    @Autowired
    private IArticleDao articleDaoImpl;

    @Autowired
    private IRoleDao roleDaoImpl;


    @Autowired
    private ICommentBridgeDao commentBridgeDaoImpl;

    @Autowired
    private ICommentThreadDao commentThreadDaoImpl;

    @Autowired
    private IUploadedFileDao uploadedFileDaoImpl;

    @Autowired
    private IUserArtsCountDao userArtsCountDaoImp;

    /*
     * 列表
     */
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
        Rs rs = userDaoImpl.find(page, pageSize);
        map.put("pageUrl", "/action_user/list");
        map.put("rs", rs);

        return MavUtil.mav(USERLIST, "");

    }

    // 查找用户名
    @RequestMapping(value = "/findname")
    @ResponseBody
    public int findname(String name) {
        if (userDaoImpl.findByUsername(name) != null) {
            return 1;
        } else {
            return -1;
        }
    }

    // 更改手机号
    @RequestMapping(value = "/chgmobile/{uid}")
    @ResponseBody
    @Token(ajax = true, log = true, loginOrAdmin = true, mark = "user--<chgmobile>")
    public int mobileUpdate(String mobile, @PathVariable("uid") Long uid, HttpServletRequest request) {
        String uidStr = HttpUtil.getCookie(messageSource, request, "uid_lerx");
        if (uidStr == null || uid == null || Long.valueOf(uidStr) - uid != 0L) {
            uid = 0L;
            return -7;
        }
        if (userDaoImpl.findByMobile(mobile, uid) != null) {
            return -10;
        } else {
            User user = userDaoImpl.findByID(uid);
            user.setMobile(mobile);
            userDaoImpl.modify(user);
            return 0;
        }
    }

    // 更改邮箱
    @RequestMapping(value = "/chgemail/{uid}")
    @ResponseBody
    @Token(ajax = true, log = true, loginOrAdmin = true, mark = "user--<chgmobile>")
    public int emailUpdate(String email, @PathVariable("uid") Long uid, HttpServletRequest request) {
        String uidStr = HttpUtil.getCookie(messageSource, request, "uid_lerx");
        if (uidStr == null || uid == null || Long.valueOf(uidStr) - uid != 0L) {
            uid = 0L;
            return -7;
        }
        if (userDaoImpl.findByEmail(email, uid) != null) {
            return -10;
        } else {
            User user = userDaoImpl.findByID(uid);
            user.setEmail(email);
            ;
            userDaoImpl.modify(user);
            return 0;
        }
    }

    // 查找手机号
    @RequestMapping(value = "/findmobile")
    @ResponseBody
    public int findMobile(String mobile, @RequestParam(value = "uid", required = false) Long excludeID) {
        if (excludeID == null) {
            excludeID = 0L;
        }
        if (userDaoImpl.findByMobile(mobile, excludeID) != null) {
            return 1;
        } else {
            return -1;
        }
    }

    // 查找邮箱
    @RequestMapping(value = "/findemail")
    @ResponseBody
    public int findEmail(String email, @RequestParam(value = "uid", required = false) Long excludeID) {
        if (excludeID == null) {
            excludeID = 0L;
        }
        if (userDaoImpl.findByEmail(email, excludeID) != null) {
            return 1;
        } else {
            return -1;
        }
    }

    // 注册
    @RequestMapping(value = "/reg")
    @Token(ajax = false, log = true, mark = "user--<reg>")
    public ModelAndView addByReg(@Valid User user, Errors result, String sendTarget, HttpServletRequest request,
                                 HttpSession session) {
        if (session.getAttribute("sendTarget") == null || session.getAttribute("targetMode") == null) {
            return MavUtil.mav(FAILED,
                    messageSource.getMessage("fail.session.timeout", null, "Session timeout!", null));
        }
        String sessionTarget = (String) session.getAttribute("sendTarget");
        int sessionTargetMode = (int) session.getAttribute("targetMode");

        if (sendTarget == null || sendTarget.trim().equals("") || sessionTarget == null
                || !sendTarget.trim().equalsIgnoreCase(sessionTarget)) {
            return MavUtil.mav(FAILED,
                    messageSource.getMessage("fail.session.timeout", null, "Session timeout!", null));
        }

        if (sessionTargetMode == 0) {
            user.setEmail(sessionTarget);
        } else {
            user.setMobile(sessionTarget);
        }

        if (userDaoImpl.findByUsername(user.getUsername()) != null) {
            return MavUtil.mav(FAILED,
                    messageSource.getMessage("fail.exists.username", null, "The username has already existed!", null),
                    true);
        }

        String charset = messageSource.getMessage("charset", null, "UTF-8", null);
        String filterWords = ConfigUtil.configContentsByComma("filterUsernames", charset);
        if (StringUtil.findByWords(user.getUsername(), filterWords)) {
            return MavUtil.mav(FAILED, messageSource.getMessage("fail.wrongful.title", null,
                    "The name is not legal or is forbidden to use!", null), true); // 发现保留字
        }
        filterWords = ConfigUtil.configContentsByComma("filterWords", charset);
        if (StringUtil.findByWords(user.getUsername(), filterWords)) {
            return MavUtil.mav(FAILED, messageSource.getMessage("fail.wrongful.title", null,
                    "The name is not legal or is forbidden to use!", null), true); // 发现敏感词
        }

        if (userDaoImpl.findByEmail(user.getEmail(), 0) != null) {
            return MavUtil.mav(FAILED,
                    messageSource.getMessage("fail.exists.email", null, "The email has already existed!", null), true);
        }

        if (userDaoImpl.findByMobile(user.getMobile(), 0) != null) {
            return MavUtil.mav(FAILED,
                    messageSource.getMessage("fail.exists.mobile", null, "The phone code has already existed!", null));
        }

        user.setRegCodeSendTarget(sessionTarget);

        String ip = IPUtil.getRealRemotIP(request);
        user.setCreateIP(ip);

        user.setState(true);
        user.setRole(roleDaoImpl.findDef());
        user = userDaoImpl.add(user);
        if (user == null) {
            return MavUtil.mav(FAILED, messageSource.getMessage("fail.universal", null, "Failed!", null));
        } else {
            session.removeAttribute("sendTarget");
            session.removeAttribute("targetMode");
            return MavUtil.mav(SUCCESS, messageSource.getMessage("success", null, "Success!", null));
        }
    }

    // 增加
    @RequestMapping(value = "/addByName")
    @ResponseBody
    @Token(ajax = true, log = true, mark = "user--<addByName>", admin = true, failedPage = ADMINFORBID, msgKey = "fail.permission")
    public int addByName(String name, HttpServletRequest request) {

        boolean existing = false;
        if (userDaoImpl.findByUsername(name) != null) {
            existing = true;
        }
        String charset = messageSource.getMessage("charset", null, "UTF-8", null);
        String filterWords = ConfigUtil.configContentsByComma("filterUsernames", charset);
        if (StringUtil.findByWords(name, filterWords)) {
            return -17; // 发现保留字
        }
        filterWords = ConfigUtil.configContentsByComma("filterWords", charset);
        if (StringUtil.findByWords(name, filterWords)) {
            return -18; // 发现敏感词
        }
        if (!existing) {
            String ip = IPUtil.getRealRemotIP(request);
            User user = new User();
            user.setUsername(name);
            user.setCreateTime(System.currentTimeMillis());
            user.setCreateIP(ip);

            user.setState(true);
            user.setRole(roleDaoImpl.findDef());
            user.setPassword(messageSource.getMessage("defalut.password", null, "123456", null));
            user = userDaoImpl.add(user);
            if (user == null) {
                return -9;
            } else {
                return 0;
            }
        } else {
            return -10;
        }

    }

    // 增加
    @RequestMapping("/add")
    @Token(ajax = false, log = true, mark = "user--<add>", admin = true, token = true, failedPage = ADMINFORBID, msgKey = "fail.permission")
    public String add(@Valid User user, Errors result, Map<String, Object> map, HttpServletRequest request,
                      HttpSession session) {
        boolean existing = false;
        boolean r = false;
        String ip = null;

        String charset = messageSource.getMessage("charset", null, "UTF-8", null);
        String filterWords = ConfigUtil.configContentsByComma("filterUsernames", charset);
        if (StringUtil.findByWords(user.getUsername(), filterWords)) {
            return FAILED; // 发现保留字
        }
        filterWords = ConfigUtil.configContentsByComma("filterWords", charset);
        if (StringUtil.findByWords(user.getUsername(), filterWords)) {
            return FAILED;
        }

        if (user.getId() == 0) {
            if (userDaoImpl.findByUsername(user.getUsername()) != null) {
                existing = true;
            }
            if (!existing) {
                ip = IPUtil.getRealRemotIP(request);
                user.setCreateTime(System.currentTimeMillis());
                user.setCreateIP(ip);

                user.setState(true);
                user.setRole(roleDaoImpl.findDef());

                if (user.getPassword() == null || user.getPassword().trim().equals("")) {
                    user.setPassword(messageSource.getMessage("defalut.password", null, "123456", null));
                }

                user = userDaoImpl.add(user);
                if (user == null) {
                    r = false;
                } else {
                    r = true;
                    user = userDaoImpl.findByID(user.getId());
                    user.setPwdAtCreate(user.getPassword());
                    userDaoImpl.modify(user);
                }

            } else {
                r = false;
                map.put("error", messageSource.getMessage("fail.exists.username", null,
                        "The username has already existed!", null));
            }

        } else {

            if (user.getRole().getId() == 0) {
                user.setRole(null);
            }

            userDaoImpl.modifySafely(user);
            r = true;

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

        User user = userDaoImpl.findByID(id);
        user.setPassword(null);
        map.put("user", user);
        List<Role> list = roleDaoImpl.queryAll();
        map.put("roles", list);
        return USERADD;

    }

    // 网站名片
    @RequestMapping(value = "/card/{id}")
    public String card(Map<String, Object> map, HttpSession session,
                       @CookieValue(value = "uid_lerx", required = false) String uid_lerx,
                       @PathVariable(value = "id", required = false) Long id, HttpServletRequest request) {
        if (id == null) {
            id = 0L;
        }
        long uid;
        User loginer = null;
        if (uid_lerx == null) {
            uid = 0L;
        } else {
            uid = Long.valueOf(uid_lerx);
            loginer = userDaoImpl.findByID(uid);
        }
        boolean own = false;
        if (uid - id == 0) {
            own = true;
        }

        if (id == 0L) {
            id = uid;
        }

        if (id == 0) {
            return "jsp/result/failed";
        }

        User user = userDaoImpl.findByID(id);

        if (user == null) {
            return "jsp/result/failed";
        }
        if (!own & !UserUtil.isadmin(loginer)) {
            user.setLastLoginIP(IPUtil.ipFilter(user.getLastLoginIP(),
                    messageSource.getMessage("ip.filter.mask", null, "1111", null)));
            user.setCreateIP(IPUtil.ipFilter(user.getCreateIP(),
                    messageSource.getMessage("ip.filter.mask", null, "1111", null)));
        }

        map.put("user", user);
        map.put("avatarNull", ConfigUtil.getAvatarNullFile(messageSource, request));
        List<Role> list = roleDaoImpl.queryAll();
        map.put("roles", list);
        return "jsp/user/card";

    }

    @ResponseBody
    @RequestMapping("/pwsReset")
    @Token(ajax = true, log = true, mark = "user--<password-reset>")
    public int modifyPwsReset(long uid, String password, HttpServletRequest request, HttpSession session) {
        if (session.getAttribute("sendTarget") == null) {
            return -6;
        }
        User user = userDaoImpl.findByID(uid);
        if (user == null) {
            return -1;
        }
        userDaoImpl.pwchange(user, password);
        return 0;
    }

    @ResponseBody
    @RequestMapping("/pws")
    @Token(ajax = true, log = true, mark = "user--<password>", admin = true, failedPage = ADMINFORBID, msgKey = "fail.permission")
    public int modifyPws(long uid, String password) {
        User user = userDaoImpl.findByID(uid);
        userDaoImpl.pwchange(user, password);
        return 0;
    }

    @ResponseBody
    @RequestMapping(value = "/forget")
    @Token(ajax = true, log = true,mark = "user--<forget>")
    public ResponseResult forget(String keywords, HttpServletRequest request, HttpSession session) {
        ResponseResult rr = new ResponseResult();
        Portal portal = portalDaoImpl.query();
        User user = userDaoImpl.findByKeywords(keywords);
        if (user == null) {
            rr.setResult(-1);
            rr.setMsg(messageSource.getMessage("fail.null", null, "null", null));
        } else {
            rr.setValueL(user.getId());
            rr.setValueS1(user.getUsername());

            rr.setResult(0);
            int r = -1;
            if (StringUtil.emailTest(keywords)) {
                r = CaptchaUtil.send(messageSource, request, session, portal.getName(), 0, keywords);
                rr.setValueS2(keywords);
                rr.setValueI(0);
            } else if (StringUtil.isNumber(keywords) && keywords.length() == 11) {
                r = CaptchaUtil.send(messageSource, request, session, portal.getName(), 1, keywords);
                rr.setValueS2(keywords);
                rr.setValueI(1);
            } else {
                if (user.getEmail() != null && StringUtil.emailTest(user.getEmail())) {
                    r = CaptchaUtil.send(messageSource, request, session, portal.getName(), 0, user.getEmail());
                    rr.setValueS2(user.getEmail());
                    rr.setValueI(0);
                } else if (StringUtil.isNumber(user.getMobile()) && user.getMobile().length() == 11) {
                    r = CaptchaUtil.send(messageSource, request, session, portal.getName(), 1, keywords);
                    rr.setValueS2(user.getMobile());
                    rr.setValueI(1);
                } else {
                    r = -13;
                }
            }
            rr.setResult(r);
        }

        return rr;
    }

    @ResponseBody
    @RequestMapping(value = "/pws/{uid}")
    @Token(ajax = true, log = true, mark = "user--<password>", login = true, failedPage = LOGINPAGE, msgKey = "fail.permission")
    public int modifySelfPws(@PathVariable("uid") Long uid, String password, HttpServletRequest request) {
        String uidStr = HttpUtil.getCookie(messageSource, request, "uid_lerx");
        if (uidStr == null || uidStr.trim().equals("") || !StringUtil.isNumber(uidStr)) {
            return -3;
        }
        if (uid == null || uid == 0L) {
            return -11;
        }

        long currUid = Long.valueOf(uidStr);

        if (currUid - uid != 0) {
            return -2;
        }
        User user = userDaoImpl.findByID(uid);
        if (user == null) {
            return -11;
        }

        userDaoImpl.pwchange(user, password);
        return 0;
    }

    /*
     * 删除
     */
    @ResponseBody
    @RequestMapping("/del")
    @Token(ajax = false, log = true, mark = "user--<del>", admin0 = true, failedPage = ADMINFORBID, msgKey = "fail.permission")
    public int del(Long id, HttpSession session) {

        if (articleDaoImpl.countByUid(id)>0L) {
            return -4;
        }
        if (commentBridgeDaoImpl.countByUid(id)>0L) {
            return -4;
        }
        if (commentThreadDaoImpl.countByUid(id)>0L) {
            return -4;
        }
        if (uploadedFileDaoImpl.countByUid(id)>0L) {
            return -4;
        }
        if (userArtsCountDaoImp.countByUid(id)>0L) {
            return -4;
        }


        boolean result = false;
        result = userDaoImpl.delByID(id);
        if (result) {
            return 0;
        } else {
            return -9;
        }

    }

    /*
     * 匹配
     */
    @ResponseBody
    @RequestMapping(value = "/match/{uid}")
    public int match(@PathVariable("uid") Long uid, HttpServletRequest request) {

        String uidStr = HttpUtil.getCookie(messageSource, request, "uid_lerx");
        if (uidStr == null || uidStr.trim().equals("") || !StringUtil.isNumber(uidStr)) {
            return -3;
        }

        if (uid == null || uid == 0L) {
            return -11;
        }

        long currUid = Long.valueOf(uidStr);
        if (currUid - uid == 0) {
            return 0;
        } else {
            return -2;
        }

    }

    /*
     * 更新头像
     */
    @ResponseBody
    @RequestMapping("/avatar")
    @Token(ajax = false, log = true, mark = "user--<avatar-update>", login = true, failedPage = LOGINPAGE, msgKey = "fail.permission")
    public int avatarUpdate(@RequestParam(value = "uid", required = false) Long uid,
                            @RequestParam(value = "avatar", required = false) String avatar, HttpServletRequest request) {

        if (uid == null) {
            uid = 0L;
            return -11;
        }
        User user = userDaoImpl.findByID(uid);
        if (user == null) {
            return -11;
        }

        String uidStr = HttpUtil.getCookie(messageSource, request, "uid_lerx");
        if (uidStr == null || uidStr.trim().equals("") || !StringUtil.isNumber(uidStr)) {
            return -11;
        }
        long currUid = Long.valueOf(uidStr);
        if (currUid - uid != 0L) {
            return -2;
        }

        if (avatar == null || avatar.trim().equals("")) {
            return -12;
        }

        user.setAvatarUrl(avatar);
        return 0;

    }

    private LoginTest loginTest(User user, String vcode, boolean w, HttpServletRequest request,
                                HttpServletResponse response, HttpSession session) {
        LoginTest lt = new LoginTest();

        LoginSessionTest lst = LoginSafeRecUtil.interruptTest(
                messageSource.getMessage("login.upper.failed", null, "5", null),
                messageSource.getMessage("login.minutes.wait.afterFailed", null, "10", null), "user");

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
            session.setAttribute("vcode_user_lerx", "true"); // 验证码需求
            return lt;
        }

        // 如果需要用验证码
        String vcodeRequest = (String) session.getAttribute("vcode_user_lerx");
        String safeSessionStr = SafeStrUtil.getstr(messageSource, request, "user");
        String vcodeSession = (String) session.getAttribute(safeSessionStr);

        if (vcodeRequest != null && !vcodeRequest.trim().equals("") && vcodeRequest.trim().equals("true")) {

            if (((vcode == null) || (!(vcode.trim().equalsIgnoreCase(vcodeSession))))) {

                String msg = messageSource.getMessage("error.verify.code", null, "Verification code error!", null);
                lt.setMsg(msg);
                lt.setReturnPage(LOGINPAGE);
                lt.setResult(-5);
                return lt;
            }

        }

        if (user != null && user.getUsername() != null && !user.getUsername().trim().equals("")) {
            String passwordPlain = user.getPassword();
            user = userDaoImpl.login(user);

            if (user != null) { // 登录成功
                if (w) {
                    String ip = IPUtil.getRealRemotIP(request);
                    user.setLastLoginIP(ip);
                    user.setLastLoginTime(System.currentTimeMillis());
                    userDaoImpl.modify(user);
                }
                HttpUtil.saveCookie(messageSource, response, "username_lerx", user.getUsername());
                HttpUtil.saveCookie(messageSource, response, "password_lerx", passwordPlain);
                HttpUtil.saveCookie(messageSource, response, "uid_lerx", "" + user.getId());
                if (user.getRole() != null && user.getRole().getMask()!=null && user.getRole().getMask().trim().equals("0")) {
                    HttpUtil.saveCookie(messageSource, response, "role0_lerx", "true");
                }
                session.removeAttribute("lsr_user"); // 清除登录次数Session
                session.removeAttribute("vcode_user_lerx"); // 清除验证码需求
                lt.setMsg("");
                lt.setResult(0);
                lt.setReturnPage("/jsp/user/login_success");
            } else {
                HttpUtil.clearCookie(request, response, "username_lerx");
                HttpUtil.clearCookie(request, response, "password_lerx");
                HttpUtil.clearCookie(request, response, "uid_lerx");
                HttpUtil.clearCookie(request, response, "rid_lerx");
                HttpUtil.clearCookie(request, response, "role0_lerx");
                HttpUtil.clearCookie(request, response, "role_name_lerx");
                lt.setMsg(messageSource.getMessage("fail.login", null, "Login failed!", null));
                lt.setReturnPage(LOGINPAGE);
                lt.setResult(-1);
            }
        } else {
            lt.setMsg(messageSource.getMessage("fail.login", null, "Login failed!", null));
            lt.setReturnPage(LOGINPAGE);
            lt.setResult(-11);

        }

        if (lt.getResult() < 0) {
            session.setAttribute("vcode_user_lerx", "true"); // 验证码需求
            session.setAttribute("lsr_user", lst.getLsrStr()); // 登录次数Session
        }
        return lt;

    }

    @RequestMapping("/login")
    @Token(ajax = false, log = true, mark = "user--<login>", token = true, failedPage = LOGINPAGE, msgKey = "fail.login")
    public ModelAndView login(@Valid User user, Errors result,
                              @RequestParam(value = "vcode", required = false) String vcode, Map<String, Object> map,
                              HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        LoginTest lt = loginTest(user, vcode, true, request, response, session);
        return MavUtil.mav(lt.getReturnPage(), lt.getMsg());
    }

    @ResponseBody
    @RequestMapping("/loginAjax")
    @Token(ajax = true, token = true)
    public int loginAjax(@Valid User user, Errors result, @RequestParam(value = "vcode", required = false) String vcode,
                         Map<String, Object> map, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        LoginTest lt = loginTest(user, vcode, false, request, response, session);
        return lt.getResult();

    }

    @RequestMapping("/logout")
    @Token(ajax = false, log = true, mark = "user--<logout>")
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        logout(request, response, session, true);
        long uid = LoginUtil.uid(messageSource, request);
        if (uid > 0L) {
            session.removeAttribute("vcode_user_lerx"); // 验证码需求清除
            session.removeAttribute("lsr_user"); // 登录次数Session清除
        }

        return "/jsp/user/login";

    }

    @ResponseBody
    @RequestMapping("/logoutAjax")
    @Token(ajax = true, log = true, mark = "user--<logout>")
    public int logoutAjax(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        logout(request, response, session, true);
        return 0;

    }

    private void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session, boolean flag) {
        HttpUtil.clearCookie(request, response, "username_lerx");
        HttpUtil.clearCookie(request, response, "password_lerx");
        HttpUtil.clearCookie(request, response, "uid_lerx");
        HttpUtil.clearCookie(request, response, "rid_lerx");
        // HttpUtil.clearCookie(request, response, "role_mask_lerx");
        HttpUtil.clearCookie(request, response, "role0_lerx");

        HttpUtil.clearCookie(request, response, "role_name_lerx");
        session.removeAttribute("lsr"); // 清除登录次数Session
        session.removeAttribute("vcode_request_lerx"); // 清除验证码需求
    }


    public ModelAndView regByQQUpdate(User user) {
        return null;

    }



}
