package com.lwhtarena.company.web.common;

import com.lwhtarena.company.sys.obj.FileInf;
import com.lwhtarena.company.web.entities.UploadedFile;

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
public class UploadUtil {

    public static UploadedFile coverFromFi(FileInf fi){
        UploadedFile uf = new UploadedFile();
        uf.setUrl(fi.getUrl());
        uf.setFullPath(fi.getFullPath());
        uf.setExt(fi.getExt());
        uf.setNameNoExt(fi.getNameNoExt());
        uf.setRealPath(fi.getRealPath());
        uf.setSize(fi.getSize());
        uf.setUploadDatetime(fi.getAddtime());
        uf.setMd5(fi.getMd5());
        return uf;
    }

}
