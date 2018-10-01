package com.lwhtarena.company.web.common;

import com.lwhtarena.company.analyze.util.AnalyzeUtil;
import com.lwhtarena.company.sys.util.ConfigReader;
import com.lwhtarena.company.sys.util.FileUtil;
import com.lwhtarena.company.sys.util.StringUtil;
import com.lwhtarena.company.sys.util.SysUtil;
import com.lwhtarena.company.web.entities.Article;
import com.lwhtarena.company.web.portal.obj.FileEl;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.*;
import org.springframework.beans.BeanUtils;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.ReflectionUtils;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class ArticleUtil {


    public static String fmt(String lf, Article article, String imgHtmlTemplet, HttpServletRequest request, int titleLen) {
        lf = AnalyzeUtil.replace(lf, "tag", "id", "" + article.getId());
        lf = AnalyzeUtil.replace(lf, "tag", "aid", "" + article.getId());
        String title=article.getSubject();
        lf = AnalyzeUtil.replace(lf, "tag", "title", title);
        lf = AnalyzeUtil.replace(lf, "tag", "alt", title);
        if (titleLen>0 && title.length()>titleLen) {
            title=title.substring(0, titleLen) + "…";
        }

        lf = AnalyzeUtil.replace(lf, "tag", "subject", title);

        String subjectShort=article.getSubjectShort();
        if (titleLen>0 && subjectShort.length()>titleLen) {
            subjectShort=subjectShort.substring(0, titleLen) + "…";
        }
        if (article.getSubjectShort() != null && !article.getSubjectShort().trim().equals("")) {
            lf = AnalyzeUtil.replace(lf, "tag", "subjectShort", subjectShort);
        } else {
            lf = AnalyzeUtil.replace(lf, "tag", "subjectShort", title);
        }

        lf = AnalyzeUtil.replace(lf, "tag", "navTitle", article.getAgroup().getTitle());
        lf = AnalyzeUtil.replace(lf, "tag", "agTitle", article.getAgroup().getTitle());
        lf = AnalyzeUtil.replace(lf, "tag", "author", article.getAuthor());
        if (article.getUser()!=null) {
            lf = AnalyzeUtil.replace(lf, "tag", "username", article.getUser().getUsername());
            lf = AnalyzeUtil.replace(lf, "tag", "uid", ""+article.getUser().getId());
        }else {
            lf = AnalyzeUtil.replace(lf, "tag", "username", "");
            lf = AnalyzeUtil.replace(lf, "tag", "uid", "0");
        }
        lf = AnalyzeUtil.replace(lf, "tag", "synopsis", article.getSynopsis());
        lf = AnalyzeUtil.replace(lf, "tag", "authorDept", article.getAuthorDept());
        lf = AnalyzeUtil.replace(lf, "tag", "authorEmail", article.getAuthorEmail());
        lf = AnalyzeUtil.replace(lf, "tag", "authorUrl", article.getAuthorUrl());
        lf = AnalyzeUtil.replace(lf, "tag", "content", article.getContent());
        lf = AnalyzeUtil.replace(lf, "tag", "body", "" + article.getContent());
        lf = AnalyzeUtil.replace(lf, "tag", "addTime", article.getCreationTime());
        lf = AnalyzeUtil.replace(lf, "tag", "toppic", article.getToppic());
        lf = AnalyzeUtil.replace(lf, "tag", "extra", article.getExtra());
        lf = AnalyzeUtil.replace(lf, "tag", "views", article.getVbook().getViewsTotal());
        lf = AnalyzeUtil.replace(lf, "tag", "ips", article.getVbook().getIpTotal());
        lf = AnalyzeUtil.replace(lf, "tag", "pollID", "" + article.getPoll().getId());
        lf = AnalyzeUtil.replace(lf, "tag", "poll_id", "" + article.getPoll().getId());
        lf = AnalyzeUtil.replace(lf, "tag", "poll_agrees", "" + article.getPoll().getAgrees());
        lf = AnalyzeUtil.replace(lf, "tag", "poll_antis", "" + article.getPoll().getAntis());
        lf = AnalyzeUtil.replace(lf, "tag", "poll_passbys", "" + article.getPoll().getPassbys());
        lf = AnalyzeUtil.replace(lf, "tag", "pollAgrees", "" + article.getPoll().getAgrees());
        lf = AnalyzeUtil.replace(lf, "tag", "pollAntis", "" + article.getPoll().getAntis());
        lf = AnalyzeUtil.replace(lf, "tag", "pollPassbys", "" + article.getPoll().getPassbys());

        String href;
        if (article.getHfs() != null && article.getHfs().isStatus()) {
            href = article.getHfs().getUrl();
        } else {
            href = request.getContextPath() + "/action_show/art/" + article.getId();
        }

        lf = AnalyzeUtil.replace(lf, "tag", "href", href);

        if (article.getCb() != null) {
            lf = AnalyzeUtil.replace(lf, "tag", "comm_bid", "" + article.getCb().getId());
            lf = AnalyzeUtil.replace(lf, "tag", "commBID", "" + article.getCb().getId());
        } else {
            lf = AnalyzeUtil.replace(lf, "tag", "comm_bid", "0");
            lf = AnalyzeUtil.replace(lf, "tag", "commBID", "0");
        }

        String thumbnail;

        if (article.getThumbnail() != null && !article.getThumbnail().trim().equals("")) {
            thumbnail = article.getThumbnail();
        } else if (article.getTitleImg() != null && !article.getTitleImg().trim().equals("")) {
            thumbnail = article.getTitleImg();
        } else {
            thumbnail = "";
        }

        /*
         * 由于图像需要一段代码，src为空将会产生错误。所以，如果传入一段 img src=格式的模板将避免出现错误
         */
        String tmp;
        if (imgHtmlTemplet != null && !imgHtmlTemplet.trim().equals("")) {

            if (thumbnail != null && !thumbnail.trim().equals("")) {
                tmp = imgHtmlTemplet;
                tmp = AnalyzeUtil.replace(tmp, "tag", "src", thumbnail);
                lf = AnalyzeUtil.replace(lf, "tag", "thumbnail", tmp);
            } else {
                lf = AnalyzeUtil.replace(lf, "tag", "thumbnail", "");
            }

            if (article.getTitleImg() != null && !article.getTitleImg().trim().equals("")) {
                tmp = imgHtmlTemplet;
                tmp = AnalyzeUtil.replace(tmp, "tag", "src", article.getTitleImg());
                lf = AnalyzeUtil.replace(lf, "tag", "imgMain", tmp);
                lf = AnalyzeUtil.replace(lf, "tag", "imgMainTxt", article.getTitleImgTxt());
            } else {
                lf = AnalyzeUtil.replace(lf, "tag", "imgMain", "");
                lf = AnalyzeUtil.replace(lf, "tag", "imgMainTxt", "");
            }

        } else {
            lf = AnalyzeUtil.replace(lf, "tag", "thumbnail", thumbnail);
            lf = AnalyzeUtil.replace(lf, "tag", "imgMain", article.getTitleImg());
        }

        lf = AnalyzeUtil.replace(lf, "tag", "lastModifyTime", article.getLastModifyTime());

        return lf;
    }

    /*
     * 构造静态文件
     */
    public static FileEl feBuild(ResourceBundleMessageSource messageSource, HttpServletRequest request, Article art) {
        FileEl fe = new FileEl();
        String fmtStr = messageSource.getMessage("art.file.static.fmt", null, "", null);
        SimpleDateFormat fm = new SimpleDateFormat(fmtStr);
        String file = fm.format(System.currentTimeMillis());
        file = StringUtil.strReplace(file, "aid", "" + art.getId());
        file = StringUtil.strReplace(file, "gid", "" + art.getAgroup().getId());
        if (art.getAgroup() != null && art.getAgroup().getFolder() != null
                && !art.getAgroup().getFolder().trim().equals("")) {
            file = StringUtil.strReplace(file, "gfolder", "" + art.getAgroup().getFolder());
        } else {
            file = StringUtil.strReplace(file, "gfolder", "g" + art.getAgroup().getId());
        }

        String url = request.getContextPath() + "/" + file;
        String realFile = FileUtil.appPath() + file;
        fe.setUrl(url);
        fe.setRealPath(realFile);

        return fe;
    }

    /**
     * 高亮显示文章
     *
     * @param query
     *            {@link org.apache.lucene.search.Query}
     * @param data
     *            未高亮的数据
     * @param fields
     *            需要高亮的字段
     * @return 高亮数据
     */
    public static List<Article> hightLight(Query query, List<Article> data, String... fields) {
        List<Article> result = new ArrayList<Article>();
        Formatter formatter = new SimpleHTMLFormatter("<b style=\"color:red\">", "</b>");
        QueryScorer queryScorer = new QueryScorer(query);
        Fragmenter fragmenter = new SimpleSpanFragmenter(queryScorer);
        Highlighter highlighter = new Highlighter(formatter, queryScorer);
        highlighter.setTextFragmenter(fragmenter);// 设置成高亮
        // 使用IK中文分词
        Analyzer analyzer = new IKAnalyzer();
        String content;
        for (Article a : data) {
            // 构建新的对象进行返回，避免页面错乱（我的页面有错乱）
            Article article = new Article();
            article.setCreationTime(a.getCreationTime());
            article.setHfs(a.getHfs());
            article.setUser(a.getUser());
            a.setContent(StringUtil.nullAndHtmlFilter(a.getContent()));
            for (String fieldName : fields) {
                // 获得字段值，并给新的文章对象赋值
                Object fieldValue = ReflectionUtils
                        .invokeMethod(BeanUtils.getPropertyDescriptor(Article.class, fieldName).getReadMethod(), a);
                ReflectionUtils.invokeMethod(BeanUtils.getPropertyDescriptor(Article.class, fieldName).getWriteMethod(),
                        article, fieldValue);
                String hightLightFieldValue = null;
                try {
                    hightLightFieldValue = highlighter.getBestFragment(analyzer, fieldName, String.valueOf(fieldValue));
                } catch (Exception e) {
                    throw new RuntimeException("高亮显示关键字失败", e);
                }
                // 如果高亮成功则重新赋值
                if (hightLightFieldValue != null) {
                    ReflectionUtils.invokeMethod(
                            BeanUtils.getPropertyDescriptor(Article.class, fieldName).getWriteMethod(), article,
                            hightLightFieldValue);
                }
            }
            // 赋值ID
            ReflectionUtils.invokeMethod(BeanUtils.getPropertyDescriptor(Article.class, "id").getWriteMethod(), article,
                    a.getId());
            content = article.getContent();

            Pattern pattern = Pattern.compile("</p>(&nbsp;*)<p>");
            Matcher matcher = pattern.matcher(content);
            content = matcher.replaceAll(" ");

            // 任意空白字符
            pattern = Pattern.compile("</p>(\\s*)<p>");
            matcher = pattern.matcher(content);
            content = matcher.replaceAll(" ");

            article.setContent(content);
            result.add(article);
        }
        return result;
    }


    public static void htmlCreate(HttpServletRequest request,ResourceBundleMessageSource messageSource,Article art) {
        File configFile=SysUtil.getConfigFile(messageSource, "app");
        String contextPath= ConfigReader.getKey(configFile,"context.path");
        if (contextPath==null || contextPath.trim().equals("")) {
            contextPath="http://localhost/";
        }
        String strCreateDateTime=messageSource.getMessage("msg.datetime.html.static.create", null, "", null);
        String fromurl=contextPath+"/action_show/art/"+art.getId();
        fromurl=FileUtil.repairUrl(fromurl);
        String charset=messageSource.getMessage("charset", null, "utf-8", null);
        FileUtil.htmlBySniff(fromurl, art.getHfs().getRealPath(), strCreateDateTime,  charset);
    }

    public static Article validate(ResourceBundleMessageSource messageSource, Article article) {
        String charset=messageSource.getMessage("charset", null, "UTF-8", null);
        String filterWords=ConfigUtil.configContentsByComma("filterWords", charset);
        String repTarget="";
        boolean rep=false;
        if (messageSource.getMessage("filter.replace", null, "true", null).trim().equals("true")) {
            rep=true;
            repTarget=messageSource.getMessage("filter.replace.target", null, "*", null);
        }
        article.setSubject(validateAss(article.getSubject(), filterWords, rep, repTarget,true));
        article.setContent(StringUtil.filterByWords(article.getContent(), filterWords, rep, repTarget));
        article.setAuthor(validateAss(article.getAuthor(), filterWords, rep, repTarget,false));
        article.setAuthorDept(validateAss(article.getAuthorDept(), filterWords, rep, repTarget,false));
        article.setAuthorEmail(validateAss(article.getAuthorEmail(), filterWords, rep, repTarget,false));
        article.setAuthorUrl(validateAss(article.getAuthorUrl(), filterWords, rep, repTarget,false));
        article.setSubjectShort(validateAss(article.getSubjectShort(), filterWords, rep, repTarget,false));
        article.setExtra(validateAss(article.getExtra(), filterWords, rep, repTarget,false));
        article.setSynopsis(validateAss(article.getSynopsis(), filterWords, rep, repTarget,false));
        article.setTitleImg(validateAss(article.getTitleImg(), filterWords, rep, repTarget,false));
        article.setTitleImgTxt(validateAss(article.getTitleImgTxt(), filterWords, rep, repTarget,false));
        article.setToppic(validateAss(article.getToppic(), filterWords, rep, repTarget,false));
        article.setThumbnail(validateAss(article.getThumbnail(), filterWords, rep, repTarget,false));

        return article;
    }

    private static String validateAss(String str,String filterWords, boolean rep, String repTarget,boolean nullfilter) {
        if (nullfilter) {
            str=StringUtil.nullAndHtmlFilter(str);
        }else {
            str=StringUtil.htmlFilter(str);
        }

        str=StringUtil.filterByWords(str, filterWords, rep, repTarget);
		/*if (str!=null && !str.trim().equals("")) {
			str = StringUtil.escape(str);
		}*/
        return str;
    }

}
