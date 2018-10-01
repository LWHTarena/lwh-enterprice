package com.lwhtarena.company.sys.util;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:38 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class Log4jInit extends HttpServlet {

    public static String outputDir;

    public Log4jInit() {
    }

    public String getOutputDir() {
        return outputDir;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String prefix = config.getServletContext().getRealPath("/");
        String file = config.getInitParameter("log4j");
        String filePath = prefix + file;
        String outputDir = config.getInitParameter("outputDir");
        Log4jInit.outputDir = outputDir;
        Properties props = new Properties();
        String srvRunFile = FileUtil.webinfPath() + "/srvstart";

        try {
            FileInputStream istream = new FileInputStream(filePath);
            props.load(istream);
            istream.close();
            istream = null;
            String logFile = prefix + outputDir + File.separator + props.getProperty("log4j.appender.appender1.File");
            props.setProperty("log4j.appender.appender1.File", logFile);
            PropertyConfigurator.configure(props);
            Logger log = Logger.getLogger(Log4jInit.class);
            FileUtil.writeStringToFile(srvRunFile, "" + System.currentTimeMillis(), false, "UTF-8");
            System.out.println("启动日志成功！");
        } catch (IOException var11) {
            System.out.println("错误日志输出：" + var11);
            System.out.println("启动日志失败！");
        }

    }
}
