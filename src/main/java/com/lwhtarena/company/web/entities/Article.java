package com.lwhtarena.company.web.entities;


import com.lwhtarena.company.web.interceptor.ArticleIndexStatusInterceptor;
//import org.hibernate.search.annotations.*;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * @Author：liwh
 * @Description:
 * @Date 10:49 2018/8/5
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
//@Indexed(interceptor=ArticleIndexStatusInterceptor.class)
//@Analyzer(impl = IKAnalyzer.class)
public class Article {

//    @DocumentId
    private long id;
    private ArticleGroup agroup;
//    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.YES)
    private String subject;
    private String subjectShort;
    private String thumbnail;
    private String titleImg;
//    @Field (index=Index.YES, analyze=Analyze.YES, store=Store.YES)
    private String titleImgTxt;
    private String mediaUrl;
    private String toppic;
    private String extra;
//    @Field (index=Index.YES, analyze=Analyze.YES, store=Store.YES)
    private String content;
    private String synopsis; 				// 简介
//    @Field (index=Index.YES, analyze=Analyze.YES, store=Store.YES)
    private String author;
//    @Field (index=Index.YES, analyze=Analyze.YES, store=Store.YES)
    private String authorDept;
    private String authorEmail;
    private String authorUrl;
    private long views;
    private long creationTime;
    private long lastModifyTime;
    private long lastViewTime;
    private boolean status;
    private int price;
    private boolean soul;
    private boolean topOne;
    private User user;
    private Poll poll;
    private VisitorsBook vbook;
    private CommentBridge cb;
    private HtmlFileStatic hfs;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public ArticleGroup getAgroup() {
        return agroup;
    }
    public void setAgroup(ArticleGroup agroup) {
        this.agroup = agroup;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getMediaUrl() {
        return mediaUrl;
    }
    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getSubjectShort() {
        return subjectShort;
    }
    public void setSubjectShort(String subjectShort) {
        this.subjectShort = subjectShort;
    }
    public String getToppic() {
        return toppic;
    }
    public void setToppic(String toppic) {
        this.toppic = toppic;
    }
    public String getExtra() {
        return extra;
    }
    public void setExtra(String extra) {
        this.extra = extra;
    }
    public String getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public String getTitleImg() {
        return titleImg;
    }
    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }
    public String getTitleImgTxt() {
        return titleImgTxt;
    }
    public void setTitleImgTxt(String titleImgTxt) {
        this.titleImgTxt = titleImgTxt;
    }
    public String getSynopsis() {
        return synopsis;
    }
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getAuthorDept() {
        return authorDept;
    }
    public void setAuthorDept(String authorDept) {
        this.authorDept = authorDept;
    }
    public String getAuthorEmail() {
        return authorEmail;
    }
    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }
    public String getAuthorUrl() {
        return authorUrl;
    }
    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }
    public long getViews() {
        return views;
    }
    public void setViews(long views) {
        this.views = views;
    }
    public long getCreationTime() {
        return creationTime;
    }
    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }
    public long getLastModifyTime() {
        return lastModifyTime;
    }
    public void setLastModifyTime(long lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
    public long getLastViewTime() {
        return lastViewTime;
    }
    public void setLastViewTime(long lastViewTime) {
        this.lastViewTime = lastViewTime;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public boolean isSoul() {
        return soul;
    }
    public void setSoul(boolean soul) {
        this.soul = soul;
    }
    public boolean isTopOne() {
        return topOne;
    }
    public void setTopOne(boolean topOne) {
        this.topOne = topOne;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Poll getPoll() {
        return poll;
    }
    public void setPoll(Poll poll) {
        this.poll = poll;
    }
    public VisitorsBook getVbook() {
        return vbook;
    }
    public void setVbook(VisitorsBook vbook) {
        this.vbook = vbook;
    }
    public CommentBridge getCb() {
        return cb;
    }
    public void setCb(CommentBridge cb) {
        this.cb = cb;
    }
    public HtmlFileStatic getHfs() {
        return hfs;
    }
    public void setHfs(HtmlFileStatic hfs) {
        this.hfs = hfs;
    }

}
