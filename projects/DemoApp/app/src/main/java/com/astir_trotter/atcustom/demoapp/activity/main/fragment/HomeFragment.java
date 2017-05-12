package com.astir_trotter.atcustom.demoapp.activity.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astir_trotter.atcustom.demoapp.R;
import com.astir_trotter.atcustom.demoapp.activity.main.adapter.DemoItemsAdapter;
import com.astir_trotter.atcustom.demoapp.activity.main.model.DemoItem;
import com.astir_trotter.atcustom.demoapp.activity.main.model.Tag;
import com.astir_trotter.atcustom.singleton.lang.MultiLangStringRes;
import com.yalantis.filter.adapter.FilterAdapter;
import com.yalantis.filter.animator.FiltersListItemAnimator;
import com.yalantis.filter.listener.FilterListener;
import com.yalantis.filter.widget.Filter;
import com.yalantis.filter.widget.FilterItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 5/10/17
 */

public class HomeFragment extends Fragment implements FilterListener<Tag> {
    private static final String TAG = HomeFragment.class.getSimpleName();


    private RecyclerView mRecyclerView;

    private List<DemoItem> mAllDemoItems;
    private Filter<Tag> mFilter;
    private DemoItemsAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentLayout = inflater.inflate(R.layout.main_content, container, false);

        mFilter = (Filter<Tag>) fragmentLayout.findViewById(R.id.main_list_filter);
        mFilter.setAdapter(new Adapter(Tag.getTags()));
        mFilter.setListener(this);

        //the text to show when there's no selected items
        mFilter.setNoSelectedItemText(MultiLangStringRes.getInstance().get(R.string.demoitem_tag_all));
        mFilter.build();


        mRecyclerView = (RecyclerView) fragmentLayout.findViewById(R.id.main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter = new DemoItemsAdapter(mAllDemoItems = DemoItem.getAllDemoItems()));
        mRecyclerView.setItemAnimator(new FiltersListItemAnimator());

        return fragmentLayout;
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
            Context context = getContext();
            FilterItem filterItem = new FilterItem(context);

            filterItem.setStrokeColor(item.getColor());
            filterItem.setTextColor(item.getColor());
            filterItem.setCheckedTextColor(ContextCompat.getColor(context, android.R.color.white));
            filterItem.setColor(ContextCompat.getColor(context, android.R.color.white));
            filterItem.setCheckedColor(item.getColor());
            filterItem.setText(item.getText());
            filterItem.deselect();

            return filterItem;
        }
    }
}
