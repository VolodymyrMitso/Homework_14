package mitso.v.homework_14.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import mitso.v.homework_14.interfaces.ReceiverModule;

public class HeadphoneReceiver extends BroadcastReceiver {

    private ReceiverModule receiverModule;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int headphoneState = intent.getIntExtra("state", -1);
            switch (headphoneState) {
                case 0:
                    receiverModule.onChangeHeadphone(false);
                    break;
                case 1:
                    receiverModule.onChangeHeadphone(true);
                    break;
            }
        }
    }

    public void setReceiverModule(Context context) {
        this.receiverModule = (ReceiverModule) context;
    }
}