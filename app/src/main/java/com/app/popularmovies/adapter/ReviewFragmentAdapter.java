package com.app.popularmovies.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.popularmovies.R;
import com.app.popularmovies.model.Review;

import java.util.List;

/**
 * Created by Arif on 26-Dec-15.
 */
public class ReviewFragmentAdapter extends BaseAdapter {


    private final Context mContext;
    private final List<Review> reviewList;

    public ReviewFragmentAdapter(Context context, List<Review> reviewList) {
        this.mContext = context;
        this.reviewList = reviewList;
    }

    @Override
    public int getCount() {
        return reviewList.size();
    }

    @Override
    public Object getItem(int i) {
        return reviewList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.review_list_item, parent, false);
            holder = new ViewHolder();
            holder.reviewTV = (TextView) convertView.findViewById(R.id.reviewTV);
            holder.authorTV = (TextView) convertView.findViewById(R.id.authorTV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.reviewTV.setText(reviewList.get(i).getContent());
        holder.authorTV.setText("- "+reviewList.get(i).getAuthor());

        return convertView;
    }

    static class ViewHolder{
        TextView authorTV,reviewTV;

    }
}
