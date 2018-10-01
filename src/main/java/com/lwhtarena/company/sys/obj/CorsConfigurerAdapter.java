package com.lwhtarena.company.sys.obj;

import com.lwhtarena.company.sys.util.FileUtil;
import com.lwhtarena.company.sys.util.StringUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author：liwh
 * @Description:
 * @Date 22:49 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 * spring5.0 之后已废弃WebMvcConfigurerAdapter
 * 替代者：继承 WebMvcConfigurationSupport
 *        实现 WebMvcConfigurer
 *
 * 官方推荐使用 WebMvcConfigurer
 *
 *
 * WebMvcConfigurerAdapter 的作用是进行SpringMVC的一些配置
 * 在Spring中，为了减少xml的配置，引入了@Configuration注解。 不经controller直接处理
 *
 */
@Configuration
public class CorsConfigurerAdapter implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String configFile = FileUtil.classesPath() + "allowed_origins.conf";
        FileInputStream fis =null;
        InputStreamReader isr =null;
        BufferedReader reader =null;
        try {
            fis = new FileInputStream(configFile);
            isr = new InputStreamReader(fis, "utf8");
            reader = new BufferedReader(isr);
            String strLine;
            while (StringUtil.isNotNull(strLine = reader.readLine())) {
                if (StringUtil.isNotNull(strLine)
                        &&  !StringUtil.isEmpty(strLine)) {
                    strLine = StringUtil.clear65279(strLine);
                    strLine = strLine.trim();
                    registry.addMapping(strLine).allowedOrigins(new String[]{"*"});
                }
            }
            fis.close();
            isr.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(StringUtil.isNotNull(reader)){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(StringUtil.isNotNull(isr)){
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(StringUtil.isNotNull(fis)){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }
}
