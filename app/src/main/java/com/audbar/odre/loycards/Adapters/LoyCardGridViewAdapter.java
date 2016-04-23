package com.audbar.odre.loycards.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.audbar.odre.loycards.Model.LoyCard;
import com.audbar.odre.loycards.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Audrius on 2016-04-23.
 */
public class LoyCardGridViewAdapter extends BaseAdapter {

    private Context context;
    private List<LoyCard> loyCards;

    public LoyCardGridViewAdapter(Context context, List<LoyCard> loyCards) {
        this.context = context;
        this.loyCards = loyCards;
    }

    @Override
    public int getCount() {
        return loyCards.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.loy_card_grid_item, null);
//
//            TextView textView =(TextView) gridView.findViewById(R.id.loy_card_list_item_title);
//            textView.setText(loyCards.get(position).cardNumber);

            ImageView imageView = (ImageView) gridView.findViewById(R.id.loy_card_list_item_img);
            Picasso.with(context).load(loyCards.get(position).imgUrl).into(imageView);

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }
}
