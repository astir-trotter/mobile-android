package com.astir_trotter.atcustom.singleton.lang.base;

import java.util.ArrayList;
import java.util.List;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/21/17
 */

public class Language {
    private static final String TAG = Language.class.getSimpleName();

    private static List<Language> sRegisteredLanguages = null;

    public static final Language English = new Language("English");
    public static final Language Portuguese = new Language("Português");
    public static final Language Spanish = new Language("Español");
    public static final Language Swedish = new Language("Svenska");
    public static final Language Italian = new Language("italiano");
    public static final Language German = new Language("Deutsche");
    public static final Language Chinese = new Language("中文");
    public static final Language Japanese = new Language("日本語");
    public static final Language Korean = new Language("한국어");
    public static final Language Arabics = new Language("عربى", true);

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private String originName;
    private boolean rtl;

    public Language(String originName) {
        this(originName, false);
    }

    public Language(String originName, boolean rtl) {
        this.originName = originName;
        this.rtl = rtl;

        if (sRegisteredLanguages == null)
            sRegisteredLanguages = new ArrayList<>();
        sRegisteredLanguages.add(this);
    }

    public String getOriginName() {
        return originName;
    }

    public boolean getRtl() {
        return rtl;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static List<Language> getRegisteredLanguages() {
        return sRegisteredLanguages;
    }
}
