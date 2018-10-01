package com.lwhtarena.company.web.handlers;

import com.lwhtarena.company.annotation.Token;
import com.lwhtarena.company.app.SafeStrUtil;
import com.lwhtarena.company.sys.util.MavUtil;
import com.lwhtarena.company.sys.util.StringUtil;
import com.lwhtarena.company.web.common.UserUtil;
import com.lwhtarena.company.web.dao.ICommentBridgeDao;
import com.lwhtarena.company.web.dao.ICommentThreadDao;
import com.lwhtarena.company.web.dao.IPortalDao;
import com.lwhtarena.company.web.dao.IUserDao;
import com.lwhtarena.company.web.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author：liwh
 * @Description:
 * @Date 12:24 2018/8/12
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
@RequestMapping("/action_comm")
@Controller
public class CommentHandler {

    @Autowired
    private ResourceBundleMessageSource messageSource;

    @Autowired
    private IPortalDao portalDaoImpl;

    @Autowired
    private ICommentThreadDao commentThreadDaoImpl;

    @Autowired
    private ICommentBridgeDao commentBridgeDaoImpl;

    @Autowired
    private IUserDao userDaoImpl;

    private static final String SUCCESS = "jsp/result/success";
    private static final String FAILED = "jsp/result/failed";


    @ResponseBody
    @RequestMapping(value = "/pass/{id}")
    @Token(ajax = true, log = true, mark = "comment--<pass>")
    public int passUpdate(@PathVariable(value = "id", required = true) Long id, @CookieValue(value="uid_lerx",required=false) String uid_lerx) {
        CommentThread ct = commentThreadDaoImpl.findByID(id);
        if (ct==null) {
            return -11;
        }
        User user = null;
        CommentBridge cb=ct.getCb();

        if (cb==null) {
            return -11;
        }


        if (uid_lerx!=null && !uid_lerx.trim().equals("") && StringUtil.isNumber(uid_lerx)) {
            long uid=Long.valueOf(uid_lerx);
            user=userDaoImpl.findByID(uid);
        }

        if (user==null) {
            return -3;
        }

        if (cb.getUser().getId() - user.getId() == 0) {
            ct.setStatus(true);
            commentThreadDaoImpl.modify(ct);
            return 0;
        }else {
            return -2;
        }

    }

    @ResponseBody
    @RequestMapping(value = "/del/{id}")
    @Token(ajax = true, log = true, mark = "comment--<del>")
    public int del(@PathVariable(value = "id", required = true) Long id,@CookieValue(value="uid_lerx",required=false) String uid_lerx) {
        CommentThread ct = commentThreadDaoImpl.findByID(id);
        if (ct==null) {
            return -11;
        }
        User user = null;
        CommentBridge cb=ct.getCb();

        if (cb==null) {
            return -11;
        }


        if (uid_lerx!=null && !uid_lerx.trim().equals("") && StringUtil.isNumber(uid_lerx)) {
            long uid=Long.valueOf(uid_lerx);
            user=userDaoImpl.findByID(uid);
        }

        if (user==null) {
            return -3;
        }

        if (cb.getUser().getId() - user.getId() == 0 || UserUtil.isadmin(user)) {
            ct.setDeleted(true);
            commentThreadDaoImpl.modify(ct);
            return 0;
        }else {
            return -2;
        }

    }

    @ResponseBody
    @RequestMapping(value = "/env/{bid}")
    public int env(@PathVariable(value = "bid", required = true) Long bid,@CookieValue(value="uid_lerx",required=false) String uid_lerx) {
        Portal portal = portalDaoImpl.query();
        User user = null;
        CommentBridge cb=null;
        cb = commentBridgeDaoImpl.findByID(bid);
        if (cb==null) {
            return -11;
        }

        if (!cb.isStatus() || !portal.isComm()) {
            return -5;
        }

        if (uid_lerx!=null && !uid_lerx.trim().equals("") && StringUtil.isNumber(uid_lerx)) {
            long uid=Long.valueOf(uid_lerx);
            user=userDaoImpl.findByID(uid);
        }

        if (!portal.isFreeComm() && user==null) {
            return -3;
        }
        return 0;

    }

    @RequestMapping("/add")
    @Token(ajax = false, log = true, mark = "comment--<add>", token = true, failedPage = FAILED, msgKey = "fail.universal")
    public ModelAndView add(HttpServletRequest request, HttpSession session, @CookieValue(value="uid_lerx",required=false) String uid_lerx, @RequestParam(value = "content", required = false) String content, @RequestParam(value = "bid", required = true) long bid, @RequestParam(value = "pid", required = false) Long pid, @RequestParam(value = "vcode", required = true) String vcode) {
        if (pid==null) {
            pid=0L;
        }
        Portal portal = portalDaoImpl.query();
        if (!portal.isComm()) {

            return MavUtil.mav(FAILED, messageSource.getMessage("fail.function.notopen", null, "The system does not open the function!", null));
        }
        String safeSessionStr = SafeStrUtil.getstr(messageSource, request, "comm");
        String vcodeSession = (String) session.getAttribute(safeSessionStr);

        if (((vcode == null) || (!(vcode.trim().equalsIgnoreCase(vcodeSession))))) {
            return MavUtil.mav(FAILED, messageSource.getMessage("error.verify.code", null, "Please enter the correct verification code!", null));
        }
        if (content==null || content.trim().equals("")) {
            return MavUtil.mav(SUCCESS, messageSource.getMessage("fail.content.empty", null, "The content can't be empty!", null));
        }
        CommentBridge cb=null;
        if (bid>0L) {
            cb = commentBridgeDaoImpl.findByID(bid);
        }else {
            return MavUtil.mav(SUCCESS, messageSource.getMessage("error.args", null, "Parameter error!", null));
        }

        if (cb==null) {
            return MavUtil.mav(SUCCESS, messageSource.getMessage("error.args", null, "Parameter error!", null));
        }
        CommentThread parent=null;
        if (pid>0L) {
            parent=	commentThreadDaoImpl.findByID(pid);
        }

        CommentThread ct = new CommentThread();
        ct.setCb(cb);
        ct.setContent(content);
        ct.setOccurDatetime(System.currentTimeMillis());
        Poll poll=new Poll();
        if (portal.isPoll()) {
            poll.setStatus(true);
        }
        ct.setPoll(poll);
        User user=null;
        if (uid_lerx!=null && !uid_lerx.trim().equals("") && StringUtil.isNumber(uid_lerx)) {
            long uid=Long.valueOf(uid_lerx);
            user=userDaoImpl.findByID(uid);
            ct.setUser(user);
        }
        if (!portal.isFreeComm() && user==null) {
            return MavUtil.mav(SUCCESS, messageSource.getMessage("fail.nologin", null, "Sorry, you need to log in to finish this operation.", null));
        }
        if (portal.isCommPassAuto()) {
            ct.setStatus(true);
        }
        ct.setParent(parent);
        commentThreadDaoImpl.add(ct);

        return MavUtil.mav(SUCCESS, "");
    }

}
