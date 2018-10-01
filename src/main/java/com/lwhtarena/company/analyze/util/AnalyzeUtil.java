package com.lwhtarena.company.analyze.util;

import com.lwhtarena.company.analyze.vo.DataShowParams;
import com.lwhtarena.company.analyze.vo.FindedDataAnalyzeResult;
import com.lwhtarena.company.sys.util.StringUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author：liwh
 * @Description:
 * @Date 20:32 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class AnalyzeUtil {
    public static DataShowParams analyze(String varstr) {
        DataShowParams dsp = new DataShowParams();
        String pat = "\\s*data\\s*:(\\s*\\w+\\s*),+?.*";
        String datasourceStr = StringUtil.mactcherG1(varstr, pat);
        if (datasourceStr != null) {
            if (datasourceStr.trim().equals("")) {
                return null;
            } else {
                dsp.setDataSource(datasourceStr.trim().toLowerCase());
                pat = ",\\s*formatSource\\s*:(\\s*\\w+\\s*)(,|$)";
                String formatSource = StringUtil.mactcherG1(varstr, pat);
                if (formatSource != null) {
                    if (formatSource.trim().equals("")) {
                        dsp.setFormatSource((String)null);
                    } else {
                        dsp.setFormatSource(formatSource);
                    }
                } else {
                    dsp.setFormatSource((String)null);
                }

                if (dsp.getFormatSource() == null && dsp.getLoopFormatStr() == null) {
                    return null;
                } else {
                    pat = ",\\s*format\\s*:(\\s*\\w+\\s*)(,|$)";
                    String lfStr = StringUtil.mactcherG1(varstr, pat);
                    if (lfStr != null) {
                        if (lfStr.trim().equals("")) {
                            dsp.setLoopFormatStr((String)null);
                        } else {
                            dsp.setLoopFormatStr(lfStr);
                        }
                    } else {
                        dsp.setLoopFormatStr((String)null);
                    }

                    pat = ",\\s*fid\\s*:(\\s*[0-9]+\\s*)(,|$)";
                    String fidStr = StringUtil.mactcherG1(varstr, pat);
                    if (fidStr != null) {
                        dsp.setFid(Integer.parseInt(fidStr.trim()));
                    } else {
                        dsp.setFid(0);
                    }

                    pat = ",\\s*subFormatSource\\s*:(\\s*\\w+\\s*)(,|$)";
                    String subFormatSource = StringUtil.mactcherG1(varstr, pat);
                    if (subFormatSource != null) {
                        if (subFormatSource.trim().equals("")) {
                            dsp.setSubFormatSource((String)null);
                        } else {
                            dsp.setSubFormatSource(subFormatSource);
                        }
                    } else {
                        dsp.setSubFormatSource((String)null);
                    }

                    pat = ",\\s*subFormat\\s*:(\\s*\\w+\\s*)(,|$)";
                    String sublfStr = StringUtil.mactcherG1(varstr, pat);
                    if (sublfStr != null) {
                        if (sublfStr.trim().equals("")) {
                            dsp.setSubLoopFormatStr((String)null);
                        } else {
                            dsp.setSubLoopFormatStr(sublfStr);
                        }
                    } else {
                        dsp.setSubLoopFormatStr((String)null);
                    }

                    pat = ",\\s*subFid\\s*:(\\s*[0-9]+\\s*)(,|$)";
                    String subFidStr = StringUtil.mactcherG1(varstr, pat);
                    if (subFidStr != null) {
                        dsp.setSubFid(Integer.parseInt(subFidStr.trim()));
                    } else {
                        dsp.setSubFid(0);
                    }

                    pat = ",\\s*gid\\s*:(\\s*[0-9]+\\s*)(,|$)";
                    String gidStr = StringUtil.mactcherG1(varstr, pat);
                    if (gidStr != null) {
                        dsp.setGid((long)Integer.parseInt(gidStr.trim()));
                    } else {
                        dsp.setGid(0L);
                    }

                    pat = ",\\s*permit\\s*:(\\s*[0-9]+\\s*)(,|$)";
                    String permitStr = StringUtil.mactcherG1(varstr, pat);
                    if (permitStr != null) {
                        dsp.setPermit(Integer.parseInt(permitStr.trim()));
                    } else {
                        dsp.setPermit(0);
                    }

                    pat = ",\\s*personal\\s*:(\\s*[0-9]+\\s*)(,|$)";
                    String personalStr = StringUtil.mactcherG1(varstr, pat);
                    if (personalStr != null) {
                        dsp.setPersonal(Integer.parseInt(personalStr.trim()));
                    } else {
                        dsp.setPersonal(0);
                    }

                    pat = ",\\s*single\\s*:(\\s*[0-9]+\\s*)(,|$)";
                    String singleStr = StringUtil.mactcherG1(varstr, pat);
                    if (singleStr != null) {
                        dsp.setSingle(Integer.parseInt(singleStr.trim()));
                    } else {
                        dsp.setSingle(0);
                    }

                    pat = ",\\s*singleID\\s*:(\\s*[0-9]+\\s*)(,|$)";
                    String singleIDStr = StringUtil.mactcherG1(varstr, pat);
                    if (singleIDStr != null) {
                        dsp.setSingleID((long)Integer.parseInt(singleIDStr.trim()));
                    } else {
                        dsp.setSingleID(0L);
                    }

                    pat = ",\\s*firstResult\\s*:(\\s*[0-9]+\\s*)(,|$)";
                    String firstResultStr = StringUtil.mactcherG1(varstr, pat);
                    if (firstResultStr != null) {
                        if (StringUtil.isNumber(firstResultStr.trim())) {
                            dsp.setFirstResult(Integer.parseInt(firstResultStr.trim()));
                        } else {
                            dsp.setFirstResult(0);
                        }
                    } else {
                        dsp.setFirstResult(0);
                    }

                    pat = ",\\s*page\\s*:(\\s*[0-9]+\\s*)(,|$)";
                    String pageStr = StringUtil.mactcherG1(varstr, pat);
                    if (pageStr != null) {
                        if (StringUtil.isNumber(pageStr.trim())) {
                            dsp.setCurpage(Integer.parseInt(pageStr.trim()));
                        } else {
                            dsp.setCurpage(0);
                        }
                    } else {
                        dsp.setCurpage(0);
                    }

                    pat = ",\\s*pagesize\\s*:(\\s*[0-9]+\\s*)(,|$)";
                    String pagesizeStr = StringUtil.mactcherG1(varstr, pat);
                    if (pagesizeStr != null) {
                        if (StringUtil.isNumber(pagesizeStr.trim())) {
                            dsp.setPagesize(Integer.parseInt(pagesizeStr.trim()));
                        } else {
                            dsp.setPagesize(0);
                        }
                    } else {
                        dsp.setPagesize(0);
                    }

                    pat = ",\\s*titleLen\\s*:(\\s*[0-9]+\\s*)(,|$)";
                    String titleLen = StringUtil.mactcherG1(varstr, pat);
                    if (titleLen != null) {
                        if (StringUtil.isNumber(titleLen.trim())) {
                            dsp.setTitleLen(Integer.parseInt(titleLen.trim()));
                        } else {
                            dsp.setTitleLen(0);
                        }
                    } else {
                        dsp.setTitleLen(0);
                    }

                    pat = ",\\s*hrefLen\\s*:(\\s*[0-9]+\\s*)(,|$)";
                    String hrefLen = StringUtil.mactcherG1(varstr, pat);
                    if (hrefLen != null) {
                        if (StringUtil.isNumber(hrefLen.trim())) {
                            dsp.setHrefLen(Integer.parseInt(hrefLen.trim()));
                        } else {
                            dsp.setHrefLen(0);
                        }
                    } else {
                        dsp.setHrefLen(0);
                    }

                    pat = ",\\s*order\\s*:(\\s*[0-1]+\\s*)(,|$)";
                    String orderStr = StringUtil.mactcherG1(varstr, pat);
                    if (orderStr != null) {
                        if (StringUtil.isNumber(orderStr.trim())) {
                            dsp.setOrder(Integer.parseInt(orderStr.trim()));
                        } else {
                            dsp.setOrder(0);
                        }
                    } else {
                        dsp.setOrder(0);
                    }

                    pat = ",\\s*top\\s*:(\\s*[0-9]+\\s*)(,|$)";
                    String topStr = StringUtil.mactcherG1(varstr, pat);
                    if (topStr != null) {
                        if (StringUtil.isNumber(topStr.trim())) {
                            dsp.setTop(Integer.parseInt(topStr.trim()));
                        } else {
                            dsp.setTop(0);
                        }
                    } else {
                        dsp.setTop(0);
                    }

                    pat = ",\\s*soul\\s*:(\\s*[0-9]+\\s*)(,|$)";
                    String soulStr = StringUtil.mactcherG1(varstr, pat);
                    if (soulStr != null) {
                        if (StringUtil.isNumber(soulStr.trim())) {
                            dsp.setSoul(Integer.parseInt(soulStr.trim()));
                        } else {
                            dsp.setSoul(0);
                        }
                    } else {
                        dsp.setSoul(0);
                    }

                    pat = ",\\s*img\\s*:(\\s*[0-9]+\\s*)(,|$)";
                    String imgStr = StringUtil.mactcherG1(varstr, pat);
                    if (imgStr != null) {
                        if (StringUtil.isNumber(imgStr.trim())) {
                            dsp.setImg(Integer.parseInt(imgStr.trim()));
                        } else {
                            dsp.setImg(0);
                        }
                    } else {
                        dsp.setImg(0);
                    }

                    pat = ",\\s*spare1\\s*:(\\s*[0-9]+\\s*)(,|$)";
                    String spare1Str = StringUtil.mactcherG1(varstr, pat);
                    if (spare1Str != null) {
                        if (StringUtil.isNumber(spare1Str.trim())) {
                            dsp.setSpare1(Integer.parseInt(spare1Str.trim()));
                        } else {
                            dsp.setSpare1(0);
                        }
                    } else {
                        dsp.setSpare1(0);
                    }

                    pat = ",\\s*spare2\\s*:(\\s*[0-9]+\\s*)(,|$)";
                    String spare2Str = StringUtil.mactcherG1(varstr, pat);
                    if (spare2Str != null) {
                        if (StringUtil.isNumber(spare2Str.trim())) {
                            dsp.setSpare2(Integer.parseInt(spare2Str.trim()));
                        } else {
                            dsp.setSpare2(0);
                        }
                    } else {
                        dsp.setSpare2(0);
                    }

                    return dsp;
                }
            }
        } else {
            return null;
        }
    }

    public static String replace(String source, String modul, String tag, String target) {
        String pat = "\\{\\$(\\s*" + modul + "\\s*:\\s*" + tag + "\\s*)\\$\\}";
        Pattern pattern = Pattern.compile(pat);
        Matcher matcher = pattern.matcher(source);
        if (target == null) {
            target = "";
        }

        while(matcher.find()) {
            String wholeTag = matcher.group(0);
            source = StringUtil.strReplace(source, wholeTag, target);
        }

        return source;
    }

    public static String replaceElong(String source, String modul, String tag, String target) {
        String pat = "\\{\\$(\\s*" + modul + "\\s*:\\s*" + tag + "\\w*\\s*)\\$\\}";
        Pattern pattern = Pattern.compile(pat);
        Matcher matcher = pattern.matcher(source);
        if (target == null) {
            target = "";
        }

        while(matcher.find()) {
            String wholeTag = matcher.group(0);
            source = StringUtil.strReplace(source, wholeTag, target);
        }

        return source;
    }

    public static String replace(String source, String modul, String tag, Long dt) {
        String pat = "\\{\\$(\\s*" + modul + "\\s*:\\s*" + tag + "@(.*)\\s*)\\$\\}";
        Pattern pattern = Pattern.compile(pat);
        Matcher matcher = pattern.matcher(source);
        if (dt == null) {
            return source;
        } else {
            while(matcher.find()) {
                String wholeTag = matcher.group(0);
                String insideTag = matcher.group(1);
                String[] sArray = insideTag.split("@");
                if (sArray.length > 1) {
                    DateFormat f = new SimpleDateFormat(sArray[1]);
                    source = StringUtil.strReplace(source, wholeTag, f.format(dt));
                }
            }

            return source;
        }
    }

    public static Set<FindedDataAnalyzeResult> find(String source) {
        Set<FindedDataAnalyzeResult> fdarSet = new HashSet();
        String pat = "\\{\\$(\\s*data\\s*[^{}]+)\\$\\}";
        Pattern pattern = Pattern.compile(pat);
        Matcher matcher = pattern.matcher(source);

        while(matcher.find()) {
            FindedDataAnalyzeResult fdar = new FindedDataAnalyzeResult();
            String block = matcher.group(1);
            block = block.replaceAll("\\/\\/[^\\n]*|\\/\\*([^\\*^\\/]*|[\\*^\\/*]*|[^\\**\\/]*)*\\*+\\/", "");
            DataShowParams dsp = analyze(block);
            if (dsp != null) {
                if (dsp.getFormatSource() == null) {
                    dsp.getLoopFormatStr();
                }

                fdar.setDsp(dsp);
                fdar.setWholeTag(matcher.group(0));
                fdarSet.add(fdar);
            }
        }

        return fdarSet;
    }

    public static void main(String[] args) {
        String lf = "{$tag:addTime@yyyy-MM-dd HH:mm:ss /*dddd*/$}";
        String s = lf.replaceAll("\\/\\/[^\\n]*|\\/\\*([^\\*^\\/]*|[\\*^\\/*]*|[^\\**\\/]*)*\\*+\\/", "");
        System.out.println(s);
        lf = replace(lf, "tag", "addTime", System.currentTimeMillis());
    }
}
