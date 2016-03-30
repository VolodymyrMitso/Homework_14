package mitso.v.homework_14.support;

import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.CallLog;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import mitso.v.homework_14.R;

public class Helper {

    public void setText_LastRebootTime(TextView textView) {
        textView.setText(getLastRebootTime());
    }

    public void setText_HeadphoneState(Context c, TextView textView) {
        if (getHeadphonesState(c))
            textView.setText(c.getResources().getString(R.string.s_headphoneOn));
        else
            textView.setText(c.getResources().getString(R.string.s_headphoneOff));
    }

    public void setText_HeadphoneState(Context c, TextView textView, boolean isConnected) {
        if (isConnected)
            textView.setText(c.getResources().getString(R.string.s_headphoneOn));
        else
            textView.setText(c.getResources().getString(R.string.s_headphoneOff));
    }

    public void setText_LastIncomingCall(Context c, TextView textView1, TextView textView2) {
        textView1.setText(getLastIncomingCall(c).get(0));
        textView2.setText(getLastIncomingCall(c).get(1));
    }

    public void setText_Charge(Context c, TextView textView1, TextView textView2, int chargeLevel, boolean isConnected) {
        String chargePercent = String.valueOf(chargeLevel) + c.getResources().getString(R.string.s_percent);
        textView1.setText(chargePercent);
        if (isConnected)
            textView2.setText(c.getResources().getString(R.string.s_chargeOn));
        else
            textView2.setText(c.getResources().getString(R.string.s_chargeOff));
    }

    private String getLastRebootTime() {
        long currentTime = System.currentTimeMillis();
        long timeFromLastReboot = SystemClock.elapsedRealtime();
        return new SimpleDateFormat("hh:mm:ss").format(new Date(currentTime - timeFromLastReboot));
    }

    private boolean getHeadphonesState(Context c){
        AudioManager am = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
        return am.isWiredHeadsetOn();
    }

    private ArrayList<String> getLastIncomingCall(Context c) {

        ArrayList<String> lastCallDetails = new ArrayList<>();
        lastCallDetails.add(c.getResources().getString(R.string.s_noCalls));
        lastCallDetails.add(c.getResources().getString(R.string.s_noCalls));

        Uri contacts = CallLog.Calls.CONTENT_URI;

        String numberString = "";
        String timeString = "";

        Cursor cursor = null;
        try {
            cursor = c.getContentResolver().query(contacts, null, null, null, null);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        if (cursor != null) {
            int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = cursor.getColumnIndex(CallLog.Calls.DATE);

            while (cursor.moveToNext()) {

                String callNumber = cursor.getString(number);
                String callType = cursor.getString(type);
                String callDate = cursor.getString(date);

                String callTime = new SimpleDateFormat("dd MMMM yyyy - kk:mm:ss").format(new Date(Long.parseLong(callDate)));

                if (Integer.parseInt(callType) == CallLog.Calls.INCOMING_TYPE) {
                    numberString = callNumber;
                    timeString = callTime;
                }
            }
            cursor.close();
        }

        if (!timeString.isEmpty())
            lastCallDetails.add(0, timeString);
        if (!numberString.isEmpty())
            lastCallDetails.set(1, numberString);

        return lastCallDetails;
    }
}
