package com.astir_trotter.atcustom.singleton.base;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/21/17
 */

public enum Language {
    English("English"),
    Portuguese("Português"),
    Spanish("Español"),
    Swedish("Svenska"),
    Italian("italiano"),
    German("Deutsche"),
    Chinese("中文"),
    Japanese("日本語"),
    Korean("조선어"),
    Arabics("عربى", true);

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private String originName;
    private boolean rtl;

    Language(String originName) {
        this(originName, false);
    }

    Language(String originName, boolean rtl) {
        this.originName = originName;
        this.rtl = rtl;
    }

    public String getOriginName() {
        return originName;
    }

    public boolean getRtl() {
        return rtl;
    }
}
