package com.astir_trotter.atcustom.demoapp.demo.model;

import com.yalantis.filter.model.FilterModel;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/21/17
 */

public class Tag implements FilterModel {

    private String text;
    private int color;

    public Tag(String text, int color) {
        this.text = text;
        this.color = color;
    }

    @Override
    public String getText() {
        return text;
    }

    public int getColor() {
        return color;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Tag &&
                ((Tag) obj).getText().equals(text) &&
                ((Tag) obj).getColor() == color;

    }

}
