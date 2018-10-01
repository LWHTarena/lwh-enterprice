package com.lwhtarena.company.web.handlers;

import com.lwhtarena.company.annotation.Token;
import com.lwhtarena.company.hql.entities.Rs;
import com.lwhtarena.company.hql.util.RsUtil;
import com.lwhtarena.company.sys.obj.ResFile;
import com.lwhtarena.company.sys.util.DesUtils;
import com.lwhtarena.company.sys.util.FileUtil;
import com.lwhtarena.company.sys.util.ListUtil;
import com.lwhtarena.company.sys.util.MavUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author：liwh
 * @Description:
 * @Date 12:53 2018/8/12
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */

@RequestMapping("/action_res")
@Controller
public class ResHandler {

    private static final String ADMINFORBID = "_admin.forbid_";

    @Autowired
    private ResourceBundleMessageSource messageSource;

    @RequestMapping("/list")
    @Token(ajax = false, admin = true, failedPage = ADMINFORBID, msgKey = "fail.permission")
    public ModelAndView list(HttpServletRequest request, Map<String, Object> map, @RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (page==null) {
            page=1;
        }
        if (pageSize==null) {
            pageSize=10;
        }
        String separator;
        if (File.separator.equals("/")) {
            separator = File.separator;
        } else {
            separator = "\\";
        }
        String resRealPath=FileUtil.webinfPath()+separator+messageSource.getMessage("res", null, "res", null);
        resRealPath = FileUtil.repairFilePath(resRealPath);
        List<ResFile> lfArray=FileUtil.getResFiles(messageSource,resRealPath);
        List<Object> lobject=new ArrayList<Object>();
        for (Object o1:lfArray) {
            lobject.add(o1);
        }
        List<Object> currpage=ListUtil.paging(lobject, page, pageSize);
        List<ResFile> currPageFiles=new ArrayList<ResFile>();
        for (Object o2:currpage) {
            ResFile lf=(ResFile) o2;
            currPageFiles.add(lf);
        }
        Rs rs=RsUtil.init(page, pageSize, lfArray.size());

        rs.setList(currPageFiles);
        map.put("rs", rs);
        map.put("pageUrl", "/action_res/list");
        return MavUtil.mav("jsp/res/list", "");

    }

    @RequestMapping("/details")
    @Token(ajax = false, admin = true, failedPage = ADMINFORBID, msgKey = "fail.permission")
    public ModelAndView details(Map<String, Object> map, @RequestParam(value = "securityStr", required = true) String securityStr) {
        String realPath=DesUtils.decrypt(messageSource, securityStr,true);
        String log;
        String charset=messageSource.getMessage("charset", null, "UTF-8", null);
//		System.out.println("System.getProperty(\"file.encoding\"):"+System.getProperty("file.encoding"));
        log = FileUtil.readLargeFile4(realPath,null, charset);
        map.put("txt", log);
        map.put("file", securityStr);
        return MavUtil.mav("jsp/res/details", "");
    }

    @RequestMapping("/modify")
    @Token(ajax = false,log=true, mark="res--<modify>",admin = true, failedPage = ADMINFORBID, msgKey = "fail.permission")
    public ModelAndView modify(String filepath,String filecontent) {
        String realPath=DesUtils.decrypt(messageSource, filepath,true);
        String charset=messageSource.getMessage("charset", null, "utf-8", null);
        FileUtil.writeStringToFile(realPath,  filecontent, false, charset);
        return MavUtil.mav("jsp/result/success", "");
    }

}
