# SearchFilter

Firstly you need to place `Filter` above your `RecyclerView` in the layout file

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.design.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <android.support.design.widget.AppBarLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <android.support.v7.widget.Toolbar
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            android:background="@color/colorPrimary"
            android:elevation="4dp"
            android:paddingRight="16dp">

            <RelativeLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent">

                <android.support.v7.widget.AppCompatImageView
                    android:layout_width="24dp"
                    android:layout_height="24dp"
                    android:layout_centerVertical="true"
                    android:src="@drawable/ic_alarm"
                    android:tint="@android:color/white" />

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_centerInParent="true"
                    android:text="Questions"
                    android:textColor="@android:color/white"
                    android:textSize="20sp" />

                <android.support.v7.widget.AppCompatImageView
                    android:layout_width="24dp"
                    android:layout_height="24dp"
                    android:layout_alignParentRight="true"
                    android:layout_centerVertical="true"
                    android:src="@drawable/ic_search"
                    android:tint="@android:color/white" />

            </RelativeLayout>

        </android.support.v7.widget.Toolbar>
    </android.support.design.widget.AppBarLayout>

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#E4E6E3"
        app:layout_behavior="@string/appbar_scrolling_view_behavior">

        <android.support.v7.widget.RecyclerView
            android:id="@+id/list"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="@dimen/container_height"
            tools:listitem="@layout/item_list" />

        <com.yalantis.filter.widget.Filter
            android:id="@+id/filter"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

    </FrameLayout>
</android.support.design.widget.CoordinatorLayout>

```

After that you need to create a class that extends `FilterAdapter` and to pass your model class as a generic.
Here you can easily customize the appearance of filter items. For the sample app I created `Tag` model to represent
the category of a question in the conversation.

```java

class Adapter extends FilterAdapter<Tag> {

        Adapter(@NotNull List<? extends Tag> items) {
            super(items);
        }

        @NotNull
        @Override
        public FilterItem createView(int position, Tag item) {
            FilterItem filterItem = new FilterItem(ExampleActivity.this);

            filterItem.setStrokeColor(mColors[0]);
            filterItem.setTextColor(mColors[0]);
            filterItem.setCheckedTextColor(ContextCompat.getColor(ExampleActivity.this, android.R.color.white));
            filterItem.setColor(ContextCompat.getColor(ExampleActivity.this, android.R.color.white));
            filterItem.setCheckedColor(mColors[position]);
            filterItem.setText(item.getText());
            filterItem.deselect();

            return filterItem;
        }
    }

```

To receive all the events from the `Filter` such as selection or deselection of a filter item it's necessary
to add a `FilterListener` with the same model class passed as a generic

```java

private FilterListener<Tag> mListener = new FilterListener<Tag>() {

        @Override
        public void onFiltersSelected(@NotNull ArrayList<Tag> filters) {

        }

        @Override
        public void onNothingSelected() {

        }

        @Override
        public void onFilterSelected(Tag item) {

        }

        @Override
        public void onFilterDeselected(Tag item) {

        }

    };

```

Basically `Filter` setup looks like that

```java

 mFilter = (Filter<Tag>) findViewById(R.id.filter);
 mFilter.setAdapter(new Adapter(getTags()));
 mFilter.setListener(this);

 //the text to show when there's no selected items
 mFilter.setNoSelectedItemText(getString(R.string.str_all_selected));
 mFilter.build();

```
