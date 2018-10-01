package com.lwhtarena.company.web.handlers;

import com.lwhtarena.company.sys.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author：liwh
 * @Description:
 * @Date 12:58 2018/8/12
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
@RequestMapping("/action_system")
@Controller
public class SystemHandler {

    @RequestMapping("/random")
    @ResponseBody
    public String findTemplate() throws Exception{

        return StringUtil.uuidStr();
    }

}