package com.lwhtarena.company.sys.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:41 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class ObjUtil {

    public static void objectXmlEncoder(Object obj, String fileName) throws FileNotFoundException, IOException, Exception {
        File fo = new File(fileName);
        if (fo.exists()) {
            fo.delete();
        }

        FileOutputStream fos = new FileOutputStream(fo);
        XMLEncoder encoder = new XMLEncoder(fos);
        encoder.writeObject(obj);
        encoder.flush();
        encoder.close();
        fos.flush();
        fos.close();
        encoder = null;
        fos = null;
    }

    public static List objectXmlDecoder(String objSource) throws FileNotFoundException, IOException, Exception {
        List objList = new ArrayList();
        File fin = new File(objSource);
        FileInputStream fis = new FileInputStream(fin);
        XMLDecoder decoder = new XMLDecoder(fis);
        Object obj = null;

        try {
            while((obj = decoder.readObject()) != null) {
                objList.add(obj);
            }
        } catch (Exception var7) {
            ;
        }

        fis.close();
        decoder.close();
        fis = null;
        decoder = null;
        return objList;
    }

    public static Object copy(Object oldObj) {
        Object obj = null;

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(oldObj);
            out.flush();
            out.close();
            bos.close();
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bis);
            obj = in.readObject();
            in.close();
            bis.close();
            out = null;
            in = null;
            bis = null;
            bos = null;
        } catch (IOException var6) {
            var6.printStackTrace();
        } catch (ClassNotFoundException var7) {
            var7.printStackTrace();
        }

        return obj;
    }

    public static Object getProperty(Object beanObj, String property) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        PropertyDescriptor pd = new PropertyDescriptor(property, beanObj.getClass());
        Method getMethod = pd.getReadMethod();
        return getMethod.invoke(beanObj);
    }

    public static Object setProperty(Object beanObj, String property, Object value) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        PropertyDescriptor pd = new PropertyDescriptor(property, beanObj.getClass());
        Method setMethod = pd.getWriteMethod();
        return setMethod.invoke(beanObj, value);
    }
}
