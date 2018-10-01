package com.lwhtarena.company.web.handlers;

import com.lwhtarena.company.annotation.Token;
import com.lwhtarena.company.aop.args.CutTags;
import com.lwhtarena.company.web.common.GroupUtil;
import com.lwhtarena.company.web.dao.IGroupDao;
import com.lwhtarena.company.web.dao.IPortalDao;
import com.lwhtarena.company.web.entities.ArticleGroup;
import com.lwhtarena.company.web.entities.Portal;
import com.lwhtarena.company.web.portal.obj.ArticleGroupMapCreateArgs;
import com.lwhtarena.company.web.portal.obj.ImageSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author：liwh
 * @Description:
 * @Date 12:28 2018/8/12
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */

@RequestMapping("/action_agroup")
@Controller
public class GroupHandler {

	/*private static final String SUCCESS = "jsp/result/success";
	private static final String FAILED = "jsp/result/failed";*/

    private static final String GROUPLIST = "jsp/agroup/list";
    private static final String GROUPEDIT = "jsp/agroup/add";
    private static final String ADMINFORBID = "_admin.forbid_";

    @Autowired
    private IGroupDao groupDaoImpl;

    @Autowired
    private IPortalDao portalDaoImpl;

    @Autowired
    private ResourceBundleMessageSource messageSource;

    @ModelAttribute
    public void getArticleGroup(@RequestParam(value = "id", required = false) Long id, Map<String, Object> map) {
        if (id != null && id > 0) {
            map.put("group", groupDaoImpl.findByID(id));
        }
    }


	/*private static void mapCreate(Map<String, Object> map,IGroupDao groupDaoImpl,ArticleGroup group) {

		List<ArticleGroup> glist=groupDaoImpl.queryByParentID(0L,true);

		map.put("preListAll", GroupUtil.prebuild(groupDaoImpl.ht(),glist,null,"├ ","根"));
		if (group!=null && group.getId()>0L) {
			group=groupDaoImpl.findByID(group.getId());
			map.put("prelist", GroupUtil.prebuild(groupDaoImpl.ht(),glist,group,"├ ","根"));
		}else {
			map.put("prelist", GroupUtil.prebuild(groupDaoImpl.ht(),glist,null,"├ ","根"));
		}

	}*/

    @RequestMapping("/add")
    @Token(ajax=false,token=true,log=true,mark="agroup--<add>",admin=true,failedPage=ADMINFORBID)
    public String add(ArticleGroup group, Errors result, HttpSession session, Map<String, Object> map, CutTags tags) {
        if (group.getId()==0) {
            group.setOpen(true);
            group.setStatus(true);
            group.setClogging(true);
            group.setGather(true);
            group.setStaticPage(true);
            group.setChanged(true);
            Portal portal=portalDaoImpl.query();
            group.setComm(portal.isComm());
            group.setPoll(portal.isPoll());
            if (group.getParent()!=null && group.getParent().getId()==0) {
                group.setParent(null);
            }
            groupDaoImpl.add(group);
        }else {
            groupDaoImpl.modify(group);
        }

        //mapCreate(map,groupDaoImpl,group);

        return "jsp/result/success" ;
    }

    @RequestMapping("/beforeAdd")
    public String beforeAdd(HttpServletRequest request, Map<String, Object> map) {
        ArticleGroup group = new ArticleGroup();
        group.setOpen(true);
        group.setClogging(true);
        group.setStatus(true);
        map.put("group", group);
        String rootTitle=messageSource.getMessage("root.title", null, "Root", null);

        ArticleGroupMapCreateArgs agmca = new ArticleGroupMapCreateArgs();
        agmca.setCurrRoleMask("0");
        agmca.setCurrRoot(null);
        agmca.setFree(true);
        agmca.setGroupDaoImpl(groupDaoImpl);
        agmca.setMap(map);
        agmca.setRootTitle(rootTitle);
        agmca.setStatus(0);
        agmca.setMessageSource(messageSource);
        agmca.setRequest(request);
        GroupUtil.mapCreate(agmca);
        return GROUPEDIT;
    }


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, HttpServletRequest request, Map<String, Object> map) {
        ArticleGroup group = groupDaoImpl.findByID(id);
        map.put("group", group);

        String rootTitle=messageSource.getMessage("root.title", null, "Root", null);
        ArticleGroupMapCreateArgs agmca = new ArticleGroupMapCreateArgs();
        agmca.setCurrRoleMask("0");
        agmca.setCurrRoot(null);
        agmca.setFree(true);
        agmca.setGroupDaoImpl(groupDaoImpl);
        agmca.setMap(map);
        agmca.setRootTitle(rootTitle);
        agmca.setStatus(1);
        agmca.setMessageSource(messageSource);
        agmca.setRequest(request);
        GroupUtil.mapCreate(agmca);
        return GROUPEDIT;
    }



    @ResponseBody
    @RequestMapping("/ipscope")
    @Token(ajax=true,log=true,mark="agroup--<ipscope-change>",admin=true,failedPage=ADMINFORBID)
    public int modifyIpScope(Long id,String val) {
        if (val!=null && !val.trim().equals("") && val.trim().length()>6){
            ArticleGroup group = groupDaoImpl.findByID(id);
            group.setIpVisitAllow(val);
            groupDaoImpl.modify(group);
            return 0;
        }else{
            return -1;
        }


//		mapCreate(map,groupDaoImpl,null);

    }

    @ResponseBody
    @RequestMapping("/jumpurl")
    @Token(ajax=true,log=true,mark="agroup--<jumpurl-change>",admin=true,failedPage=ADMINFORBID)
    public int modifyJumpUrl(Long id,String val) {
        if (val!=null && !val.trim().equals("") && val.trim().length()>6){
            ArticleGroup group = groupDaoImpl.findByID(id);
            group.setJumpToUrl(val);
            groupDaoImpl.modify(group);
            return 0;
        }else{
            return -1;
        }
//		mapCreate(map,groupDaoImpl,null);

    }

    @ResponseBody
    @RequestMapping(value = "/del/{id}")
    @Token(ajax=true,log=true,mark="agroup--<del>",admin=true,failedPage=ADMINFORBID)
    public int del(@PathVariable(value = "id", required = true) Long id,HttpSession session,Map<String, Object> map) {
        boolean result;

        result=groupDaoImpl.delByID(id);
        if (result) {
            return 0;
        }else {
            return -1;
        }
//		mapCreate(map,groupDaoImpl,null);

    }

    @ResponseBody
    @RequestMapping(value = "/smartWH/{id}")
    public ImageSize smartWH(@PathVariable(value = "id", required = true) Long id) {
        ImageSize is = new ImageSize();
        ArticleGroup ag=groupDaoImpl.findByID(id);
        if (ag==null) {
            is.setWidth(0);
            is.setHeight(0);
        }else {
            is.setWidth(ag.getCw());
            is.setHeight(ag.getCh());
        }
        return is;

    }

    @RequestMapping("/list")
    @Token(ajax=false,admin=true,failedPage=ADMINFORBID)
    public String list(HttpServletRequest request,Map<String, Object> map) {
        String rootTitle=messageSource.getMessage("root.title", null, "Root", null);
        ArticleGroupMapCreateArgs agmca = new ArticleGroupMapCreateArgs();
        agmca.setCurrRoleMask("0");
        agmca.setCurrRoot(null);
        agmca.setFree(true);
        agmca.setGroupDaoImpl(groupDaoImpl);
        agmca.setMap(map);
        agmca.setRootTitle(rootTitle);
        agmca.setStatus(0);
        agmca.setMessageSource(messageSource);
        agmca.setRequest(request);
        GroupUtil.mapCreate(agmca);
        return GROUPLIST;
    }

    @ResponseBody
    @RequestMapping(value = "/move/{id}/{offset}")
    @Token(ajax=true,log=true,mark="agroup--<move>",admin=true,failedPage=ADMINFORBID)
    public int modifyOrder(@PathVariable(value = "id", required = true) Long id,@PathVariable(value = "offset", required = true) int offset,HttpServletRequest request,Map<String, Object> map, CutTags tags) {
        ArticleGroup group=groupDaoImpl.findByID(id);
        groupDaoImpl.move(group, offset);

        String rootTitle=messageSource.getMessage("root.title", null, "Root", null);
        ArticleGroupMapCreateArgs agmca = new ArticleGroupMapCreateArgs();
        agmca.setCurrRoleMask("0");
        agmca.setCurrRoot(null);
        agmca.setFree(true);
        agmca.setGroupDaoImpl(groupDaoImpl);
        agmca.setMap(map);
        agmca.setRootTitle(rootTitle);
        agmca.setStatus(1);
        agmca.setMessageSource(messageSource);
        agmca.setRequest(request);
        GroupUtil.mapCreate(agmca);
        return 0;
    }

    @RequestMapping("/map")
    public String map(HttpServletRequest request,Map<String, Object> map) {
        Portal portal =portalDaoImpl.query();
        map.put("portal", portal);

        String rootTitle=messageSource.getMessage("root.title", null, "Root", null);
        ArticleGroupMapCreateArgs agmca = new ArticleGroupMapCreateArgs();
        agmca.setCurrRoleMask("0");
        agmca.setCurrRoot(null);
        agmca.setFree(true);
        agmca.setGroupDaoImpl(groupDaoImpl);
        agmca.setMap(map);
        agmca.setRootTitle(rootTitle);
        agmca.setStatus(1);
        agmca.setMessageSource(messageSource);
        agmca.setRequest(request);
        GroupUtil.mapCreate(agmca);

        return "jsp/agroup/map";
    }

}

