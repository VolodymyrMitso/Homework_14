package mitso.v.homework_14;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import mitso.v.homework_14.interfaces.ReceiverModule;
import mitso.v.homework_14.receivers.ChargeReceiver;
import mitso.v.homework_14.receivers.HeadphoneReceiver;
import mitso.v.homework_14.receivers.PhoneCallReceiver;
import mitso.v.homework_14.support.Helper;

public class MainActivity extends AppCompatActivity implements ReceiverModule {

    private TextView mTextView_LastRebootTime;
    private TextView mTextView_HeadphoneState;
    private TextView mTextView_LastIncomingCallTime;
    private TextView mTextView_LastIncomingCallPhone;
    private TextView mTextView_ChargeLevel;
    private TextView mTextView_ChargeState;

    private Helper mHelper = new Helper();

    private HeadphoneReceiver mHeadphoneReceiver;
    private PhoneCallReceiver mPhoneCallReceiver;
    private ChargeReceiver mChargeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mHeadphoneReceiver = new HeadphoneReceiver();
        mHeadphoneReceiver.setReceiverModule(this);

        mPhoneCallReceiver = new PhoneCallReceiver();
        mPhoneCallReceiver.setReceiverModule(this);

        mChargeReceiver = new ChargeReceiver();
        mChargeReceiver.setReceiverModule(this);

        mTextView_LastRebootTime = (TextView) findViewById(R.id.tv_LastRebootTime);
        mTextView_HeadphoneState = (TextView) findViewById(R.id.tv_HeadphoneState);
        mTextView_LastIncomingCallTime = (TextView) findViewById(R.id.tv_LastIncomingCallTime);
        mTextView_LastIncomingCallPhone = (TextView) findViewById(R.id.tv_LastIncomingCallPhone);
        mTextView_ChargeLevel = (TextView) findViewById(R.id.tv_ChargeLevel);
        mTextView_ChargeState = (TextView) findViewById(R.id.tv_ChargeState);

        mHelper.setText_LastRebootTime(mTextView_LastRebootTime);
        mHelper.setText_HeadphoneState(this, mTextView_HeadphoneState);
        mHelper.setText_LastIncomingCall(this, mTextView_LastIncomingCallTime, mTextView_LastIncomingCallPhone);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mHeadphoneReceiver, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
        registerReceiver(mChargeReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mHeadphoneReceiver);
        unregisterReceiver(mChargeReceiver);
    }

    @Override
    public void onChangeHeadphone(boolean isConnected) {
        mHelper.setText_HeadphoneState(mTextView_HeadphoneState, isConnected);
    }

    @Override
    public void onNewCall() {
        mHelper.setText_LastIncomingCall(this, mTextView_LastIncomingCallTime, mTextView_LastIncomingCallPhone);
    }

    @Override
    public void onChangeCharge(int chargeLevel, boolean isConnected) {
        mHelper.setText_Charge(mTextView_ChargeLevel, mTextView_ChargeState, chargeLevel, isConnected);
    }
}