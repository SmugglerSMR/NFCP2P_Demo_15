package com.deeepsense.smuggler.nfcp2p_demo_15;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import org.ndeftools.Message;
import org.ndeftools.util.activity.NfcBeamWriterActivity;
import org.ndeftools.wellknown.UriRecord;


@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class BeamWriterActivity extends NfcBeamWriterActivity {

    private static final String EXTRA = "com.deeepsense.smuggler.nfcp2p_demo_15.TEXT";
    private static final String TAG = BeamWriterActivity.class.getName();
    private static final String ACTION = "com.deeepsense.smuggler.nfcp2p_demo_15.BEAM_MESSAGE";
    private static final int MESSAGE_SENT = 1;
    protected Message message;
    private String text;
    private Button buttonCancel;

    public static Intent getIntent(Context context, String text) {
        Intent intent = new Intent(context, BeamWriterActivity.class);
        intent.setAction(ACTION);
        intent.putExtra(EXTRA, text);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_beam_writer);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.activity_beam_writer);
        setDetecting(true);
        startPushing();

        text = getIntent().getStringExtra(EXTRA);
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
        if(enabled) {
            ToastMaker.toast(this, R.string.nfcAvailableEnabled);
        } else {
            ToastMaker.toast(this, R.string.nfcAvailableDisabled);
        }
    }

    @Override
    protected void onNfcFeatureNotFound() {
        ToastMaker.toast(this, R.string.noNfcMessage);
    }

    @Override
    protected void onTagLost() {
        ToastMaker.toast(this, R.string.noNfcMessage);
    }

    @Override
    protected void onNfcPushStateEnabled() {
        ToastMaker.toast(this, R.string.nfcBeamAvailableEnabled);
    }

    @Override
    protected void onNfcPushStateDisabled() {
        ToastMaker.toast(this, R.string.nfcBeamAvailableDisabled);
        startNfcSettingsActivity();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onNfcPushStateChange(boolean enabled) {
        if(enabled) {
            ToastMaker.toast(this, R.string.nfcBeamAvailableEnabled);
        } else {
            ToastMaker.toast(this, R.string.nfcBeamAvailableDisabled);
        }
    }

    @Override
    protected void onNdefPushCompleted() {
        mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();
    }

    /** This handler receives a message from onNdefPushComplete */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MESSAGE_SENT:
                    onNdefPushCompleteMessage();
                    break;
            }
        }
    };

    private void onNdefPushCompleteMessage() {
        ToastMaker.toast(this, R.string.nfcBeamed);

        //Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;
        //vibe.vibrate(500);

        setResult(Activity.RESULT_OK);
        finish();
    }
    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        Log.d(TAG, "Create message to be beamed");

        Message message = new Message();
        Uri uri = Uri.parse(text);
        UriRecord uriRecord = new UriRecord();
        uriRecord.setUri(uri);
        message.add(uriRecord);

        return message.getNdefMessage();
    }

    @Override
    protected void readNdefMessage(Message message) {
        if(message.size() > 1) {
		    ToastMaker.toast(this, R.string.readMultipleRecordNDEFMessage);
		} else {
		    ToastMaker.toast(this, R.string.readSingleRecordNDEFMessage);
		}
    }

    @Override
    protected void readEmptyNdefMessage() {
        ToastMaker.toast(this, R.string.readEmptyMessage);
    }

    @Override
    protected void readNonNdefMessage() {
        ToastMaker.toast(this, R.string.readNonNDEFMessage);
    }
}
