package com.lwhtarena.company.aop.util;

import com.lwhtarena.company.annotation.Token;
import org.aspectj.lang.JoinPoint;

import java.lang.reflect.Method;

/**
 * @Author：liwh
 * @Description:
 * @Date 12:39 2018/8/5
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class JoinPointUtil {
    public static String currMethod(JoinPoint joinPoint){
     Class<?> targetClass =null;
     String targetName =joinPoint.getTarget().getClass().getName();
     String methodName =joinPoint.getSignature().getName();
     Object[] arguments =joinPoint.getArgs();

     try {
         targetClass = Class.forName(targetName);
     }catch (ClassNotFoundException e){
         e.printStackTrace();
     }

        Method[] methods =targetClass.getMethods();
     for(int i=0,len =methods.length;i<len;i++){
         Method method =methods[i];
         if(method.getName().equals(methodName)){
             Class[] clazz =method.getParameterTypes();
             if((clazz !=null) && (clazz.length == arguments.length) && (method.getAnnotation(Token.class)!=null)){
                 return method.getName();
             }
         }
     }

     return null;
    }
}
