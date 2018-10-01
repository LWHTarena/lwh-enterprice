package com.lwhtarena.company.web.common;

import com.lwhtarena.company.analyze.util.AnalyzeUtil;
import com.lwhtarena.company.web.entities.Portal;

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
public class PortalUtil {

    public static String fmt(String lf,Portal portal) {

        lf = AnalyzeUtil.replace(lf, "tag", "portalName", portal.getName());
        lf = AnalyzeUtil.replace(lf, "tag", "portalFullName", portal.getFullName());
        lf = AnalyzeUtil.replace(lf, "tag", "portalKeyWords", portal.getKeyWords());
        lf = AnalyzeUtil.replace(lf, "tag", "portalDescription", portal.getDescription());
        lf = AnalyzeUtil.replace(lf, "tag", "portalHost", portal.getHost());
        lf = AnalyzeUtil.replace(lf, "tag", "portalUrl", portal.getUrl());
        lf = AnalyzeUtil.replace(lf, "tag", "portalWelcomeStr", portal.getWelcomeStr());
        lf = AnalyzeUtil.replace(lf, "tag", "portalViews", ""+portal.getVbook().getViewsTotal());
        lf = AnalyzeUtil.replace(lf, "tag", "portalIps", ""+portal.getVbook().getIpTotal());

        return lf;
    }
}
