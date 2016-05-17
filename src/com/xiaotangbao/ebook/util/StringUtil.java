package com.xiaotangbao.ebook.util;


import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-5-2 18:58
 */
public class StringUtil {

    public final static String md5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final static String stripTags(String str) {
        if (null == str || "".equals(str)) {
            return "";
        }
        // 如果是<script>标签，标签中间的内容也过滤掉
        str = str.replaceAll("<script([^>])*>([^<>]*)</script>", "");
        // 非<script>标签保留标签中间的内容
        Pattern pattern = Pattern.compile("</([^<>]*)>|<([^ >]*)[^>]*>", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String tag1 = (null == matcher.group(1)) ? "" : matcher.group(1);
            String tag2 = (null == matcher.group(2)) ? "" : matcher.group(2);
            if ("img".equalsIgnoreCase(tag2)) {
                // 如果是<img/>标签，不过滤
                matcher.appendReplacement(sb, "$0");
            } else if ("a".equalsIgnoreCase(tag1) || "a".equalsIgnoreCase(tag2)) {
                // <a>标签不过滤
                matcher.appendReplacement(sb, "$0");
            } else {
                // 非<img><a>标签，去掉
                matcher.appendReplacement(sb, "");
            }
        }
        // 把剩余的拼接上
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 计算两个字符传的相似度，返回0~1之间的小数，0-完全不同  1-相同
     * 根据Levenshtein距离计算
     * 原理：http://wdhdmx.iteye.com/blog/1343856
     *
     * @param str1
     * @param str2
     * @return
     */
    public final static float similarity(String str1, String str2) {
        // 有空串的情况
        if ((null == str1 || "".equals(str1)) && (null == str2 || "".equals(str2))) {
            return 1;
        }
        if ((null == str1 || "".equals(str1)) && (null != str2 && !"".equals(str2))) {
            return 0;
        }
        if ((null != str1 && !"".equals(str1)) && (null == str2 || "".equals(str2))) {
            return 0;
        }
        // 都不为空
        String shortStr, longStr;
        if (str1.length() < str2.length()) {
            shortStr = str1;
            longStr  = str2;
        } else {
            shortStr = str2;
            longStr  = str1;
        }
        int len1 = shortStr.length();
        int len2 = longStr.length();

        // 空间优化，用一行的空间代替二维空间
        int[] distance = new int[len1 + 1];
        for (int i = 0; i < len1 + 1; i++) {
            distance[i] = i;
        }
        // str1、str2垃圾回收
        str1 = null;
        str2 = null;

        for (int i = 1; i <= len2; i++) {
            int topleft = distance[0];
            distance[0] = i;
            for (int j = 1; j <= len1; j++) {
                int tmp;
                if (shortStr.charAt(j - 1) == longStr.charAt(i - 1)) {
                    tmp = Math.min(Math.min(topleft, distance[j-1] + 1), distance[j] + 1);
                } else {
                    tmp = Math.min(Math.min(topleft + 1, distance[j-1] + 1), distance[j] + 1);
                }
                topleft = distance[j];
                distance[j] = tmp;
            }
        }
        int minDistance = distance[len1];
        //System.out.println(minDistance + "   " + len1 + ' ' + len2);
        return 1 - minDistance / (len1 * 1.0f + len2);
    }

    /**
     * 正则匹配，默认大小写不敏感
     *
     * @param patternStr    正则模式串
     * @param str           目标字符串
     * @param group         指定要返回的组
     * @return              返回指定组 | null
     */
    public final static String regexMatch(String patternStr, String str, int group) {
        Pattern pattern = Pattern.compile(patternStr, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(group);
        }
        return null;
    }

    /**
     * 简单的html文本转义
     *
     * @param html
     * @return
     */
    public final static String htmlEscape(String html) {
        if (null == html || html.isEmpty()) {
            return "";
        }
        // html = html.replace( "'", "&apos;");
        html = html.replaceAll("&", "&amp;");
        html = html.replace("\"", "&quot;");  //"
        //html = html.replace( "\t", "&nbsp;&nbsp;");// 替换跳格
        html = html.replace(" ", "&nbsp;");// 替换空格
        html = html.replace("<", "&lt;");
        html = html.replaceAll(">", "&gt;");

        return html;
    }
}
