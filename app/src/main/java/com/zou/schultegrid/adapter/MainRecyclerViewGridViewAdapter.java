package com.zou.schultegrid.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zou.schultegrid.R;
import com.zou.schultegrid.bean.MainGridItemBean;

import java.util.List;

/**
 * ************************************************************
 * description:  <br>
 * packageName:com.zou.schultegrid.adapter mode:SchulteGrid <br>
 * created by 邹高原 on 2018/11/12 10:04<br>
 * last modified time：2018/11/12 10:04 <br>
 *
 * @author 邹高原
 * ************************************************************
 */
public class MainRecyclerViewGridViewAdapter extends RecyclerView.Adapter<MainRecyclerViewGridViewAdapter.ViewHolder> {

    private List<MainGridItemBean> itemBeans;
    private LayoutInflater inflater;
    private View.OnClickListener onItemClickListener;

    public MainRecyclerViewGridViewAdapter(List<MainGridItemBean> itemBeans, Context context) {
        this.itemBeans = itemBeans;
        inflater = LayoutInflater.from(context);
    }

    public MainRecyclerViewGridViewAdapter setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.layout_main_grid_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(itemBeans.get(i).getText());
        viewHolder.rootView.setTag(i);
        viewHolder.rootView.setOnClickListener(onItemClickListener);
        viewHolder.rootView.setEnabled(itemBeans.get(i).isEnable());
    }

    @Override
    public int getItemCount() {
        return itemBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        TextView textView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            textView = rootView.findViewById(R.id.layoutMainGridItemTextView);
        }
    }

}
