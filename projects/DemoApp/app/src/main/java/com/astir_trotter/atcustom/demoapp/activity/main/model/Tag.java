package com.astir_trotter.atcustom.demoapp.activity.main.model;

import android.support.annotation.NonNull;

import com.astir_trotter.atcustom.util.ResourceUtils;
import com.yalantis.filter.model.FilterModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/21/17
 */

public class Tag implements FilterModel {
    private static final String TAG = Tag.class.getSimpleName();

    private String text;
    private int color;

    public Tag(String text, int color) {
        this.text = text;
        this.color = color;
    }

    @NonNull
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

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public enum TagId {
        UI();

        private int textResId, colorResId;

        TagId() {
            this(0, 0);
        }

        TagId(int textResId, int colorResId) {
            this.textResId = textResId;
            this.colorResId = colorResId;
        }
    }

    public static List<Tag> getTags() {
        return getTags(TagId.values());
    }

    public static List<Tag> getTags(TagId... tagIds) {
        List<Tag> tags = new ArrayList<>();

        for (TagId tagId : tagIds) {
            tags.add(new Tag(ResourceUtils.getString(tagId.textResId),
                    ResourceUtils.getColor(tagId.colorResId)));
        }

        return tags;
    }

}
