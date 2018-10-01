package com.lwhtarena.company.web.common;

import com.lwhtarena.company.analyze.util.AnalyzeUtil;
import com.lwhtarena.company.sys.util.HttpUtil;
import com.lwhtarena.company.sys.util.StringUtil;
import com.lwhtarena.company.sys.util.TimeUtil;
import com.lwhtarena.company.web.dao.IUserArtsCountDao;
import com.lwhtarena.company.web.entities.Article;
import com.lwhtarena.company.web.entities.Role;
import com.lwhtarena.company.web.entities.User;
import com.lwhtarena.company.web.entities.UserArtsCount;
import com.lwhtarena.company.web.portal.obj.EnvirSet;

import java.util.Date;

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
public class UserUtil {

    public static long loginRefresh(EnvirSet es,String username,String password){
        long uid= -1;

        if (username!=null && !username.trim().equals("")){
            User user=new User();
            user.setUsername(username);
            user.setPassword(password);
            String passwordPlain=user.getPassword();
            user=es.getUserDaoImpl().login(user);
            if (user!=null){
                uid=user.getId();
                HttpUtil.saveCookie(es.getMessageSource(), es.getResponse(), "uid_lerx", ""+uid);
                Role role=user.getRole();
                if (role!=null) {
                    role=es.getRoleDaoImpl().findByID(role.getId());
                }
                if (role!=null) {
                    HttpUtil.saveCookie(es.getMessageSource(), es.getResponse(), "rid_lerx", ""+user.getId());
                    HttpUtil.saveCookie(es.getMessageSource(), es.getResponse(), "username_lerx", user.getUsername());
                    HttpUtil.saveCookie(es.getMessageSource(), es.getResponse(), "password_lerx", passwordPlain);
                    HttpUtil.saveCookie(es.getMessageSource(), es.getResponse(), "role_name_lerx", role.getName());
                    if (user.getRole()!=null && user.getRole().getMask()!=null && user.getRole().getMask().trim().equals("0")) {
                        HttpUtil.saveCookie(es.getMessageSource(), es.getResponse(), "admin0_lerx", "true");
                    }else {
                        HttpUtil.saveCookie(es.getMessageSource(), es.getResponse(), "admin0_lerx", "false");
                    }
                }


            }else {
                HttpUtil.clearCookie(es.getRequest(), es.getResponse(), "username_lerx");
                HttpUtil.clearCookie(es.getRequest(), es.getResponse(), "password_lerx");
                HttpUtil.clearCookie(es.getRequest(), es.getResponse(), "uid_lerx");
                HttpUtil.clearCookie(es.getRequest(), es.getResponse(), "rid_lerx");
                HttpUtil.clearCookie(es.getRequest(), es.getResponse(), "admin0_lerx");
                HttpUtil.clearCookie(es.getRequest(), es.getResponse(), "role_name_lerx");
            }
        }

        return uid;

    }

    public static String maskChkForArtAdd(EnvirSet es) {
        String username=HttpUtil.getCookie(es.getMessageSource(), es.getRequest(), "username_lerx");
        String password=HttpUtil.getCookie(es.getMessageSource(), es.getRequest(), "password_lerx");
        long uid=loginRefresh(es,username,password);
        if (uid<=0) {
            return null;
        }else {
            String uidStr=HttpUtil.getCookie(es.getMessageSource(), es.getRequest(), "uid_lerx");
            if (!StringUtil.isNumber(uidStr)) {
                return null;
            }
            User user=es.getUserDaoImpl().findByID(Long.valueOf(uidStr));
            if (user == null) {
                return null;
            }
            Role role = user.getRole();

            if (role ==null) {
                return null;
            }
            String mask=role.getMask();
            if (mask==null || mask.trim().equals("")) {
                return null;
            }
            if (mask.trim().equals("0")) {
                return "0";
            }
            if (mask.indexOf("p") != -1) {
                return "p";
            }else if (mask.indexOf("a") != -1) {
                return "a";
            }else {
                return "";
            }
        }

    }


    public static String fmt(String lf,User user,String imgHtmlTemplet){
        lf=AnalyzeUtil.replace(lf, "tag", "uid", ""+user.getId());
        lf=AnalyzeUtil.replace(lf, "tag", "username", user.getUsername());
        lf=AnalyzeUtil.replace(lf, "tag", "truename", user.getTruename());
        lf=AnalyzeUtil.replace(lf, "tag", "email", user.getEmail());
        if (user.getRole()!=null) {
            lf=AnalyzeUtil.replace(lf, "tag", "roleID", ""+user.getRole().getId());
            lf=AnalyzeUtil.replace(lf, "tag", "rid", ""+user.getRole().getId());
            lf=AnalyzeUtil.replace(lf, "tag", "role", user.getRole().getName());
        }else {
            lf=AnalyzeUtil.replace(lf, "tag", "roleID", "0");
            lf=AnalyzeUtil.replace(lf, "tag", "rid", "0");
            lf=AnalyzeUtil.replace(lf, "tag", "role", "");
        }

        String tmp;
        if (imgHtmlTemplet!=null && !imgHtmlTemplet.trim().equals("")) {

            if (user.getAvatarUrl()!=null && !user.getAvatarUrl().equals("")) {
                tmp=imgHtmlTemplet;
                tmp=AnalyzeUtil.replace(tmp, "tag", "src", user.getAvatarUrl());
                lf=AnalyzeUtil.replace(lf, "tag", "avatar", tmp);
            }else {
                lf=AnalyzeUtil.replace(lf, "tag", "avatar", "");
            }



        }else {
            lf=AnalyzeUtil.replace(lf, "tag", "avatar", "");
        }
        return lf;
    }

    /*
     * 检测是否前台管理员
     */
    public static boolean isadmin(User user) {
        if (user!=null && user.getRole()!=null && user.getRole().getMask()!=null && user.getRole().getMask().trim().equals("0")) {
            return true;
        }else {
            return false;
        }

    }


    public static void uacModify(User u, String timeKeyStr, long addTime,
                                 IUserArtsCountDao userArtsCountDaoImp, int col, int v,
                                 boolean quarter) {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
                timeKeyStr);
        int timeKey;
        if (!quarter) {
            timeKey = Integer.valueOf(formatter.format(addTime));
        } else {
            timeKey = Integer.parseInt(timeKeyStr);
        }
        UserArtsCount uac = userArtsCountDaoImp.findByUK(u, timeKey);
        if (uac == null) {
            uac = new UserArtsCount();
            uac.setUser(u);
            uac.setTimeKey(timeKey);
            uac.setArtsTotal(0);
            uac.setArtsPassed(0);

        }
        if (col == 1) {
            if (v == 1) {
                uac.setArtsPassed(uac.getArtsPassed() + 1);
            } else if (v == -1) {
                if (uac.getArtsPassed() > 0) {
                    uac.setArtsPassed(uac.getArtsPassed() - 1);
                }
            }
        } else if (col == 0) {
            if (v == 1) {
                uac.setArtsTotal(uac.getArtsTotal() + 1);
            } else if (v == -1) {
                if (uac.getArtsTotal() > 0) {
                    uac.setArtsTotal(uac.getArtsTotal() - 1);
                }
            }
        }

        userArtsCountDaoImp.modify(uac);
    }


    /*
     * 更新用户的文章发表和通数数
     */
    public static void uacUpdate(User user, Article article, IUserArtsCountDao userArtsCountDaoImp, int tag, int v) {
        UserUtil.uacModify(user, "yyyy", article.getCreationTime(),userArtsCountDaoImp, tag, v,false);
        UserUtil.uacModify(user, "yyyyMM", article.getCreationTime(),userArtsCountDaoImp, tag, v,false);
        Date weekStamp=TimeUtil.firstDayAtWeek(new Date(article.getCreationTime()));
        UserUtil.uacModify(user, "yyyyMMdd", weekStamp.getTime(),userArtsCountDaoImp, tag, v,false);
        UserUtil.uacModify(user, String.valueOf(TimeUtil.quarter(article.getCreationTime())), article.getCreationTime(),userArtsCountDaoImp, tag, v,true);
    }
}
