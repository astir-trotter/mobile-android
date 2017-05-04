package com.astir_trotter.atcustom.demoapp.activity.main.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/21/17
 */

public class DemoItem {
    private static final String TAG = DemoItem.class.getSimpleName();

    private int         id;
    private String      title;
    private String      description;
    private int         imageResId;
    private List<Tag>   tags;

    private boolean     like;

    public DemoItem(int id, String title, String description, int imageResId, List<Tag> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean hasTag(String string) {
        for (Tag tag : tags) {
            if (tag.getText().equals(string)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DemoItem)) return false;

        DemoItem demoItem = (DemoItem) o;

        return id == demoItem.id &&
                title.equals(demoItem.title) &&
                description.equals(demoItem.description) &&
                imageResId == demoItem.imageResId &&
                tags.equals(demoItem.tags) &&
                like == demoItem.like;
    }

    @Override
    public int hashCode() {
        return id * 31 +
                title.hashCode() * 31 +
                description.hashCode() * 31 +
                imageResId * 31 +
                tags.hashCode() * 31 +
                (like ? 1 : 0);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static List<DemoItem> getAllDemoItems() {
        return new ArrayList<DemoItem>() {{
            add(new DemoItem(1, "Shimmer1", "Shimmer animation", 0, Tag.getTags(Tag.TagId.UserInterface, Tag.TagId.Animation)));
            add(new DemoItem(1, "Shimmer2", "Shimmer animation", 0, Tag.getTags(Tag.TagId.UserInterface, Tag.TagId.Animation)));
            add(new DemoItem(1, "Shimmer3", "Shimmer animation", 0, Tag.getTags(Tag.TagId.UserInterface, Tag.TagId.Animation)));
            add(new DemoItem(1, "Shimmer4", "Shimmer animation", 0, Tag.getTags(Tag.TagId.UserInterface, Tag.TagId.Animation)));
            add(new DemoItem(1, "Shimmer5", "Shimmer animation", 0, Tag.getTags(Tag.TagId.UserInterface, Tag.TagId.Animation)));
        }};
    }
}
