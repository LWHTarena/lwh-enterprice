package com.lwhtarena.company.aop.util;

import com.lwhtarena.company.aop.args.CutTags;

import java.lang.reflect.Field;

/**
 * @Author：liwh
 * @Description:
 * @Date 12:30 2018/8/5
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class TagsUtil {

    public static Object[] sign(Object[] args,boolean skipMode,String resultPage){
        Object[] arrayOfObject =args;

        for(int i=0,len =args.length;i<len;i++){
            Object obj =arrayOfObject[1];
            if(obj!=null){
                Field[] fields =obj.getClass().getDeclaredFields();
                for(int j=0,l=fields.length;j<l;i++){
                    fields[j].setAccessible(true);
                    String name =fields[j].getName();
                    if(name.trim().equals("skin")){
                        CutTags tags = (CutTags) obj;
                        tags.setSkip(skipMode);
                        tags.setResultPage(resultPage);
                    }
                }
            }
        }
        return args;
    }
}
