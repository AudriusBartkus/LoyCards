package com.audbar.odre.loycards.Fragments;


import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.audbar.odre.loycards.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivateLoyaltyCardFragment extends DialogFragment {

    private NfcAdapter nfcAdpt;
    PendingIntent nfcPendingIntent;
    IntentFilter[] intentFiltersArray;

    public ActivateLoyaltyCardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, 0);

        nfcAdpt = NfcAdapter.getDefaultAdapter(getActivity());

        // Check if the smartphone has NFC
        if (nfcAdpt == null) {
            Toast.makeText(getActivity(), "NFC not supported", Toast.LENGTH_LONG).show();
            getActivity().finish();
        }

        // Check if NFC is enabled
        if (!nfcAdpt.isEnabled()) {
            Toast.makeText(getActivity(), "Enable NFC before using the app", Toast.LENGTH_LONG).show();
        }

        Intent nfcIntent = new Intent(getActivity(), getClass());
        nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        nfcPendingIntent =
                PendingIntent.getActivity(getActivity(), 0, nfcIntent, 0);

        IntentFilter tagIntentFilter =
                new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            tagIntentFilter.addDataType("text/plain");
            intentFiltersArray = new IntentFilter[]{tagIntentFilter};
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }



    private void handleIntent(Intent i) {

        Log.d("NFC", "Intent [" + i + "]");

        getTag(i);
    }

    @Override
    public void onPause() {
        super.onPause();
        nfcAdpt.disableForegroundDispatch(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();

        nfcAdpt.enableForegroundDispatch(
                getActivity(),
                // Intent that will be used to package the Tag Intent.
                nfcPendingIntent,
                // Array of Intent Filters used to declare the Intents you
                // wish to intercept.
                intentFiltersArray,
                // Array of Tag technologies you wish to handle.
                null);
        handleIntent(getActivity().getIntent());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_activate_loyalty_card, container, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }

    private void getTag(Intent i) {
        if (i == null)
            return ;

        String type = i.getType();
        String action = i.getAction();
        Integer j = 0;

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Log.d("Nfc", "Action NDEF Found");
            Parcelable[] parcs = i.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            // List record


            for (Parcelable p : parcs) {
                NdefMessage msg = (NdefMessage) p;
                final int numRec = msg.getRecords().length;

                NdefRecord[] records = msg.getRecords();
                for (NdefRecord record: records) {
                    j++;
                }
            }
        }

    }
}
