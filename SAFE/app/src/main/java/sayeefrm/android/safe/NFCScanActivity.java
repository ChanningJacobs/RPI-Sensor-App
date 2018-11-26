package sayeefrm.android.safe;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NFCScanActivity extends AppCompatActivity {

    private TextView focus;
    private Button acceptButton;
    private Button declineButton;

    private NfcAdapter nfcAdpt;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFilters;

    private String USERS_PATH = "users";
    private String DEVICES_PATH = "devices";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        focus = findViewById(R.id.focus_text);
        focus.setTextColor(Color.LTGRAY);
        acceptButton = findViewById(R.id.accept);
        declineButton = findViewById(R.id.decline);
        acceptButton.setEnabled(false);
        declineButton.setEnabled(false);

        nfcAdpt = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType("text/plain");
        }
        catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        intentFilters = new IntentFilter[] {ndef};

        setContentView(R.layout.activity_nfcscan);
    }

    public void onResume() {
        super.onResume();
        nfcAdpt.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
    }

    protected void onNewIntent(Intent intent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages =
                    intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if(rawMessages.length > 0) {
                NdefMessage message = (NdefMessage) rawMessages[0];
                if(message.getRecords() != null) {
                    NdefRecord record = message.getRecords()[0];
                    try {
                        String device_id = new String(record.getPayload(), "UTF-8");
                        focus.setText(device_id);
                        focus.setTypeface(null, Typeface.BOLD);
                        focus.setTextColor(Color.BLUE);
                        acceptButton.setEnabled(true);
                        declineButton.setEnabled(false);

                        acceptButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch(v.getId()) {
                                    case R.id.accept:
                                        // TODO: handle adding this device to the user's list of devices

                                        nfcAdpt.disableForegroundDispatch(NFCScanActivity.this);
                                        // go back to the device list with the newly added device
                                        Intent device_list_intent = new Intent(NFCScanActivity.this, DeviceList.class);
                                        startActivity(device_list_intent);
                                }
                            }
                        });

                        declineButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch(v.getId()) {
                                    case R.id.decline:
                                        acceptButton.setEnabled(false);
                                        declineButton.setEnabled(false);
                                        focus.setText(R.string.scanning_for_devices);
                                        focus.setTypeface(null, Typeface.ITALIC);
                                        focus.setTextColor(Color.LTGRAY);
                                }
                            }
                        });
                    }
                    catch(Exception e) {
                        // failed to convert the payload to a string
                    }
                }
            }
        }

    }
}
