package com.lwhtarena.company.lang;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.*;

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
public class Pinyin4jUtil {

    public static String converterToFirstSpell(String chines) {
        StringBuffer pinyinName = new StringBuffer();
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        for(int i = 0; i < nameChar.length; ++i) {
            if (nameChar[i] > 128) {
                try {
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
                    if (strs != null) {
                        for(int j = 0; j < strs.length; ++j) {
                            pinyinName.append(strs[j].charAt(0));
                            if (j != strs.length - 1) {
                                pinyinName.append(",");
                            }
                        }
                    }
                } catch (BadHanyuPinyinOutputFormatCombination var7) {
                    var7.printStackTrace();
                }
            } else {
                pinyinName.append(nameChar[i]);
            }

            pinyinName.append(" ");
        }

        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
    }

    public static String converterToSpell(String chines) {
        StringBuffer pinyinName = new StringBuffer();
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        for(int i = 0; i < nameChar.length; ++i) {
            if (nameChar[i] > 128) {
                try {
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
                    if (strs != null) {
                        for(int j = 0; j < strs.length; ++j) {
                            pinyinName.append(strs[j]);
                            if (j != strs.length - 1) {
                                pinyinName.append(",");
                            }
                        }
                    }
                } catch (BadHanyuPinyinOutputFormatCombination var7) {
                    var7.printStackTrace();
                }
            } else {
                pinyinName.append(nameChar[i]);
            }

            pinyinName.append(" ");
        }

        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
    }

    private static List<Map<String, Integer>> discountTheChinese(String theStr) {
        List<Map<String, Integer>> mapList = new ArrayList();
        Map<String, Integer> onlyOne = null;
        String[] firsts = theStr.split(" ");
        String[] var7 = firsts;
        int var6 = firsts.length;

        for(int var5 = 0; var5 < var6; ++var5) {
            String str = var7[var5];
            onlyOne = new Hashtable();
            String[] china = str.split(",");
            String[] var12 = china;
            int var11 = china.length;

            for(int var10 = 0; var10 < var11; ++var10) {
                String s = var12[var10];
                Integer count = (Integer)onlyOne.get(s);
                if (count == null) {
                    onlyOne.put(s, new Integer(1));
                } else {
                    onlyOne.remove(s);
                    count = count + 1;
                    onlyOne.put(s, count);
                }
            }

            mapList.add(onlyOne);
        }

        return mapList;
    }

    private static String parseTheChineseByObject(List<Map<String, Integer>> list) {
        Map<String, Integer> first = null;

        for(int i = 0; i < list.size(); ++i) {
            Map<String, Integer> temp = new Hashtable();
            String s;
            Iterator var5;
            if (first == null) {
                var5 = ((Map)list.get(i)).keySet().iterator();

                while(var5.hasNext()) {
                    s = (String)var5.next();
                    temp.put(s, 1);
                }
            } else {
                var5 = first.keySet().iterator();

                while(true) {
                    if (!var5.hasNext()) {
                        if (temp != null && temp.size() > 0) {
                            first.clear();
                        }
                        break;
                    }

                    s = (String)var5.next();
                    Iterator var7 = ((Map)list.get(i)).keySet().iterator();

                    while(var7.hasNext()) {
                        String s1 = (String)var7.next();
                        String str = s + s1;
                        temp.put(str, 1);
                    }
                }
            }

            if (temp != null && temp.size() > 0) {
                first = temp;
            }
        }

        String returnStr = "";
        String str;
        if (first != null) {
            for(Iterator var11 = first.keySet().iterator(); var11.hasNext(); returnStr = returnStr + str + ",") {
                str = (String)var11.next();
            }
        }

        if (returnStr.length() > 0) {
            returnStr = returnStr.substring(0, returnStr.length() - 1);
        }

        return returnStr;
    }

    public static void main(String[] args) {
        String str = "长沙市长";
        String pinyin = converterToSpell(str);
        System.out.println(str + " pin yin ：" + pinyin);
        pinyin = converterToFirstSpell(str);
        System.out.println(str + " short pin yin ：" + pinyin);
    }
}
