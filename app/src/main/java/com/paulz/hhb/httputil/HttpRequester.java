package com.paulz.hhb.httputil;

import com.paulz.hhb.HApplication;
import com.paulz.hhb.common.AppUrls;

import java.util.HashMap;

public class HttpRequester extends com.core.framework.net.HttpRequester{
	
	public HttpRequester() {
		mParams = new HashMap<String, Object>();
//		mParams.put("token", HApplication.getInstance().token);
//		mParams.put("sb_platform", "android");
		mParams.put("sessionid", HApplication.getInstance().session_id);
	}
	
	
}
