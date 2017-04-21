/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 12/2/16
 */
package com.astir_trotter.atcustom.util;

import android.support.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {
    private static final String TAG = TextUtils.class.getSimpleName();

    public static String[] getLines(@NonNull String text) {
        Pattern pattern = Pattern.compile("^(.*)$", Constants.PATTERN_FLAG);
        Matcher matcher = pattern.matcher(text).useAnchoringBounds(false);
        List<String> ret = new ArrayList<>();
        while (matcher.find())
            ret.add(matcher.group(1));

        return ret.toArray(new String[ret.size()]);
    }

    private static String getNRepeatString(int n, String repetition) {
        if (n <= 0 || repetition.isEmpty())
            return Constants.EMPTY_STRING;

        if (repetition.equals(" "))
            return String.format("%" + n + "s", repetition);

        StringBuilder buf = new StringBuilder(n * repetition.length());
        while (n-- > 0)
            buf.append(repetition);

        return buf.toString();
    }

    public static String getSentenceForm(@NonNull String text, boolean capitalize, boolean dotEnd) {
        if (text.isEmpty())
            return text;

        StringBuilder ret = new StringBuilder();

        text = text.trim();
        if (text.endsWith("."))
            text = text.substring(0, text.length() - 1);
        if (capitalize)
            ret.append(text.substring(0, 1).toUpperCase());
        else
            ret.append(text.substring(0, 1).toLowerCase());
        ret.append(text.substring(1));
        if (dotEnd)
            ret.append(".");

        return ret.toString();
    }

    public static String getPluralFormIfNeeded(long n, String unit) {
        if (n < 2)
            return unit;

        if (unit.endsWith("y"))
            unit = unit.substring(0, unit.length() - 1) + "ie";

        return unit + "s";
    }

    public static String getStorageSizeText(long n) {
        return getRadixSuffix(n, 1024) + "B";
    }

    public static String getRadixSuffix(long n, int radix) {
        if (n <= 0)
            return "0";

        final String[] units = new String[]{"", "K", "M", "G", "T"};
        int digitGroups = (int) (Math.log10(n) / Math.log10(radix));
        return numberFormat(n / Math.pow(radix, digitGroups), 10, 2, false) + units[digitGroups];
    }

    public static String numberFormat(long n, int radix, boolean suffix) {
        return numberFormat(n, radix, 0, suffix);
    }

    /**
     * number -> value[.decimal][suffix]
     * suffix: h, o, b
     */
    public static String numberFormat(double d, int radix, int decimal, boolean suffix) {
        if (radix == 10) {
            String formatString = "#,##0";
            if (decimal > 0)
                formatString += "." + getNRepeatString(decimal, "#");
            return new DecimalFormat(formatString).format(d);
        } else if (radix == 16 || radix == 8 || radix == 2) {
            String suf;
            if (suffix) {
                if (radix == 16) suf = "h";
                else if (radix == 8) suf = "o";
                else suf = "b";
            } else
                suf = Constants.EMPTY_STRING;

            return Long.toString((long) d, radix) + suf;
        } else
            throw new IllegalArgumentException();
    }

    /**
     * Find line break ('\n', '\r', "\r\n" or eof).
     * @param data - array of bytes to be referenced
     * @param offset - offset in data
     * @param end - size of data
     * @return next value of line break in data,
     *              e.g. 1 for "\nxxx", 3 for "x\r\nxxx", 5 for "xxxxx"
     */
    public static int findLineBreak(byte[] data, int offset, int end) {
        boolean found = false;

        for (int i = offset; i < end; i++) {
            switch (data[i]) {
                case (byte) '\n':
                    return i + 1;
                case (byte) '\r':
                    if (found)
                        return i;
                    found = true;
                    break;
                default:
                    if (found)
                        return i;
            }
        }

        return end;
    }

    public static String decode(byte[] data, int offset, int byteCount, @NonNull String charsetName) {
        try {
            return new String(data, offset, byteCount, charsetName);
        } catch (UnsupportedEncodingException e) {
            LogHelper.log(TAG, "**** An error occurs while decoding ****");
        }

        return null;
    }

    @NonNull
    public static String encrypt(@NonNull String source) {
        byte[] bytesOfMessage = source.getBytes();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            StringBuilder ret = new StringBuilder(thedigest.length * 2 + 4);
            for (byte digest : thedigest)
                ret.append(Integer.toHexString(digest >= 0 ? digest : -digest));
            return ret.toString();
        } catch (NoSuchAlgorithmException e) {
            LogHelper.log(TAG, "**** An error occurs while encoding ****");
        }

        return null;
    }
}
