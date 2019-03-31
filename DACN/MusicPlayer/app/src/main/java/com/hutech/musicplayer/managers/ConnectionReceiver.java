package com.hutech.musicplayer.managers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionReceiver extends BroadcastReceiver {
    Context mcontext;
    public interface ListenerNetwork{
        void onNetWorkStateChange(boolean isCheck);
    }
    ListenerNetwork listenerNetwork;
    public ConnectionReceiver(Context context, ListenerNetwork listenerNetwork){
        mcontext = context;
        this.listenerNetwork = listenerNetwork;
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(this,intentFilter);
    }
    public void unRegister()
    {
        mcontext.unregisterReceiver(this);
    }
    ConnectivityManager connectivityManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        listenerNetwork.onNetWorkStateChange(checkConnectivity());
    }

    public boolean checkConnectivity()
    {
        connectivityManager = (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        }

        if (!networkInfo.isConnected()) {
            return false;
        }

        if (!networkInfo.isAvailable()) {
            return false;
        }
        return true;
    }

}
