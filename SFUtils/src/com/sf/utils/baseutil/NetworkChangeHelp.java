package com.sf.utils.baseutil;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * listenning for network change
 * @author xieningtao
 *
 */
public class NetworkChangeHelp {

	public static interface NetworkChangeEvent{
		public void onNetworkChangeEvent(boolean connectMobile,boolean connectWifi);
	}
	
	private NetworkChangeEvent event;
	private static NetworkChangeHelp instance=new NetworkChangeHelp();
	private Activity activity;
	
	private BroadcastReceiver connectionReceiver=new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			boolean connectMobile=false;
			boolean connectWifi=false;
			ConnectivityManager connectMgr = (ConnectivityManager) activity.getSystemService(Activity.CONNECTIVITY_SERVICE); 
			NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); 
			NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI); 
			if (mobNetInfo.isConnected() && wifiNetInfo.isConnected()) { // network
				connectMobile=true;
				connectWifi=true;
			}else if(mobNetInfo.isConnected()) { //mobile network
				connectMobile=true;
				connectWifi=false;
			} else if(wifiNetInfo.isConnected()){//wifi network
				connectMobile=false;
				connectWifi=true;
			}else{//no network
				connectMobile=false;
				connectWifi=false;
			}
			if(event!=null)event.onNetworkChangeEvent(connectMobile, connectWifi);
			} 
	};
	
	private NetworkChangeHelp(){
		
	}
	
	public static NetworkChangeHelp getInstance(){
		return instance;
	}
	
	public void register(Activity activity,NetworkChangeEvent event){
		if(activity==null)throw new NullPointerException("activity can not be null");
		this.event=event;
		IntentFilter intentFilter = new IntentFilter(); 
		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION); 
		activity.registerReceiver(connectionReceiver, intentFilter);
	}
	
	public void unregister(){
		if(activity==null)return;
		if(connectionReceiver!=null){
			activity.unregisterReceiver(connectionReceiver);
			connectionReceiver=null;
		}
		event=null;
	}
}
