package com.lwhtarena.company.web.portal.obj;

import com.lwhtarena.company.web.dao.IUploadedFileDao;
import com.lwhtarena.company.web.dao.IUserDao;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
public class UploadArgs {

    private ResourceBundleMessageSource messageSource;
    private IUserDao userDaoImpl;
    private IUploadedFileDao uploadedFileDaoImpl;
    private MultipartFile file;
    private String targetFile;
    private String title;
    private Boolean intact;
    private Integer cw;
    private Integer ch;
    private HttpServletRequest request;
    private HttpSession session;
    private long uid;

    public ResourceBundleMessageSource getMessageSource() {
        return messageSource;
    }
    public void setMessageSource(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }
    public IUserDao getUserDaoImpl() {
        return userDaoImpl;
    }
    public void setUserDaoImpl(IUserDao userDaoImpl) {
        this.userDaoImpl = userDaoImpl;
    }
    public IUploadedFileDao getUploadedFileDaoImpl() {
        return uploadedFileDaoImpl;
    }
    public void setUploadedFileDaoImpl(IUploadedFileDao uploadedFileDaoImpl) {
        this.uploadedFileDaoImpl = uploadedFileDaoImpl;
    }
    public MultipartFile getFile() {
        return file;
    }
    public void setFile(MultipartFile file) {
        this.file = file;
    }
    public String getTargetFile() {
        return targetFile;
    }
    public void setTargetFile(String targetFile) {
        this.targetFile = targetFile;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Boolean getIntact() {
        return intact;
    }
    public void setIntact(Boolean intact) {
        this.intact = intact;
    }
    public Integer getCw() {
        return cw;
    }
    public void setCw(Integer cw) {
        this.cw = cw;
    }
    public Integer getCh() {
        return ch;
    }
    public void setCh(Integer ch) {
        this.ch = ch;
    }
    public HttpServletRequest getRequest() {
        return request;
    }
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    public HttpSession getSession() {
        return session;
    }
    public void setSession(HttpSession session) {
        this.session = session;
    }
    public long getUid() {
        return uid;
    }
    public void setUid(long uid) {
        this.uid = uid;
    }
}
