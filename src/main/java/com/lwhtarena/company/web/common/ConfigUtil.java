package com.lwhtarena.company.web.common;

import com.lwhtarena.company.sys.util.FileUtil;
import com.lwhtarena.company.sys.util.StringUtil;
import org.springframework.context.support.ResourceBundleMessageSource;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

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
public class ConfigUtil {

    public static boolean mailChk(ResourceBundleMessageSource messageSource,String mail){

        if (mail==null || mail.trim().equals("") || !StringUtil.emailTest(mail)){
            return false;
        }

        String[] mailtmp = mail.trim().split("@");

        String mailSuffixes = mailtmp[1];


        String suffixes = messageSource.getMessage("mail.send.allow.suffix", null,"", null);

        if (suffixes==null || suffixes.trim().equals("") || suffixes.trim().equals("null")){
            return true;
        }else{
            String[] suffix = suffixes.trim().split(",");
            String tmp;
            for (int i = 0; i < suffix.length; i++) {

                tmp = suffix[i];
                if (tmp==null){
                    tmp="";
                }
                tmp=tmp.trim();
                if (mailSuffixes.equalsIgnoreCase(tmp)){
                    return true;
                }
            }
        }
        return false;

    }


    public static String configContentsByComma(String filename,String charset){

        String classesPath = FileUtil.classesPath();
        String file = classesPath + filename;
        File f = new File(file);
        String tempString = null,str;
        if (f.exists()) {
            tempString="";
            String strn="\n";
            BufferedReader reader;
            try {
                FileInputStream fis=new FileInputStream(f);
                InputStreamReader isr = new InputStreamReader(fis,charset);
                reader = new BufferedReader(isr);
				/*reader = new BufferedReader(new InputStreamReader(new FileInputStream(
				        f), charset));*/
                while ((str = reader.readLine()) != null) {
                    tempString+=str+strn;
                }
//							tempString = reader.readLine();
                fis.close();
                isr.close();
                reader.close();
                fis=null;
                isr=null;
                reader=null;
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (tempString!=null){
            tempString=tempString.trim();
            tempString=StringUtil.strReplace(tempString, "\n", ",");
            tempString=StringUtil.strReplace(tempString, ",,", ",");
        }
        return tempString;

    }

    public static String getAdminFolder(ResourceBundleMessageSource messageSource){
        String folder=messageSource.getMessage("admin.folder", null, "admin", null);
        return folder;
    }

    public static String getAvatarNullFile(ResourceBundleMessageSource messageSource,HttpServletRequest request) {
        String contextPath=request.getContextPath();
        if (contextPath==null || contextPath.trim().equals("")) {
            contextPath="/";
        }else {
            if (contextPath.endsWith("/")) {
                contextPath+="/";
            }
        }
        return contextPath+messageSource.getMessage("img.avatar.null", null, "images/login/noavatar_small.gif", null);

    }
}
