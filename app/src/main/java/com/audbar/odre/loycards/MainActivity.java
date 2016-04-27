package com.audbar.odre.loycards;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.audbar.odre.loycards.Database.DatabaseMethods;
import com.audbar.odre.loycards.Database.LocalDatabaseMethods;
import com.audbar.odre.loycards.Database.LoyCardsDbHelper;
import com.audbar.odre.loycards.Fragments.AboutFragment;
import com.audbar.odre.loycards.Fragments.ChangePasswordFragment;
import com.audbar.odre.loycards.Fragments.LoyCardsListFragment;
import com.audbar.odre.loycards.Fragments.MainFragment;
import com.audbar.odre.loycards.Fragments.NewLoyCardFragment;
import com.audbar.odre.loycards.Fragments.OffersFragment;
import com.audbar.odre.loycards.Fragments.SettingsFragment;
import com.audbar.odre.loycards.Model.BaseRecord;
import com.audbar.odre.loycards.Model.LoyCard;
import com.audbar.odre.loycards.Model.RDTSpRecord;
import com.audbar.odre.loycards.Model.User;
import com.audbar.odre.loycards.Servlet.ServletPostAsyncTask;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.OnConnectionFailedListener {

    Toolbar toolbar = null;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignInActivity";
    private GoogleApiClient mGoogleApiClient;
    GoogleSignInAccount acc = null;
    NavigationView navigationView;
    private NfcAdapter nfcAdpt;
    PendingIntent nfcPendingIntent;
    IntentFilter[] intentFiltersArray;
    LoyCardsDbHelper mDbHelper;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new LoyCardsDbHelper(getApplicationContext());

        MainFragment fragment = new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        OptionalPendingResult<GoogleSignInResult> pendingResult =
                Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (pendingResult.isDone()) {
            GoogleSignInResult result = pendingResult.get();
            if (result.isSuccess())
            {
                acc = result.getSignInAccount();
                saveUserInfo(acc);
                updateUI(true);
            }
            else{
                acc = null;
                updateUI(false);
            }
        } else {
            // There's no immediate result ready, displays some progress indicator and waits for the
            // async callback.
            //showProgressIndicator();
            pendingResult.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult result) {
                    if (result.isSuccess())
                    {
                        acc = result.getSignInAccount();
                        saveUserInfo(acc);
                        updateUI(true);
                    }
                    else{
                        acc = null;
                        clearUserInfo();
                        clearUserCards();
                        updateUI(false);
                    }
                }
            });
        }

        nfcAdpt = NfcAdapter.getDefaultAdapter(this);

        // Check if the smartphone has NFC
        if (nfcAdpt == null) {
            Toast.makeText(this, "NFC not supported", Toast.LENGTH_LONG).show();
            finish();
        }

        // Check if NFC is enabled
        if (!nfcAdpt.isEnabled()) {
            Toast.makeText(this, "Enable NFC before using the app", Toast.LENGTH_LONG).show();
        }

        Intent nfcIntent = new Intent(this, getClass());
        nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        nfcPendingIntent =
                PendingIntent.getActivity(this, 0, nfcIntent, 0);

        // Create an Intent Filter limited to the URI or MIME type to
        // intercept TAG scans from.
        IntentFilter tagIntentFilter =
                new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            //tagIntentFilter.addDataScheme("http");
            // tagIntentFilter.addDataScheme("vnd.android.nfc");
            tagIntentFilter.addDataScheme("tel");
            //tagIntentFilter.addDataType("text/plain");
            intentFiltersArray = new IntentFilter[]{tagIntentFilter};
        }
        catch (Throwable t) {
            t.printStackTrace();
        }


       // new ServletPostAsyncTask().execute(new Pair<Context, String>(this, "Manfred"));


    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.d("Nfc", "New intent");
        getTag(intent);
    }

    private void handleIntent(Intent i) {

        Log.d("NFC", "Intent [" + i + "]");

        getTag(i);
    }

    @Override
    protected void onResume() {
        super.onResume();

        nfcAdpt.enableForegroundDispatch(
                this,
                // Intent that will be used to package the Tag Intent.
                nfcPendingIntent,
                // Array of Intent Filters used to declare the Intents you
                // wish to intercept.
                intentFiltersArray,
                // Array of Tag technologies you wish to handle.
                null);
        handleIntent(getIntent());
    }


    @Override
    protected void onPause() {
        super.onPause();
        nfcAdpt.disableForegroundDispatch(this);
    }




    private void getTag(Intent i) {
        if (i == null)
            return ;

        String type = i.getType();
        String action = i.getAction();
        List<BaseRecord> dataList = new ArrayList<BaseRecord>();

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Log.d("Nfc", "Action NDEF Found");
            Parcelable[] parcs = i.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            // List record


            for (Parcelable p : parcs) {
                NdefMessage msg = (NdefMessage) p;
                final int numRec = msg.getRecords().length;

                NdefRecord[] records = msg.getRecords();
                for (NdefRecord record: records) {

                    BaseRecord result = NDEFRecordFactory.createRecord(record);
                    if (result != null)
                    Toast.makeText(this, result.payload, Toast.LENGTH_LONG).show();
                    if (result instanceof RDTSpRecord)
                        dataList.addAll( ((RDTSpRecord) result).records);
                    else
                        dataList.add(result);

                }
            }
        }

    }


    private void saveUserInfo(GoogleSignInAccount acc){
        GlobalVariables gVar = (GlobalVariables)getApplicationContext();
        gVar.setGvUserId(acc.getId());
        gVar.setGvUserName(acc.getDisplayName());
        gVar.setGvUserEmail(acc.getEmail());

        user = new User();
        user.dateCreated = new Date();
        user.deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        user.userName = acc.getDisplayName();
        user.userLastName = "";
        user.imageUrl = acc.getPhotoUrl().toString();
        user.email = acc.getEmail();
        user.googleId = acc.getId();

        DatabaseMethods.registerUser(this, user);

        //syncUserCards(acc.getId());


//        LocalDatabaseMethods localDb = new LocalDatabaseMethods(this.getApplicationContext());
//        localDb.InsertOrUpdateUser(acc.getId(), acc.getDisplayName(), acc.getEmail(), acc.getPhotoUrl().toString(), Settings.Secure.getString(this.getContentResolver(),
//                Settings.Secure.ANDROID_ID));
    }

    private void clearUserInfo(){
        GlobalVariables gVar = (GlobalVariables)getApplicationContext();
        gVar.setGvUserId("");
        gVar.setGvUserName("");
        gVar.setGvUserEmail("");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_loy_cards_list) {
            LoyCardsListFragment fragment = new LoyCardsListFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_main, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_about) {
            AboutFragment fragment = new AboutFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_main, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
//        }
// else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_settings) {
            SettingsFragment fragment = new SettingsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_main, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_login) {
            signIn();

        } else if (id == R.id.nav_logout) {
            signOut();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        super.onPrepareOptionsMenu(menu);
//        if (acc == null)
//        {
//            menu.getItem(R.id.nav_login).setVisible(true);
//            menu.getItem(R.id.nav_logout).setVisible(false);
//        } else{
//            menu.getItem(R.id.nav_login).setVisible(false);
//            menu.getItem(R.id.nav_logout).setVisible(true);
//        }
//        return true;
//    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());

        if (result.isSuccess()) {
            acc = result.getSignInAccount();
            saveUserInfo(acc);

            updateUI(true);
        } else {
            acc = null;
            clearUserInfo();
            clearUserCards();
            updateUI(false);
        }
    }

    public void syncUserCards(String user_id){
        DatabaseMethods.getUserLoyCards(this, user_id);
    }

    public void clearUserCards(){
        LocalDatabaseMethods localDb = new LocalDatabaseMethods(this.getApplicationContext());
        localDb.clearLoyCards();

    }

    private void updateUI(boolean signedIn) {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        View headerLayout = navigationView.getHeaderView(0);
        TextView tvEmail = (TextView) headerLayout.findViewById(R.id.tvUserEmail);
        TextView tvUserName = (TextView) headerLayout.findViewById(R.id.tvUserName);
        ImageView ivUserPhoto = (ImageView) headerLayout.findViewById(R.id.ivUserPhoto);
        if (signedIn) {
            nav_Menu.findItem(R.id.nav_login).setVisible(false);
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
            nav_Menu.findItem(R.id.nav_loy_cards_list).setEnabled(true);
            //nav_Menu.findItem(R.id.nav_offers).setEnabled(true);
            tvEmail.setText(acc.getEmail());
            tvUserName.setText(acc.getDisplayName());
            Picasso.with(this).load(acc.getPhotoUrl()).into(ivUserPhoto);

        } else {
            nav_Menu.findItem(R.id.nav_login).setVisible(true);
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
            nav_Menu.findItem(R.id.nav_loy_cards_list).setEnabled(false);
            //nav_Menu.findItem(R.id.nav_offers).setEnabled(false);
            ivUserPhoto.setImageResource(R.drawable.common_ic_googleplayservices);
            tvEmail.setText("");
            tvUserName.setText("Neprisijungta");
        }
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        clearUserInfo();
                        clearUserCards();
                        // [END_EXCLUDE]
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        clearUserInfo();
                        clearUserCards();
                        // [END_EXCLUDE]
                    }
                });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
}
