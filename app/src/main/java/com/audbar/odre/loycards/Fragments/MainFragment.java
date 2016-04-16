package com.audbar.odre.loycards.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.audbar.odre.loycards.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Button bActivateCard = (Button) view.findViewById(R.id.b_activate_loy_card);

        bActivateCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                ActivateLoyaltyCardFragment activateLoyaltyCardFragment = new ActivateLoyaltyCardFragment();
//                activateLoyaltyCardFragment.setShowsDialog(true);
//                activateLoyaltyCardFragment.show(getFragmentManager(), "dialog");
            }
        });

        return view;
    }

}
