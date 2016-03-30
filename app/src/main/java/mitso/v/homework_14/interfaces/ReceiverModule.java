package mitso.v.homework_14.interfaces;

public interface ReceiverModule {

    void onChangeHeadphone(boolean isConnected);
    void onNewCall();
    void onChangeCharge(int chargeLevel, boolean isConnected);
}