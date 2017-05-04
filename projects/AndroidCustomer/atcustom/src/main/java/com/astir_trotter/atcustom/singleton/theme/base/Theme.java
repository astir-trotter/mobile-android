package com.astir_trotter.atcustom.singleton.theme.base;

import java.util.ArrayList;
import java.util.List;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/22/17
 */

public class Theme {
    private static final String TAG = Theme.class.getSimpleName();

    private static List<Theme> sRegisteredThemes = null;

    public static final Theme Light = new Theme("Light");
    public static final Theme Dark = new Theme("Dark");

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private String name;

    public Theme(String name) {
        this.name = name;

        if (sRegisteredThemes == null)
            sRegisteredThemes = new ArrayList<>();

        sRegisteredThemes.add(this);
    }

    public String getName() {
        return name;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static List<Theme> getRegisteredThemes() {
        return sRegisteredThemes;
    }
}
