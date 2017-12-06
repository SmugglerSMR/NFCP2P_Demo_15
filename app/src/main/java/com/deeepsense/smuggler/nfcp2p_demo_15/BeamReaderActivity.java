package com.deeepsense.smuggler.nfcp2p_demo_15;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import org.ndeftools.Message;
import org.ndeftools.MimeRecord;
import org.ndeftools.Record;
import org.ndeftools.externaltype.ExternalTypeRecord;
import org.ndeftools.util.activity.NfcReaderActivity;
import org.ndeftools.wellknown.TextRecord;


public class BeamReaderActivity extends NfcReaderActivity {
    private static final String TAG = BeamReaderActivity.class.getName();
    private static final String ACTION = "com.deeepsense.smuggler.nfcp2p_demo_15.action.READ_TAG";
    protected Message message;
    private Button buttonCancel;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, BeamReaderActivity.class);
        intent.setAction(ACTION);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.activity_beam_reader);
        setDetecting(true);

        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onNfcStateEnabled() {
        ToastMaker.toast(this, R.string.nfcAvailableEnabled);
    }

    @Override
    protected void onNfcStateDisabled() {
        ToastMaker.toast(this, R.string.nfcAvailableDisabled);
        startNfcSettingsActivity();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onNfcStateChange(boolean enabled) {
        if (enabled) {
            ToastMaker.toast(this, R.string.nfcAvailableEnabled);
        } else {
            ToastMaker.toast(this, R.string.nfcAvailableDisabled);
        }
    }

    @Override
    public void nfcIntentDetected(Intent intent, String action) {
        super.nfcIntentDetected(intent, action);
        //vibrate(200);
    }

    @Override
    protected void onNfcFeatureNotFound() {
        ToastMaker.toast(this, R.string.noNfcMessage);
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onTagLost() {
        ToastMaker.toast(this, R.string.noNfcMessage);
    }

    @Override
    protected void readNdefMessage(Message message) {
        if (message.size() > 1) {
             ToastMaker.toast(this, R.string.readMultipleRecordNDEFMessage);
        } else {
             ToastMaker.toast(this, R.string.readSingleRecordNDEFMessage);
        }

        this.message = message;

        // process message

        // show in log
        if (message != null) {
            // iterate through all records in message
            Log.d(TAG, "Found " + message.size() + " NDEF records");

            for (int k = 0; k < message.size(); k++) {
                Record record = message.get(k);

                Log.d(TAG, "Record " + k + " type "
                        + record.getClass().getSimpleName());

                // your own code here, for example:
                if (record instanceof MimeRecord) {
                    // ..
                } else if (record instanceof ExternalTypeRecord) {
                    // ..
                } else if (record instanceof TextRecord) {
                    // ..
                } else { // more else
                    // ..
                }
            }
        }

        setResult(Activity.RESULT_OK,
                BeamReaderFragment.getIntentForResult(message));
        finish();
    }

    @Override
    protected void readEmptyNdefMessage() {
        ToastMaker.toast(this, R.string.readEmptyMessage);
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    protected void readNonNdefMessage() {
        ToastMaker.toast(this, R.string.readNonNDEFMessage);
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
