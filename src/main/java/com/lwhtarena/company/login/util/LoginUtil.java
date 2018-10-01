package com.lwhtarena.company.login.util;

import com.lwhtarena.company.app.SafeStrUtil;
import com.lwhtarena.company.login.val.LoginSignUnitValues;
import com.lwhtarena.company.sys.util.HttpUtil;
import com.lwhtarena.company.sys.util.StringUtil;
import com.lwhtarena.company.user.entities.User;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.orm.hibernate5.HibernateTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 18:25 2018/8/5
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class LoginUtil {

    private static String className(User user) {
        if (user == null) {
            user = new User();
        }

        String entitiesClass = user.getClass().getName();
        return entitiesClass;
    }

    public static User find(HibernateTemplate ht, User user) {
        String key = user.getUsername();
        String entitiesClass = className(user);
        if (key == null) {
            return null;
        } else {
            String hql;
            if (StringUtil.emailTest(key)) {
                hql = "from " + entitiesClass + " a where a.email=?0";
            } else if (StringUtil.isNumber(key) && key.length() == 11) {
                hql = "from " + entitiesClass + " a where a.mobile=?0";
            } else {
                hql = "from " + entitiesClass + " a where a.username=?0";
            }

            List<User> lu = (List<User>) ht.find(hql, new Object[]{key});
            if (lu.size() > 0) {
                ht.clear();
                return (User)lu.get(0);
            } else {
                return null;
            }
        }
    }

    public static User findByUsername(HibernateTemplate ht, String username) {
        User user = null;
        String entitiesClass = className((User)user);
        if (username == null) {
            return null;
        } else {
            String hql = "from " + entitiesClass + " a where a.username=?0";
            List<User> lu = (List<User>) ht.find(hql, new Object[]{username});
            if (lu.size() > 0) {
                ht.clear();
                return (User)lu.get(0);
            } else {
                return null;
            }
        }
    }

    public static User findByUID(HibernateTemplate ht, long uid) {
        User user = null;
        String entitiesClass = className(user);
        if (uid <= 0L) {
            return null;
        } else {
            user = (User)ht.get(entitiesClass, uid);
            return user;
        }
    }

    public static User safeSave(HibernateTemplate ht, User user) {
        String entitiesClass = className(user);
        User udb = (User)ht.get(entitiesClass, user.getId());
        ht.clear();
        user.setUsername(udb.getUsername());
        user.setSalt(udb.getSalt());
        user.setPassword(udb.getPassword());
        user.setCreateIP(udb.getCreateIP());
        user.setCreateTime(udb.getCreateTime());
        user.setLastLoginIP(udb.getLastLoginIP());
        user.setLastLoginTime(udb.getLastLoginTime());
        user.setPwdAtCreate(udb.getPwdAtCreate());
        if (udb.getUuid() != null && !udb.getUuid().trim().equals("")) {
            user.setUuid(udb.getUuid());
        } else {
            user.setUuid(StringUtil.uuidStr());
        }

        ht.update(user);
        return user;
    }

    public static User login(HibernateTemplate ht, User user) {
        User udb = find(ht, user);
        if (udb == null) {
            return null;
        } else {
            user.setSalt(udb.getSalt());
            user.setUsername(udb.getUsername());
            user = passwordMD5Create(user);
            user.setId(udb.getId());
            return compare(ht, user, true) && udb.isState() ? udb : null;
        }
    }

    public static boolean compare(HibernateTemplate ht, User user, boolean md5) {
        String entitiesClass = className(user);
        ht.clear();
        User udb = (User)ht.get(entitiesClass, user.getId());
        ht.clear();
        user.setSalt(udb.getSalt());
        if (!md5) {
            user = passwordMD5Create(user);
        }

        return user.getPassword().equals(udb.getPassword());
    }

    public static User create(User user, boolean adminInit) {
        user = passwordMD5Create(user);
        if (adminInit) {
            user.setPwdAtCreate(user.getPassword());
        }

        user.setUuid(StringUtil.uuidStr());
        user.setCreateTime(System.currentTimeMillis());
        return user;
    }

    public static User passwordMD5Create(User user) {
        String salt = user.getSalt();
        if (salt == null || salt.trim().equals("")) {
            salt = StringUtil.randomString(6);
            user.setSalt(salt);
        }

        String pw = user.getPassword();
        String password = StringUtil.md5(StringUtil.md5(pw).toLowerCase().concat(salt)).toLowerCase();
        user.setPassword(password);
        return user;
    }

    public static boolean userLoginChk(ResourceBundleMessageSource messageSource, HttpSession session) {
        LoginSignUnitValues lsuv = read(session);
        String loginstateStr = lsuv.getUserID();
        return loginstateStr != null && lsuv.getUsername() != null && loginstateStr.equals("true");
    }

    public static boolean adminLoginChk(ResourceBundleMessageSource messageSource, HttpSession session) {
        LoginSignUnitValues lsuv = read(session);
        return lsuv.getRoleMask() != null && lsuv.getRoleMask().trim().equals("0");
    }

    public int checkUsername(String username, String reservedWords) {
        if (username == null) {
            return -1;
        } else if (username.trim().equals("")) {
            return -2;
        } else {
            String[] sArray = reservedWords.split(",");
            username = username.trim();

            for(int step = 0; step < sArray.length; ++step) {
                String tmp = sArray[step].trim();
                if (tmp.equalsIgnoreCase(username)) {
                    return -3;
                }
            }

            return 0;
        }
    }

    public static LoginSignUnitValues read(HttpSession session) {
        LoginSignUnitValues lvn = new LoginSignUnitValues();
        lvn.setRoleID((String)session.getAttribute("session_role_id"));
        lvn.setRoleName((String)session.getAttribute("session_role_name"));
        lvn.setRoleMask((String)session.getAttribute("session_role_mask"));
        lvn.setUserID((String)session.getAttribute("session_user_uid"));
        lvn.setUsername((String)session.getAttribute("session_user_username"));
        lvn.setAdminID((String)session.getAttribute("session_admin_uid"));
        lvn.setAdminName((String)session.getAttribute("session_admin_username"));
        return lvn;
    }

    public static void save(HttpSession session, LoginSignUnitValues lsuv) {
        session.setAttribute("session_role_id", lsuv.getRoleID());
        session.setAttribute("session_role_name", lsuv.getRoleName());
        session.setAttribute("session_role_mask", lsuv.getRoleMask());
        session.setAttribute("session_user_uid", lsuv.getUserID());
        session.setAttribute("session_user_username", lsuv.getUsername());
        session.setAttribute("session_admin_uid", lsuv.getAdminID());
        session.setAttribute("session_admin_username", lsuv.getAdminName());
    }

    public static void clearAdmin(HttpSession session) {
        session.removeAttribute("session_admin_uid");
        session.removeAttribute("session_admin_username");
    }

    public static void clearUser(HttpSession session) {
        session.removeAttribute("session_user_uid");
        session.removeAttribute("session_user_username");
        session.removeAttribute("session_user_password");
    }

    public static String getAdminID(ResourceBundleMessageSource messageSource, HttpSession session) {
        LoginSignUnitValues lsuv = read(session);
        return lsuv.getAdminID() != null ? lsuv.getAdminID() : null;
    }

    public static String getAdminUsername(ResourceBundleMessageSource messageSource, HttpSession session) {
        LoginSignUnitValues lsuv = read(session);
        if (lsuv.getAdminID() != null) {
            return lsuv.getAdminID().trim().equals("0") ? "Config_Admin" : lsuv.getAdminName();
        } else {
            return null;
        }
    }

    public static boolean admin0Chk(ResourceBundleMessageSource messageSource, HttpSession session) {
        LoginSignUnitValues lsuv = read(session);
        return lsuv.getAdminID() != null && lsuv.getAdminID().trim().equals("0");
    }

    public static boolean adminChk(ResourceBundleMessageSource messageSource, HttpSession session) {
        LoginSignUnitValues lsuv = read(session);
        return lsuv.getAdminID() != null;
    }

    public static long uid(ResourceBundleMessageSource messageSource, HttpServletRequest request) {
        String username = HttpUtil.getCookie(messageSource, request, "username_lerx");
        if (username != null && !username.trim().equals("")) {
            String uid = HttpUtil.getCookie(messageSource, request, "uid_lerx");
            return uid != null && !uid.trim().equals("") && StringUtil.isNumber(uid) ? Long.valueOf(uid) : -1L;
        } else {
            return -1L;
        }
    }

    public static long userChk(ResourceBundleMessageSource messageSource, HttpSession session) {
        LoginSignUnitValues lsuv = read(session);
        String uidStr = lsuv.getUserID();
        String username = lsuv.getUsername();
        if (uidStr != null && username != null) {
            long uid = Long.valueOf(uidStr);
            return uid > 0L && username != null && !username.trim().equals("") ? uid : -1L;
        } else {
            return -1L;
        }
    }

    public static boolean vcodeChk(ResourceBundleMessageSource messageSource, HttpServletRequest request, HttpSession session, String vcode, String keyHead) {
        String safeSessionStr = SafeStrUtil.getstr(messageSource, request, keyHead);
        return vcode != null && vcode.trim().equals(session.getAttribute(safeSessionStr));
    }
}
