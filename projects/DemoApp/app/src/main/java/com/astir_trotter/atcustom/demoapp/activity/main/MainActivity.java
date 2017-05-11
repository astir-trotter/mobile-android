package com.astir_trotter.atcustom.demoapp.activity.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.astir_trotter.atcustom.demoapp.R;
import com.astir_trotter.atcustom.demoapp.activity.main.adapter.DemoItemsAdapter;
import com.astir_trotter.atcustom.demoapp.activity.main.model.DemoItem;
import com.astir_trotter.atcustom.demoapp.activity.main.model.Tag;
import com.astir_trotter.atcustom.singleton.lang.MultiLangStringRes;
import com.astir_trotter.atcustom.ui.activity.BaseActivity;
import com.yalantis.filter.adapter.FilterAdapter;
import com.yalantis.filter.listener.FilterListener;
import com.yalantis.filter.widget.Filter;
import com.yalantis.filter.widget.FilterItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements FilterListener<Tag> {
    private static final String TAG = MainActivity.class.getSimpleName();

    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    private RecyclerView mRecyclerView;

    private List<DemoItem> mAllDemoItems;
    private Filter<Tag> mFilter;
    private DemoItemsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

//        ((TextView) findViewById(R.id.actionbar_title)).setText(MultiLangStringRes.getInstance().get(R.string.app_name));
//
//        mFilter = (Filter<Tag>) findViewById(R.id.main_list_filter);
//        mFilter.setAdapter(new Adapter(Tag.getTags()));
//        mFilter.setListener(this);
//
//        //the text to show when there's no selected items
//        mFilter.setNoSelectedItemText(MultiLangStringRes.getInstance().get(R.string.demoitem_tag_all));
//        mFilter.build();
//
//
//        mRecyclerView = (RecyclerView) findViewById(R.id.main_list);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        mRecyclerView.setAdapter(mAdapter = new DemoItemsAdapter(mAllDemoItems = DemoItem.getAllDemoItems()));
//        mRecyclerView.setItemAnimator(new FiltersListItemAnimator());
    }

    private void calculateDiff(final List<DemoItem> oldList, final List<DemoItem> newList) {
        DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return oldList.size();
            }

            @Override
            public int getNewListSize() {
                return newList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
            }
        }).dispatchUpdatesTo(mAdapter);
    }

    @Override
    public void onNothingSelected() {
        if (mRecyclerView != null) {
            mAdapter.setQuestions(mAllDemoItems);
            mAdapter.notifyDataSetChanged();
        }
    }

    private List<DemoItem> findByTags(List<Tag> tags) {
        List<DemoItem> demoItems = new ArrayList<>();

        for (DemoItem demoItem : mAllDemoItems) {
            for (Tag tag : tags) {
                if (demoItem.hasTag(tag.getText()) && !demoItems.contains(demoItem)) {
                    demoItems.add(demoItem);
                }
            }
        }

        return demoItems;
    }

    @Override
    public void onFiltersSelected(@NonNull ArrayList<Tag> filters) {
        List<DemoItem> newDemoItems = findByTags(filters);
        List<DemoItem> oldDemoItems = mAdapter.getQuestions();
        mAdapter.setQuestions(newDemoItems);
        calculateDiff(oldDemoItems, newDemoItems);
    }

    @Override
    public void onFilterSelected(Tag item) {
        if (item.getText().equals(MultiLangStringRes.getInstance().get(R.string.demoitem_tag_all))) {
            mFilter.deselectAll();
            mFilter.collapse();
        }
    }

    @Override
    public void onFilterDeselected(Tag item) {

    }

    private class Adapter extends FilterAdapter<Tag> {

        Adapter(@NonNull List<? extends Tag> items) {
            super(items);
        }

        @NonNull
        @Override
        public FilterItem createView(int position, Tag item) {
            FilterItem filterItem = new FilterItem(MainActivity.this);

            filterItem.setStrokeColor(item.getColor());
            filterItem.setTextColor(item.getColor());
            filterItem.setCheckedTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
            filterItem.setColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
            filterItem.setCheckedColor(item.getColor());
            filterItem.setText(item.getText());
            filterItem.deselect();

            return filterItem;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        drawerToggle.syncState();
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
