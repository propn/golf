package com.golf.utils;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletInputStream;

import com.golf.Golf;

/**
 * 字符串工具类
 * 
 * @author Thunder.Hsu
 * 
 */
public class StringUtils {
    //
    public static final String EMPTY_STR = "".intern();
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final String charsetName = Golf.charsetName;
    public static final int BUFFER_SIZE = 8192;

    public static boolean isBlank(String str) {
        if (null == str || str.isEmpty() || str.trim().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNumeric(String str) {
        if (isBlank(str))
            return false;

        char first = str.charAt(0);
        int i = first == '-' ? 1 : 0;
        for (; i < str.length(); i++) {
            if (isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean isInteger(String str) {
        if (isBlank(str))
            return false;

        char first = str.charAt(0);
        int i = first == '-' ? 1 : 0;
        for (; i < str.length(); i++) {
            if (isDigit(str.charAt(i)) == false) {
                return false;
            }
        }

        Long t = Long.parseLong(str);
        return t <= Integer.MAX_VALUE && t >= Integer.MIN_VALUE;
    }

    public static boolean isLong(String str) {
        if (isBlank(str))
            return false;

        char first = str.charAt(0);
        char end = str.charAt(str.length() - 1);
        boolean j = end == 'l' || end == 'L';
        int i = first == '-' ? 1 : 0;
        int len = j ? str.length() - 1 : str.length();
        for (; i < len; i++) {
            if (isDigit(str.charAt(i)) == false) {
                return false;
            }
        }

        if (!j) {
            Long t = Long.parseLong(str);
            return t > Integer.MAX_VALUE || t < Integer.MIN_VALUE;
        } else {
            return true;
        }
    }

    public static boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    public static boolean isFloat(String str) {
        if (isBlank(str))
            return false;

        char end = str.charAt(str.length() - 1);
        if (!(end == 'f' || end == 'F'))
            return false;

        int point = 0;
        int i = str.charAt(0) == '-' ? 1 : 0;
        for (; i < str.length() - 1; i++) {
            char c = str.charAt(i);
            if (c == '.') {
                point++;
            } else if (isDigit(c) == false) {
                return false;
            }
        }
        return point == 1 || point == 0;
    }

    public static boolean isDouble(String str) {
        if (isBlank(str))
            return false;

        int point = 0;
        int i = str.charAt(0) == '-' ? 1 : 0;
        for (; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '.') {
                point++;
            } else if (isDigit(c) == false) {
                return false;
            }
        }

        return point == 1;
    }

    public static String trimToNull(String str) {
        if (str == null) {
            return null;
        }
        str = str.trim();
        return (str.length() == 0) ? null : str;
    }

    public static String array2Strig(String[] array, char split) {
        if (null == array) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (String str : array) {
            sb.append(str).append(split);
        }
        return sb.substring(0, sb.length() - 1);
    }

    public static String list2Stirng(List<?> args, char split) {
        if (null == args || args.isEmpty()) {
            return null;
        }
        StringBuffer rst = new StringBuffer();
        for (Object obj : args) {
            rst.append(obj).append(split);
        }
        return rst.substring(0, rst.length() - 1);
    }

    public static final void writeTo(InputStream in, OutputStream out) throws IOException {
        int read;
        final byte[] data = new byte[BUFFER_SIZE];
        while ((read = in.read(data)) != -1) {
            out.write(data, 0, read);
        }
    }

    public static final void writeTo(Reader in, Writer out) throws IOException {
        int read;
        final char[] data = new char[BUFFER_SIZE];
        while ((read = in.read(data)) != -1) {
            out.write(data, 0, read);
        }
    }

    public static ByteArrayInputStream servletInputStream2ByteArrayInputStream(ServletInputStream in)
            throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int i = 0;
        while ((i = in.read(b, 0, 1024)) > 0) {
            out.write(b, 0, i);
        }
        ByteArrayInputStream bin = new ByteArrayInputStream(out.toByteArray());
        return bin;
    }

    public static String servletInputStream2String(ServletInputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int i = 0;
        while ((i = in.read(b, 0, 1024)) > 0) {
            out.write(b, 0, i);
        }
        in.reset();
        return new String(out.toByteArray(), charsetName);
    }

    public static String byteArrayInputStream2String(ByteArrayInputStream bin) throws IOException {
        byte[] b = new byte[bin.available()];
        bin.read(b, 0, bin.available());
        String entity = new String(b, charsetName);
        return entity;
    }

    public static String inputStream2String(InputStream in) throws IOException {
        Reader reader = new InputStreamReader(in, charsetName);
        StringBuilder sb = new StringBuilder();
        char[] c = new char[BUFFER_SIZE];
        int l;
        while ((l = reader.read(c)) != -1) {
            sb.append(c, 0, l);
        }
        return sb.toString();
    }

    /**
     * Convert a string to bytes and write those bytes to an output stream.
     */
    public static final void writeToAsString(String s, OutputStream out) throws IOException {
        Writer osw = new BufferedWriter(new OutputStreamWriter(out, charsetName));
        osw.write(s);
        osw.flush();
    }

    /**
     * 驼峰转下划线
     * 
     * @param param
     * @return
     */
    public static String camel4underline(String param) {
        Pattern p = Pattern.compile("[A-Z]");
        if (param == null || param.equals("")) {
            return "";
        }
        StringBuilder builder = new StringBuilder(param);
        Matcher mc = p.matcher(param);
        int i = 0;
        while (mc.find()) {
            builder.replace(mc.start() + i, mc.end() + i, "_" + mc.group().toLowerCase());
            i++;
        }
        if ('_' == builder.charAt(0)) {
            builder.deleteCharAt(0);
        }
        return builder.toString().toUpperCase();
    }

    /**
     * 下划线转驼峰
     * 
     * @param columnName
     * @return
     */
    public static String underline4camel(String columnName) {

        if (columnName == null || columnName.equals("")) {
            return "";
        }

        if (columnName.indexOf("_") < 0) {
            return columnName.toLowerCase();
        }

        columnName = columnName.toLowerCase();
        Pattern p = Pattern.compile("_[a-z]");
        Matcher mc = p.matcher(columnName);
        StringBuilder builder = new StringBuilder(columnName);
        int i = 0;
        while (mc.find()) {
            builder.replace(mc.start() + i, mc.end() + i, mc.group().toUpperCase());
        }
        return builder.toString().replaceAll("_", "");
    }

    // -----------------------------------------------------------------------
    /**
     * <p>
     * Splits the provided text into an array, using whitespace as the separator. Whitespace is defined by
     * {@link Character#isWhitespace(char)}.
     * </p>
     * 
     * <p>
     * The separator is not included in the returned String array. Adjacent separators are treated as one separator. For
     * more control over the split use the StrTokenizer class.
     * </p>
     * 
     * <p>
     * A <code>null</code> input String returns <code>null</code>.
     * </p>
     * 
     * <pre>
     * StringUtils.split(null)       = null
     * StringUtils.split("")         = []
     * StringUtils.split("abc def")  = ["abc", "def"]
     * StringUtils.split("abc  def") = ["abc", "def"]
     * StringUtils.split(" abc ")    = ["abc"]
     * </pre>
     * 
     * @param str the String to parse, may be null
     * @return an array of parsed Strings, <code>null</code> if null String input
     */
    public static String[] split(String str) {
        return split(str, null, -1);
    }

    /**
     * <p>
     * Splits the provided text into an array, separators specified. This is an alternative to using StringTokenizer.
     * </p>
     * 
     * <p>
     * The separator is not included in the returned String array. Adjacent separators are treated as one separator. For
     * more control over the split use the StrTokenizer class.
     * </p>
     * 
     * <p>
     * A <code>null</code> input String returns <code>null</code>. A <code>null</code> separatorChars splits on
     * whitespace.
     * </p>
     * 
     * <pre>
     * StringUtils.split(null, *)         = null
     * StringUtils.split("", *)           = []
     * StringUtils.split("abc def", null) = ["abc", "def"]
     * StringUtils.split("abc def", " ")  = ["abc", "def"]
     * StringUtils.split("abc  def", " ") = ["abc", "def"]
     * StringUtils.split("ab:cd:ef", ":") = ["ab", "cd", "ef"]
     * </pre>
     * 
     * @param str the String to parse, may be null
     * @param separatorChars the characters used as the delimiters, <code>null</code> splits on whitespace
     * @return an array of parsed Strings, <code>null</code> if null String input
     */
    public static String[] split(String str, String separatorChars) {
        return splitWorker(str, separatorChars, -1, false);
    }

    /**
     * <p>
     * Splits the provided text into an array, separator specified. This is an alternative to using StringTokenizer.
     * </p>
     * 
     * <p>
     * The separator is not included in the returned String array. Adjacent separators are treated as one separator. For
     * more control over the split use the StrTokenizer class.
     * </p>
     * 
     * <p>
     * A <code>null</code> input String returns <code>null</code>.
     * </p>
     * 
     * <pre>
     * StringUtils.split(null, *)         = null
     * StringUtils.split("", *)           = []
     * StringUtils.split("a.b.c", '.')    = ["a", "b", "c"]
     * StringUtils.split("a..b.c", '.')   = ["a", "b", "c"]
     * StringUtils.split("a:b:c", '.')    = ["a:b:c"]
     * StringUtils.split("a b c", ' ')    = ["a", "b", "c"]
     * </pre>
     * 
     * @param str the String to parse, may be null
     * @param separatorChar the character used as the delimiter
     * @return an array of parsed Strings, <code>null</code> if null String input
     * @since 2.0
     */
    public static String[] split(String str, char separatorChar) {
        return splitWorker(str, separatorChar, false);
    }

    /**
     * <p>
     * Splits the provided text into an array with a maximum length, separators specified.
     * </p>
     * 
     * <p>
     * The separator is not included in the returned String array. Adjacent separators are treated as one separator.
     * </p>
     * 
     * <p>
     * A <code>null</code> input String returns <code>null</code>. A <code>null</code> separatorChars splits on
     * whitespace.
     * </p>
     * 
     * <p>
     * If more than <code>max</code> delimited substrings are found, the last returned string includes all characters
     * after the first <code>max - 1</code> returned strings (including separator characters).
     * </p>
     * 
     * <pre>
     * StringUtils.split(null, *, *)            = null
     * StringUtils.split("", *, *)              = []
     * StringUtils.split("ab de fg", null, 0)   = ["ab", "cd", "ef"]
     * StringUtils.split("ab   de fg", null, 0) = ["ab", "cd", "ef"]
     * StringUtils.split("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
     * StringUtils.split("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
     * </pre>
     * 
     * @param str the String to parse, may be null
     * @param separatorChars the characters used as the delimiters, <code>null</code> splits on whitespace
     * @param max the maximum number of elements to include in the array. A zero or negative value implies no limit
     * @return an array of parsed Strings, <code>null</code> if null String input
     */
    public static String[] split(String str, String separatorChars, int max) {
        return splitWorker(str, separatorChars, max, false);
    }

    /**
     * Performs the logic for the <code>split</code> and <code>splitPreserveAllTokens</code> methods that return a
     * maximum array length.
     * 
     * @param str the String to parse, may be <code>null</code>
     * @param separatorChars the separate character
     * @param max the maximum number of elements to include in the array. A zero or negative value implies no limit.
     * @param preserveAllTokens if <code>true</code>, adjacent separators are treated as empty token separators; if
     *            <code>false</code>, adjacent separators are treated as one separator.
     * @return an array of parsed Strings, <code>null</code> if null String input
     */
    private static String[] splitWorker(String str, String separatorChars, int max, boolean preserveAllTokens) {
        // Performance tuned for 2.0 (JDK1.4)
        // Direct code is quicker than StringTokenizer.
        // Also, StringTokenizer uses isSpace() not isWhitespace()

        if (str == null) {
            return null;
        }
        int len = str.length();
        if (len == 0) {
            return EMPTY_STRING_ARRAY;
        }
        List<String> list = new ArrayList<String>();
        int sizePlus1 = 1;
        int i = 0, start = 0;
        boolean match = false;
        boolean lastMatch = false;
        if (separatorChars == null) {
            // Null separator means use whitespace
            while (i < len) {
                if (Character.isWhitespace(str.charAt(i))) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        } else if (separatorChars.length() == 1) {
            // Optimise 1 character case
            char sep = separatorChars.charAt(0);
            while (i < len) {
                if (str.charAt(i) == sep) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        } else {
            // standard case
            while (i < len) {
                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        }
        if (match || (preserveAllTokens && lastMatch)) {
            list.add(str.substring(start, i));
        }
        return (String[]) list.toArray(EMPTY_STRING_ARRAY);
    }

    /**
     * Performs the logic for the <code>split</code> and <code>splitPreserveAllTokens</code> methods that do not return
     * a maximum array length.
     * 
     * @param str the String to parse, may be <code>null</code>
     * @param separatorChar the separate character
     * @param preserveAllTokens if <code>true</code>, adjacent separators are treated as empty token separators; if
     *            <code>false</code>, adjacent separators are treated as one separator.
     * @return an array of parsed Strings, <code>null</code> if null String input
     */
    private static String[] splitWorker(String str, char separatorChar, boolean preserveAllTokens) {
        // Performance tuned for 2.0 (JDK1.4)

        if (str == null) {
            return null;
        }
        int len = str.length();
        if (len == 0) {
            return EMPTY_STRING_ARRAY;
        }
        List<String> list = new ArrayList<String>();
        int i = 0, start = 0;
        boolean match = false;
        boolean lastMatch = false;
        while (i < len) {
            if (str.charAt(i) == separatorChar) {
                if (match || preserveAllTokens) {
                    list.add(str.substring(start, i));
                    match = false;
                    lastMatch = true;
                }
                start = ++i;
                continue;
            }
            lastMatch = false;
            match = true;
            i++;
        }
        if (match || (preserveAllTokens && lastMatch)) {
            list.add(str.substring(start, i));
        }
        return list.toArray(EMPTY_STRING_ARRAY);
    }

    /**
     * <p>
     * Splits the provided text into an array, separator string specified.
     * </p>
     * 
     * <p>
     * The separator(s) will not be included in the returned String array. Adjacent separators are treated as one
     * separator.
     * </p>
     * 
     * <p>
     * A <code>null</code> input String returns <code>null</code>. A <code>null</code> separator splits on whitespace.
     * </p>
     * 
     * <pre>
     * StringUtils.splitByWholeSeparator(null, *)               = null
     * StringUtils.splitByWholeSeparator("", *)                 = []
     * StringUtils.splitByWholeSeparator("ab de fg", null)      = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparator("ab   de fg", null)    = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparator("ab:cd:ef", ":")       = ["ab", "cd", "ef"]
     * StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-") = ["ab", "cd", "ef"]
     * </pre>
     * 
     * @param str the String to parse, may be null
     * @param separator String containing the String to be used as a delimiter, <code>null</code> splits on whitespace
     * @return an array of parsed Strings, <code>null</code> if null String was input
     */
    public static String[] splitByWholeSeparator(String str, String separator) {
        return splitByWholeSeparatorWorker(str, separator, -1, false);
    }

    /**
     * <p>
     * Splits the provided text into an array, separator string specified. Returns a maximum of <code>max</code>
     * substrings.
     * </p>
     * 
     * <p>
     * The separator(s) will not be included in the returned String array. Adjacent separators are treated as one
     * separator.
     * </p>
     * 
     * <p>
     * A <code>null</code> input String returns <code>null</code>. A <code>null</code> separator splits on whitespace.
     * </p>
     * 
     * <pre>
     * StringUtils.splitByWholeSeparator(null, *, *)               = null
     * StringUtils.splitByWholeSeparator("", *, *)                 = []
     * StringUtils.splitByWholeSeparator("ab de fg", null, 0)      = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparator("ab   de fg", null, 0)    = ["ab", "de", "fg"]
     * StringUtils.splitByWholeSeparator("ab:cd:ef", ":", 2)       = ["ab", "cd:ef"]
     * StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 5) = ["ab", "cd", "ef"]
     * StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 2) = ["ab", "cd-!-ef"]
     * </pre>
     * 
     * @param str the String to parse, may be null
     * @param separator String containing the String to be used as a delimiter, <code>null</code> splits on whitespace
     * @param max the maximum number of elements to include in the returned array. A zero or negative value implies no
     *            limit.
     * @return an array of parsed Strings, <code>null</code> if null String was input
     */
    public static String[] splitByWholeSeparator(String str, String separator, int max) {
        return splitByWholeSeparatorWorker(str, separator, max, false);
    }

    /**
     * Performs the logic for the <code>splitByWholeSeparatorPreserveAllTokens</code> methods.
     * 
     * @param str the String to parse, may be <code>null</code>
     * @param separator String containing the String to be used as a delimiter, <code>null</code> splits on whitespace
     * @param max the maximum number of elements to include in the returned array. A zero or negative value implies no
     *            limit.
     * @param preserveAllTokens if <code>true</code>, adjacent separators are treated as empty token separators; if
     *            <code>false</code>, adjacent separators are treated as one separator.
     * @return an array of parsed Strings, <code>null</code> if null String input
     * @since 2.4
     */
    private static String[] splitByWholeSeparatorWorker(String str, String separator, int max, boolean preserveAllTokens) {
        if (str == null) {
            return null;
        }

        int len = str.length();

        if (len == 0) {
            return EMPTY_STRING_ARRAY;
        }

        if ((separator == null) || (EMPTY_STR.equals(separator))) {
            // Split on whitespace.
            return splitWorker(str, null, max, preserveAllTokens);
        }

        int separatorLength = separator.length();

        ArrayList<String> substrings = new ArrayList<String>();
        int numberOfSubstrings = 0;
        int beg = 0;
        int end = 0;
        while (end < len) {
            end = str.indexOf(separator, beg);

            if (end > -1) {
                if (end > beg) {
                    numberOfSubstrings += 1;

                    if (numberOfSubstrings == max) {
                        end = len;
                        substrings.add(str.substring(beg));
                    } else {
                        // The following is OK, because String.substring( beg, end ) excludes
                        // the character at the position 'end'.
                        // System.out.println("sub " + beg + "|" + end +"|" + str.substring(beg, end));
                        substrings.add(str.substring(beg, end));

                        // Set the starting point for the next search.
                        // The following is equivalent to beg = end + (separatorLength - 1) + 1,
                        // which is the right calculation:
                        beg = end + separatorLength;
                    }
                } else {
                    // We found a consecutive occurrence of the separator, so skip it.
                    if (preserveAllTokens) {
                        numberOfSubstrings += 1;
                        if (numberOfSubstrings == max) {
                            end = len;
                            substrings.add(str.substring(beg));
                        } else {
                            substrings.add(EMPTY_STR);
                        }
                    }
                    beg = end + separatorLength;
                }
            } else {
                // String.substring( beg ) goes from 'beg' to the end of the String.
                // System.out.println("sub~~ " + beg + "|" + end +"|" + str.substring(beg));
                String t = str.substring(beg);
                if (!t.equals(EMPTY_STR))
                    substrings.add(str.substring(beg));
                end = len;
            }
        }

        return substrings.toArray(EMPTY_STRING_ARRAY);
    }

    public static boolean hasText(String str) {
        return hasText((CharSequence) str);
    }

    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

    public static boolean hasLength(String str) {
        return hasLength((CharSequence) str);
    }

    /**
     * 将字符串中特定模式的字符转换成map中对应的值
     * 
     * @param s 需要转换的字符串
     * @param map 转换所需的键值对集合
     * @return 转换后的字符串
     */
    public static String replace(String s, Map<String, Object> map) {
        StringBuilder ret = new StringBuilder((int) (s.length() * 1.5));
        int cursor = 0;
        for (int start, end; (start = s.indexOf("${", cursor)) != -1 && (end = s.indexOf("}", start)) != -1;) {
            ret.append(s.substring(cursor, start)).append(map.get(s.substring(start + 2, end)));
            cursor = end + 1;
        }
        ret.append(s.substring(cursor, s.length()));
        return ret.toString();
    }

    public static String replace(String s, Object... objs) {
        if (objs == null || objs.length == 0)
            return s;
        if (s.indexOf("{}") == -1)
            return s;

        StringBuilder ret = new StringBuilder((int) (s.length() * 1.5));
        int cursor = 0;
        int index = 0;
        for (int start; (start = s.indexOf("{}", cursor)) != -1;) {
            ret.append(s.substring(cursor, start));
            if (index < objs.length)
                ret.append(objs[index]);
            else
                ret.append("{}");
            cursor = start + 2;
            index++;
        }
        ret.append(s.substring(cursor, s.length()));
        return ret.toString();
    }

    public static String escapeXML(String str) {
        if (str == null)
            return "";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            switch (c) {
            case '\u00FF':
            case '\u0024':
                break;
            case '&':
                sb.append("&amp;");
                break;
            case '<':
                sb.append("&lt;");
                break;
            case '>':
                sb.append("&gt;");
                break;
            case '\"':
                sb.append("&quot;");
                break;
            case '\'':
                sb.append("&apos;");
                break;
            default:
                if (c >= '\u0000' && c <= '\u001F')
                    break;
                if (c >= '\uE000' && c <= '\uF8FF')
                    break;
                if (c >= '\uFFF0' && c <= '\uFFFF')
                    break;
                sb.append(c);
                break;
            }
        }
        return sb.toString();
    }

}
