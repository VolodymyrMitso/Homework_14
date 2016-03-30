package mitso.v.homework_14.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import mitso.v.homework_14.interfaces.ReceiverModule;

public class ChargeReceiver extends BroadcastReceiver {

    private ReceiverModule receiverModule;

    @Override
    public void onReceive(Context c, Intent intent) {
        int chargeLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

        int chargeStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isConnected =
                        chargeStatus == BatteryManager.BATTERY_STATUS_CHARGING ||
                        chargeStatus == BatteryManager.BATTERY_STATUS_FULL;

        receiverModule.onChangeCharge(chargeLevel, isConnected);
    }

    public void setReceiverModule(Context context) {
        this.receiverModule = (ReceiverModule) context;
    }
}