package com.lwhtarena.company.web.common;

import com.lwhtarena.company.analyze.util.AnalyzeUtil;
import com.lwhtarena.company.sys.obj.DatetimeSpanSuffixTxt;
import com.lwhtarena.company.sys.util.TimeUtil;
import com.lwhtarena.company.web.entities.CommentThread;
import com.lwhtarena.company.web.entities.Poll;
import com.lwhtarena.company.web.entities.User;
import com.lwhtarena.company.web.portal.obj.TempletCommentFmtRequisite;

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
public class CommUtil {

    public static String fmt(TempletCommentFmtRequisite tcfr, CommentThread ct) {
        String lf=tcfr.getLf();
        String funHtmlPass=tcfr.getFunHtmlPass();
        String funHtmlDel=tcfr.getFunHtmlDel();
        DatetimeSpanSuffixTxt dsst = tcfr.getDsst();

        if (ct.isDeleted()) {
            funHtmlDel = " --deleted--";
        }

        if (ct.isStatus()) {
            funHtmlPass = " --passed--";
        }


        if (tcfr.isMaster()) {
            lf=AnalyzeUtil.replace(lf, "tag", "del", funHtmlDel);
            lf =AnalyzeUtil.replace(lf, "tag", "pass", funHtmlPass);
        }else {
            lf=AnalyzeUtil.replace(lf, "tag", "del", "");
            lf =AnalyzeUtil.replace(lf, "tag", "pass", "");
        }
        lf =AnalyzeUtil.replace(lf, "tag", "content", ct.getContent());
        lf =AnalyzeUtil.replace(lf, "tag", "subject", ct.getSubject());
        lf =AnalyzeUtil.replace(lf, "tag", "replies", ""+ct.getReplies());
        lf =AnalyzeUtil.replace(lf, "tag", "views", ""+ct.getViews());
        lf =AnalyzeUtil.replace(lf, "tag", "id", ""+ct.getId());
        lf =AnalyzeUtil.replace(lf, "tag", "cid", ""+ct.getId());
        lf =AnalyzeUtil.replace(lf, "tag", "bid", ""+ct.getCb().getId());
        lf =AnalyzeUtil.replace(lf, "tag", "ip", ct.getIp());
        lf=AnalyzeUtil.replace(lf, "tag", "addTime", ct.getOccurDatetime());
        lf =AnalyzeUtil.replace(lf, "tag", "timesLosted", TimeUtil.statTimesLosted(ct.getOccurDatetime(),dsst));

        lf = speakerFmt(lf,ct.getUser(),tcfr);

        String replyAdd=tcfr.getReplyAdd();
        if (ct.getParent()!=null) {
            replyAdd = speakerFmt(replyAdd,ct.getParent().getUser(),tcfr);

        }else {
            replyAdd="";
        }
        lf=AnalyzeUtil.replace(lf, "tag", "replyAdd", replyAdd);


        /*
         * 投票处理
         */

        Poll poll =  ct.getPoll();
        lf=AnalyzeUtil.replace(lf, "tag", "pollID", ""+poll.getId());
        lf=AnalyzeUtil.replace(lf, "tag", "pollAgrees", ""+poll.getAgrees());
        lf=AnalyzeUtil.replace(lf, "tag", "pollAntis", ""+poll.getAntis());
        lf=AnalyzeUtil.replace(lf, "tag", "pollPassbys", ""+poll.getPassbys());

        return lf;
    }

    private static String speakerFmt(String lf, User user, TempletCommentFmtRequisite tcfr) {
        String herdSrc;
        if (user == null) {
            herdSrc=tcfr.getHeadDef();
            lf =AnalyzeUtil.replace(lf, "tag", "username", tcfr.getAnonymity());
            lf =AnalyzeUtil.replace(lf, "tag", "UID", "-1");
            lf =AnalyzeUtil.replace(lf, "tag", "uid", "-1");
            lf=AnalyzeUtil.replace(lf, "tag", "avatarSrc", tcfr.getAvatarNull());

        }else {
            if (user.getAvatarUrl()!=null && !user.getAvatarUrl().trim().equals("")) {
                herdSrc=user.getAvatarUrl();
                lf=AnalyzeUtil.replace(lf, "tag", "avatarSrc", user.getAvatarUrl());
            }else {
                herdSrc=tcfr.getHeadDef();
                lf=AnalyzeUtil.replace(lf, "tag", "avatarSrc", tcfr.getAvatarNull());
            }

            lf =AnalyzeUtil.replace(lf, "tag", "username", user.getUsername());
            lf =AnalyzeUtil.replace(lf, "tag", "UID", ""+user.getId());
            lf =AnalyzeUtil.replace(lf, "tag", "uid", ""+user.getId());
        }

        String tmp;
        if (tcfr.getImgHtmlTemplet()!=null && !tcfr.getImgHtmlTemplet().trim().equals("")) {

            if (user!=null &&user.getAvatarUrl()!=null && !user.getAvatarUrl().equals("")) {
                tmp=tcfr.getImgHtmlTemplet();
                tmp=AnalyzeUtil.replace(tmp, "tag", "src", herdSrc);
                lf=AnalyzeUtil.replace(lf, "tag", "avatar", tmp);
            }else {
                lf=AnalyzeUtil.replace(lf, "tag", "src", herdSrc);
                lf=AnalyzeUtil.replace(lf, "tag", "avatar", tcfr.getAvatarNull());
            }



        }else {
            lf=AnalyzeUtil.replace(lf, "tag", "avatar", tcfr.getAvatarNull());
        }

        tmp=AnalyzeUtil.replace(lf, "tag", "herdSrc", herdSrc);
        return lf;
    }
}
