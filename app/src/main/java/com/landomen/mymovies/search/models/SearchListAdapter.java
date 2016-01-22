package com.landomen.mymovies.search.models;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.landomen.mymovies.R;
import com.landomen.mymovies.rest.models.Search;
import com.landomen.mymovies.rest.models.SearchResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom ListView adapter for displaying search results.
 * Enables multi selection of items.
 */
public class SearchListAdapter extends BaseAdapter {

    private List<Search> searchItems;
    private Context context;

    public SearchListAdapter(Context context, SearchResponse searchResponse) {
        this.context = context;
        this.searchItems = searchResponse.getSearch();
    }

    @Override
    public int getCount() {
        return searchItems.size();
    }

    @Override
    public Object getItem(int position) {
        return searchItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Returns a list of IMDB IDs for selected items.
     *
     * @return
     */
    public List<String> getSelectedItemsIds() {
        List<String> checked = new ArrayList<>();
        for (Search s :
                searchItems) {
            if (s.isSelected())
                checked.add(s.getImdbID());
        }
        return checked;
    }

    /**
     * Unchecks all movies
     */
    public void removeSelection() {
        for (Search s :
                searchItems) {
            if (s.isSelected())
                s.setIsSelected(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Search item = (Search) getItem(position);
        final SearchItemViewHolder viewHolder;

        if (convertView == null) {
            convertView = ((Activity) context).getLayoutInflater().inflate(R.layout.search_item_row, parent, false);
            viewHolder = new SearchItemViewHolder();
            viewHolder.titleTV = (TextView) convertView.findViewById(R.id.search_item_title);
            viewHolder.yearTV = (TextView) convertView.findViewById(R.id.search_item_year);
            viewHolder.poster = (ImageView) convertView.findViewById(R.id.search_item_poster);
            viewHolder.selected = (CheckBox) convertView.findViewById(R.id.search_item_select);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SearchItemViewHolder) convertView.getTag();
        }

        viewHolder.titleTV.setText(item.getTitle());
        viewHolder.yearTV.setText(String.valueOf(item.getYear()));
        viewHolder.selected.setChecked(item.isSelected());
        viewHolder.selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setIsSelected(viewHolder.selected.isChecked());
            }
        });
        Picasso.with(context)
                .load(item.getPosterUrl())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(viewHolder.poster);

        return convertView;
    }

    private static class SearchItemViewHolder {
        TextView titleTV, yearTV;
        ImageView poster;
        CheckBox selected;
    }
}
