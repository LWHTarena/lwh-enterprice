package com.lwhtarena.company.web.handlers;

import com.lwhtarena.company.annotation.Token;
import com.lwhtarena.company.aop.args.CutTags;
import com.lwhtarena.company.sys.util.MavUtil;
import com.lwhtarena.company.web.common.GroupUtil;
import com.lwhtarena.company.web.dao.IGroupDao;
import com.lwhtarena.company.web.dao.IRoleDao;
import com.lwhtarena.company.web.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author：liwh
 * @Description:
 * @Date 12:55 2018/8/12
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */

@RequestMapping("/action_role")
@Controller
public class RoleHandler {

	/*private static final String SUCCESS = "jsp/result/success";
	private static final String FAILED = "jsp/result/failed";*/

    private static final String ADMINFORBID = "_admin.forbid_";

    @Autowired
    private IGroupDao groupDaoImpl;

    @Autowired
    private ResourceBundleMessageSource messageSource;

    @Autowired
    private IRoleDao roleDaoImpl;

    @ModelAttribute
    public void getRole(@RequestParam(value = "id", required = false) Long id, Map<String, Object> map) {
        if (id != null && id>0) {
            map.put("role", roleDaoImpl.findByID(id));
//			System.out.println("从数据库获取对象");
        }
    }


    //增加
    @ResponseBody
    @RequestMapping("/add")
    @Token(ajax = true,log=true,mark="role--<add>",admin=true,failedPage = ADMINFORBID,msgKey="fail.permission")
    public int add(String name,Map<String, Object> map){
        if (name==null || name.trim().equals("")) {
            return -12;
        }

        if (roleDaoImpl.findByName(name)!=null) {
            return -10;
        }
        Role role=null;
        role=new Role();
        role.setDef(false);
        role.setStatus(true);
        role.setName(name.trim());
        role=roleDaoImpl.add(role);
        if (role==null) {
            return -1;
        }
        return 0;

    }


    //修改
    @RequestMapping("/chg")
    @Token(ajax = false,log=true,mark="role--<change>",admin=true,failedPage = ADMINFORBID,msgKey="fail.permission")
    public String modify(@Valid Role role, Errors result, Map<String, Object> map){
        if (roleDaoImpl.findByNameOutID(role.getName(), role.getId())!=null) {
            map.put("error", messageSource.getMessage("fail.exists.name", null, "This name has been used by other roles!", null));
            return "jsp/result/failed";
        }
        roleDaoImpl.modify(role);
        return "jsp/result/success";

    }

    //删除
    @ResponseBody
    @RequestMapping("/del")
    @Token(ajax = true,log=true,mark="role--<del>",admin=true,failedPage = ADMINFORBID,msgKey="fail.permission")
    public boolean del(Long id,HttpSession session) {
        boolean result=false;
        /*
         * 检查是否有下级人员
         */
        result=roleDaoImpl.delByID(id);

        return result;
    }

    //进入修改页面
    @RequestMapping("/edit")
    @Token(ajax = false,admin=true,failedPage = ADMINFORBID,msgKey="fail.permission")
    public String edit(Map<String, Object> map,Long id) {

        Role role = roleDaoImpl.findByID(id);
        map.put("role", role);
		/*String maskAll=role.getMask();
		if (maskAll==null){
			maskAll="";
		}
		String[] sArray = maskAll.split(",");
		boolean detail=false,ad=false,donate=false;
		for (int i = 0; i < sArray.length; i++) {
			if (sArray[i].equals("detail")){
				detail=true;
			}
			if (sArray[i].equals("ad")){
				ad=true;
			}
			if (sArray[i].equals("donate")){
				donate=true;
			}
		}
		map.put("detail", detail);
		map.put("ad", ad);
		map.put("donate", donate);*/
        return "jsp/role/add";

    }

    /*
     * 权限码
     */
    @RequestMapping("/mask")
    @Token(ajax = false,log=true,mark="role--<mask>",admin=true,failedPage = ADMINFORBID,msgKey="fail.permission")
    public ModelAndView mask(long id, Map<String, Object> map) {
        String rootTitle=messageSource.getMessage("root.title", null, "Root", null);
        Role role = roleDaoImpl.findByID(id);
        if (role==null) {
            return MavUtil.mav("jsp/result/error", "error");
        }
        String mask=role.getMask();
        GroupUtil.mapGroupMaskCreate(map,groupDaoImpl,rootTitle,mask,1);
        boolean admin=false;
        boolean addAll=false;
        if (mask!=null && mask.trim().equals("0")) {
            admin=true;
        }
        if (mask!=null) {
            mask=","+mask+",";
            if (mask.indexOf(",a0,")!= -1) {
                addAll=true;
            }


        }
        map.put("admin", admin);
        map.put("add0", addAll);
        map.put("roleID", role.getId());

        return MavUtil.mav("jsp/role/mask", "success");
    }

    protected List<String> getParameterValues(HttpServletRequest request, String str){
        String[] arr = request.getParameterValues(str);
        List<String> result = new ArrayList<String>();
        if(null != arr && arr.length > 0){
            for (int i = 0; i < arr.length; i++) {
                result.add(arr[i]);
            }
        }
        return result;
    }

    @RequestMapping(value="/maskchg")
    @Token(ajax = false,log=true,mark="role--<mask-change>",admin=true,failedPage = ADMINFORBID,msgKey="fail.permission")
    public ModelAndView modifyMask(HttpServletRequest request,Map<String, Object> map,long id,boolean admin,boolean add0, @RequestParam(required = false, value = "list[]") String[] mkey) {
//		String[] agmask=request.getParameterValues("agmask");
		/*List<String> aaa=getParameterValues(request,"agmask");
		System.out.println("aaa.size():"+aaa.size());*/
        List<String> mvalueList=new ArrayList<String> ();
        Map<?, ?> map2 = request.getParameterMap();
        String value;
        for (Object key : map2.keySet()) {
            if (key.toString().startsWith("mvalue")){
                value= request.getParameter(key.toString());
                if (value!=null && !value.trim().equals("")) {
                    mvalueList.add(value);
                }
            }
//		    System.out.println(key+":"+request.getParameter(key.toString()));
        }
        Role role = roleDaoImpl.findByID(id);

        String maskCode="";
        if (admin) {
            maskCode="0";
        }else {
            if (add0) {
                maskCode+="a0";
            }

            if (mvalueList != null && mvalueList.size() > 0) {
                for (int i = 0; i < mvalueList.size(); i++) {
                    if (mvalueList.get(i)!=null && !mvalueList.get(i).equals("")) {
                        if (add0 && mvalueList.get(i).trim().startsWith("a")) {
                            continue;
                        }
                        if (maskCode.trim().equals("")) {
                            maskCode+=mvalueList.get(i);
                        }else {
                            maskCode+=","+mvalueList.get(i);
                        }
                    }
                }
            }

        }

        role.setMask(maskCode);
        roleDaoImpl.modify(role);
        return MavUtil.mav("jsp/result/success", "");
    }


    /*
     * 列表
     */
    @RequestMapping("/list")
    @Token(ajax = false,admin=true,failedPage = ADMINFORBID,msgKey="fail.permission")
    public ModelAndView list(Map<String, Object> map,HttpSession session,CutTags tags){
        List<Role> list = roleDaoImpl.queryAllAndCounts();
        map.put("pageUrl", "/action_role/list");
        map.put("roles", list);
        return MavUtil.mav("jsp/role/list", "success");

    }

}

