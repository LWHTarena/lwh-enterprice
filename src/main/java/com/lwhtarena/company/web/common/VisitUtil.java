package com.lwhtarena.company.web.common;

import com.lwhtarena.company.ip.util.IPUtil;
import com.lwhtarena.company.sys.util.HttpUtil;
import com.lwhtarena.company.sys.util.StringUtil;
import com.lwhtarena.company.sys.util.TimeUtil;
import com.lwhtarena.company.web.dao.IVisitArchivesDao;
import com.lwhtarena.company.web.entities.VisitArchives;
import com.lwhtarena.company.web.entities.VisitorCountInPeriod;
import com.lwhtarena.company.web.entities.VisitorIPRecord;
import com.lwhtarena.company.web.entities.VisitorsBook;
import com.lwhtarena.company.web.portal.obj.EnvirSet;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;

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
public class VisitUtil {

    /*
     * 智能更新访问统计
     * mask为三位数字符串 以1和0区分
     * 百位数：总量
     * 十位：下级总量
     * 个位：自身
     */
    public static void visitorRefresh(EnvirSet is, VisitorsBook vbook, String mask, String url, String referer) {

        HttpServletRequest request=is.getRequest();
        if (request==null) {
            request=HttpUtil.currRequest();
        }
        String ip=IPUtil.getRealRemotIP(request);
        String spanHours=is.getMessageSource().getMessage("ip.record.nosame.span.hours", null, "6", null);
        long datetimePoint,currWholePointTime;
        if (StringUtil.isNumber(spanHours)) {
            datetimePoint=System.currentTimeMillis() - Long.valueOf(spanHours)*1000*60*60 ;
        }else {
            datetimePoint=System.currentTimeMillis() - 6*1000*60*60 ;
        }

        Calendar cal = Calendar.getInstance();


        int hourNow = cal.get(Calendar.HOUR_OF_DAY);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        currWholePointTime = cal.getTimeInMillis();

        List<VisitorIPRecord> viprList=is.getVisitorIPRecordDaoImpl().findSameIPAfterDatetime(vbook.getId(), datetimePoint, ip);
        VisitorIPRecord vipr=null;
        boolean ipRecordsExists=false;
        if (viprList!=null && viprList.size()>0) {
            ipRecordsExists=true;

        }

        //从当前整点时间开始查找ip
        List<VisitorIPRecord> viprList2=is.getVisitorIPRecordDaoImpl().findSameIPAfterDatetime(vbook.getId(), currWholePointTime, ip);


        //每日记录器
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");

        String dayStr=formatter.format(System.currentTimeMillis());
        int dayKey=Integer.valueOf(dayStr);
        VisitArchives va=is.getVisitArchivesDaoImpl().findByDayKey(vbook.getId(), dayKey);
        if (va==null) {
            va = new VisitArchives();
            va .setDayKey(dayKey);
            va.setVbook(vbook);
            va = is.getVisitArchivesDaoImpl().add(va);
        }




        VisitorCountInPeriod vcip=is.getVisitorCountInPeriodDaoImpl().findByPeriod(va.getId(), hourNow);
        if (vcip==null){
            vcip = new VisitorCountInPeriod();
            vcip.setHour(hourNow);
            vcip.setVa(va);
            vcip=is.getVisitorCountInPeriodDaoImpl().add(vcip);
        }

        va.setViews(va.getViews()+1);
        vcip.setTotalView(vcip.getTotalView()+1);
        //
        boolean total=false;
        boolean sub=false;
        boolean own=false;
        String flag;
        if (mask!=null && !mask.trim().equals("")) {
            flag=mask.substring(mask.length()-1, mask.length());
//			System.out.println("oox："+flag);
            if (Integer.valueOf(flag) ==1) {
                own=true;
            }

            flag=mask.substring(mask.length()-2, mask.length()-1);
//			System.out.println("oxo："+flag);
            if (Integer.valueOf(flag) ==1) {
                sub=true;
            }

            flag=mask.substring(mask.length()-3, mask.length()-2);
//			System.out.println("xoo："+flag);
            if (Integer.valueOf(flag) ==1) {
                total=true;
            }

        }
        String queryString;

        //如果从当前整点时间开始未有ip记录
        if (viprList2==null || viprList2.isEmpty() || viprList2.size()<=0){
            vcip.setTotalIP(vcip.getTotalIP()+1);
        }

        //如果从规定的前面时间段，如6小时内没有ip记录
        if (!ipRecordsExists) {
            va.setIps(va.getIps()+1);
            vipr = new VisitorIPRecord();
            vipr.setIp(ip);
            vipr.setVisitDatetime(System.currentTimeMillis());
            vipr.setVbook(vbook);

//			IPLocation ipl	=	IPUtil.queryQQWry(ip);
//			String ipfrom	=	ipl.getCountry() + " | " + ipl.getArea();
//			vipr.setIpfrom(ipfrom);
            if (referer==null || referer.trim().equals("")) {
                vipr.setReffer(request.getHeader("Referer"));
            }else {
                vipr.setReffer(referer);
            }

            if (url==null || url.trim().equals("")) {
                queryString=request.getQueryString();
                if (queryString!=null && !queryString.trim().equals("")) {
                    queryString="?"+queryString;
                }else {
                    queryString="";
                }
                vipr.setVisitUrl(request.getRequestURL()+queryString);
            }else {
                vipr.setVisitUrl(url);
            }

            is.getVisitorIPRecordDaoImpl().add(vipr);
            if (total) {
                vbook.setIpTotal(vbook.getIpTotal()+1);
            }
            if (sub) {
                vbook.setIpSub(vbook.getIpSub()+1);
            }
            if (own) {
                vbook.setIpOwn(vbook.getIpOwn()+1);
            }


        }else {
            vipr=viprList.get(0);
            if (referer==null || referer.trim().equals("")) {
                vipr.setReffer(request.getHeader("Referer"));
            }else {
                vipr.setReffer(referer);
            }
            vipr.setVisitDatetime(System.currentTimeMillis());

            if (url==null || url.trim().equals("")) {
                queryString=request.getQueryString();
                if (queryString!=null && !queryString.trim().equals("")) {
                    queryString="?"+queryString;
                }else {
                    queryString="";
                }
                vipr.setVisitUrl(request.getRequestURL()+queryString);
            }else {
                vipr.setVisitUrl(url);
            }

            is.getVisitorIPRecordDaoImpl().modify(vipr);
        }

        if (total) {
            vbook.setViewsTotal(vbook.getViewsTotal()+1);
        }
        if (sub) {
            vbook.setViewsSub(vbook.getViewsSub()+1);
        }
        if (own) {
            vbook.setViewsOwn(vbook.getViewsOwn()+1);
        }
        is.getVisitArchivesDaoImpl().modify(va);
        is.getVisitorsBookDaoImpl().modify(vbook);
        is.getVisitorCountInPeriodDaoImpl().modify(vcip);

    }


    public static VisitArchives visitQuery(long vid, int dayKey, IVisitArchivesDao visitArchivesDaoImpl) {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
        String currDayStr=formatter.format(System.currentTimeMillis());
        int currDayKey=Integer.valueOf(currDayStr);
        if (dayKey==0) {			//如果=0，则于当前
            dayKey=currDayKey;
        }else if (dayKey<0){		//如果<0则，计算
            long datetime=TimeUtil.cal(System.currentTimeMillis(),3,dayKey);

            dayKey=Integer.valueOf(formatter.format(datetime));
        }
        VisitArchives va = visitArchivesDaoImpl.findByDayKey(vid, dayKey);
        if (va==null) {
            va=new VisitArchives();
        }
        return va;
    }
}
