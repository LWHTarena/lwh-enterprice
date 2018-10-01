package com.lwhtarena.company.web.handlers;

import com.lwhtarena.company.app.SafeStrUtil;
import com.lwhtarena.company.sys.util.*;
import com.lwhtarena.company.web.common.CaptchaUtil;
import com.lwhtarena.company.web.common.ConfigUtil;
import com.lwhtarena.company.web.dao.IPortalDao;
import com.lwhtarena.company.web.dao.IUserDao;
import com.lwhtarena.company.web.entities.Portal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;

/**
 * @Author：liwh
 * @Description:
 * @Date 12:21 2018/8/12
 * @Modified By:
 * <h1>验证码</h1>
 * <ol></ol>
 */
/*
 *
 */
@RequestMapping("/action_captcha")
@Controller
public class CaptchaHandler {

    @Autowired
    private ResourceBundleMessageSource messageSource;

    @Autowired
    private IPortalDao portalDaoImpl;

    @Autowired
    private IUserDao userDaoImpl;

    @RequestMapping("/randomNum")
    public void randomNum(HttpServletRequest request, HttpServletResponse response, HttpSession session, String key) throws Exception{
        File configFile=SysUtil.getConfigFile(messageSource, "app");
//		String appFile = messageSource.getMessage("configFile.app", null,"app.conf", null);
        String captchaMode= ConfigReader.getKey(configFile,"captcha.mode");
        int imgMode;
        if (captchaMode.trim().equalsIgnoreCase("words")){	//
            imgMode=2;
        }else if (captchaMode.trim().equalsIgnoreCase("number")){
            imgMode=1;
        }else{
            imgMode=0;
        }

        RandomNumUtil randomNum = new RandomNumUtil(response,60,18,4,18,imgMode);
        String  safeSessionStr=SafeStrUtil.getstr(messageSource, request,key);
        session.setAttribute(safeSessionStr, randomNum.getRandomCode());

    }


    @RequestMapping("/send")
    @ResponseBody
    public int send(String target, @RequestParam(value = "mode", required = false) Integer mode, @RequestParam(value = "newuser", required = false) Integer newuser, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
        int sendM = -1;
        Portal portal=portalDaoImpl.query();
        if (mode!=null) {
            sendM=mode;
        }
        if (sendM == -1) {

            if (portal.getCodeSendMode()==0) {	//如果验证码发向邮箱
                sendM=0;
                if (target == null || !StringUtil.emailTest(target)) {
                    return -11;
                }
                if (!ConfigUtil.mailChk(messageSource, target)) {
                    return -5;
                }
            }else {//如果发向手机
                sendM=1;
                if (target.trim().length()!=11) {
                    return -11;
                }
            }
        }

        if (newuser==null) {
            newuser=-1;
        }

        if (newuser == -1) {
            if (sendM==1) {	//查找手机
                if (userDaoImpl.findByMobile(target, 0) != null) {
                    return -10;
                }
            }else {			//查找邮箱
                if (userDaoImpl.findByEmail(target, 0) != null) {
                    return -10;
                }
            }
        }

        int r=CaptchaUtil.send(messageSource, request, session, portal.getName(), sendM, target);

        if (r>=0) {
            session.setAttribute("sendTarget", target);
        }
        return r;
    }


    @ResponseBody
    @RequestMapping("/validate")
    public int validate(String vcode,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
        String securityStr = SecurityUtil.readWords(messageSource);
        long endTime=System.currentTimeMillis();
        if (session.getAttribute("validateEnd")!=null) {
            endTime = (long) session.getAttribute("validateEnd");
        }
        long currTime = System.currentTimeMillis();
        if (currTime > endTime) {
            return -8;
        }

        String md5 = StringUtil.md5(String.valueOf(endTime) + "|" + vcode + "|" + securityStr);
        String encryptedStr = String.valueOf(endTime) + "|" + vcode + "|" + md5;

        String encryptedStrSrv = (String) session.getAttribute("encryptedStr");
        if (encryptedStr.equals(encryptedStrSrv)) {
            session.removeAttribute("encryptedStr");
            session.removeAttribute("validateEnd");
            session.removeAttribute("lastSend");
            session.setAttribute("validateSuccess", true);
            return 0;
        } else {
            return -1;
        }
    }


}
