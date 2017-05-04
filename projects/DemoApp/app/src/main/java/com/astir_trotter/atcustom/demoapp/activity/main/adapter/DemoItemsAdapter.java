package com.astir_trotter.atcustom.demoapp.activity.main.adapter;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astir_trotter.atcustom.demoapp.R;
import com.astir_trotter.atcustom.demoapp.activity.main.model.DemoItem;
import com.astir_trotter.atcustom.demoapp.activity.main.model.Tag;
import com.astir_trotter.atcustom.singleton.Cache;

import java.util.List;

public class DemoItemsAdapter extends RecyclerView.Adapter<DemoItemsAdapter.ViewHolder> {

    private List<DemoItem> mDemoItems;

    public DemoItemsAdapter(List<DemoItem> demoItems) {
        mDemoItems = demoItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_demoitem, null, false));
    }

    public List<DemoItem> getQuestions() {
        return mDemoItems;
    }

    public void setQuestions(List<DemoItem> demoItems) {
        this.mDemoItems = demoItems;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DemoItem demoItem = mDemoItems.get(position);

        holder.title.setText(demoItem.getTitle());
        holder.description.setText(demoItem.getDescription());
        holder.tags.removeAllViews();
        for (Tag tag : demoItem.getTags()) {
            TextView tagView = new TextView(Cache.getInstance().getContext());
            tagView.setText(tag.getText());

            GradientDrawable drawable = new GradientDrawable();
            drawable.setCornerRadius(1000);
            drawable.setColor(tag.getColor());
            tagView.setBackgroundDrawable(drawable);

            holder.tags.addView(tagView);
        }
    }

    @Override
    public int getItemCount() {
        return mDemoItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View image;
        TextView title;
        View like;
        LinearLayout tags;
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.main_demoitem_image);
            title = (TextView) itemView.findViewById(R.id.main_demoitem_title);
            like = itemView.findViewById(R.id.main_demoitem_like);
            tags = (LinearLayout) itemView.findViewById(R.id.main_demoitem_tags);
            description = (TextView) itemView.findViewById(R.id.main_demoitem_description);
        }
    }
}
