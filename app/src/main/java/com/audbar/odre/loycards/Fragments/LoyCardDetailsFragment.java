package com.audbar.odre.loycards.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.audbar.odre.loycards.Model.LoyCard;
import com.audbar.odre.loycards.R;
import com.squareup.picasso.Picasso;


public class LoyCardDetailsFragment extends Fragment {

    private static LoyCard loyCard;

    public LoyCardDetailsFragment() {
        // Required empty public constructor
    }


    public static LoyCardDetailsFragment newInstance(LoyCard card) {
        LoyCardDetailsFragment fragment = new LoyCardDetailsFragment();
        loyCard = card;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loy_card_details, container, false);
        TextView tvCardType = (TextView)view.findViewById(R.id.tv_loy_card_name);
        tvCardType.setText(loyCard.cardType);
        ImageView ivLogo = (ImageView)view.findViewById(R.id.iv_logo);
        Picasso.with(getContext()).load(loyCard.imgUrl).into(ivLogo);
        TextView tvCardNumber = (TextView)view.findViewById(R.id.tv_card_number);
        tvCardNumber.setText(loyCard.cardNumber);
        return view;

    }
}
