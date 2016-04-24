package com.audbar.odre.loycards.Fragments;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.audbar.odre.loycards.Adapters.LoyCardGridViewAdapter;
import com.audbar.odre.loycards.Database.DatabaseMethods;
import com.audbar.odre.loycards.Database.DbReaderContract;
import com.audbar.odre.loycards.Database.LocalDatabaseMethods;
import com.audbar.odre.loycards.Database.LoyCardsDbHelper;
import com.audbar.odre.loycards.GlobalVariables;
import com.audbar.odre.loycards.Model.LoyCard;
import com.audbar.odre.loycards.R;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoyCardsListFragment extends Fragment {

    LoyCardsDbHelper mDbHelper;
    Cursor c = null;



    public LoyCardsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

//        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
//        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);
//
//        mAdapter = new ImageAdapter(getActivity());
//
//        ImageCache.ImageCacheParams cacheParams =
//                new ImageCache.ImageCacheParams(getActivity(), IMAGE_CACHE_DIR);
//
//        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory
//
//        // The ImageFetcher takes care of loading images into our ImageView children asynchronously
//        mImageFetcher = new ImageFetcher(getActivity(), mImageThumbSize);
//        mImageFetcher.setLoadingImage(R.drawable.empty_photo);
//        mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(), cacheParams);

        GlobalVariables gVar = (GlobalVariables)getActivity().getApplicationContext();

        mDbHelper = new LoyCardsDbHelper(getActivity().getApplicationContext());

        //kuriam random irasa
        //registerLoyCard(gVar.getGvUserId());

        String android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

/*        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String selection = DbReaderContract.LoyaltyCardDb.COLUMN_NAME_ENTRY_USER_ID + " = " + gVar.getGvUserId();
        String[] selectionArgs = {gVar.getGvUserId()};

        c = db.query(
                DbReaderContract.LoyaltyCardDb.TABLE_NAME,
                projection,
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        ArrayList<String> list=new ArrayList<String>();

        while(c.moveToNext()){
            list.add(c.getString(0));
        }*/



    }

/*    private void registerLoyCard(String userId){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbReaderContract.LoyaltyCardDb.COLUMN_NAME_CODE, randomString(7));
        values.put(DbReaderContract.LoyaltyCardDb.COLUMN_NAME_DATE_CREATED, String.valueOf(new Date()));
        values.put(DbReaderContract.LoyaltyCardDb.COLUMN_NAME_DESCRIPTION, randomString(50));
        values.put(DbReaderContract.LoyaltyCardDb.COLUMN_NAME_TITLE, "Name"+randomString(3));
        values.put(DbReaderContract.LoyaltyCardDb.COLUMN_NAME_ENTRY_USER_ID, userId);

        long newRowId;
        newRowId = db.insert(
                DbReaderContract.LoyaltyCardDb.TABLE_NAME,
                null,
                values);
    }*/



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_loy_cards_list, container, false);

        FloatingActionButton fabNewCard = (FloatingActionButton) view.findViewById(R.id.fab_add_new_card);

        fabNewCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NewLoyCardFragment newCardFragment = new NewLoyCardFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_main, newCardFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        GlobalVariables gVar = (GlobalVariables)getActivity().getApplicationContext();

        //DatabaseMethods.getUserLoyCards(getActivity(), this, gVar.getGvUserId());

        LocalDatabaseMethods localDb = new LocalDatabaseMethods(getActivity().getApplicationContext());
        List<LoyCard> loyCards = localDb.getLocalLoyCards(gVar.getGvUserId());
        handleLoyCardsResult(loyCards, view);

        return view;
    }

    public void handleLoyCardsResult(List<LoyCard> loyCards, View view)
    {
        GridView gvItems = (GridView) view.findViewById(R.id.gridView);

        gvItems.setAdapter(new LoyCardGridViewAdapter(this.getContext(), loyCards));

        gvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Kolkas nerealizuota", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
