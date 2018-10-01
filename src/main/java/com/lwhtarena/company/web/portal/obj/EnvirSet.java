package com.lwhtarena.company.web.portal.obj;

import com.lwhtarena.company.web.dao.*;
import org.springframework.context.support.ResourceBundleMessageSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class EnvirSet {

    private long startTime;
    private long gid;
    private long aid;
    private long uid;
    private int page;
    private int pageSize;
    private String searchKey;
    private ResourceBundleMessageSource messageSource;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private IPortalDao portalDaoImpl;
    private IGroupDao groupDaoImpl;
    private IArticleDao articleDaoImpl;
    private IUserDao userDaoImpl;
    private IUserArtsCountDao userArtsCountDaoImp;
    private IRoleDao roleDaoImpl;
    private ITemplatePortalMainDao templatePortalMainDaoImpl;
    private IVisitorIPRecordDao visitorIPRecordDaoImpl;
    private IVisitorsBookDao visitorsBookDaoImpl;
    private IVisitArchivesDao visitArchivesDaoImpl;
    private IVisitorCountInPeriodDao visitorCountInPeriodDaoImpl;
    private IUploadedFileDao uploadedFileDaoImpl;
    private IHtmlFileStaticDao htmlFileStaticDaoImpl;
    private ICommentThreadDao commentThreadDaoImpl;

    public long getStartTime() {
        return startTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public long getGid() {
        return gid;
    }
    public void setGid(long gid) {
        this.gid = gid;
    }
    public long getAid() {
        return aid;
    }
    public void setAid(long aid) {
        this.aid = aid;
    }
    public long getUid() {
        return uid;
    }
    public void setUid(long uid) {
        this.uid = uid;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public String getSearchKey() {
        return searchKey;
    }
    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
    public ResourceBundleMessageSource getMessageSource() {
        return messageSource;
    }
    public void setMessageSource(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }
    public HttpServletRequest getRequest() {
        return request;
    }
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    public HttpServletResponse getResponse() {
        return response;
    }
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
    public IPortalDao getPortalDaoImpl() {
        return portalDaoImpl;
    }
    public void setPortalDaoImpl(IPortalDao portalDaoImpl) {
        this.portalDaoImpl = portalDaoImpl;
    }
    public IUserDao getUserDaoImpl() {
        return userDaoImpl;
    }
    public void setUserDaoImpl(IUserDao userDaoImpl) {
        this.userDaoImpl = userDaoImpl;
    }
    public IUserArtsCountDao getUserArtsCountDaoImp() {
        return userArtsCountDaoImp;
    }
    public void setUserArtsCountDaoImp(IUserArtsCountDao userArtsCountDaoImp) {
        this.userArtsCountDaoImp = userArtsCountDaoImp;
    }
    public IGroupDao getGroupDaoImpl() {
        return groupDaoImpl;
    }
    public void setGroupDaoImpl(IGroupDao groupDaoImpl) {
        this.groupDaoImpl = groupDaoImpl;
    }
    public ITemplatePortalMainDao getTempletPortalMainDaoImpl() {
        return templatePortalMainDaoImpl;
    }
    public void setTempletPortalMainDaoImpl(ITemplatePortalMainDao templatePortalMainDaoImpl) {
        this.templatePortalMainDaoImpl = templatePortalMainDaoImpl;
    }
    public IArticleDao getArticleDaoImpl() {
        return articleDaoImpl;
    }
    public void setArticleDaoImpl(IArticleDao articleDaoImpl) {
        this.articleDaoImpl = articleDaoImpl;
    }
    public IRoleDao getRoleDaoImpl() {
        return roleDaoImpl;
    }
    public void setRoleDaoImpl(IRoleDao roleDaoImpl) {
        this.roleDaoImpl = roleDaoImpl;
    }
    public IVisitorIPRecordDao getVisitorIPRecordDaoImpl() {
        return visitorIPRecordDaoImpl;
    }
    public void setVisitorIPRecordDaoImpl(IVisitorIPRecordDao visitorIPRecordDaoImpl) {
        this.visitorIPRecordDaoImpl = visitorIPRecordDaoImpl;
    }
    public IVisitorsBookDao getVisitorsBookDaoImpl() {
        return visitorsBookDaoImpl;
    }
    public void setVisitorsBookDaoImpl(IVisitorsBookDao visitorsBookDaoImpl) {
        this.visitorsBookDaoImpl = visitorsBookDaoImpl;
    }
    public IVisitArchivesDao getVisitArchivesDaoImpl() {
        return visitArchivesDaoImpl;
    }
    public void setVisitArchivesDaoImpl(IVisitArchivesDao visitArchivesDaoImpl) {
        this.visitArchivesDaoImpl = visitArchivesDaoImpl;
    }
    public IVisitorCountInPeriodDao getVisitorCountInPeriodDaoImpl() {
        return visitorCountInPeriodDaoImpl;
    }
    public void setVisitorCountInPeriodDaoImpl(IVisitorCountInPeriodDao visitorCountInPeriodDaoImpl) {
        this.visitorCountInPeriodDaoImpl = visitorCountInPeriodDaoImpl;
    }
    public IUploadedFileDao getUploadedFileDaoImpl() {
        return uploadedFileDaoImpl;
    }
    public void setUploadedFileDaoImpl(IUploadedFileDao uploadedFileDaoImpl) {
        this.uploadedFileDaoImpl = uploadedFileDaoImpl;
    }
    public IHtmlFileStaticDao getHtmlFileStaticDaoImpl() {
        return htmlFileStaticDaoImpl;
    }
    public void setHtmlFileStaticDaoImpl(IHtmlFileStaticDao htmlFileStaticDaoImpl) {
        this.htmlFileStaticDaoImpl = htmlFileStaticDaoImpl;
    }
    public ICommentThreadDao getCommentThreadDaoImpl() {
        return commentThreadDaoImpl;
    }
    public void setCommentThreadDaoImpl(ICommentThreadDao commentThreadDaoImpl) {
        this.commentThreadDaoImpl = commentThreadDaoImpl;
    }
}
