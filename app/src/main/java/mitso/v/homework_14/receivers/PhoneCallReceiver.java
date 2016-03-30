package mitso.v.homework_14.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import mitso.v.homework_14.interfaces.ReceiverModule;

public class PhoneCallReceiver extends BroadcastReceiver {

    private ReceiverModule receiverModule;

    @Override
    public void onReceive(Context context, Intent intent) {

        String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (TelephonyManager.EXTRA_STATE_IDLE.equals(phoneState))
            receiverModule.onNewCall();
    }

    public void setReceiverModule(Context context) {
        this.receiverModule = (ReceiverModule) context;
    }
}