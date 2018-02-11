package com.paulz.hhb.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.core.framework.store.sharePer.PreferencesUtils;
import com.paulz.hhb.HApplication;
import com.paulz.hhb.R;
import com.paulz.hhb.common.AppStatic;
import com.paulz.hhb.utils.AppUtil;

import cn.jpush.android.api.JPushInterface;

public class LaunchActivity extends Activity{
	TextView tvVersion;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);

		if(JPushInterface.isPushStopped(getApplicationContext())){
			JPushInterface.resumePush(getApplicationContext());
		}
		tvVersion=(TextView)findViewById(R.id.tv_version);
		tvVersion.setText("保险  for android v"+ HApplication.getInstance().getVersionName());
		if(!PreferencesUtils.getBoolean("not_new_install")){
			PreferencesUtils.putBoolean("not_new_install",true);
		}
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(false){
//				if(HApplication.getInstance().isNewVison()){
					GuideActivity.invoke(LaunchActivity.this);
				}else {
					MainActivity.invoke(LaunchActivity.this);
/*
					if(AppStatic.getInstance().isLogin && !AppUtil.isNull(HApplication.getInstance().session_id)){//已经登录了
						MainActivity.invoke(LaunchActivity.this);
					}else {
						UserLoginActivity.invoke(LaunchActivity.this,false);
					}*/
				}
				finish();
			}
		},4000);
	}
	
	

}
