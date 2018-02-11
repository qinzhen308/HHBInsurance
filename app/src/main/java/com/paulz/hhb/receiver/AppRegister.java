package com.paulz.hhb.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.paulz.hhb.common.AppStatic;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class AppRegister extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
		msgApi.registerApp(AppStatic.WX_APPID);
	}
}
