package com.lwhtarena.company.web.common;

import com.lwhtarena.company.analyze.util.AnalyzeUtil;
import com.lwhtarena.company.rank.obj.RankUnitPlace;
import com.lwhtarena.company.rank.util.RankUnitUtil;
import com.lwhtarena.company.sys.util.StringUtil;
import com.lwhtarena.company.web.dao.IGroupDao;
import com.lwhtarena.company.web.entities.ArticleGroup;
import com.lwhtarena.company.web.portal.obj.ArticleGroupMapCreateArgs;
import com.lwhtarena.company.web.portal.obj.ArticleGroupNearby;
import com.lwhtarena.company.web.portal.obj.EnvirSet;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
public class GroupUtil {

    public static List<ArticleGroup> prebuild(HibernateTemplate ht, List<ArticleGroup> list, ArticleGroup disabler, String pre, String rootTitle){
        List<ArticleGroup> prelist = new ArrayList<ArticleGroup>();
        if (rootTitle!=null ) {
            ArticleGroup groot = new ArticleGroup();
            groot.setId(0);
            groot.setTitle(rootTitle);
            groot.setName(rootTitle);
            prelist.add(groot);
        }
        for (ArticleGroup g:list) {

            if (disabler == null || (disabler != null && !(g.getFootLeft()>=disabler.getFootLeft() && g.getFootRight() <= disabler.getFootRight()))) {
                RankUnitPlace rup=RankUnitUtil.placeInfInParent(ht, g);
                g.setRup(rup);
                g.setTitle(StringUtil.countStr((g.getOrderStr().length()-1)/3 - 1,"　")+pre+g.getName());

                prelist.add(g);
            }

        }
        return prelist;
    }

    public static List<ArticleGroup> prebuild2(ArticleGroupMapCreateArgs agmca, List<ArticleGroup> list, ArticleGroup disabler, String pre){
        List<ArticleGroup> prelist = new ArrayList<ArticleGroup>();
        String indexFile=agmca.getMessageSource().getMessage("file.html.default", null, "index.html", null);
        String htmlRoot=agmca.getMessageSource().getMessage("group.file.static.root", null, "html", null);
        htmlRoot=agmca.getRequest().getContextPath()+"/"+htmlRoot;

        if (agmca.getRootTitle()!=null ) {
            ArticleGroup groot = new ArticleGroup();
            groot.setId(0);
            groot.setTitle(agmca.getRootTitle());
            groot.setName(agmca.getRootTitle());
            prelist.add(groot);
        }
        List<ArticleGroup> listInMask=mGroupsListCreate(agmca.getGroupDaoImpl(),agmca.getCurrRoleMask());
        //这上面一行
        for (ArticleGroup g:list) {

            if (disabler == null || (disabler != null && !(g.getFootLeft()>=disabler.getFootLeft() && g.getFootRight() <= disabler.getFootRight()))) {
                RankUnitPlace rup=RankUnitUtil.placeInfInParent(agmca.getGroupDaoImpl().ht(), g);
                g.setRup(rup);
                g.setTitle(StringUtil.countStr((g.getOrderStr().length()-1)/3 - 1,"　")+pre+g.getName());


                String htmlFolder = htmlFolder(g,htmlRoot);
                String href;
                href = htmlFolder+indexFile;
                href = StringUtil.strReplace(href, "\\","/");
                g.setUrl(href);

                if (agmca.isFree() || powerChk(g,listInMask)) {
                    prelist.add(g);
                }

            }

        }
        return prelist;
    }

    //根据掩码，生成一个全部的可增加文章的栏目列表
    private static List<ArticleGroup> mGroupsListCreate(IGroupDao groupDaoImpl, String mask){

        List<ArticleGroup> listMAg=new ArrayList<ArticleGroup>();
        Long gid;
        if (mask!=null && mask.indexOf(",a0,") == -1 && !mask.trim().equals("0")) {
            String[] sArray = mask.split(",");
            String spanStr;
            for (int i=0;i<sArray.length;i++){
                spanStr = sArray[i];
                if (spanStr!=null && (spanStr.startsWith("a") || spanStr.startsWith("p"))) {
                    spanStr=spanStr.substring(1,spanStr.length());	//取数字
                    gid = Long.valueOf(spanStr);
                    ArticleGroup ag=groupDaoImpl.findByID(gid);
                    if (statusChk(ag)) {
                        listMAg.add(ag);
                    }


                }
            }

        }
        return listMAg;
    }

    private static boolean powerChk(ArticleGroup g,List<ArticleGroup> maskGoupList) {
        for (ArticleGroup mag:maskGoupList) {
            if (g.isStatus() && g.getFootLeft()<=mag.getFootLeft() && g.getFootRight()>=mag.getFootRight()) {
                return true;
            }
        }
        return false;
    }

    public static List<ArticleGroup> preAndMaskBuild(HibernateTemplate ht,List<ArticleGroup> list,String pre,String rootTitle,String mask){
        List<ArticleGroup> prelist = new ArrayList<ArticleGroup>();
        boolean admin=false;
        boolean addAll=false;
        boolean ignore=false;
//		System.out.println("mask:"+mask);
        if (mask==null || mask.trim().equals("")) {
            ignore=true;				//忽略
        }
        if (!ignore) {
            mask=","+mask+",";
            if (mask!=null && mask.trim().equals("0")) {
                admin=true;
            }
            if (mask!=null && mask.trim().equals("a0")) {
                addAll=true;
            }
        }

        if (rootTitle!=null ) {
            ArticleGroup groot = new ArticleGroup();
            groot.setId(0);
            groot.setTitle(rootTitle);
            groot.setName(rootTitle);

            prelist.add(groot);
        }
        for (ArticleGroup g:list) {

            RankUnitPlace rup=RankUnitUtil.placeInfInParent(ht, g);
            g.setRup(rup);
            g.setTitle(StringUtil.countStr((g.getOrderStr().length()-1)/3 - 1,"　")+pre+g.getName());
            g.setTmp(0);
            if (!ignore) {
                if (!admin) {
                    if (mask!=null) {
                        if (addAll || mask.indexOf(",a"+g.getId()+",") != -1) {
                            g.setTmp(1);
                        }
                        if (mask.indexOf(",p"+g.getId()+",") != -1) {
                            g.setTmp(2);
                        }
                    }
                }else {
                    g.setTmp(2);
                }
            }

            prelist.add(g);

        }
        return prelist;
    }


    public static void mapCreate(ArticleGroupMapCreateArgs agmca) {


        //Map<String, Object> map,IGroupDao groupDaoImpl,ArticleGroup group,String rootTitle,int status,boolean free,String mask
        ArticleGroup group=agmca.getCurrRoot();
        Map<String, Object> map = agmca.getMap();
        List<ArticleGroup> glist=agmca.getGroupDaoImpl().queryByParentID(0L,true,false,0);
        List<ArticleGroup> glist2;
        glist2 = new ArrayList<ArticleGroup>();
        for (ArticleGroup g:glist) {
            switch (agmca.getStatus()) {
                case 1:
                    if (statusChk(g) ) {
                        glist2.add(g);
                    }
                    break;
                case -1:
                    if (!g.isStatus()) {
                        glist2.add(g);
                    }
                    break;
                default:
                    glist2.add(g);
            }

        }


        map.put("preListAll", prebuild2(agmca,glist2,null,"├ "));
        if (group!=null && group.getId()>0L) {
            group=agmca.getGroupDaoImpl().findByID(group.getId());
            map.put("prelist", prebuild2(agmca,glist2,group,"├ "));

        }else {
            map.put("prelist", prebuild2(agmca,glist2,null,"├ "));

        }

    }

    public static void mapGroupMaskCreate(Map<String, Object> map,IGroupDao groupDaoImpl,String rootTitle,String mask,int status) {

        List<ArticleGroup> glist=groupDaoImpl.queryByParentID(0L,true,false,status);

        map.put("preListAll", preAndMaskBuild(groupDaoImpl.ht(),glist,"├ ",rootTitle,mask));


    }


    public static String fmt(String lf,ArticleGroup agroup){
		/*String tail="";
		if (sub) {
			tail="_sub";
		}*/
        lf=AnalyzeUtil.replace(lf, "tag", "id", ""+agroup.getId());
        lf=AnalyzeUtil.replace(lf, "tag", "name", agroup.getName());
        lf=AnalyzeUtil.replace(lf, "tag", "navName", agroup.getName());
        lf=AnalyzeUtil.replace(lf, "tag", "title", agroup.getTitle());
        lf=AnalyzeUtil.replace(lf, "tag", "folder", agroup.getFolder());
        lf=AnalyzeUtil.replace(lf, "tag", "jumpToUrl", agroup.getJumpToUrl());
        lf=AnalyzeUtil.replace(lf, "tag", "staticFolder", ""); 		//静态目录，以后再写

        if (agroup.getVbook()!=null) {
            lf=AnalyzeUtil.replace(lf, "tag", "views", agroup.getVbook().getViewsTotal());
            lf=AnalyzeUtil.replace(lf, "tag", "vbID", agroup.getVbook().getId());
            lf=AnalyzeUtil.replace(lf, "tag", "ips", agroup.getVbook().getIpTotal());
        }else {
            lf=AnalyzeUtil.replace(lf, "tag", "views", "0");
            lf=AnalyzeUtil.replace(lf, "tag", "vbID", "0");
            lf=AnalyzeUtil.replace(lf, "tag", "ips", "0");
        }

        return lf;
    }



    public static String locationStr(EnvirSet es, String href, String sundrieTag, boolean endHref) {
        String locationStr="";
        String currLocation;
        String tmpHref;
        String indexFile=es.getMessageSource().getMessage("file.html.default", null, "index.html", null);
        String htmlRoot=es.getMessageSource().getMessage("group.file.static.root", null, "html", null);
        ArticleGroup agroup=es.getGroupDaoImpl().findByID(es.getGid());
        htmlRoot=es.getRequest().getContextPath()+"/"+htmlRoot;
        currLocation = agroup.getName();
        if (endHref) {
            tmpHref = href;
            tmpHref=AnalyzeUtil.replace(tmpHref, "tag", "folder", agroup.getFolder());
            tmpHref=AnalyzeUtil.replace(tmpHref, "tag", "gid", ""+agroup.getId());
            tmpHref=AnalyzeUtil.replace(tmpHref, "tag", "name", agroup.getName());
            tmpHref = GroupUtil.folderUrl(tmpHref, agroup, htmlRoot,indexFile);
            currLocation = tmpHref;
        }else {
            currLocation = "<a href=\""+es.getRequest().getContextPath()+"/action_show/nav/"+agroup.getId()+"\">"+agroup.getName()+"</a>";	//endHref flase 在当前页，无须链接
        }
        if (agroup.getParent()!=null) {
            boolean con=true;
            ArticleGroup parent;


            while (con) {
                tmpHref = href;
                parent = (ArticleGroup) agroup.getParent();
                if (parent == null) {
                    con=false;
                }else {
                    tmpHref=AnalyzeUtil.replace(tmpHref, "tag", "folder", parent.getFolder());
                    tmpHref=AnalyzeUtil.replace(tmpHref, "tag", "gid", ""+parent.getId());
                    tmpHref=AnalyzeUtil.replace(tmpHref, "tag", "name", parent.getName());
                    tmpHref = GroupUtil.folderUrl(tmpHref, parent, htmlRoot,indexFile);
                    locationStr = tmpHref + sundrieTag + currLocation;

                    agroup = (ArticleGroup) agroup.getParent();
                    currLocation = locationStr;
                }



            }
        }

        return currLocation;
    }

    public static List<ArticleGroupNearby> subOrNearby(IGroupDao groupDaoImpl, long gid, int upper){
        List<ArticleGroupNearby> listAgn= new ArrayList<ArticleGroupNearby>();
        ArticleGroup agcurr=groupDaoImpl.findByID(gid);
        List<ArticleGroup> listAg = groupDaoImpl.queryByParentID(gid, false,false, 1);
        int step=0;
        for (ArticleGroup ag:listAg) {
            step ++;
            ArticleGroupNearby agn = new ArticleGroupNearby();
            agn.setAgroup(ag);
            agn.setProp(0);
            listAgn.add(agn);
            if (upper>0 && step == upper) {
                break;
            }
        }
        /*
         * 取同级的
         */
        boolean root=false;
        long parentID;
        if (agcurr.getParent()!=null) {
            parentID=agcurr.getParent().getId();
        }else {
            parentID=0;
            root=true;
        }
        if (listAgn.size() < upper && agcurr!=null ) {
            listAg = groupDaoImpl.queryByParentID(parentID, false,false, 1);
            for (ArticleGroup ag:listAg) {
                if (ag.getId()==gid) {
                    continue;
                }
                step ++;
                ArticleGroupNearby agn = new ArticleGroupNearby();
                agn.setAgroup(ag);
                agn.setProp(1);
                listAgn.add(agn);
                if (upper>0 && step == upper) {
                    break;
                }
            }
        }

        if (agcurr.getParent()!=null) {
            if (agcurr.getParent().getParent()!=null) {
                parentID=agcurr.getParent().getParent().getId();
            }else {
                parentID=0;
            }
        }else {
            parentID=0;
        }

        if (listAgn.size() < upper && agcurr!=null ) {
            if (parentID==0 && root) {
                return listAgn;
            }
            listAg = groupDaoImpl.queryByParentID(parentID, false,false, 1);
            for (ArticleGroup ag:listAg) {
                step ++;
                ArticleGroupNearby agn = new ArticleGroupNearby();
                agn.setAgroup(ag);
                agn.setProp(2);
                listAgn.add(agn);
                if (upper>0 && step == upper) {
                    break;
                }
            }
        }

        return listAgn;
    }

    /*
     * 根据掩码，判断是否可以审核
     */
    public static boolean auditMaskChk(ArticleGroup ag,String mask) {
        if (mask==null || mask.trim().equals("")) {
            return false;
        }
        if (mask.trim().equals("0")) {
            return true;
        }
        if (ag==null) {
            return false;
        }
        mask = ","+mask+",";
        List<ArticleGroup> agList = new ArrayList<ArticleGroup>();
        agList.add(ag);
        boolean con=true;
        if (ag.getParent()!=null) {
            ArticleGroup parent;


            while (con) {
                parent = (ArticleGroup) ag.getParent();
                if (parent == null) {
                    con=false;
                }else {
                    agList.add(parent);
                    ag = parent;
                }

            }
        }

        for (ArticleGroup g:agList) {
            if (mask.indexOf(",p"+g.getId()+",")!=-1) {
                return true;
            }
        }

        return false;
    }

    public static boolean poolchk(ArticleGroup ag) {

        if (!ag.isPoll()) {
            return false;
        }
        boolean con=true;
        if (ag.getParent()!=null) {
            ArticleGroup parent;


            while (con) {
                parent = (ArticleGroup) ag.getParent();
                if (parent == null) {
                    con=false;
                }else {
                    if (!parent.isPoll()) {
                        return false;
                    }
                    ag = parent;
                }

            }
        }

        return true;


    }

    public static boolean statusChk(ArticleGroup ag) {
        if (ag==null || !ag.isStatus()) {
            return false;
        }
        boolean con=true;
        if (ag.getParent()!=null) {
            ArticleGroup parent;

            while (con) {
                parent = (ArticleGroup) ag.getParent();
                if (parent == null) {
                    con=false;
                }else {
                    if (!parent.isStatus()) {
                        return false;
                    }
                    ag = parent;
                }

            }
        }

        return true;
    }

    public static String htmlFolder(ArticleGroup ag,String htmlRoot) {
        String dir=null;
        if (ag!=null) {
            if (htmlRoot!=null && !htmlRoot.trim().equals("")) {
                htmlRoot=htmlRoot+File.separator;
            }
            if (ag.getFolder() != null
                    && !ag.getFolder().trim().equals("")) {
                dir = htmlRoot+ag.getFolder()+File.separator;
            } else {
                dir = htmlRoot+"g" + ag.getId()+File.separator;
            }
        }

        return dir;
    }

    public static String folderUrl(String lf,ArticleGroup agroup,String htmlRoot,String indexName){
        String htmlFolder = htmlFolder(agroup,htmlRoot);
        String href;
        href = htmlFolder+indexName;
        href = StringUtil.strReplace(href, "\\","/");
        lf=AnalyzeUtil.replace(lf, "tag", "href", href);
        return lf;
    }
}
