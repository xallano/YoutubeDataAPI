package com.example.xavier.youtubedataapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;



public class Adapter extends ArrayAdapter<ResponseVideo.ItemsBean> {

    private final LayoutInflater layoutInflater;

    public Adapter(Context context, List<ResponseVideo.ItemsBean> objects) {
        super(context, 0, objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.viewholder, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.details = (TextView) convertView.findViewById(R.id.description);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageVideo);
            viewHolder.channelTitle = (TextView) convertView.findViewById(R.id.channelTitle);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ResponseVideo.ItemsBean item = getItem(position);
        viewHolder.title.setText(item.getSnippet().getTitle());
        viewHolder.details.setText(item.getSnippet().getDescription());
        viewHolder.channelTitle.setText(item.getSnippet().getChannelTitle());
        new Image((ImageView) convertView.findViewById(R.id.imageVideo)) .execute(item.getSnippet().getThumbnails().getDefaultX().getUrl());

        return convertView;
    }
    static class ViewHolder {
        private TextView title;
        private TextView details;
        private ImageView imageView;
        private TextView channelTitle;
    }
}
