package com.audbar.odre.loycards.Fragments;


import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.audbar.odre.loycards.Adapters.LoyCardListViewCursorAdapter;
import com.audbar.odre.loycards.Database.DbReaderContract;
import com.audbar.odre.loycards.Database.LoyCardsDbHelper;
import com.audbar.odre.loycards.GlobalVariables;
import com.audbar.odre.loycards.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoyCardsListFragment extends Fragment {

    LoyCardsDbHelper mDbHelper;
    Cursor c = null;

    String[] projection = {
            DbReaderContract.LoyaltyCardTypesDb.COLUMN_NAME_TITLE,
            DbReaderContract.LoyaltyCardTypesDb.COLUMN_NAME_IMAGE_URL
    };

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

        mDbHelper = new LoyCardsDbHelper(getContext());

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



    String randomString( int len ){
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();

        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_loy_cards_list, container, false);

        GridView lvItems = (GridView) view.findViewById(R.id.gridView);
        LoyCardListViewCursorAdapter adapter = new LoyCardListViewCursorAdapter(this.getContext(), c);
        lvItems.setAdapter(adapter);

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

        return view;
    }

//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    @Override
//    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//        final Intent i = new Intent(getActivity(), ImageDetailActivity.class);
//        i.putExtra(ImageDetailActivity.EXTRA_IMAGE, (int) id);
//        if (Utils.hasJellyBean()) {
//            // makeThumbnailScaleUpAnimation() looks kind of ugly here as the loading spinner may
//            // show plus the thumbnail image in GridView is cropped. so using
//            // makeScaleUpAnimation() instead.
//            ActivityOptions options =
//                    ActivityOptions.makeScaleUpAnimation(v, 0, 0, v.getWidth(), v.getHeight());
//            getActivity().startActivity(i, options.toBundle());
//        } else {
//            startActivity(i);
//        }
//    }

}
